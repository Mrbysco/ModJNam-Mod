package com.mrbysco.cactusmod.items;

import com.mrbysco.cactusmod.CactusMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSlab;

public class ItemCactusSlab extends ItemSlab
{
    public ItemCactusSlab(Block block, BlockSlab singleSlab, BlockSlab doubleSlab) {
    	super(block, singleSlab, doubleSlab);
	}
    
    @Override
    public CreativeTabs[] getCreativeTabs() {
    	CreativeTabs[] tabs = new CreativeTabs[] { getCreativeTab()} ;
    	
    	if(!this.getUnlocalizedName().contains("double"))
    		tabs = new CreativeTabs[] {CreativeTabs.DECORATIONS, CactusMod.cactustab};
    	
    	return tabs;
    }
}
