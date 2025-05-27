package com.cainiao1053.cbcmoreshells.munitions.big_cannon.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

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

public class SharpnelTorpedoPropertiesHandler extends EntityPropertiesTypeHandler<SharpnelTorpedoProperties> {

	private static final SharpnelTorpedoProperties DEFAULT = new SharpnelTorpedoProperties(BallisticPropertiesComponent.DEFAULT,
		EntityDamagePropertiesComponent.DEFAULT, TorpedoProjectilePropertiesComponent.DEFAULT, BigCannonFuzePropertiesComponent.DEFAULT,
		ExplosionPropertiesComponent.DEFAULT, 4, 30, ProjectileBurstParentPropertiesComponent.DEFAULT);

	@Override
	protected SharpnelTorpedoProperties parseJson(ResourceLocation location, JsonObject obj) throws JsonParseException {
		String id = location.toString();
		BallisticPropertiesComponent ballistics = BallisticPropertiesComponent.fromJson(id, obj);
		EntityDamagePropertiesComponent damage = EntityDamagePropertiesComponent.fromJson(id, obj);
		TorpedoProjectilePropertiesComponent torpedoProperties = TorpedoProjectilePropertiesComponent.fromJson(id, obj);
		BigCannonFuzePropertiesComponent fuze = BigCannonFuzePropertiesComponent.fromJson(id, obj);
		ExplosionPropertiesComponent explosion = ExplosionPropertiesComponent.fromJson(id, obj);
		float maxCharges = Math.max(-1, getOrWarn(obj, "max_charges", id, 4f, JsonElement::getAsFloat));
		int lifetime = Math.max(0, getOrWarn(obj, "lifetime", id, 30, JsonElement::getAsInt));
		ProjectileBurstParentPropertiesComponent torpedoBurst = ProjectileBurstParentPropertiesComponent.fromJson(id, "shrapnel_", obj);
		return new SharpnelTorpedoProperties(ballistics, damage, torpedoProperties, fuze, explosion, maxCharges, lifetime, torpedoBurst);
	}

	@Override
	protected SharpnelTorpedoProperties readPropertiesFromNetwork(EntityType<?> entityType, FriendlyByteBuf buf) {
		BallisticPropertiesComponent ballistics = BallisticPropertiesComponent.fromNetwork(buf);
		EntityDamagePropertiesComponent damage = EntityDamagePropertiesComponent.fromNetwork(buf);
		TorpedoProjectilePropertiesComponent torpedoProperties = TorpedoProjectilePropertiesComponent.fromNetwork(buf);
		BigCannonFuzePropertiesComponent fuze = BigCannonFuzePropertiesComponent.fromNetwork(buf);
		ExplosionPropertiesComponent explosion = ExplosionPropertiesComponent.fromNetwork(buf);
		float maxCharges = buf.readFloat();
		int lifetime = buf.readInt();
		ProjectileBurstParentPropertiesComponent torpedoBurst = ProjectileBurstParentPropertiesComponent.fromNetwork(buf);
		return new SharpnelTorpedoProperties(ballistics, damage, torpedoProperties, fuze, explosion, maxCharges, lifetime, torpedoBurst);
	}

	@Override
	protected void writePropertiesToNetwork(SharpnelTorpedoProperties properties, FriendlyByteBuf buf) {
		properties.ballistics().toNetwork(buf);
		properties.damage().toNetwork(buf);
		properties.torpedoProperties().toNetwork(buf);
		properties.fuze().toNetwork(buf);
		properties.explosion().toNetwork(buf);
		buf.writeFloat(properties.maxCharges());
		buf.writeInt(properties.lifetime());
		properties.torpedoBurst().toNetwork(buf);
	}

	@Override protected SharpnelTorpedoProperties getNoPropertiesValue() { return DEFAULT; }

}
