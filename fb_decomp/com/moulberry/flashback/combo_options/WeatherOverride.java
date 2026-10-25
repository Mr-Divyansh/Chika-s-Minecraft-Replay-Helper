/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.language.I18n
 */
package com.moulberry.flashback.combo_options;

import com.moulberry.flashback.combo_options.ComboOption;
import net.minecraft.client.resources.language.I18n;

public enum WeatherOverride implements ComboOption
{
    NONE("flashback.weather_override.no_override"),
    CLEAR("flashback.weather_override.clear"),
    OVERCAST("flashback.weather_override.overcast"),
    RAINING("flashback.weather_override.raining"),
    SNOWING("flashback.weather_override.snowing"),
    THUNDERING("flashback.weather_override.thundering");

    private final String text;

    private WeatherOverride(String text) {
        this.text = text;
    }

    @Override
    public String text() {
        return I18n.get((String)this.text, (Object[])new Object[0]);
    }
}

