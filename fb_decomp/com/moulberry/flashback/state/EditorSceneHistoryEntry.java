/*
 * Decompiled with CFR 0.152.
 */
package com.moulberry.flashback.state;

import com.moulberry.flashback.state.EditorSceneHistoryAction;
import java.util.List;

public record EditorSceneHistoryEntry(List<EditorSceneHistoryAction> undo, List<EditorSceneHistoryAction> redo, String description) {
}

