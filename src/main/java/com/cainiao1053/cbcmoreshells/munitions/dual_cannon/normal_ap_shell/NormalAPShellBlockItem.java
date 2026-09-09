package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.normal_ap_shell;

import com.cainiao1053.cbcmoreshells.base.CBCMSTooltip;
import com.cainiao1053.cbcmoreshells.munitions.dual_cannon.FuzedDualCannonProjectileBlockItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;
import java.util.List;



public class NormalAPShellBlockItem extends FuzedDualCannonProjectileBlockItem {

	public NormalAPShellBlockItem(Block block, Properties properties) {
		super(block, properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, context, tooltip, flag);
		CBCMSTooltip.appendExplosiveDualCannonProjectileInfo(stack, context, tooltip, flag,
				getMunitionEntry());
	}

}
