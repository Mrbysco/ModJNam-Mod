package com.Mrbysco.CactusMod.items;

import java.util.List;

import com.Mrbysco.CactusMod.CactusMod;
import com.Mrbysco.CactusMod.Reference;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class itemCactusArmor extends ItemArmor{

	public itemCactusArmor(String registryName, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		super(CactusMod.cactusArmor, renderIndexIn, equipmentSlotIn);
		this.maxStackSize = 1;
		
		this.setCreativeTab(CactusMod.cactustab);
		this.setUnlocalizedName(Reference.PREFIX + registryName.replaceAll("_", ""));
		this.setRegistryName(registryName);
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		stack.addEnchantment(Enchantments.THORNS, 2);
		super.onCreated(stack, worldIn, playerIn);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		
		tooltip.add(TextFormatting.GREEN + I18n.translateToLocal("cactus.armor.text"));
	}
}
