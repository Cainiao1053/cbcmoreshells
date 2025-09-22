package com.cainiao1053.cbcmoreshells.mixin;

import com.cainiao1053.cbcmoreshells.cannon_control.contraption.MountedProjectileRackContraption;
import com.cainiao1053.cbcmoreshells.cannon_control.contraption.MountedTorpedoTubeContraption;
import com.cainiao1053.cbcmoreshells.cannons.projectile_rack.breeches.quick_firing_breech.ProjectileRackCannonMountPoint;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rbasamoyai.createbigcannons.cannon_control.contraption.AbstractMountedCannonContraption;
import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;
import rbasamoyai.createbigcannons.cannon_control.fixed_cannon_mount.FixedCannonMountBlockEntity;
import rbasamoyai.createbigcannons.cannons.big_cannons.breeches.quickfiring_breech.CannonMountPoint;
import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.breeches.quick_firing_breech.TorpedoCannonMountPoint;

@Mixin(
		value = {CannonMountPoint.class},
		remap = false
)
public class CannonMountPointMixin {
	public CannonMountPointMixin() {
	}
	
	@Inject(
			method = {"getInsertedResultAndDoSomething"},
			at = {@At("RETURN")},
			cancellable = true
	)
	public void getInsertedResultAndDoSomethingInject(ItemStack stack, boolean simulate, AbstractMountedCannonContraption cannon, PitchOrientedContraptionEntity poce, CallbackInfoReturnable<ItemStack> cir) {
		if (cannon instanceof MountedTorpedoTubeContraption torp) {
			cir.setReturnValue(TorpedoCannonMountPoint.torpedoTubeInsert(stack, simulate, torp, poce));
		}else if (cannon instanceof MountedProjectileRackContraption rack) {
			cir.setReturnValue(ProjectileRackCannonMountPoint.projectileRackInsert(stack, simulate, rack, poce));
		}

	}
}