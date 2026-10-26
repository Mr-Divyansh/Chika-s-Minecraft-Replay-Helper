/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.moulberry.flashback.action.Action
 *  com.moulberry.flashback.io.AsyncReplaySaver
 *  com.moulberry.flashback.io.ReplayWriter
 *  com.moulberry.flashback.record.Recorder
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Constant
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.ModifyConstant
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package yueyue.flashback.mixin;

import com.moulberry.flashback.action.Action;
import com.moulberry.flashback.io.AsyncReplaySaver;
import com.moulberry.flashback.io.ReplayWriter;
import com.moulberry.flashback.record.Recorder;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yueyue.flashback.UiAction;
import yueyue.flashback.UiConfig;
import yueyue.flashback.UiFrame;
import yueyue.flashback.UiTaskQueue;
import yueyue.flashback.capture.UiCapture;
import yueyue.flashback.capture.UiImage;

@Mixin(value={Recorder.class}, remap=false)
public abstract class RecorderMixin {
    @Shadow
    @Final
    private AsyncReplaySaver asyncReplaySaver;
    @Unique
    private UiImage yueyue$lastWritten;
    @Unique
    private final AtomicBoolean yueyue$pending = new AtomicBoolean();
    @Unique
    private long yueyue$segmentBytes;

    @ModifyConstant(method={"endTick"}, constant={@Constant(intValue=6000)})
    private int yueyue$boundedSegment(int original) {
        return this.yueyue$segmentBytes >= 0x4000000L ? 0 : original;
    }

    @Inject(method={"writeSnapshot"}, at={@At(value="HEAD")})
    private void yueyue$resetBudget(boolean actual, CallbackInfo ci) {
        this.yueyue$segmentBytes = 0L;
    }

    @Inject(method={"writeLocalData"}, at={@At(value="TAIL")})
    private void yueyue$record(CallbackInfo ci) {
        this.yueyue$write(false);
    }

    @Inject(method={"writeSnapshot"}, at={@At(value="INVOKE", target="Lcom/moulberry/flashback/record/Recorder;writeCustomSnapshot(Ljava/util/function/Consumer;)V")})
    private void yueyue$snapshot(boolean actual, CallbackInfo ci) {
        this.yueyue$write(true);
    }

    @Unique
    private void yueyue$write(boolean snapshot) {
        UiFrame frame;
        UiImage image = UiCapture.latest;
        if (!UiConfig.current.record) {
            image = null;
        }
        if (image != null && image.session() != this) {
            image = null;
        }
        if (!snapshot && image == this.yueyue$lastWritten) {
            return;
        }
        LocalPlayer player = Minecraft.getInstance().player;
        UUID uuid = player == null ? new UUID(0L, 0L) : player.getUUID();
        frame = image == null ? new UiFrame(uuid, 0, 0, false, false, 0, 0, 1, new byte[0]) : new UiFrame(uuid, image.width(), image.height(), image.screenOpen(), image.crosshair(), image.mouseX(), image.mouseY(), image.cursorScale(), image.png());
        if (!snapshot && !this.yueyue$pending.compareAndSet(false, true)) {
            return;
        }
        Consumer<ReplayWriter> task = writer -> {
            try {
                writer.startAction((Action)UiAction.INSTANCE);
                frame.write(writer.friendlyByteBuf());
                writer.finishAction((Action)UiAction.INSTANCE);
            }
            finally {
                if (!snapshot) {
                    this.yueyue$pending.set(false);
                }
            }
        };
        if (snapshot) {
            this.asyncReplaySaver.submit(task);
        } else if (!((UiTaskQueue)this.asyncReplaySaver).yueyue$offer(task)) {
            this.yueyue$pending.set(false);
            return;
        }
        this.yueyue$lastWritten = image;
        this.yueyue$segmentBytes += (long)frame.png().length;
    }
}

