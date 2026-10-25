/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.JsonOps
 *  net.minecraft.client.multiplayer.ClientRegistryLayer
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.core.LayeredRegistryAccess
 *  net.minecraft.core.Registry
 *  net.minecraft.core.RegistryAccess
 *  net.minecraft.core.RegistryAccess$Frozen
 *  net.minecraft.core.RegistryAccess$ImmutableRegistryAccess
 *  net.minecraft.resources.RegistryDataLoader$RegistryData
 *  net.minecraft.resources.RegistryOps
 */
package com.moulberry.flashback.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.multiplayer.ClientRegistryLayer;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.RegistryOps;

public class RegistryHelper {
    public static boolean equals(RegistryAccess one, RegistryAccess two, List<RegistryDataLoader.RegistryData<?>> registries) {
        ArrayList<Registry> listOne = new ArrayList<Registry>();
        ArrayList<Registry> listTwo = new ArrayList<Registry>();
        for (RegistryDataLoader.RegistryData<?> registryData : registries) {
            Optional registryOne = one.lookup(registryData.key());
            Optional registryTwo = two.lookup(registryData.key());
            if (registryOne.isEmpty() || registryTwo.isEmpty()) {
                if (registryOne.isEmpty() && registryTwo.isEmpty()) continue;
                return false;
            }
            if (((Registry)registryOne.get()).size() != ((Registry)registryTwo.get()).size()) {
                return false;
            }
            listOne.add((Registry)registryOne.get());
            listTwo.add((Registry)registryTwo.get());
        }
        class FrozenAccess
        extends RegistryAccess.ImmutableRegistryAccess
        implements RegistryAccess.Frozen {
            public FrozenAccess(List<? extends Registry<?>> list) {
                super(list);
            }
        }
        LayeredRegistryAccess layeredOne = ClientRegistryLayer.createRegistryAccess().replaceFrom((Object)ClientRegistryLayer.REMOTE, new RegistryAccess.Frozen[]{new FrozenAccess(listOne)});
        LayeredRegistryAccess layeredTwo = ClientRegistryLayer.createRegistryAccess().replaceFrom((Object)ClientRegistryLayer.REMOTE, new RegistryAccess.Frozen[]{new FrozenAccess(listTwo)});
        RegistryOps dynamicOpsOne = RegistryOps.create((DynamicOps)JsonOps.INSTANCE, (HolderLookup.Provider)layeredOne.compositeAccess());
        RegistryOps dynamicOpsTwo = RegistryOps.create((DynamicOps)JsonOps.INSTANCE, (HolderLookup.Provider)layeredTwo.compositeAccess());
        for (RegistryDataLoader.RegistryData<?> registryData : registries) {
            if (RegistryHelper.equalsAssumeSameSize((RegistryAccess)layeredOne.compositeAccess(), (RegistryAccess)layeredTwo.compositeAccess(), dynamicOpsOne, dynamicOpsTwo, registryData)) continue;
            return false;
        }
        return true;
    }

    private static <T> boolean equalsAssumeSameSize(RegistryAccess one, RegistryAccess two, RegistryOps<?> dynamicOpsOne, RegistryOps<?> dynamicOpsTwo, RegistryDataLoader.RegistryData<T> registryData) {
        Optional registryOne = one.lookup(registryData.key());
        Optional registryTwo = two.lookup(registryData.key());
        if (registryOne.isEmpty() || registryTwo.isEmpty()) {
            return registryOne.isEmpty() && registryTwo.isEmpty();
        }
        Iterator iteratorOne = ((Registry)registryOne.get()).iterator();
        Iterator iteratorTwo = ((Registry)registryTwo.get()).iterator();
        while (iteratorOne.hasNext()) {
            Object valueTwo;
            Object valueOne = iteratorOne.next();
            if (RegistryHelper.equalsUsingCodec(valueOne, valueTwo = iteratorTwo.next(), dynamicOpsOne, dynamicOpsTwo, registryData.elementCodec())) continue;
            return false;
        }
        return true;
    }

    private static <T> boolean equalsUsingCodec(T one, T two, RegistryOps<?> dynamicOpsOne, RegistryOps<?> dynamicOpsTwo, Codec<T> codec) {
        if (one.equals(two)) {
            return true;
        }
        DataResult resultOne = codec.encodeStart(dynamicOpsOne, one);
        if (resultOne.isError()) {
            return false;
        }
        DataResult resultTwo = codec.encodeStart(dynamicOpsTwo, two);
        if (resultTwo.isError()) {
            return false;
        }
        return resultOne.getOrThrow().equals(resultTwo.getOrThrow());
    }
}

