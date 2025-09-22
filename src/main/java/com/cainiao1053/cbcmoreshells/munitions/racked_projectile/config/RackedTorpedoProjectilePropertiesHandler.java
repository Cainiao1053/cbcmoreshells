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

import static java.lang.Math.max;

public class RackedTorpedoProjectilePropertiesHandler extends EntityPropertiesTypeHandler<RackedTorpedoProjectileProperties> {

	private static final RackedTorpedoProjectileProperties DEFAULT = new RackedTorpedoProjectileProperties(BallisticPropertiesComponent.DEFAULT,
		EntityDamagePropertiesComponent.DEFAULT, RackedProjectilePropertiesComponent.DEFAULT, BigCannonFuzePropertiesComponent.DEFAULT,
		ExplosionPropertiesComponent.DEFAULT, 4, 30, 0f, 0.5f, 0.1f);

	@Override
	protected RackedTorpedoProjectileProperties parseJson(ResourceLocation location, JsonObject obj) throws JsonParseException {
		String id = location.toString();
		BallisticPropertiesComponent ballistics = BallisticPropertiesComponent.fromJson(id, obj);
		EntityDamagePropertiesComponent damage = EntityDamagePropertiesComponent.fromJson(id, obj);
		RackedProjectilePropertiesComponent rackedProjectileProperties = RackedProjectilePropertiesComponent.fromJson(id, obj);
		BigCannonFuzePropertiesComponent fuze = BigCannonFuzePropertiesComponent.fromJson(id, obj);
		ExplosionPropertiesComponent explosion = ExplosionPropertiesComponent.fromJson(id, obj);
		int explosionCooldown = max(-1, getOrWarn(obj, "explosion_cooldown", id, 15, JsonElement::getAsInt));
		int lifetime = max(0, getOrWarn(obj, "lifetime", id, 80, JsonElement::getAsInt));
		float buoyancyFactor = Math.max(0, GsonHelper.getAsFloat(obj, "buoyancy_factor", 0.1f));
		float steadyStateVel = Math.max(0, GsonHelper.getAsFloat(obj, "steady_state_vel", 0.5f));
		float waterDamp = Math.max(0, GsonHelper.getAsFloat(obj, "water_damp", 0.1f));
		return new RackedTorpedoProjectileProperties(ballistics, damage, rackedProjectileProperties, fuze, explosion, explosionCooldown, lifetime, buoyancyFactor, steadyStateVel, waterDamp);
	}

	@Override
	protected RackedTorpedoProjectileProperties readPropertiesFromNetwork(EntityType<?> entityType, FriendlyByteBuf buf) {
		BallisticPropertiesComponent ballistics = BallisticPropertiesComponent.fromNetwork(buf);
		EntityDamagePropertiesComponent damage = EntityDamagePropertiesComponent.fromNetwork(buf);
		RackedProjectilePropertiesComponent rackedProjectileProperties = RackedProjectilePropertiesComponent.fromNetwork(buf);
		BigCannonFuzePropertiesComponent fuze = BigCannonFuzePropertiesComponent.fromNetwork(buf);
		ExplosionPropertiesComponent explosion = ExplosionPropertiesComponent.fromNetwork(buf);
		int explosionCooldown = buf.readInt();
		int lifetime = buf.readInt();
		float buoyancyFactor = buf.readFloat();
		float steadyStateVel = buf.readFloat();
		float waterDamp = buf.readFloat();
		return new RackedTorpedoProjectileProperties(ballistics, damage, rackedProjectileProperties, fuze, explosion, explosionCooldown, lifetime, buoyancyFactor, steadyStateVel,waterDamp);
	}

	@Override
	protected void writePropertiesToNetwork(RackedTorpedoProjectileProperties properties, FriendlyByteBuf buf) {
		properties.ballistics().toNetwork(buf);
		properties.damage().toNetwork(buf);
		properties.rackedProjectileProperties().toNetwork(buf);
		properties.fuze().toNetwork(buf);
		properties.explosion().toNetwork(buf);
		buf.writeInt(properties.explosionCooldown());
		buf.writeInt(properties.lifetime());
		buf.writeFloat(properties.buoyancyFactor());
		buf.writeFloat(properties.steadyStateVel());
		buf.writeFloat(properties.waterDamp());
	}

	@Override protected RackedTorpedoProjectileProperties getNoPropertiesValue() { return DEFAULT; }

}
