package com.mrbysco.cactusmod.config;

import com.mrbysco.cactusmod.CactusMod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class CactusConfig {
	public static class Common {

		public final ModConfigSpec.BooleanValue statuesCompat;

		Common(ModConfigSpec.Builder builder) {
			builder.comment("Compat settings")
					.push("compat");

			statuesCompat = builder
					.comment("decides if right-clicking the Cactus Snow Man with a Statues sombrero will create a Cactoni [Default: true]")
					.define("statuesCompat", true);

			builder.pop();
		}
	}

	public static final ModConfigSpec commonSpec;
	public static final Common COMMON;

	static {
		final Pair<Common, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Common::new);
		commonSpec = specPair.getRight();
		COMMON = specPair.getLeft();
	}

	@SubscribeEvent
	public static void onLoad(final ModConfigEvent.Loading configEvent) {
		CactusMod.logger.debug("Loaded Cactus Mod's config file {}", configEvent.getConfig().getFileName());
	}

	@SubscribeEvent
	public static void onFileChange(final ModConfigEvent.Reloading configEvent) {
		CactusMod.logger.debug("Cactus Mod's config just got changed on the file system!");
	}
}
