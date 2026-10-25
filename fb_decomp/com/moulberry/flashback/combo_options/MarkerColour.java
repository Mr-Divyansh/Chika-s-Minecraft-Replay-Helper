/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.language.I18n
 */
package com.moulberry.flashback.combo_options;

import com.moulberry.flashback.combo_options.ComboOption;
import net.minecraft.client.resources.language.I18n;

public enum MarkerColour implements ComboOption
{
    RED("color.minecraft.red", -65536),
    GREEN("color.minecraft.green", -16711936),
    BLUE("color.minecraft.blue", -16776961),
    ORANGE("color.minecraft.orange", -38881),
    MAGENTA("color.minecraft.magenta", -65281),
    LIGHT_BLUE("color.minecraft.light_blue", -6635315),
    YELLOW("color.minecraft.yellow", -256),
    LIME("color.minecraft.lime", -4194560),
    PINK("color.minecraft.pink", -38476),
    CYAN("color.minecraft.cyan", -16711681),
    PURPLE("color.minecraft.purple", -6283024),
    BROWN("color.minecraft.brown", -7650029),
    WHITE("color.minecraft.white", -1),
    LIGHT_GRAY("color.minecraft.light_gray", -2894893),
    GRAY("color.minecraft.gray", -8355712),
    BLACK("color.minecraft.black", -16777216),
    CUSTOM_RGB("flashback.custom_rgb", -1);

    private final String text;
    public final int colour;

    private MarkerColour(String text, int colour) {
        this.text = text;
        this.colour = colour;
    }

    @Override
    public String text() {
        return I18n.get((String)this.text, (Object[])new Object[0]);
    }

    public String toString() {
        return this.text();
    }
}

