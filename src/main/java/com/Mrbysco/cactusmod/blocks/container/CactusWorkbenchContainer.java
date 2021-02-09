package com.mrbysco.cactusmod.blocks.container;

import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.util.IWorldPosCallable;

public class CactusWorkbenchContainer extends WorkbenchContainer {

	public CactusWorkbenchContainer(int id, PlayerInventory playerInventory) {
		super(id, playerInventory);
	}

	public CactusWorkbenchContainer(int id, PlayerInventory playerInventory, IWorldPosCallable p_i50090_3_) {
		super(id, playerInventory, p_i50090_3_);
	}

	@Override
	public ContainerType<?> getType() {
		return CactusRegistry.CACTUS_WORKBENCH_CONTAINER.get();
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return isWithinUsableDistance(this.worldPosCallable, playerIn, CactusRegistry.CACTUS_CRAFTING_TABLE.get());
	}
}
