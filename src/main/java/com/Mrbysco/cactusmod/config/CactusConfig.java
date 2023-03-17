package com.mrbysco.cactusmod.config;

import com.mrbysco.cactusmod.CactusMod;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

public class CactusConfig {
	public static class Common {

		public final BooleanValue statuesCompat;

		Common(ForgeConfigSpec.Builder builder) {
			builder.comment("Compat settings")
					.push("compat");

			statuesCompat = builder
					.comment("decides if right-clicking the Cactus Snow Man with a Statues sombrero will create a Cactoni [Default: true]")
					.define("statuesCompat", true);

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
	public static void onLoad(final ModConfigEvent.Loading configEvent) {
		CactusMod.logger.debug("Loaded Cactus Mod's config file {}", configEvent.getConfig().getFileName());
	}

	@SubscribeEvent
	public static void onFileChange(final ModConfigEvent.Reloading configEvent) {
		CactusMod.logger.debug("Cactus Mod's config just got changed on the file system!");
	}
}
