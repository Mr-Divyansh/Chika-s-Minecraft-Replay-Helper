/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.tools;

import org.bytedeco.javacpp.tools.Declarator;
import org.bytedeco.javacpp.tools.Type;

class Declaration {
    Type type = null;
    Declarator declarator = null;
    boolean abstractMember = false;
    boolean constMember = false;
    boolean inaccessible = false;
    boolean incomplete = false;
    boolean function = false;
    boolean variable = false;
    boolean comment = false;
    boolean custom = false;
    String signature = "";
    String text = "";

    Declaration() {
    }

    public String toString() {
        return this.text;
    }
}

