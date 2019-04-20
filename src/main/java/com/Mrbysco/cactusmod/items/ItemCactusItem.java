package com.mrbysco.cactusmod.items;

import com.mrbysco.cactusmod.CactusMod;
import com.mrbysco.cactusmod.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemCactusItem extends Item{

	public ItemCactusItem(String registryName) {
		this.setUnlocalizedName(Reference.PREFIX + registryName.replaceAll("_", ""));
		this.setRegistryName(registryName);
	}
	
	@Override
    public CreativeTabs[] getCreativeTabs() {
    	return new CreativeTabs[] {CreativeTabs.MISC, CactusMod.cactustab};
    }
}
