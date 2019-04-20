package com.mrbysco.cactusmod.blocks.decorative;

import com.mrbysco.cactusmod.Reference;
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
