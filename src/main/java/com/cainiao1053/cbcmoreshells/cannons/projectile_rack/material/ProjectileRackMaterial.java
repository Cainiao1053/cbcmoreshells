package com.cainiao1053.cbcmoreshells.cannons.projectile_rack.material;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public record ProjectileRackMaterial(ResourceLocation name, ProjectileRackMaterialProperties defaultProperties) {

	public ProjectileRackMaterialProperties properties() {
		ProjectileRackMaterialProperties custom = ProjectileRackMaterialPropertiesHandler.getMaterial(this);
		return custom == null ? this.defaultProperties : custom;
	}

	private static final Map<ResourceLocation, ProjectileRackMaterial> CANNON_MATERIALS = new HashMap<>();

	public static ProjectileRackMaterial register(ResourceLocation loc, ProjectileRackMaterialProperties defaultProperties) {
		ProjectileRackMaterial material = new ProjectileRackMaterial(loc, defaultProperties);
		CANNON_MATERIALS.put(material.name(), material);
		return material;
	}

	public static ProjectileRackMaterial fromName(ResourceLocation loc) {
		if (!CANNON_MATERIALS.containsKey(loc)) throw new IllegalArgumentException("No material '" + loc + "' registered");
		return CANNON_MATERIALS.get(loc);
	}

	public static ProjectileRackMaterial fromNameOrNull(ResourceLocation loc) { return CANNON_MATERIALS.get(loc); }

}
