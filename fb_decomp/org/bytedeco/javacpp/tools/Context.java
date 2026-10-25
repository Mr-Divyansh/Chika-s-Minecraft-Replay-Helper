/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bytedeco.javacpp.tools.Declarator;
import org.bytedeco.javacpp.tools.InfoMap;
import org.bytedeco.javacpp.tools.TemplateMap;
import org.bytedeco.javacpp.tools.Type;

class Context {
    String namespace = null;
    String baseType = null;
    String cppName = null;
    String javaName = null;
    String constName = null;
    String constBaseName = null;
    boolean immutable = false;
    boolean inaccessible = false;
    boolean beanify = false;
    boolean objectify = false;
    boolean upcast = false;
    boolean virtualize = false;
    Declarator variable = null;
    InfoMap infoMap = null;
    TemplateMap templateMap = null;
    List<String> usingList = null;
    Map<String, String> namespaceMap = null;

    Context() {
        this.usingList = new ArrayList<String>();
        this.namespaceMap = new HashMap<String, String>();
    }

    Context(Context c) {
        this.namespace = c.namespace;
        this.baseType = c.baseType;
        this.cppName = c.cppName;
        this.javaName = c.javaName;
        this.constName = c.constName;
        this.constBaseName = c.constBaseName;
        this.immutable = c.immutable;
        this.inaccessible = c.inaccessible;
        this.beanify = c.beanify;
        this.objectify = c.objectify;
        this.upcast = c.upcast;
        this.virtualize = c.virtualize;
        this.variable = c.variable;
        this.infoMap = c.infoMap;
        this.templateMap = c.templateMap;
        this.usingList = c.usingList;
        this.namespaceMap = c.namespaceMap;
    }

    String[] qualify(String cppName) {
        return this.qualify(cppName, null);
    }

    String[] qualify(String cppName, String parameters) {
        if (cppName == null || cppName.length() == 0) {
            return new String[0];
        }
        if (cppName.startsWith("::")) {
            return new String[]{cppName.substring(2)};
        }
        for (Map.Entry<String, String> e : this.namespaceMap.entrySet()) {
            cppName = cppName.replaceAll(e.getKey() + "::", e.getValue() + "::");
        }
        ArrayList<String> names = new ArrayList<String>();
        String ns = this.namespace != null ? this.namespace : "";
        String prefix = this.infoMap.normalize(cppName, false, true);
        while (ns != null) {
            String name;
            String string = name = ns.length() > 0 ? ns + "::" + cppName : cppName;
            if (parameters != null && name.endsWith(parameters)) {
                name = name.substring(0, name.length() - parameters.length());
            }
            TemplateMap map = this.templateMap;
            if (!name.endsWith(">")) {
                while (map != null) {
                    Object name2 = map.getName();
                    if (parameters != null && name2 != null && ((String)name2).endsWith(parameters)) {
                        name2 = ((String)name2).substring(0, ((String)name2).length() - parameters.length());
                    }
                    if (name.equals(name2)) {
                        String args = "<";
                        String separator = "";
                        for (Type t : map.values()) {
                            if (t == null) continue;
                            args = args + separator + t.cppName;
                            separator = ",";
                        }
                        names.add(name + args + (args.endsWith(">") ? " >" : ">") + (parameters != null ? parameters : ""));
                        break;
                    }
                    map = map.parent;
                }
            }
            names.add(name);
            for (String s : this.usingList) {
                int i = s.lastIndexOf("::") + 2;
                String ns2 = ns.length() > 0 ? ns + "::" + s.substring(0, i) : s.substring(0, i);
                String suffix = s.substring(i);
                if (suffix.length() != 0 && !prefix.equals(suffix)) continue;
                names.add(ns2 + cppName);
            }
            int i = (ns = this.infoMap.normalize(ns, false, true)).lastIndexOf("::");
            ns = i >= 0 ? ns.substring(0, i) : (ns.length() > 0 ? "" : null);
        }
        return names.toArray(new String[names.size()]);
    }

    String shorten(String javaName) {
        if (this.javaName != null) {
            int lastDot = 0;
            String s1 = javaName;
            String s2 = this.javaName + '.';
            for (int i = 0; i < s1.length() && i < s2.length() && s1.charAt(i) == s2.charAt(i); ++i) {
                if (s1.charAt(i) != '.') continue;
                lastDot = i;
            }
            if (lastDot > 0) {
                javaName = javaName.substring(lastDot + 1);
            }
        }
        return javaName;
    }
}

