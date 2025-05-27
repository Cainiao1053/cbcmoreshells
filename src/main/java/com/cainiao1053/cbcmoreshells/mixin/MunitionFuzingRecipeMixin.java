package com.cainiao1053.cbcmoreshells.mixin;

import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rbasamoyai.createbigcannons.crafting.munition_assembly.MunitionFuzingRecipe;
import rbasamoyai.createbigcannons.munitions.FuzedItemMunition;
import rbasamoyai.createbigcannons.munitions.FuzedProjectileBlockItem;
import rbasamoyai.createbigcannons.munitions.autocannon.AutocannonCartridgeItem;
import rbasamoyai.createbigcannons.munitions.fuzes.FuzeItem;

@Mixin(MunitionFuzingRecipe.class)
public abstract class MunitionFuzingRecipeMixin extends CustomRecipe {

	private MunitionFuzingRecipeMixin() {
		super(null, null);
	}

	@Inject(method = "matches", at = @At("HEAD"), cancellable = true, remap = false)
	private void injectedMatches(CraftingContainer container, Level level, CallbackInfoReturnable<Boolean> cir) {
		ItemStack round = ItemStack.EMPTY;
		ItemStack fuze = ItemStack.EMPTY;

		for (int i = 0; i < container.getContainerSize(); ++i) {
			ItemStack stack = container.getItem(i);
			if (stack.isEmpty()) continue;

			if (stack.getItem() instanceof AutocannonCartridgeItem) {
				if (!round.isEmpty()) {
					cir.setReturnValue(false);
					return;
				}
				stack = AutocannonCartridgeItem.getProjectileStack(stack);
			}

			if (stack.getItem() instanceof FuzedItemMunition) {
				if (!round.isEmpty() || stack.getOrCreateTag().contains("Fuze", Tag.TAG_COMPOUND)) {
					cir.setReturnValue(false);
					return;
				}
				round = stack;
			} else if (stack.getItem() instanceof FuzedProjectileBlockItem) {
				if (!round.isEmpty() || stack.getOrCreateTag().getCompound("BlockEntityTag").contains("Fuze", Tag.TAG_COMPOUND)) {
					cir.setReturnValue(false);
					return;
				}
				round = stack;
			} else if (stack.getItem() instanceof FuzeItem) {
				if (!fuze.isEmpty()) {
					cir.setReturnValue(false);
					return;
				}
				fuze = stack;
			} else {
				cir.setReturnValue(false);
				return;
			}
		}

		cir.setReturnValue(!round.isEmpty() && !fuze.isEmpty());
	}

	@Inject(method = "assemble", at = @At("HEAD"), cancellable = true, remap = false)
	private void injectedAssemble(CraftingContainer container, RegistryAccess access, CallbackInfoReturnable<ItemStack> cir) {
		ItemStack round = ItemStack.EMPTY;
		ItemStack fuze = ItemStack.EMPTY;

		for (int i = 0; i < container.getContainerSize(); ++i) {
			ItemStack stack = container.getItem(i);
			if (stack.isEmpty()) continue;

			if (stack.getItem() instanceof FuzedItemMunition || stack.getItem() instanceof AutocannonCartridgeItem || stack.getItem() instanceof FuzedProjectileBlockItem) {
				if (!round.isEmpty()) {
					cir.setReturnValue(ItemStack.EMPTY);
					return;
				}
				round = stack;
			} else if (stack.getItem() instanceof FuzeItem) {
				if (!fuze.isEmpty()) {
					cir.setReturnValue(ItemStack.EMPTY);
					return;
				}
				fuze = stack;
			} else {
				cir.setReturnValue(ItemStack.EMPTY);
				return;
			}
		}

		if (round.isEmpty() || fuze.isEmpty()) {
			cir.setReturnValue(ItemStack.EMPTY);
			return;
		}

		ItemStack result = round.copy();
		result.setCount(1);
		ItemStack fuzeCopy = fuze.copy();
		fuzeCopy.setCount(1);
		CompoundTag tag = result.getOrCreateTag();

		if (result.getItem() instanceof FuzedItemMunition) {
			tag.put("Fuze", fuzeCopy.save(new CompoundTag()));
		} else if (result.getItem() instanceof AutocannonCartridgeItem) {
			CompoundTag projectileTag = tag.getCompound("Projectile").getCompound("tag");
			projectileTag.put("Fuze", fuzeCopy.save(new CompoundTag()));
			tag.getCompound("Projectile").put("tag", projectileTag);
		} else if (result.getItem() instanceof FuzedProjectileBlockItem) {
			CompoundTag blockEntityTag = tag.getCompound("BlockEntityTag");
			blockEntityTag.put("Fuze", fuzeCopy.save(new CompoundTag()));
			blockEntityTag.putString("id", "createbigcannons:fuzed_block");
			tag.put("BlockEntityTag", blockEntityTag);
		}

		cir.setReturnValue(result);
	}
}
