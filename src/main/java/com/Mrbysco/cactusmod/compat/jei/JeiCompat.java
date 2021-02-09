package com.mrbysco.cactusmod.compat.jei;

import com.mrbysco.cactusmod.Reference;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class JeiCompat implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Reference.MOD_ID, "main");
    }

//    @Override
//    public void register(IModRegistry registry) {
//        if(Loader.isModLoaded("fastbench")) {
//            registry.getRecipeTransferRegistry().addRecipeTransferHandler(ContainerFastCactusWorkbench.class, VanillaRecipeCategoryUid.CRAFTING, 1, 9, 10, 36);
//        } else {
//            registry.getRecipeTransferRegistry().addRecipeTransferHandler(ContainerCactusWorkbench.class, VanillaRecipeCategoryUid.CRAFTING, 1, 9, 10, 36);
//        }
//    }
}
