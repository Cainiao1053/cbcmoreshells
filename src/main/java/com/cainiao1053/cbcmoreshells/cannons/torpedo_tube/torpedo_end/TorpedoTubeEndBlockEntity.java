package com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.torpedo_end;

import java.util.List;

import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.ITorpedoTubeBlockEntity;
import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.TorpedoTubeBlock;
import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.material.TorpedoTubeMaterial;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;

import com.simibubi.create.foundation.utility.Iterate;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import rbasamoyai.createbigcannons.cannons.big_cannons.BigCannonBehavior;
import rbasamoyai.createbigcannons.cannons.big_cannons.BigCannonBlock;
import rbasamoyai.createbigcannons.cannons.big_cannons.IBigCannonBlockEntity;
import rbasamoyai.createbigcannons.cannons.big_cannons.material.BigCannonMaterial;
import rbasamoyai.createbigcannons.crafting.WandActionable;
import rbasamoyai.createbigcannons.crafting.boring.AbstractCannonDrillBlockEntity;
import rbasamoyai.createbigcannons.crafting.boring.DrillBoringBlockRecipe;
import rbasamoyai.createbigcannons.crafting.builtup.LayeredBigCannonBlockEntity;
import rbasamoyai.createbigcannons.crafting.casting.CannonCastShape;

public class TorpedoTubeEndBlockEntity extends SmartBlockEntity implements ITorpedoTubeBlockEntity, WandActionable {

	private BigCannonBehavior cannonBehavior;

	public TorpedoTubeEndBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
		behaviours.add(this.cannonBehavior = new BigCannonBehavior(this, this::canLoadBlock));
	}

	@Override
	public boolean canLoadBlock(StructureBlockInfo blockInfo) {
		return false;
	}

	@Override
	public BigCannonBehavior cannonBehavior() {
		return this.cannonBehavior;
	}

	@Override
	public InteractionResult onWandUsed(UseOnContext context) {
		BlockState state = this.getBlockState();
		Direction dir = state.getValue(BlockStateProperties.FACING);
		DrillBoringBlockRecipe recipe = AbstractCannonDrillBlockEntity.getBlockRecipe(state, dir);
		if (recipe == null) return InteractionResult.PASS;
		if (!this.getLevel().isClientSide) {
			CompoundTag loadTag = this.saveWithFullMetadata();
			BlockState boredState = recipe.getResultState(state);
			TorpedoTubeMaterial material = ((TorpedoTubeBlock) state.getBlock()).getCannonMaterialInLevel(this.level, state, this.worldPosition);
			this.setRemoved();
			this.getLevel().setBlock(this.worldPosition, boredState, 11);
			BlockEntity newBE = this.getLevel().getBlockEntity(this.worldPosition);
			if (newBE != null) newBE.load(loadTag);

			for (Direction dir1 : Iterate.directions) {
				if (!this.cannonBehavior.isConnectedTo(dir1)) continue;
				BlockPos pos1 = this.worldPosition.relative(dir1);
				BlockState state1 = this.level.getBlockState(pos1);
				BlockEntity be1 = this.level.getBlockEntity(pos1);
				Direction opposite = dir1.getOpposite();
				if (state1.getBlock() instanceof TorpedoTubeBlock cBlock1
					&& cBlock1.getCannonMaterialInLevel(this.level, state1, pos1) == material
					&& be1 instanceof ITorpedoTubeBlockEntity cbe1
					&& cBlock1.canConnectToSide(state1, opposite)) {
					cbe1.cannonBehavior().setConnectedFace(opposite, true);
					if (cbe1 instanceof LayeredBigCannonBlockEntity layered) {
						for (CannonCastShape layer : layered.getLayers().keySet()) {
							layered.setLayerConnectedTo(opposite, layer, true);
						}
					}
					be1.setChanged();
				}
			}
			this.level.playSound(null, this.worldPosition, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 1.0f, 1.0f);
		}
		return InteractionResult.sidedSuccess(this.getLevel().isClientSide);
	}

}
