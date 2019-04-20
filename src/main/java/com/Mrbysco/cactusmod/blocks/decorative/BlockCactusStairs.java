package com.mrbysco.cactusmod.blocks.decorative;

import com.mrbysco.cactusmod.Reference;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockCactusStairs extends BlockStairs{
	public BlockCactusStairs(String registryName, IBlockState state) {
		super(state);

		setHardness(0.4F);
		setSoundType(SoundType.CLOTH);
		
		this.setUnlocalizedName(Reference.PREFIX + registryName.replaceAll("_", ""));
		this.setRegistryName(registryName);
	}
	
	@Override
	public Material getMaterial(IBlockState state) {
		return Material.CACTUS;
	}
}
