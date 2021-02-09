package com.mrbysco.cactusmod.util;

import com.mrbysco.cactusmod.blocks.container.CactusWorkbenchContainer;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.ModList;

public class WorkBenchHelper {
	private static final ITextComponent CONTAINER_NAME = new TranslationTextComponent("container.crafting");

	public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
		if(!ModList.get().isLoaded("fastbench")) {
			return new SimpleNamedContainerProvider((id, inventory, player) -> {
				return new CactusWorkbenchContainer(id, inventory, IWorldPosCallable.of(worldIn, pos));
			}, CONTAINER_NAME);
		} else {
			return new com.mrbysco.cactusmod.compat.fastbench.FastBenchHelper().getContainer(state, worldIn, pos);
		}
	}
}
