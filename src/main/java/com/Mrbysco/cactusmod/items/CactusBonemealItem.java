package com.mrbysco.cactusmod.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CactusBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CactusBonemealItem extends Item{

    public CactusBonemealItem(Item.Properties builder) {
        super(builder);
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        World world = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        if (applyBonemeal(context.getItemInHand(), world, blockpos, context.getPlayer())) {
            if (!world.isClientSide) {
                world.levelEvent(2005, blockpos, 0);
            }

            return ActionResultType.sidedSuccess(world.isClientSide);
        }
        return super.useOn(context);
    }

    public static boolean applyBonemeal(ItemStack stack, World worldIn, BlockPos pos, net.minecraft.entity.player.PlayerEntity player) {
        BlockState blockstate = worldIn.getBlockState(pos);
        int hook = net.minecraftforge.event.ForgeEventFactory.onApplyBonemeal(player, worldIn, pos, blockstate, stack);
        if (hook != 0) return hook > 0;

        if (blockstate.getBlock() instanceof CactusBlock) {
            if (canGrow(worldIn, pos, blockstate)) {
                if (worldIn instanceof ServerWorld) {
                    growCactus(worldIn, pos, blockstate);
                    stack.shrink(1);
                }

                return true;
            }
        } else if(blockstate.getBlock().is(BlockTags.SAND)) {
            if (worldIn instanceof ServerWorld) {
                growBush(worldIn, pos, blockstate);
                stack.shrink(1);
            }

            return true;
        }

        return false;
    }

    public static boolean canGrow(World worldIn, BlockPos pos, BlockState state) {
        BlockPos blockpos = pos.above();

        if (worldIn.isEmptyBlock(blockpos)) {
            int i;

            for (i = 1; worldIn.getBlockState(pos.below(i)).getBlock() instanceof CactusBlock; ++i) {
                ;
            }

            if (i < 5) {
                int j = (Integer) state.getValue(CactusBlock.AGE) + getInt(worldIn.random, 3, 8);
                int k = 15;
                if (j > k) {
                    j = k;
                }

                if (j == k) {
                    return true;
                } else {
                    return true;
                }
            }
        }

        return false;
    }

    public static void growCactus(World worldIn, BlockPos pos, BlockState state) {
        BlockPos blockpos = pos.above();
        if (worldIn.isEmptyBlock(blockpos)) {
            int i;
            for(i = 1; worldIn.getBlockState(pos.below(i)).is(state.getBlock()); ++i) {
                ;
            }

            if (i < 3) {
                int j = state.getValue(CactusBlock.AGE) + getInt(worldIn.random, 3, 8);
                if (j >= 15) {
                    worldIn.setBlockAndUpdate(blockpos, state.getBlock().defaultBlockState());
                    BlockState blockstate = state.setValue(CactusBlock.AGE, Integer.valueOf(0));
                    worldIn.setBlock(pos, blockstate, 4);
                    blockstate.neighborChanged(worldIn, blockpos, state.getBlock(), pos, false);
                } else {
                    worldIn.setBlock(pos, state.setValue(CactusBlock.AGE, Integer.valueOf(j + 1)), 4);
                }
            }
        }
    }

    public static void growBush(World worldIn, BlockPos pos, BlockState state) {
        final int range = 3;
        if(!worldIn.isClientSide) {
            List<BlockPos> validCoords = new ArrayList<>();

            for(int i = -range - 1; i < range; i++)
                for(int j = -range - 1; j < range; j++) {
                    for(int k = 2; k >= -2; k--) {
                        BlockPos pos_ = pos.offset(i + 1, k + 1, j + 1);
                        if(worldIn.isEmptyBlock(pos_) && (!worldIn.dimensionType().ultraWarm() || pos_.getY() < 255) && isValidGround(state))
                            validCoords.add(pos_);
                    }
                }

            int bushCount = Math.min(validCoords.size(), worldIn.random.nextBoolean() ? 3 : 4);
            for(int i = 0; i < bushCount; i++) {
                BlockPos coords = validCoords.get(worldIn.random.nextInt(validCoords.size()));
                if(isValidGround(worldIn.getBlockState(coords.below()))) {
                    worldIn.setBlockAndUpdate(coords, Blocks.DEAD_BUSH.defaultBlockState());
                }
                validCoords.remove(coords);
            }
        }
    }

    protected static boolean isValidGround(BlockState state) {
        Block block = state.getBlock();
        return block == Blocks.SAND || block == Blocks.RED_SAND || block == Blocks.TERRACOTTA || block == Blocks.WHITE_TERRACOTTA || block == Blocks.ORANGE_TERRACOTTA || block == Blocks.MAGENTA_TERRACOTTA || block == Blocks.LIGHT_BLUE_TERRACOTTA || block == Blocks.YELLOW_TERRACOTTA || block == Blocks.LIME_TERRACOTTA || block == Blocks.PINK_TERRACOTTA || block == Blocks.GRAY_TERRACOTTA || block == Blocks.LIGHT_GRAY_TERRACOTTA || block == Blocks.CYAN_TERRACOTTA || block == Blocks.PURPLE_TERRACOTTA || block == Blocks.BLUE_TERRACOTTA || block == Blocks.BROWN_TERRACOTTA || block == Blocks.GREEN_TERRACOTTA || block == Blocks.RED_TERRACOTTA || block == Blocks.BLACK_TERRACOTTA || block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.PODZOL;
    }

    public static int getInt(Random random, int minimum, int maximum) {
        return minimum >= maximum ? minimum : random.nextInt(maximum - minimum + 1) + minimum;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("cactus.bonemeal.info").withStyle(TextFormatting.GREEN));
    }
}
