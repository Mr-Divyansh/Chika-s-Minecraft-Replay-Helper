/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.moulberry.flashback.io.ReplayWriter
 */
package yueyue.flashback;

import com.moulberry.flashback.io.ReplayWriter;
import java.util.function.Consumer;

public interface UiTaskQueue {
    public boolean yueyue$offer(Consumer<ReplayWriter> var1);
}

