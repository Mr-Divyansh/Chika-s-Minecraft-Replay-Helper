/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation
 *  com.llamalad7.mixinextras.sugar.Local
 *  it.unimi.dsi.fastutil.ints.IntAVLTreeSet
 *  it.unimi.dsi.fastutil.ints.IntSortedSet
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.components.AbstractStringWidget
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Renderable
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.layouts.GridLayout
 *  net.minecraft.client.gui.layouts.GridLayout$RowHelper
 *  net.minecraft.client.gui.layouts.LayoutElement
 *  net.minecraft.client.gui.screens.ConfirmScreen
 *  net.minecraft.client.gui.screens.PauseScreen
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.Identifier
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.moulberry.flashback.mixin.ui;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.FlashbackTextComponents;
import com.moulberry.flashback.combo_options.RecordingControlsLocation;
import com.moulberry.flashback.screen.BottomTextWidget;
import com.moulberry.flashback.screen.FlashbackButton;
import it.unimi.dsi.fastutil.ints.IntAVLTreeSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import java.util.Objects;
import java.util.function.IntSupplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractStringWidget;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={PauseScreen.class})
public abstract class MixinPauseScreen
extends Screen {
    protected MixinPauseScreen(Component component) {
        super(component);
    }

    @WrapOperation(method={"createPauseMenu"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/gui/layouts/GridLayout;arrangeElements()V")})
    public void createPauseMenuBelow(GridLayout instance, Operation<Void> original, @Local GridLayout.RowHelper rowHelper) {
        RecordingControlsLocation controls = Flashback.getConfig().recordingControls.controlsLocation;
        if (!Flashback.isInReplay() && controls == RecordingControlsLocation.BELOW) {
            rowHelper.addChild((LayoutElement)new BottomTextWidget(204, 30, FlashbackTextComponents.FLASHBACK, Minecraft.getInstance().font), 2);
            if (Flashback.RECORDER == null) {
                rowHelper.addChild((LayoutElement)Button.builder((Component)Component.translatable((String)"flashback.recording_controls.start"), btn -> {
                    Flashback.startRecordingReplay();
                    Minecraft.getInstance().setScreen(null);
                }).width(204).build(), 2);
            } else {
                rowHelper.addChild((LayoutElement)Button.builder((Component)Component.translatable((String)"flashback.recording_controls.finish"), btn -> {
                    Flashback.finishRecordingReplay();
                    Minecraft.getInstance().setScreen(null);
                }).width(204).build(), 2);
                if (Flashback.RECORDER.isPaused()) {
                    rowHelper.addChild((LayoutElement)Button.builder((Component)Component.translatable((String)"flashback.recording_controls.unpause"), btn -> {
                        Flashback.pauseRecordingReplay(false);
                        Minecraft.getInstance().setScreen(null);
                    }).width(98).build());
                } else {
                    rowHelper.addChild((LayoutElement)Button.builder((Component)Component.translatable((String)"flashback.recording_controls.pause"), btn -> {
                        Flashback.pauseRecordingReplay(true);
                        Minecraft.getInstance().setScreen(null);
                    }).width(98).build());
                }
                rowHelper.addChild((LayoutElement)Button.builder((Component)Component.translatable((String)"flashback.recording_controls.cancel"), btn -> Minecraft.getInstance().setScreen((Screen)new ConfirmScreen(value -> {
                    if (value) {
                        Flashback.cancelRecordingReplay();
                        Minecraft.getInstance().setScreen(null);
                    } else {
                        Minecraft.getInstance().setScreen((Screen)new PauseScreen(true));
                    }
                }, (Component)Component.translatable((String)"flashback.confirm_cancel_recording"), (Component)Component.translatable((String)"flashback.confirm_cancel_recording_description")))).width(98).build());
            }
        }
        original.call(new Object[]{instance});
    }

    @Inject(method={"createPauseMenu"}, at={@At(value="RETURN")})
    public void createPauseMenuSide(CallbackInfo ci) {
        RecordingControlsLocation controls = Flashback.getConfig().recordingControls.controlsLocation;
        if (Flashback.isInReplay() || controls != RecordingControlsLocation.RIGHT && controls != RecordingControlsLocation.LEFT) {
            return;
        }
        boolean useRight = controls == RecordingControlsLocation.RIGHT;
        int x = this.width / 2;
        IntAVLTreeSet heightSet = new IntAVLTreeSet();
        for (Renderable renderable : this.renderables) {
            if (renderable instanceof AbstractStringWidget || !(renderable instanceof AbstractWidget)) continue;
            AbstractWidget otherWidget = (AbstractWidget)renderable;
            if (useRight) {
                newX = otherWidget.getRight() + 4;
                if (newX > x) {
                    x = newX;
                    heightSet.clear();
                }
            } else {
                newX = otherWidget.getX() - 20 - 4;
                if (newX < x) {
                    x = newX;
                    heightSet.clear();
                }
            }
            heightSet.add(otherWidget.getY());
        }
        IntSupplier nextHeight = new IntSupplier(){
            private final int[] heights;
            private int heightIndex;
            private int lastHeight;
            final /* synthetic */ IntSortedSet val$heightSet;
            final /* synthetic */ MixinPauseScreen this$0;
            {
                this.val$heightSet = intSortedSet;
                MixinPauseScreen mixinPauseScreen = this$0;
                Objects.requireNonNull(mixinPauseScreen);
                this.this$0 = mixinPauseScreen;
                this.heights = this.val$heightSet.toIntArray();
                this.heightIndex = this.heights.length >= 4 ? 1 : 0;
                this.lastHeight = Integer.MIN_VALUE;
            }

            @Override
            public int getAsInt() {
                if (this.heightIndex >= this.heights.length) {
                    if (this.lastHeight == Integer.MIN_VALUE) {
                        this.lastHeight = this.this$0.height / 2;
                        return this.lastHeight;
                    }
                    this.lastHeight += 24;
                    return this.lastHeight;
                }
                int height = this.heights[this.heightIndex];
                ++this.heightIndex;
                if (this.lastHeight != Integer.MIN_VALUE) {
                    height = Math.max(height, this.lastHeight + 24);
                }
                this.lastHeight = height;
                return this.lastHeight;
            }
        };
        if (Flashback.RECORDER == null) {
            int y = nextHeight.getAsInt();
            this.addRenderableWidget((GuiEventListener)new FlashbackButton(x, y, 20, 20, (Component)Component.translatable((String)"flashback.recording_controls.start"), btn -> {
                Flashback.startRecordingReplay();
                Minecraft.getInstance().setScreen(null);
            }, Identifier.fromNamespaceAndPath((String)"flashback", (String)"icon_pixelated_start.png")).flashbackWithTooltip());
        } else {
            int y = nextHeight.getAsInt();
            this.addRenderableWidget((GuiEventListener)new FlashbackButton(x, y, 20, 20, (Component)Component.translatable((String)"flashback.recording_controls.finish"), btn -> {
                Flashback.finishRecordingReplay();
                Minecraft.getInstance().setScreen(null);
            }, Identifier.fromNamespaceAndPath((String)"flashback", (String)"icon_pixelated_finish.png")).flashbackWithTooltip());
            if (Flashback.RECORDER.isPaused()) {
                y = nextHeight.getAsInt();
                this.addRenderableWidget((GuiEventListener)new FlashbackButton(x, y, 20, 20, (Component)Component.translatable((String)"flashback.recording_controls.unpause"), btn -> {
                    Flashback.pauseRecordingReplay(false);
                    Minecraft.getInstance().setScreen(null);
                }, Identifier.fromNamespaceAndPath((String)"flashback", (String)"icon_pixelated_start.png")).flashbackWithTooltip());
            } else {
                y = nextHeight.getAsInt();
                this.addRenderableWidget((GuiEventListener)new FlashbackButton(x, y, 20, 20, (Component)Component.translatable((String)"flashback.recording_controls.pause"), btn -> {
                    Flashback.pauseRecordingReplay(true);
                    Minecraft.getInstance().setScreen(null);
                }, Identifier.fromNamespaceAndPath((String)"flashback", (String)"icon_pixelated_pause.png")).flashbackWithTooltip());
            }
            y = nextHeight.getAsInt();
            this.addRenderableWidget((GuiEventListener)new FlashbackButton(x, y, 20, 20, (Component)Component.translatable((String)"flashback.recording_controls.cancel"), btn -> Minecraft.getInstance().setScreen((Screen)new ConfirmScreen(value -> {
                if (value) {
                    Flashback.cancelRecordingReplay();
                    Minecraft.getInstance().setScreen(null);
                } else {
                    Minecraft.getInstance().setScreen((Screen)new PauseScreen(true));
                }
            }, (Component)Component.translatable((String)"flashback.confirm_cancel_recording"), (Component)Component.translatable((String)"flashback.confirm_cancel_recording_description"))), Identifier.fromNamespaceAndPath((String)"flashback", (String)"icon_pixelated_cancel.png")).flashbackWithTooltip());
        }
    }

    @WrapOperation(method={"createPauseMenu"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/gui/layouts/FrameLayout;alignInRectangle(Lnet/minecraft/client/gui/layouts/LayoutElement;IIIIFF)V")})
    public void createPauseMenu_alignInRectangle(LayoutElement layoutElement, int i, int j, int k, int l, float f, float g, Operation<Void> original) {
        if (!Flashback.isInReplay() && Flashback.getConfig().recordingControls.controlsLocation == RecordingControlsLocation.BELOW) {
            g += 0.05f;
        }
        original.call(new Object[]{layoutElement, i, j, k, l, Float.valueOf(f), Float.valueOf(g)});
    }
}

