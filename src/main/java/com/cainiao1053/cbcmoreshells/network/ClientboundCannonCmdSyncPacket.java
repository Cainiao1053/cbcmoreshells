package com.cainiao1053.cbcmoreshells.network;

import com.cainiao1053.cbcmoreshells.cannon_control.contraption.MountedDualCannonContraption;
import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.OrientedContraptionEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;
import rbasamoyai.createbigcannons.network.RootPacket;

import java.util.concurrent.Executor;
import java.util.function.Consumer;

public class ClientboundCannonCmdSyncPacket implements RootPacket {

	private final int entityId;
	private final boolean effect;
	private final int cooldown;
	private final int left;

	public ClientboundCannonCmdSyncPacket(int entityId, boolean effect, int cooldown, int left) {
		this.entityId = entityId;
		this.effect = effect;
		this.cooldown = cooldown;
		this.left = left;
	}

	public ClientboundCannonCmdSyncPacket(FriendlyByteBuf buf) {
		this.entityId = buf.readVarInt();
		this.effect   = buf.readBoolean();
		this.cooldown = buf.readVarInt();
		this.left     = buf.readVarInt();
	}

	@Override
	public void rootEncode(FriendlyByteBuf buf) {
		buf.writeVarInt(entityId);
		buf.writeBoolean(effect);
		buf.writeVarInt(cooldown);
		buf.writeVarInt(left);
	}

	@Override
	public void handle(Executor executor, PacketListener packetListener, @Nullable ServerPlayer serverPlayer) {
		// 注意：这是 S2C 包，serverPlayer 在客户端恒为 null
		executor.execute(() -> {
			Minecraft mc = Minecraft.getInstance();
			if (mc.level == null) return;

			Entity e = mc.level.getEntity(this.entityId);
			if (!(e instanceof PitchOrientedContraptionEntity poce)) return;
			Contraption c = poce.getContraption();
			if (!(c instanceof MountedDualCannonContraption dual)) return;

			// 应用同步字段到客户端镜像
			dual.commandEffect   = this.effect;
			dual.commandCooldown = this.cooldown;
			dual.commandLeft     = this.left;

			// 如有渲染/GUI依赖这些字段，这里触发你的刷新逻辑（可选）
			// 例如：poce.setChanged(); 或发事件等
		});
	}


}
