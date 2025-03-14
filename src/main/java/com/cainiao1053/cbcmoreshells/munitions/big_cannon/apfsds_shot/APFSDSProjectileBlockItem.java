package com.cainiao1053.cbcmoreshells.munitions.big_cannon.apfsds_shot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.cainiao1053.cbcmoreshells.Cbcmoreshells;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.utility.Components;
import com.simibubi.create.foundation.utility.Lang;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import rbasamoyai.createbigcannons.munitions.FuzedProjectileBlockItem;
import rbasamoyai.createbigcannons.munitions.big_cannon.ProjectileBlockItem;
import rbasamoyai.createbigcannons.munitions.fuzes.FuzeItem;
import com.cainiao1053.cbcmoreshells.base.CBCMSTooltip;

import static com.cainiao1053.cbcmoreshells.base.CBCMSTooltip.addHoldShift;
import static rbasamoyai.createbigcannons.base.CBCTooltip.getPalette;

public class APFSDSProjectileBlockItem extends FuzedProjectileBlockItem {

	public APFSDSProjectileBlockItem(Block block, Properties properties) {
		super(block, properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, level, tooltip, flag);
		boolean desc = Screen.hasShiftDown();
		if (!desc) {
			addHoldShift(desc, tooltip);
			return;
		}
		String key1 = "block."+Cbcmoreshells.MODID+".apfsds_shot.tooltip.title";
		TooltipHelper.Palette palette = getPalette(level, stack);
		tooltip.add(Components.translatable(key1).withStyle(ChatFormatting.GRAY));
		String key2 = "block."+Cbcmoreshells.MODID+".apfsds_shot.tooltip.desc";
		tooltip.addAll(TooltipHelper.cutStringTextComponent(I18n.get(key2), palette.primary(), palette.highlight(), 1));

	}

}
