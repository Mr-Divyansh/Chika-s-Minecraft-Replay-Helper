/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.maven.artifact.Artifact
 *  org.apache.maven.plugin.AbstractMojo
 *  org.apache.maven.plugin.MojoExecutionException
 *  org.apache.maven.plugin.descriptor.PluginDescriptor
 *  org.apache.maven.plugin.logging.Log
 *  org.apache.maven.plugins.annotations.LifecyclePhase
 *  org.apache.maven.plugins.annotations.Mojo
 *  org.apache.maven.plugins.annotations.Parameter
 *  org.apache.maven.plugins.annotations.ResolutionScope
 *  org.apache.maven.project.MavenProject
 */
package org.bytedeco.javacpp.tools;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.tools.BuildMojo;
import org.bytedeco.javacpp.tools.ClassFilter;
import org.bytedeco.javacpp.tools.ClassScanner;
import org.bytedeco.javacpp.tools.Generator;
import org.bytedeco.javacpp.tools.Logger;
import org.bytedeco.javacpp.tools.UserClassLoader;

@Mojo(name="cache", defaultPhase=LifecyclePhase.NONE, threadSafe=true, requiresDependencyResolution=ResolutionScope.COMPILE_PLUS_RUNTIME)
public class CacheMojo
extends AbstractMojo {
    @Parameter(property="javacpp.classOrPackageName")
    String classOrPackageName = null;
    @Parameter(property="javacpp.classOrPackageNames")
    String[] classOrPackageNames = null;
    @Parameter(defaultValue="${project}", required=true, readonly=true)
    MavenProject project;
    @Parameter(defaultValue="${plugin}", required=true, readonly=true)
    PluginDescriptor plugin;

    String join(String separator, Iterable<String> strings) {
        String string = "";
        for (String s : strings) {
            string = string + (string.length() > 0 ? separator : "") + s;
        }
        return string;
    }

    public void execute() throws MojoExecutionException {
        final Log log = this.getLog();
        Logger logger = new Logger(){

            @Override
            public void debug(String s) {
                log.debug((CharSequence)s);
            }

            @Override
            public void info(String s) {
                log.info((CharSequence)s);
            }

            @Override
            public void warn(String s) {
                log.warn((CharSequence)s);
            }

            @Override
            public void error(String s) {
                log.error((CharSequence)s);
            }
        };
        ClassFilter classFilter = new ClassFilter(){
            byte[] s = Generator.signature(Properties.class).getBytes(StandardCharsets.UTF_8);

            @Override
            public boolean keep(String filename, byte[] data) {
                boolean found = false;
                block0: for (int i = 0; i < data.length; ++i) {
                    for (int j = 0; i + j < data.length && j < this.s.length; ++j) {
                        if (data[i + j] != this.s[j]) continue block0;
                    }
                    found = true;
                    break;
                }
                return found;
            }
        };
        try {
            Set artifacts = this.project.getArtifacts();
            ArrayList<Class> classes = new ArrayList<Class>();
            UserClassLoader classLoader = new UserClassLoader();
            ClassScanner classScanner = new ClassScanner(logger, classes, classLoader, classFilter);
            classLoader.addPaths(this.plugin.getPluginArtifact().getFile().getAbsolutePath());
            for (Artifact a : artifacts) {
                classLoader.addPaths(a.getFile().getAbsolutePath());
            }
            this.classOrPackageNames = BuildMojo.merge(this.classOrPackageNames, this.classOrPackageName);
            if (this.classOrPackageNames == null || this.classOrPackageNames.length == 0) {
                classScanner.addPackage(null, true);
            } else {
                for (String s : this.classOrPackageNames) {
                    classScanner.addClassOrPackage(s);
                }
            }
            LinkedHashSet<String> packages = new LinkedHashSet<String>();
            for (Class c : classes) {
                try {
                    Method cachePackage = c.getMethod("cachePackage", new Class[0]);
                    logger.info("Caching " + c);
                    File f = (File)cachePackage.invoke((Object)c, new Object[0]);
                    if (f == null) continue;
                    packages.add(Loader.getCanonicalPath(f));
                }
                catch (NoSuchMethodException cachePackage) {}
            }
            Class<?> loader = Class.forName("org.bytedeco.javacpp.Loader", true, classLoader);
            Method load = loader.getMethod("load", Class[].class);
            String[] filenames = (String[])load.invoke(loader, new Object[]{classes.toArray(new Class[classes.size()])});
            LinkedHashSet<String> paths = new LinkedHashSet<String>();
            for (String filename : filenames) {
                if (filename == null) continue;
                paths.add(new File(filename).getParent());
            }
            System.out.println("PATH=" + this.join(File.pathSeparator, paths));
            System.out.println("PACKAGEPATH=" + this.join(File.pathSeparator, packages));
        }
        catch (IOException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoClassDefFoundError | NoSuchMethodException | InvocationTargetException e) {
            log.error((CharSequence)("Failed to execute JavaCPP Loader: " + e.getMessage()));
            throw new MojoExecutionException("Failed to execute JavaCPP Loader", e);
        }
    }
}

