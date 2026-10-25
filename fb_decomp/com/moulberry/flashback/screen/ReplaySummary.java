/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.loader.api.FabricLoader
 *  net.minecraft.SharedConstants
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.Nullable
 */
package com.moulberry.flashback.screen;

import com.moulberry.flashback.record.FlashbackMeta;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.Nullable;

public class ReplaySummary
implements Comparable<ReplaySummary> {
    private final Path path;
    private final FlashbackMeta metadata;
    private final String replayId;
    private final long lastModified;
    private final long filesize;
    private final byte[] iconBytes;
    private Component info = null;
    private Component hoverInfo = null;
    private boolean canOpen = true;
    private boolean hasWarning = false;
    private boolean hasNamespaceMismatch = false;

    public ReplaySummary(Path path, FlashbackMeta metadata, LinkedHashMap<String, LinkedHashSet<String>> currentNamespacesForRegistries, String replayId, long lastModified, long filesize, @Nullable byte[] iconBytes) {
        this.path = path;
        this.metadata = metadata;
        this.replayId = replayId;
        this.lastModified = lastModified;
        this.filesize = filesize;
        this.iconBytes = iconBytes;
        String versionString = FabricLoader.getInstance().getRawGameVersion();
        if (metadata.namespacesForRegistries != null && !currentNamespacesForRegistries.equals(metadata.namespacesForRegistries)) {
            this.hasNamespaceMismatch = true;
        }
        if (metadata.protocolVersion != 0 && metadata.protocolVersion != SharedConstants.getProtocolVersion()) {
            this.canOpen = false;
            this.hoverInfo = metadata.versionString != null && !metadata.versionString.equals(versionString) ? Component.translatable((String)"flashback.incompatible_replay_version", (Object[])new Object[]{Component.literal((String)metadata.versionString), Component.literal((String)versionString)}) : Component.translatable((String)"flashback.incompatible_replay_version_protocol", (Object[])new Object[]{Component.literal((String)String.valueOf(metadata.protocolVersion)), Component.literal((String)String.valueOf(SharedConstants.getProtocolVersion()))});
        } else if (metadata.dataVersion != 0 && metadata.dataVersion != SharedConstants.getCurrentVersion().dataVersion().version()) {
            this.hasWarning = true;
            this.hoverInfo = metadata.versionString != null && !metadata.versionString.equals(versionString) ? Component.translatable((String)"flashback.maybe_incompatible_replay_version", (Object[])new Object[]{Component.literal((String)metadata.versionString), Component.literal((String)versionString)}) : Component.translatable((String)"flashback.incompatible_replay_version_data", (Object[])new Object[]{Component.literal((String)String.valueOf(metadata.dataVersion)), Component.literal((String)String.valueOf(SharedConstants.getCurrentVersion().dataVersion().version()))});
        } else if (this.hasNamespaceMismatch) {
            this.hasWarning = true;
            this.hoverInfo = Component.translatable((String)"flashback.maybe_incompatible_registry_data");
        }
    }

    public Path getPath() {
        return this.path;
    }

    public String getReplayId() {
        return this.replayId;
    }

    public FlashbackMeta getReplayMetadata() {
        return this.metadata;
    }

    public String getReplayName() {
        return this.metadata.name.isEmpty() ? this.replayId : this.metadata.name;
    }

    @Nullable
    public String getWorldName() {
        return this.metadata.worldName;
    }

    public long getLastModified() {
        return this.lastModified;
    }

    public long getFilesize() {
        return this.filesize;
    }

    public Component getInfo() {
        if (this.info == null) {
            MutableComponent mutable = Component.empty();
            if (this.metadata.totalTicks > 0) {
                int ticks = this.metadata.totalTicks;
                int seconds = ticks / 20 % 60;
                int minutes = ticks / 20 / 60 % 60;
                int hours = ticks / 20 / 60 / 60;
                StringBuilder builder = new StringBuilder();
                if (hours > 0) {
                    builder.append(hours).append('h');
                }
                if (minutes > 0) {
                    if (!builder.isEmpty()) {
                        builder.append(' ');
                    }
                    builder.append(minutes).append('m');
                }
                if (seconds > 0) {
                    if (!builder.isEmpty()) {
                        builder.append(' ');
                    }
                    builder.append(seconds).append('s');
                }
                if (builder.isEmpty()) {
                    builder.append("0s");
                }
                mutable.append((Component)Component.translatable((String)"flashback.select_replay.duration", (Object[])new Object[]{builder}));
            }
            this.info = mutable;
        }
        return this.info;
    }

    public byte[] getIconBytes() {
        return this.iconBytes;
    }

    public Component getHoverInfo() {
        return this.hoverInfo;
    }

    public boolean hasWarning() {
        return this.hasWarning;
    }

    public boolean hasNamespaceMismatch() {
        return this.hasNamespaceMismatch;
    }

    public boolean canOpen() {
        return this.canOpen;
    }

    @Override
    public int compareTo(ReplaySummary levelSummary) {
        if (this.getLastModified() < levelSummary.getLastModified()) {
            return 1;
        }
        if (this.getLastModified() > levelSummary.getLastModified()) {
            return -1;
        }
        return this.replayId.compareTo(levelSummary.replayId);
    }
}

