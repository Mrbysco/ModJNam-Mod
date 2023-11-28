package com.mrbysco.cactusmod.items;

import com.mrbysco.cactusmod.entities.SpikeEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class CactusBowItem extends BowItem {
	public static final Predicate<ItemStack> ARROW_ONLY = stack -> stack.is(ItemTags.ARROWS); //TODO: TEST!

	public CactusBowItem(Item.Properties builder) {
		super(builder);
	}

	@Override
	public Predicate<ItemStack> getAllSupportedProjectiles() {
		return ARROW_ONLY;
	}

	@Override
	public int getDefaultProjectileRange() {
		return 16;
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

	@Override
	public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeLeft) {
		if (livingEntity instanceof Player player) {
			int i = this.getUseDuration(stack) - timeLeft;
			if (i < 0) return;

			float f = getSpikeVelocity(i);
			if (!((double) f < 0.1D)) {
				if (!level.isClientSide) {
					SpikeEntity spike = new SpikeEntity(level, livingEntity);
					spike.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, f * 3.0F, 1.0F);
					spike.setDamage(0D);
					spike.setKnockbackStrength(3);

					stack.hurtAndBreak(1, player, (p_220009_1_) -> {
						p_220009_1_.broadcastBreakEvent(player.getUsedItemHand());
					});

					level.addFreshEntity(spike);
				}

				level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (level.random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
				player.awardStat(Stats.ITEM_USED.get(this));
			}
		}
	}

	public static float getSpikeVelocity(int charge) {
		float f = (float) charge / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;
		if (f > 1.0F) {
			f = 1.0F;
		}
		return f;
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 72000;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.BOW;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player playerIn, InteractionHand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		playerIn.startUsingItem(handIn);
		return new InteractionResultHolder<ItemStack>(InteractionResult.SUCCESS, itemstack);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, level, tooltip, flagIn);
		tooltip.add(Component.translatable("cactus.bow.text").withStyle(ChatFormatting.GREEN));
	}
}