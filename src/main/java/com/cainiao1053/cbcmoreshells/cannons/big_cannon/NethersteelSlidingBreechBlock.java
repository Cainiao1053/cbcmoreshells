package com.cainiao1053.cbcmoreshells.cannons.big_cannon;

import javax.annotation.Nullable;

import com.cainiao1053.cbcmoreshells.index.CBCMSBlockEntities;
import com.tterrag.registrate.util.nullness.NonNullSupplier;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import rbasamoyai.createbigcannons.cannons.big_cannons.breeches.sliding_breech.SlidingBreechBlock;
import rbasamoyai.createbigcannons.cannons.big_cannons.breeches.sliding_breech.SlidingBreechBlockEntity;
import rbasamoyai.createbigcannons.cannons.big_cannons.material.BigCannonMaterial;


public class NethersteelSlidingBreechBlock extends SlidingBreechBlock {


	public NethersteelSlidingBreechBlock(Properties properties, BigCannonMaterial cannonMaterial, NonNullSupplier<? extends Block> quickfiringConversion) {
		super(properties, cannonMaterial, quickfiringConversion);
	}

	@Override
	public BlockEntityType<? extends SlidingBreechBlockEntity> getBlockEntityType() {
		return CBCMSBlockEntities.SLIDING_BREECH.get();
	}


}
