package com.mrbysco.cactusmod.datagen;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.datagen.assets.CactusBlockstateProvider;
import com.mrbysco.cactusmod.datagen.data.CactusBiomeModifiers;
import com.mrbysco.cactusmod.datagen.data.CactusBlockTags;
import com.mrbysco.cactusmod.datagen.data.CactusDamageTypeProvider;
import com.mrbysco.cactusmod.datagen.data.CactusLootProvider;
import com.mrbysco.cactusmod.feature.CactusConfiguredFeatures;
import com.mrbysco.cactusmod.feature.CactusPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CactusDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
		ExistingFileHelper helper = event.getExistingFileHelper();

		if (event.includeServer()) {
			generator.addProvider(event.includeServer(), new CactusLootProvider(packOutput));
			generator.addProvider(event.includeServer(), new CactusBlockTags(packOutput, lookupProvider, helper));

			generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(
					packOutput, CompletableFuture.supplyAsync(CactusDatagen::getProvider), Set.of(Reference.MOD_ID)));
		}
		if (event.includeClient()) {
			generator.addProvider(event.includeClient(), new CactusBlockstateProvider(packOutput, helper));
		}
	}

	private static HolderLookup.Provider getProvider() {
		final RegistrySetBuilder registryBuilder = new RegistrySetBuilder();
		registryBuilder.add(Registries.DAMAGE_TYPE, CactusDamageTypeProvider::bootstrap);
		registryBuilder.add(Registries.CONFIGURED_FEATURE, CactusConfiguredFeatures::bootstrap);
		registryBuilder.add(Registries.PLACED_FEATURE, CactusPlacedFeatures::bootstrap);
		registryBuilder.add(ForgeRegistries.Keys.BIOME_MODIFIERS, CactusBiomeModifiers::bootstrap);
		// We need the BIOME registry to be present so we can use a biome tag, doesn't matter that it's empty
		registryBuilder.add(Registries.BIOME, context -> {
		});
		RegistryAccess.Frozen regAccess = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
		return registryBuilder.buildPatch(regAccess, VanillaRegistries.createLookup());
	}
}
