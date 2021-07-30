package com.mrbysco.cactusmod.items;

import com.mrbysco.cactusmod.entities.SpikeEntity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class CactusBowItem extends Item implements IVanishable {
    public CactusBowItem(Item.Properties builder) {
        super(builder);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity)entityLiving;
            int i = this.getUseDuration(stack) - timeLeft;
            if (i < 0) return;

            float f = getSpikeVelocity(i);
            if (!((double)f < 0.1D)) {
                if (!worldIn.isClientSide) {
                    SpikeEntity spike = new SpikeEntity(worldIn, entityLiving);
                    spike.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot, 0.0F, f * 3.0F, 1.0F);
                    spike.setDamage(0D);
                    spike.setKnockbackStrength(3);

                    stack.hurtAndBreak(1, playerentity, (p_220009_1_) -> {
                        p_220009_1_.broadcastBreakEvent(playerentity.getUsedItemHand());
                    });

                    worldIn.addFreshEntity(spike);
                }

                worldIn.playSound((PlayerEntity)null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), SoundEvents.ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                playerentity.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    public static float getSpikeVelocity(int charge) {
        float f = (float)charge / 20.0F;
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
    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        playerIn.startUsingItem(handIn);
        return new ActionResult<ItemStack>(ActionResultType.SUCCESS, itemstack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("cactus.bow.text").withStyle(TextFormatting.GREEN));
    }
}