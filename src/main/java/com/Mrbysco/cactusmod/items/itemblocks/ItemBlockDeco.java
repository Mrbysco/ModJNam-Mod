package com.mrbysco.cactusmod.items.itemblocks;

import com.mrbysco.cactusmod.CactusMod;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;

public class ItemBlockDeco extends ItemBlock
{
	private final Block block;

    public ItemBlockDeco(Block block) {
    	super(block);
    	
    	this.block = block;
	}
    
    @Override
    public CreativeTabs[] getCreativeTabs() {
    	return new CreativeTabs[] {CreativeTabs.DECORATIONS, CactusMod.cactustab};
    }
    
}
