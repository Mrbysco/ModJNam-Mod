package com.mrbysco.cactusmod.blocks.redstone;

import com.mrbysco.cactusmod.init.CactusRegistry;
import com.mrbysco.cactusmod.tileentities.CactusChestTile;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractChestBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.tileentity.TileEntityMerger.ICallbackWrapper;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

public class CactusChestBlock extends AbstractChestBlock<CactusChestTile> implements IWaterLoggable {
	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	protected static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);
	private final Supplier<TileEntityType<? extends CactusChestTile>> tileEntityTypeSupplier = () -> CactusRegistry.CACTUS_CHEST_TILE.get();

	public CactusChestBlock(AbstractBlock.Properties builder) {
		super(builder, () -> {
			return CactusRegistry.CACTUS_CHEST_TILE.get();
		});
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.valueOf(false)));
	}

	public TileEntityMerger.ICallbackWrapper<? extends CactusChestTile> getWrapper(BlockState state, World world, BlockPos pos, boolean override) {
		BiPredicate<IWorld, BlockPos> biPredicate;
		if (override) {
			biPredicate = (p_226918_0_, p_226918_1_) -> false;
		}
		else {
			biPredicate = CactusChestBlock::isBlocked;
		}

		return TileEntityMerger.combineWithNeigbour(tileEntityTypeSupplier.get(), CactusChestBlock::getMergerType, CactusChestBlock::getDirectionToAttached, FACING, state, world, pos, biPredicate);
	}

	public static TileEntityMerger.Type getMergerType(BlockState blockState) {
		return TileEntityMerger.Type.SINGLE;
	}

	public static Direction getDirectionToAttached(BlockState state) {
		Direction direction = state.getValue(FACING);
		return direction.getCounterClockWise();
	}

	@Nullable
	@Override
	public TileEntity newBlockEntity(IBlockReader worldIn) {
		return new CactusChestTile();
	}

	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (worldIn.isClientSide) {
			return ActionResultType.SUCCESS;
		} else {
			INamedContainerProvider inamedcontainerprovider = this.getMenuProvider(state, worldIn, pos);
			if (inamedcontainerprovider != null) {
				player.openMenu(inamedcontainerprovider);
				player.awardStat(this.getOpenStat());
			}

			return ActionResultType.CONSUME;
		}
	}

	protected Stat<ResourceLocation> getOpenStat() {
		return Stats.CUSTOM.get(Stats.OPEN_CHEST);
	}


	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	public BlockState getStateForPlacement(BlockItemUseContext context) {
		FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
	}

	public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		if (stack.hasCustomHoverName()) {
			TileEntity tileentity = worldIn.getBlockEntity(pos);
			if (tileentity instanceof CactusChestTile) {
				((CactusChestTile)tileentity).setCustomName(stack.getHoverName());
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static TileEntityMerger.ICallback<CactusChestTile, Float2FloatFunction> getLidRotationCallback(final IChestLid lid) {
		return new TileEntityMerger.ICallback<CactusChestTile, Float2FloatFunction>() {
			public Float2FloatFunction acceptDouble(CactusChestTile p_225539_1_, CactusChestTile p_225539_2_) {
				return (angle) -> {
					return Math.max(p_225539_1_.getOpenNess(angle), p_225539_2_.getOpenNess(angle));
				};
			}

			public Float2FloatFunction acceptSingle(CactusChestTile p_225538_1_) {
				return p_225538_1_::getOpenNess;
			}

			public Float2FloatFunction acceptNone() {
				return lid::getOpenNess;
			}
		};
	}

	public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!state.is(newState.getBlock())) {
			TileEntity tileentity = worldIn.getBlockEntity(pos);
			if (tileentity instanceof IInventory) {
				InventoryHelper.dropContents(worldIn, pos, (IInventory)tileentity);
				worldIn.updateNeighbourForOutputSignal(pos, this);
			}

			super.onRemove(state, worldIn, pos, newState, isMoving);
		}
	}

	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING, WATERLOGGED);
	}

	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.getValue(WATERLOGGED)) {
			worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
		}

		return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	public boolean isPathfindable(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		return false;
	}

	public static boolean isBlocked(IWorld world, BlockPos pos) {
		return isBelowSolidBlock(world, pos) || isCatSittingOn(world, pos);
	}

	private static boolean isBelowSolidBlock(IBlockReader reader, BlockPos worldIn) {
		BlockPos blockpos = worldIn.above();
		return reader.getBlockState(blockpos).isRedstoneConductor(reader, blockpos);
	}

	private static boolean isCatSittingOn(IWorld world, BlockPos pos) {
		List<CatEntity> list = world.getEntitiesOfClass(CatEntity.class, new AxisAlignedBB((double)pos.getX(), (double)(pos.getY() + 1), (double)pos.getZ(), (double)(pos.getX() + 1), (double)(pos.getY() + 2), (double)(pos.getZ() + 1)));
		if (!list.isEmpty()) {
			for(CatEntity catentity : list) {
				if (catentity.isInSittingPose()) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	public int getAnalogOutputSignal(BlockState blockState, World worldIn, BlockPos pos) {
		return Container.getRedstoneSignalFromContainer((IInventory) worldIn.getBlockEntity(pos));
	}

	@Override
	public ICallbackWrapper<? extends ChestTileEntity> combine(BlockState state, World world, BlockPos pos, boolean override) {
		return TileEntityMerger.ICallback::acceptNone;
	}
}
