package com.cainiao1053.cbcmoreshells.munitions.racked_projectile.aphe_bomb;

import com.cainiao1053.cbcmoreshells.CBCMSEntityTypes;
import com.cainiao1053.cbcmoreshells.index.CBCMSMunitionPropertiesHandlers;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.ShellessShellBlock;
import com.cainiao1053.cbcmoreshells.munitions.racked_projectile.AbstractRackedProjectile;
import com.cainiao1053.cbcmoreshells.munitions.racked_projectile.FuzedRackedProjectile;
import com.cainiao1053.cbcmoreshells.munitions.racked_projectile.FuzedRackedProjectileBlock;
import com.cainiao1053.cbcmoreshells.munitions.racked_projectile.GeneralRackedProjectileBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.Vec3;
import rbasamoyai.createbigcannons.index.CBCMunitionPropertiesHandlers;


public class APHEBombBlock extends GeneralRackedProjectileBlock<APHEBombProjectile> {

	public APHEBombBlock(Properties properties) {
		super(properties);
	}

	@Override
	public boolean isBaseFuze() {
		return CBCMSMunitionPropertiesHandlers.RACKED_PROJECTILE.getPropertiesOf(this.getAssociatedEntityType()).fuze().baseFuze();
	}

//	@Override
//	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos neighbour, boolean p_60514_) {
//		super.neighborChanged(state, level, pos, block, neighbour, p_60514_);
//		if (!level.isClientSide()) {
//			if(level.hasNeighborSignal(pos)){
//				FuzedRackedProjectile projectile = (FuzedRackedProjectile) this.getProjectile(level,pos,state);
//				projectile.setFuze(getFuzeFromBlock(level,pos));
//				projectile.setLifetime(getLifetimeFromBlock()-20);
//				Vec3 initVel = getInitialVelDir(state);
//				projectile.setPos(pos.getX() + 1.5*initVel.x,pos.getY(),pos.getZ() + 1.5*initVel.z);
//				projectile.shoot(initVel.x, initVel.y - 0.25, initVel.z, 0.2f, 0.8f);
//				level.addFreshEntity(projectile);
//				level.setBlock(pos, Blocks.AIR.defaultBlockState(),2);
//			}
//		}
//	}


	@Override
	public int getLifetimeFromBlock() {
		return CBCMSMunitionPropertiesHandlers.RACKED_PROJECTILE.getPropertiesOf(this.getAssociatedEntityType()).lifetime();
	}

	@Override
	public EntityType<? extends APHEBombProjectile> getAssociatedEntityType() {
		return CBCMSEntityTypes.APHE_BOMB.get();
	}

}
