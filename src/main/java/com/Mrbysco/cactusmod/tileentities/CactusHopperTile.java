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
   		if (this.level != null && !this.level.isClientSide) {
			--this.cooldownTime;
			++this.ticksSinceDeleted;
			this.tickedGameTime = this.level.getGameTime();
			if (!this.isOnCooldown()) {
				this.setCooldown(0);
				this.tryMoveItems(() -> {
					return suckInItems(this);
				});
			}
			if(!this.isEmpty() && this.ticksSinceDeleted > 60) {
				int randInt = this.level.random.nextInt(this.getContainerSize());

				if(this.items.get(randInt) != ItemStack.EMPTY) {
					ItemStack stack = getItems().get(randInt);
					stack.shrink(this.level.random.nextInt(4) + 1);
					this.setItem(randInt, stack);
					this.ticksSinceDeleted = 0;
					this.tryMoveItems(() -> {
						return suckInItems(this);
					});
				}
			}
		}
	}
}
