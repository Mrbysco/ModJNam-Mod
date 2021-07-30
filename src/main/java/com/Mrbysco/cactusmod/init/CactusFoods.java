package com.mrbysco.cactusmod.init;

import net.minecraft.item.Food;

public class CactusFoods {
    public static final Food FRUIT = (new Food.Builder()).nutrition(4).saturationMod(0.3F).build();
    public static final Food JUICE = (new Food.Builder()).nutrition(4).build();
}
