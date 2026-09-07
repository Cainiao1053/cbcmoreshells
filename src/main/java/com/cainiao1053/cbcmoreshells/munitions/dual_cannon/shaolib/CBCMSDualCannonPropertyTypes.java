package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib;

import com.cainiao1053.cbcmoreshells.Cbcmoreshells;
import com.verr1.shaolib.munitions.config.properties.DualCannonIncendiaryShellProperties;
import com.verr1.shaolib.munitions.config.properties.DualCannonShellProperties;
import com.verr1.shaolib.munitions.config.properties.MunitionPropertyComponents;
import com.verr1.shaolib.munitions.config.properties.MunitionPropertyType;
import net.minecraft.resources.ResourceLocation;

/**
 * Munition property types for cbcms dual cannon shells, registered under the {@code cbcmoreshells}
 * namespace so they never collide with the {@code shaolib_munitions} ones.
 *
 * <p>Datapack files live at {@code data/cbcmoreshells/shaolib_munitions/munitions/<path>.json};
 * the file path must match the id exactly or the type silently falls back to the values below.
 *
 * <p>The seven "normal" shells reuse Shaolib's own fallbacks — that package was authored against
 * cbcmoreshells (its fallbacks already point {@code cbc_projectile_effect_entity} at
 * {@code cbcmoreshells:<id>}), so those numbers are the intended baseline. The two "extended"
 * variants have no Shaolib counterpart and derive from their normal siblings.
 */
public final class CBCMSDualCannonPropertyTypes {

	public static final MunitionPropertyType<DualCannonShellProperties> NORMAL_AP_SHOT =
		DualCannonShellProperties.createType(id("normal_ap_shot"), DualCannonShellProperties::normalApShotFallback);

	public static final MunitionPropertyType<DualCannonShellProperties> NORMAL_HE_SHELL =
		DualCannonShellProperties.createType(id("normal_he_shell"), DualCannonShellProperties::normalHeFallback);

	public static final MunitionPropertyType<DualCannonShellProperties> NORMAL_ANTIAIR_HE_SHELL =
		DualCannonShellProperties.createType(id("normal_antiair_he_shell"), DualCannonShellProperties::normalAntiairHeFallback);

	public static final MunitionPropertyType<DualCannonShellProperties> NORMAL_AP_SHELL =
		DualCannonShellProperties.createType(id("normal_ap_shell"), DualCannonShellProperties::normalApShellFallback);

	public static final MunitionPropertyType<DualCannonShellProperties> NORMAL_APBC_SHELL =
		DualCannonShellProperties.createType(id("normal_apbc_shell"), DualCannonShellProperties::normalApbcShellFallback);

	public static final MunitionPropertyType<DualCannonShellProperties> NORMAL_SAP_SHELL =
		DualCannonShellProperties.createType(id("normal_sap_shell"), DualCannonShellProperties::normalSapShellFallback);

	public static final MunitionPropertyType<DualCannonIncendiaryShellProperties> NORMAL_INCENDIARY_HE_SHELL =
		DualCannonIncendiaryShellProperties.createType(id("normal_incendiary_he_shell"),
			DualCannonIncendiaryShellProperties::normalIncendiaryHeFallback);

	public static final MunitionPropertyType<DualCannonShellProperties> EXTENDED_AP_SHOT =
		DualCannonShellProperties.createType(id("extended_ap_shot"), CBCMSDualCannonPropertyTypes::extendedApShotFallback);

	public static final MunitionPropertyType<DualCannonShellProperties> EXTENDED_ANTIAIR_HE_SHELL =
		DualCannonShellProperties.createType(id("extended_antiair_he_shell"),
			CBCMSDualCannonPropertyTypes::extendedAntiairHeShellFallback);

	private CBCMSDualCannonPropertyTypes() {}

	/** Forces static initialisation; call once during mod setup. */
	public static void register() {}

	/**
	 * Extended AP shot: the normal shot with a longer reach. It renders as the normal AP shot block
	 * (matching the old {@code ExtendedAPShotProjectile.getRenderedBlockState}).
	 */
	private static DualCannonShellProperties extendedApShotFallback() {
		DualCannonShellProperties base = DualCannonShellProperties.normalApShotFallback();
		return withReach(base, base.runtime().maxDistance() * 1.5D, "normal_ap_shot");
	}

	/** Extended AA shell: the normal AA shell with a longer reach, rendered as the normal AA block. */
	private static DualCannonShellProperties extendedAntiairHeShellFallback() {
		DualCannonShellProperties base = DualCannonShellProperties.normalAntiairHeFallback();
		return withReach(base, base.runtime().maxDistance() * 1.5D, "normal_antiair_he_shell");
	}

	private static DualCannonShellProperties withReach(DualCannonShellProperties base, double maxDistance,
													   String renderedBlockPath) {
		MunitionPropertyComponents.RuntimeProperties runtime = base.runtime();
		MunitionPropertyComponents.EffectProperties effects = base.effects();
		return new DualCannonShellProperties(
			base.ballistics(),
			new MunitionPropertyComponents.RuntimeProperties(maxDistance, runtime.terminalTtlTicks(),
				runtime.detonationTtlTicks()),
			base.impact(),
			new MunitionPropertyComponents.EffectProperties(effects.blockHitEffects(), effects.trailSmoke(),
				effects.flybySound(), Cbcmoreshells.resource(renderedBlockPath), effects.explosion()),
			base.damage(),
			base.dualCannon(),
			base.dualImpact());
	}

	private static ResourceLocation id(String path) {
		return Cbcmoreshells.resource(path);
	}

}
