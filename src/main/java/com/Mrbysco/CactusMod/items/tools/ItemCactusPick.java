package com.Mrbysco.CactusMod.items.tools;

import com.Mrbysco.CactusMod.Reference;
import com.Mrbysco.CactusMod.init.CactusItems;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemCactusPick extends ItemPickaxe{
    private final ToolMaterial material;
	
    public ItemCactusPick(String registryName) {
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
}
