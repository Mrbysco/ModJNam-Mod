package com.mrbysco.cactusmod.datagen.data;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.feature.CactusPlacedFeatures;
import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class CactusBiomeModifiers {

	protected static final ResourceKey<BiomeModifier> ADD_CACTUS_PLANT = createKey("add_cactus_plant");
	protected static final ResourceKey<BiomeModifier> ADD_CACTUS_COW = createKey("add_cactus_cow");
	protected static final ResourceKey<BiomeModifier> ADD_CACTUS_CREEPER = createKey("add_cactus_creeper");
	protected static final ResourceKey<BiomeModifier> ADD_CACTUS_SLIME = createKey("add_cactus_slime");
	protected static final ResourceKey<BiomeModifier> ADD_CACTUS_SHEEP = createKey("add_cactus_sheep");
	protected static final ResourceKey<BiomeModifier> ADD_CACTUS_PIG = createKey("add_cactus_pig");
	protected static final ResourceKey<BiomeModifier> ADD_CACTUS_SPIDER = createKey("add_cactus_spider");
	protected static final ResourceKey<BiomeModifier> ADD_CACTUS_SKELETON = createKey("add_cactus_skeleton");
	protected static final ResourceKey<BiomeModifier> ADD_CACTONI = createKey("add_cactoni");

	private static ResourceKey<BiomeModifier> createKey(String name) {
		return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Reference.MOD_ID, name));
	}

	public static void bootstrap(BootstapContext<BiomeModifier> context) {
		HolderGetter<Biome> biomeGetter = context.lookup(Registries.BIOME);
		HolderGetter<PlacedFeature> placedGetter = context.lookup(Registries.PLACED_FEATURE);

		var sandyTag = biomeGetter.getOrThrow(Tags.Biomes.IS_SANDY);

		context.register(ADD_CACTUS_PLANT, new BiomeModifiers.AddFeaturesBiomeModifier(
				sandyTag,
				HolderSet.direct(placedGetter.getOrThrow(CactusPlacedFeatures.CACTUS_PLANT)),
				GenerationStep.Decoration.VEGETAL_DECORATION));

		context.register(ADD_CACTUS_COW, BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
				sandyTag,
				new MobSpawnSettings.SpawnerData(CactusRegistry.CACTUS_COW.get(), 8, 4, 4))
		);

		context.register(ADD_CACTUS_CREEPER, BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
				sandyTag,
				new MobSpawnSettings.SpawnerData(CactusRegistry.CACTUS_CREEPER.get(), 100, 4, 4))
		);

		context.register(ADD_CACTUS_SLIME, BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
				sandyTag,
				new MobSpawnSettings.SpawnerData(CactusRegistry.CACTUS_SLIME.get(), 100, 2, 2))
		);

		context.register(ADD_CACTUS_SHEEP, BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
				sandyTag,
				new MobSpawnSettings.SpawnerData(CactusRegistry.CACTUS_SHEEP.get(), 12, 4, 4))
		);

		context.register(ADD_CACTUS_PIG, BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
				sandyTag,
				new MobSpawnSettings.SpawnerData(CactusRegistry.CACTUS_PIG.get(), 10, 4, 4))
		);

		context.register(ADD_CACTUS_SPIDER, BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
				sandyTag,
				new MobSpawnSettings.SpawnerData(CactusRegistry.CACTUS_SPIDER.get(), 100, 4, 4))
		);

		context.register(ADD_CACTUS_SKELETON, BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
				sandyTag,
				new MobSpawnSettings.SpawnerData(CactusRegistry.CACTUS_SKELETON.get(), 100, 4, 4))
		);

		context.register(ADD_CACTONI, BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
				sandyTag,
				new MobSpawnSettings.SpawnerData(CactusRegistry.CACTONI.get(), 1, 2, 2))
		);
	}
}
