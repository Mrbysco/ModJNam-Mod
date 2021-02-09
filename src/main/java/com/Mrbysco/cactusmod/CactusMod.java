package com.mrbysco.cactusmod;

import com.mrbysco.cactusmod.client.ClientHandler;
import com.mrbysco.cactusmod.config.CactusConfig;
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
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Reference.MOD_ID)
public class CactusMod {
	public static final Logger logger = LogManager.getLogger(Reference.MOD_ID);

	public CactusMod() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

		ModLoadingContext.get().registerConfig(Type.COMMON, CactusConfig.commonSpec);
		eventBus.register(CactusConfig.class);

		CactusRegistry.FEATURES.register(eventBus);
		CactusRegistry.BLOCKS.register(eventBus);
		CactusRegistry.ITEMS.register(eventBus);
		CactusRegistry.ENTITIES.register(eventBus);
		CactusRegistry.TILE_ENTITIES.register(eventBus);
		CactusRegistry.SOUND_EVENTS.register(eventBus);

		if(ModList.get().isLoaded("fastbench")) {
			com.mrbysco.cactusmod.compat.fastbench.FastBenchCompat.CONTAINERS.register(eventBus);
		} else {
			CactusRegistry.CONTAINERS.register(eventBus);
		}
		eventBus.addListener(this::setup);

		MinecraftForge.EVENT_BUS.register(new CactusWorkbenchHandler());
		MinecraftForge.EVENT_BUS.register(new CactusToolHandler());
		MinecraftForge.EVENT_BUS.register(new CactusMobHandler());
		MinecraftForge.EVENT_BUS.register(new CactusModCompatHandlers());

		DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
			eventBus.addListener(ClientHandler::registerRenders);
			eventBus.addListener(ClientHandler::preStitchEvent);
			eventBus.addListener(ClientHandler::registerItemColors);
		});
	}

	private void setup(final FMLCommonSetupEvent event) {
		CactusSpawns.entityAttributes();
	}
}
