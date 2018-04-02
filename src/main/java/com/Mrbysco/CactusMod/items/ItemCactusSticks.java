package com.Mrbysco.CactusMod.items;

import com.Mrbysco.CactusMod.CactusMod;
import com.Mrbysco.CactusMod.Reference;

import net.minecraft.item.Item;

public class ItemCactusSticks extends Item{

	public ItemCactusSticks(String registryName) {
		setCreativeTab(CactusMod.cactustab);
		
		this.setUnlocalizedName(Reference.PREFIX + registryName.replaceAll("_", ""));
		this.setRegistryName(registryName);
	}
}
