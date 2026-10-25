/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.osgi.annotation.versioning.ConsumerType
 */
package org.bytedeco.javacpp.tools;

import java.util.Properties;
import org.bytedeco.javacpp.tools.Logger;
import org.osgi.annotation.versioning.ConsumerType;

@ConsumerType
public interface BuildEnabled {
    public void init(Logger var1, Properties var2, String var3);
}

