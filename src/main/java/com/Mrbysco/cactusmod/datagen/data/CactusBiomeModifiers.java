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
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

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
		return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Reference.MOD_ID, name));
	}

	public static void bootstrap(BootstapContext<BiomeModifier> context) {
		HolderGetter<Biome> biomeGetter = context.lookup(Registries.BIOME);
		HolderGetter<PlacedFeature> placedGetter = context.lookup(Registries.PLACED_FEATURE);

		var sandyTag = biomeGetter.getOrThrow(Tags.Biomes.IS_SANDY);

		context.register(ADD_CACTUS_PLANT, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
				sandyTag,
				HolderSet.direct(placedGetter.getOrThrow(CactusPlacedFeatures.CACTUS_PLANT)),
				GenerationStep.Decoration.VEGETAL_DECORATION));

		context.register(ADD_CACTUS_COW, ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
				sandyTag,
				new MobSpawnSettings.SpawnerData(CactusRegistry.CACTUS_COW.get(), 8, 4, 4))
		);

		context.register(ADD_CACTUS_CREEPER, ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
				sandyTag,
				new MobSpawnSettings.SpawnerData(CactusRegistry.CACTUS_CREEPER.get(), 100, 4, 4))
		);

		context.register(ADD_CACTUS_SLIME, ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
				sandyTag,
				new MobSpawnSettings.SpawnerData(CactusRegistry.CACTUS_SLIME.get(), 100, 2, 2))
		);

		context.register(ADD_CACTUS_SHEEP, ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
				sandyTag,
				new MobSpawnSettings.SpawnerData(CactusRegistry.CACTUS_SHEEP.get(), 12, 4, 4))
		);

		context.register(ADD_CACTUS_PIG, ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
				sandyTag,
				new MobSpawnSettings.SpawnerData(CactusRegistry.CACTUS_PIG.get(), 10, 4, 4))
		);

		context.register(ADD_CACTUS_SPIDER, ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
				sandyTag,
				new MobSpawnSettings.SpawnerData(CactusRegistry.CACTUS_SPIDER.get(), 100, 4, 4))
		);

		context.register(ADD_CACTUS_SKELETON, ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
				sandyTag,
				new MobSpawnSettings.SpawnerData(CactusRegistry.CACTUS_SKELETON.get(), 100, 4, 4))
		);

		context.register(ADD_CACTONI, ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
				sandyTag,
				new MobSpawnSettings.SpawnerData(CactusRegistry.CACTONI.get(), 1, 2, 2))
		);
	}
}
