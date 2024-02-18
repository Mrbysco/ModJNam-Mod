package com.mrbysco.cactusmod.entities;

import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class SpikeEntity extends AbstractSpikeEntity {
	public SpikeEntity(EntityType<? extends SpikeEntity> type, Level level) {
		super(type, level);
	}

	public SpikeEntity(Level level, double x, double y, double z) {
		this(CactusRegistry.CACTUS_SPIKE.get(), level);
		this.setPos(x, y, z);
	}

	public SpikeEntity(Level level, LivingEntity shooter) {
		this(level, shooter.getX(), shooter.getY() + (double) shooter.getEyeHeight() - 0.10000000149011612D, shooter.getZ());
		this.setOwner(shooter);
	}

	protected void onHit(HitResult result) {
		super.onHit(result);
		if (!this.level().isClientSide) {
			this.level().broadcastEntityEvent(this, (byte) 3);
			this.discard();
		}
	}
}
