package com.cainiao1053.cbcmoreshells.cannons.dual_cannon.material;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public record DualCannonMaterial(ResourceLocation name, DualCannonMaterialProperties defaultProperties) {

	public DualCannonMaterialProperties properties() {
		DualCannonMaterialProperties custom = DualCannonMaterialPropertiesHandler.getMaterial(this);
		return custom == null ? this.defaultProperties : custom;
	}

	private static final Map<ResourceLocation, DualCannonMaterial> CANNON_MATERIALS = new HashMap<>();

	public static DualCannonMaterial register(ResourceLocation loc, DualCannonMaterialProperties defaultProperties) {
		DualCannonMaterial material = new DualCannonMaterial(loc, defaultProperties);
		CANNON_MATERIALS.put(material.name(), material);
		return material;
	}

	public static DualCannonMaterial fromName(ResourceLocation loc) {
		if (!CANNON_MATERIALS.containsKey(loc)) throw new IllegalArgumentException("No dual cannon material '" + loc + "' registered");
		return CANNON_MATERIALS.get(loc);
	}

	public static DualCannonMaterial fromNameOrNull(ResourceLocation loc) { return CANNON_MATERIALS.get(loc); }

}
