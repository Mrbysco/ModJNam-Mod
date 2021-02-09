package com.mrbysco.cactusmod.entities.hostile;

import com.mrbysco.cactusmod.entities.ICactusMob;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class CactusSpiderEntity extends SpiderEntity implements ICactusMob{
    private static final DataParameter<Integer> SPIDER_SIZE = EntityDataManager.createKey(CactusSpiderEntity.class, DataSerializers.VARINT);

	public CactusSpiderEntity(EntityType<? extends SpiderEntity> type, World worldIn) {
        super(type, worldIn);
	}

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MAX_HEALTH, 16.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.3F);
    }

	@Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(SPIDER_SIZE, 1);
    }
	
	@Override
    public void notifyDataManagerChange(DataParameter<?> key) {
        if (SPIDER_SIZE.equals(key)) {
            this.recalculateSize();
            this.rotationYaw = this.rotationYawHead;
            this.renderYawOffset = this.rotationYawHead;
            if (this.isInWater() && this.rand.nextInt(20) == 0) {
                this.doWaterSplashEffect();
            }
        }

        super.notifyDataManagerChange(key);
    }

    public void recalculateSize() {
        double d0 = this.getPosX();
        double d1 = this.getPosY();
        double d2 = this.getPosZ();
        super.recalculateSize();
        this.setPosition(d0, d1, d2);
    }

    public void setSpiderSize(int size, boolean resetHealth) {
        this.recenterBoundingBox();
        this.recalculateSize();
        if(isSmallSpider()) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(16.0D);
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((double)(4));
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.375D);
        }

        if (resetHealth) {
            this.setHealth(this.getMaxHealth());
        }

        this.experienceValue = size;
    }

    public int getSpiderSize() {
        return this.dataManager.get(SPIDER_SIZE);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("Size", this.getSpiderSize() - 1);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        int i = compound.getInt("Size");

        if (i < 0) {
            i = 0;
        }

        this.setSpiderSize(i + 1, false);
    }
	
	public boolean isSmallSpider() {
        return this.getSpiderSize() <= 4;
	}
	
	protected CactusSpiderEntity createInstance() {
        return new CactusSpiderEntity(null, this.world);
    }

    public EntityType<? extends CactusSpiderEntity> getType() {
        return (EntityType<? extends CactusSpiderEntity>)super.getType();
    }

    @Override
    public void remove(boolean keepData) {
        int i = this.getSpiderSize();
        if (!this.world.isRemote && i > 1 && this.getShouldBeDead() && !this.removed) {
            ITextComponent itextcomponent = this.getCustomName();
            boolean flag = this.isAIDisabled();
            float f = (float)i / 4.0F;
            int j = i / 2;
            int k = 2 + this.rand.nextInt(3);

            for(int l = 0; l < k; ++l) {
                float f1 = ((float)(l % 2) - 0.5F) * f;
                float f2 = ((float)(l / 2) - 0.5F) * f;
                CactusSpiderEntity cactusSpider = this.getType().create(this.world);
                if (this.isNoDespawnRequired()) {
                    cactusSpider.enablePersistence();
                }

                cactusSpider.setCustomName(itextcomponent);
                cactusSpider.setNoAI(flag);
                cactusSpider.setInvulnerable(this.isInvulnerable());
                cactusSpider.setSpiderSize(j, true);
                cactusSpider.setLocationAndAngles(this.getPosX() + (double)f1, this.getPosY() + 0.5D, this.getPosZ() + (double)f2, this.rand.nextFloat() * 360.0F, 0.0F);
                this.world.addEntity(cactusSpider);
            }
        }

        super.remove(keepData);
    }

    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.setSpiderSize(4, true);
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

	@Override
	protected void collideWithEntity(Entity entityIn) {
		super.collideWithEntity(entityIn);
		if(!(entityIn instanceof ICactusMob))
			entityIn.attackEntityFrom(DamageSource.CACTUS, 1.0F);
	}
}
