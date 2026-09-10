package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib;

import com.google.gson.JsonObject;
import com.verr1.shaolib.munitions.config.properties.MunitionPropertyComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.util.GsonHelper;

public record DualCannonImpactProperties(
	double smashToughness,
	double maximumMomentum,
	double spallExplosionPower,
	double highPenetrationMassPenaltyScale,
	double penetrationMassPenaltyScale,
	double bounceMassPenaltyScale,
	double minDeflection
) {

	public static final DualCannonImpactProperties DEFAULT =
		new DualCannonImpactProperties(1.0, 128.0, 1.0, 1.3, 1.0, 1.0 / 3.0, 0.005);

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
		minDeflection = MunitionPropertyComponents.finiteNonNegative("minDeflection", minDeflection);
	}

	public static DualCannonImpactProperties fromJson(JsonObject json, DualCannonImpactProperties fallback) {
		return new DualCannonImpactProperties(
			GsonHelper.getAsDouble(json, "smash_toughness", fallback.smashToughness()),
			GsonHelper.getAsDouble(json, "maximum_momentum", fallback.maximumMomentum()),
			GsonHelper.getAsDouble(json, "spall_explosion_power", fallback.spallExplosionPower()),
			GsonHelper.getAsDouble(json, "high_penetration_mass_penalty_scale", fallback.highPenetrationMassPenaltyScale()),
			GsonHelper.getAsDouble(json, "penetration_mass_penalty_scale", fallback.penetrationMassPenaltyScale()),
			GsonHelper.getAsDouble(json, "bounce_mass_penalty_scale", fallback.bounceMassPenaltyScale()),
				GsonHelper.getAsDouble(json, "minDeflection", fallback.minDeflection())
		);
	}

	public static void write(RegistryFriendlyByteBuf buffer, DualCannonImpactProperties impact) {
		buffer.writeDouble(impact.smashToughness());
		buffer.writeDouble(impact.maximumMomentum());
		buffer.writeDouble(impact.spallExplosionPower());
		buffer.writeDouble(impact.highPenetrationMassPenaltyScale());
		buffer.writeDouble(impact.penetrationMassPenaltyScale());
		buffer.writeDouble(impact.bounceMassPenaltyScale());
		buffer.writeDouble(impact.minDeflection());
	}

	public static DualCannonImpactProperties read(RegistryFriendlyByteBuf buffer) {
		return new DualCannonImpactProperties(buffer.readDouble(), buffer.readDouble(), buffer.readDouble(),
			buffer.readDouble(), buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
	}

}
