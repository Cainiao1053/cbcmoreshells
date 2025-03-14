package com.cainiao1053.cbcmoreshells.munitions.big_cannon;

import net.minecraft.world.level.block.entity.BlockEntityType;
import rbasamoyai.createbigcannons.index.CBCBlockEntities;
import rbasamoyai.createbigcannons.munitions.big_cannon.FuzedBlockEntity;
import rbasamoyai.createbigcannons.munitions.big_cannon.FuzedProjectileBlock;

public abstract class ShellessShellBlock<ENTITY_TYPE extends ShellessFuzedBigCannonProjectile>
	extends ShellessFuzedProjectileBlock<FuzedBlockEntity, ENTITY_TYPE> {

	protected ShellessShellBlock(Properties properties) {
		super(properties);
	}

	@Override
	public Class<FuzedBlockEntity> getBlockEntityClass() {
		return FuzedBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends FuzedBlockEntity> getBlockEntityType() {
		return CBCBlockEntities.FUZED_BLOCK.get();
	}

}
