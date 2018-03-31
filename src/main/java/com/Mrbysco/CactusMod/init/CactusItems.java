package com.Mrbysco.CactusMod.init;

import java.util.ArrayList;

import com.Mrbysco.CactusMod.items.ItemCactusSticks;
import com.Mrbysco.CactusMod.items.itemCactusArmor;
import com.Mrbysco.CactusMod.items.tools.ItemCactusAxe;
import com.Mrbysco.CactusMod.items.tools.ItemCactusHoe;
import com.Mrbysco.CactusMod.items.tools.ItemCactusPick;
import com.Mrbysco.CactusMod.items.tools.ItemCactusShovel;
import com.Mrbysco.CactusMod.items.tools.ItemCactusSword;

import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
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
	
	public static ItemArmor cactus_helmet;
	public static ItemArmor cactus_chestplate;
	public static ItemArmor cactus_leggings;
	public static ItemArmor cactus_boots;
	
	public static ArrayList<Item> ITEMS = new ArrayList<>();
	
	@SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
		IForgeRegistry<Item> registry = event.getRegistry();
		
		cactus_stick = registerItem(new ItemCactusSticks("cactus_stick"));
		
		//Tools
		cactus_sword = registerItem(new ItemCactusSword("cactus_sword"));
		cactus_shovel = registerItem(new ItemCactusShovel("cactus_shovel"));
		cactus_pickaxe = registerItem(new ItemCactusPick("cactus_pickaxe"));
		cactus_axe = registerItem(new ItemCactusAxe("cactus_axe"));
		cactus_hoe = registerItem(new ItemCactusHoe("cactus_hoe"));
		
		//Armor
		cactus_helmet = registerItem(new itemCactusArmor("cactus_helmet", 0, EntityEquipmentSlot.HEAD));
		cactus_chestplate = registerItem(new itemCactusArmor("cactus_chestplate", 0, EntityEquipmentSlot.CHEST));
		cactus_leggings = registerItem(new itemCactusArmor("cactus_leggings", 0, EntityEquipmentSlot.LEGS));
		cactus_boots = registerItem(new itemCactusArmor("cactus_boots", 0, EntityEquipmentSlot.FEET));
		
		registry.registerAll(ITEMS.toArray(new Item[0]));
    }
    
    public static <T extends Item> T registerItem(T item)
    {
        ITEMS.add(item);
        return item;
    }
}
