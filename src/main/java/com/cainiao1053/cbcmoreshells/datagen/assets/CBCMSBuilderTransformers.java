package com.cainiao1053.cbcmoreshells.datagen.assets;

import com.cainiao1053.cbcmoreshells.Cbcmoreshells;
import com.cainiao1053.cbcmoreshells.cannons.dual_cannon.DualCannonBlock;
import com.cainiao1053.cbcmoreshells.cannons.dual_cannon.DualCannonBlockItem;
import com.cainiao1053.cbcmoreshells.cannons.projectile_rack.ProjectileRackBlock;
import com.cainiao1053.cbcmoreshells.cannons.projectile_rack.ProjectileRackBlockItem;
import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.TorpedoTubeBlock;
import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.TorpedoTubeBlockItem;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllTags.AllBlockTags;
import com.simibubi.create.content.kinetics.base.DirectionalAxisKineticBlock;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import rbasamoyai.createbigcannons.CBCTags;
//import rbasamoyai.createbigcannons.CreateBigCannons;
import rbasamoyai.createbigcannons.cannon_control.carriage.CannonCarriageBlock;
import rbasamoyai.createbigcannons.cannon_control.carriage.CannonCarriageBlockItem;
import rbasamoyai.createbigcannons.cannon_loading.CannonLoaderGen;
import rbasamoyai.createbigcannons.cannons.autocannon.AutocannonBarrelBlock;
import rbasamoyai.createbigcannons.cannons.autocannon.AutocannonBlock;
import rbasamoyai.createbigcannons.cannons.autocannon.AutocannonBlockItem;
import rbasamoyai.createbigcannons.cannons.autocannon.breech.AutocannonBreechBlock;
//import rbasamoyai.createbigcannons.cannons.big_cannons.BigCannonBlock;
import rbasamoyai.createbigcannons.cannons.big_cannons.BigCannonBlockItem;
import rbasamoyai.createbigcannons.cannons.big_cannons.breeches.sliding_breech.SlidingBreechBlockGen;
import rbasamoyai.createbigcannons.crafting.boring.CannonDrillGen;
import rbasamoyai.createbigcannons.crafting.builtup.CannonBuilderGen;
import rbasamoyai.createbigcannons.crafting.builtup.CannonBuilderHeadBlock;
import rbasamoyai.createbigcannons.crafting.casting.CannonCastMouldBlock;
import rbasamoyai.createbigcannons.crafting.incomplete.IncompleteScrewBreechBlockGen;
import rbasamoyai.createbigcannons.crafting.incomplete.IncompleteSlidingBreechBlockGen;
import rbasamoyai.createbigcannons.index.CBCItems;
import rbasamoyai.createbigcannons.munitions.autocannon.ammo_container.AutocannonAmmoContainerBlock;
import rbasamoyai.createbigcannons.munitions.autocannon.ammo_container.AutocannonAmmoContainerItem;
import rbasamoyai.createbigcannons.munitions.big_cannon.propellant.BigCannonPropellantBlock;
import rbasamoyai.createbigcannons.munitions.big_cannon.propellant.BigCartridgeBlock;
import rbasamoyai.createbigcannons.munitions.big_cannon.propellant.BigCartridgeBlockItem;
import rbasamoyai.createbigcannons.utils.CBCUtils;

public class CBCMSBuilderTransformers {

	public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> torpedoBarrel(String material, boolean bored) {
		NonNullUnaryOperator<BlockBuilder<T, P>> b1 = cannonPart(Cbcmoreshells.resource("block/cannon_barrel"),
			"cannon_barrel/" + material + "_cannon_barrel_side",
			"cannon_barrel/" + (bored ? "" : "unbored_") + material + "_cannon_barrel_end");
		return bored ? b1.andThen(b -> b.tag(CBCTags.CBCBlockTags.REDUCES_SPREAD)) : b1;
	}

	public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> builtUpCannonBarrel(String material, boolean bored) {
		NonNullUnaryOperator<BlockBuilder<T, P>> b1 = cannonPart(Cbcmoreshells.resource("block/built_up_cannon_barrel"),
			"cannon_barrel/built_up_" + material + "_cannon_barrel_side",
			"cannon_barrel/" + (bored ? "" : "unbored_") + "built_up_" + material + "_cannon_barrel_end");
		return bored ? b1.andThen(b -> b.tag(CBCTags.CBCBlockTags.REDUCES_SPREAD)) : b1;
	}

	public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> torpedoChamber(String material, boolean bored) {
		NonNullUnaryOperator<BlockBuilder<T, P>> b1 = cannonPart(CBCUtils.location("block/cube_column"),
			"cannon_chamber/" + material + "_cannon_chamber_side",
			"cannon_chamber/" + (bored ? "" : "unbored_") + material + "_cannon_chamber_end");
		return bored ? b1.andThen(b -> b.tag(CBCTags.CBCBlockTags.THICK_TUBING).tag(CBCTags.CBCBlockTags.REDUCES_SPREAD)) : b1;
	}

	public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> builtUpCannonChamber(String material, boolean bored) {
		NonNullUnaryOperator<BlockBuilder<T, P>> b1 = cannonPart(Cbcmoreshells.resource("block/built_up_cannon_chamber"),
			"cannon_chamber/built_up_" + material + "_cannon_chamber_side",
			"cannon_chamber/" + (bored ? "" : "unbored_") + "built_up_" + material + "_cannon_chamber_end");
		return bored ? b1.andThen(b -> b.tag(CBCTags.CBCBlockTags.THICK_TUBING).tag(CBCTags.CBCBlockTags.REDUCES_SPREAD)) : b1;
	}


	public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> cannonPart(ResourceLocation model, String side, String end) {
		ResourceLocation sideLoc = Cbcmoreshells.resource("block/" + side);
		ResourceLocation endLoc = Cbcmoreshells.resource("block/" + end);
		return b -> b.properties(p -> p.noOcclusion())
			.addLayer(() -> RenderType::cutoutMipped)
			.blockstate((c, p) -> p.directionalBlock(c.get(), p.models().withExistingParent(c.getName(), model)
				.texture("side", sideLoc)
				.texture("end", endLoc)
				.texture("particle", sideLoc)))
			.tag(AllBlockTags.SAFE_NBT.tag);
	}


	public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> cannonEnd(String pathAndMaterial) {
		ResourceLocation baseLoc = Cbcmoreshells.resource("block/cannon_end");
		ResourceLocation sideLoc = Cbcmoreshells.resource("block/" + pathAndMaterial + "_cannon_end_side");
		ResourceLocation topLoc = Cbcmoreshells.resource("block/" + pathAndMaterial + "_cannon_end_top");
		ResourceLocation bottomLoc = Cbcmoreshells.resource("block/" + pathAndMaterial + "_cannon_end_bottom");
		ResourceLocation knobLoc = Cbcmoreshells.resource("block/" + pathAndMaterial + "_cannon_end_knob");
		return b -> b.properties(p -> p.noOcclusion())
			.addLayer(() -> RenderType::cutoutMipped)
			.blockstate((c, p) -> p.directionalBlock(c.get(), p.models().withExistingParent(c.getName(), baseLoc)
				.texture("side", sideLoc)
				.texture("top", topLoc)
				.texture("bottom", bottomLoc)
				.texture("knob", knobLoc)
				.texture("particle", topLoc)))
			.tag(AllBlockTags.SAFE_NBT.tag);
	}



	public static <T extends Block & TorpedoTubeBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> torpedoSlidingBreech(String pathAndMaterial) {
		ResourceLocation itemBaseLoc = Cbcmoreshells.resource("block/torpedo_sliding_breech_item");
		ResourceLocation holeLoc = Cbcmoreshells.resource("block/" + pathAndMaterial + "_torpedo_sliding_breech_hole");
		ResourceLocation sideLoc = Cbcmoreshells.resource("block/" + pathAndMaterial + "_torpedo_sliding_breech_side");
		ResourceLocation sideHoleLoc = Cbcmoreshells.resource("block/" + pathAndMaterial + "_torpedo_sliding_breech_side_hole");
		ResourceLocation insideLoc = Cbcmoreshells.resource("block/" + pathAndMaterial + "_torpedo_sliding_breech_inside");
		ResourceLocation breechblockTopLoc = Cbcmoreshells.resource("block/" + pathAndMaterial + "_torpedo_sliding_breech_breechblock_top");
		ResourceLocation breechblockEndLoc = Cbcmoreshells.resource("block/" + pathAndMaterial + "_torpedo_sliding_breech_breechblock_end");
		ResourceLocation breechblockSideLoc = Cbcmoreshells.resource("block/" + pathAndMaterial + "_torpedo_sliding_breech_breechblock_side");
		ResourceLocation breechblockBottomLoc = Cbcmoreshells.resource("block/" + pathAndMaterial + "_torpedo_sliding_breech_breechblock_bottom");
		return b -> b.properties(p -> p.noOcclusion())
			.addLayer(() -> RenderType::cutoutMipped)
			//.blockstate(SlidingBreechBlockGen.create(pathAndMaterial)::generate)
			.tag(AllBlockTags.SAFE_NBT.tag)
			.item(TorpedoTubeBlockItem::new)
			.model((c, p) -> p.getBuilder(c.getName()).parent(p.getExistingFile(itemBaseLoc))
				.texture("hole", holeLoc)
				.texture("side", sideLoc)
				.texture("side_hole", sideHoleLoc)
				.texture("inside", insideLoc)
				.texture("breechblock_top", breechblockTopLoc)
				.texture("breechblock_end", breechblockEndLoc)
				.texture("breechblock_side", breechblockSideLoc)
				.texture("breechblock_bottom", breechblockBottomLoc))
			.build();
	}

	public static <T extends Block & ProjectileRackBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> projectileRackSlidingBreech(String pathAndMaterial) {
		ResourceLocation itemBaseLoc = Cbcmoreshells.resource("block/projectile_rack_sliding_breech_item");
		ResourceLocation holeLoc = Cbcmoreshells.resource("block/" + pathAndMaterial + "_projectile_rack_sliding_breech_hole");
		ResourceLocation sideLoc = Cbcmoreshells.resource("block/" + pathAndMaterial + "_projectile_rack_sliding_breech_side");
		ResourceLocation sideHoleLoc = Cbcmoreshells.resource("block/" + pathAndMaterial + "_projectile_rack_sliding_breech_side_hole");
		ResourceLocation insideLoc = Cbcmoreshells.resource("block/" + pathAndMaterial + "_projectile_rack_sliding_breech_inside");
		ResourceLocation breechblockTopLoc = Cbcmoreshells.resource("block/" + pathAndMaterial + "_projectile_rack_sliding_breech_breechblock_top");
		ResourceLocation breechblockEndLoc = Cbcmoreshells.resource("block/" + pathAndMaterial + "_projectile_rack_sliding_breech_breechblock_end");
		ResourceLocation breechblockSideLoc = Cbcmoreshells.resource("block/" + pathAndMaterial + "_projectile_rack_sliding_breech_breechblock_side");
		ResourceLocation breechblockBottomLoc = Cbcmoreshells.resource("block/" + pathAndMaterial + "_projectile_rack_sliding_breech_breechblock_bottom");
		return b -> b.properties(p -> p.noOcclusion())
				.addLayer(() -> RenderType::cutoutMipped)
				//.blockstate(SlidingBreechBlockGen.create(pathAndMaterial)::generate)
				.tag(AllBlockTags.SAFE_NBT.tag)
				.item(ProjectileRackBlockItem::new)
				.model((c, p) -> p.getBuilder(c.getName()).parent(p.getExistingFile(itemBaseLoc))
						.texture("hole", holeLoc)
						.texture("side", sideLoc)
						.texture("side_hole", sideHoleLoc)
						.texture("inside", insideLoc)
						.texture("breechblock_top", breechblockTopLoc)
						.texture("breechblock_end", breechblockEndLoc)
						.texture("breechblock_side", breechblockSideLoc)
						.texture("breechblock_bottom", breechblockBottomLoc))
				.build();
	}

	public static <T extends Block & DualCannonBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> dualCannonSlidingBreech(String pathAndMaterial) {
		ResourceLocation itemBaseLoc = Cbcmoreshells.resource("block/dual_cannon_sliding_breech_item");
		ResourceLocation holeLoc = Cbcmoreshells.resource("block/" + pathAndMaterial + "_dual_cannon_sliding_breech_hole");
		ResourceLocation sideLoc = Cbcmoreshells.resource("block/" + pathAndMaterial + "_dual_cannon_sliding_breech_side");
		ResourceLocation sideHoleLoc = Cbcmoreshells.resource("block/" + pathAndMaterial + "_dual_cannon_sliding_breech_side_hole");
		ResourceLocation insideLoc = Cbcmoreshells.resource("block/" + pathAndMaterial + "_dual_cannon_sliding_breech_inside");
		ResourceLocation breechblockTopLoc = Cbcmoreshells.resource("block/" + pathAndMaterial + "_dual_cannon_sliding_breech_breechblock_top");
		ResourceLocation breechblockEndLoc = Cbcmoreshells.resource("block/" + pathAndMaterial + "_dual_cannon_sliding_breech_breechblock_end");
		ResourceLocation breechblockSideLoc = Cbcmoreshells.resource("block/" + pathAndMaterial + "_dual_cannon_sliding_breech_breechblock_side");
		ResourceLocation breechblockBottomLoc = Cbcmoreshells.resource("block/" + pathAndMaterial + "_dual_cannon_sliding_breech_breechblock_bottom");
		return b -> b.properties(p -> p.noOcclusion())
				.addLayer(() -> RenderType::cutoutMipped)
				//.blockstate(SlidingBreechBlockGen.create(pathAndMaterial)::generate)
				.tag(AllBlockTags.SAFE_NBT.tag)
				.item(DualCannonBlockItem::new)
				.model((c, p) -> p.getBuilder(c.getName()).parent(p.getExistingFile(itemBaseLoc))
						.texture("hole", holeLoc)
						.texture("side", sideLoc)
						.texture("side_hole", sideHoleLoc)
						.texture("inside", insideLoc)
						.texture("breechblock_top", breechblockTopLoc)
						.texture("breechblock_end", breechblockEndLoc)
						.texture("breechblock_side", breechblockSideLoc)
						.texture("breechblock_bottom", breechblockBottomLoc))
				.build();
	}



	public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> unuese_projectile(String pathAndMaterial) {
		return unused_projectile(pathAndMaterial, false);
	}

	public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> unused_projectile(String pathAndMaterial, boolean useStandardModel) {
		ResourceLocation baseLoc = Cbcmoreshells.resource(String.format("block/%sprojectile_block", useStandardModel ? "standard_" : ""));
		ResourceLocation sideLoc = Cbcmoreshells.resource("block/" + pathAndMaterial);
		ResourceLocation topLoc = Cbcmoreshells.resource("block/" + pathAndMaterial + "_top");
		ResourceLocation bottomLoc = Cbcmoreshells.resource("block/" + pathAndMaterial + "_bottom");
		return b -> b.properties(p -> p.noOcclusion())
			.addLayer(() -> RenderType::solid)
			.blockstate((c, p) -> {
				BlockModelBuilder builder = p.models().withExistingParent(c.getName(), baseLoc)
					.texture("side", sideLoc)
					.texture("top", topLoc)
					.texture("particle", topLoc);
				if (!useStandardModel) builder.texture("bottom", bottomLoc);
				p.directionalBlock(c.get(), builder);
			});
	}

	public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> powderCharge() {
		ResourceLocation baseLoc = Cbcmoreshells.resource("block/powder_charge");
		return b -> b.properties(p -> p.noOcclusion())
			.addLayer(() -> RenderType::solid)
			.blockstate((c, p) -> BlockStateGen.axisBlock(c, p, $ -> p.models().getExistingFile(baseLoc)));
	}

	public static <T extends BigCartridgeBlock & BigCannonPropellantBlock, P> NonNullUnaryOperator<BlockBuilder<T, P>> bigCartridge() {
		ResourceLocation filledLoc = Cbcmoreshells.resource("block/big_cartridge_filled");
		ResourceLocation emptyLoc = Cbcmoreshells.resource("block/big_cartridge_empty");
		return b -> b.properties(p -> p.noOcclusion())
			.addLayer(() -> RenderType::solid)
			.blockstate((c, p) -> BlockStateGen.directionalBlockIgnoresWaterlogged(c, p, s -> {
				return p.models().getExistingFile(s.getValue(BigCartridgeBlock.FILLED) ? filledLoc : emptyLoc);
			}))
			.tag(AllBlockTags.SAFE_NBT.tag)
			.loot((t, c) -> {
				t.add(c, LootTable.lootTable()
					.withPool(t.applyExplosionCondition(c, LootPool.lootPool()
						.add(LootItem.lootTableItem(c))
						.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
						.apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY).copy("Power", "Power")))));
			})
			.item(BigCartridgeBlockItem::new)
			.tag(CBCTags.CBCItemTags.BIG_CANNON_CARTRIDGES)
			.model((c, p) -> {
				p.withExistingParent(c.getName(), emptyLoc)
					.override().model(p.getExistingFile(filledLoc)).predicate(Cbcmoreshells.resource("big_cartridge_filled"), 1).end();
			})
			.build();
	}



	public static <T extends Item, P> NonNullUnaryOperator<ItemBuilder<T, P>> torpedoSlidingBreechblock(String pathAndMaterial) {
		ResourceLocation baseLoc = Cbcmoreshells.resource("item/torpedo_sliding_breechblock");
		ResourceLocation topLoc = Cbcmoreshells.resource("block/" + pathAndMaterial + "_torpedo_sliding_breech_breechblock_top");
		ResourceLocation endLoc = Cbcmoreshells.resource("block/" + pathAndMaterial + "_torpedo_sliding_breech_breechblock_end");
		ResourceLocation sideLoc = Cbcmoreshells.resource("block/" + pathAndMaterial + "_torpedo_sliding_breech_breechblock_side");
		ResourceLocation bottomLoc = Cbcmoreshells.resource("block/" + pathAndMaterial + "_torpedo_sliding_breech_breechblock_bottom");
		return b -> b.model((c, p) -> p.getBuilder(c.getName()).parent(p.getExistingFile(baseLoc))
			.texture("top", topLoc)
			.texture("end", endLoc)
			.texture("side", sideLoc)
			.texture("bottom", bottomLoc));
	}






	public static <T extends Block> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> simpleBlock(String path) {
		return (c, p) -> p.simpleBlock(c.get(), p.models().getExistingFile(Cbcmoreshells.resource(path)));
	}



	public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> safeNbt() {
		return b -> b.tag(AllBlockTags.SAFE_NBT.tag);
	}

}
