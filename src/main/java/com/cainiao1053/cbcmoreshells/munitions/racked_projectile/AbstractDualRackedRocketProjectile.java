package com.cainiao1053.cbcmoreshells.munitions.racked_projectile;


import com.cainiao1053.cbcmoreshells.munitions.racked_projectile.config.RackedRocketProjectileProperties;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import rbasamoyai.createbigcannons.effects.particles.smoke.TrailSmokeParticleData;
import rbasamoyai.createbigcannons.munitions.config.FluidDragHandler;
import rbasamoyai.createbigcannons.munitions.config.components.BallisticPropertiesComponent;



public abstract class AbstractDualRackedRocketProjectile extends FuzedRackedProjectile {

	public AbstractDualRackedRocketProjectile(EntityType<? extends AbstractDualRackedRocketProjectile> type, Level level) {
		super(type, level);
	}

	protected int thrustTime = getAllProperties().thrustTime();

	@Override
	public void tick() {
		super.tick();
		if (!this.isInGround()) {
			int lifetime = 20;
			ParticleOptions options = new TrailSmokeParticleData(lifetime);
			for (int i = 0; i < 5; ++i) {
				double partial = i * 0.2f;
				double dx = Mth.lerp(partial, this.xOld, this.getX());
				double dy = Mth.lerp(partial, this.yOld, this.getY());
				double dz = Mth.lerp(partial, this.zOld, this.getZ());
				double sx = this.level().random.nextDouble() * 0.004d - 0.002d;
				double sy = this.level().random.nextDouble() * 0.004d - 0.002d;
				double sz = this.level().random.nextDouble() * 0.004d - 0.002d;
				this.level().addAlwaysVisibleParticle(options, true, dx, dy, dz, sx, sy, sz);
			}
		}
	}

	@Override
	protected double getDragForce() {
		BallisticPropertiesComponent properties = this.getBallisticProperties();
		double vel = this.getDeltaMovement().length();
		double ssVel = getAllProperties().steadyStateVel();
		double formDrag = properties.drag();
		double drag = formDrag * vel;
		FluidState fluidState = this.level().getFluidState(this.blockPosition());
		if (!fluidState.isEmpty()) {
			drag += FluidDragHandler.getFluidDrag(fluidState)*formDrag*vel;
		}
		if(this.getAge()<thrustTime){
			drag += 0.12 * (vel - ssVel);
		}
		return drag;
	}

	@Override
	protected Vec3 getForces(Vec3 position, Vec3 velocity) {
		return velocity.normalize().scale(-this.getDragForce()).add((double)0.0F, this.getGravity(), (double)0.0F);
	}

	protected abstract RackedRocketProjectileProperties getAllProperties();
}
