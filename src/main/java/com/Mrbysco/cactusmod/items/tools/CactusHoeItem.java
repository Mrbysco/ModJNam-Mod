package com.mrbysco.cactusmod.items.tools;

import net.minecraft.item.HoeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;

public class CactusHoeItem extends HoeItem {

	public CactusHoeItem(IItemTier itemTier, int attackDamage, float attackSpeed, Item.Properties properties) {
		super(itemTier, attackDamage, attackSpeed, properties);
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		if(context.getWorld().rand.nextInt(10) < 3)
			context.getPlayer().attackEntityFrom(DamageSource.CACTUS, 1F);
		return super.onItemUse(context);
	}
}
