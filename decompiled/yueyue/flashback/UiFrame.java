package yueyue.flashback;

import java.nio.ByteBuffer;
import java.util.UUID;
import net.minecraft.network.RegistryFriendlyByteBuf;

public record UiFrame(UUID player, int width, int height, boolean screenOpen, boolean crosshair, int mouseX, int mouseY, int cursorScale, byte[] png) {

    public UiFrame(UUID player, int width, int height, boolean screenOpen, boolean crosshair, int mouseX, int mouseY, int cursorScale, byte[] png) {
        this.player = player;
        this.width = width;
        this.height = height;
        this.screenOpen = screenOpen;
        this.crosshair = crosshair;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.cursorScale = cursorScale;
        this.png = png;
    }

    public void write(RegistryFriendlyByteBuf buffer) {
        buffer.writeVarInt(2);
        buffer.writeUUID(this.player);
        buffer.writeVarInt(this.width);
        buffer.writeVarInt(this.height);
        buffer.writeBoolean(this.screenOpen);
        buffer.writeBoolean(this.crosshair);
        buffer.writeVarInt(this.mouseX);
        buffer.writeVarInt(this.mouseY);
        buffer.writeVarInt(this.cursorScale);
        buffer.writeByteArray(this.png);
    }

    public static UiFrame read(RegistryFriendlyByteBuf buffer) {
        int version = buffer.readVarInt();
        UUID player = buffer.readUUID();
        int width = buffer.readVarInt();
        int height = buffer.readVarInt();
        boolean screenOpen = buffer.readBoolean();
        boolean crosshair = buffer.readBoolean();
        int mouseX = 0;
        int mouseY = 0;
        int cursorScale = 1;
        if (version >= 2) {
            mouseX = buffer.readVarInt();
            mouseY = buffer.readVarInt();
            cursorScale = buffer.readVarInt();
        }
        byte[] png = buffer.readByteArray(0x2000000);
        if (png.length == 0 && width == 0 && height == 0) {
            return new UiFrame(player, 0, 0, false, false, 0, 0, 1, png);
        }
        UiFrame.validatePng(png, width, height);
        return new UiFrame(player, width, height, screenOpen, crosshair, mouseX, mouseY, cursorScale, png);
    }

    public static void validatePng(byte[] bytes, int width, int height) {
        if (width < 1 || height < 1 || width > 4096 || height > 4096 || (long)width * (long)height > 0x870000L || bytes.length < 33 || bytes.length > 0x2000000) {
            throw new IllegalArgumentException("UI image exceeds limits");
        }
        ByteBuffer header = ByteBuffer.wrap(bytes);
        if (header.getLong() != -8552249625308161526L || header.getInt() != 13 || header.getInt() != 1229472850 || header.getInt() != width || header.getInt() != height || header.get() != 8 || header.get() != 6) {
            throw new IllegalArgumentException("Unexpected UI PNG header");
        }
    }

    public UUID player() { return this.player; }
    public int width() { return this.width; }
    public int height() { return this.height; }
    public boolean screenOpen() { return this.screenOpen; }
    public boolean crosshair() { return this.crosshair; }
    public int mouseX() { return this.mouseX; }
    public int mouseY() { return this.mouseY; }
    public int cursorScale() { return this.cursorScale; }
    public byte[] png() { return this.png; }
}
