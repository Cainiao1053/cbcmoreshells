package com.cainiao1053.cbcmoreshells.munitions.autocannon.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import rbasamoyai.createbigcannons.munitions.config.EntityPropertiesTypeHandler;
import rbasamoyai.createbigcannons.munitions.config.components.BallisticPropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.EntityDamagePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.autocannon.config.AutocannonProjectilePropertiesComponent;

public class AntiairAutocannonProjectilePropertiesHandler extends EntityPropertiesTypeHandler<AntiairAutocannonProjectileProperties> {

	private static final AntiairAutocannonProjectileProperties DEFAULT = new AntiairAutocannonProjectileProperties(BallisticPropertiesComponent.DEFAULT,
		EntityDamagePropertiesComponent.DEFAULT, AutocannonProjectilePropertiesComponent.DEFAULT);

	@Override
	protected AntiairAutocannonProjectileProperties parseJson(ResourceLocation location, JsonObject obj) throws JsonParseException {
		String id = location.toString();
		BallisticPropertiesComponent ballistics = BallisticPropertiesComponent.fromJson(id, obj);
		EntityDamagePropertiesComponent damage = EntityDamagePropertiesComponent.fromJson(id, obj);
		AutocannonProjectilePropertiesComponent autocannonProperties = AutocannonProjectilePropertiesComponent.fromJson(id, obj);
		return new AntiairAutocannonProjectileProperties(ballistics, damage, autocannonProperties);
	}

	@Override
	protected AntiairAutocannonProjectileProperties readPropertiesFromNetwork(EntityType<?> entityType, FriendlyByteBuf buf) {
		BallisticPropertiesComponent ballistics = BallisticPropertiesComponent.fromNetwork(buf);
		EntityDamagePropertiesComponent damage = EntityDamagePropertiesComponent.fromNetwork(buf);
		AutocannonProjectilePropertiesComponent autocannonProperties = AutocannonProjectilePropertiesComponent.fromNetwork(buf);
		return new AntiairAutocannonProjectileProperties(ballistics, damage, autocannonProperties);
	}

	@Override
	protected void writePropertiesToNetwork(AntiairAutocannonProjectileProperties properties, FriendlyByteBuf buf) {
		properties.ballistics().toNetwork(buf);
		properties.damage().toNetwork(buf);
		properties.autocannonProperties().toNetwork(buf);
	}

	@Override protected AntiairAutocannonProjectileProperties getNoPropertiesValue() { return DEFAULT; }

}
