/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package com.moulberry.flashback.screen.select_replay;

import com.moulberry.flashback.screen.ReplaySummary;
import com.moulberry.flashback.screen.select_replay.ReplaySelectionEntry;
import com.moulberry.flashback.screen.select_replay.ReplaySelectionList;
import java.nio.file.Path;
import java.util.Locale;
import net.minecraft.client.Minecraft;

public interface PendingSelectionEntry {
    public ReplaySelectionEntry createEntry(ReplaySelectionList var1, Minecraft var2);

    public boolean matchesFilter(String var1);

    public record Replay(ReplaySummary summary) implements PendingSelectionEntry
    {
        @Override
        public ReplaySelectionEntry createEntry(ReplaySelectionList replaySelectionList, Minecraft minecraft) {
            return new ReplaySelectionEntry.ReplayListEntry(replaySelectionList, minecraft, this.summary);
        }

        @Override
        public boolean matchesFilter(String filter) {
            if (this.summary.getReplayName().toLowerCase(Locale.ROOT).contains(filter) || this.summary.getReplayId().toLowerCase(Locale.ROOT).contains(filter)) {
                return true;
            }
            String worldName = this.summary.getWorldName();
            if (worldName != null) {
                return worldName.toLowerCase(Locale.ROOT).contains(filter);
            }
            return false;
        }
    }

    public record Folder(Path path, String filename, long modifiedTime, int replayCount) implements PendingSelectionEntry
    {
        public Folder(Path path, long modifiedTime, int replayCount) {
            this(path, path.getFileName().toString().toLowerCase(Locale.ROOT), modifiedTime, replayCount);
        }

        @Override
        public ReplaySelectionEntry createEntry(ReplaySelectionList replaySelectionList, Minecraft minecraft) {
            return new ReplaySelectionEntry.ReplayFolder(replaySelectionList, minecraft, this.path, this.modifiedTime, this.replayCount);
        }

        @Override
        public boolean matchesFilter(String filter) {
            return this.filename.contains(filter);
        }
    }
}

