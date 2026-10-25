/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.tools;

import org.bytedeco.javacpp.tools.Declaration;
import org.bytedeco.javacpp.tools.Parameters;
import org.bytedeco.javacpp.tools.Type;

class Declarator {
    Type type = null;
    Parameters parameters = null;
    Declaration definition = null;
    int infoNumber = 0;
    int indices = 0;
    int indirections = 0;
    boolean constPointer = false;
    boolean operator = false;
    boolean reference = false;
    boolean rvalue = false;
    String cppName = "";
    String javaName = "";
    String signature = "";

    Declarator() {
    }
}

