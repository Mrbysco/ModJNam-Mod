package com.mrbysco.cactusmod.tileentities;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.blocks.redstone.CactusChestBlock;
import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.DoubleSidedInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(
		value = Dist.CLIENT,
		_interface = IChestLid.class
)
public class CactusChestTile extends LockableLootTileEntity implements IChestLid, ITickableTileEntity {
	private NonNullList<ItemStack> chestContents = NonNullList.withSize(27, ItemStack.EMPTY);
	/** The current angle of the lid (between 0 and 1) */
	protected float lidAngle;
	/** The angle of the lid last tick */
	protected float prevLidAngle;
	/** The number of players currently using this chest */
	protected int numPlayersUsing;
	/**
	 * A counter that is incremented once each tick. Used to determine when to recompute ; this is done every 200 ticks
	 * (but staggered between different chests). However, the new value isn't actually sent to clients when it is
	 * changed.
	 */
	private int ticksSinceSync;
	private int ticksSinceDeleted;
	private net.minecraftforge.common.util.LazyOptional<net.minecraftforge.items.IItemHandlerModifiable> chestHandler;

	protected CactusChestTile(TileEntityType<?> typeIn) {
		super(typeIn);
	}

	public CactusChestTile() {
		this(CactusRegistry.CACTUS_CHEST_TILE.get());
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	public int getContainerSize() {
		return 27;
	}

	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent(Reference.MOD_ID, "container.cactus_chest");
	}

	public void load(BlockState state, CompoundNBT nbt) {
		super.load(state, nbt);
		this.chestContents = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		if (!this.tryLoadLootTable(nbt)) {
			ItemStackHelper.loadAllItems(nbt, this.chestContents);
		}

	}

	public CompoundNBT save(CompoundNBT compound) {
		super.save(compound);
		if (!this.trySaveLootTable(compound)) {
			ItemStackHelper.saveAllItems(compound, this.chestContents);
		}

		return compound;
	}

	public void tick() {
		int i = this.worldPosition.getX();
		int j = this.worldPosition.getY();
		int k = this.worldPosition.getZ();
		++this.ticksSinceSync;

		this.numPlayersUsing = calculatePlayersUsingSync(this.level, this, this.ticksSinceSync, i, j, k, this.numPlayersUsing);
		this.prevLidAngle = this.lidAngle;
		float f = 0.1F;
		if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F) {
			this.playSound(SoundEvents.CHEST_OPEN);
		}

		if(!this.level.isClientSide) {
			++this.ticksSinceDeleted;
			if(!this.isEmpty()) {
				int randInt = this.level.random.nextInt(getContainerSize());
				if(getItems().get(randInt) != ItemStack.EMPTY && this.ticksSinceDeleted >= 200) {
					ItemStack stack = getItems().get(randInt);
					int size = stack.getCount();
					int minusSize = this.level.random.nextInt(size+1);

					stack.shrink(minusSize);
					this.ticksSinceDeleted = 0;
				}
			}
		}

		if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F || this.numPlayersUsing > 0 && this.lidAngle < 1.0F) {
			float f1 = this.lidAngle;
			if (this.numPlayersUsing > 0) {
				this.lidAngle += 0.1F;
			} else {
				this.lidAngle -= 0.1F;
			}

			if (this.lidAngle > 1.0F) {
				this.lidAngle = 1.0F;
			}

			float f2 = 0.5F;
			if (this.lidAngle < 0.5F && f1 >= 0.5F) {
				this.playSound(SoundEvents.CHEST_CLOSE);
			}

			if (this.lidAngle < 0.0F) {
				this.lidAngle = 0.0F;
			}
		}

	}

	public static int calculatePlayersUsingSync(World p_213977_0_, LockableTileEntity p_213977_1_, int p_213977_2_, int p_213977_3_, int p_213977_4_, int p_213977_5_, int p_213977_6_) {
		if (!p_213977_0_.isClientSide && p_213977_6_ != 0 && (p_213977_2_ + p_213977_3_ + p_213977_4_ + p_213977_5_) % 200 == 0) {
			p_213977_6_ = calculatePlayersUsing(p_213977_0_, p_213977_1_, p_213977_3_, p_213977_4_, p_213977_5_);
		}

		return p_213977_6_;
	}

	public static int calculatePlayersUsing(World p_213976_0_, LockableTileEntity p_213976_1_, int p_213976_2_, int p_213976_3_, int p_213976_4_) {
		int i = 0;
		float f = 5.0F;

		for(PlayerEntity playerentity : p_213976_0_.getEntitiesOfClass(PlayerEntity.class, new AxisAlignedBB((double)((float)p_213976_2_ - 5.0F), (double)((float)p_213976_3_ - 5.0F), (double)((float)p_213976_4_ - 5.0F), (double)((float)(p_213976_2_ + 1) + 5.0F), (double)((float)(p_213976_3_ + 1) + 5.0F), (double)((float)(p_213976_4_ + 1) + 5.0F)))) {
			if (playerentity.containerMenu instanceof ChestContainer) {
				IInventory iinventory = ((ChestContainer)playerentity.containerMenu).getContainer();
				if (iinventory == p_213976_1_ || iinventory instanceof DoubleSidedInventory && ((DoubleSidedInventory)iinventory).contains(p_213976_1_)) {
					++i;
				}
			}
		}

		return i;
	}

	private void playSound(SoundEvent soundIn) {
		double d0 = (double)this.worldPosition.getX();
		double d1 = (double)this.worldPosition.getY();
		double d2 = (double)this.worldPosition.getZ();
		this.level.playSound((PlayerEntity)null, d0, d1, d2, soundIn, SoundCategory.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
	}

	/**
	 * See {@link Block#eventReceived} for more information. This must return true serverside before it is called
	 * clientside.
	 */
	public boolean triggerEvent(int id, int type) {
		if (id == 1) {
			this.numPlayersUsing = type;
			return true;
		} else {
			return super.triggerEvent(id, type);
		}
	}

	public void startOpen(PlayerEntity player) {
		if (!player.isSpectator()) {
			if (this.numPlayersUsing < 0) {
				this.numPlayersUsing = 0;
			}

			++this.numPlayersUsing;
			this.onOpenOrClose();
		}

	}

	public void stopOpen(PlayerEntity player) {
		if (!player.isSpectator()) {
			--this.numPlayersUsing;
			this.onOpenOrClose();
		}

	}

	protected void onOpenOrClose() {
		Block block = this.getBlockState().getBlock();
		if (block instanceof CactusChestBlock) {
			this.level.blockEvent(this.worldPosition, block, 1, this.numPlayersUsing);
			this.level.updateNeighborsAt(this.worldPosition, block);
		}

	}

	protected NonNullList<ItemStack> getItems() {
		return this.chestContents;
	}

	protected void setItems(NonNullList<ItemStack> itemsIn) {
		this.chestContents = itemsIn;
	}

	@OnlyIn(Dist.CLIENT)
	public float getOpenNess(float partialTicks) {
		return MathHelper.lerp(partialTicks, this.prevLidAngle, this.lidAngle);
	}

	public static int getPlayersUsing(IBlockReader reader, BlockPos posIn) {
		BlockState blockstate = reader.getBlockState(posIn);
		if (blockstate.hasTileEntity()) {
			TileEntity tileentity = reader.getBlockEntity(posIn);
			if (tileentity instanceof CactusChestTile) {
				return ((CactusChestTile)tileentity).numPlayersUsing;
			}
		}

		return 0;
	}

	public static void swapContents(CactusChestTile chest, CactusChestTile otherChest) {
		NonNullList<ItemStack> nonnulllist = chest.getItems();
		chest.setItems(otherChest.getItems());
		otherChest.setItems(nonnulllist);
	}

	protected Container createMenu(int id, PlayerInventory player) {
		return ChestContainer.threeRows(id, player, this);
	}

	@Override
	public void clearCache() {
		super.clearCache();
		if (this.chestHandler != null) {
			this.chestHandler.invalidate();
			this.chestHandler = null;
		}
	}

	@Override
	public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> cap, Direction side) {
		if (!this.remove && cap == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (this.chestHandler == null)
				this.chestHandler = net.minecraftforge.common.util.LazyOptional.of(this::createHandler);
			return this.chestHandler.cast();
		}
		return super.getCapability(cap, side);
	}

	private net.minecraftforge.items.IItemHandlerModifiable createHandler() {
		BlockState state = this.getBlockState();
		if (!(state.getBlock() instanceof CactusChestBlock)) {
			return new net.minecraftforge.items.wrapper.InvWrapper(this);
		}
		IInventory inv = (IInventory) this.level.getBlockEntity(worldPosition);
		return new net.minecraftforge.items.wrapper.InvWrapper(inv == null ? this : inv);
	}

	@Override
	protected void invalidateCaps() {
		super.invalidateCaps();
		if (chestHandler != null)
			chestHandler.invalidate();
	}
}
