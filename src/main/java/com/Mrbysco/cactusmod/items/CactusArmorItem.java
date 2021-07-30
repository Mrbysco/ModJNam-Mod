package com.mrbysco.cactusmod.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class CactusArmorItem extends ArmorItem {

	public CactusArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot, Item.Properties builderIn) {
		super(materialIn, slot, builderIn);
	}
	
	@Override
	public void onCraftedBy(ItemStack stack, World worldIn, PlayerEntity playerIn) {
		stack.enchant(Enchantments.THORNS, 2);
		super.onCraftedBy(stack, worldIn, playerIn);
	}
	
	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack stack = playerIn.getItemInHand(handIn);
		if(!stack.isEnchanted()) {
			stack.enchant(Enchantments.THORNS, 2);
		}
		return super.use(worldIn, playerIn, handIn);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(new TranslationTextComponent("cactus.armor.text").withStyle(TextFormatting.GREEN));
	}
}
