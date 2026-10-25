/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.moulberry.mixinconstraints.annotations.IfModLoaded
 *  de.johni0702.minecraft.bobby.FakeChunkManager
 *  net.minecraft.client.multiplayer.ClientPacketListener
 *  org.jetbrains.annotations.Contract
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Pseudo
 *  org.spongepowered.asm.mixin.gen.Invoker
 */
package com.moulberry.flashback.mixin.compat.bobby;

import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import de.johni0702.minecraft.bobby.FakeChunkManager;
import net.minecraft.client.multiplayer.ClientPacketListener;
import org.jetbrains.annotations.Contract;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.gen.Invoker;

@IfModLoaded(value="bobby")
@Pseudo
@Mixin(value={FakeChunkManager.class}, remap=false)
public interface FakeChunkManagerAccessor {
    @Invoker(value="getCurrentWorldOrServerName")
    @Contract
    public static String getCurrentWorldOrServerName(ClientPacketListener clientPacketListener) {
        throw new RuntimeException();
    }
}

