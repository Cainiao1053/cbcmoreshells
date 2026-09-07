package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib;

import com.cainiao1053.cbcmoreshells.network.CBCMSNetworkImpl;
import com.cainiao1053.cbcmoreshells.network.ClientboundCBCMSSplashPacket;
import com.cainiao1053.cbcmoreshells.network.ClientboundCBCMSTrailPacket;
import com.verr1.shaolib.api.projectile.ProjectileInstance;
import com.verr1.shaolib.api.projectile.ProjectileServerContext;
import com.verr1.shaolib.munitions.config.properties.CbcLikeMunitionProperties;
import com.verr1.shaolib.munitions.config.properties.DualCannonMunitionProperties;
import com.verr1.shaolib.munitions.config.properties.MunitionPropertyResolver;
import com.verr1.shaolib.munitions.projectile.dual_cannon.DualCannonShellBehavior;
import com.verr1.shaolib.munitions.projectile.dual_cannon.DualCannonShellState;
import com.verr1.shaolib.munitions.projectile.shell.FuzedShellData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

/**
 * cbcms' dual cannon shell behavior: Shaolib's flight/impact/fuze pipeline plus the two things the
 * library has no notion of — a per-shot tick budget and cbcms' own tracer trail packets.
 *
 * <p>Everything else (penetration, ricochet, durability mass bookkeeping, explosion power scaling
 * per {@link DualCannonShellBehavior.Kind}, the anti-air altitude bonus, the incendiary hand-off)
 * is inherited. In particular the explosion multipliers in the parent already match the formulas
 * the old entity projectiles used, so they are deliberately not overridden.
 */
public class CBCMSDualCannonShellBehavior<P extends CbcLikeMunitionProperties & DualCannonMunitionProperties>
	extends DualCannonShellBehavior<P> {

	/** Ticks between trail segments while the shell is in flight. */
	private static final int TRAIL_INTERVAL = 5;
	/** Cooldown parked on the projectile once tracing has finished, so we stop re-checking. */
	private static final int TRAIL_IDLE = 200;
	/** Initial delay before the first segment, matching the old entity's {@code sendTrail} seed. */
	private static final int TRAIL_INITIAL_DELAY = 20;

	public CBCMSDualCannonShellBehavior(MunitionPropertyResolver<P> propertyResolver, Kind kind) {
		super(propertyResolver, kind);
	}

	@Override
	public void onSpawn(ProjectileServerContext<DualCannonShellState> context) {
		super.onSpawn(context);
		ProjectileInstance projectile = context.projectile();
		projectile.set(CBCMSDualCannonData.TRAIL_ANCHOR, projectile.position());
		projectile.set(CBCMSDualCannonData.TRAIL_COOLDOWN, TRAIL_INITIAL_DELAY);
		projectile.set(CBCMSDualCannonData.TRAIL_STAGE, CBCMSDualCannonData.TRAIL_STAGE_TRACING);
	}

	@Override
	public void tickServer(ProjectileServerContext<DualCannonShellState> context) {
		if (this.expireOnLifetime(context)) return;
		this.tickTrail(context);
		super.tickServer(context);
	}

	/**
	 * Replaces the old {@code age > maxAge -> discard()} check.
	 *
	 * <p>Terminal states are left alone on purpose: they are governed by
	 * {@code runtime.terminalTtlTicks} / {@code detonationTtlTicks}, and cutting them short here
	 * would truncate detonation effects that the old entity had already finished playing by the
	 * time it despawned.
	 *
	 * @return true if the projectile was discarded and the tick should stop
	 */
	protected boolean expireOnLifetime(ProjectileServerContext<DualCannonShellState> context) {
		ProjectileInstance projectile = context.projectile();
		if (FuzedShellData.isTerminalState(projectile.get(FuzedShellData.STATE))) return false;
		int lifetime = projectile.get(CBCMSDualCannonData.LIFETIME_TICKS);
		if (lifetime <= 0 || projectile.ageTicks() < lifetime) return false;
		context.runtime().discard("cbcms_lifetime");
		return true;
	}

	/**
	 * Ports the tracer trail/splash packets from the old {@code AbstractDualCannonProjectile.tick}.
	 * A segment is drawn from the last anchor to the current position every {@link #TRAIL_INTERVAL}
	 * ticks; when the shell goes to ground or enters water one final shortened segment is drawn and
	 * tracing stops.
	 */
	protected void tickTrail(ProjectileServerContext<DualCannonShellState> context) {
		ProjectileInstance projectile = context.projectile();
		if (projectile.get(CBCMSDualCannonData.TRAIL_STAGE) != CBCMSDualCannonData.TRAIL_STAGE_TRACING) return;

		int cooldown = projectile.get(CBCMSDualCannonData.TRAIL_COOLDOWN);
		if (cooldown > 0) {
			projectile.set(CBCMSDualCannonData.TRAIL_COOLDOWN, cooldown - 1);
			return;
		}

		ServerLevel level = context.level();
		Vec3 anchor = projectile.get(CBCMSDualCannonData.TRAIL_ANCHOR);
		Vec3 position = projectile.position();
		boolean grounded = projectile.isEmbedded()
			|| !FuzedShellData.isFlyingState(projectile.get(FuzedShellData.STATE));
		boolean inWater = level.getFluidState(projectile.blockPosition()).is(FluidTags.WATER);

		if (!grounded && !inWater) {
			this.broadcastTrail(level, position, anchor, false);
			projectile.set(CBCMSDualCannonData.TRAIL_ANCHOR, position);
			projectile.set(CBCMSDualCannonData.TRAIL_COOLDOWN, TRAIL_INTERVAL);
			return;
		}

		// Final segment: pull the endpoint most of the way back toward the anchor so the trail does
		// not visibly poke through the surface the shell just hit.
		Vec3 endpoint = new Vec3(
			Mth.lerp(0.75D, position.x, anchor.x),
			Mth.lerp(0.75D, position.y, anchor.y),
			Mth.lerp(0.75D, position.z, anchor.z));
		this.broadcastTrail(level, endpoint, anchor, inWater);
		projectile.set(CBCMSDualCannonData.TRAIL_STAGE, CBCMSDualCannonData.TRAIL_STAGE_DONE);
		projectile.set(CBCMSDualCannonData.TRAIL_COOLDOWN, TRAIL_IDLE);
	}

	private void broadcastTrail(ServerLevel level, Vec3 to, Vec3 from, boolean splash) {
		for (ServerPlayer player : level.players()) {
			if (splash) {
				CBCMSNetworkImpl.sendToClientPlayer(
					new ClientboundCBCMSSplashPacket(to.x, to.y, to.z, from.x, from.y, from.z), player);
			} else {
				CBCMSNetworkImpl.sendToClientPlayer(
					new ClientboundCBCMSTrailPacket(to.x, to.y, to.z, from.x, from.y, from.z), player);
			}
		}
	}

}
