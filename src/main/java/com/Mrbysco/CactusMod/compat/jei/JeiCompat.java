package com.mrbysco.cactusmod.compat.jei;

import com.mrbysco.cactusmod.gui.ContainerCactusWorkbench;
import com.mrbysco.cactusmod.gui.compat.ContainerFastCactusWorkbench;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraftforge.fml.common.Loader;

@JEIPlugin
public class JeiCompat implements IModPlugin {

    @Override
    public void register(IModRegistry registry) {
        if(Loader.isModLoaded("fastbench")) {
            registry.getRecipeTransferRegistry().addRecipeTransferHandler(ContainerFastCactusWorkbench.class, VanillaRecipeCategoryUid.CRAFTING, 1, 9, 10, 36);
        } else {
            registry.getRecipeTransferRegistry().addRecipeTransferHandler(ContainerCactusWorkbench.class, VanillaRecipeCategoryUid.CRAFTING, 1, 9, 10, 36);
        }
    }
}
