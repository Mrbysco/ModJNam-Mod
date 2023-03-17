package com.mrbysco.cactusmod.datagen.data;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.feature.CactusFeatureConfig;
import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;

import java.util.List;
import java.util.Map;

public class CactusBiomeModifiers {


	public static Map<ResourceLocation, PlacedFeature> getConfiguredFeatures(RegistryOps<JsonElement> ops) {
		Map<ResourceLocation, PlacedFeature> map = Maps.newHashMap();

		final ResourceKey<ConfiguredFeature<?, ?>> cactusPlantResourceKey = CactusFeatureConfig.CACTUS_PLANT.unwrapKey().get().cast(Registry.CONFIGURED_FEATURE_REGISTRY).get();
		final Holder<ConfiguredFeature<?, ?>> cactusPlantFeatureHolder = ops.registry(Registry.CONFIGURED_FEATURE_REGISTRY).get().getOrCreateHolderOrThrow(cactusPlantResourceKey);
		final PlacedFeature cactusPlantFeature = new PlacedFeature(
				cactusPlantFeatureHolder,
				List.of(RarityFilter.onAverageOnceEvery(20), CountPlacement.of(UniformInt.of(0, 4)), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
		map.put(new ResourceLocation(Reference.MOD_ID, "cactus_plant"), cactusPlantFeature);

		return map;
	}

	public static Map<ResourceLocation, BiomeModifier> getBiomeModifiers(RegistryOps<JsonElement> ops) {
		Map<ResourceLocation, BiomeModifier> map = Maps.newHashMap();

		final HolderSet.Named<Biome> sandyTag = new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), Tags.Biomes.IS_SANDY);
		final BiomeModifier addCactusPlant = new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
				sandyTag,
				HolderSet.direct(ops.registry(Registry.PLACED_FEATURE_REGISTRY).get().getOrCreateHolderOrThrow(ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY,
						new ResourceLocation(Reference.MOD_ID, "cactus_plant")))),
				GenerationStep.Decoration.VEGETAL_DECORATION);
		map.put(new ResourceLocation(Reference.MOD_ID, "add_cactus_plant"), addCactusPlant);

		final BiomeModifier addCactusCow = ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
				sandyTag,
				new MobSpawnSettings.SpawnerData(CactusRegistry.CACTUS_COW.get(), 8, 4, 4));
		map.put(new ResourceLocation(Reference.MOD_ID, "add_cactus_cow"), addCactusCow);

		final BiomeModifier addCactusCreeper = ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
				sandyTag,
				new MobSpawnSettings.SpawnerData(CactusRegistry.CACTUS_CREEPER.get(), 100, 4, 4));
		map.put(new ResourceLocation(Reference.MOD_ID, "add_cactus_creeper"), addCactusCreeper);

		final BiomeModifier addCactusSlime = ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
				sandyTag,
				new MobSpawnSettings.SpawnerData(CactusRegistry.CACTUS_SLIME.get(), 100, 2, 2));
		map.put(new ResourceLocation(Reference.MOD_ID, "add_cactus_slime"), addCactusSlime);

		final BiomeModifier addCactusSheep = ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
				sandyTag,
				new MobSpawnSettings.SpawnerData(CactusRegistry.CACTUS_SHEEP.get(), 12, 4, 4));
		map.put(new ResourceLocation(Reference.MOD_ID, "add_cactus_sheep"), addCactusSheep);

		final BiomeModifier addCactusPig = ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
				sandyTag,
				new MobSpawnSettings.SpawnerData(CactusRegistry.CACTUS_PIG.get(), 10, 4, 4));
		map.put(new ResourceLocation(Reference.MOD_ID, "add_cactus_pig"), addCactusPig);

		final BiomeModifier addCactusSpider = ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
				sandyTag,
				new MobSpawnSettings.SpawnerData(CactusRegistry.CACTUS_SPIDER.get(), 100, 4, 4));
		map.put(new ResourceLocation(Reference.MOD_ID, "add_cactus_spider"), addCactusSpider);

		final BiomeModifier addCactusSkeleton = ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
				sandyTag,
				new MobSpawnSettings.SpawnerData(CactusRegistry.CACTUS_SKELETON.get(), 100, 4, 4));
		map.put(new ResourceLocation(Reference.MOD_ID, "add_cactus_skeleton"), addCactusSkeleton);

		final BiomeModifier addCactoni = ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
				sandyTag,
				new MobSpawnSettings.SpawnerData(CactusRegistry.CACTONI.get(), 1, 2, 2));
		map.put(new ResourceLocation(Reference.MOD_ID, "add_cactoni"), addCactoni);

		return map;
	}


}
