package com.cainiao1053.cbcmoreshells.cannons.dual_cannon.breeches;

import java.util.List;

import com.cainiao1053.cbcmoreshells.cannons.dual_cannon.IDualCannonBlockEntity;
import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.ITorpedoTubeBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import rbasamoyai.createbigcannons.cannons.big_cannons.BigCannonBehavior;


public abstract class AbstractDualCannonBreechBlockEntity extends KineticBlockEntity implements IDualCannonBlockEntity {

	protected BigCannonBehavior cannonBehavior;

	public AbstractDualCannonBreechBlockEntity(BlockEntityType<? extends AbstractDualCannonBreechBlockEntity> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviors) {
		super.addBehaviours(behaviors);
		behaviors.add(this.cannonBehavior = new BigCannonBehavior(this, this::canLoadBlock));
	}

	public abstract boolean isOpen();

	@Override
	public boolean canLoadBlock(StructureBlockInfo blockInfo) {
		return this.isOpen() && IDualCannonBlockEntity.super.canLoadBlock(blockInfo);
	}

	@Override
	public BigCannonBehavior cannonBehavior() {
		return this.cannonBehavior;
	}

}
