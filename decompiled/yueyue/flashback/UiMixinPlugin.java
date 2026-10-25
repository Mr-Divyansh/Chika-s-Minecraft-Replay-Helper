/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.ClassNode
 *  org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin
 *  org.spongepowered.asm.mixin.extensibility.IMixinInfo
 */
package yueyue.flashback;

import java.util.List;
import java.util.Set;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import yueyue.flashback.UiBootstrap;

public final class UiMixinPlugin
implements IMixinConfigPlugin {
    public boolean shouldApplyMixin(String target, String mixin) {
        return UiBootstrap.available();
    }

    public void onLoad(String mixinPackage) {
    }

    public String getRefMapperConfig() {
        return null;
    }

    public void acceptTargets(Set<String> mine, Set<String> others) {
    }

    public List<String> getMixins() {
        return null;
    }

    public void preApply(String target, ClassNode node, String mixin, IMixinInfo info) {
    }

    public void postApply(String target, ClassNode node, String mixin, IMixinInfo info) {
    }
}

