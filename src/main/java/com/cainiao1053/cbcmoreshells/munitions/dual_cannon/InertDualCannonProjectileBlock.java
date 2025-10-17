package com.cainiao1053.cbcmoreshells.munitions.dual_cannon;

import java.util.List;

import com.simibubi.create.foundation.block.IBE;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.BlockHitResult;
import rbasamoyai.createbigcannons.index.CBCBlockEntities;
import rbasamoyai.createbigcannons.index.CBCItems;

import rbasamoyai.createbigcannons.munitions.big_cannon.BigCannonProjectileBlockEntity;

public abstract class InertDualCannonProjectileBlock<S extends AbstractDualCannonProjectile> extends DualCannonProjectileBlock<AbstractDualCannonProjectile> implements IBE<BigCannonProjectileBlockEntity> {

	protected InertDualCannonProjectileBlock(Properties properties) {
		super(properties);
	}

	@Override
	public AbstractDualCannonProjectile getProjectile(Level level, List<StructureTemplate.StructureBlockInfo> projectileBlocks) {
		AbstractDualCannonProjectile projectile = this.getAssociatedEntityType().create(level);
		projectile.setTracer(getTracerFromBlocks(projectileBlocks));
		return projectile;
	}

	@Override
	public AbstractDualCannonProjectile getProjectile(Level level, ItemStack itemStack) {
		AbstractDualCannonProjectile projectile = this.getAssociatedEntityType().create(level);
		projectile.setTracer(getTracerFromItemStack(itemStack));
		return projectile;
	}

	//@Override
	public AbstractDualCannonProjectile getProjectile(Level level, BlockPos pos, BlockState state) {
		AbstractDualCannonProjectile projectile = this.getAssociatedEntityType().create(level);
		projectile.setTracer(getTracerFromBlock(level, pos, state));
		return projectile;
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (hand == InteractionHand.OFF_HAND)
			return InteractionResult.PASS;
		BigCannonProjectileBlockEntity projectileBlock = this.getBlockEntity(level, pos);
		if (projectileBlock == null)
			return InteractionResult.PASS;
		ItemStack stack = player.getItemInHand(hand);

		if (stack.isEmpty()) {
			if (projectileBlock.getItem(0).isEmpty())
				return InteractionResult.PASS;
			if (!level.isClientSide) {
				ItemStack resultStack = projectileBlock.removeItem(0, 1);
				if (!player.addItem(resultStack) && !player.isCreative()) {
					ItemEntity item = player.drop(resultStack, false);
					if (item != null) {
						item.setNoPickUpDelay();
						item.setTarget(player.getUUID());
					}
				}
				projectileBlock.notifyUpdate();
			}
			level.playSound(player, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.NEUTRAL, 1.0f, 1.0f);
			return InteractionResult.sidedSuccess(level.isClientSide);
		} else {
			int slot = -1;
			if (CBCItems.TRACER_TIP.isIn(stack)) {
				slot = 0;
			}
			if (slot == -1 || !projectileBlock.getItem(slot).isEmpty())
				return InteractionResult.PASS;
			if (!level.isClientSide) {
				ItemStack copy = player.getAbilities().instabuild ? stack.copy() : stack.split(1);
				copy.setCount(1);
				projectileBlock.setItem(slot, copy);
				projectileBlock.notifyUpdate();
			}
			level.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.NEUTRAL, 1.0f, 1.0f);
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
	}

	@Override public Class<BigCannonProjectileBlockEntity> getBlockEntityClass() { return BigCannonProjectileBlockEntity.class; }
	@Override public BlockEntityType<? extends BigCannonProjectileBlockEntity> getBlockEntityType() { return CBCBlockEntities.PROJECTILE_BLOCK.get(); }

}
