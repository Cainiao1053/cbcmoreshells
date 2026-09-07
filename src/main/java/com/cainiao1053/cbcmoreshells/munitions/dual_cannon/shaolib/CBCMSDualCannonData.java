package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib;

import com.verr1.shaolib.api.projectile.data.ProjectileDataAccessor;
import com.verr1.shaolib.api.projectile.data.ProjectileDataSchema;
import com.verr1.shaolib.api.projectile.data.ProjectileDataSerializers;
import com.verr1.shaolib.api.projectile.data.ProjectileSyncPolicy;
import com.verr1.shaolib.munitions.projectile.shell.FuzedShellData;

/**
 * Per-instance projectile data for cbcms dual cannon shells, on top of {@link FuzedShellData}.
 *
 * <p>Only values that must live on the {@code ProjectileInstance} belong here; everything else is a
 * field on {@link DualCannonState}. The single entry below qualifies because
 * {@code MunitionPropertyResolver.encodedJson} reads it through a {@link ProjectileDataAccessor},
 * which can only address instance data.
 */
public final class CBCMSDualCannonData {

	private static final ProjectileDataSchema.Builder BUILDER = FuzedShellData.SCHEMA.extend();

	/**
	 * Per-shot property overrides as JSON, consumed by
	 * {@code MunitionPropertyResolver.encodedJson}. Empty means "use the datapack config unchanged".
	 *
	 * <p>This is how a single shell type covers every barrel: the contraption encodes the durability
	 * mass and reach it actually fired with, instead of the mod registering a type per combination.
	 */
	public static final ProjectileDataAccessor<String> DYNAMIC_PROPERTIES = BUILDER.define("cbcms_dynamic_properties",
		ProjectileDataSerializers.STRING, "", ProjectileSyncPolicy.SPAWN_ONLY);

	public static final ProjectileDataSchema SCHEMA = BUILDER.build();

	private CBCMSDualCannonData() {}

}
