/*
 * Decompiled with CFR 0.152.
 */
package com.moulberry.flashback.ext;

public interface ServerLevelExt {
    public void flashback$setSeedHash(long var1);

    public long flashback$getSeedHash();

    public boolean flashback$shouldSendChunk(long var1);

    public void flashback$markChunkAsSendable(long var1);

    public void flashback$setCanSpawnEntities(boolean var1);
}

