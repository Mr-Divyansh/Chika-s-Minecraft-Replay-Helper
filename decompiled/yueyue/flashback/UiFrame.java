/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.RegistryFriendlyByteBuf
 */
package yueyue.flashback;

import java.nio.ByteBuffer;
import java.util.UUID;
import net.minecraft.network.RegistryFriendlyByteBuf;

public record UiFrame(UUID player, int width, int height, boolean screenOpen, boolean crosshair, byte[] png) {
    private final UUID player;
    private final int width;
    private final int height;
    private final boolean screenOpen;
    private final boolean crosshair;
    private final byte[] png;

    public UiFrame(UUID player, int width, int height, boolean screenOpen, boolean crosshair, byte[] png) {
        this.player = player;
        this.width = width;
        this.height = height;
        this.screenOpen = screenOpen;
        this.crosshair = crosshair;
        this.png = png;
    }

    public void write(RegistryFriendlyByteBuf buffer) {
        buffer.writeVarInt(1);
        buffer.writeUUID(this.player);
        buffer.writeVarInt(this.width);
        buffer.writeVarInt(this.height);
        buffer.writeBoolean(this.screenOpen);
        buffer.writeBoolean(this.crosshair);
        buffer.writeByteArray(this.png);
    }

    public static UiFrame read(RegistryFriendlyByteBuf buffer) {
        if (buffer.readVarInt() != 1) {
            throw new IllegalArgumentException("Unsupported UI frame version");
        }
        UUID player = buffer.readUUID();
        int width = buffer.readVarInt();
        int height = buffer.readVarInt();
        boolean screenOpen = buffer.readBoolean();
        boolean crosshair = buffer.readBoolean();
        byte[] png = buffer.readByteArray(0x2000000);
        if (png.length == 0 && width == 0 && height == 0) {
            return new UiFrame(player, 0, 0, false, false, png);
        }
        UiFrame.validatePng(png, width, height);
        return new UiFrame(player, width, height, screenOpen, crosshair, png);
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

    public UUID player() {
        return this.player;
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    public boolean screenOpen() {
        return this.screenOpen;
    }

    public boolean crosshair() {
        return this.crosshair;
    }

    public byte[] png() {
        return this.png;
    }
}

