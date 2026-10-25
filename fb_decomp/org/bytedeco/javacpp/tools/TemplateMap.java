/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.tools;

import java.util.LinkedHashMap;
import java.util.Map;
import org.bytedeco.javacpp.tools.Declarator;
import org.bytedeco.javacpp.tools.Type;

class TemplateMap
extends LinkedHashMap<String, Type> {
    Type type = null;
    Declarator declarator = null;
    TemplateMap parent = null;
    boolean variadic = false;

    TemplateMap(TemplateMap parent) {
        this.parent = parent;
    }

    String getName() {
        return this.type != null ? this.type.cppName : (this.declarator != null ? this.declarator.cppName : null);
    }

    boolean empty() {
        for (Type t : this.values()) {
            if (t == null) continue;
            return false;
        }
        return true;
    }

    boolean full() {
        for (Type t : this.values()) {
            if (t != null) continue;
            return false;
        }
        return true;
    }

    Type get(String key) {
        Type value = (Type)super.get(key);
        if (value == null && this.parent != null) {
            return this.parent.get(key);
        }
        return value;
    }

    @Override
    public String toString() {
        String s = "<";
        for (Map.Entry e : this.entrySet()) {
            if (s.length() > 1) {
                s = s + ",";
            }
            Type t = (Type)e.getValue();
            s = s + (t != null ? t.cppName : (String)e.getKey());
        }
        if (s.endsWith(">")) {
            s = s + " ";
        }
        return s + ">";
    }
}

