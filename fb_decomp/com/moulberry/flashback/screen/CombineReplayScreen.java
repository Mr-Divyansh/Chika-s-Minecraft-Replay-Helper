/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Lifecycle
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.client.gui.components.StringWidget
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.layouts.FrameLayout
 *  net.minecraft.client.gui.layouts.GridLayout
 *  net.minecraft.client.gui.layouts.GridLayout$RowHelper
 *  net.minecraft.client.gui.layouts.LayoutElement
 *  net.minecraft.client.gui.screens.AlertScreen
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.gui.screens.TitleScreen
 *  net.minecraft.commands.Commands$CommandSelection
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Holder$Reference
 *  net.minecraft.core.MappedRegistry
 *  net.minecraft.core.Registry
 *  net.minecraft.core.RegistryAccess
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.network.chat.CommonComponents
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.WorldLoader
 *  net.minecraft.server.WorldLoader$DataLoadOutput
 *  net.minecraft.server.WorldLoader$InitConfig
 *  net.minecraft.server.WorldLoader$PackConfig
 *  net.minecraft.server.WorldStem
 *  net.minecraft.server.packs.repository.PackRepository
 *  net.minecraft.server.packs.repository.ServerPacksSource
 *  net.minecraft.server.permissions.PermissionSet
 *  net.minecraft.util.Util
 *  net.minecraft.world.Difficulty
 *  net.minecraft.world.flag.FeatureFlags
 *  net.minecraft.world.level.DataPackConfig
 *  net.minecraft.world.level.GameType
 *  net.minecraft.world.level.LevelSettings
 *  net.minecraft.world.level.LevelSettings$DifficultySettings
 *  net.minecraft.world.level.WorldDataConfiguration
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.biome.Biomes
 *  net.minecraft.world.level.chunk.ChunkGenerator
 *  net.minecraft.world.level.dimension.BuiltinDimensionTypes
 *  net.minecraft.world.level.dimension.LevelStem
 *  net.minecraft.world.level.levelgen.WorldDimensions
 *  net.minecraft.world.level.levelgen.WorldDimensions$Complete
 *  net.minecraft.world.level.levelgen.WorldGenSettings
 *  net.minecraft.world.level.levelgen.WorldOptions
 *  net.minecraft.world.level.storage.LevelDataAndDimensions$WorldDataAndGenSettings
 *  net.minecraft.world.level.storage.PrimaryLevelData
 *  net.minecraft.world.level.storage.WorldData
 *  org.jetbrains.annotations.Nullable
 */
package com.moulberry.flashback.screen;

import com.mojang.serialization.Lifecycle;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.exporting.AsyncFileDialogs;
import com.moulberry.flashback.io.ReplayCombiner;
import com.moulberry.flashback.playback.EmptyLevelSource;
import com.moulberry.flashback.screen.BottomTextWidget;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.screens.AlertScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.commands.Commands;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.WorldLoader;
import net.minecraft.server.WorldStem;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.ServerPacksSource;
import net.minecraft.server.permissions.PermissionSet;
import net.minecraft.util.Util;
import net.minecraft.world.Difficulty;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.WorldDataConfiguration;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.WorldDimensions;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.storage.LevelDataAndDimensions;
import net.minecraft.world.level.storage.PrimaryLevelData;
import net.minecraft.world.level.storage.WorldData;
import org.jetbrains.annotations.Nullable;

public class CombineReplayScreen
extends Screen {
    @Nullable
    private final Screen lastScreen;
    private String newReplayName = "Combined Replay";
    private Path firstReplay;
    private Path secondReplay;
    private Path output;
    private Button firstReplayButton;
    private Button secondReplayButton;
    private Button outputButton;

    protected CombineReplayScreen(@Nullable Screen lastScreen, @Nullable Path firstReplay, @Nullable Path secondReplay, @Nullable Path output) {
        super((Component)Component.translatable((String)"flashback.combine_replay"));
        this.lastScreen = lastScreen;
        this.firstReplay = firstReplay;
        this.secondReplay = secondReplay;
        this.output = output;
    }

    protected void setInitialFocus() {
    }

    protected void init() {
        super.init();
        GridLayout gridLayout = new GridLayout();
        gridLayout.defaultCellSetting().padding(4, 4, 4, 0);
        GridLayout.RowHelper rowHelper = gridLayout.createRowHelper(2);
        rowHelper.addChild((LayoutElement)new StringWidget(204, 20, (Component)Component.translatable((String)"flashback.combine_replay"), this.font), 2);
        rowHelper.addChild((LayoutElement)new BottomTextWidget(204, 10, (Component)Component.translatable((String)"flashback.combine_replay.new_replay_name"), this.font).alignLeft(), 2);
        EditBox replayNameEditBox = new EditBox(this.font, 0, 0, 204, 20, (Component)Component.literal((String)this.newReplayName));
        replayNameEditBox.setMaxLength(128);
        replayNameEditBox.setValue(this.newReplayName);
        replayNameEditBox.setResponder(string -> {
            this.newReplayName = string;
        });
        rowHelper.addChild((LayoutElement)replayNameEditBox, 2);
        Path replayFolder = Flashback.getReplayFolder();
        rowHelper.addChild((LayoutElement)new BottomTextWidget(204, 10, (Component)Component.translatable((String)"flashback.combine_replay.source_n", (Object[])new Object[]{Component.literal((String)"1")}), this.font).alignLeft(), 2);
        String firstReplayPath = this.firstReplay == null ? "" : this.firstReplay.toString();
        this.firstReplayButton = Button.builder((Component)Component.literal((String)firstReplayPath), button -> {
            CompletableFuture<String> future = AsyncFileDialogs.openFileDialog(replayFolder.toString(), "Replay Archive", "zip");
            future.thenAccept(pathStr -> {
                if (pathStr != null) {
                    this.firstReplay = Path.of(pathStr, new String[0]);
                    this.firstReplayButton.setMessage((Component)Component.literal((String)this.firstReplay.toString()));
                }
            });
        }).width(204).build();
        rowHelper.addChild((LayoutElement)this.firstReplayButton, 2);
        rowHelper.addChild((LayoutElement)new BottomTextWidget(204, 10, (Component)Component.translatable((String)"flashback.combine_replay.source_n", (Object[])new Object[]{Component.literal((String)"2")}), this.font).alignLeft(), 2);
        String secondReplayPath = this.secondReplay == null ? "" : this.secondReplay.toString();
        this.secondReplayButton = Button.builder((Component)Component.literal((String)secondReplayPath), button -> {
            CompletableFuture<String> future = AsyncFileDialogs.openFileDialog(replayFolder.toString(), "Replay Archive", "zip");
            future.thenAccept(pathStr -> {
                if (pathStr != null) {
                    this.secondReplay = Path.of(pathStr, new String[0]);
                    this.secondReplayButton.setMessage((Component)Component.literal((String)this.secondReplay.toString()));
                }
            });
        }).width(204).build();
        rowHelper.addChild((LayoutElement)this.secondReplayButton, 2);
        rowHelper.addChild((LayoutElement)new BottomTextWidget(204, 10, (Component)Component.translatable((String)"flashback.combine_replay.output"), this.font).alignLeft(), 2);
        String outputPath = this.output == null ? "" : this.output.toString();
        this.outputButton = Button.builder((Component)Component.literal((String)outputPath), button -> {
            CompletableFuture<String> future = AsyncFileDialogs.saveFileDialog(replayFolder.toString(), "combined.zip", "Replay Archive", "zip");
            future.thenAccept(pathStr -> {
                if (pathStr != null) {
                    this.output = Path.of(pathStr, new String[0]);
                    this.outputButton.setMessage((Component)Component.literal((String)this.output.toString()));
                }
            });
        }).width(204).build();
        rowHelper.addChild((LayoutElement)this.outputButton, 2);
        rowHelper.addChild((LayoutElement)new BottomTextWidget(204, 10, (Component)Component.literal((String)""), this.font), 2);
        rowHelper.addChild((LayoutElement)Button.builder((Component)Component.translatable((String)"flashback.combine_replay.do_combine"), button -> {
            try {
                PackRepository packRepository = ServerPacksSource.createVanillaTrustedRepository();
                packRepository.reload();
                WorldDataConfiguration worldDataConfiguration = new WorldDataConfiguration(new DataPackConfig(List.of(), List.of()), FeatureFlags.DEFAULT_FLAGS);
                LevelSettings levelSettings = new LevelSettings("Replay", GameType.SPECTATOR, new LevelSettings.DifficultySettings(Difficulty.NORMAL, false, true), true, worldDataConfiguration);
                WorldLoader.PackConfig packConfig = new WorldLoader.PackConfig(packRepository, worldDataConfiguration, false, true);
                WorldLoader.InitConfig initConfig = new WorldLoader.InitConfig(packConfig, Commands.CommandSelection.DEDICATED, PermissionSet.ALL_PERMISSIONS);
                WorldStem worldStem = (WorldStem)Util.blockUntilDone(executor -> WorldLoader.load((WorldLoader.InitConfig)initConfig, dataLoadContext -> {
                    Registry registry = new MappedRegistry(Registries.LEVEL_STEM, Lifecycle.stable()).freeze();
                    Holder.Reference plains = (Holder.Reference)dataLoadContext.datapackWorldgen().lookupOrThrow(Registries.BIOME).get(Biomes.PLAINS).get();
                    Holder.Reference overworld = (Holder.Reference)dataLoadContext.datapackWorldgen().lookupOrThrow(Registries.DIMENSION_TYPE).get(BuiltinDimensionTypes.OVERWORLD).get();
                    WorldDimensions worldDimensions = new WorldDimensions(Map.of(LevelStem.OVERWORLD, new LevelStem((Holder)overworld, (ChunkGenerator)new EmptyLevelSource((Holder.Reference<Biome>)plains))));
                    WorldDimensions.Complete complete = worldDimensions.bake(registry);
                    return new WorldLoader.DataLoadOutput((Object)new LevelDataAndDimensions.WorldDataAndGenSettings((WorldData)new PrimaryLevelData(levelSettings, complete.specialWorldProperty(), complete.lifecycle()), new WorldGenSettings(new WorldOptions(0L, false, false), worldDimensions)), complete.dimensionsRegistryAccess());
                }, WorldStem::new, (Executor)Util.backgroundExecutor(), (Executor)executor)).get();
                ReplayCombiner.combine((RegistryAccess)worldStem.registries().compositeAccess(), this.newReplayName, this.firstReplay, this.secondReplay, this.output);
                Minecraft.getInstance().setScreen((Screen)new TitleScreen());
                worldStem.close();
            }
            catch (Exception e) {
                Flashback.LOGGER.error("Error combining replays", (Throwable)e);
                Minecraft.getInstance().setScreen((Screen)new AlertScreen(() -> Minecraft.getInstance().setScreen(this.lastScreen), (Component)Component.translatable((String)"flashback.combine_replay.error"), (Component)Component.literal((String)e.getMessage())));
            }
        }).width(98).build(), 1);
        rowHelper.addChild((LayoutElement)Button.builder((Component)CommonComponents.GUI_CANCEL, button -> Minecraft.getInstance().setScreen(this.lastScreen)).width(98).build(), 1);
        gridLayout.arrangeElements();
        FrameLayout.alignInRectangle((LayoutElement)gridLayout, (int)0, (int)0, (int)this.width, (int)this.height, (float)0.5f, (float)0.5f);
        CombineReplayScreen combineReplayScreen = this;
        gridLayout.visitWidgets(x$0 -> combineReplayScreen.addRenderableWidget((GuiEventListener)x$0));
        this.setInitialFocus((GuiEventListener)replayNameEditBox);
    }

    public void onClose() {
        this.minecraft.setScreen(this.lastScreen);
    }
}

