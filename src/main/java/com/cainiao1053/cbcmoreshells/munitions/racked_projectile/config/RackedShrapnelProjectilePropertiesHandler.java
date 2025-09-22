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
import rbasamoyai.createbigcannons.munitions.fragment_burst.ProjectileBurstParentPropertiesComponent;

public class RackedShrapnelProjectilePropertiesHandler extends EntityPropertiesTypeHandler<RackedShrapnelProjectileProperties> {

	private static final RackedShrapnelProjectileProperties DEFAULT = new RackedShrapnelProjectileProperties(BallisticPropertiesComponent.DEFAULT,
		EntityDamagePropertiesComponent.DEFAULT, RackedProjectilePropertiesComponent.DEFAULT, BigCannonFuzePropertiesComponent.DEFAULT,
		ExplosionPropertiesComponent.DEFAULT, 4, 30, ProjectileBurstParentPropertiesComponent.DEFAULT, 4);

	@Override
	protected RackedShrapnelProjectileProperties parseJson(ResourceLocation location, JsonObject obj) throws JsonParseException {
		String id = location.toString();
		BallisticPropertiesComponent ballistics = BallisticPropertiesComponent.fromJson(id, obj);
		EntityDamagePropertiesComponent damage = EntityDamagePropertiesComponent.fromJson(id, obj);
		RackedProjectilePropertiesComponent rackedProjectileProperties = RackedProjectilePropertiesComponent.fromJson(id, obj);
		BigCannonFuzePropertiesComponent fuze = BigCannonFuzePropertiesComponent.fromJson(id, obj);
		ExplosionPropertiesComponent explosion = ExplosionPropertiesComponent.fromJson(id, obj);
		int explosionCooldown = Math.max(-1, getOrWarn(obj, "explosion_cooldown", id, 15, JsonElement::getAsInt));
		int lifetime = Math.max(0, getOrWarn(obj, "lifetime", id, 80, JsonElement::getAsInt));
		ProjectileBurstParentPropertiesComponent burst = ProjectileBurstParentPropertiesComponent.fromJson(id, "shrapnel_", obj);
		int destroyRange = Math.max(0, getOrWarn(obj, "destroy_range", id, 4, JsonElement::getAsInt));
		return new RackedShrapnelProjectileProperties(ballistics, damage, rackedProjectileProperties, fuze, explosion, explosionCooldown, lifetime, burst, destroyRange);
	}

	@Override
	protected RackedShrapnelProjectileProperties readPropertiesFromNetwork(EntityType<?> entityType, FriendlyByteBuf buf) {
		BallisticPropertiesComponent ballistics = BallisticPropertiesComponent.fromNetwork(buf);
		EntityDamagePropertiesComponent damage = EntityDamagePropertiesComponent.fromNetwork(buf);
		RackedProjectilePropertiesComponent rackedProjectileProperties = RackedProjectilePropertiesComponent.fromNetwork(buf);
		BigCannonFuzePropertiesComponent fuze = BigCannonFuzePropertiesComponent.fromNetwork(buf);
		ExplosionPropertiesComponent explosion = ExplosionPropertiesComponent.fromNetwork(buf);
		int explosionCooldown = buf.readInt();
		int lifetime = buf.readInt();
		ProjectileBurstParentPropertiesComponent burst = ProjectileBurstParentPropertiesComponent.fromNetwork(buf);
		int destroyRange = buf.readInt();
		return new RackedShrapnelProjectileProperties(ballistics, damage, rackedProjectileProperties, fuze, explosion, explosionCooldown, lifetime, burst, destroyRange);
	}

	@Override
	protected void writePropertiesToNetwork(RackedShrapnelProjectileProperties properties, FriendlyByteBuf buf) {
		properties.ballistics().toNetwork(buf);
		properties.damage().toNetwork(buf);
		properties.rackedProjectileProperties().toNetwork(buf);
		properties.fuze().toNetwork(buf);
		properties.explosion().toNetwork(buf);
		buf.writeInt(properties.explosionCooldown());
		buf.writeInt(properties.lifetime());
		properties.burst().toNetwork(buf);
		buf.writeInt(properties.destroyRange());
	}

	@Override protected RackedShrapnelProjectileProperties getNoPropertiesValue() { return DEFAULT; }

}
