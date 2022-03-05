package com.mrbysco.cactusmod;

import com.mojang.logging.LogUtils;
import com.mrbysco.cactusmod.client.ClientHandler;
import com.mrbysco.cactusmod.config.CactusConfig;
import com.mrbysco.cactusmod.feature.CactusFeatureConfig;
import com.mrbysco.cactusmod.handlers.CactusMobHandler;
import com.mrbysco.cactusmod.handlers.CactusModCompatHandlers;
import com.mrbysco.cactusmod.handlers.CactusToolHandler;
import com.mrbysco.cactusmod.handlers.CactusWorkbenchHandler;
import com.mrbysco.cactusmod.init.CactusRegistry;
import com.mrbysco.cactusmod.init.CactusSpawns;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Reference.MOD_ID)
public class CactusMod {
	public static final Logger logger = LogUtils.getLogger();

	public CactusMod() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

		ModLoadingContext.get().registerConfig(Type.COMMON, CactusConfig.commonSpec);
		eventBus.register(CactusConfig.class);

		CactusRegistry.FEATURES.register(eventBus);
		CactusRegistry.BLOCKS.register(eventBus);
		CactusRegistry.ITEMS.register(eventBus);
		CactusRegistry.ENTITIES.register(eventBus);
		CactusRegistry.BLOCK_ENTITIES.register(eventBus);
		CactusRegistry.SOUND_EVENTS.register(eventBus);
		CactusRegistry.CONTAINERS.register(eventBus);

		eventBus.addListener(this::setup);
		eventBus.addListener(CactusSpawns::registerEntityAttributes);

		MinecraftForge.EVENT_BUS.register(new CactusWorkbenchHandler());
		MinecraftForge.EVENT_BUS.register(new CactusToolHandler());
		MinecraftForge.EVENT_BUS.register(new CactusMobHandler());
		MinecraftForge.EVENT_BUS.register(new CactusModCompatHandlers());

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			eventBus.addListener(ClientHandler::registerRenders);
			eventBus.addListener(ClientHandler::registerEntityRenders);
			eventBus.addListener(ClientHandler::registerLayerDefinitions);
			eventBus.addListener(ClientHandler::preStitchEvent);
		});
	}

	private void setup(final FMLCommonSetupEvent event) {
		CactusSpawns.initSpawnPlacements();
		CactusFeatureConfig.initialize();
	}
}
