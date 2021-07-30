package com.mrbysco.cactusmod.items.tools;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CactusShovelItem extends ShovelItem {

	public CactusShovelItem(IItemTier tier, float attackDamageIn, float attackSpeedIn, Item.Properties builder) {
		super(tier, attackDamageIn, attackSpeedIn, builder);
	}

	@Override
	public boolean mineBlock(ItemStack stack, World worldIn, BlockState state, BlockPos pos,
									LivingEntity entityLiving) {
		if(worldIn.random.nextInt(10) < 3)
			entityLiving.hurt(DamageSource.CACTUS, 1F);
		return super.mineBlock(stack, worldIn, state, pos, entityLiving);
	}

	@Override
	public ActionResultType useOn(ItemUseContext context) {
		if(context.getLevel().random.nextInt(10) < 3)
			context.getPlayer().hurt(DamageSource.CACTUS, 1F);
		return super.useOn(context);
	}
}
