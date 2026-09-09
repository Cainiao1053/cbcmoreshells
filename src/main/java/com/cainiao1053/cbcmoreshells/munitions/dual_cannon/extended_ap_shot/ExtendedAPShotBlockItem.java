package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.extended_ap_shot;

import com.cainiao1053.cbcmoreshells.base.CBCMSTooltip;
import com.cainiao1053.cbcmoreshells.munitions.dual_cannon.FuzedDualCannonProjectileBlockItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;


public class ExtendedAPShotBlockItem extends FuzedDualCannonProjectileBlockItem {

	public ExtendedAPShotBlockItem(Block block, Properties properties) {
		super(block, properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, context, tooltip, flag);

		CBCMSTooltip.appendInertDualCannonProjectileInfo(stack, context, tooltip, flag,
				getMunitionEntry());
	}

}
