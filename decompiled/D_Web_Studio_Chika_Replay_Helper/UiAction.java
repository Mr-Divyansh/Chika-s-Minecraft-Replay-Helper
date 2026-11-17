/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.moulberry.flashback.action.Action
 *  com.moulberry.flashback.playback.ReplayServer
 *  net.minecraft.network.RegistryFriendlyByteBuf
 *  net.minecraft.resources.Identifier
 *  org.slf4j.LoggerFactory
 */
package D_Web_Studio_Chika_Replay_Helper;

import com.moulberry.flashback.action.Action;
import com.moulberry.flashback.playback.ReplayServer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.Identifier;
import org.slf4j.LoggerFactory;

public final class UiAction
implements Action {
    public static final UiAction INSTANCE = new UiAction();
    private static final Identifier NAME = Identifier.fromNamespaceAndPath((String)"d_web_studio_chika_replay_helper", (String)"first_person_ui_optional");

    private UiAction() {
    }

    public Identifier name() {
        return NAME;
    }

    public void handle(ReplayServer server, RegistryFriendlyByteBuf buffer) {
        try {
            UiFrame frame = UiFrame.read(buffer);
            if (buffer.isReadable()) {
                throw new IllegalArgumentException("Trailing optional UI data");
            }
            UiRuntime.accept(server, frame);
        }
        catch (RuntimeException e) {
            buffer.skipBytes(buffer.readableBytes());
            UiRuntime.accept(server, null);
            LoggerFactory.getLogger((String)"yueyue-flashback").warn("Skipped invalid optional UI frame: {}", (Object)e.toString());
        }
    }
}

