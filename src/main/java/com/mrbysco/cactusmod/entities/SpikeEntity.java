package com.mrbysco.cactusmod.entities;

import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;

public class SpikeEntity extends AbstractSpikeEntity {
	public SpikeEntity(EntityType<? extends SpikeEntity> type, Level world) {
        super(type, world);
	}
	
	public SpikeEntity(Level worldIn, double x, double y, double z) {
        this(CactusRegistry.CACTUS_SPIKE.get(), worldIn);
        this.setPos(x, y, z);
    }
	
	public SpikeEntity(Level worldIn, LivingEntity shooter) {
        this(worldIn, shooter.getX(), shooter.getY() + (double)shooter.getEyeHeight() - 0.10000000149011612D, shooter.getZ());
        this.setOwner(shooter);
    }

    public SpikeEntity(SpawnEntity spawnEntity, Level worldIn) {
        this(CactusRegistry.CACTUS_SPIKE.get(), worldIn);
    }

    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level.isClientSide) {
            this.level.broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
