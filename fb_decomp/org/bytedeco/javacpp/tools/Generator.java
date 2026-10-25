/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.tools;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bytedeco.javacpp.BoolPointer;
import org.bytedeco.javacpp.BooleanPointer;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.CLongPointer;
import org.bytedeco.javacpp.CharPointer;
import org.bytedeco.javacpp.ClassProperties;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.FunctionPointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.LoadEnabled;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.LongPointer;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacpp.ShortPointer;
import org.bytedeco.javacpp.SizeTPointer;
import org.bytedeco.javacpp.annotation.Adapter;
import org.bytedeco.javacpp.annotation.Allocator;
import org.bytedeco.javacpp.annotation.ArrayAllocator;
import org.bytedeco.javacpp.annotation.AsUtf16;
import org.bytedeco.javacpp.annotation.ByPtr;
import org.bytedeco.javacpp.annotation.ByPtrPtr;
import org.bytedeco.javacpp.annotation.ByPtrRef;
import org.bytedeco.javacpp.annotation.ByRef;
import org.bytedeco.javacpp.annotation.ByVal;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Const;
import org.bytedeco.javacpp.annotation.Convention;
import org.bytedeco.javacpp.annotation.CriticalRegion;
import org.bytedeco.javacpp.annotation.Function;
import org.bytedeco.javacpp.annotation.Index;
import org.bytedeco.javacpp.annotation.MemberGetter;
import org.bytedeco.javacpp.annotation.MemberSetter;
import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.Namespace;
import org.bytedeco.javacpp.annotation.NoDeallocator;
import org.bytedeco.javacpp.annotation.NoException;
import org.bytedeco.javacpp.annotation.NoOffset;
import org.bytedeco.javacpp.annotation.Opaque;
import org.bytedeco.javacpp.annotation.Platform;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.annotation.Raw;
import org.bytedeco.javacpp.annotation.ValueGetter;
import org.bytedeco.javacpp.annotation.ValueSetter;
import org.bytedeco.javacpp.annotation.Virtual;
import org.bytedeco.javacpp.tools.AdapterInformation;
import org.bytedeco.javacpp.tools.IndexedSet;
import org.bytedeco.javacpp.tools.Logger;
import org.bytedeco.javacpp.tools.MethodInformation;

public class Generator {
    static final String JNI_VERSION = "JNI_VERSION_1_6";
    static final List<Class> baseClasses = Arrays.asList(Loader.class, Loader.Helper.class, Pointer.class, BytePointer.class, ShortPointer.class, IntPointer.class, LongPointer.class, FloatPointer.class, DoublePointer.class, CharPointer.class, BooleanPointer.class, PointerPointer.class, BoolPointer.class, CLongPointer.class, SizeTPointer.class);
    final Logger logger;
    final java.util.Properties properties;
    final String encoding;
    PrintWriter out;
    PrintWriter out2;
    PrintWriter jniConfigOut;
    PrintWriter reflectConfigOut;
    Map<String, String> callbacks;
    IndexedSet<Class> functions;
    IndexedSet<Class> deallocators;
    IndexedSet<Class> arrayDeallocators;
    IndexedSet<Class> jclasses;
    Map<Class, Set<String>> members;
    Map<Class, Set<String>> virtualFunctions;
    Map<Class, Set<String>> virtualMembers;
    Map<Method, MethodInformation> annotationCache;
    boolean mayThrowExceptions;
    boolean usesAdapters;
    boolean passesStrings;
    boolean accessesEnums;

    public Generator(Logger logger, java.util.Properties properties) {
        this(logger, properties, null);
    }

    public Generator(Logger logger, java.util.Properties properties, String encoding) {
        this.logger = logger;
        this.properties = properties;
        this.encoding = encoding;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean generate(String sourceFilename, String jniConfigFilename, String reflectConfigFilename, String headerFilename, String loadSuffix, String baseLoadSuffix, String classPath, Class<?> ... classes) throws IOException {
        try {
            this.out = new PrintWriter(new Writer(){

                @Override
                public void write(char[] cbuf, int off, int len) {
                }

                @Override
                public void flush() {
                }

                @Override
                public void close() {
                }
            });
            this.reflectConfigOut = null;
            this.jniConfigOut = null;
            this.out2 = null;
            this.callbacks = new LinkedHashMap<String, String>();
            this.functions = new IndexedSet();
            this.deallocators = new IndexedSet();
            this.arrayDeallocators = new IndexedSet();
            this.jclasses = new IndexedSet();
            this.members = new LinkedHashMap<Class, Set<String>>();
            this.virtualFunctions = new LinkedHashMap<Class, Set<String>>();
            this.virtualMembers = new LinkedHashMap<Class, Set<String>>();
            this.annotationCache = new LinkedHashMap<Method, MethodInformation>();
            this.mayThrowExceptions = false;
            this.usesAdapters = false;
            this.passesStrings = false;
            if (baseLoadSuffix == null || baseLoadSuffix.isEmpty()) {
                for (Class cls : baseClasses) {
                    this.jclasses.index(cls);
                }
            }
            if (this.classes(true, true, true, true, loadSuffix, baseLoadSuffix, classPath, classes)) {
                File sourceFile = new File(sourceFilename);
                File sourceDir = sourceFile.getParentFile();
                if (sourceDir != null) {
                    sourceDir.mkdirs();
                }
                PrintWriter printWriter = this.out = this.encoding != null ? new PrintWriter(sourceFile, this.encoding) : new PrintWriter(sourceFile);
                if (headerFilename != null) {
                    this.logger.info("Generating " + headerFilename);
                    File headerFile = new File(headerFilename);
                    File headerDir = headerFile.getParentFile();
                    if (headerDir != null) {
                        headerDir.mkdirs();
                    }
                    PrintWriter printWriter2 = this.out2 = this.encoding != null ? new PrintWriter(headerFile, this.encoding) : new PrintWriter(headerFile);
                }
                if (jniConfigFilename != null) {
                    this.logger.info("Generating " + jniConfigFilename);
                    File jniConfigFile = new File(jniConfigFilename);
                    File jniConfigDir = jniConfigFile.getParentFile();
                    if (jniConfigDir != null) {
                        jniConfigDir.mkdirs();
                    }
                    PrintWriter printWriter3 = this.jniConfigOut = this.encoding != null ? new PrintWriter(jniConfigFile, this.encoding) : new PrintWriter(jniConfigFile);
                }
                if (reflectConfigFilename != null) {
                    this.logger.info("Generating " + reflectConfigFilename);
                    File reflectConfigFile = new File(reflectConfigFilename);
                    File reflectConfigDir = reflectConfigFile.getParentFile();
                    if (reflectConfigDir != null) {
                        reflectConfigDir.mkdirs();
                    }
                    this.reflectConfigOut = this.encoding != null ? new PrintWriter(reflectConfigFile, this.encoding) : new PrintWriter(reflectConfigFile);
                }
                boolean bl = this.classes(this.mayThrowExceptions, this.usesAdapters, this.passesStrings, this.accessesEnums, loadSuffix, baseLoadSuffix, classPath, classes);
                return bl;
            }
            boolean bl = false;
            return bl;
        }
        finally {
            if (this.out != null) {
                this.out.close();
            }
            if (this.out2 != null) {
                this.out2.close();
            }
            if (this.jniConfigOut != null) {
                this.jniConfigOut.close();
            }
            if (this.reflectConfigOut != null) {
                this.reflectConfigOut.close();
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    boolean classes(boolean handleExceptions, boolean defineAdapters, boolean convertStrings, boolean declareEnums, String loadSuffix, String baseLoadSuffix, String classPath, Class<?> ... classes) {
        Class<?>[] memberIterator;
        Set<String> m;
        Object typeName;
        String name;
        Class<?> nativeDeallocator;
        Class<?> deallocator;
        String version = Generator.class.getPackage().getImplementationVersion();
        if (version == null) {
            version = "unknown";
        }
        String warning = "// Generated by JavaCPP version " + version + ": DO NOT EDIT THIS FILE";
        this.out.println(warning);
        this.out.println();
        if (this.out2 != null) {
            this.out2.println(warning);
            this.out2.println();
        }
        ClassProperties clsProperties = Loader.loadProperties(classes, this.properties, true);
        for (String s : clsProperties.get("platform.pragma")) {
            this.out.println("#pragma " + s);
        }
        for (String s : clsProperties.get("platform.define")) {
            this.out.println("#define " + s);
        }
        this.out.println();
        this.out.println("#ifdef _WIN32");
        this.out.println("    #define _JAVASOFT_JNI_MD_H_");
        this.out.println();
        this.out.println("    #define JNIEXPORT __declspec(dllexport)");
        this.out.println("    #define JNIIMPORT __declspec(dllimport)");
        this.out.println("    #define JNICALL __stdcall");
        this.out.println();
        this.out.println("    typedef int jint;");
        this.out.println("    typedef long long jlong;");
        this.out.println("    typedef signed char jbyte;");
        this.out.println("#elif defined(__GNUC__) && !defined(__ANDROID__)");
        this.out.println("    #define _JAVASOFT_JNI_MD_H_");
        this.out.println();
        this.out.println("    #define JNIEXPORT __attribute__((visibility(\"default\")))");
        this.out.println("    #define JNIIMPORT");
        this.out.println("    #define JNICALL");
        this.out.println();
        this.out.println("    typedef int jint;");
        this.out.println("    typedef long long jlong;");
        this.out.println("    typedef signed char jbyte;");
        this.out.println("#endif");
        this.out.println();
        this.out.println("#include <jni.h>");
        if (this.out2 != null) {
            this.out2.println("#include <jni.h>");
        }
        this.out.println();
        this.out.println("#ifdef __ANDROID__");
        this.out.println("    #include <android/log.h>");
        this.out.println("#elif defined(__APPLE__) && defined(__OBJC__)");
        this.out.println("    #include <TargetConditionals.h>");
        this.out.println("    #include <Foundation/Foundation.h>");
        this.out.println("#elif defined(__APPLE__)");
        this.out.println("    #include <TargetConditionals.h>");
        this.out.println("#endif");
        this.out.println();
        this.out.println("#ifdef __linux__");
        this.out.println("    #include <malloc.h>");
        this.out.println("    #include <sys/types.h>");
        this.out.println("    #include <sys/stat.h>");
        this.out.println("    #include <sys/syscall.h>");
        this.out.println("    #include <sys/sysinfo.h>");
        this.out.println("    #include <fcntl.h>");
        this.out.println("    #include <unistd.h>");
        this.out.println("    #include <dlfcn.h>");
        this.out.println("    #include <link.h>");
        this.out.println("    #include <pthread.h>");
        this.out.println("#elif defined(__FreeBSD__)");
        this.out.println("    #include <sys/types.h>");
        this.out.println("    #include <sys/stat.h>");
        this.out.println("    #include <sys/sysctl.h>");
        this.out.println("    #include <sys/user.h>");
        this.out.println("    #include <fcntl.h>");
        this.out.println("    #include <unistd.h>");
        this.out.println("    #include <dlfcn.h>");
        this.out.println("    #include <link.h>");
        this.out.println("    #include <pthread.h>");
        this.out.println("    #include <pthread_np.h>");
        this.out.println("    #include <malloc_np.h>");
        this.out.println("#elif defined(__APPLE__)");
        this.out.println("    #include <sys/types.h>");
        this.out.println("    #include <sys/sysctl.h>");
        this.out.println("    #include <mach/mach_init.h>");
        this.out.println("    #include <mach/mach_host.h>");
        this.out.println("    #include <mach/task.h>");
        this.out.println("    #include <unistd.h>");
        this.out.println("    #include <dlfcn.h>");
        this.out.println("    #include <mach-o/dyld.h>");
        this.out.println("    #include <pthread.h>");
        this.out.println("#elif defined(_WIN32) && !defined(NO_WINDOWS_H)");
        this.out.println("    #define NOMINMAX");
        this.out.println("    #include <windows.h>");
        this.out.println("    #include <psapi.h>");
        this.out.println("#elif defined(_WIN32)");
        this.out.println("    extern \"C\" unsigned long __stdcall GetCurrentThreadId();");
        this.out.println("#endif");
        this.out.println();
        this.out.println("#if defined(__ANDROID__) || TARGET_OS_IPHONE");
        this.out.println("    #define NewWeakGlobalRef(obj) NewGlobalRef(obj)");
        this.out.println("    #define DeleteWeakGlobalRef(obj) DeleteGlobalRef(obj)");
        this.out.println("#endif");
        this.out.println();
        this.out.println("#include <limits.h>");
        this.out.println("#include <stddef.h>");
        this.out.println("#ifndef _WIN32");
        this.out.println("    #include <stdint.h>");
        this.out.println("#endif");
        this.out.println("#include <stdio.h>");
        this.out.println("#include <stdlib.h>");
        this.out.println("#include <string.h>");
        this.out.println("#include <exception>");
        this.out.println("#include <memory>");
        this.out.println("#include <new>");
        this.out.println("#include <chrono>");
        if (baseLoadSuffix == null || baseLoadSuffix.isEmpty()) {
            this.out.println();
            this.out.println("#if defined(NATIVE_ALLOCATOR) && defined(NATIVE_DEALLOCATOR)");
            this.out.println("    void* operator new(std::size_t size, const std::nothrow_t&) throw() {");
            this.out.println("        return NATIVE_ALLOCATOR(size);");
            this.out.println("    }");
            this.out.println("    void* operator new[](std::size_t size, const std::nothrow_t&) throw() {");
            this.out.println("        return NATIVE_ALLOCATOR(size);");
            this.out.println("    }");
            this.out.println("    void* operator new(std::size_t size) throw(std::bad_alloc) {");
            this.out.println("        return NATIVE_ALLOCATOR(size);");
            this.out.println("    }");
            this.out.println("    void* operator new[](std::size_t size) throw(std::bad_alloc) {");
            this.out.println("        return NATIVE_ALLOCATOR(size);");
            this.out.println("    }");
            this.out.println("    void operator delete(void* ptr) throw() {");
            this.out.println("        NATIVE_DEALLOCATOR(ptr);");
            this.out.println("    }");
            this.out.println("    void operator delete[](void* ptr) throw() {");
            this.out.println("        NATIVE_DEALLOCATOR(ptr);");
            this.out.println("    }");
            this.out.println("#endif");
        }
        this.out.println();
        this.out.println("#define jlong_to_ptr(a) ((void*)(uintptr_t)(a))");
        this.out.println("#define ptr_to_jlong(a) ((jlong)(uintptr_t)(a))");
        this.out.println();
        this.out.println("#if defined(_MSC_VER)");
        this.out.println("    #define JavaCPP_noinline __declspec(noinline)");
        this.out.println("    #define JavaCPP_hidden /* hidden by default */");
        this.out.println("#elif defined(__GNUC__)");
        this.out.println("    #define JavaCPP_noinline __attribute__((noinline)) __attribute__ ((unused))");
        this.out.println("    #define JavaCPP_hidden   __attribute__((visibility(\"hidden\"))) __attribute__ ((unused))");
        this.out.println("#else");
        this.out.println("    #define JavaCPP_noinline");
        this.out.println("    #define JavaCPP_hidden");
        this.out.println("#endif");
        this.out.println("#if __cplusplus >= 201103L || _MSC_VER >= 1900");
        this.out.println("    #define JavaCPP_override override");
        this.out.println("#else");
        this.out.println("    #define JavaCPP_override");
        this.out.println("#endif");
        this.out.println();
        if (loadSuffix == null) {
            loadSuffix = "";
            String p = clsProperties.getProperty("platform.library.static", "false").toLowerCase();
            if (p.equals("true") || p.equals("t") || p.equals("")) {
                loadSuffix = "_" + clsProperties.getProperty("platform.library");
            }
        }
        if (classes != null) {
            List<String> exclude = clsProperties.get("platform.exclude");
            List[] include = new List[]{clsProperties.get("platform.cinclude"), clsProperties.get("platform.include")};
            for (int i = 0; i < include.length; ++i) {
                if (include[i] == null || include[i].size() <= 0) continue;
                if (i == 0) {
                    this.out.println("extern \"C\" {");
                    if (this.out2 != null) {
                        this.out2.println("#ifdef __cplusplus");
                        this.out2.println("extern \"C\" {");
                        this.out2.println("#endif");
                    }
                }
                for (String s : include[i]) {
                    if (exclude.contains(s)) continue;
                    String line = "#include ";
                    if (!s.startsWith("<") && !s.startsWith("\"")) {
                        line = line + '\"';
                    }
                    line = line + s;
                    if (!s.endsWith(">") && !s.endsWith("\"")) {
                        line = line + '\"';
                    }
                    this.out.println(line);
                    if (this.out2 == null) continue;
                    this.out2.println(line);
                }
                if (i == 0) {
                    this.out.println("}");
                    if (this.out2 != null) {
                        this.out2.println("#ifdef __cplusplus");
                        this.out2.println("}");
                        this.out2.println("#endif");
                    }
                }
                this.out.println();
            }
        }
        this.out.println("static JavaVM* JavaCPP_vm = NULL;");
        this.out.println("static bool JavaCPP_haveAllocObject = false;");
        this.out.println("static bool JavaCPP_haveNonvirtual = false;");
        this.out.println("static const char* JavaCPP_classNames[" + this.jclasses.size() + "] = {");
        Iterator<Class> classIterator = this.jclasses.iterator();
        int maxMemberSize = 0;
        while (classIterator.hasNext()) {
            Set<String> m2;
            Class c = classIterator.next();
            this.out.print("        \"" + c.getName().replace('.', '/') + "\"");
            if (classIterator.hasNext()) {
                this.out.println(",");
            }
            if ((m2 = this.members.get(c)) == null || m2.size() <= maxMemberSize) continue;
            maxMemberSize = m2.size();
        }
        this.out.println(" };");
        this.out.println("static jclass JavaCPP_classes[" + this.jclasses.size() + "] = { NULL };");
        this.out.println("static jfieldID JavaCPP_addressFID = NULL;");
        this.out.println("static jfieldID JavaCPP_positionFID = NULL;");
        this.out.println("static jfieldID JavaCPP_limitFID = NULL;");
        this.out.println("static jfieldID JavaCPP_capacityFID = NULL;");
        this.out.println("static jfieldID JavaCPP_deallocatorFID = NULL;");
        this.out.println("static jfieldID JavaCPP_ownerAddressFID = NULL;");
        if (declareEnums) {
            this.out.println("static jfieldID JavaCPP_booleanValueFID = NULL;");
            this.out.println("static jfieldID JavaCPP_byteValueFID = NULL;");
            this.out.println("static jfieldID JavaCPP_shortValueFID = NULL;");
            this.out.println("static jfieldID JavaCPP_intValueFID = NULL;");
            this.out.println("static jfieldID JavaCPP_longValueFID = NULL;");
        }
        this.out.println("static jmethodID JavaCPP_initMID = NULL;");
        this.out.println("static jmethodID JavaCPP_arrayMID = NULL;");
        this.out.println("static jmethodID JavaCPP_arrayOffsetMID = NULL;");
        this.out.println("static jfieldID JavaCPP_bufferPositionFID = NULL;");
        this.out.println("static jfieldID JavaCPP_bufferLimitFID = NULL;");
        this.out.println("static jfieldID JavaCPP_bufferCapacityFID = NULL;");
        this.out.println("static jmethodID JavaCPP_stringMID = NULL;");
        this.out.println("static jmethodID JavaCPP_getBytesMID = NULL;");
        this.out.println("static jmethodID JavaCPP_toStringMID = NULL;");
        this.out.println("#ifdef STRING_BYTES_CHARSET");
        this.out.println("#ifdef MODIFIED_UTF8_STRING");
        this.out.println("#pragma message (\"warning: STRING_BYTES_CHARSET and MODIFIED_UTF8_STRING are mutually exclusive.\")");
        this.out.println("#endif");
        this.out.println("static jobject JavaCPP_stringBytesCharset = NULL;");
        this.out.println("static jmethodID JavaCPP_stringWithCharsetMID = NULL;");
        this.out.println("static jmethodID JavaCPP_getBytesWithCharsetMID = NULL;");
        this.out.println("#endif");
        this.out.println();
        this.out.println("static inline void JavaCPP_log(const char* fmt, ...) {");
        this.out.println("    va_list ap;");
        this.out.println("    va_start(ap, fmt);");
        this.out.println("#ifdef __ANDROID__");
        this.out.println("    __android_log_vprint(ANDROID_LOG_ERROR, \"javacpp\", fmt, ap);");
        this.out.println("#elif defined(__APPLE__) && defined(__OBJC__)");
        this.out.println("    NSLogv([NSString stringWithUTF8String:fmt], ap);");
        this.out.println("#else");
        this.out.println("    vfprintf(stderr, fmt, ap);");
        this.out.println("    fprintf(stderr, \"\\n\");");
        this.out.println("    fflush(stderr);");
        this.out.println("#endif");
        this.out.println("    va_end(ap);");
        this.out.println("}");
        this.out.println();
        this.out.println("#if !defined(NO_JNI_DETACH_THREAD) && defined(_WIN32)");
        this.out.println("#if __cplusplus >= 201103L || _MSC_VER >= 1900");
        this.out.println("   static thread_local");
        this.out.println("#else");
        this.out.println("   static __declspec(thread)");
        this.out.println("#endif");
        this.out.println("   struct JavaCPP_thread_local {");
        this.out.println("       JNIEnv* env = NULL;");
        this.out.println("       ~JavaCPP_thread_local() {");
        this.out.println("           if (env && JavaCPP_vm) {");
        this.out.println("               JavaCPP_vm->DetachCurrentThread();");
        this.out.println("           }");
        this.out.println("       }");
        this.out.println("   } JavaCPP_thread_local; ");
        this.out.println("#elif !defined(NO_JNI_DETACH_THREAD) && (defined(__linux__) || defined(__APPLE__) || defined(__FreeBSD__))");
        this.out.println("    static pthread_key_t JavaCPP_current_env;");
        this.out.println("    static JavaCPP_noinline void JavaCPP_detach_env(void *data) {");
        this.out.println("        if (JavaCPP_vm) {");
        this.out.println("            JavaCPP_vm->DetachCurrentThread();");
        this.out.println("        }");
        this.out.println("    }");
        this.out.println("    static JavaCPP_noinline void JavaCPP_create_pthread_key(void) {");
        this.out.println("        pthread_key_create(&JavaCPP_current_env, JavaCPP_detach_env);");
        this.out.println("    }");
        this.out.println("#endif");
        this.out.println();
        if (baseLoadSuffix == null || baseLoadSuffix.isEmpty()) {
            this.out.println("static inline jboolean JavaCPP_trimMemory() {");
            this.out.println("#if defined(__linux__) && !defined(__ANDROID__)");
            this.out.println("    return (jboolean)malloc_trim(0);");
            this.out.println("#elif defined(__FreeBSD__)");
            this.out.println("    return (jboolean)(mallctl(\"arena.0.purge\", NULL, NULL, NULL, 0) == 0);");
            this.out.println("#else");
            this.out.println("    return 0;");
            this.out.println("#endif");
            this.out.println("}");
            this.out.println();
            this.out.println("static inline jlong JavaCPP_physicalBytes(jlong maxSize = 0) {");
            this.out.println("    jlong size = 0;");
            this.out.println("#ifdef __linux__");
            this.out.println("    static int fd = open(\"/proc/self/statm\", O_RDONLY, 0);");
            this.out.println("    if (fd >= 0) {");
            this.out.println("        char line[256];");
            this.out.println("        char* s;");
            this.out.println("        int n;");
            this.out.println("        if ((n = pread(fd, line, sizeof(line), 0)) > 0 && (s = (char*)memchr(line, ' ', n)) != NULL) {");
            this.out.println("            size = (jlong)atoll(s + 1);");
            this.out.println("            if ((s = (char*)memchr(s + 1, ' ', n)) != NULL) {");
            this.out.println("                size -= (jlong)atoll(s + 1);");
            this.out.println("            }");
            this.out.println("        }");
            this.out.println("        size *= (jlong)getpagesize();");
            this.out.println("        // no close(fd);");
            this.out.println("    }");
            this.out.println("#elif defined(__FreeBSD__)");
            this.out.println("    struct kinfo_proc kp;");
            this.out.println("    size_t length = sizeof(kp);");
            this.out.println("    int mib[4] = {CTL_KERN, KERN_PROC, KERN_PROC_PID, getpid()};");
            this.out.println("    if (sysctl(mib, 4, &kp, &length, NULL, 0) == 0) {");
            this.out.println("        size = (jlong)(kp.ki_dsize + kp.ki_ssize) * getpagesize();");
            this.out.println("    }");
            this.out.println("#elif defined(__APPLE__)");
            this.out.println("    task_vm_info_data_t info;");
            this.out.println("    mach_msg_type_number_t count = TASK_VM_INFO_COUNT;");
            this.out.println("    if (task_info(current_task(), TASK_VM_INFO, (task_info_t)&info, &count) == KERN_SUCCESS) {");
            this.out.println("        size = (jlong)info.internal;");
            this.out.println("    }");
            this.out.println("#elif defined(_WIN32)");
            this.out.println("    PROCESS_MEMORY_COUNTERS counters;");
            this.out.println("    if (GetProcessMemoryInfo(GetCurrentProcess(), &counters, sizeof(counters))) {");
            this.out.println("        jlong size2 = (jlong)counters.WorkingSetSize;");
            this.out.println("        if (size2 <= maxSize) return size2;");
            this.out.println("    }");
            this.out.println("    DWORD length = sizeof(PSAPI_WORKING_SET_INFORMATION);");
            this.out.println("    PSAPI_WORKING_SET_INFORMATION *info = (PSAPI_WORKING_SET_INFORMATION*)malloc(length);");
            this.out.println("    BOOL success = QueryWorkingSet(GetCurrentProcess(), info, length);");
            this.out.println("    while (!success && GetLastError() == ERROR_BAD_LENGTH) {");
            this.out.println("        length = sizeof(PSAPI_WORKING_SET_INFORMATION) + info->NumberOfEntries * sizeof(PSAPI_WORKING_SET_BLOCK);");
            this.out.println("        info = (PSAPI_WORKING_SET_INFORMATION*)realloc(info, length);");
            this.out.println("        success = QueryWorkingSet(GetCurrentProcess(), info, length);");
            this.out.println("    }");
            this.out.println("    if (success && info != NULL) {");
            this.out.println("        for (DWORD i = 0; i < info->NumberOfEntries; i++) {");
            this.out.println("            size += !info->WorkingSetInfo[i].Shared;");
            this.out.println("        }");
            this.out.println("    }");
            this.out.println("    SYSTEM_INFO sysinfo;");
            this.out.println("    GetSystemInfo(&sysinfo);");
            this.out.println("    size *= (jlong)sysinfo.dwPageSize;");
            this.out.println("    free(info);");
            this.out.println("#endif");
            this.out.println("    return size;");
            this.out.println("}");
            this.out.println();
            this.out.println("static inline jlong JavaCPP_totalPhysicalBytes() {");
            this.out.println("    jlong size = 0;");
            this.out.println("#ifdef __linux__");
            this.out.println("    struct sysinfo info;");
            this.out.println("    if (sysinfo(&info) == 0) {");
            this.out.println("        size = (jlong)info.totalram * info.mem_unit;");
            this.out.println("    }");
            this.out.println("#elif defined(__APPLE__)");
            this.out.println("    size_t length = sizeof(size);");
            this.out.println("    sysctlbyname(\"hw.memsize\", &size, &length, NULL, 0);");
            this.out.println("#elif defined(__FreeBSD__)");
            this.out.println("    size_t length = sizeof(size);");
            this.out.println("    int mib[2] = {CTL_HW,HW_PHYSMEM};");
            this.out.println("    sysctl(mib, 2, &size, &length, NULL, 0);");
            this.out.println("#elif defined(_WIN32)");
            this.out.println("    MEMORYSTATUSEX status;");
            this.out.println("    status.dwLength = sizeof(status);");
            this.out.println("    if (GlobalMemoryStatusEx(&status)) {");
            this.out.println("        size = status.ullTotalPhys;");
            this.out.println("    }");
            this.out.println("#endif");
            this.out.println("    return size;");
            this.out.println("}");
            this.out.println();
            this.out.println("static inline jlong JavaCPP_availablePhysicalBytes() {");
            this.out.println("    jlong size = 0;");
            this.out.println("#ifdef __linux__");
            this.out.println("    int fd = open(\"/proc/meminfo\", O_RDONLY, 0);");
            this.out.println("    if (fd >= 0) {");
            this.out.println("        char temp[4096];");
            this.out.println("        char *s;");
            this.out.println("        int n;");
            this.out.println("        if ((n = read(fd, temp, sizeof(temp))) > 0 && (s = (char*)memmem(temp, n, \"MemAvailable:\", 13)) != NULL) {");
            this.out.println("            size = (jlong)(atoll(s + 13) * 1024);");
            this.out.println("        }");
            this.out.println("        close(fd);");
            this.out.println("    }");
            this.out.println("    if (size == 0) {");
            this.out.println("        struct sysinfo info;");
            this.out.println("        if (sysinfo(&info) == 0) {");
            this.out.println("            size = (jlong)info.freeram * info.mem_unit;");
            this.out.println("        }");
            this.out.println("    }");
            this.out.println("#elif defined(__FreeBSD__)");
            this.out.println("    uint32_t free_count, inactive_count;");
            this.out.println("    size_t length = sizeof(uint32_t);");
            this.out.println("    if (sysctlbyname(\"vm.stats.vm.v_free_count\", &free_count, &length, NULL, 0) == 0 &&");
            this.out.println("        sysctlbyname(\"vm.stats.vm.v_inactive_count\", &inactive_count, &length, NULL, 0) == 0) {");
            this.out.println("        size = (jlong)(free_count + inactive_count) * getpagesize();");
            this.out.println("    }");
            this.out.println("#elif defined(__APPLE__)");
            this.out.println("    vm_statistics_data_t info;");
            this.out.println("    mach_msg_type_number_t count = HOST_VM_INFO_COUNT;");
            this.out.println("    if (host_statistics(mach_host_self(), HOST_VM_INFO, (host_info_t)&info, &count) == KERN_SUCCESS) {");
            this.out.println("        size = (jlong)info.free_count * getpagesize();");
            this.out.println("    }");
            this.out.println("#elif defined(_WIN32)");
            this.out.println("    MEMORYSTATUSEX status;");
            this.out.println("    status.dwLength = sizeof(status);");
            this.out.println("    if (GlobalMemoryStatusEx(&status)) {");
            this.out.println("        size = status.ullAvailPhys;");
            this.out.println("    }");
            this.out.println("#endif");
            this.out.println("    return size;");
            this.out.println("}");
            this.out.println();
            this.out.println("static inline jlong JavaCPP_getDirectBufferAddress(JNIEnv *env, jclass cls, jobject obj) {");
            this.out.println("    return (jlong)env->GetDirectBufferAddress(obj);");
            this.out.println("}");
            this.out.println();
            this.out.println("static inline jint JavaCPP_totalProcessors() {");
            this.out.println("    jint total = 0;");
            this.out.println("#ifdef __linux__");
            this.out.println("    total = sysconf(_SC_NPROCESSORS_CONF);");
            this.out.println("#elif defined(__FreeBSD__)");
            this.out.println("    size_t length = sizeof(total);");
            this.out.println("    int mib[2] = {CTL_HW, HW_NCPU};");
            this.out.println("    if (sysctl(mib, 2, &total, &length, NULL, 0) == -1) {");
            this.out.println("        total = 0;");
            this.out.println("    }");
            this.out.println("#elif defined(__APPLE__)");
            this.out.println("    size_t length = sizeof(total);");
            this.out.println("    sysctlbyname(\"hw.logicalcpu_max\", &total, &length, NULL, 0);");
            this.out.println("#elif defined(_WIN32)");
            this.out.println("    SYSTEM_INFO info;");
            this.out.println("    GetSystemInfo(&info);");
            this.out.println("    total = info.dwNumberOfProcessors;");
            this.out.println("#endif");
            this.out.println("    return total;");
            this.out.println("}");
            this.out.println();
            this.out.println("static inline jint JavaCPP_totalCores() {");
            this.out.println("    jint total = 0;");
            this.out.println("#ifdef __linux__");
            this.out.println("    const int n = sysconf(_SC_NPROCESSORS_CONF);");
            this.out.println("    int pids[n], cids[n];");
            this.out.println("    for (int i = 0; i < n; i++) {");
            this.out.println("        int fd = 0, pid = 0, cid = 0;");
            this.out.println("        char temp[256];");
            this.out.println("        sprintf(temp, \"/sys/devices/system/cpu/cpu%d/topology/physical_package_id\", i);");
            this.out.println("        if ((fd = open(temp, O_RDONLY, 0)) >= 0) {");
            this.out.println("            if (read(fd, temp, sizeof(temp)) > 0) {");
            this.out.println("                pid = atoi(temp);");
            this.out.println("            }");
            this.out.println("            close(fd);");
            this.out.println("        }");
            this.out.println("        sprintf(temp, \"/sys/devices/system/cpu/cpu%d/topology/core_id\", i);");
            this.out.println("        if ((fd = open(temp, O_RDONLY, 0)) >= 0) {");
            this.out.println("            if (read(fd, temp, sizeof(temp)) > 0) {");
            this.out.println("                cid = atoi(temp);");
            this.out.println("            }");
            this.out.println("            close(fd);");
            this.out.println("        }");
            this.out.println("        bool found = false;");
            this.out.println("        for (int j = 0; j < total; j++) {");
            this.out.println("            if (pids[j] == pid && cids[j] == cid) {");
            this.out.println("                found = true;");
            this.out.println("                break;");
            this.out.println("            }");
            this.out.println("        }");
            this.out.println("        if (!found) {");
            this.out.println("            pids[total] = pid;");
            this.out.println("            cids[total] = cid;");
            this.out.println("            total++;");
            this.out.println("        }");
            this.out.println("    }");
            this.out.println("#elif defined(__FreeBSD__)");
            this.out.println("    uint32_t ncpu, allowed = 0;");
            this.out.println("    uint32_t threads_per_core = 1;");
            this.out.println("    size_t length = sizeof(ncpu);");
            this.out.println("    int mib[2] = {CTL_HW, HW_NCPU};");
            this.out.println("    // Get total logical CPUs");
            this.out.println("    if (sysctl(mib, 2, &ncpu, &length, NULL, 0) == 0) {");
            this.out.println("        size_t t_length = sizeof(threads_per_core);");
            this.out.println("        size_t t_lengthallowed = sizeof(allowed);");
            this.out.println("        if (sysctlbyname(\"machdep.hyperthreading_allowed\", &allowed, &t_lengthallowed, NULL, 0) == 0 &&");
            this.out.println("            sysctlbyname(\"kern.smp.threads_per_core\", &threads_per_core, &t_length, NULL, 0) != 0){");
            this.out.println("            // Fallback for some architectures/versions");
            this.out.println("            threads_per_core = 1; ");
            this.out.println("        }");
            this.out.println("        total = ncpu / (allowed == 1 ? (threads_per_core > 0 ? threads_per_core : 1) : 1);");
            this.out.println("    }");
            this.out.println("#elif defined(__APPLE__)");
            this.out.println("    size_t length = sizeof(total);");
            this.out.println("    sysctlbyname(\"hw.physicalcpu_max\", &total, &length, NULL, 0);");
            this.out.println("#elif defined(_WIN32)");
            this.out.println("    SYSTEM_LOGICAL_PROCESSOR_INFORMATION *info = NULL;");
            this.out.println("    DWORD length = 0;");
            this.out.println("    BOOL success = GetLogicalProcessorInformation(info, &length);");
            this.out.println("    while (!success && GetLastError() == ERROR_INSUFFICIENT_BUFFER) {");
            this.out.println("        info = (SYSTEM_LOGICAL_PROCESSOR_INFORMATION*)realloc(info, length);");
            this.out.println("        success = GetLogicalProcessorInformation(info, &length);");
            this.out.println("    }");
            this.out.println("    if (success && info != NULL) {");
            this.out.println("        length /= sizeof(SYSTEM_LOGICAL_PROCESSOR_INFORMATION);");
            this.out.println("        for (DWORD i = 0; i < length; i++) {");
            this.out.println("            if (info[i].Relationship == RelationProcessorCore) {");
            this.out.println("                total++;");
            this.out.println("            }");
            this.out.println("        }");
            this.out.println("    }");
            this.out.println("    free(info);");
            this.out.println("#endif");
            this.out.println("    return total;");
            this.out.println("}");
            this.out.println();
            this.out.println("static inline jint JavaCPP_totalChips() {");
            this.out.println("    jint total = 0;");
            this.out.println("#ifdef __linux__");
            this.out.println("    const int n = sysconf(_SC_NPROCESSORS_CONF);");
            this.out.println("    int pids[n];");
            this.out.println("    for (int i = 0; i < n; i++) {");
            this.out.println("        int fd = 0, pid = 0;");
            this.out.println("        char temp[256];");
            this.out.println("        sprintf(temp, \"/sys/devices/system/cpu/cpu%d/topology/physical_package_id\", i);");
            this.out.println("        if ((fd = open(temp, O_RDONLY, 0)) >= 0) {");
            this.out.println("            if (read(fd, temp, sizeof(temp)) > 0) {");
            this.out.println("                pid = atoi(temp);");
            this.out.println("            }");
            this.out.println("            close(fd);");
            this.out.println("        }");
            this.out.println("        bool found = false;");
            this.out.println("        for (int j = 0; j < total; j++) {");
            this.out.println("            if (pids[j] == pid) {");
            this.out.println("                found = true;");
            this.out.println("                break;");
            this.out.println("            }");
            this.out.println("        }");
            this.out.println("        if (!found) {");
            this.out.println("            pids[total] = pid;");
            this.out.println("            total++;");
            this.out.println("        }");
            this.out.println("    }");
            this.out.println("#elif defined(__APPLE__)");
            this.out.println("    size_t length = sizeof(total);");
            this.out.println("    sysctlbyname(\"hw.packages\", &total, &length, NULL, 0);");
            this.out.println("#elif defined(__FreeBSD__)");
            this.out.println("    total = 1;");
            this.out.println("#elif defined(_WIN32)");
            this.out.println("    SYSTEM_LOGICAL_PROCESSOR_INFORMATION *info = NULL;");
            this.out.println("    DWORD length = 0;");
            this.out.println("    BOOL success = GetLogicalProcessorInformation(info, &length);");
            this.out.println("    while (!success && GetLastError() == ERROR_INSUFFICIENT_BUFFER) {");
            this.out.println("        info = (SYSTEM_LOGICAL_PROCESSOR_INFORMATION*)realloc(info, length);");
            this.out.println("        success = GetLogicalProcessorInformation(info, &length);");
            this.out.println("    }");
            this.out.println("    if (success && info != NULL) {");
            this.out.println("        length /= sizeof(SYSTEM_LOGICAL_PROCESSOR_INFORMATION);");
            this.out.println("        for (DWORD i = 0; i < length; i++) {");
            this.out.println("            if (info[i].Relationship == RelationProcessorPackage) {");
            this.out.println("                total++;");
            this.out.println("            }");
            this.out.println("        }");
            this.out.println("    }");
            this.out.println("    free(info);");
            this.out.println("#endif");
            this.out.println("    return total;");
            this.out.println("}");
            this.out.println();
            this.out.println("#if (defined(__linux__) && !(defined(__ANDROID__) && defined(__arm__))) || defined (__FreeBSD__)");
            this.out.println("static int JavaCPP_dlcallback(dl_phdr_info *info, size_t size, void *data) {");
            this.out.println("    void *handle = dlopen(info->dlpi_name, RTLD_LAZY);");
            this.out.println("    if (handle != NULL) {");
            this.out.println("        void *address = dlsym(handle, ((char**)data)[0]);");
            this.out.println("        dlclose(handle);");
            this.out.println("        if (address != NULL) {");
            this.out.println("            ((void**)data)[1] = address;");
            this.out.println("            return 1;");
            this.out.println("        }");
            this.out.println("    }");
            this.out.println("    return 0;");
            this.out.println("}");
            this.out.println("#endif");
            this.out.println();
            this.out.println("static JavaCPP_noinline jclass JavaCPP_getClass(JNIEnv* env, int i);");
            this.out.println("static inline void JavaCPP_loadGlobal(JNIEnv* env, jclass cls, const char* filename) {");
            this.out.println("#ifdef _WIN32");
            this.out.println("    HMODULE handle = LoadLibraryA(filename);");
            this.out.println("    if (handle == NULL) {");
            this.out.println("        char temp[256];");
            this.out.println("        sprintf(temp, \"LoadLibrary() failed with 0x%lx\", GetLastError());");
            this.out.println("        env->ThrowNew(JavaCPP_getClass(env, " + this.jclasses.index(UnsatisfiedLinkError.class) + "), temp);");
            this.out.println("    }");
            this.out.println("#else");
            this.out.println("    void *handle = dlopen(filename, RTLD_LAZY | RTLD_GLOBAL);");
            this.out.println("    if (handle == NULL) {");
            this.out.println("        env->ThrowNew(JavaCPP_getClass(env, " + this.jclasses.index(UnsatisfiedLinkError.class) + "), dlerror());");
            this.out.println("    }");
            this.out.println("#endif");
            this.out.println("}");
            this.out.println();
            this.out.println("static inline void* JavaCPP_addressof(const char* name) {");
            this.out.println("    void *address = NULL;");
            this.out.println("#if defined(__linux__) || defined(__FreeBSD__)");
            this.out.println("    address = dlsym(RTLD_DEFAULT, name);");
            this.out.println("#if !(defined(__ANDROID__) && defined(__arm__))");
            this.out.println("    if (address == NULL) {");
            this.out.println("        void *data[] = { (char*)name, NULL };");
            this.out.println("        dl_iterate_phdr(JavaCPP_dlcallback, data);");
            this.out.println("        address = data[1];");
            this.out.println("    }");
            this.out.println("#endif");
            this.out.println("#elif defined(__APPLE__)");
            this.out.println("    address = dlsym(RTLD_DEFAULT, name);");
            this.out.println("    if (address == NULL) {");
            this.out.println("        for (uint32_t i = 0; i < _dyld_image_count(); i++) {");
            this.out.println("            const char *libname = _dyld_get_image_name(i);");
            this.out.println("            if (libname != NULL) {");
            this.out.println("                void *handle = dlopen(libname, RTLD_LAZY);");
            this.out.println("                if (handle != NULL) {");
            this.out.println("                    address = dlsym(handle, name);");
            this.out.println("                    dlclose(handle);");
            this.out.println("                    if (address != NULL) {");
            this.out.println("                        break;");
            this.out.println("                    }");
            this.out.println("                }");
            this.out.println("            }");
            this.out.println("        }");
            this.out.println("    }");
            this.out.println("#elif defined(_WIN32)");
            this.out.println("    HANDLE process = GetCurrentProcess();");
            this.out.println("    HMODULE *modules = NULL;");
            this.out.println("    DWORD length = 0, needed = 0;");
            this.out.println("    BOOL success = EnumProcessModules(process, modules, length, &needed);");
            this.out.println("    while (success && needed > length) {");
            this.out.println("        modules = (HMODULE*)realloc(modules, length = needed);");
            this.out.println("        success = EnumProcessModules(process, modules, length, &needed);");
            this.out.println("    }");
            this.out.println("    if (success && modules != NULL) {");
            this.out.println("        length = needed / sizeof(HMODULE);");
            this.out.println("        for (DWORD i = 0; i < length; i++) {");
            this.out.println("            address = (void*)GetProcAddress(modules[i], name);");
            this.out.println("            if (address != NULL) {");
            this.out.println("                break;");
            this.out.println("            }");
            this.out.println("        }");
            this.out.println("    }");
            this.out.println("    free(modules);");
            this.out.println("#endif");
            this.out.println("    return address;");
            this.out.println("}");
            this.out.println();
            this.out.println("static inline JavaVM* JavaCPP_getJavaVM() {");
            this.out.println("    return JavaCPP_vm;");
            this.out.println("}");
            this.out.println();
            this.out.println("static inline jobject JavaCPP_newGlobalRef(JNIEnv *env, jclass cls, jobject obj) {");
            this.out.println("    return env->NewGlobalRef(obj);");
            this.out.println("}");
            this.out.println();
            this.out.println("static inline jobject JavaCPP_accessGlobalRef(JNIEnv *env, jclass cls, jobject globalRef) {");
            this.out.println("    return globalRef;");
            this.out.println("}");
            this.out.println();
            this.out.println("static inline void JavaCPP_deleteGlobalRef(JNIEnv *env, jclass cls, jobject globalRef) {");
            this.out.println("    env->DeleteGlobalRef(globalRef);");
            this.out.println("}");
            this.out.println();
        }
        this.out.println("static JavaCPP_noinline jclass JavaCPP_getClass(JNIEnv* env, int i) {");
        this.out.println("    if (JavaCPP_classes[i] == NULL && env->PushLocalFrame(1) == 0) {");
        this.out.println("        jclass cls = env->FindClass(JavaCPP_classNames[i]);");
        this.out.println("        if (cls == NULL || env->ExceptionCheck()) {");
        this.out.println("            JavaCPP_log(\"Error loading class %s.\", JavaCPP_classNames[i]);");
        this.out.println("            return NULL;");
        this.out.println("        }");
        this.out.println("        JavaCPP_classes[i] = (jclass)env->NewWeakGlobalRef(cls);");
        this.out.println("        if (JavaCPP_classes[i] == NULL || env->ExceptionCheck()) {");
        this.out.println("            JavaCPP_log(\"Error creating global reference of class %s.\", JavaCPP_classNames[i]);");
        this.out.println("            return NULL;");
        this.out.println("        }");
        this.out.println("        env->PopLocalFrame(NULL);");
        this.out.println("    }");
        this.out.println("    return JavaCPP_classes[i];");
        this.out.println("}");
        this.out.println();
        this.out.println("static JavaCPP_noinline jfieldID JavaCPP_getFieldID(JNIEnv* env, int i, const char* name, const char* sig) {");
        this.out.println("    jclass cls = JavaCPP_getClass(env, i);");
        this.out.println("    if (cls == NULL) {");
        this.out.println("        return NULL;");
        this.out.println("    }");
        this.out.println("    jfieldID fid = env->GetFieldID(cls, name, sig);");
        this.out.println("    if (fid == NULL || env->ExceptionCheck()) {");
        this.out.println("        JavaCPP_log(\"Error getting field ID of %s/%s\", JavaCPP_classNames[i], name);");
        this.out.println("        return NULL;");
        this.out.println("    }");
        this.out.println("    return fid;");
        this.out.println("}");
        this.out.println();
        if (declareEnums) {
            this.out.println("static JavaCPP_noinline jfieldID JavaCPP_getFieldID(JNIEnv* env, const char* clsName, const char* name, const char* sig) {");
            this.out.println("    jclass cls = env->FindClass(clsName);");
            this.out.println("    if (cls == NULL || env->ExceptionCheck()) {");
            this.out.println("        JavaCPP_log(\"Error loading class %s.\", clsName);");
            this.out.println("        return NULL;");
            this.out.println("    }");
            this.out.println("    jfieldID fid = env->GetFieldID(cls, name, sig);");
            this.out.println("    if (fid == NULL || env->ExceptionCheck()) {");
            this.out.println("        JavaCPP_log(\"Error getting field ID of %s/%s\", clsName, name);");
            this.out.println("        return NULL;");
            this.out.println("    }");
            this.out.println("    return fid;");
            this.out.println("}");
            this.out.println();
        }
        this.out.println("static JavaCPP_noinline jmethodID JavaCPP_getMethodID(JNIEnv* env, int i, const char* name, const char* sig) {");
        this.out.println("    jclass cls = JavaCPP_getClass(env, i);");
        this.out.println("    if (cls == NULL) {");
        this.out.println("        return NULL;");
        this.out.println("    }");
        this.out.println("    jmethodID mid = env->GetMethodID(cls, name, sig);");
        this.out.println("    if (mid == NULL || env->ExceptionCheck()) {");
        this.out.println("        JavaCPP_log(\"Error getting method ID of %s/%s\", JavaCPP_classNames[i], name);");
        this.out.println("        return NULL;");
        this.out.println("    }");
        this.out.println("    return mid;");
        this.out.println("}");
        this.out.println();
        this.out.println("static JavaCPP_noinline jmethodID JavaCPP_getStaticMethodID(JNIEnv* env, int i, const char* name, const char* sig) {");
        this.out.println("    jclass cls = JavaCPP_getClass(env, i);");
        this.out.println("    if (cls == NULL) {");
        this.out.println("        return NULL;");
        this.out.println("    }");
        this.out.println("    jmethodID mid = env->GetStaticMethodID(cls, name, sig);");
        this.out.println("    if (mid == NULL || env->ExceptionCheck()) {");
        this.out.println("        JavaCPP_log(\"Error getting static method ID of %s/%s\", JavaCPP_classNames[i], name);");
        this.out.println("        return NULL;");
        this.out.println("    }");
        this.out.println("    return mid;");
        this.out.println("}");
        this.out.println();
        this.out.println("static JavaCPP_noinline jobject JavaCPP_createPointer(JNIEnv* env, int i, jclass cls = NULL) {");
        this.out.println("    if (cls == NULL && (cls = JavaCPP_getClass(env, i)) == NULL) {");
        this.out.println("        return NULL;");
        this.out.println("    }");
        this.out.println("    if (JavaCPP_haveAllocObject) {");
        this.out.println("        return env->AllocObject(cls);");
        this.out.println("    } else {");
        this.out.println("        jmethodID mid = env->GetMethodID(cls, \"<init>\", \"(Lorg/bytedeco/javacpp/Pointer;)V\");");
        this.out.println("        if (mid == NULL || env->ExceptionCheck()) {");
        this.out.println("            JavaCPP_log(\"Error getting Pointer constructor of %s, while VM does not support AllocObject()\", JavaCPP_classNames[i]);");
        this.out.println("            return NULL;");
        this.out.println("        }");
        this.out.println("        return env->NewObject(cls, mid, NULL);");
        this.out.println("    }");
        this.out.println("}");
        this.out.println();
        this.out.println("static JavaCPP_noinline void JavaCPP_initPointer(JNIEnv* env, jobject obj, const void* ptr, jlong size, void* owner, void (*deallocator)(void*)) {");
        this.out.println("    if (owner != NULL && deallocator != NULL) {");
        this.out.println("        jvalue args[4];");
        this.out.println("        args[0].j = ptr_to_jlong(ptr);");
        this.out.println("        args[1].j = size;");
        this.out.println("        args[2].j = ptr_to_jlong(owner);");
        this.out.println("        args[3].j = ptr_to_jlong(deallocator);");
        this.out.println("        if (JavaCPP_haveNonvirtual) {");
        this.out.println("            env->CallNonvirtualVoidMethodA(obj, JavaCPP_getClass(env, " + this.jclasses.index(Pointer.class) + "), JavaCPP_initMID, args);");
        this.out.println("        } else {");
        this.out.println("            env->CallVoidMethodA(obj, JavaCPP_initMID, args);");
        this.out.println("        }");
        this.out.println("    } else {");
        this.out.println("        env->SetLongField(obj, JavaCPP_addressFID, ptr_to_jlong(ptr));");
        this.out.println("        env->SetLongField(obj, JavaCPP_limitFID, (jlong)size);");
        this.out.println("        env->SetLongField(obj, JavaCPP_capacityFID, (jlong)size);");
        this.out.println("    }");
        this.out.println("}");
        this.out.println();
        if (handleExceptions || convertStrings) {
            this.out.println("#include <string>");
            this.out.println("static JavaCPP_noinline jstring JavaCPP_createStringFromBytes(JNIEnv* env, const char* ptr, size_t length) {");
            this.out.println("    if (ptr == NULL) {");
            this.out.println("        return NULL;");
            this.out.println("    }");
            this.out.println("#ifdef MODIFIED_UTF8_STRING");
            this.out.println("    return env->NewStringUTF(ptr);");
            this.out.println("#else");
            this.out.println("    jbyteArray bytes = env->NewByteArray(length < INT_MAX ? length : INT_MAX);");
            this.out.println("    env->SetByteArrayRegion(bytes, 0, length < INT_MAX ? length : INT_MAX, (signed char*)ptr);");
            this.out.println("#ifdef STRING_BYTES_CHARSET");
            this.out.println("    jstring s = (jstring)env->NewObject(JavaCPP_getClass(env, " + this.jclasses.index(String.class) + "), JavaCPP_stringWithCharsetMID, bytes, JavaCPP_stringBytesCharset);");
            this.out.println("#else");
            this.out.println("    jstring s = (jstring)env->NewObject(JavaCPP_getClass(env, " + this.jclasses.index(String.class) + "), JavaCPP_stringMID, bytes);");
            this.out.println("#endif // STRING_BYTES_CHARSET");
            this.out.println("    env->DeleteLocalRef(bytes);");
            this.out.println("    return s;");
            this.out.println("#endif // MODIFIED_UTF8_STRING");
            this.out.println("}");
            this.out.println();
            this.out.println("static JavaCPP_noinline jstring JavaCPP_createStringFromBytes(JNIEnv* env, const char* ptr) {");
            this.out.println("    if (ptr == NULL) {");
            this.out.println("        return NULL;");
            this.out.println("    }");
            this.out.println("    return JavaCPP_createStringFromBytes(env, ptr, std::char_traits<char>::length(ptr));");
            this.out.println("}");
            this.out.println();
            this.out.println("static JavaCPP_noinline jstring JavaCPP_createStringFromUTF16(JNIEnv* env, const char16_t* ptr, size_t length) {");
            this.out.println("    if (ptr == NULL) {");
            this.out.println("        return NULL;");
            this.out.println("    }");
            this.out.println("    return env->NewString(reinterpret_cast<const jchar*>(ptr), length);");
            this.out.println("}");
            this.out.println();
            this.out.println("static JavaCPP_noinline jstring JavaCPP_createStringFromUTF16(JNIEnv* env, const char16_t* ptr) {");
            this.out.println("    if (ptr == NULL) {");
            this.out.println("        return NULL;");
            this.out.println("    }");
            this.out.println("    return JavaCPP_createStringFromUTF16(env, ptr, std::char_traits<char16_t>::length(ptr));");
            this.out.println("}");
            this.out.println();
        }
        if (convertStrings) {
            this.out.println("static JavaCPP_noinline const char* JavaCPP_getStringBytes(JNIEnv* env, jstring str) {");
            this.out.println("    if (str == NULL) {");
            this.out.println("        return NULL;");
            this.out.println("    }");
            this.out.println("#ifdef MODIFIED_UTF8_STRING");
            this.out.println("    return env->GetStringUTFChars(str, NULL);");
            this.out.println("#else");
            this.out.println("#ifdef STRING_BYTES_CHARSET");
            this.out.println("    jbyteArray bytes = (jbyteArray)env->CallObjectMethod(str, JavaCPP_getBytesWithCharsetMID, JavaCPP_stringBytesCharset);");
            this.out.println("#else");
            this.out.println("    jbyteArray bytes = (jbyteArray)env->CallObjectMethod(str, JavaCPP_getBytesMID);");
            this.out.println("#endif // STRING_BYTES_CHARSET");
            this.out.println("    if (bytes == NULL || env->ExceptionCheck()) {");
            this.out.println("        JavaCPP_log(\"Error getting bytes from string.\");");
            this.out.println("        return NULL;");
            this.out.println("    }");
            this.out.println("    jsize length = env->GetArrayLength(bytes);");
            this.out.println("    signed char* ptr = new (std::nothrow) signed char[length + 1];");
            this.out.println("    if (ptr != NULL) {");
            this.out.println("        env->GetByteArrayRegion(bytes, 0, length, ptr);");
            this.out.println("        ptr[length] = 0;");
            this.out.println("    }");
            this.out.println("    return (const char*)ptr;");
            this.out.println("#endif // MODIFIED_UTF8_STRING");
            this.out.println("}");
            this.out.println();
            this.out.println("static JavaCPP_noinline void JavaCPP_releaseStringBytes(JNIEnv* env, jstring str, const char* ptr) {");
            this.out.println("#ifdef MODIFIED_UTF8_STRING");
            this.out.println("    if (str != NULL && ptr != NULL) {");
            this.out.println("        env->ReleaseStringUTFChars(str, ptr);");
            this.out.println("    }");
            this.out.println("#else");
            this.out.println("    delete[] ptr;");
            this.out.println("#endif");
            this.out.println("}");
            this.out.println();
            this.out.println("static JavaCPP_noinline const char16_t* JavaCPP_getStringUTF16(JNIEnv* env, jstring str) {");
            this.out.println("    if (str == NULL) {");
            this.out.println("        return NULL;");
            this.out.println("    }");
            this.out.println("    const jsize length = env->GetStringLength(str);");
            this.out.println("    char16_t* ptr = new (std::nothrow) char16_t[length + 1];");
            this.out.println("    if (ptr != NULL) {");
            this.out.println("        env->GetStringRegion(str, 0, length, reinterpret_cast<jchar*>(ptr));");
            this.out.println("        ptr[length] = 0;");
            this.out.println("    }");
            this.out.println("    return ptr;");
            this.out.println("}");
            this.out.println();
            this.out.println("static JavaCPP_noinline void JavaCPP_releaseStringUTF16(JNIEnv*, const char16_t* ptr) {");
            this.out.println("    delete[] ptr;");
            this.out.println("}");
            this.out.println();
        }
        this.out.println("class JavaCPP_hidden JavaCPP_exception : public std::exception {");
        this.out.println("public:");
        this.out.println("    JavaCPP_exception(const char* str) throw() {");
        this.out.println("        if (str == NULL) {");
        this.out.println("            strcpy(msg, \"Unknown exception.\");");
        this.out.println("        } else {");
        this.out.println("            strncpy(msg, str, sizeof(msg));");
        this.out.println("            msg[sizeof(msg) - 1] = 0;");
        this.out.println("        }");
        this.out.println("    }");
        this.out.println("    virtual const char* what() const throw() { return msg; }");
        this.out.println("    char msg[1024];");
        this.out.println("};");
        this.out.println();
        if (handleExceptions) {
            this.out.println("#ifndef GENERIC_EXCEPTION_CLASS");
            this.out.println("#define GENERIC_EXCEPTION_CLASS std::exception");
            this.out.println("#endif");
            this.out.println("#ifndef GENERIC_EXCEPTION_TOSTRING");
            this.out.println("#define GENERIC_EXCEPTION_TOSTRING what()");
            this.out.println("#endif");
            this.out.println("static JavaCPP_noinline jthrowable JavaCPP_handleException(JNIEnv* env, int i) {");
            this.out.println("    jstring str = NULL;");
            this.out.println("    try {");
            this.out.println("        throw;");
            this.out.println("    } catch (GENERIC_EXCEPTION_CLASS& e) {");
            this.out.println("        str = JavaCPP_createStringFromBytes(env, e.GENERIC_EXCEPTION_TOSTRING);");
            this.out.println("    } catch (...) {");
            this.out.println("        str = JavaCPP_createStringFromBytes(env, \"Unknown exception.\");");
            this.out.println("    }");
            this.out.println("    jmethodID mid = JavaCPP_getMethodID(env, i, \"<init>\", \"(Ljava/lang/String;)V\");");
            this.out.println("    if (mid == NULL) {");
            this.out.println("        return NULL;");
            this.out.println("    }");
            this.out.println("    return (jthrowable)env->NewObject(JavaCPP_getClass(env, i), mid, str);");
            this.out.println("}");
            this.out.println();
        }
        try {
            deallocator = Class.forName(Pointer.class.getName() + "$Deallocator", false, Pointer.class.getClassLoader());
            nativeDeallocator = Class.forName(Pointer.class.getName() + "$NativeDeallocator", false, Pointer.class.getClassLoader());
        }
        catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        if (defineAdapters) {
            this.out.println("static JavaCPP_noinline void* JavaCPP_getPointerOwner(JNIEnv* env, jobject obj) {");
            this.out.println("    if (obj != NULL) {");
            this.out.println("        jobject deallocator = env->GetObjectField(obj, JavaCPP_deallocatorFID);");
            this.out.println("        if (deallocator != NULL && env->IsInstanceOf(deallocator, JavaCPP_getClass(env, " + this.jclasses.index(nativeDeallocator) + "))) {");
            this.out.println("            return jlong_to_ptr(env->GetLongField(deallocator, JavaCPP_ownerAddressFID));");
            this.out.println("        }");
            this.out.println("    }");
            this.out.println("    return NULL;");
            this.out.println("}");
            this.out.println();
            this.out.println("#include <vector>");
            this.out.println("template<typename P, typename T = P, typename A = std::allocator<T> > class JavaCPP_hidden VectorAdapter {");
            this.out.println("public:");
            this.out.println("    VectorAdapter(const P* ptr, typename std::vector<T,A>::size_type size, void* owner) : ptr((P*)ptr), size(size), owner(owner),");
            this.out.println("        vec2(ptr ? std::vector<T,A>((P*)ptr, (P*)ptr + size) : std::vector<T,A>()), vec(vec2) { }");
            this.out.println("    VectorAdapter(const std::vector<T,A>& vec) : ptr(0), size(0), owner(0), vec2(vec), vec(vec2) { }");
            this.out.println("    VectorAdapter(      std::vector<T,A>& vec) : ptr(0), size(0), owner(0), vec(vec) { }");
            this.out.println("    VectorAdapter(const std::vector<T,A>* vec) : ptr(0), size(0), owner(0), vec(*(std::vector<T,A>*)vec) { }");
            this.out.println("    void assign(P* ptr, typename std::vector<T,A>::size_type size, void* owner) {");
            this.out.println("        this->ptr = ptr;");
            this.out.println("        this->size = size;");
            this.out.println("        this->owner = owner;");
            this.out.println("        vec.assign(ptr, ptr + size);");
            this.out.println("    }");
            this.out.println("    static void deallocate(void* owner) { operator delete(owner); }");
            this.out.println("    operator P*() {");
            this.out.println("        if (vec.size() > size) {");
            this.out.println("            ptr = (P*)(operator new(sizeof(P) * vec.size(), std::nothrow_t()));");
            this.out.println("        }");
            this.out.println("        if (ptr) {");
            this.out.println("            std::uninitialized_copy(vec.begin(), vec.end(), ptr);");
            this.out.println("        }");
            this.out.println("        size = vec.size();");
            this.out.println("        owner = ptr;");
            this.out.println("        return ptr;");
            this.out.println("    }");
            this.out.println("    operator const P*()        { size = vec.size(); return &vec[0]; }");
            this.out.println("    operator std::vector<T,A>&() { return vec; }");
            this.out.println("    operator std::vector<T,A>*() { return ptr ? &vec : 0; }");
            this.out.println("    P* ptr;");
            this.out.println("    typename std::vector<T,A>::size_type size;");
            this.out.println("    void* owner;");
            this.out.println("    std::vector<T,A> vec2;");
            this.out.println("    std::vector<T,A>& vec;");
            this.out.println("};");
            this.out.println();
            this.out.println("#include <string>");
            this.out.println("template<typename T = char> class JavaCPP_hidden StringAdapter {");
            this.out.println("public:");
            this.out.println("    StringAdapter(const          char* ptr, typename std::basic_string<T>::size_type size, void* owner) : ptr((T*)ptr), size(size), owner(owner),");
            this.out.println("        str2(ptr ? (T*)ptr : \"\", ptr ? (size > 0 ? size : strlen((char*)ptr)) : 0), str(str2) { }");
            this.out.println("    StringAdapter(const signed   char* ptr, typename std::basic_string<T>::size_type size, void* owner) : ptr((T*)ptr), size(size), owner(owner),");
            this.out.println("        str2(ptr ? (T*)ptr : \"\", ptr ? (size > 0 ? size : strlen((char*)ptr)) : 0), str(str2) { }");
            this.out.println("    StringAdapter(const unsigned char* ptr, typename std::basic_string<T>::size_type size, void* owner) : ptr((T*)ptr), size(size), owner(owner),");
            this.out.println("        str2(ptr ? (T*)ptr : \"\", ptr ? (size > 0 ? size : strlen((char*)ptr)) : 0), str(str2) { }");
            this.out.println("    StringAdapter(const       wchar_t* ptr, typename std::basic_string<T>::size_type size, void* owner) : ptr((T*)ptr), size(size), owner(owner),");
            this.out.println("        str2(ptr ? (T*)ptr : L\"\", ptr ? (size > 0 ? size : wcslen((wchar_t*)ptr)) : 0), str(str2) { }");
            this.out.println("    StringAdapter(const char16_t* ptr, typename std::basic_string<T>::size_type size, void* owner) : ptr((T*)ptr), size(size), owner(owner),");
            this.out.println("        str2(ptr ? (T*)ptr : L\"\", ptr ? (size > 0 ? size : wcslen((wchar_t*)ptr)) : 0), str(str2) { }");
            this.out.println("    StringAdapter(const   signed   int* ptr, typename std::basic_string<T>::size_type size, void* owner) : ptr((T*)ptr), size(size), owner(owner),");
            this.out.println("        str2(ptr ? (T*)ptr : L\"\", ptr ? (size > 0 ? size : wcslen((wchar_t*)ptr)) : 0), str(str2) { }");
            this.out.println("    StringAdapter(const std::basic_string<T>& str) : ptr(0), size(0), owner(0), str2(str), str(str2) { }");
            this.out.println("    StringAdapter(      std::basic_string<T>& str) : ptr(0), size(0), owner(0), str(str) { }");
            this.out.println("    StringAdapter(const std::basic_string<T>* str) : ptr(0), size(0), owner(0), str(*(std::basic_string<T>*)str) { }");
            this.out.println("    void assign(char* ptr, typename std::basic_string<T>::size_type size, void* owner) {");
            this.out.println("        this->ptr = ptr;");
            this.out.println("        this->size = size;");
            this.out.println("        this->owner = owner;");
            this.out.println("        str.assign(ptr ? ptr : \"\", ptr ? (size > 0 ? size : strlen((char*)ptr)) : 0);");
            this.out.println("    }");
            this.out.println("    void assign(const          char* ptr, typename std::basic_string<T>::size_type size, void* owner) { assign((char*)ptr, size, owner); }");
            this.out.println("    void assign(const signed   char* ptr, typename std::basic_string<T>::size_type size, void* owner) { assign((char*)ptr, size, owner); }");
            this.out.println("    void assign(const unsigned char* ptr, typename std::basic_string<T>::size_type size, void* owner) { assign((char*)ptr, size, owner); }");
            this.out.println("    void assign(wchar_t* ptr, typename std::basic_string<T>::size_type size, void* owner) {");
            this.out.println("        this->ptr = ptr;");
            this.out.println("        this->size = size;");
            this.out.println("        this->owner = owner;");
            this.out.println("        str.assign(ptr ? ptr : L\"\", ptr ? (size > 0 ? size : wcslen((wchar_t*)ptr)) : 0);");
            this.out.println("    }");
            this.out.println("    void assign(const        wchar_t* ptr, typename std::basic_string<T>::size_type size, void* owner) { assign((wchar_t*)ptr, size, owner); }");
            this.out.println("    void assign(const char16_t* ptr, typename std::basic_string<T>::size_type size, void* owner) { assign((wchar_t*)ptr, size, owner); }");
            this.out.println("    void assign(const   signed   int* ptr, typename std::basic_string<T>::size_type size, void* owner) { assign((wchar_t*)ptr, size, owner); }");
            this.out.println("    static void deallocate(void* owner) { delete[] (T*)owner; }");
            this.out.println("    operator char*() {");
            this.out.println("        const char* data = str.data();");
            this.out.println("        if (str.size() > size) {");
            this.out.println("            ptr = new (std::nothrow) char[str.size()+1];");
            this.out.println("            if (ptr) memset(ptr, 0, str.size()+1);");
            this.out.println("        }");
            this.out.println("        if (ptr && memcmp(ptr, data, str.size()) != 0) {");
            this.out.println("            memcpy(ptr, data, str.size());");
            this.out.println("            if (size > str.size()) ptr[str.size()] = 0;");
            this.out.println("        }");
            this.out.println("        size = str.size();");
            this.out.println("        owner = ptr;");
            this.out.println("        return ptr;");
            this.out.println("    }");
            this.out.println("    operator       signed   char*() { return (signed   char*)(operator char*)(); }");
            this.out.println("    operator       unsigned char*() { return (unsigned char*)(operator char*)(); }");
            this.out.println("    operator const          char*() { size = str.size(); return                 str.c_str(); }");
            this.out.println("    operator const signed   char*() { size = str.size(); return (signed   char*)str.c_str(); }");
            this.out.println("    operator const unsigned char*() { size = str.size(); return (unsigned char*)str.c_str(); }");
            this.out.println("    operator wchar_t*() {");
            this.out.println("        const wchar_t* data = str.data();");
            this.out.println("        if (str.size() > size) {");
            this.out.println("            ptr = new (std::nothrow) wchar_t[str.size()+1];");
            this.out.println("            if (ptr) memset(ptr, 0, sizeof(wchar_t) * (str.size()+1));");
            this.out.println("        }");
            this.out.println("        if (ptr && memcmp(ptr, data, sizeof(wchar_t) * str.size()) != 0) {");
            this.out.println("            memcpy(ptr, data, sizeof(wchar_t) * str.size());");
            this.out.println("            if (size > str.size()) ptr[str.size()] = 0;");
            this.out.println("        }");
            this.out.println("        size = str.size();");
            this.out.println("        owner = ptr;");
            this.out.println("        return ptr;");
            this.out.println("    }");
            this.out.println("    operator         char16_t*() { return (char16_t*)(operator wchar_t*)(); }");
            this.out.println("    operator       signed int*() { return (signed int*)(operator wchar_t*)(); }");
            this.out.println("    operator const    wchar_t*() { size = str.size(); return              str.c_str(); }");
            this.out.println("    operator const   char16_t*() { size = str.size(); return   (char16_t*)str.c_str(); }");
            this.out.println("    operator const signed int*() { size = str.size(); return (signed int*)str.c_str(); }");
            this.out.println("    operator std::basic_string<T>&() { return str; }");
            this.out.println("    operator std::basic_string<T>*() { return ptr ? &str : 0; }");
            this.out.println("    T* ptr;");
            this.out.println("    typename std::basic_string<T>::size_type size;");
            this.out.println("    void* owner;");
            this.out.println("    std::basic_string<T> str2;");
            this.out.println("    std::basic_string<T>& str;");
            this.out.println("};");
            this.out.println();
            this.out.println("template<typename P, typename T = P> class JavaCPP_hidden BasicStringAdapter {");
            this.out.println("public:");
            this.out.println("    BasicStringAdapter(const P* ptr, typename std::basic_string<T>::size_type size, void* owner) : str(str2) { assign(const_cast<P*>(ptr), size, owner); }");
            this.out.println();
            this.out.println("    BasicStringAdapter(const std::basic_string<T>& str) : size(0), owner(NULL), ptr(NULL), str2(str), str(str2) { }");
            this.out.println("    BasicStringAdapter(      std::basic_string<T>& str) : size(0), owner(NULL), ptr(NULL), str(str) { }");
            this.out.println("    BasicStringAdapter(const std::basic_string<T>* str) : size(0), owner(NULL), ptr(NULL), str(*const_cast<std::basic_string<T>*>(str)) { }");
            this.out.println();
            this.out.println("    static void deallocate(void* owner) { delete[] static_cast<T*>(owner); }");
            this.out.println();
            this.out.println("    operator P*() {");
            this.out.println("        const T* data = str.data();");
            this.out.println("        if (str.size() > size) {");
            this.out.println("            ptr = new (std::nothrow) T[str.size() + 1]();");
            this.out.println("        }");
            this.out.println("        if (ptr && memcmp(ptr, data, sizeof(T) * str.size()) != 0) {");
            this.out.println("            memcpy(ptr, data, sizeof(T) * str.size());");
            this.out.println("            if (size > str.size()) ptr[str.size()] = 0;");
            this.out.println("        }");
            this.out.println("        size = str.size();");
            this.out.println("        owner = ptr;");
            this.out.println("        return reinterpret_cast<P*>(ptr);");
            this.out.println("    }");
            this.out.println("    operator const P*() {");
            this.out.println("        size = str.size();");
            this.out.println("        return reinterpret_cast<const P*>(str.c_str());");
            this.out.println("    }");
            this.out.println();
            this.out.println("    operator std::basic_string<T>&() { return str; }");
            this.out.println("    operator std::basic_string<T>*() { return ptr ? &str : NULL; }");
            this.out.println();
            this.out.println("    void assign(P* ptr, typename std::basic_string<T>::size_type size, void* owner) {");
            this.out.println("        this->ptr = reinterpret_cast<T*>(ptr);");
            this.out.println("        this->size = size;");
            this.out.println("        this->owner = owner;");
            this.out.println("        if (this->ptr) {");
            this.out.println("            str.assign(this->ptr, size > 0 ? size : std::char_traits<T>::length(this->ptr));");
            this.out.println("        } else {");
            this.out.println("            str.clear();");
            this.out.println("        }");
            this.out.println("    }");
            this.out.println();
            this.out.println("    typename std::basic_string<T>::size_type size;");
            this.out.println("    void* owner;");
            this.out.println();
            this.out.println("private:");
            this.out.println("    T* ptr;");
            this.out.println("    std::basic_string<T> str2;");
            this.out.println("    std::basic_string<T>& str;");
            this.out.println("};");
            this.out.println();
            this.out.println("#if __cplusplus >= 201103L || _MSC_VER >= 1900");
            this.out.println("#include <utility>");
            this.out.println("#ifndef SHARED_PTR_NAMESPACE");
            this.out.println("#define SHARED_PTR_NAMESPACE std");
            this.out.println("#endif");
            this.out.println("#ifndef UNIQUE_PTR_NAMESPACE");
            this.out.println("#define UNIQUE_PTR_NAMESPACE std");
            this.out.println("#endif");
            this.out.println("#ifndef MOVE_NAMESPACE");
            this.out.println("#define MOVE_NAMESPACE std");
            this.out.println("#endif");
            this.out.println("#endif");
            this.out.println();
            this.out.println("#if __cplusplus >= 201703L");
            this.out.println("#include <optional>");
            this.out.println("#ifndef OPTIONAL_NAMESPACE");
            this.out.println("#define OPTIONAL_NAMESPACE std");
            this.out.println("#endif");
            this.out.println("#endif");
            this.out.println();
            this.out.println("#ifdef SHARED_PTR_NAMESPACE");
            this.out.println("template<class T> class SharedPtrAdapter {");
            this.out.println("public:");
            this.out.println("    typedef SHARED_PTR_NAMESPACE::shared_ptr<T> S;");
            this.out.println("    SharedPtrAdapter(const T* ptr, size_t size, void* owner) : ptr((T*)ptr), size(size), owner(owner),");
            this.out.println("            sharedPtr2(owner != NULL && owner != ptr ? *(S*)owner : S((T*)ptr)), sharedPtr(sharedPtr2) { }");
            this.out.println("    SharedPtrAdapter(const S& sharedPtr) : ptr(0), size(0), owner(0), sharedPtr2(sharedPtr), sharedPtr(sharedPtr2) { }");
            this.out.println("    SharedPtrAdapter(      S& sharedPtr) : ptr(0), size(0), owner(0), sharedPtr(sharedPtr) { }");
            this.out.println("    SharedPtrAdapter(const S* sharedPtr) : ptr(0), size(0), owner(0), sharedPtr(*(S*)sharedPtr) { }");
            this.out.println("    void assign(T* ptr, size_t size, void* owner) {");
            this.out.println("        this->ptr = ptr;");
            this.out.println("        this->size = size;");
            this.out.println("        this->owner = owner;");
            this.out.println("        this->sharedPtr = owner != NULL && owner != ptr ? *(S*)owner : S((T*)ptr);");
            this.out.println("    }");
            this.out.println("    static void deallocate(void* owner) { delete (S*)owner; }");
            this.out.println("    operator typename SHARED_PTR_NAMESPACE::remove_const<T>::type*() {");
            this.out.println("        ptr = sharedPtr.get();");
            this.out.println("        if (owner == NULL || owner == ptr) {");
            this.out.println("            owner = new S(sharedPtr);");
            this.out.println("        }");
            this.out.println("        return (typename SHARED_PTR_NAMESPACE::remove_const<T>::type*)ptr;");
            this.out.println("    }");
            this.out.println("    operator S&() { return sharedPtr; }");
            this.out.println("    operator S*() { return &sharedPtr; }");
            this.out.println("    T* ptr;");
            this.out.println("    size_t size;");
            this.out.println("    void* owner;");
            this.out.println("    S sharedPtr2;");
            this.out.println("    S& sharedPtr;");
            this.out.println("};");
            this.out.println("#endif");
            this.out.println();
            this.out.println("#ifdef UNIQUE_PTR_NAMESPACE");
            this.out.println("template<class T, class D = UNIQUE_PTR_NAMESPACE::default_delete<T> > class UniquePtrAdapter {");
            this.out.println("public:");
            this.out.println("    typedef UNIQUE_PTR_NAMESPACE::unique_ptr<T,D> U;");
            this.out.println("    UniquePtrAdapter(const T* ptr, size_t size, void* owner) : ptr((T*)ptr), size(size), owner(owner),");
            this.out.println("            uniquePtr2(owner != NULL && owner != ptr ? U() : U((T*)ptr)),");
            this.out.println("            uniquePtr(owner != NULL && owner != ptr ? *(U*)owner : uniquePtr2) { }");
            this.out.println("    UniquePtrAdapter(U&& uniquePtr) : ptr(0), size(0), owner(0), uniquePtr2(UNIQUE_PTR_NAMESPACE::move(uniquePtr)), uniquePtr(uniquePtr2) { }");
            this.out.println("    UniquePtrAdapter(const U& uniquePtr) : ptr(0), size(0), owner(0), uniquePtr2(U(NULL, D())), uniquePtr((U&)uniquePtr) { }");
            this.out.println("    UniquePtrAdapter(      U& uniquePtr) : ptr(0), size(0), owner(0), uniquePtr2(U(NULL, D())), uniquePtr(uniquePtr) { }");
            this.out.println("    UniquePtrAdapter(const U* uniquePtr) : ptr(0), size(0), owner(0), uniquePtr2(U(NULL, D())), uniquePtr(*(U*)uniquePtr) { }");
            this.out.println("    void assign(T* ptr, size_t size, void* owner) {");
            this.out.println("        if (this->ptr == ptr && this->size == size && this->owner == owner) return;");
            this.out.println("        this->ptr = ptr;");
            this.out.println("        this->size = size;");
            this.out.println("        this->owner = owner;");
            this.out.println("        this->uniquePtr = owner != NULL && owner != ptr ? (U&&)*(U*)owner : U((T*)NULL);");
            this.out.println("    }");
            this.out.println("    static void deallocate(void* owner) { delete (U*)owner; }");
            this.out.println("    operator typename UNIQUE_PTR_NAMESPACE::remove_const<T>::type*() {");
            this.out.println("        ptr = uniquePtr.get();");
            this.out.println("        if (ptr == uniquePtr2.get() && (owner == NULL || owner == ptr)) {");
            this.out.println("            // only move the pointer if we actually own it through uniquePtr2");
            this.out.println("            owner = new U(UNIQUE_PTR_NAMESPACE::move(uniquePtr));");
            this.out.println("        }");
            this.out.println("        return (typename UNIQUE_PTR_NAMESPACE::remove_const<T>::type*)ptr;");
            this.out.println("    }");
            this.out.println("    operator U&() const { return uniquePtr; }");
            this.out.println("    operator U&&() { owner = NULL; return UNIQUE_PTR_NAMESPACE::move(uniquePtr); }");
            this.out.println("    operator U*() { return &uniquePtr; }");
            this.out.println("    T* ptr;");
            this.out.println("    size_t size;");
            this.out.println("    void* owner;");
            this.out.println("    U uniquePtr2;");
            this.out.println("    U& uniquePtr;");
            this.out.println("};");
            this.out.println("#endif");
            this.out.println("");
            this.out.println("#ifdef MOVE_NAMESPACE");
            this.out.println("template<class T> class MoveAdapter {");
            this.out.println("public:");
            this.out.println("    MoveAdapter(const T* ptr, size_t size, void* owner) : ptr(&movedPtr), size(size), owner(owner), movedPtr(MOVE_NAMESPACE::move(*(T*)ptr)) { }");
            this.out.println("    MoveAdapter(const T& ptr) : ptr(&movedPtr), size(0), owner(0), movedPtr(MOVE_NAMESPACE::move((T&)ptr)) { }");
            this.out.println("    MoveAdapter(T&& ptr) : ptr(&movedPtr), size(0), owner(0), movedPtr((T&&)ptr) { }");
            this.out.println("    void assign(T* ptr, size_t size, void* owner) {");
            this.out.println("        this->ptr = &this->movedPtr;");
            this.out.println("        this->size = size;");
            this.out.println("        this->owner = owner;");
            this.out.println("        this->movedPtr = MOVE_NAMESPACE::move(*ptr);");
            this.out.println("    }");
            this.out.println("    static void deallocate(void* owner) { delete (T*)owner; }");
            this.out.println("    operator T*() {");
            this.out.println("        ptr = new T(MOVE_NAMESPACE::move(movedPtr));");
            this.out.println("        owner = ptr;");
            this.out.println("        return ptr;");
            this.out.println("    }");
            this.out.println("    operator const T*() { return ptr; }");
            this.out.println("    operator T&&() { owner = NULL; return MOVE_NAMESPACE::move(movedPtr); }");
            this.out.println("    T* ptr;");
            this.out.println("    size_t size;");
            this.out.println("    void* owner;");
            this.out.println("    T movedPtr;");
            this.out.println("};");
            this.out.println("#endif");
            this.out.println();
            this.out.println("#ifdef OPTIONAL_NAMESPACE");
            this.out.println("template<class T> class OptionalAdapter {");
            this.out.println("public:");
            this.out.println("    typedef OPTIONAL_NAMESPACE::optional<T> O;");
            this.out.println("    OptionalAdapter(const T* ptr, size_t size, void* owner) : ptr((T*)ptr), size(size), owner(owner),");
            this.out.println("            optional2(owner != NULL && owner != ptr ? *(O*)owner : (ptr ? O(*(T*)ptr) : O())), optional(optional2) { }");
            this.out.println("    OptionalAdapter(const O& optional) : ptr(0), size(0), owner(0), optional2(optional), optional(optional2) { }");
            this.out.println("    OptionalAdapter(      O& optional) : ptr(0), size(0), owner(0), optional(optional) { }");
            this.out.println("    OptionalAdapter(const O* optional) : ptr(0), size(0), owner(0), optional(*(O*)optional) { }");
            this.out.println("    void assign(T* ptr, size_t size, void* owner) {");
            this.out.println("        this->ptr = ptr;");
            this.out.println("        this->size = size;");
            this.out.println("        this->owner = owner;");
            this.out.println("        this->optional = owner != NULL && owner != ptr ? *(O*)owner : (ptr ? O(*(T*)ptr) : O());");
            this.out.println("    }");
            this.out.println("    static void deallocate(void* owner) { delete (O*)owner; }");
            this.out.println("    operator typename OPTIONAL_NAMESPACE::remove_const<T>::type*() {");
            this.out.println("        if (owner == NULL || owner == ptr) {");
            this.out.println("            owner = new O(optional);");
            this.out.println("        }");
            this.out.println("        ptr = (*(O*)owner).has_value() ? (*(O*)owner).operator->() : NULL;");
            this.out.println("        return (typename OPTIONAL_NAMESPACE::remove_const<T>::type*)ptr;");
            this.out.println("    }");
            this.out.println("    operator O&() { return optional; }");
            this.out.println("    operator O*() { return &optional; }");
            this.out.println("    T* ptr;");
            this.out.println("    size_t size;");
            this.out.println("    void* owner;");
            this.out.println("    O optional2;");
            this.out.println("    O& optional;");
            this.out.println("};");
            this.out.println("#endif");
        }
        if (!this.functions.isEmpty() || !this.virtualFunctions.isEmpty()) {
            this.out.println("#if !defined(NO_JNI_DETACH_THREAD) && (defined(__linux__) || defined(__APPLE__) || defined(__FreeBSD__))");
            this.out.println("  static pthread_once_t JavaCPP_once = PTHREAD_ONCE_INIT;");
            this.out.println("#endif");
            this.out.println();
            this.out.println("static JavaCPP_noinline void JavaCPP_detach(bool detach) {");
            this.out.println("#if !defined(NO_JNI_DETACH_THREAD) && !defined(__linux__) && !defined(__APPLE__) && !defined(_WIN32)");
            this.out.println("    if (detach && JavaCPP_vm->DetachCurrentThread() != JNI_OK) {");
            this.out.println("        JavaCPP_log(\"Could not detach the JavaVM from the current thread.\");");
            this.out.println("    }");
            this.out.println("#endif");
            this.out.println("}");
            this.out.println();
            if (!loadSuffix.isEmpty()) {
                this.out.println("extern \"C\" {");
                this.out.println("JNIEXPORT jint JNICALL JNI_OnLoad" + loadSuffix + "(JavaVM* vm, void* reserved);");
                this.out.println("}");
            }
            this.out.println("static JavaCPP_noinline bool JavaCPP_getEnv(JNIEnv** env) {");
            this.out.println("    bool attached = false;");
            this.out.println("    JavaVM *vm = JavaCPP_vm;");
            this.out.println("    if (vm == NULL) {");
            if (this.out2 != null) {
                this.out.println("#if !defined(__ANDROID__) && !TARGET_OS_IPHONE");
                this.out.println("        int size = 1;");
                this.out.println("        if (JNI_GetCreatedJavaVMs(&vm, 1, &size) != JNI_OK || size == 0) {");
                this.out.println("#endif");
            }
            this.out.println("            JavaCPP_log(\"Could not get any created JavaVM.\");");
            this.out.println("            *env = NULL;");
            this.out.println("            return false;");
            if (this.out2 != null) {
                this.out.println("#if !defined(__ANDROID__) && !TARGET_OS_IPHONE");
                this.out.println("        }");
                this.out.println("#endif");
            }
            this.out.println("    }");
            this.out.println("#if !defined(NO_JNI_DETACH_THREAD) && defined(_WIN32)");
            this.out.println("    if ((*env = JavaCPP_thread_local.env) != NULL) {");
            this.out.println("        attached = true;");
            this.out.println("        goto done;");
            this.out.println("    }");
            this.out.println("#elif !defined(NO_JNI_DETACH_THREAD) && (defined(__linux__) || defined(__APPLE__) || defined(__FreeBSD__))");
            this.out.println("    pthread_once(&JavaCPP_once, JavaCPP_create_pthread_key);");
            this.out.println("    if ((*env = (JNIEnv *)pthread_getspecific(JavaCPP_current_env)) != NULL) {");
            this.out.println("        attached = true;");
            this.out.println("        goto done;");
            this.out.println("    }");
            this.out.println("#endif");
            this.out.println("    if (vm->GetEnv((void**)env, JNI_VERSION_1_6) != JNI_OK) {");
            this.out.println("        struct {");
            this.out.println("            JNIEnv **env;");
            this.out.println("            operator JNIEnv**() { return env; } // Android JNI");
            this.out.println("            operator void**() { return (void**)env; } // standard JNI");
            this.out.println("        } env2 = { env };");
            this.out.println("        JavaVMAttachArgs args;");
            this.out.println("        args.version = JNI_VERSION_1_6;");
            this.out.println("        args.group = NULL;");
            this.out.println("        char name[64] = {0};");
            this.out.println("#ifdef _WIN32");
            this.out.println("        sprintf(name, \"JavaCPP Thread ID %lu\", GetCurrentThreadId());");
            this.out.println("#elif defined(__APPLE__)");
            this.out.println("        sprintf(name, \"JavaCPP Thread ID %u\", pthread_mach_thread_np(pthread_self()));");
            this.out.println("#elif defined(__linux__)");
            this.out.println("        sprintf(name, \"JavaCPP Thread ID %ld\", syscall(SYS_gettid));");
            this.out.println("#elif defined(__FreeBSD__)");
            this.out.println("        sprintf(name, \"JavaCPP Thread ID %ld\", (long)pthread_getthreadid_np());");
            this.out.println("#endif");
            this.out.println("        args.name = name;");
            this.out.println("        if (vm->AttachCurrentThreadAsDaemon(env2, &args) != JNI_OK) {");
            this.out.println("            JavaCPP_log(\"Could not attach the JavaVM to the current thread.\");");
            this.out.println("            *env = NULL;");
            this.out.println("            goto done;");
            this.out.println("        }");
            this.out.println("#if !defined(NO_JNI_DETACH_THREAD) && defined(_WIN32)");
            this.out.println("        JavaCPP_thread_local.env = *env;");
            this.out.println("#elif !defined(NO_JNI_DETACH_THREAD) && (defined(__linux__) || defined(__APPLE__))");
            this.out.println("        pthread_setspecific(JavaCPP_current_env, *env);");
            this.out.println("#endif");
            this.out.println("        attached = true;");
            this.out.println("    }");
            this.out.println("    if (JavaCPP_vm == NULL) {");
            this.out.println("        if (JNI_OnLoad" + loadSuffix + "(vm, NULL) < 0) {");
            this.out.println("            JavaCPP_detach(attached);");
            this.out.println("            *env = NULL;");
            this.out.println("            goto done;");
            this.out.println("        }");
            this.out.println("    }");
            this.out.println("done:");
            this.out.println("    return attached;");
            this.out.println("}");
            this.out.println();
        }
        for (Class c : this.functions) {
            String[] typeName2 = this.cppTypeName(c);
            String[] returnConvention = typeName2[0].split("\\(");
            String[] stringArray = new String[]{returnConvention[0] + (returnConvention.length > 2 ? "(*" : ""), returnConvention.length > 2 ? ")(" + returnConvention[2] : ""};
            if (returnConvention.length > 2) {
                returnConvention = Arrays.copyOfRange(returnConvention, 2, returnConvention.length);
            }
            returnConvention[1] = Generator.constValueTypeName(returnConvention[1]);
            String parameterDeclaration = typeName2[1].substring(1);
            String instanceTypeName = Generator.functionClassName(c);
            this.out.println("struct JavaCPP_hidden " + instanceTypeName + " {");
            this.out.println("    " + instanceTypeName + "() : ptr(NULL), obj(NULL) { }");
            if (parameterDeclaration != null && parameterDeclaration.length() > 0) {
                this.out.println("    " + stringArray[0] + "operator()" + parameterDeclaration + stringArray[1] + ";");
            }
            this.out.println("    " + stringArray[0] + "(" + returnConvention[1] + "*ptr)" + parameterDeclaration + stringArray[1] + ";");
            this.out.println("    jobject obj; static jmethodID mid;");
            this.out.println("};");
            this.out.println("jmethodID " + instanceTypeName + "::mid = NULL;");
        }
        this.out.println();
        for (Class c : this.jclasses) {
            Set<String> functionList = this.virtualFunctions.get(c);
            if (functionList == null) continue;
            Set<String> memberList = this.virtualMembers.get(c);
            String[] stringArray = this.cppTypeName(c);
            String valueTypeName2 = Generator.valueTypeName(stringArray);
            String subType = "JavaCPP_" + Generator.mangle(valueTypeName2);
            this.out.println("class JavaCPP_hidden " + subType + " : public " + valueTypeName2 + " {");
            this.out.println("public:");
            this.out.println("    jobject obj;");
            for (String s : functionList) {
                this.out.println("    static jmethodID " + s + ";");
            }
            this.out.println();
            for (String s : memberList) {
                this.out.println(s);
            }
            this.out.println("};");
            for (String s : functionList) {
                this.out.println("jmethodID " + subType + "::" + s + " = NULL;");
            }
        }
        this.out.println();
        for (String s : this.callbacks.values()) {
            this.out.println(s);
        }
        this.out.println();
        for (Class c : this.deallocators) {
            name = "JavaCPP_" + Generator.mangle(c.getName());
            this.out.print("static void " + name + "_deallocate(void *p) { ");
            if (FunctionPointer.class.isAssignableFrom(c)) {
                typeName = Generator.functionClassName(c);
                if (this.callbacks.containsKey(typeName)) {
                    this.out.print("\n    int n = sizeof(" + (String)typeName + "_instances) / sizeof(" + (String)typeName + "_instances[0]);\n    for (int i = 0; i < n; i++) { if (" + (String)typeName + "_instances[i].obj == ((" + (String)typeName + "*)p)->obj) " + (String)typeName + "_instances[i].obj = NULL; }");
                }
                this.out.println("\n    JNIEnv *e; bool a = JavaCPP_getEnv(&e); if (e != NULL) e->DeleteWeakGlobalRef((jweak)((" + (String)typeName + "*)p)->obj); delete (" + (String)typeName + "*)p; JavaCPP_detach(a); }");
                continue;
            }
            if (this.virtualFunctions.containsKey(c)) {
                typeName = this.cppTypeName(c);
                String string = Generator.valueTypeName(typeName);
                String subType = "JavaCPP_" + Generator.mangle(string);
                this.out.println("JNIEnv *e; bool a = JavaCPP_getEnv(&e); if (e != NULL) e->DeleteWeakGlobalRef((jweak)((" + subType + "*)p)->obj); delete (" + subType + "*)p; JavaCPP_detach(a); }");
                continue;
            }
            typeName = this.cppTypeName(c);
            this.out.println("delete (" + typeName[0] + typeName[1] + ")p; }");
        }
        for (Class c : this.arrayDeallocators) {
            name = "JavaCPP_" + Generator.mangle(c.getName());
            typeName = this.cppTypeName(c);
            this.out.println("static void " + name + "_deallocateArray(void* p) { delete[] (" + typeName[0] + typeName[1] + ")p; }");
        }
        this.out.println();
        this.out.println("static const char* JavaCPP_members[" + this.jclasses.size() + "][" + (maxMemberSize + 1) + "] = {");
        classIterator = this.jclasses.iterator();
        while (classIterator.hasNext()) {
            Iterator<String> memberIterator2;
            this.out.print("        { ");
            m = this.members.get(classIterator.next());
            Iterator<String> iterator = memberIterator2 = m == null ? null : m.iterator();
            if (memberIterator2 == null || !memberIterator2.hasNext()) {
                this.out.print("NULL");
            } else {
                while (memberIterator2.hasNext()) {
                    this.out.print("\"" + memberIterator2.next() + "\"");
                    if (!memberIterator2.hasNext()) continue;
                    this.out.print(", ");
                }
            }
            this.out.print(" }");
            if (!classIterator.hasNext()) continue;
            this.out.println(",");
        }
        this.out.println(" };");
        this.out.println("static int JavaCPP_offsets[" + this.jclasses.size() + "][" + (maxMemberSize + 1) + "] = {");
        classIterator = this.jclasses.iterator();
        while (classIterator.hasNext()) {
            this.out.print("        { ");
            Class c = classIterator.next();
            Set<String> m3 = this.members.get(c);
            Class<?>[] classArray = memberIterator = m3 == null ? null : m3.iterator();
            if (memberIterator == null || !memberIterator.hasNext()) {
                this.out.print("-1");
            } else {
                while (memberIterator.hasNext()) {
                    typeName = this.cppTypeName(c);
                    String string = Generator.valueTypeName(typeName);
                    String memberName = memberIterator.next();
                    if ("sizeof".equals(memberName)) {
                        void var20_37;
                        if ("void".equals(string)) {
                            String string2 = "void*";
                        }
                        this.out.print("sizeof(" + (String)var20_37 + ")");
                    } else {
                        this.out.print("offsetof(" + string + ", " + memberName + ")");
                    }
                    if (!memberIterator.hasNext()) continue;
                    this.out.print(", ");
                }
            }
            this.out.print(" }");
            if (!classIterator.hasNext()) continue;
            this.out.println(",");
        }
        this.out.println(" };");
        this.out.print("static int JavaCPP_memberOffsetSizes[" + this.jclasses.size() + "] = { ");
        classIterator = this.jclasses.iterator();
        while (classIterator.hasNext()) {
            m = this.members.get(classIterator.next());
            this.out.print(m == null ? 1 : m.size());
            if (!classIterator.hasNext()) continue;
            this.out.print(", ");
        }
        this.out.println(" };");
        this.out.println();
        this.out.println("extern \"C\" {");
        if (this.out2 != null) {
            this.out2.println();
            this.out2.println("#ifdef __cplusplus");
            this.out2.println("extern \"C\" {");
            this.out2.println("#endif");
            this.out2.println("JNIIMPORT int JavaCPP_init" + loadSuffix + "(int argc, const char *argv[]);");
            this.out.println();
            this.out.println("JNIEXPORT int JavaCPP_init" + loadSuffix + "(int argc, const char *argv[]) {");
            this.out.println("#if defined(__ANDROID__) || TARGET_OS_IPHONE");
            this.out.println("    return JNI_OK;");
            this.out.println("#else");
            this.out.println("    if (JavaCPP_vm != NULL) {");
            this.out.println("        return JNI_OK;");
            this.out.println("    }");
            this.out.println("    int err;");
            this.out.println("    JavaVM *vm;");
            this.out.println("    JNIEnv *env;");
            this.out.println("    int nOptions = 1 + (argc > 255 ? 255 : argc);");
            this.out.println("    JavaVMOption options[256] = { { NULL } };");
            this.out.println("    options[0].optionString = (char*)\"-Djava.class.path=" + classPath.replace('\\', '/') + "\";");
            this.out.println("    for (int i = 1; i < nOptions && argv != NULL; i++) {");
            this.out.println("        options[i].optionString = (char*)argv[i - 1];");
            this.out.println("    }");
            this.out.println("    JavaVMInitArgs vm_args = { JNI_VERSION_1_6, nOptions, options };");
            this.out.println("    return (err = JNI_CreateJavaVM(&vm, (void**)&env, &vm_args)) == JNI_OK && vm != NULL && (err = JNI_OnLoad" + loadSuffix + "(vm, NULL)) >= 0 ? JNI_OK : err;");
            this.out.println("#endif");
            this.out.println("}");
        }
        if (baseLoadSuffix != null && !baseLoadSuffix.isEmpty()) {
            this.out.println();
            this.out.println("JNIEXPORT jint JNICALL JNI_OnLoad" + baseLoadSuffix + "(JavaVM* vm, void* reserved);");
            this.out.println("JNIEXPORT void JNICALL JNI_OnUnload" + baseLoadSuffix + "(JavaVM* vm, void* reserved);");
        }
        this.out.println();
        this.out.println("JNIEXPORT jint JNICALL JNI_OnLoad" + loadSuffix + "(JavaVM* vm, void* reserved) {");
        if (baseLoadSuffix != null && !baseLoadSuffix.isEmpty()) {
            this.out.println("    if (JNI_OnLoad" + baseLoadSuffix + "(vm, reserved) == JNI_ERR) {");
            this.out.println("        return JNI_ERR;");
            this.out.println("    }");
        }
        this.out.println("    JNIEnv* env;");
        this.out.println("    if (vm->GetEnv((void**)&env, JNI_VERSION_1_6) != JNI_OK) {");
        this.out.println("        JavaCPP_log(\"Could not get JNIEnv for JNI_VERSION_1_6 inside JNI_OnLoad" + loadSuffix + "().\");");
        this.out.println("        return JNI_ERR;");
        this.out.println("    }");
        this.out.println("    if (JavaCPP_vm == vm) {");
        this.out.println("        return env->GetVersion();");
        this.out.println("    }");
        this.out.println("    JavaCPP_vm = vm;");
        this.out.println("    JavaCPP_haveAllocObject = env->functions->AllocObject != NULL;");
        this.out.println("    JavaCPP_haveNonvirtual = env->functions->CallNonvirtualVoidMethodA != NULL;");
        this.out.println("    jmethodID putMemberOffsetMID = JavaCPP_getStaticMethodID(env, " + this.jclasses.index(Loader.class) + ", \"putMemberOffset\", \"(Ljava/lang/String;Ljava/lang/String;I)Ljava/lang/Class;\");");
        this.out.println("    if (putMemberOffsetMID == NULL) {");
        this.out.println("        return JNI_ERR;");
        this.out.println("    }");
        this.out.println("    for (int i = 0; i < " + this.jclasses.size() + " && !env->ExceptionCheck(); i++) {");
        this.out.println("        for (int j = 0; j < JavaCPP_memberOffsetSizes[i] && !env->ExceptionCheck(); j++) {");
        this.out.println("            if (env->PushLocalFrame(3) == 0) {");
        this.out.println("                jvalue args[3];");
        this.out.println("                args[0].l = env->NewStringUTF(JavaCPP_classNames[i]);");
        this.out.println("                args[1].l = JavaCPP_members[i][j] == NULL ? NULL : env->NewStringUTF(JavaCPP_members[i][j]);");
        this.out.println("                args[2].i = JavaCPP_offsets[i][j];");
        this.out.println("                jclass cls = (jclass)env->CallStaticObjectMethodA(JavaCPP_getClass(env, " + this.jclasses.index(Loader.class) + "), putMemberOffsetMID, args);");
        this.out.println("                if (env->ExceptionCheck()) {");
        this.out.println("                    JavaCPP_log(\"Error putting member offsets for class %s.\", JavaCPP_classNames[i]);");
        this.out.println("                    return JNI_ERR;");
        this.out.println("                }");
        this.out.println("                JavaCPP_classes[i] = cls == NULL ? NULL : (jclass)env->NewWeakGlobalRef(cls);");
        this.out.println("                if (env->ExceptionCheck()) {");
        this.out.println("                    JavaCPP_log(\"Error creating global reference of class %s.\", JavaCPP_classNames[i]);");
        this.out.println("                    return JNI_ERR;");
        this.out.println("                }");
        this.out.println("                env->PopLocalFrame(NULL);");
        this.out.println("            }");
        this.out.println("        }");
        this.out.println("    }");
        this.out.println("    JavaCPP_addressFID = JavaCPP_getFieldID(env, " + this.jclasses.index(Pointer.class) + ", \"address\", \"J\");");
        this.out.println("    if (JavaCPP_addressFID == NULL) {");
        this.out.println("        return JNI_ERR;");
        this.out.println("    }");
        this.out.println("    JavaCPP_positionFID = JavaCPP_getFieldID(env, " + this.jclasses.index(Pointer.class) + ", \"position\", \"J\");");
        this.out.println("    if (JavaCPP_positionFID == NULL) {");
        this.out.println("        return JNI_ERR;");
        this.out.println("    }");
        this.out.println("    JavaCPP_limitFID = JavaCPP_getFieldID(env, " + this.jclasses.index(Pointer.class) + ", \"limit\", \"J\");");
        this.out.println("    if (JavaCPP_limitFID == NULL) {");
        this.out.println("        return JNI_ERR;");
        this.out.println("    }");
        this.out.println("    JavaCPP_capacityFID = JavaCPP_getFieldID(env, " + this.jclasses.index(Pointer.class) + ", \"capacity\", \"J\");");
        this.out.println("    if (JavaCPP_capacityFID == NULL) {");
        this.out.println("        return JNI_ERR;");
        this.out.println("    }");
        this.out.println("    JavaCPP_deallocatorFID = JavaCPP_getFieldID(env, " + this.jclasses.index(Pointer.class) + ", \"deallocator\", \"" + Generator.signature(deallocator) + "\");");
        this.out.println("    if (JavaCPP_deallocatorFID == NULL) {");
        this.out.println("        return JNI_ERR;");
        this.out.println("    }");
        this.out.println("    JavaCPP_ownerAddressFID = JavaCPP_getFieldID(env, " + this.jclasses.index(nativeDeallocator) + ", \"ownerAddress\", \"J\");");
        this.out.println("    if (JavaCPP_ownerAddressFID == NULL) {");
        this.out.println("        return JNI_ERR;");
        this.out.println("    }");
        if (declareEnums) {
            this.out.println("    JavaCPP_booleanValueFID = JavaCPP_getFieldID(env, \"" + BooleanEnum.class.getName().replace('.', '/') + "\", \"value\", \"Z\");");
            this.out.println("    if (JavaCPP_booleanValueFID == NULL) {");
            this.out.println("        return JNI_ERR;");
            this.out.println("    }");
            this.out.println("    JavaCPP_byteValueFID = JavaCPP_getFieldID(env, \"" + ByteEnum.class.getName().replace('.', '/') + "\", \"value\", \"B\");");
            this.out.println("    if (JavaCPP_byteValueFID == NULL) {");
            this.out.println("        return JNI_ERR;");
            this.out.println("    }");
            this.out.println("    JavaCPP_shortValueFID = JavaCPP_getFieldID(env, \"" + ShortEnum.class.getName().replace('.', '/') + "\", \"value\", \"S\");");
            this.out.println("    if (JavaCPP_shortValueFID == NULL) {");
            this.out.println("        return JNI_ERR;");
            this.out.println("    }");
            this.out.println("    JavaCPP_intValueFID = JavaCPP_getFieldID(env, \"" + IntEnum.class.getName().replace('.', '/') + "\", \"value\", \"I\");");
            this.out.println("    if (JavaCPP_intValueFID == NULL) {");
            this.out.println("        return JNI_ERR;");
            this.out.println("    }");
            this.out.println("    JavaCPP_longValueFID = JavaCPP_getFieldID(env, \"" + LongEnum.class.getName().replace('.', '/') + "\", \"value\", \"J\");");
            this.out.println("    if (JavaCPP_longValueFID == NULL) {");
            this.out.println("        return JNI_ERR;");
            this.out.println("    }");
        }
        this.out.println("    JavaCPP_initMID = JavaCPP_getMethodID(env, " + this.jclasses.index(Pointer.class) + ", \"init\", \"(JJJJ)V\");");
        this.out.println("    if (JavaCPP_initMID == NULL) {");
        this.out.println("        return JNI_ERR;");
        this.out.println("    }");
        this.out.println("    JavaCPP_arrayMID = JavaCPP_getMethodID(env, " + this.jclasses.index(Buffer.class) + ", \"array\", \"()Ljava/lang/Object;\");");
        this.out.println("    if (JavaCPP_arrayMID == NULL) {");
        this.out.println("        return JNI_ERR;");
        this.out.println("    }");
        this.out.println("    JavaCPP_arrayOffsetMID = JavaCPP_getMethodID(env, " + this.jclasses.index(Buffer.class) + ", \"arrayOffset\", \"()I\");");
        this.out.println("    if (JavaCPP_arrayOffsetMID == NULL) {");
        this.out.println("        return JNI_ERR;");
        this.out.println("    }");
        this.out.println("    JavaCPP_bufferPositionFID = JavaCPP_getFieldID(env, " + this.jclasses.index(Buffer.class) + ", \"position\", \"I\");");
        this.out.println("    if (JavaCPP_bufferPositionFID == NULL) {");
        this.out.println("        return JNI_ERR;");
        this.out.println("    }");
        this.out.println("    JavaCPP_bufferLimitFID = JavaCPP_getFieldID(env, " + this.jclasses.index(Buffer.class) + ", \"limit\", \"I\");");
        this.out.println("    if (JavaCPP_bufferLimitFID == NULL) {");
        this.out.println("        return JNI_ERR;");
        this.out.println("    }");
        this.out.println("    JavaCPP_bufferCapacityFID = JavaCPP_getFieldID(env, " + this.jclasses.index(Buffer.class) + ", \"capacity\", \"I\");");
        this.out.println("    if (JavaCPP_bufferCapacityFID == NULL) {");
        this.out.println("        return JNI_ERR;");
        this.out.println("    }");
        this.out.println("    JavaCPP_stringMID = JavaCPP_getMethodID(env, " + this.jclasses.index(String.class) + ", \"<init>\", \"([B)V\");");
        this.out.println("    if (JavaCPP_stringMID == NULL) {");
        this.out.println("        return JNI_ERR;");
        this.out.println("    }");
        this.out.println("    JavaCPP_getBytesMID = JavaCPP_getMethodID(env, " + this.jclasses.index(String.class) + ", \"getBytes\", \"()[B\");");
        this.out.println("    if (JavaCPP_getBytesMID == NULL) {");
        this.out.println("        return JNI_ERR;");
        this.out.println("    }");
        this.out.println("    JavaCPP_toStringMID = JavaCPP_getMethodID(env, " + this.jclasses.index(Object.class) + ", \"toString\", \"()Ljava/lang/String;\");");
        this.out.println("    if (JavaCPP_toStringMID == NULL) {");
        this.out.println("        return JNI_ERR;");
        this.out.println("    }");
        this.out.println("#ifdef STRING_BYTES_CHARSET");
        this.out.println("    jmethodID charsetForNameMID = JavaCPP_getStaticMethodID(env, " + this.jclasses.index(Charset.class) + ", \"forName\", \"(Ljava/lang/String;)Ljava/nio/charset/Charset;\");");
        this.out.println("    if (charsetForNameMID == NULL) {");
        this.out.println("        return JNI_ERR;");
        this.out.println("    }");
        this.out.println("    jstring charsetName = env->NewStringUTF(STRING_BYTES_CHARSET);");
        this.out.println("    if (charsetName == NULL || env->ExceptionCheck()) {");
        this.out.println("        JavaCPP_log(\"Error creating java.lang.String from '%s'\", STRING_BYTES_CHARSET);");
        this.out.println("        return JNI_ERR;");
        this.out.println("    }");
        this.out.println("    JavaCPP_stringBytesCharset = env->CallStaticObjectMethod(JavaCPP_getClass(env, " + this.jclasses.index(Charset.class) + "), charsetForNameMID, charsetName);");
        this.out.println("    if (JavaCPP_stringBytesCharset == NULL || env->ExceptionCheck()) {");
        this.out.println("        JavaCPP_log(\"Error when calling Charset.forName() for '%s'\", STRING_BYTES_CHARSET);");
        this.out.println("        return JNI_ERR;");
        this.out.println("    }");
        this.out.println("    JavaCPP_stringBytesCharset = env->NewGlobalRef(JavaCPP_stringBytesCharset);");
        this.out.println("    if (JavaCPP_stringBytesCharset == NULL) {");
        this.out.println("        JavaCPP_log(\"Error creating global reference for java.nio.charset.Charset instance\");");
        this.out.println("        return JNI_ERR;");
        this.out.println("    }");
        this.out.println("    JavaCPP_stringWithCharsetMID = JavaCPP_getMethodID(env, " + this.jclasses.index(String.class) + ", \"<init>\", \"([BLjava/nio/charset/Charset;)V\");");
        this.out.println("    if (JavaCPP_stringWithCharsetMID == NULL) {");
        this.out.println("        return JNI_ERR;");
        this.out.println("    }");
        this.out.println("    JavaCPP_getBytesWithCharsetMID = JavaCPP_getMethodID(env, " + this.jclasses.index(String.class) + ", \"getBytes\", \"(Ljava/nio/charset/Charset;)[B\");");
        this.out.println("    if (JavaCPP_getBytesWithCharsetMID == NULL) {");
        this.out.println("        return JNI_ERR;");
        this.out.println("    }");
        this.out.println("#endif // STRING_BYTES_CHARSET");
        this.out.println("    return env->GetVersion();");
        this.out.println("}");
        this.out.println();
        if (this.out2 != null) {
            this.out2.println("JNIIMPORT int JavaCPP_uninit" + loadSuffix + "();");
            this.out2.println();
            this.out.println("JNIEXPORT int JavaCPP_uninit" + loadSuffix + "() {");
            this.out.println("#if defined(__ANDROID__) || TARGET_OS_IPHONE");
            this.out.println("    return JNI_OK;");
            this.out.println("#else");
            this.out.println("    JavaVM *vm = JavaCPP_vm;");
            this.out.println("    JNI_OnUnload" + loadSuffix + "(JavaCPP_vm, NULL);");
            this.out.println("    return vm->DestroyJavaVM();");
            this.out.println("#endif");
            this.out.println("}");
        }
        this.out.println();
        this.out.println("JNIEXPORT void JNICALL JNI_OnUnload" + loadSuffix + "(JavaVM* vm, void* reserved) {");
        this.out.println("    JNIEnv* env;");
        this.out.println("    if (vm->GetEnv((void**)&env, JNI_VERSION_1_6) != JNI_OK) {");
        this.out.println("        JavaCPP_log(\"Could not get JNIEnv for JNI_VERSION_1_6 inside JNI_OnUnLoad" + loadSuffix + "().\");");
        this.out.println("        return;");
        this.out.println("    }");
        this.out.println("    for (int i = 0; i < " + this.jclasses.size() + "; i++) {");
        this.out.println("        env->DeleteWeakGlobalRef((jweak)JavaCPP_classes[i]);");
        this.out.println("        JavaCPP_classes[i] = NULL;");
        this.out.println("    }");
        this.out.println("#ifdef STRING_BYTES_CHARSET");
        this.out.println("    env->DeleteGlobalRef(JavaCPP_stringBytesCharset);");
        this.out.println("    JavaCPP_stringBytesCharset = NULL;");
        this.out.println("#endif");
        if (baseLoadSuffix != null && !baseLoadSuffix.isEmpty()) {
            this.out.println("    JNI_OnUnload" + baseLoadSuffix + "(vm, reserved);");
        }
        this.out.println("    JavaCPP_vm = NULL;");
        this.out.println("}");
        this.out.println();
        boolean supportedPlatform = false;
        LinkedHashSet allClasses = new LinkedHashSet();
        if (baseLoadSuffix == null || baseLoadSuffix.isEmpty()) {
            supportedPlatform = true;
            allClasses.addAll(baseClasses);
        }
        if (classes != null) {
            void var20_39;
            allClasses.addAll(Arrays.asList(classes));
            memberIterator = classes;
            int typeName2 = memberIterator.length;
            boolean bl = false;
            while (var20_39 < typeName2) {
                Class<?> cls = memberIterator[var20_39];
                supportedPlatform |= Loader.checkPlatform(cls, this.properties);
                ++var20_39;
            }
        }
        boolean didSomethingUseful = false;
        for (Class clazz : allClasses) {
            try {
                didSomethingUseful |= this.methods(clazz);
            }
            catch (NoClassDefFoundError e) {
                this.logger.warn("Could not generate code for class " + clazz.getCanonicalName() + ": " + e);
            }
        }
        this.out.println("}");
        this.out.println();
        if (this.out2 != null) {
            this.out2.println("#ifdef __cplusplus");
            this.out2.println("}");
            this.out2.println("#endif");
        }
        allClasses.addAll(this.jclasses.keySet());
        LinkedHashSet<Class<String>> reflectClasses = new LinkedHashSet<Class<String>>();
        reflectClasses.addAll(baseClasses);
        reflectClasses.add(BooleanEnum.class);
        reflectClasses.add(ByteEnum.class);
        reflectClasses.add(ShortEnum.class);
        reflectClasses.add(IntEnum.class);
        reflectClasses.add(LongEnum.class);
        reflectClasses.add(Charset.class);
        reflectClasses.add(Object.class);
        reflectClasses.add(Buffer.class);
        reflectClasses.add(String.class);
        for (Class<?> cls : new LinkedHashSet(allClasses)) {
            while ((cls = cls.getEnclosingClass()) != null) {
                allClasses.add(cls);
            }
        }
        if (declareEnums) {
            allClasses.add(BooleanEnum.class);
            allClasses.add(ByteEnum.class);
            allClasses.add(ShortEnum.class);
            allClasses.add(IntEnum.class);
            allClasses.add(LongEnum.class);
        }
        for (PrintWriter o : new PrintWriter[]{this.jniConfigOut, this.reflectConfigOut}) {
            if (o == null) continue;
            o.println("[");
            String separator = "";
            for (Class<?> cls : allClasses) {
                o.println(separator + "  {");
                o.print("    \"name\" : \"" + cls.getName() + "\"");
                if (reflectClasses.contains(cls) || reflectClasses.contains(cls.getEnclosingClass()) || this.virtualFunctions.get(cls) != null || FunctionPointer.class.isAssignableFrom(cls)) {
                    o.println(",");
                    o.println("    \"allDeclaredConstructors\" : true,");
                    o.println("    \"allPublicConstructors\" : true,");
                    o.println("    \"allDeclaredMethods\" : true,");
                    o.println("    \"allPublicMethods\" : true,");
                    o.println("    \"allDeclaredFields\" : true,");
                    o.println("    \"allPublicFields\" : true,");
                    o.println("    \"allDeclaredClasses\" : true,");
                    o.print("    \"allPublicClasses\" : true");
                } else if (LoadEnabled.class.isAssignableFrom(cls)) {
                    o.println(",");
                    o.println("    \"allDeclaredConstructors\" : true,");
                    o.print("    \"allPublicConstructors\" : true");
                }
                o.println();
                o.print("  }");
                separator = "," + System.lineSeparator();
                cls = cls.getEnclosingClass();
            }
            o.println();
            o.println("]");
        }
        return supportedPlatform;
    }

    boolean methods(Class<?> cls) {
        if (!Loader.checkPlatform(cls, this.properties)) {
            return false;
        }
        Set<String> memberList = this.members.get(cls);
        if (!cls.isAnnotationPresent(Opaque.class) && cls != Loader.class && !FunctionPointer.class.isAssignableFrom(cls) && cls.getEnclosingClass() != Loader.class && cls.getEnclosingClass() != Pointer.class) {
            if (memberList == null) {
                memberList = new LinkedHashSet<String>();
                this.members.put(cls, memberList);
            }
            if (!memberList.contains("sizeof")) {
                memberList.add("sizeof");
            }
        }
        boolean didSomething = false;
        for (Class<?> clazz : cls.getDeclaredClasses()) {
            if (!Pointer.class.isAssignableFrom(clazz) && !Pointer.class.equals(clazz.getEnclosingClass())) continue;
            didSomething |= this.methods(clazz);
        }
        Method[] methods = cls.getDeclaredMethods();
        MethodInformation[] methodInfos = new MethodInformation[methods.length];
        for (int i = 0; i < methods.length; ++i) {
            methodInfos[i] = this.methodInformation(methods[i]);
        }
        for (Class<?> c = cls.getSuperclass(); c != null && c != Object.class && !Modifier.isAbstract(cls.getModifiers()); c = c.getSuperclass()) {
            for (Method m : c.getDeclaredMethods()) {
                if (!m.isAnnotationPresent(Virtual.class) || !m.getAnnotation(Virtual.class).subclasses()) continue;
                boolean found = false;
                String name = m.getName();
                Object[] types = m.getParameterTypes();
                for (Method m2 : methods) {
                    if (!name.equals(m2.getName()) || !Arrays.equals(types, m2.getParameterTypes())) continue;
                    found = true;
                    break;
                }
                if (found) continue;
                methods = Arrays.copyOf(methods, methods.length + 1);
                methods[methods.length - 1] = m;
                methodInfos = Arrays.copyOf(methodInfos, methodInfos.length + 1);
                methodInfos[methods.length - 1] = this.methodInformation(m);
                methodInfos[methods.length - 1].cls = cls;
            }
        }
        boolean[] blArray = new boolean[methods.length];
        Method[] functionMethods = Generator.functionMethods(cls, methods, blArray);
        boolean firstCallback = true;
        for (int i = 0; i < methods.length; ++i) {
            String callbackName;
            if (!Loader.checkPlatform(methods[i].getAnnotation(Platform.class), this.properties)) continue;
            MethodInformation methodInfo = methodInfos[i];
            String nativeName = Generator.mangle(cls.getName()) + "_" + Generator.mangle(methods[i].getName());
            String string = callbackName = blArray[i] && methodInfo.parameterTypes.length > 0 ? null : "JavaCPP_" + nativeName + "_callback";
            if (blArray[i] && functionMethods == null) {
                this.logger.warn("No callback method call() or apply() has been not declared in \"" + cls.getCanonicalName() + "\". No code will be generated for callback allocator.");
                continue;
            }
            if (functionMethods != null) {
                for (Method functionMethod : functionMethods) {
                    if ((functionMethod == null || !blArray[i]) && (!methods[i].equals(functionMethod) || Modifier.isNative(methods[i].getModifiers()))) continue;
                    this.functions.index(cls);
                    Name name = methods[i].getAnnotation(Name.class);
                    if (name != null && name.value().length > 0 && name.value()[0].length() > 0) {
                        callbackName = name.value()[0];
                    }
                    this.callback(cls, functionMethod, callbackName, methodInfo.allocatorMax, firstCallback, null);
                    firstCallback = false;
                    didSomething = true;
                }
            }
            if (!(!Modifier.isNative(methods[i].getModifiers()) && !Modifier.isAbstract(methods[i].getModifiers()) || methodInfo.valueGetter || methodInfo.valueSetter || methodInfo.memberGetter || methodInfo.memberSetter || cls.isInterface() || !methods[i].isAnnotationPresent(Virtual.class) && !methodInfo.allocator)) {
                this.callback(cls, methods[i], methodInfo.memberName[0], methodInfo.allocatorMax, !methodInfo.allocator, methodInfo);
            }
            if (!Modifier.isNative(methods[i].getModifiers())) continue;
            if (!(!methodInfo.memberGetter && !methodInfo.memberSetter || methodInfo.noOffset || memberList == null || Modifier.isStatic(methodInfo.modifiers) || memberList.contains(methodInfo.memberName[0]))) {
                memberList.add(methodInfo.memberName[0]);
            }
            didSomething = true;
            this.out.print("JNIEXPORT " + Generator.jniTypeName(methodInfo.returnType) + " JNICALL Java_" + nativeName);
            if (methodInfo.overloaded) {
                this.out.print("__" + Generator.mangle(Generator.signature(methodInfo.parameterTypes)));
            }
            if (Modifier.isStatic(methodInfo.modifiers)) {
                this.out.print("(JNIEnv* env, jclass cls");
            } else {
                this.out.print("(JNIEnv* env, jobject obj");
            }
            for (int j = 0; j < methodInfo.parameterTypes.length; ++j) {
                this.out.print(", " + Generator.jniTypeName(methodInfo.parameterTypes[j]) + " arg" + j);
            }
            this.out.println(") {");
            if (blArray[i]) {
                this.callbackAllocator(cls, callbackName, methodInfo.allocatorMax);
                continue;
            }
            if (!(Modifier.isStatic(methodInfo.modifiers) || !Pointer.class.isAssignableFrom(cls) || methodInfo.allocator || methodInfo.arrayAllocator || methodInfo.deallocator)) {
                String[] typeName = this.cppTypeName(cls);
                if ("void*".equals(typeName[0]) && !cls.isAnnotationPresent(Opaque.class)) {
                    typeName[0] = "char*";
                } else if (FunctionPointer.class.isAssignableFrom(cls)) {
                    this.functions.index(cls);
                    typeName[0] = Generator.functionClassName(cls) + "*";
                    typeName[1] = "";
                }
                this.out.println("    " + typeName[0] + " ptr" + typeName[1] + " = (" + typeName[0] + typeName[1] + ")jlong_to_ptr(env->GetLongField(obj, JavaCPP_addressFID));");
                this.out.println("    if (ptr == NULL) {");
                this.out.println("        env->ThrowNew(JavaCPP_getClass(env, " + this.jclasses.index(NullPointerException.class) + "), \"This pointer address is NULL.\");");
                this.out.println("        return" + (methodInfo.returnType == Void.TYPE ? ";" : " 0;"));
                this.out.println("    }");
                if (FunctionPointer.class.isAssignableFrom(cls) && !methodInfo.valueGetter && !methodInfo.valueSetter) {
                    this.out.println("    if (ptr->ptr == NULL) {");
                    this.out.println("        env->ThrowNew(JavaCPP_getClass(env, " + this.jclasses.index(NullPointerException.class) + "), \"This function pointer address is NULL.\");");
                    this.out.println("        return" + (methodInfo.returnType == Void.TYPE ? ";" : " 0;"));
                    this.out.println("    }");
                }
                if (!cls.isAnnotationPresent(Opaque.class)) {
                    this.out.println("    jlong position = env->GetLongField(obj, JavaCPP_positionFID);");
                    if (methodInfo.bufferGetter) {
                        this.out.println("    jlong limit = env->GetLongField(obj, JavaCPP_limitFID);");
                        this.out.println("    jlong capacity = env->GetLongField(obj, JavaCPP_capacityFID);");
                    } else {
                        this.out.println("    ptr += position;");
                    }
                }
            }
            this.parametersBefore(methodInfo);
            String returnPrefix = this.returnBefore(methodInfo);
            this.call(methodInfo, returnPrefix, false);
            this.returnAfter(methodInfo);
            this.parametersAfter(methodInfo);
            if (methodInfo.throwsException != null) {
                this.out.println("    if (exc != NULL) {");
                this.out.println("        env->Throw(exc);");
                this.out.println("    }");
            }
            if (methodInfo.returnType != Void.TYPE) {
                this.out.println("    return rarg;");
            }
            this.out.println("}");
        }
        this.out.println();
        return didSomething;
    }

    void parametersBefore(MethodInformation methodInfo) {
        int skipParameters;
        String adapterLine = "";
        AdapterInformation prevAdapterInfo = null;
        for (int j = skipParameters = methodInfo.parameterTypes.length > 0 && methodInfo.parameterTypes[0] == Class.class ? 1 : 0; j < methodInfo.parameterTypes.length; ++j) {
            String s;
            AdapterInformation adapterInfo;
            String[] stringArray;
            if (methodInfo.parameterTypes[j].isPrimitive()) continue;
            Annotation passBy = this.by(methodInfo, j);
            String cast = this.cast(methodInfo, j);
            if (methodInfo.parameterRaw[j]) {
                String[] stringArray2 = new String[1];
                stringArray = stringArray2;
                stringArray2[0] = "";
            } else {
                stringArray = this.cppTypeName(methodInfo, j);
            }
            String[] typeName = stringArray;
            AdapterInformation adapterInformation = adapterInfo = methodInfo.parameterRaw[j] ? null : this.adapterInformation(false, methodInfo, j);
            if (Enum.class.isAssignableFrom(methodInfo.parameterTypes[j])) {
                this.accessesEnums = true;
                s = this.enumValueType(methodInfo.parameterTypes[j]);
                if (s == null) continue;
                String S = Character.toUpperCase(s.charAt(0)) + s.substring(1);
                this.out.println("    if (arg" + j + " == NULL) {");
                this.out.println("        env->ThrowNew(JavaCPP_getClass(env, " + this.jclasses.index(NullPointerException.class) + "), \"Enum for argument " + j + " is NULL.\");");
                this.out.println("        return" + (methodInfo.returnType == Void.TYPE ? ";" : " 0;"));
                this.out.println("    }");
                this.out.println("    " + typeName[0] + " val" + j + typeName[1] + " = (" + typeName[0] + typeName[1] + ")env->Get" + S + "Field(arg" + j + ", JavaCPP_" + s + "ValueFID);");
                continue;
            }
            if (FunctionPointer.class.isAssignableFrom(methodInfo.parameterTypes[j])) {
                this.functions.index(methodInfo.parameterTypes[j]);
                if (methodInfo.parameterTypes[j] == FunctionPointer.class) {
                    this.logger.warn("Method \"" + methodInfo.method + "\" has an abstract FunctionPointer parameter, but a concrete subclass is required. Compilation will most likely fail.");
                }
                typeName[0] = Generator.functionClassName(methodInfo.parameterTypes[j]) + "*";
                typeName[1] = "";
            }
            if (typeName[0].length() == 0 || methodInfo.parameterRaw[j]) {
                methodInfo.parameterRaw[j] = true;
                typeName[0] = Generator.jniTypeName(methodInfo.parameterTypes[j]);
                this.out.println("    " + typeName[0] + " ptr" + j + " = arg" + j + ";");
                continue;
            }
            if ("void*".equals(typeName[0]) && !methodInfo.parameterTypes[j].isAnnotationPresent(Opaque.class)) {
                typeName[0] = "char*";
            }
            this.out.print("    " + typeName[0] + " ptr" + j + typeName[1] + " = ");
            if (Pointer.class.isAssignableFrom(methodInfo.parameterTypes[j])) {
                this.out.println("arg" + j + " == NULL ? NULL : (" + typeName[0] + typeName[1] + ")jlong_to_ptr(env->GetLongField(arg" + j + ", JavaCPP_addressFID));");
                if (j == 0 && FunctionPointer.class.isAssignableFrom(methodInfo.cls) && methodInfo.cls.isAnnotationPresent(Namespace.class) || passBy instanceof ByVal && ((ByVal)passBy).nullValue().length() == 0 || passBy instanceof ByRef && ((ByRef)passBy).nullValue().length() == 0) {
                    this.out.println("    if (ptr" + j + " == NULL) {");
                    this.out.println("        env->ThrowNew(JavaCPP_getClass(env, " + this.jclasses.index(NullPointerException.class) + "), \"Pointer address of argument " + j + " is NULL.\");");
                    this.out.println("        return" + (methodInfo.returnType == Void.TYPE ? ";" : " 0;"));
                    this.out.println("    }");
                }
                if (adapterInfo != null || prevAdapterInfo != null) {
                    this.out.println("    jlong size" + j + " = arg" + j + " == NULL ? 0 : env->GetLongField(arg" + j + ", JavaCPP_limitFID);");
                    this.out.println("    void* owner" + j + " = JavaCPP_getPointerOwner(env, arg" + j + ");");
                }
                if (!methodInfo.parameterTypes[j].isAnnotationPresent(Opaque.class)) {
                    this.out.println("    jlong position" + j + " = arg" + j + " == NULL ? 0 : env->GetLongField(arg" + j + ", JavaCPP_positionFID);");
                    this.out.println("    ptr" + j + " += position" + j + ";");
                    if (adapterInfo != null || prevAdapterInfo != null) {
                        this.out.println("    size" + j + " -= position" + j + ";");
                    }
                }
            } else if (methodInfo.parameterTypes[j] == String.class) {
                this.passesStrings = true;
                this.out.println(Generator.getStringData("arg" + j, Generator.asUtf16(methodInfo, j)));
                if (adapterInfo != null || prevAdapterInfo != null) {
                    this.out.println("    jlong size" + j + " = 0;");
                    this.out.println("    void* owner" + j + " = (void*)ptr" + j + ";");
                }
            } else if (methodInfo.parameterTypes[j].isArray() && methodInfo.parameterTypes[j].getComponentType().isPrimitive()) {
                this.out.print("arg" + j + " == NULL ? NULL : ");
                s = methodInfo.parameterTypes[j].getComponentType().getName();
                if (methodInfo.criticalRegion || methodInfo.valueGetter || methodInfo.valueSetter || methodInfo.memberGetter || methodInfo.memberSetter) {
                    this.out.println("(" + typeName[0] + typeName[1] + ")env->GetPrimitiveArrayCritical(arg" + j + ", NULL);");
                } else {
                    s = Character.toUpperCase(s.charAt(0)) + s.substring(1);
                    this.out.println("(" + typeName[0] + typeName[1] + ")env->Get" + s + "ArrayElements(arg" + j + ", NULL);");
                }
                if (adapterInfo != null || prevAdapterInfo != null) {
                    this.out.println("    jlong size" + j + " = arg" + j + " == NULL ? 0 : env->GetArrayLength(arg" + j + ");");
                    this.out.println("    void* owner" + j + " = (void*)ptr" + j + ";");
                }
            } else if (Buffer.class.isAssignableFrom(methodInfo.parameterTypes[j])) {
                this.out.println("arg" + j + " == NULL ? NULL : (" + typeName[0] + typeName[1] + ")env->GetDirectBufferAddress(arg" + j + ");");
                if (adapterInfo != null || prevAdapterInfo != null) {
                    this.out.println("    jlong size" + j + " = arg" + j + " == NULL ? 0 : env->GetIntField(arg" + j + ", JavaCPP_bufferLimitFID);");
                    this.out.println("    void* owner" + j + " = (void*)ptr" + j + ";");
                }
                if (methodInfo.parameterTypes[j] != Buffer.class) {
                    String paramName = methodInfo.parameterTypes[j].getSimpleName();
                    paramName = paramName.substring(0, paramName.length() - 6);
                    String paramNameLowerCase = Character.toLowerCase(paramName.charAt(0)) + paramName.substring(1);
                    this.out.println("    j" + paramNameLowerCase + "Array arr" + j + " = NULL;");
                    this.out.println("    jlong offset" + j + " = 0;");
                    this.out.println("    if (arg" + j + " != NULL && ptr" + j + " == NULL) {");
                    this.out.println("        arr" + j + " = (j" + paramNameLowerCase + "Array)env->CallObjectMethod(arg" + j + ", JavaCPP_arrayMID);");
                    this.out.println("        offset" + j + " = env->CallIntMethod(arg" + j + ", JavaCPP_arrayOffsetMID);");
                    this.out.println("        if (env->ExceptionOccurred() != NULL) {");
                    this.out.println("            return" + (methodInfo.returnType == Void.TYPE ? ";" : " 0;"));
                    this.out.println("        } else {");
                    if (methodInfo.criticalRegion) {
                        this.out.println("            ptr" + j + " = arr" + j + " == NULL ? NULL : (" + typeName[0] + typeName[1] + ")env->GetPrimitiveArrayCritical(arr" + j + ", NULL) + offset" + j + ";");
                    } else {
                        this.out.println("            ptr" + j + " = arr" + j + " == NULL ? NULL : (" + typeName[0] + typeName[1] + ")env->Get" + paramName + "ArrayElements(arr" + j + ", NULL) + offset" + j + ";");
                    }
                    this.out.println("        }");
                    this.out.println("    }");
                }
                this.out.println("    jlong position" + j + " = arg" + j + " == NULL ? 0 : env->GetIntField(arg" + j + ", JavaCPP_bufferPositionFID);");
                this.out.println("    ptr" + j + " += position" + j + ";");
                if (adapterInfo != null || prevAdapterInfo != null) {
                    this.out.println("    size" + j + " -= position" + j + ";");
                }
            } else {
                this.out.println("arg" + j + ";");
                this.logger.warn("Method \"" + methodInfo.method + "\" has an unsupported parameter of type \"" + methodInfo.parameterTypes[j].getCanonicalName() + "\". Compilation will most likely fail.");
            }
            if (adapterInfo != null) {
                this.usesAdapters = true;
                adapterLine = "    " + adapterInfo.name + " adapter" + j + "(";
                prevAdapterInfo = adapterInfo;
            }
            if (prevAdapterInfo != null) {
                if (!FunctionPointer.class.isAssignableFrom(methodInfo.cls)) {
                    adapterLine = adapterLine + cast;
                }
                adapterLine = adapterLine + "ptr" + j + ", size" + j + ", owner" + j;
                if (--prevAdapterInfo.argc > 0) {
                    adapterLine = adapterLine + ", ";
                }
            }
            if (prevAdapterInfo == null || prevAdapterInfo.argc > 0) continue;
            this.out.println(adapterLine + ");");
            prevAdapterInfo = null;
        }
    }

    String returnBefore(MethodInformation methodInfo) {
        String returnPrefix = "";
        if (methodInfo.returnType == Void.TYPE) {
            if (methodInfo.allocator || methodInfo.arrayAllocator) {
                this.jclasses.index(methodInfo.cls);
                String[] typeName = this.cppTypeName(methodInfo.cls);
                String valueTypeName = Generator.valueTypeName(typeName);
                AdapterInformation adapterInfo = this.adapterInformation(false, valueTypeName, methodInfo.annotations);
                if (adapterInfo == null) {
                    returnPrefix = typeName[0] + " rptr" + typeName[1] + " = ";
                } else {
                    this.usesAdapters = true;
                    String cast2 = adapterInfo.cast2.trim();
                    if (cast2.length() > 0 && !cast2.startsWith("(") && !cast2.endsWith(")")) {
                        cast2 = "(" + cast2 + ")";
                    }
                    returnPrefix = adapterInfo.name + " radapter(" + cast2;
                }
            }
        } else {
            String[] stringArray;
            String cast = this.cast(methodInfo.returnType, methodInfo.annotations);
            if (methodInfo.returnRaw) {
                String[] stringArray2 = new String[1];
                stringArray = stringArray2;
                stringArray2[0] = "";
            } else {
                stringArray = this.cppCastTypeName(methodInfo.returnType, methodInfo.annotations);
            }
            String[] typeName = stringArray;
            Annotation returnBy = this.by(methodInfo.annotations);
            if (FunctionPointer.class.isAssignableFrom(methodInfo.cls) && !methodInfo.cls.isAnnotationPresent(Namespace.class) && methodInfo.valueGetter) {
                typeName = this.cppTypeName(methodInfo.cls);
            }
            if (methodInfo.valueSetter || methodInfo.memberSetter || methodInfo.noReturnGetter) {
                this.out.println("    jobject rarg = obj;");
            } else if (methodInfo.returnType.isPrimitive()) {
                this.out.println("    " + Generator.jniTypeName(methodInfo.returnType) + " rarg = 0;");
                returnPrefix = typeName[0] + " rval" + typeName[1] + " = " + cast;
                if (returnBy instanceof ByPtr || returnBy instanceof ByPtrRef) {
                    returnPrefix = returnPrefix + "*";
                } else if ((returnBy instanceof ByVal || returnBy instanceof ByRef) && cast.endsWith("*)")) {
                    returnPrefix = returnPrefix + "&";
                }
            } else if (Enum.class.isAssignableFrom(methodInfo.returnType)) {
                this.accessesEnums = true;
                this.out.println("    jobject rarg = JavaCPP_createPointer(env, " + this.jclasses.index(methodInfo.returnType) + ");");
                returnPrefix = typeName[0] + " rval" + typeName[1] + " = " + cast;
            } else {
                String valueTypeName = Generator.valueTypeName(typeName);
                AdapterInformation adapterInfo = this.adapterInformation(false, valueTypeName, methodInfo.annotations);
                returnPrefix = "rptr = " + cast;
                if (typeName[0].length() == 0 || methodInfo.returnRaw) {
                    methodInfo.returnRaw = true;
                    typeName[0] = Generator.jniTypeName(methodInfo.returnType);
                    this.out.println("    " + typeName[0] + " rarg = NULL;");
                    this.out.println("    " + typeName[0] + " rptr;");
                } else if (Pointer.class.isAssignableFrom(methodInfo.returnType) || Buffer.class.isAssignableFrom(methodInfo.returnType) || methodInfo.returnType.isArray() && methodInfo.returnType.getComponentType().isPrimitive()) {
                    if (FunctionPointer.class.isAssignableFrom(methodInfo.returnType)) {
                        this.functions.index(methodInfo.returnType);
                        returnPrefix = "if (rptr != NULL) rptr->ptr = ";
                        if (methodInfo.method.isAnnotationPresent(Virtual.class)) {
                            returnPrefix = returnPrefix + "(" + typeName[0] + typeName[1] + ")&";
                        }
                        typeName[0] = Generator.functionClassName(methodInfo.returnType) + "*";
                        typeName[1] = "";
                        valueTypeName = Generator.valueTypeName(typeName);
                    }
                    if (returnBy instanceof ByVal || returnBy instanceof ByRef && ((ByRef)returnBy).value()) {
                        returnPrefix = returnPrefix + (Generator.noException(methodInfo.returnType, methodInfo.method) ? "new (std::nothrow) " : "new ") + valueTypeName + typeName[1] + "(";
                    } else if (returnBy instanceof ByRef) {
                        returnPrefix = returnPrefix + "&";
                    } else if (returnBy instanceof ByPtrPtr) {
                        if (cast.length() > 0) {
                            typeName[0] = typeName[0].substring(0, typeName[0].length() - 1);
                        }
                        returnPrefix = "rptr = NULL; " + typeName[0] + "* rptrptr" + typeName[1] + " = " + cast;
                    }
                    if (adapterInfo != null && methodInfo.returnType.isArray() && methodInfo.returnType.getComponentType().isPrimitive() && !typeName[0].startsWith("const ")) {
                        typeName[0] = "const " + typeName[0];
                    }
                    if (methodInfo.bufferGetter) {
                        this.out.println("    jobject rarg = NULL;");
                        this.out.println("    char* rptr;");
                    } else {
                        this.out.println("    " + Generator.jniTypeName(methodInfo.returnType) + " rarg = NULL;");
                        this.out.println("    " + typeName[0] + " rptr" + typeName[1] + ";");
                    }
                    if (FunctionPointer.class.isAssignableFrom(methodInfo.returnType)) {
                        this.out.println("    rptr = new (std::nothrow) " + valueTypeName + ";");
                        if (returnBy instanceof ByPtrPtr) {
                            String[] cpptypeName = this.cppTypeName(methodInfo.returnType, methodInfo.annotations);
                            returnPrefix = cpptypeName[0] + "* rptrptr" + cpptypeName[1] + " = ";
                        }
                    }
                } else if (methodInfo.returnType == String.class) {
                    if (returnBy instanceof ByPtrPtr) {
                        typeName[0] = valueTypeName;
                    }
                    if (!typeName[0].startsWith("const ")) {
                        typeName[0] = "const " + typeName[0];
                    }
                    this.out.println("    jstring rarg = NULL;");
                    this.out.println("    " + typeName[0] + " rptr;");
                    returnPrefix = returnBy instanceof ByRef ? "std::basic_string<" + valueTypeName + "> rstr(" : (returnBy instanceof ByPtrPtr ? "rptr = NULL; " + typeName[0] + "* rptrptr = (" + typeName[0] + "*)" : returnPrefix + "(" + typeName[0] + ")");
                } else {
                    this.logger.warn("Method \"" + methodInfo.method + "\" has unsupported return type \"" + methodInfo.returnType.getCanonicalName() + "\". Compilation will most likely fail.");
                }
                if (adapterInfo != null) {
                    this.usesAdapters = true;
                    String cast2 = adapterInfo.cast2.trim();
                    if (cast2.length() > 0 && !cast2.startsWith("(") && !cast2.endsWith(")")) {
                        cast2 = "(" + cast2 + ")";
                    }
                    returnPrefix = adapterInfo.name + " radapter(" + cast2;
                }
            }
        }
        if (methodInfo.throwsException != null) {
            this.out.println("    jthrowable exc = NULL;");
            this.out.println("    try {");
        }
        return returnPrefix;
    }

    void call(MethodInformation methodInfo, String returnPrefix, boolean secondCall) {
        String cast;
        boolean needSecondCall = false;
        String indent = secondCall ? "" : (methodInfo.throwsException != null ? "        " : "    ");
        String prefix = "(";
        String suffix = ")";
        int skipParameters = methodInfo.parameterTypes.length > 0 && methodInfo.parameterTypes[0] == Class.class ? 1 : 0;
        Index index = methodInfo.method.getAnnotation(Index.class);
        if (index == null && methodInfo.pairedMethod != null) {
            index = methodInfo.pairedMethod.getAnnotation(Index.class);
        }
        if (methodInfo.deallocator) {
            this.out.println(indent + "void* allocatedAddress = jlong_to_ptr(arg0);");
            this.out.println(indent + "void (*deallocatorAddress)(void*) = (void(*)(void*))jlong_to_ptr(arg1);");
            this.out.println(indent + "if (deallocatorAddress != NULL && allocatedAddress != NULL) {");
            this.out.println(indent + "    (*deallocatorAddress)(allocatedAddress);");
            this.out.println(indent + "}");
            return;
        }
        if (!FunctionPointer.class.isAssignableFrom(methodInfo.cls) && (methodInfo.valueGetter || methodInfo.valueSetter || methodInfo.memberGetter || methodInfo.memberSetter)) {
            boolean wantsPointer = false;
            int k = methodInfo.parameterTypes.length - 1;
            if ((methodInfo.valueSetter || methodInfo.memberSetter) && !(this.by(methodInfo, k) instanceof ByRef) && this.adapterInformation(false, methodInfo, k) == null && methodInfo.parameterTypes[k] == String.class) {
                if (Generator.asUtf16(methodInfo, k)) {
                    this.out.print(indent + "memcpy(");
                    suffix = ", (std::char_traits<char16_t>::length(ptr" + k + ") + 1) * sizeof(char16_t))";
                } else {
                    this.out.print(indent + "strcpy((char*)");
                }
                wantsPointer = true;
                prefix = ", ";
            } else if (k >= 1 && methodInfo.parameterTypes[0].isArray() && methodInfo.parameterTypes[0].getComponentType().isPrimitive() && (methodInfo.parameterTypes[1] == Integer.TYPE || methodInfo.parameterTypes[1] == Long.TYPE)) {
                this.out.print(indent + "memcpy(");
                wantsPointer = true;
                prefix = ", ";
                if (methodInfo.memberGetter || methodInfo.valueGetter) {
                    this.out.print("ptr0 + arg1, ");
                } else {
                    prefix = prefix + "ptr0 + arg1, ";
                }
                skipParameters = 2;
                suffix = " * sizeof(*ptr0)" + suffix;
            } else {
                this.out.print(indent + returnPrefix);
                prefix = methodInfo.valueGetter || methodInfo.memberGetter ? "" : " = ";
                suffix = "";
            }
            if (Modifier.isStatic(methodInfo.modifiers) || !Pointer.class.isAssignableFrom(methodInfo.cls)) {
                this.out.print(Generator.cppScopeName(methodInfo));
            } else if (methodInfo.memberGetter || methodInfo.memberSetter) {
                if (index != null) {
                    this.out.print("(*ptr)");
                    prefix = "." + methodInfo.memberName[0] + prefix;
                } else {
                    this.out.print("ptr->" + methodInfo.memberName[0]);
                }
            } else {
                cast = this.cast(methodInfo.returnType, methodInfo.annotations);
                if (index == null && cast.length() > 0) {
                    this.out.print("*(" + cast.substring(1, cast.length() - 1) + "*)&");
                }
                this.out.print(index != null ? "(*ptr)" : (methodInfo.dim > 0 || wantsPointer ? "ptr" : "*ptr"));
            }
        } else if (methodInfo.bufferGetter) {
            this.out.print(indent + returnPrefix + "ptr");
            prefix = "";
            suffix = "";
        } else {
            this.out.print(indent + returnPrefix);
            if (FunctionPointer.class.isAssignableFrom(methodInfo.cls)) {
                if (methodInfo.cls.isAnnotationPresent(Namespace.class)) {
                    this.out.print("(ptr0->*(ptr->ptr))");
                    skipParameters = 1;
                    if (methodInfo.valueGetter || methodInfo.valueSetter) {
                        prefix = methodInfo.valueGetter ? "" : " = ";
                        suffix = "";
                    }
                } else if (methodInfo.valueGetter || methodInfo.valueSetter) {
                    this.out.print("ptr->ptr");
                    prefix = methodInfo.valueGetter ? "" : " = ";
                    suffix = "";
                } else {
                    this.out.print("(*ptr->ptr)");
                }
            } else if (methodInfo.allocator) {
                String[] typeName = this.cppTypeName(methodInfo.cls);
                String valueTypeName = Generator.valueTypeName(typeName);
                if (this.virtualFunctions.containsKey(methodInfo.cls)) {
                    String subType;
                    valueTypeName = subType = "JavaCPP_" + Generator.mangle(valueTypeName);
                }
                if (methodInfo.cls == Pointer.class) {
                    prefix = "";
                    suffix = "";
                } else if (methodInfo.method.isAnnotationPresent(Name.class)) {
                    this.out.print(methodInfo.memberName[0]);
                } else {
                    this.out.print((Generator.noException(methodInfo.cls, methodInfo.method) ? "new (std::nothrow) " : "new ") + valueTypeName + typeName[1]);
                    if (methodInfo.arrayAllocator) {
                        prefix = "[";
                        suffix = "]";
                    }
                }
            } else if (Modifier.isStatic(methodInfo.modifiers) || !Pointer.class.isAssignableFrom(methodInfo.cls)) {
                this.out.print(Generator.cppScopeName(methodInfo));
            } else {
                String name = methodInfo.memberName[0];
                String[] typeName = this.cppTypeName(methodInfo.cls);
                String valueTypeName = Generator.valueTypeName(typeName);
                if (this.virtualFunctions.containsKey(methodInfo.cls) && !secondCall) {
                    String subType = "JavaCPP_" + Generator.mangle(valueTypeName);
                    if (Modifier.isPublic(methodInfo.method.getModifiers())) {
                        this.out.print("(dynamic_cast<" + subType + "*>(ptr) != NULL ? ");
                        needSecondCall = true;
                    }
                    if (methodInfo.method.isAnnotationPresent(Virtual.class)) {
                        name = "super_" + methodInfo.name;
                    }
                    this.out.print("((" + subType + "*)ptr)->" + name);
                } else if (secondCall && methodInfo.method.getDeclaringClass() != methodInfo.cls) {
                    String[] typeName2 = this.cppTypeName(methodInfo.method.getDeclaringClass());
                    this.out.print("((" + typeName2[0] + typeName2[1] + ")ptr)->" + name);
                } else if (index != null) {
                    this.out.print("(*ptr)");
                    prefix = "." + name + prefix;
                } else {
                    String op;
                    String string = op = name.startsWith("operator") ? name.substring(8).trim() : "";
                    if (methodInfo.parameterTypes.length > 0 && (op.equals("=") || op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/") || op.equals("%") || op.equals("==") || op.equals("!=") || op.equals("<") || op.equals(">") || op.equals("<=") || op.equals(">="))) {
                        this.out.print("((*ptr)");
                        prefix = op + prefix;
                        suffix = suffix + ")";
                    } else {
                        this.out.print("ptr->" + name);
                    }
                }
            }
        }
        for (int j = skipParameters; j <= methodInfo.parameterTypes.length; ++j) {
            AdapterInformation adapterInfo;
            if (j == skipParameters + methodInfo.dim) {
                if (methodInfo.memberName.length > 1) {
                    this.out.print(methodInfo.memberName[1]);
                }
                this.out.print(prefix);
                if (methodInfo.withEnv) {
                    this.out.print(Modifier.isStatic(methodInfo.modifiers) ? "env, cls" : "env, obj");
                    if (methodInfo.parameterTypes.length - skipParameters - methodInfo.dim > 0) {
                        this.out.print(", ");
                    }
                }
            }
            if (j == methodInfo.parameterTypes.length) break;
            if (j < skipParameters + methodInfo.dim) {
                if (index == null || index.function().length() == 0) {
                    this.out.print("[");
                } else {
                    this.out.print("." + index.function() + "(");
                }
            }
            Annotation passBy = this.by(methodInfo, j);
            cast = this.cast(methodInfo, j);
            AdapterInformation adapterInformation = adapterInfo = methodInfo.parameterRaw[j] ? null : this.adapterInformation(false, methodInfo, j);
            if (FunctionPointer.class.isAssignableFrom(methodInfo.cls) && !methodInfo.cls.isAnnotationPresent(Namespace.class) && methodInfo.valueSetter) {
                String[] typeName = this.cppTypeName(methodInfo.cls);
                cast = "(" + typeName[0] + typeName[1] + ")";
            }
            if (Enum.class.isAssignableFrom(methodInfo.parameterTypes[j])) {
                this.accessesEnums = true;
                this.out.print(cast + "val" + j);
            } else if (("(void*)".equals(cast) || "(void *)".equals(cast)) && methodInfo.parameterTypes[j] == Long.TYPE) {
                this.out.print("jlong_to_ptr(arg" + j + ")");
            } else if (methodInfo.parameterTypes[j].isPrimitive()) {
                if (passBy instanceof ByPtr || passBy instanceof ByPtrRef) {
                    this.out.print("&");
                } else if ((passBy instanceof ByVal || passBy instanceof ByRef) && cast.endsWith("*)")) {
                    this.out.print("*");
                }
                this.out.print(cast + "arg" + j);
            } else if (adapterInfo != null) {
                String cast2;
                cast = adapterInfo.cast.trim();
                if (cast.length() > 0 && !cast.startsWith("(") && !cast.endsWith(")")) {
                    cast = "(" + cast + ")";
                }
                if ((cast2 = adapterInfo.cast2.trim()).length() > 0 && !cast2.startsWith("(") && !cast2.endsWith(")")) {
                    cast2 = "(" + cast2 + ")";
                }
                this.out.print(cast + cast2 + "adapter" + j);
                j += adapterInfo.argc - 1;
            } else if (FunctionPointer.class.isAssignableFrom(methodInfo.parameterTypes[j]) && (!(passBy instanceof ByVal) && !(passBy instanceof ByRef) || cast.length() > 0)) {
                if (passBy instanceof ByPtrRef) {
                    this.out.print(cast + "(ptr" + j + "->ptr)");
                } else {
                    if (passBy instanceof ByVal || passBy instanceof ByRef) {
                        this.out.print("*");
                    }
                    this.out.print(cast + "(ptr" + j + " == NULL ? NULL : " + (passBy instanceof ByPtrPtr ? "&ptr" : "ptr") + j + "->ptr)");
                }
            } else if (passBy instanceof ByVal || passBy instanceof ByRef && methodInfo.parameterTypes[j] != String.class) {
                String nullValue;
                boolean rvalue;
                boolean bl = rvalue = passBy instanceof ByRef ? ((ByRef)passBy).value() : false;
                String string = passBy instanceof ByVal ? ((ByVal)passBy).nullValue() : (nullValue = passBy instanceof ByRef ? ((ByRef)passBy).nullValue() : "");
                if (rvalue) {
                    this.out.print("std::move(");
                }
                this.out.print((nullValue.length() > 0 ? "ptr" + j + " == NULL ? " + nullValue + " : " : "") + "*" + cast + "ptr" + j);
                if (rvalue) {
                    this.out.print(")");
                }
            } else if (passBy instanceof ByPtrPtr) {
                this.out.print(cast + "(arg" + j + " == NULL ? NULL : &ptr" + j + ")");
            } else {
                this.out.print(cast + "ptr" + j);
            }
            if (j < skipParameters + methodInfo.dim) {
                if (index == null || index.function().length() == 0) {
                    this.out.print("]");
                    continue;
                }
                this.out.print(")");
                continue;
            }
            if (j >= methodInfo.parameterTypes.length - 1) continue;
            this.out.print(", ");
        }
        this.out.print(suffix);
        if (methodInfo.memberName.length > 2) {
            this.out.print(methodInfo.memberName[2]);
        }
        if (needSecondCall) {
            this.call(methodInfo, " : ", true);
            this.out.print(")");
        }
    }

    void returnAfter(MethodInformation methodInfo) {
        String suffix;
        String[] stringArray;
        String indent;
        String string = indent = methodInfo.throwsException != null ? "        " : "    ";
        if (methodInfo.allocator || methodInfo.arrayAllocator) {
            stringArray = this.cppTypeName(methodInfo.cls);
        } else if (methodInfo.returnRaw) {
            String[] stringArray2 = new String[1];
            stringArray = stringArray2;
            stringArray2[0] = "";
        } else {
            stringArray = this.cppCastTypeName(methodInfo.returnType, methodInfo.annotations);
        }
        String[] typeName = stringArray;
        Annotation returnBy = this.by(methodInfo.annotations);
        String valueTypeName = Generator.valueTypeName(typeName);
        AdapterInformation adapterInfo = this.adapterInformation(false, valueTypeName, methodInfo.annotations);
        String string2 = suffix = methodInfo.deallocator ? "" : ";";
        if (this.by(methodInfo.annotations) instanceof ByRef && methodInfo.returnType == String.class && adapterInfo == null) {
            this.out.print(");\n" + indent + "rptr = rstr.c_str()");
        }
        if ((methodInfo.allocator || methodInfo.arrayAllocator || !methodInfo.returnType.isPrimitive()) && adapterInfo != null) {
            suffix = ")" + suffix;
        }
        if (Pointer.class.isAssignableFrom(methodInfo.returnType) || methodInfo.returnType.isArray() && methodInfo.returnType.getComponentType().isPrimitive() || Buffer.class.isAssignableFrom(methodInfo.returnType) || methodInfo.returnType == String.class) {
            if ((returnBy instanceof ByVal || returnBy instanceof ByRef && ((ByRef)returnBy).value()) && adapterInfo == null) {
                suffix = ")" + suffix;
            } else if (returnBy instanceof ByPtrPtr) {
                this.out.println(suffix);
                suffix = "";
                this.out.println(indent + "if (rptrptr == NULL) {");
                this.out.println(indent + "    env->ThrowNew(JavaCPP_getClass(env, " + this.jclasses.index(NullPointerException.class) + "), \"Return pointer address is NULL.\");");
                this.out.println(indent + "} else {");
                if (FunctionPointer.class.isAssignableFrom(methodInfo.returnType)) {
                    this.out.println(indent + "    rptr->ptr = *rptrptr;");
                } else {
                    this.out.println(indent + "    rptr = *rptrptr;");
                }
                this.out.println(indent + "}");
            }
            if (adapterInfo != null && methodInfo.returnType.isArray() && methodInfo.returnType.getComponentType().isPrimitive() && !typeName[0].startsWith("const ")) {
                typeName[0] = "const " + typeName[0];
            }
        }
        this.out.println(suffix);
        if (methodInfo.returnType == Void.TYPE) {
            if (methodInfo.allocator || methodInfo.arrayAllocator) {
                boolean noDeallocator;
                boolean bl = noDeallocator = methodInfo.cls == Pointer.class || methodInfo.cls.isAnnotationPresent(NoDeallocator.class) || methodInfo.method.isAnnotationPresent(NoDeallocator.class);
                if (adapterInfo != null) {
                    this.out.println(indent + typeName[0] + " rptr" + typeName[1] + " = radapter;");
                    this.out.println(indent + "jlong rcapacity = (jlong)radapter.size;");
                    this.out.println(indent + "void* rowner = radapter.owner;");
                    if (noDeallocator) {
                        this.out.println(indent + "void (*deallocator)(void*) = 0;");
                    } else {
                        this.out.println(indent + "void (*deallocator)(void*) = rowner != NULL ? &" + adapterInfo.name + "::deallocate : 0;");
                    }
                    this.out.println(indent + "JavaCPP_initPointer(env, obj, rptr, rcapacity, rowner, deallocator);");
                } else {
                    this.out.println(indent + "jlong rcapacity = " + (methodInfo.arrayAllocator ? "arg0;" : "1;"));
                    this.out.print(indent + "JavaCPP_initPointer(env, obj, rptr, rcapacity, rptr, ");
                    if (noDeallocator) {
                        this.out.println("NULL);");
                    } else if (methodInfo.arrayAllocator) {
                        this.out.println("&JavaCPP_" + Generator.mangle(methodInfo.cls.getName()) + "_deallocateArray);");
                        this.arrayDeallocators.index(methodInfo.cls);
                    } else if (!methodInfo.method.isAnnotationPresent(Name.class)) {
                        this.out.println("&JavaCPP_" + Generator.mangle(methodInfo.cls.getName()) + "_deallocate);");
                        this.deallocators.index(methodInfo.cls);
                    } else {
                        this.out.println("NULL);");
                    }
                }
                if (this.virtualFunctions.containsKey(methodInfo.cls)) {
                    valueTypeName = Generator.valueTypeName(typeName);
                    String subType = "JavaCPP_" + Generator.mangle(valueTypeName);
                    this.out.println(indent + "((" + subType + "*)rptr)->obj = env->NewWeakGlobalRef(obj);");
                }
            }
        } else if (!(methodInfo.valueSetter || methodInfo.memberSetter || methodInfo.noReturnGetter)) {
            if (methodInfo.returnType.isPrimitive()) {
                this.out.println(indent + "rarg = (" + Generator.jniTypeName(methodInfo.returnType) + ")rval;");
            } else if (methodInfo.returnRaw) {
                this.out.println(indent + "rarg = rptr;");
            } else if (Enum.class.isAssignableFrom(methodInfo.returnType)) {
                this.accessesEnums = true;
                String s = this.enumValueType(methodInfo.returnType);
                if (s != null) {
                    String S = Character.toUpperCase(s.charAt(0)) + s.substring(1);
                    this.out.println(indent + "if (rarg != NULL) {");
                    this.out.println(indent + "    env->Set" + S + "Field(rarg, JavaCPP_" + s + "ValueFID, (j" + s + ")rval);");
                    this.out.println(indent + "}");
                }
            } else {
                boolean needInit = false;
                if (adapterInfo != null) {
                    this.out.println(indent + "rptr = radapter;");
                    if (methodInfo.returnType != String.class) {
                        this.out.println(indent + "jlong rcapacity = (jlong)radapter.size;");
                        if (Pointer.class.isAssignableFrom(methodInfo.returnType)) {
                            this.out.println(indent + "void* rowner = radapter.owner;");
                            this.out.println(indent + "void (*deallocator)(void*) = rowner != NULL ? &" + adapterInfo.name + "::deallocate : 0;");
                        } else {
                            this.out.println(indent + "void (*deallocator)(void*) = 0;");
                        }
                    }
                    needInit = true;
                } else if (returnBy instanceof ByVal || FunctionPointer.class.isAssignableFrom(methodInfo.returnType)) {
                    this.out.println(indent + "jlong rcapacity = 1;");
                    this.out.println(indent + "void* rowner = (void*)rptr;");
                    this.out.println(indent + "void (*deallocator)(void*) = &JavaCPP_" + Generator.mangle(methodInfo.returnType.getName()) + "_deallocate;");
                    this.deallocators.index(methodInfo.returnType);
                    needInit = true;
                }
                if (Pointer.class.isAssignableFrom(methodInfo.returnType)) {
                    this.out.print(indent);
                    if (!(returnBy instanceof ByVal)) {
                        if (Modifier.isStatic(methodInfo.modifiers) && methodInfo.parameterTypes.length > 0) {
                            for (int i = 0; i < methodInfo.parameterTypes.length; ++i) {
                                String cast = this.cast(methodInfo, i);
                                if (!Arrays.equals(methodInfo.parameterAnnotations[i], methodInfo.annotations) || methodInfo.parameterTypes[i] != methodInfo.returnType || returnBy instanceof ByPtrPtr || returnBy instanceof ByPtrRef) continue;
                                this.out.println("if (rptr == " + cast + "ptr" + i + ") {");
                                this.out.println(indent + "    rarg = arg" + i + ";");
                                this.out.print(indent + "} else ");
                            }
                        } else if (!Modifier.isStatic(methodInfo.modifiers) && methodInfo.cls == methodInfo.returnType) {
                            this.out.println("if (rptr == ptr) {");
                            this.out.println(indent + "    rarg = obj;");
                            this.out.print(indent + "} else ");
                        }
                    }
                    this.out.println("if (rptr != NULL) {");
                    this.out.println(indent + "    rarg = JavaCPP_createPointer(env, " + this.jclasses.index(methodInfo.returnType) + (methodInfo.parameterTypes.length > 0 && methodInfo.parameterTypes[0] == Class.class ? ", arg0);" : ");"));
                    this.out.println(indent + "    if (rarg != NULL) {");
                    if (needInit) {
                        this.out.println(indent + "        JavaCPP_initPointer(env, rarg, rptr, rcapacity, rowner, deallocator);");
                    } else {
                        this.out.println(indent + "        env->SetLongField(rarg, JavaCPP_addressFID, ptr_to_jlong(rptr));");
                    }
                    this.out.println(indent + "    }");
                    this.out.println(indent + "}");
                } else if (methodInfo.returnType == String.class) {
                    this.passesStrings = true;
                    this.out.println(indent + "if (rptr != NULL) {");
                    this.out.println(indent + "    rarg = " + Generator.createString("rptr", adapterInfo != null ? "radapter" : null, Generator.asUtf16(methodInfo.annotations)));
                    this.out.println(indent + "}");
                } else if (methodInfo.returnType.isArray() && methodInfo.returnType.getComponentType().isPrimitive()) {
                    if (adapterInfo == null && !(returnBy instanceof ByVal)) {
                        this.out.println(indent + "jlong rcapacity = rptr != NULL ? 1 : 0;");
                    }
                    String componentName = methodInfo.returnType.getComponentType().getName();
                    String componentNameUpperCase = Character.toUpperCase(componentName.charAt(0)) + componentName.substring(1);
                    this.out.println(indent + "if (rptr != NULL) {");
                    this.out.println(indent + "    rarg = env->New" + componentNameUpperCase + "Array(rcapacity < INT_MAX ? rcapacity : INT_MAX);");
                    this.out.println(indent + "    env->Set" + componentNameUpperCase + "ArrayRegion(rarg, 0, rcapacity < INT_MAX ? rcapacity : INT_MAX, (j" + componentName + "*)rptr);");
                    this.out.println(indent + "}");
                    if (adapterInfo != null) {
                        this.out.println(indent + "if (deallocator != 0 && rptr != NULL) {");
                        this.out.println(indent + "    (*(void(*)(void*))jlong_to_ptr(deallocator))((void*)rptr);");
                        this.out.println(indent + "}");
                    }
                } else if (Buffer.class.isAssignableFrom(methodInfo.returnType)) {
                    if (methodInfo.bufferGetter) {
                        this.out.println(indent + "jlong rposition = position;");
                        this.out.println(indent + "jlong rlimit = limit;");
                        this.out.println(indent + "jlong rcapacity = capacity;");
                    } else if (adapterInfo == null && !(returnBy instanceof ByVal)) {
                        this.out.println(indent + "jlong rcapacity = rptr != NULL ? 1 : 0;");
                    }
                    this.out.println(indent + "if (rptr != NULL) {");
                    this.out.println(indent + "    jlong rcapacityptr = rcapacity * sizeof(rptr[0]);");
                    this.out.println(indent + "    rarg = env->NewDirectByteBuffer((void*)rptr, rcapacityptr < INT_MAX ? rcapacityptr : INT_MAX);");
                    if (methodInfo.bufferGetter) {
                        this.out.println(indent + "    jlong rpositionptr = rposition * sizeof(rptr[0]);");
                        this.out.println(indent + "    jlong rlimitptr = rlimit * sizeof(rptr[0]);");
                        this.out.println(indent + "    env->SetIntField(rarg, JavaCPP_bufferPositionFID, rpositionptr < INT_MAX ? rpositionptr : INT_MAX);");
                        this.out.println(indent + "    env->SetIntField(rarg, JavaCPP_bufferLimitFID, rlimitptr < INT_MAX ? rlimitptr : INT_MAX);");
                    }
                    this.out.println(indent + "}");
                }
            }
        }
    }

    void parametersAfter(MethodInformation methodInfo) {
        int skipParameters;
        if (methodInfo.throwsException != null) {
            this.mayThrowExceptions = true;
            this.out.println("    } catch (...) {");
            this.out.println("        exc = JavaCPP_handleException(env, " + this.jclasses.index(methodInfo.throwsException) + ");");
            this.out.println("    }");
            this.out.println();
        }
        for (int j = skipParameters = methodInfo.parameterTypes.length > 0 && methodInfo.parameterTypes[0] == Class.class ? 1 : 0; j < methodInfo.parameterTypes.length; ++j) {
            if (methodInfo.parameterRaw[j] || Enum.class.isAssignableFrom(methodInfo.parameterTypes[j])) continue;
            Annotation passBy = this.by(methodInfo, j);
            String cast = this.cast(methodInfo, j);
            String[] typeName = this.cppCastTypeName(methodInfo.parameterTypes[j], methodInfo.parameterAnnotations[j]);
            AdapterInformation adapterInfo = this.adapterInformation(true, methodInfo, j);
            if ("void*".equals(typeName[0]) && !methodInfo.parameterTypes[j].isAnnotationPresent(Opaque.class)) {
                typeName[0] = "char*";
            }
            String releaseArrayFlag = cast.contains(" const *") || cast.startsWith("(const ") ? "JNI_ABORT" : "0";
            if (Pointer.class.isAssignableFrom(methodInfo.parameterTypes[j])) {
                if (adapterInfo != null) {
                    for (int k = 0; k < adapterInfo.argc; ++k) {
                        this.out.println("    " + typeName[0] + " rptr" + (j + k) + typeName[1] + " = " + cast + "adapter" + j + ";");
                        this.out.println("    jlong rsize" + (j + k) + " = (jlong)adapter" + j + ".size" + (k > 0 ? k + 1 + ";" : ";"));
                        this.out.println("    void* rowner" + (j + k) + " = adapter" + j + ".owner" + (k > 0 ? k + 1 + ";" : ";"));
                        this.out.println("    if (rptr" + (j + k) + " != " + cast + "ptr" + (j + k) + ") {");
                        this.out.println("        JavaCPP_initPointer(env, arg" + j + ", rptr" + (j + k) + ", rsize" + (j + k) + ", rowner" + (j + k) + ", &" + adapterInfo.name + "::deallocate);");
                        this.out.println("    } else {");
                        this.out.println("        env->SetLongField(arg" + j + ", JavaCPP_limitFID, rsize" + (j + k) + (!methodInfo.parameterTypes[j].isAnnotationPresent(Opaque.class) ? " + position" + (j + k) : "") + ");");
                        this.out.println("    }");
                    }
                    continue;
                }
                if (!(passBy instanceof ByPtrPtr) && !(passBy instanceof ByPtrRef) || methodInfo.valueSetter || methodInfo.memberSetter) continue;
                if (!methodInfo.parameterTypes[j].isAnnotationPresent(Opaque.class)) {
                    this.out.println("    ptr" + j + " -= position" + j + ";");
                }
                this.out.println("    if (arg" + j + " != NULL) env->SetLongField(arg" + j + ", JavaCPP_addressFID, ptr_to_jlong(ptr" + j + "));");
                continue;
            }
            if (methodInfo.parameterTypes[j] == String.class) {
                this.out.println("    " + Generator.releaseStringData("arg" + j, "ptr" + j, Generator.asUtf16(methodInfo, j)));
                continue;
            }
            if (methodInfo.parameterTypes[j].isArray() && methodInfo.parameterTypes[j].getComponentType().isPrimitive()) {
                for (int k = 0; adapterInfo != null && k < adapterInfo.argc; ++k) {
                    this.out.println("    " + typeName[0] + " rptr" + (j + k) + typeName[1] + " = " + cast + "adapter" + j + ";");
                    this.out.println("    void* rowner" + (j + k) + " = adapter" + j + ".owner" + (k > 0 ? k + 1 + ";" : ";"));
                    this.out.println("    if (rptr" + (j + k) + " != " + cast + "ptr" + (j + k) + ") {");
                    this.out.println("        " + adapterInfo.name + "::deallocate(rowner" + (j + k) + ");");
                    this.out.println("    }");
                }
                this.out.print("    if (arg" + j + " != NULL) ");
                if (methodInfo.criticalRegion || methodInfo.valueGetter || methodInfo.valueSetter || methodInfo.memberGetter || methodInfo.memberSetter) {
                    this.out.println("env->ReleasePrimitiveArrayCritical(arg" + j + ", ptr" + j + ", " + releaseArrayFlag + ");");
                    continue;
                }
                String componentType = methodInfo.parameterTypes[j].getComponentType().getName();
                String componentTypeUpperCase = Character.toUpperCase(componentType.charAt(0)) + componentType.substring(1);
                this.out.println("env->Release" + componentTypeUpperCase + "ArrayElements(arg" + j + ", (j" + componentType + "*)ptr" + j + ", " + releaseArrayFlag + ");");
                continue;
            }
            if (!Buffer.class.isAssignableFrom(methodInfo.parameterTypes[j]) || methodInfo.parameterTypes[j] == Buffer.class) continue;
            for (int k = 0; adapterInfo != null && k < adapterInfo.argc; ++k) {
                this.out.println("    " + typeName[0] + " rptr" + (j + k) + typeName[1] + " = " + cast + "adapter" + j + ";");
                this.out.println("    void* rowner" + (j + k) + " = adapter" + j + ".owner" + (k > 0 ? k + 1 + ";" : ";"));
                this.out.println("    if (rptr" + (j + k) + " != " + cast + "ptr" + (j + k) + ") {");
                this.out.println("        " + adapterInfo.name + "::deallocate(rowner" + (j + k) + ");");
                this.out.println("    }");
            }
            this.out.print("    if (arr" + j + " != NULL) ");
            String parameterSimpleName = methodInfo.parameterTypes[j].getSimpleName();
            parameterSimpleName = parameterSimpleName.substring(0, parameterSimpleName.length() - 6);
            String parameterSimpleNameLowerCase = Character.toLowerCase(parameterSimpleName.charAt(0)) + parameterSimpleName.substring(1);
            if (methodInfo.criticalRegion) {
                this.out.println("env->ReleasePrimitiveArrayCritical(arr" + j + ", ptr" + j + " - position" + j + ", " + releaseArrayFlag + ");");
                continue;
            }
            this.out.println("env->Release" + parameterSimpleName + "ArrayElements(arr" + j + ", (j" + parameterSimpleNameLowerCase + "*)(ptr" + j + " - position" + j + "), " + releaseArrayFlag + ");");
        }
    }

    void callback(Class<?> cls, Method callbackMethod, String callbackName, int allocatorMax, boolean needDefinition, MethodInformation methodInfo) {
        String[] typeName;
        boolean throwsExceptions;
        Class<?> callbackReturnType = callbackMethod.getReturnType();
        Class<?>[] callbackParameterTypes = callbackMethod.getParameterTypes();
        Annotation[] callbackAnnotations = callbackMethod.getAnnotations();
        Annotation[][] callbackParameterAnnotations = callbackMethod.getParameterAnnotations();
        String instanceTypeName = Generator.functionClassName(cls);
        String[] callbackTypeName = this.cppFunctionTypeName(callbackMethod);
        String[] returnConvention = callbackTypeName[0].split("\\(");
        String[] returnType = new String[]{returnConvention[0] + (returnConvention.length > 2 ? "(*" : ""), returnConvention.length > 2 ? ")(" + returnConvention[2] : ""};
        if (returnConvention.length > 2) {
            returnConvention = Arrays.copyOfRange(returnConvention, 2, returnConvention.length);
        }
        returnConvention[1] = Generator.constValueTypeName(returnConvention[1]);
        String parameterDeclaration = callbackTypeName[1].substring(1);
        String fieldName = Generator.mangle(callbackMethod.getName()) + "__" + Generator.mangle(Generator.signature(callbackMethod.getParameterTypes()));
        String callbackArguments = "";
        for (int j = 0; j < callbackParameterTypes.length; ++j) {
            String[] typeName2 = this.cppTypeName(callbackParameterTypes[j], callbackParameterAnnotations[j]);
            String valueTypeName = Generator.valueTypeName(typeName2);
            AdapterInformation adapterInfo = this.adapterInformation(false, valueTypeName, callbackParameterAnnotations[j]);
            if (adapterInfo != null) {
                String cast2 = adapterInfo.cast2.trim();
                if (cast2.length() > 0 && !cast2.startsWith("(") && !cast2.endsWith(")")) {
                    cast2 = "(" + cast2 + ")";
                }
                callbackArguments = callbackArguments + cast2;
            }
            callbackArguments = callbackArguments + "arg" + j;
            if (j >= callbackParameterTypes.length - 1) continue;
            callbackArguments = callbackArguments + ", ";
        }
        String firstLine = "";
        if (methodInfo != null) {
            String[] stringArray;
            String nonconstParamDeclaration;
            String string = nonconstParamDeclaration = parameterDeclaration.endsWith(" const") ? parameterDeclaration.substring(0, parameterDeclaration.length() - 6) : parameterDeclaration;
            if (methodInfo.returnRaw) {
                String[] stringArray2 = new String[1];
                stringArray = stringArray2;
                stringArray2[0] = "";
            } else {
                stringArray = this.cppTypeName(methodInfo.cls);
            }
            String[] typeName3 = stringArray;
            String valueTypeName = Generator.valueTypeName(typeName3);
            String subType = "JavaCPP_" + Generator.mangle(valueTypeName);
            Set<String> memberList = this.virtualMembers.get(cls);
            if (memberList == null) {
                memberList = new LinkedHashSet<String>();
                this.virtualMembers.put(cls, memberList);
            }
            String member = "    ";
            if (methodInfo.arrayAllocator) {
                return;
            }
            if (methodInfo.allocator) {
                member = member + subType + nonconstParamDeclaration + " : " + valueTypeName + "(";
                member = member + callbackArguments + "), obj(NULL) { }";
            } else {
                Set<String> functionList = this.virtualFunctions.get(cls);
                if (functionList == null) {
                    functionList = new LinkedHashSet<String>();
                    this.virtualFunctions.put(cls, functionList);
                }
                String usingLine = "using " + valueTypeName + "::" + methodInfo.memberName[0] + ";";
                boolean needUsing = true;
                for (String s : memberList) {
                    if (!s.split("\n", 2)[0].equals(member + usingLine)) continue;
                    needUsing = false;
                    break;
                }
                if (needUsing) {
                    member = member + usingLine + "\n    ";
                }
                member = member + "virtual " + returnType[0] + (returnConvention.length > 1 ? returnConvention[1] : "") + methodInfo.memberName[0] + parameterDeclaration + returnType[1] + " JavaCPP_override;\n    " + returnType[0] + "super_" + methodInfo.name + nonconstParamDeclaration + returnType[1] + " { ";
                if (methodInfo.method.getAnnotation(Virtual.class).value()) {
                    member = member + "throw JavaCPP_exception(\"Cannot call pure virtual function " + valueTypeName + "::" + methodInfo.memberName[0] + "().\"); }";
                } else {
                    member = member + (callbackReturnType != Void.TYPE ? "return " : "") + valueTypeName + "::" + methodInfo.memberName[0] + "(";
                    member = member + callbackArguments + "); }";
                }
                firstLine = returnType[0] + (returnConvention.length > 1 ? returnConvention[1] : "") + subType + "::" + methodInfo.memberName[0] + parameterDeclaration + returnType[1] + " {";
                functionList.add(fieldName);
            }
            memberList.add(member);
        } else if (callbackName != null) {
            int i;
            this.callbacks.put(instanceTypeName, "static " + instanceTypeName + " " + instanceTypeName + "_instances[" + allocatorMax + "];");
            Convention convention = cls.getAnnotation(Convention.class);
            if (convention != null && !convention.extern().equals("C")) {
                this.out.println("extern \"" + convention.extern() + "\" {");
                if (this.out2 != null) {
                    this.out2.println("extern \"" + convention.extern() + "\" {");
                }
            }
            for (i = 0; i < allocatorMax; ++i) {
                if (this.out2 != null) {
                    this.out2.println("JNIIMPORT " + returnType[0] + (returnConvention.length > 1 ? returnConvention[1] : "") + callbackName + (i > 0 ? Integer.valueOf(i) : "") + parameterDeclaration + returnType[1] + ";");
                }
                this.out.println("JNIEXPORT " + returnType[0] + (returnConvention.length > 1 ? returnConvention[1] : "") + callbackName + (i > 0 ? Integer.valueOf(i) : "") + parameterDeclaration + returnType[1] + " {");
                this.out.print((callbackReturnType != Void.TYPE ? "    return " : "    ") + instanceTypeName + "_instances[" + i + "](");
                this.out.println(callbackArguments + ");");
                this.out.println("}");
            }
            if (convention != null && !convention.extern().equals("C")) {
                this.out.println("}");
                if (this.out2 != null) {
                    this.out2.println("}");
                }
            }
            this.out.println("static " + returnType[0] + "(" + (returnConvention.length > 1 ? returnConvention[1] : "") + "*" + callbackName + "s[" + allocatorMax + "])" + parameterDeclaration + returnType[1] + " = {");
            for (i = 0; i < allocatorMax; ++i) {
                this.out.print("        " + callbackName + (i > 0 ? Integer.valueOf(i) : ""));
                if (i + 1 >= allocatorMax) continue;
                this.out.println(",");
            }
            this.out.println(" };");
            firstLine = returnType[0] + instanceTypeName + "::operator()" + parameterDeclaration + returnType[1] + " {";
        }
        if (!needDefinition) {
            return;
        }
        this.out.println(firstLine);
        String returnPrefix = "";
        if (callbackReturnType != Void.TYPE) {
            this.out.println("    " + Generator.jniTypeName(callbackReturnType) + " rarg = 0;");
            returnPrefix = "rarg = ";
            if (callbackReturnType == String.class) {
                returnPrefix = returnPrefix + "(jstring)";
            }
        }
        String callbackReturnCast = this.cast(callbackReturnType, callbackAnnotations);
        Annotation returnBy = this.by(callbackAnnotations);
        String[] returnTypeName = this.cppTypeName(callbackReturnType, callbackAnnotations);
        String returnValueTypeName = Generator.valueTypeName(returnTypeName);
        AdapterInformation returnAdapterInfo = this.adapterInformation(false, returnValueTypeName, callbackAnnotations);
        boolean bl = throwsExceptions = !Generator.noException(cls, callbackMethod);
        if (throwsExceptions) {
            this.out.println("    jthrowable exc = NULL;");
        }
        this.out.println("    JNIEnv* env;");
        this.out.println("    bool attached = JavaCPP_getEnv(&env);");
        this.out.println("    if (env == NULL) {");
        this.out.println("        goto end;");
        this.out.println("    }");
        this.out.println("{");
        if (callbackParameterTypes.length > 0) {
            this.out.println("    jvalue args[" + callbackParameterTypes.length + "];");
            for (int j = 0; j < callbackParameterTypes.length; ++j) {
                Annotation passBy = this.by(callbackParameterAnnotations[j]);
                if (callbackParameterTypes[j].isPrimitive()) {
                    this.out.println("    args[" + j + "]." + Generator.signature(callbackParameterTypes[j]).toLowerCase() + " = (" + Generator.jniTypeName(callbackParameterTypes[j]) + ")" + (passBy instanceof ByPtr || passBy instanceof ByPtrRef ? "*arg" : "arg") + j + ";");
                    continue;
                }
                if (Enum.class.isAssignableFrom(callbackParameterTypes[j])) {
                    this.accessesEnums = true;
                    String s = this.enumValueType(callbackParameterTypes[j]);
                    if (s == null) continue;
                    String S = Character.toUpperCase(s.charAt(0)) + s.substring(1);
                    this.out.println("    jobject obj" + j + " = JavaCPP_createPointer(env, " + this.jclasses.index(callbackParameterTypes[j]) + ");");
                    this.out.println("    args[" + j + "].l = obj" + j + ";");
                    this.out.println("    if (obj" + j + " != NULL) {");
                    this.out.println("        env->Set" + S + "Field(obj" + j + ", JavaCPP_" + s + "ValueFID, (j" + s + ")arg" + j + ");");
                    this.out.println("    }");
                    continue;
                }
                typeName = this.cppTypeName(callbackParameterTypes[j], callbackParameterAnnotations[j]);
                String valueTypeName = Generator.valueTypeName(typeName);
                AdapterInformation adapterInfo = this.adapterInformation(false, valueTypeName, callbackParameterAnnotations[j]);
                if (adapterInfo != null) {
                    this.usesAdapters = true;
                    String cast2 = adapterInfo.cast2.trim();
                    if (cast2.length() > 0 && !cast2.startsWith("(") && !cast2.endsWith(")")) {
                        cast2 = "(" + cast2 + ")";
                    }
                    this.out.println("    " + adapterInfo.name + " adapter" + j + "(" + cast2 + "arg" + j + ");");
                }
                if (Pointer.class.isAssignableFrom(callbackParameterTypes[j]) || Buffer.class.isAssignableFrom(callbackParameterTypes[j]) || callbackParameterTypes[j].isArray() && callbackParameterTypes[j].getComponentType().isPrimitive()) {
                    String cast = "(" + typeName[0] + typeName[1] + ")";
                    if (FunctionPointer.class.isAssignableFrom(callbackParameterTypes[j])) {
                        this.functions.index(callbackParameterTypes[j]);
                        typeName[0] = Generator.functionClassName(callbackParameterTypes[j]) + "*";
                        typeName[1] = "";
                        valueTypeName = Generator.valueTypeName(typeName);
                    } else if (this.virtualFunctions.containsKey(callbackParameterTypes[j])) {
                        String subType;
                        valueTypeName = subType = "JavaCPP_" + Generator.mangle(valueTypeName);
                    }
                    this.out.println("    " + Generator.jniTypeName(callbackParameterTypes[j]) + " obj" + j + " = NULL;");
                    this.out.println("    " + typeName[0] + " ptr" + j + typeName[1] + " = NULL;");
                    if (FunctionPointer.class.isAssignableFrom(callbackParameterTypes[j])) {
                        this.out.println("    ptr" + j + " = new (std::nothrow) " + valueTypeName + ";");
                        this.out.println("    if (ptr" + j + " != NULL) {");
                        this.out.println("        ptr" + j + "->ptr = " + cast + "&arg" + j + ";");
                        this.out.println("    }");
                    } else if (adapterInfo != null) {
                        this.out.println("    ptr" + j + " = adapter" + j + ";");
                    } else if (passBy instanceof ByVal && callbackParameterTypes[j] != Pointer.class) {
                        this.out.println("    ptr" + j + (Generator.noException(callbackParameterTypes[j], callbackMethod) ? " = new (std::nothrow) " : " = new ") + valueTypeName + typeName[1] + "(*" + cast + "&arg" + j + ");");
                    } else if (passBy instanceof ByVal || passBy instanceof ByRef) {
                        this.out.println("    ptr" + j + " = " + cast + "&arg" + j + ";");
                    } else if (passBy instanceof ByPtrPtr) {
                        this.out.println("    if (arg" + j + " == NULL) {");
                        this.out.println("        JavaCPP_log(\"Pointer address of argument " + j + " is NULL in callback for " + cls.getCanonicalName() + ".\");");
                        this.out.println("    } else {");
                        this.out.println("        ptr" + j + " = " + cast + "*arg" + j + ";");
                        this.out.println("    }");
                    } else {
                        this.out.println("    ptr" + j + " = " + cast + "arg" + j + ";");
                    }
                }
                boolean needInit = false;
                if (adapterInfo != null) {
                    if (callbackParameterTypes[j] != String.class) {
                        this.out.println("    jlong size" + j + " = (jlong)adapter" + j + ".size;");
                        this.out.println("    void* owner" + j + " = adapter" + j + ".owner;");
                        this.out.println("    void (*deallocator" + j + ")(void*) = &" + adapterInfo.name + "::deallocate;");
                    }
                    needInit = true;
                } else if (passBy instanceof ByVal && callbackParameterTypes[j] != Pointer.class || FunctionPointer.class.isAssignableFrom(callbackParameterTypes[j])) {
                    this.out.println("    jlong size" + j + " = 1;");
                    this.out.println("    void* owner" + j + " = ptr" + j + ";");
                    this.out.println("    void (*deallocator" + j + ")(void*) = &JavaCPP_" + Generator.mangle(callbackParameterTypes[j].getName()) + "_deallocate;");
                    this.deallocators.index(callbackParameterTypes[j]);
                    needInit = true;
                }
                if (Pointer.class.isAssignableFrom(callbackParameterTypes[j])) {
                    String s = "    obj" + j + " = JavaCPP_createPointer(env, " + this.jclasses.index(callbackParameterTypes[j]) + ");";
                    adapterInfo = this.adapterInformation(true, valueTypeName, callbackParameterAnnotations[j]);
                    if (adapterInfo != null || passBy instanceof ByPtrPtr || passBy instanceof ByPtrRef) {
                        this.out.println(s);
                    } else {
                        this.out.println("    if (ptr" + j + " != NULL) { ");
                        this.out.println("    " + s);
                        this.out.println("    }");
                    }
                    this.out.println("    if (obj" + j + " != NULL) { ");
                    if (needInit) {
                        this.out.println("        JavaCPP_initPointer(env, obj" + j + ", ptr" + j + ", size" + j + ", owner" + j + ", deallocator" + j + ");");
                    } else {
                        this.out.println("        env->SetLongField(obj" + j + ", JavaCPP_addressFID, ptr_to_jlong(ptr" + j + "));");
                    }
                    this.out.println("    }");
                    this.out.println("    args[" + j + "].l = obj" + j + ";");
                    continue;
                }
                if (callbackParameterTypes[j] == String.class) {
                    this.passesStrings = true;
                    if (passBy instanceof ByPtrPtr) {
                        typeName[0] = valueTypeName;
                    }
                    if (!typeName[0].startsWith("const ")) {
                        typeName[0] = "const " + typeName[0];
                    }
                    this.out.println("    " + typeName[0] + " ptr" + j + typeName[1] + " = (" + typeName[0] + typeName[1] + ")" + (adapterInfo != null ? "adapter" : "arg") + j + ";");
                    this.out.println("    jstring obj" + j + " = " + Generator.createString("ptr" + j, adapterInfo != null ? "adapter" + j : null, Generator.asUtf16(callbackParameterAnnotations[j])));
                    this.out.println("    args[" + j + "].l = obj" + j + ";");
                    continue;
                }
                if (callbackParameterTypes[j].isArray() && callbackParameterTypes[j].getComponentType().isPrimitive()) {
                    if (adapterInfo == null) {
                        this.out.println("    jlong size" + j + " = ptr" + j + " != NULL ? 1 : 0;");
                    }
                    String componentType = callbackParameterTypes[j].getComponentType().getName();
                    String S = Character.toUpperCase(componentType.charAt(0)) + componentType.substring(1);
                    this.out.println("    if (ptr" + j + " != NULL) {");
                    this.out.println("        obj" + j + " = env->New" + S + "Array(size" + j + " < INT_MAX ? size" + j + " : INT_MAX);");
                    this.out.println("        env->Set" + S + "ArrayRegion(obj" + j + ", 0, size" + j + " < INT_MAX ? size" + j + " : INT_MAX, (j" + componentType + "*)ptr" + j + ");");
                    this.out.println("    }");
                    if (adapterInfo != null) {
                        this.out.println("    if (deallocator" + j + " != 0 && ptr" + j + " != NULL) {");
                        this.out.println("        (*(void(*)(void*))jlong_to_ptr(deallocator" + j + "))((void*)ptr" + j + ");");
                        this.out.println("    }");
                    }
                    this.out.println("    args[" + j + "].l = obj" + j + ";");
                    continue;
                }
                if (Buffer.class.isAssignableFrom(callbackParameterTypes[j])) {
                    if (adapterInfo == null) {
                        this.out.println("    jlong size" + j + " = ptr" + j + " != NULL ? 1 : 0;");
                    }
                    this.out.println("    if (ptr" + j + " != NULL) {");
                    this.out.println("        jlong sizeptr = size" + j + " * sizeof(ptr" + j + "[0]);");
                    this.out.println("        obj" + j + " = env->NewDirectByteBuffer((void*)ptr" + j + ", sizeptr < INT_MAX ? sizeptr : INT_MAX);");
                    this.out.println("    }");
                    this.out.println("    args[" + j + "].l = obj" + j + ";");
                    continue;
                }
                this.logger.warn("Callback \"" + callbackMethod + "\" has unsupported parameter type \"" + callbackParameterTypes[j].getCanonicalName() + "\". Compilation will most likely fail.");
            }
        }
        if (methodInfo != null) {
            Virtual virtual = callbackMethod.getAnnotation(Virtual.class);
            String methodName = virtual != null && virtual.method().length() > 0 ? virtual.method() : methodInfo.method.getName();
            this.out.println("    if (" + fieldName + " == NULL) {");
            this.out.println("        " + fieldName + " = JavaCPP_getMethodID(env, " + this.jclasses.index(cls) + ", \"" + methodName + "\", \"(" + Generator.signature(methodInfo.method.getParameterTypes()) + ")" + Generator.signature(methodInfo.method.getReturnType()) + "\");");
            this.out.println("    }");
            this.out.println("    jmethodID mid = " + fieldName + ";");
        } else if (callbackName != null) {
            this.out.println("    if (obj == NULL) {");
            this.out.println("        obj = JavaCPP_createPointer(env, " + this.jclasses.index(cls) + ");");
            this.out.println("        obj = obj == NULL ? NULL : env->NewGlobalRef(obj);");
            this.out.println("        if (obj == NULL) {");
            this.out.println("            JavaCPP_log(\"Error creating global reference of " + cls.getCanonicalName() + " instance for callback.\");");
            this.out.println("        } else {");
            this.out.println("            env->SetLongField(obj, JavaCPP_addressFID, ptr_to_jlong(this));");
            this.out.println("        }");
            this.out.println("        for (int i = 0; i < " + allocatorMax + "; i++) {");
            this.out.println("            if (this == &" + instanceTypeName + "_instances[i]) {");
            this.out.println("                ptr = " + callbackName + "s[i];");
            this.out.println("                break;");
            this.out.println("            }");
            this.out.println("        }");
            this.out.println("    }");
            this.out.println("    if (mid == NULL) {");
            this.out.println("        mid = JavaCPP_getMethodID(env, " + this.jclasses.index(cls) + ", \"" + callbackMethod.getName() + "\", \"(" + Generator.signature(callbackMethod.getParameterTypes()) + ")" + Generator.signature(callbackMethod.getReturnType()) + "\");");
            this.out.println("    }");
        }
        this.out.println("    if (obj == NULL) {");
        this.out.println("        JavaCPP_log(\"Function pointer object is NULL in callback for " + cls.getCanonicalName() + ".\");");
        this.out.println("    } else if (mid == NULL) {");
        this.out.println("        JavaCPP_log(\"Error getting method ID of function caller \\\"" + callbackMethod + "\\\" for callback.\");");
        this.out.println("    } else {");
        String s = "Object";
        if (callbackReturnType.isPrimitive()) {
            s = callbackReturnType.getName();
            s = Character.toUpperCase(s.charAt(0)) + s.substring(1);
        }
        this.out.println("        " + returnPrefix + "env->Call" + s + "MethodA(obj, mid, " + (callbackParameterTypes.length == 0 ? "NULL);" : "args);"));
        if (throwsExceptions) {
            this.out.println("        if ((exc = env->ExceptionOccurred()) != NULL) {");
            this.out.println("            env->ExceptionClear();");
            this.out.println("        }");
        }
        this.out.println("    }");
        for (int j = 0; j < callbackParameterTypes.length; ++j) {
            if (Pointer.class.isAssignableFrom(callbackParameterTypes[j])) {
                typeName = this.cppTypeName(callbackParameterTypes[j], callbackParameterAnnotations[j]);
                Annotation passBy = this.by(callbackParameterAnnotations[j]);
                String cast = this.cast(callbackParameterTypes[j], callbackParameterAnnotations[j]);
                String valueTypeName = Generator.valueTypeName(typeName);
                AdapterInformation adapterInfo = this.adapterInformation(true, valueTypeName, callbackParameterAnnotations[j]);
                if ("void*".equals(typeName[0]) && !callbackParameterTypes[j].isAnnotationPresent(Opaque.class)) {
                    typeName[0] = "char*";
                }
                if (adapterInfo != null || passBy instanceof ByPtrPtr || passBy instanceof ByPtrRef) {
                    this.out.println("    " + typeName[0] + " rptr" + j + typeName[1] + " = (" + typeName[0] + typeName[1] + ")jlong_to_ptr(env->GetLongField(obj" + j + ", JavaCPP_addressFID));");
                    if (adapterInfo != null) {
                        this.out.println("    jlong rsize" + j + " = env->GetLongField(obj" + j + ", JavaCPP_limitFID);");
                        this.out.println("    void* rowner" + j + " = JavaCPP_getPointerOwner(env, obj" + j + ");");
                    }
                    if (!callbackParameterTypes[j].isAnnotationPresent(Opaque.class) && !FunctionPointer.class.isAssignableFrom(callbackParameterTypes[j])) {
                        this.out.println("    jlong rposition" + j + " = env->GetLongField(obj" + j + ", JavaCPP_positionFID);");
                        this.out.println("    rptr" + j + " += rposition" + j + ";");
                        if (adapterInfo != null) {
                            this.out.println("    rsize" + j + " -= rposition" + j + ";");
                        }
                    }
                    if (adapterInfo != null) {
                        this.out.println("    adapter" + j + ".assign(rptr" + j + ", rsize" + j + ", rowner" + j + ");");
                    } else if (passBy instanceof ByPtrPtr) {
                        this.out.println("    if (arg" + j + " != NULL) {");
                        this.out.println("        *arg" + j + " = *" + cast + "&rptr" + j + ";");
                        this.out.println("    }");
                    } else if (passBy instanceof ByPtrRef) {
                        this.out.println("    arg" + j + " = " + cast + "rptr" + j + ";");
                    }
                }
            }
            if (callbackParameterTypes[j].isPrimitive()) continue;
            this.out.println("    env->DeleteLocalRef(obj" + j + ");");
        }
        this.out.println("}");
        this.out.println("end:");
        if (callbackReturnType != Void.TYPE) {
            if ("void*".equals(returnTypeName[0]) && !callbackReturnType.isAnnotationPresent(Opaque.class)) {
                returnTypeName[0] = "char*";
            }
            if (Enum.class.isAssignableFrom(callbackReturnType)) {
                this.accessesEnums = true;
                s = this.enumValueType(callbackReturnType);
                if (s != null) {
                    String S = Character.toUpperCase(s.charAt(0)) + s.substring(1);
                    this.out.println("    if (rarg == NULL) {");
                    this.out.println("        JavaCPP_log(\"Enum for return is NULL in callback for " + cls.getCanonicalName() + ".\");");
                    this.out.println("    }");
                    this.out.println("    " + returnTypeName[0] + " rval" + returnTypeName[1] + " = (" + returnTypeName[0] + returnTypeName[1] + ")(rarg == NULL ? 0 : env->Get" + S + "Field(rarg, JavaCPP_" + s + "ValueFID));");
                }
            } else if (Pointer.class.isAssignableFrom(callbackReturnType)) {
                if (FunctionPointer.class.isAssignableFrom(callbackReturnType)) {
                    this.functions.index(callbackReturnType);
                    returnTypeName[0] = Generator.functionClassName(callbackReturnType) + "*";
                    returnTypeName[1] = "";
                }
                this.out.println("    " + returnTypeName[0] + " rptr" + returnTypeName[1] + " = rarg == NULL ? NULL : (" + returnTypeName[0] + returnTypeName[1] + ")jlong_to_ptr(env->GetLongField(rarg, JavaCPP_addressFID));");
                if (returnAdapterInfo != null) {
                    this.out.println("    jlong rsize = rarg == NULL ? 0 : env->GetLongField(rarg, JavaCPP_limitFID);");
                    this.out.println("    void* rowner = JavaCPP_getPointerOwner(env, rarg);");
                }
                if (!callbackReturnType.isAnnotationPresent(Opaque.class)) {
                    this.out.println("    jlong rposition = rarg == NULL ? 0 : env->GetLongField(rarg, JavaCPP_positionFID);");
                    this.out.println("    rptr += rposition;");
                    if (returnAdapterInfo != null) {
                        this.out.println("    rsize -= rposition;");
                    }
                }
            } else if (callbackReturnType == String.class) {
                this.passesStrings = true;
                this.out.println("    " + returnTypeName[0] + " rptr" + returnTypeName[1] + " = " + Generator.getStringData("rarg", Generator.asUtf16(callbackAnnotations)));
                if (returnAdapterInfo != null) {
                    this.out.println("    jlong rsize = 0;");
                    this.out.println("    void* rowner = (void*)rptr;");
                }
            } else if (Buffer.class.isAssignableFrom(callbackReturnType)) {
                this.out.println("    " + returnTypeName[0] + " rptr" + returnTypeName[1] + " = rarg == NULL ? NULL : (" + returnTypeName[0] + returnTypeName[1] + ")env->GetDirectBufferAddress(rarg);");
                if (returnAdapterInfo != null) {
                    this.out.println("    jlong rsize = rarg == NULL ? 0 : env->GetIntField(rarg, JavaCPP_bufferLimitFID);");
                    this.out.println("    void* rowner = (void*)rptr;");
                    this.out.println("    jlong rposition = rarg == NULL ? 0 : env->GetIntField(rarg, JavaCPP_bufferPositionFID);");
                    this.out.println("    rptr += rposition;");
                    this.out.println("    rsize -= rposition;");
                }
            } else if (!callbackReturnType.isPrimitive()) {
                this.logger.warn("Callback \"" + callbackMethod + "\" has unsupported return type \"" + callbackReturnType.getCanonicalName() + "\". Compilation will most likely fail.");
            }
        }
        this.passesStrings = true;
        if (throwsExceptions) {
            this.out.println("    if (exc != NULL) {");
            this.out.println("        jstring str = (jstring)env->CallObjectMethod(exc, JavaCPP_toStringMID);");
            this.out.println("        env->DeleteLocalRef(exc);");
            this.out.println("        const char *msg = JavaCPP_getStringBytes(env, str);");
            this.out.println("        JavaCPP_exception e(msg);");
            this.out.println("        JavaCPP_releaseStringBytes(env, str, msg);");
            this.out.println("        env->DeleteLocalRef(str);");
            this.out.println("        JavaCPP_detach(attached);");
            this.out.println("        throw e;");
            this.out.println("    } else {");
            this.out.println("        JavaCPP_detach(attached);");
            this.out.println("    }");
        } else {
            this.out.println("    JavaCPP_detach(attached);");
        }
        if (callbackReturnType != Void.TYPE) {
            if (callbackReturnType.isPrimitive()) {
                this.out.println("    return " + callbackReturnCast + (returnBy instanceof ByPtr || returnBy instanceof ByPtrRef ? "&rarg;" : "rarg;"));
            } else if (Enum.class.isAssignableFrom(callbackReturnType)) {
                this.out.println("    return " + callbackReturnCast + "rval;");
            } else if (returnAdapterInfo != null) {
                this.usesAdapters = true;
                this.out.println("    return " + returnAdapterInfo.name + "(" + callbackReturnCast + "rptr, rsize, rowner);");
            } else if (FunctionPointer.class.isAssignableFrom(callbackReturnType)) {
                this.functions.index(callbackReturnType);
                this.out.println("    return " + callbackReturnCast + "(rptr == NULL ? NULL : rptr->ptr);");
            } else if (returnBy instanceof ByVal || returnBy instanceof ByRef) {
                this.out.println("    if (rptr == NULL) {");
                this.out.println("        JavaCPP_log(\"Return pointer address is NULL in callback for " + cls.getCanonicalName() + ".\");");
                this.out.println("    }");
                this.out.println("    return *" + callbackReturnCast + "rptr;");
            } else if (returnBy instanceof ByPtrPtr) {
                this.out.println("    return " + callbackReturnCast + "&rptr;");
            } else {
                this.out.println("    return " + callbackReturnCast + "rptr;");
            }
        }
        this.out.println("}");
    }

    void callbackAllocator(Class<?> cls, String callbackName, int allocatorMax) {
        String[] typeName = this.cppTypeName(cls);
        String instanceTypeName = Generator.functionClassName(cls);
        this.out.println("    obj = env->NewWeakGlobalRef(obj);");
        this.out.println("    if (obj == NULL) {");
        this.out.println("        JavaCPP_log(\"Error creating global reference of " + cls.getCanonicalName() + " instance for callback.\");");
        this.out.println("        return;");
        this.out.println("    }");
        this.out.println("    " + instanceTypeName + "* rptr = new (std::nothrow) " + instanceTypeName + ";");
        this.out.println("    if (rptr != NULL) {");
        this.out.println("        rptr->obj = obj;");
        this.out.println("        JavaCPP_initPointer(env, obj, rptr, 1, rptr, &JavaCPP_" + Generator.mangle(cls.getName()) + "_deallocate);");
        this.deallocators.index(cls);
        if (callbackName != null) {
            this.out.println("        for (int i = 0; i < " + allocatorMax + "; i++) {");
            this.out.println("            if (" + instanceTypeName + "_instances[i].obj == NULL) {");
            this.out.println("                rptr->ptr = " + callbackName + "s[i];");
            this.out.println("                " + instanceTypeName + "_instances[i] = *rptr;");
            this.out.println("                break;");
            this.out.println("            }");
            this.out.println("        }");
        } else {
            this.out.println("        rptr->ptr = (" + typeName[0] + typeName[1] + ")jlong_to_ptr(arg0);");
        }
        this.out.println("    }");
        this.out.println("}");
    }

    static String functionClassName(Class<?> cls) {
        Name name = cls.getAnnotation(Name.class);
        return name != null ? name.value()[0] : "JavaCPP_" + Generator.mangle(cls.getName());
    }

    static Method[] functionMethods(Class<?> cls, Method[] methods, boolean[] callbackAllocators) {
        if (!FunctionPointer.class.isAssignableFrom(cls)) {
            return null;
        }
        Method[] functionMethods = new Method[3];
        for (int i = 0; i < methods.length; ++i) {
            String methodName = methods[i].getName();
            int modifiers = methods[i].getModifiers();
            Class<?>[] parameterTypes = methods[i].getParameterTypes();
            Class<?> returnType = methods[i].getReturnType();
            if (Modifier.isStatic(modifiers)) continue;
            if (callbackAllocators != null && methodName.startsWith("allocate") && Modifier.isNative(modifiers) && returnType == Void.TYPE && (parameterTypes.length == 0 || parameterTypes.length == 1 && (parameterTypes[0] == Integer.TYPE || parameterTypes[0] == Long.TYPE))) {
                callbackAllocators[i] = true;
                continue;
            }
            if (methodName.startsWith("call") || methodName.startsWith("apply")) {
                functionMethods[0] = methods[i];
                continue;
            }
            if (methodName.startsWith("get") && Modifier.isNative(modifiers) && cls.isAnnotationPresent(Namespace.class)) {
                functionMethods[1] = methods[i];
                continue;
            }
            if (!methodName.startsWith("put") || !Modifier.isNative(modifiers) || !cls.isAnnotationPresent(Namespace.class)) continue;
            functionMethods[2] = methods[i];
        }
        return functionMethods[0] != null || functionMethods[1] != null || functionMethods[2] != null ? functionMethods : null;
    }

    MethodInformation methodInformation(Method method) {
        String[] stringArray;
        MethodInformation info = new MethodInformation();
        info.cls = method.getDeclaringClass();
        info.method = method;
        info.annotations = method.getAnnotations();
        info.modifiers = method.getModifiers();
        info.returnType = method.getReturnType();
        info.name = method.getName();
        Name name = method.getAnnotation(Name.class);
        if (name != null) {
            stringArray = name.value();
        } else {
            String[] stringArray2 = new String[1];
            stringArray = stringArray2;
            stringArray2[0] = info.name;
        }
        info.memberName = stringArray;
        Index index = method.getAnnotation(Index.class);
        info.allocatorMax = Generator.allocatorMax(info.cls, info.method);
        info.dim = index != null ? index.value() : 0;
        info.parameterTypes = method.getParameterTypes();
        info.parameterAnnotations = method.getParameterAnnotations();
        info.criticalRegion = Generator.criticalRegion(info.cls, info.method);
        info.returnRaw = method.isAnnotationPresent(Raw.class);
        info.withEnv = info.returnRaw ? method.getAnnotation(Raw.class).withEnv() : false;
        info.parameterRaw = new boolean[info.parameterAnnotations.length];
        for (int i = 0; i < info.parameterAnnotations.length; ++i) {
            for (int j = 0; j < info.parameterAnnotations[i].length; ++j) {
                if (!(info.parameterAnnotations[i][j] instanceof Raw)) continue;
                info.parameterRaw[i] = true;
                info.withEnv |= ((Raw)info.parameterAnnotations[i][j]).withEnv();
            }
        }
        boolean canBeGetter = info.returnType != Void.TYPE || info.parameterTypes.length > 0 && info.parameterTypes[0].isArray() && info.parameterTypes[0].getComponentType().isPrimitive();
        boolean canBeSetter = (info.returnType == Void.TYPE || info.returnType == info.cls) && info.parameterTypes.length > 0;
        boolean canBeAllocator = !Modifier.isStatic(info.modifiers) && info.returnType == Void.TYPE;
        boolean canBeArrayAllocator = canBeAllocator && info.parameterTypes.length == 1 && (info.parameterTypes[0] == Integer.TYPE || info.parameterTypes[0] == Long.TYPE);
        boolean valueGetter = false;
        boolean valueSetter = false;
        boolean memberGetter = false;
        boolean memberSetter = false;
        boolean noReturnGetter = false;
        Method pairedMethod = null;
        for (Method method2 : info.cls.getDeclaredMethods()) {
            boolean parameterAsReturn2;
            int skipParameters2;
            MethodInformation info2 = this.annotationCache.get(method2);
            if (info2 == null) {
                info2 = new MethodInformation();
                this.annotationCache.put(method2, info2);
                info2.modifiers = method2.getModifiers();
                info2.returnType = method2.getReturnType();
                info2.name = method2.getName();
                info2.parameterTypes = method2.getParameterTypes();
                info2.annotations = method2.getAnnotations();
                info2.parameterAnnotations = method2.getParameterAnnotations();
            }
            int skipParameters = info.parameterTypes.length > 0 && info.parameterTypes[0] == Class.class ? 1 : 0;
            int n = skipParameters2 = info2.parameterTypes.length > 0 && info2.parameterTypes[0] == Class.class ? 1 : 0;
            if (method.equals(method2) || !Modifier.isNative(info2.modifiers)) continue;
            boolean canBeValueGetter = false;
            boolean canBeValueSetter = false;
            boolean canBeMemberGetter = false;
            boolean canBeMemberSetter = false;
            if (canBeGetter && "get".equals(info.name) && "put".equals(info2.name) && name == null) {
                canBeValueGetter = true;
            } else if (canBeSetter && "put".equals(info.name) && "get".equals(info2.name) && name == null) {
                canBeValueSetter = true;
            } else {
                if (!info2.name.equals(info.name)) continue;
                info.overloaded = true;
                canBeMemberGetter = canBeGetter;
                canBeMemberSetter = canBeSetter;
            }
            boolean sameIndexParameters = true;
            for (int j = 0; j < info.parameterTypes.length - skipParameters && j < info2.parameterTypes.length - skipParameters2; ++j) {
                if (info.parameterTypes[j + skipParameters] == info2.parameterTypes[j + skipParameters2]) continue;
                sameIndexParameters = false;
            }
            if (!sameIndexParameters) continue;
            boolean parameterAsReturn = canBeValueGetter && info.parameterTypes.length > 0 && info.parameterTypes[0].isArray() && info.parameterTypes[0].getComponentType().isPrimitive();
            boolean bl = parameterAsReturn2 = canBeValueSetter && info2.parameterTypes.length > 0 && info2.parameterTypes[0].isArray() && info2.parameterTypes[0].getComponentType().isPrimitive();
            if (canBeGetter && info2.parameterTypes.length - (parameterAsReturn ? 0 : 1) == info.parameterTypes.length - skipParameters && (parameterAsReturn ? info.parameterTypes[info.parameterTypes.length - 1] : info.returnType) == info2.parameterTypes[info2.parameterTypes.length - 1] && (info2.returnType == Void.TYPE || info2.returnType == info.cls) && (info2.parameterAnnotations[info2.parameterAnnotations.length - 1].length == 0 || Arrays.equals(info2.parameterAnnotations[info2.parameterAnnotations.length - 1], info.annotations))) {
                pairedMethod = method2;
                valueGetter = canBeValueGetter;
                memberGetter = canBeMemberGetter;
                noReturnGetter = parameterAsReturn;
            } else if (canBeSetter && info.parameterTypes.length - (parameterAsReturn2 ? 0 : 1) == info2.parameterTypes.length - skipParameters2 && (parameterAsReturn2 ? info2.parameterTypes[info2.parameterTypes.length - 1] : info2.returnType) == info.parameterTypes[info.parameterTypes.length - 1] && (info.returnType == Void.TYPE || info.returnType == info.cls) && (info.parameterAnnotations[info.parameterAnnotations.length - 1].length == 0 || Arrays.equals(info.parameterAnnotations[info.parameterAnnotations.length - 1], info2.annotations))) {
                pairedMethod = method2;
                valueSetter = canBeValueSetter;
                memberSetter = canBeMemberSetter;
            }
            if (!memberGetter && !memberSetter) continue;
            for (int j = skipParameters; j < info.parameterTypes.length; ++j) {
                if (method.isAnnotationPresent(Index.class) || pairedMethod != null && pairedMethod.isAnnotationPresent(Index.class) || info.parameterTypes[j] == Integer.TYPE || info.parameterTypes[j] == Long.TYPE) continue;
                memberGetter = false;
                if (j >= info.parameterTypes.length - 1) continue;
                memberSetter = false;
            }
        }
        Annotation behavior = this.behavior(info.annotations);
        if (canBeGetter && behavior instanceof ValueGetter) {
            info.valueGetter = true;
            info.noReturnGetter = noReturnGetter;
        } else if (canBeSetter && behavior instanceof ValueSetter) {
            info.valueSetter = true;
        } else if (canBeGetter && behavior instanceof MemberGetter) {
            info.memberGetter = true;
            info.noReturnGetter = noReturnGetter;
        } else if (canBeSetter && behavior instanceof MemberSetter) {
            info.memberSetter = true;
        } else if (canBeAllocator && behavior instanceof Allocator) {
            info.allocator = true;
        } else if (canBeArrayAllocator && behavior instanceof ArrayAllocator) {
            info.arrayAllocator = true;
            info.allocator = true;
        } else if (behavior == null) {
            if (info.returnType == Void.TYPE && "deallocate".equals(info.name) && !Modifier.isStatic(info.modifiers) && info.parameterTypes.length == 2 && info.parameterTypes[0] == Long.TYPE && info.parameterTypes[1] == Long.TYPE) {
                info.deallocator = true;
            } else if (canBeAllocator && "allocate".equals(info.name)) {
                info.allocator = true;
            } else if (canBeArrayAllocator && "allocateArray".equals(info.name)) {
                info.arrayAllocator = true;
                info.allocator = true;
            } else if (info.returnType.isAssignableFrom(ByteBuffer.class) && "asDirectBuffer".equals(info.name) && !Modifier.isStatic(info.modifiers) && info.parameterTypes.length == 0) {
                info.bufferGetter = true;
            } else if (valueGetter || !memberGetter && canBeGetter && "get".equals(info.name) && index != null) {
                info.valueGetter = true;
                info.noReturnGetter = noReturnGetter;
                info.pairedMethod = pairedMethod;
            } else if (valueSetter) {
                info.valueSetter = true;
                info.pairedMethod = pairedMethod;
            } else if (memberGetter) {
                info.memberGetter = true;
                info.noReturnGetter = noReturnGetter;
                info.pairedMethod = pairedMethod;
            } else if (memberSetter) {
                info.memberSetter = true;
                info.pairedMethod = pairedMethod;
            }
        } else if (!(behavior instanceof Function)) {
            this.logger.warn("Method \"" + method + "\" cannot behave like a \"" + behavior.annotationType().getSimpleName() + "\". No code will be generated.");
            return null;
        }
        if (name == null && info.pairedMethod != null && (name = info.pairedMethod.getAnnotation(Name.class)) != null) {
            info.memberName = name.value();
        }
        boolean bl = info.noOffset = info.cls.isAnnotationPresent(NoOffset.class) || method.isAnnotationPresent(NoOffset.class) || method.isAnnotationPresent(Index.class);
        if (!info.noOffset && info.pairedMethod != null) {
            boolean bl2 = info.noOffset = info.pairedMethod.isAnnotationPresent(NoOffset.class) || info.pairedMethod.isAnnotationPresent(Index.class);
        }
        if (info.parameterTypes.length == 0 || !info.parameterTypes[0].isArray()) {
            if (info.valueGetter || info.memberGetter) {
                info.dim = info.parameterTypes.length;
            } else if (info.memberSetter || info.valueSetter) {
                info.dim = info.parameterTypes.length - 1;
            }
            if ((info.valueGetter || info.valueSetter) && FunctionPointer.class.isAssignableFrom(info.cls) && info.cls.isAnnotationPresent(Namespace.class)) {
                --info.dim;
            }
        }
        Index index2 = pairedMethod != null ? pairedMethod.getAnnotation(Index.class) : null;
        info.throwsException = null;
        if (!(Generator.noException(info.cls, method) || !(this.by(info.annotations) instanceof ByVal && !Generator.noException(info.returnType, method) || index != null && index.function().length() > 0 || index2 != null && index2.function().length() > 0) && (info.deallocator || info.valueGetter || info.valueSetter || info.memberGetter || info.memberSetter || info.bufferGetter))) {
            Class<?>[] exceptions = method.getExceptionTypes();
            info.throwsException = exceptions.length > 0 ? exceptions[0] : RuntimeException.class;
        }
        return info;
    }

    static int allocatorMax(Class<?> cls, Method method) {
        try {
            Allocator a = Generator.allocator(cls, method);
            return a != null ? a.max() : ((Integer)Allocator.class.getDeclaredMethod("max", new Class[0]).getDefaultValue()).intValue();
        }
        catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    static Allocator allocator(Class<?> cls, Method method) {
        Allocator a = method.getAnnotation(Allocator.class);
        while (a == null && cls != null && (a = cls.getAnnotation(Allocator.class)) == null) {
            Properties classProperties = cls.getAnnotation(Properties.class);
            if (Pointer.class.isAssignableFrom(cls) && classProperties != null) {
                Class c;
                Class[] classArray = classProperties.inherit();
                int n = classArray.length;
                for (int i = 0; i < n && (a = Generator.allocator(c = classArray[i], method)) == null; ++i) {
                }
            }
            if (cls.getEnclosingClass() != null) {
                cls = cls.getEnclosingClass();
                continue;
            }
            cls = cls.getSuperclass();
        }
        return a;
    }

    static boolean criticalRegion(Class<?> cls, Method method) {
        boolean criticalRegion;
        boolean bl = criticalRegion = baseClasses.contains(cls) || method.isAnnotationPresent(CriticalRegion.class);
        while (!criticalRegion && cls != null && !(criticalRegion = cls.isAnnotationPresent(CriticalRegion.class))) {
            Properties classProperties = cls.getAnnotation(Properties.class);
            if (Pointer.class.isAssignableFrom(cls) && classProperties != null) {
                Class c;
                Class[] classArray = classProperties.inherit();
                int n = classArray.length;
                for (int i = 0; i < n && !(criticalRegion = Generator.criticalRegion(c = classArray[i], method)); ++i) {
                }
            }
            if (cls.getEnclosingClass() != null) {
                cls = cls.getEnclosingClass();
                continue;
            }
            cls = cls.getSuperclass();
        }
        return criticalRegion;
    }

    static boolean noException(Class<?> cls, Method method) {
        boolean noException;
        boolean bl = noException = baseClasses.contains(cls) || method.isAnnotationPresent(NoException.class);
        while (!noException && cls != null && !(noException = cls.isAnnotationPresent(NoException.class))) {
            Properties classProperties = cls.getAnnotation(Properties.class);
            if (Pointer.class.isAssignableFrom(cls) && classProperties != null) {
                Class c;
                Class[] classArray = classProperties.inherit();
                int n = classArray.length;
                for (int i = 0; i < n && !(noException = Generator.noException(c = classArray[i], method)); ++i) {
                }
            }
            if (cls.getEnclosingClass() != null) {
                cls = cls.getEnclosingClass();
                continue;
            }
            cls = cls.getSuperclass();
        }
        return noException;
    }

    AdapterInformation adapterInformation(boolean out, MethodInformation methodInfo, int j) {
        String valueTypeName;
        AdapterInformation adapter;
        if (out && (methodInfo.parameterTypes[j] == String.class || methodInfo.valueSetter || methodInfo.memberSetter)) {
            return null;
        }
        String typeName = this.cast(methodInfo, j);
        if (typeName != null && typeName.startsWith("(") && typeName.endsWith(")")) {
            typeName = typeName.substring(1, typeName.length() - 1);
        }
        if (typeName == null || typeName.length() == 0) {
            typeName = this.cppCastTypeName(methodInfo.parameterTypes[j], methodInfo.parameterAnnotations[j])[0];
        }
        if ((adapter = this.adapterInformation(out, valueTypeName = Generator.valueTypeName(typeName), methodInfo.parameterAnnotations[j])) == null && methodInfo.pairedMethod != null && j == methodInfo.parameterTypes.length - 1 && (methodInfo.valueSetter || methodInfo.memberSetter)) {
            adapter = this.adapterInformation(out, valueTypeName, methodInfo.pairedMethod.getAnnotations());
        }
        return adapter;
    }

    AdapterInformation adapterInformation(boolean out, String valueTypeName, Annotation ... annotations) {
        AdapterInformation adapterInfo = null;
        boolean constant = false;
        String cast = "";
        String cast2 = "";
        for (Annotation a : annotations) {
            Cast c;
            if (!(a instanceof Cast) || (c = (Cast)a).value().length <= 0 || c.value()[0].length() <= 0) continue;
            valueTypeName = Generator.constValueTypeName(c.value()[0]);
        }
        for (Annotation a : annotations) {
            Adapter adapter;
            Adapter adapter2 = adapter = a instanceof Adapter ? (Adapter)a : a.annotationType().getAnnotation(Adapter.class);
            if (adapter != null) {
                adapterInfo = new AdapterInformation();
                adapterInfo.name = adapter.value();
                adapterInfo.argc = adapter.argc();
                if (a == adapter) continue;
                try {
                    Class<? extends Annotation> cls = a.annotationType();
                    if (cls.isAnnotationPresent(Const.class)) {
                        constant = true;
                    }
                    try {
                        String value = cls.getDeclaredMethod("value", new Class[0]).invoke((Object)a, new Object[0]).toString();
                        if (value != null && value.length() > 0) {
                            valueTypeName = value;
                        }
                    }
                    catch (NoSuchMethodException e) {
                        valueTypeName = null;
                    }
                    Cast c = cls.getAnnotation(Cast.class);
                    if (c != null && cast.length() == 0) {
                        cast = c.value()[0];
                        if (valueTypeName != null) {
                            cast = cast + "< " + valueTypeName + " >";
                        }
                        if (c.value().length > 1) {
                            cast = cast + c.value()[1];
                        }
                        if (c.value().length > 2) {
                            cast2 = c.value()[2];
                        }
                    }
                }
                catch (Exception ex) {
                    this.logger.warn("Could not invoke the value() method on annotation \"" + a + "\": " + ex);
                }
                if (valueTypeName == null || valueTypeName.length() <= 0) continue;
                adapterInfo.name = adapterInfo.name + "< " + valueTypeName + " >";
                continue;
            }
            if (a instanceof Const) {
                constant = true;
                continue;
            }
            if (!(a instanceof Cast)) continue;
            Cast c = (Cast)a;
            if (c.value().length > 1) {
                cast = c.value()[1];
            }
            if (c.value().length <= 2) continue;
            cast2 = c.value()[2];
        }
        if (adapterInfo != null) {
            adapterInfo.cast = cast;
            adapterInfo.cast2 = cast2;
            adapterInfo.constant = constant;
        }
        return out && constant ? null : adapterInfo;
    }

    String cast(MethodInformation methodInfo, int j) {
        String cast = this.cast(methodInfo.parameterTypes[j], methodInfo.parameterAnnotations[j]);
        if ((cast == null || cast.length() == 0) && j == methodInfo.parameterTypes.length - 1 && (methodInfo.valueSetter || methodInfo.memberSetter) && methodInfo.pairedMethod != null) {
            cast = this.cast(methodInfo.pairedMethod.getReturnType(), methodInfo.pairedMethod.getAnnotations());
        }
        return cast;
    }

    String cast(Class<?> type, Annotation ... annotations) {
        String[] typeName = null;
        for (Annotation a : annotations) {
            if ((!(a instanceof Cast) || ((Cast)a).value()[0].length() <= 0) && !(a instanceof Const)) continue;
            typeName = this.cppCastTypeName(type, annotations);
            break;
        }
        return typeName != null && typeName.length > 0 ? "(" + (String)typeName[0] + typeName[1] + ")" : "";
    }

    Annotation by(MethodInformation methodInfo, int j) {
        Annotation passBy = this.by(methodInfo.parameterAnnotations[j]);
        if (passBy == null && methodInfo.pairedMethod != null && (methodInfo.valueSetter || methodInfo.memberSetter) && j == methodInfo.parameterAnnotations.length - 1) {
            passBy = this.by(methodInfo.pairedMethod.getAnnotations());
        }
        return passBy;
    }

    Annotation by(Annotation ... annotations) {
        Annotation byAnnotation = null;
        for (Annotation a : annotations) {
            if (!(a instanceof ByPtr) && !(a instanceof ByPtrPtr) && !(a instanceof ByPtrRef) && !(a instanceof ByRef) && !(a instanceof ByVal)) continue;
            if (byAnnotation != null) {
                this.logger.warn("\"By\" annotation \"" + byAnnotation + "\" already found. Ignoring superfluous annotation \"" + a + "\".");
                continue;
            }
            byAnnotation = a;
        }
        return byAnnotation;
    }

    Annotation behavior(Annotation ... annotations) {
        Annotation behaviorAnnotation = null;
        for (Annotation a : annotations) {
            if (!(a instanceof Function) && !(a instanceof Allocator) && !(a instanceof ArrayAllocator) && !(a instanceof ValueSetter) && !(a instanceof ValueGetter) && !(a instanceof MemberGetter) && !(a instanceof MemberSetter)) continue;
            if (behaviorAnnotation != null) {
                this.logger.warn("Behavior annotation \"" + behaviorAnnotation + "\" already found. Ignoring superfluous annotation \"" + a + "\".");
                continue;
            }
            behaviorAnnotation = a;
        }
        return behaviorAnnotation;
    }

    String enumValueType(Class<?> type) {
        try {
            Field f = type.getField("value");
            if (!f.getType().isPrimitive()) {
                this.logger.warn("Field \"value\" of enum type \"" + type.getCanonicalName() + "\" is not of a primitive type. Compilation will most likely fail.");
            }
            return f.getType().getName();
        }
        catch (NoSuchFieldException ex) {
            this.logger.warn("Field \"value\" missing from enum type \"" + type.getCanonicalName() + ". Compilation will most likely fail.");
            return null;
        }
    }

    static boolean asUtf16(MethodInformation methodInfo, int j) {
        if (methodInfo.parameterAnnotations[j].length == 0 && methodInfo.pairedMethod != null && j == methodInfo.parameterTypes.length - 1 && (methodInfo.valueSetter || methodInfo.memberSetter)) {
            return Generator.asUtf16(methodInfo.pairedMethod.getAnnotations());
        }
        return Generator.asUtf16(methodInfo.parameterAnnotations[j]);
    }

    static boolean asUtf16(Annotation[] annotations) {
        if (annotations == null) {
            return false;
        }
        for (Annotation annotation : annotations) {
            if (!(annotation instanceof AsUtf16)) continue;
            return true;
        }
        return false;
    }

    static String createString(String ptr, String adapter, boolean asUtf16) {
        return (asUtf16 ? "JavaCPP_createStringFromUTF16(env, " : "JavaCPP_createStringFromBytes(env, ") + ptr + (adapter != null ? ", " + adapter + ".size);" : ");");
    }

    static String getStringData(String str, boolean asUtf16) {
        return (asUtf16 ? "JavaCPP_getStringUTF16(env, " : "JavaCPP_getStringBytes(env, ") + str + ");";
    }

    static String releaseStringData(String str, String ptr, boolean asUtf16) {
        return (asUtf16 ? "JavaCPP_releaseStringUTF16(env, " : "JavaCPP_releaseStringBytes(env, " + str + ", ") + ptr + ");";
    }

    static String constValueTypeName(String ... typeName) {
        String type = typeName[0];
        if (type.endsWith("*") || type.endsWith("&")) {
            type = type.substring(0, type.length() - 1);
        }
        return type;
    }

    static String valueTypeName(String ... typeName) {
        String type = typeName[0];
        if (type.startsWith("const ")) {
            type = type.substring(6);
        }
        if (type.endsWith(" const")) {
            type = type.substring(0, type.length() - 6);
        }
        if (type.endsWith("*") || type.endsWith("&")) {
            type = type.substring(0, type.length() - 1);
        }
        if (type.endsWith(" const")) {
            type = type.substring(0, type.length() - 6);
        }
        return type;
    }

    static boolean constFunction(Class<?> classType, Method functionMethod) {
        if (classType.isAnnotationPresent(Const.class)) {
            return true;
        }
        if (!functionMethod.isAnnotationPresent(Const.class)) {
            return false;
        }
        for (Annotation a : functionMethod.getDeclaredAnnotations()) {
            if (!(a instanceof Const)) continue;
            boolean[] b = ((Const)a).value();
            return b.length > 2 && b[2];
        }
        return false;
    }

    static boolean noexceptFunction(Class<?> classType, Method functionMethod) {
        if (classType.isAnnotationPresent(NoException.class)) {
            return true;
        }
        if (!functionMethod.isAnnotationPresent(NoException.class)) {
            return false;
        }
        for (Annotation a : functionMethod.getDeclaredAnnotations()) {
            if (!(a instanceof NoException)) continue;
            return ((NoException)a).value();
        }
        return false;
    }

    String[] cppAnnotationTypeName(Class<?> type, Annotation ... annotations) {
        Annotation by;
        String[] typeName = this.cppCastTypeName(type, annotations);
        String prefix = typeName[0];
        String suffix = typeName[1];
        boolean casted = false;
        for (Annotation a : annotations) {
            if ((!(a instanceof Cast) || ((Cast)a).value()[0].length() <= 0) && !(a instanceof Const)) continue;
            casted = true;
            break;
        }
        if ((by = this.by(annotations)) instanceof ByVal) {
            prefix = Generator.constValueTypeName(typeName);
        } else if (by instanceof ByRef) {
            prefix = Generator.constValueTypeName(typeName) + "&";
        } else if (by instanceof ByPtrPtr && !casted) {
            prefix = prefix + "*";
        } else if (by instanceof ByPtrRef) {
            prefix = prefix + "&";
        } else if (by instanceof ByPtr && type.isPrimitive()) {
            prefix = prefix + "*";
        }
        typeName[0] = prefix;
        typeName[1] = suffix;
        return typeName;
    }

    String[] cppCastTypeName(Class<?> type, Annotation ... annotations) {
        String[] typeName = null;
        boolean warning = false;
        boolean adapter = false;
        for (Annotation a : annotations) {
            if (a instanceof Cast) {
                String[] stringArray;
                warning = typeName != null;
                String prefix = ((Cast)a).value()[0];
                String suffix = "";
                int templateCount = 0;
                for (int i = 0; i < prefix.length(); ++i) {
                    char c = prefix.charAt(i);
                    if (c == '<') {
                        ++templateCount;
                        continue;
                    }
                    if (c == '>') {
                        --templateCount;
                        continue;
                    }
                    if (prefix.contains("decltype(") || templateCount != 0 || c != ')') continue;
                    suffix = prefix.substring(i).trim();
                    prefix = prefix.substring(0, i).trim();
                    break;
                }
                if (prefix.length() > 0) {
                    String[] stringArray2 = new String[2];
                    stringArray2[0] = prefix;
                    stringArray = stringArray2;
                    stringArray2[1] = suffix;
                } else {
                    stringArray = null;
                }
                typeName = stringArray;
                continue;
            }
            if (a instanceof Const) {
                Annotation by;
                boolean[] b = ((Const)a).value();
                if (b.length == 1 && !b[0] || b.length > 1 && !b[0] && !b[1] || (warning = typeName != null)) continue;
                typeName = this.cppTypeName(type, annotations);
                if (typeName[0].contains("(*")) {
                    if (b.length > 0 && b[0] && !typeName[0].endsWith(" const")) {
                        typeName[0] = typeName[0] + " const";
                    }
                } else {
                    if (b.length > 1 && b[1] && !typeName[0].endsWith(" const")) {
                        typeName[0] = typeName[0] + " const";
                    }
                    if (b.length > 0 && b[0] && !typeName[0].startsWith("const ")) {
                        typeName[0] = "const " + typeName[0];
                    }
                }
                if ((by = this.by(annotations)) instanceof ByPtrPtr) {
                    typeName[0] = typeName[0] + "*";
                    continue;
                }
                if (!(by instanceof ByPtrRef)) continue;
                typeName[0] = typeName[0] + "&";
                continue;
            }
            if (!(a instanceof Adapter) && !a.annotationType().isAnnotationPresent(Adapter.class)) continue;
            adapter = true;
        }
        if (warning && !adapter) {
            this.logger.warn("Without \"Adapter\", \"Cast\" and \"Const\" annotations are mutually exclusive.");
        }
        if (typeName == null) {
            typeName = this.cppTypeName(type, annotations);
        }
        return typeName;
    }

    String[] cppTypeName(MethodInformation methodInfo, int j) {
        if (methodInfo.parameterAnnotations[j].length == 0 && methodInfo.pairedMethod != null && j == methodInfo.parameterTypes.length - 1 && (methodInfo.valueSetter || methodInfo.memberSetter)) {
            return this.cppTypeName(methodInfo.pairedMethod.getReturnType(), methodInfo.pairedMethod.getAnnotations());
        }
        return this.cppTypeName(methodInfo.parameterTypes[j], methodInfo.parameterAnnotations[j]);
    }

    String[] cppTypeName(Class<?> type) {
        return this.cppTypeName(type, null);
    }

    String[] cppTypeName(Class<?> type, Annotation[] annotations) {
        String prefix = "";
        String suffix = "";
        if (type == Buffer.class || type == Pointer.class) {
            prefix = "void*";
        } else if (type == byte[].class || type == ByteBuffer.class || type == BytePointer.class) {
            prefix = "signed char*";
        } else if (type == short[].class || type == ShortBuffer.class || type == ShortPointer.class) {
            prefix = "short*";
        } else if (type == int[].class || type == IntBuffer.class || type == IntPointer.class) {
            prefix = "int*";
        } else if (type == long[].class || type == LongBuffer.class || type == LongPointer.class) {
            prefix = "jlong*";
        } else if (type == float[].class || type == FloatBuffer.class || type == FloatPointer.class) {
            prefix = "float*";
        } else if (type == double[].class || type == DoubleBuffer.class || type == DoublePointer.class) {
            prefix = "double*";
        } else if (type == char[].class || type == CharBuffer.class || type == CharPointer.class) {
            prefix = "char16_t*";
        } else if (type == boolean[].class || type == BooleanPointer.class) {
            prefix = "unsigned char*";
        } else if (type == PointerPointer.class) {
            prefix = "void**";
        } else if (type == String.class) {
            prefix = Generator.asUtf16(annotations) ? "const char16_t*" : "const char*";
        } else if (type == Byte.TYPE) {
            prefix = "signed char";
        } else if (type == Long.TYPE) {
            prefix = "jlong";
        } else if (type == Character.TYPE) {
            prefix = "char16_t";
        } else if (type == Boolean.TYPE) {
            prefix = "unsigned char";
        } else if (type.isPrimitive()) {
            prefix = type.getName();
        } else if (FunctionPointer.class.isAssignableFrom(type)) {
            Method[] functionMethods = Generator.functionMethods(type, type.getDeclaredMethods(), null);
            String[] prefixSuffix = this.cppFunctionTypeName(functionMethods);
            if (prefixSuffix != null) {
                return prefixSuffix;
            }
        } else {
            String scopedType = Generator.cppScopeName(type);
            if (scopedType.length() > 0) {
                prefix = scopedType + (Enum.class.isAssignableFrom(type) ? "" : "*");
            } else {
                this.logger.warn("The class " + type.getCanonicalName() + " does not map to any C++ type. Compilation will most likely fail.");
            }
        }
        return new String[]{prefix, suffix};
    }

    String[] cppFunctionTypeName(Method ... functionMethods) {
        String spaceName;
        Namespace namespace;
        Method functionMethod = null;
        if (functionMethods != null) {
            for (Method m : functionMethods) {
                if (m == null) continue;
                functionMethod = m;
                break;
            }
        }
        if (functionMethod == null) {
            return null;
        }
        Class<?> type = functionMethod.getDeclaringClass();
        Convention convention = type.getAnnotation(Convention.class);
        String callingConvention = convention == null ? "" : convention.value() + " ";
        Namespace namespace2 = namespace = FunctionPointer.class.isAssignableFrom(type) ? type.getAnnotation(Namespace.class) : null;
        if (namespace != null && namespace.value().length() == 0) {
            namespace = null;
        }
        String string = spaceName = namespace == null ? "" : namespace.value();
        if (spaceName.length() > 0 && !spaceName.endsWith("::")) {
            spaceName = spaceName + "::";
        }
        Class<?> returnType = functionMethod.getReturnType();
        Class<?>[] parameterTypes = functionMethod.getParameterTypes();
        Annotation[] annotations = functionMethod.getAnnotations();
        Annotation[][] parameterAnnotations = functionMethod.getParameterAnnotations();
        String[] returnTypeName = this.cppAnnotationTypeName(returnType, annotations);
        AdapterInformation returnAdapterInfo = this.adapterInformation(false, Generator.valueTypeName(returnTypeName), annotations);
        String prefix = returnAdapterInfo != null && returnAdapterInfo.cast.length() > 0 ? returnAdapterInfo.cast : returnTypeName[0] + returnTypeName[1];
        prefix = prefix + " (" + callingConvention + spaceName + "*";
        String suffix = ")";
        if (functionMethod == functionMethods[0]) {
            int j;
            suffix = suffix + "(";
            if (FunctionPointer.class.isAssignableFrom(type) && namespace != null && (parameterTypes.length == 0 || !Pointer.class.isAssignableFrom(parameterTypes[0]))) {
                this.logger.warn("First parameter of caller method call() or apply() for member function pointer " + type.getCanonicalName() + " is not a Pointer. Compilation will most likely fail.");
            }
            int n = j = namespace == null ? 0 : 1;
            while (j < parameterTypes.length) {
                String[] paramTypeName = this.cppAnnotationTypeName(parameterTypes[j], parameterAnnotations[j]);
                AdapterInformation paramAdapterInfo = this.adapterInformation(false, Generator.valueTypeName(paramTypeName), parameterAnnotations[j]);
                if (paramAdapterInfo != null && paramAdapterInfo.constant) {
                    suffix = suffix + "const ";
                }
                suffix = paramAdapterInfo != null && paramAdapterInfo.cast.length() > 0 ? suffix + paramAdapterInfo.cast + " arg" + j : suffix + paramTypeName[0] + " arg" + j + paramTypeName[1];
                if (j < parameterTypes.length - 1) {
                    suffix = suffix + ", ";
                }
                ++j;
            }
            suffix = suffix + ")";
        }
        if (Generator.constFunction(type, functionMethod)) {
            suffix = suffix + " const";
        }
        if (Generator.noexceptFunction(type, functionMethod)) {
            suffix = suffix + " noexcept";
        }
        return new String[]{prefix, suffix};
    }

    static String cppScopeName(MethodInformation methodInfo) {
        String spaceName;
        Namespace namespace;
        String scopeName = Generator.cppScopeName(methodInfo.cls);
        if (methodInfo.method.isAnnotationPresent(Virtual.class)) {
            String subType;
            scopeName = subType = "JavaCPP_" + Generator.mangle(scopeName);
        }
        if ((namespace = methodInfo.method.getAnnotation(Namespace.class)) == null && methodInfo.pairedMethod != null) {
            namespace = methodInfo.pairedMethod.getAnnotation(Namespace.class);
        }
        String string = spaceName = namespace == null ? "" : namespace.value();
        if (namespace != null && namespace.value().length() == 0 || spaceName.startsWith("::")) {
            scopeName = "";
        }
        if (scopeName.length() > 0 && !scopeName.endsWith("::")) {
            scopeName = scopeName + "::";
        }
        scopeName = scopeName + spaceName;
        if (spaceName.length() > 0 && !spaceName.endsWith("::")) {
            scopeName = scopeName + "::";
        }
        return scopeName + methodInfo.memberName[0];
    }

    static String cppScopeName(Class<?> type) {
        String scopeName = "";
        while (type != null) {
            String spaceName;
            Namespace namespace = type.getAnnotation(Namespace.class);
            String string = spaceName = namespace == null ? "" : namespace.value();
            if ((Enum.class.isAssignableFrom(type) || Pointer.class.isAssignableFrom(type)) && (!baseClasses.contains(type) || type.isAnnotationPresent(Name.class))) {
                String s;
                Name name = type.getAnnotation(Name.class);
                if (name == null) {
                    s = type.getName();
                    int i = s.lastIndexOf("$");
                    if (i < 0) {
                        i = s.lastIndexOf(".");
                    }
                    s = s.substring(i + 1);
                } else {
                    s = name.value()[0];
                }
                if (spaceName.length() > 0 && !spaceName.endsWith("::")) {
                    spaceName = spaceName + "::";
                }
                spaceName = spaceName + s;
            }
            if (!(scopeName.length() <= 0 || scopeName.startsWith("class ") || scopeName.startsWith("struct ") || scopeName.startsWith("union ") || spaceName.endsWith("::"))) {
                spaceName = spaceName + "::";
            }
            scopeName = spaceName + scopeName;
            if (namespace != null && namespace.value().length() == 0 || spaceName.startsWith("::")) break;
            type = type.getEnclosingClass();
        }
        return scopeName;
    }

    static String jniTypeName(Class type) {
        if (type == Byte.TYPE) {
            return "jbyte";
        }
        if (type == Short.TYPE) {
            return "jshort";
        }
        if (type == Integer.TYPE) {
            return "jint";
        }
        if (type == Long.TYPE) {
            return "jlong";
        }
        if (type == Float.TYPE) {
            return "jfloat";
        }
        if (type == Double.TYPE) {
            return "jdouble";
        }
        if (type == Character.TYPE) {
            return "jchar";
        }
        if (type == Boolean.TYPE) {
            return "jboolean";
        }
        if (type == byte[].class) {
            return "jbyteArray";
        }
        if (type == short[].class) {
            return "jshortArray";
        }
        if (type == int[].class) {
            return "jintArray";
        }
        if (type == long[].class) {
            return "jlongArray";
        }
        if (type == float[].class) {
            return "jfloatArray";
        }
        if (type == double[].class) {
            return "jdoubleArray";
        }
        if (type == char[].class) {
            return "jcharArray";
        }
        if (type == boolean[].class) {
            return "jbooleanArray";
        }
        if (type.isArray()) {
            return "jobjectArray";
        }
        if (type == String.class) {
            return "jstring";
        }
        if (type == Class.class) {
            return "jclass";
        }
        if (type == Void.TYPE) {
            return "void";
        }
        return "jobject";
    }

    static String signature(Class ... types) {
        StringBuilder signature = new StringBuilder(2 * types.length);
        for (Class type : types) {
            if (type == Byte.TYPE) {
                signature.append("B");
                continue;
            }
            if (type == Short.TYPE) {
                signature.append("S");
                continue;
            }
            if (type == Integer.TYPE) {
                signature.append("I");
                continue;
            }
            if (type == Long.TYPE) {
                signature.append("J");
                continue;
            }
            if (type == Float.TYPE) {
                signature.append("F");
                continue;
            }
            if (type == Double.TYPE) {
                signature.append("D");
                continue;
            }
            if (type == Boolean.TYPE) {
                signature.append("Z");
                continue;
            }
            if (type == Character.TYPE) {
                signature.append("C");
                continue;
            }
            if (type == Void.TYPE) {
                signature.append("V");
                continue;
            }
            if (type.isArray()) {
                signature.append(type.getName().replace('.', '/'));
                continue;
            }
            signature.append("L").append(type.getName().replace('.', '/')).append(";");
        }
        return signature.toString();
    }

    static String mangle(String name) {
        StringBuilder mangledName = new StringBuilder(2 * name.length());
        for (int i = 0; i < name.length(); ++i) {
            char c = name.charAt(i);
            if (c >= '0' && c <= '9' || c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z') {
                mangledName.append(c);
                continue;
            }
            if (c == '_') {
                mangledName.append("_1");
                continue;
            }
            if (c == ';') {
                mangledName.append("_2");
                continue;
            }
            if (c == '[') {
                mangledName.append("_3");
                continue;
            }
            if (c == '.' || c == '/') {
                mangledName.append("_");
                continue;
            }
            String code = Integer.toHexString(c);
            mangledName.append("_0");
            switch (code.length()) {
                case 1: {
                    mangledName.append("0");
                }
                case 2: {
                    mangledName.append("0");
                }
                case 3: {
                    mangledName.append("0");
                }
            }
            mangledName.append(code);
        }
        return mangledName.toString();
    }

    static enum LongEnum {
        LONG;

        long value;
    }

    static enum IntEnum {
        INT;

        int value;
    }

    static enum ShortEnum {
        SHORT;

        short value;
    }

    static enum ByteEnum {
        BYTE;

        byte value;
    }

    static enum BooleanEnum {
        BOOLEAN;

        boolean value;
    }
}

