package com.mrbysco.cactusmod.entities;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.entities.AI.EatSandGoal;
import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class CactusSheepEntity extends AnimalEntity implements IForgeShearable, ICactusMob {
    private static final DataParameter<Boolean> SHEARED = EntityDataManager.<Boolean>defineId(CactusSheepEntity.class, DataSerializers.BOOLEAN);
    
    /**
     * Used to control movement as well as wool regrowth. Set to 40 on handleHealthUpdate and counts down with each
     * tick.
     */
    private int sheepTimer;
    private EatSandGoal eatSandGoal;

    public CactusSheepEntity(EntityType<? extends CactusSheepEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.eatSandGoal = new EatSandGoal(this);
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, Ingredient.of(Items.WHEAT), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, this.eatSandGoal);
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }
    
    @Override
    protected void customServerAiStep()
    {
        this.sheepTimer = this.eatSandGoal.getEatingSandTimer();
        super.customServerAiStep();
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void aiStep() {
        if (this.level.isClientSide) {
            this.sheepTimer = Math.max(0, this.sheepTimer - 1);
        }

        super.aiStep();
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.MOVEMENT_SPEED, (double)0.23F);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHEARED, false);
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == 10) {
            this.sheepTimer = 40;
        } else {
            super.handleEntityEvent(id);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public float getHeadRotationPointY(float p_70894_1_) {
        if (this.sheepTimer <= 0) {
            return 0.0F;
        } else if (this.sheepTimer >= 4 && this.sheepTimer <= 36) {
            return 1.0F;
        } else {
            return this.sheepTimer < 4 ? ((float)this.sheepTimer - p_70894_1_) / 4.0F : -((float)(this.sheepTimer - 40) - p_70894_1_) / 4.0F;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public float getHeadRotationAngleX(float p_70890_1_) {
        if (this.sheepTimer > 4 && this.sheepTimer <= 36) {
            float f = ((float)(this.sheepTimer - 4) - p_70890_1_) / 32.0F;
            return ((float)Math.PI / 5F) + 0.21991149F * MathHelper.sin(f * 28.7F);
        } else {
            return this.sheepTimer > 0 ? ((float)Math.PI / 5F) : this.xRot * ((float)Math.PI / 180F);
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
      super.addAdditionalSaveData(compound);
      compound.putBoolean("Sheared", this.getSheared());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.setSheared(compound.getBoolean("Sheared"));
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.SHEEP_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.SHEEP_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.SHEEP_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.SHEEP_STEP, 0.15F, 1.0F);
    }

    public boolean isShearable() {
        return this.isAlive() && !this.getSheared() && !this.isBaby();
    }

    /**
     * returns true if a sheeps wool has been sheared
     */
    public boolean getSheared() {
        return this.entityData.get(SHEARED);
    }

    public void setSheared(boolean sheared) {
        this.entityData.set(SHEARED, sheared);
    }

    @Override
    public CactusSheepEntity getBreedOffspring(ServerWorld worldIn, AgeableEntity p_241840_2_) {
        return CactusRegistry.CACTUS_SHEEP.get().create(worldIn);
    }

    /**
     * This function applies the benefits of growing back wool and faster growing up to the acting entity. (This
     * function is used in the AIEatGrass)
     */
    public void eatSandBonus() {
        this.setSheared(false);
        if (this.isBaby()) {
            this.ageUp(60);
        }
    }

    @Override
    public boolean isShearable(@Nonnull ItemStack item, World world, BlockPos pos) {
        return isShearable();
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nullable PlayerEntity player, @Nonnull ItemStack item, World world, BlockPos pos, int fortune) {
        world.playSound(null, this, SoundEvents.SHEEP_SHEAR, player == null ? SoundCategory.BLOCKS : SoundCategory.PLAYERS, 1.0F, 1.0F);
        if (!world.isClientSide) {
            this.setSheared(true);
            int i = 1 + this.random.nextInt(3);

            java.util.List<ItemStack> items = new java.util.ArrayList<>();
            for (int j = 0; j < i; ++j) {
                items.add(new ItemStack(Blocks.CACTUS, 1));
            }
            return items;
        }
        return java.util.Collections.emptyList();
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 0.95F * sizeIn.height;
    }
    
    @Override
	@Nullable
    protected ResourceLocation getDefaultLootTable() {
    	if(this.getSheared())
    		return new ResourceLocation(Reference.PREFIX + "entities/cactus_sheep");
    	else
    		return new ResourceLocation(Reference.PREFIX + "entities/cactus_sheep1");
    }
    
    @Override
	protected void doPush(Entity entityIn) {
    	if(!this.getSheared() && !(entityIn instanceof ICactusMob))
    		entityIn.hurt(DamageSource.CACTUS, 1.0F);
		
		super.doPush(entityIn);
	}

    public static boolean canAnimalSpawn(EntityType<? extends AnimalEntity> animal, IWorld worldIn, SpawnReason reason, BlockPos pos, Random random) {
        return worldIn.getBlockState(pos.below()).is(Tags.Blocks.SAND) && worldIn.getRawBrightness(pos, 0) > 8;
    }
}