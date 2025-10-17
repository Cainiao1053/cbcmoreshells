package com.cainiao1053.cbcmoreshells.blocks.command_deployer;


import com.cainiao1053.cbcmoreshells.Cbcmoreshells;
import com.cainiao1053.cbcmoreshells.CbcmoreshellsClient;
import com.cainiao1053.cbcmoreshells.api.vs.ValkyrienSkies;
import com.cainiao1053.cbcmoreshells.cannon_control.contraption.MountedDualCannonContraption;
import com.cainiao1053.cbcmoreshells.cannons.dual_cannon.material.DualCannonMaterial;
import com.cainiao1053.cbcmoreshells.index.CBCMSDualCannonMaterials;
import com.cainiao1053.cbcmoreshells.munitions.dual_cannon.combat_command.CombatCommandBaseItem;
import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.OrientedContraptionEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.item.SmartInventory;
import com.simibubi.create.foundation.utility.Color;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.joml.Vector3f;
import org.joml.primitives.AABBdc;
import org.slf4j.Logger;
import org.valkyrienskies.core.api.ships.Ship;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;
import rbasamoyai.createbigcannons.utils.CBCUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


public class CommandDeployerBlockEntity extends SmartBlockEntity {


    public CommandDeployerBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    public final SmartInventory inventory = new SmartInventory(1, this);

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> list) {
    }

    Logger LOGGER = Cbcmoreshells.LOGGER;

    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide) {
            //tickClient();
        }
    }



    public void activateCannons() {
        if(level.isClientSide()) {
            return;
        }
        ItemStack stack = inventory.getItem(0);
        if(stack.isEmpty() || !(stack.getItem() instanceof CombatCommandBaseItem)) {
            return;
        }
        AABB box;
        Vec3 posV3;
        Ship ship = getShipOn();
        if (ship == null) {
            BlockPos pos = getBlockPos();
            posV3 = new Vec3(pos.getX(), pos.getY()+1, pos.getZ());
            box = AABB.ofSize(posV3, 32, 16, 32);
        } else {

            box = toAABB(ship.getWorldAABB());
            Vector3dc posV3dc = ship.getTransform().getPositionInWorld();
            posV3 = new Vec3(posV3dc.x(), posV3dc.y()+box.getYsize()/2, posV3dc.z());
        }
        CombatCommandBaseItem ccb = (CombatCommandBaseItem) stack.getItem();
        int cannonCount = 0;
        int activatedCannonCount = 0;
        CompoundTag tag = stack.getOrCreateTag();
        DualCannonMaterial recordedMaterial = DualCannonMaterial.fromNameOrNull(CBCUtils.location(tag.getString("Material")));
        if(recordedMaterial == null){
            recordedMaterial = CBCMSDualCannonMaterials.CAST_IRON;
        }
        List<PitchOrientedContraptionEntity> poce = findPitchOrientedEntities(level, box);
        List<MountedDualCannonContraption> availableDualCannon = new ArrayList<>();
        for (PitchOrientedContraptionEntity cannon : poce) {
            //MountedDualCannonContraption.CannonPose cannonPose = MountedDualCannonContraption.getCannonPoseWorld(cannon);
            MountedDualCannonContraption dualCannon = (MountedDualCannonContraption) cannon.getContraption();
            if (dualCannon.getCommandActivation() || dualCannon.getCommandCooldown()>0) {
                activatedCannonCount++;
                continue;
            }
            if(dualCannon.getCannonMaterial() != recordedMaterial){
                continue;
            }
            availableDualCannon.add(dualCannon);
            cannonCount++;
        }
        int maxUse = ccb.getMaximumUseAtOnce();
        if(cannonCount > 0 && activatedCannonCount < maxUse){
            int activateCannon = 0;
            int availableCannon = maxUse - activatedCannonCount;
            for(MountedDualCannonContraption dualCannon : availableDualCannon){
                activateCannon++;
                if(activateCannon > availableCannon){
                    activateCannon--;
                    break;
                }
                dualCannon.activateCombatCommand();
                dualCannon.setCommandModifiers(ccb.getCommandDurabilityModifier(), ccb.getCommandReloadTimeModifier(), ccb.getCommandLifetimeModifier(), ccb.getCommandSpreadModifier());
                boolean broke = stack.hurt(1, ((ServerLevel) level).random, null);
                if(broke){
                    stack.shrink(1);
                    break;
                }
            }
            setChanged();
            for(Player player : level.players()){
                if(box.contains(new Vec3(player.getX(), player.getY(), player.getZ()))) {
                    Component msg = Component.translatable("item.cbcmoreshells.combat_command_base.on_effect", activateCannon);
					player.sendSystemMessage(msg);
                }
            }
            ItemStack rocketStack = new ItemStack(Items.FIREWORK_ROCKET);

            CompoundTag fireworks = new CompoundTag();
            fireworks.putByte("Flight", (byte) 1);

            ListTag explosions = new ListTag();
            CompoundTag boom = new CompoundTag();
            boom.putByte("Type", (byte) 1);
            boom.putIntArray("Colors", new int[]{0xFBBA1E});
            boom.putIntArray("FadeColors", new int[]{0xEC8E0D});
            boom.putBoolean("Trail", true);
            boom.putBoolean("Flicker", true);
            explosions.add(boom);
            fireworks.put("Explosions", explosions);

            CompoundTag stackTag = rocketStack.getOrCreateTag();
            stackTag.put("Fireworks", fireworks);

            FireworkRocketEntity fw = new FireworkRocketEntity(level, posV3.x, posV3.y, posV3.z, rocketStack);
            fw.setDeltaMovement(0, 0.55, 0);
            level.addFreshEntity(fw);
//            level.playSound(
//                    null,
//                    posV3.x(),
//                    posV3.y(),
//                    posV3.z(),
//                    SoundEvents.FIREWORK_ROCKET_SHOOT,
//                    SoundSource.BLOCKS,
//                    4.5f,
//                    1.0f
//            );

        }
    }

    public ItemStack insertStack(ItemStack stack, int slot, boolean simulate) {
        stack = inventory.insertItem(slot, stack, simulate);
        if (stack.getCount() < stack.getMaxStackSize())
            setChangedAndSync();
        return stack;
    }

    private void setChangedAndSync() {
        this.setChanged();
        if (level instanceof ServerLevel sl)
            sl.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
    }

    public ItemStack extractStack(int slot) {
        ItemStack stack = ItemStack.EMPTY;
        stack = inventory.extractItem(slot, 1, false);
        return stack;
    }

    public static List<MountedDualCannonContraption> findCannons(Level level, AABB box) {
        //AABB box = AABB.ofSize(center, 2 * radius, 2 * radius, 2 * radius);
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

    public static List<PitchOrientedContraptionEntity> findPitchOrientedEntities(Level level, AABB box) {
        //if (level.isClientSide()) return List.of();

        return level.getEntitiesOfClass(
                PitchOrientedContraptionEntity.class,
                box,
                e -> e.getContraption() instanceof MountedDualCannonContraption
        );
    }

    public static AABB toAABB(AABBdc i) {
        return new AABB(
                i.minX(), i.minY(), i.minZ(),
                i.maxX(), i.maxY(), i.maxZ()
        );
    }

    public SmartInventory getInventory() {
        return inventory;
    }

    @Override
    public void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        compound.put("Inventory", inventory.serializeNBT());
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        inventory.deserializeNBT(compound.getCompound("Inventory"));
    }

    public Vector3d getPositionShip() {
        Vec3 front = getBlockPos().getCenter();
        return new Vector3d(front.x, front.y, front.z);
    }

    public @Nullable Ship getShipOn() {
        return ValkyrienSkies.getShipManagingBlock(level, getBlockPos());
    }

}
