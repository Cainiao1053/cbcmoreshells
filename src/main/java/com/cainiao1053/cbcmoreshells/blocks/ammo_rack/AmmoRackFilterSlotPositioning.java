package com.cainiao1053.cbcmoreshells.blocks.ammo_rack;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;

//import net.createmod.catnip.math.VecHelper;
//import net.createmod.catnip.math.AngleHelper;
import com.simibubi.create.foundation.utility.AngleHelper;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

public class AmmoRackFilterSlotPositioning extends ValueBoxTransform.Sided{


	//@Override
	protected boolean isSideActive(BlockState state, Direction direction) {
//		return direction.getAxis()
//			.isHorizontal();
		return direction == state.getValue(BlockStateProperties.HORIZONTAL_FACING);
	}

	//@Override
	protected Vec3 getSouthLocation() {
		return VecHelper.voxelSpace(8, 2, 15.75);
	}

}
