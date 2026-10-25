/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.culling.Frustum
 *  net.minecraft.client.renderer.debug.DebugRenderer$SimpleDebugRenderer
 *  net.minecraft.gizmos.GizmoStyle
 *  net.minecraft.gizmos.Gizmos
 *  net.minecraft.util.debug.DebugValueAccess
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 */
package com.moulberry.flashback.visuals;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.editor.ui.ReplayUI;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import java.util.Objects;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.gizmos.GizmoStyle;
import net.minecraft.gizmos.Gizmos;
import net.minecraft.util.debug.DebugValueAccess;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class FlashbackDebugRenderer
implements DebugRenderer.SimpleDebugRenderer {
    private final Minecraft minecraft;

    public FlashbackDebugRenderer(Minecraft minecraft) {
        this.minecraft = minecraft;
    }

    public void emitGizmos(double d, double e, double f, DebugValueAccess debugValueAccess, Frustum frustum, float partialTick) {
        UUID audioSource;
        if (!Flashback.isInReplay() || this.minecraft.level == null) {
            return;
        }
        EditorState editorState = EditorStateManager.getCurrent();
        if (editorState == null) {
            return;
        }
        if (Flashback.isExporting() || !ReplayUI.isActive()) {
            return;
        }
        UUID selected = ReplayUI.getSelectedEntity();
        if (selected != null) {
            this.boundingBox(selected, -256, partialTick);
        }
        if ((audioSource = editorState.audioSourceEntity) != null && !Objects.equals(audioSource, selected)) {
            this.boundingBox(audioSource, -16711681, partialTick);
        }
        if (editorState.maybeHasHiddenEntities()) {
            for (Entity entity : this.minecraft.level.entitiesForRendering()) {
                if (!editorState.isEntityHidden(entity) || entity == this.minecraft.player) continue;
                this.boundingBox(entity, 0x30FFFFFF, partialTick);
            }
        }
    }

    private void boundingBox(UUID uuid, int colour, float partialTick) {
        Entity entity = this.minecraft.level.getEntity(uuid);
        if (entity != null) {
            this.boundingBox(entity, colour, partialTick);
        }
    }

    private void boundingBox(Entity entity, int colour, float partialTick) {
        Vec3 position = entity.position();
        Vec3 interpPosition = entity.getPosition(partialTick);
        Vec3 interpDelta = interpPosition.subtract(position);
        Gizmos.cuboid((AABB)entity.getBoundingBox().move(interpDelta), (GizmoStyle)GizmoStyle.stroke((int)colour));
    }
}

