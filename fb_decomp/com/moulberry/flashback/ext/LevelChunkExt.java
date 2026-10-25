/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.state.BlockState
 */
package com.moulberry.flashback.ext;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public interface LevelChunkExt {
    public BlockState flashback$setBlockStateWithoutUpdates(BlockPos var1, BlockState var2);

    public int flashback$getCachedChunkId();

    public void flashback$setCachedChunkId(int var1);
}

