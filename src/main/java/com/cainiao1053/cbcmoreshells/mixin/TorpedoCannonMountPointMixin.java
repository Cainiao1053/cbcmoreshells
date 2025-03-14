package com.cainiao1053.cbcmoreshells.mixin;

import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.breeches.quick_firing_breech.TorpedoCannonMountPoint;
import org.spongepowered.asm.mixin.Mixin;

import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import rbasamoyai.createbigcannons.cannon_control.cannon_mount.CannonMountBlockEntity;
import rbasamoyai.createbigcannons.cannon_control.cannon_mount.ExtendsCannonMount;
import rbasamoyai.createbigcannons.cannon_control.contraption.AbstractMountedCannonContraption;
import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;
import rbasamoyai.createbigcannons.cannon_control.fixed_cannon_mount.FixedCannonMountBlockEntity;
//import rbasamoyai.createbigcannons.cannons.big_cannons.breeches.quickfiring_breech.CannonMountPoint;

@Mixin(TorpedoCannonMountPoint.class)
public abstract class TorpedoCannonMountPointMixin extends ArmInteractionPoint {

	TorpedoCannonMountPointMixin(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
		super(type, level, pos, state);
	}

	@Override
	public ItemStack insert(ItemStack stack, boolean simulate) {
		System.out.println("1145141919810");
		TorpedoCannonMountPoint self = (TorpedoCannonMountPoint) (Object) this;
		BlockEntity be = this.getLevel().getBlockEntity(this.pos);
		PitchOrientedContraptionEntity poce;
		if (be instanceof ExtendsCannonMount extendsMount) {
			CannonMountBlockEntity mount = extendsMount.getCannonMount();
			if (mount == null)
				return stack;
			poce = mount.getContraption();
		} else if (be instanceof FixedCannonMountBlockEntity mount) {
			poce = mount.getContraption();
		} else {
			return stack;
		}
		if (poce == null || !(poce.getContraption() instanceof AbstractMountedCannonContraption cannon))
			return stack;
		return self.getInsertedResultAndDoSomething(stack, simulate, cannon, poce);
	}

}
