package com.mrbysco.cactusmod.entities;

import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.common.Tags.Blocks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class CactusCowEntity extends CowEntity implements IForgeShearable, ICactusMob{

	public CactusCowEntity(EntityType<? extends CowEntity> type, World worldIn) {
        super(type, worldIn);
	}

    public CactusCowEntity getBreedOffspring(ServerWorld worldIn, AgeableEntity p_241840_2_) {
        return CactusRegistry.CACTUS_COW.get().create(worldIn);
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, (double)0.2F);
    }

    @Override
    public boolean isShearable(@Nonnull ItemStack item, World world, BlockPos pos) {
        return isShearable();
    }

    public boolean isShearable() {
        return this.isAlive() && !this.isBaby();
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nullable PlayerEntity player, @Nonnull ItemStack item, World world, BlockPos pos, int fortune) {
        world.playSound(null, this, SoundEvents.MOOSHROOM_SHEAR, player == null ? SoundCategory.BLOCKS : SoundCategory.PLAYERS, 1.0F, 1.0F);
        if (!world.isClientSide()) {
            ((ServerWorld)this.level).sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY(0.5D), this.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            this.remove();
            CowEntity cowentity = EntityType.COW.create(this.level);
            cowentity.moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, this.xRot);
            cowentity.setHealth(this.getHealth());
            cowentity.yBodyRot = this.yBodyRot;
            if (this.hasCustomName()) {
                cowentity.setCustomName(this.getCustomName());
                cowentity.setCustomNameVisible(this.isCustomNameVisible());
            }

            if (this.isPersistenceRequired()) {
                cowentity.setPersistenceRequired();
            }

            cowentity.setInvulnerable(this.isInvulnerable());
            this.level.addFreshEntity(cowentity);

            java.util.List<ItemStack> items = new java.util.ArrayList<>();
            for (int i = 0; i < 5; ++i) {
                items.add(Items.CACTUS.getDefaultInstance());
            }

            return items;
        }
        return java.util.Collections.emptyList();
    }

    @Override
    public ActionResultType mobInteract(PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (itemstack.getItem() == Items.GLASS_BOTTLE && !this.isBaby()) {
            playerIn.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
            ItemStack itemstack1 = DrinkHelper.createFilledResult(itemstack, playerIn, CactusRegistry.CACTUS_JUICE.get().getDefaultInstance());
            playerIn.setItemInHand(handIn, itemstack1);
            return ActionResultType.sidedSuccess(this.level.isClientSide);
        } else {
            return super.mobInteract(playerIn, handIn);
        }
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.isBaby() ? sizeIn.height * 0.95F : 1.3F;
    }

    public static boolean canAnimalSpawn(EntityType<? extends AnimalEntity> animal, IWorld worldIn, SpawnReason reason, BlockPos pos, Random random) {
        return worldIn.getBlockState(pos.below()).is(Blocks.SAND) && worldIn.getRawBrightness(pos, 0) > 8;
    }
}
