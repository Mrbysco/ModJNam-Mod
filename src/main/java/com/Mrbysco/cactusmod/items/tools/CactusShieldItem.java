package com.mrbysco.cactusmod.items.tools;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class CactusShieldItem extends ShieldItem {
	public CactusShieldItem(Item.Properties builder) {
		super(builder);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(new TranslationTextComponent("cactus.shield.text").withStyle(TextFormatting.GREEN));
	}
}
