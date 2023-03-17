package com.mrbysco.cactusmod.items.tools;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class CactusPickaxeItem extends PickaxeItem {
	public CactusPickaxeItem(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builder) {
		super(tier, attackDamageIn, attackSpeedIn, builder);
	}

	@Override
	public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos,
							 LivingEntity entityLiving) {
		if (level.random.nextInt(10) < 3)
			entityLiving.hurt(DamageSource.CACTUS, 1F);
		return super.mineBlock(stack, level, state, pos, entityLiving);
	}
}
