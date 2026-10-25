/*
 * Decompiled with CFR 0.152.
 */
package com.moulberry.flashback.editor.keybinds;

import com.moulberry.flashback.configuration.FlashbackConfigV1;
import com.moulberry.flashback.editor.keybinds.Keybind;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Keybinds {
    public static Map<Integer, Set<Keybind>> keybindsForKey = new HashMap<Integer, Set<Keybind>>();
    public static final Keybind PAUSE = new Keybind("pause", 80, false, false, false, false);
    public static final Keybind COPY = new Keybind("copy", 67, false, true, false, false);
    public static final Keybind PASTE = new Keybind("paste", 86, false, true, false, false);
    public static final Keybind UNDO = new Keybind("undo", 90, false, true, false, false);
    public static final Keybind REDO = new Keybind("redo", 89, false, true, false, false);
    public static final Keybind MARK_IN = new Keybind("mark_in", 73, false, false, false, false);
    public static final Keybind MARK_OUT = new Keybind("mark_out", 79, false, false, false, false);
    public static final Keybind CLEAR_IN = new Keybind("clear_in", 73, false, true, false, false);
    public static final Keybind CLEAR_OUT = new Keybind("clear_out", 79, false, true, false, false);
    public static final Keybind ZOOM_IN = new Keybind("zoom_in", 61, false, false, false, false);
    public static final Keybind ZOOM_OUT = new Keybind("zoom_out", 45, false, false, false, false);
    public static final Keybind ADD_CAMERA = new Keybind("add_camera", 0, false, false, false, false);
    public static final Keybind TIMELINE_ZOOM_SCROLL = new Keybind("timeline_zoom_scroll", 17, false, false, false, false).withForceScrollKey();
    public static final Keybind TIMELINE_MOVE_SCROLL = new Keybind("timeline_move_scroll", 17, false, true, false, false).withForceScrollKey();
    public static final Keybind ROLL_CW = new Keybind("roll_cw", 0, false, false, false, false);
    public static final Keybind ROLL_CCW = new Keybind("roll_ccw", 0, false, false, false, false);
    public static final List<Keybind> KEYBINDS = List.of(PAUSE, COPY, PASTE, UNDO, REDO, MARK_IN, MARK_OUT, CLEAR_IN, CLEAR_OUT, ZOOM_IN, ZOOM_OUT, ADD_CAMERA, TIMELINE_ZOOM_SCROLL, TIMELINE_MOVE_SCROLL, ROLL_CW, ROLL_CCW);

    public static void updateMapping(Keybind keybind, int old) {
        if (keybind.getKey() == old) {
            return;
        }
        if (old != 0 && keybindsForKey.containsKey(old)) {
            Set<Keybind> set = keybindsForKey.get(old);
            set.remove(keybind);
        }
        if (keybind.getKey() != 0) {
            keybindsForKey.computeIfAbsent(keybind.getKey(), k -> new HashSet()).add(keybind);
        }
    }

    public static void load(FlashbackConfigV1 flashbackConfig) {
        for (Keybind keybind : KEYBINDS) {
            String description = keybind.getDescriptionRaw();
            String keybindValue = flashbackConfig.keybinds.get(description);
            if (keybindValue == null) continue;
            keybind.loadFromConfigValue(keybindValue);
        }
    }

    public static void save(FlashbackConfigV1 flashbackConfig) {
        for (Keybind keybind : KEYBINDS) {
            String description = keybind.getDescriptionRaw();
            String keybindValue = keybind.toConfigValue();
            flashbackConfig.keybinds.put(description, keybindValue);
        }
    }
}

