/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class EncodingFileWriter
extends OutputStreamWriter {
    String newline = "\n";

    public EncodingFileWriter(File file, String newline) throws IOException {
        super(new FileOutputStream(file));
        if (newline != null) {
            this.newline = newline;
        }
    }

    public EncodingFileWriter(File file, String encoding, String newline) throws IOException {
        super((OutputStream)new FileOutputStream(file), encoding);
        if (newline != null) {
            this.newline = newline;
        }
    }

    @Override
    public Writer append(CharSequence text) throws IOException {
        return super.append(((String)text).replace("\n", this.newline).replace("\\u", "\\u005Cu"));
    }
}

