package com.Mrbysco.CactusMod.init;

import java.util.ArrayList;

import com.Mrbysco.CactusMod.items.ItemBase;
import com.Mrbysco.CactusMod.items.tools.ItemCactusAxe;
import com.Mrbysco.CactusMod.items.tools.ItemCactusHoe;
import com.Mrbysco.CactusMod.items.tools.ItemCactusPick;
import com.Mrbysco.CactusMod.items.tools.ItemCactusShovel;
import com.Mrbysco.CactusMod.items.tools.ItemCactusSword;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber
public class CactusItems {
	public static Item cactus_stick;
	public static ItemSword cactus_sword;
	public static ItemSpade cactus_shovel;
	public static ItemPickaxe cactus_pickaxe;
	public static ItemTool cactus_axe;
	public static ItemHoe cactus_hoe;

	public static final ToolMaterial cactusMaterial = EnumHelper.addToolMaterial("cactus", 0, 67, 2.2F, 0.2F, 15).setRepairItem(new ItemStack(Blocks.CACTUS));

	public static ArrayList<Item> ITEMS = new ArrayList<>();
	
	@SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
		IForgeRegistry<Item> registry = event.getRegistry();
		
		cactus_stick = registerItem(new ItemBase("cactus_stick"));
		
		//Tools
		cactus_sword = registerItem(new ItemCactusSword("cactus_sword"));
		cactus_shovel = registerItem(new ItemCactusShovel("cactus_shovel"));
		cactus_pickaxe = registerItem(new ItemCactusPick("cactus_pickaxe"));
		cactus_axe = registerItem(new ItemCactusAxe("cactus_axe"));
		cactus_hoe = registerItem(new ItemCactusHoe("cactus_hoe"));
		
		registry.registerAll(ITEMS.toArray(new Item[0]));
    }
    
    public static <T extends Item> T registerItem(T item)
    {
        ITEMS.add(item);
        return item;
    }
}
