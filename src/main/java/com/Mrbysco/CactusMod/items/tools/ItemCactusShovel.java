package com.Mrbysco.CactusMod.items.tools;

import com.Mrbysco.CactusMod.Reference;
import com.Mrbysco.CactusMod.init.CactusItems;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemCactusShovel extends ItemSpade{
	private final ToolMaterial material;
	
	public ItemCactusShovel(String registryName) {
		super(CactusItems.cactusMaterial);
		
		this.material = CactusItems.cactusMaterial;
        this.maxStackSize = 1;

		this.setUnlocalizedName(Reference.PREFIX + registryName.replaceAll("_", ""));
		this.setRegistryName(registryName);
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
			EntityLivingBase entityLiving) {
		entityLiving.attackEntityFrom(DamageSource.CACTUS, 0.5F);
		return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		player.attackEntityFrom(DamageSource.CACTUS, 0.5F);
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
}
