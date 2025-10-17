package com.cainiao1053.cbcmoreshells.cannons.dual_cannon;

import java.util.function.Supplier;

import com.cainiao1053.cbcmoreshells.cannons.dual_cannon.dual_cannon_end.DualCannonEnd;
import com.cainiao1053.cbcmoreshells.cannons.dual_cannon.material.DualCannonMaterial;
import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.material.TorpedoTubeMaterial;
import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.torpedo_end.TorpedoTubeEnd;
import com.cainiao1053.cbcmoreshells.index.CBCMSBlockEntities;
import com.simibubi.create.AllShapes;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.utility.VoxelShaper;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
//import rbasamoyai.createbigcannons.cannons.big_cannons.cannon_end.BigCannonEnd;
import rbasamoyai.createbigcannons.cannons.big_cannons.material.BigCannonMaterial;
import rbasamoyai.createbigcannons.crafting.casting.CannonCastShape;
import rbasamoyai.createbigcannons.index.CBCBlockEntities;

public class DualCannonBodyBlock extends DualCannonBaseBlock implements IBE<DualCannonBlockEntity> {

	private final VoxelShaper visualShapes;
	private final VoxelShaper collisionShapes;
	private final Supplier<CannonCastShape> cannonShape;

	public DualCannonBodyBlock(Properties properties, DualCannonMaterial material, Supplier<CannonCastShape> cannonShape, VoxelShape base) {
		this(properties, material, cannonShape, base, base);
	}

	public DualCannonBodyBlock(Properties properties, DualCannonMaterial material, Supplier<CannonCastShape> cannonShape, VoxelShape visualShape, VoxelShape collisionShape) {
		super(properties, material);
		this.visualShapes = new AllShapes.Builder(visualShape).forDirectional();
		this.collisionShapes = new AllShapes.Builder(collisionShape).forDirectional();
		this.cannonShape = cannonShape;
	}

	public static DualCannonBodyBlock verySmall(Properties properties, DualCannonMaterial material) {
		return new DualCannonBodyBlock(properties, material, () -> CannonCastShape.VERY_SMALL, Block.box(0, 0, 5, 16, 16, 11));
	}

	public static DualCannonBodyBlock small(Properties properties, DualCannonMaterial material) {
		return new DualCannonBodyBlock(properties, material, () -> CannonCastShape.SMALL, Block.box(1, 0, 1, 15, 16, 15));
	}

	public static DualCannonBodyBlock medium(Properties properties, DualCannonMaterial material) {
		return new DualCannonBodyBlock(properties, material, () -> CannonCastShape.MEDIUM, Block.box(0, 0, 4, 16, 16, 12));
	}

	public static DualCannonBodyBlock large(Properties properties, DualCannonMaterial material) {
		return new DualCannonBodyBlock(properties, material, () -> CannonCastShape.LARGE, Block.box(-1, 0, -1, 17, 16, 17), Shapes.block());
	}

	public static DualCannonBodyBlock veryLarge(Properties properties, DualCannonMaterial material) {
		return new DualCannonBodyBlock(properties, material, () -> CannonCastShape.VERY_LARGE, Block.box(-2, 0, -2, 18, 16, 18), Shapes.block());
	}

	@Override
	public CannonCastShape getCannonShape() {
		return this.cannonShape.get();
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return this.collisionShapes.get(this.getFacing(state));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return this.visualShapes.get(this.getFacing(state));
	}

	@Override public DualCannonEnd getDefaultOpeningType() { return DualCannonEnd.OPEN; }

	@Override
	public boolean isComplete(BlockState state) {
		return true;
	}

	@Override
	public Class<DualCannonBlockEntity> getBlockEntityClass() {
		return DualCannonBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends DualCannonBlockEntity> getBlockEntityType() {
		return CBCMSBlockEntities.DUAL_CANNON_BODY.get();
	}

}
