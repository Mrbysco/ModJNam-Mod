package com.mrbysco.cactusmod.blockentities;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CactusHopperBlockEntity extends HopperBlockEntity {
	private int ticksSinceDeleted;

	public CactusHopperBlockEntity(BlockPos pos, BlockState state) {
		super(pos, state);
	}

	@Override
	public BlockEntityType<?> getType() {
		return CactusRegistry.CACTUS_HOPPER_BLOCK_ENTITY.get();
	}

	@Override
	protected Component getDefaultName() {
		return new TranslatableComponent(Reference.MOD_ID, "container.cactus_hopper");
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, CactusHopperBlockEntity chestBlockEntity) {
		--chestBlockEntity.cooldownTime;
		++chestBlockEntity.ticksSinceDeleted;
		chestBlockEntity.tickedGameTime = level.getGameTime();
		if (!chestBlockEntity.isOnCooldown()) {
			chestBlockEntity.setCooldown(0);
			tryMoveItems(level, pos, state, chestBlockEntity, () -> suckInItems(level, chestBlockEntity));
		}
		if (!chestBlockEntity.isEmpty() && chestBlockEntity.ticksSinceDeleted > 60) {
			int randInt = level.random.nextInt(chestBlockEntity.getContainerSize());

			if (chestBlockEntity.items.get(randInt) != ItemStack.EMPTY) {
				ItemStack stack = chestBlockEntity.getItems().get(randInt);
				stack.shrink(level.random.nextInt(4) + 1);
				chestBlockEntity.setItem(randInt, stack);
				chestBlockEntity.ticksSinceDeleted = 0;
			}
		}
	}
}
