package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib;

import com.cainiao1053.cbcmoreshells.Cbcmoreshells;
import com.verr1.shaolib.munitions.config.properties.MunitionPropertyType;
import net.minecraft.resources.ResourceLocation;

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

	public static void register() {}

	private static ResourceLocation id(String path) {
		return Cbcmoreshells.resource(path);
	}

}
