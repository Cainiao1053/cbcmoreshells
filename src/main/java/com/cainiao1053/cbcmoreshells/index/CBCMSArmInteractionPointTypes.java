package com.cainiao1053.cbcmoreshells.index;

import java.util.function.Function;

import javax.annotation.Nullable;

import com.cainiao1053.cbcmoreshells.Cbcmoreshells;
import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.breeches.quick_firing_breech.TorpedoCannonMountPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import rbasamoyai.createbigcannons.CreateBigCannons;
import rbasamoyai.createbigcannons.cannon_control.cannon_mount.CannonMountBlockEntity;
import rbasamoyai.createbigcannons.cannon_control.cannon_mount.CannonMountExtensionBlockEntity;
import rbasamoyai.createbigcannons.cannon_control.cannon_mount.YawControllerBlockEntity;
import rbasamoyai.createbigcannons.cannon_control.fixed_cannon_mount.FixedCannonMountBlockEntity;
//import rbasamoyai.createbigcannons.cannons.big_cannons.breeches.quickfiring_breech.CannonMountPoint;
import rbasamoyai.createbigcannons.index.CBCBlocks;

public class CBCMSArmInteractionPointTypes {

	public static final CannonMountType TORPEDO_TUBE = register("torpedo_cannon_mount", CannonMountType::new);

	private static <T extends ArmInteractionPointType> T register(String id, Function<ResourceLocation, T> factory) {
		T type = factory.apply(Cbcmoreshells.resource(id));
		ArmInteractionPointType.register(type);
		return type;
	}

	public static class CannonMountType extends ArmInteractionPointType {
		public CannonMountType(ResourceLocation id) {
			super(id);
		}

		@Override
		public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
			if (CBCBlocks.CANNON_MOUNT.has(state))
				return level.getBlockEntity(pos) instanceof CannonMountBlockEntity;
			if (CBCBlocks.FIXED_CANNON_MOUNT.has(state))
				return level.getBlockEntity(pos) instanceof FixedCannonMountBlockEntity;
			if (CBCBlocks.CANNON_MOUNT_EXTENSION.has(state))
				return level.getBlockEntity(pos) instanceof CannonMountExtensionBlockEntity;
			if (CBCBlocks.YAW_CONTROLLER.has(state))
				return level.getBlockEntity(pos) instanceof YawControllerBlockEntity;
			return false;
		}

		@Nullable
		@Override
		public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
			return new TorpedoCannonMountPoint(this, level, pos, state);
		}
	}

	public static void register() {
	}

}
