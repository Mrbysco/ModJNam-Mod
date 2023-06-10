package com.mrbysco.cactusmod.items;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class CactusFruitItem extends Item {

	public CactusFruitItem(Item.Properties properties) {
		super(properties);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
		ItemStack itemstack = super.finishUsingItem(stack, level, entityLiving);

		if (!level.isClientSide) {
			if (level.random.nextInt(10) < 5)
				entityLiving.hurt(entityLiving.damageSources().cactus(), 1F);
		}

		return itemstack;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.DRINK;
	}
}
