package com.cainiao1053.cbcmoreshells.munitions.racked_projectile.he_bouncing_bomb;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


import com.cainiao1053.cbcmoreshells.index.CBCMSMunitionPropertiesHandlers;
import com.cainiao1053.cbcmoreshells.munitions.racked_projectile.FuzedRackedProjectileBlockItem;
import com.cainiao1053.cbcmoreshells.munitions.racked_projectile.config.RackedProjectileProperties;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import com.cainiao1053.cbcmoreshells.base.CBCMSTooltip;

import static com.cainiao1053.cbcmoreshells.CBCMSEntityTypes.APHE_BOMB;
import static com.cainiao1053.cbcmoreshells.CBCMSEntityTypes.HE_BOUNCING_BOMB;


public class HEBouncingBombBlockItem extends FuzedRackedProjectileBlockItem {

	public HEBouncingBombBlockItem(Block block, Properties properties) {
		super(block, properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, level, tooltip, flag);
		RackedProjectileProperties properties = CBCMSMunitionPropertiesHandlers.RACKED_PROJECTILE.getPropertiesOf(HE_BOUNCING_BOMB.get());
		CBCMSTooltip.appendBombInfo(stack, level, tooltip, flag, properties.ballistics().durabilityMass(), properties.ballistics().penetration(), properties.ballistics().deflection(), properties.explosion().explosivePower(),properties.lifetime());
	}

}
