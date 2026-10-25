/*
 * Decompiled with CFR 0.152.
 */
package com.moulberry.flashback.screen.select_replay;

import java.util.Comparator;

public class NaturalOrderComparator
implements Comparator<String> {
    public static NaturalOrderComparator INSTANCE = new NaturalOrderComparator();

    private int compareRight(String a, String b) {
        int bias = 0;
        int ia = 0;
        int ib = 0;
        while (true) {
            char ca = NaturalOrderComparator.charAt(a, ia);
            char cb = NaturalOrderComparator.charAt(b, ib);
            if (!NaturalOrderComparator.isDigit(ca) && !NaturalOrderComparator.isDigit(cb)) {
                return bias;
            }
            if (!NaturalOrderComparator.isDigit(ca)) {
                return -1;
            }
            if (!NaturalOrderComparator.isDigit(cb)) {
                return 1;
            }
            if (ca == '\u0000' && cb == '\u0000') {
                return bias;
            }
            if (bias == 0) {
                if (ca < cb) {
                    bias = -1;
                } else if (ca > cb) {
                    bias = 1;
                }
            }
            ++ia;
            ++ib;
        }
    }

    @Override
    public int compare(String a, String b) {
        int indexA = 0;
        int indexB = 0;
        while (true) {
            int bias;
            int numZeroesB = 0;
            int numZeroesA = 0;
            char charA = NaturalOrderComparator.charAt(a, indexA);
            char charB = NaturalOrderComparator.charAt(b, indexB);
            while (Character.isSpaceChar(charA) || charA == '0') {
                numZeroesA = charA == '0' ? ++numZeroesA : 0;
                charA = NaturalOrderComparator.charAt(a, ++indexA);
            }
            while (Character.isSpaceChar(charB) || charB == '0') {
                numZeroesB = charB == '0' ? ++numZeroesB : 0;
                charB = NaturalOrderComparator.charAt(b, ++indexB);
            }
            if (charA == '\u0000' && charB == '\u0000') {
                return NaturalOrderComparator.compareEqual(a, b, numZeroesA, numZeroesB);
            }
            boolean isDigitA = Character.isDigit(charA);
            boolean isDigitB = Character.isDigit(charB);
            if (isDigitA && isDigitB && (bias = this.compareRight(a.substring(indexA), b.substring(indexB))) != 0) {
                return bias;
            }
            if (isDigitA && !isDigitB) {
                return 1;
            }
            if (isDigitB && !isDigitA) {
                return -1;
            }
            int charCompare = Character.compare(charA, charB);
            if (charCompare != 0) {
                return charCompare;
            }
            ++indexA;
            ++indexB;
        }
    }

    private static boolean isDigit(char c) {
        return Character.isDigit(c) || c == '.' || c == ',';
    }

    private static char charAt(String s, int i) {
        return i >= s.length() ? (char)'\u0000' : s.charAt(i);
    }

    private static int compareEqual(String a, String b, int nza, int nzb) {
        if (nza - nzb != 0) {
            return nza - nzb;
        }
        if (a.length() == b.length()) {
            return a.compareTo(b);
        }
        return a.length() - b.length();
    }
}

