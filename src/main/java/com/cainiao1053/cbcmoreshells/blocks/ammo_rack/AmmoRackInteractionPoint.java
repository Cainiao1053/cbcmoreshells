package com.cainiao1053.cbcmoreshells.blocks.ammo_rack;

import java.util.HashSet;
import java.util.Set;

import com.cainiao1053.cbcmoreshells.Cbcmoreshells;
import com.cainiao1053.cbcmoreshells.cannon_control.contraption.MountedTorpedoTubeContraption;
import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.ITorpedoTubeBlockEntity;
import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.TorpedoTubeBlock;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.TorpedoProjectileBlock;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.kinetics.mechanicalArm.AllArmInteractionPointTypes;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.minecraft.world.phys.Vec3;
// rbasamoyai.createbigcannons.cannon_control.cannon_mount.CannonMountBlockEntity;
//import rbasamoyai.createbigcannons.cannon_control.cannon_mount.ExtendsCannonMount;
//import rbasamoyai.createbigcannons.cannon_control.contraption.AbstractMountedCannonContraption;
//import rbasamoyai.createbigcannons.cannon_control.contraption.MountedAutocannonContraption;
//import rbasamoyai.createbigcannons.cannon_control.contraption.MountedBigCannonContraption;
//import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;
//import rbasamoyai.createbigcannons.cannon_control.fixed_cannon_mount.FixedCannonMountBlockEntity;
//import rbasamoyai.createbigcannons.cannons.autocannon.breech.AbstractAutocannonBreechBlockEntity;
//import rbasamoyai.createbigcannons.cannons.big_cannons.BigCannonBlock;
//import rbasamoyai.createbigcannons.cannons.big_cannons.IBigCannonBlockEntity;
//import rbasamoyai.createbigcannons.config.CBCConfigs;
//import rbasamoyai.createbigcannons.munitions.autocannon.ammo_container.AutocannonAmmoContainerItem;
import org.slf4j.Logger;
import rbasamoyai.createbigcannons.munitions.big_cannon.BigCannonMunitionBlock;
//import rbasamoyai.createbigcannons.munitions.big_cannon.ProjectileBlock;
import rbasamoyai.createbigcannons.munitions.big_cannon.ProjectileBlockItem;
import rbasamoyai.createbigcannons.munitions.big_cannon.propellant.BigCartridgeBlock;
import rbasamoyai.createbigcannons.munitions.big_cannon.propellant.BigCartridgeBlockItem;

public class AmmoRackInteractionPoint extends ArmInteractionPoint {

	public AmmoRackInteractionPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
		super(type, level, pos, state);
		this.mode = Mode.TAKE;
	}

	Logger LOGGER = Cbcmoreshells.LOGGER;


	@Override
	public ItemStack extract(int slot, boolean simulate) {
		if(!simulate) {
			return ItemStack.EMPTY;
		}
		AmmoRackBlockEntity be = (AmmoRackBlockEntity) level.getBlockEntity(pos);
		ItemStack stack = be.getInventory().extractItem(slot,1,simulate);
		if(stack.getItem() instanceof BigCartridgeBlockItem) {
			return stack;
		}
		if(!(stack.getItem() instanceof ProjectileBlockItem)) {
			return ItemStack.EMPTY;
		}
		if(!be.getInventory().isItemValid(slot, stack)) {
			return ItemStack.EMPTY;
		}
		return stack;
	}

	@Override
	public void cycleMode() {
	}

	@Override
	public ItemStack insert(ItemStack stack, boolean simulate) {
		return ItemStack.EMPTY;
	}

//	@Override
//	public int getSlotCount() {
//		return 0;
//	}



}
