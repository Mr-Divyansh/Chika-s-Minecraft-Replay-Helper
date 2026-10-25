/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.tools;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class MethodInformation {
    Class<?> cls;
    Method method;
    Annotation[] annotations;
    int modifiers;
    Class<?> returnType;
    String name;
    String[] memberName;
    int allocatorMax;
    int dim;
    boolean[] parameterRaw;
    Class<?>[] parameterTypes;
    Annotation[][] parameterAnnotations;
    boolean criticalRegion;
    boolean returnRaw;
    boolean withEnv;
    boolean overloaded;
    boolean noOffset;
    boolean deallocator;
    boolean allocator;
    boolean arrayAllocator;
    boolean bufferGetter;
    boolean valueGetter;
    boolean valueSetter;
    boolean memberGetter;
    boolean memberSetter;
    boolean noReturnGetter;
    Method pairedMethod;
    Class<?> throwsException;
}

