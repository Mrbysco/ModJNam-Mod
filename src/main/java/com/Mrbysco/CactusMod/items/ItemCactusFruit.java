package com.Mrbysco.CactusMod.items;

import com.Mrbysco.CactusMod.CactusMod;
import com.Mrbysco.CactusMod.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemCactusFruit extends ItemFood{

	public ItemCactusFruit(String registryName) {
		super(4, 0.3F, false);
		
		this.setUnlocalizedName(Reference.PREFIX + registryName.replaceAll("_", ""));
		this.setRegistryName(registryName);
	}
	
	@Override
    public CreativeTabs[] getCreativeTabs() {
    	return new CreativeTabs[] {CreativeTabs.FOOD, CactusMod.cactustab};
    }
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        ItemStack itemstack = super.onItemUseFinish(stack, worldIn, entityLiving);

        if (!worldIn.isRemote)
        {
        	if(worldIn.rand.nextInt(10) < 5)
    			entityLiving.attackEntityFrom(DamageSource.CACTUS, 1F);
        }

        return itemstack;
    }
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.DRINK;
	}
}