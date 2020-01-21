package com.mrbysco.cactusmod.init;

import com.mrbysco.cactusmod.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class CactusTab extends CreativeTabs{

	public CactusTab() {
		super(Reference.MOD_ID);
	}
	@Override
	public ItemStack createIcon() {
		return new ItemStack(Blocks.CACTUS);
	}

}
