/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.tools;

import org.bytedeco.javacpp.tools.Attribute;

class Type {
    int indirections = 0;
    boolean anonymous = false;
    boolean constExpr = false;
    boolean constPointer = false;
    boolean constValue = false;
    boolean constructor = false;
    boolean destructor = false;
    boolean operator = false;
    boolean simple = false;
    boolean staticMember = false;
    boolean using = false;
    boolean reference = false;
    boolean rvalue = false;
    boolean value = false;
    boolean friend = false;
    boolean typedef = false;
    boolean virtual = false;
    String annotations = "";
    String cppName = "";
    String javaName = "";
    String[] javaNames = null;
    Type[] arguments = null;
    Attribute[] attributes = null;

    Type() {
    }

    Type(String name) {
        this.cppName = this.javaName = name;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj.getClass() == this.getClass()) {
            Type other = (Type)obj;
            return this.cppName.equals(other.cppName) && this.javaName.equals(other.javaName);
        }
        return false;
    }

    public int hashCode() {
        return this.cppName.hashCode() ^ this.javaName.hashCode();
    }

    String signature() {
        String sig = "";
        for (char c : this.javaName.substring(this.javaName.lastIndexOf(32) + 1).toCharArray()) {
            sig = sig + (Character.isJavaIdentifierPart(c) ? c : (char)'_');
        }
        return sig;
    }
}

