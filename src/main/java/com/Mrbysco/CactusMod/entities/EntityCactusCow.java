package com.Mrbysco.CactusMod.entities;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityCactusCow extends EntityCow implements net.minecraftforge.common.IShearable{

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
	
	@Nullable
    protected ResourceLocation getLootTable()
    {
		return new ResourceLocation("cactusmod:loot_tables/entities/cactus_cow");
    }

}
