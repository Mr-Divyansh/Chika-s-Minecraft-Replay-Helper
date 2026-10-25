/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.spi.AbstractInterruptibleChannel;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.bytedeco.javacpp.ClassProperties;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.Platform;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.annotation.Raw;
import org.bytedeco.javacpp.presets.javacpp;
import org.bytedeco.javacpp.tools.Logger;

@Properties(inherit={javacpp.class})
public class Loader {
    private static final Logger logger = Logger.create(Loader.class);
    private static final String PLATFORM = Detector.getPlatform();
    private static final boolean WINDOWS = PLATFORM.startsWith("windows");
    private static java.util.Properties platformProperties = null;
    private static final ThreadLocal<Deque<Class<?>>> classStack = new ThreadLocal<Deque<Class<?>>>(){

        @Override
        protected Deque<Class<?>> initialValue() {
            return new ArrayDeque();
        }
    };
    static File cacheDir = null;
    static File tempDir = null;
    static Map<String, URL[]> foundLibraries = new HashMap<String, URL[]>();
    static Map<String, String> loadedLibraries = new HashMap<String, String>();
    static boolean canCreateSymbolicLink = !WINDOWS;
    static boolean pathsFirst = false;
    static boolean cacheLibraries = true;
    static boolean findLibraries = true;
    static WeakHashMap<Class<? extends Pointer>, HashMap<String, Integer>> memberOffsets;

    public static String getPlatform() {
        return PLATFORM;
    }

    public static String getCanonicalPath(File file) throws IOException {
        return WINDOWS && file.exists() ? file.toPath().toRealPath(new LinkOption[0]).toString() : file.getCanonicalPath();
    }

    public static File getCanonicalFile(File file) throws IOException {
        return WINDOWS && file.exists() ? file.toPath().toRealPath(new LinkOption[0]).toFile() : file.getCanonicalFile();
    }

    public static java.util.Properties loadProperties() {
        return Loader.loadProperties(false);
    }

    public static java.util.Properties loadProperties(boolean forceReload) {
        String name = Loader.getPlatform();
        if (!forceReload && platformProperties != null && name.equals(platformProperties.getProperty("platform"))) {
            return platformProperties;
        }
        platformProperties = Loader.loadProperties(name, null);
        return platformProperties;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    public static java.util.Properties loadProperties(String name, String defaults) {
        java.util.Properties p;
        block31: {
            if (defaults == null) {
                defaults = "generic";
            }
            p = new java.util.Properties();
            p.put("platform", name);
            p.put("platform.path.separator", File.pathSeparator);
            String s = System.mapLibraryName("/");
            int i = s.indexOf(47);
            p.put("platform.library.prefix", s.substring(0, i));
            p.put("platform.library.suffix", s.substring(i + 1));
            name = "properties/" + name + ".properties";
            InputStream is = Loader.class.getResourceAsStream(name);
            try {
                try {
                    p.load(new InputStreamReader(is));
                }
                catch (NoSuchMethodError e) {
                    p.load(is);
                }
            }
            catch (Exception e) {
                name = "properties/" + defaults + ".properties";
                InputStream is2 = Loader.class.getResourceAsStream(name);
                try {
                    p.load(new InputStreamReader(is2));
                }
                catch (NoSuchMethodError e2) {
                    p.load(is2);
                }
                try {
                    if (is2 != null) {
                        is2.close();
                    }
                    break block31;
                }
                catch (IOException ex) {
                    logger.error("Unable to close resource : " + ex.getMessage());
                }
                break block31;
                catch (Exception ex) {
                    try {
                        if (is2 != null) {
                            is2.close();
                        }
                        break block31;
                    }
                    catch (IOException ex2) {
                        logger.error("Unable to close resource : " + ex2.getMessage());
                    }
                    break block31;
                    catch (Throwable throwable) {
                        try {
                            if (is2 != null) {
                                is2.close();
                            }
                        }
                        catch (IOException ex3) {
                            logger.error("Unable to close resource : " + ex3.getMessage());
                        }
                        throw throwable;
                    }
                }
            }
            finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                }
                catch (IOException ex) {
                    logger.error("Unable to close resource : " + ex.getMessage());
                }
            }
        }
        for (Map.Entry<Object, Object> e : System.getProperties().entrySet()) {
            if (!(e.getKey() instanceof String) || !(e.getValue() instanceof String)) continue;
            String key = (String)e.getKey();
            String value = (String)e.getValue();
            if (key == null || value == null || !key.startsWith("org.bytedeco.javacpp.platform.")) continue;
            p.put(key.substring(key.indexOf("platform.")), value);
        }
        return p;
    }

    public static boolean checkVersion(String groupId, String artifactId) {
        return Loader.checkVersion(groupId, artifactId, "-", true, Loader.getCallerClass(2));
    }

    public static boolean checkVersion(String groupId, String artifactId, String separator, boolean logWarnings, Class cls) {
        try {
            int n;
            String javacppVersion = Loader.getVersion();
            String version = Loader.getVersion(groupId, artifactId, cls);
            if (version == null) {
                if (logWarnings && Loader.isLoadLibraries()) {
                    logger.warn("Version of " + groupId + ":" + artifactId + " could not be found.");
                }
                return false;
            }
            String[] javacppVersions = javacppVersion.split(separator);
            String[] versions = version.split(separator);
            boolean matches = versions[n = versions.length - (versions[versions.length - 1].equals("SNAPSHOT") ? 2 : 1)].equals(javacppVersions[0]);
            if (!matches && logWarnings && Loader.isLoadLibraries()) {
                logger.warn("Versions of org.bytedeco:javacpp:" + javacppVersion + " and " + groupId + ":" + artifactId + ":" + version + " do not match.");
            }
            return matches;
        }
        catch (Exception ex) {
            if (logWarnings && Loader.isLoadLibraries()) {
                logger.warn("Unable to load properties : " + ex.getMessage());
            }
            return false;
        }
    }

    public static String getVersion() throws IOException {
        return Loader.getVersion("org.bytedeco", "javacpp");
    }

    public static String getVersion(String groupId, String artifactId) throws IOException {
        return Loader.getVersion(groupId, artifactId, Loader.getCallerClass(2));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String getVersion(String groupId, String artifactId, Class cls) throws IOException {
        java.util.Properties p = new java.util.Properties();
        InputStream is = cls.getClassLoader().getResourceAsStream("META-INF/maven/" + groupId + "/" + artifactId + "/pom.properties");
        if (is == null) {
            return null;
        }
        try {
            try {
                p.load(new InputStreamReader(is));
            }
            catch (NoSuchMethodError e) {
                p.load(is);
            }
            String string = p.getProperty("version");
            return string;
        }
        finally {
            try {
                if (is != null) {
                    is.close();
                }
            }
            catch (IOException ex) {
                logger.error("Unable to close resource : " + ex.getMessage());
            }
        }
    }

    public static Class getEnclosingClass(Class cls) {
        Platform p;
        Class<?> c = cls;
        while (c.getEnclosingClass() != null && !c.isAnnotationPresent(Properties.class) && (!c.isAnnotationPresent(Platform.class) || (p = c.getAnnotation(Platform.class)).pragma().length <= 0 && p.define().length <= 0 && p.exclude().length <= 0 && p.include().length <= 0 && p.cinclude().length <= 0 && p.includepath().length <= 0 && p.includeresource().length <= 0 && p.compiler().length <= 0 && p.linkpath().length <= 0 && p.linkresource().length <= 0 && p.link().length <= 0 && p.frameworkpath().length <= 0 && p.framework().length <= 0 && p.preloadresource().length <= 0 && p.preloadpath().length <= 0 && p.preload().length <= 0 && p.resourcepath().length <= 0 && p.resource().length <= 0 && p.library().length() <= 0)) {
            c = c.getEnclosingClass();
        }
        return c;
    }

    public static ClassProperties loadProperties(Class[] cls, java.util.Properties properties, boolean inherit) {
        ClassProperties cp = new ClassProperties(properties);
        if (cls != null) {
            for (Class c : cls) {
                cp.load(c, inherit);
            }
        }
        return cp;
    }

    public static ClassProperties loadProperties(Class cls, java.util.Properties properties, boolean inherit) {
        ClassProperties cp = new ClassProperties(properties);
        if (cls != null) {
            cp.load(cls, inherit);
        }
        return cp;
    }

    public static Class getCallerClass(int i) {
        Class[] classContext = null;
        try {
            classContext = new SecurityManager(){

                public Class[] getClassContext() {
                    return super.getClassContext();
                }
            }.getClassContext();
        }
        catch (NoSuchMethodError | SecurityException e) {
            logger.warn("Could not create an instance of SecurityManager: " + e.getMessage());
        }
        if (classContext != null) {
            for (int j = 0; j < classContext.length; ++j) {
                if (classContext[j] != Loader.class) continue;
                return classContext[i + j];
            }
        } else {
            try {
                StackTraceElement[] classNames = Thread.currentThread().getStackTrace();
                for (int j = 0; j < classNames.length; ++j) {
                    if (Class.forName(classNames[j].getClassName()) != Loader.class) continue;
                    return Class.forName(classNames[i + j].getClassName());
                }
            }
            catch (ClassNotFoundException e) {
                logger.error("No definition for the class found : " + e.getMessage());
            }
        }
        return null;
    }

    public static File cacheResource(String name) throws IOException {
        Class cls = Loader.getCallerClass(2);
        return Loader.cacheResource(cls, name);
    }

    public static File cacheResource(Class cls, String name) throws IOException {
        URL u = Loader.findResource(cls, name);
        return u != null ? Loader.cacheResource(u) : null;
    }

    public static File[] cacheResources(String name) throws IOException {
        Class cls = Loader.getCallerClass(2);
        return Loader.cacheResources(cls, name);
    }

    public static File[] cacheResources(Class cls, String name) throws IOException {
        URL[] urls = Loader.findResources(cls, name);
        File[] files = new File[urls.length];
        for (int i = 0; i < urls.length; ++i) {
            files[i] = Loader.cacheResource(urls[i]);
        }
        return files;
    }

    public static File cacheResource(URL resourceURL) throws IOException {
        return Loader.cacheResource(resourceURL, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static File cacheResource(URL resourceURL, String target) throws IOException {
        Path path;
        Runtime runtime;
        File cacheDir;
        File urlFile;
        String[] splitURL = resourceURL.toString().split("#");
        try {
            resourceURL = new URL(splitURL[0]);
            urlFile = new File(new URI(splitURL[0]));
        }
        catch (IllegalArgumentException | URISyntaxException e) {
            urlFile = new File(resourceURL.getPath());
        }
        String name = urlFile.getName();
        boolean reference = false;
        long size = 0L;
        long timestamp = 0L;
        File cacheSubdir = cacheDir = Loader.getCacheDir();
        String s = System.getProperty("org.bytedeco.javacpp.cachedir.nosubdir", "false").toLowerCase();
        boolean noSubdir = s.equals("true") || s.equals("t") || s.equals("");
        URLConnection urlConnection = resourceURL.openConnection();
        if (urlConnection instanceof JarURLConnection) {
            JarFile jarFile = ((JarURLConnection)urlConnection).getJarFile();
            JarEntry jarEntry = ((JarURLConnection)urlConnection).getJarEntry();
            File jarFileFile = new File(jarFile.getName());
            File jarEntryFile = new File(jarEntry.getName());
            size = jarEntry.getSize();
            timestamp = jarEntry.getTime();
            if (!noSubdir) {
                String subdirName = jarFileFile.getName();
                String parentName = jarEntryFile.getParent();
                if (parentName != null) {
                    subdirName = subdirName + File.separator + parentName;
                }
                cacheSubdir = new File(cacheSubdir, subdirName);
            }
        } else if (urlConnection instanceof HttpURLConnection) {
            size = urlConnection.getContentLength();
            timestamp = urlConnection.getLastModified();
            if (!noSubdir) {
                String path2 = resourceURL.getHost() + resourceURL.getPath();
                cacheSubdir = new File(cacheSubdir, path2.substring(0, path2.lastIndexOf(47) + 1));
            }
        } else if (resourceURL.getProtocol().equals("jrt")) {
            String p = resourceURL.getPath();
            try {
                Path path3 = Paths.get(new URI("jrt", p, null));
                try {
                    size = Files.size(path3);
                }
                catch (NoSuchFileException e) {
                    path3 = Paths.get(new URI("jrt", "/modules" + p, null));
                    size = Files.size(path3);
                }
                timestamp = Files.getLastModifiedTime(path3, new LinkOption[0]).toMillis();
            }
            catch (URISyntaxException e) {
                size = 0L;
                timestamp = 0L;
            }
            if (!noSubdir) {
                cacheSubdir = new File(cacheSubdir, urlFile.getParentFile().getName());
            }
        } else {
            if (urlFile.exists()) {
                size = urlFile.length();
                timestamp = urlFile.lastModified();
            } else if (urlConnection != null) {
                size = urlConnection.getContentLengthLong();
                timestamp = urlConnection.getLastModified();
            }
            if (!noSubdir) {
                cacheSubdir = new File(cacheSubdir, urlFile.getParentFile().getName());
            }
        }
        if (splitURL.length > 1 && splitURL[1] != null && splitURL[1].length() > 0) {
            String newName = splitURL[1];
            reference = newName.equals(name);
            name = newName;
        }
        File file = new File(cacheSubdir, name);
        File lockFile = new File(cacheDir, ".lock");
        AbstractInterruptibleChannel lockChannel = null;
        FileLock lock = null;
        if (canCreateSymbolicLink && target != null && target.length() > 0) {
            runtime = Runtime.getRuntime();
            synchronized (runtime) {
                block71: {
                    try {
                        path = file.toPath();
                        Path targetPath = Paths.get(target, new String[0]).normalize();
                        if (!(file.exists() && Files.isSymbolicLink(path) && Files.readSymbolicLink(path).equals(targetPath) || !targetPath.isAbsolute() || targetPath.equals(path) || targetPath.toRealPath(new LinkOption[0]).equals(path))) {
                            if (logger.isDebugEnabled()) {
                                logger.debug("Locking " + cacheDir + " to create symbolic link");
                            }
                            lockChannel = new FileOutputStream(lockFile).getChannel();
                            lock = ((FileChannel)lockChannel).lock();
                            if (!(file.exists() && Files.isSymbolicLink(path) && Files.readSymbolicLink(path).equals(targetPath) || !targetPath.isAbsolute() || targetPath.equals(path) || targetPath.toRealPath(new LinkOption[0]).equals(path))) {
                                if (logger.isDebugEnabled()) {
                                    logger.debug("Creating symbolic link " + path + " to " + targetPath);
                                }
                                try {
                                    file.getParentFile().mkdirs();
                                    Files.createSymbolicLink(path, targetPath, new FileAttribute[0]);
                                }
                                catch (FileAlreadyExistsException e) {
                                    file.delete();
                                    Files.createSymbolicLink(path, targetPath, new FileAttribute[0]);
                                }
                            }
                        }
                        if (lock == null) break block71;
                    }
                    catch (IOException | RuntimeException e) {
                        File targetPath;
                        block72: {
                            try {
                                canCreateSymbolicLink = false;
                                if (logger.isDebugEnabled()) {
                                    logger.debug("Failed to create symbolic link " + file + ": " + e);
                                }
                                targetPath = null;
                                if (lock == null) break block72;
                            }
                            catch (Throwable throwable) {
                                if (lock != null) {
                                    lock.release();
                                }
                                if (lockChannel == null) throw throwable;
                                lockChannel.close();
                                throw throwable;
                            }
                            lock.release();
                        }
                        if (lockChannel == null) return targetPath;
                        lockChannel.close();
                        return targetPath;
                    }
                    lock.release();
                }
                if (lockChannel == null) return file;
                lockChannel.close();
                return file;
            }
        }
        if (canCreateSymbolicLink && urlFile.exists() && reference) {
            runtime = Runtime.getRuntime();
            synchronized (runtime) {
                File file2;
                block73: {
                    try {
                        path = file.toPath();
                        Path urlPath = urlFile.toPath().normalize();
                        if (!(file.exists() && Files.isSymbolicLink(path) && Files.readSymbolicLink(path).equals(urlPath) || !urlPath.isAbsolute() || urlPath.equals(path) || urlPath.toRealPath(new LinkOption[0]).equals(path))) {
                            if (logger.isDebugEnabled()) {
                                logger.debug("Locking " + cacheDir + " to create symbolic link");
                            }
                            lockChannel = new FileOutputStream(lockFile).getChannel();
                            lock = ((FileChannel)lockChannel).lock();
                            if (!(file.exists() && Files.isSymbolicLink(path) && Files.readSymbolicLink(path).equals(urlPath) || !urlPath.isAbsolute() || urlPath.equals(path) || urlPath.toRealPath(new LinkOption[0]).equals(path))) {
                                if (logger.isDebugEnabled()) {
                                    logger.debug("Creating symbolic link " + path + " to " + urlPath);
                                }
                                try {
                                    file.getParentFile().mkdirs();
                                    Files.createSymbolicLink(path, urlPath, new FileAttribute[0]);
                                }
                                catch (FileAlreadyExistsException e) {
                                    file.delete();
                                    Files.createSymbolicLink(path, urlPath, new FileAttribute[0]);
                                }
                            }
                        }
                        file2 = file;
                        if (lock == null) break block73;
                    }
                    catch (IOException | RuntimeException e) {
                        try {
                            canCreateSymbolicLink = false;
                            if (logger.isDebugEnabled()) {
                                logger.debug("Could not create symbolic link " + file + ": " + e);
                            }
                        }
                        catch (Throwable throwable) {
                            throw throwable;
                        }
                        finally {
                            if (lock != null) {
                                lock.release();
                            }
                            if (lockChannel != null) {
                                lockChannel.close();
                            }
                        }
                    }
                    lock.release();
                }
                if (lockChannel == null) return file2;
                lockChannel.close();
                return file2;
            }
        }
        if (file.exists() && file.length() == size && file.lastModified() == timestamp && (!canCreateSymbolicLink || cacheSubdir.equals(Loader.getCanonicalFile(file).getParentFile()))) return file;
        runtime = Runtime.getRuntime();
        synchronized (runtime) {
            try {
                if (logger.isDebugEnabled()) {
                    logger.debug("Locking " + cacheDir + " before extracting");
                }
                lockChannel = new FileOutputStream(lockFile).getChannel();
                lock = ((FileChannel)lockChannel).lock();
                if (file.exists() && file.length() == size && file.lastModified() == timestamp && (!canCreateSymbolicLink || cacheSubdir.equals(Loader.getCanonicalFile(file).getParentFile()))) return file;
                if (logger.isDebugEnabled()) {
                    logger.debug("Extracting " + resourceURL);
                }
                file.delete();
                Loader.extractResource(resourceURL, file, null, null, true);
                file.setLastModified(timestamp);
            }
            finally {
                if (lock != null) {
                    lock.release();
                }
                if (lockChannel != null) {
                    lockChannel.close();
                }
            }
            return file;
        }
    }

    public static File extractResource(String name, File directory, String prefix, String suffix) throws IOException {
        Class cls = Loader.getCallerClass(2);
        return Loader.extractResource(cls, name, directory, prefix, suffix);
    }

    public static File extractResource(Class cls, String name, File directory, String prefix, String suffix) throws IOException {
        URL u = Loader.findResource(cls, name);
        return u != null ? Loader.extractResource(u, directory, prefix, suffix) : null;
    }

    public static File[] extractResources(String name, File directory, String prefix, String suffix) throws IOException {
        Class cls = Loader.getCallerClass(2);
        return Loader.extractResources(cls, name, directory, prefix, suffix);
    }

    public static File[] extractResources(Class cls, String name, File directory, String prefix, String suffix) throws IOException {
        URL[] urls = Loader.findResources(cls, name);
        File[] files = new File[urls.length];
        for (int i = 0; i < urls.length; ++i) {
            files[i] = Loader.extractResource(urls[i], directory, prefix, suffix);
        }
        return files;
    }

    public static File extractResource(URL resourceURL, File directoryOrFile, String prefix, String suffix) throws IOException {
        return Loader.extractResource(resourceURL, directoryOrFile, prefix, suffix, false);
    }

    public static File extractResource(URL resourceURL, File directoryOrFile, String prefix, String suffix, boolean cacheDirectory) throws IOException {
        URLConnection urlConnection;
        URLConnection uRLConnection = urlConnection = resourceURL != null ? resourceURL.openConnection() : null;
        if (urlConnection instanceof JarURLConnection) {
            JarFile jarFile = ((JarURLConnection)urlConnection).getJarFile();
            JarEntry jarEntry = ((JarURLConnection)urlConnection).getJarEntry();
            String jarFileName = jarFile.getName();
            String jarEntryName = jarEntry.getName();
            if (!jarEntryName.endsWith("/")) {
                jarEntryName = jarEntryName + "/";
            }
            if (jarEntry.isDirectory() || jarFile.getJarEntry(jarEntryName) != null) {
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    String entryName = entry.getName();
                    long entrySize = entry.getSize();
                    long entryTimestamp = entry.getTime();
                    if (!entryName.startsWith(jarEntryName)) continue;
                    File file = new File(directoryOrFile, entryName.substring(jarEntryName.length()));
                    if (entry.isDirectory()) {
                        file.mkdirs();
                    } else if (!cacheDirectory || !file.exists() || file.length() != entrySize || file.lastModified() != entryTimestamp || canCreateSymbolicLink && !file.equals(Loader.getCanonicalFile(file))) {
                        file.delete();
                        String s = resourceURL.toString();
                        URL u = new URL(s.substring(0, s.lastIndexOf("!/") + 2) + entryName);
                        file = Loader.extractResource(u, file, prefix, suffix);
                    }
                    file.setLastModified(entryTimestamp);
                }
                return directoryOrFile;
            }
        }
        InputStream is = urlConnection != null ? urlConnection.getInputStream() : null;
        OutputStream os = null;
        if (is == null) {
            return null;
        }
        File file = null;
        boolean fileExisted = false;
        try {
            int length;
            if (prefix == null && suffix == null) {
                File directory;
                if (directoryOrFile == null) {
                    directoryOrFile = new File(System.getProperty("java.io.tmpdir"));
                }
                if (directoryOrFile.isDirectory()) {
                    directory = directoryOrFile;
                    try {
                        file = new File(directoryOrFile, new File(new URI(resourceURL.toString().split("#")[0])).getName());
                    }
                    catch (IllegalArgumentException | URISyntaxException ex) {
                        file = new File(directoryOrFile, new File(resourceURL.getPath()).getName());
                    }
                } else {
                    directory = directoryOrFile.getParentFile();
                    file = directoryOrFile;
                }
                if (directory != null) {
                    directory.mkdirs();
                }
                fileExisted = file.exists();
            } else {
                file = File.createTempFile(prefix, suffix, directoryOrFile);
            }
            file.delete();
            os = new FileOutputStream(file);
            byte[] buffer = new byte[65536];
            while ((length = is.read(buffer)) != -1) {
                os.write(buffer, 0, length);
            }
        }
        catch (IOException e) {
            if (file != null && !fileExisted) {
                file.delete();
            }
            throw e;
        }
        finally {
            is.close();
            if (os != null) {
                os.close();
            }
        }
        return file;
    }

    public static URL findResource(Class cls, String name) throws IOException {
        URL[] url = Loader.findResources(cls, name, 1);
        return url.length > 0 ? url[0] : null;
    }

    public static URL[] findResources(Class cls, String name) throws IOException {
        return Loader.findResources(cls, name, -1);
    }

    public static URL[] findResources(Class cls, String name, int maxLength) throws IOException {
        if (maxLength == 0) {
            return new URL[0];
        }
        while (name.contains("//")) {
            name = name.replace("//", "/");
        }
        URL url = cls.getResource(name);
        if (url != null && maxLength == 1) {
            return new URL[]{url};
        }
        String path = "";
        if (!name.startsWith("/")) {
            String s = cls.getName().replace('.', '/');
            int n = s.lastIndexOf(47);
            if (n >= 0) {
                path = s.substring(0, n + 1);
            }
        } else {
            name = name.substring(1);
        }
        ClassLoader classLoader = cls.getClassLoader();
        if (classLoader == null) {
            classLoader = ClassLoader.getSystemClassLoader();
        }
        Enumeration<URL> urls = classLoader.getResources(path + name);
        ArrayList<URL> array = new ArrayList<URL>();
        if (url != null) {
            array.add(url);
        }
        while (url == null && !urls.hasMoreElements() && path.length() > 0) {
            int n = path.lastIndexOf(47, path.length() - 2);
            path = n >= 0 ? path.substring(0, n + 1) : "";
            urls = classLoader.getResources(path + name);
        }
        while (urls.hasMoreElements() && (maxLength < 0 || array.size() < maxLength)) {
            url = urls.nextElement();
            if (array.contains(url)) continue;
            array.add(url);
        }
        return array.toArray(new URL[array.size()]);
    }

    public static void deleteDirectory(File directory) throws IOException {
        Files.walkFileTree(directory.toPath(), (FileVisitor<? super Path>)new SimpleFileVisitor<Path>(){

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
                if (e != null) {
                    throw e;
                }
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static void clearCacheDir() throws IOException {
        logger.info("Deleting " + Loader.getCacheDir());
        Loader.deleteDirectory(Loader.getCacheDir());
    }

    public static File getCacheDir() throws IOException {
        if (cacheDir == null) {
            String[] dirNames;
            for (String dirName : dirNames = new String[]{System.getProperty("org.bytedeco.javacpp.cachedir"), System.getProperty("org.bytedeco.javacpp.cacheDir"), System.getProperty("user.home") + "/.javacpp/cache/", System.getProperty("java.io.tmpdir") + "/.javacpp-" + System.getProperty("user.name") + "/cache/"}) {
                if (dirName == null) continue;
                File f = new File(dirName);
                try {
                    if (!f.exists() && !f.mkdirs() || !f.canRead() || !f.canWrite() || !f.canExecute()) continue;
                    cacheDir = Loader.getCanonicalFile(f);
                    break;
                }
                catch (SecurityException e) {
                    logger.warn("Could not access " + f + ": " + e.getMessage());
                }
            }
        }
        if (cacheDir == null) {
            throw new IOException("Could not create the cache: Set the \"org.bytedeco.javacpp.cachedir\" system property.");
        }
        return cacheDir;
    }

    public static File getTempDir() {
        if (tempDir == null) {
            File tmpdir = new File(System.getProperty("java.io.tmpdir"));
            for (int i = 0; i < 1000; ++i) {
                File f = new File(tmpdir, "javacpp" + System.nanoTime());
                if (!f.mkdir()) continue;
                tempDir = f;
                tempDir.deleteOnExit();
                break;
            }
        }
        return tempDir;
    }

    public static synchronized Map<String, String> getLoadedLibraries() {
        return new HashMap<String, String>(loadedLibraries);
    }

    public static boolean isLoadLibraries() {
        String s = System.getProperty("org.bytedeco.javacpp.loadlibraries", "true").toLowerCase();
        return (s = System.getProperty("org.bytedeco.javacpp.loadLibraries", s).toLowerCase()).equals("true") || s.equals("t") || s.equals("");
    }

    public static boolean checkPlatform(Class<?> cls, java.util.Properties properties) {
        return Loader.checkPlatform(cls, properties, false);
    }

    public static boolean checkPlatform(Class<?> cls, java.util.Properties properties, boolean acceptAllExtensions) {
        boolean supported;
        Class enclosingClass = Loader.getEnclosingClass(cls);
        while (!cls.isAnnotationPresent(Properties.class) && !cls.isAnnotationPresent(Platform.class) && cls.getSuperclass() != null) {
            if (enclosingClass != null && cls.getSuperclass() == Object.class) {
                cls = enclosingClass;
                enclosingClass = null;
                continue;
            }
            cls = cls.getSuperclass();
        }
        Properties classProperties = cls.getAnnotation(Properties.class);
        Platform classPlatform = cls.getAnnotation(Platform.class);
        boolean bl = supported = classProperties == null && classPlatform == null;
        if (classProperties != null) {
            Class[] classes = classProperties.inherit();
            String[] defaultNames = classProperties.names();
            ArrayDeque<Class> queue = new ArrayDeque<Class>(Arrays.asList(classes));
            while (queue.size() > 0 && (defaultNames == null || defaultNames.length == 0)) {
                Class c = (Class)queue.removeFirst();
                Properties p = c.getAnnotation(Properties.class);
                if (p == null) continue;
                defaultNames = p.names();
                queue.addAll(Arrays.asList(p.inherit()));
            }
            Platform[] platforms = classProperties.value();
            if (platforms != null && platforms.length > 0) {
                for (Platform p : platforms) {
                    if (!Loader.checkPlatform(p, properties, acceptAllExtensions, defaultNames)) continue;
                    supported = true;
                    break;
                }
            } else if (classes != null && classes.length > 0) {
                for (Class c : classes) {
                    if (!Loader.checkPlatform(c, properties, acceptAllExtensions)) continue;
                    supported = true;
                    break;
                }
            }
        }
        if (classPlatform != null) {
            supported = Loader.checkPlatform(cls.getAnnotation(Platform.class), properties, acceptAllExtensions, new String[0]);
        }
        return supported;
    }

    public static boolean checkPlatform(Platform platform, java.util.Properties properties) {
        return Loader.checkPlatform(platform, properties, false, new String[0]);
    }

    public static boolean checkPlatform(Platform platform, java.util.Properties properties, boolean acceptAllExtensions, String ... defaultNames) {
        if (platform == null) {
            return true;
        }
        if (defaultNames == null) {
            defaultNames = new String[]{};
        }
        String platform2 = properties.getProperty("platform");
        String platformExtension = properties.getProperty("platform.extension");
        String[][] names = new String[][]{platform.value().length > 0 ? platform.value() : defaultNames, platform.not(), platform.pattern()};
        boolean[] matches = new boolean[]{false, false, false};
        block0: for (int i = 0; i < names.length; ++i) {
            for (String s : names[i]) {
                if ((i >= 2 || !platform2.startsWith(s)) && (s.length() <= 0 || !platform2.matches(s))) continue;
                matches[i] = true;
                continue block0;
            }
        }
        if (!(names[0].length != 0 && !matches[0] || names[1].length != 0 && matches[1] || names[2].length != 0 && !matches[2])) {
            boolean match = platform.extension().length == 0 || acceptAllExtensions;
            for (String s : platform.extension()) {
                if (platformExtension == null || platformExtension.length() <= 0 || !platformExtension.endsWith(s)) continue;
                match = true;
                break;
            }
            return match;
        }
        return false;
    }

    public static String[] load(Class ... classes) {
        return Loader.load(classes, true);
    }

    public static String[] load(Class[] classes, boolean logMessages) {
        String[] filenames = new String[classes.length];
        java.util.Properties properties = Loader.loadProperties();
        ClassProperties libProperties = null;
        for (int i = 0; i < classes.length; ++i) {
            Class c = classes[i];
            if (Loader.getEnclosingClass(c) != c || !(libProperties = Loader.loadProperties(c, properties, false)).isLoaded()) continue;
            libProperties = Loader.loadProperties(c, properties, true);
            if (!libProperties.isLoaded()) {
                if (!logMessages) continue;
                logger.warn("Could not load platform properties for " + c);
                continue;
            }
            try {
                if (logMessages) {
                    logger.info("Loading " + c);
                }
                filenames[i] = Loader.load(c);
                continue;
            }
            catch (NoClassDefFoundError | UnsatisfiedLinkError e) {
                if (!logMessages) continue;
                logger.warn("Could not load " + c + ": " + e);
            }
        }
        return filenames;
    }

    public static String load() {
        return Loader.load(Loader.getCallerClass(2), Loader.loadProperties(), pathsFirst);
    }

    public static String load(boolean pathsFirst) {
        Class cls = Loader.getCallerClass(2);
        return Loader.load(cls, Loader.loadProperties(), pathsFirst);
    }

    public static String load(Class cls) {
        return Loader.load(cls, Loader.loadProperties(), pathsFirst);
    }

    public static String load(Class cls, String executable) {
        return Loader.load(cls, Loader.loadProperties(), pathsFirst, executable);
    }

    public static String load(Class cls, java.util.Properties properties, boolean pathsFirst) {
        return Loader.load(cls, properties, pathsFirst, null);
    }

    public static String load(Class cls, java.util.Properties properties, boolean pathsFirst, String executable) {
        Class classToLoad = cls;
        if (!Loader.isLoadLibraries() || cls == null) {
            return null;
        }
        if (!Loader.checkPlatform(cls, properties, properties.getProperty("platform.extension") == null)) {
            throw new UnsatisfiedLinkError("Platform \"" + properties.getProperty("platform") + "\" not supported by " + cls);
        }
        ClassProperties p = Loader.loadProperties(cls = Loader.getEnclosingClass(cls), properties, true);
        List<String> targets = p.get("global");
        if (targets.isEmpty()) {
            if (p.getInheritedClasses() != null) {
                for (Class c : p.getInheritedClasses()) {
                    targets.add(c.getName());
                }
            }
            targets.add(cls.getName());
        }
        if (!targets.contains(classToLoad.getName())) {
            targets.add(classToLoad.getName());
        }
        for (String s : targets) {
            try {
                if (logger.isDebugEnabled()) {
                    logger.debug("Loading class " + s);
                }
                Class.forName(s, true, cls.getClassLoader());
            }
            catch (ClassNotFoundException ex) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Failed to load class " + s + ": " + ex);
                }
                NoClassDefFoundError e = new NoClassDefFoundError(ex.toString());
                e.initCause(ex);
                throw e;
            }
        }
        String cacheDir = null;
        try {
            cacheDir = cacheLibraries && findLibraries ? Loader.getCacheDir().toString() : null;
        }
        catch (IOException s) {
            // empty catch block
        }
        ArrayList<String> preloads = new ArrayList<String>();
        ArrayList<String> preloaded = new ArrayList<String>();
        ArrayList<String> preloadGlobals = new ArrayList<String>();
        preloads.addAll(p.get("platform.preload"));
        preloads.addAll(p.get("platform.link"));
        UnsatisfiedLinkError preloadError = null;
        for (String preload : preloads) {
            try {
                URL[] urls;
                boolean loadGlobally = false;
                if (preload.endsWith("!")) {
                    loadGlobally = true;
                    preload = preload.substring(0, preload.length() - 1);
                }
                URL[] oldUrls = urls = foundLibraries.get(preload);
                if (urls == null && findLibraries) {
                    urls = Loader.findLibrary(cls, p, preload, pathsFirst);
                    foundLibraries.put(preload, urls);
                }
                String filename = null;
                if (oldUrls == null || urls.length > 0) {
                    filename = Loader.loadLibrary(cls, urls, preload, preloaded.toArray(new String[preloaded.size()]));
                }
                if (filename != null && new File(filename).exists()) {
                    preloaded.add(filename);
                    if (loadGlobally) {
                        preloadGlobals.add(filename);
                    }
                }
                if (cacheDir == null || filename == null || !filename.startsWith(cacheDir)) continue;
                Loader.createLibraryLink(filename, p, preload, new String[0]);
            }
            catch (UnsatisfiedLinkError e) {
                preloadError = e;
            }
        }
        List<String> executables = p.get("platform.executable");
        LinkedHashMap<String, String> executablePaths = new LinkedHashMap<String, String>();
        if (executables.size() == 0 && executable != null) {
            throw new IllegalArgumentException("executable specified for class which does not have any executables");
        }
        if (executables.size() > 0) {
            String platform = p.getProperty("platform");
            String[] extensions = p.get("platform.extension").toArray(new String[0]);
            String prefix = p.getProperty("platform.executable.prefix", "");
            String suffix = p.getProperty("platform.executable.suffix", "");
            String filename = prefix + executables.get(0) + suffix;
            String libraryPath = p.getProperty("platform.library.path", "");
            try {
                if (libraryPath.length() > 0) {
                    block13: for (String preload : preloads) {
                        URL[] urls;
                        for (URL url : urls = Loader.findLibrary(cls, p, preload, true)) {
                            File f = Loader.cacheResource(url);
                            if (f == null) continue;
                            f.setExecutable(true);
                            continue block13;
                        }
                    }
                }
                block15: for (String e : executables) {
                    String[] split = e.split("#");
                    filename = prefix + split[0] + suffix;
                    String filename2 = split.length > 1 ? prefix + split[1] + suffix : null;
                    for (int i = extensions.length - 1; i >= -1; --i) {
                        Object f;
                        String extension;
                        String string = extension = i >= 0 ? extensions[i] : "";
                        String subdir = libraryPath.length() > 0 ? "/" + libraryPath : platform + (extension == null ? "" : extension);
                        URL u = Loader.findResource(cls, subdir + "/" + filename);
                        if (u == null) continue;
                        if (filename2 != null && !(u = new URL(u + "#" + filename2)).toString().contains("#")) {
                            f = URL.class.getDeclaredField("ref");
                            ((Field)f).setAccessible(true);
                            ((Field)f).set(u, filename2);
                        }
                        if ((f = Loader.cacheResource(u)) == null) continue;
                        ((File)f).setExecutable(true);
                        executablePaths.put(e, ((File)f).getAbsolutePath());
                        continue block15;
                    }
                }
            }
            catch (IOException | IllegalAccessException | NoSuchFieldException e) {
                logger.error("Could not extract executable " + filename + ": " + e);
            }
            return executable != null ? (String)executablePaths.get(executable) : (executablePaths.size() > 0 ? (String)executablePaths.values().iterator().next() : null);
        }
        int librarySuffix = -1;
        block17: while (true) {
            try {
                URL[] urls;
                String library = p.getProperty("platform.library");
                if (librarySuffix >= 0) {
                    library = library + "#" + library + librarySuffix;
                }
                URL[] oldUrls = urls = foundLibraries.get(library);
                if (urls == null && findLibraries) {
                    urls = Loader.findLibrary(cls, p, library, pathsFirst);
                    foundLibraries.put(library, urls);
                }
                String filename = null;
                if (oldUrls == null || urls.length > 0) {
                    filename = Loader.loadLibrary(cls, urls, library, preloaded.toArray(new String[preloaded.size()]));
                }
                if (cacheDir != null && filename != null && filename.startsWith(cacheDir)) {
                    Loader.createLibraryLink(filename, p, library, new String[0]);
                }
                for (String preloadGlobal : preloadGlobals) {
                    Loader.loadGlobal(preloadGlobal);
                }
                return filename;
            }
            catch (UnsatisfiedLinkError e) {
                Throwable t = e;
                while (t != null) {
                    if (t instanceof UnsatisfiedLinkError && t.getMessage().contains("already loaded in another classloader")) {
                        ++librarySuffix;
                        continue block17;
                    }
                    t = t.getCause() != t ? t.getCause() : null;
                }
                if (preloadError != null && e.getCause() == null) {
                    e.initCause(preloadError);
                }
                if (!Loader.checkPlatform(cls, properties, false)) {
                    return null;
                }
                throw e;
            }
            break;
        }
    }

    public static URL[] findLibrary(Class cls, ClassProperties properties, String libnameversion) {
        return Loader.findLibrary(cls, properties, libnameversion, pathsFirst);
    }

    public static URL[] findLibrary(Class cls, ClassProperties properties, String libnameversion, boolean pathsFirst) {
        String[] styles2;
        String[] styles;
        boolean nostyle = false;
        if (libnameversion.startsWith(":")) {
            nostyle = true;
            libnameversion = libnameversion.substring(1);
        } else if (libnameversion.contains(":")) {
            nostyle = true;
            libnameversion = libnameversion.substring(libnameversion.indexOf(":") + 1);
        }
        if (libnameversion.endsWith("!")) {
            libnameversion = libnameversion.substring(0, libnameversion.length() - 1);
        }
        if (libnameversion.trim().endsWith("#") && !libnameversion.trim().endsWith("##")) {
            return new URL[0];
        }
        String[] split = libnameversion.split("#");
        boolean reference = split.length > 1 && split[1].length() > 0;
        String[] s = split[0].split("@");
        String[] s2 = (reference ? split[1] : split[0]).split("@");
        String libname = s[0];
        String libname2 = s2[0];
        String version = s.length > 1 ? s[s.length - 1] : "";
        String version2 = s2.length > 1 ? s2[s2.length - 1] : "";
        String platform = properties.getProperty("platform");
        String[] extensions = properties.get("platform.extension").toArray(new String[0]);
        String prefix = properties.getProperty("platform.library.prefix", "");
        String suffix = properties.getProperty("platform.library.suffix", "");
        if (version.length() == 0 && version2.length() == 0) {
            styles = new String[]{prefix + libname + suffix};
            styles2 = new String[]{prefix + libname2 + suffix};
        } else {
            styles = new String[]{prefix + libname + suffix + version, prefix + libname + version + suffix, prefix + libname + suffix};
            styles2 = new String[]{prefix + libname2 + suffix + version2, prefix + libname2 + version2 + suffix, prefix + libname2 + suffix};
        }
        String[] suffixes = properties.get("platform.library.suffix").toArray(new String[0]);
        if (suffixes.length > 1) {
            styles = new String[3 * suffixes.length];
            styles2 = new String[3 * suffixes.length];
            for (int i = 0; i < suffixes.length; ++i) {
                styles[3 * i] = prefix + libname + suffixes[i] + version;
                styles[3 * i + 1] = prefix + libname + version + suffixes[i];
                styles[3 * i + 2] = prefix + libname + suffixes[i];
                styles2[3 * i] = prefix + libname2 + suffixes[i] + version2;
                styles2[3 * i + 1] = prefix + libname2 + version2 + suffixes[i];
                styles2[3 * i + 2] = prefix + libname2 + suffixes[i];
            }
        }
        if (nostyle) {
            styles = new String[]{libname};
            styles2 = new String[]{libname2};
        }
        ArrayList<String> paths = new ArrayList<String>();
        paths.addAll(properties.get("platform.linkpath"));
        paths.addAll(properties.get("platform.preloadpath"));
        List<String> resources = properties.get("platform.preloadresource");
        String libraryPath = properties.getProperty("platform.library.path", "");
        if (libraryPath.length() > 0 && pathsFirst) {
            resources.add(0, libraryPath);
        }
        resources.add(null);
        String libpath = System.getProperty("sun.boot.library.path", "");
        String libpath2 = System.getProperty("java.library.path", "");
        if (libpath.length() > 0 && libpath2.length() > 0) {
            libpath = libpath + File.pathSeparator + libpath2;
        } else if (libpath2.length() > 0) {
            libpath = libpath2;
        }
        if (libpath.length() > 0 && (pathsFirst || !Loader.isLoadLibraries() || reference)) {
            paths.addAll(Arrays.asList(libpath.split(File.pathSeparator)));
        }
        ArrayList<URL> urls = new ArrayList<URL>(styles.length * (1 + paths.size()));
        for (int i = 0; cls != null && i < styles.length; ++i) {
            for (int j = extensions.length - 1; j >= -1; --j) {
                String extension = j >= 0 ? extensions[j] : "";
                for (String resource : resources) {
                    if (resource != null && !resource.endsWith("/")) {
                        resource = resource + "/";
                    }
                    String subdir = libraryPath.length() > 0 && libraryPath.equals(resource) ? "/" + libraryPath : (resource == null ? "" : "/" + resource) + platform + (extension == null ? "" : extension);
                    try {
                        URL u = Loader.findResource(cls, subdir + "/" + styles[i]);
                        if (u == null) continue;
                        if (reference && !(u = new URL(u + "#" + styles2[i])).toString().contains("#")) {
                            Field f = URL.class.getDeclaredField("ref");
                            f.setAccessible(true);
                            f.set(u, styles2[i]);
                        }
                        if (urls.contains(u)) continue;
                        urls.add(u);
                    }
                    catch (IOException | IllegalAccessException | NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        int k = pathsFirst ? 0 : urls.size();
        for (int i = 0; paths.size() > 0 && i < styles.length; ++i) {
            for (String path : paths) {
                File file = new File(path, styles[i]);
                if (!file.exists()) continue;
                try {
                    URL u = file.toURI().toURL();
                    if (reference && !(u = new URL(u + "#" + styles2[i])).toString().contains("#")) {
                        Field f = URL.class.getDeclaredField("ref");
                        f.setAccessible(true);
                        f.set(u, styles2[i]);
                    }
                    if (urls.contains(u)) continue;
                    urls.add(k++, u);
                }
                catch (IOException | IllegalAccessException | NoSuchFieldException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        return urls.toArray(new URL[urls.size()]);
    }

    public static String loadLibrary(String libnameversion, String ... preloaded) {
        return Loader.loadLibrary(Loader.getCallerClass(2), libnameversion, preloaded);
    }

    public static String loadLibrary(Class<?> cls, String libnameversion, String ... preloaded) {
        try {
            return Loader.loadLibrary(Loader.findResources(cls, libnameversion), libnameversion, preloaded);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String loadLibrary(URL[] urls, String libnameversion, String ... preloaded) {
        return Loader.loadLibrary(null, urls, libnameversion, preloaded);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static synchronized String loadLibrary(Class<?> cls, URL[] urls, String libnameversion, String ... preloaded) {
        if (!Loader.isLoadLibraries()) {
            return null;
        }
        if (libnameversion.startsWith(":")) {
            libnameversion = libnameversion.substring(1);
        } else if (libnameversion.contains(":")) {
            libnameversion = libnameversion.substring(0, libnameversion.indexOf(":"));
        }
        boolean loadGlobally = false;
        if (libnameversion.endsWith("!")) {
            loadGlobally = true;
            libnameversion = libnameversion.substring(0, libnameversion.length() - 1);
        }
        String[] split = libnameversion.split("#");
        String libnameversion2 = split[0];
        if (split.length > 1 && split[1].length() > 0) {
            libnameversion2 = split[1];
        }
        String filename = loadedLibraries.get(libnameversion2);
        UnsatisfiedLinkError loadError = null;
        classStack.get().push(cls);
        try {
            for (URL url : urls != null ? urls : new URL[]{}) {
                File file;
                block54: {
                    URI uri = url.toURI();
                    file = null;
                    try {
                        file = new File(uri);
                    }
                    catch (Exception exc) {
                        block53: {
                            File f = cacheLibraries ? Loader.cacheResource(url, filename) : null;
                            try {
                                if (f != null) {
                                    file = f;
                                    break block53;
                                }
                                try {
                                    file = new File(new URI(uri.toString().split("#")[0]));
                                }
                                catch (IllegalArgumentException | URISyntaxException e) {
                                    if (uri.getPath() != null) {
                                        file = new File(uri.getPath());
                                    }
                                }
                            }
                            catch (Exception exc2) {
                                if (!logger.isDebugEnabled()) break block53;
                                logger.debug("Failed to access " + uri + ": " + exc2);
                            }
                        }
                        if (file == null || preloaded == null) break block54;
                        File dir = file.getParentFile();
                        for (String s : preloaded) {
                            File file2 = new File(s);
                            File dir2 = file2.getParentFile();
                            if (!canCreateSymbolicLink || dir2 == null || dir2.equals(dir)) continue;
                            File linkFile = new File(dir, file2.getName());
                            try {
                                Path linkPath = linkFile.toPath().normalize();
                                Path targetPath = file2.toPath().normalize();
                                if (linkFile.exists() && Files.isSymbolicLink(linkPath) && Files.readSymbolicLink(linkPath).equals(targetPath) || !targetPath.isAbsolute() || targetPath.equals(linkPath) || targetPath.toRealPath(new LinkOption[0]).equals(linkPath)) continue;
                                if (logger.isDebugEnabled()) {
                                    logger.debug("Creating symbolic link " + linkPath + " to " + targetPath);
                                }
                                linkFile.delete();
                                Files.createSymbolicLink(linkPath, targetPath, new FileAttribute[0]);
                            }
                            catch (IOException | RuntimeException e) {
                                canCreateSymbolicLink = false;
                                if (!logger.isDebugEnabled()) continue;
                                logger.debug("Failed to create symbolic link " + linkFile + " to " + file2 + ": " + e);
                            }
                        }
                    }
                }
                if (filename != null) {
                    String exc = filename;
                    return exc;
                }
                if (file == null || !file.exists()) continue;
                String filename2 = file.getAbsolutePath();
                try {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Loading " + filename2);
                    }
                    loadedLibraries.put(libnameversion2, filename2);
                    boolean loadedByLoad0 = false;
                    if (cls != null && Loader.class.getClassLoader() != cls.getClassLoader()) {
                        try {
                            Method load0 = Runtime.class.getDeclaredMethod("load0", Class.class, String.class);
                            load0.setAccessible(true);
                            load0.invoke((Object)Runtime.getRuntime(), cls, filename2);
                            loadedByLoad0 = true;
                        }
                        catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException cnfe) {
                            logger.warn("Unable to load the library " + libnameversion2 + " within the ClassLoader scope of " + cls.getName());
                        }
                        catch (InvocationTargetException ite) {
                            Throwable target = ite.getTargetException();
                            if (target instanceof UnsatisfiedLinkError) {
                                throw (UnsatisfiedLinkError)target;
                            }
                            logger.warn("Unable to load the library " + libnameversion2 + " within the ClassLoader scope of " + cls.getName() + " because: " + target.getMessage());
                        }
                    }
                    if (!loadedByLoad0) {
                        System.load(filename2);
                    }
                    if (loadGlobally) {
                        Loader.loadGlobal(filename2);
                    }
                    String string = filename2;
                    return string;
                }
                catch (UnsatisfiedLinkError e) {
                    loadError = e;
                    loadedLibraries.remove(libnameversion2);
                    if (!logger.isDebugEnabled()) continue;
                    logger.debug("Failed to load " + filename2 + ": " + e);
                }
            }
            if (filename != null) {
                String string = filename;
                return string;
            }
            if (!libnameversion.trim().endsWith("#")) {
                void var9_13;
                String string;
                if (loadError == null) {
                    loadError = new UnsatisfiedLinkError("Could not find " + libnameversion + " in class, module, and library paths.");
                }
                if ((string = libnameversion.split("#")[0].split("@")[0]).endsWith("!")) {
                    String string2 = string.substring(0, string.length() - 1);
                }
                if (logger.isDebugEnabled()) {
                    logger.debug("Loading library " + (String)var9_13);
                }
                loadedLibraries.put(libnameversion2, (String)var9_13);
                boolean loadedByLoadLibrary0 = false;
                if (cls != null && Loader.class.getClassLoader() != cls.getClassLoader()) {
                    try {
                        Method load0 = Runtime.class.getDeclaredMethod("loadLibrary0", Class.class, String.class);
                        load0.setAccessible(true);
                        load0.invoke((Object)Runtime.getRuntime(), cls, var9_13);
                        loadedByLoadLibrary0 = true;
                    }
                    catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException cnfe) {
                        logger.warn("Unable to load the library " + (String)var9_13 + " within the ClassLoader scope of " + cls.getName());
                    }
                    catch (InvocationTargetException ite) {
                        Throwable target = ite.getTargetException();
                        if (target instanceof UnsatisfiedLinkError) {
                            throw (UnsatisfiedLinkError)target;
                        }
                        logger.warn("Unable to load the library " + (String)var9_13 + " within the ClassLoader scope of " + cls.getName() + " because: " + target.getMessage());
                    }
                }
                if (!loadedByLoadLibrary0) {
                    System.loadLibrary((String)var9_13);
                }
                void var11_23 = var9_13;
                return var11_23;
            }
            String string = null;
            return string;
        }
        catch (UnsatisfiedLinkError unsatisfiedLinkError) {
            loadedLibraries.remove(libnameversion2);
            if (loadError != null && unsatisfiedLinkError.getCause() == null) {
                unsatisfiedLinkError.initCause(loadError);
            }
            if (!logger.isDebugEnabled()) throw unsatisfiedLinkError;
            logger.debug("Failed to load for " + libnameversion + ": " + unsatisfiedLinkError);
            throw unsatisfiedLinkError;
        }
        catch (IOException | URISyntaxException exception) {
            loadedLibraries.remove(libnameversion2);
            if (loadError != null && exception.getCause() == null) {
                exception.initCause(loadError);
            }
            UnsatisfiedLinkError e = new UnsatisfiedLinkError(exception.toString());
            e.initCause(exception);
            if (!logger.isDebugEnabled()) throw e;
            logger.debug("Failed to extract for " + libnameversion + ": " + e);
            throw e;
        }
        finally {
            classStack.get().pop();
        }
    }

    public static String createLibraryLink(String filename, ClassProperties properties, String libnameversion, String ... paths) {
        String version;
        String[] stringArray;
        if (libnameversion != null && libnameversion.startsWith(":")) {
            libnameversion = libnameversion.substring(1);
        } else if (libnameversion != null && libnameversion.contains(":")) {
            libnameversion = libnameversion.substring(0, libnameversion.indexOf(":"));
        }
        if (libnameversion != null && libnameversion.endsWith("!")) {
            libnameversion = libnameversion.substring(0, libnameversion.length() - 1);
        }
        File file = new File(filename);
        String parent = file.getParent();
        String name = file.getName();
        String link = null;
        if (libnameversion != null) {
            stringArray = libnameversion.split("#");
        } else {
            String[] stringArray2 = new String[1];
            stringArray = stringArray2;
            stringArray2[0] = "";
        }
        String[] split = stringArray;
        String[] s = (split.length > 1 && split[1].length() > 0 ? split[1] : split[0]).split("@");
        String libname = s[0];
        String string = version = s.length > 1 ? s[s.length - 1] : "";
        if (!name.contains(libname)) {
            return filename;
        }
        for (String suffix : properties.get("platform.library.suffix")) {
            int n = name.lastIndexOf(suffix);
            int n2 = version.length() != 0 ? name.lastIndexOf(version) : name.indexOf(".");
            int n3 = name.lastIndexOf(".");
            if (n2 < n && n < n3) {
                link = name.substring(0, n) + suffix;
                break;
            }
            if (n <= 0 || n2 <= 0) continue;
            link = name.substring(0, n < n2 ? n : n2) + suffix;
            break;
        }
        if (link == null) {
            for (String suffix : properties.get("platform.library.suffix")) {
                if (!name.endsWith(suffix)) continue;
                link = name;
                break;
            }
        }
        if (canCreateSymbolicLink && link != null && link.length() > 0) {
            File linkFile = new File(parent, link);
            try {
                Path linkPath = linkFile.toPath();
                Path targetPath = Paths.get(name, new String[0]);
                if (!(linkFile.exists() && Files.isSymbolicLink(linkPath) && Files.readSymbolicLink(linkPath).equals(targetPath) || targetPath.isAbsolute() || targetPath.equals(linkPath.getFileName()))) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Creating symbolic link " + linkPath);
                    }
                    linkFile.delete();
                    Files.createSymbolicLink(linkPath, targetPath, new FileAttribute[0]);
                }
                filename = linkFile.toString();
                for (String parent2 : paths) {
                    if (parent2 == null) continue;
                    for (String link2 : new String[]{link, name}) {
                        File linkFile2 = new File(parent2, link2);
                        Path linkPath2 = linkFile2.toPath();
                        Path relativeTarget = Paths.get(parent2, new String[0]).relativize(Paths.get(parent, new String[0])).resolve(name);
                        if (linkFile2.exists() && Files.isSymbolicLink(linkPath2) && Files.readSymbolicLink(linkPath2).equals(relativeTarget) || relativeTarget.isAbsolute() || relativeTarget.equals(linkPath2.getFileName())) continue;
                        if (logger.isDebugEnabled()) {
                            logger.debug("Creating symbolic link " + linkPath2);
                        }
                        linkFile2.delete();
                        Files.createSymbolicLink(linkPath2, relativeTarget, new FileAttribute[0]);
                    }
                }
            }
            catch (IOException | RuntimeException e) {
                canCreateSymbolicLink = false;
                if (logger.isDebugEnabled()) {
                    logger.debug("Failed to create symbolic link " + linkFile + ": " + e);
                }
                return null;
            }
        }
        return filename;
    }

    static Class putMemberOffset(String typeName, String member, int offset) throws ClassNotFoundException {
        try {
            Class<?> context = classStack.get().peek();
            Class<?> c = Class.forName(typeName.replace('/', '.'), false, context != null ? context.getClassLoader() : Loader.class.getClassLoader());
            if (member != null) {
                Loader.putMemberOffset(c.asSubclass(Pointer.class), member, offset);
            }
            return c;
        }
        catch (ClassNotFoundException e) {
            logger.warn("Loader.putMemberOffset(): " + e);
            return null;
        }
    }

    static synchronized void putMemberOffset(Class<? extends Pointer> type, String member, int offset) {
        HashMap<String, Integer> offsets = memberOffsets.get(type);
        if (offsets == null) {
            offsets = new HashMap();
            memberOffsets.put(type, offsets);
        }
        offsets.put(member, offset);
    }

    public static int offsetof(Class<? extends Pointer> type, String member) {
        HashMap<String, Integer> offsets = memberOffsets.get(type);
        while (offsets == null && type.getSuperclass() != null) {
            type = type.getSuperclass().asSubclass(Pointer.class);
            offsets = memberOffsets.get(type);
        }
        return offsets.get(member);
    }

    public static int sizeof(Class<? extends Pointer> type) {
        return Loader.offsetof(type, "sizeof");
    }

    public static int totalProcessors() {
        return Helper.totalProcessors();
    }

    public static int totalCores() {
        return Helper.totalCores();
    }

    public static int totalChips() {
        return Helper.totalChips();
    }

    public static Pointer addressof(String symbol) {
        return Helper.addressof(symbol);
    }

    public static void loadGlobal(String filename) {
        Helper.loadGlobal(filename);
    }

    public static Pointer getJavaVM() {
        return Helper.getJavaVM();
    }

    public static Pointer newGlobalRef(Object object) {
        return Helper.newGlobalRef(object);
    }

    public static Object accessGlobalRef(Pointer globalRef) {
        return Helper.accessGlobalRef(globalRef);
    }

    public static void deleteGlobalRef(Pointer globalRef) {
        Helper.deleteGlobalRef(globalRef);
    }

    static {
        String s = System.getProperty("org.bytedeco.javacpp.pathsfirst", "false").toLowerCase();
        s = System.getProperty("org.bytedeco.javacpp.pathsFirst", s).toLowerCase();
        pathsFirst = s.equals("true") || s.equals("t") || s.equals("");
        s = System.getProperty("org.bytedeco.javacpp.cancreatesymboliclink", WINDOWS ? "false" : "true").toLowerCase();
        s = System.getProperty("org.bytedeco.javacpp.canCreateSymbolicLink", s).toLowerCase();
        canCreateSymbolicLink = s.equals("true") || s.equals("t") || s.equals("");
        s = System.getProperty("org.bytedeco.javacpp.cachelibraries", "true").toLowerCase();
        s = System.getProperty("org.bytedeco.javacpp.cacheLibraries", s).toLowerCase();
        cacheLibraries = s.equals("true") || s.equals("t") || s.equals("");
        s = System.getProperty("org.bytedeco.javacpp.findlibraries", "true").toLowerCase();
        s = System.getProperty("org.bytedeco.javacpp.findLibraries", s).toLowerCase();
        findLibraries = s.equals("true") || s.equals("t") || s.equals("");
        memberOffsets = new WeakHashMap();
    }

    public static class Helper {
        @Name(value={"JavaCPP_totalProcessors"})
        public static native int totalProcessors();

        @Name(value={"JavaCPP_totalCores"})
        public static native int totalCores();

        @Name(value={"JavaCPP_totalChips"})
        public static native int totalChips();

        @Name(value={"JavaCPP_addressof"})
        public static native Pointer addressof(String var0);

        @Name(value={"JavaCPP_loadGlobal"})
        @Raw(withEnv=true)
        public static native void loadGlobal(String var0);

        @Name(value={"JavaCPP_getJavaVM"})
        @Cast(value={"JavaVM*"})
        public static native Pointer getJavaVM();

        @Name(value={"JavaCPP_newGlobalRef"})
        @Cast(value={"jobject"})
        public static native Pointer newGlobalRef(@Raw(withEnv=true) Object var0);

        @Name(value={"JavaCPP_accessGlobalRef"})
        @Raw(withEnv=true)
        public static native Object accessGlobalRef(@Cast(value={"jobject"}) Pointer var0);

        @Name(value={"JavaCPP_deleteGlobalRef"})
        @Raw(withEnv=true)
        public static native void deleteGlobalRef(@Cast(value={"jobject"}) Pointer var0);

        static {
            block2: {
                try {
                    Loader.load();
                }
                catch (Throwable t) {
                    if (!logger.isDebugEnabled()) break block2;
                    logger.debug("Could not load Loader.Helper: " + t);
                }
            }
        }
    }

    public static class Detector {
        public static String getPlatform() {
            String jvmName = System.getProperty("java.vm.name", "").toLowerCase();
            String osName = System.getProperty("os.name", "").toLowerCase();
            String osArch = System.getProperty("os.arch", "").toLowerCase();
            String abiType = System.getProperty("sun.arch.abi", "").toLowerCase();
            String libPath = System.getProperty("sun.boot.library.path", "").toLowerCase();
            if (jvmName.startsWith("dalvik") && osName.startsWith("linux")) {
                osName = "android";
            } else if (jvmName.startsWith("robovm") && osName.startsWith("darwin")) {
                osName = "ios";
                osArch = "arm";
            } else if (osName.startsWith("mac os x") || osName.startsWith("darwin")) {
                osName = "macosx";
            } else {
                int spaceIndex = osName.indexOf(32);
                if (spaceIndex > 0) {
                    osName = osName.substring(0, spaceIndex);
                }
            }
            if (osArch.equals("i386") || osArch.equals("i486") || osArch.equals("i586") || osArch.equals("i686")) {
                osArch = "x86";
            } else if (osArch.equals("amd64") || osArch.equals("x86-64") || osArch.equals("x64")) {
                osArch = "x86_64";
            } else if (osArch.startsWith("aarch64") || osArch.startsWith("armv8") || osArch.startsWith("arm64")) {
                osArch = "arm64";
            } else if (osArch.startsWith("arm") && (abiType.equals("gnueabihf") || libPath.contains("jdk-armhf"))) {
                osArch = "armhf";
            } else if (osArch.startsWith("arm")) {
                osArch = "arm";
            }
            return System.getProperty("org.bytedeco.javacpp.platform", osName + "-" + osArch);
        }
    }
}

