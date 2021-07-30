package com.mrbysco.cactusmod.items.tools;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CactusSwordItem extends SwordItem {

	public CactusSwordItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builderIn) {
		super(tier, attackDamageIn, attackSpeedIn, builderIn);
	}

	@Override
	public boolean mineBlock(ItemStack stack, World worldIn, BlockState state, BlockPos pos,
									LivingEntity entityLiving) {
		if(worldIn.random.nextInt(10) < 3)
			entityLiving.hurt(DamageSource.CACTUS, 1F);
		return super.mineBlock(stack, worldIn, state, pos, entityLiving);
	}
}
