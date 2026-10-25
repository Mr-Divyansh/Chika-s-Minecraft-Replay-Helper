/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bytedeco.javacpp.ClassProperties;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.tools.Attribute;
import org.bytedeco.javacpp.tools.BuildEnabled;
import org.bytedeco.javacpp.tools.Context;
import org.bytedeco.javacpp.tools.Declaration;
import org.bytedeco.javacpp.tools.DeclarationList;
import org.bytedeco.javacpp.tools.Declarator;
import org.bytedeco.javacpp.tools.DocTag;
import org.bytedeco.javacpp.tools.EncodingFileWriter;
import org.bytedeco.javacpp.tools.Info;
import org.bytedeco.javacpp.tools.InfoMap;
import org.bytedeco.javacpp.tools.InfoMapper;
import org.bytedeco.javacpp.tools.Logger;
import org.bytedeco.javacpp.tools.Parameters;
import org.bytedeco.javacpp.tools.ParserException;
import org.bytedeco.javacpp.tools.TemplateMap;
import org.bytedeco.javacpp.tools.Templates;
import org.bytedeco.javacpp.tools.Token;
import org.bytedeco.javacpp.tools.TokenIndexer;
import org.bytedeco.javacpp.tools.Tokenizer;
import org.bytedeco.javacpp.tools.Type;

public class Parser {
    final Logger logger;
    final Properties properties;
    final String encoding;
    InfoMap infoMap = null;
    InfoMap leafInfoMap = null;
    TokenIndexer tokens = null;
    String lineSeparator = null;
    Set<String> upcasts = new HashSet<String>();
    Map<String, Map<Type, Boolean>> downcasts = new HashMap<String, Map<Type, Boolean>>();
    Set<String> polymorphicClasses = new HashSet<String>();

    public Parser(Logger logger, Properties properties) {
        this(logger, properties, null, null);
    }

    public Parser(Logger logger, Properties properties, String encoding, String lineSeparator) {
        this.logger = logger;
        this.properties = properties;
        this.encoding = encoding;
        this.lineSeparator = lineSeparator;
    }

    Parser(Parser p, String text) {
        this.logger = p.logger;
        this.properties = p.properties;
        this.encoding = p.encoding;
        this.infoMap = p.infoMap;
        this.upcasts = p.upcasts;
        Token t = p.tokens != null ? p.tokens.get() : Token.EOF;
        this.tokens = new TokenIndexer(this.infoMap, new Tokenizer(text, t.file, t.lineNumber).tokenize(), false);
        this.lineSeparator = p.lineSeparator;
    }

    void addDowncast(String base, Type from, boolean virtual) {
        Map<Type, Boolean> inheritance = this.downcasts.get(base);
        if (inheritance == null) {
            inheritance = new HashMap<Type, Boolean>(1);
            this.downcasts.put(base, inheritance);
        }
        inheritance.put(from, virtual);
    }

    static String[] removeDuplicates(String[] a) {
        return a != null && a.length > 1 ? new LinkedHashSet<String>(Arrays.asList(a)).toArray(new String[0]) : a;
    }

    static String removeAnnotations(String s) {
        return s.substring(s.lastIndexOf(32) + 1);
    }

    static String desugarVarargs(String s) {
        return s.trim().endsWith("...") ? s.trim().substring(0, s.length() - 3) + "[]" : s;
    }

    static String filterJavaAnnotations(String s) {
        return s.contains("@Deprecated") ? "@Deprecated " : "";
    }

    static String upcastMethodName(String javaName) {
        String shortName = javaName.substring(javaName.lastIndexOf(46) + 1);
        return "as" + Character.toUpperCase(shortName.charAt(0)) + shortName.substring(1);
    }

    static String constructorName(String cppName) {
        String constructorName = Templates.strip(cppName);
        int namespace = constructorName.lastIndexOf("::");
        if (namespace >= 0) {
            constructorName = constructorName.substring(namespace + 2);
        }
        return cppName + "::" + constructorName;
    }

    String translate(String text) {
        Info info2;
        Info info = this.infoMap.getFirst(text);
        if (info != null && info.javaNames != null && info.javaNames.length > 0) {
            return info.javaNames[0];
        }
        int namespace = text.lastIndexOf("::");
        if (namespace >= 0) {
            Info info22 = this.infoMap.getFirst(text.substring(0, namespace));
            String localName = text.substring(namespace + 2);
            if (info22 != null && info22.pointerTypes != null) {
                text = info22.pointerTypes[0] + "." + localName;
            } else if (localName.length() > 0 && Character.isJavaIdentifierStart(localName.charAt(0))) {
                for (char c : localName.toCharArray()) {
                    if (Character.isJavaIdentifierPart(c)) continue;
                    localName = null;
                    break;
                }
                if (localName != null) {
                    text = localName;
                }
            }
        }
        int castStart = text.lastIndexOf(40);
        int castEnd = text.indexOf(41, castStart);
        if (castStart >= 0 && castStart < castEnd && (info2 = this.infoMap.getFirst(text.substring(castStart + 1, castEnd))) != null && info2.valueTypes != null && info2.valueTypes.length > 0) {
            text = text.substring(0, castStart + 1) + info2.valueTypes[0] + text.substring(castEnd);
        }
        return text;
    }

    void containers(Context context, DeclarationList declList) throws ParserException {
        ArrayList<String> basicContainers = new ArrayList<String>();
        for (Info info : this.infoMap.get("basic/containers")) {
            basicContainers.addAll(Arrays.asList(info.cppTypes));
        }
        for (String containerName : basicContainers) {
            LinkedHashSet<Info> infoSet = new LinkedHashSet<Info>();
            infoSet.addAll(this.leafInfoMap.get("const " + containerName));
            infoSet.addAll(this.leafInfoMap.get(containerName));
            for (Info info : infoSet) {
                int i;
                int j;
                boolean purify;
                Type valueType;
                Type indexType;
                Declaration decl = new Declaration();
                if (info == null || info.skip || !info.define || !info.cppNames[0].contains(containerName)) {
                    if (info == null || info.javaText == null) continue;
                    decl.type = new Type(info.pointerTypes[0]);
                    decl.text = info.javaText;
                    declList.add(decl);
                    continue;
                }
                int dim = containerName.toLowerCase().endsWith("optional") || containerName.toLowerCase().endsWith("variant") || containerName.toLowerCase().endsWith("tuple") || containerName.toLowerCase().endsWith("function") || containerName.toLowerCase().endsWith("pair") ? 0 : 1;
                boolean string = containerName.toLowerCase().endsWith("string");
                boolean constant = info.cppNames[0].startsWith("const ");
                boolean resizable = !constant;
                Type containerType = new Parser(this, info.cppNames[0]).type(context);
                Type firstType = null;
                Type secondType = null;
                if (containerType.arguments == null || containerType.arguments.length == 0 || containerType.arguments[0] == null || containerType.arguments[containerType.arguments.length - 1] == null) continue;
                if (containerType.arguments.length > 1 && containerType.arguments[1].javaName.length() > 0) {
                    resizable = false;
                    indexType = containerType.arguments[0];
                    valueType = containerType.arguments[1];
                } else {
                    resizable &= containerType.arguments.length == 1;
                    indexType = new Type();
                    indexType.value = true;
                    indexType.cppName = "size_t";
                    indexType.javaName = "long";
                    valueType = containerType.arguments[0];
                }
                String indexFunction = "(function = \"at\")";
                String iteratorType = "iterator";
                String keyVariable = "first";
                String valueVariable = "second";
                boolean dict = false;
                boolean list = resizable;
                boolean tuple = false;
                boolean function = false;
                if (valueType.javaName == null || valueType.javaName.length() == 0 || containerName.toLowerCase().endsWith("bitset")) {
                    indexFunction = "";
                    valueType.javaName = "boolean";
                    resizable = false;
                } else if (containerName.toLowerCase().endsWith("dict")) {
                    indexFunction = "(function = \"operator []\")";
                    iteratorType = "Iterator";
                    keyVariable = "key()";
                    valueVariable = "value()";
                    dict = true;
                } else if (containerName.toLowerCase().endsWith("list") || containerName.toLowerCase().endsWith("optional") || containerName.toLowerCase().endsWith("variant") || containerName.toLowerCase().endsWith("tuple") || containerName.toLowerCase().endsWith("function") || containerName.toLowerCase().endsWith("set")) {
                    if (containerType.arguments.length > 1) {
                        valueType = indexType;
                    }
                    indexType = null;
                    resizable = false;
                    list = containerName.toLowerCase().endsWith("list");
                    tuple = containerName.toLowerCase().endsWith("tuple");
                    function = containerName.toLowerCase().endsWith("function");
                } else if (!constant && !resizable) {
                    indexFunction = "";
                }
                while (valueType.cppName.startsWith(containerName) && this.leafInfoMap.get(valueType.cppName, false).size() == 0) {
                    ++dim;
                    valueType = valueType.arguments[0];
                }
                int valueTemplate = valueType.cppName.indexOf("<");
                if (containerName.toLowerCase().endsWith("pair")) {
                    firstType = containerType.arguments[0];
                    secondType = containerType.arguments[1];
                } else if (dim > 0 && valueTemplate >= 0 && valueType.cppName.substring(0, valueTemplate).toLowerCase().endsWith("pair")) {
                    firstType = valueType.arguments[0];
                    secondType = valueType.arguments[1];
                }
                if (function) {
                    int n = valueType.cppName.indexOf(40);
                    Info info2 = this.infoMap.getFirst(valueType.cppName, false);
                    if (info2 != null && info2.pointerTypes != null && info2.pointerTypes.length > 0) {
                        valueType.javaName = info2.pointerTypes[0];
                        valueType.javaNames = info2.pointerTypes;
                    } else {
                        valueType.javaName = "";
                        valueType.javaNames = null;
                    }
                    Declarator dcl = new Parser(this, "typedef " + valueType.cppName.substring(0, n) + "(*" + valueType.javaName + ")" + valueType.cppName.substring(n)).declarator(context, containerType.javaName, -1, false, 0, false, true);
                    valueType.javaName = dcl.type.javaName;
                    dcl.definition.text = "\n" + dcl.definition.text;
                    decl.declarator = dcl;
                }
                ArrayList<Type> types = new ArrayList<Type>(4 + containerType.arguments.length);
                types.addAll(Arrays.asList(firstType, secondType, indexType, valueType));
                types.addAll(Arrays.asList(containerType.arguments));
                for (Type type : types) {
                    Info info2;
                    if (type == null) continue;
                    if (type.annotations == null || type.annotations.length() == 0) {
                        type.annotations = (type.constValue ? "@Const " : "") + (type.indirections == 0 && !type.value ? "@ByRef " : "");
                    }
                    if ((info2 = this.infoMap.getFirst(type.cppName)) == null || !info2.cast || type.annotations.contains("@Cast") || type.javaName.contains("@Cast")) continue;
                    String cast = type.cppName;
                    if (type.constValue && !cast.startsWith("const ")) {
                        cast = "const " + cast;
                    }
                    if (type.indirections > 0) {
                        for (int i2 = 0; i2 < type.indirections; ++i2) {
                            cast = cast + "*";
                        }
                    } else if (!type.value) {
                        cast = cast + "*";
                    }
                    if (type.reference) {
                        cast = cast + "&";
                    }
                    if (type.rvalue) {
                        cast = cast + "&&";
                    }
                    if (type.constPointer && !cast.endsWith(" const")) {
                        cast = cast + " const";
                    }
                    type.annotations = "@Cast(\"" + cast + "\") " + type.annotations;
                }
                String arrayBrackets = "";
                for (int i3 = 0; i3 < dim - 1; ++i3) {
                    arrayBrackets = arrayBrackets + "[]";
                }
                int annotation = containerType.javaName.lastIndexOf(32);
                containerType.annotations = containerType.annotations + containerType.javaName.substring(0, annotation + 1);
                containerType.javaName = containerType.javaName.substring(annotation + 1);
                decl.type = new Type(containerType.javaName);
                decl.text = decl.text + (dim == 0 ? "\n@NoOffset " : "\n") + "@Name(\"" + containerType.cppName + "\") public static class " + containerType.javaName + " extends Pointer {\n    static { Loader.load(); }\n    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */\n    public " + containerType.javaName + "(Pointer p) { super(p); }\n";
                boolean bl = purify = info != null && info.purify;
                if (!constant && !purify && (dim == 0 || containerType.arguments.length == 1 && indexType != null) && firstType != null && secondType != null) {
                    String[] stringArray;
                    String[] stringArray2;
                    if (firstType.javaNames != null) {
                        stringArray2 = Parser.removeDuplicates(firstType.javaNames);
                    } else {
                        String[] stringArray3 = new String[1];
                        stringArray2 = stringArray3;
                        stringArray3[0] = firstType.javaName;
                    }
                    String[] firstNames = stringArray2;
                    if (secondType.javaNames != null) {
                        stringArray = Parser.removeDuplicates(secondType.javaNames);
                    } else {
                        String[] stringArray4 = new String[1];
                        stringArray = stringArray4;
                        stringArray4[0] = secondType.javaName;
                    }
                    String[] secondNames = stringArray;
                    String brackets = arrayBrackets + (dim > 0 ? "[]" : "");
                    for (int n = 0; n < firstNames.length || n < secondNames.length; ++n) {
                        decl.text = decl.text + "    public " + containerType.javaName + "(" + firstNames[Math.min(n, firstNames.length - 1)] + brackets + " firstValue, " + secondNames[Math.min(n, secondNames.length - 1)] + brackets + " secondValue) { this(" + (dim > 0 ? "Math.min(firstValue.length, secondValue.length)" : "") + "); put(firstValue, secondValue); }\n";
                    }
                } else if (resizable && !purify && firstType == null && secondType == null) {
                    String[] stringArray;
                    if (valueType.javaNames != null) {
                        stringArray = Parser.removeDuplicates(valueType.javaNames);
                    } else {
                        String[] stringArray5 = new String[1];
                        stringArray = stringArray5;
                        stringArray5[0] = valueType.javaName;
                    }
                    for (String javaName : stringArray) {
                        if (dim < 2 && !javaName.equals("int") && !javaName.equals("long")) {
                            decl.text = decl.text + "    public " + containerType.javaName + "(" + javaName + " value) { this(1); put(0, value); }\n";
                        }
                        decl.text = decl.text + "    public " + containerType.javaName + "(" + Parser.desugarVarargs(javaName) + arrayBrackets + " ... array) { this(array.length); put(array); }\n";
                    }
                } else if (indexType == null && dim == 0 && !constant && !purify) {
                    int n = 0;
                    String valueNames = "";
                    String valueNames2 = "";
                    String separator = "";
                    for (Type type : containerType.arguments) {
                        String[] stringArray;
                        if (tuple) {
                            valueNames = valueNames + separator + type.annotations + type.javaName + " value" + n;
                            valueNames2 = valueNames2 + separator + "value" + n;
                            separator = ", ";
                            ++n;
                            continue;
                        }
                        if (type.javaNames != null) {
                            stringArray = Parser.removeDuplicates(type.javaNames);
                        } else {
                            String[] stringArray6 = new String[1];
                            stringArray = stringArray6;
                            stringArray6[0] = type.javaName;
                        }
                        for (String javaName : stringArray) {
                            if (javaName.substring(javaName.indexOf(32) + 1).equals("Pointer")) continue;
                            decl.text = decl.text + "    public " + containerType.javaName + "(" + javaName + " value) { this(); put(value); }\n";
                        }
                    }
                    if (tuple) {
                        decl.text = decl.text + "    public " + containerType.javaName + "(" + valueNames + ") { allocate(" + valueNames2 + "); }\n    private native void allocate(" + valueNames + ");\n";
                    }
                }
                if (!purify) {
                    String initArg = "";
                    String initParam = "";
                    if (string) {
                        initArg = ", (" + Parser.removeAnnotations(valueType.javaName) + ")0";
                        initParam = ", " + Parser.removeAnnotations(valueType.javaName) + " value";
                    }
                    decl.text = decl.text + "    public " + containerType.javaName + "()       { allocate();  }\n" + (!resizable ? "" : "    public " + containerType.javaName + "(long n) { allocate(n" + initArg + "); }\n") + "    private native void allocate();\n" + (!resizable ? "" : "    private native void allocate(@Cast(\"size_t\") long n" + initParam + ");\n") + (constant ? "\n\n" : "    public native @Name(\"operator =\") @ByRef " + containerType.javaName + " put(@ByRef " + containerType.annotations + containerType.javaName + " x);\n\n");
                }
                for (int i4 = 0; i4 < dim; ++i4) {
                    String indexAnnotation = i4 > 0 ? "@Index(" + (i4 > 1 ? "value = " + i4 + ", " : "") + "function = \"at\") " : "";
                    String indices = "";
                    String indices2 = "";
                    String separator2 = "";
                    for (j = 0; indexType != null && j < i4; ++j) {
                        indices = indices + separator2 + indexType.annotations + indexType.javaName + " " + (char)(105 + j);
                        indices2 = indices2 + separator2 + (char)(105 + j);
                        separator2 = ", ";
                    }
                    decl.text = decl.text + "    public boolean empty(" + indices + ") { return size(" + indices2 + ") == 0; }\n    public native " + indexAnnotation + "long size(" + indices + ");\n" + (!resizable ? "" : "    public void clear(" + indices + ") { resize(" + indices2 + separator2 + "0); }\n    public native " + indexAnnotation + "void resize(" + indices + separator2 + "@Cast(\"size_t\") long n);\n");
                }
                String params = "";
                String separator = "";
                for (i = 0; indexType != null && i < dim; ++i) {
                    params = params + separator + indexType.annotations + indexType.javaName + " " + (char)(105 + i);
                    separator = ", ";
                }
                if ((dim == 0 || indexType != null) && firstType != null && secondType != null) {
                    int i5;
                    String indexAnnotation = dim == 0 ? "@MemberGetter " : "@Index(" + (dim > 1 ? "value = " + dim + ", " : "") + "function = \"at\") ";
                    decl.text = decl.text + "\n    " + indexAnnotation + "public native " + firstType.annotations + firstType.javaName + " first(" + params + "); public native " + containerType.javaName + " first(" + params + separator + Parser.removeAnnotations(firstType.javaName) + " first);\n    " + indexAnnotation + "public native " + secondType.annotations + secondType.javaName + " second(" + params + ");  public native " + containerType.javaName + " second(" + params + separator + Parser.removeAnnotations(secondType.javaName) + " second);\n";
                    firstType.javaNames = Parser.removeDuplicates(firstType.javaNames);
                    secondType.javaNames = Parser.removeDuplicates(secondType.javaNames);
                    for (i5 = 1; !constant && firstType.javaNames != null && i5 < firstType.javaNames.length; ++i5) {
                        decl.text = decl.text + "    @MemberSetter @Index" + indexFunction + " public native " + containerType.javaName + " first(" + params + separator + firstType.annotations + firstType.javaNames[i5] + " first);\n";
                    }
                    for (i5 = 1; !constant && secondType.javaNames != null && i5 < secondType.javaNames.length; ++i5) {
                        decl.text = decl.text + "    @MemberSetter @Index" + indexFunction + " public native " + containerType.javaName + " second(" + params + separator + secondType.annotations + secondType.javaNames[i5] + " second);\n";
                    }
                } else {
                    if (indexType != null) {
                        decl.text = decl.text + "\n";
                        if (dim == 1 && indexType.javaName.equals("long")) {
                            decl.text = decl.text + "    public " + Parser.removeAnnotations(valueType.javaName) + " front() { return get(0); }\n    public " + Parser.removeAnnotations(valueType.javaName) + " back() { return get(size() - 1); }\n";
                        }
                        decl.text = decl.text + "    @Index" + indexFunction + " public native " + valueType.annotations + valueType.javaName + " get(" + params + ");\n";
                        if (!constant) {
                            decl.text = decl.text + "    public native " + containerType.javaName + " put(" + params + separator + Parser.removeAnnotations(valueType.javaName) + " value);\n";
                        }
                        valueType.javaNames = Parser.removeDuplicates(valueType.javaNames);
                        for (i = 1; !constant && valueType.javaNames != null && i < valueType.javaNames.length; ++i) {
                            decl.text = decl.text + "    @ValueSetter @Index" + indexFunction + " public native " + containerType.javaName + " put(" + params + separator + valueType.annotations + valueType.javaNames[i] + " value);\n";
                        }
                    } else if (dim == 0 && !function) {
                        int n = 0;
                        Type[] i6 = containerType.arguments;
                        int separator2 = i6.length;
                        for (j = 0; j < separator2; ++j) {
                            Type type = i6[j];
                            if (containerType.arguments.length == 1 && !tuple) {
                                decl.text = decl.text + "    public native boolean has_value();\n    public native void reset();\n    public native @Name(\"value\") " + type.annotations + type.javaName + " get();\n";
                            } else {
                                int namespace = containerName.lastIndexOf("::");
                                String ns = containerName.substring(0, namespace);
                                decl.text = decl.text + "    public " + type.annotations + type.javaName + " get" + n + "() { return get" + n + "(this); }\n    @Namespace @Name(\"" + ns + "::get<" + n + ">\") public static native " + type.annotations + type.javaName + " get" + n + "(@ByRef " + containerType.javaName + " container);\n";
                            }
                            if (!constant && !tuple) {
                                decl.text = decl.text + "    @ValueSetter public native " + containerType.javaName + " put(" + type.annotations + type.javaName + " value);\n";
                            }
                            type.javaNames = Parser.removeDuplicates(type.javaNames);
                            for (int i7 = 1; !constant && !tuple && type.javaNames != null && i7 < type.javaNames.length; ++i7) {
                                decl.text = decl.text + "    @ValueSetter public native " + containerType.javaName + " put(" + type.annotations + type.javaNames[i7] + " value);\n";
                            }
                            ++n;
                        }
                    }
                    if (dim == 1 && !containerName.toLowerCase().endsWith("bitset") && containerType.arguments.length >= 1 && containerType.arguments[containerType.arguments.length - 1].javaName.length() > 0) {
                        decl.text = decl.text + "\n";
                        if (indexType == null || !indexType.javaName.equals("long") && containerType.arguments.length == 1) {
                            decl.text = decl.text + "    public " + Parser.removeAnnotations(valueType.javaName) + " front() { try (Iterator it = begin()) { return it.get(); } }\n";
                        }
                        if (!constant) {
                            if (list) {
                                decl.text = decl.text + "    public native @ByVal Iterator insert(@ByVal Iterator pos, " + valueType.annotations + valueType.javaName + " value);\n    public native @ByVal Iterator erase(@ByVal Iterator pos);\n";
                            } else if (indexType == null) {
                                decl.text = decl.text + "    public native void insert(" + valueType.annotations + valueType.javaName + " value);\n    public native void erase(" + valueType.annotations + valueType.javaName + " value);\n";
                            } else if (!dict) {
                                decl.text = decl.text + "    public native void erase(@ByVal Iterator pos);\n";
                            }
                        }
                        if (!(indexType == null || indexType.annotations.contains("@Const") || indexType.annotations.contains("@Cast") || indexType.value)) {
                            indexType.annotations = indexType.annotations + "@Const ";
                        }
                        if (!valueType.annotations.contains("@Const") && !valueType.value) {
                            valueType.annotations = valueType.annotations + "@Const ";
                        }
                        decl.text = decl.text + "    public native @ByVal Iterator begin();\n    public native @ByVal Iterator end();\n    @NoOffset @Name(\"" + iteratorType + "\") public static class Iterator extends Pointer {\n        public Iterator(Pointer p) { super(p); }\n        public Iterator() { }\n\n        public native @Name(\"operator ++\") @ByRef Iterator increment();\n        public native @Name(\"operator ==\") boolean equals(@ByRef Iterator it);\n" + (containerType.arguments.length > 1 && indexType != null ? "        public native @Name(\"operator *()." + keyVariable + "\") @MemberGetter " + indexType.annotations + indexType.javaName + " first();\n        public native @Name(\"operator *()." + valueVariable + "\") @MemberGetter " + valueType.annotations + valueType.javaName + " second();\n" : "        public native @Name(\"operator *\") " + valueType.annotations + valueType.javaName + " get();\n") + "    }\n";
                        if (this.infoMap.getFirst(containerType.cppName + "::" + iteratorType) == null) {
                            this.infoMap.put(new Info(containerType.cppName + "::" + iteratorType).pointerTypes(containerType.javaName + ".Iterator"));
                        }
                    }
                    if (resizable) {
                        int i8;
                        valueType.javaName = Parser.removeAnnotations(valueType.javaName);
                        decl.text = decl.text + "\n    public " + valueType.javaName + arrayBrackets + "[] get() {\n";
                        String indent = "        ";
                        String indices = "";
                        String args = "";
                        String brackets = arrayBrackets;
                        separator = "";
                        for (i8 = 0; i8 < dim; ++i8) {
                            char c = (char)(105 + i8);
                            decl.text = decl.text + indent + (i8 == 0 ? valueType.javaName + brackets + "[] " : "") + "array" + indices + " = new " + valueType.javaName + "[size(" + args + ") < Integer.MAX_VALUE ? (int)size(" + args + ") : Integer.MAX_VALUE]" + brackets + ";\n" + indent + "for (int " + c + " = 0; " + c + " < array" + indices + ".length; " + c + "++) {\n";
                            indent = indent + "    ";
                            indices = indices + "[" + c + "]";
                            args = args + separator + c;
                            brackets = brackets.length() < 2 ? "" : brackets.substring(2);
                            separator = ", ";
                        }
                        decl.text = decl.text + indent + "array" + indices + " = get(" + args + ");\n";
                        for (i8 = 0; i8 < dim; ++i8) {
                            indent = indent.substring(4);
                            decl.text = decl.text + indent + "}\n";
                        }
                        decl.text = decl.text + "        return array;\n    }\n    @Override public String toString() {\n        return java.util.Arrays." + (dim < 2 ? "toString" : "deepToString") + "(get());\n    }\n";
                    }
                }
                if (!constant && (dim == 0 || containerType.arguments.length == 1 && indexType != null) && firstType != null && secondType != null) {
                    String[] stringArray;
                    String[] stringArray7;
                    if (firstType.javaNames != null) {
                        stringArray7 = Parser.removeDuplicates(firstType.javaNames);
                    } else {
                        String[] stringArray8 = new String[1];
                        stringArray7 = stringArray8;
                        stringArray8[0] = firstType.javaName;
                    }
                    String[] firstNames = stringArray7;
                    if (secondType.javaNames != null) {
                        stringArray = Parser.removeDuplicates(secondType.javaNames);
                    } else {
                        String[] stringArray9 = new String[1];
                        stringArray = stringArray9;
                        stringArray9[0] = secondType.javaName;
                    }
                    String[] secondNames = stringArray;
                    String brackets = arrayBrackets + (dim > 0 ? "[]" : "");
                    for (int n = 0; n < firstNames.length || n < secondNames.length; ++n) {
                        int i9;
                        String firstName = firstNames[Math.min(n, firstNames.length - 1)];
                        String secondName = secondNames[Math.min(n, secondNames.length - 1)];
                        firstName = Parser.removeAnnotations(firstName);
                        secondName = Parser.removeAnnotations(secondName);
                        decl.text = decl.text + "\n    public " + containerType.javaName + " put(" + firstName + brackets + " firstValue, " + secondName + brackets + " secondValue) {\n";
                        String indent = "        ";
                        String indices = "";
                        String args = "";
                        separator = "";
                        for (i9 = 0; i9 < dim; ++i9) {
                            char c = (char)(105 + i9);
                            decl.text = decl.text + indent + "for (int " + c + " = 0; " + c + " < firstValue" + indices + ".length && " + c + " < secondValue" + indices + ".length; " + c + "++) {\n";
                            indent = indent + "    ";
                            indices = indices + "[" + c + "]";
                            args = args + separator + c;
                            separator = ", ";
                        }
                        decl.text = decl.text + indent + "first(" + args + separator + "firstValue" + indices + ");\n" + indent + "second(" + args + separator + "secondValue" + indices + ");\n";
                        for (i9 = 0; i9 < dim; ++i9) {
                            indent = indent.substring(4);
                            decl.text = decl.text + indent + "}\n";
                        }
                        decl.text = decl.text + "        return this;\n    }\n";
                    }
                } else if (resizable && firstType == null && secondType == null) {
                    String[] stringArray;
                    boolean first = true;
                    if (valueType.javaNames != null) {
                        stringArray = Parser.removeDuplicates(valueType.javaNames);
                    } else {
                        String[] stringArray10 = new String[1];
                        stringArray = stringArray10;
                        stringArray10[0] = valueType.javaName;
                    }
                    for (String javaName : stringArray) {
                        int i10;
                        javaName = Parser.removeAnnotations(javaName);
                        decl.text = decl.text + "\n";
                        if (dim < 2) {
                            if (first) {
                                decl.text = decl.text + "    public " + javaName + " pop_back() {\n        long size = size();\n        " + javaName + " value = get(size - 1);\n        resize(size - 1);\n        return value;\n    }\n";
                            }
                            decl.text = decl.text + "    public " + containerType.javaName + " push_back(" + javaName + " value) {\n        long size = size();\n        resize(size + 1);\n        return put(size, value);\n    }\n    public " + containerType.javaName + " put(" + javaName + " value) {\n        if (size() != 1) { resize(1); }\n        return put(0, value);\n    }\n";
                        }
                        decl.text = decl.text + "    public " + containerType.javaName + " put(" + Parser.desugarVarargs(javaName) + arrayBrackets + " ... array) {\n";
                        String indent = "        ";
                        String indices = "";
                        String args = "";
                        separator = "";
                        for (i10 = 0; i10 < dim; ++i10) {
                            char c = (char)(105 + i10);
                            decl.text = decl.text + indent + "if (size(" + args + ") != array" + indices + ".length) { resize(" + args + separator + "array" + indices + ".length); }\n" + indent + "for (int " + c + " = 0; " + c + " < array" + indices + ".length; " + c + "++) {\n";
                            indent = indent + "    ";
                            indices = indices + "[" + c + "]";
                            args = args + separator + c;
                            separator = ", ";
                        }
                        decl.text = decl.text + indent + "put(" + args + separator + "array" + indices + ");\n";
                        for (i10 = 0; i10 < dim; ++i10) {
                            indent = indent.substring(4);
                            decl.text = decl.text + indent + "}\n";
                        }
                        decl.text = decl.text + "        return this;\n    }\n";
                        first = false;
                    }
                }
                if (function && decl.declarator != null) {
                    Declarator dcl = decl.declarator.definition.declarator;
                    decl.text = decl.text + "    public native @Name(\"operator =\") @ByRef " + containerType.javaName + " put(@ByRef " + valueType.javaName + " value);\n    public native @Name(\"operator ()\") " + dcl.type.annotations + dcl.type.javaName + " call" + dcl.parameters.list + ";\n";
                }
                if (info != null && info.javaText != null) {
                    declList.spacing = "\n    ";
                    decl.text = decl.text + declList.rescan(info.javaText) + "\n";
                    declList.spacing = null;
                }
                decl.text = decl.text + "}\n";
                declList.add(decl);
            }
        }
    }

    TemplateMap template(Context context) throws ParserException {
        if (!this.tokens.get().match(Token.TEMPLATE)) {
            return null;
        }
        TemplateMap map = new TemplateMap(context.templateMap);
        this.tokens.next().expect(Character.valueOf('<'));
        Token token = this.tokens.next();
        while (!token.match(Token.EOF)) {
            if (token.match(Token.CLASS, Token.TYPENAME)) {
                Token t = this.tokens.next();
                if (t.match("...")) {
                    map.variadic = true;
                    t = this.tokens.next();
                }
                if (t.match(5)) {
                    String key = t.value;
                    map.put(key, map.get(key));
                    token = this.tokens.next();
                } else {
                    map.put("typename arg" + map.size(), null);
                }
            } else if (token.match(5)) {
                String key;
                Type type = this.type(context);
                Token t = this.tokens.get();
                if (t.match(5)) {
                    key = t.value;
                    map.put(key, map.get(key));
                    token = this.tokens.next();
                } else if (type != null) {
                    key = type.cppName;
                    map.put(key, map.get(key));
                }
            }
            if (!token.match(Character.valueOf(','), Character.valueOf('>'))) {
                int count = 0;
                token = this.tokens.get();
                while (!(token.match(Token.EOF) || count == 0 && token.match(Character.valueOf(','), Character.valueOf('>')))) {
                    if (token.match(Character.valueOf('<'), Character.valueOf('('))) {
                        ++count;
                    } else if (token.match(Character.valueOf('>'), Character.valueOf(')'))) {
                        --count;
                    }
                    token = this.tokens.next();
                }
            }
            if (token.expect(Character.valueOf(','), Character.valueOf('>')).match(Character.valueOf('>'))) {
                if (!this.tokens.next().match(Token.TEMPLATE)) break;
                this.tokens.next().expect(Character.valueOf('<'));
            }
            token = this.tokens.next();
        }
        return map;
    }

    Type[] templateArguments(Context context) throws ParserException {
        if (!this.tokens.get().match(Character.valueOf('<'))) {
            return null;
        }
        ArrayList<Type> arguments = new ArrayList<Type>();
        Token token = this.tokens.next();
        while (!token.match(Token.EOF) && !token.match(Character.valueOf('>'))) {
            Parameters p;
            Type type = this.type(context);
            arguments.add(type);
            token = this.tokens.get();
            if (token.match(Character.valueOf('(')) && (p = this.parameters(context, 0, false)) != null) {
                type.cppName = type.cppName + "(";
                String separator = "";
                for (Declarator d : p.declarators) {
                    if (d == null) continue;
                    String s = d.type.cppName;
                    if (d.type.constValue && !s.startsWith("const ")) {
                        s = "const " + s;
                    }
                    for (int i = 0; i < d.indirections; ++i) {
                        s = s + "*";
                    }
                    if (d.reference) {
                        s = s + "&";
                    }
                    if (d.rvalue) {
                        s = s + "&&";
                    }
                    if (d.type.constPointer && !s.endsWith(" const")) {
                        s = s + " const";
                    }
                    type.cppName = type.cppName + separator + s;
                    separator = ",";
                }
                type.cppName = type.cppName + ")";
                token = this.tokens.get();
                if (token.match(Character.valueOf('<'))) {
                    this.tokens.next();
                    token = this.tokens.get();
                }
            }
            if (!token.match(Character.valueOf(','), Character.valueOf('>')) && type != null) {
                int count = 0;
                token = this.tokens.get();
                while (!(token.match(Token.EOF) || count == 0 && token.match(Character.valueOf(','), Character.valueOf('>')))) {
                    if (token.match(Character.valueOf('<'), Character.valueOf('('))) {
                        ++count;
                    } else if (token.match(Character.valueOf('>'), Character.valueOf(')'))) {
                        --count;
                        if (this.tokens.get(1).match(Character.valueOf('<'))) {
                            this.tokens.next();
                        }
                    }
                    for (int i = 0; i < type.indirections; ++i) {
                        type.cppName = type.cppName + "*";
                    }
                    type.indirections = 0;
                    type.cppName = type.cppName + token;
                    if (token.match(Token.CONST, Token.__CONST)) {
                        type.cppName = type.cppName + " ";
                    }
                    token = this.tokens.next();
                }
                if (type.cppName.endsWith("*")) {
                    type.javaName = "PointerPointer";
                    type.annotations = type.annotations + "@Cast(\"" + type.cppName + "*\") ";
                }
            }
            if (token.expect(Character.valueOf(','), Character.valueOf('>')).match(Character.valueOf('>'))) break;
            token = this.tokens.next();
        }
        return arguments.toArray(new Type[0]);
    }

    private String operator(Context context) throws ParserException {
        String res = this.tokens.get().toString();
        int lenFirstToken = res.length();
        String s = "";
        this.tokens.next();
        int backIndex = this.tokens.index;
        Token token = this.tokens.get();
        while (!token.match(Character.valueOf('('), Character.valueOf(';'), Token.EOF)) {
            s = s + token;
            token = this.tokens.next();
        }
        s = Templates.strip(s);
        this.tokens.index = backIndex;
        token = this.tokens.get();
        while (s.length() > res.length() - lenFirstToken) {
            res = res + token;
            token = this.tokens.next();
        }
        return res;
    }

    Type type(Context context) throws ParserException {
        return this.type(context, false);
    }

    Type type(Context context, boolean definition) throws ParserException {
        Type type = new Type();
        ArrayList<Attribute> attributes = new ArrayList<Attribute>();
        Token prevToken = new Token();
        Token token = this.tokens.get();
        while (!token.match(Token.EOF)) {
            block88: {
                block82: {
                    block95: {
                        int backIndex;
                        block96: {
                            block94: {
                                block93: {
                                    block92: {
                                        block91: {
                                            block90: {
                                                block89: {
                                                    block87: {
                                                        block86: {
                                                            block85: {
                                                                block84: {
                                                                    block83: {
                                                                        block81: {
                                                                            if (!token.match("::")) break block81;
                                                                            Info info = this.infoMap.getFirst(type.cppName, false);
                                                                            if (info != null && info.pointerTypes != null && info.pointerTypes.length > 0 && !type.cppName.contains("::") && token.spacing.length() > 0) break;
                                                                            type.cppName = type.cppName + token;
                                                                            break block82;
                                                                        }
                                                                        if (token.match(Token.DECLTYPE)) {
                                                                            type.cppName = type.cppName + token.toString() + this.tokens.next().expect(Character.valueOf('('));
                                                                            int count = 1;
                                                                            token = this.tokens.next();
                                                                            while (!token.match(Token.EOF)) {
                                                                                if (token.match(Character.valueOf('('))) {
                                                                                    ++count;
                                                                                } else if (token.match(Character.valueOf(')'))) {
                                                                                    --count;
                                                                                }
                                                                                type.cppName = type.cppName + token;
                                                                                if (count == 0) break;
                                                                                token = this.tokens.next();
                                                                            }
                                                                            this.tokens.next();
                                                                            break;
                                                                        }
                                                                        if (!token.match(Character.valueOf('<')) || prevToken.match(Token.OPERATOR, Character.valueOf('<'))) break block83;
                                                                        type.arguments = this.templateArguments(context);
                                                                        type.cppName = type.cppName + "<";
                                                                        String separator = "";
                                                                        for (Type t : type.arguments) {
                                                                            String s;
                                                                            if (t == null) continue;
                                                                            type.cppName = type.cppName + separator;
                                                                            Info info = this.infoMap.getFirst(t.cppName);
                                                                            String string = s = info != null && info.cppTypes != null && info.cppTypes.length > 0 ? info.cppTypes[0] : t.cppName;
                                                                            if (t.constValue && !s.startsWith("const ")) {
                                                                                s = "const " + s;
                                                                            }
                                                                            int n = s.indexOf(40);
                                                                            for (int i = 0; i < t.indirections; ++i) {
                                                                                s = n >= 0 ? s.substring(0, n) + "*" + s.substring(n) : s + "*";
                                                                            }
                                                                            if (t.reference) {
                                                                                s = n >= 0 ? s.substring(0, n) + "&" + s.substring(n) : s + "&";
                                                                            }
                                                                            if (t.rvalue) {
                                                                                s = n >= 0 ? s.substring(0, n) + "&&" + s.substring(n) : s + "&&";
                                                                            }
                                                                            if (t.constPointer && !s.endsWith(" const")) {
                                                                                s = s + " const";
                                                                            }
                                                                            type.cppName = type.cppName + s;
                                                                            separator = ",";
                                                                        }
                                                                        type.cppName = type.cppName + (type.cppName.endsWith(">") ? " >" : ">");
                                                                        break block82;
                                                                    }
                                                                    if (!token.match(Token.CONST, Token.__CONST, Token.CONSTEXPR)) break block84;
                                                                    String simpleName = Templates.strip(type.cppName);
                                                                    if (!simpleName.trim().contains(" ") || type.simple) {
                                                                        type.constValue = true;
                                                                    } else {
                                                                        type.constPointer = true;
                                                                    }
                                                                    if (token.match(Token.CONSTEXPR)) {
                                                                        type.constExpr = true;
                                                                    }
                                                                    break block82;
                                                                }
                                                                if (token.match(Character.valueOf('*'))) {
                                                                    ++type.indirections;
                                                                    this.tokens.next();
                                                                    break;
                                                                }
                                                                if (token.match(Character.valueOf('&'))) {
                                                                    type.reference = true;
                                                                    this.tokens.next();
                                                                    break;
                                                                }
                                                                if (token.match("&&")) {
                                                                    type.rvalue = true;
                                                                    this.tokens.next();
                                                                    break;
                                                                }
                                                                if (!token.match(Character.valueOf('~'))) break block85;
                                                                type.cppName = type.cppName + "~";
                                                                type.destructor = true;
                                                                break block82;
                                                            }
                                                            if (!token.match(Token.STATIC)) break block86;
                                                            type.staticMember = true;
                                                            break block82;
                                                        }
                                                        if (!token.match(Token.OPERATOR)) break block87;
                                                        if (type.cppName.length() == 0) {
                                                            type.operator = true;
                                                            prevToken = token;
                                                            this.tokens.next();
                                                        } else {
                                                            if (!type.cppName.endsWith("::")) break;
                                                            type.operator = true;
                                                            prevToken = token;
                                                            this.tokens.next();
                                                            type.cppName = type.cppName + this.operator(context);
                                                        }
                                                        break block88;
                                                    }
                                                    if (!token.match(Token.USING)) break block89;
                                                    type.using = true;
                                                    break block82;
                                                }
                                                if (!token.match(Token.FRIEND)) break block90;
                                                type.friend = true;
                                                break block82;
                                            }
                                            if (!token.match(Token.TYPEDEF)) break block91;
                                            type.typedef = true;
                                            break block82;
                                        }
                                        if (!token.match(Token.VIRTUAL)) break block92;
                                        type.virtual = true;
                                        break block82;
                                    }
                                    if (!token.match(Token.ENUM, Token.EXPLICIT, Token.EXTERN, Token.INLINE, Token.CLASS, Token.FINAL, Token.INTERFACE, Token.__INTERFACE, Token.MUTABLE, Token.NAMESPACE, Token.STRUCT, Token.UNION, Token.TYPENAME, Token.REGISTER, Token.THREAD_LOCAL, Token.VOLATILE)) break block93;
                                    prevToken = token;
                                    this.tokens.next();
                                    break block88;
                                }
                                if (!token.match(this.infoMap.getFirst((String)"basic/types").cppTypes) || this.tokens.get(1).match(Character.valueOf('<')) || type.cppName.length() != 0 && !type.simple) break block94;
                                type.cppName = type.cppName + token.value + " ";
                                type.simple = true;
                                break block82;
                            }
                            if (!token.match(5, "[[")) break block95;
                            backIndex = this.tokens.index;
                            Attribute attr = this.attribute();
                            if (attr == null || !attr.annotation && !token.match("[[")) break block96;
                            type.annotations = type.annotations + attr.javaName;
                            attributes.add(attr);
                            prevToken = token;
                            break block88;
                        }
                        this.tokens.index = backIndex;
                        if (type.cppName.length() == 0 || type.cppName.endsWith("::") || type.cppName.endsWith("~")) {
                            type.cppName = type.cppName + token.value;
                        } else if (type.cppName.endsWith("::template")) {
                            type.cppName = type.cppName + " " + token.value;
                        } else {
                            Info info = this.infoMap.getFirst(this.tokens.get((int)1).value);
                            if (info != null && info.annotations != null || !this.tokens.get(1).match(Character.valueOf('*'), Character.valueOf('&'), 5, Token.CONST, Token.__CONST, Token.CONSTEXPR, Token.FINAL)) {
                                break;
                            }
                        }
                        break block82;
                    }
                    if (!token.match(Character.valueOf('}'))) break;
                    type.anonymous = true;
                    this.tokens.next();
                    break;
                }
                prevToken = token;
                this.tokens.next();
            }
            token = this.tokens.get();
        }
        if (attributes.size() > 0) {
            type.attributes = attributes.toArray(new Attribute[0]);
        }
        type.cppName = type.cppName.trim();
        if (this.tokens.get().match("...")) {
            this.tokens.next();
            boolean paren = this.tokens.get().match(Character.valueOf('('));
            if (paren) {
                this.tokens.next();
            }
            if (this.tokens.get().match(5)) {
                this.tokens.next();
            }
            if (paren && this.tokens.get().match(Character.valueOf(')'))) {
                this.tokens.next();
            }
            return null;
        }
        if (type.operator) {
            token = this.tokens.get();
            while (!token.match(Token.EOF, Character.valueOf('('), Character.valueOf(';'))) {
                type.cppName = type.cppName + token;
                token = this.tokens.next();
            }
        }
        if (type.cppName.endsWith("*")) {
            ++type.indirections;
            type.cppName = type.cppName.substring(0, type.cppName.length() - 1);
        }
        if (type.cppName.endsWith("&")) {
            type.reference = true;
            type.cppName = type.cppName.substring(0, type.cppName.length() - 1);
        }
        if (type.cppName.endsWith("&&")) {
            type.rvalue = true;
            type.cppName = type.cppName.substring(0, type.cppName.length() - 2);
        }
        if (context.templateMap != null) {
            List<String> types = Templates.splitNamespace(type.cppName, true);
            String separator = "";
            type.cppName = "";
            ArrayList<Type> arguments = new ArrayList<Type>();
            int paramsIdx = types.size() - 1;
            for (int i = 0; i < paramsIdx; ++i) {
                Type t2 = context.templateMap.get(types.get(i));
                type.cppName = type.cppName + separator + (t2 != null ? t2.cppName : types.get(i));
                if (t2 != null && t2.arguments != null) {
                    arguments.addAll(Arrays.asList(t2.arguments));
                }
                separator = "::";
            }
            type.cppName = type.cppName + types.get(paramsIdx);
            if (arguments.size() > 0) {
                type.arguments = arguments.toArray(new Type[0]);
            }
        }
        if (type.cppName.startsWith("const ")) {
            type.constValue = true;
            type.cppName = type.cppName.substring(6);
        }
        if (type.cppName.endsWith(" const")) {
            type.constPointer = true;
            type.cppName = type.cppName.substring(0, type.cppName.length() - 6);
        }
        if (type.cppName.endsWith("*")) {
            ++type.indirections;
            if (type.reference) {
                type.constValue = false;
            } else if (type.constValue) {
                type.constValue = false;
                type.constPointer = true;
            }
            type.cppName = type.cppName.substring(0, type.cppName.length() - 1);
        }
        if (type.cppName.endsWith("&")) {
            type.reference = true;
            type.cppName = type.cppName.substring(0, type.cppName.length() - 1);
        }
        if (type.cppName.endsWith("&&")) {
            type.rvalue = true;
            type.cppName = type.cppName.substring(0, type.cppName.length() - 2);
        }
        if (type.cppName.endsWith(" const")) {
            type.constValue = true;
            type.cppName = type.cppName.substring(0, type.cppName.length() - 6);
        }
        Info info = null;
        String shortName = type.cppName;
        String[] names = context.qualify(type.cppName);
        if (definition && names.length > 0) {
            String constName = type.constValue ? "const " + names[0] : names[0];
            constName = type.constPointer ? constName + " const" : constName;
            info = this.infoMap.getFirst(constName, false);
            type.cppName = names[0];
        } else {
            String groupName = context.cppName;
            for (String name : names) {
                if (groupName != null && Templates.strip(groupName).endsWith("::" + shortName) && name.equals(groupName + "::" + shortName)) continue;
                String constName = type.constValue ? "const " + name : name;
                constName = type.constPointer ? constName + " const" : constName;
                info = this.infoMap.getFirst(constName, false);
                if (info != null) {
                    type.cppName = name;
                    break;
                }
                if (this.infoMap.getFirst(constName) == null) continue;
                type.cppName = name;
            }
        }
        if (info != null && info.cppTypes != null && info.cppTypes.length > 0 && !info.cppTypes[0].equals(type.cppName)) {
            type.cppName = info.cppTypes[0];
        }
        if (type.cppName.startsWith("const ")) {
            type.constValue = true;
            type.cppName = type.cppName.substring(6);
        }
        if (type.cppName.endsWith(" const")) {
            type.constPointer = true;
            type.cppName = type.cppName.substring(0, type.cppName.length() - 6);
        }
        if (type.cppName.endsWith("*")) {
            ++type.indirections;
            if (type.reference) {
                type.constValue = false;
            }
            type.cppName = type.cppName.substring(0, type.cppName.length() - 1);
        }
        if (type.cppName.endsWith("&")) {
            type.reference = true;
            type.cppName = type.cppName.substring(0, type.cppName.length() - 1);
        }
        if (type.cppName.endsWith("&&")) {
            type.rvalue = true;
            type.cppName = type.cppName.substring(0, type.cppName.length() - 2);
        }
        if (type.cppName.endsWith(" const")) {
            type.constValue = true;
            type.cppName = type.cppName.substring(0, type.cppName.length() - 6);
        }
        List<String> typeSplit = Templates.splitNamespace(type.cppName);
        String string = type.javaName = Templates.notExists(type.cppName) ? typeSplit.get(typeSplit.size() - 1) : type.cppName;
        if (info != null) {
            if (type.indirections == 0 && !type.reference && info.valueTypes != null && info.valueTypes.length > 0) {
                type.javaName = info.valueTypes[0];
                type.javaNames = info.valueTypes;
                type.value = true;
            } else if (info.pointerTypes != null && info.pointerTypes.length > 0) {
                type.javaName = info.pointerTypes[0];
                type.javaNames = info.pointerTypes;
            } else if (info.javaNames != null && info.javaNames.length > 0) {
                type.javaName = info.javaNames[0];
                type.javaNames = info.javaNames;
            }
        }
        if (type.operator) {
            if (type.constValue && !type.constExpr) {
                type.annotations = type.annotations + "@Const ";
            }
            if (type.indirections == 0 && !type.reference && !type.value) {
                type.annotations = type.annotations + "@ByVal ";
            } else if (type.indirections == 0 && type.reference && !type.value) {
                type.annotations = type.annotations + "@ByRef ";
            }
            if (info != null && info.cast) {
                type.annotations = type.annotations + "@Cast(\"" + type.cppName + (!type.value ? "*" : "") + "\") ";
            }
            type.annotations = type.annotations + "@Name(\"operator " + (type.constValue && !type.constExpr ? "const " : "") + type.cppName + (type.indirections > 0 ? "*" : (type.reference ? "&" : "")) + "\") ";
        }
        if (info != null && info.annotations != null) {
            for (String s : info.annotations) {
                type.annotations = type.annotations + s + " ";
            }
        }
        if (context.cppName != null && type.javaName.length() > 0) {
            String cppName = Templates.strip(type.cppName);
            String groupName = Templates.strip(context.cppName);
            List<String> cppNameSplit = Templates.splitNamespace(cppName);
            List<String> groupNameSplit = Templates.splitNamespace(groupName);
            if (cppNameSplit.size() == 1 && groupNameSplit.size() > 1) {
                groupName = groupNameSplit.get(groupNameSplit.size() - 1);
            } else if (cppNameSplit.size() > 1 && groupNameSplit.size() == 1) {
                cppName = cppNameSplit.get(cppNameSplit.size() - 1);
            }
            if (cppName.equals(groupName)) {
                boolean bl = type.constructor = !type.destructor && !type.operator && type.indirections == 0 && !type.reference && this.tokens.get().match(Character.valueOf('('), Character.valueOf(':'));
                if (type.constructor) {
                    type.cppName = context.cppName;
                }
            }
            type.javaName = context.shorten(type.javaName);
        }
        if (info != null && info.upcast) {
            this.upcasts.add(type.javaName);
        }
        return type;
    }

    /*
     * WARNING - void declaration
     */
    Declarator declarator(Context context, String defaultName, int infoNumber, boolean useDefaults, int varNumber, boolean arrayAsPointer, boolean pointerAsArray) throws ParserException {
        String originalName;
        String[] infoList;
        Info info;
        Attribute convention;
        boolean fieldPointer;
        Declaration definition;
        Info groupInfo;
        int indirections2;
        int[] dims;
        Attribute attr;
        String cast;
        String precast;
        int count;
        String[] type;
        Declarator dcl;
        boolean using;
        boolean typedef;
        block174: {
            Token token;
            block168: {
                typedef = this.tokens.get().match(Token.TYPEDEF);
                using = this.tokens.get().match(Token.USING);
                if (using && defaultName != null) {
                    this.tokens.next().expect(5);
                    this.tokens.next().expect(Character.valueOf('='));
                    this.tokens.next();
                }
                dcl = new Declarator();
                type = this.type(context);
                if (type == null) {
                    return null;
                }
                typedef |= type.typedef;
                count = 0;
                int number = 0;
                Token token2 = this.tokens.get();
                while (number < varNumber && !token2.match(Token.EOF)) {
                    if (token2.match(Character.valueOf('('), Character.valueOf('['), Character.valueOf('{'))) {
                        ++count;
                    } else if (token2.match(Character.valueOf(')'), Character.valueOf(']'), Character.valueOf('}'))) {
                        --count;
                    } else if (token2.match("]]")) {
                        count -= 2;
                    } else if (count <= 0) {
                        if (token2.match(Character.valueOf(','))) {
                            ++number;
                        } else if (token2.match(Character.valueOf(';'))) {
                            this.tokens.next();
                            return null;
                        }
                    }
                    token2 = this.tokens.next();
                }
                precast = null;
                cast = type.cppName;
                if (varNumber == 0 && type.indirections > 0) {
                    dcl.indirections += type.indirections;
                    for (int i = 0; i < type.indirections; ++i) {
                        cast = cast + "*";
                    }
                }
                if (type.constValue) {
                    cast = "const " + cast;
                }
                if (type.constPointer) {
                    dcl.constPointer = true;
                }
                if (varNumber == 0 && type.reference) {
                    dcl.reference = true;
                    cast = cast + "&";
                }
                if (varNumber == 0 && type.rvalue) {
                    dcl.rvalue = true;
                    cast = cast + "&&";
                }
                Token token3 = this.tokens.get();
                while (!token3.match(Token.EOF)) {
                    if (token3.match(Character.valueOf('*'))) {
                        ++dcl.indirections;
                    } else if (token3.match(Character.valueOf('&'))) {
                        dcl.reference = true;
                    } else if (token3.match("&&")) {
                        dcl.rvalue = true;
                    } else {
                        if (!token3.match(Token.CONST, Token.__CONST, Token.CONSTEXPR)) break;
                        dcl.constPointer = true;
                    }
                    cast = cast + token3;
                    token3 = this.tokens.next();
                }
                ArrayList<Attribute> attributes = new ArrayList<Attribute>();
                if (type.attributes != null) {
                    attributes.addAll(Arrays.asList(type.attributes));
                }
                int backIndex = this.tokens.index;
                attr = this.attribute();
                while (attr != null && attr.annotation) {
                    type.annotations = type.annotations + attr.javaName;
                    attributes.add(attr);
                    backIndex = this.tokens.index;
                    attr = this.attribute();
                }
                attr = null;
                this.tokens.index = backIndex;
                for (Attribute a : attributes) {
                    if (a.javaName != null && a.javaName.contains("@Name ") && a.arguments.length() > 0 && Character.isJavaIdentifierStart(a.arguments.charAt(0))) {
                        attr = a;
                        for (char c : a.arguments.toCharArray()) {
                            if (Character.isJavaIdentifierPart(c)) continue;
                            attr = null;
                            break;
                        }
                    }
                    if (attr == null) continue;
                    type.annotations = type.annotations.replace("@Name ", "");
                    break;
                }
                count = 0;
                while (this.tokens.get().match(Character.valueOf('(')) && this.tokens.get(1).match(Character.valueOf('('))) {
                    this.tokens.next();
                    ++count;
                }
                dims = new int[256];
                indirections2 = 0;
                dcl.cppName = "";
                groupInfo = null;
                definition = new Declaration();
                fieldPointer = false;
                convention = null;
                for (Attribute a : attributes) {
                    if (!a.annotation || a.javaName.length() != 0 || a.arguments.length() != 0) continue;
                    convention = a;
                }
                if ((!this.tokens.get().match(Character.valueOf('(')) || using && !this.tokens.get(1).match(Character.valueOf('*')) && (!this.tokens.get(2).match("::") || !this.tokens.get(3).match(Character.valueOf('*')))) && (!typedef || !this.tokens.get(1).match(Character.valueOf('(')))) break block168;
                if (this.tokens.get().match(Character.valueOf('('))) {
                    this.tokens.next();
                }
                token = this.tokens.get();
                while (!token.match(Token.EOF)) {
                    block173: {
                        block170: {
                            block171: {
                                int backIndex2;
                                block172: {
                                    block169: {
                                        if (!token.match(Token.CONST, Token.__CONST, Token.CONSTEXPR)) break block169;
                                        dcl.constPointer = true;
                                        break block170;
                                    }
                                    if (!token.match(5, "::")) break block171;
                                    backIndex2 = this.tokens.index;
                                    Attribute attr2 = this.attribute();
                                    if (attr2 == null || !attr2.annotation) break block172;
                                    type.annotations = type.annotations + attr2.javaName;
                                    attributes.add(attr2);
                                    convention = attr2;
                                    break block173;
                                }
                                this.tokens.index = backIndex2;
                                dcl.cppName = dcl.cppName + token;
                                break block170;
                            }
                            if (token.match(Character.valueOf('*'))) {
                                ++indirections2;
                                if (dcl.cppName.endsWith("::")) {
                                    dcl.cppName = dcl.cppName.substring(0, dcl.cppName.length() - 2);
                                    for (String name : context.qualify(dcl.cppName)) {
                                        groupInfo = this.infoMap.getFirst(name, false);
                                        if (groupInfo != null) {
                                            dcl.cppName = name;
                                            break;
                                        }
                                        if (this.infoMap.getFirst(name) == null) continue;
                                        dcl.cppName = name;
                                    }
                                    definition.text = definition.text + "@Namespace(\"" + dcl.cppName + "\") ";
                                } else if (convention != null || dcl.cppName.length() > 0) {
                                    definition.text = definition.text + "@Convention(\"" + (convention != null ? convention.cppName : dcl.cppName) + "\") ";
                                    convention = null;
                                }
                                dcl.cppName = "";
                            } else if (token.match(Character.valueOf('['))) {
                                Token n = this.tokens.get(1);
                                try {
                                    dims[dcl.indices++] = n.match(1) ? Integer.decode(n.value) : -1;
                                }
                                catch (NumberFormatException e) {
                                    dims[dcl.indices] = -1;
                                }
                            } else if (token.match(Character.valueOf('('), Character.valueOf(')'))) break;
                        }
                        this.tokens.next();
                    }
                    token = this.tokens.get();
                }
                if (!this.tokens.get().match(Character.valueOf(')'))) break block174;
                this.tokens.next();
                break block174;
            }
            if (this.tokens.get().match(5, "::")) {
                token = this.tokens.get();
                while (!token.match(Token.EOF)) {
                    if (dcl.cppName.length() > 1 && token.match(Character.valueOf('*'))) {
                        dcl.cppName = dcl.cppName.substring(0, dcl.cppName.length() - 2);
                        for (String name : context.qualify(dcl.cppName)) {
                            groupInfo = this.infoMap.getFirst(name, false);
                            if (groupInfo != null) {
                                dcl.cppName = name;
                                break;
                            }
                            if (this.infoMap.getFirst(name) == null) continue;
                            dcl.cppName = name;
                        }
                        definition.text = definition.text + "@Namespace(\"" + dcl.cppName + "\") ";
                        token = this.tokens.get();
                        while (!token.match(Token.EOF) && token.match(Character.valueOf('*'))) {
                            ++indirections2;
                            token = this.tokens.next();
                        }
                        dcl.cppName = token.match(5) ? token.toString() : "";
                        fieldPointer = groupInfo != null;
                    } else if (token.match("::")) {
                        dcl.cppName = dcl.cppName + token;
                    } else if (token.match(Token.OPERATOR)) {
                        dcl.operator = true;
                        if (!this.tokens.get(1).match(5) || this.tokens.get(1).match(Token.NEW, Token.DELETE)) {
                            this.tokens.next();
                            dcl.cppName = dcl.cppName + "operator " + this.operator(context);
                            break;
                        }
                    } else if (token.match(Character.valueOf('<'))) {
                        Type[] types = this.templateArguments(context);
                        dcl.cppName = dcl.cppName + "<";
                        for (int i = 0; i < types.length; ++i) {
                            if (i > 0) {
                                dcl.cppName = dcl.cppName + ",";
                            }
                            dcl.cppName = dcl.cppName + types[i].cppName;
                        }
                        if (dcl.cppName.endsWith(">")) {
                            dcl.cppName = dcl.cppName + " ";
                        }
                        dcl.cppName = dcl.cppName + ">";
                    } else {
                        if (!token.match(5) || dcl.cppName.length() != 0 && !dcl.cppName.endsWith("::")) break;
                        dcl.cppName = dcl.cppName + token;
                    }
                    token = this.tokens.next();
                }
            }
        }
        if (dcl.cppName.length() == 0) {
            dcl.cppName = defaultName;
        }
        boolean bracket = false;
        Token token = this.tokens.get();
        while (!token.match(Token.EOF)) {
            if (!bracket && token.match(Character.valueOf('['))) {
                bracket = true;
                Token n = this.tokens.get(1);
                try {
                    dims[dcl.indices++] = n.match(1) ? Integer.decode(n.value) : -1;
                }
                catch (NumberFormatException e) {
                    dims[dcl.indices] = -1;
                }
            } else {
                if (!bracket) break;
                if (bracket && token.match(Character.valueOf(']'))) {
                    bracket = false;
                }
            }
            token = this.tokens.next();
        }
        while (dcl.indices > 0 && indirections2 > 0) {
            dims[dcl.indices++] = -1;
            --indirections2;
        }
        if (arrayAsPointer && dcl.indices > 0) {
            ++dcl.indirections;
            String dimCast = "";
            for (int i = 1; i < dcl.indices; ++i) {
                if (dims[i] <= 0) continue;
                dimCast = dimCast + "[" + dims[i] + "]";
            }
            if (!dimCast.isEmpty()) {
                cast = dims[0] != -1 ? cast + "(* /*[" + dims[0] + "]*/ )" : cast + "(*)";
                cast = cast + dimCast;
            } else {
                cast = cast + "*";
            }
        }
        if (pointerAsArray && dcl.indirections > (type.anonymous ? 0 : 1)) {
            dims[dcl.indices++] = -1;
            --dcl.indirections;
            cast = cast.substring(0, cast.length() - 1);
        }
        if (this.tokens.get().match(Character.valueOf(':'))) {
            type.annotations = type.annotations + "@NoOffset ";
            this.tokens.next().expect(1, 5);
            this.tokens.next().expect(Character.valueOf(','), Character.valueOf(';'));
            if (dcl.cppName == null) {
                dcl.cppName = "";
            }
        }
        dcl.parameters = this.parameters(context, infoNumber, useDefaults);
        if (type.cppName.equals("void") && indirections2 == 1 && !typedef && this.tokens.get(1).match(Character.valueOf('('))) {
            this.tokens.next().expect(Character.valueOf('('));
            this.tokens.next().expect(5);
            this.type(context);
            indirections2 = 0;
        } else if (indirections2 == 1 && !typedef && this.tokens.get(1).match(Character.valueOf('['))) {
            this.tokens.next().expect(Character.valueOf('['));
            this.tokens.next().expect(5);
            this.tokens.next().expect(Character.valueOf(']'));
            ++dcl.indirections;
            --indirections2;
        }
        int infoLength = 1;
        boolean valueType = false;
        boolean needCast = arrayAsPointer && dcl.indices > 1;
        boolean implicitConst = false;
        Info constInfo = this.infoMap.getFirst("const " + type.cppName, false);
        Info info2 = info = type.constValue && (dcl.indirections == 0 || dcl.indirections < 2 && !dcl.reference) ? constInfo : this.infoMap.getFirst(type.cppName, false);
        if (!(typedef && dcl.parameters == null || constInfo != null && (constInfo.cppTypes == null || constInfo.cppTypes.length <= 0) || info != null && (info.cppTypes == null || info.cppTypes.length <= 0))) {
            String[] type2 = type;
            if (info != null) {
                type2 = new Parser(this, info.cppTypes[0]).type(context);
            }
            infoList = this.infoMap.get(type2.cppName);
            for (Info info3 : infoList) {
                if (type2.arguments == null || info3.annotations == null) continue;
                type.constPointer = type2.arguments[0].constPointer;
                type.constValue = type2.arguments[0].constValue;
                type.simple = type2.arguments[0].simple;
                type.indirections = type2.arguments[0].indirections;
                type.reference = type2.arguments[0].reference;
                type.rvalue = type2.arguments[0].rvalue;
                type.value = type2.arguments[0].value;
                type.annotations = type2.arguments[0].annotations;
                type.cppName = type2.arguments[0].cppName;
                type.javaName = type2.arguments[0].javaName;
                dcl.indirections = 1;
                dcl.reference = false;
                dcl.rvalue = false;
                if (context.virtualize) {
                    needCast = true;
                    precast = cast;
                }
                cast = type.cppName + "*";
                if (type.constValue && !cast.startsWith("const ")) {
                    cast = "const " + cast;
                }
                if (type.indirections > 0) {
                    dcl.indirections += type.indirections;
                    for (int i = 0; i < type.indirections; ++i) {
                        cast = cast + "*";
                    }
                }
                if (type.reference) {
                    dcl.reference = true;
                    cast = cast + "&";
                }
                if (type.rvalue) {
                    dcl.rvalue = true;
                    cast = cast + "&&";
                }
                if (type.constPointer && !cast.endsWith(" const")) {
                    cast = cast + " const";
                }
                String[] i = info3.annotations;
                int n = i.length;
                for (int j = 0; j < n; ++j) {
                    String s = i[j];
                    type.annotations = type.annotations + s + " ";
                }
                info = this.infoMap.getFirst(type.cppName, false);
                break;
            }
        }
        if (!(using && defaultName == null || info == null)) {
            valueType = (info.enumerate || info.valueTypes != null) && (type.constValue && dcl.indirections == 0 && dcl.reference || dcl.indirections == 0 && !dcl.reference || info.pointerTypes == null);
            boolean bl = implicitConst = info.cppNames[0].startsWith("const ") && !info.define;
            infoLength = valueType ? (info.valueTypes != null ? info.valueTypes.length : 1) : (info.pointerTypes != null ? info.pointerTypes.length : 1);
            int n = dcl.infoNumber = infoNumber < 0 ? 0 : infoNumber % infoLength;
            type.javaName = valueType ? (info.valueTypes != null ? info.valueTypes[dcl.infoNumber] : type.javaName) : (info.pointerTypes != null ? info.pointerTypes[dcl.infoNumber] : type.javaName);
            type.javaName = context.shorten(type.javaName);
            needCast |= info.cast && !type.cppName.equals(type.javaName);
        }
        if (!valueType || context.virtualize) {
            if (!valueType && dcl.indirections == 0 && !dcl.reference) {
                type.annotations = type.annotations + (dcl.rvalue ? "@ByRef(true) " : "@ByVal ");
            } else if (dcl.indirections == 0 && dcl.reference) {
                if (type.javaName.contains("@ByPtrPtr ")) {
                    type.javaName = type.javaName.replace("@ByPtrPtr ", "@ByPtrRef ");
                } else {
                    type.annotations = type.annotations + "@ByRef ";
                }
            } else if (!type.javaName.contains("@ByPtrRef ") && dcl.indirections == 1 && dcl.reference) {
                type.annotations = type.annotations + "@ByPtrRef ";
            } else if (!(type.javaName.contains("@ByPtrPtr ") || dcl.indirections != 2 || dcl.reference || infoNumber < 0 && !type.javaName.equals("PointerPointer"))) {
                type.annotations = type.annotations + "@ByPtrPtr ";
                needCast |= type.cppName.equals("void");
            } else if (dcl.indirections >= 2) {
                dcl.infoNumber += infoLength;
                needCast = true;
                if (type.javaName.contains("@ByPtrRef ") || dcl.reference) {
                    type.annotations = type.annotations + "@ByRef ";
                } else if (type.javaName.contains("@ByPtrPtr ") || dcl.indirections >= 3) {
                    type.annotations = type.annotations + "@ByPtrPtr ";
                }
                type.javaName = "PointerPointer";
            }
            if (!needCast && !type.javaName.contains("@Cast")) {
                if (type.constValue && !implicitConst) {
                    type.annotations = "@Const " + type.annotations;
                }
                if (type.constPointer) {
                    // empty if block
                }
            }
        }
        if (needCast || valueType && dcl.rvalue && !type.annotations.contains("@") && !type.javaName.contains("@")) {
            if (dcl.indirections == 0 && dcl.reference) {
                cast = cast.replace('&', '*');
            }
            if (!valueType && dcl.indirections == 0 && dcl.rvalue) {
                cast = cast.replace("&&", "*");
            }
            if (valueType && type.constValue && dcl.reference) {
                cast = cast.substring(0, cast.length() - 1);
            }
            if (type.constValue && !cast.startsWith("const ")) {
                cast = "const " + cast;
            }
            type.annotations = precast != null ? "@Cast({\"" + cast + "\", \"" + precast + "\"}) " + type.annotations : (!valueType && dcl.indirections == 0 && !dcl.reference && !dcl.rvalue ? type.annotations + "@Cast(\"" + cast + "*\") " : "@Cast(\"" + cast + "\") " + type.annotations);
        }
        info = null;
        String string = dcl.javaName = attr != null ? attr.arguments : dcl.cppName;
        if (defaultName == null) {
            for (String string2 : context.qualify(dcl.cppName)) {
                info = this.infoMap.getFirst(string2, false);
                if (info != null) {
                    dcl.cppName = string2;
                    break;
                }
                if (this.infoMap.getFirst(string2) == null) continue;
                dcl.cppName = string2;
            }
        }
        String string3 = originalName = fieldPointer ? groupInfo.pointerTypes[0] : dcl.javaName;
        if (attr == null && defaultName == null && info != null && info.javaNames != null && info.javaNames.length > 0 && (dcl.operator || Templates.notExists(info.cppNames[0]) || dcl.cppName.equals(info.cppNames[0]))) {
            dcl.javaName = info.javaNames[0];
        }
        if (info != null && info.annotations != null) {
            void var35_62;
            infoList = info.annotations;
            int n = infoList.length;
            boolean bl = false;
            while (var35_62 < n) {
                String s2 = infoList[var35_62];
                if (!type.annotations.contains(s2)) {
                    type.annotations = type.annotations + s2 + " ";
                }
                ++var35_62;
            }
        }
        dcl.type = type;
        dcl.signature = dcl.javaName;
        if (dcl.parameters != null || fieldPointer) {
            if (dcl.parameters != null) {
                dcl.infoNumber = Math.max(dcl.infoNumber, dcl.parameters.infoNumber);
            }
            if (dcl.parameters != null && indirections2 == 0 && !using && !typedef) {
                dcl.signature = dcl.signature + dcl.parameters.signature;
            } else {
                void var35_75;
                String string4;
                void var35_73;
                void var35_68;
                if (convention != null) {
                    definition.text = definition.text + "@Convention(\"" + convention.cppName + "\") ";
                }
                String cppType = "";
                if (dcl.type != null) {
                    void var35_64;
                    String s3 = dcl.type.cppName;
                    if (dcl.type.constValue && !s3.startsWith("const ")) {
                        s3 = "const " + s3;
                    }
                    boolean bl = false;
                    while (var35_64 < dcl.indirections) {
                        s3 = s3 + "*";
                        ++var35_64;
                    }
                    if (dcl.reference) {
                        s3 = s3 + "&";
                    }
                    if (dcl.rvalue) {
                        s3 = s3 + "&&";
                    }
                    if (dcl.type.constPointer && !s3.endsWith(" const")) {
                        s3 = s3 + " const";
                    }
                    cppType = cppType + s3;
                }
                cppType = cppType + " (*)(";
                String separator = "";
                if (dcl.parameters != null) {
                    for (Declarator d : dcl.parameters.declarators) {
                        if (d == null) continue;
                        String s5 = d.type.cppName;
                        if (d.type.constValue && !s5.startsWith("const ")) {
                            s5 = "const " + s5;
                        }
                        for (int i = 0; i < d.indirections; ++i) {
                            s5 = s5 + "*";
                        }
                        if (d.reference) {
                            s5 = s5 + "&";
                        }
                        if (d.rvalue) {
                            s5 = s5 + "&&";
                        }
                        if (d.type.constPointer && !s5.endsWith(" const")) {
                            s5 = s5 + " const";
                        }
                        cppType = cppType + separator + s5;
                        separator = ", ";
                    }
                }
                if ((info = this.infoMap.getFirst(cppType = cppType + ")")) == null) {
                    info = this.infoMap.getFirst(dcl.cppName);
                }
                Object var35_66 = null;
                if (originalName != null) {
                    String string5 = Character.toUpperCase(originalName.charAt(0)) + originalName.substring(1);
                }
                if (info != null && info.pointerTypes != null && info.pointerTypes.length > 0) {
                    String string6 = info.pointerTypes[infoNumber < 0 ? 0 : infoNumber % info.pointerTypes.length];
                } else if (typedef && originalName != null && originalName.length() > 0 && originalName != defaultName) {
                    String string7 = originalName;
                } else if (dcl.parameters != null && dcl.parameters.signature.length() > 0) {
                    String string8 = (String)var35_68 + dcl.parameters.signature;
                } else if (!type.javaName.equals("void")) {
                    String s4 = type.javaName.trim();
                    int n = s4.lastIndexOf(32);
                    if (n > 0) {
                        s4 = s4.substring(n + 1);
                    }
                    String string9 = Character.toUpperCase(s4.charAt(0)) + s4.substring(1) + "_" + (String)var35_68;
                }
                if (info != null && info.annotations != null) {
                    for (String s : info.annotations) {
                        definition.text = definition.text + s + " ";
                    }
                }
                if (var35_73 == null) {
                    String string10 = "null";
                }
                if (!(string4 = Parser.removeAnnotations((String)var35_75)).equals("Pointer") && !string4.equals("long")) {
                    definition.type = new Type(string4);
                    for (Info info23 : this.infoMap.get("function/pointers")) {
                        if (info23 == null || info23.annotations == null) continue;
                        for (String s6 : info23.annotations) {
                            definition.text = definition.text + s6 + " ";
                        }
                    }
                    definition.text = definition.text + (this.tokens.get().match(Token.CONST, Token.__CONST, Token.CONSTEXPR) ? "@Const " : "") + "public static class " + string4 + " extends FunctionPointer {\n    static { Loader.load(); }\n    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */\n    public    " + string4 + "(Pointer p) { super(p); }\n" + (groupInfo != null ? "" : "    protected " + string4 + "() { allocate(); }\n    private native void allocate();\n");
                    definition.text = fieldPointer ? definition.text + "    public native " + type.annotations + type.javaName + " get(" + groupInfo.pointerTypes[0] + " o);\n    public native " + string4 + " put(" + groupInfo.pointerTypes[0] + " o, " + type.annotations + type.javaName + " v);\n}\n" : definition.text + "    public native " + type.annotations + type.javaName + " call" + (groupInfo != null ? "(" + groupInfo.pointerTypes[0] + " o" + (dcl.parameters.list.charAt(1) == ')' ? ")" : ", " + dcl.parameters.list.substring(1)) : dcl.parameters.list) + ";\n}\n";
                }
                definition.signature = string4;
                definition.declarator = new Declarator();
                definition.declarator.type = new Type(type.javaName);
                definition.declarator.parameters = dcl.parameters;
                if (info != null && info.javaText != null) {
                    definition.signature = definition.text = info.javaText;
                    definition.declarator = null;
                    boolean bl = definition.custom = !info.define;
                }
                if (info == null || !info.skip) {
                    dcl.definition = definition;
                }
                dcl.indirections = indirections2;
                if (pointerAsArray && dcl.indirections > 1) {
                    dims[dcl.indices++] = -1;
                    --dcl.indirections;
                }
                if (!fieldPointer) {
                    dcl.parameters = null;
                }
                if (dcl.indirections > 1) {
                    int n = cppType.indexOf(40);
                    type.annotations = "@Cast(\"" + cppType.substring(0, n + 1) + "*" + cppType.substring(n + 1) + "\") ";
                    type.javaName = "PointerPointer";
                } else {
                    type.annotations = info != null && info.cast ? "@Cast(\"" + cppType + "\") " : (dcl.constPointer ? "@Const " : "");
                    type.javaName = string4;
                }
            }
        }
        if (dcl.cppName != null) {
            String localName = dcl.cppName;
            if (context.namespace != null && localName.startsWith(context.namespace + "::")) {
                localName = dcl.cppName.substring(context.namespace.length() + 2);
            }
            String simpleName = Templates.strip(localName);
            if (dcl.type.friend) {
                localName = simpleName;
            }
            if (!(localName.equals(dcl.javaName) || simpleName.contains("::") && context.javaName != null)) {
                type.annotations = type.annotations + "@Name(\"" + localName + "\") ";
            }
        }
        while (this.tokens.get().match(Character.valueOf(')')) && count > 0) {
            this.tokens.next();
            --count;
        }
        return dcl;
    }

    String commentDoc(String s, int startIndex) {
        if (startIndex < 0 || startIndex > s.length()) {
            return s;
        }
        StringBuilder sb = new StringBuilder(s);
        for (int index = s.indexOf("/**", startIndex); index < sb.length(); ++index) {
            char c = sb.charAt(index);
            String ss = sb.substring(index + 1);
            if (c == '`' && ss.startsWith("``") && sb.length() - index > 3) {
                sb.replace(index, index + 3, "<pre>{@code" + (Character.isWhitespace(sb.charAt(index + 3)) ? "" : " "));
                index = sb.indexOf("```", index);
                if (index < 0) break;
                sb.replace(index, index + 3, "}</pre>");
                continue;
            }
            if (c == '`') {
                sb.replace(index, index + 1, "{@code ");
                index = sb.indexOf("`", index);
                if (index < 0) break;
                sb.replace(index, index + 1, "}");
                continue;
            }
            if ((c == '\\' || c == '@') && ss.startsWith("code")) {
                sb.replace(index, index + 5, "<pre>{@code" + (Character.isWhitespace(sb.charAt(index + 5)) ? "" : " "));
                index = sb.indexOf(c + "endcode", index);
                if (index < 0) break;
                sb.replace(index, index + 8, "}</pre>");
                continue;
            }
            if ((c == '\\' || c == '@') && ss.startsWith("verbatim")) {
                sb.replace(index, index + 9, "<pre>{@literal" + (Character.isWhitespace(sb.charAt(index + 9)) ? "" : " "));
                index = sb.indexOf(c + "endverbatim", index);
                if (index < 0) break;
                sb.replace(index, index + 12, "}</pre>");
                continue;
            }
            if (c == '\n' && ss.length() > 0 && ss.charAt(0) == '\n') {
                int n;
                for (n = 0; n < ss.length() && ss.charAt(n) == '\n'; ++n) {
                }
                String indent = "";
                while (n < ss.length() && Character.isWhitespace(ss.charAt(n))) {
                    indent = indent + ss.charAt(n);
                    ++n;
                }
                sb.insert(index + 1, indent + "<p>");
                continue;
            }
            if (c == '\\' || c == '@') {
                boolean tagFound = false;
                for (DocTag tag : DocTag.docTags) {
                    Matcher matcher = tag.pattern.matcher(ss);
                    if (!matcher.lookingAt()) continue;
                    StringBuffer sbuf = new StringBuffer();
                    matcher.appendReplacement(sbuf, tag.replacement);
                    if (sbuf.charAt(0) == '@' && !Character.isWhitespace(sb.charAt(index + matcher.end() + 1))) {
                        sbuf.append(' ');
                    }
                    sb.replace(index + matcher.start(), index + 1 + matcher.end(), sbuf.toString());
                    index += sbuf.length() - 1;
                    tagFound = true;
                    break;
                }
                if (tagFound) continue;
                sb.setCharAt(index, '\\');
                continue;
            }
            if (c == '*' && ss.charAt(0) == '/' && (index = sb.indexOf("/**", index)) < 0) break;
        }
        return sb.toString();
    }

    String commentBefore() {
        String comment = "";
        this.tokens.raw = true;
        while (this.tokens.index > 0 && this.tokens.get(-1).match(4)) {
            --this.tokens.index;
        }
        boolean closeComment = false;
        int startDoc = -1;
        Token token = this.tokens.get();
        while (token.match(4)) {
            block12: {
                String s;
                block13: {
                    block11: {
                        s = token.value;
                        if (!s.startsWith("/**") && !s.startsWith("/*!") && !s.startsWith("///") && !s.startsWith("//!")) break block11;
                        if (s.startsWith("//") && s.contains("*/") && startDoc >= 0) {
                            s = s.replace("*/", "* /");
                        }
                        if (s.length() > 3 && s.charAt(3) == '<') break block12;
                        if (s.length() >= 3 && (s.startsWith("///") || s.startsWith("//!")) && !s.startsWith("////") && !s.startsWith("///*")) {
                            String lastComment = comment.trim();
                            int n2 = lastComment.indexOf(10);
                            while (!lastComment.startsWith("/*") && n2 > 0) {
                                lastComment = n2 + 1 < lastComment.length() ? lastComment.substring(n2 + 1).trim() : "";
                                n2 = lastComment.indexOf(10);
                            }
                            s = (comment.length() == 0 || comment.contains("*/") || !lastComment.startsWith("/*") ? "/**" : " * ") + s.substring(3);
                            closeComment = true;
                        } else if (s.length() > 3 && !s.startsWith("///")) {
                            s = "/**" + s.substring(3);
                        }
                        break block13;
                    }
                    if (closeComment && !comment.endsWith("*/")) {
                        closeComment = false;
                        comment = comment + " */";
                    }
                }
                if (startDoc < 0 && s.startsWith("/**")) {
                    startDoc = comment.length();
                }
                comment = comment + token.spacing + s;
                if (startDoc >= 0 && comment.endsWith("*/")) {
                    comment = this.commentDoc(comment, startDoc);
                    startDoc = -1;
                }
            }
            token = this.tokens.next();
        }
        if (closeComment && !comment.endsWith("*/")) {
            comment = comment + " */";
        }
        this.tokens.raw = false;
        return this.commentDoc(comment, startDoc);
    }

    String commentAfter() {
        String comment = "";
        this.tokens.raw = true;
        while (this.tokens.index > 0 && this.tokens.get(-1).match(4)) {
            --this.tokens.index;
        }
        boolean closeComment = false;
        int startDoc = -1;
        Token token = this.tokens.get();
        while (token.match(4)) {
            String s = token.value;
            String spacing = token.spacing;
            int n = spacing.lastIndexOf(10) + 1;
            if ((s.startsWith("/**") || s.startsWith("/*!") || s.startsWith("///") || s.startsWith("//!")) && (s.length() <= 3 || s.charAt(3) == '<')) {
                if (s.length() > 4 && (s.startsWith("///") || s.startsWith("//!"))) {
                    String lastComment = comment.trim();
                    int n2 = lastComment.indexOf(10);
                    while (!lastComment.startsWith("/*") && n2 > 0) {
                        lastComment = n2 + 1 < lastComment.length() ? lastComment.substring(n2 + 1).trim() : "";
                        n2 = lastComment.indexOf(10);
                    }
                    s = (comment.length() == 0 || comment.contains("*/") || !lastComment.startsWith("/*") ? "/**" : " * ") + s.substring(4);
                    closeComment = true;
                } else if (s.length() > 4) {
                    s = "/**" + s.substring(4);
                }
                if (startDoc < 0 && s.startsWith("/**")) {
                    startDoc = comment.length();
                }
                comment = comment + spacing.substring(0, n) + s;
                if (startDoc >= 0 && comment.endsWith("*/")) {
                    comment = this.commentDoc(comment, startDoc);
                    startDoc = -1;
                }
            }
            token = this.tokens.next();
        }
        if (closeComment && !comment.endsWith("*/")) {
            comment = comment + " */";
        }
        if (comment.length() > 0) {
            comment = comment + "\n";
        }
        this.tokens.raw = false;
        return this.commentDoc(comment, startDoc);
    }

    Attribute attribute() {
        return this.attribute(false);
    }

    Attribute attribute(boolean explicit) {
        int count;
        boolean brackets = false;
        if (this.tokens.get().match("[[")) {
            brackets = true;
            this.tokens.next();
        } else if (!this.tokens.get().match(5) || this.tokens.get(1).match(Character.valueOf('<'))) {
            return null;
        }
        Attribute attr = new Attribute();
        attr.cppName = this.tokens.get().value;
        Info info = this.infoMap.getFirst(attr.cppName);
        boolean keyword = attr.cppName.equals("__attribute__") || attr.cppName.equals("alignas");
        attr.annotation = info != null && info.annotations != null && info.javaNames == null && info.valueTypes == null && info.pointerTypes == null;
        if (attr.annotation) {
            for (String s : info.annotations) {
                attr.javaName = attr.javaName + s + " ";
            }
        }
        if (!brackets && explicit && !attr.annotation && !keyword) {
            return null;
        }
        int n = count = this.tokens.next().match(Character.valueOf('(')) ? 1 : 0;
        if (this.tokens.get().match("]]")) {
            brackets = false;
            this.tokens.next();
        }
        if (!brackets && count == 0) {
            return attr;
        }
        if (keyword) {
            attr.cppName = attr.cppName + this.tokens.get().spacing + this.tokens.get();
        }
        Token token = this.tokens.next();
        while (!token.match(Token.EOF) && (brackets || count > 0)) {
            if (token.match(Character.valueOf('('))) {
                ++count;
            } else if (token.match(Character.valueOf(')'))) {
                --count;
            } else if (token.match("]]")) {
                brackets = false;
            } else if (info == null || !info.skip) {
                attr.arguments = attr.arguments + token.value;
            }
            if (keyword) {
                attr.cppName = attr.cppName + token.spacing + token;
            }
            token = this.tokens.next();
        }
        if (keyword) {
            attr.annotation = true;
            info = this.infoMap.getFirst(attr.cppName);
            if (info != null && info.annotations != null) {
                for (String s : info.annotations) {
                    attr.javaName = attr.javaName + s + " ";
                }
            }
        }
        return attr;
    }

    String body() throws ParserException {
        String text = "";
        if (!this.tokens.get().match(Character.valueOf('{'))) {
            return null;
        }
        int count = 1;
        boolean catchBlock = false;
        Token token = this.tokens.next();
        while (!token.match(Token.EOF) && count > 0) {
            while (this.tokens.get().match(Character.valueOf('#'))) {
                this.macro(null, null);
                token = this.tokens.get();
            }
            if (token.match(Character.valueOf('{'))) {
                if (catchBlock) {
                    catchBlock = false;
                } else {
                    ++count;
                }
            } else if (token.match(Character.valueOf('}'))) {
                --count;
            }
            if (count == 0 && this.tokens.get(1).match("catch")) {
                ++count;
                catchBlock = true;
            }
            if (count > 0) {
                text = text + token.spacing + token;
            }
            token = this.tokens.next();
        }
        return text;
    }

    Parameters parameters(Context context, int infoNumber, boolean useDefaults) throws ParserException {
        int backIndex = this.tokens.index;
        if (!this.tokens.get().match(Character.valueOf('('))) {
            return null;
        }
        int count = 0;
        Parameters params = new Parameters();
        ArrayList<Declarator> dcls = new ArrayList<Declarator>();
        params.list = "(";
        params.names = "(";
        int lastVarargs = -1;
        Token token = this.tokens.next();
        while (!token.match(Token.EOF)) {
            if (token.match("...")) {
                token = this.tokens.next();
            }
            String spacing = token.spacing;
            if (token.match(Character.valueOf(')'))) {
                params.list = params.list + spacing + ")";
                params.names = params.names + ")";
                this.tokens.next();
                break;
            }
            Declarator dcl = this.declarator(context, "arg" + count++, infoNumber, useDefaults, 0, true, false);
            boolean hasDefault = !this.tokens.get().match(Character.valueOf(','), Character.valueOf(')'));
            Token defaultToken = null;
            String defaultValue = "";
            if (dcl != null && hasDefault) {
                String s;
                int n;
                defaultToken = this.tokens.get();
                int count2 = 0;
                token = this.tokens.next();
                token.spacing = "";
                while (!(token.match(Token.EOF) || count2 == 0 && token.match(Character.valueOf(','), Character.valueOf(')'), Character.valueOf('}')))) {
                    if (token.match(Character.valueOf('('), Character.valueOf('{')) || count2 == 0 && token.match(Character.valueOf('<'))) {
                        ++count2;
                    } else if (token.match(Character.valueOf(')'), Character.valueOf('}')) || count2 == 1 && token.match(Character.valueOf('>'))) {
                        --count2;
                    }
                    Object cppName = token.value;
                    if (context.templateMap != null) {
                        String[] types = Templates.splitNamespace((String)cppName, true);
                        String separator = "";
                        cppName = "";
                        int paramsIdx = types.size() - 1;
                        for (int i = 0; i < paramsIdx; ++i) {
                            Type t2 = context.templateMap.get((String)types.get(i));
                            cppName = (String)cppName + separator + (t2 != null ? t2.cppName : (String)types.get(i));
                            separator = "::";
                        }
                        cppName = (String)cppName + (String)types.get(paramsIdx);
                    }
                    for (String name : context.qualify((String)cppName)) {
                        if (this.infoMap.getFirst(name, false) != null) {
                            cppName = name;
                            break;
                        }
                        if (this.infoMap.getFirst(name) == null) continue;
                        cppName = name;
                    }
                    if (token.match(5)) {
                        while (this.tokens.get(1).equals("::")) {
                            this.tokens.next();
                            Token t = this.tokens.next();
                            cppName = (String)cppName + "::" + t.spacing + t;
                        }
                    }
                    defaultValue = defaultValue + token.spacing + (cppName != null && ((String)cppName).length() > 0 ? cppName : token);
                    token = this.tokens.next();
                }
                for (String name : context.qualify(defaultValue)) {
                    if (this.infoMap.getFirst(name, false) != null) {
                        defaultValue = name;
                        break;
                    }
                    if (this.infoMap.getFirst(name) == null) continue;
                    defaultValue = name;
                }
                if ((n = (s = dcl.type.annotations).indexOf("@ByVal ")) < 0) {
                    n = s.indexOf("@ByRef ");
                }
                if (n >= 0) {
                    Info info;
                    if (!defaultValue.startsWith(dcl.type.cppName)) {
                        defaultValue = dcl.type.cppName + "(" + defaultValue + ")";
                    }
                    if ((info = this.infoMap.getFirst(defaultValue)) != null && info.skip) {
                        if (useDefaults) {
                            this.tokens.index = backIndex;
                            return this.parameters(context, infoNumber, false);
                        }
                    } else {
                        defaultValue = defaultValue.replaceAll("\"", "\\\\\"").replaceAll("\n(\\s*)", "\"\n$1 + \"").replaceAll("\\(\\{\\}\\)", "{}");
                        s = s.substring(0, n + 6) + "(nullValue = \"" + defaultValue + "\")" + s.substring(n + 6);
                    }
                }
                dcl.type.annotations = s;
            }
            if (!(dcl == null || dcl.type.javaName.equals("void") || hasDefault && useDefaults)) {
                if (lastVarargs >= 0) {
                    params.list = params.list.substring(0, lastVarargs) + "[]" + params.list.substring(lastVarargs + 3);
                }
                int n = params.list.length();
                Info info = this.infoMap.getFirst(dcl.javaName);
                String paramName = info != null && info.javaNames != null && info.javaNames.length > 0 ? info.javaNames[0] : dcl.javaName;
                params.infoNumber = Math.max(params.infoNumber, dcl.infoNumber);
                params.list = params.list + (count > 1 ? "," : "") + spacing + dcl.type.annotations + dcl.type.javaName + " " + paramName;
                lastVarargs = params.list.indexOf("...", n);
                if (hasDefault && !dcl.type.annotations.contains("(nullValue = ")) {
                    params.list = params.list + "/*" + defaultToken + defaultValue + "*/";
                }
                params.signature = params.signature + '_';
                params.signature = params.signature + dcl.type.signature();
                params.names = params.names + (count > 1 ? ", " : "") + paramName;
                if (this.upcasts.contains(dcl.type.javaName)) {
                    params.names = params.names + '.' + Parser.upcastMethodName(Parser.removeAnnotations(dcl.type.javaName)) + "()";
                }
                if (dcl.javaName.startsWith("arg")) {
                    try {
                        count = Integer.parseInt(dcl.javaName.substring(3)) + 1;
                    }
                    catch (NumberFormatException numberFormatException) {
                        // empty catch block
                    }
                }
            }
            if (!(dcl == null || hasDefault && useDefaults)) {
                dcls.add(dcl);
            }
            if (this.tokens.get().expect(Character.valueOf(','), Character.valueOf(')')).match(Character.valueOf(','))) {
                this.tokens.next();
            }
            token = this.tokens.get();
        }
        if (context.templateMap == null && dcls.size() == 1 && (dcls.get(0) == null || ((Declarator)dcls.get((int)0)).type == null || ((Declarator)dcls.get((int)0)).type.cppName == null || ((Declarator)dcls.get((int)0)).type.cppName.length() == 0)) {
            this.tokens.index = backIndex;
            return null;
        }
        params.declarators = dcls.toArray(new Declarator[0]);
        return params;
    }

    static String incorporateConstAnnotation(String annotations, int constValueIndex, boolean constValue) {
        int start = annotations.indexOf("@Const");
        int end = annotations.indexOf("@", start + 1);
        if (end == -1) {
            end = annotations.length();
        }
        String prefix = annotations.substring(0, start);
        String constAnnotation = annotations.substring(start, end);
        String suffix = " " + annotations.substring(end);
        String boolPatternStr = "(true|false)";
        Pattern boolPattern = Pattern.compile(boolPatternStr);
        Matcher matcher = boolPattern.matcher(constAnnotation);
        boolean[] constArray = new boolean[]{true, false, false};
        int index = 0;
        while (matcher.find()) {
            constArray[index++] = Boolean.parseBoolean(matcher.group(1));
        }
        constArray[constValueIndex] = constValue;
        String incorporatedConstAnnotation = "@Const({" + constArray[0] + ", " + constArray[1] + ", " + constArray[2] + "})";
        return prefix + incorporatedConstAnnotation + suffix;
    }

    /*
     * WARNING - void declaration
     */
    boolean function(Context context, DeclarationList declList) throws ParserException {
        boolean friendly;
        String localName;
        boolean isQualified;
        int backIndex = this.tokens.index;
        String spacing = this.tokens.get().spacing;
        String modifiers = "public native ";
        int startIndex = this.tokens.index;
        Type type = this.type(context);
        Parameters params = this.parameters(context, 0, false);
        Declarator dcl = new Declarator();
        Declaration decl = new Declaration();
        if (type.javaName.length() == 0) {
            this.tokens.index = backIndex;
            return false;
        }
        if (context.javaName == null && !type.operator && params != null) {
            while (!this.tokens.get().match(Character.valueOf(':'), Character.valueOf('{'), Character.valueOf(';'), Token.EOF)) {
                this.tokens.next();
            }
            Token token = this.tokens.get();
            while (!token.match(Token.EOF) && this.attribute() != null) {
                token = this.tokens.get();
            }
            if (this.tokens.get().match(Character.valueOf(':'))) {
                int count = 0;
                Token token2 = this.tokens.next();
                while (!token2.match(Token.EOF)) {
                    if (token2.match(Character.valueOf('('))) {
                        ++count;
                    } else if (token2.match(Character.valueOf(')'))) {
                        --count;
                    }
                    if (count == 0 && !token2.match(5, Character.valueOf(','), Character.valueOf('>')) && this.tokens.get(1).match(Character.valueOf('{'))) {
                        this.tokens.next();
                        break;
                    }
                    if (count == 0 && token2.match(Character.valueOf(';'))) break;
                    token2 = this.tokens.next();
                }
            }
            if (this.tokens.get().match("->")) {
                this.tokens.next();
                type = this.type(context);
            }
            if (this.tokens.get().match(Character.valueOf('{'))) {
                this.body();
            } else {
                while (!this.tokens.get().match(Character.valueOf(';'), Token.EOF)) {
                    this.tokens.next();
                }
            }
            decl.text = spacing;
            decl.function = true;
            declList.add(decl);
            return true;
        }
        if ((type.constructor || type.destructor || type.operator) && params != null) {
            dcl.type = type;
            dcl.parameters = params;
            dcl.cppName = type.cppName;
            dcl.javaName = Parser.removeAnnotations(type.javaName);
            if (type.operator) {
                dcl.cppName = "operator " + (dcl.type.constValue ? "const " : "") + dcl.type.cppName + (dcl.type.indirections > 0 ? "*" : (dcl.type.reference ? "&" : ""));
                dcl.javaName = Parser.upcastMethodName(dcl.javaName);
            }
            dcl.signature = dcl.javaName + params.signature;
        } else {
            this.tokens.index = startIndex;
            dcl = this.declarator(context, null, -1, false, 0, false, false);
            type = dcl.type;
        }
        if (dcl.cppName == null || type.javaName.length() == 0 || dcl.parameters == null) {
            this.tokens.index = backIndex;
            return false;
        }
        boolean bl = isQualified = Templates.splitNamespace(dcl.cppName).size() > 1;
        if (!(context.namespace == null || isQualified || type.constructor || type.destructor)) {
            dcl.cppName = context.namespace + "::" + dcl.cppName;
        }
        Info info = null;
        Info fullInfo = null;
        String templateArgs = declList.templateMap != null ? declList.templateMap.toString() : "";
        String fullname = dcl.cppName;
        if (!fullname.endsWith(">")) {
            fullname = fullname + templateArgs;
        }
        String param1 = "";
        String param2 = "";
        if (dcl.parameters != null) {
            param1 = "(";
            param2 = "(";
            String separator = "";
            for (Declarator d : dcl.parameters.declarators) {
                if (d == null) continue;
                String s = d.type.cppName;
                String s2 = d.type.cppName;
                if (d.type.constValue && !s.startsWith("const ")) {
                    s = "const " + s;
                }
                if (d.indirections > 0) {
                    for (int i = 0; i < d.indirections; ++i) {
                        s = s + "*";
                        s2 = s2 + "*";
                    }
                }
                if (d.reference) {
                    s = s + "&";
                    s2 = s2 + "&";
                }
                if (d.rvalue) {
                    s = s + "&&";
                    s2 = s2 + "&&";
                }
                if (d.type.constPointer && !s.endsWith(" const")) {
                    s = s + " const";
                }
                param1 = param1 + separator + s;
                param2 = param2 + separator + s2;
                separator = ", ";
            }
            param1 = param1 + ")";
            param2 = param2 + ")";
            fullInfo = this.infoMap.getFirst(fullname = fullname + param1, false);
            info = fullInfo;
            if (info == null) {
                String cppName = dcl.cppName;
                if (!cppName.endsWith(">")) {
                    cppName = cppName + templateArgs;
                }
                if ((info = this.infoMap.getFirst(cppName + param2, false)) == null && !cppName.equals(dcl.cppName) && (info = this.infoMap.getFirst(dcl.cppName + param1, false)) == null) {
                    info = this.infoMap.getFirst(dcl.cppName + param2, false);
                }
            }
        }
        if (info == null) {
            String name;
            if (type.constructor && (info = (fullInfo = this.infoMap.getFirst(fullname = (name = Parser.constructorName(dcl.cppName)) + templateArgs + param1))) == null && (info = (fullInfo = this.infoMap.getFirst(name + templateArgs + param2))) == null) {
                info = fullInfo = this.infoMap.getFirst(name + templateArgs);
            }
            if (info == null) {
                info = this.infoMap.getFirst(dcl.cppName + (dcl.cppName.endsWith(">") ? "" : templateArgs));
            }
            if (!(type.constructor || type.destructor || type.operator || context.templateMap != null && !context.templateMap.full())) {
                this.infoMap.put(info != null ? new Info(info).cppNames(fullname).javaNames(null) : new Info(fullname));
            }
        }
        if ((localName = dcl.cppName).startsWith(context.namespace + "::")) {
            localName = dcl.cppName.substring(context.namespace.length() + 2);
        }
        boolean localNamespace = Templates.splitNamespace(localName).size() > 1;
        Info info2 = this.infoMap.getFirst(null);
        boolean bl2 = info != null ? info.friendly : (friendly = info2 != null ? info2.friendly : false);
        if (type.friend && !friendly || this.tokens.get().match("&&") || context.javaName == null && localNamespace || info != null && info.skip) {
            while (!this.tokens.get().match(Character.valueOf(':'), Character.valueOf('{'), Character.valueOf(';'), Token.EOF)) {
                this.tokens.next();
            }
            Token token = this.tokens.get();
            while (!token.match(Token.EOF) && this.attribute() != null) {
                token = this.tokens.get();
            }
            if (this.tokens.get().match(Character.valueOf(':'))) {
                int count = 0;
                Token token3 = this.tokens.next();
                while (!token3.match(Token.EOF)) {
                    if (token3.match(Character.valueOf('('))) {
                        ++count;
                    } else if (token3.match(Character.valueOf(')'))) {
                        --count;
                    }
                    if (count == 0 && !token3.match(5, Character.valueOf(','), Character.valueOf('>')) && this.tokens.get(1).match(Character.valueOf('{'))) {
                        this.tokens.next();
                        break;
                    }
                    if (count == 0 && token3.match(Character.valueOf(';'))) break;
                    token3 = this.tokens.next();
                }
            }
            if (this.tokens.get().match("->")) {
                this.tokens.next();
                type = this.type(context);
            }
            if (this.tokens.get().match(Character.valueOf('{'))) {
                this.body();
            } else {
                while (!this.tokens.get().match(Character.valueOf(';'), Token.EOF)) {
                    this.tokens.next();
                }
            }
            decl.text = spacing;
            decl.function = true;
            declList.add(decl);
            return true;
        }
        if (type.friend) {
            modifiers = "private static native @Namespace ";
        } else if (type.staticMember || context.javaName == null) {
            modifiers = "public " + (info != null && info.objectify || context.objectify ? "" : "static ") + "native ";
            if (this.tokens.isCFile) {
                modifiers = "@NoException " + modifiers;
            }
        }
        type = this.functionAfter(context, decl, dcl, type);
        context = new Context(context);
        context.virtualize = context.virtualize && type.virtual || info != null && info.virtualize && !type.constructor;
        ArrayList<Declarator> prevDcl = new ArrayList<Declarator>();
        boolean first = true;
        for (int n = -2; n < Integer.MAX_VALUE; ++n) {
            boolean needUpcast;
            int foundThis;
            boolean useDefaults;
            decl = new Declaration();
            Declaration extraDecl = null;
            this.tokens.index = startIndex;
            boolean bl3 = useDefaults = (info == null || !info.skipDefaults) && n % 2 != 0;
            if ((type.constructor || type.destructor || type.operator) && params != null) {
                type = this.type(context);
                params = this.parameters(context, n / 2, useDefaults);
                dcl = new Declarator();
                dcl.type = type;
                dcl.parameters = params;
                dcl.cppName = type.cppName;
                dcl.javaName = Parser.removeAnnotations(type.javaName);
                if (type.operator) {
                    dcl.cppName = "operator " + (dcl.type.constValue ? "const " : "") + dcl.type.cppName + (dcl.type.indirections > 0 ? "*" : (dcl.type.reference ? "&" : ""));
                    dcl.javaName = Parser.upcastMethodName(dcl.javaName);
                }
                dcl.signature = dcl.javaName + params.signature;
                Token token = this.tokens.get();
                while (!token.match(Token.EOF)) {
                    Attribute attr = this.attribute();
                    if (attr != null && attr.annotation) {
                        dcl.type.annotations = dcl.type.annotations + attr.javaName;
                    } else if (attr == null) break;
                    token = this.tokens.get();
                }
                if (this.tokens.get().match(Character.valueOf(':'))) {
                    int count = 0;
                    Token token4 = this.tokens.next();
                    while (!token4.match(Token.EOF)) {
                        if (token4.match(Character.valueOf('('))) {
                            ++count;
                        } else if (token4.match(Character.valueOf(')'))) {
                            --count;
                        }
                        if (count == 0 && !token4.match(5, Character.valueOf(','), Character.valueOf('>')) && this.tokens.get(1).match(Character.valueOf('{'))) {
                            this.tokens.next();
                        } else if (count != 0 || !token4.match(Character.valueOf(';'))) {
                            token4 = this.tokens.next();
                            continue;
                        }
                        break;
                    }
                }
            } else {
                dcl = this.declarator(context, null, n / 2, (info == null || !info.skipDefaults) && n % 2 != 0, 0, false, false);
                type = dcl.type;
                boolean bl4 = isQualified = Templates.splitNamespace(dcl.cppName).size() > 1;
                if (context.namespace != null && !isQualified) {
                    dcl.cppName = context.namespace + "::" + dcl.cppName;
                }
                for (String string : context.qualify(dcl.cppName, param1)) {
                    if (this.infoMap.getFirst(string, false) != null) {
                        dcl.cppName = string;
                        break;
                    }
                    if (this.infoMap.getFirst(string) == null) continue;
                    dcl.cppName = string;
                }
            }
            String localName2 = dcl.cppName;
            if (context.namespace != null && localName2.startsWith(context.namespace + "::")) {
                localName2 = dcl.cppName.substring(context.namespace.length() + 2);
            }
            if (localName2.endsWith(param1)) {
                localName2 = localName2.substring(0, localName2.length() - param1.length());
            }
            if (fullInfo != null && fullInfo.javaNames != null && fullInfo.javaNames.length > 0) {
                dcl.javaName = fullInfo.javaNames[0];
                dcl.signature = dcl.javaName + dcl.parameters.signature;
                String simpleName = Templates.strip(localName2);
                if (!(localName2.equals(dcl.javaName) || simpleName.contains("::") && context.javaName != null)) {
                    type.annotations = type.annotations.replaceAll("@Name\\(.*\\) ", "");
                    type.annotations = type.annotations + "@Name(\"" + localName2 + "\") ";
                }
            }
            type = this.functionAfter(context, decl, dcl, type);
            if (this.tokens.get().match(Character.valueOf('{'))) {
                this.body();
            } else {
                if (this.tokens.get().match(Character.valueOf('='))) {
                    Token token = this.tokens.next().expect("0", Token.DELETE, Token.DEFAULT);
                    if (token.match("0")) {
                        decl.abstractMember = true;
                    } else if (token.match(Token.DELETE)) {
                        decl.text = spacing;
                        declList.add(decl);
                        return true;
                    }
                    this.tokens.next().expect(Character.valueOf(';'));
                }
                this.tokens.next();
            }
            if (type.friend && friendly) {
                void var30_50;
                Declarator[] paramDeclarators = dcl.parameters.declarators;
                String signature = dcl.javaName;
                String string = "";
                String staticArgList = "";
                foundThis = 0;
                for (Declarator paramDecl : paramDeclarators) {
                    void var30_52;
                    if (staticArgList.length() > 0) {
                        staticArgList = staticArgList + ", ";
                    }
                    if (foundThis == 0 && paramDecl.type.cppName.equals(context.cppName)) {
                        foundThis = 1;
                        staticArgList = staticArgList + "this";
                        continue;
                    }
                    if (var30_50.length() > 0) {
                        String string2 = (String)var30_50 + ", ";
                    }
                    String string3 = (String)var30_52 + paramDecl.type.javaName + " " + paramDecl.javaName;
                    signature = signature + '_' + paramDecl.type.signature();
                    staticArgList = staticArgList + paramDecl.javaName;
                }
                if (foundThis != 0) {
                    extraDecl = new Declaration();
                    extraDecl.signature = signature;
                    extraDecl.declarator = dcl;
                    extraDecl.text = "public " + Parser.desugarVarargs(dcl.type.javaName) + " " + dcl.javaName + "(" + (String)var30_50 + ") { " + (dcl.type.javaName.equals("void") ? "" : "return ") + dcl.javaName + "(" + staticArgList + "); }\n";
                } else {
                    friendly = false;
                }
            }
            if (type.friend && !friendly || !decl.constMember && context.constName != null) {
                decl.text = spacing;
                declList.add(decl);
                return true;
            }
            if (decl.constMember && context.virtualize) {
                type.annotations = type.annotations.contains("@Const") ? Parser.incorporateConstAnnotation(type.annotations, 2, true) : type.annotations + "@Const({false, false, true}) ";
            }
            boolean staticMethod = type.staticMember || type.friend || context.javaName == null;
            boolean bl5 = needUpcast = !type.constructor && !staticMethod && context.upcast;
            if (!type.constructor) {
                Declarator[] declaratorArray = dcl.parameters.declarators;
                int staticArgList = declaratorArray.length;
                for (foundThis = 0; foundThis < staticArgList; ++foundThis) {
                    Declarator paramDecl = declaratorArray[foundThis];
                    if (paramDecl == null) continue;
                    needUpcast |= this.upcasts.contains(paramDecl.type.javaName);
                }
            }
            if (context.virtualize && !type.annotations.contains("@Virtual")) {
                modifiers = "@Virtual";
                modifiers = needUpcast ? modifiers + (decl.abstractMember ? "(value = true, " : "(") + "method = \"" + dcl.javaName + "\") " : modifiers + (decl.abstractMember ? "(true) " : " ");
                modifiers = modifiers + (context.inaccessible ? "protected native " : "public native ");
            }
            decl.declarator = dcl;
            if (context.namespace != null && context.javaName == null) {
                decl.text = decl.text + "@Namespace(\"" + context.namespace + "\") ";
            }
            if (fullInfo != null && fullInfo.annotations != null) {
                String[] stringArray = fullInfo.annotations;
                int staticArgList = stringArray.length;
                for (foundThis = 0; foundThis < staticArgList; ++foundThis) {
                    String s = stringArray[foundThis];
                    if (type.annotations.contains(s)) continue;
                    type.annotations = type.annotations + s + " ";
                }
            }
            if (type.constructor && params != null) {
                decl.text = decl.text + Parser.filterJavaAnnotations(type.annotations) + "public " + context.shorten(context.javaName) + dcl.parameters.list + " { super((Pointer)null); allocate" + params.names + "; }\n" + type.annotations + "private native void allocate" + dcl.parameters.list + ";\n";
            } else {
                void var30_61;
                String string = modifiers;
                if (needUpcast) {
                    if (!type.annotations.contains("@Name")) {
                        type.annotations = type.annotations + "@Name(\"" + localName2 + "\") ";
                    }
                    Pattern pattern = Pattern.compile("\\b(private|protected|public)\\b");
                    Matcher matcher = pattern.matcher(string);
                    String string4 = matcher.replaceFirst("private");
                    String accessModifier = matcher.group(1);
                    StringBuilder sb = new StringBuilder();
                    for (Declarator param : dcl.parameters.declarators) {
                        if (sb.length() > 0) {
                            sb.append(", ");
                        }
                        sb.append(Parser.removeAnnotations(param.type.javaName)).append(" ").append(param.javaName);
                    }
                    decl.text = decl.text + accessModifier + " " + (staticMethod ? "static " : "") + Parser.desugarVarargs(Parser.removeAnnotations(type.javaName)) + " " + dcl.javaName + "(" + sb + ") { " + (type.javaName.equals("void") ? "" : "return ") + (context.upcast && !staticMethod ? Parser.upcastMethodName(context.javaName) + "()." : "") + "_" + dcl.javaName + (dcl.parameters.names == null ? "()" : dcl.parameters.names) + "; }\n";
                    dcl.javaName = "_" + dcl.javaName;
                }
                decl.text = decl.text + (String)var30_61 + type.annotations + context.shorten(Parser.desugarVarargs(type.javaName)) + " " + dcl.javaName + dcl.parameters.list + ";\n";
            }
            decl.signature = dcl.signature;
            if (useDefaults) {
                decl.text = decl.text.replaceAll("@Override ", "");
            }
            if (info != null && info.javaText != null) {
                if (!first) break;
                decl.signature = decl.text = info.javaText;
                decl.custom = !info.define;
            }
            String string = this.commentAfter();
            if (first) {
                declList.spacing = spacing;
                decl.text = string + decl.text;
            }
            decl.function = true;
            boolean found = false;
            for (Declarator d : prevDcl) {
                found |= dcl.signature.equals(d.signature);
            }
            if (dcl.javaName.length() > 0 && !found && (!type.destructor || info != null && info.javaText != null)) {
                if (declList.add(decl, fullname)) {
                    first = false;
                    if (extraDecl != null) {
                        declList.add(extraDecl);
                    }
                }
                if (context.virtualize) {
                    break;
                }
            } else if (found && n / 2 > 0 && n % 2 == 0 && n / 2 > Math.max(dcl.infoNumber, dcl.parameters.infoNumber)) break;
            prevDcl.add(dcl);
        }
        declList.spacing = null;
        return true;
    }

    Type functionAfter(Context context, Declaration decl, Declarator dcl, Type type) throws ParserException {
        Token token = this.tokens.get();
        while (!token.match(Token.EOF)) {
            Attribute attr;
            if (token.match(Token.CONST, Token.__CONST, Token.CONSTEXPR)) {
                decl.constMember = true;
                token = this.tokens.next();
            } else if (token.match(Token.OVERRIDE)) {
                type.virtual = true;
            }
            if (token.match(Character.valueOf('&'), "&&") || token.match(Token.VOLATILE)) {
                token = this.tokens.next();
            }
            if ((attr = this.attribute()) != null && attr.annotation) {
                dcl.type.annotations = dcl.type.annotations + attr.javaName;
            } else if (attr == null) break;
            token = this.tokens.get();
        }
        if (this.tokens.get().match("->")) {
            this.tokens.next();
            type = this.type(context);
        }
        return type;
    }

    boolean variable(Context context, DeclarationList declList) throws ParserException {
        Info info2;
        int backIndex = this.tokens.index;
        String spacing = this.tokens.get().spacing;
        String modifiers = "public static native ";
        String setterType = "void ";
        Declarator dcl = this.declarator(context, null, -1, false, 0, false, true);
        Declaration decl = new Declaration();
        String cppName = dcl.cppName;
        String javaName = dcl.javaName;
        Attribute attr = this.attribute();
        if (attr != null && attr.annotation) {
            dcl.type.annotations = dcl.type.annotations + attr.javaName;
        }
        if (cppName == null || javaName == null || !this.tokens.get().match(Character.valueOf('('), Character.valueOf('['), Character.valueOf('='), Character.valueOf(','), Character.valueOf(':'), Character.valueOf(';'), Character.valueOf('{'))) {
            this.tokens.index = backIndex;
            return false;
        }
        if (!dcl.type.staticMember && context.javaName != null) {
            modifiers = "public native ";
            setterType = context.shorten(context.javaName) + " ";
        }
        int namespace = cppName.lastIndexOf("::");
        if (context.namespace != null && namespace < 0) {
            cppName = context.namespace + "::" + cppName;
        }
        Info info = this.infoMap.getFirst(cppName);
        Info info3 = info2 = context.variable != null ? this.infoMap.getFirst(context.variable.cppName) : null;
        if (dcl.cppName.length() == 0 || info != null && info.skip || info2 != null && info2.skip) {
            decl.text = spacing;
            declList.add(decl);
            int count = 0;
            Token token = this.tokens.get();
            while (!token.match(Token.EOF)) {
                if (token.match(Character.valueOf('{'))) {
                    ++count;
                } else if (token.match(Character.valueOf('}'))) {
                    --count;
                }
                if (count == 0 && token.match(Character.valueOf(';'))) break;
                token = this.tokens.next();
            }
            this.tokens.next();
            return true;
        }
        if (info == null) {
            info2 = this.infoMap.getFirst(dcl.cppName);
            this.infoMap.put(info2 != null ? new Info(info2).cppNames(cppName) : new Info(cppName));
        }
        boolean first = true;
        Declarator metadcl = context.variable;
        for (int n = 0; n < Integer.MAX_VALUE; ++n) {
            String indices;
            decl = new Declaration();
            this.tokens.index = backIndex;
            dcl = this.declarator(context, null, -1, false, n, false, true);
            if (dcl == null || dcl.cppName == null) break;
            decl.declarator = dcl;
            cppName = dcl.cppName;
            namespace = cppName.lastIndexOf("::");
            if (context.namespace != null && namespace < 0) {
                cppName = context.namespace + "::" + cppName;
            }
            if ((info = this.infoMap.getFirst(cppName)) != null && info.skip) continue;
            namespace = cppName.lastIndexOf("::");
            String shortName = cppName;
            if (namespace >= 0) {
                shortName = cppName.substring(namespace + 2);
            }
            javaName = dcl.javaName;
            if (metadcl == null || metadcl.indices == 0 || dcl.indices == 0) {
                String rawType;
                boolean hasSetter;
                indices = "";
                for (int i = 0; i < (metadcl == null || metadcl.indices == 0 ? dcl.indices : metadcl.indices); ++i) {
                    if (i > 0) {
                        indices = indices + ", ";
                    }
                    indices = indices + "int " + (char)(105 + i);
                }
                if (context.namespace != null && context.javaName == null) {
                    decl.text = decl.text + "@Namespace(\"" + context.namespace + "\") ";
                }
                String nameAnnotation = "";
                if (metadcl != null && metadcl.cppName != null && metadcl.cppName.length() > 0) {
                    nameAnnotation = metadcl.indices == 0 ? "@Name(\"" + metadcl.cppName + "." + metadcl.type.cppName + shortName + "\") " : "@Name({\"" + metadcl.cppName + "\", \"." + metadcl.type.cppName + shortName + "\"}) ";
                    javaName = metadcl.javaName + "_" + metadcl.type.javaName + shortName;
                }
                boolean beanify = context.beanify && indices.isEmpty();
                String capitalizedJavaName = null;
                if (beanify) {
                    if (nameAnnotation.length() == 0) {
                        nameAnnotation = "@Name(\"" + shortName + "\") ";
                    }
                    capitalizedJavaName = javaName.substring(0, 1).toUpperCase() + javaName.substring(1);
                    javaName = "get" + capitalizedJavaName;
                }
                if (nameAnnotation.length() > 0) {
                    dcl.type.annotations = dcl.type.annotations.replaceAll("@Name\\(.*\\) ", "");
                    decl.text = decl.text + nameAnnotation;
                }
                dcl.type.annotations = dcl.type.annotations.replace("@ByVal ", "@ByRef ");
                boolean bl = hasSetter = (!dcl.type.constValue || dcl.indirections != 0) && !dcl.constPointer && !dcl.type.constExpr && !context.immutable;
                if (!hasSetter || beanify) {
                    decl.text = decl.text + "@MemberGetter ";
                }
                decl.text = decl.text + modifiers + dcl.type.annotations + dcl.type.javaName + " " + javaName + "(" + indices + ");";
                if (hasSetter) {
                    if (indices.length() > 0) {
                        indices = indices + ", ";
                    }
                    decl.text = beanify ? decl.text + "\n" + nameAnnotation + "@MemberSetter " + modifiers + setterType + "set" + capitalizedJavaName + "(" + indices + dcl.type.annotations + dcl.type.javaName + " setter);" : decl.text + " " + modifiers + setterType + javaName + "(" + indices + Parser.removeAnnotations(dcl.type.javaName) + " setter);";
                }
                decl.text = decl.text + "\n";
                if ((dcl.type.constValue || dcl.constPointer || dcl.type.constExpr) && dcl.type.staticMember && indices.length() == 0 && ("byte".equals(rawType = Parser.removeAnnotations(dcl.type.javaName)) || "short".equals(rawType) || "int".equals(rawType) || "long".equals(rawType) || "float".equals(rawType) || "double".equals(rawType) || "char".equals(rawType) || "boolean".equals(rawType))) {
                    decl.text = decl.text + "public static final " + rawType + " " + javaName + " = " + javaName + "();\n";
                }
            }
            if (dcl.indices > 0) {
                boolean hasSetter;
                this.tokens.index = backIndex;
                dcl = this.declarator(context, null, -1, false, n, true, false);
                indices = "";
                for (int i = 0; i < (metadcl == null ? 0 : metadcl.indices); ++i) {
                    if (i > 0) {
                        indices = indices + ", ";
                    }
                    indices = indices + "int " + (char)(105 + i);
                }
                if (context.namespace != null && context.javaName == null) {
                    decl.text = decl.text + "@Namespace(\"" + context.namespace + "\") ";
                }
                if (metadcl != null && metadcl.cppName.length() > 0) {
                    decl.text = decl.text + (metadcl.indices == 0 ? "@Name(\"" + metadcl.cppName + "." + metadcl.type.cppName + shortName + "\") " : "@Name({\"" + metadcl.cppName + "\", \"." + metadcl.type.cppName + shortName + "\"}) ");
                    dcl.type.annotations = dcl.type.annotations.replaceAll("@Name\\(.*\\) ", "");
                    javaName = metadcl.javaName + "_" + metadcl.type.javaName + shortName;
                }
                this.tokens.index = backIndex;
                Declarator dcl2 = this.declarator(context, null, -1, false, n, false, false);
                boolean bl = hasSetter = !dcl.type.constValue && !dcl.constPointer && dcl2.indirections >= 2 && !dcl2.type.constExpr && !context.immutable;
                if (!hasSetter) {
                    decl.text = decl.text + "@MemberGetter ";
                }
                decl.text = decl.text + modifiers + dcl.type.annotations.replace("@ByVal ", "@ByRef ") + dcl.type.javaName + " " + javaName + "(" + indices + ");";
                if (hasSetter) {
                    if (indices.length() > 0) {
                        indices = indices + ", ";
                    }
                    decl.text = decl.text + " " + modifiers + setterType + javaName + "(" + indices + dcl.type.javaName + " setter);";
                }
                decl.text = decl.text + "\n";
            }
            decl.signature = dcl.signature + "_";
            if (info != null && info.javaText != null) {
                decl.signature = decl.text = info.javaText;
                decl.declarator = null;
                decl.custom = !info.define;
            }
            int count = 0;
            Token token = this.tokens.get();
            while (!token.match(Token.EOF)) {
                if (token.match(Character.valueOf('{'))) {
                    ++count;
                } else if (token.match(Character.valueOf('}'))) {
                    --count;
                }
                if (count == 0 && token.match(Character.valueOf(';'))) break;
                token = this.tokens.next();
            }
            this.tokens.next();
            String comment = this.commentAfter();
            if (first) {
                first = false;
                declList.spacing = spacing;
                decl.text = comment + decl.text;
            }
            decl.variable = true;
            declList.add(decl);
        }
        declList.spacing = null;
        return true;
    }

    boolean macro(Context context, DeclarationList declList) throws ParserException {
        String macroName;
        int backIndex = this.tokens.index;
        if (!this.tokens.get().match(Character.valueOf('#'))) {
            return false;
        }
        this.tokens.raw = true;
        String spacing = this.tokens.get().spacing;
        Token keyword = this.tokens.next();
        if (keyword.spacing.indexOf(10) >= 0) {
            if (declList != null) {
                Declaration decl = new Declaration();
                decl.text = spacing + "// #";
                declList.add(decl);
            }
            this.tokens.raw = false;
            return true;
        }
        this.tokens.next();
        int beginIndex = this.tokens.index;
        Token token = this.tokens.get();
        while (!token.match(Token.EOF) && token.spacing.indexOf(10) < 0) {
            token = this.tokens.next();
        }
        if (context == null) {
            this.tokens.raw = false;
            return true;
        }
        int endIndex = this.tokens.index;
        while (this.tokens.get(-1).match(4)) {
            --this.tokens.index;
        }
        int lastIndex = this.tokens.index;
        Declaration decl = new Declaration();
        if (keyword.match(Token.DEFINE) && beginIndex < endIndex) {
            Info info;
            this.tokens.index = beginIndex;
            macroName = this.tokens.get().value;
            Token first = this.tokens.next();
            boolean hasArgs = first.spacing.length() == 0 && first.match(Character.valueOf('('));
            List<Info> infoList = this.infoMap.get(macroName);
            Iterator iterator = (infoList.size() > 0 ? new ArrayList<Info>(infoList) : Arrays.asList(new Info[]{null})).iterator();
            while (iterator.hasNext() && ((info = (Info)iterator.next()) == null || !info.skip)) {
                if (info == null && (hasArgs || beginIndex + 1 == endIndex) || info != null && info.cppText == null && info.cppTypes != null && info.cppTypes.length == 0) {
                    info = new Info(macroName).cppText("");
                    this.tokens.index = backIndex;
                    Token token2 = this.tokens.get();
                    while (this.tokens.index < endIndex) {
                        info.cppText = info.cppText + (token2.match("\n") ? token2 : token2.spacing + token2);
                        token2 = this.tokens.next();
                    }
                    this.infoMap.put(info);
                    break;
                }
                if (info != null && info.cppText == null && info.cppTypes != null && info.cppTypes.length > (hasArgs ? 0 : 1)) {
                    ArrayList<Declarator> prevDcl = new ArrayList<Declarator>();
                    for (int n = -1; n < Integer.MAX_VALUE; ++n) {
                        int count = 1;
                        this.tokens.index = beginIndex + 2;
                        String params = "(";
                        Token token3 = this.tokens.get();
                        while (hasArgs && this.tokens.index < lastIndex && count < info.cppTypes.length) {
                            if (token3.match(5)) {
                                String type = info.cppTypes[count];
                                Object name = token3.value;
                                if (((String)name).equals("...")) {
                                    name = "arg" + count;
                                }
                                params = params + type + " " + (String)name;
                                if (++count < info.cppTypes.length) {
                                    params = params + ", ";
                                }
                            } else if (token3.match(Character.valueOf(')'))) break;
                            token3 = this.tokens.next();
                        }
                        while (count < info.cppTypes.length) {
                            String type = info.cppTypes[count];
                            String name = "arg" + count;
                            params = params + type + " " + name;
                            if (++count >= info.cppTypes.length) continue;
                            params = params + ", ";
                        }
                        params = params + ")";
                        Declarator dcl = new Parser(this, info.cppTypes[0] + " " + macroName + params).declarator(context, null, n, false, 0, false, false);
                        for (int i = 0; i < info.cppNames.length; ++i) {
                            if (!macroName.equals(info.cppNames[i]) || info.javaNames == null) continue;
                            macroName = "@Name(\"" + info.cppNames[0] + "\") " + info.javaNames[i];
                            break;
                        }
                        boolean found = false;
                        for (Declarator d : prevDcl) {
                            found |= dcl.signature.equals(d.signature);
                        }
                        if (!found) {
                            decl.text = decl.text + "public static native " + dcl.type.annotations + dcl.type.javaName + " " + macroName + dcl.parameters.list + ";\n";
                            decl.signature = dcl.signature;
                        } else if (found && n > 0) break;
                        prevDcl.add(dcl);
                    }
                } else if (lastIndex > beginIndex + 1 && (info == null || info.cppText == null && (info.cppTypes == null || info.cppTypes.length == 1))) {
                    int i;
                    String value = "";
                    String cppType = "int";
                    String type = "int";
                    String cat = "";
                    this.tokens.index = beginIndex + 1;
                    Token prevToken = new Token();
                    boolean translate = true;
                    Token token4 = this.tokens.get();
                    while (this.tokens.index < lastIndex) {
                        if (token4.match(3)) {
                            cppType = "const char*";
                            type = "String";
                            cat = " + ";
                            break;
                        }
                        if (token4.match(2)) {
                            cppType = "double";
                            type = "double";
                            cat = "";
                            break;
                        }
                        if (token4.match(1) && token4.value.endsWith("L")) {
                            cppType = "long long";
                            type = "long";
                            cat = "";
                            break;
                        }
                        if (prevToken.match(5, Character.valueOf('>')) && token4.match(5, Character.valueOf('(')) || token4.match(Character.valueOf('{'), Character.valueOf('}'))) {
                            translate = false;
                        } else if (token4.match(5)) {
                            Info info2 = this.infoMap.getFirst(token4.value);
                            if (info == null && info2 != null && info2.cppTypes != null) {
                                info = info2;
                            }
                        }
                        prevToken = token4;
                        token4 = this.tokens.next();
                    }
                    if (info != null) {
                        if (info.cppTypes != null && info.cppTypes.length > 0) {
                            Declarator dcl = new Parser(this, info.cppTypes[0]).declarator(context, null, -1, false, 0, false, true);
                            if (!dcl.type.javaName.equals("int")) {
                                cppType = dcl.type.cppName;
                                type = dcl.type.annotations + (info.pointerTypes != null ? info.pointerTypes[0] : dcl.type.javaName);
                            }
                        }
                        for (int i2 = 0; i2 < info.cppNames.length; ++i2) {
                            if (!macroName.equals(info.cppNames[i2]) || info.javaNames == null) continue;
                            macroName = "@Name(\"" + info.cppNames[0] + "\") " + info.javaNames[i2];
                            break;
                        }
                        translate = info.translate;
                    }
                    this.tokens.index = beginIndex + 1;
                    if (translate) {
                        token = this.tokens.get();
                        while (this.tokens.index < lastIndex) {
                            value = value + token.spacing;
                            if (!type.equals("String") || !token.match("L")) {
                                value = value + token + (this.tokens.index + 1 < lastIndex && token.value.trim().length() > 0 ? cat : "");
                            }
                            token = this.tokens.next();
                        }
                        value = this.translate(value);
                        if (type.equals("int")) {
                            if (value.contains("(String)")) {
                                cppType = "const char*";
                                type = "String";
                            } else if (value.contains("(float)") || value.contains("(double)")) {
                                cppType = "double";
                                type = "double";
                            } else if (value.contains("(long)")) {
                                cppType = "long long";
                                type = "long";
                            } else {
                                try {
                                    String trimmedValue = value.trim();
                                    long longValue = Long.parseLong(trimmedValue);
                                    if (longValue > Integer.MAX_VALUE && longValue >>> 32 == 0L) {
                                        value = value.substring(0, value.length() - trimmedValue.length()) + "(int)" + trimmedValue + "L";
                                    } else if (longValue > Integer.MAX_VALUE || longValue < Integer.MIN_VALUE) {
                                        cppType = "long long";
                                        type = "long";
                                        value = value + "L";
                                    }
                                }
                                catch (NumberFormatException trimmedValue) {}
                            }
                        }
                    } else {
                        if (info != null && info.annotations != null) {
                            for (String s : info.annotations) {
                                decl.text = decl.text + s + " ";
                            }
                        }
                        decl.text = decl.text + "public static native @MemberGetter " + type + " " + macroName + "();\n";
                        value = " " + macroName + "()";
                    }
                    if ((i = type.lastIndexOf(32)) >= 0) {
                        type = type.substring(i + 1);
                    }
                    if (value.length() > 0) {
                        decl.text = decl.text + "public static final " + type + " " + macroName + " =" + value + ";\n";
                    }
                    decl.signature = macroName;
                    if (info == null || !Arrays.asList(info.cppNames).contains(macroName)) {
                        this.infoMap.put(new Info(macroName).define(true).cppTypes(cppType).pointerTypes(type).translate(translate));
                    }
                }
                if (info == null || info.javaText == null) continue;
                decl.signature = decl.text = info.javaText;
                decl.custom = !info.define;
                break;
            }
        } else if (keyword.match(Token.UNDEF)) {
            this.tokens.index = beginIndex;
            macroName = this.tokens.get().value;
            List<Info> infoList = this.infoMap.get(macroName);
            for (Info info : infoList) {
                if (info != null && info.skip) break;
                if (info == null || info.cppText == null || info.cppTypes != null || info.define) continue;
                infoList.remove(info);
                break;
            }
        }
        if (decl.text.length() == 0) {
            this.tokens.index = beginIndex;
            int n = spacing.lastIndexOf(10) + 1;
            decl.text = decl.text + "// " + spacing.substring(n) + "#" + keyword.spacing + keyword;
            Token token5 = this.tokens.get();
            while (this.tokens.index < lastIndex) {
                decl.text = decl.text + (token5.match("\n") ? "\n// " : token5.spacing + token5.toString().replace("\n", "\n//"));
                token5 = this.tokens.next();
            }
            spacing = spacing.substring(0, n);
        }
        if (decl.text.length() > 0) {
            this.tokens.index = lastIndex;
            String comment = this.commentAfter();
            decl.text = comment + decl.text;
        }
        this.tokens.raw = false;
        if (declList != null) {
            declList.spacing = spacing;
            declList.add(decl);
            declList.spacing = null;
        }
        return true;
    }

    boolean typedef(Context context, DeclarationList declList) throws ParserException {
        String usingDefName;
        String spacing = this.tokens.get().spacing;
        String string = usingDefName = this.tokens.get().match(Token.USING) && this.tokens.get(1).match(5) && this.tokens.get(2).match(Character.valueOf('=')) ? this.tokens.get((int)1).value : null;
        if (!this.tokens.get().match(Token.TYPEDEF) && !this.tokens.get(1).match(Token.TYPEDEF) && usingDefName == null) {
            return false;
        }
        int backIndex = this.tokens.index;
        for (int n = 0; n < Integer.MAX_VALUE; ++n) {
            Declaration decl = new Declaration();
            this.tokens.index = backIndex;
            Declarator dcl = this.declarator(context, usingDefName, -1, false, n, true, false);
            if (dcl == null) break;
            if (usingDefName != null) {
                dcl.cppName = usingDefName;
            }
            if (this.attribute() == null) {
                this.tokens.next();
            }
            String typeName = dcl.type.cppName;
            String defName = dcl.cppName;
            if (defName == null) {
                dcl.cppName = defName = typeName;
            }
            if (dcl.javaName == null) {
                dcl.javaName = dcl.cppName;
            }
            int namespace = defName.lastIndexOf("::");
            if (context.namespace != null && namespace < 0) {
                defName = context.namespace + "::" + defName;
            }
            Info info = this.infoMap.getFirst(defName);
            if (dcl.definition != null) {
                decl = dcl.definition;
                if (usingDefName != null) {
                    decl.text = decl.text.replace(decl.signature, usingDefName);
                    decl.type.javaName = decl.type.cppName = usingDefName;
                    decl.signature = decl.type.cppName;
                }
                if (dcl.javaName.length() > 0 && context.javaName != null) {
                    dcl.javaName = context.javaName + "." + dcl.javaName;
                }
                if (info == null || !info.skip) {
                    info = info != null ? new Info(info).cppNames(defName) : new Info(defName);
                    this.infoMap.put(info.valueTypes(dcl.javaName).pointerTypes((dcl.indirections > 0 ? "@ByPtrPtr " : "") + dcl.javaName));
                }
            } else if (typeName.equals("void")) {
                if (!(info != null && info.skip || dcl.javaName.equals("Pointer"))) {
                    if (dcl.indirections > 0) {
                        decl.text = decl.text + "@Namespace @Name(\"void\") ";
                        info = info != null ? new Info(info).cppNames(defName) : new Info(defName);
                        this.infoMap.put(info.valueTypes(dcl.javaName).pointerTypes("@ByPtrPtr " + dcl.javaName));
                    } else if (context.namespace != null && context.javaName == null) {
                        decl.text = decl.text + "@Namespace(\"" + context.namespace + "\") ";
                    }
                    decl.type = new Type(dcl.javaName);
                    decl.text = decl.text + "@Opaque public static class " + dcl.javaName + " extends Pointer {\n    /** Empty constructor. Calls {@code super((Pointer)null)}. */\n    public " + dcl.javaName + "() { super((Pointer)null); }\n    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */\n    public " + dcl.javaName + "(Pointer p) { super(p); }\n}";
                }
            } else {
                info = this.infoMap.getFirst(typeName);
                if (info == null || !info.skip) {
                    Info info2 = info = info != null ? new Info(info).cppNames(defName) : new Info(defName);
                    if (info.cppTypes == null && info.annotations != null) {
                        String s = typeName;
                        if (dcl.type.constValue && !s.startsWith("const ")) {
                            s = "const " + s;
                        }
                        if (dcl.type.indirections > 0) {
                            for (int i = 0; i < dcl.type.indirections; ++i) {
                                s = s + "*";
                            }
                        }
                        if (dcl.type.reference) {
                            s = s + "&";
                        }
                        if (dcl.type.rvalue) {
                            s = s + "&&";
                        }
                        if (dcl.type.constPointer && !s.endsWith(" const")) {
                            s = s + " const";
                        }
                        info.cppNames(defName, s).cppTypes(s);
                    }
                    if (info.valueTypes == null && dcl.indirections > 0) {
                        String[] stringArray;
                        if (info.pointerTypes != null) {
                            stringArray = info.pointerTypes;
                        } else {
                            String[] stringArray2 = new String[1];
                            stringArray = stringArray2;
                            stringArray2[0] = typeName;
                        }
                        info.valueTypes(stringArray);
                        info.pointerTypes("PointerPointer");
                    } else if (info.pointerTypes == null) {
                        info.pointerTypes(dcl.type.javaName);
                    }
                    if (info.annotations == null) {
                        if (!(dcl.type.annotations == null || dcl.type.annotations.length() <= 0 || dcl.type.annotations.startsWith("@ByVal ") || dcl.type.annotations.startsWith("@Cast(") || dcl.type.annotations.startsWith("@Const "))) {
                            info.annotations(dcl.type.annotations.trim());
                        } else {
                            info.cast(!dcl.cppName.equals(info.pointerTypes[0]) && !info.pointerTypes[0].contains("@Cast"));
                        }
                    }
                    this.infoMap.put(info);
                }
            }
            if (info != null && info.javaText != null) {
                decl.signature = decl.text = info.javaText;
                decl.custom = !info.define;
            }
            String comment = this.commentAfter();
            decl.text = comment + decl.text;
            declList.spacing = spacing;
            declList.add(decl);
            declList.spacing = null;
        }
        return true;
    }

    boolean using(Context context, DeclarationList declList) throws ParserException {
        if (!this.tokens.get().match(Token.USING)) {
            return false;
        }
        String spacing = this.tokens.get().spacing;
        boolean namespace = this.tokens.get(1).match(Token.NAMESPACE);
        Declarator dcl = this.declarator(context, null, -1, false, 0, true, false);
        this.tokens.next();
        context.usingList.add(dcl.type.cppName + (namespace ? "::" : ""));
        Declaration decl = new Declaration();
        if (dcl.definition != null) {
            decl = dcl.definition;
        }
        String cppName = dcl.type.cppName;
        String baseType = context.baseType;
        if (baseType != null) {
            String baseTypeStripped = Templates.strip(baseType);
            if (Templates.notExists(cppName) && cppName.startsWith(baseTypeStripped)) {
                cppName = baseType + cppName.substring(baseTypeStripped.length());
            }
        }
        Info info = this.infoMap.getFirst(cppName);
        if (!context.inaccessible && info != null && info.javaText != null) {
            decl.signature = decl.text = info.javaText;
            decl.declarator = dcl;
            decl.custom = !info.define;
        }
        String comment = this.commentAfter();
        decl.text = comment + decl.text;
        declList.spacing = spacing;
        declList.add(decl);
        declList.spacing = null;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    String downcast(Type derived, Type base, boolean virtual) {
        String downcastType;
        if (virtual) {
            if (!this.polymorphicClasses.contains(base.javaName)) return "";
            downcastType = "dynamic";
        } else {
            downcastType = "static";
        }
        String annotations = "";
        Info constructorInfo = this.infoMap.getFirst(Parser.constructorName(base.cppName));
        if (constructorInfo != null && constructorInfo.annotations != null) {
            for (String s : constructorInfo.annotations) {
                if (s.startsWith("@Name")) continue;
                annotations = annotations + s + " ";
            }
        }
        String shortName = derived.javaName.substring(derived.javaName.lastIndexOf(46) + 1);
        String res = "    /** Downcast constructor. */\n    " + Parser.filterJavaAnnotations(annotations) + "public " + shortName + "(" + base.javaName + " pointer) { super((Pointer)null); allocate(pointer); }\n";
        if (!annotations.isEmpty()) return res + "    @Namespace private native " + annotations + "@Name(\"SHARED_PTR_NAMESPACE::" + downcastType + "_pointer_cast<" + derived.cppName + ", " + base.cppName + ">\") void allocate(" + annotations + base.javaName + " pointer);\n";
        return res + "    @Namespace private native @Name(\"" + downcastType + "_cast<" + derived.cppName + "*>\") void allocate(" + base.javaName + " pointer);\n";
    }

    String upcast(Type derived, Type base, boolean override) {
        String ecmn = Parser.upcastMethodName(base.javaName);
        String res = "    " + (override ? "@Override " : "") + "public " + base.javaName + " " + ecmn + "() { return " + ecmn + "(this); }\n    @Namespace public static native ";
        String annotations = "";
        Info constructorInfo = this.infoMap.getFirst(Parser.constructorName(base.cppName));
        if (constructorInfo != null && constructorInfo.annotations != null) {
            for (String s : constructorInfo.annotations) {
                if (s.startsWith("@Name")) continue;
                annotations = annotations + s + " ";
            }
        }
        res = !annotations.isEmpty() ? res + annotations + "@Name(\"SHARED_PTR_NAMESPACE::static_pointer_cast<" + base.cppName + ", " + derived.cppName + ">\") " : res + "@Name(\"static_cast<" + base.cppName + "*>\") ";
        res = res + base.javaName + " " + ecmn + "(" + annotations + derived.javaName + " pointer);\n";
        return res;
    }

    /*
     * WARNING - void declaration
     */
    boolean group(Context context, DeclarationList declList) throws ParserException {
        void var34_54;
        Info info;
        boolean isQualified;
        int backIndex = this.tokens.index;
        String spacing = this.tokens.get().spacing;
        boolean typedef = this.tokens.get().match(Token.TYPEDEF) || this.tokens.get(1).match(Token.TYPEDEF);
        boolean foundGroup = false;
        boolean friend = false;
        Context ctx = new Context(context);
        Token[] prefixes = new Token[]{Token.CLASS, Token.INTERFACE, Token.__INTERFACE, Token.STRUCT, Token.UNION};
        Token token = this.tokens.get();
        while (!token.match(Token.EOF)) {
            if (token.match(prefixes)) {
                foundGroup = true;
                ctx.inaccessible = token.match(Token.CLASS);
                break;
            }
            if (token.match(Token.FRIEND)) {
                friend = true;
                if (!this.tokens.get(1).match(prefixes)) {
                    foundGroup = true;
                    break;
                }
            } else if (!token.match(5)) break;
            token = this.tokens.next();
        }
        if (!foundGroup || !this.tokens.next().match(5, Character.valueOf('{'), "::")) {
            this.tokens.index = backIndex;
            return false;
        }
        if (!(this.tokens.get().match(Character.valueOf('{')) || !this.tokens.get(1).match(5) || this.tokens.get(1).match(Token.FINAL) || friend || !typedef && this.tokens.get(2).match(Character.valueOf(';')))) {
            this.tokens.next();
        }
        Type type = this.type(context, true);
        ArrayList<Type> baseClasses = new ArrayList<Type>();
        Declaration decl = new Declaration();
        decl.text = type.annotations;
        String name = type.javaName;
        boolean anonymous = !typedef && type.cppName.length() == 0;
        boolean derivedClass = false;
        boolean skipBase = false;
        if (type.cppName.length() > 0 && this.tokens.get().match(Character.valueOf(':'))) {
            derivedClass = true;
            boolean virtualInheritance = false;
            boolean accessible = !ctx.inaccessible;
            Token token2 = this.tokens.next();
            while (!token2.match(Token.EOF)) {
                if (token2.match(Token.VIRTUAL)) {
                    virtualInheritance = true;
                } else if (token2.match(Token.PRIVATE, Token.PROTECTED, Token.PUBLIC)) {
                    accessible = token2.match(Token.PUBLIC);
                } else {
                    Type t = this.type(context);
                    t.virtual = virtualInheritance;
                    Info info2 = this.infoMap.getFirst(t.cppName);
                    if (info2 != null && info2.skip) {
                        skipBase = true;
                    }
                    if (accessible) {
                        baseClasses.add(t);
                    }
                    if (this.tokens.get().expect(Character.valueOf(','), Character.valueOf('{')).match(Character.valueOf('{'))) break;
                    virtualInheritance = false;
                    accessible = !ctx.inaccessible;
                }
                token2 = this.tokens.next();
            }
        }
        if (typedef && this.tokens.get().match(Character.valueOf('('))) {
            this.tokens.index = backIndex;
            return false;
        }
        if (typedef && type.indirections > 0) {
            while (!this.tokens.get().match(Character.valueOf(';'), Token.EOF)) {
                this.tokens.next();
            }
        }
        if (!this.tokens.get().match(Character.valueOf('{'), Character.valueOf(','), Character.valueOf(';'))) {
            this.tokens.index = backIndex;
            return false;
        }
        int startIndex = this.tokens.index;
        ArrayList<Object> variables = new ArrayList<Object>();
        String originalName = type.cppName;
        String body = this.body();
        boolean hasBody = body != null && body.length() > 0;
        Attribute attr = this.attribute(true);
        while (attr != null && attr.annotation) {
            type.annotations = type.annotations + attr.javaName;
            attr = this.attribute(true);
        }
        if (!this.tokens.get().match(Character.valueOf(';'))) {
            if (typedef) {
                Token token3 = this.tokens.get();
                while (!token3.match(Character.valueOf(';'), Token.EOF)) {
                    int indirections = 0;
                    while (token3.match(Character.valueOf('*'))) {
                        ++indirections;
                        token3 = this.tokens.next();
                    }
                    if (token3.match(5)) {
                        String name2 = token3.value;
                        if (indirections > 0) {
                            this.infoMap.put(new Info(name2).cast().valueTypes(name).pointerTypes("PointerPointer"));
                        } else {
                            if (type.cppName.equals(originalName)) {
                                type.javaName = type.cppName = token3.value;
                                name = type.cppName;
                                Info info3 = this.infoMap.getFirst(name);
                                if (info3 != null && info3.annotations != null) {
                                    for (String s : info3.annotations) {
                                        decl.text = decl.text + s + " ";
                                    }
                                }
                            }
                            if (!name2.equals(name)) {
                                this.infoMap.put(new Info(name2).cast().pointerTypes(name));
                            }
                        }
                    }
                    token3 = this.tokens.next();
                }
                decl.text = decl.text + token3.spacing;
            } else {
                int n;
                int index;
                for (index = this.tokens.index - 1; index >= 0 && this.tokens.preprocess(index, 0) == this.tokens.index; --index) {
                }
                for (n = 0; n < Integer.MAX_VALUE; ++n) {
                    this.tokens.index = index;
                    Declarator dcl = this.declarator(context, null, -1, false, n, false, true);
                    if (dcl == null || dcl.cppName == null) break;
                    anonymous = true;
                    variables.add(dcl);
                }
                if ((n = spacing.lastIndexOf(10)) >= 0) {
                    decl.text = decl.text + spacing.substring(0, n);
                }
            }
        }
        boolean bl = isQualified = Templates.splitNamespace(type.cppName).size() > 1;
        if (context.namespace != null && !isQualified) {
            type.cppName = context.namespace + "::" + type.cppName;
            originalName = context.namespace + "::" + originalName;
        }
        if (((info = this.infoMap.getFirst(type.cppName)) == null || info.base == null) && skipBase || info != null && info.skip) {
            decl.text = "";
            declList.add(decl);
            return true;
        }
        if (info != null && info.pointerTypes != null && info.pointerTypes.length > 0) {
            type.javaName = context.constName != null ? context.constName : Parser.removeAnnotations(info.pointerTypes[0]);
            name = context.shorten(type.javaName);
        } else if (info == null && !friend) {
            if (type.javaName.length() > 0 && context.javaName != null) {
                type.javaName = context.javaName + "." + type.javaName;
            }
            info = new Info(Templates.strip(type.cppName)).pointerTypes(type.javaName);
            this.infoMap.put(info);
        }
        for (Type t : baseClasses) {
            Map<Type, Boolean> froms = this.downcasts.get(t.cppName);
            if (froms == null) continue;
            for (Map.Entry<Type, Boolean> from : froms.entrySet()) {
                this.addDowncast(type.cppName, from.getKey(), t.virtual || from.getValue() != false);
            }
        }
        Type base = new Type("Pointer");
        boolean polymorphic = false;
        Iterator it = baseClasses.iterator();
        while (it.hasNext()) {
            Type next = (Type)it.next();
            Info nextInfo = this.infoMap.getFirst(next.cppName);
            boolean nextPolymorphic = this.polymorphicClasses.contains(next.javaName);
            polymorphic |= nextPolymorphic;
            if (nextPolymorphic && next.virtual && !this.upcasts.contains(next.javaName)) {
                this.logger.warn(type.cppName + " virtually inherits from polymorphic class " + next.cppName + ". Consider adding an upcast Info on " + next.cppName + ".");
            }
            if (nextInfo != null && nextInfo.flatten) continue;
            base = next;
            it.remove();
            break;
        }
        String casts = "";
        if (!baseClasses.isEmpty()) {
            for (Type t : baseClasses) {
                Info baseInfo = this.infoMap.getFirst(t.cppName);
                if (t.javaName.equals("Pointer") || baseInfo != null && baseInfo.skip) continue;
                casts = casts + this.upcast(type, t, false);
                this.addDowncast(t.cppName, t, false);
            }
        }
        if (this.upcasts.contains(type.javaName)) {
            casts = casts + "    public " + type.javaName + " " + Parser.upcastMethodName(type.javaName) + "() { return this; }\n";
        }
        if (this.upcasts.contains(base.javaName)) {
            casts = casts + this.upcast(type, base, true);
            this.addDowncast(base.cppName, base, false);
        } else if (this.polymorphicClasses.contains(base.javaName) && base.virtual) {
            casts = casts + this.upcast(type, base, false);
            this.addDowncast(base.cppName, base, false);
        }
        decl.signature = type.javaName;
        this.tokens.index = startIndex;
        String shortName = name.substring(name.lastIndexOf(46) + 1);
        String fullName = context.namespace != null ? context.namespace + "::" + name : name;
        String cppName = type.cppName;
        Token[] tokenArray = prefixes;
        int n = tokenArray.length;
        boolean bl2 = false;
        while (var34_54 < n) {
            Token prefix = tokenArray[var34_54];
            if (info != null && info.cppNames[0].startsWith(prefix.value + " ")) {
                cppName = prefix.value + " " + cppName;
                break;
            }
            ++var34_54;
        }
        if (name.length() > 0 && !hasBody) {
            if (!this.tokens.get().match(Character.valueOf(';'))) {
                this.tokens.next();
                this.tokens.next();
            }
            this.tokens.next();
            if (friend) {
                decl.text = "";
                declList.add(decl);
                return true;
            }
            if (info != null && info.base != null) {
                String string = base.javaName = context.constName != null ? context.constBaseName : info.base;
            }
            if (name.equals("Pointer")) {
                return true;
            }
            if (!fullName.equals(cppName)) {
                decl.text = decl.text + "@Name(\"" + (context.javaName == null || context.namespace == null ? cppName : cppName.substring(context.namespace.length() + 2)) + "\") ";
            } else if (context.namespace != null && context.javaName == null) {
                decl.text = decl.text + "@Namespace(\"" + context.namespace + "\") ";
            }
            decl.text = decl.text + "@Opaque public static class " + name + " extends " + base.javaName + " {\n    /** Empty constructor. Calls {@code super((Pointer)null)}. */\n    public " + name + "() { super((Pointer)null); }\n    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */\n    public " + name + "(Pointer p) { super(p); }\n}";
            decl.type = type;
            decl.incomplete = true;
            String comment = this.commentAfter();
            decl.text = comment + decl.text;
            declList.spacing = spacing;
            declList.add(decl);
            declList.spacing = null;
            return true;
        }
        if (this.tokens.get().match(Character.valueOf('{'))) {
            this.tokens.next();
        }
        if (type.cppName.length() > 0) {
            ctx.namespace = type.cppName;
            ctx.cppName = originalName;
        }
        if (!anonymous) {
            ctx.javaName = type.javaName;
        }
        if (info != null) {
            if (info.virtualize) {
                ctx.virtualize = true;
            }
            if (info.immutable) {
                ctx.immutable = true;
            }
            if (info.beanify) {
                ctx.beanify = true;
            }
            ctx.upcast = info.upcast;
        }
        ctx.baseType = base.cppName;
        DeclarationList declList2 = new DeclarationList();
        if (variables.size() == 0) {
            this.declarations(ctx, declList2);
        } else {
            for (Declarator declarator : variables) {
                if (context.variable != null) {
                    if (context.variable.indices > 0 && declarator.indices == 0) {
                        declarator.indices = context.variable.indices;
                        declarator.type.cppName = declarator.cppName + ".";
                        declarator.type.javaName = declarator.javaName + "_";
                        declarator.cppName = context.variable.cppName;
                        declarator.javaName = context.variable.javaName;
                    } else {
                        declarator.cppName = context.variable.cppName + "." + declarator.cppName;
                        declarator.javaName = context.variable.javaName + "_" + declarator.javaName;
                    }
                }
                ctx.variable = declarator;
                this.declarations(ctx, declList2);
            }
        }
        String modifiers = "public static ";
        String string = "";
        String inheritedConstructors = "";
        boolean implicitConstructor = true;
        boolean arrayConstructor = false;
        boolean defaultConstructor = false;
        boolean longConstructor = false;
        boolean pointerConstructor = false;
        boolean abstractClass = info != null && info.purify && !ctx.virtualize;
        boolean allPureConst = true;
        boolean haveVariables = false;
        String constructorName = Parser.constructorName(originalName);
        Info constructorInfo = this.infoMap.getFirst(constructorName);
        for (String[] d : declList2) {
            polymorphic |= d.declarator != null && d.declarator.type != null && d.declarator.type.virtual;
            if (d.declarator != null && d.declarator.type != null && d.declarator.type.using && decl.text != null) {
                defaultConstructor |= d.text.contains("private native void allocate();");
                longConstructor |= d.text.contains("private native void allocate(long");
                pointerConstructor |= d.text.contains("private native void allocate(Pointer");
                implicitConstructor &= !d.text.contains("private native void allocate(");
                String baseType = d.declarator.type.cppName;
                List<String> baseTypeSplit = Templates.splitNamespace(baseType);
                baseType = baseType.substring(0, baseType.length() - baseTypeSplit.get(baseTypeSplit.size() - 1).length() - 2);
                String baseStripped = Templates.strip(base.cppName);
                if (Templates.notExists(baseType) && baseStripped.length() != base.cppName.length() && baseType.equals(baseStripped)) {
                    baseType = base.cppName;
                }
                List<Info> infoList = this.infoMap.get(baseType);
                String[] pointerTypes = null;
                for (Info info2 : infoList) {
                    if (info2 == null || info2.pointerTypes == null || info2.pointerTypes.length <= 0) continue;
                    pointerTypes = info2.pointerTypes;
                    break;
                }
                int namespace2 = baseType.lastIndexOf("::");
                inheritedConstructors = inheritedConstructors + d.text.replace(pointerTypes != null ? " " + pointerTypes[0].substring(pointerTypes[0].lastIndexOf(46) + 1) : (namespace2 >= 0 ? " " + baseType.substring(namespace2 + 2) : " " + baseType), " " + shortName) + "\n";
                d.text = "";
            } else if (d.declarator != null && d.declarator.type != null && d.declarator.type.constructor) {
                implicitConstructor = false;
                Declarator[] paramDcls = d.declarator.parameters.declarators;
                String t = paramDcls.length > 0 ? paramDcls[0].type.javaName : null;
                arrayConstructor |= paramDcls.length == 1 && (t.equals("int") || t.equals("long") || t.equals("float") || t.equals("double")) && !d.inaccessible;
                boolean defaultConstructor2 = (paramDcls.length == 0 || paramDcls.length == 1 && t.equals("void")) && !d.inaccessible;
                boolean longConstructor2 = paramDcls.length == 1 && t.equals("long") && !d.inaccessible;
                boolean pointerConstructor2 = paramDcls.length == 1 && t.equals("Pointer") && !d.inaccessible;
                int n2 = d.text.indexOf("private native void allocate");
                if (defaultConstructor && defaultConstructor2 || longConstructor && longConstructor2 || pointerConstructor && pointerConstructor2 || n2 >= 0 && inheritedConstructors.contains(d.text.substring(n2))) {
                    d.text = "";
                }
                defaultConstructor |= defaultConstructor2;
                longConstructor |= longConstructor2;
                pointerConstructor |= pointerConstructor2;
            }
            abstractClass |= d.abstractMember;
            allPureConst &= d.constMember && d.abstractMember;
            haveVariables |= d.variable;
        }
        if (allPureConst && ctx.virtualize) {
            modifiers = "@Const " + modifiers;
        }
        if (!anonymous) {
            void var34_68;
            Map<Type, Boolean> froms;
            if (!fullName.equals(cppName)) {
                decl.text = decl.text + "@Name(\"" + (context.javaName == null || context.namespace == null ? cppName : cppName.substring(context.namespace.length() + 2)) + "\") ";
            } else if (context.namespace != null && context.javaName == null) {
                decl.text = decl.text + "@Namespace(\"" + context.namespace + "\") ";
            }
            if ((!implicitConstructor || derivedClass) && haveVariables) {
                decl.text = decl.text + "@NoOffset ";
            }
            if (info != null && info.base != null) {
                base.javaName = context.constName != null ? context.constBaseName : info.base;
            }
            decl.text = decl.text + modifiers + "class " + shortName + " extends " + base.javaName + " {\n    static { Loader.load(); }\n";
            Object constructorAnnotations = "";
            if (constructorInfo != null && constructorInfo.annotations != null) {
                for (String a : constructorInfo.annotations) {
                    constructorAnnotations = (String)constructorAnnotations + a + " ";
                }
            }
            if (!(!implicitConstructor || info != null && info.purify || abstractClass && !ctx.virtualize)) {
                void var34_59;
                String string2 = string + "    /** Default native constructor. */\n    " + Parser.filterJavaAnnotations((String)constructorAnnotations) + "public " + shortName + "() { super((Pointer)null); allocate(); }\n";
                if (((String)constructorAnnotations).isEmpty()) {
                    String string3 = string2 + "    /** Native array allocator. Access with {@link Pointer#position(long)}. */\n    public " + shortName + "(long size) { super((Pointer)null); allocateArray(size); }\n";
                }
                String string4 = (String)var34_59 + "    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */\n    public " + shortName + "(Pointer p) { super(p); }\n    " + (String)constructorAnnotations + "private native void allocate();\n";
                if (((String)constructorAnnotations).isEmpty()) {
                    String string5 = string4 + "    private native void allocateArray(long size);\n    @Override public " + shortName + " position(long position) {\n        return (" + shortName + ")super.position(position);\n    }\n    @Override public " + shortName + " getPointer(long i) {\n        return new " + shortName + "((Pointer)this).offsetAddress(i);\n    }\n";
                }
            } else {
                if (!(info != null && info.purify || abstractClass && !ctx.virtualize)) {
                    String string6 = string + inheritedConstructors;
                }
                if (!pointerConstructor) {
                    void var34_63;
                    String string7 = (String)var34_63 + "    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */\n    public " + shortName + "(Pointer p) { super(p); }\n";
                }
                if (!(!defaultConstructor || info != null && info.purify || abstractClass && !ctx.virtualize || arrayConstructor || !((String)constructorAnnotations).isEmpty())) {
                    void var34_65;
                    String string8 = (String)var34_65 + "    /** Native array allocator. Access with {@link Pointer#position(long)}. */\n    public " + shortName + "(long size) { super((Pointer)null); allocateArray(size); }\n    private native void allocateArray(long size);\n    @Override public " + shortName + " position(long position) {\n        return (" + shortName + ")super.position(position);\n    }\n    @Override public " + shortName + " getPointer(long i) {\n        return new " + shortName + "((Pointer)this).offsetAddress(i);\n    }\n";
                }
            }
            if ((froms = this.downcasts.get(base.cppName)) != null) {
                for (Map.Entry<Type, Boolean> i : froms.entrySet()) {
                    boolean found = false;
                    for (Declaration d : declList2) {
                        if (!(shortName + "_" + i.getKey().javaName).equals(d.signature)) continue;
                        found = true;
                        break;
                    }
                    if (found) continue;
                    String string9 = (String)var34_68 + this.downcast(type, i.getKey(), i.getValue() != false || base.virtual);
                }
            }
            if (info == null || !info.skipDefaults) {
                decl.text = decl.text + (String)var34_68;
            }
            declList.spacing = spacing;
            decl.text = declList.rescan(decl.text + casts + "\n");
            declList.spacing = null;
        }
        for (Type base2 : baseClasses) {
            Info baseInfo = this.infoMap.getFirst(base2.cppName);
            if (baseInfo == null || !baseInfo.flatten || baseInfo.javaText == null) continue;
            String text = baseInfo.javaText;
            int start = text.indexOf(123);
            int n3 = 0;
            while (n3 < 2) {
                char c = text.charAt(start);
                if (c == '\n') {
                    ++n3;
                } else if (!Character.isWhitespace((int)c)) {
                    n3 = 0;
                }
                ++start;
            }
            int end = text.lastIndexOf(125);
            decl.text = decl.text + text.substring(start, end).replace(base2.javaName, type.javaName);
            decl.custom = !info.define;
        }
        if (polymorphic) {
            this.polymorphicClasses.add(type.javaName);
        }
        for (String[] d : declList2) {
            if ((!d.inaccessible || d.declarator != null && d.declarator.type.friend) && (d.declarator == null || d.declarator.type == null || !d.declarator.type.constructor || !abstractClass || info != null && info.virtualize)) {
                decl.text = decl.text + d.text;
            }
            if (d.inaccessible || d.declarator == null || d.declarator.type == null || !d.declarator.type.constructor) continue;
            inheritedConstructors = inheritedConstructors + d.text;
        }
        if (constructorInfo == null) {
            constructorInfo = new Info(constructorName);
            this.infoMap.put(constructorInfo);
        }
        if (constructorInfo.javaText == null && inheritedConstructors.length() > 0) {
            constructorInfo.javaText(inheritedConstructors);
        }
        if (!anonymous) {
            decl.text = decl.text + this.tokens.get().spacing + '}';
        }
        Token token4 = this.tokens.next();
        while (!token4.match(Token.EOF)) {
            if (token4.match(Character.valueOf(';'))) {
                decl.text = decl.text + token4.spacing;
                break;
            }
            token4 = this.tokens.next();
        }
        this.tokens.next();
        decl.type = type;
        if (info != null && info.javaText != null) {
            decl.signature = decl.text = info.javaText;
            decl.custom = !info.define;
        } else if (info != null && info.flatten) {
            info.javaText = decl.text;
        }
        declList.add(decl);
        return true;
    }

    boolean enumeration(Context context, DeclarationList declList) throws ParserException {
        boolean enumerate;
        String comment;
        int backIndex = this.tokens.index;
        String enumSpacing = this.tokens.get().spacing;
        boolean typedef = this.tokens.get().match(Token.TYPEDEF);
        boolean foundEnum = false;
        Token token = this.tokens.get();
        while (!token.match(Token.EOF)) {
            if (token.match(Token.ENUM)) {
                foundEnum = true;
                break;
            }
            if (!token.match(5)) break;
            token = this.tokens.next();
        }
        if (!foundEnum) {
            this.tokens.index = backIndex;
            return false;
        }
        boolean enumClass = false;
        String enumType = "enum";
        if (this.tokens.get(1).match(Token.CLASS, Token.STRUCT)) {
            enumClass = true;
            enumType = enumType + " " + this.tokens.next();
        }
        if (typedef && !this.tokens.get(1).match(Character.valueOf('{')) && this.tokens.get(2).match(5)) {
            this.tokens.next();
        }
        int count = 0;
        boolean longenum = false;
        String cppType = "int";
        String javaType = "int";
        String separator = "";
        String enumPrefix = "public static final " + javaType;
        String countPrefix = "";
        String enumerators = "";
        String enumerators2 = "";
        HashMap<String, String> enumeratorMap = new HashMap<String, String>();
        String extraText = "";
        String name = "";
        Token token2 = this.tokens.next().expect(5, Character.valueOf('{'), Character.valueOf(':'), Character.valueOf(';'));
        if (token2.match(5)) {
            Attribute attr = this.attribute(true);
            while (attr != null && attr.annotation) {
                attr = this.attribute(true);
            }
            name = this.tokens.get().value;
            token2 = this.tokens.next();
        }
        if (token2.match(Character.valueOf(':'))) {
            token2 = this.tokens.next();
            Type type = this.type(context);
            cppType = type.cppName;
            javaType = type.javaName;
            enumPrefix = "public static final " + javaType;
            token2 = this.tokens.get();
        }
        if (typedef || !token2.match(Character.valueOf(';'))) {
            if (!token2.match(Character.valueOf('{'))) {
                this.tokens.index = backIndex;
                return false;
            }
            token2 = this.tokens.next();
            while (!token2.match(Token.EOF, Character.valueOf('}'))) {
                comment = this.commentBefore();
                if (this.macro(context, declList)) {
                    Declaration macroDecl = (Declaration)declList.remove(declList.size() - 1);
                    extraText = extraText + comment + macroDecl.text;
                    if (separator.equals(",") && !macroDecl.text.trim().startsWith("//")) {
                        separator = ";";
                        enumPrefix = "public static final " + javaType;
                    }
                } else {
                    Token enumerator = this.tokens.get();
                    String cppName = enumerator.value;
                    if (cppName == null || cppName.length() == 0) {
                        this.tokens.next().spacing = token2.spacing;
                    } else {
                        Info info;
                        String javaName = cppName;
                        if (enumClass) {
                            cppName = name + "::" + cppName;
                        }
                        if (context.namespace != null) {
                            cppName = context.namespace + "::" + cppName;
                        }
                        if ((info = this.infoMap.getFirst(cppName)) != null && info.javaNames != null && info.javaNames.length > 0) {
                            javaName = info.javaNames[0];
                        } else if (info == null) {
                            info = new Info(cppName).cppText("").translate();
                            this.infoMap.put(info);
                        }
                        this.tokens.next();
                        String annotations = "";
                        Attribute attr = this.attribute(true);
                        while (attr != null && attr.annotation) {
                            annotations = annotations + attr.javaName;
                            attr = this.attribute(true);
                        }
                        String spacing2 = "";
                        if (this.tokens.get().match(Character.valueOf('='))) {
                            spacing2 = this.tokens.get().spacing;
                            if (spacing2.length() > 0 && spacing2.charAt(0) == ' ') {
                                spacing2 = spacing2.substring(1);
                            }
                            countPrefix = "";
                            int count2 = 0;
                            Token prevToken = new Token();
                            boolean translate = info == null || info.translate;
                            token2 = this.tokens.next();
                            while (!token2.match(Token.EOF, Character.valueOf('#'), Character.valueOf(','), Character.valueOf('}')) || count2 > 0) {
                                if (token2.match(1) && token2.value.endsWith("L")) {
                                    longenum = true;
                                }
                                countPrefix = countPrefix + token2.spacing + token2;
                                if (token2.match(Character.valueOf('('))) {
                                    ++count2;
                                } else if (token2.match(Character.valueOf(')'))) {
                                    --count2;
                                }
                                if (prevToken.match(5) && token2.match(Character.valueOf('(')) || token2.match(Character.valueOf('{'), Character.valueOf('}'))) {
                                    translate = false;
                                }
                                prevToken = token2;
                                token2 = this.tokens.next();
                            }
                            try {
                                count = Integer.parseInt(countPrefix.trim());
                                countPrefix = "";
                            }
                            catch (NumberFormatException e) {
                                count = 0;
                                if (translate) {
                                    if ((countPrefix = this.translate(countPrefix)).length() > 0 && countPrefix.charAt(0) == ' ') {
                                        countPrefix = countPrefix.substring(1);
                                    }
                                }
                                if (separator.equals(",")) {
                                    separator = ";";
                                }
                                if (!javaName.equals(cppName)) {
                                    annotations = annotations + "@Name(\"" + cppName + "\") ";
                                } else if (context.namespace != null && context.javaName == null) {
                                    annotations = annotations + "@Namespace(\"" + context.namespace + "\") ";
                                }
                                if (info == null || !info.skip) {
                                    extraText = extraText + "\n" + annotations + "public static native @MemberGetter " + javaType + " " + javaName + "();\n";
                                }
                                enumPrefix = "public static final " + javaType;
                                countPrefix = javaName + "()";
                            }
                        }
                        if (extraText.length() > 0 && !extraText.endsWith("\n") && enumPrefix.length() > 0) {
                            extraText = extraText + comment + "\n";
                            comment = "";
                        }
                        String text = separator + extraText + enumPrefix + comment;
                        String text2 = separator + comment;
                        comment = this.commentAfter();
                        if (comment.length() == 0 && this.tokens.get().match(Character.valueOf(','))) {
                            this.tokens.next();
                            comment = this.commentAfter();
                        }
                        String spacing = enumerator.spacing;
                        if (comment.length() > 0) {
                            text = text + spacing + comment;
                            text2 = text2 + spacing + comment;
                            int newline = spacing.lastIndexOf(10);
                            if (newline >= 0) {
                                spacing = spacing.substring(newline + 1);
                            }
                        }
                        if (spacing.length() == 0 && !text.endsWith(",")) {
                            spacing = " ";
                        }
                        String cast = javaType.equals("byte") || javaType.equals("short") ? "(" + javaType + ")(" : "";
                        text = text + spacing + annotations + javaName + spacing2 + " = " + cast + countPrefix;
                        String countPrefix2 = countPrefix;
                        for (String key : enumeratorMap.keySet()) {
                            countPrefix2 = countPrefix2.replaceAll("\\b" + key + "\\b", key + ".value");
                        }
                        text2 = text2 + spacing + annotations + javaName + spacing2 + "(" + cast + countPrefix2;
                        if (countPrefix.trim().length() > 0) {
                            if (count > 0) {
                                text = text + " + " + count;
                                text2 = text2 + " + " + count;
                            }
                        } else {
                            text = text + count;
                            text2 = text2 + count;
                        }
                        if (javaType.equals("boolean") && (!countPrefix.equals("true") && !countPrefix.equals("false") || count > 0)) {
                            text = text + " != 0";
                            text2 = text2 + " != 0";
                        }
                        if (cast.length() > 0) {
                            text = text + ")";
                            text2 = text2 + ")";
                        }
                        ++count;
                        if (info == null || !info.skip) {
                            enumerators = enumerators + text;
                            enumerators2 = enumerators2 + text2 + ")";
                            enumeratorMap.put(javaName, text);
                            separator = ",";
                            enumPrefix = "";
                            extraText = "";
                        }
                    }
                }
                token2 = this.tokens.get();
            }
        }
        if (longenum) {
            enumerators = enumerators.replace(" " + javaType, " long");
            cppType = "long long";
            javaType = "long";
        }
        comment = this.commentBefore();
        Declaration decl = new Declaration();
        token2 = this.tokens.get();
        while (!token2.match(Character.valueOf(';'), Token.EOF)) {
            int indirections = 0;
            while (token2.match(Character.valueOf('*'))) {
                ++indirections;
                token2 = this.tokens.next();
            }
            if (token2.match(5)) {
                String name1 = name;
                String name2 = token2.value;
                if (typedef && indirections == 0 || name == null || name.length() == 0) {
                    name = name2;
                    if (name1 != null && name1.length() > 0) {
                        name2 = name1;
                    }
                }
                String cppName2 = context.namespace != null ? context.namespace + "::" + name2 : name2;
                Info info2 = this.infoMap.getFirst(cppType);
                if (indirections > 0) {
                    this.infoMap.put(new Info(info2).cast().cppNames(cppName2).valueTypes(info2.pointerTypes).pointerTypes("PointerPointer"));
                } else {
                    this.infoMap.put(new Info(info2).cast().cppNames(cppName2));
                }
            }
            token2 = this.tokens.next();
        }
        String cppName = context.namespace != null ? context.namespace + "::" + name : name;
        Info info = this.infoMap.getFirst(cppName);
        Info info2 = this.infoMap.getFirst(null);
        boolean bl = info != null ? info.enumerate : (enumerate = info2 != null ? info2.enumerate : false);
        if (info != null && info.skip) {
            decl.text = enumSpacing;
        } else {
            String javaName;
            int newline;
            if (info != null && info.cppTypes != null && info.cppTypes.length > 0) {
                cppType = info.cppTypes[0];
                String javaType2 = this.infoMap.getFirst((String)cppType).valueTypes[0];
                enumerators = enumerators.replace(" " + javaType, " " + javaType2);
                javaType = javaType2;
            }
            String enumSpacing2 = (newline = enumSpacing.lastIndexOf(10)) < 0 ? enumSpacing : enumSpacing.substring(newline + 1);
            String string = javaName = info != null && info.valueTypes != null && info.valueTypes.length > 0 ? info.valueTypes[0] : name;
            if (enumerate && javaName != null && javaName.length() > 0 && !javaName.equals(javaType) && enumerators.length() > 0 && enumerators2.length() > 0) {
                int i;
                String shortName = javaName.substring(javaName.lastIndexOf(46) + 1);
                String fullName = context.namespace != null ? context.namespace + "::" + shortName : shortName;
                String annotations = "";
                if (!fullName.equals(cppName)) {
                    annotations = annotations + "@Name(\"" + cppName + "\") ";
                } else if (context.namespace != null && context.javaName == null) {
                    annotations = annotations + "@Namespace(\"" + context.namespace + "\") ";
                }
                decl.text = decl.text + enumSpacing + annotations + "public enum " + shortName + " {" + enumerators2 + token2.expect((Object[])new Object[]{Character.valueOf((char)';')}).spacing + ";" + (comment.length() > 0 && comment.charAt(0) == ' ' ? comment.substring(1) : comment) + "\n\n" + enumSpacing2 + "    public final " + javaType + " value;\n" + enumSpacing2 + "    private " + shortName + "(" + javaType + " v) { this.value = v; }\n" + enumSpacing2 + "    private " + shortName + "(" + shortName + " e) { this.value = e.value; }\n" + enumSpacing2 + "    public " + shortName + " intern() { for (" + shortName + " e : values()) if (e.value == value) return e; return this; }\n" + enumSpacing2 + "    @Override public String toString() { return intern().name(); }\n" + enumSpacing2 + "}";
                info2 = new Info(this.infoMap.getFirst(cppType)).cppNames(cppName);
                info2.valueTypes = Arrays.copyOf(info2.valueTypes, info2.valueTypes.length + 1);
                for (i = 1; i < info2.valueTypes.length; ++i) {
                    info2.valueTypes[i] = "@Cast(\"" + cppName + "\") " + info2.valueTypes[i - 1];
                }
                info2.valueTypes[0] = context.javaName != null && context.javaName.length() > 0 ? context.javaName + "." + javaName : javaName;
                info2.pointerTypes = Arrays.copyOf(info2.pointerTypes, info2.pointerTypes.length);
                for (i = 0; i < info2.pointerTypes.length; ++i) {
                    info2.pointerTypes[i] = "@Cast(\"" + cppName + "*\") " + info2.pointerTypes[i];
                }
                this.infoMap.put(info2.cast(false).enumerate());
            } else {
                decl.text = decl.text + enumSpacing + "/** " + enumType + " " + cppName + " */\n" + enumSpacing2 + enumerators + token2.expect((Object[])new Object[]{Character.valueOf((char)';')}).spacing + ";";
                if (cppName.length() > 0) {
                    info2 = this.infoMap.getFirst(cppType);
                    this.infoMap.put(new Info(info2).cast().cppNames(cppName));
                }
                decl.text = decl.text + extraText + comment;
            }
        }
        declList.add(decl);
        this.tokens.next();
        return true;
    }

    boolean namespace(Context context, DeclarationList declList) throws ParserException {
        if (!this.tokens.get().match(Token.NAMESPACE)) {
            return false;
        }
        Declaration decl = new Declaration();
        String spacing = this.tokens.get().spacing;
        String name = null;
        this.tokens.next();
        while (this.tokens.get().match(5)) {
            name = (name != null ? name : "") + this.tokens.get().value;
            if (!this.tokens.next().match("::")) continue;
            name = name + this.tokens.get();
            this.tokens.next();
        }
        if (this.tokens.get().match(Character.valueOf('='))) {
            if (this.tokens.next().match("::")) {
                this.tokens.next();
            }
            Type type = this.type(context);
            context.namespaceMap.put(name, type.cppName);
            this.tokens.get().expect(Character.valueOf(';'));
            this.tokens.next();
            return true;
        }
        this.tokens.get().expect(Character.valueOf('{'));
        this.tokens.next();
        if (this.tokens.get().spacing.indexOf(10) < 0) {
            this.tokens.get().spacing = spacing;
        }
        context = new Context(context);
        context.namespace = name == null ? context.namespace : (context.namespace != null ? context.namespace + "::" + name : name);
        this.declarations(context, declList);
        decl.text = decl.text + this.tokens.get().expect((Object[])new Object[]{Character.valueOf((char)'}')}).spacing;
        this.tokens.next();
        declList.add(decl);
        return true;
    }

    boolean extern(Context context, DeclarationList declList) throws ParserException {
        if (!this.tokens.get().match(Token.EXTERN) || !this.tokens.get(1).match(3)) {
            return false;
        }
        String spacing = this.tokens.get().spacing;
        Declaration decl = new Declaration();
        this.tokens.next().expect("\"C\"", "\"C++\"");
        if (!this.tokens.next().match(Character.valueOf('{'))) {
            this.tokens.get().spacing = spacing;
            declList.add(decl);
            return true;
        }
        this.tokens.next();
        this.declarations(context, declList);
        this.tokens.get().expect(Character.valueOf('}'));
        this.tokens.next();
        declList.add(decl);
        return true;
    }

    void declarations(Context context, DeclarationList declList) throws ParserException {
        Token token = this.tokens.get();
        while (!token.match(Token.EOF, Character.valueOf('}'))) {
            if (token.match(Token.PRIVATE, Token.PROTECTED, Token.PUBLIC) && this.tokens.get(1).match(Character.valueOf(':'))) {
                context.inaccessible = !token.match(Token.PUBLIC);
                this.tokens.next();
                this.tokens.next();
            } else {
                Context ctx = context;
                String comment = this.commentBefore();
                token = this.tokens.get();
                String spacing = token.spacing;
                TemplateMap map = this.template(ctx);
                if (map != null) {
                    token = this.tokens.get();
                    token.spacing = spacing;
                    ctx = new Context(ctx);
                    ctx.templateMap = map;
                }
                Declaration decl = new Declaration();
                if (comment != null && comment.length() > 0) {
                    decl.inaccessible = ctx.inaccessible;
                    decl.text = comment;
                    decl.comment = true;
                    declList.add(decl);
                }
                int startIndex = this.tokens.index;
                declList.infoMap = this.infoMap;
                declList.context = ctx;
                declList.templateMap = map;
                declList.infoIterator = null;
                declList.spacing = null;
                do {
                    Info info;
                    if (map != null && declList.infoIterator != null) {
                        info = declList.infoIterator.next();
                        if (info == null) continue;
                        Type type = new Parser(this, info.cppNames[0]).type(context);
                        if (type.arguments == null || type.arguments.length > map.size()) continue;
                        int count = 0;
                        for (Map.Entry entry : map.entrySet()) {
                            entry.setValue(null);
                            if (count >= type.arguments.length) continue;
                            Type t = type.arguments[count++];
                            String s = t.cppName;
                            if (t.constValue && !s.startsWith("const ")) {
                                s = "const " + s;
                            }
                            if (t.indirections > 0) {
                                for (int i = 0; i < t.indirections; ++i) {
                                    s = s + "*";
                                }
                            }
                            if (t.reference) {
                                s = s + "&";
                            }
                            if (t.rvalue) {
                                s = s + "&&";
                            }
                            if (t.constPointer && !s.endsWith(" const")) {
                                s = s + " const";
                            }
                            t.cppName = s;
                            entry.setValue(t);
                        }
                        this.tokens.index = startIndex;
                    } else if (declList.infoIterator != null) {
                        info = declList.infoIterator.next();
                        if (info == null) continue;
                        if (info.cppNames != null && info.cppNames.length > 0 && info.cppNames[0].startsWith("const ") && info.pointerTypes != null && info.pointerTypes.length > 0) {
                            ctx = new Context(ctx);
                            ctx.constName = Parser.removeAnnotations(info.pointerTypes[0]);
                            ctx.constBaseName = info.base != null ? info.base : "Pointer";
                        }
                        this.tokens.index = startIndex;
                    }
                    if (!(this.tokens.get().match(Character.valueOf(';')) || this.macro(ctx, declList) || this.extern(ctx, declList) || this.namespace(ctx, declList) || this.enumeration(ctx, declList) || this.group(ctx, declList) || this.typedef(ctx, declList) || this.using(ctx, declList) || this.function(ctx, declList) || this.variable(ctx, declList))) {
                        spacing = this.tokens.get().spacing;
                        if (this.attribute() != null) {
                            this.tokens.get().spacing = spacing;
                        } else {
                            throw new ParserException(token.file + ":" + token.lineNumber + ":" + (token.text != null ? "\"" + token.text + "\": " : "") + "Could not parse declaration at '" + token + "'");
                        }
                    }
                    while (this.tokens.get().match(Character.valueOf(';')) && !this.tokens.get().match(Token.EOF)) {
                        this.tokens.next();
                    }
                } while (declList.infoIterator != null && declList.infoIterator.hasNext());
            }
            token = this.tokens.get();
        }
        String comment = this.commentBefore() + (this.tokens.get().match(Token.EOF) ? this.tokens.get().spacing : "");
        Declaration decl = new Declaration();
        if (comment.length() > 0) {
            decl.text = comment;
            decl.comment = true;
            declList.add(decl);
        }
    }

    void parse(Context context, DeclarationList declList, String[] includePath, String include, boolean isCFile) throws IOException, ParserException {
        Info info;
        ArrayList<Token> tokenList = new ArrayList<Token>();
        Object file = null;
        String filename = include;
        if (filename == null || filename.length() == 0) {
            return;
        }
        if (filename.startsWith("<") && filename.endsWith(">")) {
            filename = filename.substring(1, filename.length() - 1);
        } else {
            String[] f = new File(filename);
            if (f.exists()) {
                file = f;
            }
        }
        if (file == null && includePath != null) {
            for (String path : includePath) {
                File f = Loader.getCanonicalFile(new File(path, filename));
                if (!f.exists()) continue;
                file = f;
                break;
            }
        }
        if (file == null) {
            file = new File(filename);
        }
        if ((info = this.infoMap.getFirst(((File)file).getName())) != null && info.skip && info.linePatterns == null) {
            return;
        }
        if (!((File)file).exists()) {
            throw new FileNotFoundException("Could not parse \"" + file + "\": File does not exist");
        }
        this.logger.info("Parsing " + file);
        Token token = new Token();
        token.type = 4;
        token.value = "\n// Parsed from " + include + "\n\n";
        tokenList.add(token);
        Tokenizer tokenizer = new Tokenizer((File)file, this.encoding);
        if (info != null && info.linePatterns != null) {
            tokenizer.filterLines(info.linePatterns, info.skip);
        }
        while (!(token = tokenizer.nextToken()).isEmpty()) {
            if (token.type == -1) {
                token.type = 4;
            }
            tokenList.add(token);
        }
        if (this.lineSeparator == null) {
            this.lineSeparator = tokenizer.lineSeparator;
        }
        tokenizer.close();
        token = new Token(Token.EOF);
        token.spacing = "\n";
        token.file = file;
        token.lineNumber = ((Token)tokenList.get((int)(tokenList.size() - 1))).lineNumber;
        tokenList.add(token);
        this.tokens = new TokenIndexer(this.infoMap, tokenList.toArray(new Token[0]), isCFile);
        context.objectify = context.objectify | (info != null && info.objectify);
        this.declarations(context, declList);
    }

    public File[] parse(String outputDirectory, String[] classPath, Class cls) throws IOException, ParserException {
        return this.parse(new File(outputDirectory), classPath, cls);
    }

    public File[] parse(File outputDirectory, String[] classPath, Class cls) throws IOException, ParserException {
        boolean isCFile;
        ClassProperties allProperties = Loader.loadProperties(cls, this.properties, true);
        ClassProperties clsProperties = Loader.loadProperties(cls, this.properties, false);
        HashSet<String> excludes = new HashSet<String>(allProperties.get("platform.exclude"));
        HashSet<String> cIncludes = new HashSet<String>(allProperties.get("platform.cinclude"));
        ArrayList<String> clsIncludes = new ArrayList<String>();
        clsIncludes.addAll(clsProperties.get("platform.cinclude"));
        clsIncludes.addAll(clsProperties.get("platform.include"));
        ArrayList<String> allIncludes = new ArrayList<String>();
        allIncludes.addAll(allProperties.get("platform.cinclude"));
        allIncludes.addAll(allProperties.get("platform.include"));
        List<String> allTargets = allProperties.get("target");
        List<String> allGlobals = allProperties.get("global");
        List<String> clsTargets = clsProperties.get("target");
        List<String> clsGlobals = clsProperties.get("global");
        List<String> clsHelpers = clsProperties.get("helper");
        String target = clsTargets.get(clsTargets.size() - 1);
        String global = clsGlobals.get(clsGlobals.size() - 1);
        List<Class> allInherited = allProperties.getInheritedClasses();
        this.infoMap = new InfoMap();
        for (Class c : allInherited) {
            try {
                InfoMapper infoMapper = (InfoMapper)c.newInstance();
                if (infoMapper instanceof BuildEnabled) {
                    ((BuildEnabled)((Object)infoMapper)).init(this.logger, this.properties, this.encoding);
                }
                infoMapper.map(this.infoMap);
            }
            catch (ClassCastException | IllegalAccessException | InstantiationException infoMapper) {}
        }
        this.leafInfoMap = new InfoMap();
        try {
            InfoMapper infoMapper = (InfoMapper)cls.newInstance();
            if (infoMapper instanceof BuildEnabled) {
                ((BuildEnabled)((Object)infoMapper)).init(this.logger, this.properties, this.encoding);
            }
            infoMapper.map(this.leafInfoMap);
        }
        catch (ClassCastException | IllegalAccessException | InstantiationException infoMapper) {
            // empty catch block
        }
        this.infoMap.putAll(this.leafInfoMap);
        String version = Parser.class.getPackage().getImplementationVersion();
        if (version == null) {
            version = "unknown";
        }
        int n = global.lastIndexOf(46);
        String text = "";
        String header = "// Targeted by JavaCPP version " + version + ": DO NOT EDIT THIS FILE\n\n";
        String targetHeader = header + "package " + target + ";\n\n";
        String globalHeader = header + (n >= 0 ? "package " + global.substring(0, n) + ";\n\n" : "");
        List<Info> infoList = this.leafInfoMap.get(null);
        boolean objectify = false;
        for (Info info : infoList) {
            if (info == null) continue;
            objectify |= info.objectify;
            if (info.javaText == null || !info.javaText.startsWith("import")) continue;
            text = text + info.javaText + "\n";
        }
        if (!target.equals(global) && !targetHeader.equals(globalHeader)) {
            globalHeader = globalHeader + "import " + target + ".*;\n\n";
        }
        text = text + "import java.nio.*;\nimport org.bytedeco.javacpp.*;\nimport org.bytedeco.javacpp.annotation.*;\n\n";
        for (int i = 0; i < allTargets.size(); ++i) {
            if (target.equals(allTargets.get(i))) continue;
            text = allTargets.get(i).equals(allGlobals.get(i)) ? text + "import static " + allTargets.get(i) + ".*;\n" : text + "import " + allTargets.get(i) + ".*;\nimport static " + allGlobals.get(i) + ".*;\n";
        }
        if (allTargets.size() > 1) {
            text = text + "\n";
        }
        String globalText = globalHeader + text + "public class " + global.substring(n + 1) + " extends " + (clsHelpers.size() > 0 && clsIncludes.size() > 0 ? clsHelpers.get(0) : cls.getCanonicalName()) + " {\n    static { Loader.load(); }\n";
        String targetPath = target.replace('.', File.separatorChar);
        String globalPath = global.replace('.', File.separatorChar);
        File targetDir = new File(outputDirectory, targetPath);
        File globalFile = new File(outputDirectory, globalPath + ".java");
        this.logger.info("Targeting " + globalFile);
        Context context = new Context();
        context.infoMap = this.infoMap;
        context.objectify = objectify;
        String[] includePath = classPath;
        n = globalPath.lastIndexOf(File.separatorChar);
        if (n >= 0) {
            includePath = (String[])classPath.clone();
            int i = 0;
            while (i < includePath.length) {
                int n2 = i++;
                includePath[n2] = includePath[n2] + File.separator + globalPath.substring(0, n);
            }
        }
        List<String> paths = allProperties.get("platform.includepath");
        for (String s : allProperties.get("platform.includeresource")) {
            for (File f : Loader.cacheResources(s)) {
                paths.add(Loader.getCanonicalPath(f));
            }
        }
        if (clsIncludes.size() == 0) {
            this.logger.info("Nothing targeted for " + globalFile);
            return null;
        }
        String[] includePaths = paths.toArray(new String[paths.size() + includePath.length]);
        System.arraycopy(includePath, 0, includePaths, paths.size(), includePath.length);
        DeclarationList declList = new DeclarationList();
        for (String include : allIncludes) {
            if (clsIncludes.contains(include)) continue;
            isCFile = cIncludes.contains(include);
            try {
                this.parse(context, declList, includePaths, include, isCFile);
            }
            catch (FileNotFoundException e) {
                if (excludes.contains(include)) {
                    this.logger.warn(e.toString());
                    continue;
                }
                throw e;
            }
        }
        declList = new DeclarationList(declList);
        if (clsIncludes.size() > 0) {
            this.containers(context, declList);
            for (String include : clsIncludes) {
                if (!allIncludes.contains(include)) continue;
                isCFile = cIncludes.contains(include);
                try {
                    this.parse(context, declList, includePaths, include, isCFile);
                }
                catch (FileNotFoundException e) {
                    if (excludes.contains(include)) {
                        this.logger.warn(e.toString());
                        continue;
                    }
                    throw e;
                }
            }
        }
        if (declList.size() == 0) {
            this.logger.info("Nothing targeted for " + globalFile);
            return null;
        }
        File globalDir = globalFile.getParentFile();
        if (!target.equals(global)) {
            targetDir.mkdirs();
        }
        if (globalDir != null) {
            globalDir.mkdirs();
        }
        ArrayList<File> outputFiles = new ArrayList<File>();
        outputFiles.add(globalFile);
        try (EncodingFileWriter out = this.encoding != null ? new EncodingFileWriter(globalFile, this.encoding, this.lineSeparator) : new EncodingFileWriter(globalFile, this.lineSeparator);){
            ((Writer)out).append(globalText);
            for (Info info : infoList) {
                if (info.javaText == null || info.javaText.startsWith("import")) continue;
                ((Writer)out).append(info.javaText + "\n");
            }
            Declaration prevd = null;
            for (Declaration d : declList) {
                if (!target.equals(global) && d.type != null && d.type.javaName != null && d.type.javaName.length() > 0) {
                    String shortName = d.type.javaName.substring(d.type.javaName.lastIndexOf(46) + 1);
                    File javaFile = new File(targetDir, shortName + ".java");
                    if (prevd != null && !prevd.comment) {
                        ((Writer)out).append(prevd.text);
                    }
                    ((Writer)out).append("\n// Targeting " + globalDir.toPath().relativize(javaFile.toPath()) + "\n\n");
                    this.logger.info("Targeting " + javaFile);
                    String javaText = targetHeader + text + "import static " + global + ".*;\n" + (prevd != null && prevd.comment ? prevd.text : "") + d.text.replace("public static class " + shortName + " ", "@Properties(inherit = " + cls.getCanonicalName() + ".class)\npublic class " + shortName + " ") + "\n";
                    outputFiles.add(javaFile);
                    javaText = javaText.replace("\n", this.lineSeparator).replace("\\u", "\\u005Cu");
                    Files.write(javaFile.toPath(), this.encoding != null ? javaText.getBytes(this.encoding) : javaText.getBytes(), new OpenOption[0]);
                    prevd = null;
                    continue;
                }
                if (prevd != null) {
                    ((Writer)out).append(prevd.text);
                }
                prevd = d;
            }
            if (prevd != null) {
                ((Writer)out).append(prevd.text);
            }
            ((Writer)out).append("\n}\n").close();
        }
        return outputFiles.toArray(new File[0]);
    }
}

