package com.cainiao1053.cbcmoreshells.network;

import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.Vec3;
import rbasamoyai.createbigcannons.effects.particles.smoke.TrailSmokeParticleData;
import rbasamoyai.createbigcannons.effects.particles.splashes.ProjectileSplashParticleData;

public class CBCMSClientHandlers {

	public static void addTrailFromServer(ClientboundCBCMSTrailPacket pkt) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.level == null || mc.player == null)
			return;

		ParticleOptions trail = new TrailSmokeParticleData(320);
		for (int i = 0; i < 10; ++i) {
			double partial = i * 0.1f;
			double dx = Mth.lerp(partial, pkt.xOld(), pkt.x());
			double dy = Mth.lerp(partial, pkt.yOld(), pkt.y());
			double dz = Mth.lerp(partial, pkt.zOld(), pkt.z());
			double sx = mc.level.random.nextDouble() * 0.004d - 0.002d;
			double sy = mc.level.random.nextDouble() * 0.004d - 0.002d;
			double sz = mc.level.random.nextDouble() * 0.004d - 0.002d;
			mc.level.addAlwaysVisibleParticle(trail, true, dx, dy, dz, sx, sy, sz);
		}
	}

	public static void addSplashFromServer(ClientboundCBCMSSplashPacket pkt) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.level == null || mc.player == null)
			return;
		double dx = -(pkt.x()-pkt.xOld())*0.03;
		double dy = -(pkt.y()-pkt.yOld())*0.05;
		double dz = -(pkt.z()-pkt.zOld())*0.03;
		ParticleOptions splash = new ProjectileSplashParticleData(0.92f,0.97f,1f,0);
		mc.level.addAlwaysVisibleParticle(splash, true, pkt.x(), pkt.y(), pkt.z(), dx, dy, dz);
	}


}
