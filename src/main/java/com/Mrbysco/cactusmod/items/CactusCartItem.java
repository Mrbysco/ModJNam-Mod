package com.mrbysco.cactusmod.items;

import com.mrbysco.cactusmod.entities.CactiCartEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class CactusCartItem extends Item {
	private static final DispenseItemBehavior MINECART_DISPENSER_BEHAVIOR = new DefaultDispenseItemBehavior() {
		private final DefaultDispenseItemBehavior behaviourDefaultDispenseItem = new DefaultDispenseItemBehavior();

		/**
		 * Dispense the specified stack, play the dispense sound and spawn particles.
		 */
		public ItemStack execute(BlockSource source, ItemStack stack) {
			Direction direction = source.state().getValue(DispenserBlock.FACING);
			Level level = source.level();
			Vec3 vec3 = source.center();
			double d0 = vec3.x() + (double) direction.getStepX() * 1.125;
			double d1 = Math.floor(vec3.y()) + (double) direction.getStepY();
			double d2 = vec3.z() + (double) direction.getStepZ() * 1.125;
			BlockPos blockpos = source.pos().relative(direction);
			BlockState blockstate = level.getBlockState(blockpos);
			RailShape railshape = blockstate.getBlock() instanceof BaseRailBlock ? ((BaseRailBlock) blockstate.getBlock()).getRailDirection(blockstate, level, blockpos, null) : RailShape.NORTH_SOUTH;
			double d3;
			if (blockstate.is(BlockTags.RAILS)) {
				if (railshape.isAscending()) {
					d3 = 0.6D;
				} else {
					d3 = 0.1D;
				}
			} else {
				if (!blockstate.isAir() || !level.getBlockState(blockpos.below()).is(BlockTags.RAILS)) {
					return this.behaviourDefaultDispenseItem.dispense(source, stack);
				}

				BlockState blockstate1 = level.getBlockState(blockpos.below());
				RailShape railshape1 = blockstate1.getBlock() instanceof BaseRailBlock ? blockstate1.getValue(((BaseRailBlock) blockstate1.getBlock()).getShapeProperty()) : RailShape.NORTH_SOUTH;
				if (direction != Direction.DOWN && railshape1.isAscending()) {
					d3 = -0.4D;
				} else {
					d3 = -0.9D;
				}
			}

			CactiCartEntity cactusCart = new CactiCartEntity(level, d0, d1 + d3, d2);
			if (stack.hasCustomHoverName()) {
				cactusCart.setCustomName(stack.getHoverName());
			}

			level.addFreshEntity(cactusCart);
			stack.shrink(1);
			return stack;
		}

		/**
		 * Play the dispense sound from the specified block.
		 */
		protected void playSound(BlockSource source) {
			source.level().levelEvent(1000, source.pos(), 0);
		}
	};

	public CactusCartItem(Item.Properties builder) {
		super(builder);

		DispenserBlock.registerBehavior(this, MINECART_DISPENSER_BEHAVIOR);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		BlockState blockstate = level.getBlockState(blockpos);
		if (!blockstate.is(BlockTags.RAILS)) {
			return InteractionResult.FAIL;
		} else {
			ItemStack itemstack = context.getItemInHand();
			if (!level.isClientSide) {
				RailShape railshape = blockstate.getBlock() instanceof BaseRailBlock ? ((BaseRailBlock) blockstate.getBlock()).getRailDirection(blockstate, level, blockpos, null) : RailShape.NORTH_SOUTH;
				double d0 = 0.0D;
				if (railshape.isAscending()) {
					d0 = 0.5D;
				}

				CactiCartEntity cactusCart = new CactiCartEntity(level, (double) blockpos.getX() + 0.5D, (double) blockpos.getY() + 0.0625D + d0, (double) blockpos.getZ() + 0.5D);
				if (itemstack.hasCustomHoverName()) {
					cactusCart.setCustomName(itemstack.getHoverName());
				}

				level.addFreshEntity(cactusCart);
			}

			itemstack.shrink(1);
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, level, tooltip, flagIn);
		tooltip.add(Component.translatable("cactus.cart.info").withStyle(ChatFormatting.GREEN));
	}
}
