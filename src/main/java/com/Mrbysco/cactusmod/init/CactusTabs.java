package com.mrbysco.cactusmod.init;

import com.mrbysco.cactusmod.Reference;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CactusTabs {
	public static final CreativeModeTab CACTUS_TAB = new CreativeModeTab(Reference.MOD_ID) {
		@OnlyIn(Dist.CLIENT)
		public ItemStack makeIcon() {
			return new ItemStack(Blocks.CACTUS);
		}
	};
}
