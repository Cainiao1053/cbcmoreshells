package com.cainiao1053.cbcmoreshells.mixin;

import com.cainiao1053.cbcmoreshells.cannon_control.contraption.MountedTorpedoTubeContraption;
import com.cainiao1053.cbcmoreshells.network.CBCMSNetworkImpl;
import com.cainiao1053.cbcmoreshells.network.ClientboundCBCMSSplashPacket;
import com.cainiao1053.cbcmoreshells.network.ClientboundCBCMSTrailPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rbasamoyai.createbigcannons.cannon_control.contraption.AbstractMountedCannonContraption;
import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;
import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.breeches.quick_firing_breech.TorpedoCannonMountPoint;
import rbasamoyai.createbigcannons.munitions.big_cannon.AbstractBigCannonProjectile;

import static com.simibubi.create.infrastructure.ponder.scenes.fluid.HosePulleyScenes.level;

@Mixin(
		value = {AbstractBigCannonProjectile.class},
		remap = false
)
public class AbstractBigCannonProjectileMixin {
	public AbstractBigCannonProjectileMixin() {
	}
	@Unique
	private int sendTrail = 20;
	@Unique
	private double xOld;
	@Unique
	private double yOld;
	@Unique
	private double zOld;
	@Unique
	private int traced = 0;
	
	@Inject(
			method = {"tick"},
			at = {@At("TAIL")},
			cancellable = true,
			remap = true
	)
	public void tick(CallbackInfo ci) {
			if (sendTrail<0) {
				AbstractBigCannonProjectile self = (AbstractBigCannonProjectile)(Object) this;
//				if (!self.isInGround() && !self.isInWater()) {
//					if (!self.level().isClientSide && self.level() instanceof ServerLevel serverLevel) {
//						if(traced == 0){
//							xOld = self.getX();
//							yOld = self.getY();
//							zOld = self.getZ();
//							traced++;
//						}
//						for (ServerPlayer players : serverLevel.players()) {
//							sendTrailToClient(self.getX(), self.getY(), self.getZ(), xOld, yOld, zOld, players);
//						}
//						sendTrail = 5;
//						xOld = self.getX();
//						yOld = self.getY();
//						zOld = self.getZ();
//					}
//				}
				if(traced == 1){
					if (!self.isInGround() && !self.isInWater()) {
						if (!self.level().isClientSide && self.level() instanceof ServerLevel serverLevel) {
							for (ServerPlayer players : serverLevel.players()) {
								sendTrailToClient(self.getX(), self.getY(), self.getZ(), xOld, yOld, zOld, players);
							}
							sendTrail = 5;
							xOld = self.getX();
							yOld = self.getY();
							zOld = self.getZ();
						}
					}else{
						if(!self.level().isClientSide && self.level() instanceof ServerLevel serverLevel){
							double xLerp = Mth.lerp(0.75,self.getX(),xOld);
							double yLerp = Mth.lerp(0.75,self.getY(),yOld);
							double zLerp = Mth.lerp(0.75,self.getZ(),zOld);
							if(self.isInWater()){
								for (ServerPlayer players : serverLevel.players()) {
									sendSplashToClient(xLerp, yLerp, zLerp, xOld, yOld, zOld, players);
								}
							}else{
								for (ServerPlayer players : serverLevel.players()) {
									sendTrailToClient(xLerp, yLerp, zLerp, xOld, yOld, zOld, players);
								}
							}
						traced++;
						sendTrail = 100;
						}
					}
				}else if(traced == 0){
					xOld = self.getX();
					yOld = self.getY();
					zOld = self.getZ();
					traced++;
					sendTrail = 5;
				}else{sendTrail = 200;}
			}else{sendTrail--;}
	}

	@Unique
	public void sendTrailToClient(double x, double y ,double z,double xOld, double yOld ,double zOld,ServerPlayer player) {
		CBCMSNetworkImpl.sendToClientPlayer(new ClientboundCBCMSTrailPacket(x,y,z,xOld,yOld,zOld), player);
	}

	@Unique
	public void sendSplashToClient(double x, double y ,double z,double xOld, double yOld ,double zOld,ServerPlayer player) {
		CBCMSNetworkImpl.sendToClientPlayer(new ClientboundCBCMSSplashPacket(x,y,z,xOld,yOld,zOld), player);
	}

}