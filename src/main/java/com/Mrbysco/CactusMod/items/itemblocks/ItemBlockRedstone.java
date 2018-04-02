package com.Mrbysco.CactusMod.items.itemblocks;

import com.Mrbysco.CactusMod.CactusMod;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;

public class ItemBlockRedstone extends ItemBlock
{
	private final Block block;

    public ItemBlockRedstone(Block block) {
    	super(block);
    	
    	this.block = block;
	}
    
    @Override
    public CreativeTabs[] getCreativeTabs() {
    	return new CreativeTabs[] {CreativeTabs.REDSTONE, CactusMod.cactustab};
    }
    
}
