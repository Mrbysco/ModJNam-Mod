
package com.Mrbysco.CactusMod.items.dispense;

import com.Mrbysco.CactusMod.init.CactusItems;
import com.Mrbysco.CactusMod.items.itemCactusBonemeal;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.init.Bootstrap.BehaviorDispenseOptional;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BonemealBehavior extends BehaviorDispenseOptional {
	
	protected ItemStack dispenseStack(IBlockSource source, ItemStack stack)
    {
        if (stack.getItem() == CactusItems.cactus_bonemeal)
        {
            World world = source.getWorld();
            BlockPos blockpos = source.getBlockPos().offset((EnumFacing)source.getBlockState().getValue(BlockDispenser.FACING));

            if (itemCactusBonemeal.applyBonemeal(stack, world, blockpos))
            {
                if (!world.isRemote)
                {
                    world.playEvent(2005, blockpos, 0);
                }
            }

            return stack;
        }
        else
        {
            return super.dispenseStack(source, stack);
        }
    }
}