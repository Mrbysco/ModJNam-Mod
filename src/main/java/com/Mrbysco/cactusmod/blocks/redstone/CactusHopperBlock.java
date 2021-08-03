package com.mrbysco.cactusmod.blocks.redstone;

import com.mrbysco.cactusmod.blockentities.CactusHopperBlockEntity;
import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class CactusHopperBlock extends HopperBlock {
	
	public CactusHopperBlock(BlockBehaviour.Properties builder) {
        super(builder);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new CactusHopperBlockEntity(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
		return level.isClientSide ? null : createTickerHelper(blockEntityType, CactusRegistry.CACTUS_HOPPER_BLOCK_ENTITY.get(), CactusHopperBlockEntity::serverTick);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(new TranslatableComponent("cactus.hopper.info").withStyle(ChatFormatting.GREEN));
	}
}
