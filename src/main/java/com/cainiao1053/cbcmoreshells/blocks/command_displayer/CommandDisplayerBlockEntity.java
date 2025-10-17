package com.cainiao1053.cbcmoreshells.blocks.command_displayer;


import com.cainiao1053.cbcmoreshells.Cbcmoreshells;
import com.cainiao1053.cbcmoreshells.CbcmoreshellsClient;
import com.cainiao1053.cbcmoreshells.api.vs.ValkyrienSkies;
import com.cainiao1053.cbcmoreshells.cannon_control.contraption.MountedDualCannonContraption;
import com.cainiao1053.cbcmoreshells.cannons.dual_cannon.material.DualCannonMaterial;
import com.cainiao1053.cbcmoreshells.index.CBCMSDualCannonMaterials;
import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.OrientedContraptionEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.utility.Color;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector3d;
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
import java.util.List;
import java.util.function.Predicate;


public class CommandDisplayerBlockEntity extends SmartBlockEntity {


    public CommandDisplayerBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    Color BLUE = new Color(0, 0, 245).setImmutable().setAlpha(0.5f);
    Color GOLD = new Color(248, 189, 27).setImmutable().setAlpha(0.5f);
    Color LIGHT_BLUE = new Color(34, 177, 250).setImmutable().setAlpha(0.5f);
    Color RED = new Color(250, 0, 0).setImmutable().setAlpha(0.5f);
    Color GREEN = new Color(0, 250, 0).setImmutable().setAlpha(0.5f);
    Color YELLOW = new Color(249, 246, 44).setImmutable().setAlpha(0.5f);
    List<Color> PALETTE = List.of(BLUE, GOLD, LIGHT_BLUE, RED, GREEN, YELLOW);

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> list) {
    }

    Logger LOGGER = Cbcmoreshells.LOGGER;

    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide) {
            tickClient();
        }
    }


    @OnlyIn(Dist.CLIENT)
    public void displayCannonStatus(MountedDualCannonContraption.CannonPose pose) {
        displayCannonStatus(pose, 3.0, null, 0);
    }

    @OnlyIn(Dist.CLIENT)
    public void displayCannonStatus(MountedDualCannonContraption.CannonPose pose, double length, @Nullable String keySuffix, int colorIndex) {
        if (this.level == null || !this.level.isClientSide()) return;
        if (pose == null) return;

        Vec3 start = pose.muzzleWorld();
        Vec3 dir = pose.forwardUnit();
        if (dir == null || dir.lengthSqr() < 1e-12) return;

        Vec3 end = start.add(dir.scale(length));

        String key = "cannon_ray_" + getBlockPos().asLong();
        if (keySuffix != null && !keySuffix.isEmpty()) {
            key += "_" + keySuffix;
        }

        CbcmoreshellsClient.CLIENT_LERPED_OUTLINER
                .showLine(key, start, end)
                .colored(PALETTE.get(colorIndex))
                .lineWidth(0.25f);
    }

    @OnlyIn(Dist.CLIENT)
    protected void tickClient() {
        if (!getBlockState().getValue(BlockStateProperties.POWERED)) {
            return;
        }
        AABB box;
        if (getShipOn() == null) {
            BlockPos pos = getBlockPos();
            box = AABB.ofSize(new Vec3(pos.getX(), pos.getY(), pos.getZ()), 32, 16, 32);
        } else {
            box = toAABB(getShipOn().getWorldAABB());
        }
        searchForCannons(level, box);
    }

    public void searchForCannons(Level level, AABB box) {
        List<PitchOrientedContraptionEntity> poce = findPitchOrientedEntities(level, box);
        for (PitchOrientedContraptionEntity cannon : poce) {
            MountedDualCannonContraption.CannonPose cannonPose = MountedDualCannonContraption.getCannonPoseWorld(cannon);
            MountedDualCannonContraption dualCannon = (MountedDualCannonContraption) cannon.getContraption();
            int colorIndex = 4;
            if (dualCannon.getCommandActivation()) {
                colorIndex = 1;
            }else{
                int cooldown = dualCannon.getCommandCooldown();
                int maxCooldown = dualCannon.getCannonMaterial().properties().combatCommandCooldown();
                if (cooldown > 0) {
                    if (cooldown > maxCooldown/3) {
                        colorIndex = 3;
                    }else{
                        colorIndex = 5;
                    }
                }
            }
            if (level.isClientSide) {
                displayCannonStatus(cannonPose, 1, String.valueOf(cannon.getId()),colorIndex);
            }
        }


    }


    public static List<MountedDualCannonContraption> findCannons(Level level, AABB box) {
        //AABB box = AABB.ofSize(center, 2 * radius, 2 * radius, 2 * radius);
        if (level.isClientSide()) return List.of();

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

    @Override
    public void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
    }

    public Vector3d getPositionShip() {
        Vec3 front = getBlockPos().getCenter();
        return new Vector3d(front.x, front.y, front.z);
    }

    public @Nullable Ship getShipOn() {
        return ValkyrienSkies.getShipManagingBlock(level, getBlockPos());
    }

}
