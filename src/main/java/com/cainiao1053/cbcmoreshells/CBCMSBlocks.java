package com.cainiao1053.cbcmoreshells;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;
import static com.cainiao1053.cbcmoreshells.Cbcmoreshells.REGISTRATE;

import java.util.function.Supplier;

import com.cainiao1053.cbcmoreshells.blocks.LootBarrelBlock;
import com.cainiao1053.cbcmoreshells.cannons.big_cannon.NethersteelQuickfiringBreechBlock;
import com.cainiao1053.cbcmoreshells.cannons.big_cannon.NethersteelSlidingBreechBlock;
import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.TorpedoTubeBodyBlock;
import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.breeches.quick_firing_breech.TorpQuickfiringBreechBlock;
import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.breeches.sliding_breech.TorpedoSlidingBreechBlock;
import com.cainiao1053.cbcmoreshells.datagen.assets.CBCMSBuilderTransformers;
import com.cainiao1053.cbcmoreshells.index.CBCMSTorpedoTubeMaterials;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.airdropped_shrapnel_torpedo.AirdroppedShrapnelTorpedoBlock;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.airdropped_shrapnel_torpedo.AirdroppedShrapnelTorpedoBlockItem;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.airdropped_torpedo.AirdroppedTorpedoBlock;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.airdropped_torpedo.AirdroppedTorpedoBlockItem;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.antiair_he_shell.AntiairHEShellBlock;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.antiair_he_shell.AntiairHEShellBlockItem;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.ap_super_heavy.APSuperHeavyBlockItem;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.ap_super_heavy.APSuperHeavyShotBlock;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.apbc_shot.APBCBlockItem;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.apbc_shot.APBCShotBlock;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.apfsds_shot.APFSDSProjectileBlockItem;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.apfsds_shot.APFSDSShotBlock;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.aphe_cannon_rocket.APHECannonRocketBlock;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.baguette_shot.BaguetteProjectileBlockItem;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.baguette_shot.BaguetteShotBlock;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.baked_apfsds_shot.BakedAPFSDSProjectileBlockItem;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.baked_apfsds_shot.BakedAPFSDSShotBlock;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.cannon_torpedo.CannonTorpedoBlock;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.cannon_torpedo.CannonTorpedoBlockItem;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.deepwater_shrapnel_torpedo.DeepwaterShrapnelTorpedoBlock;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.deepwater_shrapnel_torpedo.DeepwaterShrapnelTorpedoBlockItem;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.he_cannon_rocket.HECannonRocketBlock;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.hesh_shell.HESHShellBlock;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.highspeed_torpedo.HighspeedTorpedoBlock;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.highspeed_torpedo.HighspeedTorpedoBlockItem;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.incendiary_he_shell.IncendiaryHEProjectileBlockItem;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.incendiary_he_shell.IncendiaryHEShellBlock;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.inferior_he_shell.InferiorHEShellBlock;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.long_range_torpedo.LongRangeTorpedoBlock;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.long_range_torpedo.LongRangeTorpedoBlockItem;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.medium_range_deepwater_torpedo.MediumRangeDeepwaterTorpedoBlock;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.medium_range_deepwater_torpedo.MediumRangeDeepwaterTorpedoBlockItem;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.medium_range_deepwater_torpedo_typeb.MediumRangeDeepwaterTorpedoTypeBBlock;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.medium_range_deepwater_torpedo_typeb.MediumRangeDeepwaterTorpedoTypeBBlockItem;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.medium_range_torpedo_typeb.MediumRangeTorpedoTypeBBlock;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.medium_range_torpedo_typeb.MediumRangeTorpedoTypeBBlockItem;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.sap_shell.SAPShellBlock;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.sharpnel_torpedo.SharpnelTorpedoBlock;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.sharpnel_torpedo.SharpnelTorpedoBlockItem;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.shelless_he_shell.ShellessHEProjectileBlockItem;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.shelless_he_shell.ShellessHEShellBlock;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.shelless_incendiary_he_shell.ShellessIncendiaryHEProjectileBlockItem;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.shelless_incendiary_he_shell.ShellessIncendiaryHEShellBlock;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.shelless_sap_shell.ShellessSAPProjectileBlockItem;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.shelless_sap_shell.ShellessSAPShellBlock;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.shellless_ap_shot.ShellessAPProjectileBlockItem;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.shellless_ap_shot.ShellessAPShotBlock;
import com.cainiao1053.cbcmoreshells.munitions.torpedo_tube.medium_range_torpedo.MediumRangeTorpedoBlock;
import com.cainiao1053.cbcmoreshells.munitions.torpedo_tube.medium_range_torpedo.MediumRangeTorpedoBlockItem;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.decoration.copycat.CopycatPanelBlock;
import com.simibubi.create.content.redstone.displayLink.AllDisplayBehaviours;
import com.simibubi.create.foundation.data.BuilderTransformers;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.content.decoration.copycat.CopycatPanelModel;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.ShellessFuzedProjectileBlockItem;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.inferior_he_shell.InferiorHEShellBlockItem;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.sap_shell.SAPShellBlockItem;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.hesh_shell.HESHShellBlockItem;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.ShellessInertProjectileBlockItem;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.CannonRocketProjectileBlockItem;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.FuzedTorpedoProjectileBlockItem;
import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.TorpedoTubeBlockItem;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import rbasamoyai.createbigcannons.CBCTags;
import rbasamoyai.createbigcannons.base.CBCDefaultStress;
import rbasamoyai.createbigcannons.cannons.big_cannons.breeches.quickfiring_breech.QuickfiringBreechBlock;
import rbasamoyai.createbigcannons.cannons.big_cannons.breeches.sliding_breech.SlidingBreechBlock;
import rbasamoyai.createbigcannons.cannons.big_cannons.breeches.sliding_breech.SlidingBreechCTBehavior;
import rbasamoyai.createbigcannons.datagen.assets.CBCBuilderTransformers;
import rbasamoyai.createbigcannons.index.CBCBigCannonMaterials;
import rbasamoyai.createbigcannons.index.CBCSpriteShifts;
import rbasamoyai.createbigcannons.munitions.FuzedProjectileBlockItem;
import rbasamoyai.createbigcannons.munitions.big_cannon.ProjectileBlockItem;
import rbasamoyai.createbigcannons.munitions.big_cannon.ap_shot.APShotBlock;
import rbasamoyai.createbigcannons.munitions.config.MunitionPropertiesHandler;

public class CBCMSBlocks {

	static { ModGroup.useModTab(ModGroup.MAIN_TAB_KEY); }

	//////// Log cannon blocks ////////
	public static final BlockEntry<InferiorHEShellBlock> Inferior_HE_SHELL = REGISTRATE
			.block("inferior_he_shell", InferiorHEShellBlock::new)
			.transform(shell(MapColor.COLOR_RED))
			.transform(axeOrPickaxe())
			.transform(CBCBuilderTransformers.projectile("projectile/inferior_he_shell"))
			.transform(CBCBuilderTransformers.safeNbt())
			.loot(CBCBuilderTransformers.shellLoot())
			.lang("Inferior High Explosive (HE) Shell")
			.item(InferiorHEShellBlockItem::new)
			.tag(CBCTags.CBCItemTags.BIG_CANNON_PROJECTILES)
			.build()
			.register();

	public static final BlockEntry<SAPShellBlock> SAP_SHELL = REGISTRATE
			.block("sap_shell", SAPShellBlock::new)
			.transform(shell(MapColor.COLOR_RED))
			.transform(axeOrPickaxe())
			.transform(CBCBuilderTransformers.projectile("projectile/sap_shell"))
			.transform(CBCBuilderTransformers.safeNbt())
			.loot(CBCBuilderTransformers.shellLoot())
			.lang("SAP Shell")
			.item(SAPShellBlockItem::new)
			.tag(CBCTags.CBCItemTags.BIG_CANNON_PROJECTILES)
			.build()
			.register();

	public static final BlockEntry<HESHShellBlock> HESH_SHELL = REGISTRATE
			.block("hesh_shell", HESHShellBlock::new)
			.transform(shell(MapColor.COLOR_YELLOW))
			.transform(axeOrPickaxe())
			.transform(CBCBuilderTransformers.projectile("projectile/hesh_shell"))
			.transform(CBCBuilderTransformers.safeNbt())
			.loot(CBCBuilderTransformers.shellLoot())
			.lang("HESH Shell")
			.item(HESHShellBlockItem::new)
			.tag(CBCTags.CBCItemTags.BIG_CANNON_PROJECTILES)
			.build()
			.register();

	public static final BlockEntry<ShellessHEShellBlock> SHELLESS_HE_SHELL = REGISTRATE
			.block("shelless_he_shell", ShellessHEShellBlock::new)
			.transform(shell(MapColor.COLOR_YELLOW))
			.transform(axeOrPickaxe())
			.transform(CBCBuilderTransformers.projectile("projectile/shelless_he_shell"))
			.transform(CBCBuilderTransformers.safeNbt())
			.loot(CBCBuilderTransformers.shellLoot())
			.lang("Shelless HE Shell")
			.item(ShellessHEProjectileBlockItem::new)
			.tag(CBCTags.CBCItemTags.BIG_CANNON_PROJECTILES)
			.build()
			.register();

	public static final BlockEntry<APHECannonRocketBlock> APHE_CANNON_ROCKET = REGISTRATE
			.block("aphe_cannon_rocket", APHECannonRocketBlock::new)
			.transform(shell(MapColor.COLOR_YELLOW))
			.transform(axeOrPickaxe())
			.transform(CBCBuilderTransformers.projectile("projectile/aphe_cannon_rocket"))
			.transform(CBCBuilderTransformers.safeNbt())
			.loot(CBCBuilderTransformers.shellLoot())
			.lang("APHE Cannon Rocket")
			.item(CannonRocketProjectileBlockItem::new)
			.tag(CBCTags.CBCItemTags.BIG_CANNON_PROJECTILES)
			.build()
			.register();

	public static final BlockEntry<ShellessAPShotBlock> SHELLESS_AP_SHOT = REGISTRATE
			.block("shelless_ap_shot", ShellessAPShotBlock::new)
			.transform(shell(MapColor.COLOR_YELLOW))
			.transform(axeOrPickaxe())
			.transform(CBCBuilderTransformers.projectile("projectile/shelless_ap_shot"))
			.transform(CBCBuilderTransformers.safeNbt())
			.loot(CBCBuilderTransformers.shellLoot())
			.lang("Shelless AP Shot")
			.item(ShellessAPProjectileBlockItem::new)
			.tag(CBCTags.CBCItemTags.BIG_CANNON_PROJECTILES)
			.build()
			.register();

	public static final BlockEntry<APFSDSShotBlock> APFSDS_SHOT = REGISTRATE
			.block("apfsds_shot", APFSDSShotBlock::new)
			.transform(shell(MapColor.COLOR_YELLOW))
			.transform(axeOrPickaxe())
			.transform(CBCBuilderTransformers.projectile("projectile/apfsds_shot"))
			.transform(CBCBuilderTransformers.safeNbt())
			.loot(CBCBuilderTransformers.shellLoot())
			.lang("APFSDS Shot")
			.item(APFSDSProjectileBlockItem::new)
			.tag(CBCTags.CBCItemTags.BIG_CANNON_PROJECTILES)
			.build()
			.register();

	public static final BlockEntry<BakedAPFSDSShotBlock> BAKED_APFSDS_SHOT = REGISTRATE
			.block("baked_apfsds_shot", BakedAPFSDSShotBlock::new)
			.transform(shell(MapColor.COLOR_YELLOW))
			.transform(axeOrPickaxe())
			.transform(CBCBuilderTransformers.projectile("projectile/baked_apfsds_shot"))
			.transform(CBCBuilderTransformers.safeNbt())
			.loot(CBCBuilderTransformers.shellLoot())
			.lang("Baked APFSDS Shot")
			.item(BakedAPFSDSProjectileBlockItem::new)
			.tag(CBCTags.CBCItemTags.BIG_CANNON_PROJECTILES)
			.build()
			.register();

	public static final BlockEntry<BaguetteShotBlock> BAGUETTE_SHOT = REGISTRATE
			.block("baguette_shot", BaguetteShotBlock::new)
			.transform(shell(MapColor.COLOR_YELLOW))
			.transform(axeOrPickaxe())
			.transform(CBCBuilderTransformers.projectile("projectile/baguette_shot"))
			.transform(CBCBuilderTransformers.safeNbt())
			.loot(CBCBuilderTransformers.shellLoot())
			.lang("Baguette Shot")
			.item(BaguetteProjectileBlockItem::new)
			.tag(CBCTags.CBCItemTags.BIG_CANNON_PROJECTILES)
			.build()
			.register();

	public static final BlockEntry<Block> APFSDS_SHOT_PROJECTILE = REGISTRATE
			.block("apfsds_shot_projectile", Block::new)
			.initialProperties(SharedProperties::stone)
			.properties(p -> p.noOcclusion())
			.blockstate(CBCBuilderTransformers.simpleBlock("block/apfsds_shot_projectile"))
			.simpleItem()
			.register();

	public static final BlockEntry<Block> BAKED_APFSDS_SHOT_PROJECTILE = REGISTRATE
			.block("baked_apfsds_shot_projectile", Block::new)
			.initialProperties(SharedProperties::stone)
			.properties(p -> p.noOcclusion())
			.blockstate(CBCBuilderTransformers.simpleBlock("block/baked_apfsds_shot_projectile"))
			.simpleItem()
			.register();

	public static final BlockEntry<Block> BAGUETTE_SHOT_PROJECTILE = REGISTRATE
			.block("baguette_shot_projectile", Block::new)
			.initialProperties(SharedProperties::stone)
			.properties(p -> p.noOcclusion())
			.blockstate(CBCBuilderTransformers.simpleBlock("block/baguette_shot_projectile"))
			.simpleItem()
			.register();

	public static final BlockEntry<ShellessSAPShellBlock> SHELLESS_SAP_SHELL = REGISTRATE
			.block("shelless_sap_shell", ShellessSAPShellBlock::new)
			.transform(shell(MapColor.COLOR_YELLOW))
			.transform(axeOrPickaxe())
			.transform(CBCBuilderTransformers.projectile("projectile/shelless_sap_shell"))
			.transform(CBCBuilderTransformers.safeNbt())
			.loot(CBCBuilderTransformers.shellLoot())
			.lang("Shelless SAP Shell")
			.item(ShellessSAPProjectileBlockItem::new)
			.tag(CBCTags.CBCItemTags.BIG_CANNON_PROJECTILES)
			.build()
			.register();

	public static final BlockEntry<HECannonRocketBlock> HE_CANNON_ROCKET = REGISTRATE
			.block("he_cannon_rocket", HECannonRocketBlock::new)
			.transform(shell(MapColor.COLOR_YELLOW))
			.transform(axeOrPickaxe())
			.transform(CBCBuilderTransformers.projectile("projectile/he_cannon_rocket"))
			.transform(CBCBuilderTransformers.safeNbt())
			.loot(CBCBuilderTransformers.shellLoot())
			.lang("HE Cannon Rocket")
			.item(CannonRocketProjectileBlockItem::new)
			.tag(CBCTags.CBCItemTags.BIG_CANNON_PROJECTILES)
			.build()
			.register();

	public static final BlockEntry<AntiairHEShellBlock> ANTIAIR_HE_SHELL = REGISTRATE
			.block("antiair_he_shell", AntiairHEShellBlock::new)
			.transform(shell(MapColor.COLOR_YELLOW))
			.transform(axeOrPickaxe())
			.transform(CBCBuilderTransformers.projectile("projectile/antiair_he_shell"))
			.transform(CBCBuilderTransformers.safeNbt())
			.loot(CBCBuilderTransformers.shellLoot())
			.lang("Antiair HE Shell")
			.item(AntiairHEShellBlockItem::new)
			.tag(CBCTags.CBCItemTags.BIG_CANNON_PROJECTILES)
			.build()
			.register();

	public static final BlockEntry<APBCShotBlock> APBC_SHOT = REGISTRATE
			.block("apbc_shot", APBCShotBlock::new)
			.transform(shell(MapColor.COLOR_YELLOW))
			.transform(axeOrPickaxe())
			.transform(CBCBuilderTransformers.projectile("projectile/apbc_shot"))
			.transform(CBCBuilderTransformers.safeNbt())
			.loot(CBCBuilderTransformers.shellLoot())
			.lang("APBC Shot")
			.item(APBCBlockItem::new)
			.tag(CBCTags.CBCItemTags.BIG_CANNON_PROJECTILES)
			.build()
			.register();

	public static final BlockEntry<APSuperHeavyShotBlock> AP_SUPER_HEAVY_SHOT = REGISTRATE
			.block("ap_super_heavy_shot", APSuperHeavyShotBlock::new)
			.transform(shell(MapColor.COLOR_YELLOW))
			.transform(axeOrPickaxe())
			.transform(CBCBuilderTransformers.projectile("projectile/ap_super_heavy_shot"))
			.transform(CBCBuilderTransformers.safeNbt())
			.loot(CBCBuilderTransformers.shellLoot())
			.lang("AP Super Heavy Shot")
			.item(APSuperHeavyBlockItem::new)
			.tag(CBCTags.CBCItemTags.BIG_CANNON_PROJECTILES)
			.build()
			.register();

	public static final BlockEntry<IncendiaryHEShellBlock> INCENDIARY_HE_SHELL = REGISTRATE
			.block("incendiary_he_shell", IncendiaryHEShellBlock::new)
			.transform(shell(MapColor.COLOR_YELLOW))
			.transform(axeOrPickaxe())
			.transform(CBCBuilderTransformers.projectile("projectile/incendiary_he_shell"))
			.transform(CBCBuilderTransformers.safeNbt())
			.loot(CBCBuilderTransformers.shellLoot())
			.lang("Incendiary HE Shell")
			.item(IncendiaryHEProjectileBlockItem::new)
			.tag(CBCTags.CBCItemTags.BIG_CANNON_PROJECTILES)
			.build()
			.register();

	public static final BlockEntry<ShellessIncendiaryHEShellBlock> SHELLESS_INCENDIARY_HE_SHELL = REGISTRATE
			.block("shelless_incendiary_he_shell", ShellessIncendiaryHEShellBlock::new)
			.transform(shell(MapColor.COLOR_YELLOW))
			.transform(axeOrPickaxe())
			.transform(CBCBuilderTransformers.projectile("projectile/shelless_incendiary_he_shell"))
			.transform(CBCBuilderTransformers.safeNbt())
			.loot(CBCBuilderTransformers.shellLoot())
			.lang("Shelless Incendiary HE Shell")
			.item(ShellessIncendiaryHEProjectileBlockItem::new)
			.tag(CBCTags.CBCItemTags.BIG_CANNON_PROJECTILES)
			.build()
			.register();

	public static final BlockEntry<CannonTorpedoBlock> CANNON_TORPEDO = REGISTRATE
			.block("cannon_torpedo", CannonTorpedoBlock::new)
			.transform(shell(MapColor.COLOR_YELLOW))
			.transform(axeOrPickaxe())
			.transform(CBCBuilderTransformers.projectile("projectile/cannon_torpedo"))
			.transform(CBCBuilderTransformers.safeNbt())
			.loot(CBCBuilderTransformers.shellLoot())
			.lang("Cannon Torpedo")
			.item(CannonTorpedoBlockItem::new)
			.tag(CBCTags.CBCItemTags.BIG_CANNON_PROJECTILES)
			.build()
			.register();

	public static final BlockEntry<MediumRangeTorpedoBlock> MEDIUM_RANGE_TORPEDO = REGISTRATE
			.block("medium_range_torpedo", MediumRangeTorpedoBlock::new)
			.transform(shell(MapColor.COLOR_YELLOW))
			.transform(axeOrPickaxe())
			.transform(CBCBuilderTransformers.projectile("projectile/medium_range_torpedo"))
			.transform(CBCBuilderTransformers.safeNbt())
			.loot(CBCBuilderTransformers.shellLoot())
			.lang("Medium Range Torpedo")
			.item(MediumRangeTorpedoBlockItem::new)
			.tag(CBCTags.CBCItemTags.BIG_CANNON_PROJECTILES)
			.build()
			.register();

	public static final BlockEntry<MediumRangeTorpedoTypeBBlock> MEDIUM_RANGE_TORPEDO_TYPEB = REGISTRATE
			.block("medium_range_torpedo_typeb", MediumRangeTorpedoTypeBBlock::new)
			.transform(shell(MapColor.COLOR_YELLOW))
			.transform(axeOrPickaxe())
			.transform(CBCBuilderTransformers.projectile("projectile/medium_range_torpedo_typeb"))
			.transform(CBCBuilderTransformers.safeNbt())
			.loot(CBCBuilderTransformers.shellLoot())
			.lang("Medium Range Torpedo Type B")
			.item(MediumRangeTorpedoTypeBBlockItem::new)
			.tag(CBCTags.CBCItemTags.BIG_CANNON_PROJECTILES)
			.build()
			.register();


	public static final BlockEntry<LongRangeTorpedoBlock> LONG_RANGE_TORPEDO = REGISTRATE
			.block("long_range_torpedo", LongRangeTorpedoBlock::new)
			.transform(shell(MapColor.COLOR_YELLOW))
			.transform(axeOrPickaxe())
			.transform(CBCBuilderTransformers.projectile("projectile/long_range_torpedo"))
			.transform(CBCBuilderTransformers.safeNbt())
			.loot(CBCBuilderTransformers.shellLoot())
			.lang("Long Range Torpedo")
			.item(LongRangeTorpedoBlockItem::new)
			.tag(CBCTags.CBCItemTags.BIG_CANNON_PROJECTILES)
			.build()
			.register();

	public static final BlockEntry<MediumRangeDeepwaterTorpedoBlock> MEDIUM_RANGE_DEEPWATER_TORPEDO = REGISTRATE
			.block("medium_range_deepwater_torpedo", MediumRangeDeepwaterTorpedoBlock::new)
			.transform(shell(MapColor.COLOR_YELLOW))
			.transform(axeOrPickaxe())
			.transform(CBCBuilderTransformers.projectile("projectile/medium_range_deepwater_torpedo"))
			.transform(CBCBuilderTransformers.safeNbt())
			.loot(CBCBuilderTransformers.shellLoot())
			.lang("Medium Range Deepwater Torpedo")
			.item(MediumRangeDeepwaterTorpedoBlockItem::new)
			.tag(CBCTags.CBCItemTags.BIG_CANNON_PROJECTILES)
			.build()
			.register();

	public static final BlockEntry<MediumRangeDeepwaterTorpedoTypeBBlock> MEDIUM_RANGE_DEEPWATER_TORPEDO_TYPEB = REGISTRATE
			.block("medium_range_deepwater_torpedo_typeb", MediumRangeDeepwaterTorpedoTypeBBlock::new)
			.transform(shell(MapColor.COLOR_YELLOW))
			.transform(axeOrPickaxe())
			.transform(CBCBuilderTransformers.projectile("projectile/medium_range_deepwater_torpedo_typeb"))
			.transform(CBCBuilderTransformers.safeNbt())
			.loot(CBCBuilderTransformers.shellLoot())
			.lang("Medium Range Deepwater Torpedo")
			.item(MediumRangeDeepwaterTorpedoTypeBBlockItem::new)
			.tag(CBCTags.CBCItemTags.BIG_CANNON_PROJECTILES)
			.build()
			.register();

	public static final BlockEntry<HighspeedTorpedoBlock> HIGHSPEED_TORPEDO = REGISTRATE
			.block("highspeed_torpedo", HighspeedTorpedoBlock::new)
			.transform(shell(MapColor.COLOR_YELLOW))
			.transform(axeOrPickaxe())
			.transform(CBCBuilderTransformers.projectile("projectile/highspeed_torpedo"))
			.transform(CBCBuilderTransformers.safeNbt())
			.loot(CBCBuilderTransformers.shellLoot())
			.lang("Highspeed Torpedo")
			.item(HighspeedTorpedoBlockItem::new)
			.tag(CBCTags.CBCItemTags.BIG_CANNON_PROJECTILES)
			.build()
			.register();

	public static final BlockEntry<AirdroppedTorpedoBlock> AIRDROPPED_TORPEDO = REGISTRATE
			.block("airdropped_torpedo", AirdroppedTorpedoBlock::new)
			.transform(shell(MapColor.COLOR_YELLOW))
			.transform(axeOrPickaxe())
			.transform(CBCBuilderTransformers.projectile("projectile/airdropped_torpedo"))
			.transform(CBCBuilderTransformers.safeNbt())
			.loot(CBCBuilderTransformers.shellLoot())
			.lang("Airdropped Torpedo")
			.item(AirdroppedTorpedoBlockItem::new)
			.tag(CBCTags.CBCItemTags.BIG_CANNON_PROJECTILES)
			.build()
			.register();

	public static final BlockEntry<SharpnelTorpedoBlock> SHARPNEL_TORPEDO = REGISTRATE
			.block("sharpnel_torpedo", SharpnelTorpedoBlock::new)
			.transform(shell(MapColor.COLOR_YELLOW))
			.transform(axeOrPickaxe())
			.transform(CBCBuilderTransformers.projectile("projectile/sharpnel_torpedo"))
			.transform(CBCBuilderTransformers.safeNbt())
			.loot(CBCBuilderTransformers.shellLoot())
			.lang("Sharpnel Torpedo")
			.item(SharpnelTorpedoBlockItem::new)
			.tag(CBCTags.CBCItemTags.BIG_CANNON_PROJECTILES)
			.build()
			.register();

	public static final BlockEntry<AirdroppedShrapnelTorpedoBlock> AIRDROPPED_SHRAPNEL_TORPEDO = REGISTRATE
			.block("airdropped_shrapnel_torpedo", AirdroppedShrapnelTorpedoBlock::new)
			.transform(shell(MapColor.COLOR_YELLOW))
			.transform(axeOrPickaxe())
			.transform(CBCBuilderTransformers.projectile("projectile/airdropped_shrapnel_torpedo"))
			.transform(CBCBuilderTransformers.safeNbt())
			.loot(CBCBuilderTransformers.shellLoot())
			.lang("Airdropped Shrapnel Torpedo")
			.item(AirdroppedShrapnelTorpedoBlockItem::new)
			.tag(CBCTags.CBCItemTags.BIG_CANNON_PROJECTILES)
			.build()
			.register();

	public static final BlockEntry<DeepwaterShrapnelTorpedoBlock> DEEPWATER_SHRAPNEL_TORPEDO = REGISTRATE
			.block("deepwater_shrapnel_torpedo", DeepwaterShrapnelTorpedoBlock::new)
			.transform(shell(MapColor.COLOR_YELLOW))
			.transform(axeOrPickaxe())
			.transform(CBCBuilderTransformers.projectile("projectile/deepwater_shrapnel_torpedo"))
			.transform(CBCBuilderTransformers.safeNbt())
			.loot(CBCBuilderTransformers.shellLoot())
			.lang("Deepwater Shrapnel Torpedo")
			.item(DeepwaterShrapnelTorpedoBlockItem::new)
			.tag(CBCTags.CBCItemTags.BIG_CANNON_PROJECTILES)
			.build()
			.register();






	// Torpedo Blocks
	public static final BlockEntry<TorpedoTubeBodyBlock> STEEL_TORPEDO_BARREL = REGISTRATE
			.block("steel_torpedo_tube_barrel", p -> TorpedoTubeBodyBlock.verySmall(p, CBCMSTorpedoTubeMaterials.STEEL))
			.transform(CBCBuilderTransformers.cannonBarrel("steel", true))
			.loot(CBCBuilderTransformers.steelScrapLoot(10))
			.properties(p -> p.strength(5.0f,14f))
			.properties(p -> p.sound(SoundType.METAL))
			.tag(BlockTags.MINEABLE_WITH_PICKAXE)
			.item(TorpedoTubeBlockItem::new).build()
			.register();

	public static final BlockEntry<TorpedoTubeBodyBlock> STEEL_TORPEDO_CHAMBER = REGISTRATE
			.block("steel_torpedo_tube_chamber", p -> TorpedoTubeBodyBlock.medium(p, CBCMSTorpedoTubeMaterials.STEEL))
			.transform(CBCBuilderTransformers.cannonChamber("steel", true))
			.loot(CBCBuilderTransformers.steelScrapLoot(10))
			.properties(p -> p.strength(5.0f,14f))
			.properties(p -> p.sound(SoundType.METAL))
			.tag(BlockTags.MINEABLE_WITH_PICKAXE)
			.item(TorpedoTubeBlockItem::new).build()
			.register();

	public static final BlockEntry<TorpQuickfiringBreechBlock> STEEL_TORPEDO_QUICKFIRING_BREECH = REGISTRATE
			.block("steel_torpedo_quickfiring_breech", p -> new TorpQuickfiringBreechBlock(p, CBCMSTorpedoTubeMaterials.STEEL, steelSlidingBreech()))
			.lang("Steel Torpedo Quick-Firing Breech")
			//.transform(strongCannonBlock(false))
			.loot(CBCBuilderTransformers.steelScrapLoot(10))
			.transform(CBCMSBuilderTransformers.torpedoSlidingBreech("torpedo_sliding_breech/steel"))
			.properties(p -> p.strength(5.0f,14f))
			.properties(p -> p.sound(SoundType.METAL))
			.tag(BlockTags.MINEABLE_WITH_PICKAXE)
			.onRegister(CreateRegistrate.connectedTextures(() ->
					new SlidingBreechCTBehavior(CBCSpriteShifts.STEEL_SLIDING_BREECH_SIDE, CBCSpriteShifts.STEEL_SLIDING_BREECH_SIDE_HOLE)))
			//.item(TorpedoTubeBlockItem::new).build()
			.register();

	private static NonNullSupplier<? extends Block> steelSlidingBreech() {
		return STEEL_TORPEDO_SLIDING_BREECH;
	}

	public static final BlockEntry<TorpedoSlidingBreechBlock> STEEL_TORPEDO_SLIDING_BREECH = REGISTRATE
			.block("steel_torpedo_sliding_breech", p -> new TorpedoSlidingBreechBlock(p, CBCMSTorpedoTubeMaterials.STEEL, STEEL_TORPEDO_QUICKFIRING_BREECH))
			//.transform(strongCannonBlock(false))
			.loot(CBCBuilderTransformers.steelScrapLoot(10))
			//.transform(CBCMSBuilderTransformers.torpedoSlidingBreech("torpedo_sliding_breech/steel"))
			.transform(CBCDefaultStress.setImpact(32.0d))
			.onRegister(CreateRegistrate.connectedTextures(() ->
					new SlidingBreechCTBehavior(CBCSpriteShifts.STEEL_SLIDING_BREECH_SIDE, CBCSpriteShifts.STEEL_SLIDING_BREECH_SIDE_HOLE)))
			.register();

	public static final BlockEntry<CopycatPanelBlock> ANTIBLAST_COPYCAT_PANEL =
			REGISTRATE.block("antiblast_panel", CopycatPanelBlock::new)
					.transform(BuilderTransformers.copycat())
					.onRegister(CreateRegistrate.blockModel(() -> CopycatPanelModel::new))
					.item()
					.transform(customItemModel("copycat_base", "panel"))
					.properties(p -> p.strength(5.0f))
					.properties(p -> p.explosionResistance(24f))
					.properties(BlockBehaviour.Properties::noCollission)
					.register();

	public static final BlockEntry<LootBarrelBlock> LOOT_BARREL = REGISTRATE
			.block("loot_barrel", LootBarrelBlock::new)
			.properties(p -> p.strength(5.0f,14f))
			.properties(p -> p.sound(SoundType.WOOD))
			.item().build()
			.register();

	public static final BlockEntry<NethersteelQuickfiringBreechBlock> NETHERSTEEL_QUICKFIRING_BREECH = REGISTRATE
			.block("nethersteel_quickfiring_breech", p -> new NethersteelQuickfiringBreechBlock(p, CBCBigCannonMaterials.NETHERSTEEL, nethersteelSlidingBreech()))
			.lang("Nethersteel Quick-Firing Breech")
			.transform(strongCannonBlock(false))
			.transform(CBCBuilderTransformers.slidingBreech("sliding_breech/nethersteel"))
			.register();

	public static final BlockEntry<NethersteelSlidingBreechBlock> NETHERSTEEL_SLIDING_BREECH = REGISTRATE
			.block("nethersteel_sliding_breech", p -> new NethersteelSlidingBreechBlock(p, CBCBigCannonMaterials.NETHERSTEEL, NETHERSTEEL_QUICKFIRING_BREECH))
			.transform(strongCannonBlock(false))
			.transform(CBCBuilderTransformers.slidingBreech("sliding_breech/nethersteel"))
			.transform(CBCDefaultStress.setImpact(32.0d))
			.register();



	private static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> shell(MapColor color) {
		return b -> b.addLayer(() -> RenderType::solid)
				.properties(p -> p.mapColor(color))
				.properties(p -> p.strength(2.0f, 3.0f))
				.properties(p -> p.sound(SoundType.STONE));
	}

	private static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> strongCannonBlock(boolean canPassThrough) {
		NonNullUnaryOperator<BlockBuilder<T, P>> transform = b -> b.properties(p -> p.strength(50.0f, 1200.0f))
				.properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
				.properties(p -> p.requiresCorrectToolForDrops())
				.tag(BlockTags.MINEABLE_WITH_PICKAXE)
				.tag(BlockTags.NEEDS_DIAMOND_TOOL);
		return canPassThrough ? transform.andThen(b -> b.tag(CBCTags.CBCBlockTags.DRILL_CAN_PASS_THROUGH)) : transform;
	}

	private static NonNullSupplier<? extends Block> nethersteelSlidingBreech() {
		return NETHERSTEEL_SLIDING_BREECH;
	}


	public static void register() {
	}

}
