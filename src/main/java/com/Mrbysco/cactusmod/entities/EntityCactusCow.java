package com.mrbysco.cactusmod.entities;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.init.CactusItems;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class EntityCactusCow extends EntityCow implements net.minecraftforge.common.IShearable, ICactusMob{

	public EntityCactusCow(World worldIn) {
		super(worldIn);
	}
	
	public EntityCactusCow createChild(EntityAgeable ageable)
    {
        return new EntityCactusCow(this.world);
    }

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
		return getGrowingAge() >= 0;
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		this.setDead();
		
		WorldServer server = (WorldServer) this.world;
		server.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, false, this.posX, this.posY + (double)(this.height / 2.0F), this.posZ, 1, 0.0D, 0.0D, 0.0D, 0.0D);

        EntityCow cow = new EntityCow(this.world);
        cow.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
        cow.setHealth(this.getHealth());
        cow.renderYawOffset = this.renderYawOffset;

        if (this.hasCustomName())
        	cow.setCustomNameTag(this.getCustomNameTag());

        this.world.spawnEntity(cow);

        List<ItemStack> ret = new ArrayList<ItemStack>();
        for (int i = 0; i < 5; ++i)
        {
            ret.add(new ItemStack(Blocks.CACTUS));
        }

        this.playSound(SoundEvents.ENTITY_MOOSHROOM_SHEAR, 1.0F, 1.0F);
        return ret;
	}
	
	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		ItemStack itemstack = player.getHeldItem(hand);

        if (itemstack.getItem() == Items.GLASS_BOTTLE && this.getGrowingAge() >= 0 && !player.capabilities.isCreativeMode)
        {
            itemstack.shrink(1);

            if (itemstack.isEmpty())
            {
                player.setHeldItem(hand, new ItemStack(CactusItems.cactus_juice));
            }
            else if (!player.inventory.addItemStackToInventory(new ItemStack(CactusItems.cactus_juice)))
            {
                player.dropItem(new ItemStack(CactusItems.cactus_juice), false);
            }

            return true;
        }
        else
        {
            return super.processInteract(player, hand);
        }
	}
	
	@Override
	@Nullable
    protected ResourceLocation getLootTable()
    {
		return new ResourceLocation(Reference.PREFIX + "entities/cactus_cow");
    }
}
