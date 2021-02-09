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
        return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    public void explode() {
        if (!this.world.isRemote) {
            float f = 4.0F;
            ExplosionHelper.arrowExplosion(this, this.getPosX(), this.getPosY() + (double)(this.getHeight() / 16.0F), this.getPosZ(), f, false);
            this.remove();
        }
    }
	
	@Override
	public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putByte("ExplosionRadius", (byte)this.explosionRadius);
	}
	
	@Override
	public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);

        if (compound.contains("ExplosionRadius", 99)) {
            this.explosionRadius = compound.getByte("ExplosionRadius");
        }
	}
}
