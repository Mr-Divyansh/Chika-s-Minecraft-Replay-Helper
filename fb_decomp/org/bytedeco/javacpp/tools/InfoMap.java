/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.bytedeco.javacpp.tools.Info;
import org.bytedeco.javacpp.tools.Templates;
import org.bytedeco.javacpp.tools.Token;
import org.bytedeco.javacpp.tools.Tokenizer;

public class InfoMap
extends HashMap<String, List<Info>> {
    InfoMap parent = null;
    static final InfoMap defaults = new InfoMap(null).put(new Info("basic/containers").cppTypes("std::array", "std::bitset", "std::deque", "std::list", "std::map", "std::queue", "std::set", "std::stack", "std::vector", "std::valarray", "std::pair", "std::tuple", "std::forward_list", "std::priority_queue", "std::unordered_map", "std::unordered_set", "std::optional", "std::variant", "std::function", "std::basic_string")).put(new Info("basic/types").cppTypes("signed", "unsigned", "char", "short", "int", "long", "bool", "float", "double", "__int8", "__int16", "__int32", "__int64", "_Bool", "_Complex", "_Imaginary", "complex", "imaginary")).put(new Info("noexcept").annotations("@NoException(true)")).put(new Info("__COUNTER__").cppText("#define __COUNTER__ 0")).put(new Info(" __attribute__", "__declspec", "static_assert").annotations(new String[0]).skip()).put(new Info("void").valueTypes("void").pointerTypes("Pointer")).put(new Info("std::nullptr_t").valueTypes("Pointer").pointerTypes("PointerPointer")).put(new Info("FILE", "time_t", "va_list", "std::exception", "std::istream", "std::ostream", "std::iostream", "std::ifstream", "std::ofstream", "std::fstream", "std::istringstream", "std::ostringstream", "std::stringstream").cast().pointerTypes("Pointer")).put(new Info("std::int8_t", "int8_t", "__int8", "jbyte", "signed char").valueTypes("byte").pointerTypes("BytePointer", "ByteBuffer", "byte[]")).put(new Info("std::uint8_t", "uint8_t", "unsigned __int8", "char", "unsigned char").cast().valueTypes("byte").pointerTypes("BytePointer", "ByteBuffer", "byte[]")).put(new Info("std::int16_t", "int16_t", "__int16", "jshort", "short", "signed short", "short int", "signed short int").valueTypes("short").pointerTypes("ShortPointer", "ShortBuffer", "short[]")).put(new Info("std::uint16_t", "uint16_t", "unsigned __int16", "unsigned short", "unsigned short int").cast().valueTypes("short").pointerTypes("ShortPointer", "ShortBuffer", "short[]")).put(new Info("std::int32_t", "int32_t", "__int32", "jint", "int", "signed int", "signed").valueTypes("int").pointerTypes("IntPointer", "IntBuffer", "int[]")).put(new Info("std::uint32_t", "uint32_t", "unsigned __int32", "unsigned int", "unsigned").cast().valueTypes("int").pointerTypes("IntPointer", "IntBuffer", "int[]")).put(new Info("jlong", "long long", "signed long long", "long long int", "signed long long int").valueTypes("long").pointerTypes("LongPointer", "LongBuffer", "long[]")).put(new Info("std::int64_t", "int64_t", "__int64", "std::uint64_t", "uint64_t", "unsigned __int64", "unsigned long long", "unsigned long long int").cast().valueTypes("long").pointerTypes("LongPointer", "LongBuffer", "long[]")).put(new Info("long", "signed long", "long int", "signed long int").valueTypes("long").pointerTypes("CLongPointer")).put(new Info("unsigned long", "unsigned long int").cast().valueTypes("long").pointerTypes("CLongPointer")).put(new Info("std::size_t", "std::ssize_t", "std::ptrdiff_t", "std::intptr_t", "std::uintptr_t", "std::off_t", "size_t", "ssize_t", "ptrdiff_t", "intptr_t", "uintptr_t", "off_t").cast().valueTypes("long").pointerTypes("SizeTPointer")).put(new Info("float", "jfloat").valueTypes("float").pointerTypes("FloatPointer", "FloatBuffer", "float[]")).put(new Info("double", "jdouble").valueTypes("double").pointerTypes("DoublePointer", "DoubleBuffer", "double[]")).put(new Info("long double").cast().valueTypes("double").pointerTypes("Pointer")).put(new Info("std::complex<float>", "float _Complex", "float _Imaginary", "float complex", "float imaginary").cast().pointerTypes("FloatPointer", "FloatBuffer", "float[]")).put(new Info("std::complex<double>", "double _Complex", "double _Imaginary", "double complex", "double imaginary").cast().pointerTypes("DoublePointer", "DoubleBuffer", "double[]")).put(new Info("jboolean").valueTypes("boolean").pointerTypes("BooleanPointer", "boolean[]")).put(new Info("_Bool", "bool").cast().valueTypes("boolean").pointerTypes("BoolPointer", "boolean[]")).put(new Info("jchar").valueTypes("char").pointerTypes("CharPointer", "char[]")).put(new Info("std::char16_t", "char16_t").cast().valueTypes("char").pointerTypes("CharPointer", "char[]")).put(new Info("std::char32_t", "char32_t").cast().valueTypes("int").pointerTypes("IntPointer", "int[]")).put(new Info("std::wchar_t", "wchar_t", "WCHAR").cast().valueTypes("char", "int").pointerTypes("CharPointer", "IntPointer")).put(new Info("const char").valueTypes("@Cast(\"const char\") byte").pointerTypes("@Cast(\"const char*\") BytePointer", "String")).put(new Info("boost::optional", "std::optional").annotations("@Optional")).put(new Info("boost::shared_ptr", "std::shared_ptr").annotations("@SharedPtr")).put(new Info("boost::movelib::unique_ptr", "std::unique_ptr").annotations("@UniquePtr")).put(new Info("std::string").annotations("@StdString").valueTypes("BytePointer", "String").pointerTypes("BytePointer")).put(new Info("std::u16string").annotations("@StdString(\"char16_t\")").valueTypes("CharPointer").pointerTypes("CharPointer")).put(new Info("std::u32string").annotations("@StdString(\"char32_t\")").valueTypes("IntPointer").pointerTypes("IntPointer")).put(new Info("std::wstring").annotations("@StdWString").valueTypes("CharPointer", "IntPointer").pointerTypes("CharPointer", "IntPointer")).put(new Info("std::vector").annotations("@StdVector")).put(new Info("abstract").javaNames("_abstract")).put(new Info("assert").javaNames("_assert")).put(new Info("boolean").javaNames("_boolean")).put(new Info("byte").javaNames("_byte")).put(new Info("extends").javaNames("_extends")).put(new Info("final").javaNames("_final")).put(new Info("finally").javaNames("_finally")).put(new Info("implements").javaNames("_implements")).put(new Info("import").javaNames("_import")).put(new Info("instanceof").javaNames("_instanceof")).put(new Info("interface").javaNames("_interface")).put(new Info("native").javaNames("_native")).put(new Info("null").javaNames("_null")).put(new Info("package").javaNames("_package")).put(new Info("strictfp").javaNames("_strictfp")).put(new Info("super").javaNames("_super")).put(new Info("synchronized").javaNames("_synchronized")).put(new Info("throws").javaNames("_throws")).put(new Info("transient").javaNames("_transient")).put(new Info("operator ->").javaNames("access")).put(new Info("operator ()").javaNames("apply")).put(new Info("operator []").javaNames("get")).put(new Info("operator =").javaNames("put")).put(new Info("operator +").javaNames("add")).put(new Info("operator -").javaNames("subtract")).put(new Info("operator *").javaNames("multiply")).put(new Info("operator /").javaNames("divide")).put(new Info("operator %").javaNames("mod")).put(new Info("operator ++").javaNames("increment")).put(new Info("operator --").javaNames("decrement")).put(new Info("operator ==").javaNames("equals")).put(new Info("operator !=").javaNames("notEquals")).put(new Info("operator <").javaNames("lessThan")).put(new Info("operator >").javaNames("greaterThan")).put(new Info("operator <=").javaNames("lessThanEquals")).put(new Info("operator >=").javaNames("greaterThanEquals")).put(new Info("operator !").javaNames("not")).put(new Info("operator &&").javaNames("and")).put(new Info("operator ||").javaNames("or")).put(new Info("operator &").javaNames("and")).put(new Info("operator |").javaNames("or")).put(new Info("operator ^").javaNames("xor")).put(new Info("operator ~").javaNames("not")).put(new Info("operator <<").javaNames("shiftLeft")).put(new Info("operator >>").javaNames("shiftRight")).put(new Info("operator +=").javaNames("addPut")).put(new Info("operator -=").javaNames("subtractPut")).put(new Info("operator *=").javaNames("multiplyPut")).put(new Info("operator /=").javaNames("dividePut")).put(new Info("operator %=").javaNames("modPut")).put(new Info("operator &=").javaNames("andPut")).put(new Info("operator |=").javaNames("orPut")).put(new Info("operator ^=").javaNames("xorPut")).put(new Info("operator <<=").javaNames("shiftLeftPut")).put(new Info("operator >>=").javaNames("shiftRightPut")).put(new Info("operator new").javaNames("_new")).put(new Info("operator delete").javaNames("_delete")).put(new Info("getClass").javaNames("_getClass")).put(new Info("notify").javaNames("_notify")).put(new Info("notifyAll").javaNames("_notifyAll")).put(new Info("wait").javaNames("_wait")).put(new Info("allocate").javaNames("_allocate")).put(new Info("close").javaNames("_close")).put(new Info("deallocate").javaNames("_deallocate")).put(new Info("free").javaNames("_free")).put(new Info("hashCode").javaNames("_hashCode")).put(new Info("address").javaNames("_address")).put(new Info("position").javaNames("_position")).put(new Info("limit").javaNames("_limit")).put(new Info("capacity").javaNames("_capacity")).put(new Info("fill").javaNames("_fill")).put(new Info("zero").javaNames("_zero"));

    public InfoMap() {
        this.parent = defaults;
    }

    public InfoMap(InfoMap parent) {
        this.parent = parent;
    }

    String normalize(String name, boolean unconst, boolean untemplate) {
        int i;
        if (name == null || name.length() == 0 || name.startsWith("basic/")) {
            return name;
        }
        if (untemplate) {
            List<String> comps = Templates.splitNamespace(name, true);
            int paramsIdx = comps.size() - 1;
            String lastComp = comps.get(paramsIdx - 1);
            comps.set(paramsIdx - 1, Templates.strip(lastComp));
            name = comps.get(0);
            for (int i2 = 1; i2 < paramsIdx; ++i2) {
                name = name + "::" + comps.get(i2);
            }
            if ((name = name + comps.get(paramsIdx)).isEmpty()) {
                return name;
            }
        }
        boolean foundConst = false;
        boolean simpleType = true;
        String prefix = null;
        Object[] tokens = new Tokenizer(name, null, 0).tokenize();
        int n = tokens.length;
        Info info = this.getFirst("basic/types");
        Object[] basicTypes = info != null ? info.cppTypes : new String[]{};
        Arrays.sort(basicTypes);
        for (i = 0; i < n; ++i) {
            int j;
            if (tokens[i].match(Token.CONST, Token.CONSTEXPR)) {
                foundConst = true;
                for (j = i + 1; j < n; ++j) {
                    tokens[j - 1] = tokens[j];
                }
                --i;
                --n;
                continue;
            }
            if (((Token)tokens[i]).match(Token.CLASS, Token.STRUCT, Token.UNION)) {
                prefix = ((Token)tokens[i]).value;
                for (j = i + 1; j < n; ++j) {
                    tokens[j - 1] = tokens[j];
                }
                --i;
                --n;
                continue;
            }
            if (Arrays.binarySearch(basicTypes, ((Token)tokens[i]).value) >= 0) continue;
            simpleType = false;
            break;
        }
        if (simpleType) {
            Arrays.sort(tokens, 0, n);
            name = (foundConst ? "const " : "") + ((Token)tokens[0]).value;
            for (i = 1; i < n; ++i) {
                name = name + " " + ((Token)tokens[i]).value;
            }
        }
        if (unconst && foundConst) {
            name = name.substring(name.indexOf("const") + 5);
        }
        if (prefix != null) {
            name = name.substring(name.indexOf(prefix) + prefix.length());
        }
        return name.trim();
    }

    @Override
    public boolean containsKey(Object key) {
        return super.containsKey(key) || this.parent != null && this.parent.containsKey(key);
    }

    public List<Info> get(String cppName) {
        return this.get(cppName, true);
    }

    public List<Info> get(String cppName, boolean partial) {
        List<Info> l;
        String key = this.normalize(cppName, false, false);
        List<Info> infoList = (ArrayList<Info>)super.get(key);
        boolean partialMatch = false;
        if (infoList == null) {
            key = this.normalize(cppName, true, false);
            infoList = (List)super.get(key);
        }
        if (infoList == null && partial) {
            key = this.normalize(cppName, true, true);
            infoList = (List)super.get(key);
            partialMatch = true;
        }
        if (infoList == null) {
            infoList = new ArrayList<Info>();
        }
        if (this.parent != null && (l = this.parent.get(cppName, partial)) != null && l.size() > 0) {
            infoList = new ArrayList<Info>(infoList);
            if (partialMatch) {
                infoList.addAll(0, l);
            } else {
                infoList.addAll(l);
            }
        }
        return infoList;
    }

    public Info get(int index, String cppName) {
        return this.get(index, cppName, true);
    }

    public Info get(int index, String cppName, boolean partial) {
        List<Info> infoList = this.get(cppName, partial);
        return infoList.size() > 0 ? infoList.get(index) : null;
    }

    public Info getFirst(String cppName) {
        return this.getFirst(cppName, true);
    }

    public Info getFirst(String cppName, boolean partial) {
        List<Info> infoList = this.get(cppName, partial);
        return infoList.size() > 0 ? infoList.get(0) : null;
    }

    @Override
    public InfoMap put(int index, Info info) {
        String[] stringArray;
        if (info.cppNames != null) {
            stringArray = info.cppNames;
        } else {
            String[] stringArray2 = new String[1];
            stringArray = stringArray2;
            stringArray2[0] = null;
        }
        for (String cppName : stringArray) {
            String[] keys;
            block4: for (String key : keys = new String[]{this.normalize(cppName, false, false), this.normalize(cppName, false, true)}) {
                ArrayList<Info> infoList = (ArrayList<Info>)super.get(key);
                if (infoList == null) {
                    infoList = new ArrayList<Info>();
                    super.put(key, infoList);
                }
                if (infoList.contains(info)) continue;
                switch (index) {
                    case -1: {
                        infoList.add(info);
                        continue block4;
                    }
                    default: {
                        infoList.add(index, info);
                    }
                }
            }
        }
        return this;
    }

    public InfoMap put(Info info) {
        return this.put(-1, info);
    }

    public InfoMap putFirst(Info info) {
        return this.put(0, info);
    }
}

