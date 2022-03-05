package com.mrbysco.cactusmod.init;

import net.minecraft.world.food.FoodProperties;

public class CactusFoods {
	public static final FoodProperties FRUIT = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.3F).build();
	public static final FoodProperties JUICE = (new FoodProperties.Builder()).nutrition(4).build();
}
