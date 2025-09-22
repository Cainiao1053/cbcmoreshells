package com.cainiao1053.cbcmoreshells.munitions.big_cannon;

import java.util.function.Predicate;

import javax.annotation.Nonnull;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.core.particles.ParticleTypes;
import rbasamoyai.createbigcannons.CreateBigCannons;
import rbasamoyai.createbigcannons.block_armor_properties.BlockArmorPropertiesHandler;
import rbasamoyai.createbigcannons.config.CBCConfigs;
import rbasamoyai.createbigcannons.index.CBCEntityTypes;
import rbasamoyai.createbigcannons.munitions.ImpactExplosion;
import rbasamoyai.createbigcannons.munitions.ProjectileContext;
import rbasamoyai.createbigcannons.munitions.ShellExplosion;
import rbasamoyai.createbigcannons.munitions.big_cannon.AbstractBigCannonProjectile;
import rbasamoyai.createbigcannons.munitions.fragment_burst.CBCProjectileBurst;

public abstract class ShellessInertBigCannonProjectile extends AbstractBigCannonProjectile {

	public boolean tooManyCharges = false;
	protected int ageRemaining;

	protected ShellessInertBigCannonProjectile(EntityType<? extends ShellessInertBigCannonProjectile> type, Level level) {
		super(type, level);
	}


	@Override
	public void tick() {
		if (this.tooManyCharges) {
			if (this.level() instanceof ServerLevel slevel) {
				slevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, this.getRenderedBlockState()), this.getX(), this.getY(), this.getZ(), 40, 0.1f, 0.1f, 0.1f, 0.01d);
			}
			SoundType soundType = this.getRenderedBlockState().getSoundType();
			this.playSound(soundType.getBreakSound(), soundType.getVolume() * 0.5f, soundType.getPitch() * 0.75f);
			this.discard();
			return;
		}
		super.tick();

		if (!this.level().isClientSide) {
			this.ageRemaining--;
			if (this.ageRemaining <= 0)
				this.expireProjectile();
		}
	}

	@Override
	protected ImpactResult calculateBlockPenetration(ProjectileContext projectileContext, BlockState state, BlockHitResult blockHitResult) {
		ImpactResult result =  super.calculateBlockPenetration(projectileContext, state, blockHitResult);
		if(result.kinematics() == ImpactResult.KinematicOutcome.PENETRATE) {
			double toughness = BlockArmorPropertiesHandler.getProperties(state).toughness(this.level(),state,blockHitResult.getBlockPos(),true);
			if(toughness > 11) {
				Vec3 oldDelta = this.getDeltaMovement();
				//Vec3 location = blockHitResult.getLocation();
				Vec3 location = new Vec3(this.getX(), this.getY(), this.getZ());
				int shrapnelCount = (int) (toughness / 5) + 2;
				CBCProjectileBurst.spawnConeBurst(this.level(), CBCEntityTypes.SHRAPNEL_BURST.get(), location,
						oldDelta, shrapnelCount, 0.7);
			}
		}
		return result;
	}

	protected void expireProjectile() {
		this.discard();
	}

	public void setLifetime(int lifetime) { this.ageRemaining = lifetime; }


	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putBoolean("TooManyCharges", this.tooManyCharges);
		tag.putInt("Age", this.ageRemaining);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.tooManyCharges = tag.getBoolean("TooManyCharges");
		this.ageRemaining = tag.getInt("Age");
	}





}
