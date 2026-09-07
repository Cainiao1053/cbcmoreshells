package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib;

import com.google.gson.JsonObject;

/**
 * Builds the per-shot property overrides carried in
 * {@link CBCMSDualCannonData#DYNAMIC_PROPERTIES}.
 *
 * <p>A dual cannon's barrel material, combat commands and equipment all scale the shell that comes
 * out of it. Registering a projectile type per combination is not viable, so instead each shot
 * carries a small JSON patch and {@code MunitionPropertyResolver.encodedJson} layers it over the
 * datapack config at resolve time (once per projectile, memoised).
 *
 * <p><b>Why durability mass goes through here rather than being set directly.</b>
 * {@code AbstractFuzedShellBehavior.onSpawn} calls {@code initializeDurabilityMass(...)}, which only
 * writes when the state has no mass yet. Whether a spawn initializer's handle phase runs before or
 * after {@code onSpawn} is not something this code should depend on, so rather than racing it we put
 * the value in the properties the behavior itself reads. The result is order-independent.
 *
 * <p>{@link DualCannonState#setDurabilityModifier} is still set separately and is <em>not</em>
 * redundant: the modifier drives explosive yield scaling, while the mass written here drives
 * penetration. They are different quantities derived from the same multiplier.
 */
public final class CBCMSDualCannonShotOverrides {

	private CBCMSDualCannonShotOverrides() {}

	/**
	 * @param durabilityMassModifier combined barrel/command/equipment multiplier for this shot
	 * @param baseDurabilityMass     the munition's configured durability mass
	 * @return a JSON string for {@link CBCMSDualCannonData#DYNAMIC_PROPERTIES}, or an empty string
	 *         when the shot needs no overrides at all — the resolver treats empty as "use the
	 *         datapack config unchanged", which skips a parse
	 */
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

	/**
	 * Reach is normally left to {@code runtime.max_distance} from the datapack, with the real limit
	 * being the per-shot tick budget on {@link DualCannonState}. This variant is here for callers
	 * that want to clamp distance for a specific shot as well.
	 */
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
