package com.cainiao1053.cbcmoreshells.munitions.big_cannon;

import java.util.function.Predicate;

import javax.annotation.Nonnull;

import net.minecraft.core.Position;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.core.particles.ParticleTypes;
import rbasamoyai.createbigcannons.munitions.ProjectileContext;
import rbasamoyai.createbigcannons.munitions.big_cannon.AbstractBigCannonProjectile;

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
