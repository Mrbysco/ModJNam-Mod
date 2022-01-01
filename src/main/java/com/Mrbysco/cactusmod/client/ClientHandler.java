package com.mrbysco.cactusmod.client;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.blocks.container.CactusWorkbenchContainer;
import com.mrbysco.cactusmod.client.render.CactoniRenderer;
import com.mrbysco.cactusmod.client.render.CactusBoatRenderer;
import com.mrbysco.cactusmod.client.render.CactusCartRenderer;
import com.mrbysco.cactusmod.client.render.CactusCowRenderer;
import com.mrbysco.cactusmod.client.render.CactusCreeperRenderer;
import com.mrbysco.cactusmod.client.render.CactusGolemRenderer;
import com.mrbysco.cactusmod.client.render.CactusPigRenderer;
import com.mrbysco.cactusmod.client.render.CactusSheepRenderer;
import com.mrbysco.cactusmod.client.render.CactusSkeletonRenderer;
import com.mrbysco.cactusmod.client.render.CactusSlimeRenderer;
import com.mrbysco.cactusmod.client.render.CactusSnowmanRenderer;
import com.mrbysco.cactusmod.client.render.CactusSpiderRenderer;
import com.mrbysco.cactusmod.client.render.CactusTNTRenderer;
import com.mrbysco.cactusmod.client.render.SpikeRenderer;
import com.mrbysco.cactusmod.client.render.block.CactusChestTESR;
import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.ScreenManager.IScreenFactory;
import net.minecraft.client.gui.screen.inventory.CraftingScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import shadows.fastbench.gui.GuiFastBench;

public class ClientHandler {
    public static void registerRenders(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(CactusRegistry.CACTUS_GOLEM.get(), CactusGolemRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(CactusRegistry.CACTUS_COW.get(), CactusCowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(CactusRegistry.CACTUS_CART_ENTITY.get(), CactusCartRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(CactusRegistry.CACTUS_TNT_ENTITY.get(), CactusTNTRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(CactusRegistry.CACTUS_SPIKE.get(), SpikeRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(CactusRegistry.CACTUS_CREEPER.get(), CactusCreeperRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(CactusRegistry.CACTUS_SNOW_GOLEM.get(), CactusSnowmanRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(CactusRegistry.CACTUS_SLIME.get(), CactusSlimeRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(CactusRegistry.CACTUS_SHEEP.get(), CactusSheepRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(CactusRegistry.CACTUS_PIG.get(), CactusPigRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(CactusRegistry.CACTUS_SPIDER.get(), CactusSpiderRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(CactusRegistry.CACTUS_SKELETON.get(), CactusSkeletonRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(CactusRegistry.CACTUS_BOAT_ENTITY.get(), CactusBoatRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(CactusRegistry.CACTONI.get(), CactoniRenderer::new);

        RenderTypeLookup.setRenderLayer(CactusRegistry.CACTUS_SLIME_BLOCK.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(CactusRegistry.CACTUS_TNT.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CactusRegistry.CARVED_CACTUS.get(), RenderType.cutout());

        ClientRegistry.bindTileEntityRenderer(CactusRegistry.CACTUS_CHEST_TILE.get(), CactusChestTESR::new);

        if(!ModList.get().isLoaded("fastbench")) {
            ScreenManager.register(CactusRegistry.CACTUS_WORKBENCH_CONTAINER.get(), new Factory());
        } else {
            ScreenManager.register(CactusRegistry.CACTUS_WORKBENCH_CONTAINER.get(), new FastFactory());
        }

        ItemModelsProperties.register(CactusRegistry.CACTUS_BOW.get(), new ResourceLocation("pull"), (stack, world, entity) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return entity.getUseItem() != stack ? 0.0F : (float)(stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F;
            }
        });
        ItemModelsProperties.register(CactusRegistry.CACTUS_BOW.get(), new ResourceLocation("pulling"), (stack, world, entity) -> {
            return entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F;
        });
    }

    private static class Factory implements IScreenFactory {
        @Override
        public CraftingScreen create(Container container, PlayerInventory pInv, ITextComponent name) {
            return new CraftingScreen((CactusWorkbenchContainer) container, pInv, name);
        }
    }

    private static class FastFactory implements IScreenFactory {
        @Override
        public GuiFastBench create(Container container, PlayerInventory pInv, ITextComponent name) {
            return new shadows.fastbench.gui.GuiFastBench((com.mrbysco.cactusmod.blocks.container.CactusFastBenchContainer) container, pInv, name);
        }
    }

    public static final ResourceLocation CACTUS_CHEST_LOCATION = new ResourceLocation(Reference.MOD_ID, "entity/cactus_chest");
    public static void preStitchEvent(TextureStitchEvent.Pre event) {
        if(event.getMap().location().toString().equals("minecraft:textures/atlas/chest.png")) {
            event.addSprite(CACTUS_CHEST_LOCATION);
        }
    }
}
