/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.buffers.GpuBuffer
 *  com.mojang.blaze3d.buffers.Std140Builder
 *  com.mojang.blaze3d.buffers.Std140SizeCalculator
 *  com.mojang.blaze3d.systems.RenderSystem
 *  org.joml.Matrix4fc
 *  org.lwjgl.system.MemoryStack
 */
package com.moulberry.flashback.exporting;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.buffers.Std140SizeCalculator;
import com.mojang.blaze3d.systems.RenderSystem;
import java.nio.ByteBuffer;
import org.joml.Matrix4fc;
import org.lwjgl.system.MemoryStack;

public class TransformDepthUniform
implements AutoCloseable {
    public static final int UBO_SIZE = new Std140SizeCalculator().putInt().putFloat().putFloat().get();
    private boolean lastZZeroToOne = false;
    private float lastNear = 0.0f;
    private float lastFar = 0.0f;
    private GpuBuffer buffer = null;

    public GpuBuffer getOrUpdate(Matrix4fc projection) {
        float far;
        float near;
        boolean isZZeroToOne;
        if (RenderSystem.getDevice().isZZeroToOne()) {
            isZZeroToOne = true;
            near = projection.m32() / projection.m22();
            far = projection.m32() / (projection.m22() - projection.m23());
        } else {
            isZZeroToOne = false;
            near = projection.perspectiveNear();
            far = projection.perspectiveFar();
        }
        if (isZZeroToOne != this.lastZZeroToOne || near != this.lastNear || far != this.lastFar || this.buffer == null) {
            this.lastZZeroToOne = isZZeroToOne;
            this.lastNear = near;
            this.lastFar = far;
            if (this.buffer == null) {
                this.buffer = RenderSystem.getDevice().createBuffer(() -> "Flashback Transform Depth UBO", 136, (long)UBO_SIZE);
            }
            try (MemoryStack stack = MemoryStack.stackPush();){
                ByteBuffer data = Std140Builder.onStack((MemoryStack)stack, (int)UBO_SIZE).putInt(isZZeroToOne ? 1 : 0).putFloat(near).putFloat(far).get();
                RenderSystem.getDevice().createCommandEncoder().writeToBuffer(this.buffer.slice(), data);
            }
        }
        return this.buffer;
    }

    @Override
    public void close() {
        if (this.buffer != null) {
            this.buffer.close();
            this.buffer = null;
        }
        this.lastZZeroToOne = false;
        this.lastNear = 0.0f;
        this.lastFar = 0.0f;
    }
}

