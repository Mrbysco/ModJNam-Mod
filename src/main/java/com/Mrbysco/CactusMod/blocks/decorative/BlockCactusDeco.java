package com.Mrbysco.CactusMod.blocks.decorative;

import com.Mrbysco.CactusMod.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockCactusDeco extends Block{
	public BlockCactusDeco(String registryName) {
		super(Material.CACTUS);

		setHardness(0.4F);
		setSoundType(SoundType.CLOTH);
		
		this.setUnlocalizedName(Reference.PREFIX + registryName.replaceAll("_", ""));
		this.setRegistryName(registryName);
	}
}
