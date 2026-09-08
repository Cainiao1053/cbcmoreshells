package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib;

import com.google.gson.JsonObject;

public final class CBCMSDualCannonShotOverrides {

	private CBCMSDualCannonShotOverrides() {}
	public static String encode(double durabilityMassModifier, double baseDurabilityMass) {
		if (isUnitModifier(durabilityMassModifier)) return "";

		JsonObject ballistics = new JsonObject();
		ballistics.addProperty("durability_mass", Math.max(0.0, baseDurabilityMass * durabilityMassModifier));

		JsonObject overrides = new JsonObject();
		overrides.add("ballistics", ballistics);

		JsonObject root = new JsonObject();
		root.add("overrides", overrides);
		return root.toString();
	}
	public static String encode(double durabilityMassModifier, double baseDurabilityMass, double maxDistance) {
		JsonObject ballistics = new JsonObject();
		ballistics.addProperty("durability_mass", Math.max(0.0, baseDurabilityMass * durabilityMassModifier));

		JsonObject runtime = new JsonObject();
		runtime.addProperty("max_distance", Math.max(0.0, maxDistance));

		JsonObject overrides = new JsonObject();
		overrides.add("ballistics", ballistics);
		overrides.add("runtime", runtime);

		JsonObject root = new JsonObject();
		root.add("overrides", overrides);
		return root.toString();
	}

	/** Treats near-1 modifiers as "no change", so an unmodified barrel does not pay for a parse. */
	private static boolean isUnitModifier(double modifier) {
		return Math.abs(modifier - 1.0) < 1.0E-6;
	}

}
