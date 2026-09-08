package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib;

import com.verr1.shaolib.api.projectile.data.ProjectileDataAccessor;
import com.verr1.shaolib.api.projectile.data.ProjectileDataSchema;
import com.verr1.shaolib.api.projectile.data.ProjectileDataSerializers;
import com.verr1.shaolib.api.projectile.data.ProjectileSyncPolicy;
import com.verr1.shaolib.munitions.projectile.shell.FuzedShellData;

public final class CBCMSDualCannonData {

	private static final ProjectileDataSchema.Builder BUILDER = FuzedShellData.SCHEMA.extend();

	public static final ProjectileDataAccessor<String> DYNAMIC_PROPERTIES = BUILDER.define("cbcms_dynamic_properties",
		ProjectileDataSerializers.STRING, "", ProjectileSyncPolicy.SPAWN_ONLY);

	public static final ProjectileDataSchema SCHEMA = BUILDER.build();

	private CBCMSDualCannonData() {}

}
