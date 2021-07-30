package com.mrbysco.cactusmod.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.BreakableBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class CactusSlimeBlock extends BreakableBlock {
	
	public CactusSlimeBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public float getSlipperiness(BlockState state, IWorldReader world, BlockPos pos, @Nullable Entity entity) {
        return 0.5F;
    }
    
	/**
     * Block's chance to react to a living entity falling on it.
     */
	@Override
    public void fallOn(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        if (entityIn.isShiftKeyDown()) {
            super.fallOn(worldIn, pos, entityIn, fallDistance * 0.5F);
        } else {
            entityIn.causeFallDamage(fallDistance, 0.0F);
        }
    }

    @Override
    public void updateEntityAfterFallOn(IBlockReader worldIn, Entity entityIn) {
        if (entityIn.isSuppressingBounce()) {
            super.updateEntityAfterFallOn(worldIn, entityIn);
        } else {
            this.bounceEntity(entityIn);
        }
    }

    private void bounceEntity(Entity entity) {
        if(entity.level.random.nextInt(40) < 1)
            entity.hurt(DamageSource.CACTUS, 1.0F);

        Vector3d vector3d = entity.getDeltaMovement();
        if (vector3d.y < 0.0D) {
            double d0 = entity instanceof LivingEntity ? 1.0D : 0.8D;
            entity.setDeltaMovement(vector3d.x, -vector3d.y * d0, vector3d.z);
        }
    }

    /**
     * Called when the given entity walks on this Block
     */
	@Override
    public void stepOn(World worldIn, BlockPos pos, Entity entityIn) {
        double d0 = Math.abs(entityIn.getDeltaMovement().y);
        if (d0 < 0.1D && !entityIn.isSteppingCarefully()) {
            double d1 = 0.4D + d0 * 0.2D;
            entityIn.setDeltaMovement(entityIn.getDeltaMovement().multiply(d1, 1.0D, d1));
        }

        super.stepOn(worldIn, pos, entityIn);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("cactus.slimeblock.info").withStyle(TextFormatting.GREEN));
    }
}
