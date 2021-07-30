package com.mrbysco.cactusmod.items.tools;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CactusAxeItem extends AxeItem {

	public CactusAxeItem(IItemTier tier, float attackDamageIn, float attackSpeedIn, Item.Properties builder) {
		super(tier, attackDamageIn, attackSpeedIn, builder);
	}

	@Override
	public boolean mineBlock(ItemStack stack, World worldIn, BlockState state, BlockPos pos,
									LivingEntity entityLiving) {
		if(worldIn.random.nextInt(10) < 3)
			entityLiving.hurt(DamageSource.CACTUS, 1F);
		return super.mineBlock(stack, worldIn, state, pos, entityLiving);
	}
}
