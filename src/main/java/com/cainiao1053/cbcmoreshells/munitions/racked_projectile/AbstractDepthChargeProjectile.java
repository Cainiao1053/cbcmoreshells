package com.cainiao1053.cbcmoreshells.munitions.racked_projectile;

import javax.annotation.Nonnull;

import com.cainiao1053.cbcmoreshells.CBCMSBlocks;
import com.cainiao1053.cbcmoreshells.Cbcmoreshells;
import com.cainiao1053.cbcmoreshells.index.CBCMSMunitionPropertiesHandlers;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.AbstractCannonTorpedoProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.FuzedCannonTorpedoProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.ShellessFuzedBigCannonProjectile;
//import com.cainiao1053.cbcmoreshells.munitions.big_cannon.config.BigCannonShellessShellProperties;
import com.cainiao1053.cbcmoreshells.munitions.racked_projectile.FuzedRackedProjectile;
import com.cainiao1053.cbcmoreshells.munitions.racked_projectile.config.RackedProjectileProperties;
import com.cainiao1053.cbcmoreshells.munitions.racked_projectile.config.RackedProjectilePropertiesComponent;
import com.cainiao1053.cbcmoreshells.munitions.racked_projectile.config.RackedShrapnelProjectileProperties;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;
import rbasamoyai.createbigcannons.CreateBigCannons;
import rbasamoyai.createbigcannons.config.CBCConfigs;
import rbasamoyai.createbigcannons.index.CBCBlocks;
import rbasamoyai.createbigcannons.munitions.ProjectileContext;
import rbasamoyai.createbigcannons.munitions.ShellExplosion;
import rbasamoyai.createbigcannons.munitions.big_cannon.config.BigCannonFuzePropertiesComponent;
//import rbasamoyai.createbigcannons.munitions.big_cannon.config.BigCannonProjectilePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.DimensionMunitionPropertiesHandler;
import rbasamoyai.createbigcannons.munitions.config.FluidDragHandler;
import rbasamoyai.createbigcannons.munitions.config.components.BallisticPropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.EntityDamagePropertiesComponent;
import rbasamoyai.createbigcannons.network.ClientboundPlayBlockHitEffectPacket;
import rbasamoyai.createbigcannons.utils.CBCUtils;

import java.util.List;

public abstract class AbstractDepthChargeProjectile extends FuzedRackedProjectile {

	public AbstractDepthChargeProjectile(EntityType<? extends AbstractDepthChargeProjectile> type, Level level) {
		super(type, level);
	}

	@Override
	protected boolean onImpactFluid(ProjectileContext projectileContext, BlockState blockState, FluidState fluidState, Vec3 impactPos, BlockHitResult fluidHitResult) {
		return false;
	}


	@Override
	protected double getDragForce() {
		BallisticPropertiesComponent properties = this.getBallisticProperties();
		double vel = this.getDeltaMovement().length();
		double formDrag = properties.drag();
		double density = DimensionMunitionPropertiesHandler.getProperties(this.level()).dragMultiplier();

		FluidState fluidState = this.level().getFluidState(this.blockPosition());
		if (!fluidState.isEmpty()) {
			density += 32;
		}

		double drag = formDrag * density * vel;
		if (properties.isQuadraticDrag()) {
			drag *= vel;
		}

		return Math.min(drag, vel);
	}

	public void destroyTorpedo(Position pos, ServerLevel serverLevel) {
		double searchRange = getAllProperties().destroyRange();
		AABB box = new AABB(pos.x() - searchRange, pos.y() - searchRange, pos.z() - searchRange,
				pos.x() + searchRange, pos.y() + searchRange, pos.z() + searchRange);
		List<FuzedCannonTorpedoProjectile> torpedoes =
				serverLevel.getEntitiesOfClass(
						FuzedCannonTorpedoProjectile.class,
						box,
						torp -> {
							return true;
						});
		if(!torpedoes.isEmpty()){
			for(FuzedCannonTorpedoProjectile torp : torpedoes){
				torp.triggerDetonation();
				torp.discard();
			}
		}
	}

	protected abstract RackedShrapnelProjectileProperties getAllProperties();
}
