package com.Mrbysco.CactusMod.items;

import com.Mrbysco.CactusMod.CactusMod;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockSpecial;

public class CustomItemCactusCake extends ItemBlockSpecial{

    private final Block block;

	public CustomItemCactusCake(Block block) {
		super(block);
		this.block = block;
		setCreativeTab(CactusMod.cactustab);
		setMaxStackSize(1);
		
		this.setUnlocalizedName(block.getUnlocalizedName());
	}
}
