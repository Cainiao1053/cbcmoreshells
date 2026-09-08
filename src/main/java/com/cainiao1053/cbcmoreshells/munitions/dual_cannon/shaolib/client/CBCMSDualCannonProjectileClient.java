package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib.client;

import com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib.CBCMSDualCannonData;
import com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib.CBCMSDualCannonMunitionRegistry;
import com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib.DualCannonMunitionProperties;
import com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib.DualCannonState;
import com.verr1.shaolib.api.projectile.ProjectileType;
import com.verr1.shaolib.munitions.config.properties.MunitionPropertyResolver;
import com.verr1.shaolib.munitions.config.properties.MunitionPropertyType;
import com.verr1.shaolib.projectile.client.ClientProjectileBehaviors;
import net.minecraft.resources.ResourceLocation;

/**
 * Client-side rendering hookup for the dual cannon shells. Client only — do not touch from common
 * code.
 *
 * <p>Driven off {@link CBCMSDualCannonMunitionRegistry} rather than a second hand-written list, so a
 * munition cannot end up registered on the server and invisible on the client.
 */
public final class CBCMSDualCannonProjectileClient {

	private CBCMSDualCannonProjectileClient() {}

	public static void register() {
		for (CBCMSDualCannonMunitionRegistry.Entry entry : CBCMSDualCannonMunitionRegistry.all()) {
			register(entry.projectileType(), entry.propertyType(), entry.renderedBlock());
		}
	}

	/**
	 * Uses the same {@code encodedJson} resolver as the server type, so both sides read a shot's
	 * per-shot overrides identically instead of the client silently falling back to the datapack
	 * values.
	 */
	private static <P extends DualCannonMunitionProperties> void register(ProjectileType<DualCannonState> type,
																		  MunitionPropertyType<P> propertyType,
																		  ResourceLocation renderedBlock) {
		MunitionPropertyResolver<P> resolver =
			MunitionPropertyResolver.encodedJson(propertyType, CBCMSDualCannonData.DYNAMIC_PROPERTIES);
		ClientProjectileBehaviors.register(type, new DualCannonProjectileClientBehavior<>(resolver, renderedBlock));
	}

}
