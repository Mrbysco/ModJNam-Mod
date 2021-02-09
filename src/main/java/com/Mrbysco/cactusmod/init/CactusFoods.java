package com.mrbysco.cactusmod.init;

import net.minecraft.item.Food;

public class CactusFoods {
    public static final Food FRUIT = (new Food.Builder()).hunger(4).saturation(0.3F).build();
    public static final Food JUICE = (new Food.Builder()).hunger(4).build();
}
