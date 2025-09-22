package com.cainiao1053.cbcmoreshells.blocks.ammo_rack;

import com.cainiao1053.cbcmoreshells.Cbcmoreshells;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import com.simibubi.create.foundation.item.SmartInventory;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.utility.AngleHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.slf4j.Logger;


public class AmmoRackRenderer extends SmartBlockEntityRenderer<AmmoRackBlockEntity> {
    public AmmoRackRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    Logger LOGGER = Cbcmoreshells.LOGGER;

    @Override
    protected void renderSafe(AmmoRackBlockEntity blockEntity, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        super.renderSafe(blockEntity, partialTicks, ms, buffer, light, overlay);
        SmartInventory inventory = blockEntity.getInventory();
        //ItemStack stack = inventory.getItem(0);
        Direction facing = blockEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
        float yaw = AngleHelper.horizontalAngle(facing);
        //float pitch = getClockWise(facing);
//        if(stack.isEmpty()) {
//            return;
//        }
        //Vec3 translation = new Vec3(-0.3f,0.25f,0.3f);
        //translation = rotateYaw(translation, yaw);
//        ms.pushPose();
//        ms.translate(0.5, 0.5, 0.5);
//        ms.mulPose(Axis.YP.rotationDegrees(yaw));
//
//        ms.translate(translation.x, translation.y, translation.z);
//        ms.mulPose(Axis.XP.rotationDegrees(-90));
//
//        ms.scale(0.75f, 0.75f, 0.75f);
//        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
//        int packedLight = LevelRenderer.getLightColor(blockEntity.getLevel(),
//                blockEntity.getBlockPos());
//
//        itemRenderer.renderStatic(stack,
//                ItemDisplayContext.FIXED,
//                packedLight,
//                overlay,
//                ms,
//                buffer,
//                blockEntity.getLevel(),
//                0);
//
//        ms.popPose();

        for(int i = 1; i<4; i++){
            for(int j = 1; j<3; j++){
                Vec3 position = new Vec3(0.3125f*i-0.625f,0.3125f*j-0.3125f,0.275f);
                int slot = i+3*(j-1);
                ItemStack stack = inventory.getItem(slot-1);
                if(stack.isEmpty()) {
                    continue;
                }

                ms.pushPose();
                ms.translate(0.5, 0.5, 0.5);
                ms.mulPose(Axis.YP.rotationDegrees(yaw));

                ms.translate(position.x, position.y, position.z);
                ms.mulPose(Axis.XP.rotationDegrees(-90));

                ms.scale(0.8f, 0.8f, 0.8f);
                ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
                int packedLight = LevelRenderer.getLightColor(blockEntity.getLevel(),
                        blockEntity.getBlockPos());

                itemRenderer.renderStatic(stack,
                        ItemDisplayContext.FIXED,
                        packedLight,
                        overlay,
                        ms,
                        buffer,
                        blockEntity.getLevel(),
                        0);

                ms.popPose();
            }
        }
    }

//    protected float getClockWise(Direction facing) {
//        if (facing == Direction.SOUTH || facing == Direction.NORTH) {
//            return 90;
//        }else{
//            return -90;
//        }
//    }

//    public static Vec3 rotateYaw(Vec3 v, float yawDeg) {
//        double rad = Math.toRadians(-yawDeg);
//        double cos = Math.cos(rad);
//        double sin = Math.sin(rad);
//
//        double x = v.x *  cos - v.z * sin;
//        double z = v.x *  sin + v.z * cos;
//        return new Vec3(x, v.y, z);
//    }
}
