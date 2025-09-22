package com.cainiao1053.cbcmoreshells.munitions.racked_projectile.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.EntityType;

import rbasamoyai.createbigcannons.munitions.big_cannon.config.BigCannonFuzePropertiesComponent;
//import rbasamoyai.createbigcannons.munitions.big_cannon.config.BigCannonProjectilePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.EntityPropertiesTypeHandler;
import rbasamoyai.createbigcannons.munitions.config.components.BallisticPropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.EntityDamagePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.ExplosionPropertiesComponent;

public class RackedRocketProjectilePropertiesHandler extends EntityPropertiesTypeHandler<RackedRocketProjectileProperties> {

	private static final RackedRocketProjectileProperties DEFAULT = new RackedRocketProjectileProperties(BallisticPropertiesComponent.DEFAULT,
		EntityDamagePropertiesComponent.DEFAULT, RackedProjectilePropertiesComponent.DEFAULT, BigCannonFuzePropertiesComponent.DEFAULT,
		ExplosionPropertiesComponent.DEFAULT, 4, 30, 60, 3);

	@Override
	protected RackedRocketProjectileProperties parseJson(ResourceLocation location, JsonObject obj) throws JsonParseException {
		String id = location.toString();
		BallisticPropertiesComponent ballistics = BallisticPropertiesComponent.fromJson(id, obj);
		EntityDamagePropertiesComponent damage = EntityDamagePropertiesComponent.fromJson(id, obj);
		RackedProjectilePropertiesComponent rackedProjectileProperties = RackedProjectilePropertiesComponent.fromJson(id, obj);
		BigCannonFuzePropertiesComponent fuze = BigCannonFuzePropertiesComponent.fromJson(id, obj);
		ExplosionPropertiesComponent explosion = ExplosionPropertiesComponent.fromJson(id, obj);
		int explosionCooldown = Math.max(-1, getOrWarn(obj, "explosion_cooldown", id, 15, JsonElement::getAsInt));
		int lifetime = Math.max(0, getOrWarn(obj, "lifetime", id, 80, JsonElement::getAsInt));
		int thrustTime = Math.max(0, getOrWarn(obj, "thrust_time", id, 60, JsonElement::getAsInt));
		float steadyStateVel = Math.max(0, GsonHelper.getAsFloat(obj, "steady_state_vel", 3f));
		return new RackedRocketProjectileProperties(ballistics, damage, rackedProjectileProperties, fuze, explosion, explosionCooldown, lifetime, thrustTime, steadyStateVel);
	}

	@Override
	protected RackedRocketProjectileProperties readPropertiesFromNetwork(EntityType<?> entityType, FriendlyByteBuf buf) {
		BallisticPropertiesComponent ballistics = BallisticPropertiesComponent.fromNetwork(buf);
		EntityDamagePropertiesComponent damage = EntityDamagePropertiesComponent.fromNetwork(buf);
		RackedProjectilePropertiesComponent rackedProjectileProperties = RackedProjectilePropertiesComponent.fromNetwork(buf);
		BigCannonFuzePropertiesComponent fuze = BigCannonFuzePropertiesComponent.fromNetwork(buf);
		ExplosionPropertiesComponent explosion = ExplosionPropertiesComponent.fromNetwork(buf);
		int explosionCooldown = buf.readInt();
		int lifetime = buf.readInt();
		int thrustTime = buf.readInt();
		float steadyStateVel = buf.readFloat();
		return new RackedRocketProjectileProperties(ballistics, damage, rackedProjectileProperties, fuze, explosion, explosionCooldown, lifetime, thrustTime, steadyStateVel);
	}

	@Override
	protected void writePropertiesToNetwork(RackedRocketProjectileProperties properties, FriendlyByteBuf buf) {
		properties.ballistics().toNetwork(buf);
		properties.damage().toNetwork(buf);
		properties.rackedProjectileProperties().toNetwork(buf);
		properties.fuze().toNetwork(buf);
		properties.explosion().toNetwork(buf);
		buf.writeInt(properties.explosionCooldown());
		buf.writeInt(properties.lifetime());
		buf.writeInt(properties.thrustTime());
		buf.writeFloat(properties.steadyStateVel());
	}

	@Override protected RackedRocketProjectileProperties getNoPropertiesValue() { return DEFAULT; }

}
