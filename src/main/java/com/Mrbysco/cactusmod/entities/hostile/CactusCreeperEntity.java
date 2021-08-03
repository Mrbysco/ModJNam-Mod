package com.mrbysco.cactusmod.entities.hostile;

import com.mrbysco.cactusmod.entities.ICactusMob;
import com.mrbysco.cactusmod.util.ExplosionHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class CactusCreeperEntity extends Creeper implements ICactusMob{
    private int explosionRadius = 2;

	public CactusCreeperEntity(EntityType<? extends Creeper> type, Level worldIn) {
        super(type, worldIn);
	}

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    public void explodeCreeper() {
        if (!this.level.isClientSide) {
            float f = 4.0F;
            ExplosionHelper.arrowExplosion(this, this.getX(), this.getY() + (double)(this.getBbHeight() / 16.0F), this.getZ(), f, false);
            this.discard();
        }
    }
	
	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putByte("ExplosionRadius", (byte)this.explosionRadius);
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        if (compound.contains("ExplosionRadius", 99)) {
            this.explosionRadius = compound.getByte("ExplosionRadius");
        }
	}
}
