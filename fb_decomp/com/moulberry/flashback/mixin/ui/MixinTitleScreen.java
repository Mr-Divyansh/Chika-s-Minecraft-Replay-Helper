/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.components.Renderable
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.screens.AlertScreen
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.gui.screens.TitleScreen
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  org.apache.commons.lang3.StringUtils
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.moulberry.flashback.mixin.ui;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.screen.FlashbackButton;
import com.moulberry.flashback.screen.select_replay.SelectReplayScreen;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.AlertScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={TitleScreen.class}, priority=1300)
public class MixinTitleScreen
extends Screen {
    @Unique
    private final List<AbstractWidget> normalMenuWidgets = new ArrayList<AbstractWidget>();
    @Unique
    private AbstractWidget openSelectReplayScreenButton = null;
    @Unique
    private boolean checkedPositionOfReplayButton = false;

    private MixinTitleScreen() {
        super(null);
    }

    @Inject(method={"init"}, at={@At(value="RETURN")})
    public void init(CallbackInfo ci) {
        this.createOrPositionOpenSelectReplayScreenButton(true);
        this.checkedPositionOfReplayButton = false;
    }

    @Inject(method={"tick"}, at={@At(value="RETURN")})
    public void tick(CallbackInfo ci) {
        if (!this.checkedPositionOfReplayButton) {
            this.checkedPositionOfReplayButton = true;
            this.createOrPositionOpenSelectReplayScreenButton(false);
        }
    }

    @Unique
    private void createOrPositionOpenSelectReplayScreenButton(boolean allowCreating) {
        if (this.openSelectReplayScreenButton != null && !this.renderables.contains(this.openSelectReplayScreenButton)) {
            this.openSelectReplayScreenButton = null;
        }
        if (!allowCreating && this.openSelectReplayScreenButton == null) {
            return;
        }
        if (this.openSelectReplayScreenButton != null) {
            int x = this.openSelectReplayScreenButton.getX();
            int y = this.openSelectReplayScreenButton.getY();
            int width = this.openSelectReplayScreenButton.getWidth();
            int height = this.openSelectReplayScreenButton.getHeight();
            boolean overlapsWithExistingButton = false;
            for (Renderable renderable : this.renderables) {
                AbstractWidget otherWidget;
                if (renderable == this.openSelectReplayScreenButton || !(renderable instanceof AbstractWidget) || x >= (otherWidget = (AbstractWidget)renderable).getRight() || x + width <= otherWidget.getX() || y >= otherWidget.getBottom() || y + height <= otherWidget.getY()) continue;
                overlapsWithExistingButton = true;
                break;
            }
            if (!overlapsWithExistingButton) {
                return;
            }
        }
        for (int offsetX = 0; offsetX < 5; ++offsetX) {
            for (int i = this.normalMenuWidgets.size() - 1; i >= 0; --i) {
                AbstractWidget widget = this.normalMenuWidgets.get(i);
                if (!this.renderables.contains(widget)) continue;
                int size = widget.getHeight();
                int x = widget.getRight() + 4 + size * offsetX;
                int y = widget.getY();
                boolean overlapsWithExistingButton = false;
                for (Renderable renderable : this.renderables) {
                    AbstractWidget otherWidget;
                    if (renderable == this.openSelectReplayScreenButton || !(renderable instanceof AbstractWidget) || x >= (otherWidget = (AbstractWidget)renderable).getRight() || x + size <= otherWidget.getX() || y >= otherWidget.getBottom() || y + size <= otherWidget.getY()) continue;
                    overlapsWithExistingButton = true;
                    break;
                }
                if (overlapsWithExistingButton) continue;
                if (this.openSelectReplayScreenButton == null) {
                    this.openSelectReplayScreenButton = new FlashbackButton(x, y, size, size, (Component)Component.translatable((String)"flashback.open_replays"), button -> {
                        List<Object> incompatibleMods;
                        List<Object> list = incompatibleMods = this.minecraft.hasShiftDown() ? List.of() : Flashback.getReplayIncompatibleMods();
                        if (incompatibleMods.isEmpty()) {
                            this.minecraft.setScreen((Screen)new SelectReplayScreen(this));
                        } else {
                            String mods = StringUtils.join(incompatibleMods, (String)", ");
                            MutableComponent description = Component.translatable((String)"flashback.incompatible_with_viewing_description").append((Component)Component.literal((String)mods).withStyle(ChatFormatting.RED));
                            this.minecraft.setScreen((Screen)new AlertScreen(() -> Minecraft.getInstance().setScreen((Screen)this), (Component)Component.translatable((String)"flashback.incompatible_with_viewing"), (Component)description));
                        }
                    });
                    this.addRenderableWidget((GuiEventListener)this.openSelectReplayScreenButton);
                } else {
                    this.openSelectReplayScreenButton.setPosition(x, y);
                }
                return;
            }
        }
    }

    @WrapOperation(method={"createNormalMenuOptions"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/gui/screens/TitleScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;")})
    public GuiEventListener addNormalMenuOption(TitleScreen instance, GuiEventListener guiEventListener, Operation<GuiEventListener> original) {
        GuiEventListener option = (GuiEventListener)original.call(new Object[]{instance, guiEventListener});
        if (option instanceof AbstractWidget) {
            AbstractWidget widget = (AbstractWidget)option;
            this.normalMenuWidgets.add(widget);
        }
        return option;
    }

    @Inject(method={"createNormalMenuOptions"}, at={@At(value="HEAD")})
    public void createNormalMenuOptions(int i, int j, CallbackInfoReturnable<Integer> cir) {
        this.normalMenuWidgets.clear();
    }
}

