/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.language.I18n
 *  org.jetbrains.annotations.Nullable
 */
package com.moulberry.flashback.keyframe.types;

import com.moulberry.flashback.keyframe.KeyframeType;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.impl.BlockOverrideKeyframe;
import net.minecraft.client.resources.language.I18n;
import org.jetbrains.annotations.Nullable;

public class BlockOverrideKeyframeType
implements KeyframeType<BlockOverrideKeyframe> {
    public static BlockOverrideKeyframeType INSTANCE = new BlockOverrideKeyframeType();

    private BlockOverrideKeyframeType() {
    }

    @Override
    public Class<? extends KeyframeChange> keyframeChangeType() {
        return null;
    }

    @Override
    @Nullable
    public String icon() {
        return "\uea44";
    }

    @Override
    public String name() {
        return I18n.get((String)"flashback.keyframe.block_override", (Object[])new Object[0]);
    }

    @Override
    public String id() {
        return "BLOCK_OVERRIDE";
    }

    @Override
    public boolean allowChangingInterpolationType() {
        return false;
    }

    @Override
    public boolean canBeCreatedNormally() {
        return false;
    }

    @Override
    @Nullable
    public BlockOverrideKeyframe createDirect() {
        return null;
    }

    @Override
    public KeyframeType.KeyframeCreatePopup<BlockOverrideKeyframe> createPopup() {
        return null;
    }
}

