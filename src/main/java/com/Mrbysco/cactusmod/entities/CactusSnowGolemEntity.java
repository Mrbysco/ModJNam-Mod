package com.mrbysco.cactusmod.entities;

import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IForgeShearable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class CactusSnowGolemEntity extends GolemEntity implements IRangedAttackMob, IForgeShearable, ICactusMob{
    private static final DataParameter<Byte> CACTUS_EQUIPPED = EntityDataManager.<Byte>defineId(CactusSnowGolemEntity.class, DataSerializers.BYTE);

	public CactusSnowGolemEntity(EntityType<? extends GolemEntity> type, World world) {
        super(type, world);
	}

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.25D, 20, 10.0F));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 1.0D, 1.0000001E-5F));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, MobEntity.class, 10, true, false, (p_213621_0_) -> {
            return p_213621_0_ instanceof IMob;
        }));
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 4.0D).add(Attributes.MOVEMENT_SPEED, (double)0.20000000298023224D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CACTUS_EQUIPPED, (byte)16);
    }

    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Cactus", this.isCactusEquipped());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Pumpkin")) {
            this.setCactusEquipped(compound.getBoolean("Cactus"));
        }
    }

    public boolean isSensitiveToWater() {
        return true;
    }

    public void aiStep() {
        super.aiStep();
        if (!this.level.isClientSide) {
            int i = MathHelper.floor(this.getX());
            int j = MathHelper.floor(this.getY());
            int k = MathHelper.floor(this.getZ());
            if (this.level.getBiome(new BlockPos(i, 0, k)).getTemperature(new BlockPos(i, j, k)) > 1.0F) {
                this.hurt(DamageSource.ON_FIRE, 1.0F);
            }

            if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this)) {
                return;
            }

            BlockState blockstate = Blocks.SNOW.defaultBlockState();

            for(int l = 0; l < 4; ++l) {
                i = MathHelper.floor(this.getX() + (double)((float)(l % 2 * 2 - 1) * 0.25F));
                j = MathHelper.floor(this.getY());
                k = MathHelper.floor(this.getZ() + (double)((float)(l / 2 % 2 * 2 - 1) * 0.25F));
                BlockPos blockpos = new BlockPos(i, j, k);
                if (this.level.isEmptyBlock(blockpos) && this.level.getBiome(blockpos).getTemperature(blockpos) < 0.8F && blockstate.canSurvive(this.level, blockpos)) {
                    this.level.setBlockAndUpdate(blockpos, blockstate);
                }
            }
        }

    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        AbstractSpikeEntity spike = CactusRegistry.CACTUS_SPIKE.get().create(level);
        if(spike != null) {
            double d0 = target.getEyeY() - (double)1.1F;
            double d1 = target.getX() - this.getX();
            double d2 = d0 - spike.getY();
            double d3 = target.getZ() - this.getZ();
            float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
            spike.shoot(d1, d2 + (double)f, d3, 1.6F, 12.0F);
            this.playSound(SoundEvents.SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
            this.level.addFreshEntity(spike);
        }
    }
    
    public boolean isShearable() {
        return this.isAlive() && this.isCactusEquipped();
    }
    
    public boolean isCactusEquipped() {
        return (this.entityData.get(CACTUS_EQUIPPED) & 16) != 0;
    }

    public void setCactusEquipped(boolean cactusEquipped) {
        byte b0 = this.entityData.get(CACTUS_EQUIPPED);
        if (cactusEquipped) {
            this.entityData.set(CACTUS_EQUIPPED, (byte)(b0 | 16));
        } else {
            this.entityData.set(CACTUS_EQUIPPED, (byte)(b0 & -17));
        }
    }

    @Override
    public boolean isShearable(@Nonnull ItemStack item, World world, BlockPos pos) {
        return this.isShearable();
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nullable PlayerEntity player, @Nonnull ItemStack item, World world, BlockPos pos, int fortune) {
        world.playSound(null, this, SoundEvents.SNOW_GOLEM_SHEAR, player == null ? SoundCategory.BLOCKS : SoundCategory.PLAYERS, 1.0F, 1.0F);
        if (!world.isClientSide()) {
            setCactusEquipped(false);
            return java.util.Collections.singletonList(new ItemStack(CactusRegistry.CARVED_CACTUS.get()));
        }
        return java.util.Collections.emptyList();
    }

    @OnlyIn(Dist.CLIENT)
    public Vector3d getLeashOffset() {
        return new Vector3d(0.0D, (double)(0.75F * this.getEyeHeight()), (double)(this.getBbWidth() * 0.4F));
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 1.7F;
    }

    @Nullable
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SNOW_GOLEM_AMBIENT;
    }

    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.SNOW_GOLEM_HURT;
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.SNOW_GOLEM_DEATH;
    }
}
