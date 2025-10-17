package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.combat_command;

import com.cainiao1053.cbcmoreshells.base.CBCMSTooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RangeCombatCommandItem extends CombatCommandBaseItem {

	public RangeCombatCommandItem(Properties properties) {
		super(properties);
	}


	@Override
	public float getCommandLifetimeModifier() {
		return 1.5f;
	}

	@Override
	public float getCommandDurabilityModifier() {
		return 1.1f;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, level, tooltip, flag);
		CBCMSTooltip.appendCombatCommandRangeInfo(stack,level,tooltip,flag,getCommandLifetimeModifier());
		CBCMSTooltip.appendCombatCommandDamageInfo(stack,level,tooltip,flag,getCommandDurabilityModifier());
	}
}
