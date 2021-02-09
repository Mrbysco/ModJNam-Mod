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
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        ItemStack itemstack = super.onItemUseFinish(stack, worldIn, entityLiving);

        if (!worldIn.isRemote) {
        	if(worldIn.rand.nextInt(10) < 5)
    			entityLiving.attackEntityFrom(DamageSource.CACTUS, 1F);
        }

        return itemstack;
    }
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.DRINK;
	}
}
