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
    private static final DataParameter<Integer> SPIDER_SIZE = EntityDataManager.defineId(CactusSpiderEntity.class, DataSerializers.INT);

	public CactusSpiderEntity(EntityType<? extends SpiderEntity> type, World worldIn) {
        super(type, worldIn);
	}

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MonsterEntity.createMonsterAttributes().add(Attributes.MAX_HEALTH, 16.0D).add(Attributes.MOVEMENT_SPEED, (double)0.3F);
    }

	@Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SPIDER_SIZE, 1);
    }
	
	@Override
    public void onSyncedDataUpdated(DataParameter<?> key) {
        if (SPIDER_SIZE.equals(key)) {
            this.refreshDimensions();
            this.yRot = this.yHeadRot;
            this.yBodyRot = this.yHeadRot;
            if (this.isInWater() && this.random.nextInt(20) == 0) {
                this.doWaterSplashEffect();
            }
        }

        super.onSyncedDataUpdated(key);
    }

    public void refreshDimensions() {
        double d0 = this.getX();
        double d1 = this.getY();
        double d2 = this.getZ();
        super.refreshDimensions();
        this.setPos(d0, d1, d2);
    }

    public void setSpiderSize(int size, boolean resetHealth) {
        this.reapplyPosition();
        this.refreshDimensions();
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

        this.xpReward = size;
    }

    public int getSpiderSize() {
        return this.entityData.get(SPIDER_SIZE);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Size", this.getSpiderSize() - 1);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
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
        return new CactusSpiderEntity(null, this.level);
    }

    public EntityType<? extends CactusSpiderEntity> getType() {
        return (EntityType<? extends CactusSpiderEntity>)super.getType();
    }

    @Override
    public void remove(boolean keepData) {
        int i = this.getSpiderSize();
        if (!this.level.isClientSide && i > 1 && this.isDeadOrDying() && !this.removed) {
            ITextComponent itextcomponent = this.getCustomName();
            boolean flag = this.isNoAi();
            float f = (float)i / 4.0F;
            int j = i / 2;
            int k = 2 + this.random.nextInt(3);

            for(int l = 0; l < k; ++l) {
                float f1 = ((float)(l % 2) - 0.5F) * f;
                float f2 = ((float)(l / 2) - 0.5F) * f;
                CactusSpiderEntity cactusSpider = this.getType().create(this.level);
                if (this.isPersistenceRequired()) {
                    cactusSpider.setPersistenceRequired();
                }

                cactusSpider.setCustomName(itextcomponent);
                cactusSpider.setNoAi(flag);
                cactusSpider.setInvulnerable(this.isInvulnerable());
                cactusSpider.setSpiderSize(j, true);
                cactusSpider.moveTo(this.getX() + (double)f1, this.getY() + 0.5D, this.getZ() + (double)f2, this.random.nextFloat() * 360.0F, 0.0F);
                this.level.addFreshEntity(cactusSpider);
            }
        }

        super.remove(keepData);
    }

    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.setSpiderSize(4, true);
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

	@Override
	protected void doPush(Entity entityIn) {
		super.doPush(entityIn);
		if(!(entityIn instanceof ICactusMob))
			entityIn.hurt(DamageSource.CACTUS, 1.0F);
	}
}
