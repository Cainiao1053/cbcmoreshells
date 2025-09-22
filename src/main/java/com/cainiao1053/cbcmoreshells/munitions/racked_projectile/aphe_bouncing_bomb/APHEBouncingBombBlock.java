package com.cainiao1053.cbcmoreshells.munitions.racked_projectile.aphe_bouncing_bomb;

import com.cainiao1053.cbcmoreshells.CBCMSEntityTypes;
import com.cainiao1053.cbcmoreshells.index.CBCMSMunitionPropertiesHandlers;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.ShellessShellBlock;
import com.cainiao1053.cbcmoreshells.munitions.racked_projectile.FuzedRackedProjectileBlock;
import com.cainiao1053.cbcmoreshells.munitions.racked_projectile.GeneralRackedProjectileBlock;
import com.simibubi.create.foundation.utility.VoxelShaper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import rbasamoyai.createbigcannons.index.CBCMunitionPropertiesHandlers;


public class APHEBouncingBombBlock extends GeneralRackedProjectileBlock<APHEBouncingBombProjectile> {

	public APHEBouncingBombBlock(Properties properties) {
		super(properties);
		this.shapes = makeShapes();
	}

	protected VoxelShaper shapes;

	@Override
	public boolean isBaseFuze() {
		return CBCMSMunitionPropertiesHandlers.RACKED_PROJECTILE.getPropertiesOf(this.getAssociatedEntityType()).fuze().baseFuze();
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
		//return this.shapes.get(state.getValue(FACING));
		Direction direction = state.getValue(FACING);
		direction = rotateHorizontal(direction);
		return this.shapes.get(direction);
	}

	public Direction rotateHorizontal(Direction direction) {
		//Direction direction;
		switch (direction) {
			case NORTH:
				direction = Direction.EAST;
				break;
			case SOUTH:
				direction = Direction.WEST;
				break;
			case WEST:
				direction = Direction.NORTH;
				break;
			case EAST:
				direction = Direction.SOUTH;
				break;
			case UP:
				direction = Direction.UP;
				break;
			case DOWN:
				direction = Direction.DOWN;
				break;
			default:
				throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
		}

		return direction;
	}

	@Override
	public int getLifetimeFromBlock() {
		return CBCMSMunitionPropertiesHandlers.RACKED_PROJECTILE.getPropertiesOf(this.getAssociatedEntityType()).lifetime();
	}

	@Override
	public EntityType<? extends APHEBouncingBombProjectile> getAssociatedEntityType() {
		return CBCMSEntityTypes.APHE_BOUNCING_BOMB.get();
	}

}
