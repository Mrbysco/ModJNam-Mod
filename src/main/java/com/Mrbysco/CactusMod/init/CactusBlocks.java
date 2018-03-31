package com.Mrbysco.CactusMod.init;

import java.util.ArrayList;

import com.Mrbysco.CactusMod.blocks.BlockCactusCake;
import com.Mrbysco.CactusMod.blocks.BlockCactusChest;
import com.Mrbysco.CactusMod.blocks.BlockCactusHopper;
import com.Mrbysco.CactusMod.blocks.BlockPricklyIron;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHopper;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber
public class CactusBlocks {
	public static Block prickly_iron;
	
	public static Block cactus_cake;
	public static ItemBlock cactus_cake_item;
	
	public static BlockContainer cactus_chest;
	public static BlockHopper cactus_hopper;
	
	public static ArrayList<Block> BLOCKS = new ArrayList<>();
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
	    IForgeRegistry<Block> registry = event.getRegistry();

	    prickly_iron = registerBlock(new BlockPricklyIron("prickly_iron_block"));
	    
		cactus_chest = registerBlock(new BlockCactusChest("cactus_chest"));
		cactus_hopper = registerBlock(new BlockCactusHopper("cactus_hopper"));
	    cactus_cake = registerCake(new BlockCactusCake("cactus_cake"));
	    
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
    
    public static <T extends Block> T registerCake(T block)
    {
    	ItemBlock item = new ItemBlock(block);
    	item.setMaxStackSize(1);
    	
        return registerBlock(block, item);
    }
}
