package com.cainiao1053.cbcmoreshells.cannons.torpedo_tube;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.cainiao1053.cbcmoreshells.cannon_control.contraption.MountedTorpedoTubeContraption;
import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.material.TorpedoTubeMaterial;
import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.torpedo_end.TorpedoTubeEnd;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.contraptions.Contraption;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.minecraft.world.phys.Vec3;
import rbasamoyai.createbigcannons.cannon_control.contraption.AbstractMountedCannonContraption;
//import rbasamoyai.createbigcannons.cannon_control.contraption.MountedTorpedoTubeContraption;
import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;
import rbasamoyai.createbigcannons.cannons.CannonContraptionProviderBlock;
import rbasamoyai.createbigcannons.cannons.InteractableCannonBlock;
import rbasamoyai.createbigcannons.cannons.big_cannons.BigCannonBehavior;
//import rbasamoyai.createbigcannons.cannons.big_cannons.cannon_end.TorpedoTubeEnd;
//import rbasamoyai.createbigcannons.cannons.big_cannons.material.BigCannonMaterial;
import rbasamoyai.createbigcannons.config.CBCConfigs;
//import rbasamoyai.createbigcannons.crafting.builtup.LayeredBigCannonBlockEntity;
//import rbasamoyai.createbigcannons.crafting.casting.CannonCastShape;
import rbasamoyai.createbigcannons.crafting.welding.WeldableBlock;
import rbasamoyai.createbigcannons.equipment.manual_loading.HandloadingTool;
import rbasamoyai.createbigcannons.multiloader.NetworkPlatform;
import rbasamoyai.createbigcannons.munitions.big_cannon.BigCannonMunitionBlock;
import rbasamoyai.createbigcannons.network.ClientboundUpdateContraptionPacket;

public interface TorpedoTubeBlock extends WeldableBlock, CannonContraptionProviderBlock, InteractableCannonBlock {

	TorpedoTubeMaterial getCannonMaterial();

	Direction getFacing(BlockState state);

	default TorpedoTubeEnd getOpeningType(@Nullable Level level, BlockState state, BlockPos pos) {
		return this.getDefaultOpeningType();
	}

	default TorpedoTubeEnd getOpeningType(MountedTorpedoTubeContraption contraption, BlockState state, BlockPos pos) {
		return this.getDefaultOpeningType();
	}

	TorpedoTubeEnd getDefaultOpeningType();

	default TorpedoTubeMaterial getCannonMaterialInLevel(LevelAccessor level, BlockState state, BlockPos pos) {
		return this.getCannonMaterial();
	}

    default boolean isImmovable(BlockState state) {
		return false;
	}

	default void onRemoveCannon(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.is(newState.getBlock())) return;
		Direction facing = this.getFacing(state);
		Direction opposite = facing.getOpposite();
		TorpedoTubeMaterial material = this.getCannonMaterial();

		BlockPos pos1 = pos.relative(facing);
		BlockState state1 = level.getBlockState(pos1);
		BlockEntity be1 = level.getBlockEntity(pos1);

		if (this.canConnectToSide(state, facing)
			&& state1.getBlock() instanceof TorpedoTubeBlock cBlock1
			&& cBlock1.getCannonMaterialInLevel(level, state1, pos1) == material
			&& be1 instanceof ITorpedoTubeBlockEntity cbe1
			&& cBlock1.canConnectToSide(state1, opposite)) {
			cbe1.cannonBehavior().setConnectedFace(opposite, false);
			cbe1.cannonBehavior().setWelded(opposite, false);


			cbe1.cannonBehavior().blockEntity.notifyUpdate();
		}
		BlockPos pos2 = pos.relative(opposite);
		BlockState state2 = level.getBlockState(pos2);
		BlockEntity be2 = level.getBlockEntity(pos2);

		if (this.canConnectToSide(state, opposite)
			&& state2.getBlock() instanceof TorpedoTubeBlock cBlock2
			&& cBlock2.getCannonMaterialInLevel(level, state2, pos2) == material
			&& be2 instanceof ITorpedoTubeBlockEntity cbe2
			&& cBlock2.canConnectToSide(state2, facing)) {
			cbe2.cannonBehavior().setConnectedFace(facing, false);
			cbe2.cannonBehavior().setWelded(facing, false);

			cbe2.cannonBehavior().blockEntity.notifyUpdate();
		}
	}

	default void playerWillDestroyBigCannon(Level level, BlockPos pos, BlockState state, Player player) {
		BlockEntity be = level.getBlockEntity(pos);
		if (be instanceof ITorpedoTubeBlockEntity cbe) {
			StructureBlockInfo info = cbe.cannonBehavior().block();
			BlockState innerState = info.state();
			ItemStack stack = innerState.getBlock() instanceof BigCannonMunitionBlock munition ? munition.getExtractedItem(info) : ItemStack.EMPTY;
			if (!stack.isEmpty()) {
				Containers.dropItemStack(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack);
			}
			cbe.cannonBehavior().removeBlock();
			cbe.cannonBehavior().blockEntity.notifyUpdate();
			if (!innerState.isAir()) {
				innerState.getBlock().playerWillDestroy(level, pos, innerState, player);
				SoundType soundtype = innerState.getSoundType();
				level.playSound(null, pos, soundtype.getBreakSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
			}
		}
	}

	static void onPlace(Level level, BlockPos pos) {
		BlockState state = level.getBlockState(pos);

		if (state.getBlock() instanceof TorpedoTubeBlock cBlock) {
			Direction facing = cBlock.getFacing(state);
			Direction opposite = facing.getOpposite();
			Vec3 center = Vec3.atCenterOf(pos);
			Vec3 offset = Vec3.atBottomCenterOf(facing.getNormal()).scale(0.5d);
			TorpedoTubeMaterial material = cBlock.getCannonMaterial();



			BlockEntity be = level.getBlockEntity(pos);
			if (be instanceof ITorpedoTubeBlockEntity cbe) {
				BlockPos pos1 = pos.relative(facing);
				BlockState state1 = level.getBlockState(pos1);
				BlockEntity be1 = level.getBlockEntity(pos1);

				if (cBlock.canConnectToSide(state, facing)
					&& state1.getBlock() instanceof TorpedoTubeBlock cBlock1
					&& cBlock1.getCannonMaterialInLevel(level, state1, pos1) == material
					&& level.getBlockEntity(pos1) instanceof ITorpedoTubeBlockEntity cbe1
					&& cBlock1.canConnectToSide(state1, opposite)) {
					cbe.cannonBehavior().setConnectedFace(facing, true);
					cbe1.cannonBehavior().setConnectedFace(opposite, true);

					cbe1.cannonBehavior().blockEntity.notifyUpdate();
					if (level instanceof ServerLevel slevel) {
						Vec3 particlePos = center.add(offset);
						slevel.sendParticles(ParticleTypes.CRIT, particlePos.x, particlePos.y, particlePos.z, 10, 0.5d, 0.5d, 0.5d, 0.1d);
					}
				}

				BlockPos pos2 = pos.relative(opposite);
				BlockState state2 = level.getBlockState(pos2);
				BlockEntity be2 = level.getBlockEntity(pos2);

				if (cBlock.canConnectToSide(state, opposite)
					&& state2.getBlock() instanceof TorpedoTubeBlock cBlock2
					&& cBlock2.getCannonMaterialInLevel(level, state2, pos2) == material
					&& level.getBlockEntity(pos2) instanceof ITorpedoTubeBlockEntity cbe2
					&& cBlock2.canConnectToSide(state2, facing)) {
					cbe.cannonBehavior().setConnectedFace(opposite, true);
					cbe2.cannonBehavior().setConnectedFace(facing, true);

					cbe2.cannonBehavior().blockEntity.notifyUpdate();
					if (level instanceof ServerLevel slevel) {
						Vec3 particlePos = center.add(offset.reverse());
						slevel.sendParticles(ParticleTypes.CRIT, particlePos.x, particlePos.y, particlePos.z, 10, 0.5d, 0.5d, 0.5d, 0.1d);
					}
				}
				cbe.cannonBehavior().blockEntity.notifyUpdate();
			}
		}
	}

	@Override
	default boolean onInteractWhileAssembled(Player player, BlockPos localPos, Direction side, InteractionHand interactionHand,
											 Level level, Contraption contraption, BlockEntity be,
											 StructureBlockInfo info, PitchOrientedContraptionEntity entity) {
		if (!(be instanceof ITorpedoTubeBlockEntity cbe)
			|| !(contraption instanceof MountedTorpedoTubeContraption cannon)
			|| ((TorpedoTubeBlock) info.state().getBlock()).getFacing(info.state()).getAxis() != side.getAxis()
			|| cbe.cannonBehavior().isConnectedTo(side))
			return false;
		ItemStack stack = player.getItemInHand(interactionHand);
		if (Block.byItem(stack.getItem()) instanceof BigCannonMunitionBlock munition) {
			if (!level.isClientSide) {
				StructureBlockInfo loadInfo = munition.getHandloadingInfo(stack, localPos, side);
				boolean flag = false;
				 	if (cbe.cannonBehavior().tryLoadingBlock(loadInfo)) {
						writeAndSyncSingleBlockData(be, info, entity, contraption);
						flag = true;
					}

				if (flag) {
					SoundType sound = loadInfo.state().getSoundType();
					level.playSound(null, player.blockPosition(), sound.getPlaceSound(), SoundSource.BLOCKS, sound.getVolume(), sound.getPitch());
					if (!player.isCreative()) stack.shrink(1);
				}
			}
			return true;
		}
//		if (stack.getItem() instanceof HandloadingTool tool && !player.getCooldowns().isOnCooldown(stack.getItem())) {
//			tool.onUseOnCannon(player, level, localPos, side, cannon);
//			return true;
//		}
		return false;
	}

	static void writeAndSyncSingleBlockData(BlockEntity be, StructureBlockInfo oldInfo, AbstractContraptionEntity entity, Contraption contraption) {
		CompoundTag tag = be.saveWithFullMetadata();
		tag.remove("x");
		tag.remove("y");
		tag.remove("z");
		StructureBlockInfo newInfo = new StructureBlockInfo(oldInfo.pos(), oldInfo.state(), tag);
		contraption.getBlocks().put(oldInfo.pos(), newInfo);
		NetworkPlatform.sendToClientTracking(new ClientboundUpdateContraptionPacket(entity, oldInfo.pos(), newInfo), entity);
	}

	static void writeAndSyncMultipleBlockData(Set<BlockPos> changed, AbstractContraptionEntity entity, Contraption contraption) {
		Map<BlockPos, StructureBlockInfo> changes = new HashMap<>(changed.size());
		Map<BlockPos, StructureBlockInfo> blocks = contraption.getBlocks();
		for (BlockPos pos : changed) {
			StructureBlockInfo oldInfo = blocks.get(pos);
			CompoundTag tag = null;
			BlockEntity be = contraption.presentBlockEntities.get(pos);
			if (be != null) {
				tag = be.saveWithFullMetadata();
				tag.remove("x");
				tag.remove("y");
				tag.remove("z");
			}
			StructureBlockInfo newInfo = new StructureBlockInfo(oldInfo.pos(), oldInfo.state(), tag);
			changes.put(pos, newInfo);
		}
		blocks.putAll(changes);
		NetworkPlatform.sendToClientTracking(new ClientboundUpdateContraptionPacket(entity, changes), entity);
	}

	@Override default boolean isWeldable(BlockState state) { return this.getCannonMaterial().properties().isWeldable(); }
	@Override default int weldDamage() { return this.getCannonMaterial().properties().weldDamage(); }

	@Override
	default boolean canWeldSide(Level level, Direction dir, BlockState state, BlockState otherState, BlockPos pos) {
		return otherState.getBlock() instanceof TorpedoTubeBlock cblock
			&& cblock.getCannonMaterial() == this.getCannonMaterial()
			&& this.isWeldable(state)
			&& cblock.isWeldable(otherState)
			&& this.canConnectToSide(state, dir)
			&& cblock.canConnectToSide(otherState, dir.getOpposite())
			&& level.getBlockEntity(pos) instanceof ITorpedoTubeBlockEntity cbe
			&& level.getBlockEntity(pos.relative(dir)) instanceof ITorpedoTubeBlockEntity cbe1
			&& (!cbe.cannonBehavior().isConnectedTo(dir) || !cbe1.cannonBehavior().isConnectedTo(dir.getOpposite()));
	}

	@Override
	default void weldBlock(Level level, BlockState state, BlockPos pos, Direction dir) {
		if (!(level.getBlockEntity(pos) instanceof ITorpedoTubeBlockEntity cbe)) return;
		BigCannonBehavior behavior = cbe.cannonBehavior();
		behavior.setConnectedFace(dir, true);
		behavior.setWelded(dir, true);
		behavior.blockEntity.notifyUpdate();
	}

	@Nonnull
	@Override
	default AbstractMountedCannonContraption getCannonContraption() {
		return new MountedTorpedoTubeContraption();
	}

}
