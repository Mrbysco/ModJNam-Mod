package com.mrbysco.cactusmod.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CarpetBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class PunjiCactusBlock extends CarpetBlock {

	public PunjiCactusBlock(AbstractBlock.Properties builder) {
		super(DyeColor.GREEN, builder);
	}

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        super.onEntityCollision(state, worldIn, pos, entityIn);

        if(entityIn instanceof MobEntity) {
            entityIn.attackEntityFrom(DamageSource.CACTUS, 1.0F);
        } else if (entityIn instanceof ItemEntity) {
            ItemEntity item = (ItemEntity)entityIn;
            if(item.ticksExisted >= 2400) {
                entityIn.attackEntityFrom(DamageSource.CACTUS, 1.0F);
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("cactus.carpet.info").mergeStyle(TextFormatting.GREEN));
        tooltip.add(new TranslationTextComponent("cactus.carpet.info2").mergeStyle(TextFormatting.GREEN));
    }
}
