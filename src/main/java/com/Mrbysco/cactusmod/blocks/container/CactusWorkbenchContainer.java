package com.mrbysco.cactusmod.blocks.container;

import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.MenuType;

public class CactusWorkbenchContainer extends CraftingMenu {

	public CactusWorkbenchContainer(int id, Inventory playerInventory) {
		super(id, playerInventory);
	}

	public CactusWorkbenchContainer(int id, Inventory playerInventory, ContainerLevelAccess levelAccess) {
		super(id, playerInventory, levelAccess);
	}

	@Override
	public MenuType<?> getType() {
		return CactusRegistry.CACTUS_WORKBENCH_CONTAINER.get();
	}

	@Override
	public boolean stillValid(Player playerIn) {
		return stillValid(this.access, playerIn, CactusRegistry.CACTUS_CRAFTING_TABLE.get());
	}
}
