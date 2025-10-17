package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.combat_command;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.cainiao1053.cbcmoreshells.Cbcmoreshells;
import com.cainiao1053.cbcmoreshells.cannon_control.contraption.MountedDualCannonContraption;
import com.cainiao1053.cbcmoreshells.cannons.dual_cannon.material.DualCannonMaterial;
import com.cainiao1053.cbcmoreshells.index.CBCMSDualCannonMaterials;
import com.cainiao1053.cbcmoreshells.index.CBCMSMunitionPropertiesHandlers;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.FuzedTorpedoProjectileBlockItem;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.config.SharpnelTorpedoProperties;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.config.TorpedoProperties;
import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.OrientedContraptionEntity;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.utility.Components;
import com.simibubi.create.foundation.utility.Lang;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;
import rbasamoyai.createbigcannons.munitions.FuzedProjectileBlockItem;
import rbasamoyai.createbigcannons.munitions.big_cannon.ProjectileBlockItem;
import rbasamoyai.createbigcannons.munitions.fuzes.FuzeItem;
import com.cainiao1053.cbcmoreshells.base.CBCMSTooltip;
import rbasamoyai.createbigcannons.utils.CBCUtils;

import static com.cainiao1053.cbcmoreshells.CBCMSEntityTypes.*;
import static com.cainiao1053.cbcmoreshells.base.CBCMSTooltip.addHoldShift;
import static rbasamoyai.createbigcannons.base.CBCTooltip.getPalette;

public class CombatCommandBaseItem extends Item {
	private DualCannonMaterial recordedMaterial;

	public CombatCommandBaseItem(Properties properties) {
		super(properties);
	}

	Logger LOGGER = Cbcmoreshells.LOGGER;

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		int cannonCount = 0;
		ItemStack stack = player.getItemInHand(hand);
		CompoundTag tag = stack.getOrCreateTag();
		//if(!level.isClientSide){
			if(!player.isShiftKeyDown()){
//				List<MountedDualCannonContraption> dualCannons = findCannons(level,player.position(),48);
//				this.recordedMaterial = DualCannonMaterial.fromNameOrNull(CBCUtils.location(tag.getString("Material")));
//				//LOGGER.info("item Material: " + this.recordedMaterial);
//				//this.recordedMaterial = DualCannonMaterial.fromNameOrNull(CBCUtils.location(getMaterial(player.getItemInHand(hand)).getString("Material")));
//				if(this.recordedMaterial == null){
//					this.recordedMaterial = CBCMSDualCannonMaterials.CAST_IRON;
//					tag.putString("Material", recordedMaterial.name().toString());
//					stack.setTag(tag);
//				}
//				for(MountedDualCannonContraption dualCannon : dualCannons){
//					if(dualCannon.getCannonMaterial() != recordedMaterial){
//						continue;
//					}
//					if(dualCannon.getCommandActivation()||dualCannon.getCommandCooldown()>0){
//						continue;
//					}
//					dualCannon.activateCombatCommand();
//					dualCannon.setCommandModifiers(getCommandDurabilityModifier(), getCommandReloadTimeModifier(), getCommandLifetimeModifier(), getCommandSpreadModifier());
//					cannonCount++;
//					if(!player.isCreative()){
//						stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
////                        if(stack.hasTag() && stack.getTag().getCompound("Damage").isEmpty() && stack.getTag().getInt("Damage")<=0){
////							break;
////						}
//						if(stack.isEmpty()){
//							break;
//						}
//					}
//					if(cannonCount == getMaximumUseAtOnce()){
//						break;
//					}
//				}
//				if(!level.isClientSide){
//					Component msg = Component.translatable("item.cbcmoreshells.combat_command_base.on_effect", cannonCount);
//					player.sendSystemMessage(msg);
//				}
			}else{
					MountedDualCannonContraption cannon = findNearestCannon(level,player.position(),32);
					if(cannon==null){
						player.getCooldowns().addCooldown(this, 40);
						//createMaterial(player.getItemInHand(hand));
						if(!level.isClientSide){
							Component msg = Component.translatable("item.cbcmoreshells.combat_command_base.change_material_fail");
							player.sendSystemMessage(msg);
						}
						return super.use(level, player, hand);
					}
					this.recordedMaterial = cannon.getCannonMaterial();
					tag.putString("Material", recordedMaterial.name().toString());
					stack.setTag(tag);
					if(!level.isClientSide){
						Component matName = Component.translatable(
								"block.cbcmoreshells.material." + recordedMaterial.name().getPath()
						);
						Component msg = Component.translatable("item.cbcmoreshells.combat_command_base.change_material", matName);
						player.sendSystemMessage(msg);
					}

			}
		//}
		player.getCooldowns().addCooldown(this, 50);
		return super.use(level, player, hand);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		return super.useOn(context);
	}

	public float getCommandDurabilityModifier(){
		return 1;
	}

	public float getCommandReloadTimeModifier(){
		return 1;
	}

	public float getCommandSpreadModifier(){
		return 1f;
	}

	public float getCommandLifetimeModifier(){
		return 1;
	}

	public int getMaximumUseAtOnce(){
		return 10;
	}


	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, level, tooltip, flag);
		boolean desc = Screen.hasShiftDown();
		if (!desc) {
			addHoldShift(desc, tooltip);
			return;
		}
		CBCMSTooltip.appendCombatCommandMainInfo(stack,level,tooltip,flag,getMaximumUseAtOnce());
		CBCMSTooltip.appendCombatCommandMaterialInfo(stack,level,tooltip,flag);
	}

	public static List<MountedDualCannonContraption> findCannons(Level level, Vec3 center, double radius) {
		AABB box = AABB.ofSize(center, 2 * radius, 2 * radius, 2 * radius);
		//if (level.isClientSide()) return List.of();

		Predicate<OrientedContraptionEntity> isMountedCannon = e -> {
			Contraption c = e.getContraption();
			return c instanceof MountedDualCannonContraption;
		};

		List<OrientedContraptionEntity> carriers =
				level.getEntitiesOfClass(OrientedContraptionEntity.class, box, isMountedCannon);

		return carriers.stream()
				.map(OrientedContraptionEntity::getContraption)
				.filter(c -> c instanceof MountedDualCannonContraption)
				.map(c -> (MountedDualCannonContraption) c)
				.distinct()
				.toList();
	}

	public static MountedDualCannonContraption findNearestCannon(Level level, Vec3 center, double radius) {
		AABB box = AABB.ofSize(center, 2 * radius, 2 * radius, 2 * radius);

		//if (level.isClientSide()) return null;

		List<OrientedContraptionEntity> carriers =
				level.getEntitiesOfClass(OrientedContraptionEntity.class, box,
						e -> e.getContraption() instanceof MountedDualCannonContraption);

		OrientedContraptionEntity nearest = carriers.stream()
				.min(Comparator.comparingDouble(e -> e.distanceToSqr(center)))
				.orElse(null);

		if (nearest == null) return null;

		Contraption c = nearest.getContraption();
		return (c instanceof MountedDualCannonContraption m) ? m : null;
	}

	public CompoundTag getMaterial(ItemStack stack) {
		CompoundTag tag = stack.getOrCreateTag();
		return tag.getCompound("Material").isEmpty()
				? createMaterial(stack)
				: tag.getCompound("Material");
	}

	private CompoundTag createMaterial(ItemStack stack) {
		CompoundTag tag = stack.getOrCreateTag();
		tag.putString("Material", this.recordedMaterial == null ? CBCMSDualCannonMaterials.CAST_IRON.name().toString() : this.recordedMaterial.name().toString());
		//tag.put("Material", root);
		return tag;
	}

}
