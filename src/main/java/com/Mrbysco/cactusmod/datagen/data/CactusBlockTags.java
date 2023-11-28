package com.mrbysco.cactusmod.datagen.data;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.init.CactusTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class CactusBlockTags extends BlockTagsProvider {
	public CactusBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, Reference.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		this.tag(CactusTags.NEEDS_CACTUS_TOOL);
	}
}
