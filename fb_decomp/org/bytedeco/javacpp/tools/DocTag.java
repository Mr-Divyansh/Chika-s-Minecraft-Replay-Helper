/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.tools;

import java.util.regex.Pattern;

class DocTag {
    static String[][] docTagsStr = new String[][]{{"\\\\", "\\\\"}, {"@", "{@literal @}"}, {"(?s)f\\[(.*?)[@\\\\]f\\]", "<pre>{@code \\\\[$1\\\\]}</pre>"}, {"(?s)f\\{([^\\}]*)\\}\\s*\\{(.*?)[@\\\\]f\\}", "<pre>{@code \\\\begin{$1}$2\\\\end{$1}}</pre>"}, {"(?s)f\\$(.*?)[@\\\\]f\\$", "{@code $1}"}, {"authors?\\b", "@author"}, {"deprecated\\b", "@deprecated"}, {"(?:exception|throws?)\\b", "@throws"}, {"param\\s*(\\[[a-z,\\s]+\\])\\s+([a-zA-Z\\$_]+)", "@param $2 $1"}, {"param\\b", "@param"}, {"(?:returns?|result)\\b", "@return"}, {"(?:see|sa)\\b", "@see"}, {"since\\b", "@since"}, {"version\\b", "@version"}};
    static DocTag[] docTags = new DocTag[docTagsStr.length];
    Pattern pattern;
    String replacement;

    DocTag(String p, String r) {
        this.pattern = Pattern.compile(p);
        this.replacement = r;
    }

    static {
        for (int i = 0; i < docTagsStr.length; ++i) {
            DocTag.docTags[i] = new DocTag(docTagsStr[i][0], docTagsStr[i][1]);
        }
    }
}

