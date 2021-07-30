package com.mrbysco.cactusmod.entities.hostile;

import com.mrbysco.cactusmod.entities.ICactusMob;
import com.mrbysco.cactusmod.util.ExplosionHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;

public class CactusCreeperEntity extends CreeperEntity implements ICactusMob{
    private int explosionRadius = 2;

	public CactusCreeperEntity(EntityType<? extends CreeperEntity> type, World worldIn) {
        super(type, worldIn);
	}

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MonsterEntity.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    public void explodeCreeper() {
        if (!this.level.isClientSide) {
            float f = 4.0F;
            ExplosionHelper.arrowExplosion(this, this.getX(), this.getY() + (double)(this.getBbHeight() / 16.0F), this.getZ(), f, false);
            this.remove();
        }
    }
	
	@Override
	public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putByte("ExplosionRadius", (byte)this.explosionRadius);
	}
	
	@Override
	public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);

        if (compound.contains("ExplosionRadius", 99)) {
            this.explosionRadius = compound.getByte("ExplosionRadius");
        }
	}
}
