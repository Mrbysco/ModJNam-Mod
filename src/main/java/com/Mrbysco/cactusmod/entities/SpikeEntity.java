package com.mrbysco.cactusmod.entities;

import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class SpikeEntity extends AbstractSpikeEntity {
	public SpikeEntity(EntityType<? extends SpikeEntity> type, World world) {
        super(type, world);
	}
	
	public SpikeEntity(World worldIn, double x, double y, double z) {
        this(CactusRegistry.CACTUS_SPIKE.get(), worldIn);
        this.setPosition(x, y, z);
    }
	
	public SpikeEntity(World worldIn, LivingEntity shooter) {
        this(worldIn, shooter.getPosX(), shooter.getPosY() + (double)shooter.getEyeHeight() - 0.10000000149011612D, shooter.getPosZ());
        this.setShooter(shooter);
    }

    public SpikeEntity(FMLPlayMessages.SpawnEntity spawnEntity, World worldIn) {
        this(CactusRegistry.CACTUS_SPIKE.get(), worldIn);
    }

    protected void onImpact(RayTraceResult result) {
        super.onImpact(result);
        if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)3);
            this.remove();
        }
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
