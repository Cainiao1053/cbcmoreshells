package com.cainiao1053.cbcmoreshells;

import static com.cainiao1053.cbcmoreshells.Cbcmoreshells.REGISTRATE;
import com.cainiao1053.cbcmoreshells.datagen.assets.CBCMSBuilderTransformers;

import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import rbasamoyai.createbigcannons.datagen.assets.CBCBuilderTransformers;
import rbasamoyai.createbigcannons.utils.CBCRegistryUtils;
import rbasamoyai.createbigcannons.utils.CBCUtils;

public class CBCMSItems {

	static { ModGroup.useModTab(ModGroup.MAIN_TAB_KEY); }


	public static final ItemEntry<Item>


	STEEL_TORPEDO_SLIDING_BREECHBLOCK = REGISTRATE.item("steel_torpedo_sliding_breechblock", Item::new)
			.transform(CBCMSBuilderTransformers.torpedoSlidingBreechblock("torpedo_sliding_breech/steel"))
			.register();


	//public static final ItemEntry<SequencedAssemblyItem>


	public static void register() {
	}

	public static TagKey<Item> tag(ResourceLocation loc) { return CBCRegistryUtils.createItemTag(loc); }
	private static TagKey<Item> forgeTag(String loc) { return tag(CBCUtils.location("forge", loc)); }
	private static TagKey<Item> fabricTag(String loc) { return tag(CBCUtils.location("c", loc)); }

}
