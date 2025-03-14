package com.cainiao1053.cbcmoreshells.munitions.big_cannon.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

import rbasamoyai.createbigcannons.munitions.big_cannon.config.BigCannonFuzePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.big_cannon.config.BigCannonProjectilePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.EntityPropertiesTypeHandler;
import rbasamoyai.createbigcannons.munitions.config.components.BallisticPropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.EntityDamagePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.ExplosionPropertiesComponent;

public class ShellessInertBigCannonPropertiesHandler extends EntityPropertiesTypeHandler<ShellessInertBigCannonProperties> {

	private static final ShellessInertBigCannonProperties DEFAULT = new ShellessInertBigCannonProperties(BallisticPropertiesComponent.DEFAULT,
		EntityDamagePropertiesComponent.DEFAULT, BigCannonProjectilePropertiesComponent.DEFAULT,
		0, 30);

	@Override
	protected ShellessInertBigCannonProperties parseJson(ResourceLocation location, JsonObject obj) throws JsonParseException {
		String id = location.toString();
		BallisticPropertiesComponent ballistics = BallisticPropertiesComponent.fromJson(id, obj);
		EntityDamagePropertiesComponent damage = EntityDamagePropertiesComponent.fromJson(id, obj);
		BigCannonProjectilePropertiesComponent bigCannonProperties = BigCannonProjectilePropertiesComponent.fromJson(id, obj);
		float maxCharges = Math.max(-1, getOrWarn(obj, "max_charges", id, 8f, JsonElement::getAsFloat));
		int lifetime = Math.max(0, getOrWarn(obj, "lifetime", id, 30, JsonElement::getAsInt));
		return new ShellessInertBigCannonProperties(ballistics, damage, bigCannonProperties, maxCharges, lifetime);
	}

	@Override
	protected ShellessInertBigCannonProperties readPropertiesFromNetwork(EntityType<?> entityType, FriendlyByteBuf buf) {
		BallisticPropertiesComponent ballistics = BallisticPropertiesComponent.fromNetwork(buf);
		EntityDamagePropertiesComponent damage = EntityDamagePropertiesComponent.fromNetwork(buf);
		BigCannonProjectilePropertiesComponent bigCannonProperties = BigCannonProjectilePropertiesComponent.fromNetwork(buf);
		float maxCharges = buf.readFloat();
		int lifetime = buf.readInt();
		return new ShellessInertBigCannonProperties(ballistics, damage, bigCannonProperties, maxCharges, lifetime);
	}

	@Override
	protected void writePropertiesToNetwork(ShellessInertBigCannonProperties properties, FriendlyByteBuf buf) {
		properties.ballistics().toNetwork(buf);
		properties.damage().toNetwork(buf);
		properties.bigCannonProperties().toNetwork(buf);
		buf.writeFloat(properties.maxCharges());
		buf.writeInt(properties.lifetime());
	}

	@Override protected ShellessInertBigCannonProperties getNoPropertiesValue() { return DEFAULT; }

}
