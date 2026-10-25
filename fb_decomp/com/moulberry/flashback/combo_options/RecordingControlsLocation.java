/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.language.I18n
 */
package com.moulberry.flashback.combo_options;

import com.moulberry.flashback.combo_options.ComboOption;
import net.minecraft.client.resources.language.I18n;

public enum RecordingControlsLocation implements ComboOption
{
    RIGHT("flashback.recording_controls_location.right"),
    BELOW("flashback.recording_controls_location.below"),
    LEFT("flashback.recording_controls_location.left"),
    HIDDEN("flashback.recording_controls_location.hidden");

    private final String text;

    private RecordingControlsLocation(String text) {
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

