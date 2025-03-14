package com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.material;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public record TorpedoTubeMaterial(ResourceLocation name, TorpedoTubeMaterialProperties defaultProperties) {

	public TorpedoTubeMaterialProperties properties() {
		TorpedoTubeMaterialProperties custom = TorpedoTubeMaterialPropertiesHandler.getMaterial(this);
		return custom == null ? this.defaultProperties : custom;
	}

	private static final Map<ResourceLocation, TorpedoTubeMaterial> CANNON_MATERIALS = new HashMap<>();

	public static TorpedoTubeMaterial register(ResourceLocation loc, TorpedoTubeMaterialProperties defaultProperties) {
		TorpedoTubeMaterial material = new TorpedoTubeMaterial(loc, defaultProperties);
		CANNON_MATERIALS.put(material.name(), material);
		return material;
	}

	public static TorpedoTubeMaterial fromName(ResourceLocation loc) {
		if (!CANNON_MATERIALS.containsKey(loc)) throw new IllegalArgumentException("No big cannon material '" + loc + "' registered");
		return CANNON_MATERIALS.get(loc);
	}

	public static TorpedoTubeMaterial fromNameOrNull(ResourceLocation loc) { return CANNON_MATERIALS.get(loc); }

}
