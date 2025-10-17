package com.cainiao1053.cbcmoreshells;

import static com.cainiao1053.cbcmoreshells.Cbcmoreshells.REGISTRATE;
import com.cainiao1053.cbcmoreshells.datagen.assets.CBCMSBuilderTransformers;

import com.cainiao1053.cbcmoreshells.munitions.autocannon.bullet.AntiairMachineGunRoundItem;
import com.cainiao1053.cbcmoreshells.munitions.dual_cannon.combat_command.*;
import com.cainiao1053.cbcmoreshells.munitions.fuzes.ShipProximityFuzeItem;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import rbasamoyai.createbigcannons.CBCTags;
import rbasamoyai.createbigcannons.datagen.assets.CBCBuilderTransformers;
import rbasamoyai.createbigcannons.utils.CBCRegistryUtils;
import rbasamoyai.createbigcannons.utils.CBCUtils;

public class CBCMSItems {

	static { ModGroup.useModTab(ModGroup.MAIN_TAB_KEY); }


	public static final ItemEntry<Item>


	STEEL_TORPEDO_SLIDING_BREECHBLOCK = REGISTRATE.item("steel_torpedo_sliding_breechblock", Item::new)
			.transform(CBCMSBuilderTransformers.torpedoSlidingBreechblock("torpedo_sliding_breech/steel"))
			.register(),

	ROCKET_BRACKET = REGISTRATE.item("rocket_bracket", Item::new)
			.register();

	public static final ItemEntry<AntiairMachineGunRoundItem> ANTIAIR_MACHINE_GUN_ROUND = REGISTRATE
			.item("antiair_machine_gun_round", AntiairMachineGunRoundItem::new)
			.tag(CBCTags.CBCItemTags.AUTOCANNON_CARTRIDGES)
			.register();

	public static final ItemEntry<ShipProximityFuzeItem> SHIP_PROXIMITY_FUZE = REGISTRATE.item("ship_proximity_fuze", ShipProximityFuzeItem::new)
			.tag(CBCTags.CBCItemTags.FUZES)
			.register();

	public static final ItemEntry<CombatCommandBaseItem> BASE_COMBAT_COMMAND = REGISTRATE.item("base_combat_command", CombatCommandBaseItem::new)
			.properties(properties -> properties.stacksTo(1)
					.durability(8))
			.register();

	public static final ItemEntry<ReloadCombatCommandItem> RELOAD_COMBAT_COMMAND = REGISTRATE.item("reload_combat_command", ReloadCombatCommandItem::new)
			.properties(properties -> properties.stacksTo(1)
					.durability(40)
					.rarity(Rarity.RARE))
			.register();

	public static final ItemEntry<RangeCombatCommandItem> RANGE_COMBAT_COMMAND = REGISTRATE.item("range_combat_command", RangeCombatCommandItem::new)
			.properties(properties -> properties.stacksTo(1)
					.durability(40)
					.rarity(Rarity.RARE))
			.register();

	public static final ItemEntry<SpreadCombatCommandItem> SPREAD_COMBAT_COMMAND = REGISTRATE.item("spread_combat_command", SpreadCombatCommandItem::new)
			.properties(properties -> properties.stacksTo(1)
					.durability(40)
					.rarity(Rarity.RARE))
			.register();

	public static final ItemEntry<DamageCombatCommandItem> DAMAGE_COMBAT_COMMAND = REGISTRATE.item("damage_combat_command", DamageCombatCommandItem::new)
			.properties(properties -> properties.stacksTo(1)
					.durability(40)
					.rarity(Rarity.RARE))
			.register();

	public static final ItemEntry<GamblerCombatCommandItem> GAMBLER_COMBAT_COMMAND = REGISTRATE.item("gambler_combat_command", GamblerCombatCommandItem::new)
			.properties(properties -> properties.stacksTo(1)
					.durability(24)
					.rarity(Rarity.EPIC))
			.register();

	public static final ItemEntry<BerserkerCombatCommandItem> BERSERKER_COMBAT_COMMAND = REGISTRATE.item("berserker_combat_command", BerserkerCombatCommandItem::new)
			.properties(properties -> properties.stacksTo(1)
					.durability(32)
					.rarity(Rarity.EPIC))
			.register();

	public static final ItemEntry<MyopiaCombatCommandItem> MYOPIA_COMBAT_COMMAND = REGISTRATE.item("myopia_combat_command", MyopiaCombatCommandItem::new)
			.properties(properties -> properties.stacksTo(1)
					.durability(32)
					.rarity(Rarity.EPIC))
			.register();

	public static final ItemEntry<SniperCombatCommandItem> SNIPER_COMBAT_COMMAND = REGISTRATE.item("sniper_combat_command", SniperCombatCommandItem::new)
			.properties(properties -> properties.stacksTo(1)
					.durability(32)
					.rarity(Rarity.EPIC))
			.register();

	public static final ItemEntry<AssasinCombatCommandItem> ASSASSIN_COMBAT_COMMAND = REGISTRATE.item("assassin_combat_command", AssasinCombatCommandItem::new)
			.properties(properties -> properties.stacksTo(1)
					.durability(24)
					.rarity(Rarity.EPIC))
			.register();

	public static final ItemEntry<CombatCommandInfoItem> COMBAT_COMMAND_INFO = REGISTRATE.item("combat_command_info", CombatCommandInfoItem::new)
			.properties(properties -> properties.stacksTo(1))
			.register();



	//public static final ItemEntry<SequencedAssemblyItem>


	public static void register() {
	}

	public static TagKey<Item> tag(ResourceLocation loc) { return CBCRegistryUtils.createItemTag(loc); }
	private static TagKey<Item> forgeTag(String loc) { return tag(CBCUtils.location("forge", loc)); }
	private static TagKey<Item> fabricTag(String loc) { return tag(CBCUtils.location("c", loc)); }

}
