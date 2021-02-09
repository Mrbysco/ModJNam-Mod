package com.mrbysco.cactusmod.compat.fastbench;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.ModList;

public class FastBenchHelper {

	public static com.mrbysco.cactusmod.blocks.container.CactusFastBenchContainer createFastBenchContainer(int id, PlayerEntity player, World world, BlockPos pos) {
		if(ModList.get().isLoaded("fastbench"))
			return new com.mrbysco.cactusmod.blocks.container.CactusFastBenchContainer(id, player, world, pos);
		else
			return null;
	}

	/**
	 * Separate file to avoid classloading when it's not installed
	 */
	private static final ITextComponent CONTAINER_NAME = new TranslationTextComponent("container.crafting");

	public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
		return new SimpleNamedContainerProvider((id, pInv, player) -> {
			return createFastBenchContainer(id, player, worldIn, pos);
		}, CONTAINER_NAME);
	}
}
