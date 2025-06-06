package com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.breeches.quick_firing_breech;

import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.TorpedoTubeBlock;
import com.cainiao1053.cbcmoreshells.index.CBCMSBlockPartials;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.backend.instancing.blockentity.BlockEntityInstance;
import com.jozufozu.flywheel.core.Materials;
import com.jozufozu.flywheel.core.PartialModel;
import com.jozufozu.flywheel.core.materials.oriented.OrientedData;
import com.mojang.math.Axis;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.utility.AnimationTickHolder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
//import rbasamoyai.createbigcannons.cannons.big_cannons.BigCannonBlock;
//import rbasamoyai.createbigcannons.index.CBCBlockPartials;

public class TorpQuickfiringBreechInstance extends BlockEntityInstance<TorpQuickfiringBreechBlockEntity> implements DynamicInstance {

	private OrientedData breechblock;
	private OrientedData shaft;
	private OrientedData lever;
	private Direction direction;
	private Direction blockRotation;

	public TorpQuickfiringBreechInstance(MaterialManager materialManager, TorpQuickfiringBreechBlockEntity blockEntity) {
		super(materialManager, blockEntity);
	}

	@Override
	public void init() {
		super.init();

		Direction.Axis axis = getRotationAxis(this.blockState);
		Direction facing = this.blockState.getValue(BlockStateProperties.FACING);
		this.blockRotation = facing.getCounterClockWise(axis);
		if (this.blockRotation == Direction.DOWN) this.blockRotation = Direction.UP;

		this.breechblock = this.materialManager.defaultSolid()
			.material(Materials.ORIENTED)
			.getModel(getPartialModelForState(this.blockState), this.blockState, this.blockRotation)
			.createInstance();

		this.shaft = this.materialManager.defaultSolid()
			.material(Materials.ORIENTED)
			.getModel(AllBlocks.SHAFT.getDefaultState().setValue(BlockStateProperties.AXIS, axis))
			.createInstance();

		this.direction = facing.getCounterClockWise(this.blockRotation.getAxis());

		this.lever = this.materialManager.defaultSolid()
			.material(Materials.ORIENTED)
			.getModel(CBCMSBlockPartials.QUICKFIRING_BREECH_LEVER, this.blockState, this.direction)
			.createInstance();

		boolean alongFirst = this.blockState.getValue(TorpQuickfiringBreechBlock.AXIS);
		if (facing.getAxis().isHorizontal() && !alongFirst) {
			Direction rotDir = facing.getAxis() == Direction.Axis.X ? Direction.UP : Direction.EAST;
			Quaternionf q = Axis.of(rotDir.step()).rotationDegrees(90f);
			this.breechblock.setRotation(q);
		}
		if (facing.getAxis() == Direction.Axis.X && alongFirst) {
			this.breechblock.setRotation(Axis.of(this.blockRotation.step()).rotationDegrees(90f));
		}

		this.transformModels();
	}

	@Override
	public void beginFrame() {
		this.transformModels();
	}

	private void transformModels() {
		float progress = this.blockEntity.getOpenProgress(AnimationTickHolder.getPartialTicks());
		BlockPos instancePos = this.getInstancePosition();

		float renderedBreechblockOffset = progress / 16.0f * 13.0f;
		Vector3f normal = this.blockRotation.step();
		normal.mul(renderedBreechblockOffset);
		this.breechblock.setPosition(instancePos).nudge(normal.x(), normal.y(), normal.z());

		float angle = progress * 90;
		Quaternionf qrot = Axis.of(this.direction.step()).rotationDegrees(angle);
		this.shaft.setPosition(instancePos).setRotation(qrot);
		this.lever.setPosition(instancePos.relative(this.direction)).setRotation(qrot);
	}

	@Override
	public void updateLight() {
		super.updateLight();
		this.relight(this.pos, this.breechblock);
		this.relight(this.pos, this.shaft);
		this.relight(this.pos, this.lever);
	}

	@Override
	public void remove() {
		this.breechblock.delete();
		this.shaft.delete();
		this.lever.delete();
	}

	private static PartialModel getPartialModelForState(BlockState state) {
		return state.getBlock() instanceof TorpedoTubeBlock cBlock ? CBCMSBlockPartials.breechblockFor(cBlock.getCannonMaterial())
			: CBCMSBlockPartials.STEEL_TORPEDO_SLIDING_BREECHBLOCK;
	}

	private static Direction.Axis getRotationAxis(BlockState state) {
		boolean flag = state.getValue(TorpQuickfiringBreechBlock.AXIS);
		return switch (state.getValue(TorpQuickfiringBreechBlock.FACING).getAxis()) {
			case X -> flag ? Direction.Axis.Y : Direction.Axis.Z;
			case Y -> flag ? Direction.Axis.X : Direction.Axis.Z;
			case Z -> flag ? Direction.Axis.X : Direction.Axis.Y;
		};
	}

}
