package com.cainiao1053.cbcmoreshells.munitions.fuzes;

import java.util.List;

import com.cainiao1053.cbcmoreshells.munitions.AbstractTorpedoProjectile;
import com.cainiao1053.cbcmoreshells.munitions.ProjectileContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class TorpedoFuzeItem extends Item {

	public TorpedoFuzeItem(Properties properties) {
		super(properties);
	}

	public boolean onProjectileTick(ItemStack stack, AbstractTorpedoProjectile projectile) { return false; }
	public boolean onProjectileClip(ItemStack stack, AbstractTorpedoProjectile projectile, Vec3 start, Vec3 end, ProjectileContext ctx, boolean baseFuze) { return false; }
	public boolean onProjectileImpact(ItemStack stack, AbstractTorpedoProjectile projectile, HitResult hitResult, AbstractTorpedoProjectile.ImpactResult impactResult, boolean baseFuze) { return false; }
	public boolean onProjectileExpiry(ItemStack stack, AbstractTorpedoProjectile projectile) { return false; }
	public boolean onRedstoneSignal(ItemStack stack, Level level, BlockPos pos, BlockState state, int signalStrength, Direction from) { return false; }
	public boolean canLingerInGround(ItemStack stack, AbstractTorpedoProjectile projectile) { return false; }

	public void addExtraInfo(List<Component> tooltip, boolean isSneaking, ItemStack stack) {}

}
