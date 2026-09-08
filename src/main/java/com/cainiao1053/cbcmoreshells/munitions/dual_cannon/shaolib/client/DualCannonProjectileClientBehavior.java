package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib.client;

import com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib.DualCannonMunitionProperties;
import com.verr1.shaolib.api.projectile.ProjectileInstance;
import com.verr1.shaolib.munitions.config.properties.MunitionPropertyResolver;
import com.verr1.shaolib.munitions.projectile.client.CbcShellBlockModelProjectileClientBehavior;
import com.verr1.shaolib.munitions.projectile.client.CbcShellClientSpec;
import com.verr1.shaolib.munitions.projectile.client.MunitionClientState;
import com.verr1.shaolib.munitions.projectile.shell.FuzedShellData;
import com.verr1.shaolib.projectile.client.ClientProjectileContext;
import com.verr1.shaolib.projectile.client.ClientProjectileRenderContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

/**
 * Renders a dual cannon shell client-side using the munition block's model, and drives its flight
 * particles and flyby sound.
 *
 * <p>Owned by cbcms rather than taken from Shaolib's {@code DualCannonShellClientBehavior}, for the
 * same reason as the server-side classes: that dual cannon package is temporary. The generic
 * block-model/effect plumbing underneath it is library code and is still used directly.
 */
public final class DualCannonProjectileClientBehavior<P extends DualCannonMunitionProperties>
	extends CbcShellBlockModelProjectileClientBehavior<P> {

	/**
	 * @param projectileBlockId block whose model represents the shell in flight; the extended
	 *                          variants borrow their normal sibling's model
	 */
	public DualCannonProjectileClientBehavior(MunitionPropertyResolver<P> propertyResolver,
											  ResourceLocation projectileBlockId) {
		super(propertyResolver, CbcShellClientSpec.cbc(projectileBlockId));
	}

	@Override
	protected boolean isFlying(ClientProjectileContext context, ProjectileInstance projectile,
							   MunitionClientState state) {
		return FuzedShellData.isFlyingState(projectile.get(FuzedShellData.STATE));
	}

	@Override
	protected boolean isEmbedded(ClientProjectileContext context, ProjectileInstance projectile,
								 MunitionClientState state) {
		return projectile.get(FuzedShellData.STATE) == FuzedShellData.STATE_EMBEDDED || projectile.isEmbedded();
	}

	@Override
	protected Vec3 trailVelocity(ClientProjectileContext context, ProjectileInstance projectile,
								 MunitionClientState state) {
		return projectile.get(FuzedShellData.TRAIL_VELOCITY);
	}

	/** A detonated shell no longer exists as a body; only its explosion should be visible. */
	@Override
	protected boolean shouldRenderBlock(ClientProjectileRenderContext context, ProjectileInstance projectile,
										MunitionClientState state) {
		return projectile.get(FuzedShellData.STATE) != FuzedShellData.STATE_DETONATED;
	}

}
