package com.mrbysco.cactusmod.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class CactusFruitItem extends Item {

	public CactusFruitItem(Item.Properties properties) {
		super(properties);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        ItemStack itemstack = super.finishUsingItem(stack, worldIn, entityLiving);

        if (!worldIn.isClientSide) {
        	if(worldIn.random.nextInt(10) < 5)
    			entityLiving.hurt(DamageSource.CACTUS, 1F);
        }

        return itemstack;
    }
	
	@Override
	public UseAction getUseAnimation(ItemStack stack) {
		return UseAction.DRINK;
	}
}
