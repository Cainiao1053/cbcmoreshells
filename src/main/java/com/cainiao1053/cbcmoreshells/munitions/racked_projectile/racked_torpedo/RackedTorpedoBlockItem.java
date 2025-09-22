package com.cainiao1053.cbcmoreshells.munitions.racked_projectile.racked_torpedo;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


import com.cainiao1053.cbcmoreshells.index.CBCMSMunitionPropertiesHandlers;
import com.cainiao1053.cbcmoreshells.munitions.racked_projectile.FuzedRackedProjectileBlockItem;
import com.cainiao1053.cbcmoreshells.munitions.racked_projectile.config.RackedProjectileProperties;

import com.cainiao1053.cbcmoreshells.munitions.racked_projectile.config.RackedTorpedoProjectileProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import com.cainiao1053.cbcmoreshells.base.CBCMSTooltip;

import static com.cainiao1053.cbcmoreshells.CBCMSEntityTypes.*;


public class RackedTorpedoBlockItem extends FuzedRackedProjectileBlockItem {

	public RackedTorpedoBlockItem(Block block, Properties properties) {
		super(block, properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, level, tooltip, flag);
		RackedTorpedoProjectileProperties properties = CBCMSMunitionPropertiesHandlers.RACKED_TORPEDO.getPropertiesOf(RACKED_TORPEDO.get());
		CBCMSTooltip.appendRackedTorpedoInfo(stack, level, tooltip, flag, properties.explosion().explosivePower(),properties.lifetime(), properties.buoyancyFactor(), properties.steadyStateVel(), properties.waterDamp());
	}

}
