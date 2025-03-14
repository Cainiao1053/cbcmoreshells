package com.cainiao1053.cbcmoreshells.munitions.big_cannon.config;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;

public record TorpedoProjectilePropertiesComponent(float addedChargePower, float minimumChargePower, boolean canSquib, float addedRecoil, float buoyancyFactor, float torpedoSpeed, float torpedoSpread) {

	public static final TorpedoProjectilePropertiesComponent DEFAULT = new TorpedoProjectilePropertiesComponent(0, 0, false, 0, 0, 0, 1);

	public static TorpedoProjectilePropertiesComponent fromJson(String id, JsonObject obj) {
		float addedChargePower = Math.max(0, GsonHelper.getAsFloat(obj, "added_charge_power", 0));
		float minimumChargePower = Math.max(0, GsonHelper.getAsFloat(obj, "minimum_charge_power", 1));
		boolean canSquib = GsonHelper.getAsBoolean(obj, "can_squib", true);
		float addedRecoil = Math.max(0, GsonHelper.getAsFloat(obj, "added_recoil", 1));
		float buoyancyFactor = Math.max(0, GsonHelper.getAsFloat(obj, "buoyancy_factor", 0.1f));
		float torpedoSpeed = Math.max(0, GsonHelper.getAsFloat(obj, "torpedo_speed", 1f));
		float torpedoSread = Math.max(0, GsonHelper.getAsFloat(obj, "torpedo_spread", 1f));
		return new TorpedoProjectilePropertiesComponent(addedChargePower, minimumChargePower, canSquib, addedRecoil, buoyancyFactor, torpedoSpeed, torpedoSread);
	}

	public static TorpedoProjectilePropertiesComponent fromNetwork(FriendlyByteBuf buf) {
		return new TorpedoProjectilePropertiesComponent(buf.readFloat(), buf.readFloat(), buf.readBoolean(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
	}

	public void toNetwork(FriendlyByteBuf buf) {
		buf.writeFloat(this.addedChargePower)
			.writeFloat(this.minimumChargePower)
			.writeBoolean(this.canSquib)
			.writeFloat(this.addedRecoil)
			.writeFloat(this.buoyancyFactor)
			.writeFloat(this.torpedoSpeed)
		    .writeFloat(this.torpedoSpread);
	}

}
