/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.language.I18n
 */
package com.moulberry.flashback.combo_options;

import com.moulberry.flashback.combo_options.ComboOption;
import net.minecraft.client.resources.language.I18n;

public enum AspectRatio implements ComboOption
{
    ASPECT_16_9("flashback.aspect_ratio.16_by_9", 1.7777778f),
    ASPECT_9_16("flashback.aspect_ratio.9_by_16", 0.5625f),
    ASPECT_240_1("flashback.aspect_ratio.widescreen", 2.4f),
    ASPECT_1_1("flashback.aspect_ratio.square", 1.0f),
    ASPECT_4_3("flashback.aspect_ratio.fullscreen", 1.3333334f),
    ASPECT_3_2("flashback.aspect_ratio.photo", 1.5f);

    private final String text;
    private final float aspectRatio;

    private AspectRatio(String text, float aspectRatio) {
        this.text = text;
        this.aspectRatio = aspectRatio;
    }

    @Override
    public String text() {
        return I18n.get((String)this.text, (Object[])new Object[0]);
    }

    public float aspectRatio() {
        return this.aspectRatio;
    }
}

