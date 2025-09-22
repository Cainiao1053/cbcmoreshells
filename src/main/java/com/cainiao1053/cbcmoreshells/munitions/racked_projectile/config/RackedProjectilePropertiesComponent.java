package com.cainiao1053.cbcmoreshells.munitions.racked_projectile.config;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;

public record RackedProjectilePropertiesComponent(float addedChargePower, float minimumChargePower, boolean canSquib, float addedRecoil, float initialVel, float projectileSpread) {

	public static final RackedProjectilePropertiesComponent DEFAULT = new RackedProjectilePropertiesComponent(0, 0, false, 0, 0, 1);

	public static RackedProjectilePropertiesComponent fromJson(String id, JsonObject obj) {
		float addedChargePower = Math.max(0, GsonHelper.getAsFloat(obj, "added_charge_power", 0));
		float minimumChargePower = Math.max(0, GsonHelper.getAsFloat(obj, "minimum_charge_power", 1));
		boolean canSquib = GsonHelper.getAsBoolean(obj, "can_squib", true);
		float addedRecoil = Math.max(0, GsonHelper.getAsFloat(obj, "added_recoil", 1));;
		float initialVel = Math.max(0, GsonHelper.getAsFloat(obj, "initial_vel", 0.5f));
		float projectileSpread = Math.max(0, GsonHelper.getAsFloat(obj, "projectile_spread", 0.5f));
		return new RackedProjectilePropertiesComponent(addedChargePower, minimumChargePower, canSquib, addedRecoil, initialVel, projectileSpread);
	}

	public static RackedProjectilePropertiesComponent fromNetwork(FriendlyByteBuf buf) {
		return new RackedProjectilePropertiesComponent(buf.readFloat(), buf.readFloat(), buf.readBoolean(), buf.readFloat(), buf.readFloat(), buf.readFloat());
	}

	public void toNetwork(FriendlyByteBuf buf) {
		buf.writeFloat(this.addedChargePower)
			.writeFloat(this.minimumChargePower)
			.writeBoolean(this.canSquib)
			.writeFloat(this.addedRecoil)
			.writeFloat(this.initialVel)
		    .writeFloat(this.projectileSpread);
	}

}
