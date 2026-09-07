package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib;

import com.cainiao1053.cbcmoreshells.Cbcmoreshells;
import com.verr1.shaolib.api.projectile.ProjectileOrientationSync;
import com.verr1.shaolib.api.projectile.ProjectileType;
import com.verr1.shaolib.api.projectile.ShaolibProjectiles;
import com.verr1.shaolib.munitions.config.properties.CbcLikeMunitionProperties;
import com.verr1.shaolib.munitions.config.properties.DualCannonMunitionProperties;
import com.verr1.shaolib.munitions.config.properties.MunitionPropertyResolver;
import com.verr1.shaolib.munitions.config.properties.MunitionPropertyType;
import com.verr1.shaolib.munitions.projectile.dual_cannon.DualCannonShellBehavior;
import com.verr1.shaolib.munitions.projectile.dual_cannon.DualCannonShellSerializer;
import com.verr1.shaolib.munitions.projectile.dual_cannon.DualCannonShellState;
import com.verr1.shaolib.munitions.projectile.motion.MunitionMotionModels;

/**
 * cbcms' own dual cannon projectile types.
 *
 * <p>Shaolib Munitions already registers seven {@code shaolib_munitions:normal_*} dual cannon types
 * (that package was written against this mod), but we register our own under {@code cbcmoreshells}
 * for three reasons: the two {@code extended_*} shells have no counterpart there, the per-shot tick
 * budget needs an extended schema, and per-shot property overrides need an
 * {@link MunitionPropertyResolver#encodedJson} resolver rather than a plain configured one.
 */
public final class CBCMSDualCannonProjectiles {

	/**
	 * Hard ceiling enforced by the runtime. The real per-shot budget is
	 * {@link CBCMSDualCannonData#LIFETIME_TICKS}; this only needs to sit above any value the barrel,
	 * command and equipment modifiers can produce so it never truncates a legitimate shot.
	 */
	private static final int MAX_LIFETIME_TICKS = 600;

	/** Matches the interval Shaolib uses for its own dual cannon shells. */
	private static final int SYNC_INTERVAL_TICKS = 10;

	public static final ProjectileType<DualCannonShellState> NORMAL_AP_SHOT =
		register("normal_ap_shot", CBCMSDualCannonPropertyTypes.NORMAL_AP_SHOT, DualCannonShellBehavior.Kind.AP_SHOT);

	public static final ProjectileType<DualCannonShellState> EXTENDED_AP_SHOT =
		register("extended_ap_shot", CBCMSDualCannonPropertyTypes.EXTENDED_AP_SHOT, DualCannonShellBehavior.Kind.AP_SHOT);

	public static final ProjectileType<DualCannonShellState> NORMAL_HE_SHELL =
		register("normal_he_shell", CBCMSDualCannonPropertyTypes.NORMAL_HE_SHELL, DualCannonShellBehavior.Kind.HE);

	public static final ProjectileType<DualCannonShellState> NORMAL_ANTIAIR_HE_SHELL =
		register("normal_antiair_he_shell", CBCMSDualCannonPropertyTypes.NORMAL_ANTIAIR_HE_SHELL,
			DualCannonShellBehavior.Kind.ANTIAIR_HE);

	public static final ProjectileType<DualCannonShellState> EXTENDED_ANTIAIR_HE_SHELL =
		register("extended_antiair_he_shell", CBCMSDualCannonPropertyTypes.EXTENDED_ANTIAIR_HE_SHELL,
			DualCannonShellBehavior.Kind.ANTIAIR_HE);

	public static final ProjectileType<DualCannonShellState> NORMAL_AP_SHELL =
		register("normal_ap_shell", CBCMSDualCannonPropertyTypes.NORMAL_AP_SHELL, DualCannonShellBehavior.Kind.APHE);

	public static final ProjectileType<DualCannonShellState> NORMAL_APBC_SHELL =
		register("normal_apbc_shell", CBCMSDualCannonPropertyTypes.NORMAL_APBC_SHELL, DualCannonShellBehavior.Kind.APBC);

	public static final ProjectileType<DualCannonShellState> NORMAL_SAP_SHELL =
		register("normal_sap_shell", CBCMSDualCannonPropertyTypes.NORMAL_SAP_SHELL, DualCannonShellBehavior.Kind.SAP);

	public static final ProjectileType<DualCannonShellState> NORMAL_INCENDIARY_HE_SHELL =
		register("normal_incendiary_he_shell", CBCMSDualCannonPropertyTypes.NORMAL_INCENDIARY_HE_SHELL,
			DualCannonShellBehavior.Kind.INCENDIARY);

	private CBCMSDualCannonProjectiles() {}

	/** Forces static initialisation; call once during mod setup. */
	public static void register() {}

	private static <P extends CbcLikeMunitionProperties & DualCannonMunitionProperties>
	ProjectileType<DualCannonShellState> register(String path, MunitionPropertyType<P> propertyType,
												  DualCannonShellBehavior.Kind kind) {
		MunitionPropertyResolver<P> resolver =
			MunitionPropertyResolver.encodedJson(propertyType, CBCMSDualCannonData.DYNAMIC_PROPERTIES);
		return ShaolibProjectiles.register(
			ProjectileType.<DualCannonShellState>builder(Cbcmoreshells.resource(path),
					(projectile, level) -> new DualCannonShellState())
				.schema(CBCMSDualCannonData.SCHEMA)
				.motionModel(MunitionMotionModels.configured(resolver))
				.orientationSync(ProjectileOrientationSync.NONE)
				.behavior(new CBCMSDualCannonShellBehavior<>(resolver, kind))
				.serializer(new DualCannonShellSerializer<DualCannonShellState>())
				.syncIntervalTicks(SYNC_INTERVAL_TICKS)
				.maxLifetimeTicks(MAX_LIFETIME_TICKS)
				.build());
	}

}
