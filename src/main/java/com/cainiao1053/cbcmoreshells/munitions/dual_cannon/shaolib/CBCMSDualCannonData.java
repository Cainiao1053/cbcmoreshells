package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib;

import com.verr1.shaolib.api.projectile.data.ProjectileDataAccessor;
import com.verr1.shaolib.api.projectile.data.ProjectileDataSchema;
import com.verr1.shaolib.api.projectile.data.ProjectileDataSerializers;
import com.verr1.shaolib.api.projectile.data.ProjectileSyncPolicy;
import com.verr1.shaolib.munitions.projectile.shell.FuzedShellData;
import net.minecraft.world.phys.Vec3;

/**
 * Per-instance data carried by cbcms dual cannon projectiles, on top of
 * {@link FuzedShellData}'s schema.
 *
 * <p>These live on the {@code ProjectileInstance} rather than on the server state because
 * {@code DualCannonShellBehavior} is nailed to {@code DualCannonShellState} (it extends
 * {@code AbstractFuzedShellBehavior<DualCannonShellState, P>}, not a type variable), so we cannot
 * subclass the state without also giving up the library's behavior. Schema values are written by
 * {@code ProjectileInstance.save} regardless of sync policy, so they persist across saves.
 */
public final class CBCMSDualCannonData {

	private static final ProjectileDataSchema.Builder BUILDER = FuzedShellData.SCHEMA.extend();

	/**
	 * Per-shot tick budget, replacing the old {@code AbstractDualCannonProjectile.maxAge}. Set at
	 * launch from the munition's base lifetime plus barrel/command/equipment modifiers.
	 */
	public static final ProjectileDataAccessor<Integer> LIFETIME_TICKS =
		BUILDER.define("cbcms_lifetime_ticks", ProjectileDataSerializers.INT, 30, ProjectileSyncPolicy.SPAWN_ONLY);

	/**
	 * Encoded JSON property overrides for this shot, consumed by
	 * {@code MunitionPropertyResolver.encodedJson}. Empty means "use the datapack config as-is".
	 */
	public static final ProjectileDataAccessor<String> DYNAMIC_PROPERTIES =
		BUILDER.define("cbcms_dynamic_properties", ProjectileDataSerializers.STRING, "", ProjectileSyncPolicy.SPAWN_ONLY);

	/** Last point a trail segment was drawn from. Server-only bookkeeping. */
	public static final ProjectileDataAccessor<Vec3> TRAIL_ANCHOR =
		BUILDER.define("cbcms_trail_anchor", ProjectileDataSerializers.VEC3, Vec3.ZERO, ProjectileSyncPolicy.LOCAL_ONLY);

	/** Ticks remaining before the next trail segment is sent. Server-only bookkeeping. */
	public static final ProjectileDataAccessor<Integer> TRAIL_COOLDOWN =
		BUILDER.define("cbcms_trail_cooldown", ProjectileDataSerializers.INT, 20, ProjectileSyncPolicy.LOCAL_ONLY);

	/** {@link #TRAIL_STAGE_PENDING} / {@link #TRAIL_STAGE_TRACING} / {@link #TRAIL_STAGE_DONE}. */
	public static final ProjectileDataAccessor<Integer> TRAIL_STAGE =
		BUILDER.define("cbcms_trail_stage", ProjectileDataSerializers.INT, 0, ProjectileSyncPolicy.LOCAL_ONLY);

	public static final int TRAIL_STAGE_PENDING = 0;
	public static final int TRAIL_STAGE_TRACING = 1;
	public static final int TRAIL_STAGE_DONE = 2;

	public static final ProjectileDataSchema SCHEMA = BUILDER.build();

	private CBCMSDualCannonData() {}

}
