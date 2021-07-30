package com.mrbysco.cactusmod.blocks.redstone;

import com.mrbysco.cactusmod.entities.SpikeEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.Position;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class DispenserCactusBlock extends Block{
	public static final DirectionProperty FACING = DirectionalBlock.FACING;
	public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;

	public DispenserCactusBlock(AbstractBlock.Properties builder) {
		super(builder);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(TRIGGERED, Boolean.valueOf(false)));
	}

	public int tickRate() {
		return 16;
	}

	public static IPosition getDispensePosition(IBlockSource coords) {
		Direction direction = coords.getBlockState().getValue(FACING);
		double d0 = coords.x() + 0.7D * (double)direction.getStepX();
		double d1 = coords.y() + 0.7D * (double)direction.getStepY();
		double d2 = coords.z() + 0.7D * (double)direction.getStepZ();
		return new Position(d0, d1, d2);
	}

	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.MODEL;
	}


	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING, TRIGGERED);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		boolean flag = worldIn.hasNeighborSignal(pos) || worldIn.hasNeighborSignal(pos.above());
		boolean flag1 = state.getValue(TRIGGERED);
		if (flag && !flag1) {
			worldIn.getBlockTicks().scheduleTick(pos, this, 4);
			worldIn.setBlock(pos, state.setValue(TRIGGERED, Boolean.valueOf(true)), 4);
		} else if (!flag && flag1) {
			worldIn.setBlock(pos, state.setValue(TRIGGERED, Boolean.valueOf(false)), 4);
		}
	}

	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
		this.dispense(worldIn, pos);
	}

	protected void dispense(ServerWorld worldIn, BlockPos pos) {
		BlockState state = worldIn.getBlockState(pos);
		if(state.getBlock() == this) {
			Direction direction = state.getValue(DispenserBlock.FACING);
			SpikeEntity spikeEntity = new SpikeEntity(worldIn, pos.getX(), pos.getY(), pos.getZ());
			spikeEntity.setKnockbackStrength(1);
			spikeEntity.shoot((double)direction.getStepX(), (double)((float)direction.getStepY() + 0.1F), (double)direction.getStepZ(), 1.1F, 6.0F);
			worldIn.addFreshEntity(spikeEntity);
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(new TranslationTextComponent("cactus.dispenser.info").withStyle(TextFormatting.GREEN));
	}
}
