package com.mrbysco.cactusmod.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CakeBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class CactusCakeBlock extends CakeBlock {
	public CactusCakeBlock(AbstractBlock.Properties builder) {
        super(builder);
	}

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public ActionResultType eat(IWorld world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!player.canEat(false)) {
            return ActionResultType.PASS;
        } else {
            player.awardStat(Stats.EAT_CAKE_SLICE);
            player.hurt(DamageSource.CACTUS, 1.0F);
            player.getFoodData().eat(2, 0.1F);
            int i = state.getValue(BITES);
            if (i < 6) {
                world.setBlock(pos, state.setValue(BITES, Integer.valueOf(i + 1)), 3);
            } else {
                world.removeBlock(pos, false);
            }

            return ActionResultType.SUCCESS;
        }
    }

    @Override
    public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        entityIn.hurt(DamageSource.CACTUS, 1.0F);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("cactus.cake.info").withStyle(TextFormatting.GREEN));
    }
}
