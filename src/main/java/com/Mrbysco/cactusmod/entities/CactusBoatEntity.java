package com.mrbysco.cactusmod.entities;

import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class CactusBoatEntity extends BoatEntity implements ICactusMob {
    private int timeInBoat;
    private int timeInBoat2;

    public CactusBoatEntity(EntityType<? extends BoatEntity> type, World world) {
        super(type, world);
    }

    public CactusBoatEntity(World worldIn, double x, double y, double z) {
        this(CactusRegistry.CACTUS_BOAT_ENTITY.get(), worldIn);
        this.setPosition(x, y, z);
        this.setMotion(Vector3d.ZERO);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
    }

    public CactusBoatEntity(FMLPlayMessages.SpawnEntity spawnEntity, World worldIn) {
        this(CactusRegistry.CACTUS_BOAT_ENTITY.get(), worldIn);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
    
    @Override
    public Item getItemBoat() {
    	return CactusRegistry.CACTUS_BOAT.get();
    }

    @Override
    public void tick() {
        super.tick();
        if(!this.world.isRemote) {
            if(!this.getPassengers().isEmpty() && this.getPassengers().get(0) != null) {
                ++this.timeInBoat;
            } else {
                if(this.timeInBoat > 0)
                    this.timeInBoat = 0;
            }

            if(this.getPassengers().size() == 2 && this.getPassengers().get(1) != null) {
                ++this.timeInBoat2;
            } else {
                if(this.timeInBoat2 > 0)
                    this.timeInBoat2 = 0;
            }

            if(!this.getPassengers().isEmpty() && this.getPassengers().get(0) instanceof LivingEntity && this.timeInBoat >= 120) {
                LivingEntity entity = (LivingEntity) this.getPassengers().get(0);
                if(!(entity instanceof ICactusMob))
                    entity.attackEntityFrom(DamageSource.GENERIC, 1.0F);

                this.timeInBoat = 0;
            }


            if(this.getPassengers().size() == 2 && this.getPassengers().get(1) instanceof LivingEntity && this.timeInBoat2 >= 120) {
                LivingEntity entity2 = (LivingEntity) this.getPassengers().get(1);
                if(!(entity2 instanceof ICactusMob))
                    entity2.attackEntityFrom(DamageSource.CACTUS, 1.0F);

                this.timeInBoat2 = 0;
            }
        }
    }

    @Override
    public void updatePassenger(Entity passenger) {
        super.updatePassenger(passenger);
    }
}