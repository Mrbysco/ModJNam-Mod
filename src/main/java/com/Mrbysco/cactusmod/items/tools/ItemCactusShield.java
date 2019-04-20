package com.mrbysco.cactusmod.items.tools;

import com.mrbysco.cactusmod.CactusMod;
import com.mrbysco.cactusmod.Reference;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemCactusShield extends ItemShield{
	public ItemCactusShield(String registryName) {        
        this.setMaxDamage(97);

		this.setUnlocalizedName(Reference.PREFIX + registryName.replaceAll("_", ""));
		this.setRegistryName(registryName);
	}

	@Override
    public CreativeTabs[] getCreativeTabs() {
    	return new CreativeTabs[] {CreativeTabs.COMBAT, CactusMod.cactustab};
    }
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return I18n.format("item.cactusmod:cactusshield.name");
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TextFormatting.GREEN + I18n.format("cactus.shield.text"));
	}
	
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return repair.getItem() == Item.getItemFromBlock(Blocks.CACTUS) ? true : super.getIsRepairable(toRepair, repair);
    }
}
