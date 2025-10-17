package com.cainiao1053.cbcmoreshells.blocks.command_deployer;

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


public class CommandDeployerRenderer extends SmartBlockEntityRenderer<CommandDeployerBlockEntity> {
    public CommandDeployerRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    Logger LOGGER = Cbcmoreshells.LOGGER;

    @Override
    protected void renderSafe(CommandDeployerBlockEntity blockEntity, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        super.renderSafe(blockEntity, partialTicks, ms, buffer, light, overlay);
        SmartInventory inventory = blockEntity.getInventory();
        Direction facing = blockEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
        float yaw = AngleHelper.horizontalAngle(facing);

        //Vec3 position = new Vec3(0.5, 0.0625, 0.35);
        Vec3 position = new Vec3(0, -0.3125, 0.515625);
        ItemStack stack = inventory.getItem(0);
        if (stack.isEmpty()) {
            return;
        }

        ms.pushPose();
        ms.translate(0.5, 0.5, 0.5);
        ms.mulPose(Axis.YP.rotationDegrees(yaw));

        ms.translate(position.x, position.y, position.z);
        ms.mulPose(Axis.XP.rotationDegrees(-90));
        ms.mulPose(Axis.YP.rotationDegrees(180));

        //ms.scale(0.5f, 0.5f, 0.5f);
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
