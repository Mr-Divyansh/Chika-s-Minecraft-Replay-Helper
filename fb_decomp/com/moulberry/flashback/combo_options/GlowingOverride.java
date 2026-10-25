/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.language.I18n
 */
package com.moulberry.flashback.combo_options;

import com.moulberry.flashback.combo_options.ComboOption;
import net.minecraft.client.resources.language.I18n;

public enum GlowingOverride implements ComboOption
{
    DEFAULT("flashback.glowing_override.default"),
    FORCE_GLOW("flashback.glowing_override.force_glow"),
    FORCE_NO_GLOW("flashback.glowing_override.force_no_glow");

    private final String text;

    private GlowingOverride(String text) {
        this.text = text;
    }

    @Override
    public String text() {
        return I18n.get((String)this.text, (Object[])new Object[0]);
    }
}

