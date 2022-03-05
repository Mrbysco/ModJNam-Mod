package com.mrbysco.cactusmod.init;

import com.mrbysco.cactusmod.Reference;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

public class CactusTabs {
	public static final CreativeModeTab CACTUS_TAB = new CreativeModeTab(Reference.MOD_ID) {
		public ItemStack makeIcon() {
			return new ItemStack(Blocks.CACTUS);
		}
	};
}
