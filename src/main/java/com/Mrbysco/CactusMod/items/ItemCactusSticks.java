package com.Mrbysco.CactusMod.items;

import java.util.List;

import com.Mrbysco.CactusMod.CactusMod;
import com.Mrbysco.CactusMod.Reference;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class ItemCactusSticks extends Item{

	public ItemCactusSticks(String registryName) {
		setCreativeTab(CactusMod.cactustab);
		
		this.setUnlocalizedName(Reference.PREFIX + registryName.replaceAll("_", ""));
		this.setRegistryName(registryName);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);

		tooltip.add(TextFormatting.GREEN + I18n.translateToLocal("cactus_stick.text"));
		tooltip.add(TextFormatting.GREEN + I18n.translateToLocal("cactus_stick.text2"));
	}
}
