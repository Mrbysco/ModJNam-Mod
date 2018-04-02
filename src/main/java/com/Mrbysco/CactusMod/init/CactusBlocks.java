package com.Mrbysco.CactusMod.init;

import java.util.ArrayList;

import com.Mrbysco.CactusMod.blocks.BlockCactusCake;
import com.Mrbysco.CactusMod.blocks.BlockCactusChest;
import com.Mrbysco.CactusMod.blocks.BlockCactusDoor;
import com.Mrbysco.CactusMod.blocks.BlockCactusHopper;
import com.Mrbysco.CactusMod.blocks.BlockCactusPunji;
import com.Mrbysco.CactusMod.blocks.BlockCactusTNT;
import com.Mrbysco.CactusMod.blocks.BlockCarvedCactus;
import com.Mrbysco.CactusMod.blocks.BlockPricklyIron;
import com.Mrbysco.CactusMod.items.ItemCactusDoor;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockTNT;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber
public class CactusBlocks {
	public static Block prickly_iron;
	
	public static Block carved_cactus;
	public static Block jacko_cactus;
	
	public static Block cactus_carpet;
	
	public static Block cactus_cake;
	public static ItemBlock cactus_cake_item;
	
	public static BlockContainer cactus_chest;
	public static BlockHopper cactus_hopper;
	public static BlockTNT cactus_tnt;
	public static BlockDoor cactus_door;
	
	public static ArrayList<Block> BLOCKS = new ArrayList<>();
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
	    IForgeRegistry<Block> registry = event.getRegistry();

	    prickly_iron = registerBlock(new BlockPricklyIron("prickly_iron_block"));
	    
	    carved_cactus = registerBlock(new BlockCarvedCactus("carved_cactus"));
	    
	    jacko_cactus = new BlockCarvedCactus("jacko_cactus");
	    jacko_cactus.setLightLevel(1F);
	    registerBlock(jacko_cactus);
	    
	    cactus_carpet = registerBlock(new BlockCactusPunji("cactus_carpet"));
	    
		cactus_chest = registerBlock(new BlockCactusChest("cactus_chest"));
		cactus_hopper = registerBlock(new BlockCactusHopper("cactus_hopper"));
	    cactus_cake = registerCake(new BlockCactusCake("cactus_cake"));
	    
	    cactus_tnt = registerBlock(new BlockCactusTNT("cactus_tnt"));
	    
	    cactus_door = registerBlock(new BlockCactusDoor("cactus_door"));
	    
	    registry.registerAll(BLOCKS.toArray(new Block[0]));
	}
	
	public static <T extends Block> T registerBlock(T block)
    {
        return registerBlock(block, new ItemBlock(block));
    }
    
	public static <T extends Block> T registerBlock(T block, ItemBlock item)
	{
		item.setRegistryName(block.getRegistryName());
		CactusItems.ITEMS.add(item);
		BLOCKS.add(block);
		return block;
	}
	
    public static <T extends Block> T registerDoor(T block)
    {
    	ItemCactusDoor item = new ItemCactusDoor(block);
    	item.setRegistryName(block.getRegistryName().getResourceDomain(), block.getRegistryName().getResourcePath() + "_item");

    	CactusItems.ITEMS.add(item);
        BLOCKS.add(block);
        return block;
    }
    
    public static <T extends Block> T registerCake(T block)
    {
    	ItemBlock item = new ItemBlock(block);
    	item.setMaxStackSize(1);
    	
        return registerBlock(block, item);
    }
}
