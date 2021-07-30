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
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

public class PunjiCactusBlock extends CarpetBlock {
    protected static final VoxelShape SHAPE = Stream.of(
            Block.box(1, 0, 1, 15, 1, 15),
            Block.box(4, 1, 5, 5, 2, 6),
            Block.box(4, 1, 8, 5, 2, 9),
            Block.box(9, 1, 6, 10, 2, 7),
            Block.box(7, 1, 3, 8, 2, 4),
            Block.box(11, 1, 3, 12, 2, 4),
            Block.box(12, 1, 8, 13, 2, 9),
            Block.box(11, 1, 11, 12, 2, 12),
            Block.box(9, 1, 11, 10, 2, 12),
            Block.box(7, 1, 9, 8, 2, 10),
            Block.box(5, 1, 12, 6, 2, 13),
            Block.box(14, 1, 8, 15, 2, 9),
            Block.box(14, 1, 5, 15, 2, 6),
            Block.box(14, 1, 11, 15, 2, 12),
            Block.box(12, 1, 14, 13, 2, 15),
            Block.box(9, 1, 14, 10, 2, 15),
            Block.box(6, 1, 14, 7, 2, 15),
            Block.box(2, 1, 14, 3, 2, 15),
            Block.box(1, 1, 10, 2, 2, 11),
            Block.box(1, 1, 7, 2, 2, 8),
            Block.box(1, 1, 3, 2, 2, 4),
            Block.box(3, 1, 1, 4, 2, 2),
            Block.box(7, 1, 1, 8, 2, 2),
            Block.box(11, 1, 1, 12, 2, 2)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

	public PunjiCactusBlock(AbstractBlock.Properties builder) {
		super(DyeColor.GREEN, builder);
	}

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
    }

    @Override
    public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        super.entityInside(state, worldIn, pos, entityIn);

        if(entityIn instanceof MobEntity) {
            entityIn.hurt(DamageSource.CACTUS, 1.0F);
        } else if (entityIn instanceof ItemEntity) {
            ItemEntity item = (ItemEntity)entityIn;
            if(item.tickCount >= 2400) {
                entityIn.hurt(DamageSource.CACTUS, 1.0F);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("cactus.carpet.info").withStyle(TextFormatting.GREEN));
        tooltip.add(new TranslationTextComponent("cactus.carpet.info2").withStyle(TextFormatting.GREEN));
    }
}
