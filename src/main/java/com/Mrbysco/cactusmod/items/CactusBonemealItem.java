package com.mrbysco.cactusmod.items;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CactusBonemealItem extends Item {

	public CactusBonemealItem(Item.Properties builder) {
		super(builder);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level world = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		if (applyBonemeal(context.getItemInHand(), world, blockpos, context.getPlayer())) {
			if (!world.isClientSide) {
				world.levelEvent(2005, blockpos, 0);
			}

			return InteractionResult.sidedSuccess(world.isClientSide);
		}
		return super.useOn(context);
	}

	public static boolean applyBonemeal(ItemStack stack, Level worldIn, BlockPos pos, net.minecraft.world.entity.player.Player player) {
		BlockState blockstate = worldIn.getBlockState(pos);
		int hook = net.minecraftforge.event.ForgeEventFactory.onApplyBonemeal(player, worldIn, pos, blockstate, stack);
		if (hook != 0) return hook > 0;

		if (blockstate.getBlock() instanceof CactusBlock) {
			if (canGrow(worldIn, pos, blockstate)) {
				if (worldIn instanceof ServerLevel) {
					growCactus(worldIn, pos, blockstate);
					stack.shrink(1);
				}

				return true;
			}
		} else if (blockstate.is(BlockTags.SAND)) {
			if (worldIn instanceof ServerLevel) {
				growBush(worldIn, pos, blockstate);
				stack.shrink(1);
			}

			return true;
		}

		return false;
	}

	public static boolean canGrow(Level worldIn, BlockPos pos, BlockState state) {
		BlockPos blockpos = pos.above();

		if (worldIn.isEmptyBlock(blockpos)) {
			int i;

			for (i = 1; worldIn.getBlockState(pos.below(i)).getBlock() instanceof CactusBlock; ++i) {
			}

			if (i < 5) {
				int j = (Integer) state.getValue(CactusBlock.AGE) + getInt(worldIn.random, 3, 8);
				int k = 15;
				if (j > k) {
					j = k;
				}

				return true;
			}
		}

		return false;
	}

	public static void growCactus(Level worldIn, BlockPos pos, BlockState state) {
		BlockPos blockpos = pos.above();
		if (worldIn.isEmptyBlock(blockpos)) {
			int i;
			for (i = 1; worldIn.getBlockState(pos.below(i)).is(state.getBlock()); ++i) {
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

	public static void growBush(Level worldIn, BlockPos pos, BlockState state) {
		final int range = 3;
		if (!worldIn.isClientSide) {
			List<BlockPos> validCoords = new ArrayList<>();

			for (int i = -range - 1; i < range; i++)
				for (int j = -range - 1; j < range; j++) {
					for (int k = 2; k >= -2; k--) {
						BlockPos pos_ = pos.offset(i + 1, k + 1, j + 1);
						if (worldIn.isEmptyBlock(pos_) && (!worldIn.dimensionType().ultraWarm() || pos_.getY() < 255) && isValidGround(state))
							validCoords.add(pos_);
					}
				}

			int bushCount = Math.min(validCoords.size(), worldIn.random.nextBoolean() ? 3 : 4);
			for (int i = 0; i < bushCount; i++) {
				BlockPos coords = validCoords.get(worldIn.random.nextInt(validCoords.size()));
				if (isValidGround(worldIn.getBlockState(coords.below()))) {
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
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(new TranslatableComponent("cactus.bonemeal.info").withStyle(ChatFormatting.GREEN));
	}
}
