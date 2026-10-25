/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.loader.api.FabricLoader
 *  net.irisshaders.iris.api.v0.IrisApi
 */
package com.moulberry.flashback.compat;

import net.fabricmc.loader.api.FabricLoader;
import net.irisshaders.iris.api.v0.IrisApi;

public class IrisApiWrapper {
    public static boolean isIrisAvailable() {
        return FabricLoader.getInstance().isModLoaded("iris");
    }

    public static boolean isShaderPackInUse() {
        return IrisApi.getInstance().isShaderPackInUse();
    }
}

