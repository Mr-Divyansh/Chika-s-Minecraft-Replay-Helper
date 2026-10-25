/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.PoseStack$Pose
 *  net.minecraft.world.phys.Vec3
 *  org.joml.Matrix4f
 *  org.joml.Matrix4fc
 */
package com.moulberry.flashback.visuals;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;

public class Shapes {
    public static void line(BufferBuilder bufferBuilder, PoseStack.Pose pose, Vec3 from, Vec3 to) {
        Shapes.line(bufferBuilder, pose, 1.0f, 1.0f, 1.0f, 1.0f, (float)from.x, (float)from.y, (float)from.z, (float)to.x, (float)to.y, (float)to.z);
    }

    public static void line(BufferBuilder bufferBuilder, PoseStack.Pose pose, Vec3 from, Vec3 to, float red, float green, float blue, float alpha) {
        Shapes.line(bufferBuilder, pose, red, green, blue, alpha, (float)from.x, (float)from.y, (float)from.z, (float)to.x, (float)to.y, (float)to.z);
    }

    public static void line(BufferBuilder bufferBuilder, PoseStack.Pose pose, float red, float green, float blue, float alpha, float fromX, float fromY, float fromZ, float toX, float toY, float toZ) {
        float dx = toX - fromX;
        float dy = toY - fromY;
        float dz = toZ - fromZ;
        float distanceInv = 1.0f / (float)Math.sqrt(dx * dx + dy * dy + dz * dz);
        Matrix4f transform = pose.pose();
        bufferBuilder.addVertex((Matrix4fc)transform, fromX, fromY, fromZ).setColor(red, green, blue, alpha).setNormal(pose, dx *= distanceInv, dy *= distanceInv, dz *= distanceInv).setLineWidth(2.0f);
        bufferBuilder.addVertex((Matrix4fc)transform, toX, toY, toZ).setColor(red, green, blue, alpha).setNormal(pose, dx, dy, dz).setLineWidth(2.0f);
    }
}

