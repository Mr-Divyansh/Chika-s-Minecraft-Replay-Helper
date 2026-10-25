/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Templates {
    static final Pattern templatePattern = Pattern.compile("<[^<>=]*>");

    Templates() {
    }

    static String strip(String s) {
        Matcher m;
        do {
            m = templatePattern.matcher(s);
            s = m.replaceFirst("");
        } while (!m.hitEnd());
        return s;
    }

    static boolean notExists(String s) {
        return Templates.strip(s).length() == s.length();
    }

    static List<String> splitNamespace(String s) {
        return Templates.splitNamespace(s, false);
    }

    static List<String> splitNamespace(String s, boolean returnParams) {
        String params;
        int i;
        Matcher m;
        String sTemplatesMasked = s;
        while ((m = templatePattern.matcher(sTemplatesMasked)).find()) {
            char[] c = new char[m.end() - m.start()];
            Arrays.fill(c, '.');
            sTemplatesMasked = sTemplatesMasked.substring(0, m.start()) + new String(c) + sTemplatesMasked.substring(m.end());
        }
        ArrayList<String> comps = new ArrayList<String>();
        int pIndex = sTemplatesMasked.lastIndexOf(41);
        if (pIndex > 0) {
            int count = 1;
            --pIndex;
            while (pIndex >= 0) {
                char c = sTemplatesMasked.charAt(pIndex);
                if (c == ')') {
                    ++count;
                } else if (c == '(' && --count == 0) break;
                --pIndex;
            }
        }
        int start = 0;
        while ((i = sTemplatesMasked.indexOf("::", start)) >= 0 && (i < pIndex || pIndex == -1)) {
            comps.add(s.substring(start, i));
            start = i + 2;
        }
        if (pIndex >= 0) {
            comps.add(s.substring(start, pIndex));
            params = s.substring(pIndex);
        } else {
            comps.add(s.substring(start));
            params = "";
        }
        if (returnParams) {
            comps.add(params);
        }
        return comps;
    }
}

