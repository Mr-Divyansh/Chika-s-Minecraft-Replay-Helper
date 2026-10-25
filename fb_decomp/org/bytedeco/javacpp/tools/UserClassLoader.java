/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.tools;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class UserClassLoader
extends URLClassLoader {
    private List<String> paths = new ArrayList<String>();

    public UserClassLoader() {
        super(new URL[0]);
    }

    public UserClassLoader(ClassLoader parent) {
        super(new URL[0], parent);
    }

    public void addPaths(String ... paths) {
        if (paths == null) {
            return;
        }
        for (String path : paths) {
            File f = new File(path);
            if (f.getName().equals("*")) {
                File[] files = f.getParentFile().listFiles();
                String[] jars = new String[files.length];
                int n = 0;
                for (File file : files) {
                    String p = file.getPath();
                    if (!p.endsWith(".jar") && !p.endsWith(".JAR")) continue;
                    jars[n++] = p;
                }
                this.addPaths(Arrays.copyOf(jars, n));
                continue;
            }
            if (this.paths.contains(path) || !f.exists()) continue;
            this.paths.add(path);
            try {
                this.addURL(f.toURI().toURL());
            }
            catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String[] getPaths() {
        if (this.paths.isEmpty()) {
            this.addPaths(System.getProperty("user.dir"));
        }
        return this.paths.toArray(new String[this.paths.size()]);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (this.paths.isEmpty()) {
            this.addPaths(System.getProperty("user.dir"));
        }
        return super.findClass(name);
    }
}

