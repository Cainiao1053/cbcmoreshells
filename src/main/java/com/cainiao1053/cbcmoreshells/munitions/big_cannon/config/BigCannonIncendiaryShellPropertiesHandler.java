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

public class BigCannonIncendiaryShellPropertiesHandler extends EntityPropertiesTypeHandler<BigCannonIncendiaryShellProperties> {

	private static final BigCannonIncendiaryShellProperties DEFAULT = new BigCannonIncendiaryShellProperties(BallisticPropertiesComponent.DEFAULT,
		EntityDamagePropertiesComponent.DEFAULT, BigCannonProjectilePropertiesComponent.DEFAULT, BigCannonFuzePropertiesComponent.DEFAULT,
		ExplosionPropertiesComponent.DEFAULT, 0, 30, 0.05f, 5);

	@Override
	protected BigCannonIncendiaryShellProperties parseJson(ResourceLocation location, JsonObject obj) throws JsonParseException {
		String id = location.toString();
		BallisticPropertiesComponent ballistics = BallisticPropertiesComponent.fromJson(id, obj);
		EntityDamagePropertiesComponent damage = EntityDamagePropertiesComponent.fromJson(id, obj);
		BigCannonProjectilePropertiesComponent bigCannonProperties = BigCannonProjectilePropertiesComponent.fromJson(id, obj);
		BigCannonFuzePropertiesComponent fuze = BigCannonFuzePropertiesComponent.fromJson(id, obj);
		ExplosionPropertiesComponent explosion = ExplosionPropertiesComponent.fromJson(id, obj);
		float maxCharges = Math.max(-1, getOrWarn(obj, "max_charges", id, 2f, JsonElement::getAsFloat));
		int lifetime = Math.max(0, getOrWarn(obj, "lifetime", id, 30, JsonElement::getAsInt));
		float fireChance = Math.max(0, getOrWarn(obj, "fire_chance", id, 0.05f, JsonElement::getAsFloat));
		int fireRange = Math.max(0, getOrWarn(obj, "fire_range", id, 5, JsonElement::getAsInt));
		return new BigCannonIncendiaryShellProperties(ballistics, damage, bigCannonProperties, fuze, explosion, maxCharges, lifetime, fireChance, fireRange);
	}

	@Override
	protected BigCannonIncendiaryShellProperties readPropertiesFromNetwork(EntityType<?> entityType, FriendlyByteBuf buf) {
		BallisticPropertiesComponent ballistics = BallisticPropertiesComponent.fromNetwork(buf);
		EntityDamagePropertiesComponent damage = EntityDamagePropertiesComponent.fromNetwork(buf);
		BigCannonProjectilePropertiesComponent bigCannonProperties = BigCannonProjectilePropertiesComponent.fromNetwork(buf);
		BigCannonFuzePropertiesComponent fuze = BigCannonFuzePropertiesComponent.fromNetwork(buf);
		ExplosionPropertiesComponent explosion = ExplosionPropertiesComponent.fromNetwork(buf);
		float maxCharges = buf.readFloat();
		int lifetime = buf.readInt();
		float fireChance = buf.readFloat();
		int fireRange = buf.readInt();
		return new BigCannonIncendiaryShellProperties(ballistics, damage, bigCannonProperties, fuze, explosion, maxCharges, lifetime, fireChance, fireRange);
	}

	@Override
	protected void writePropertiesToNetwork(BigCannonIncendiaryShellProperties properties, FriendlyByteBuf buf) {
		properties.ballistics().toNetwork(buf);
		properties.damage().toNetwork(buf);
		properties.bigCannonProperties().toNetwork(buf);
		properties.fuze().toNetwork(buf);
		properties.explosion().toNetwork(buf);
		buf.writeFloat(properties.maxCharges());
		buf.writeInt(properties.lifetime());
		buf.writeFloat(properties.fireChance());
		buf.writeInt(properties.fireRange());
	}

	@Override protected BigCannonIncendiaryShellProperties getNoPropertiesValue() { return DEFAULT; }

}
