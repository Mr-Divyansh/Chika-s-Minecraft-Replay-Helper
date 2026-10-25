/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.osgi.annotation.versioning.ConsumerType
 */
package org.bytedeco.javacpp.tools;

import org.bytedeco.javacpp.tools.InfoMap;
import org.osgi.annotation.versioning.ConsumerType;

@ConsumerType
public interface InfoMapper {
    public void map(InfoMap var1);
}

