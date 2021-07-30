package com.mrbysco.cactusmod.items;

import com.mrbysco.cactusmod.entities.CactiCartEntity;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.properties.RailShape;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class CactusCartItem extends Item{
    private static final IDispenseItemBehavior MINECART_DISPENSER_BEHAVIOR = new DefaultDispenseItemBehavior() {
        private final DefaultDispenseItemBehavior behaviourDefaultDispenseItem = new DefaultDispenseItemBehavior();

        /**
         * Dispense the specified stack, play the dispense sound and spawn particles.
         */
        public ItemStack execute(IBlockSource source, ItemStack stack) {
            Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
            World world = source.getLevel();
            double d0 = source.x() + (double)direction.getStepX() * 1.125D;
            double d1 = Math.floor(source.y()) + (double)direction.getStepY();
            double d2 = source.z() + (double)direction.getStepZ() * 1.125D;
            BlockPos blockpos = source.getPos().relative(direction);
            BlockState blockstate = world.getBlockState(blockpos);
            RailShape railshape = blockstate.getBlock() instanceof AbstractRailBlock ? ((AbstractRailBlock)blockstate.getBlock()).getRailDirection(blockstate, world, blockpos, null) : RailShape.NORTH_SOUTH;
            double d3;
            if (blockstate.is(BlockTags.RAILS)) {
                if (railshape.isAscending()) {
                    d3 = 0.6D;
                } else {
                    d3 = 0.1D;
                }
            } else {
                if (!blockstate.isAir() || !world.getBlockState(blockpos.below()).is(BlockTags.RAILS)) {
                    return this.behaviourDefaultDispenseItem.dispense(source, stack);
                }

                BlockState blockstate1 = world.getBlockState(blockpos.below());
                RailShape railshape1 = blockstate1.getBlock() instanceof AbstractRailBlock ? blockstate1.getValue(((AbstractRailBlock)blockstate1.getBlock()).getShapeProperty()) : RailShape.NORTH_SOUTH;
                if (direction != Direction.DOWN && railshape1.isAscending()) {
                    d3 = -0.4D;
                } else {
                    d3 = -0.9D;
                }
            }

            CactiCartEntity cactusCart = new CactiCartEntity(world, d0, d1 + d3, d2);
            if (stack.hasCustomHoverName()) {
                cactusCart.setCustomName(stack.getHoverName());
            }

            world.addFreshEntity(cactusCart);
            stack.shrink(1);
            return stack;
        }

        /**
         * Play the dispense sound from the specified block.
         */
        protected void playSound(IBlockSource source) {
            source.getLevel().levelEvent(1000, source.getPos(), 0);
        }
    };

	public CactusCartItem(Item.Properties builder) {
        super(builder);

        DispenserBlock.registerBehavior(this, MINECART_DISPENSER_BEHAVIOR);
	}

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        World world = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = world.getBlockState(blockpos);
        if (!blockstate.is(BlockTags.RAILS)) {
            return ActionResultType.FAIL;
        } else {
            ItemStack itemstack = context.getItemInHand();
            if (!world.isClientSide) {
                RailShape railshape = blockstate.getBlock() instanceof AbstractRailBlock ? ((AbstractRailBlock)blockstate.getBlock()).getRailDirection(blockstate, world, blockpos, null) : RailShape.NORTH_SOUTH;
                double d0 = 0.0D;
                if (railshape.isAscending()) {
                    d0 = 0.5D;
                }

                CactiCartEntity cactusCart = new CactiCartEntity(world, (double)blockpos.getX() + 0.5D, (double)blockpos.getY() + 0.0625D + d0, (double)blockpos.getZ() + 0.5D);
                if (itemstack.hasCustomHoverName()) {
                    cactusCart.setCustomName(itemstack.getHoverName());
                }

                world.addFreshEntity(cactusCart);
            }

            itemstack.shrink(1);
            return ActionResultType.sidedSuccess(world.isClientSide);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("cactus.cart.info").withStyle(TextFormatting.GREEN));
    }
}
