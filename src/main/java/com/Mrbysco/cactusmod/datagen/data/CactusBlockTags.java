package com.mrbysco.cactusmod.datagen.data;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.init.CactusTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class CactusBlockTags extends BlockTagsProvider {
	public CactusBlockTags(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
		super(generator, Reference.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags() {
		this.tag(CactusTags.NEEDS_CACTUS_TOOL);
	}
}
