package com.mrbysco.cactusmod.datagen.assets;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.blocks.redstone.CactusDoorBlock;
import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class CactusBlockstateProvider extends BlockStateProvider {
	public CactusBlockstateProvider(PackOutput packOutput, ExistingFileHelper exFileHelper) {
		super(packOutput, Reference.MOD_ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		door(CactusRegistry.CACTUS_DOOR);
	}

	public void door(DeferredBlock<CactusDoorBlock> doorObject) {
		doorBlockWithRenderType(doorObject.get(), doorObject.getId().toString(),
				modLoc("block/door_cactus_lower"),
				modLoc("block/door_cactus_upper"), "cutout");
	}
}
