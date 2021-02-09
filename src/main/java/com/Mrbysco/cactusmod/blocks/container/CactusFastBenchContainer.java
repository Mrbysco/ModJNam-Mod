package com.mrbysco.cactusmod.blocks.container;

import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CactusFastBenchContainer extends shadows.fastbench.gui.ContainerFastBench {

	public CactusFastBenchContainer(int id, PlayerInventory pInv) {
		super(id, pInv);
	}

	public CactusFastBenchContainer(int id, PlayerEntity player, World world, BlockPos pos) {
		super(id, player, world, pos);
	}

	@Override
	public ContainerType<?> getType() {
		return com.mrbysco.cactusmod.compat.fastbench.FastBenchCompat.CACTUS_WORKBENCH_CONTAINER.get();
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return isWithinUsableDistance(this.worldPosCallable, playerIn, CactusRegistry.CACTUS_CRAFTING_TABLE.get());
	}
}
