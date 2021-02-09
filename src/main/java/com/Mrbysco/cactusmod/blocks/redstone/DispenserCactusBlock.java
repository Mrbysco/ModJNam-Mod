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
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(TRIGGERED, Boolean.valueOf(false)));
	}

	public int tickRate() {
		return 16;
	}

	public static IPosition getDispensePosition(IBlockSource coords) {
		Direction direction = coords.getBlockState().get(FACING);
		double d0 = coords.getX() + 0.7D * (double)direction.getXOffset();
		double d1 = coords.getY() + 0.7D * (double)direction.getYOffset();
		double d2 = coords.getZ() + 0.7D * (double)direction.getZOffset();
		return new Position(d0, d1, d2);
	}

	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}


	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(FACING, rot.rotate(state.get(FACING)));
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.toRotation(state.get(FACING)));
	}

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING, TRIGGERED);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(FACING, context.getNearestLookingDirection().getOpposite());
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		boolean flag = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(pos.up());
		boolean flag1 = state.get(TRIGGERED);
		if (flag && !flag1) {
			worldIn.getPendingBlockTicks().scheduleTick(pos, this, 4);
			worldIn.setBlockState(pos, state.with(TRIGGERED, Boolean.valueOf(true)), 4);
		} else if (!flag && flag1) {
			worldIn.setBlockState(pos, state.with(TRIGGERED, Boolean.valueOf(false)), 4);
		}
	}

	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
		this.dispense(worldIn, pos);
	}

	protected void dispense(ServerWorld worldIn, BlockPos pos) {
		BlockState state = worldIn.getBlockState(pos);
		if(state.getBlock() == this) {
			Direction direction = state.get(DispenserBlock.FACING);
			SpikeEntity spikeEntity = new SpikeEntity(worldIn, pos.getX(), pos.getY(), pos.getZ());
			spikeEntity.setKnockbackStrength(1);
			spikeEntity.shoot((double)direction.getXOffset(), (double)((float)direction.getYOffset() + 0.1F), (double)direction.getZOffset(), 1.1F, 6.0F);
			worldIn.addEntity(spikeEntity);
		}
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(new TranslationTextComponent("cactus.dispenser.info").mergeStyle(TextFormatting.GREEN));
	}
}
