package com.mrbysco.cactusmod.blocks.redstone;

import com.mrbysco.cactusmod.entities.SpikeEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class CactusDispenserBlock extends Block {
	public static final DirectionProperty FACING = DirectionalBlock.FACING;
	public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;

	public CactusDispenserBlock(BlockBehaviour.Properties builder) {
		super(builder);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(TRIGGERED, Boolean.valueOf(false)));
	}

	public int tickRate() {
		return 16;
	}

	public static BlockPos getDispensePosition(BlockState state, BlockPos pos) {
		Direction direction = state.getValue(FACING);
		double d0 = pos.getX() + 0.7D * (double) direction.getStepX();
		double d1 = pos.getY() + 0.7D * (double) direction.getStepY();
		double d2 = pos.getZ() + 0.7D * (double) direction.getStepZ();
		return BlockPos.containing(d0, d1, d2);
	}

	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}


	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, TRIGGERED);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
	}

	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		boolean flag = level.hasNeighborSignal(pos) || level.hasNeighborSignal(pos.above());
		boolean flag1 = state.getValue(TRIGGERED);
		if (flag && !flag1) {
			level.scheduleTick(pos, this, 4);
			level.setBlock(pos, state.setValue(TRIGGERED, Boolean.valueOf(true)), 4);
		} else if (!flag && flag1) {
			level.setBlock(pos, state.setValue(TRIGGERED, Boolean.valueOf(false)), 4);
		}
	}

	public void tick(BlockState state, ServerLevel level, BlockPos pos, Random rand) {
		this.dispenseFrom(level, pos);
	}

	protected void dispenseFrom(ServerLevel level, BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		if (state.getBlock() == this) {
			Direction direction = state.getValue(DispenserBlock.FACING);

			double x = pos.getX() + 0.7D * (double) direction.getStepX() + 0.5;
			double y = pos.getY() + 0.7D * (double) direction.getStepY() - 0.5;
			double z = pos.getZ() + 0.7D * (double) direction.getStepZ() + 0.5;


			SpikeEntity spikeEntity = new SpikeEntity(level, z, y, z);
			spikeEntity.setPos(x, y + 1, z);
			spikeEntity.setKnockbackStrength(1);
			spikeEntity.shoot((double) direction.getStepX(), (double) ((float) direction.getStepY() + 0.1F), (double) direction.getStepZ(), 1.1F, 6.0F);

			level.addFreshEntity(spikeEntity);
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, level, tooltip, flagIn);
		tooltip.add(Component.translatable("cactus.dispenser.info").withStyle(ChatFormatting.GREEN));
	}
}
