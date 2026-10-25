/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.language.I18n
 */
package com.moulberry.flashback.combo_options;

import com.moulberry.flashback.combo_options.ComboOption;
import net.minecraft.client.resources.language.I18n;

public enum ExportProjection implements ComboOption
{
    PERSPECTIVE("flashback.projection.perspective"),
    ORTHOGRAPHIC("flashback.projection.orthographic"),
    CUBE_MAP("flashback.projection.cube_map"),
    EQUIRECTANGULAR("flashback.projection.equirectangular");

    private final String key;

    private ExportProjection(String key) {
        this.key = key;
    }

    @Override
    public String text() {
        return I18n.get((String)this.key, (Object[])new Object[0]);
    }
}

