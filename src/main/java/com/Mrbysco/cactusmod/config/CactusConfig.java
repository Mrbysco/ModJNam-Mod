package com.mrbysco.cactusmod.config;

import com.mrbysco.cactusmod.CactusMod;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class CactusConfig {
	public static class Common {
		public final BooleanValue creeperSpawn;
		public final BooleanValue cowSpawn;
		public final BooleanValue slimeSpawn;
		public final BooleanValue sheepSpawn;
		public final BooleanValue pigSpawn;
		public final BooleanValue spiderSpawn;
		public final BooleanValue skeletonSpawn;
		public final BooleanValue cactoniSpawn;

		public final BooleanValue statuesCompat;

		public final BooleanValue generateCactusPlant;
		public final IntValue cactusPlantRarity;

		Common(ForgeConfigSpec.Builder builder) {
			builder.comment("General settings")
					.push("general");

			creeperSpawn = builder
					.comment("Decides if the Cactus Creeper spawns naturally [Default: true]")
					.define("creeperSpawn", true);

			cowSpawn = builder
					.comment("Decides if the Cactus Cow spawns naturally [Default: true]")
					.define("cowSpawn", true);

			slimeSpawn = builder
					.comment("Decides if the Cactus Slime spawns naturally [Default: true]")
					.define("slimeSpawn", true);

			sheepSpawn = builder
					.comment("Decides if the Cactus Sheep spawns naturally [Default: true]")
					.define("sheepSpawn", true);

			pigSpawn = builder
					.comment("Decides if the Cactus Pig spawns naturally [Default: true]")
					.define("pigSpawn", true);

			spiderSpawn = builder
					.comment("Decides if the Cactus Spider spawns naturally [Default: true]")
					.define("spiderSpawn", true);

			skeletonSpawn = builder
					.comment("Decides if the Cactus Skeleton spawns naturally [Default: true]")
					.define("skeletonSpawn", true);

			cactoniSpawn = builder
					.comment("Decides if the Cactus Cactoni spawns naturally [Default: false]")
					.define("cactoniSpawn", true);

			builder.pop();
			builder.comment("Compat settings")
					.push("compat");

			statuesCompat = builder
					.comment("decides if right-clicking the Cactus Snow Man with a Statues sombrero will create a Cactoni [Default: true]")
					.define("statuesCompat", true);

			builder.pop();
			builder.comment("Worldgen settings")
					.push("worldgen");

			generateCactusPlant = builder
					.comment("Decides if the Cactus Plant will generate [Default: true]")
					.define("generateCactusPlant", true);

			cactusPlantRarity = builder
					.comment("Decides the rarity in which the cactus plant spawns")
					.defineInRange("cactusPlantRarity", 20, 1, Integer.MAX_VALUE);

			builder.pop();
		}
	}

	public static final ForgeConfigSpec commonSpec;
	public static final Common COMMON;

	static {
		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		commonSpec = specPair.getRight();
		COMMON = specPair.getLeft();
	}

	@SubscribeEvent
	public static void onLoad(final ModConfig.Loading configEvent) {
		CactusMod.logger.debug("Loaded Cactus Mod's config file {}", configEvent.getConfig().getFileName());
	}

	@SubscribeEvent
	public static void onFileChange(final ModConfig.Reloading configEvent) {
		CactusMod.logger.fatal("Cactus Mod's config just got changed on the file system!");
	}
}
