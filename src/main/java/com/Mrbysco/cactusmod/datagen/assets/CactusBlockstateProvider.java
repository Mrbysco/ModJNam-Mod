package com.mrbysco.cactusmod.datagen.assets;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class CactusBlockstateProvider extends BlockStateProvider {
	public CactusBlockstateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, Reference.MOD_ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		door(CactusRegistry.CACTUS_DOOR);
	}

	public void door(RegistryObject<? extends DoorBlock> doorObject) {
		doorBlockWithRenderType(doorObject.get(), doorObject.getId().toString(),
				modLoc("block/door_cactus_lower"),
				modLoc("block/door_cactus_upper"), "cutout");
	}
}
