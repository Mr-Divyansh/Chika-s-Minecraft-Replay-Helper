/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.osgi.annotation.versioning.ConsumerType
 */
package org.bytedeco.javacpp;

import org.bytedeco.javacpp.ClassProperties;
import org.osgi.annotation.versioning.ConsumerType;

@ConsumerType
public interface LoadEnabled {
    public void init(ClassProperties var1);
}

