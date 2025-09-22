package com.cainiao1053.cbcmoreshells.munitions.racked_projectile.config;

import com.cainiao1053.cbcmoreshells.munitions.racked_projectile.aphe_bomb.APHEBombBlock;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import com.tterrag.registrate.util.nullness.NonnullType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

import rbasamoyai.createbigcannons.munitions.big_cannon.config.BigCannonFuzePropertiesComponent;
//import rbasamoyai.createbigcannons.munitions.big_cannon.config.BigCannonProjectilePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.EntityPropertiesTypeHandler;
import rbasamoyai.createbigcannons.munitions.config.components.BallisticPropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.EntityDamagePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.ExplosionPropertiesComponent;

public class RackedProjectilePropertiesHandler extends EntityPropertiesTypeHandler<RackedProjectileProperties> {

	private static final RackedProjectileProperties DEFAULT = new RackedProjectileProperties(BallisticPropertiesComponent.DEFAULT,
		EntityDamagePropertiesComponent.DEFAULT, RackedProjectilePropertiesComponent.DEFAULT, BigCannonFuzePropertiesComponent.DEFAULT,
		ExplosionPropertiesComponent.DEFAULT, 4, 30);

	@Override
	protected RackedProjectileProperties parseJson(ResourceLocation location, JsonObject obj) throws JsonParseException {
		String id = location.toString();
		BallisticPropertiesComponent ballistics = BallisticPropertiesComponent.fromJson(id, obj);
		EntityDamagePropertiesComponent damage = EntityDamagePropertiesComponent.fromJson(id, obj);
		RackedProjectilePropertiesComponent rackedProjectileProperties = RackedProjectilePropertiesComponent.fromJson(id, obj);
		BigCannonFuzePropertiesComponent fuze = BigCannonFuzePropertiesComponent.fromJson(id, obj);
		ExplosionPropertiesComponent explosion = ExplosionPropertiesComponent.fromJson(id, obj);
		int explosionCooldown = Math.max(-1, getOrWarn(obj, "explosion_cooldown", id, 15, JsonElement::getAsInt));
		int lifetime = Math.max(0, getOrWarn(obj, "lifetime", id, 80, JsonElement::getAsInt));
		return new RackedProjectileProperties(ballistics, damage, rackedProjectileProperties, fuze, explosion, explosionCooldown, lifetime);
	}

	@Override
	protected RackedProjectileProperties readPropertiesFromNetwork(EntityType<?> entityType, FriendlyByteBuf buf) {
		BallisticPropertiesComponent ballistics = BallisticPropertiesComponent.fromNetwork(buf);
		EntityDamagePropertiesComponent damage = EntityDamagePropertiesComponent.fromNetwork(buf);
		RackedProjectilePropertiesComponent rackedProjectileProperties = RackedProjectilePropertiesComponent.fromNetwork(buf);
		BigCannonFuzePropertiesComponent fuze = BigCannonFuzePropertiesComponent.fromNetwork(buf);
		ExplosionPropertiesComponent explosion = ExplosionPropertiesComponent.fromNetwork(buf);
		int explosionCooldown = buf.readInt();
		int lifetime = buf.readInt();
		return new RackedProjectileProperties(ballistics, damage, rackedProjectileProperties, fuze, explosion, explosionCooldown, lifetime);
	}

	@Override
	protected void writePropertiesToNetwork(RackedProjectileProperties properties, FriendlyByteBuf buf) {
		properties.ballistics().toNetwork(buf);
		properties.damage().toNetwork(buf);
		properties.rackedProjectileProperties().toNetwork(buf);
		properties.fuze().toNetwork(buf);
		properties.explosion().toNetwork(buf);
		buf.writeInt(properties.explosionCooldown());
		buf.writeInt(properties.lifetime());
	}

	@Override protected RackedProjectileProperties getNoPropertiesValue() { return DEFAULT; }

}
