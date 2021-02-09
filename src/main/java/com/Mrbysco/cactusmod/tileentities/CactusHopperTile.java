package com.mrbysco.cactusmod.tileentities;

import com.mrbysco.cactusmod.Reference;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.HopperTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class CactusHopperTile extends HopperTileEntity {
	private int ticksSinceDeleted;

	@Override
	public TileEntityType<?> getType() {
		return super.getType();
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent(Reference.MOD_ID,  "container.cactus_hopper");
	}

	@Override
	public void tick() {
   		if (this.world != null && !this.world.isRemote) {
			--this.transferCooldown;
			++this.ticksSinceDeleted;
			this.tickedGameTime = this.world.getGameTime();
			if (!this.isOnTransferCooldown()) {
				this.setTransferCooldown(0);
				this.updateHopper(() -> {
					return pullItems(this);
				});
			}
			if(!this.isEmpty() && this.ticksSinceDeleted > 60) {
				int randInt = this.world.rand.nextInt(this.getSizeInventory());

				if(this.inventory.get(randInt) != ItemStack.EMPTY) {
					ItemStack stack = getItems().get(randInt);
					stack.shrink(this.world.rand.nextInt(4) + 1);
					this.setInventorySlotContents(randInt, stack);
					this.ticksSinceDeleted = 0;
					this.updateHopper(() -> {
						return pullItems(this);
					});
				}
			}
		}
	}
}
