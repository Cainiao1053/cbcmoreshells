package com.cainiao1053.cbcmoreshells.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Entity;

@FunctionalInterface
public interface ExtraEntityLayer {
	void render(PoseStack poseStack, MultiBufferSource buffer, Entity entity, float partialTicks);
}