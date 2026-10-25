/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.components.debug.DebugScreenEntryList
 *  net.minecraft.client.gui.components.debug.DebugScreenEntryStatus
 *  net.minecraft.resources.Identifier
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.moulberry.flashback.mixin;

import com.moulberry.flashback.Flashback;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.gui.components.debug.DebugScreenEntryList;
import net.minecraft.client.gui.components.debug.DebugScreenEntryStatus;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={DebugScreenEntryList.class})
public class MixinDebugScreenEntryList {
    @Shadow
    private Map<Identifier, DebugScreenEntryStatus> allStatuses;

    @Inject(method={"rebuildCurrentList"}, at={@At(value="HEAD")})
    public void rebuildCurrentList(CallbackInfo ci) {
        if (!this.allStatuses.containsKey(Flashback.RECORDING_INFO_DEBUG_SCREEN_ID)) {
            if (!(this.allStatuses instanceof HashMap)) {
                this.allStatuses = new HashMap<Identifier, DebugScreenEntryStatus>(this.allStatuses);
            }
            this.allStatuses.put(Flashback.RECORDING_INFO_DEBUG_SCREEN_ID, DebugScreenEntryStatus.IN_OVERLAY);
        }
    }
}

