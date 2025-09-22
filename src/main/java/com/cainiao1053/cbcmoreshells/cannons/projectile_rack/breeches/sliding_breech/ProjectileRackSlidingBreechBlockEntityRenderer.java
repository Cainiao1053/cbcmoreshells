package com.cainiao1053.cbcmoreshells.cannons.projectile_rack.breeches.sliding_breech;

//import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.breeches.quick_firing_breech.TorpQuickfiringBreechBlock;
import com.cainiao1053.cbcmoreshells.cannons.projectile_rack.breeches.quick_firing_breech.ProjectileRackQuickfiringBreechBlock;
import com.jozufozu.flywheel.backend.Backend;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import rbasamoyai.createbigcannons.CBCClientCommon;
//import rbasamoyai.createbigcannons.cannons.big_cannons.breeches.quickfiring_breech.QuickfiringBreechBlock;

public class ProjectileRackSlidingBreechBlockEntityRenderer extends KineticBlockEntityRenderer<ProjectileRackSlidingBreechBlockEntity> {

	public ProjectileRackSlidingBreechBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	protected void renderSafe(ProjectileRackSlidingBreechBlockEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		super.renderSafe(te, partialTicks, ms, buffer, light, overlay);

		BlockState blockState = te.getBlockState();

		if (Backend.canUseInstancing(te.getLevel())) return;

		Direction facing = blockState.getValue(BlockStateProperties.FACING);
		Direction.Axis axis = CBCClientCommon.getRotationAxis(blockState);
		Direction blockRotation = facing.getCounterClockWise(axis);
		if (blockRotation == Direction.DOWN) blockRotation = Direction.UP;

		Quaternionf qrot;

		boolean alongFirst = blockState.getValue(ProjectileRackQuickfiringBreechBlock.AXIS);
		if (facing.getAxis().isHorizontal() && !alongFirst) {
			Direction rotDir = facing.getAxis() == Direction.Axis.X ? Direction.UP : Direction.EAST;
			qrot = Axis.of(rotDir.step()).rotationDegrees(90f);
		} else if (facing.getAxis() == Direction.Axis.X && alongFirst) {
			qrot = Axis.of(blockRotation.step()).rotationDegrees(90f);
		} else {
			qrot = Axis.of(blockRotation.step()).rotationDegrees(0);
		}

		float renderedBreechblockOffset = te.getRenderedBlockOffset(partialTicks);
		renderedBreechblockOffset = renderedBreechblockOffset / 16.0f * 13.0f;
		Vector3f normal = blockRotation.step();
		normal.mul(renderedBreechblockOffset);

		ms.pushPose();

		CachedBufferer.partialFacing(CBCClientCommon.getBreechblockForState(blockState), blockState, blockRotation)
			.translate(normal.x(), normal.y(), normal.z())
			.rotateCentered(qrot)
			.light(light)
			.renderInto(ms, buffer.getBuffer(RenderType.solid()));

		ms.popPose();
	}

	@Override
	protected BlockState getRenderedBlockState(ProjectileRackSlidingBreechBlockEntity te) {
		return shaft(getRotationAxisOf(te));
	}

}
