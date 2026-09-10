package com.cainiao1053.cbcmoreshells.munitions.dual_cannon;

import com.cainiao1053.cbcmoreshells.Cbcmoreshells;
import com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib.CBCMSDualCannonMunitionRegistry;
import com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib.DualCannonMunitionProperties;
import com.cainiao1053.cbcmoreshells.munitions.dual_cannon.table.BallisticColumnMode;
import com.cainiao1053.cbcmoreshells.munitions.dual_cannon.table.DualCannonLoadout;
import com.cainiao1053.cbcmoreshells.munitions.dual_cannon.table.DualCannonMomentumModel;
import com.cainiao1053.cbcmoreshells.munitions.dual_cannon.table.DualCannonShellContext;
import com.cainiao1053.cbcmoreshells.munitions.dual_cannon.table.DualCannonStats;
import com.cainiao1053.cbcmoreshells.munitions.dual_cannon.table.StatSink;
import com.simibubi.create.foundation.item.TooltipHelper;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import rbasamoyai.createbigcannons.munitions.FuzedProjectileBlockItem;

import javax.annotation.Nullable;
import java.util.List;

import static com.cainiao1053.cbcmoreshells.base.CBCMSTooltip.addHoldShift;
import static rbasamoyai.createbigcannons.base.CBCTooltip.getPalette;

public class FuzedDualCannonProjectileBlockItem extends FuzedProjectileBlockItem {

	public FuzedDualCannonProjectileBlockItem(Block block, Properties properties) {
		super(block, properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, context, tooltip, flag);
		boolean desc = Screen.hasShiftDown();
		if (!desc) {
			addHoldShift(desc, tooltip);
			return;
		}
		String key1 = "block."+Cbcmoreshells.MODID+".dual_cannon_projectile.tooltip.title";
		FontHelper.Palette palette = getPalette();
		tooltip.add(Component.translatable(key1).withStyle(ChatFormatting.GRAY));
		String key2 = "block."+Cbcmoreshells.MODID+".dual_cannon_projectile.tooltip.desc";
		tooltip.addAll(TooltipHelper.cutStringTextComponent(I18n.get(key2), palette.primary(), palette.highlight(), 1));

	}

	/**
	 * This round's entry in the dual cannon registry, or null if the block was never registered
	 * there. {@code getBlock()} is per-instance, so two rounds sharing an item class still resolve
	 * to their own entry.
	 */
	@Nullable
	public CBCMSDualCannonMunitionRegistry.Entry getMunitionEntry() {
		return CBCMSDualCannonMunitionRegistry.of(this.getBlock());
	}

	/**
	 * Datapack properties for this round. Carries no per-shot override, but overrides only touch
	 * {@code durability_mass}, so drag, gravity and muzzle velocity here are what the shell really
	 * flies with. Only call this after registration has finished.
	 */
	@Nullable
	public DualCannonMunitionProperties getProjectileProperties() {
		CBCMSDualCannonMunitionRegistry.Entry entry = this.getMunitionEntry();
		return entry == null ? null : CBCMSDualCannonMunitionRegistry.properties(entry);
	}

	// -------------------------------------------------------------------------------------------
	// Firing table hooks
	//
	// A shell class declares what its table shows by overriding these; the table itself knows
	// nothing about shell kinds. Subclasses call super and add, so the shared rows stay in one
	// place.
	// -------------------------------------------------------------------------------------------

	/**
	 * Stats that do not change with the barrel material, shown once above the material rows. The
	 * context cannot reach a barrel, so nothing declared here can accidentally vary per material.
	 */
	public void collectShellStats(StatSink<DualCannonShellContext> sink) {
		sink.add(DualCannonStats.MUZZLE_VELOCITY);
		sink.add(DualCannonStats.DEFLECTION_ANGLE);
		sink.add(DualCannonStats.BOUNCE_ANGLE);
	}

	/**
	 * Stats that change with the barrel material, one column each in the material rows. Kept short
	 * on purpose — every column here costs a distance column in the ballistic block.
	 */
	public void collectMaterialStats(StatSink<DualCannonLoadout> sink) {
		sink.add(DualCannonStats.RELOAD);
		sink.add(DualCannonStats.RECOIL);
	}

	/**
	 * What the distance columns can show, in the order the toggle button cycles them. The first
	 * entry is the default.
	 */
	public List<BallisticColumnMode> ballisticModes() {
		return List.of(BallisticColumnMode.MOMENTUM, BallisticColumnMode.FLIGHT_TIME);
	}

	/** How this shell class converts impact speed into penetrating power. */
	public DualCannonMomentumModel momentumModel() {
		return DualCannonMomentumModel.CAPPED;
	}

}
