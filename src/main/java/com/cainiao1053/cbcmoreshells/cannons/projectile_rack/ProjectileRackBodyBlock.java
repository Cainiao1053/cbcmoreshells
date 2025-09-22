package com.cainiao1053.cbcmoreshells.cannons.projectile_rack;

import java.util.function.Supplier;

import com.cainiao1053.cbcmoreshells.cannons.projectile_rack.material.ProjectileRackMaterial;
import com.cainiao1053.cbcmoreshells.cannons.projectile_rack.projectile_rack_end.ProjectileRackEnd;
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

public class ProjectileRackBodyBlock extends ProjectileRackBaseBlock implements IBE<ProjectileRackBlockEntity> {

	private final VoxelShaper visualShapes;
	private final VoxelShaper collisionShapes;
	private final VoxelShaper collisionShapeCeiling;
	private final VoxelShaper visualShapeCeiling;
	private final Supplier<CannonCastShape> cannonShape;

	public ProjectileRackBodyBlock(Properties properties, ProjectileRackMaterial material, Supplier<CannonCastShape> cannonShape, VoxelShape base) {
		this(properties, material, cannonShape, base, base);
	}

	public ProjectileRackBodyBlock(Properties properties, ProjectileRackMaterial material, Supplier<CannonCastShape> cannonShape, VoxelShape visualShape, VoxelShape collisionShape) {
		super(properties, material);
		this.visualShapes = new AllShapes.Builder(visualShape).forDirectional();
		this.collisionShapes = new AllShapes.Builder(collisionShape).forDirectional();
		this.collisionShapeCeiling = new AllShapes.Builder(Block.box(3, 0, 0, 13, 16, 4)).forDirectional();
		this.visualShapeCeiling = new AllShapes.Builder(Block.box(3, 0, 0, 13, 16, 4)).forDirectional();
		this.cannonShape = cannonShape;
	}

	public static ProjectileRackBodyBlock verySmall(Properties properties, ProjectileRackMaterial material) {
		return new ProjectileRackBodyBlock(properties, material, () -> CannonCastShape.VERY_SMALL, Block.box(2, 0, 2, 14, 16, 14));
	}

	public static ProjectileRackBodyBlock small(Properties properties, ProjectileRackMaterial material) {
		return new ProjectileRackBodyBlock(properties, material, () -> CannonCastShape.SMALL, Block.box(1, 0, 1, 15, 16, 15));
	}

	public static ProjectileRackBodyBlock medium(Properties properties, ProjectileRackMaterial material) {
		return new ProjectileRackBodyBlock(properties, material, () -> CannonCastShape.MEDIUM, Shapes.block());
	}

	public static ProjectileRackBodyBlock large(Properties properties, ProjectileRackMaterial material) {
		return new ProjectileRackBodyBlock(properties, material, () -> CannonCastShape.LARGE, Block.box(-1, 0, -1, 17, 16, 17), Shapes.block());
	}

	public static ProjectileRackBodyBlock veryLarge(Properties properties, ProjectileRackMaterial material) {
		return new ProjectileRackBodyBlock(properties, material, () -> CannonCastShape.VERY_LARGE, Block.box(-2, 0, -2, 18, 16, 18), Shapes.block());
	}

	public static ProjectileRackBodyBlock normalRack(Properties properties, ProjectileRackMaterial material) {
		return new ProjectileRackBodyBlock(properties, material, () -> CannonCastShape.SMALL, Block.box(3, 0, 12, 13, 16, 16));
	}

	@Override
	public CannonCastShape getCannonShape() {
		return this.cannonShape.get();
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		VoxelShape colShape = this.collisionShapes.get(this.getFacing(state));
		if(getCeiling(state)){
			colShape = this.collisionShapeCeiling.get(this.getFacing(state));
		}
		return colShape;
//		return this.collisionShapes.get(this.getFacing(state));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
//		return this.visualShapes.get(this.getFacing(state));
		VoxelShape visShape = this.visualShapes.get(this.getFacing(state));
		if(getCeiling(state)){
			visShape = this.visualShapeCeiling.get(this.getFacing(state));
		}
		return visShape;
	}

	@Override public ProjectileRackEnd getDefaultOpeningType() { return ProjectileRackEnd.OPEN; }

	@Override
	public boolean isComplete(BlockState state) {
		return true;
	}

	@Override
	public Class<ProjectileRackBlockEntity> getBlockEntityClass() {
		return ProjectileRackBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends ProjectileRackBlockEntity> getBlockEntityType() {
		return CBCMSBlockEntities.PROJECTILE_RACK_BODY.get();
	}

}
