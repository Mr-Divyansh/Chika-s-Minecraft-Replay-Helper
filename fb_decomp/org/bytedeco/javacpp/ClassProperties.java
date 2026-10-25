/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bytedeco.javacpp.LoadEnabled;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Platform;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.tools.Logger;

public class ClassProperties
extends HashMap<String, List<String>> {
    private static final Logger logger = Logger.create(ClassProperties.class);
    String[] defaultNames = new String[0];
    String platform;
    String platformExtension;
    String platformRoot;
    String pathSeparator;
    List<Class> inheritedClasses = null;
    List<Class> effectiveClasses = null;
    boolean loaded = false;

    public ClassProperties() {
    }

    public ClassProperties(java.util.Properties properties) {
        this.platform = properties.getProperty("platform");
        this.platformExtension = properties.getProperty("platform.extension");
        this.platformRoot = properties.getProperty("platform.root");
        this.pathSeparator = properties.getProperty("platform.path.separator");
        if (this.platformRoot == null || this.platformRoot.length() == 0) {
            this.platformRoot = ".";
        }
        if (!this.platformRoot.endsWith(File.separator)) {
            this.platformRoot = this.platformRoot + File.separator;
        }
        for (Map.Entry<Object, Object> e : properties.entrySet()) {
            String k = (String)e.getKey();
            String v = (String)e.getValue();
            if (v == null || v.length() == 0) continue;
            if (k.equals("platform.includepath") || k.equals("platform.includeresource") || k.equals("platform.include") || k.equals("platform.linkpath") || k.equals("platform.linkresource") || k.equals("platform.link") || k.equals("platform.preloadpath") || k.equals("platform.preloadresource") || k.equals("platform.preload") || k.equals("platform.resourcepath") || k.equals("platform.resource") || k.equals("platform.frameworkpath") || k.equals("platform.framework") || k.equals("platform.executablepath") || k.equals("platform.executable") || k.equals("platform.compiler.*") || k.equals("platform.library.suffix") || k.equals("platform.extension")) {
                this.addAll(k, v.split(this.pathSeparator));
                continue;
            }
            this.setProperty(k, v);
        }
    }

    public List<String> get(String key) {
        ArrayList list = (ArrayList)super.get(key);
        if (list == null) {
            list = new ArrayList();
            this.put(key, list);
        }
        return list;
    }

    public void addAll(String key, String ... values) {
        if (values != null) {
            this.addAll(key, Arrays.asList(values));
        }
    }

    public void addAll(String key, Collection<String> values) {
        if (values != null) {
            String root = null;
            if (key.equals("platform.compiler") || key.equals("platform.sysroot") || key.equals("platform.toolchain") || key.equals("platform.includepath") || key.equals("platform.linkpath")) {
                root = this.platformRoot;
            }
            List<String> values2 = this.get(key);
            for (String value : values) {
                if (value == null) continue;
                if (root != null && !new File(value).isAbsolute() && new File(root + value).exists()) {
                    value = root + value;
                }
                if (values2.contains(value)) continue;
                values2.add(value);
            }
        }
    }

    public String getProperty(String key) {
        return this.getProperty(key, null);
    }

    public String getProperty(String key, String defaultValue) {
        List<String> values = this.get(key);
        return values.isEmpty() ? defaultValue : values.get(0);
    }

    public String setProperty(String key, String value) {
        List<String> values = this.get(key);
        String oldValue = values.isEmpty() ? null : values.get(0);
        values.clear();
        this.addAll(key, value);
        return oldValue;
    }

    public void load(Class cls, boolean inherit) {
        int i;
        Class c = Loader.getEnclosingClass(cls);
        ArrayList<Class> classList = new ArrayList<Class>();
        classList.add(0, c);
        while (!c.isAnnotationPresent(Properties.class) && !c.isAnnotationPresent(Platform.class) && c.getSuperclass() != null && c.getSuperclass() != Object.class && c.getSuperclass() != Pointer.class) {
            c = c.getSuperclass();
            classList.add(0, c);
        }
        if (this.effectiveClasses == null) {
            this.effectiveClasses = classList;
        }
        Properties classProperties = c.getAnnotation(Properties.class);
        Platform classPlatform = c.getAnnotation(Platform.class);
        Platform[] platforms = null;
        String ourTarget = null;
        if (classProperties != null) {
            String[] names;
            String helper;
            Class[] classes = classProperties.inherit();
            if (inherit && classes != null) {
                if (this.inheritedClasses == null) {
                    this.inheritedClasses = new ArrayList<Class>();
                }
                for (Class c2 : classes) {
                    this.load(c2, inherit);
                    if (this.inheritedClasses.contains(c2)) continue;
                    this.inheritedClasses.add(c2);
                }
            }
            String target = classProperties.target();
            String global = classProperties.global();
            if (global.length() == 0) {
                global = target;
            } else if (target.length() == 0) {
                target = global;
            } else if (target.length() > 0 && !global.contains(".")) {
                global = target + "." + global;
            }
            ourTarget = global;
            if (target.length() > 0) {
                this.addAll("target", target);
            }
            if (global.length() > 0) {
                this.addAll("global", global);
            }
            if ((helper = classProperties.helper()).length() > 0 && !helper.contains(".")) {
                helper = target + "." + helper;
            }
            if (helper.length() > 0) {
                this.addAll("helper", helper);
            }
            if ((names = classProperties.names()).length > 0) {
                this.defaultNames = names;
            }
            platforms = classProperties.value();
        }
        if (classPlatform != null) {
            if (platforms == null) {
                platforms = new Platform[]{classPlatform};
            } else {
                platforms = (Platform[])Arrays.copyOf(platforms, platforms.length + 1);
                platforms[platforms.length - 1] = classPlatform;
            }
        }
        boolean hasPlatformProperties = platforms != null && platforms.length > (classProperties != null && classPlatform != null ? 1 : 0);
        String[] pragma = new String[]{};
        String[] define = new String[]{};
        String[] exclude = new String[]{};
        String[] include = new String[]{};
        String[] cinclude = new String[]{};
        String[] includepath = new String[]{};
        String[] includeresource = new String[]{};
        String[] compiler = new String[]{};
        String[] linkpath = new String[]{};
        String[] linkresource = new String[]{};
        String[] link = new String[]{};
        String[] frameworkpath = new String[]{};
        String[] framework = new String[]{};
        String[] preloadpath = new String[]{};
        String[] preloadresource = new String[]{};
        String[] preload = new String[]{};
        String[] resourcepath = new String[]{};
        String[] resource = new String[]{};
        String[] extension = new String[]{};
        String[] executablepath = new String[]{};
        String[] executable = new String[]{};
        String library = "jni" + c.getSimpleName();
        if (hasPlatformProperties) {
            if (ourTarget != null && ourTarget.length() > 0) {
                library = "jni" + ourTarget.substring(ourTarget.lastIndexOf(46) + 1);
            }
        } else {
            Platform[] targets = this.get("global");
            if (targets != null && targets.size() > 0) {
                String target = (String)targets.get(targets.size() - 1);
                library = "jni" + target.substring(target.lastIndexOf(46) + 1);
            }
        }
        for (Platform p : platforms != null ? platforms : new Platform[]{}) {
            String[][] names = new String[][]{p.value().length > 0 ? p.value() : this.defaultNames, p.not(), p.pattern()};
            boolean[] matches = new boolean[]{false, false, false};
            block5: for (int i2 = 0; i2 < names.length; ++i2) {
                for (String s : names[i2]) {
                    if ((i2 >= 2 || !this.platform.startsWith(s)) && (s.length() <= 0 || !this.platform.matches(s))) continue;
                    matches[i2] = true;
                    continue block5;
                }
            }
            if (names[0].length != 0 && !matches[0] || names[1].length != 0 && matches[1] || names[2].length != 0 && !matches[2]) continue;
            boolean match = p.extension().length == 0 || Loader.isLoadLibraries() && this.platformExtension == null;
            for (String s : p.extension()) {
                if (this.platformExtension == null || this.platformExtension.length() <= 0 || !this.platformExtension.endsWith(s)) continue;
                match = true;
                break;
            }
            if (!match) continue;
            if (p.pragma().length > 0) {
                pragma = p.pragma();
            }
            if (p.define().length > 0) {
                define = p.define();
            }
            if (p.exclude().length > 0) {
                exclude = p.exclude();
            }
            if (p.include().length > 0) {
                include = p.include();
            }
            if (p.cinclude().length > 0) {
                cinclude = p.cinclude();
            }
            if (p.includepath().length > 0) {
                includepath = p.includepath();
            }
            if (p.includeresource().length > 0) {
                includeresource = p.includeresource();
            }
            if (p.compiler().length > 0) {
                compiler = p.compiler();
            }
            if (p.linkpath().length > 0) {
                linkpath = p.linkpath();
            }
            if (p.linkresource().length > 0) {
                linkresource = p.linkresource();
            }
            if (p.link().length > 0) {
                link = p.link();
            }
            if (p.frameworkpath().length > 0) {
                frameworkpath = p.frameworkpath();
            }
            if (p.framework().length > 0) {
                framework = p.framework();
            }
            if (p.preloadresource().length > 0) {
                preloadresource = p.preloadresource();
            }
            if (p.preloadpath().length > 0) {
                preloadpath = p.preloadpath();
            }
            if (p.preload().length > 0) {
                preload = p.preload();
            }
            if (p.resourcepath().length > 0) {
                resourcepath = p.resourcepath();
            }
            if (p.resource().length > 0) {
                resource = p.resource();
            }
            if (p.extension().length > 0) {
                extension = p.extension();
            }
            if (p.executablepath().length > 0) {
                executablepath = p.executablepath();
            }
            if (p.executable().length > 0) {
                executable = p.executable();
            }
            if (p.library().length() <= 0) continue;
            library = p.library();
        }
        for (i = 0; i < includeresource.length; ++i) {
            String name = includeresource[i];
            if (name.startsWith("/")) continue;
            String s = cls.getName().replace('.', '/');
            int n = s.lastIndexOf(47);
            if (n >= 0) {
                name = s.substring(0, n + 1) + name;
            }
            includeresource[i] = "/" + name;
        }
        for (i = 0; i < linkresource.length; ++i) {
            String name = linkresource[i];
            if (name.startsWith("/")) continue;
            String s = cls.getName().replace('.', '/');
            int n = s.lastIndexOf(47);
            if (n >= 0) {
                name = s.substring(0, n + 1) + name;
            }
            linkresource[i] = "/" + name;
        }
        this.addAll("platform.pragma", pragma);
        this.addAll("platform.define", define);
        this.addAll("platform.exclude", exclude);
        this.addAll("platform.include", include);
        this.addAll("platform.cinclude", cinclude);
        this.addAll("platform.includepath", includepath);
        this.addAll("platform.includeresource", includeresource);
        this.addAll("platform.compiler.*", compiler);
        this.addAll("platform.linkpath", linkpath);
        this.addAll("platform.linkresource", linkresource);
        this.addAll("platform.link", link);
        this.addAll("platform.frameworkpath", frameworkpath);
        this.addAll("platform.framework", framework);
        this.addAll("platform.preloadresource", preloadresource);
        this.addAll("platform.preloadpath", preloadpath);
        this.addAll("platform.preload", preload);
        this.addAll("platform.resourcepath", resourcepath);
        this.addAll("platform.resource", resource);
        if (this.platformExtension == null || this.platformExtension.length() == 0) {
            this.addAll("platform.extension", extension);
        }
        this.addAll("platform.executablepath", executablepath);
        this.addAll("platform.executable", executable);
        if (hasPlatformProperties || this.getProperty("platform.library", "").length() == 0) {
            this.setProperty("platform.library", library);
        }
        if (LoadEnabled.class.isAssignableFrom(c)) {
            try {
                ((LoadEnabled)c.newInstance()).init(this);
            }
            catch (ClassCastException | IllegalAccessException | InstantiationException e) {
                logger.warn("Could not create an instance of " + c + ": " + e);
            }
        }
        this.loaded |= hasPlatformProperties;
    }

    public List<Class> getInheritedClasses() {
        return this.inheritedClasses;
    }

    public List<Class> getEffectiveClasses() {
        return this.effectiveClasses;
    }

    public boolean isLoaded() {
        return this.loaded;
    }
}

