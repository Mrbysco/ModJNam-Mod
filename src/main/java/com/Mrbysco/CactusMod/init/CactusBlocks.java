package com.Mrbysco.CactusMod.init;

import java.util.ArrayList;

import com.Mrbysco.CactusMod.blocks.CactusCakeBlock;
import com.Mrbysco.CactusMod.blocks.PricklyIronBlock;
import com.Mrbysco.CactusMod.items.CustomItemCactusCake;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBlockSpecial;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber
public class CactusBlocks {
	public static Block prickly_iron;
	public static Block cactus_cake;
	
	public static ArrayList<Block> BLOCKS = new ArrayList<>();
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
	    IForgeRegistry<Block> registry = event.getRegistry();

	    prickly_iron = registerBlock(new PricklyIronBlock("prickly_iron_block"));
	    
	    cactus_cake = new CactusCakeBlock("cactus_cake");
	    registerBlockSpecial(cactus_cake, new CustomItemCactusCake(cactus_cake));
	    
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
    
    public static <T extends Block> T registerBlockSpecial(T block)
    {
    	return registerBlockSpecial(block, new ItemBlockSpecial(block));
    }
    
    public static <T extends Block> T registerBlockSpecial(T block, ItemBlockSpecial item)
    {
    	item.setRegistryName(block.getRegistryName());
    	item.setUnlocalizedName(block.getLocalizedName());
    	
    	CactusItems.ITEMS.add(item);
    	BLOCKS.add(block);
    	return block;
    }
}
