/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.language.I18n
 */
package com.moulberry.flashback.combo_options;

import com.moulberry.flashback.combo_options.ComboOption;
import net.minecraft.client.resources.language.I18n;

public enum MovementDirection implements ComboOption
{
    HORIZONTAL("flashback.movement_direction.horizontal"),
    CAMERA("flashback.movement_direction.camera");

    private final String text;

    private MovementDirection(String text) {
        this.text = text;
    }

    @Override
    public String text() {
        return I18n.get((String)this.text, (Object[])new Object[0]);
    }

    public String toString() {
        return this.text();
    }
}

