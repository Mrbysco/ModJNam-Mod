package com.mrbysco.cactusmod.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class CactusArmorItem extends ArmorItem {

	public CactusArmorItem(ArmorMaterial materialIn, EquipmentSlot slot, Item.Properties builderIn) {
		super(materialIn, slot, builderIn);
	}
	
	@Override
	public void onCraftedBy(ItemStack stack, Level worldIn, Player playerIn) {
		stack.enchant(Enchantments.THORNS, 2);
		super.onCraftedBy(stack, worldIn, playerIn);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		ItemStack stack = playerIn.getItemInHand(handIn);
		if(!stack.isEnchanted()) {
			stack.enchant(Enchantments.THORNS, 2);
		}
		return super.use(worldIn, playerIn, handIn);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(new TranslatableComponent("cactus.armor.text").withStyle(ChatFormatting.GREEN));
	}
}
