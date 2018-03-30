package com.Mrbysco.CactusMod.init;

import com.Mrbysco.CactusMod.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class CactusTab extends CreativeTabs{

	public CactusTab() {
		super(Reference.MOD_ID);
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(Blocks.CACTUS);
	}

}
