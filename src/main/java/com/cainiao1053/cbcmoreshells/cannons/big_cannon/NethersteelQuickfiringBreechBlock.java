package com.cainiao1053.cbcmoreshells.cannons.big_cannon;

import java.util.HashSet;
import java.util.Set;

import com.cainiao1053.cbcmoreshells.index.CBCMSBlockEntities;

import com.tterrag.registrate.util.nullness.NonNullSupplier;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import rbasamoyai.createbigcannons.cannons.big_cannons.breeches.quickfiring_breech.QuickfiringBreechBlock;
import rbasamoyai.createbigcannons.cannons.big_cannons.breeches.quickfiring_breech.QuickfiringBreechBlockEntity;
import rbasamoyai.createbigcannons.cannons.big_cannons.material.BigCannonMaterial;

public class NethersteelQuickfiringBreechBlock extends QuickfiringBreechBlock {



	public NethersteelQuickfiringBreechBlock(Properties properties, BigCannonMaterial material, NonNullSupplier<? extends Block> slidingConversion) {
		super(properties, material, slidingConversion);
	}

	@Override
	public BlockEntityType<? extends QuickfiringBreechBlockEntity> getBlockEntityType() {
		return CBCMSBlockEntities.NETHERSTEELQUICKFIRING_BREECH.get();
	}



}
