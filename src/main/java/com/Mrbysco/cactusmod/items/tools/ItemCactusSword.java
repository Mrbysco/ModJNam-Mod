package com.mrbysco.cactusmod.items.tools;

import com.mrbysco.cactusmod.CactusMod;
import com.mrbysco.cactusmod.Reference;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemCactusSword extends ItemSword{
	private final float attackDamage;
    private final ToolMaterial material;
    
	public ItemCactusSword(String registryName) {
		super(CactusMod.cactusTool);
		
		this.material = CactusMod.cactusTool;
		
        this.maxStackSize = 1;
        this.setMaxDamage(material.getMaxUses());
        this.attackDamage = 3.0F + material.getAttackDamage();
        
		this.setUnlocalizedName(Reference.PREFIX + registryName.replaceAll("_", ""));
		this.setRegistryName(registryName);
	}
	
	@Override
    public CreativeTabs[] getCreativeTabs() {
    	return new CreativeTabs[] {CreativeTabs.COMBAT, CactusMod.cactustab};
    }
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
			EntityLivingBase entityLiving) {
		if(worldIn.rand.nextInt(10) < 3)
			entityLiving.attackEntityFrom(DamageSource.CACTUS, 1F);
		return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
	}
}