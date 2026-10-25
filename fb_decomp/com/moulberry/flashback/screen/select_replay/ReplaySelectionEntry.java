/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.NativeImage
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.booleans.BooleanConsumer
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.components.ObjectSelectionList$Entry
 *  net.minecraft.client.gui.components.WidgetSprites
 *  net.minecraft.client.gui.components.toasts.SystemToast
 *  net.minecraft.client.gui.screens.ConfirmScreen
 *  net.minecraft.client.gui.screens.FaviconTexture
 *  net.minecraft.client.gui.screens.GenericMessageScreen
 *  net.minecraft.client.gui.screens.LoadingDotsText
 *  net.minecraft.client.gui.screens.ProgressScreen
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.input.MouseButtonEvent
 *  net.minecraft.client.renderer.RenderPipelines
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.core.Holder
 *  net.minecraft.network.chat.CommonComponents
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.Identifier
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.util.Util
 *  org.apache.commons.lang3.StringUtils
 *  org.slf4j.Logger
 */
package com.moulberry.flashback.screen.select_replay;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.logging.LogUtils;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.RegistryMetaHelper;
import com.moulberry.flashback.screen.EditReplayScreen;
import com.moulberry.flashback.screen.ReplaySummary;
import com.moulberry.flashback.screen.select_replay.ReplaySelectionList;
import com.moulberry.flashback.screen.select_replay.SelectReplayScreen;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Objects;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.FaviconTexture;
import net.minecraft.client.gui.screens.GenericMessageScreen;
import net.minecraft.client.gui.screens.LoadingDotsText;
import net.minecraft.client.gui.screens.ProgressScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

public abstract class ReplaySelectionEntry
extends ObjectSelectionList.Entry<ReplaySelectionEntry>
implements AutoCloseable {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withZone(ZoneId.systemDefault());
    private static final Identifier FOLDER_SPRITE = Flashback.createIdentifier("folder");
    private static final Identifier ERROR_HIGHLIGHTED_SPRITE = Identifier.withDefaultNamespace((String)"world_list/error_highlighted");
    private static final Identifier ERROR_SPRITE = Identifier.withDefaultNamespace((String)"world_list/error");
    private static final Identifier WARNING_HIGHLIGHTED_SPRITE = Identifier.withDefaultNamespace((String)"world_list/warning_highlighted");
    private static final Identifier WARNING_SPRITE = Identifier.withDefaultNamespace((String)"world_list/warning");
    private static final Identifier JOIN_HIGHLIGHTED_SPRITE = Identifier.withDefaultNamespace((String)"world_list/join_highlighted");
    private static final Identifier JOIN_SPRITE = Identifier.withDefaultNamespace((String)"world_list/join");
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void close() {
    }

    public static class ReplayListEntry
    extends ReplaySelectionEntry
    implements AutoCloseable {
        private static final int ICON_WIDTH = 32;
        private static final int ICON_HEIGHT = 32;
        private final Minecraft minecraft;
        private final ReplaySelectionList replaySelectionList;
        final ReplaySummary summary;
        private FaviconTexture icon;
        private long lastClickTime;

        public ReplayListEntry(ReplaySelectionList replaySelectionList, Minecraft minecraft, ReplaySummary ReplaySummary2) {
            this.minecraft = minecraft;
            this.replaySelectionList = replaySelectionList;
            this.summary = ReplaySummary2;
            this.icon = FaviconTexture.forWorld((TextureManager)this.minecraft.getTextureManager(), (String)ReplaySummary2.getReplayId());
            this.loadIcon();
        }

        public Component getNarration() {
            MutableComponent component = Component.translatable((String)"narrator.select.world_info", (Object[])new Object[]{this.summary.getReplayName(), Component.translationArg((Date)new Date(this.summary.getLastModified())), this.summary.getInfo()});
            return Component.translatable((String)"narrator.select", (Object[])new Object[]{component});
        }

        public void extractContent(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, boolean hovered, float partialTick) {
            int x = this.getContentX();
            int y = this.getContentY();
            String title = this.summary.getReplayName();
            Object fileAndTime = this.summary.getReplayId();
            long p = this.summary.getLastModified();
            if (p != -1L) {
                fileAndTime = (String)fileAndTime + " (" + DATE_FORMAT.format(Instant.ofEpochMilli(p)) + ")";
            }
            if (StringUtils.isEmpty((CharSequence)title)) {
                title = I18n.get((String)"flashback.select_replay.replay", (Object[])new Object[0]);
            }
            Component info = this.summary.getInfo();
            int titleColour = -1;
            if (!this.summary.canOpen()) {
                titleColour = -43691;
            } else if (this.summary.hasWarning()) {
                titleColour = -21931;
            }
            guiGraphics.text(this.minecraft.font, title, x + 32 + 3, y + 1, titleColour, false);
            int titleEnd = x + 32 + 3 + this.minecraft.font.width(title);
            String worldName = this.summary.getWorldName();
            if (worldName != null) {
                guiGraphics.text(this.minecraft.font, "(" + worldName + ")", titleEnd + 4, y + 1, -8355712, false);
            }
            Font font = this.minecraft.font;
            Objects.requireNonNull(this.minecraft.font);
            guiGraphics.text(font, (String)fileAndTime, x + 32 + 3, y + 9 + 3, -8355712, false);
            Font font2 = this.minecraft.font;
            Objects.requireNonNull(this.minecraft.font);
            Objects.requireNonNull(this.minecraft.font);
            guiGraphics.text(font2, info, x + 32 + 3, y + 9 + 9 + 3, -8355712, false);
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, this.icon.textureLocation(), x, y, 0.0f, 0.0f, 32, 32, 32, 32);
            if (((Boolean)this.minecraft.options.touchscreen().get()).booleanValue() || hovered) {
                boolean hoveredIcon;
                guiGraphics.fill(x, y, x + 32, y + 32, -1601138544);
                int q = mouseX - x;
                boolean bl = hoveredIcon = q < 32;
                if (hovered && this.summary.getHoverInfo() != null) {
                    guiGraphics.setTooltipForNextFrame(this.minecraft.font, this.minecraft.font.split((FormattedText)this.summary.getHoverInfo(), 240), mouseX, mouseY);
                }
                Identifier iconOverlay = !this.summary.canOpen() ? (hoveredIcon ? ERROR_HIGHLIGHTED_SPRITE : ERROR_SPRITE) : (this.summary.hasWarning() ? (hoveredIcon ? WARNING_HIGHLIGHTED_SPRITE : WARNING_SPRITE) : (hoveredIcon ? JOIN_HIGHLIGHTED_SPRITE : JOIN_SPRITE));
                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, iconOverlay, x, y, 32, 32);
            }
        }

        public boolean mouseClicked(MouseButtonEvent event, boolean doubleClicked) {
            if (!this.summary.canOpen()) {
                return true;
            }
            this.replaySelectionList.setSelected(this);
            if (event.x() - (double)this.replaySelectionList.getRowLeft() <= 32.0 || Util.getMillis() - this.lastClickTime < 250L) {
                if (this.canOpen()) {
                    this.minecraft.getSoundManager().play((SoundInstance)SimpleSoundInstance.forUI((Holder)SoundEvents.UI_BUTTON_CLICK, (float)1.0f));
                    this.openReplay();
                }
                return true;
            }
            this.lastClickTime = Util.getMillis();
            return super.mouseClicked(event, doubleClicked);
        }

        public boolean canOpen() {
            return this.summary.canOpen();
        }

        public void openReplay() {
            if (this.summary.canOpen()) {
                if (this.summary.hasNamespaceMismatch()) {
                    Screen previousScreen = this.minecraft.screen;
                    BooleanConsumer afterWarning = doLoad -> {
                        if (doLoad) {
                            this.minecraft.setScreenAndShow((Screen)new GenericMessageScreen((Component)Component.translatable((String)"flashback.select_replay.data_read")));
                            Flashback.openReplayWorld(this.summary.getPath());
                        } else {
                            this.minecraft.setScreen(previousScreen);
                        }
                    };
                    Component message = RegistryMetaHelper.createMismatchWarning(this.replaySelectionList.currentNamespacesForRegistries, this.summary.getReplayMetadata().namespacesForRegistries);
                    this.minecraft.setScreen((Screen)new ConfirmScreen(afterWarning, (Component)Component.translatable((String)"flashback.screen_registry_mismatch"), message, (Component)Component.translatable((String)"selectWorld.backupJoinSkipButton"), CommonComponents.GUI_CANCEL));
                } else {
                    this.minecraft.setScreenAndShow((Screen)new GenericMessageScreen((Component)Component.translatable((String)"flashback.select_replay.data_read")));
                    Flashback.openReplayWorld(this.summary.getPath());
                }
            }
        }

        public void deleteReplay() {
            this.minecraft.setScreen((Screen)new ConfirmScreen(bl -> {
                if (bl) {
                    this.minecraft.setScreen((Screen)new ProgressScreen(true));
                    this.doDeleteReplay();
                }
                this.minecraft.setScreen((Screen)this.replaySelectionList.getScreen());
            }, (Component)Component.translatable((String)"flashback.select_replay.delete_question"), (Component)Component.translatable((String)"selectWorld.deleteWarning", (Object[])new Object[]{this.summary.getReplayName()}), (Component)Component.translatable((String)"selectWorld.deleteButton"), CommonComponents.GUI_CANCEL));
        }

        private void doDeleteReplay() {
            String id = this.summary.getReplayId();
            try {
                Files.delete(this.summary.getPath());
            }
            catch (IOException e) {
                SystemToast.onWorldDeleteFailure((Minecraft)this.minecraft, (String)id);
                LOGGER.error("Failed to delete replay {}", (Object)id, (Object)e);
            }
            this.replaySelectionList.reloadReplayList();
        }

        public void editReplay() {
            Minecraft.getInstance().setScreen((Screen)new EditReplayScreen(Minecraft.getInstance().screen, this.summary));
        }

        private void loadIcon() {
            byte[] iconBytes = this.summary.getIconBytes();
            if (iconBytes != null) {
                try {
                    this.icon.upload(NativeImage.read((byte[])iconBytes));
                    return;
                }
                catch (Throwable t) {
                    LOGGER.error("Invalid icon for replay {}", (Object)this.summary.getReplayId(), (Object)t);
                }
            }
            this.icon.clear();
        }

        @Override
        public void close() {
            if (this.icon != null) {
                this.icon.close();
                this.icon = null;
            }
        }
    }

    public static class ReplayFolder
    extends ReplaySelectionEntry
    implements AutoCloseable {
        private static final int ICON_WIDTH = 32;
        private static final int ICON_HEIGHT = 32;
        private final Minecraft minecraft;
        private final ReplaySelectionList replaySelectionList;
        private final Path path;
        private final String nameString;
        private final Component nameComponent;
        private final long modifiedTime;
        private final int replayCount;
        private long lastClickTime;

        public ReplayFolder(ReplaySelectionList replaySelectionList, Minecraft minecraft, Path path, long modifiedTime, int replayCount) {
            this.minecraft = minecraft;
            this.replaySelectionList = replaySelectionList;
            this.path = path;
            this.nameString = path.getFileName().toString();
            this.nameComponent = Component.literal((String)this.nameString).withStyle(ChatFormatting.BOLD);
            this.modifiedTime = modifiedTime;
            this.replayCount = replayCount;
        }

        public Component getNarration() {
            return Component.translatable((String)"narrator.select", (Object[])new Object[]{this.nameComponent});
        }

        public void extractContent(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, boolean hovered, float partialTick) {
            int x = this.getContentX();
            int y = this.getContentY();
            guiGraphics.text(this.minecraft.font, this.nameComponent, x + 32 + 3, y + 1, -1, false);
            Objects.requireNonNull(this.minecraft.font);
            int textY = y + 9 + 2;
            if (this.modifiedTime > 0L) {
                String time = "Created: " + DATE_FORMAT.format(Instant.ofEpochMilli(this.modifiedTime));
                guiGraphics.text(this.minecraft.font, time, x + 32 + 3, textY + 1, -8355712, false);
                Objects.requireNonNull(this.minecraft.font);
                textY += 9;
            }
            guiGraphics.text(this.minecraft.font, "Replays: " + this.replayCount, x + 32 + 3, textY + 3, -8355712, false);
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, FOLDER_SPRITE, x, y, 32, 32);
            if (((Boolean)this.minecraft.options.touchscreen().get()).booleanValue() || hovered) {
                guiGraphics.fill(x, y, x + 32, y + 32, -1601138544);
                int q = mouseX - x;
                boolean hoveredIcon = q < 32;
                Identifier iconOverlay = hoveredIcon ? JOIN_HIGHLIGHTED_SPRITE : JOIN_SPRITE;
                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, iconOverlay, x, y, 32, 32);
            }
        }

        public boolean mouseClicked(MouseButtonEvent mouseButtonEvent, boolean doubleClick) {
            this.replaySelectionList.setSelected(this);
            if (mouseButtonEvent.x() - (double)this.replaySelectionList.getRowLeft() <= 32.0 || Util.getMillis() - this.lastClickTime < 250L) {
                this.minecraft.getSoundManager().play((SoundInstance)SimpleSoundInstance.forUI((Holder)SoundEvents.UI_BUTTON_CLICK, (float)1.0f));
                this.minecraft.setScreen((Screen)new SelectReplayScreen(this.replaySelectionList.getScreen(), this.path));
                return true;
            }
            this.lastClickTime = Util.getMillis();
            return super.mouseClicked(mouseButtonEvent, doubleClick);
        }
    }

    public static class LoadingHeader
    extends ReplaySelectionEntry {
        private static final Component LOADING_LABEL = Component.translatable((String)"flashback.select_replay.loading_replays");
        private final Minecraft minecraft;

        public LoadingHeader(Minecraft minecraft) {
            this.minecraft = minecraft;
        }

        public void extractContent(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, boolean hovered, float partialTick) {
            int p = (this.minecraft.screen.width - this.minecraft.font.width((FormattedText)LOADING_LABEL)) / 2;
            int n = this.getContentY();
            int n2 = this.getContentHeight();
            Objects.requireNonNull(this.minecraft.font);
            int q = n + (n2 - 9) / 2;
            guiGraphics.text(this.minecraft.font, LOADING_LABEL, p, q, -1, false);
            String string = LoadingDotsText.get((long)Util.getMillis());
            int r = (this.minecraft.screen.width - this.minecraft.font.width(string)) / 2;
            Objects.requireNonNull(this.minecraft.font);
            int s = q + 9;
            guiGraphics.text(this.minecraft.font, string, r, s, -8355712, false);
        }

        public Component getNarration() {
            return LOADING_LABEL;
        }
    }

    public static class LoadFromDeviceHeader
    extends ReplaySelectionEntry {
        private static final Component LOAD_REPLAY_LABEL = Component.translatable((String)"flashback.select_replay.load_replay_from_file");
        private static final WidgetSprites SPRITES = new WidgetSprites(Identifier.withDefaultNamespace((String)"widget/button"), Identifier.withDefaultNamespace((String)"widget/button_disabled"), Identifier.withDefaultNamespace((String)"widget/button_highlighted"));
        private final Minecraft minecraft;

        public LoadFromDeviceHeader(Minecraft minecraft) {
            this.minecraft = minecraft;
        }

        public void extractContent(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, boolean hovered, float partialTick) {
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, SPRITES.get(true, hovered), this.getContentX() + 4, this.getContentY() + 2, this.getContentWidth() - 8, this.getContentHeight() - 4);
            int p = (this.minecraft.screen.width - this.minecraft.font.width((FormattedText)LOAD_REPLAY_LABEL)) / 2;
            int n = this.getContentY();
            int n2 = this.getContentHeight();
            Objects.requireNonNull(this.minecraft.font);
            int q = n + (n2 - 9) / 2 + 1;
            guiGraphics.text(this.minecraft.font, LOAD_REPLAY_LABEL, p, q, -1, true);
        }

        public Component getNarration() {
            return LOAD_REPLAY_LABEL;
        }
    }
}

