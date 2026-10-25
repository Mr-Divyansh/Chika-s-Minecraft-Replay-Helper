/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import org.bytedeco.javacpp.ClassProperties;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.tools.BuildEnabled;
import org.bytedeco.javacpp.tools.ClassScanner;
import org.bytedeco.javacpp.tools.CommandExecutor;
import org.bytedeco.javacpp.tools.Generator;
import org.bytedeco.javacpp.tools.Logger;
import org.bytedeco.javacpp.tools.Parser;
import org.bytedeco.javacpp.tools.ParserException;
import org.bytedeco.javacpp.tools.UserClassLoader;

public class Builder {
    final Logger logger;
    String encoding = null;
    File outputDirectory = null;
    String outputName = null;
    File configDirectory = null;
    String jarPrefix = null;
    boolean clean = false;
    boolean generate = true;
    boolean compile = true;
    boolean deleteJniFiles = true;
    boolean header = false;
    boolean copyLibs = false;
    boolean copyResources = false;
    Properties properties = null;
    ClassScanner classScanner = null;
    String[] buildCommand = null;
    File workingDirectory = null;
    Map<String, String> environmentVariables = null;
    Collection<String> compilerOptions = null;
    CommandExecutor commandExecutor = null;

    void cleanOutputDirectory() throws IOException {
        if (this.outputDirectory != null && this.outputDirectory.isDirectory() && this.clean) {
            Loader.deleteDirectory(this.outputDirectory);
        }
    }

    File[] parse(String[] classPath, Class cls) throws IOException, ParserException {
        this.cleanOutputDirectory();
        return new Parser(this.logger, this.properties, this.encoding, null).parse(this.outputDirectory, classPath, cls);
    }

    void includeJavaPaths(ClassProperties properties, boolean header) {
        String macpath;
        if (properties.getProperty("platform", "").startsWith("android")) {
            return;
        }
        String platform = Loader.getPlatform();
        final String jvmlink = properties.getProperty("platform.link.prefix", "") + "jvm" + properties.getProperty("platform.link.suffix", "");
        final String jvmlib = properties.getProperty("platform.library.prefix", "") + "jvm" + properties.getProperty("platform.library.suffix", "");
        final HashSet<String> jnipath = new HashSet<String>();
        final HashSet<String> jvmpath = new HashSet<String>();
        FilenameFilter filter = new FilenameFilter(){

            @Override
            public boolean accept(File dir, String name) {
                if (new File(dir, "jni.h").exists()) {
                    jnipath.add(dir.getAbsolutePath());
                }
                if (new File(dir, "jni_md.h").exists()) {
                    jnipath.add(dir.getAbsolutePath());
                }
                if (new File(dir, jvmlink).exists()) {
                    jvmpath.add(dir.getAbsolutePath());
                }
                if (new File(dir, jvmlib).exists()) {
                    jvmpath.add(dir.getAbsolutePath());
                }
                return new File(dir, name).isDirectory();
            }
        };
        File[] javaHomes = new File[2];
        try {
            javaHomes[0] = Loader.getCanonicalFile(new File(System.getProperty("java.home")));
            javaHomes[1] = Loader.getCanonicalFile(javaHomes[0].getParentFile());
        }
        catch (IOException | NullPointerException e) {
            this.logger.warn("Could not include header files from java.home:" + e);
            return;
        }
        for (File javaHome : javaHomes) {
            jnipath.clear();
            jvmpath.clear();
            ArrayList<File> dirs = new ArrayList<File>(Arrays.asList(javaHome.listFiles(filter)));
            while (!dirs.isEmpty()) {
                File d = dirs.remove(dirs.size() - 1);
                String dpath = d.getPath();
                File[] files = d.listFiles(filter);
                if (dpath == null || files == null) continue;
                for (File f : files) {
                    try {
                        f = Loader.getCanonicalFile(f);
                    }
                    catch (IOException e) {
                        f = f.getAbsoluteFile();
                    }
                    if (dpath.startsWith(f.getPath())) continue;
                    dirs.add(f);
                }
            }
            if (!jnipath.isEmpty() && !jvmpath.isEmpty()) break;
        }
        if (jnipath.isEmpty() && new File(macpath = "/System/Library/Frameworks/JavaVM.framework/Headers/").isDirectory()) {
            jnipath.add(macpath);
        }
        properties.addAll("platform.includepath", jnipath);
        if (platform.equals(properties.getProperty("platform", platform)) && header) {
            properties.get("platform.link").add(0, "jvm");
            properties.addAll("platform.linkpath", jvmpath);
            if (platform.startsWith("macosx") && jvmpath.isEmpty()) {
                properties.addAll("platform.framework", "JavaVM");
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    int compile(String[] sourceFilenames, String outputFilename, ClassProperties properties, File workingDirectory) throws IOException, InterruptedException {
        void var11_30;
        ArrayList<String> command = new ArrayList<String>();
        this.includeJavaPaths(properties, this.header);
        String platform = Loader.getPlatform();
        String compilerPath = properties.getProperty("platform.compiler");
        command.add(compilerPath);
        String p = properties.getProperty("platform.sysroot.prefix", "");
        for (String s : properties.get("platform.sysroot")) {
            File file = new File(s);
            if (!file.isDirectory()) continue;
            s = Loader.getCanonicalPath(file);
            if (p.endsWith(" ")) {
                command.add(p.trim());
                command.add(s);
                continue;
            }
            command.add(p + s);
        }
        p = properties.getProperty("platform.toolchain.prefix", "");
        for (String s : properties.get("platform.toolchain")) {
            File file = new File(s);
            if (!file.isDirectory()) continue;
            s = Loader.getCanonicalPath(file);
            if (p.endsWith(" ")) {
                command.add(p.trim());
                command.add(s);
                continue;
            }
            command.add(p + s);
        }
        p = properties.getProperty("platform.includepath.prefix", "");
        for (String s : properties.get("platform.includepath")) {
            File file = new File(s);
            if (!file.isDirectory()) continue;
            s = Loader.getCanonicalPath(file);
            if (p.endsWith(" ")) {
                command.add(p.trim());
                command.add(s);
                continue;
            }
            command.add(p + s);
        }
        for (String s : properties.get("platform.includeresource")) {
            for (File f : Loader.cacheResources(s)) {
                if (!f.isDirectory()) continue;
                if (p.endsWith(" ")) {
                    command.add(p.trim());
                    command.add(Loader.getCanonicalPath(f));
                    continue;
                }
                command.add(p + Loader.getCanonicalPath(f));
            }
        }
        for (int i = sourceFilenames.length - 1; i >= 0; --i) {
            command.add(sourceFilenames[i]);
        }
        List<String> allOptions = properties.get("platform.compiler.*");
        if (!allOptions.contains("!default") && !allOptions.contains("default")) {
            allOptions.add(0, "default");
        }
        for (String s : allOptions) {
            if (s == null || s.length() == 0) continue;
            String string = "platform.compiler." + s;
            String string2 = properties.getProperty(string);
            if (string2 != null && string2.length() > 0) {
                command.addAll(Arrays.asList(string2.split(" ")));
                continue;
            }
            if ("!default".equals(s) || "default".equals(s)) continue;
            this.logger.warn("Could not get the property named \"" + string + "\"");
        }
        command.addAll(this.compilerOptions);
        String output = properties.getProperty("platform.compiler.output");
        int i = 1;
        while (i < 2 || output != null) {
            if (output != null && output.length() > 0) {
                command.addAll(Arrays.asList(output.split(" ")));
            }
            if (output == null || output.length() == 0 || output.endsWith(" ")) {
                command.add(outputFilename);
            } else {
                command.add((String)command.remove(command.size() - 1) + outputFilename);
            }
            output = properties.getProperty("platform.compiler.output" + ++i);
        }
        String p3 = properties.getProperty("platform.linkpath.prefix", "");
        String string = properties.getProperty("platform.linkpath.prefix2");
        for (String s : properties.get("platform.linkpath")) {
            File[] file2 = new File(s);
            if (!file2.isDirectory()) continue;
            s = Loader.getCanonicalPath((File)file2);
            if (p3.endsWith(" ")) {
                command.add(p3.trim());
                command.add(s);
            } else {
                command.add(p3 + s);
            }
            if (string == null) continue;
            if (string.endsWith(" ")) {
                command.add(string.trim());
                command.add(s);
                continue;
            }
            command.add(string + s);
        }
        for (String s : properties.get("platform.linkresource")) {
            for (File f : Loader.cacheResources(s)) {
                if (!f.isDirectory()) continue;
                if (p3.endsWith(" ")) {
                    command.add(p3.trim());
                    command.add(Loader.getCanonicalPath(f));
                } else {
                    command.add(p3 + Loader.getCanonicalPath(f));
                }
                if (string == null) continue;
                if (string.endsWith(" ")) {
                    command.add(string.trim());
                    command.add(Loader.getCanonicalPath(f));
                    continue;
                }
                command.add(string + Loader.getCanonicalPath(f));
            }
        }
        String p2 = properties.getProperty("platform.link.prefix", "");
        String string3 = properties.getProperty("platform.link.suffix", "");
        String string4 = "";
        String linkSuffix = "";
        ArrayList<String> linkBeforeOptions = new ArrayList<String>();
        ArrayList<String> linkAfterOptions = new ArrayList<String>();
        if (p2.endsWith(" ")) {
            linkBeforeOptions.addAll(Arrays.asList(p2.trim().split(" ")));
        } else {
            int lastSpaceIndex = p2.lastIndexOf(" ");
            if (lastSpaceIndex != -1) {
                linkBeforeOptions.addAll(Arrays.asList(p2.substring(0, lastSpaceIndex).split(" ")));
                String string5 = p2.substring(lastSpaceIndex + 1);
            } else {
                String string6 = p2;
            }
        }
        if (string3.startsWith(" ")) {
            linkAfterOptions.addAll(Arrays.asList(string3.trim().split(" ")));
        } else {
            int firstSpaceIndex = string3.indexOf(" ");
            if (firstSpaceIndex != -1) {
                linkSuffix = string3.substring(0, firstSpaceIndex);
                linkAfterOptions.addAll(Arrays.asList(string3.substring(firstSpaceIndex + 1).split(" ")));
            } else {
                linkSuffix = string3;
            }
        }
        int i2 = command.size();
        for (String s : properties.get("platform.link")) {
            void var12_38;
            String[] libnameversion = s.split("#")[0].split("@");
            s = libnameversion.length == 3 && libnameversion[1].length() == 0 ? libnameversion[0] + libnameversion[2] : libnameversion[0];
            ArrayList<String> l = new ArrayList<String>();
            l.addAll(linkBeforeOptions);
            l.add((String)var12_38 + (s.endsWith("!") ? s.substring(0, s.length() - 1) : s) + linkSuffix);
            l.addAll(linkAfterOptions);
            command.addAll(i2, l);
        }
        p2 = properties.getProperty("platform.frameworkpath.prefix", "");
        for (String string7 : properties.get("platform.frameworkpath")) {
            File file3 = new File(string7);
            if (!file3.isDirectory()) continue;
            String string8 = Loader.getCanonicalPath(file3);
            if (p2.endsWith(" ")) {
                command.add(p2.trim());
                command.add(string8);
                continue;
            }
            command.add(p2 + string8);
        }
        p2 = properties.getProperty("platform.framework.prefix", "");
        String string9 = properties.getProperty("platform.framework.suffix", "");
        for (String s : properties.get("platform.framework")) {
            if (p2.endsWith(" ") && string9.startsWith(" ")) {
                command.add(p2.trim());
                command.add(s);
                command.add(string9.trim());
                continue;
            }
            if (p2.endsWith(" ")) {
                command.add(p2.trim());
                command.add(s + string9);
                continue;
            }
            if (string9.startsWith(" ")) {
                command.add(p2 + s);
                command.add(string9.trim());
                continue;
            }
            command.add(p2 + s + string9);
        }
        boolean windows = platform.startsWith("windows");
        boolean bl = false;
        while (var11_30 < command.size()) {
            void var12_49;
            void var12_47;
            String string10 = (String)command.get((int)var11_30);
            if (string10 == null) {
                String string11 = "";
            }
            if (var12_47.trim().isEmpty() && windows) {
                String string12 = "\"\"";
            }
            command.set((int)var11_30, (String)var12_49);
            ++var11_30;
        }
        return this.commandExecutor.executeCommand(command, workingDirectory, this.environmentVariables);
    }

    File getOutputPath(Class[] classes, String[] sourcePrefixes) throws IOException {
        this.cleanOutputDirectory();
        File outputPath = this.outputDirectory != null ? Loader.getCanonicalFile(this.outputDirectory) : null;
        ClassProperties p = Loader.loadProperties(classes, this.properties, true);
        String platform = this.properties.getProperty("platform");
        String extension = this.properties.getProperty("platform.extension");
        String sourcePrefix = outputPath != null ? outputPath.getPath() + File.separator : "";
        String libraryPath = p.getProperty("platform.library.path", "");
        if (sourcePrefixes != null) {
            sourcePrefixes[0] = sourcePrefixes[1] = sourcePrefix;
        }
        if (outputPath == null) {
            URI uri = null;
            try {
                String resourceName = '/' + classes[0].getName().replace('.', '/') + ".class";
                String resourceURL = Loader.findResource(classes[0], resourceName).toString();
                String packageURI = resourceURL.substring(0, resourceURL.lastIndexOf(47) + 1);
                for (int i = 1; i < classes.length; ++i) {
                    String shortest;
                    String resourceName2 = '/' + classes[i].getName().replace('.', '/') + ".class";
                    String resourceURL2 = Loader.findResource(classes[i], resourceName2).toString();
                    String packageURI2 = resourceURL2.substring(0, resourceURL2.lastIndexOf(47) + 1);
                    String longest = packageURI2.length() >= packageURI.length() ? packageURI2 : packageURI;
                    String string = shortest = packageURI2.length() < packageURI.length() ? packageURI2 : packageURI;
                    while (!longest.startsWith(shortest) && shortest.lastIndexOf(47) > 0) {
                        shortest = shortest.substring(0, shortest.lastIndexOf(47));
                    }
                    packageURI = shortest;
                }
                uri = new URI(packageURI);
                boolean isFile = "file".equals(uri.getScheme());
                File classPath = Loader.getCanonicalFile(new File(this.classScanner.getClassLoader().getPaths()[0]));
                File packageDir = isFile ? new File(uri) : new File(classPath, resourceName.substring(0, resourceName.lastIndexOf(47) + 1));
                uri = new URI(resourceURL.substring(0, resourceURL.length() - resourceName.length() + 1));
                File targetDir = libraryPath.length() > 0 ? (isFile ? new File(uri) : classPath) : new File(packageDir, platform + (extension != null ? extension : ""));
                outputPath = new File(targetDir, libraryPath);
                sourcePrefix = packageDir.getPath() + File.separator;
                if (sourcePrefixes != null) {
                    sourcePrefixes[0] = classPath.getPath() + File.separator;
                    sourcePrefixes[1] = sourcePrefix;
                }
            }
            catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            catch (IllegalArgumentException e) {
                throw new RuntimeException("URI: " + uri, e);
            }
        }
        if (!outputPath.exists()) {
            outputPath.mkdirs();
        }
        return outputPath;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    File[] generateAndCompile(Class[] classes, String outputName, boolean first, boolean last) throws IOException, InterruptedException {
        int i;
        String[] sourcePrefixes = new String[2];
        File outputPath = this.getOutputPath(classes, sourcePrefixes);
        ClassProperties p = Loader.loadProperties(classes, this.properties, true);
        String sourceSuffix = p.getProperty("platform.source.suffix", ".cpp");
        String libraryPrefix = p.getProperty("platform.library.prefix", "");
        String librarySuffix = p.getProperty("platform.library.suffix", "");
        Generator generator = new Generator(this.logger, this.properties, this.encoding);
        String[] sourceFilenames = new String[]{sourcePrefixes[0] + "jnijavacpp" + sourceSuffix, sourcePrefixes[1] + outputName + sourceSuffix};
        String[] configDirectories = new String[]{this.configDirectory != null ? new File(this.configDirectory, "jnijavacpp").getPath() : null, this.configDirectory != null ? new File(this.configDirectory, outputName).getPath() : null};
        String[] headerFilenames = new String[]{null, this.header ? sourcePrefixes[1] + outputName + ".h" : null};
        String[] loadSuffixes = new String[]{"_jnijavacpp", null};
        String[] baseLoadSuffixes = new String[]{null, "_jnijavacpp"};
        String classPath = System.getProperty("java.class.path");
        for (String s : this.classScanner.getClassLoader().getPaths()) {
            classPath = classPath + File.pathSeparator + s;
        }
        String[] classPaths = new String[]{null, classPath};
        Class[][] classesArray = new Class[][]{null, classes};
        String[] libraryNames = new String[]{libraryPrefix + "jnijavacpp" + librarySuffix, libraryPrefix + outputName + librarySuffix};
        File[] outputFiles = null;
        if (outputName.equals("jnijavacpp")) {
            sourceFilenames = new String[]{sourcePrefixes[0] + outputName + sourceSuffix};
            configDirectories = new String[]{this.configDirectory != null ? new File(this.configDirectory, outputName).getPath() : null};
            headerFilenames = new String[]{this.header ? sourcePrefixes[0] + outputName + ".h" : null};
            loadSuffixes = new String[]{null};
            baseLoadSuffixes = new String[]{null};
            classPaths = new String[]{classPath};
            classesArray = new Class[][]{classes};
            libraryNames = new String[]{libraryPrefix + outputName + librarySuffix};
        }
        boolean generated = true;
        String[] jniConfigFilenames = new String[sourceFilenames.length];
        String[] reflectConfigFilenames = new String[sourceFilenames.length];
        for (i = 0; i < sourceFilenames.length; ++i) {
            if (i == 0 && !first) continue;
            this.logger.info("Generating " + sourceFilenames[i]);
            jniConfigFilenames[i] = configDirectories[i] != null ? configDirectories[i] + File.separator + "jni-config.json" : null;
            String string = reflectConfigFilenames[i] = configDirectories[i] != null ? configDirectories[i] + File.separator + "reflect-config.json" : null;
            if (generator.generate(sourceFilenames[i], jniConfigFilenames[i], reflectConfigFilenames[i], headerFilenames[i], loadSuffixes[i], baseLoadSuffixes[i], classPaths[i], classesArray[i])) continue;
            this.logger.info("Nothing generated for " + sourceFilenames[i]);
            generated = false;
            break;
        }
        if (!generated) return outputFiles;
        if (this.compile) {
            int i2;
            int exitValue = 0;
            String s = this.properties.getProperty("platform.library.static", "false").toLowerCase();
            if (s.equals("true") || s.equals("t") || s.equals("")) {
                outputFiles = new File[sourceFilenames.length];
                for (i2 = 0; exitValue == 0 && i2 < sourceFilenames.length; ++i2) {
                    if (i2 == 0 && !first) continue;
                    this.logger.info("Compiling " + outputPath.getPath() + File.separator + libraryNames[i2]);
                    exitValue = this.compile(new String[]{sourceFilenames[i2]}, libraryNames[i2], p, outputPath);
                    outputFiles[i2] = new File(outputPath, libraryNames[i2]);
                }
            } else {
                String libraryName = libraryNames[libraryNames.length - 1];
                this.logger.info("Compiling " + outputPath.getPath() + File.separator + libraryName);
                exitValue = this.compile(sourceFilenames, libraryName, p, outputPath);
                outputFiles = new File[]{new File(outputPath, libraryName)};
            }
            if (exitValue != 0) throw new RuntimeException("Process exited with an error: " + exitValue);
            for (i2 = sourceFilenames.length - 1; i2 >= 0; --i2) {
                if (i2 == 0 && !last) continue;
                if (this.deleteJniFiles) {
                    this.logger.info("Deleting " + sourceFilenames[i2]);
                    new File(sourceFilenames[i2]).delete();
                    continue;
                }
                this.logger.info("Keeping " + sourceFilenames[i2]);
            }
        } else {
            outputFiles = new File[sourceFilenames.length];
            for (i = 0; i < sourceFilenames.length; ++i) {
                outputFiles[i] = new File(sourceFilenames[i]);
            }
        }
        if (this.header) {
            String[] stringArray = headerFilenames;
            int n = stringArray.length;
            for (int j = 0; j < n; ++j) {
                String headerFilename = stringArray[j];
                if (headerFilename == null) continue;
                outputFiles = Arrays.copyOf(outputFiles, outputFiles.length + 1);
                outputFiles[outputFiles.length - 1] = new File(headerFilename);
            }
        }
        if (this.configDirectory == null) return outputFiles;
        for (String jniConfigFilename : jniConfigFilenames) {
            if (jniConfigFilename == null) continue;
            outputFiles = Arrays.copyOf(outputFiles, outputFiles.length + 1);
            outputFiles[outputFiles.length - 1] = new File(jniConfigFilename);
        }
        for (String reflectConfigFilename : reflectConfigFilenames) {
            if (reflectConfigFilename == null) continue;
            outputFiles = Arrays.copyOf(outputFiles, outputFiles.length + 1);
            outputFiles[outputFiles.length - 1] = new File(reflectConfigFilename);
        }
        return outputFiles;
    }

    void createJar(File jarFile, String[] classPath, File ... files) throws IOException {
        this.logger.info("Creating " + jarFile);
        JarOutputStream jos = new JarOutputStream(new FileOutputStream(jarFile));
        for (File f : files) {
            int length;
            String name = f.getPath();
            if (classPath != null) {
                int i;
                String[] names = new String[classPath.length];
                for (i = 0; i < classPath.length; ++i) {
                    String path = Loader.getCanonicalPath(new File(classPath[i]));
                    if (!name.startsWith(path)) continue;
                    names[i] = name.substring(path.length() + 1);
                }
                for (i = 0; i < names.length; ++i) {
                    if (names[i] == null || names[i].length() >= name.length()) continue;
                    name = names[i];
                }
            }
            ZipEntry e = new ZipEntry(name.replace(File.separatorChar, '/'));
            e.setTime(f.lastModified());
            jos.putNextEntry(e);
            FileInputStream fis = new FileInputStream(f);
            byte[] buffer = new byte[65536];
            while ((length = fis.read(buffer)) != -1) {
                jos.write(buffer, 0, length);
            }
            fis.close();
            jos.closeEntry();
        }
        jos.close();
    }

    public Builder() {
        this(Logger.create(Builder.class));
    }

    public Builder(Logger logger) {
        this.logger = logger;
        System.setProperty("org.bytedeco.javacpp.loadlibraries", "false");
        this.properties = Loader.loadProperties();
        this.classScanner = new ClassScanner(logger, new ArrayList<Class>(), new UserClassLoader(Thread.currentThread().getContextClassLoader()));
        this.compilerOptions = new ArrayList<String>();
        this.commandExecutor = new CommandExecutor(logger);
    }

    public Builder classPaths(String classPaths) {
        this.classPaths(classPaths == null ? null : classPaths.split(File.pathSeparator));
        return this;
    }

    public Builder classPaths(String ... classPaths) {
        this.classScanner.getClassLoader().addPaths(classPaths);
        return this;
    }

    public Builder encoding(String encoding) {
        this.encoding = encoding;
        return this;
    }

    public Builder outputDirectory(String outputDirectory) {
        this.outputDirectory(outputDirectory == null ? null : new File(outputDirectory));
        return this;
    }

    public Builder outputDirectory(File outputDirectory) {
        this.outputDirectory = outputDirectory;
        return this;
    }

    public Builder clean(boolean clean) {
        this.clean = clean;
        return this;
    }

    public Builder generate(boolean generate) {
        this.generate = generate;
        return this;
    }

    public Builder compile(boolean compile) {
        this.compile = compile;
        return this;
    }

    public Builder deleteJniFiles(boolean deleteJniFiles) {
        this.deleteJniFiles = deleteJniFiles;
        return this;
    }

    public Builder header(boolean header) {
        this.header = header;
        return this;
    }

    public Builder copyLibs(boolean copyLibs) {
        this.copyLibs = copyLibs;
        return this;
    }

    public Builder copyResources(boolean copyResources) {
        this.copyResources = copyResources;
        return this;
    }

    public Builder outputName(String outputName) {
        this.outputName = outputName;
        return this;
    }

    public Builder configDirectory(String configDirectory) {
        this.configDirectory(configDirectory == null ? null : new File(configDirectory));
        return this;
    }

    public Builder configDirectory(File configDirectory) {
        this.configDirectory = configDirectory;
        return this;
    }

    public Builder jarPrefix(String jarPrefix) {
        this.jarPrefix = jarPrefix;
        return this;
    }

    public Builder properties(String platform) {
        if (platform != null) {
            this.properties = Loader.loadProperties(platform, null);
        }
        return this;
    }

    public Builder properties(Properties properties) {
        if (properties != null) {
            for (Map.Entry<Object, Object> e : properties.entrySet()) {
                this.property((String)e.getKey(), (String)e.getValue());
            }
        }
        return this;
    }

    public Builder propertyFile(String filename) throws IOException {
        this.propertyFile(filename == null ? null : new File(filename));
        return this;
    }

    public Builder propertyFile(File propertyFile) throws IOException {
        if (propertyFile == null) {
            return this;
        }
        FileInputStream fis = new FileInputStream(propertyFile);
        this.properties = new Properties();
        try {
            this.properties.load(new InputStreamReader(fis));
        }
        catch (NoSuchMethodError e) {
            this.properties.load(fis);
        }
        fis.close();
        return this;
    }

    public Builder property(String keyValue) {
        int equalIndex = keyValue.indexOf(61);
        if (equalIndex < 0) {
            equalIndex = keyValue.indexOf(58);
        }
        this.property(keyValue.substring(0, equalIndex), keyValue.substring(equalIndex + 1));
        return this;
    }

    public Builder property(String key, String value) {
        if (key.length() > 0 && value.length() > 0) {
            this.properties.put(key, value);
        }
        return this;
    }

    public Properties getProperties() {
        return this.properties;
    }

    public String getProperty(String key) {
        return this.properties.getProperty(key);
    }

    public Builder addProperty(String key, String ... values) {
        if (values != null && values.length > 0) {
            String separator = this.properties.getProperty("platform.path.separator");
            String v = this.properties.getProperty(key, "");
            for (String s : values) {
                v = v + (v.length() == 0 || v.endsWith(separator) ? s : separator + s);
            }
            this.properties.setProperty(key, v);
        }
        return this;
    }

    public Builder classesOrPackages(String ... classesOrPackages) throws IOException, ClassNotFoundException, NoClassDefFoundError {
        if (classesOrPackages == null) {
            this.classScanner.addPackage(null, true);
        } else {
            for (String s : classesOrPackages) {
                this.classScanner.addClassOrPackage(s);
            }
        }
        return this;
    }

    public Builder buildCommand(String[] buildCommand) {
        this.buildCommand = buildCommand;
        return this;
    }

    public Builder workingDirectory(String workingDirectory) {
        this.workingDirectory(workingDirectory == null ? null : new File(workingDirectory));
        return this;
    }

    public Builder workingDirectory(File workingDirectory) {
        this.workingDirectory = workingDirectory;
        return this;
    }

    public Builder environmentVariables(Map<String, String> environmentVariables) {
        this.environmentVariables = environmentVariables;
        return this;
    }

    public Builder compilerOptions(String ... options) {
        if (options != null) {
            this.compilerOptions.addAll(Arrays.asList(options));
        }
        return this;
    }

    public Builder commandExecutor(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
        return this;
    }

    /*
     * WARNING - void declaration
     */
    public File[] build() throws IOException, InterruptedException, ParserException {
        if (this.buildCommand != null && this.buildCommand.length > 0) {
            int exitValue;
            List<String> command = Arrays.asList(this.buildCommand);
            String paths = this.properties.getProperty("platform.buildpath", "");
            String links = this.properties.getProperty("platform.linkresource", "");
            String resources = this.properties.getProperty("platform.buildresource", "");
            String separator = this.properties.getProperty("platform.path.separator");
            ArrayList<String> arrayList = new ArrayList<String>();
            ClassProperties libProperties = null;
            for (Class clazz : this.classScanner.getClasses()) {
                if (Loader.getEnclosingClass(clazz) != clazz) continue;
                libProperties = Loader.loadProperties(clazz, this.properties, true);
                if (!libProperties.isLoaded()) {
                    this.logger.warn("Could not load platform properties for " + clazz);
                    continue;
                }
                arrayList.addAll(libProperties.get("platform.preload"));
                arrayList.addAll(libProperties.get("platform.link"));
            }
            if (libProperties == null) {
                libProperties = new ClassProperties(this.properties);
            }
            this.includeJavaPaths(libProperties, this.header);
            if (this.environmentVariables == null) {
                this.environmentVariables = new HashMap<String, String>();
            }
            for (Map.Entry entry : libProperties.entrySet()) {
                void var12_52;
                String key = (String)entry.getKey();
                key = key.toUpperCase().replace('.', '_');
                List list = (List)entry.getValue();
                String string = "";
                for (String s : list) {
                    String string2 = (String)var12_52 + (var12_52.length() > 0 && !var12_52.endsWith(separator) ? separator + s : s);
                }
                this.environmentVariables.put(key, (String)var12_52);
            }
            if ((paths = paths.replace(separator, File.pathSeparator)).length() > 0 || resources.length() > 0) {
                for (Iterator<Object> iterator : resources.split(separator)) {
                    for (File f : Loader.cacheResources(iterator)) {
                        String string = Loader.getCanonicalPath(f);
                        if (paths.length() > 0 && !paths.endsWith(File.pathSeparator)) {
                            paths = paths + File.pathSeparator;
                        }
                        paths = paths + string;
                        ArrayList<String> linkPaths = new ArrayList<String>();
                        for (String s2 : links.split(separator)) {
                            for (File f2 : Loader.cacheResources(s2)) {
                                String path2 = Loader.getCanonicalPath(f2);
                                if (!path2.startsWith(string) || path2.equals(string)) continue;
                                linkPaths.add(path2);
                            }
                        }
                        File[] files = f.listFiles();
                        if (files == null) continue;
                        for (File file : files) {
                            Loader.createLibraryLink(file.getAbsolutePath(), libProperties, null, linkPaths.toArray(new String[linkPaths.size()]));
                        }
                    }
                }
                if (paths.length() > 0) {
                    if (this.environmentVariables == null) {
                        this.environmentVariables = new LinkedHashMap<String, String>();
                    }
                    this.environmentVariables.put("BUILD_PATH", paths);
                    this.environmentVariables.put("BUILD_PATH_SEPARATOR", File.pathSeparator);
                }
            }
            if ((exitValue = this.commandExecutor.executeCommand(command, this.workingDirectory, this.environmentVariables)) != 0) {
                throw new RuntimeException("Process exited with an error: " + exitValue);
            }
            return null;
        }
        ArrayList<Object> outputFiles = new ArrayList<Object>();
        ArrayList<String> allNames = new ArrayList<String>();
        if (this.classScanner.getClasses().isEmpty()) {
            if (this.outputName != null && this.outputName.equals("jnijavacpp")) {
                File[] files = this.generateAndCompile(null, this.outputName, true, true);
                if (files != null && files.length > 0) {
                    outputFiles.addAll(Arrays.asList(files));
                }
            } else {
                return null;
            }
        }
        HashMap executableMap = new HashMap();
        HashMap<String, LinkedHashSet<Class>> libraryMap = new HashMap<String, LinkedHashSet<Class>>();
        for (Class clazz : this.classScanner.getClasses()) {
            String string;
            if (Loader.getEnclosingClass(clazz) != clazz) continue;
            ClassProperties p = Loader.loadProperties(clazz, this.properties, false);
            if (p.isLoaded()) {
                String target;
                if (Arrays.asList(clazz.getInterfaces()).contains(BuildEnabled.class)) {
                    try {
                        ((BuildEnabled)clazz.newInstance()).init(this.logger, this.properties, this.encoding);
                    }
                    catch (ClassCastException | IllegalAccessException | InstantiationException e) {
                        this.logger.warn("Could not create an instance of " + clazz + ": " + e);
                    }
                }
                if ((target = p.getProperty("global")) != null && !clazz.getName().equals(target)) {
                    File[] files;
                    int n;
                    boolean bl = false;
                    for (Class clazz2 : this.classScanner.getClasses()) {
                        n |= clazz2.getName().equals(target);
                    }
                    if (this.generate && n != false || (files = this.parse(this.classScanner.getClassLoader().getPaths(), clazz)) == null) continue;
                    outputFiles.addAll(Arrays.asList(files));
                    continue;
                }
            }
            if (!p.isLoaded()) {
                p = Loader.loadProperties(clazz, this.properties, true);
            }
            if (!p.isLoaded()) {
                this.logger.warn("Could not load platform properties for " + clazz);
                continue;
            }
            List<String> executableNames = p.get("platform.executable");
            for (String executableName : executableNames) {
                void var11_41;
                LinkedHashSet linkedHashSet = (LinkedHashSet)executableMap.get(executableName = executableName.split("#")[0]);
                if (linkedHashSet == null) {
                    allNames.add(executableName);
                    LinkedHashSet linkedHashSet2 = new LinkedHashSet();
                    executableMap.put(executableName, linkedHashSet2);
                }
                var11_41.addAll(p.getEffectiveClasses());
            }
            if (executableNames.size() > 0) continue;
            String string3 = string = this.outputName != null ? this.outputName : p.getProperty("platform.library", "");
            if (!this.generate || string.length() == 0) continue;
            LinkedHashSet<Class> classList = (LinkedHashSet<Class>)libraryMap.get(string);
            if (classList == null) {
                allNames.add(string);
                classList = new LinkedHashSet<Class>();
                libraryMap.put(string, classList);
            }
            classList.addAll(p.getEffectiveClasses());
        }
        int count = 0;
        for (String name : allNames) {
            void var11_50;
            void var11_47;
            LinkedHashSet executableClassSet = (LinkedHashSet)executableMap.get(name);
            LinkedHashSet linkedHashSet = (LinkedHashSet)libraryMap.get(name);
            Class[] classArray = null;
            Object var11_43 = null;
            if (executableClassSet != null) {
                classArray = executableClassSet.toArray(new Class[executableClassSet.size()]);
                ClassProperties classProperties = Loader.loadProperties(classArray, this.properties, true);
                String prefix = classProperties.getProperty("platform.executable.prefix", "");
                String suffix = classProperties.getProperty("platform.executable.suffix", "");
                String filename = prefix + name + suffix;
                for (String path : classProperties.get("platform.executablepath")) {
                    Path in = Paths.get(path, filename);
                    if (!Files.exists(in, new LinkOption[0])) {
                        in = Paths.get(path, name);
                    }
                    if (!Files.exists(in, new LinkOption[0])) continue;
                    this.logger.info("Copying " + in);
                    File outputPath = this.getOutputPath(classArray, null);
                    Path out = new File(outputPath, filename).toPath();
                    Files.copy(in, out, StandardCopyOption.REPLACE_EXISTING);
                    File[] fileArray = new File[]{out.toFile()};
                    break;
                }
            } else {
                if (linkedHashSet == null) continue;
                classArray = linkedHashSet.toArray(new Class[linkedHashSet.size()]);
                File[] fileArray = this.generateAndCompile(classArray, name, count == 0, count == libraryMap.size() - 1);
                ++count;
            }
            if (var11_47 != null && ((void)var11_47).length > 0) {
                void var12_61;
                ClassProperties p;
                Object var12_59 = null;
                for (String string : var11_47) {
                    if (string == null) continue;
                    File file = ((File)((Object)string)).getParentFile();
                    break;
                }
                outputFiles.addAll(Arrays.asList(var11_47));
                if (this.copyLibs) {
                    p = Loader.loadProperties(classArray, this.properties, false);
                    ArrayList<String> preloads = new ArrayList<String>();
                    preloads.addAll(p.get("platform.preload"));
                    preloads.addAll(p.get("platform.link"));
                    ClassProperties p2 = Loader.loadProperties(classArray, this.properties, true);
                    for (String s : preloads) {
                        void var11_48;
                        File fi;
                        if (s.trim().endsWith("#") || s.trim().length() == 0) continue;
                        URL[] urls = Loader.findLibrary(null, p, s);
                        try {
                            fi = new File(new URI(urls[0].toURI().toString().split("#")[0]));
                        }
                        catch (Exception e) {
                            urls = Loader.findLibrary(null, p2, s);
                            try {
                                fi = new File(new URI(urls[0].toURI().toString().split("#")[0]));
                            }
                            catch (Exception e2) {
                                this.logger.warn("Could not find library " + s);
                                continue;
                            }
                        }
                        File fo = new File((File)var12_61, fi.getName());
                        if (!fi.exists() || outputFiles.contains(fo)) continue;
                        this.logger.info("Copying " + fi);
                        Files.copy(fi.toPath(), fo.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        outputFiles.add(fo);
                        File[] fileArray = (File[])Arrays.copyOf(var11_48, ((void)var11_48).length + 1);
                        fileArray[fileArray.length - 1] = fo;
                    }
                }
                if (this.copyResources) {
                    p = Loader.loadProperties(classArray, this.properties, false);
                    List<String> resources = p.get("platform.resource");
                    p = Loader.loadProperties(classArray, this.properties, true);
                    List<String> paths = p.get("platform.resourcepath");
                    Path path = var12_61.toPath();
                    block30: for (String resource : resources) {
                        final Path target = path.resolve(resource);
                        if (!Files.exists(target, new LinkOption[0])) {
                            Files.createDirectories(target, new FileAttribute[0]);
                        }
                        for (String path2 : paths) {
                            final Path source = Paths.get(path2, resource);
                            if (!Files.exists(source, new LinkOption[0])) continue;
                            this.logger.info("Copying " + source);
                            Files.walkFileTree(source, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE, (FileVisitor<? super Path>)new SimpleFileVisitor<Path>(){

                                @Override
                                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                                    block2: {
                                        Path targetdir = target.resolve(source.relativize(dir));
                                        try {
                                            Files.copy(dir, targetdir, StandardCopyOption.REPLACE_EXISTING);
                                        }
                                        catch (DirectoryNotEmptyException | FileAlreadyExistsException e) {
                                            if (Files.isDirectory(targetdir, new LinkOption[0])) break block2;
                                            throw e;
                                        }
                                    }
                                    return FileVisitResult.CONTINUE;
                                }

                                @Override
                                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                                    Files.copy(file, target.resolve(source.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
                                    return FileVisitResult.CONTINUE;
                                }
                            });
                            File[] fileArray = (File[])Arrays.copyOf(var11_50, ((void)var11_50).length + 1);
                            fileArray[fileArray.length - 1] = source.toFile();
                            continue block30;
                        }
                    }
                }
            }
            if (this.configDirectory == null) continue;
            File file = new File(this.configDirectory, name + "/resource-config.json");
            File dir = file.getParentFile();
            if (dir != null) {
                dir.mkdirs();
            }
            this.logger.info("Generating " + file);
            try (PrintWriter out = this.encoding != null ? new PrintWriter(file, this.encoding) : new PrintWriter(file);){
                out.println("{");
                out.println("  \"resources\": [");
                out.println("    {\"pattern\": \"META-INF/.*\"},");
                out.print("    {\"pattern\": \"org/bytedeco/javacpp/properties/.*\"}");
                String string = "," + System.lineSeparator();
                for (File f : var11_50 != null ? var11_50 : new File[]{}) {
                    if (f == null || f.toPath().startsWith(this.configDirectory.toPath())) continue;
                    out.print(string + "    {\"pattern\": \".*/" + f.getName() + (f.isDirectory() ? "/.*" : "") + "\"}");
                }
                out.println();
                out.println("  ]");
                out.println("}");
            }
            outputFiles.add(file);
        }
        File[] fileArray = outputFiles.toArray(new File[outputFiles.size()]);
        if (this.jarPrefix != null && fileArray.length > 0) {
            File jarFile = new File(this.jarPrefix + "-" + this.properties.getProperty("platform") + this.properties.getProperty("platform.extension", "") + ".jar");
            File d = jarFile.getParentFile();
            if (d != null && !d.exists()) {
                d.mkdir();
            }
            this.createJar(jarFile, this.outputDirectory == null ? this.classScanner.getClassLoader().getPaths() : null, fileArray);
        }
        System.setProperty("org.bytedeco.javacpp.loadlibraries", "true");
        return fileArray;
    }

    public static void printHelp() {
        String version = Builder.class.getPackage().getImplementationVersion();
        if (version == null) {
            version = "unknown";
        }
        System.out.println("JavaCPP version " + version + "\nCopyright (C) 2011-2025 Samuel Audet <samuel.audet@gmail.com>\nProject site: https://github.com/bytedeco/javacpp");
        System.out.println();
        System.out.println("Usage: java -jar javacpp.jar [options] [class or package (suffixed with .* or .**)] [commands]");
        System.out.println();
        System.out.println("where options include:");
        System.out.println();
        System.out.println("    -classpath <path>      Load user classes from path");
        System.out.println("    -encoding <name>       Character encoding used for input and output files");
        System.out.println("    -d <directory>         Output all generated files to directory");
        System.out.println("    -o <name>              Output everything in a file named after given name");
        System.out.println("    -clean                 Delete the output directory before generating anything in it");
        System.out.println("    -nogenerate            Do not try to generate C++ source files, only try to parse header files");
        System.out.println("    -nocompile             Do not compile or delete the generated C++ source files");
        System.out.println("    -nodelete              Do not delete generated C++ JNI files after compilation");
        System.out.println("    -header                Generate header file with declarations of callbacks functions");
        System.out.println("    -copylibs              Copy to output directory dependent libraries (link and preload)");
        System.out.println("    -copyresources         Copy to output directory resources listed in properties");
        System.out.println("    -configdir <directory> Also create config files for GraalVM native-image in directory");
        System.out.println("    -jarprefix <prefix>    Also create a JAR file named \"<prefix>-<platform>.jar\"");
        System.out.println("    -properties <resource> Load all platform properties from resource");
        System.out.println("    -propertyfile <file>   Load all platform properties from file");
        System.out.println("    -D<property>=<value>   Set platform property to value");
        System.out.println("    -Xcompiler <option>    Pass option directly to compiler");
        System.out.println();
        System.out.println("and where optional commands include:");
        System.out.println();
        System.out.println("    -clear                 Before doing anything else, delete all files from the cache");
        System.out.println("    -mod <file>            Output a module-info.java file for native JAR where module name is the package of the first class");
        System.out.println("    -exec [args...]        After build, call java command on the first class");
        System.out.println("    -print <property>      Print the given platform property, for example, \"platform.includepath\", and exit");
        System.out.println("                           \"platform.includepath\" has jni.h, jni_md.h, etc, and \"platform.linkpath\", the jvm library");
        System.out.println();
    }

    public static void main(String[] args) throws Exception {
        Class c;
        boolean clearCacheDir = false;
        boolean addedClasses = false;
        Builder builder = new Builder();
        String[] execArgs = null;
        String moduleFile = null;
        String printPath = null;
        for (int i = 0; i < args.length; ++i) {
            if ("-help".equals(args[i]) || "--help".equals(args[i])) {
                Builder.printHelp();
                System.exit(0);
                continue;
            }
            if ("-classpath".equals(args[i]) || "-cp".equals(args[i]) || "-lib".equals(args[i])) {
                builder.classPaths(args[++i]);
                continue;
            }
            if ("-encoding".equals(args[i])) {
                builder.encoding(args[++i]);
                continue;
            }
            if ("-d".equals(args[i])) {
                builder.outputDirectory(args[++i]);
                continue;
            }
            if ("-o".equals(args[i])) {
                builder.outputName(args[++i]);
                continue;
            }
            if ("-clean".equals(args[i])) {
                builder.clean(true);
                continue;
            }
            if ("-nocpp".equals(args[i]) || "-nogenerate".equals(args[i])) {
                builder.generate(false);
                continue;
            }
            if ("-cpp".equals(args[i]) || "-nocompile".equals(args[i])) {
                builder.compile(false);
                continue;
            }
            if ("-nodelete".equals(args[i])) {
                builder.deleteJniFiles(false);
                continue;
            }
            if ("-header".equals(args[i])) {
                builder.header(true);
                continue;
            }
            if ("-copylibs".equals(args[i])) {
                builder.copyLibs(true);
                continue;
            }
            if ("-copyresources".equals(args[i])) {
                builder.copyResources(true);
                continue;
            }
            if ("-configdir".equals(args[i])) {
                builder.configDirectory(args[++i]);
                continue;
            }
            if ("-jarprefix".equals(args[i])) {
                builder.jarPrefix(args[++i]);
                continue;
            }
            if ("-properties".equals(args[i])) {
                builder.properties(args[++i]);
                continue;
            }
            if ("-propertyfile".equals(args[i])) {
                builder.propertyFile(args[++i]);
                continue;
            }
            if (args[i].startsWith("-D")) {
                builder.property(args[i].length() > 2 ? args[i].substring(2) : args[++i]);
                continue;
            }
            if ("-Xcompiler".equals(args[i])) {
                builder.compilerOptions(args[++i]);
                continue;
            }
            if ("-clear".equals(args[i])) {
                clearCacheDir = true;
                continue;
            }
            if ("-mod".equals(args[i])) {
                moduleFile = args[++i];
                continue;
            }
            if ("-exec".equals(args[i])) {
                execArgs = Arrays.copyOfRange(args, i + 1, args.length);
                i = args.length;
                continue;
            }
            if ("-print".equals(args[i])) {
                printPath = args[++i];
                continue;
            }
            if (args[i].startsWith("-")) {
                builder.logger.error("Invalid option \"" + args[i] + "\"");
                Builder.printHelp();
                System.exit(1);
                continue;
            }
            String arg = args[i];
            if (arg.endsWith(".java")) {
                ArrayList<String> command = new ArrayList<String>(Arrays.asList("javac", "-cp"));
                String paths = System.getProperty("java.class.path");
                for (String path : builder.classScanner.getClassLoader().getPaths()) {
                    paths = paths + File.pathSeparator + path;
                }
                command.add(paths);
                command.add(arg);
                int exitValue = builder.commandExecutor.executeCommand((List<String>)command, builder.workingDirectory, builder.environmentVariables);
                if (exitValue != 0) {
                    throw new RuntimeException("Could not compile " + arg + ": " + exitValue);
                }
                arg = arg.replace(File.separatorChar, '.').replace('/', '.').substring(0, arg.length() - 5);
            }
            builder.classesOrPackages(arg);
            addedClasses = true;
        }
        if (clearCacheDir) {
            Loader.clearCacheDir();
        }
        if (printPath != null) {
            Collection<Class> classes = builder.classScanner.getClasses();
            ClassProperties p = Loader.loadProperties(classes.toArray(new Class[classes.size()]), builder.properties, true);
            builder.includeJavaPaths(p, builder.header);
            for (String s : p.get(printPath)) {
                System.out.println(s);
            }
            System.exit(0);
        } else if (!clearCacheDir && !addedClasses) {
            Builder.printHelp();
            System.exit(2);
        }
        File[] outputFiles = builder.build();
        Collection<Class> classes = builder.classScanner.getClasses();
        if (moduleFile != null) {
            c = classes.iterator().next();
            String pkg = c.getPackage().getName();
            String s = "open module " + pkg + "." + builder.properties.getProperty("platform").replace('-', '.') + " {\n  requires transitive " + pkg + ";\n}\n";
            Path f = Paths.get(moduleFile, new String[0]);
            Path d = f.getParent();
            if (d != null) {
                Files.createDirectories(d, new FileAttribute[0]);
            }
            Files.write(f, s.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }
        if (outputFiles != null && outputFiles.length > 0 && !classes.isEmpty() && execArgs != null) {
            c = classes.iterator().next();
            ArrayList<String> command = new ArrayList<String>(Arrays.asList("java", "-cp"));
            String paths = System.getProperty("java.class.path");
            for (String path : builder.classScanner.getClassLoader().getPaths()) {
                paths = paths + File.pathSeparator + path;
            }
            command.add(paths);
            command.add(c.getCanonicalName());
            command.addAll(Arrays.asList(execArgs));
            System.exit(builder.commandExecutor.executeCommand(command, builder.workingDirectory, builder.environmentVariables));
        }
    }
}

