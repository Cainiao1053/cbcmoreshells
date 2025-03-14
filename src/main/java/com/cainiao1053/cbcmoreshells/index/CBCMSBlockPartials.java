package com.cainiao1053.cbcmoreshells.index;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.cainiao1053.cbcmoreshells.Cbcmoreshells;
import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.material.TorpedoTubeMaterial;
import com.jozufozu.flywheel.core.PartialModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
//import rbasamoyai.createbigcannons.cannons.autocannon.material.AutocannonMaterial;
import rbasamoyai.createbigcannons.crafting.casting.CannonCastShape;

public class CBCMSBlockPartials {

	private static final Map<TorpedoTubeMaterial, PartialModel> BREECHBLOCK_BY_MATERIAL = new HashMap<>();
	private static final Map<TorpedoTubeMaterial, PartialModel> SCREW_LOCK_BY_MATERIAL = new HashMap<>();



	private static final Collection<Runnable> DEFERRED_MODEL_CALLBACKS = new ArrayList<>();

	public static final PartialModel
		CAST_IRON_SLIDING_BREECHBLOCK = breechblockPartial(CBCMSTorpedoTubeMaterials.CAST_IRON, "cast_iron_sliding_breechblock"),
		BRONZE_SLIDING_BREECHBLOCK = breechblockPartial(CBCMSTorpedoTubeMaterials.BRONZE, "bronze_sliding_breechblock"),
		STEEL_TORPEDO_SLIDING_BREECHBLOCK = breechblockPartial(CBCMSTorpedoTubeMaterials.STEEL, "steel_torpedo_sliding_breechblock"),

		QUICKFIRING_BREECH_LEVER = block("quickfiring_breech_lever"),




		GAS_MASK = block("gas_mask");


	private static PartialModel block(String path) {
		return new PartialModel(Cbcmoreshells.resource("block/" + path));
	}
	private static PartialModel entity(String path) { return new PartialModel(Cbcmoreshells.resource("entity/" + path)); }

	private static PartialModel breechblockPartial(TorpedoTubeMaterial material, String path) {
		return breechblockPartial(material, Cbcmoreshells.resource("item/" + path));
	}

	public static PartialModel breechblockPartial(TorpedoTubeMaterial material, ResourceLocation loc) {
		PartialModel model = new PartialModel(loc);
		BREECHBLOCK_BY_MATERIAL.put(material, model);
		return model;
	}

	public static PartialModel breechblockFor(TorpedoTubeMaterial material) {
		return BREECHBLOCK_BY_MATERIAL.getOrDefault(material, STEEL_TORPEDO_SLIDING_BREECHBLOCK);
	}












	public static void init() {}

	public static void resolveDeferredModels() {
		for (Runnable run : DEFERRED_MODEL_CALLBACKS) run.run();
		DEFERRED_MODEL_CALLBACKS.clear();
	}

}
