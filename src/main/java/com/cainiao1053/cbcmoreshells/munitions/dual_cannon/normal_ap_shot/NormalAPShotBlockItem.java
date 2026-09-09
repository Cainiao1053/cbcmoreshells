package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.normal_ap_shot;

import com.cainiao1053.cbcmoreshells.base.CBCMSTooltip;
import com.cainiao1053.cbcmoreshells.munitions.dual_cannon.FuzedDualCannonProjectileBlockItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;


public class NormalAPShotBlockItem extends FuzedDualCannonProjectileBlockItem {

	public NormalAPShotBlockItem(Block block, Properties properties) {
		super(block, properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, context, tooltip, flag);
//		boolean desc = Screen.hasShiftDown();
//		if (!desc) {
//			addHoldShift(desc, tooltip);
//			return;
//		}
//		String key1 = "block."+Cbcmoreshells.MODID+".dual_cannon_projectile.tooltip.title";
//		TooltipHelper.Palette palette = getPalette(level, stack);
//		tooltip.add(Components.translatable(key1).withStyle(ChatFormatting.GRAY));
//		String key2 = "block."+Cbcmoreshells.MODID+".dual_cannon_projectile.tooltip.desc";
//		tooltip.addAll(TooltipHelper.cutStringTextComponent(I18n.get(key2), palette.primary(), palette.highlight(), 1));

		CBCMSTooltip.appendInertDualCannonProjectileInfo(stack, context, tooltip, flag,
				getMunitionEntry());
	}

}
