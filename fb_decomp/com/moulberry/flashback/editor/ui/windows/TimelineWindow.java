/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntCollection
 *  it.unimi.dsi.fastutil.ints.IntComparators
 *  it.unimi.dsi.fastutil.ints.IntIterator
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.commands.arguments.EntityAnchorArgument$Anchor
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Vector2f
 *  org.joml.Vector3d
 *  org.joml.Vector3fc
 */
package com.moulberry.flashback.editor.ui.windows;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.FlashbackGson;
import com.moulberry.flashback.Utils;
import com.moulberry.flashback.editor.CopiedKeyframes;
import com.moulberry.flashback.editor.SavedTrack;
import com.moulberry.flashback.editor.SelectedKeyframes;
import com.moulberry.flashback.editor.keybinds.Keybinds;
import com.moulberry.flashback.editor.ui.ImGuiHelper;
import com.moulberry.flashback.editor.ui.KeyframeRelativeOffsets;
import com.moulberry.flashback.editor.ui.ReplayUI;
import com.moulberry.flashback.keyframe.Keyframe;
import com.moulberry.flashback.keyframe.KeyframeRegistry;
import com.moulberry.flashback.keyframe.KeyframeType;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.handler.MinecraftKeyframeHandler;
import com.moulberry.flashback.keyframe.impl.CameraKeyframe;
import com.moulberry.flashback.keyframe.impl.CameraOrbitKeyframe;
import com.moulberry.flashback.keyframe.impl.TimelapseKeyframe;
import com.moulberry.flashback.keyframe.interpolation.InterpolationType;
import com.moulberry.flashback.keyframe.types.CameraKeyframeType;
import com.moulberry.flashback.keyframe.types.TimelapseKeyframeType;
import com.moulberry.flashback.playback.ReplayServer;
import com.moulberry.flashback.record.FlashbackMeta;
import com.moulberry.flashback.record.ReplayMarker;
import com.moulberry.flashback.state.EditorScene;
import com.moulberry.flashback.state.EditorSceneHistoryAction;
import com.moulberry.flashback.state.EditorSceneHistoryEntry;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import com.moulberry.flashback.state.KeyframeTrack;
import com.moulberry.flashback.visuals.ReplayVisuals;
import imgui.moulberry90.ImDrawList;
import imgui.moulberry90.ImGui;
import imgui.moulberry90.ImVec4;
import imgui.moulberry90.type.ImString;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2f;
import org.joml.Vector3d;
import org.joml.Vector3fc;

public class TimelineWindow {
    private static EditorState editorState;
    private static long editorSceneStamp;
    private static boolean editorSceneStampIsWrite;
    private static EditorScene editorScene;
    private static double zoomMinBeforeDrag;
    private static double zoomMaxBeforeDrag;
    private static boolean grabbedZoomBar;
    private static boolean grabbedZoomBarResizeLeft;
    private static boolean grabbedZoomBarResizeRight;
    private static boolean grabbedExportBarResizeLeft;
    private static boolean grabbedExportBarResizeRight;
    private static boolean grabbedPlayback;
    private static boolean grabbedKeyframe;
    private static float grabbedKeyframeMouseX;
    private static boolean enableKeyframeMovement;
    private static int grabbedKeyframeTick;
    private static int grabbedKeyframeTrack;
    private static int draggingMouseButton;
    private static int repositioningKeyframeTrack;
    private static float dragStartMouseX;
    private static float dragStartMouseY;
    private static boolean trackDisabledButtonDrag;
    private static boolean trackDisabledButtonDragValue;
    @Nullable
    private static Vector2f dragSelectOrigin;
    private static KeyframeType.KeyframeCreatePopup<?> createKeyframeWithPopup;
    private static int createKeyframeWithPopupTick;
    private static ImString sceneNameString;
    private static boolean copyRelativeToPosition;
    private static boolean copyRelativeToYaw;
    private static boolean copyRelativeToPitch;
    private static float mouseX;
    private static float mouseY;
    private static int timelineOffset;
    private static int minTicks;
    private static float availableTicks;
    private static float timelineWidth;
    private static float x;
    private static float y;
    private static float width;
    private static float height;
    private static int pendingStepBackwardsTicks;
    private static int cursorTicks;
    private static long lastRenderNanos;
    private static long renderDeltaNanos;
    private static boolean hoveredControls;
    private static boolean hoveredSkipBackwards;
    private static boolean hoveredSlowDown;
    private static boolean hoveredPause;
    private static boolean hoveredFastForwards;
    private static boolean hoveredSkipForwards;
    private static boolean zoomBarHovered;
    private static int zoomBarHeight;
    private static float zoomBarMin;
    private static float zoomBarMax;
    private static boolean zoomBarExpanded;
    private static int minorSeparatorHeight;
    private static int majorSeparatorHeight;
    private static int timestampHeight;
    private static int middleY;
    private static int middleX;
    private static int keyframeSize;
    private static final List<SelectedKeyframes> selectedKeyframesList;
    private static int editingKeyframeTrack;
    private static int editingKeyframeTick;
    private static int createKeyframeAtTick;
    private static int openCreateKeyframeAtTickTrack;
    private static final float[] replayTickSpeeds;
    private static KeyframeTrack lastKeyframeTrackCameraWasAddedTo;

    public static int getCursorTick() {
        return cursorTicks;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void render() {
        ReplayServer replayServer = Flashback.getReplayServer();
        if (replayServer == null) {
            return;
        }
        String timestamp = TimelineWindow.ticksToTimestamp(cursorTicks);
        boolean hoveredBody = mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
        ImGuiHelper.pushStyleVar(2, 0.0f, 0.0f);
        int flags = 24;
        if (hoveredBody) {
            flags |= 4;
        }
        String timelineTitle = I18n.get((String)"flashback.timeline_window", (Object[])new Object[]{timestamp, cursorTicks});
        boolean timelineVisible = ImGui.begin(timelineTitle + "###Timeline", flags);
        ImGuiHelper.popStyleVar();
        cursorTicks = replayServer.getReplayTick();
        if (timelineVisible) {
            FlashbackMeta metadata = replayServer.getMetadata();
            editorState = EditorStateManager.get(metadata.replayIdentifier);
            editorSceneStamp = editorState.acquireRead();
            editorSceneStampIsWrite = false;
            try {
                editorScene = editorState.getCurrentScene(editorSceneStamp);
                TimelineWindow.renderInner(replayServer, metadata);
            }
            finally {
                editorState.release(editorSceneStamp);
                editorSceneStamp = 0L;
                editorSceneStampIsWrite = false;
                editorScene = null;
            }
        }
        ImGui.end();
    }

    private static void renderInner(ReplayServer replayServer, FlashbackMeta metadata) {
        boolean shouldProcessInput;
        Map.Entry<Integer, ReplayMarker> markerEntry;
        boolean showSubSeconds;
        int ticksPerMinor;
        int minorsPerMajor;
        int targetMajorSize;
        boolean showTrackScroll;
        ImDrawList drawList = ImGui.getWindowDrawList();
        float maxX = ImGui.getWindowContentRegionMaxX();
        float maxY = ImGui.getWindowContentRegionMaxY();
        float minX = ImGui.getWindowContentRegionMinX();
        float minY = ImGui.getWindowContentRegionMinY();
        x = ImGui.getWindowPosX() + minX;
        y = ImGui.getWindowPosY() + minY;
        width = maxX - minX;
        height = maxY - minY;
        minorSeparatorHeight = ReplayUI.scaleUi(10);
        majorSeparatorHeight = minorSeparatorHeight * 2;
        timestampHeight = ReplayUI.scaleUi(20);
        middleY = timestampHeight + majorSeparatorHeight;
        middleX = ReplayUI.scaleUi(240);
        keyframeSize = ReplayUI.scaleUi(10);
        float totalTrackHeight = (float)(TimelineWindow.editorScene.keyframeTracks.size() + 1) * (ImGui.getTextLineHeightWithSpacing() + ImGui.getStyle().getItemSpacingY());
        boolean bl = showTrackScroll = totalTrackHeight > height - (float)middleY;
        if (showTrackScroll) {
            width -= ImGui.getStyle().getScrollbarSize() - 1.0f;
        }
        if (width < 1.0f || height < 1.0f) {
            return;
        }
        selectedKeyframesList.removeIf(k -> !k.checkValid(editorScene));
        if (editingKeyframeTrack >= 0 && editingKeyframeTick >= 0) {
            for (SelectedKeyframes selectedKeyframes : selectedKeyframesList) {
                if (selectedKeyframes.trackIndex() != editingKeyframeTrack) continue;
                if (selectedKeyframes.keyframeTicks().contains(editingKeyframeTick)) break;
                editingKeyframeTrack = -1;
                editingKeyframeTick = -1;
                break;
            }
        }
        long currentTime = System.nanoTime();
        renderDeltaNanos = Math.max(0L, Math.min(1000000000L, currentTime - lastRenderNanos));
        lastRenderNanos = currentTime;
        mouseX = ReplayUI.getIO().getMousePosX();
        mouseY = ReplayUI.getIO().getMousePosY();
        int currentReplayTick = replayServer.getReplayTick();
        int totalTicks = replayServer.getTotalReplayTicks();
        timelineWidth = width - (float)middleX;
        float shownTicks = Math.round((TimelineWindow.editorState.zoomMax - TimelineWindow.editorState.zoomMin) * (double)totalTicks);
        if (currentReplayTick + (int)shownTicks > 72000) {
            int numHours = (currentReplayTick + (int)shownTicks) / 72000;
            targetMajorSize = (int)ImGuiHelper.calcTextWidth("9".repeat((int)Math.log10(numHours) + 1) + ":99:99") + 20;
        } else {
            targetMajorSize = (int)ImGuiHelper.calcTextWidth("99:99") + 20;
        }
        float targetTicksPerMajor = 1.0f / (timelineWidth / shownTicks / (float)targetMajorSize);
        if (targetTicksPerMajor < 5.0f) {
            minorsPerMajor = 5;
            ticksPerMinor = 1;
            showSubSeconds = true;
        } else if (targetTicksPerMajor < 8.0f) {
            minorsPerMajor = 5;
            ticksPerMinor = 2;
            showSubSeconds = true;
        } else {
            minorsPerMajor = 4;
            ticksPerMinor = (int)Math.ceil(targetTicksPerMajor / 20.0f) * 20 / minorsPerMajor;
            showSubSeconds = false;
        }
        int majorSnap = ticksPerMinor * minorsPerMajor;
        minTicks = (int)Math.round(TimelineWindow.editorState.zoomMin * (double)totalTicks / (double)majorSnap) * majorSnap;
        float minorSeparatorWidth = timelineWidth / shownTicks * (float)ticksPerMinor;
        availableTicks = timelineWidth / minorSeparatorWidth * (float)ticksPerMinor;
        double errorTicks = TimelineWindow.editorState.zoomMin * (double)totalTicks - (double)minTicks;
        int errorOffset = (int)(-errorTicks / (double)ticksPerMinor * (double)minorSeparatorWidth);
        timelineOffset = middleX + errorOffset;
        cursorTicks = currentReplayTick;
        if (grabbedPlayback) {
            cursorTicks = TimelineWindow.timelineXToReplayTick(mouseX - x);
        } else if (replayServer.jumpToTick >= 0) {
            cursorTicks = replayServer.jumpToTick;
        } else if (pendingStepBackwardsTicks > 0) {
            cursorTicks = Math.max(0, cursorTicks - pendingStepBackwardsTicks);
        }
        if (grabbedPlayback && !TimelineWindow.editorScene.keyframeTracks.isEmpty()) {
            int closestTick;
            boolean isShiftDown;
            boolean isCtrlDown = ImGui.isKeyDown(341) || ImGui.isKeyDown(345);
            boolean bl2 = isShiftDown = ImGui.isKeyDown(340) || ImGui.isKeyDown(344);
            if (isShiftDown && (closestTick = TimelineWindow.findClosestKeyframeForSnap(cursorTicks)) != -1) {
                cursorTicks = closestTick;
            }
            if (isCtrlDown) {
                editorState.applyKeyframes(new MinecraftKeyframeHandler(Minecraft.getInstance()), cursorTicks, editorSceneStamp);
            }
            if (!isCtrlDown && !isShiftDown) {
                ImGuiHelper.drawTooltip(I18n.get((String)"flashback.hold_ctrl_to_apply_keyframes", (Object[])new Object[0]));
                ImGuiHelper.drawTooltip(I18n.get((String)"flashback.hold_shift_to_snap_to_keyframes", (Object[])new Object[0]));
            }
        }
        int cursorX = TimelineWindow.replayTickToTimelineX(cursorTicks);
        zoomBarHeight = 6;
        float zoomBarWidth = width - (float)(middleX + 1);
        zoomBarMin = (float)((double)(x + (float)middleX + 1.0f) + TimelineWindow.editorState.zoomMin * (double)zoomBarWidth);
        zoomBarMax = (float)((double)(x + (float)middleX + 1.0f) + TimelineWindow.editorState.zoomMax * (double)zoomBarWidth);
        zoomBarExpanded = false;
        if (mouseY >= y + height - (float)(zoomBarHeight * 2) && mouseY <= y + height || grabbedZoomBar) {
            zoomBarHeight *= 2;
            zoomBarExpanded = true;
        }
        zoomBarHovered = mouseY >= y + height - (float)zoomBarHeight && mouseY <= y + height && mouseX >= zoomBarMin && mouseX <= zoomBarMax;
        drawList.addLine(x + (float)middleX, y + (float)timestampHeight, x + (float)middleX, y + height, -1);
        drawList.addLine(x, y + (float)middleY, x + width - 2.0f, y + (float)middleY, -1);
        ImGui.dummy(0.0f, middleY - 1);
        int timelineContentsFlags = 16;
        timelineContentsFlags = showTrackScroll ? (timelineContentsFlags |= 0x4000) : (timelineContentsFlags |= 8);
        ImGui.beginChild("##TimelineContents", 0.0f, 0.0f, false, timelineContentsFlags);
        ImDrawList childDrawList = ImGui.getWindowDrawList();
        float contentY = y + (float)middleY - ImGui.getScrollY();
        TimelineWindow.renderKeyframeElements(x, contentY, cursorTicks, middleX);
        childDrawList.pushClipRect(x + (float)middleX + 1.0f, y + (float)middleY, x + width, y + height);
        TimelineWindow.renderKeyframes(x, contentY, mouseX, minTicks, availableTicks, totalTicks);
        childDrawList.popClipRect();
        ImGui.endChild();
        drawList.pushClipRect(x + (float)middleX, y, x + width, y + height);
        TimelineWindow.renderExportBar(drawList);
        TimelineWindow.renderSeparators(minorsPerMajor, x, middleX, minorSeparatorWidth, errorOffset, width, drawList, y, timestampHeight, middleY, minTicks, ticksPerMinor, showSubSeconds, majorSeparatorHeight, minorSeparatorHeight);
        ReplayMarker hoveredMarker = null;
        int hoveredMarkerTick = currentReplayTick;
        int tick = minTicks - 10;
        while ((float)tick <= (float)minTicks + availableTicks + 10.0f && (markerEntry = metadata.replayMarkers.ceilingEntry(tick)) != null && !((float)markerEntry.getKey().intValue() > (float)minTicks + availableTicks + 10.0f)) {
            int markerTick = markerEntry.getKey();
            ReplayMarker marker = markerEntry.getValue();
            float markerX = x + (float)TimelineWindow.replayTickToTimelineX(markerTick);
            int colour = marker.colour();
            colour = colour >> 16 & 0xFF | colour & 0xFF00 | colour << 16 & 0xFF0000 | 0xFF000000;
            drawList.addCircleFilled(markerX, y + (float)middleY, (float)ReplayUI.scaleUi(5), colour);
            if (hoveredMarker == null && Math.abs(markerX - mouseX) <= 5.0f && Math.abs(y + (float)middleY - mouseY) <= 5.0f) {
                if (!ImGui.isAnyMouseDown() && marker.description() != null) {
                    ImGuiHelper.drawTooltip(marker.description());
                }
                hoveredMarker = marker;
                hoveredMarkerTick = markerTick;
            }
            tick = markerTick + 1;
            ++tick;
        }
        TimelineWindow.renderPlaybackHead(cursorX, x, middleX, width, cursorTicks, currentReplayTick, drawList, y, middleY, timestampHeight, height, zoomBarHeight);
        if (dragSelectOrigin != null) {
            drawList.pushClipRect(x + (float)middleX, y + (float)middleY, x + width, y + height);
            drawList.addRectFilled(Math.min(mouseX, TimelineWindow.dragSelectOrigin.x), Math.min(mouseY, TimelineWindow.dragSelectOrigin.y), Math.max(mouseX, TimelineWindow.dragSelectOrigin.x), Math.max(mouseY, TimelineWindow.dragSelectOrigin.y), -2132975616);
            drawList.addRect(Math.min(mouseX, TimelineWindow.dragSelectOrigin.x), Math.min(mouseY, TimelineWindow.dragSelectOrigin.y), Math.max(mouseX, TimelineWindow.dragSelectOrigin.x), Math.max(mouseY, TimelineWindow.dragSelectOrigin.y), -2269184);
            drawList.popClipRect();
        }
        drawList.popClipRect();
        if (TimelineWindow.editorState.zoomMax >= 1.0) {
            drawList.addLine(x + width - 2.0f, y + (float)timestampHeight, x + width - 2.0f, y + height - (float)zoomBarHeight, -1);
        }
        if (zoomBarExpanded) {
            drawList.addRectFilled(x + (float)middleX + 1.0f, y + height - (float)zoomBarHeight, x + width, y + height, -12566464, zoomBarHeight);
        }
        if (zoomBarHovered || grabbedZoomBar) {
            drawList.addRectFilled(zoomBarMin + (float)zoomBarHeight / 2.0f, y + height - (float)zoomBarHeight, zoomBarMax - (float)zoomBarHeight / 2.0f, y + height, -1, zoomBarHeight);
            drawList.addCircleFilled(zoomBarMin + (float)zoomBarHeight / 2.0f, y + height - (float)zoomBarHeight / 2.0f, (float)zoomBarHeight / 2.0f, -5592576);
            drawList.addCircleFilled(zoomBarMax - (float)zoomBarHeight / 2.0f, y + height - (float)zoomBarHeight / 2.0f, (float)zoomBarHeight / 2.0f, -5592576);
        } else {
            drawList.addRectFilled(zoomBarMin, y + height - (float)zoomBarHeight, zoomBarMax, y + height, -1, zoomBarHeight);
        }
        int controlSize = ReplayUI.scaleUi(24);
        int controlsY = (int)y + middleY / 2 - controlSize / 2;
        float manualTickRate = replayServer.getDesiredTickRate(true);
        int skipBackwardsX = (int)x + middleX / 6 - controlSize / 2;
        drawList.addTriangleFilled((float)skipBackwardsX + (float)controlSize / 3.0f, (float)controlsY + (float)controlSize / 2.0f, skipBackwardsX + controlSize, controlsY, skipBackwardsX + controlSize, controlsY + controlSize, -1);
        drawList.addRectFilled(skipBackwardsX, controlsY, (float)skipBackwardsX + (float)controlSize / 3.0f, (float)(controlsY + controlSize), -1);
        int slowDownX = (int)x + middleX * 2 / 6 - controlSize / 2;
        int slowDownColor = manualTickRate < 20.0f ? -8355585 : -1;
        drawList.addTriangleFilled(slowDownX, (float)controlsY + (float)controlSize / 2.0f, (float)slowDownX + (float)controlSize / 2.0f, controlsY, (float)slowDownX + (float)controlSize / 2.0f, controlsY + controlSize, slowDownColor);
        drawList.addTriangleFilled((float)slowDownX + (float)controlSize / 2.0f, (float)controlsY + (float)controlSize / 2.0f, slowDownX + controlSize, controlsY, slowDownX + controlSize, controlsY + controlSize, slowDownColor);
        int pauseX = (int)x + middleX / 2 - controlSize / 2;
        if (replayServer.replayPaused) {
            drawList.addTriangleFilled((float)pauseX + (float)controlSize / 12.0f, controlsY, pauseX + controlSize, (float)controlsY + (float)controlSize / 2.0f, (float)pauseX + (float)controlSize / 12.0f, controlsY + controlSize, -1);
        } else {
            drawList.addRectFilled(pauseX, controlsY, (float)pauseX + (float)controlSize / 3.0f, (float)(controlsY + controlSize), -1);
            drawList.addRectFilled((float)pauseX + (float)controlSize * 2.0f / 3.0f, controlsY, (float)(pauseX + controlSize), (float)(controlsY + controlSize), -1);
        }
        int fastForwardsX = (int)x + middleX * 4 / 6 - controlSize / 2;
        int fastForwardsColor = manualTickRate > 20.0f ? -8323200 : -1;
        drawList.addTriangleFilled(fastForwardsX, controlsY, (float)fastForwardsX + (float)controlSize / 2.0f, (float)controlsY + (float)controlSize / 2.0f, fastForwardsX, controlsY + controlSize, fastForwardsColor);
        drawList.addTriangleFilled((float)fastForwardsX + (float)controlSize / 2.0f, controlsY, fastForwardsX + controlSize, (float)controlsY + (float)controlSize / 2.0f, (float)fastForwardsX + (float)controlSize / 2.0f, controlsY + controlSize, fastForwardsColor);
        int skipForwardsX = (int)x + middleX * 5 / 6 - controlSize / 2;
        drawList.addTriangleFilled(skipForwardsX, controlsY, (float)skipForwardsX + (float)controlSize * 2.0f / 3.0f, (float)controlsY + (float)controlSize / 2.0f, skipForwardsX, controlsY + controlSize, -1);
        drawList.addRectFilled((float)skipForwardsX + (float)controlSize * 2.0f / 3.0f, controlsY, (float)(skipForwardsX + controlSize), (float)(controlsY + controlSize), -1);
        hoveredControls = mouseY > (float)controlsY && mouseY < (float)(controlsY + controlSize);
        hoveredSkipBackwards = hoveredControls && mouseX >= (float)skipBackwardsX && mouseX <= (float)(skipBackwardsX + controlSize);
        hoveredSlowDown = hoveredControls && mouseX >= (float)slowDownX && mouseX <= (float)(slowDownX + controlSize);
        hoveredPause = hoveredControls && mouseX >= (float)pauseX && mouseX <= (float)(pauseX + controlSize);
        hoveredFastForwards = hoveredControls && mouseX >= (float)fastForwardsX && mouseX <= (float)(fastForwardsX + controlSize);
        hoveredSkipForwards = hoveredControls && mouseX >= (float)skipForwardsX && mouseX <= (float)(skipForwardsX + controlSize);
        float currentTickRate = replayServer.getDesiredTickRate(true);
        if (!grabbedPlayback) {
            if (hoveredSkipBackwards) {
                ImGuiHelper.drawTooltip(I18n.get((String)"flashback.skip_backwards", (Object[])new Object[0]));
            } else if (hoveredSlowDown) {
                ImGuiHelper.drawTooltip(I18n.get((String)"flashback.slow_down_with_current_speed", (Object[])new Object[]{Float.valueOf(currentTickRate / 20.0f)}));
            } else if (hoveredPause) {
                if (replayServer.replayPaused) {
                    ImGuiHelper.drawTooltip(I18n.get((String)"flashback.start_replay", (Object[])new Object[0]));
                } else {
                    ImGuiHelper.drawTooltip(I18n.get((String)"flashback.pause_replay", (Object[])new Object[0]));
                }
            } else if (hoveredFastForwards) {
                ImGuiHelper.drawTooltip(I18n.get((String)"flashback.fast_forwards_with_current_speed", (Object[])new Object[]{Float.valueOf(currentTickRate / 20.0f)}));
            } else if (hoveredSkipForwards) {
                ImGuiHelper.drawTooltip(I18n.get((String)"flashback.skip_forwards", (Object[])new Object[0]));
            }
        }
        if (ImGuiHelper.beginPopup("##KeyframePopup")) {
            TimelineWindow.renderKeyframeOptionsPopup(totalTicks);
            ImGui.endPopup();
        } else {
            editingKeyframeTrack = -1;
            editingKeyframeTick = -1;
        }
        boolean bl3 = shouldProcessInput = !ImGui.isPopupOpen("", 3072) && !ReplayUI.getIO().getWantTextInput();
        if (shouldProcessInput) {
            int scroll = (int)Math.signum(ReplayUI.getIO().getMouseWheel());
            if (scroll != 0 && mouseX > x + (float)middleX && mouseX < x + width && mouseY > y && mouseY < y + height) {
                if (Keybinds.TIMELINE_ZOOM_SCROLL.areAllModifiersDown()) {
                    double mousePercentage = (mouseX - (x + (float)middleX)) / (width - (float)middleX);
                    if (scroll > 0) {
                        zoomDelta = TimelineWindow.editorState.zoomMax - TimelineWindow.editorState.zoomMin;
                        if (zoomDelta > 0.001) {
                            TimelineWindow.editorState.zoomMin += zoomDelta * 0.05 * mousePercentage;
                            TimelineWindow.editorState.zoomMax -= zoomDelta * 0.05 * (1.0 - mousePercentage);
                            editorState.markDirty();
                        }
                    } else if (scroll < 0) {
                        zoomDelta = TimelineWindow.editorState.zoomMax - TimelineWindow.editorState.zoomMin;
                        TimelineWindow.editorState.zoomMin = Math.max(0.0, TimelineWindow.editorState.zoomMin - zoomDelta * 0.05 / 0.9 * mousePercentage);
                        TimelineWindow.editorState.zoomMax = Math.min(1.0, TimelineWindow.editorState.zoomMax + zoomDelta * 0.05 / 0.9 * (1.0 - mousePercentage));
                        editorState.markDirty();
                    }
                } else if (Keybinds.TIMELINE_MOVE_SCROLL.areAllModifiersDown()) {
                    if (scroll > 0) {
                        if (TimelineWindow.editorState.zoomMax >= 0.99) {
                            TimelineWindow.editorState.zoomMin += 1.0 - TimelineWindow.editorState.zoomMax;
                            TimelineWindow.editorState.zoomMax = 1.0;
                        } else {
                            TimelineWindow.editorState.zoomMin += 0.01;
                            TimelineWindow.editorState.zoomMax += 0.01;
                        }
                    } else if (TimelineWindow.editorState.zoomMin <= 0.01) {
                        TimelineWindow.editorState.zoomMax -= TimelineWindow.editorState.zoomMin;
                        TimelineWindow.editorState.zoomMin = 0.0;
                    } else {
                        TimelineWindow.editorState.zoomMin -= 0.01;
                        TimelineWindow.editorState.zoomMax -= 0.01;
                    }
                }
            }
            TimelineWindow.handleKeyPresses(replayServer, cursorTicks, totalTicks);
            boolean leftClicked = ImGui.isMouseClicked(0);
            boolean rightClicked = ImGui.isMouseClicked(1);
            if (leftClicked && hoveredMarker != null) {
                replayServer.goToReplayTick(hoveredMarkerTick);
                if (hoveredMarker.position() != null) {
                    ReplayMarker.MarkerPosition position = hoveredMarker.position();
                    String dimension = Minecraft.getInstance().level.dimension().toString();
                    if (dimension.equals(position.dimension())) {
                        LocalPlayer player = Minecraft.getInstance().player;
                        Vec3 target = new Vec3((Vector3fc)position.position());
                        Vec3 playerEyes = player.getEyePosition();
                        Vec3 delta = playerEyes.subtract(target);
                        double distance = delta.length();
                        if (distance > 3.0) {
                            Vec3 repositioned = delta.normalize().scale(3.0).add(target).subtract(0.0, (double)player.getEyeHeight(), 0.0);
                            player.snapTo(repositioned);
                        }
                        player.lookAt(EntityAnchorArgument.Anchor.EYES, target);
                        player.setDeltaMovement(Vec3.ZERO);
                    }
                }
            } else if (mouseX < x + width - 2.0f && (leftClicked || rightClicked)) {
                TimelineWindow.handleClick(replayServer, totalTicks, contentY);
                draggingMouseButton = leftClicked ? 0 : 1;
                dragStartMouseX = mouseX;
                dragStartMouseY = mouseY;
            } else if (ImGui.isMouseDragging(draggingMouseButton)) {
                if (repositioningKeyframeTrack >= 0 && repositioningKeyframeTrack < TimelineWindow.editorScene.keyframeTracks.size()) {
                    float mouseDeltaY = mouseY - dragStartMouseY;
                    float lineHeight = ImGui.getTextLineHeightWithSpacing() + ImGui.getStyle().getItemSpacingY();
                    if (mouseDeltaY > lineHeight / 2.0f) {
                        if (repositioningKeyframeTrack < TimelineWindow.editorScene.keyframeTracks.size() - 1) {
                            selectedKeyframesList.clear();
                            editingKeyframeTrack = -1;
                            editingKeyframeTick = -1;
                            dragStartMouseY += lineHeight;
                            TimelineWindow.upgradeToSceneWrite();
                            if (repositioningKeyframeTrack < TimelineWindow.editorScene.keyframeTracks.size() - 1) {
                                track = TimelineWindow.editorScene.keyframeTracks.remove(repositioningKeyframeTrack);
                                TimelineWindow.editorScene.keyframeTracks.get((int)TimelineWindow.repositioningKeyframeTrack).animatedOffsetInUi += lineHeight;
                                TimelineWindow.editorScene.keyframeTracks.add(++repositioningKeyframeTrack, track);
                            }
                        }
                    } else if (mouseDeltaY < -lineHeight / 2.0f && repositioningKeyframeTrack > 0) {
                        selectedKeyframesList.clear();
                        editingKeyframeTrack = -1;
                        editingKeyframeTick = -1;
                        dragStartMouseY -= lineHeight;
                        TimelineWindow.upgradeToSceneWrite();
                        if (repositioningKeyframeTrack > 0) {
                            track = TimelineWindow.editorScene.keyframeTracks.remove(repositioningKeyframeTrack);
                            TimelineWindow.editorScene.keyframeTracks.get((int)(--TimelineWindow.repositioningKeyframeTrack)).animatedOffsetInUi -= lineHeight;
                            TimelineWindow.editorScene.keyframeTracks.add(repositioningKeyframeTrack, track);
                        }
                    }
                }
                if (grabbedExportBarResizeLeft) {
                    int closestTick;
                    ImGui.setMouseCursor(4);
                    int target = TimelineWindow.timelineXToReplayTick(mouseX - x);
                    if ((ImGui.isKeyDown(340) || ImGui.isKeyDown(344)) && (closestTick = TimelineWindow.findClosestKeyframeForSnap(target)) != -1) {
                        target = closestTick;
                    }
                    TimelineWindow.upgradeToSceneWrite();
                    editorScene.setExportTicks(target, -1, totalTicks);
                    editorState.markDirty();
                }
                if (grabbedExportBarResizeRight) {
                    int closestTick;
                    ImGui.setMouseCursor(4);
                    int target = TimelineWindow.timelineXToReplayTick(mouseX - x);
                    if ((ImGui.isKeyDown(340) || ImGui.isKeyDown(344)) && (closestTick = TimelineWindow.findClosestKeyframeForSnap(target)) != -1) {
                        target = closestTick;
                    }
                    TimelineWindow.upgradeToSceneWrite();
                    editorScene.setExportTicks(-1, target, totalTicks);
                    editorState.markDirty();
                }
                if (zoomBarWidth > 1.0f && grabbedZoomBar) {
                    float dx = mouseX - dragStartMouseX;
                    float factor = dx / zoomBarWidth;
                    if (grabbedZoomBarResizeLeft) {
                        ImGui.setMouseCursor(4);
                        TimelineWindow.editorState.zoomMin = Math.max(0.0, Math.min(TimelineWindow.editorState.zoomMax - (double)0.01f, zoomMinBeforeDrag + (double)factor));
                    } else if (grabbedZoomBarResizeRight) {
                        ImGui.setMouseCursor(4);
                        TimelineWindow.editorState.zoomMax = Math.max(TimelineWindow.editorState.zoomMin + (double)0.01f, Math.min(1.0, zoomMaxBeforeDrag + (double)factor));
                    } else {
                        ImGui.setMouseCursor(7);
                        double zoomSize = zoomMaxBeforeDrag - zoomMinBeforeDrag;
                        if (factor < 0.0f) {
                            TimelineWindow.editorState.zoomMin = Math.max(0.0, zoomMinBeforeDrag + (double)factor);
                            TimelineWindow.editorState.zoomMax = TimelineWindow.editorState.zoomMin + zoomSize;
                        } else if (factor > 0.0f) {
                            TimelineWindow.editorState.zoomMax = Math.min(1.0, zoomMaxBeforeDrag + (double)factor);
                            TimelineWindow.editorState.zoomMin = TimelineWindow.editorState.zoomMax - zoomSize;
                        }
                    }
                    editorState.markDirty();
                }
                if (grabbedPlayback) {
                    int closestTick;
                    int desiredTick = TimelineWindow.timelineXToReplayTick(mouseX - x);
                    if ((ImGui.isKeyDown(340) || ImGui.isKeyDown(344)) && (closestTick = TimelineWindow.findClosestKeyframeForSnap(desiredTick)) != -1) {
                        desiredTick = closestTick;
                    }
                    if (desiredTick > currentReplayTick) {
                        replayServer.goToReplayTick(desiredTick);
                    }
                    replayServer.replayPaused = true;
                }
            } else if (zoomBarWidth > 1.0f && mouseY > y + (float)middleY && mouseY < y + height && mouseX > x + (float)middleX && mouseX < x + width && ImGui.isMouseDown(2)) {
                grabbedZoomBar = true;
                draggingMouseButton = 2;
                zoomMinBeforeDrag = TimelineWindow.editorState.zoomMin;
                zoomMaxBeforeDrag = TimelineWindow.editorState.zoomMax;
                dragStartMouseX = mouseX;
                dragStartMouseY = mouseY;
            } else if (!ImGui.isAnyMouseDown()) {
                TimelineWindow.releaseGrabbed(replayServer, totalTicks, contentY);
            }
        } else {
            TimelineWindow.releaseGrabbed(replayServer, totalTicks, contentY);
        }
    }

    private static void upgradeToSceneWrite() {
        if (!editorSceneStampIsWrite) {
            editorState.release(editorSceneStamp);
            editorSceneStamp = editorState.acquireWrite();
            editorSceneStampIsWrite = true;
        }
    }

    private static int findClosestKeyframeForSnap(int tick) {
        int closestTick = -1;
        for (KeyframeTrack track : TimelineWindow.editorScene.keyframeTracks) {
            Integer ceil;
            float ceilX;
            Integer floor = track.keyframesByTick.floorKey(tick);
            float floorX = floor == null ? Float.NaN : x + (float)TimelineWindow.replayTickToTimelineX(floor);
            Integer closest = Utils.chooseClosest(mouseX, floorX, floor, ceilX = (ceil = track.keyframesByTick.ceilingKey(tick)) == null ? Float.NaN : x + (float)TimelineWindow.replayTickToTimelineX(ceil), ceil, keyframeSize);
            if (closest == null) continue;
            if (closestTick == -1) {
                closestTick = closest;
                continue;
            }
            if (Math.abs(closest - tick) >= Math.abs(closestTick - tick)) continue;
            closestTick = closest;
        }
        return closestTick;
    }

    private static void handleKeyPresses(ReplayServer replayServer, int cursorTicks, int totalTicks) {
        float rollCcw;
        float rollCw;
        double zoomDelta;
        double zoomDelta2;
        FlashbackMeta meta;
        boolean pressedDelete;
        boolean pressedIn = Keybinds.MARK_IN.isPressed(false);
        boolean pressedOut = Keybinds.MARK_OUT.isPressed(false);
        boolean pressedClearIn = Keybinds.CLEAR_IN.isPressed(false);
        boolean pressedClearOut = Keybinds.CLEAR_OUT.isPressed(false);
        boolean pressedCopy = Keybinds.COPY.isPressed(false);
        boolean pressedPaste = Keybinds.PASTE.isPressed(false);
        boolean bl = pressedDelete = ImGui.isKeyPressed(261, false) || ImGui.isKeyPressed(259, false);
        if (Keybinds.PAUSE.isPressed(false)) {
            TimelineWindow.togglePaused(replayServer);
        }
        if (ImGui.isKeyPressed(263, false)) {
            pendingStepBackwardsTicks += ReplayUI.isCtrlOrCmdDown() ? 5 : 1;
        } else if (pendingStepBackwardsTicks > 0 && !ImGui.isKeyDown(263)) {
            replayServer.goToReplayTick(Math.max(0, replayServer.getReplayTick() - pendingStepBackwardsTicks));
            replayServer.forceApplyKeyframes.set(true);
            pendingStepBackwardsTicks = 0;
        }
        if (ImGui.isKeyPressed(262, false)) {
            replayServer.goToReplayTick(Math.min(totalTicks, cursorTicks + (ReplayUI.isCtrlOrCmdDown() ? 5 : 1)));
            replayServer.forceApplyKeyframes.set(true);
        }
        if (ImGui.isKeyPressed(265, false)) {
            int nextKeyframeTick = TimelineWindow.editorScene.exportStartTicks >= 0 && TimelineWindow.editorScene.exportStartTicks > cursorTicks ? TimelineWindow.editorScene.exportStartTicks : (TimelineWindow.editorScene.exportEndTicks >= 0 && TimelineWindow.editorScene.exportEndTicks > cursorTicks ? TimelineWindow.editorScene.exportEndTicks : totalTicks);
            meta = replayServer.getMetadata();
            Integer nextMarker = meta.replayMarkers.ceilingKey(cursorTicks + 1);
            if (nextMarker != null && nextMarker < nextKeyframeTick) {
                nextKeyframeTick = nextMarker;
            }
            for (KeyframeTrack track : TimelineWindow.editorScene.keyframeTracks) {
                Integer ceilingKey = track.keyframesByTick.ceilingKey(cursorTicks + 1);
                if (ceilingKey == null || ceilingKey >= nextKeyframeTick) continue;
                nextKeyframeTick = ceilingKey;
            }
            replayServer.goToReplayTick(nextKeyframeTick);
            replayServer.forceApplyKeyframes.set(true);
        }
        if (ImGui.isKeyPressed(264, false)) {
            int previousKeyframeTick = TimelineWindow.editorScene.exportEndTicks >= 0 && TimelineWindow.editorScene.exportEndTicks < cursorTicks ? TimelineWindow.editorScene.exportEndTicks : (TimelineWindow.editorScene.exportStartTicks >= 0 && TimelineWindow.editorScene.exportStartTicks < cursorTicks ? TimelineWindow.editorScene.exportStartTicks : 0);
            meta = replayServer.getMetadata();
            Integer lastMarker = meta.replayMarkers.floorKey(cursorTicks - 1);
            if (lastMarker != null && lastMarker > previousKeyframeTick) {
                previousKeyframeTick = lastMarker;
            }
            for (KeyframeTrack track : TimelineWindow.editorScene.keyframeTracks) {
                Integer floorKey = track.keyframesByTick.floorKey(cursorTicks - 1);
                if (floorKey == null || floorKey <= previousKeyframeTick) continue;
                previousKeyframeTick = floorKey;
            }
            replayServer.goToReplayTick(previousKeyframeTick);
            replayServer.forceApplyKeyframes.set(true);
        }
        if (Keybinds.UNDO.isPressed(false)) {
            TimelineWindow.upgradeToSceneWrite();
            editorScene.undo(ReplayUI::setInfoOverlayShort);
            editorState.markDirty();
        }
        if (Keybinds.REDO.isPressed(false)) {
            TimelineWindow.upgradeToSceneWrite();
            editorScene.redo(ReplayUI::setInfoOverlayShort);
            editorState.markDirty();
        }
        if (pressedIn || pressedOut || pressedClearIn || pressedClearOut) {
            int start = -1;
            int end = -1;
            if (pressedIn) {
                ReplayUI.setInfoOverlayShort("Marked 'In' render point at " + cursorTicks);
                start = cursorTicks;
            }
            if (pressedOut) {
                ReplayUI.setInfoOverlayShort("Marked 'Out' render point at " + cursorTicks);
                end = cursorTicks;
            }
            if (pressedClearIn) {
                ReplayUI.setInfoOverlayShort("Cleared 'In' render point");
                start = 0;
            }
            if (pressedClearOut) {
                ReplayUI.setInfoOverlayShort("Cleared 'Out' render point");
                end = totalTicks;
            }
            TimelineWindow.upgradeToSceneWrite();
            editorScene.setExportTicks(start, end, totalTicks);
            editorState.markDirty();
        }
        if (pressedDelete && !selectedKeyframesList.isEmpty()) {
            TimelineWindow.removeAllSelectedKeyframes();
        }
        if (pressedCopy && !selectedKeyframesList.isEmpty()) {
            TimelineWindow.performCopy(totalTicks, false, false, false);
        }
        if (pressedPaste) {
            try {
                String clipboard = Minecraft.getInstance().keyboardHandler.getClipboard().trim();
                if (clipboard.startsWith("{") && clipboard.endsWith("}")) {
                    CopiedKeyframes copiedKeyframes = (CopiedKeyframes)FlashbackGson.COMPRESSED.fromJson(clipboard, CopiedKeyframes.class);
                    KeyframeRelativeOffsets offsets = new KeyframeRelativeOffsets();
                    LocalPlayer p = Minecraft.getInstance().player;
                    if (p != null) {
                        if (copiedKeyframes.relativePosition != null) {
                            offsets.oldOrigin = copiedKeyframes.relativePosition;
                            offsets.newOrigin = new Vector3d(p.getX(), p.getY(), p.getZ());
                        }
                        if (copiedKeyframes.relativeYaw != null) {
                            offsets.oldYaw = copiedKeyframes.relativeYaw.floatValue();
                            offsets.newYaw = p.getViewYRot(1.0f);
                        }
                        if (copiedKeyframes.relativePitch != null) {
                            offsets.oldPitch = copiedKeyframes.relativePitch.floatValue();
                            offsets.newPitch = p.getViewXRot(1.0f);
                        }
                    }
                    TimelineWindow.upgradeToSceneWrite();
                    int count = 0;
                    for (SavedTrack savedTrack : copiedKeyframes.savedTracks) {
                        count += savedTrack.applyToScene(editorScene, cursorTicks, totalTicks, offsets);
                    }
                    if (count > 0) {
                        ReplayUI.setInfoOverlay(I18n.get((String)"flashback.pasted_n_keyframes_from_clipboard", (Object[])new Object[]{count}));
                        editorState.markDirty();
                    }
                }
            }
            catch (Exception clipboard) {
                // empty catch block
            }
        }
        if (Keybinds.ZOOM_IN.isPressed(true) && (zoomDelta2 = TimelineWindow.editorState.zoomMax - TimelineWindow.editorState.zoomMin) > 0.001) {
            TimelineWindow.editorState.zoomMin += zoomDelta2 * 0.025;
            TimelineWindow.editorState.zoomMax -= zoomDelta2 * 0.025;
            editorState.markDirty();
        }
        if (Keybinds.ZOOM_OUT.isPressed(true) && (zoomDelta = TimelineWindow.editorState.zoomMax - TimelineWindow.editorState.zoomMin) > 0.001 && zoomDelta < 1.0) {
            TimelineWindow.editorState.zoomMin = Math.max(0.0, TimelineWindow.editorState.zoomMin - zoomDelta * 0.025);
            TimelineWindow.editorState.zoomMax = Math.min(1.0, TimelineWindow.editorState.zoomMax + zoomDelta * 0.025);
            editorState.markDirty();
        }
        float f = Keybinds.ROLL_CW.isPressed(true) ? (Keybinds.ROLL_CW.isPressed(false) ? 1.0f : 3.0f) : (rollCw = 0.0f);
        float f2 = Keybinds.ROLL_CCW.isPressed(true) ? (Keybinds.ROLL_CCW.isPressed(false) ? 1.0f : 3.0f) : (rollCcw = 0.0f);
        if (rollCw != rollCcw) {
            ReplayVisuals visuals = TimelineWindow.editorState.replayVisuals;
            visuals.overrideRoll = true;
            visuals.overrideRollAmount += rollCw - rollCcw;
            if (visuals.overrideRollAmount < -180.0f) {
                visuals.overrideRollAmount += 360.0f;
            }
            if (visuals.overrideRollAmount > 180.0f) {
                visuals.overrideRollAmount -= 360.0f;
            }
            Minecraft.getInstance().levelRenderer.needsUpdate();
            editorState.markDirty();
        }
        if (Keybinds.ADD_CAMERA.isPressed(false)) {
            ReplayUI.setInfoOverlayShort("Added camera keyframe at tick " + cursorTicks);
            TimelineWindow.upgradeToSceneWrite();
            int trackIndex = TimelineWindow.findOrCreateCameraTrack();
            editorScene.setKeyframe(trackIndex, cursorTicks, CameraKeyframeType.INSTANCE.createDirect());
            editorState.markDirty();
            lastKeyframeTrackCameraWasAddedTo = TimelineWindow.editorScene.keyframeTracks.get(trackIndex);
        }
    }

    private static int findOrCreateCameraTrack() {
        int lastCameraAddedIndex;
        if (lastKeyframeTrackCameraWasAddedTo != null && (lastCameraAddedIndex = TimelineWindow.editorScene.keyframeTracks.indexOf(lastKeyframeTrackCameraWasAddedTo)) >= 0) {
            return lastCameraAddedIndex;
        }
        int disabledIndex = -1;
        for (int i = 0; i < TimelineWindow.editorScene.keyframeTracks.size(); ++i) {
            KeyframeTrack track = TimelineWindow.editorScene.keyframeTracks.get(i);
            if (track.keyframeType != CameraKeyframeType.INSTANCE) continue;
            if (track.enabled) {
                return i;
            }
            disabledIndex = i;
        }
        if (disabledIndex >= 0) {
            return disabledIndex;
        }
        int index = TimelineWindow.editorScene.keyframeTracks.size();
        editorScene.push(new EditorSceneHistoryEntry(List.of(new EditorSceneHistoryAction.RemoveTrack(CameraKeyframeType.INSTANCE, index)), List.of(new EditorSceneHistoryAction.AddTrack(CameraKeyframeType.INSTANCE, index)), I18n.get((String)"flashback.create_named_track", (Object[])new Object[]{CameraKeyframeType.INSTANCE.name()})));
        editorState.markDirty();
        return index;
    }

    private static void performCopy(int totalTicks, boolean relativePosition, boolean relativeYaw, boolean relativePitch) {
        ArrayList<SavedTrack> tracks = new ArrayList<SavedTrack>();
        int minTick = totalTicks;
        int keyframeCount = 0;
        for (SelectedKeyframes selectedKeyframes : selectedKeyframesList) {
            IntIterator intIterator = selectedKeyframes.keyframeTicks().iterator();
            while (intIterator.hasNext()) {
                int keyframeTick = (Integer)intIterator.next();
                minTick = Math.min(minTick, keyframeTick);
                ++keyframeCount;
            }
        }
        for (SelectedKeyframes selectedKeyframes : selectedKeyframesList) {
            KeyframeTrack keyframeTrack = TimelineWindow.editorScene.keyframeTracks.get(selectedKeyframes.trackIndex());
            TreeMap<Integer, Keyframe> keyframes = new TreeMap<Integer, Keyframe>();
            IntIterator intIterator = selectedKeyframes.keyframeTicks().iterator();
            while (intIterator.hasNext()) {
                int tick = (Integer)intIterator.next();
                Keyframe keyframe = keyframeTrack.keyframesByTick.get(tick);
                keyframes.put(tick - minTick, keyframe);
            }
            tracks.add(new SavedTrack(selectedKeyframes.type(), selectedKeyframes.trackIndex(), !keyframeTrack.enabled, keyframes));
        }
        LocalPlayer p = Minecraft.getInstance().player;
        CopiedKeyframes copiedKeyframes = new CopiedKeyframes();
        if (p != null) {
            copiedKeyframes.relativePosition = relativePosition ? new Vector3d(p.getX(), p.getY(), p.getZ()) : null;
            copiedKeyframes.relativeYaw = relativeYaw ? Float.valueOf(p.getViewYRot(1.0f)) : null;
            copiedKeyframes.relativePitch = relativePitch ? Float.valueOf(p.getViewXRot(1.0f)) : null;
        }
        copiedKeyframes.savedTracks = tracks;
        String serialized = FlashbackGson.COMPRESSED.toJson((Object)copiedKeyframes);
        Minecraft.getInstance().keyboardHandler.setClipboard(serialized);
        ReplayUI.setInfoOverlay(I18n.get((String)"flashback.copied_n_keyframes_to_clipboard", (Object[])new Object[]{keyframeCount}));
    }

    private static void removeAllSelectedKeyframes() {
        TimelineWindow.upgradeToSceneWrite();
        ArrayList<EditorSceneHistoryAction> undo = new ArrayList<EditorSceneHistoryAction>();
        ArrayList<EditorSceneHistoryAction> redo = new ArrayList<EditorSceneHistoryAction>();
        for (SelectedKeyframes selectedKeyframes : selectedKeyframesList) {
            KeyframeTrack track = TimelineWindow.editorScene.keyframeTracks.get(selectedKeyframes.trackIndex());
            IntIterator intIterator = selectedKeyframes.keyframeTicks().iterator();
            while (intIterator.hasNext()) {
                int tick = (Integer)intIterator.next();
                Keyframe keyframe = track.keyframesByTick.get(tick);
                undo.add(new EditorSceneHistoryAction.SetKeyframe(track.keyframeType, selectedKeyframes.trackIndex(), tick, keyframe.copy()));
                redo.add(new EditorSceneHistoryAction.RemoveKeyframe(track.keyframeType, selectedKeyframes.trackIndex(), tick));
            }
        }
        selectedKeyframesList.clear();
        editingKeyframeTrack = -1;
        editingKeyframeTick = -1;
        editorScene.push(new EditorSceneHistoryEntry(undo, redo, I18n.get((String)"flashback.deleted_n_keyframes", (Object[])new Object[]{undo.size()})));
        editorState.markDirty();
    }

    private static void handleClick(ReplayServer replayServer, int totalTicks, float contentY) {
        TimelineWindow.releaseGrabbed(replayServer, totalTicks, contentY);
        ArrayList<SelectedKeyframes> oldSelectedKeyframesList = new ArrayList<SelectedKeyframes>(selectedKeyframesList);
        selectedKeyframesList.clear();
        boolean leftClicked = ImGui.isMouseClicked(0);
        boolean rightClicked = ImGui.isMouseClicked(1);
        if (hoveredControls) {
            if (hoveredSkipBackwards) {
                FlashbackMeta meta = replayServer.getMetadata();
                Integer lastMarker = meta.replayMarkers.floorKey(cursorTicks - 1);
                if (lastMarker != null) {
                    replayServer.goToReplayTick(lastMarker);
                } else {
                    replayServer.goToReplayTick(0);
                }
                return;
            }
            if (hoveredSlowDown) {
                float highest = replayTickSpeeds[0];
                float currentTickRate = replayServer.getDesiredTickRate(true);
                for (float replayTickSpeed : replayTickSpeeds) {
                    if (replayTickSpeed >= currentTickRate) break;
                    highest = replayTickSpeed;
                }
                replayServer.setDesiredTickRate(highest, true);
                return;
            }
            if (hoveredPause) {
                TimelineWindow.togglePaused(replayServer);
                return;
            }
            if (hoveredFastForwards) {
                float replayTickSpeed;
                float lowest = replayTickSpeeds[replayTickSpeeds.length - 1];
                float currentTickRate = replayServer.getDesiredTickRate(true);
                for (int i = replayTickSpeeds.length - 1; i >= 0 && !((replayTickSpeed = replayTickSpeeds[i]) <= currentTickRate); --i) {
                    lowest = replayTickSpeed;
                }
                replayServer.setDesiredTickRate(lowest, true);
                return;
            }
            if (hoveredSkipForwards) {
                FlashbackMeta meta = replayServer.getMetadata();
                Integer nextMarker = meta.replayMarkers.ceilingKey(cursorTicks + 1);
                if (nextMarker != null) {
                    replayServer.goToReplayTick(nextMarker);
                } else {
                    replayServer.goToReplayTick(replayServer.getTotalReplayTicks());
                }
                return;
            }
        }
        if (mouseY > y && mouseY < y + (float)middleY && mouseX > x + (float)middleX && mouseX < x + width) {
            if (TimelineWindow.editorScene.exportStartTicks >= 0 && TimelineWindow.editorScene.exportEndTicks >= 0 && mouseY > y + (float)timestampHeight) {
                int exportStartX = TimelineWindow.replayTickToTimelineX(TimelineWindow.editorScene.exportStartTicks);
                int exportEndX = TimelineWindow.replayTickToTimelineX(TimelineWindow.editorScene.exportEndTicks);
                Utils.ClosestElement closestElement = Utils.findClosest(mouseX, exportStartX, exportEndX, 5.0f);
                switch (closestElement) {
                    case LEFT: {
                        grabbedExportBarResizeLeft = leftClicked;
                        break;
                    }
                    case RIGHT: {
                        grabbedExportBarResizeRight = leftClicked;
                        break;
                    }
                    case NONE: {
                        replayServer.replayPaused = true;
                        grabbedPlayback = leftClicked;
                    }
                }
            } else {
                replayServer.replayPaused = true;
                grabbedPlayback = leftClicked;
            }
            return;
        }
        if (zoomBarHovered) {
            if (mouseX <= zoomBarMin + (float)zoomBarHeight) {
                grabbedZoomBarResizeLeft = leftClicked;
            } else if (mouseX >= zoomBarMax - (float)zoomBarHeight) {
                grabbedZoomBarResizeRight = leftClicked;
            }
            grabbedZoomBar = leftClicked;
            zoomMinBeforeDrag = TimelineWindow.editorState.zoomMin;
            zoomMaxBeforeDrag = TimelineWindow.editorState.zoomMax;
            return;
        }
        if (zoomBarExpanded && mouseX >= x + (float)middleX && mouseX <= x + width) {
            double zoomSize = TimelineWindow.editorState.zoomMax - TimelineWindow.editorState.zoomMin;
            float targetZoom = (mouseX - (x + (float)middleX)) / (x + width - (x + (float)middleX));
            TimelineWindow.editorState.zoomMin = (double)targetZoom - zoomSize / 2.0;
            TimelineWindow.editorState.zoomMax = (double)targetZoom + zoomSize / 2.0;
            if (TimelineWindow.editorState.zoomMax > 1.0) {
                TimelineWindow.editorState.zoomMax = 1.0;
                TimelineWindow.editorState.zoomMin = TimelineWindow.editorState.zoomMax - zoomSize;
            } else if (TimelineWindow.editorState.zoomMin < 0.0) {
                TimelineWindow.editorState.zoomMin = 0.0;
                TimelineWindow.editorState.zoomMax = TimelineWindow.editorState.zoomMin + zoomSize;
            }
            editorState.markDirty();
            grabbedZoomBar = leftClicked;
            zoomMinBeforeDrag = TimelineWindow.editorState.zoomMin;
            zoomMaxBeforeDrag = TimelineWindow.editorState.zoomMax;
            return;
        }
        if (mouseY > y + (float)middleY && mouseY < y + height && mouseX > x + (float)middleX && mouseX < x + width) {
            float lineHeight = ImGui.getTextLineHeightWithSpacing() + ImGui.getStyle().getItemSpacingY();
            int trackIndex = (int)Math.max(0.0, Math.floor((mouseY - (contentY + 2.0f)) / lineHeight));
            if (trackIndex >= 0 && trackIndex < TimelineWindow.editorScene.keyframeTracks.size()) {
                float floorCustomWidth;
                KeyframeTrack keyframeTrack = TimelineWindow.editorScene.keyframeTracks.get(trackIndex);
                int tick = TimelineWindow.timelineXToReplayTick(mouseX - x);
                Map.Entry<Integer, Keyframe> closest = null;
                Map.Entry<Integer, Keyframe> floor = keyframeTrack.keyframesByTick.floorEntry(tick);
                float f = floorCustomWidth = floor == null ? -1.0f : floor.getValue().getCustomWidthInTicks();
                if (floor != null && floorCustomWidth > 0.0f) {
                    int tickMax = floor.getKey() + (int)Math.ceil(floorCustomWidth);
                    if (tick <= tickMax) {
                        closest = floor;
                    }
                } else {
                    Map.Entry<Integer, Keyframe> ceil = keyframeTrack.keyframesByTick.ceilingEntry(tick);
                    float floorX = floor == null ? Float.NaN : x + (float)TimelineWindow.replayTickToTimelineX(floor.getKey());
                    float ceilX = ceil == null ? Float.NaN : x + (float)TimelineWindow.replayTickToTimelineX(ceil.getKey());
                    closest = Utils.chooseClosest(mouseX, floorX, floor, ceilX, ceil, keyframeSize);
                }
                if (closest != null) {
                    boolean reuseOld = false;
                    for (SelectedKeyframes selectedKeyframes : oldSelectedKeyframesList) {
                        if (selectedKeyframes.trackIndex() != trackIndex) continue;
                        reuseOld = selectedKeyframes.keyframeTicks().contains(closest.getKey().intValue());
                        break;
                    }
                    boolean deselectedClicked = false;
                    if (ReplayUI.isCtrlOrCmdDown()) {
                        selectedKeyframesList.addAll(oldSelectedKeyframesList);
                        boolean handled = false;
                        for (SelectedKeyframes selectedKeyframes : selectedKeyframesList) {
                            if (selectedKeyframes.type() != keyframeTrack.keyframeType || selectedKeyframes.trackIndex() != trackIndex) continue;
                            if (selectedKeyframes.keyframeTicks().remove(closest.getKey().intValue())) {
                                deselectedClicked = true;
                            } else {
                                selectedKeyframes.keyframeTicks().add(closest.getKey().intValue());
                            }
                            handled = true;
                            break;
                        }
                        if (!handled) {
                            IntOpenHashSet intSet = new IntOpenHashSet();
                            intSet.add(closest.getKey().intValue());
                            selectedKeyframesList.add(new SelectedKeyframes(keyframeTrack.keyframeType, trackIndex, (IntSet)intSet));
                        }
                    } else if (reuseOld) {
                        selectedKeyframesList.addAll(oldSelectedKeyframesList);
                    } else {
                        IntOpenHashSet intSet = new IntOpenHashSet();
                        intSet.add(closest.getKey().intValue());
                        selectedKeyframesList.add(new SelectedKeyframes(keyframeTrack.keyframeType, trackIndex, (IntSet)intSet));
                    }
                    selectedKeyframesList.removeIf(k -> !k.checkValid(editorScene));
                    if (!deselectedClicked) {
                        if (leftClicked) {
                            if (ImGui.isMouseDoubleClicked(0)) {
                                KeyframeChange change;
                                MinecraftKeyframeHandler minecraftKeyframeHandler = new MinecraftKeyframeHandler(Minecraft.getInstance());
                                if (closest.getValue().keyframeType().supportsHandler(minecraftKeyframeHandler) && (change = closest.getValue().createChange()) != null) {
                                    change.apply(minecraftKeyframeHandler);
                                }
                            } else {
                                grabbedKeyframe = true;
                                grabbedKeyframeMouseX = mouseX;
                                grabbedKeyframeTick = closest.getKey();
                                grabbedKeyframeTrack = trackIndex;
                                enableKeyframeMovement = false;
                            }
                        } else if (rightClicked) {
                            ImGui.openPopup("##KeyframePopup");
                            editingKeyframeTrack = trackIndex;
                            editingKeyframeTick = closest.getKey();
                            grabbedKeyframeTrack = trackIndex;
                        }
                    }
                    return;
                }
                if (rightClicked && keyframeTrack.keyframeType.canBeCreatedNormally()) {
                    createKeyframeAtTick = tick;
                    openCreateKeyframeAtTickTrack = trackIndex;
                }
            }
            dragSelectOrigin = new Vector2f(mouseX, mouseY);
        }
    }

    private static void togglePaused(ReplayServer replayServer) {
        Screen screen;
        if (replayServer.getReplayTick() >= replayServer.getTotalReplayTicks()) {
            replayServer.jumpToTick = 0;
        }
        boolean bl = replayServer.replayPaused = !replayServer.replayPaused;
        if (!replayServer.replayPaused && (screen = Minecraft.getInstance().screen) != null && screen.isPauseScreen()) {
            Minecraft.getInstance().setScreen(null);
        }
    }

    private static void renderExportBar(ImDrawList drawList) {
        if (TimelineWindow.editorScene.exportStartTicks >= 0 && TimelineWindow.editorScene.exportEndTicks >= 0) {
            int exportStartX = TimelineWindow.replayTickToTimelineX(TimelineWindow.editorScene.exportStartTicks);
            int exportEndX = TimelineWindow.replayTickToTimelineX(TimelineWindow.editorScene.exportEndTicks);
            drawList.addRectFilled(x + (float)exportStartX, y + (float)timestampHeight, x + (float)exportEndX, y + (float)middleY, 1627367936);
            drawList.addLine(x + (float)exportStartX, y + (float)timestampHeight, x + (float)exportStartX, y + (float)middleY, -22016, 4.0f);
            drawList.addLine(x + (float)exportEndX, y + (float)timestampHeight, x + (float)exportEndX, y + (float)middleY, -22016, 4.0f);
            if (mouseY > y + (float)timestampHeight && mouseY < y + (float)middleY && (mouseX >= (float)(exportStartX - 5) && mouseX <= (float)(exportStartX + 5) || mouseX >= (float)(exportEndX - 5) && mouseX <= (float)(exportEndX + 5))) {
                ImGui.setMouseCursor(4);
            }
        }
    }

    private static void renderKeyframeOptionsPopup(int totalTicks) {
        boolean multiple;
        if (editingKeyframeTrack < 0 || editingKeyframeTick < 0) {
            return;
        }
        KeyframeTrack keyframeTrack = TimelineWindow.editorScene.keyframeTracks.get(editingKeyframeTrack);
        Keyframe editingKeyframe = keyframeTrack.keyframesByTick.get(editingKeyframeTick);
        if (editingKeyframe == null || selectedKeyframesList.isEmpty() || ReplayUI.consumeCancel()) {
            editingKeyframeTrack = -1;
            ImGui.closeCurrentPopup();
            return;
        }
        editingKeyframe.renderEditKeyframe(updateFunction -> {
            TimelineWindow.upgradeToSceneWrite();
            ArrayList<EditorSceneHistoryAction> undo = new ArrayList<EditorSceneHistoryAction>();
            ArrayList<EditorSceneHistoryAction> redo = new ArrayList<EditorSceneHistoryAction>();
            int modified = 0;
            for (SelectedKeyframes selectedKeyframes : selectedKeyframesList) {
                KeyframeTrack track = TimelineWindow.editorScene.keyframeTracks.get(selectedKeyframes.trackIndex());
                IntIterator i$ = selectedKeyframes.keyframeTicks().iterator();
                while (i$.hasNext()) {
                    int tick = (Integer)i$.next();
                    Keyframe keyframe = track.keyframesByTick.get(tick);
                    if (keyframe.getClass() != editingKeyframe.getClass()) continue;
                    ++modified;
                    undo.add(new EditorSceneHistoryAction.SetKeyframe(track.keyframeType, selectedKeyframes.trackIndex(), tick, keyframe.copy()));
                    updateFunction.accept(keyframe);
                    redo.add(new EditorSceneHistoryAction.SetKeyframe(track.keyframeType, selectedKeyframes.trackIndex(), tick, keyframe.copy()));
                }
            }
            if (modified > 0) {
                editorScene.push(new EditorSceneHistoryEntry(undo, redo, I18n.get((String)"flashback.modified_n_keyframes", (Object[])new Object[]{modified})));
                editorState.markDirty();
            }
        });
        if (editingKeyframe.keyframeType().allowChangingInterpolationType()) {
            int[] type = new int[]{editingKeyframe.interpolationType().ordinal()};
            ImGui.setNextItemWidth(160.0f);
            if (ImGuiHelper.combo(I18n.get((String)"flashback.type", (Object[])new Object[0]), type, InterpolationType.getNames())) {
                TimelineWindow.upgradeToSceneWrite();
                InterpolationType interpolationType = InterpolationType.INTERPOLATION_TYPES[type[0]];
                ArrayList undo = new ArrayList();
                ArrayList<EditorSceneHistoryAction> redo = new ArrayList<EditorSceneHistoryAction>();
                for (SelectedKeyframes selectedKeyframes : selectedKeyframesList) {
                    KeyframeTrack track = TimelineWindow.editorScene.keyframeTracks.get(selectedKeyframes.trackIndex());
                    IntIterator intIterator = selectedKeyframes.keyframeTicks().iterator();
                    while (intIterator.hasNext()) {
                        int tick = (Integer)intIterator.next();
                        Keyframe keyframe = track.keyframesByTick.get(tick);
                        if (keyframe.interpolationType() == interpolationType) continue;
                        Keyframe changed = keyframe.copy();
                        changed.interpolationType(interpolationType);
                        undo.add(new EditorSceneHistoryAction.SetKeyframe(track.keyframeType, selectedKeyframes.trackIndex(), tick, keyframe.copy()));
                        redo.add(new EditorSceneHistoryAction.SetKeyframe(track.keyframeType, selectedKeyframes.trackIndex(), tick, changed));
                    }
                }
                editorScene.push(new EditorSceneHistoryEntry(undo, redo, I18n.get((String)"flashback.changed_interpolation_type_to", (Object[])new Object[]{interpolationType.text()})));
                editorState.markDirty();
            }
        }
        if (editingKeyframe.keyframeType().allowChangingTimelineTick()) {
            int[] intWrapper = new int[]{editingKeyframeTick};
            ImGui.setNextItemWidth(160.0f);
            ImGuiHelper.inputInt(I18n.get((String)"flashback.tick", (Object[])new Object[0]), intWrapper);
            int newEditingKeyframeTick = intWrapper[0];
            if (ImGui.isItemDeactivatedAfterEdit() && newEditingKeyframeTick != editingKeyframeTick) {
                TimelineWindow.upgradeToSceneWrite();
                for (SelectedKeyframes selectedKeyframes : selectedKeyframesList) {
                    KeyframeTrack track = TimelineWindow.editorScene.keyframeTracks.get(selectedKeyframes.trackIndex());
                    Keyframe possibleKeyframe = track.keyframesByTick.get(editingKeyframeTick);
                    if (possibleKeyframe != editingKeyframe) continue;
                    List<EditorSceneHistoryAction> undo = List.of(new EditorSceneHistoryAction.RemoveKeyframe(track.keyframeType, selectedKeyframes.trackIndex(), newEditingKeyframeTick), new EditorSceneHistoryAction.SetKeyframe(track.keyframeType, selectedKeyframes.trackIndex(), editingKeyframeTick, editingKeyframe.copy()));
                    List<EditorSceneHistoryAction> redo = List.of(new EditorSceneHistoryAction.RemoveKeyframe(track.keyframeType, selectedKeyframes.trackIndex(), editingKeyframeTick), new EditorSceneHistoryAction.SetKeyframe(track.keyframeType, selectedKeyframes.trackIndex(), newEditingKeyframeTick, editingKeyframe.copy()));
                    editorScene.push(new EditorSceneHistoryEntry(undo, redo, I18n.get((String)"flashback.moved_n_keyframes", (Object[])new Object[]{1})));
                    editorState.markDirty();
                    selectedKeyframes.keyframeTicks().remove(editingKeyframeTick);
                    selectedKeyframes.keyframeTicks().add(newEditingKeyframeTick);
                    editingKeyframeTick = newEditingKeyframeTick;
                    return;
                }
            }
        }
        if (ImGui.button(((multiple = selectedKeyframesList.size() >= 2 || selectedKeyframesList.getFirst().keyframeTicks().size() >= 2) ? I18n.get((String)"flashback.remove_all", (Object[])new Object[0]) : I18n.get((String)"flashback.remove", (Object[])new Object[0])) + "##RemoveButton")) {
            ImGui.closeCurrentPopup();
            TimelineWindow.removeAllSelectedKeyframes();
        }
        if (!multiple && editingKeyframe instanceof CameraKeyframe) {
            KeyframeChange change;
            CameraKeyframe cameraKeyframe = (CameraKeyframe)editingKeyframe;
            ImGui.sameLine();
            if (ImGui.button(I18n.get((String)"flashback.apply", (Object[])new Object[0])) && (change = cameraKeyframe.createChange()) != null) {
                change.apply(new MinecraftKeyframeHandler(Minecraft.getInstance()));
            }
        }
        if (editingKeyframe instanceof CameraKeyframe || editingKeyframe instanceof CameraOrbitKeyframe) {
            if (ImGui.button(I18n.get((String)"flashback.copy_relative", (Object[])new Object[0]) + "##CopyRelative")) {
                ImGui.openPopup("##CopyOptions");
            }
            if (ImGuiHelper.beginPopup("##CopyOptions")) {
                if (ImGui.checkbox(I18n.get((String)"flashback.copy_relative_to_position", (Object[])new Object[0]) + "##CopyRelativeToPos", copyRelativeToPosition)) {
                    boolean bl = copyRelativeToPosition = !copyRelativeToPosition;
                }
                if (ImGui.checkbox(I18n.get((String)"flashback.copy_relative_to_yaw", (Object[])new Object[0]) + "##CopyRelativeToYaw", copyRelativeToYaw)) {
                    boolean bl = copyRelativeToYaw = !copyRelativeToYaw;
                }
                if (ImGui.checkbox(I18n.get((String)"flashback.copy_relative_to_pitch", (Object[])new Object[0]) + "##CopyRelativeToPitch", copyRelativeToPitch)) {
                    boolean bl = copyRelativeToPitch = !copyRelativeToPitch;
                }
                if (ImGui.button(I18n.get((String)"flashback.do_copy_relative", (Object[])new Object[0]) + "##DoCopyRelative")) {
                    TimelineWindow.performCopy(totalTicks, copyRelativeToPosition, copyRelativeToYaw, copyRelativeToPitch);
                    ImGui.closeCurrentPopup();
                }
                ImGui.endPopup();
            }
        }
    }

    private static void releaseGrabbed(ReplayServer replayServer, int totalTicks, float contentY) {
        grabbedZoomBar = false;
        grabbedZoomBarResizeLeft = false;
        grabbedZoomBarResizeRight = false;
        grabbedExportBarResizeLeft = false;
        grabbedExportBarResizeRight = false;
        if (!ImGui.isAnyMouseDown()) {
            trackDisabledButtonDrag = false;
            repositioningKeyframeTrack = -1;
        }
        if (dragSelectOrigin != null) {
            float dragMinX = Math.min(TimelineWindow.dragSelectOrigin.x, mouseX);
            float dragMinY = Math.min(TimelineWindow.dragSelectOrigin.y, mouseY);
            float dragMaxX = Math.max(TimelineWindow.dragSelectOrigin.x, mouseX);
            float dragMaxY = Math.max(TimelineWindow.dragSelectOrigin.y, mouseY);
            float lineHeight = ImGui.getTextLineHeightWithSpacing() + ImGui.getStyle().getItemSpacingY();
            int minTrackIndex = (int)Math.floor((dragMinY - (contentY + 2.0f)) / lineHeight);
            int maxTrackIndex = (int)Math.floor((dragMaxY - (contentY + 2.0f)) / lineHeight);
            minTrackIndex = Math.max(0, minTrackIndex);
            maxTrackIndex = Math.min(TimelineWindow.editorScene.keyframeTracks.size() - 1, maxTrackIndex);
            for (int trackIndex = minTrackIndex; trackIndex <= maxTrackIndex; ++trackIndex) {
                Map.Entry<Integer, Keyframe> entry;
                KeyframeTrack keyframeTrack = TimelineWindow.editorScene.keyframeTracks.get(trackIndex);
                int minTick = TimelineWindow.timelineXToReplayTick(dragMinX - (float)keyframeSize);
                int maxTick = TimelineWindow.timelineXToReplayTick(dragMaxX + (float)keyframeSize);
                IntOpenHashSet intSet = new IntOpenHashSet();
                for (int tick = minTick; tick <= maxTick && (entry = keyframeTrack.keyframesByTick.ceilingEntry(tick)) != null && entry.getKey() <= maxTick; ++tick) {
                    tick = entry.getKey();
                    intSet.add(tick);
                }
                if (intSet.isEmpty()) continue;
                selectedKeyframesList.add(new SelectedKeyframes(keyframeTrack.keyframeType, trackIndex, (IntSet)intSet));
            }
            dragSelectOrigin = null;
        }
        if (grabbedPlayback) {
            int closestTick;
            int desiredTick = TimelineWindow.timelineXToReplayTick(mouseX - x);
            if ((ImGui.isKeyDown(340) || ImGui.isKeyDown(344)) && (closestTick = TimelineWindow.findClosestKeyframeForSnap(desiredTick)) != -1) {
                desiredTick = closestTick;
            }
            replayServer.goToReplayTick(desiredTick);
            replayServer.replayPaused = true;
            grabbedPlayback = false;
        }
        if (grabbedKeyframe) {
            GrabMovementInfo grabMovementInfo = TimelineWindow.calculateGrabMovementInfo(totalTicks);
            if (grabMovementInfo.grabbedScalePivotTick >= 0 || grabMovementInfo.grabbedDelta != 0) {
                TimelineWindow.upgradeToSceneWrite();
                ArrayList<EditorSceneHistoryAction> undo = new ArrayList<EditorSceneHistoryAction>();
                ArrayList<EditorSceneHistoryAction> redo = new ArrayList<EditorSceneHistoryAction>();
                int movedKeyframes = 0;
                for (SelectedKeyframes selectedKeyframes : selectedKeyframesList) {
                    int trackIndex = selectedKeyframes.trackIndex();
                    KeyframeTrack keyframeTrack = TimelineWindow.editorScene.keyframeTracks.get(trackIndex);
                    IntArrayList selectedTicks = new IntArrayList((IntCollection)selectedKeyframes.keyframeTicks());
                    selectedTicks.sort(mouseX < grabbedKeyframeMouseX ? IntComparators.NATURAL_COMPARATOR : IntComparators.OPPOSITE_COMPARATOR);
                    selectedKeyframes.keyframeTicks().clear();
                    Int2ObjectOpenHashMap removeFromTick = new Int2ObjectOpenHashMap();
                    Int2ObjectOpenHashMap addToTick = new Int2ObjectOpenHashMap();
                    for (int i = 0; i < selectedTicks.size(); ++i) {
                        int tick = selectedTicks.getInt(i);
                        Keyframe keyframe = keyframeTrack.keyframesByTick.get(tick);
                        if (keyframe == null) continue;
                        int newTick = tick + grabMovementInfo.grabbedDelta;
                        if (grabMovementInfo.grabbedScalePivotTick >= 0) {
                            int tickDelta = tick - grabMovementInfo.grabbedScalePivotTick;
                            newTick = grabMovementInfo.grabbedScalePivotTick + Math.round((float)tickDelta * grabMovementInfo.grabbedScaleFactor);
                        }
                        if (tick != (newTick = Math.max(0, Math.min(totalTicks, newTick)))) {
                            Keyframe copied = keyframe.copy();
                            removeFromTick.put(tick, (Object)copied);
                            addToTick.put(newTick, (Object)copied);
                            ++movedKeyframes;
                        }
                        selectedKeyframes.keyframeTicks().add(newTick);
                    }
                    IntIterator intIterator = removeFromTick.keySet().iterator();
                    while (intIterator.hasNext()) {
                        int removeTick = (Integer)intIterator.next();
                        if (addToTick.containsKey(removeTick)) continue;
                        redo.add(new EditorSceneHistoryAction.RemoveKeyframe(keyframeTrack.keyframeType, trackIndex, removeTick));
                    }
                    for (Int2ObjectMap.Entry entry : addToTick.int2ObjectEntrySet()) {
                        redo.add(new EditorSceneHistoryAction.SetKeyframe(keyframeTrack.keyframeType, trackIndex, entry.getIntKey(), (Keyframe)entry.getValue()));
                        if (removeFromTick.containsKey(entry.getIntKey())) continue;
                        Keyframe existing = keyframeTrack.keyframesByTick.get(entry.getIntKey());
                        if (existing != null) {
                            undo.add(new EditorSceneHistoryAction.SetKeyframe(keyframeTrack.keyframeType, trackIndex, entry.getIntKey(), existing.copy()));
                            continue;
                        }
                        undo.add(new EditorSceneHistoryAction.RemoveKeyframe(keyframeTrack.keyframeType, trackIndex, entry.getIntKey()));
                    }
                    for (Int2ObjectMap.Entry entry : removeFromTick.int2ObjectEntrySet()) {
                        undo.add(new EditorSceneHistoryAction.SetKeyframe(keyframeTrack.keyframeType, trackIndex, entry.getIntKey(), (Keyframe)entry.getValue()));
                    }
                }
                editorScene.push(new EditorSceneHistoryEntry(undo, redo, I18n.get((String)"flashback.moved_n_keyframes", (Object[])new Object[]{movedKeyframes})));
                editorState.markDirty();
            }
            grabbedKeyframe = false;
        }
        if (pendingStepBackwardsTicks > 0 && !ImGui.isKeyDown(263)) {
            replayServer.goToReplayTick(Math.max(0, replayServer.getReplayTick() - pendingStepBackwardsTicks));
            replayServer.forceApplyKeyframes.set(true);
            pendingStepBackwardsTicks = 0;
        }
    }

    private static GrabMovementInfo calculateGrabMovementInfo(int totalTicks) {
        int grabbedDelta = 0;
        int grabbedScalePivotTick = -1;
        float grabbedScaleFactor = 0.0f;
        if (ImGui.isKeyDown(342) || ImGui.isKeyDown(346)) {
            int minTick = totalTicks;
            int maxTick = 0;
            for (SelectedKeyframes selectedKeyframes : selectedKeyframesList) {
                IntIterator iterator = selectedKeyframes.keyframeTicks().iterator();
                while (iterator.hasNext()) {
                    int tick = iterator.nextInt();
                    minTick = Math.min(minTick, tick);
                    maxTick = Math.max(maxTick, tick);
                }
            }
            if (minTick == maxTick) {
                ImGuiHelper.drawTooltip(I18n.get((String)"flashback.scale_percentage_label", (Object[])new Object[]{100}));
            } else {
                grabbedScalePivotTick = minTick == grabbedKeyframeTick || minTick == TimelineWindow.timelineXToReplayTick(grabbedKeyframeMouseX - x) ? maxTick : minTick;
                float pivotMouseX = x + (float)TimelineWindow.replayTickToTimelineX(grabbedScalePivotTick);
                grabbedScaleFactor = (mouseX - pivotMouseX) / (grabbedKeyframeMouseX - pivotMouseX);
                if (!Float.isFinite(grabbedScaleFactor)) {
                    grabbedScaleFactor = 1.0f;
                }
                ImGuiHelper.drawTooltip(I18n.get((String)"flashback.scale_percentage_label", (Object[])new Object[]{Math.round(grabbedScaleFactor * 100.0f)}));
            }
            return new GrabMovementInfo(0, grabbedScalePivotTick, grabbedScaleFactor);
        }
        if (grabbedKeyframeMouseX != mouseX || enableKeyframeMovement) {
            boolean isShiftDown;
            enableKeyframeMovement = true;
            grabbedDelta = TimelineWindow.timelineDeltaToReplayTickDelta(mouseX - grabbedKeyframeMouseX);
            boolean bl = isShiftDown = ImGui.isKeyDown(340) || ImGui.isKeyDown(344);
            if (isShiftDown) {
                int closestTick = -1;
                for (int i = 0; i < TimelineWindow.editorScene.keyframeTracks.size(); ++i) {
                    Integer ceil;
                    float ceilX;
                    if (i == grabbedKeyframeTrack) continue;
                    KeyframeTrack track = TimelineWindow.editorScene.keyframeTracks.get(i);
                    Integer floor = track.keyframesByTick.floorKey(grabbedKeyframeTick);
                    float floorX = floor == null ? Float.NaN : x + (float)TimelineWindow.replayTickToTimelineX(floor);
                    Integer closest = Utils.chooseClosest(mouseX, floorX, floor, ceilX = (ceil = track.keyframesByTick.ceilingKey(grabbedKeyframeTick)) == null ? Float.NaN : x + (float)TimelineWindow.replayTickToTimelineX(ceil), ceil, keyframeSize);
                    if (closest == null) continue;
                    if (closestTick == -1) {
                        closestTick = closest;
                        continue;
                    }
                    if (Math.abs(closest - grabbedKeyframeTick) >= Math.abs(closestTick - grabbedKeyframeTick)) continue;
                    closestTick = closest;
                }
                if (closestTick != -1) {
                    grabbedDelta = closestTick - grabbedKeyframeTick;
                }
            }
            Object tooltip = I18n.get((String)"flashback.tick_label", (Object[])new Object[]{grabbedKeyframeTick + grabbedDelta});
            tooltip = grabbedDelta >= 0 ? (String)tooltip + I18n.get((String)"flashback.tick_offset", (Object[])new Object[]{"+" + grabbedDelta}) : (String)tooltip + I18n.get((String)"flashback.tick_offset", (Object[])new Object[]{grabbedDelta});
            if (!isShiftDown && TimelineWindow.editorScene.keyframeTracks.size() > 1) {
                tooltip = (String)tooltip + "\n" + I18n.get((String)"flashback.hold_shift_to_snap", (Object[])new Object[0]);
            }
            ImGuiHelper.drawTooltip((String)tooltip);
        }
        return new GrabMovementInfo(grabbedDelta, grabbedScalePivotTick, grabbedScaleFactor);
    }

    private static void renderKeyframes(float x, float y, float mouseX, int minTicks, float availableTicks, int totalTicks) {
        float lineHeight = ImGui.getTextLineHeightWithSpacing() + ImGui.getStyle().getItemSpacingY();
        ImDrawList drawList = ImGui.getWindowDrawList();
        GrabMovementInfo grabMovementInfo = null;
        if (grabbedKeyframe) {
            grabMovementInfo = TimelineWindow.calculateGrabMovementInfo(totalTicks);
        }
        float timelineScale = availableTicks / timelineWidth;
        float minTimelineX = x + (float)middleX;
        float maxTimelineX = x + width;
        for (int trackIndex = 0; trackIndex < TimelineWindow.editorScene.keyframeTracks.size(); ++trackIndex) {
            Map.Entry<Integer, Keyframe> entry;
            KeyframeTrack keyframeTrack = TimelineWindow.editorScene.keyframeTracks.get(trackIndex);
            TreeMap<Integer, Keyframe> keyframeTimes = keyframeTrack.keyframesByTick;
            SelectedKeyframes selectedKeyframesForTrack = null;
            for (SelectedKeyframes selectedKeyframes : selectedKeyframesList) {
                if (selectedKeyframes.trackIndex() != trackIndex) continue;
                selectedKeyframesForTrack = selectedKeyframes;
                break;
            }
            int minKeyframeTick = minTicks - 10;
            if (!keyframeTrack.keyframeType.cullKeyframesInTimelineToTheLeft()) {
                minKeyframeTick = Integer.MIN_VALUE;
            }
            int tick = minKeyframeTick;
            while ((float)tick <= (float)minTicks + availableTicks + 10.0f && (entry = keyframeTimes.ceilingEntry(tick)) != null && !((float)entry.getKey().intValue() > (float)minTicks + availableTicks + 10.0f)) {
                Map.Entry<Integer, Keyframe> floorEntry;
                tick = entry.getKey();
                Keyframe keyframe = entry.getValue();
                float midY = y + 2.0f + ((float)trackIndex + 0.5f) * lineHeight;
                if (selectedKeyframesForTrack != null && selectedKeyframesForTrack.keyframeTicks().contains(tick)) {
                    int newTick = tick;
                    if (grabMovementInfo != null) {
                        newTick = tick + grabMovementInfo.grabbedDelta;
                        if (grabMovementInfo.grabbedScalePivotTick >= 0) {
                            int tickDelta = tick - grabMovementInfo.grabbedScalePivotTick;
                            newTick = grabMovementInfo.grabbedScalePivotTick + Math.round((float)tickDelta * grabMovementInfo.grabbedScaleFactor);
                        }
                        newTick = Math.max(0, Math.min(totalTicks, newTick));
                    }
                    int keyframeX = TimelineWindow.replayTickToTimelineX(newTick);
                    float midX = x + (float)keyframeX;
                    keyframe.drawOnTimeline(drawList, keyframeSize, midX, midY, keyframeTrack.enabled ? -16776961 : -2147483393, timelineScale, minTimelineX, maxTimelineX, tick, keyframeTimes);
                } else {
                    int keyframeX = TimelineWindow.replayTickToTimelineX(tick);
                    float midX = x + (float)keyframeX;
                    int colour = -1;
                    if (keyframeTrack.keyframeType == TimelapseKeyframeType.INSTANCE) {
                        if (keyframeTrack.keyframesByTick.size() == 1) {
                            colour = -15376385;
                            if (Math.abs(mouseX - midX) < (float)keyframeSize && Math.abs(mouseY - midY) < (float)keyframeSize) {
                                ImGuiHelper.drawTooltip(I18n.get((String)"flashback.timelapse_requires_two_keyframes", (Object[])new Object[0]));
                            }
                        } else {
                            Keyframe keyframe2;
                            Map.Entry<Integer, Keyframe> floorEntry2 = keyframeTrack.keyframesByTick.floorEntry(tick - 1);
                            if (floorEntry2 != null && (keyframe2 = floorEntry2.getValue()) instanceof TimelapseKeyframe) {
                                TimelapseKeyframe timelapseKeyframe = (TimelapseKeyframe)keyframe2;
                                if (timelapseKeyframe.ticks >= ((TimelapseKeyframe)keyframe).ticks) {
                                    colour = -15376385;
                                    if (Math.abs(mouseX - midX) < (float)keyframeSize && Math.abs(mouseY - midY) < (float)keyframeSize) {
                                        ImGuiHelper.drawTooltip(I18n.get((String)"flashback.timelapse_explanation", (Object[])new Object[0]));
                                    }
                                }
                            }
                        }
                    }
                    if (!keyframeTrack.enabled) {
                        colour &= 0xFFFFFF;
                        colour |= Integer.MIN_VALUE;
                    }
                    keyframe.drawOnTimeline(drawList, keyframeSize, midX, midY, colour, timelineScale, minTimelineX, maxTimelineX, tick, keyframeTimes);
                }
                if ((selectedKeyframesForTrack == null || grabMovementInfo == null) && keyframeTrack.keyframeType == TimelapseKeyframeType.INSTANCE && (floorEntry = keyframeTrack.keyframesByTick.floorEntry(tick - 1)) != null) {
                    String message;
                    TimelapseKeyframe left = (TimelapseKeyframe)floorEntry.getValue();
                    TimelapseKeyframe right = (TimelapseKeyframe)keyframe;
                    int leftX = TimelineWindow.replayTickToTimelineX(floorEntry.getKey());
                    int rightX = TimelineWindow.replayTickToTimelineX(tick);
                    int tickDelta = right.ticks - left.ticks;
                    int textColour = -1;
                    if (tickDelta <= 0) {
                        message = I18n.get((String)"flashback.invalid", (Object[])new Object[0]).toUpperCase(Locale.ROOT);
                        textColour = -15376385;
                    } else {
                        message = Utils.timeInTicksToString(tickDelta);
                    }
                    float textY = midY - lineHeight * 0.3f;
                    float midX = (float)(leftX + rightX) / 2.0f;
                    String durationMessage = I18n.get((String)"flashback.select_replay.duration", (Object[])new Object[]{message});
                    float textWidth = ImGuiHelper.calcTextWidth(durationMessage);
                    if (textWidth <= (float)(rightX - leftX)) {
                        ImGui.getWindowDrawList().addText(x + midX - textWidth / 2.0f, textY, textColour, durationMessage);
                    } else {
                        textWidth = ImGuiHelper.calcTextWidth(message);
                        if (textWidth <= (float)(rightX - leftX)) {
                            ImGui.getWindowDrawList().addText(x + midX - textWidth / 2.0f, textY, textColour, message);
                        }
                    }
                    float startLine1 = x + (float)leftX + (float)keyframeSize;
                    float endLine1 = x + midX - textWidth / 2.0f - 5.0f;
                    float startLine2 = x + midX + textWidth / 2.0f + 5.0f;
                    float endLine2 = x + (float)rightX - (float)keyframeSize;
                    if (startLine1 < endLine1) {
                        ImGui.getWindowDrawList().addLine(startLine1, midY, endLine1, midY, -2130706433);
                    }
                    if (startLine2 < endLine2) {
                        ImGui.getWindowDrawList().addLine(startLine2, midY, endLine2, midY, -2130706433);
                    }
                }
                ++tick;
            }
        }
    }

    private static void renderKeyframeElements(float x, float y, int cursorTicks, int middleX) {
        ImGui.setCursorScreenPos(x + 8.0f, y + 6.0f);
        float lineHeight = ImGui.getTextLineHeightWithSpacing() + ImGui.getStyle().getItemSpacingY();
        int keyframeTrackToDelete = -1;
        int keyframeTrackToClear = -1;
        ImDrawList drawList = ImGui.getWindowDrawList();
        float buttonSize = ImGui.getTextLineHeight();
        float spacingX = ImGui.getStyle().getItemSpacingX();
        boolean hasOpenPopup = false;
        double animationMultiplier = Math.pow(0.9, (double)renderDeltaNanos / 1.0E7);
        for (int trackIndex = 0; trackIndex < TimelineWindow.editorScene.keyframeTracks.size(); ++trackIndex) {
            KeyframeTrack keyframeTrack = TimelineWindow.editorScene.keyframeTracks.get(trackIndex);
            KeyframeType<?> keyframeType = keyframeTrack.keyframeType;
            ImGui.pushID(trackIndex);
            if (trackIndex == openCreateKeyframeAtTickTrack) {
                ImGui.openPopup("##CreateKeyframeAtTickPopup");
                openCreateKeyframeAtTickTrack = -1;
            }
            if (ImGuiHelper.beginPopup("##CreateKeyframeAtTickPopup")) {
                if (ImGui.menuItem(I18n.get((String)"flashback.create_keyframe_at_n", (Object[])new Object[]{createKeyframeAtTick}) + "##CreateKeyframeAtN")) {
                    ImGui.closeCurrentPopup();
                    ImGui.endPopup();
                    TimelineWindow.createNewKeyframe(trackIndex, createKeyframeAtTick, keyframeType, keyframeTrack);
                } else {
                    ImGui.endPopup();
                }
            }
            Iterator<Map.Entry<Integer, Keyframe>> icon = keyframeType.icon();
            String name = keyframeTrack.customName;
            if (name == null) {
                name = keyframeType.name();
            }
            Object nameWithIcon = name;
            if (icon != null) {
                nameWithIcon = (String)((Object)icon) + " " + name;
            }
            keyframeTrack.animatedOffsetInUi = (float)((double)keyframeTrack.animatedOffsetInUi * animationMultiplier);
            if (Math.abs(keyframeTrack.animatedOffsetInUi) < 1.0f) {
                keyframeTrack.animatedOffsetInUi = 0.0f;
            }
            float trackOffset = repositioningKeyframeTrack == trackIndex ? mouseY - dragStartMouseY : (float)((int)keyframeTrack.animatedOffsetInUi);
            ImGui.setCursorPosX(repositioningKeyframeTrack == trackIndex ? 3.0f : 2.0f);
            ImGui.setCursorPosY(ImGui.getCursorPosY() + trackOffset);
            if (keyframeTrack.customColour != 0) {
                int colour = keyframeTrack.customColour & 0xFFFFFF;
                colour |= 0x30000000;
                float rectY = y + 3.0f + (float)trackIndex * lineHeight;
                if (trackIndex == 0) {
                    rectY -= 1.0f;
                }
                drawList.addRectFilled(x + (float)middleX + 1.0f, rectY, x + width, y + 2.0f + (float)trackIndex * lineHeight + lineHeight, colour);
            }
            ImGui.pushStyleVar(14, 0.0f, 0.0f);
            if (repositioningKeyframeTrack == trackIndex) {
                ImGui.textUnformatted("\ue945");
            } else {
                ImGui.textDisabled("\ue945");
                if (ImGui.isItemClicked(0)) {
                    repositioningKeyframeTrack = trackIndex;
                }
            }
            ImGui.sameLine();
            ImGui.popStyleVar();
            if (keyframeTrack.nameEditField != null) {
                boolean returnValue;
                ImGui.pushStyleVar(14, 0.0f, 0.0f);
                ImGui.pushStyleVar(11, 0.0f, 0.0f);
                if (icon != null) {
                    ImGui.textUnformatted(icon + " ");
                    ImGui.sameLine();
                }
                ImGui.setNextItemWidth(120.0f);
                if (keyframeTrack.forceFocusTrack) {
                    keyframeTrack.forceFocusTrack = false;
                    ImGui.setKeyboardFocusHere();
                }
                if ((returnValue = ImGui.inputText("##TrackName", keyframeTrack.nameEditField, 4160)) || ImGui.isItemDeactivated()) {
                    keyframeTrack.customName = ImGuiHelper.getString(keyframeTrack.nameEditField).trim();
                    if (keyframeTrack.customName.isEmpty() || keyframeTrack.customName.equals(keyframeType.name())) {
                        keyframeTrack.customName = null;
                    }
                    keyframeTrack.nameEditField = null;
                }
                ImGui.popStyleVar(2);
            } else {
                if (keyframeTrack.enabled) {
                    if (keyframeTrack.customColour != 0) {
                        ImGui.textColored(keyframeTrack.customColour, (String)nameWithIcon);
                    } else {
                        ImGui.textUnformatted((String)nameWithIcon);
                    }
                } else {
                    ImGui.textDisabled((String)nameWithIcon);
                }
                if (ImGui.isItemClicked(0) && ImGui.isMouseDoubleClicked(0)) {
                    keyframeTrack.nameEditField = ImGuiHelper.createResizableImString(name);
                    keyframeTrack.forceFocusTrack = true;
                }
                if (ImGui.isItemClicked(1)) {
                    ImGui.openPopup("##TrackPopup");
                }
            }
            ImGui.sameLine();
            float buttonX = x + (float)middleX - (buttonSize + spacingX) * 3.0f;
            float buttonY = ImGui.getCursorScreenPosY();
            ImGui.setCursorPosX(buttonX - x);
            if (ImGui.invisibleButton("##TrackOptions", buttonSize, buttonSize) || ImGui.isItemClicked(1)) {
                ImGui.openPopup("##TrackPopup");
            }
            drawList.addText(buttonX - 2.0f, buttonY, -1, "\ue5d2");
            ImGuiHelper.tooltip(I18n.get((String)"flashback.open_track_options", (Object[])new Object[0]));
            ImGui.sameLine();
            ImGui.setCursorPosX((buttonX += buttonSize + spacingX) - x);
            ImGui.invisibleButton("##ToggleEnabled", buttonSize, buttonSize);
            if (ImGui.isItemHovered(trackDisabledButtonDrag ? 128 : 0)) {
                if (trackDisabledButtonDrag) {
                    keyframeTrack.enabled = trackDisabledButtonDragValue;
                    editorState.markDirty();
                } else if (ImGui.isMouseClicked(0, false)) {
                    keyframeTrack.enabled = !keyframeTrack.enabled;
                    editorState.markDirty();
                    trackDisabledButtonDragValue = keyframeTrack.enabled;
                    trackDisabledButtonDrag = true;
                }
            }
            if (keyframeTrack.enabled) {
                drawList.addText(buttonX - 2.0f, buttonY, -1, "\ue8f4");
                ImGuiHelper.tooltip(I18n.get((String)"flashback.disable_keyframe_track", (Object[])new Object[0]));
            } else {
                drawList.addText(buttonX - 2.0f, buttonY, -1, "\ue8f5");
                ImGuiHelper.tooltip(I18n.get((String)"flashback.enable_keyframe_track", (Object[])new Object[0]));
            }
            if (keyframeTrack.keyframeType.canBeCreatedNormally()) {
                ImGui.sameLine();
                ImGui.setCursorPosX((buttonX += buttonSize + spacingX) - x);
                if (ImGui.invisibleButton("##Add", buttonSize, buttonSize)) {
                    TimelineWindow.createNewKeyframe(trackIndex, cursorTicks, keyframeType, keyframeTrack);
                    if (keyframeType instanceof CameraKeyframeType && Minecraft.getInstance().player != Minecraft.getInstance().getCameraEntity()) {
                        ReplayUI.setInfoOverlay(I18n.get((String)"flashback.camera_keyframes_not_needed", (Object[])new Object[0]));
                        Minecraft.getInstance().getConnection().sendCommand("spectate");
                    }
                }
                drawList.addText(buttonX - 2.0f, buttonY, -1, "\ue148");
                ImGuiHelper.tooltip(I18n.get((String)"flashback.add_keyframe", (Object[])new Object[0]));
            }
            if (ImGuiHelper.beginPopup("##CreateKeyframe")) {
                if (createKeyframeWithPopup != null) {
                    hasOpenPopup = true;
                    Object keyframe = createKeyframeWithPopup.render();
                    if (keyframe != null) {
                        TimelineWindow.upgradeToSceneWrite();
                        editorScene.setKeyframe(trackIndex, createKeyframeWithPopupTick, (Keyframe)keyframe);
                        editorState.markDirty();
                        ImGui.closeCurrentPopup();
                    }
                } else {
                    ImGui.closeCurrentPopup();
                }
                ImGui.endPopup();
            }
            boolean openTrackColourPopup = false;
            if (ImGuiHelper.beginPopup("##TrackPopup")) {
                if (ImGui.menuItem("\ue3c9 " + I18n.get((String)"flashback.rename", (Object[])new Object[0]))) {
                    keyframeTrack.nameEditField = ImGuiHelper.createResizableImString(name);
                    keyframeTrack.forceFocusTrack = true;
                }
                if (ImGui.menuItem("\ue40a " + I18n.get((String)"flashback.set_colour", (Object[])new Object[0]))) {
                    openTrackColourPopup = true;
                }
                if (ImGui.menuItem("\ue872 " + I18n.get((String)"flashback.delete_track", (Object[])new Object[0]))) {
                    keyframeTrackToDelete = trackIndex;
                }
                if (ImGui.menuItem("\ue14a " + I18n.get((String)"flashback.clear_keyframes", (Object[])new Object[0]))) {
                    keyframeTrackToClear = trackIndex;
                }
                ImGui.endPopup();
            }
            if (openTrackColourPopup) {
                ImGui.openPopup("##SetTrackColour");
            }
            if (ImGuiHelper.beginPopup("##SetTrackColour")) {
                if (ImGui.button(I18n.get((String)"flashback.reset_to_default", (Object[])new Object[0]) + "##ResetToDefault")) {
                    keyframeTrack.customColour = 0;
                    ImGui.closeCurrentPopup();
                } else {
                    int colour = keyframeTrack.customColour;
                    if (colour == 0) {
                        colour = ImGui.getColorU32(0);
                    }
                    ImVec4 imVec4 = new ImVec4();
                    ImGui.colorConvertU32ToFloat4(imVec4, colour);
                    float[] colourArray = new float[]{imVec4.x, imVec4.y, imVec4.z};
                    if (ImGui.colorPicker3(I18n.get((String)"flashback.track_colour", (Object[])new Object[0]), colourArray)) {
                        keyframeTrack.customColour = ImGui.colorConvertFloat4ToU32(colourArray[0], colourArray[1], colourArray[2], 1.0f);
                    }
                }
                ImGui.endPopup();
            }
            ImGui.setCursorPosY(ImGui.getCursorPosY() - trackOffset);
            ImGui.separator();
            ImGui.popID();
        }
        if (!hasOpenPopup) {
            createKeyframeWithPopup = null;
            createKeyframeWithPopupTick = cursorTicks;
        }
        openCreateKeyframeAtTickTrack = -1;
        if (keyframeTrackToDelete >= 0) {
            TimelineWindow.upgradeToSceneWrite();
            if (keyframeTrackToDelete < TimelineWindow.editorScene.keyframeTracks.size()) {
                ArrayList<EditorSceneHistoryAction> undo = new ArrayList<EditorSceneHistoryAction>();
                redo = new ArrayList<EditorSceneHistoryAction>();
                keyframeTrack = TimelineWindow.editorScene.keyframeTracks.get(keyframeTrackToDelete);
                undo.add(new EditorSceneHistoryAction.AddTrack(keyframeTrack.keyframeType, keyframeTrackToDelete));
                for (Map.Entry<Integer, Keyframe> entry : keyframeTrack.keyframesByTick.entrySet()) {
                    undo.add(new EditorSceneHistoryAction.SetKeyframe(keyframeTrack.keyframeType, keyframeTrackToDelete, entry.getKey(), entry.getValue().copy()));
                }
                redo.add(new EditorSceneHistoryAction.RemoveTrack(keyframeTrack.keyframeType, keyframeTrackToDelete));
                editorScene.push(new EditorSceneHistoryEntry(undo, redo, I18n.get((String)"flashback.delete_named_track", (Object[])new Object[]{keyframeTrack.keyframeType.name()})));
                editorState.markDirty();
                selectedKeyframesList.clear();
            }
        } else if (keyframeTrackToClear >= 0) {
            TimelineWindow.upgradeToSceneWrite();
            if (keyframeTrackToClear < TimelineWindow.editorScene.keyframeTracks.size()) {
                ArrayList<EditorSceneHistoryAction> undo = new ArrayList<EditorSceneHistoryAction>();
                redo = new ArrayList();
                keyframeTrack = TimelineWindow.editorScene.keyframeTracks.get(keyframeTrackToClear);
                for (Map.Entry<Integer, Keyframe> entry : keyframeTrack.keyframesByTick.entrySet()) {
                    undo.add(new EditorSceneHistoryAction.SetKeyframe(keyframeTrack.keyframeType, keyframeTrackToClear, entry.getKey(), entry.getValue().copy()));
                    redo.add(new EditorSceneHistoryAction.RemoveKeyframe(keyframeTrack.keyframeType, keyframeTrackToClear, entry.getKey()));
                }
                editorScene.push(new EditorSceneHistoryEntry(undo, redo, I18n.get((String)"flashback.clear_named_track", (Object[])new Object[]{keyframeTrack.keyframeType.name()})));
                editorState.markDirty();
                selectedKeyframesList.clear();
            }
        }
        ImGui.setCursorPosX(8.0f);
        if (ImGui.smallButton(I18n.get((String)"flashback.add_element", (Object[])new Object[0]) + "##AddElement")) {
            ImGui.openPopup("##AddKeyframeElement");
        }
        ImGui.sameLine();
        boolean openNewScenePopup = false;
        boolean openRenameScenePopup = false;
        boolean openDeleteScenePopup = false;
        List<EditorScene> scenes = editorState.getScenes(editorSceneStamp);
        ImGui.setNextItemWidth((float)middleX - ImGui.getCursorPosX() - spacingX);
        ImGui.pushStyleVar(11, 4.0f, 0.0f);
        if (ImGui.beginCombo("##SceneSwitcher", TimelineWindow.editorScene.name, 16)) {
            if (ImGui.menuItem("\ue148 " + I18n.get((String)"flashback.new_scene", (Object[])new Object[0]))) {
                openNewScenePopup = true;
            }
            if (ImGui.menuItem("\ue3c9 " + I18n.get((String)"flashback.rename", (Object[])new Object[0]))) {
                openRenameScenePopup = true;
            }
            if (scenes.size() > 1 && TimelineWindow.editorScene.keyframeTracks.isEmpty() && ImGui.menuItem("\ue92b " + I18n.get((String)"flashback.delete_forever", (Object[])new Object[0]))) {
                openDeleteScenePopup = true;
            }
            ImGui.separator();
            for (int i = 0; i < scenes.size(); ++i) {
                boolean selected;
                EditorScene otherScene = scenes.get(i);
                ImGui.pushID(i);
                boolean bl = selected = i == editorState.getSceneIndex();
                if (ImGui.selectable(otherScene.name, selected) && !selected) {
                    TimelineWindow.upgradeToSceneWrite();
                    editorState.setSceneIndex(i, editorSceneStamp);
                }
                if (selected) {
                    ImGui.setItemDefaultFocus();
                }
                ImGui.popID();
            }
            ImGui.endCombo();
        }
        ImGui.popStyleVar();
        ImGui.dummy(0.0f, lineHeight / 4.0f);
        if (openNewScenePopup) {
            ImGui.openPopup("##NewScene");
            sceneNameString = ImGuiHelper.createResizableImString(I18n.get((String)"flashback.default_scene_name", (Object[])new Object[]{scenes.size() + 1}));
        }
        if (ImGuiHelper.beginPopup("##NewScene")) {
            String sceneName;
            if (openNewScenePopup) {
                ImGui.setKeyboardFocusHere();
            }
            ImGui.inputText(I18n.get((String)"flashback.name", (Object[])new Object[0]), sceneNameString);
            if ((ImGui.button(I18n.get((String)"flashback.create", (Object[])new Object[0])) || ReplayUI.consumeConfirm()) && !(sceneName = ImGuiHelper.getString(sceneNameString).trim()).isEmpty()) {
                TimelineWindow.upgradeToSceneWrite();
                scenes.add(new EditorScene(sceneName));
                editorState.setSceneIndex(scenes.size() - 1, editorSceneStamp);
                editorState.markDirty();
                ImGui.closeCurrentPopup();
            }
            ImGui.sameLine();
            if (ImGui.button(I18n.get((String)"gui.cancel", (Object[])new Object[0])) || ReplayUI.consumeCancel()) {
                ImGui.closeCurrentPopup();
            }
            ImGui.endPopup();
        }
        if (openRenameScenePopup) {
            ImGui.openPopup("##RenameScene");
            sceneNameString = ImGuiHelper.createResizableImString(TimelineWindow.editorScene.name);
        }
        if (ImGuiHelper.beginPopup("##RenameScene")) {
            String sceneName;
            if (openRenameScenePopup) {
                ImGui.setKeyboardFocusHere();
            }
            ImGui.inputText(I18n.get((String)"flashback.name", (Object[])new Object[0]), sceneNameString);
            if ((ImGui.button(I18n.get((String)"flashback.rename", (Object[])new Object[0])) || ReplayUI.consumeConfirm()) && !(sceneName = ImGuiHelper.getString(sceneNameString).trim()).isEmpty()) {
                TimelineWindow.upgradeToSceneWrite();
                TimelineWindow.editorScene.name = sceneName;
                editorState.markDirty();
                ImGui.closeCurrentPopup();
            }
            ImGui.sameLine();
            if (ImGui.button(I18n.get((String)"gui.cancel", (Object[])new Object[0])) || ReplayUI.consumeCancel()) {
                ImGui.closeCurrentPopup();
            }
            ImGui.endPopup();
        }
        if (openDeleteScenePopup) {
            ImGui.openPopup("##DeleteScene");
        }
        if (ImGuiHelper.beginPopup("##DeleteScene")) {
            if (scenes.size() > 1 && TimelineWindow.editorScene.keyframeTracks.isEmpty()) {
                ImGui.textUnformatted(I18n.get((String)"flashback.delete_scene_confirm1", (Object[])new Object[0]));
                ImGui.textUnformatted(I18n.get((String)"flashback.delete_scene_confirm2", (Object[])new Object[0]));
                ImGui.textUnformatted(I18n.get((String)"flashback.delete_scene_confirm3", (Object[])new Object[0]));
                if (ImGui.button(I18n.get((String)"flashback.delete_forever", (Object[])new Object[0]))) {
                    TimelineWindow.upgradeToSceneWrite();
                    int sceneIndex = editorState.getSceneIndex();
                    scenes.remove(sceneIndex);
                    if (sceneIndex >= scenes.size()) {
                        editorState.setSceneIndex(scenes.size() - 1, editorSceneStamp);
                    }
                    editorState.markDirty();
                    ImGui.closeCurrentPopup();
                }
                ImGui.sameLine();
                if (ImGui.button(I18n.get((String)"gui.cancel", (Object[])new Object[0])) || ReplayUI.consumeCancel()) {
                    ImGui.closeCurrentPopup();
                }
            } else {
                ImGui.closeCurrentPopup();
            }
            ImGui.endPopup();
        }
        if (ImGuiHelper.beginPopup("##AddKeyframeElement")) {
            for (KeyframeType<?> type : KeyframeRegistry.getTypes()) {
                if (!type.canBeCreatedNormally() || !ImGui.selectable(type.name())) continue;
                TimelineWindow.upgradeToSceneWrite();
                ArrayList<EditorSceneHistoryAction> undo = new ArrayList<EditorSceneHistoryAction>();
                ArrayList<EditorSceneHistoryAction> redo = new ArrayList<EditorSceneHistoryAction>();
                int index = TimelineWindow.editorScene.keyframeTracks.size();
                undo.add(new EditorSceneHistoryAction.RemoveTrack(type, index));
                redo.add(new EditorSceneHistoryAction.AddTrack(type, index));
                editorScene.push(new EditorSceneHistoryEntry(undo, redo, I18n.get((String)"flashback.create_named_track", (Object[])new Object[]{type.name()})));
                editorState.markDirty();
                ImGui.closeCurrentPopup();
                if (type != CameraKeyframeType.INSTANCE) continue;
                lastKeyframeTrackCameraWasAddedTo = TimelineWindow.editorScene.keyframeTracks.get(index);
            }
            ImGui.endPopup();
        }
    }

    private static void createNewKeyframe(int trackIndex, int tick, KeyframeType<?> keyframeType, KeyframeTrack keyframeTrack) {
        if (!keyframeType.canBeCreatedNormally()) {
            return;
        }
        TimelineWindow.upgradeToSceneWrite();
        Object keyframe = keyframeType.createDirect();
        if (keyframe != null) {
            if (keyframeType == CameraKeyframeType.INSTANCE) {
                lastKeyframeTrackCameraWasAddedTo = TimelineWindow.editorScene.keyframeTracks.get(trackIndex);
            }
            editorScene.setKeyframe(trackIndex, tick, (Keyframe)keyframe);
            editorState.markDirty();
        } else if (keyframeType == TimelapseKeyframeType.INSTANCE && keyframeTrack.keyframesByTick.isEmpty()) {
            editorScene.setKeyframe(trackIndex, tick, new TimelapseKeyframe(0));
            editorState.markDirty();
        } else {
            createKeyframeWithPopup = keyframeType.createPopup();
            if (createKeyframeWithPopup != null) {
                ImGui.openPopup("##CreateKeyframe");
                createKeyframeWithPopupTick = tick;
            }
        }
    }

    private static void renderSeparators(int minorsPerMajor, float x, int middleX, float minorSeparatorWidth, int errorOffset, float width, ImDrawList drawList, float y, int timestampHeight, int middleY, int minTicks, int ticksPerMinor, boolean showSubSeconds, int majorSeparatorHeight, int minorSeparatorHeight) {
        float h;
        int hi;
        int minor = -minorsPerMajor;
        while (!((float)(hi = (int)((h = x + (float)middleX + minorSeparatorWidth * (float)minor) + (float)errorOffset)) >= x + width - 1.0f)) {
            if (minor % minorsPerMajor == 0) {
                drawList.addLine(hi, y + (float)timestampHeight, hi, y + (float)middleY, -1);
                int ticks = minTicks + minor * ticksPerMinor;
                String timestamp = TimelineWindow.ticksToTimestamp(ticks);
                drawList.addText(hi, y, -1, timestamp);
                if (showSubSeconds) {
                    float timestampWidth = ImGuiHelper.calcTextWidth(timestamp);
                    drawList.addText(hi + (int)Math.ceil(timestampWidth), y, -8355712, "/" + ticks % 20);
                }
            } else {
                drawList.addLine(hi, y + (float)timestampHeight + (float)(majorSeparatorHeight - minorSeparatorHeight), hi, y + (float)middleY, -1);
            }
            ++minor;
        }
    }

    private static void renderPlaybackHead(int cursorX, float x, int middleX, float width, int cursorTicks, int currentReplayTick, ImDrawList drawList, float y, int middleY, int timestampHeight, float height, int zoomBarHeight) {
        if (cursorX > middleX - 10 && (float)cursorX < width + 10.0f) {
            int colour = -1;
            if (cursorTicks < currentReplayTick) {
                colour = -2130706433;
            }
            int size = ReplayUI.scaleUi(5);
            drawList.addTriangleFilled(x + (float)cursorX, y + (float)middleY, x + (float)cursorX - (float)(size * 2), y + (float)timestampHeight + (float)size, x + (float)cursorX + (float)(size * 2), y + (float)timestampHeight + (float)size, colour);
            drawList.addRectFilled(x + (float)cursorX - 1.0f, y + (float)middleY - 2.0f, x + (float)cursorX + 1.0f, y + height - (float)zoomBarHeight, colour);
        }
    }

    private static int replayTickToTimelineX(int tick) {
        return timelineOffset + (int)((float)(tick - minTicks) / availableTicks * timelineWidth);
    }

    private static int timelineXToReplayTick(float x) {
        float relativeX = x - (float)timelineOffset;
        float amount = Math.max(0.0f, Math.min(1.0f, relativeX / timelineWidth));
        int numTicks = Math.round(amount * availableTicks);
        return minTicks + numTicks;
    }

    private static int timelineDeltaToReplayTickDelta(float x) {
        return Math.round(x / timelineWidth * availableTicks);
    }

    private static String ticksToTimestamp(int ticks) {
        int seconds = ticks / 20;
        int minutes = seconds / 60;
        int hours = minutes / 60;
        if (hours == 0) {
            return String.format("%02d:%02d", minutes, seconds % 60);
        }
        return String.format("%02d:%02d:%02d", hours, minutes % 60, seconds % 60);
    }

    static {
        zoomMinBeforeDrag = 0.0;
        zoomMaxBeforeDrag = 1.0;
        grabbedZoomBar = false;
        grabbedZoomBarResizeLeft = false;
        grabbedZoomBarResizeRight = false;
        grabbedExportBarResizeLeft = false;
        grabbedExportBarResizeRight = false;
        grabbedPlayback = false;
        grabbedKeyframe = false;
        grabbedKeyframeMouseX = 0.0f;
        enableKeyframeMovement = false;
        grabbedKeyframeTick = 0;
        grabbedKeyframeTrack = 0;
        draggingMouseButton = 0;
        repositioningKeyframeTrack = 0;
        dragStartMouseX = 0.0f;
        dragStartMouseY = 0.0f;
        trackDisabledButtonDrag = false;
        trackDisabledButtonDragValue = false;
        dragSelectOrigin = null;
        createKeyframeWithPopup = null;
        createKeyframeWithPopupTick = 0;
        sceneNameString = null;
        copyRelativeToPosition = false;
        copyRelativeToYaw = false;
        copyRelativeToPitch = false;
        pendingStepBackwardsTicks = 0;
        lastRenderNanos = 0L;
        renderDeltaNanos = 0L;
        minorSeparatorHeight = 10;
        majorSeparatorHeight = minorSeparatorHeight * 2;
        timestampHeight = 20;
        middleY = timestampHeight + majorSeparatorHeight;
        middleX = 240;
        keyframeSize = 10;
        selectedKeyframesList = new ArrayList<SelectedKeyframes>();
        editingKeyframeTrack = 0;
        editingKeyframeTick = 0;
        createKeyframeAtTick = 0;
        openCreateKeyframeAtTickTrack = -1;
        replayTickSpeeds = new float[]{1.0f, 2.0f, 4.0f, 10.0f, 20.0f, 40.0f, 100.0f, 200.0f, 400.0f};
        lastKeyframeTrackCameraWasAddedTo = null;
    }

    record GrabMovementInfo(int grabbedDelta, int grabbedScalePivotTick, float grabbedScaleFactor) {
    }
}

