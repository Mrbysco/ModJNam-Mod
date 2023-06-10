package com.mrbysco.cactusmod.blocks;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class CactusSlimeBlock extends HalfTransparentBlock {

	public CactusSlimeBlock(BlockBehaviour.Properties properties) {
		super(properties.friction(0.5F));
	}

	/**
	 * Block's chance to react to a living entity falling on it.
	 */
	@Override
	public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
		if (entity.isShiftKeyDown()) {
			super.fallOn(level, state, pos, entity, fallDistance * 0.5F);
		} else {
			entity.causeFallDamage(fallDistance, 0.0F, entity.damageSources().cactus());
		}
	}

	@Override
	public void updateEntityAfterFallOn(BlockGetter level, Entity entityIn) {
		if (entityIn.isSuppressingBounce()) {
			super.updateEntityAfterFallOn(level, entityIn);
		} else {
			this.bounceEntity(entityIn);
		}
	}

	private void bounceEntity(Entity entity) {
		if (entity.level().random.nextInt(40) < 1)
			entity.hurt(entity.damageSources().cactus(), 1.0F);

		Vec3 vector3d = entity.getDeltaMovement();
		if (vector3d.y < 0.0D) {
			double d0 = entity instanceof LivingEntity ? 1.0D : 0.8D;
			entity.setDeltaMovement(vector3d.x, -vector3d.y * d0, vector3d.z);
		}
	}

	/**
	 * Called when the given entity walks on this Block
	 */
	@Override
	public void stepOn(Level level, BlockPos pos, BlockState state, Entity entityIn) {
		double d0 = Math.abs(entityIn.getDeltaMovement().y);
		if (d0 < 0.1D && !entityIn.isSteppingCarefully()) {
			double d1 = 0.4D + d0 * 0.2D;
			entityIn.setDeltaMovement(entityIn.getDeltaMovement().multiply(d1, 1.0D, d1));
		}

		super.stepOn(level, pos, state, entityIn);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, level, tooltip, flagIn);
		tooltip.add(Component.translatable("cactus.slimeblock.info").withStyle(ChatFormatting.GREEN));
	}
}
