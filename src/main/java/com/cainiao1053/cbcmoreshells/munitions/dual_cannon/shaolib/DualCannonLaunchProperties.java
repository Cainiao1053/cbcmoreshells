package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib;

import com.google.gson.JsonObject;
import com.verr1.shaolib.munitions.config.properties.MunitionPropertyComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.util.GsonHelper;

public record DualCannonLaunchProperties(
	double addedChargePower,
	double minimumChargePower,
	boolean canSquib,
	double addedRecoil,
	double initialVelocity,
	double projectileSpread,
	double minimumSpread,
	double reloadTimeCoefficient,
	double cooldownReductionRate
) {

	public static final DualCannonLaunchProperties DEFAULT =
		new DualCannonLaunchProperties(0.0, 0.0, true, 0.0, 4.0, 0.0, 0.0, 1.0, 0.0);

	public DualCannonLaunchProperties {
		addedChargePower = MunitionPropertyComponents.finiteNonNegative("addedChargePower", addedChargePower);
		minimumChargePower = MunitionPropertyComponents.finiteNonNegative("minimumChargePower", minimumChargePower);
		addedRecoil = MunitionPropertyComponents.finiteNonNegative("addedRecoil", addedRecoil);
		initialVelocity = MunitionPropertyComponents.finiteNonNegative("initialVelocity", initialVelocity);
		projectileSpread = MunitionPropertyComponents.finiteNonNegative("projectileSpread", projectileSpread);
		minimumSpread = MunitionPropertyComponents.finiteNonNegative("minimumSpread", minimumSpread);
		reloadTimeCoefficient = MunitionPropertyComponents.finiteNonNegative("reloadTimeCoefficient", reloadTimeCoefficient);
		cooldownReductionRate = MunitionPropertyComponents.finiteNonNegative("cooldownReductionRate", cooldownReductionRate);
	}

	public static DualCannonLaunchProperties fromJson(JsonObject json, DualCannonLaunchProperties fallback) {
		return new DualCannonLaunchProperties(
			GsonHelper.getAsDouble(json, "added_charge_power", fallback.addedChargePower()),
			GsonHelper.getAsDouble(json, "minimum_charge_power", fallback.minimumChargePower()),
			GsonHelper.getAsBoolean(json, "can_squib", fallback.canSquib()),
			GsonHelper.getAsDouble(json, "added_recoil", fallback.addedRecoil()),
			GsonHelper.getAsDouble(json, "initial_velocity",
				GsonHelper.getAsDouble(json, "initial_vel", fallback.initialVelocity())),
			GsonHelper.getAsDouble(json, "projectile_spread", fallback.projectileSpread()),
			GsonHelper.getAsDouble(json, "minimum_spread", fallback.minimumSpread()),
			GsonHelper.getAsDouble(json, "reload_time_coefficient",
				GsonHelper.getAsDouble(json, "reload_time_coef", fallback.reloadTimeCoefficient())),
			GsonHelper.getAsDouble(json, "cooldown_reduction_rate", fallback.cooldownReductionRate()));
	}

	public static void write(RegistryFriendlyByteBuf buffer, DualCannonLaunchProperties launch) {
		buffer.writeDouble(launch.addedChargePower());
		buffer.writeDouble(launch.minimumChargePower());
		buffer.writeBoolean(launch.canSquib());
		buffer.writeDouble(launch.addedRecoil());
		buffer.writeDouble(launch.initialVelocity());
		buffer.writeDouble(launch.projectileSpread());
		buffer.writeDouble(launch.minimumSpread());
		buffer.writeDouble(launch.reloadTimeCoefficient());
		buffer.writeDouble(launch.cooldownReductionRate());
	}

	public static DualCannonLaunchProperties read(RegistryFriendlyByteBuf buffer) {
		return new DualCannonLaunchProperties(
			buffer.readDouble(), buffer.readDouble(), buffer.readBoolean(), buffer.readDouble(), buffer.readDouble(),
			buffer.readDouble(), buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
	}

}
