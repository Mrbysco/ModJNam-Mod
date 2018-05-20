package com.Mrbysco.CactusMod.init;

import java.util.ArrayList;

import com.Mrbysco.CactusMod.blocks.BlockCactusCake;
import com.Mrbysco.CactusMod.blocks.BlockCactusChest;
import com.Mrbysco.CactusMod.blocks.BlockCactusPunji;
import com.Mrbysco.CactusMod.blocks.BlockCactusSlimeblock;
import com.Mrbysco.CactusMod.blocks.BlockCarvedCactus;
import com.Mrbysco.CactusMod.blocks.BlockPricklyIron;
import com.Mrbysco.CactusMod.blocks.decorative.BlockCactusDeco;
import com.Mrbysco.CactusMod.blocks.decorative.BlockCactusSlab;
import com.Mrbysco.CactusMod.blocks.decorative.BlockCactusStairs;
import com.Mrbysco.CactusMod.blocks.plant.BlockCactusFlower;
import com.Mrbysco.CactusMod.blocks.plant.BlockCactusPlant;
import com.Mrbysco.CactusMod.blocks.redstone.BlockCactusDispenser;
import com.Mrbysco.CactusMod.blocks.redstone.BlockCactusDoor;
import com.Mrbysco.CactusMod.blocks.redstone.BlockCactusHopper;
import com.Mrbysco.CactusMod.blocks.redstone.BlockCactusTNT;
import com.Mrbysco.CactusMod.items.ItemCactusSlab;
import com.Mrbysco.CactusMod.items.itemblocks.ItemBlockDeco;
import com.Mrbysco.CactusMod.items.itemblocks.ItemBlockFood;
import com.Mrbysco.CactusMod.items.itemblocks.ItemBlockRedstone;
import com.Mrbysco.CactusMod.items.itemblocks.ItemCactusDoor;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTNT;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber
public class CactusBlocks {
	public static Block prickly_iron;
	
	public static Block cactus_brick;
	public static BlockStairs cactus_brick_stair;
	public static BlockSlab cactus_brick_slab;
	public static BlockSlab cactus_brick_double_slab;
	
	public static Block carved_cactus;
	public static Block jacko_cactus;
	
	public static Block cactus_carpet;
	
	public static Block cactus_cake;
	public static ItemBlock cactus_cake_item;
	
	public static Block cactus_dispenser;
	public static BlockContainer cactus_chest;
	public static BlockHopper cactus_hopper;
	public static BlockTNT cactus_tnt;
	public static BlockDoor cactus_door;
	
	public static Block cactus_slimeblock;
	
	public static Block cactus_flower;
	public static Block cactus_plant;
	
	public static ArrayList<Block> BLOCKS = new ArrayList<>();
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
	    IForgeRegistry<Block> registry = event.getRegistry();

	    prickly_iron = registerBlock(new BlockPricklyIron("prickly_iron_block"));
	    
	    cactus_brick = new BlockCactusDeco("cactus_brick_block");
	    registerDecorativeBlock(cactus_brick);
	    
	    cactus_brick_stair = registerDecorativeBlock(new BlockCactusStairs("cactus_brick_stair", cactus_brick.getDefaultState()));
	    
	    cactus_brick_slab = new BlockCactusSlab.Half("cactus_brick_slab");
	    cactus_brick_double_slab = new BlockCactusSlab.Double("cactus_brick_double_slab");
	    registerBlock(cactus_brick_double_slab);
	    registerCactusSlab(cactus_brick_slab, cactus_brick_double_slab);

	    carved_cactus = registerDecorativeBlock(new BlockCarvedCactus("carved_cactus"));
	    
	    jacko_cactus = new BlockCarvedCactus("jacko_cactus");
	    jacko_cactus.setLightLevel(1F);
	    registerDecorativeBlock(jacko_cactus);
	    
	    cactus_slimeblock = registerDecorativeBlock(new BlockCactusSlimeblock("cactus_slimeblock"));

	    cactus_dispenser = registerRedstoneBlock(new BlockCactusDispenser("cactus_dispenser"));
		cactus_chest = registerDecorativeBlock(new BlockCactusChest("cactus_chest"));
	    cactus_tnt = registerRedstoneBlock(new BlockCactusTNT("cactus_tnt"));
	    cactus_carpet = registerDecorativeBlock(new BlockCactusPunji("cactus_carpet"));
	    
	    cactus_hopper = registerRedstoneBlock(new BlockCactusHopper("cactus_hopper"));
	    cactus_door = registerDoor(new BlockCactusDoor("cactus_door"));
	    cactus_cake = registerCake(new BlockCactusCake("cactus_cake"));
	    
	    cactus_flower = registerDecorativeBlock(new BlockCactusFlower("cactus_flower"));
	    cactus_plant = registerDecorativeBlock(new BlockCactusPlant("cactus_plant"));
	    
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
	
	public static <T extends Block> T registerDecorativeBlock(T block)
	{
		return registerBlock(block, new ItemBlockDeco(block));
	}
	
	public static <T extends Block> T registerRedstoneBlock(T block)
	{
		return registerBlock(block, new ItemBlockRedstone(block));
	}
	
	public static <T extends BlockSlab> T registerCactusSlab(T block, T block2)
	{
		return registerBlock(block, new ItemCactusSlab(block, block, block2));
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
    	ItemBlockFood item = new ItemBlockFood(block);
    	item.setMaxStackSize(1);
    	
        return registerBlock(block, item);
    }
}
