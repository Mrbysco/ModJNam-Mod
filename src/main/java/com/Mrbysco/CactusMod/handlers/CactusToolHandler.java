package com.Mrbysco.CactusMod.handlers;

import com.Mrbysco.CactusMod.init.CactusItems;
import com.Mrbysco.CactusMod.items.tools.ItemCactusShield;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CactusToolHandler {
	
	@SubscribeEvent
	public void CactusSwordEvent(LivingAttackEvent event)
	{
		boolean flag = event.getSource().getTrueSource() instanceof EntityPlayer;
		if(event.getSource().getDamageType() == "player" && flag)
		{
			EntityPlayer player = (EntityPlayer)event.getSource().getTrueSource();
			ItemStack stack = player.getHeldItemMainhand();
			World world = player.world;
			
			if(stack.getItem() == CactusItems.cactus_sword && !world.isRemote)
			{
				if(world.rand.nextInt(10) < 3)
					player.attackEntityFrom(DamageSource.CACTUS, 1F);
			}
		}
	}
	
	@SubscribeEvent
	public void CactusShieldEvent(LivingHurtEvent event)
	{
		if(event.getEntityLiving() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.getEntityLiving();
			ItemStack heldStack = player.getActiveItemStack();
			World world = player.world;

			if(!heldStack.isEmpty() && heldStack.getItem() == CactusItems.cactus_shield)
			{
				ItemCactusShield shield = (ItemCactusShield) heldStack.getItem();
				
				if(canBlockDamageSource(event.getSource(), player))
				{
					damageShield(player, event.getAmount());
					
					if(world.rand.nextInt(10) <= 5)
					{
						Entity trueSource = event.getSource().getTrueSource();
						trueSource.attackEntityFrom(DamageSource.GENERIC, 1F + MathHelper.floor(event.getAmount()));
					}
				}
			}
		}
	}
	
	private boolean canBlockDamageSource(DamageSource damageSourceIn, EntityLivingBase player)
    {
        if (!damageSourceIn.isUnblockable() && player.isActiveItemStackBlocking())
        {
            Vec3d vec3d = damageSourceIn.getDamageLocation();

            if (vec3d != null)
            {
                Vec3d vec3d1 = player.getLook(1.0F);
                Vec3d vec3d2 = vec3d.subtractReverse(new Vec3d(player.posX, player.posY, player.posZ)).normalize();
                vec3d2 = new Vec3d(vec3d2.x, 0.0D, vec3d2.z);

                if (vec3d2.dotProduct(vec3d1) < 0.0D)
                {
                    return true;
                }
            }
        }

        return false;
    }
	
	public static void damageShield(EntityPlayer player, float damage)
	{
		ItemStack shield = player.getActiveItemStack();
		shield.damageItem(1 + MathHelper.floor(damage), player);

		if (shield.getCount() <= 0)
        {
            ForgeEventFactory.onPlayerDestroyItem(player, shield, player.getActiveHand());
            player.setHeldItem(player.getActiveHand(), ItemStack.EMPTY);
        }
	}
}
