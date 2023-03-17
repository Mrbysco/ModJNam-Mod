package com.mrbysco.cactusmod.entities;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.entities.goals.EatSandGoal;
import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class CactusSheepEntity extends Animal implements IForgeShearable, ICactusMob {
	private static final EntityDataAccessor<Boolean> SHEARED = SynchedEntityData.<Boolean>defineId(CactusSheepEntity.class, EntityDataSerializers.BOOLEAN);

	/**
	 * Used to control movement as well as wool regrowth. Set to 40 on handleHealthUpdate and counts down with each
	 * tick.
	 */
	private int sheepTimer;
	private EatSandGoal eatSandGoal;

	public CactusSheepEntity(EntityType<? extends CactusSheepEntity> type, Level worldIn) {
		super(type, worldIn);
	}

	@Override
	protected void registerGoals() {
		this.eatSandGoal = new EatSandGoal(this);
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, Ingredient.of(Items.WHEAT), false));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(5, this.eatSandGoal);
		this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
	}

	@Override
	protected void customServerAiStep() {
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

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.MOVEMENT_SPEED, (double) 0.23F);
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHEARED, false);
	}

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
			return this.sheepTimer < 4 ? ((float) this.sheepTimer - p_70894_1_) / 4.0F : -((float) (this.sheepTimer - 40) - p_70894_1_) / 4.0F;
		}
	}

	@OnlyIn(Dist.CLIENT)
	public float getHeadRotationAngleX(float p_70890_1_) {
		if (this.sheepTimer > 4 && this.sheepTimer <= 36) {
			float f = ((float) (this.sheepTimer - 4) - p_70890_1_) / 32.0F;
			return ((float) Math.PI / 5F) + 0.21991149F * Mth.sin(f * 28.7F);
		} else {
			return this.sheepTimer > 0 ? ((float) Math.PI / 5F) : this.getXRot() * ((float) Math.PI / 180F);
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("Sheared", this.getSheared());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setSheared(compound.getBoolean("Sheared"));
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.SHEEP_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.SHEEP_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
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
	public CactusSheepEntity getBreedOffspring(ServerLevel level, AgeableMob ageableMob) {
		return CactusRegistry.CACTUS_SHEEP.get().create(level);
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
	public boolean isShearable(@Nonnull ItemStack item, Level world, BlockPos pos) {
		return isShearable();
	}

	@Nonnull
	@Override
	public List<ItemStack> onSheared(@Nullable Player player, @Nonnull ItemStack item, Level world, BlockPos pos, int fortune) {
		world.playSound(null, this, SoundEvents.SHEEP_SHEAR, player == null ? SoundSource.BLOCKS : SoundSource.PLAYERS, 1.0F, 1.0F);
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
	protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return 0.95F * sizeIn.height;
	}

	@Override
	@Nullable
	protected ResourceLocation getDefaultLootTable() {
		if (this.getSheared())
			return new ResourceLocation(Reference.MOD_ID, "entities/cactus_sheep");
		else
			return new ResourceLocation(Reference.MOD_ID, "entities/cactus_sheep1");
	}

	@Override
	protected void doPush(Entity entityIn) {
		if (!this.getSheared() && !(entityIn instanceof ICactusMob))
			entityIn.hurt(DamageSource.CACTUS, 1.0F);

		super.doPush(entityIn);
	}

	public static boolean canAnimalSpawn(EntityType<? extends Animal> animal, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, Random random) {
		return worldIn.getBlockState(pos.below()).is(Tags.Blocks.SAND) && worldIn.getRawBrightness(pos, 0) > 8;
	}
}