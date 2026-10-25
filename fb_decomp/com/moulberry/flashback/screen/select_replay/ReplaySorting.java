/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 */
package com.moulberry.flashback.screen.select_replay;

import com.moulberry.flashback.screen.ReplaySummary;
import com.moulberry.flashback.screen.select_replay.NaturalOrderComparator;
import com.moulberry.flashback.screen.select_replay.PendingSelectionEntry;
import java.util.Comparator;
import net.minecraft.network.chat.Component;

public enum ReplaySorting {
    CREATED_DATE((Component)Component.translatable((String)"flashback.replay_sorting.created_date"), Comparator.comparingLong(ReplaySummary::getLastModified).reversed(), Comparator.comparingLong(ReplaySummary::getLastModified)),
    REPLAY_NAME((Component)Component.translatable((String)"flashback.replay_sorting.replay_name"), Comparator.comparing(ReplaySummary::getReplayName, NaturalOrderComparator.INSTANCE), Comparator.comparing(ReplaySummary::getReplayName, NaturalOrderComparator.INSTANCE.reversed())),
    WORLD_NAME((Component)Component.translatable((String)"flashback.replay_sorting.world_name"), Comparator.comparing(ReplaySummary::getWorldName, Comparator.nullsLast(NaturalOrderComparator.INSTANCE)), Comparator.comparing(ReplaySummary::getWorldName, Comparator.nullsLast(NaturalOrderComparator.INSTANCE.reversed()))),
    DURATION((Component)Component.translatable((String)"flashback.replay_sorting.duration"), Comparator.comparingLong(replay -> replay.getReplayMetadata().totalTicks).reversed(), Comparator.comparingLong(replay -> replay.getReplayMetadata().totalTicks)),
    FILESIZE((Component)Component.translatable((String)"flashback.replay_sorting.filesize"), Comparator.comparingLong(ReplaySummary::getFilesize).reversed(), Comparator.comparingLong(ReplaySummary::getFilesize));

    private final Component component;
    private final Comparator<ReplaySummary> descendingComparator;
    private final Comparator<ReplaySummary> ascendingComparator;

    private ReplaySorting(Component component, Comparator<ReplaySummary> descendingComparator, Comparator<ReplaySummary> ascendingComparator) {
        this.component = component;
        this.descendingComparator = descendingComparator.thenComparing(ReplaySummary::getReplayId, NaturalOrderComparator.INSTANCE);
        this.ascendingComparator = ascendingComparator.thenComparing(ReplaySummary::getReplayId, NaturalOrderComparator.INSTANCE);
    }

    public Component component() {
        return this.component;
    }

    public Comparator<PendingSelectionEntry> comparator(boolean descending) {
        Comparator<ReplaySummary> replayComparator = descending ? this.descendingComparator : this.ascendingComparator;
        return (entry1, entry2) -> {
            if (entry1 instanceof PendingSelectionEntry.Replay) {
                PendingSelectionEntry.Replay replay1 = (PendingSelectionEntry.Replay)entry1;
                if (entry2 instanceof PendingSelectionEntry.Replay) {
                    PendingSelectionEntry.Replay replay2 = (PendingSelectionEntry.Replay)entry2;
                    return replayComparator.compare(replay1.summary(), replay2.summary());
                }
                return 1;
            }
            if (entry1 instanceof PendingSelectionEntry.Folder) {
                PendingSelectionEntry.Folder folder1 = (PendingSelectionEntry.Folder)entry1;
                if (entry2 instanceof PendingSelectionEntry.Folder) {
                    PendingSelectionEntry.Folder folder2 = (PendingSelectionEntry.Folder)entry2;
                    return NaturalOrderComparator.INSTANCE.compare(folder1.filename(), folder2.filename());
                }
                return -1;
            }
            return 0;
        };
    }
}

