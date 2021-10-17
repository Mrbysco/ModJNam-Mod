package com.mrbysco.cactusmod.init;

import com.mrbysco.cactusmod.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class CactusTiers {
    public static final Tier CACTUS = TierSortingRegistry.registerTier(
            new ForgeTier(0, 67, 3.0F, 0.2F, 16,
                    BlockTags.createOptional(new ResourceLocation(Reference.MOD_ID, "needs_cactus_tool")),
                    () -> Ingredient.of(Items.CACTUS)),
            new ResourceLocation(Reference.MOD_ID, "cactus"), List.of(Tiers.WOOD), List.of(Tiers.STONE));
}
