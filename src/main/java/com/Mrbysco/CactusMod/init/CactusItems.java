package com.Mrbysco.CactusMod.init;

import java.util.ArrayList;

import com.Mrbysco.CactusMod.items.ItemCactusArmor;
import com.Mrbysco.CactusMod.items.ItemCactusBow;
import com.Mrbysco.CactusMod.items.ItemCactusCart;
import com.Mrbysco.CactusMod.items.ItemCactusFruit;
import com.Mrbysco.CactusMod.items.ItemCactusItem;
import com.Mrbysco.CactusMod.items.ItemCactusJuice;
import com.Mrbysco.CactusMod.items.itemCactusBonemeal;
import com.Mrbysco.CactusMod.items.dispense.BonemealBehavior;
import com.Mrbysco.CactusMod.items.tools.ItemCactusAxe;
import com.Mrbysco.CactusMod.items.tools.ItemCactusHoe;
import com.Mrbysco.CactusMod.items.tools.ItemCactusPick;
import com.Mrbysco.CactusMod.items.tools.ItemCactusShield;
import com.Mrbysco.CactusMod.items.tools.ItemCactusShovel;
import com.Mrbysco.CactusMod.items.tools.ItemCactusSword;

import net.minecraft.block.BlockDispenser;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber
public class CactusItems {
	public static Item cactus_stick;
	public static Item cactus_brick;
	public static Item cactus_bone;
	public static Item cactus_bonemeal;
	
	public static ItemSword cactus_sword;
	public static ItemSpade cactus_shovel;
	public static ItemPickaxe cactus_pickaxe;
	public static ItemTool cactus_axe;
	public static ItemHoe cactus_hoe;
	
	public static ItemArmor cactus_helmet;
	public static ItemArmor cactus_chestplate;
	public static ItemArmor cactus_leggings;
	public static ItemArmor cactus_boots;
	
	public static ItemShield cactus_shield;
	public static Item cactus_bow;
	public static Item cactus_cart;
	
	public static ItemFood cactus_juice;
	public static ItemFood cactus_fruit;
		
	public static ArrayList<Item> ITEMS = new ArrayList<>();
	
	@SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
		IForgeRegistry<Item> registry = event.getRegistry();
		
		cactus_stick = registerItem(new ItemCactusItem("cactus_stick"));
		cactus_brick = registerItem(new ItemCactusItem("cactus_brick"));
		cactus_bone = registerItem(new ItemCactusItem("cactus_bone"));
		cactus_bonemeal = registerItem(new itemCactusBonemeal("cactus_bonemeal"));
		
		//Tools
		cactus_sword = registerItem(new ItemCactusSword("cactus_sword"));
		cactus_shovel = registerItem(new ItemCactusShovel("cactus_shovel"));
		cactus_pickaxe = registerItem(new ItemCactusPick("cactus_pickaxe"));
		cactus_axe = registerItem(new ItemCactusAxe("cactus_axe"));
		cactus_hoe = registerItem(new ItemCactusHoe("cactus_hoe"));
		
		//Bow
		cactus_bow = registerItem(new ItemCactusBow("cactus_bow"));
		
		//Shield
		cactus_shield = registerItem(new ItemCactusShield("cactus_shield"));
				
		//Armor
		cactus_helmet = registerItem(new ItemCactusArmor("cactus_helmet", 0, EntityEquipmentSlot.HEAD));
		cactus_chestplate = registerItem(new ItemCactusArmor("cactus_chestplate", 0, EntityEquipmentSlot.CHEST));
		cactus_leggings = registerItem(new ItemCactusArmor("cactus_leggings", 0, EntityEquipmentSlot.LEGS));
		cactus_boots = registerItem(new ItemCactusArmor("cactus_boots", 0, EntityEquipmentSlot.FEET));
		
		//Other
		cactus_cart = registerItem(new ItemCactusCart("cactus_cart"));
		
		cactus_juice = registerItem(new ItemCactusJuice("cactus_juice"));
		cactus_fruit = registerItem(new ItemCactusFruit("cactus_fruit"));
						
		registry.registerAll(ITEMS.toArray(new Item[0]));
		
    	BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(CactusItems.cactus_bonemeal, new BonemealBehavior());
    }
    
    public static <T extends Item> T registerItem(T item)
    {
        ITEMS.add(item);
        return item;
    }
}
