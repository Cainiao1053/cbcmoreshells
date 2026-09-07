package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib;

import com.cainiao1053.cbcmoreshells.Cbcmoreshells;
import com.verr1.shaolib.munitions.config.properties.MunitionPropertyType;
import net.minecraft.resources.ResourceLocation;

/**
 * Registers a {@link MunitionPropertyType} for each cbcms dual cannon munition.
 *
 * <p>Ids are in the {@code cbcmoreshells} namespace and datapack files live at
 * {@code data/cbcmoreshells/shaolib_munitions/munitions/<path>.json}. The file path must match the id
 * exactly; a mismatch is not an error, it just silently leaves the baked-in fallback in place.
 *
 * <p>Registration is one-shot — {@code MunitionPropertyTypes.register} throws on a duplicate id — so
 * {@link #register()} must be reached exactly once, which {@code Cbcmoreshells.init()} guarantees.
 */
public final class CBCMSDualCannonPropertyTypes {

	public static final MunitionPropertyType<DualCannonProjectileProperties> NORMAL_AP_SHOT =
		DualCannonProjectileProperties.createType(id("normal_ap_shot"),
			DualCannonProjectileProperties::normalApShotFallback);

	public static final MunitionPropertyType<DualCannonProjectileProperties> NORMAL_HE_SHELL =
		DualCannonProjectileProperties.createType(id("normal_he_shell"),
			DualCannonProjectileProperties::normalHeShellFallback);

	public static final MunitionPropertyType<DualCannonProjectileProperties> NORMAL_ANTIAIR_HE_SHELL =
		DualCannonProjectileProperties.createType(id("normal_antiair_he_shell"),
			DualCannonProjectileProperties::normalAntiairHeShellFallback);

	public static final MunitionPropertyType<DualCannonProjectileProperties> NORMAL_AP_SHELL =
		DualCannonProjectileProperties.createType(id("normal_ap_shell"),
			DualCannonProjectileProperties::normalApShellFallback);

	public static final MunitionPropertyType<DualCannonProjectileProperties> NORMAL_APBC_SHELL =
		DualCannonProjectileProperties.createType(id("normal_apbc_shell"),
			DualCannonProjectileProperties::normalApbcShellFallback);

	public static final MunitionPropertyType<DualCannonProjectileProperties> NORMAL_SAP_SHELL =
		DualCannonProjectileProperties.createType(id("normal_sap_shell"),
			DualCannonProjectileProperties::normalSapShellFallback);

	public static final MunitionPropertyType<DualCannonIncendiaryProjectileProperties> NORMAL_INCENDIARY_HE_SHELL =
		DualCannonIncendiaryProjectileProperties.createType(id("normal_incendiary_he_shell"),
			DualCannonIncendiaryProjectileProperties::normalIncendiaryHeShellFallback);

	public static final MunitionPropertyType<DualCannonProjectileProperties> EXTENDED_AP_SHOT =
		DualCannonProjectileProperties.createType(id("extended_ap_shot"),
			DualCannonProjectileProperties::extendedApShotFallback);

	public static final MunitionPropertyType<DualCannonProjectileProperties> EXTENDED_ANTIAIR_HE_SHELL =
		DualCannonProjectileProperties.createType(id("extended_antiair_he_shell"),
			DualCannonProjectileProperties::extendedAntiairHeShellFallback);

	private CBCMSDualCannonPropertyTypes() {}

	/** Forces static initialisation; call once during mod setup. */
	public static void register() {}

	private static ResourceLocation id(String path) {
		return Cbcmoreshells.resource(path);
	}

}
