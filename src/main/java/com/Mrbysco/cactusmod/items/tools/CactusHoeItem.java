package com.mrbysco.cactusmod.items.tools;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;

public class CactusHoeItem extends HoeItem {

	public CactusHoeItem(Tier itemTier, int attackDamage, float attackSpeed, Item.Properties properties) {
		super(itemTier, attackDamage, attackSpeed, properties);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		if (context.getLevel().random.nextInt(10) < 3)
			context.getPlayer().hurt(context.getLevel().damageSources().cactus(), 1F);
		return super.useOn(context);
	}
}
