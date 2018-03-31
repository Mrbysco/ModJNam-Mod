package com.Mrbysco.CactusMod.blocks;

import com.Mrbysco.CactusMod.CactusMod;
import com.Mrbysco.CactusMod.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class BlockPricklyIron extends Block{
    public static final AxisAlignedBB FULL_AAB = new AxisAlignedBB(0.005D, 0.0D, 0.005D, 0.995D, 0.995D, 0.995D);
	public BlockPricklyIron(String registryName) {
		super(Material.IRON);
		
		setHardness(2.0F);
		setResistance(7.0F);
		setSoundType(SoundType.METAL);
		setCreativeTab(CactusMod.cactustab);
		
		this.setUnlocalizedName(Reference.PREFIX + registryName.replaceAll("_", ""));
		this.setRegistryName(registryName);
	}

	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon) {
		return true;
	}
	
	@Override
	public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction,
			IPlantable plantable) {
		if(direction == EnumFacing.UP)
		{
			EnumPlantType plant = plantable.getPlantType(world, pos.offset(direction));
			
			switch (plant) {
			case Desert:
				return true;
			default:
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
		super.onEntityWalk(worldIn, pos, entityIn);
        entityIn.attackEntityFrom(DamageSource.CACTUS, 1.0F);

	}
	
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
        entityIn.attackEntityFrom(DamageSource.CACTUS, 1.0F);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return FULL_AAB;
	}
}
