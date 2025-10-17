package com.cainiao1053.cbcmoreshells.cannons.dual_cannon.breeches.quick_firing_breech;

import java.util.List;

import com.cainiao1053.cbcmoreshells.cannons.dual_cannon.IDualCannonBlockEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import rbasamoyai.createbigcannons.cannons.big_cannons.BigCannonBehavior;
import rbasamoyai.createbigcannons.cannons.big_cannons.IBigCannonBlockEntity;
import rbasamoyai.createbigcannons.config.CBCConfigs;

public class DualCannonQuickfiringBreechBlockEntity extends SmartBlockEntity implements IDualCannonBlockEntity {

	private BigCannonBehavior cannonBehavior;
	private int openProgress;
	private boolean inPonder;
	private int openDirection;
	private int loadingCooldown;

	public DualCannonQuickfiringBreechBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
		behaviours.add(this.cannonBehavior = new BigCannonBehavior(this, this::canLoadBlock));
	}

	@Override
	public boolean canLoadBlock(StructureBlockInfo blockInfo) {
		return false;
	}

	@Override
	public BigCannonBehavior cannonBehavior() {
		return this.cannonBehavior;
	}

	@Override
	protected void write(CompoundTag tag, boolean clientPacket) {
		super.write(tag, clientPacket);
		tag.putBoolean("InPonder", this.inPonder);
		tag.putInt("OpenProgress", this.openProgress);
		tag.putInt("OpenDirection", this.openDirection);
		tag.putInt("LoadingCooldown", this.loadingCooldown);
	}

	@Override
	protected void read(CompoundTag tag, boolean clientPacket) {
		super.read(tag, clientPacket);
		this.inPonder = tag.getBoolean("InPonder");
		this.openProgress = tag.getInt("OpenProgress");
		this.openDirection = Mth.clamp(tag.getInt("OpenDirection"), -1, 1);
		this.loadingCooldown = Math.max(0, tag.getInt("LoadingCooldown"));
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.inPonder) { // Don't reset when ponder, can't use level due to Ponder's having levels.
			if (this.openProgress != 0) this.openProgress = 0;
			if (this.openDirection != 0) this.openDirection = 0;
		}
	}

	public void tickAnimation() {
		if (this.openDirection != 0 && !isInstantOpen()) {
			this.openProgress = Mth.clamp(this.openProgress + this.openDirection, 0, Math.max(getOpeningTime(), 1));
			if (!this.onInteractionCooldown()) this.openDirection = 0;
		}
		if (this.loadingCooldown > 0) --this.loadingCooldown;
	}

	public boolean isOpen() {
		return isInstantOpen() ? this.openProgress > 0 : this.openProgress >= getOpeningTime();
	}

	public int getOpenDirection() {
		return this.openDirection;
	}

	public void toggleOpening() {
		if (isInstantOpen()) {
			this.openProgress = this.openProgress > 0 ? 0 : 1;
			return;
		}
		if (!this.onInteractionCooldown()) {
			this.openDirection = this.isOpen() ? -1 : 1;
		}
	}

	public float getOpenProgress(float partialTicks) {
		if (isInstantOpen()) {
			return Mth.clamp(this.openProgress, 0.0f, 1.0f);
		}
		return Mth.clamp((this.openProgress + this.openDirection * partialTicks) / getOpeningTime(), 0.0f, 1.0f);
	}

	public int getOpenProgress() {
		return this.openProgress;
	}

	public boolean onInteractionCooldown() {
		return 0 < this.openProgress && this.openProgress < getOpeningTime();
	}

	public boolean canBeAutomaticallyLoaded() {
		return this.loadingCooldown <= 0 && this.openProgress == 0;
	}

	public void setLoadingCooldown(int value) {
		this.loadingCooldown = value;
	}

	public int getOpeningTime() {
		DualCannonQuickfiringBreechBlock b =(DualCannonQuickfiringBreechBlock) getBlockState().getBlock();
		return (int) b.getCannonMaterial().properties().reloadTimeModifier()*50 + 20;
		//return CBCConfigs.SERVER.cannons.quickfiringBreechOpeningCooldown.get()*2+20;
	}

	public boolean isInstantOpen() { return getOpeningTime() <= 0; }

}
