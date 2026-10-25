/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.pipeline.ColorTargetState
 *  com.mojang.blaze3d.pipeline.RenderPipeline
 *  com.mojang.blaze3d.pipeline.RenderPipeline$Snippet
 *  com.mojang.blaze3d.shaders.UniformType
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  net.minecraft.client.renderer.RenderPipelines
 *  net.minecraft.resources.Identifier
 */
package com.moulberry.flashback.visuals;

import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.shaders.UniformType;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.Optional;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

public class ShaderManager {
    public static final RenderPipeline BLIT_SCREEN = RenderPipelines.register((RenderPipeline)RenderPipeline.builder((RenderPipeline.Snippet[])new RenderPipeline.Snippet[0]).withLocation(Identifier.fromNamespaceAndPath((String)"flashback", (String)"pipeline/blit_screen")).withVertexShader("core/screenquad").withFragmentShader("core/blit_screen").withSampler("InSampler").withDepthStencilState(Optional.empty()).withCull(false).withVertexFormat(DefaultVertexFormat.EMPTY, VertexFormat.Mode.TRIANGLES).build());
    public static final RenderPipeline BLIT_SCREEN_WITH_UV = RenderPipelines.register((RenderPipeline)RenderPipeline.builder((RenderPipeline.Snippet[])new RenderPipeline.Snippet[0]).withLocation(Identifier.fromNamespaceAndPath((String)"flashback", (String)"pipeline/blit_screen_with_uv")).withVertexShader(Identifier.fromNamespaceAndPath((String)"flashback", (String)"core/blit_screen_old")).withFragmentShader(Identifier.fromNamespaceAndPath((String)"flashback", (String)"core/blit_screen_old")).withSampler("InSampler").withDepthStencilState(Optional.empty()).withCull(false).withUniform("DynamicTransforms", UniformType.UNIFORM_BUFFER).withVertexFormat(DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS).build());
    public static RenderPipeline BLIT_SCREEN_ROUND_ALPHA = RenderPipelines.register((RenderPipeline)RenderPipeline.builder((RenderPipeline.Snippet[])new RenderPipeline.Snippet[0]).withLocation(Identifier.fromNamespaceAndPath((String)"flashback", (String)"pipeline/flashback_blit_screen_flip")).withVertexShader("core/screenquad").withFragmentShader(Identifier.fromNamespaceAndPath((String)"flashback", (String)"core/blit_screen_round_alpha")).withSampler("InSampler").withDepthStencilState(Optional.empty()).withCull(false).withVertexFormat(DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS).build());
    public static RenderPipeline BLIT_SCREEN_FLIP = RenderPipelines.register((RenderPipeline)RenderPipeline.builder((RenderPipeline.Snippet[])new RenderPipeline.Snippet[0]).withLocation(Identifier.fromNamespaceAndPath((String)"flashback", (String)"pipeline/flashback_blit_screen_flip")).withVertexShader(Identifier.fromNamespaceAndPath((String)"flashback", (String)"core/screenquad_flip")).withFragmentShader("core/blit_screen").withSampler("InSampler").withDepthStencilState(Optional.empty()).withCull(false).withVertexFormat(DefaultVertexFormat.EMPTY, VertexFormat.Mode.TRIANGLES).build());
    public static final RenderPipeline BLIT_TRANSFORM_DEPTH = RenderPipelines.register((RenderPipeline)RenderPipeline.builder((RenderPipeline.Snippet[])new RenderPipeline.Snippet[0]).withLocation(Identifier.fromNamespaceAndPath((String)"flashback", (String)"pipeline/flashback_blit_depth")).withVertexShader(Identifier.fromNamespaceAndPath((String)"flashback", (String)"core/screenquad_flip")).withFragmentShader(Identifier.fromNamespaceAndPath((String)"flashback", (String)"core/blit_transform_depth")).withColorTargetState(new ColorTargetState(Optional.empty(), 15)).withSampler("InSampler").withUniform("TransformDepth", UniformType.UNIFORM_BUFFER).withDepthStencilState(Optional.empty()).withCull(false).withVertexFormat(DefaultVertexFormat.EMPTY, VertexFormat.Mode.TRIANGLES).build());
}

