/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.moulberry.flashback.io.AsyncReplaySaver
 *  com.moulberry.flashback.io.ReplayWriter
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 */
package D_Web_Studio_Chika_Replay_Helper.mixin;

import com.moulberry.flashback.io.AsyncReplaySaver;
import com.moulberry.flashback.io.ReplayWriter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import D_Web_Studio_Chika_Replay_Helper.UiTaskQueue;

@Mixin(value={AsyncReplaySaver.class}, remap=false)
public abstract class SaverQueueMixin
implements UiTaskQueue {
    @Shadow
    @Final
    private ArrayBlockingQueue<Consumer<ReplayWriter>> tasks;
    @Shadow
    @Final
    private AtomicBoolean hasStopped;

    @Shadow
    protected abstract void checkForError();

    @Override
    public boolean yueyue$offer(Consumer<ReplayWriter> task) {
        this.checkForError();
        return !this.hasStopped.get() && this.tasks.offer(task);
    }
}

