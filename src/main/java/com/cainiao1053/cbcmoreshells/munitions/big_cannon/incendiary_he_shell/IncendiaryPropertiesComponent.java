package com.cainiao1053.cbcmoreshells.munitions.big_cannon.incendiary_he_shell;

import static rbasamoyai.createbigcannons.munitions.config.PropertiesTypeHandler.getOrWarn;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;

public record IncendiaryPropertiesComponent(float explosivePower) {

	public static final IncendiaryPropertiesComponent DEFAULT = new IncendiaryPropertiesComponent(0);

	public static IncendiaryPropertiesComponent fromJson(String id, JsonObject obj) {
		float explosionPower = Math.max(0, getOrWarn(obj, "explosive_power", id, 2f, JsonElement::getAsFloat));
		return new IncendiaryPropertiesComponent(explosionPower);
	}

	public static IncendiaryPropertiesComponent fromNetwork(FriendlyByteBuf buf) {
		return new IncendiaryPropertiesComponent(buf.readFloat());
	}

	public void toNetwork(FriendlyByteBuf buf) {
		buf.writeFloat(this.explosivePower);
	}

}
