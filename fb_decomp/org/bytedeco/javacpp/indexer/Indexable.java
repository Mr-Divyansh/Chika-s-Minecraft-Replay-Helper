/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import org.bytedeco.javacpp.indexer.Indexer;

public interface Indexable {
    public <I extends Indexer> I createIndexer(boolean var1);
}

