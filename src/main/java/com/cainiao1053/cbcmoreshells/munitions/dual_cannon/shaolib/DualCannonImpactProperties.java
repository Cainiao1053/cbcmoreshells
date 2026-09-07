package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib;

import com.google.gson.JsonObject;
import com.verr1.shaolib.munitions.config.properties.MunitionPropertyComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.util.GsonHelper;

/**
 * Dual-cannon-specific impact tuning, layered on top of the generic
 * {@link MunitionPropertyComponents.ImpactProperties}.
 *
 * <p>{@code smashToughness} lets a heavy shell force its way through armour it could not otherwise
 * defeat; {@code maximumMomentum} caps the momentum used in the penetration test so muzzle velocity
 * cannot scale without limit; the three penalty scales control how much durability mass is spent per
 * penetration/ricochet.
 *
 * <p>Owned by cbcms — see {@link DualCannonLaunchProperties} for why this is not taken from Shaolib.
 */
public record DualCannonImpactProperties(
	double smashToughness,
	double maximumMomentum,
	double spallExplosionPower,
	double highPenetrationMassPenaltyScale,
	double penetrationMassPenaltyScale,
	double bounceMassPenaltyScale
) {

	public static final DualCannonImpactProperties DEFAULT =
		new DualCannonImpactProperties(1.0, 128.0, 1.0, 1.3, 1.08, 1.0 / 3.0);

	public DualCannonImpactProperties {
		smashToughness = MunitionPropertyComponents.finiteNonNegative("smashToughness", smashToughness);
		maximumMomentum = MunitionPropertyComponents.finiteNonNegative("maximumMomentum", maximumMomentum);
		spallExplosionPower = MunitionPropertyComponents.finiteNonNegative("spallExplosionPower", spallExplosionPower);
		highPenetrationMassPenaltyScale =
			MunitionPropertyComponents.finiteNonNegative("highPenetrationMassPenaltyScale", highPenetrationMassPenaltyScale);
		penetrationMassPenaltyScale =
			MunitionPropertyComponents.finiteNonNegative("penetrationMassPenaltyScale", penetrationMassPenaltyScale);
		bounceMassPenaltyScale =
			MunitionPropertyComponents.finiteNonNegative("bounceMassPenaltyScale", bounceMassPenaltyScale);
	}

	public static DualCannonImpactProperties fromJson(JsonObject json, DualCannonImpactProperties fallback) {
		return new DualCannonImpactProperties(
			GsonHelper.getAsDouble(json, "smash_toughness", fallback.smashToughness()),
			GsonHelper.getAsDouble(json, "maximum_momentum", fallback.maximumMomentum()),
			GsonHelper.getAsDouble(json, "spall_explosion_power", fallback.spallExplosionPower()),
			GsonHelper.getAsDouble(json, "high_penetration_mass_penalty_scale", fallback.highPenetrationMassPenaltyScale()),
			GsonHelper.getAsDouble(json, "penetration_mass_penalty_scale", fallback.penetrationMassPenaltyScale()),
			GsonHelper.getAsDouble(json, "bounce_mass_penalty_scale", fallback.bounceMassPenaltyScale()));
	}

	public static void write(RegistryFriendlyByteBuf buffer, DualCannonImpactProperties impact) {
		buffer.writeDouble(impact.smashToughness());
		buffer.writeDouble(impact.maximumMomentum());
		buffer.writeDouble(impact.spallExplosionPower());
		buffer.writeDouble(impact.highPenetrationMassPenaltyScale());
		buffer.writeDouble(impact.penetrationMassPenaltyScale());
		buffer.writeDouble(impact.bounceMassPenaltyScale());
	}

	public static DualCannonImpactProperties read(RegistryFriendlyByteBuf buffer) {
		return new DualCannonImpactProperties(buffer.readDouble(), buffer.readDouble(), buffer.readDouble(),
			buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
	}

}
