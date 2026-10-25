/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.language.I18n
 */
package com.moulberry.flashback.combo_options;

import com.moulberry.flashback.combo_options.ComboOption;
import net.minecraft.client.resources.language.I18n;

public enum TrackingBodyPart implements ComboOption
{
    HEAD("flashback.tracking_body_part.head"),
    BODY("flashback.tracking_body_part.body"),
    ROOT("flashback.tracking_body_part.root");

    private final String text;

    private TrackingBodyPart(String text) {
        this.text = text;
    }

    @Override
    public String text() {
        return I18n.get((String)this.text, (Object[])new Object[0]);
    }
}

