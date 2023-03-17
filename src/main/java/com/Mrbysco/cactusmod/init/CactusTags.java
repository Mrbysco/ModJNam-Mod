package com.mrbysco.cactusmod.init;

import com.mrbysco.cactusmod.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class CactusTags {
	public static final TagKey<Block> NEEDS_CACTUS_TOOL = BlockTags.create(new ResourceLocation(Reference.MOD_ID, "needs_cactus_tool"));
}
