/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.tools;

public class Info {
    String[] cppNames = null;
    String[] javaNames = null;
    String[] annotations = null;
    String[] cppTypes = null;
    String[] valueTypes = null;
    String[] pointerTypes = null;
    String[] linePatterns = null;
    boolean cast = false;
    boolean define = false;
    boolean enumerate = false;
    boolean flatten = false;
    boolean friendly = false;
    boolean immutable = false;
    boolean beanify = false;
    boolean objectify = false;
    boolean translate = false;
    boolean skip = false;
    boolean skipDefaults = false;
    boolean purify = false;
    boolean upcast = false;
    boolean virtualize = false;
    String base = null;
    String cppText = null;
    String javaText = null;

    public Info() {
    }

    public Info(String ... cppNames) {
        this.cppNames = cppNames;
    }

    public Info(Info i) {
        this.cppNames = i.cppNames != null ? (String[])i.cppNames.clone() : null;
        this.javaNames = i.javaNames != null ? (String[])i.javaNames.clone() : null;
        this.annotations = i.annotations != null ? (String[])i.annotations.clone() : null;
        this.cppTypes = i.cppTypes != null ? (String[])i.cppTypes.clone() : null;
        this.valueTypes = i.valueTypes != null ? (String[])i.valueTypes.clone() : null;
        this.pointerTypes = i.pointerTypes != null ? (String[])i.pointerTypes.clone() : null;
        this.linePatterns = i.linePatterns != null ? (String[])i.linePatterns.clone() : null;
        this.cast = i.cast;
        this.define = i.define;
        this.enumerate = i.enumerate;
        this.flatten = i.flatten;
        this.friendly = i.friendly;
        this.immutable = i.immutable;
        this.beanify = i.beanify;
        this.objectify = i.objectify;
        this.translate = i.translate;
        this.skip = i.skip;
        this.skipDefaults = i.skipDefaults;
        this.purify = i.purify;
        this.upcast = i.upcast;
        this.virtualize = i.virtualize;
        this.base = i.base;
        this.cppText = i.cppText;
        this.javaText = i.javaText;
    }

    public Info cppNames(String ... cppNames) {
        this.cppNames = cppNames;
        return this;
    }

    public Info javaNames(String ... javaNames) {
        this.javaNames = javaNames;
        return this;
    }

    public Info annotations(String ... annotations) {
        this.annotations = annotations;
        return this;
    }

    public Info cppTypes(String ... cppTypes) {
        this.cppTypes = cppTypes;
        return this;
    }

    public Info valueTypes(String ... valueTypes) {
        this.valueTypes = valueTypes;
        return this;
    }

    public Info pointerTypes(String ... pointerTypes) {
        this.pointerTypes = pointerTypes;
        return this;
    }

    public Info linePatterns(String ... linePatterns) {
        this.linePatterns = linePatterns;
        return this;
    }

    public Info cast() {
        this.cast = true;
        return this;
    }

    public Info cast(boolean cast) {
        this.cast = cast;
        return this;
    }

    public Info define() {
        this.define = true;
        return this;
    }

    public Info define(boolean define) {
        this.define = define;
        return this;
    }

    public Info enumerate() {
        this.enumerate = true;
        return this;
    }

    public Info enumerate(boolean enumerate) {
        this.enumerate = enumerate;
        return this;
    }

    public Info flatten() {
        this.flatten = true;
        return this;
    }

    public Info flatten(boolean flatten) {
        this.flatten = flatten;
        return this;
    }

    public Info friendly() {
        this.friendly = true;
        return this;
    }

    public Info friendly(boolean friendly) {
        this.friendly = friendly;
        return this;
    }

    public Info immutable() {
        this.immutable = true;
        return this;
    }

    public Info immutable(boolean immutable) {
        this.immutable = immutable;
        return this;
    }

    public Info beanify() {
        this.beanify = true;
        return this;
    }

    public Info beanify(boolean beanify) {
        this.beanify = beanify;
        return this;
    }

    public Info objectify() {
        this.objectify = true;
        return this;
    }

    public Info objectify(boolean objectify) {
        this.objectify = objectify;
        return this;
    }

    public Info translate() {
        this.translate = true;
        return this;
    }

    public Info translate(boolean translate) {
        this.translate = translate;
        return this;
    }

    public Info skip() {
        this.skip = true;
        return this;
    }

    public Info skip(boolean skip) {
        this.skip = skip;
        return this;
    }

    public Info skipDefaults() {
        this.skipDefaults = true;
        return this;
    }

    public Info skipDefaults(boolean skipDefaults) {
        this.skipDefaults = skipDefaults;
        return this;
    }

    public Info purify() {
        this.purify = true;
        return this;
    }

    public Info purify(boolean purify) {
        this.purify = purify;
        return this;
    }

    public Info upcast() {
        this.upcast = true;
        return this;
    }

    public Info upcast(boolean upcast) {
        this.upcast = upcast;
        return this;
    }

    public Info virtualize() {
        this.virtualize = true;
        return this;
    }

    public Info virtualize(boolean virtualize) {
        this.virtualize = virtualize;
        return this;
    }

    public Info base(String base) {
        this.base = base;
        return this;
    }

    public Info cppText(String cppText) {
        this.cppText = cppText;
        return this;
    }

    public Info javaText(String javaText) {
        this.javaText = javaText;
        return this;
    }
}

