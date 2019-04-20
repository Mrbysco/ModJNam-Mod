package com.mrbysco.cactusmod.tileentities;

import net.minecraft.block.BlockHopper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.VanillaInventoryCodeHooks;

import java.util.Random;

public class TileEntityCactusHopper extends TileEntityHopper{
	
    private NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(5, ItemStack.EMPTY);
    private int transferCooldown = -1;
    private long tickedGameTime;
    private int ticksSinceDeleted;

	@Override
	public String getName() {
        return this.hasCustomName() ? this.customName : "container.cactus.hopper";
	}
	
	@Override
	public int getSizeInventory()
    {
        return 5;
    }

	private boolean isInventoryEmpty()
    {
        for (ItemStack itemstack : this.inventory)
        {
            if (!itemstack.isEmpty())
            {
                return false;
            }
        }

        return true;
    }
	
	@Override
	public boolean isEmpty()
    {
        return this.isInventoryEmpty();
    }
	
	private boolean isFull()
    {
        for (ItemStack itemstack : this.inventory)
        {
            if (itemstack.isEmpty() || itemstack.getCount() != itemstack.getMaxStackSize())
            {
                return false;
            }
        }

        return true;
    }
	
	@Override
	public void update()
    {
        if (this.world != null && !this.world.isRemote)
        {
        	Random rand = this.world.rand;
        	
            --this.transferCooldown;
            ++this.ticksSinceDeleted;
            
            this.tickedGameTime = this.world.getTotalWorldTime();

            if (!this.isOnTransferCooldown())
            {
                this.setTransferCooldown(0);
                this.updateHopper();
            }

            if(!this.isEmpty() && this.ticksSinceDeleted > 60)
			{
				int randInt = rand.nextInt(this.getSizeInventory());
				
				if(inventory.get(randInt) != ItemStack.EMPTY)
				{
					ItemStack stack = getItems().get(randInt);
					int size = stack.getCount();
					
					stack.setCount(size - rand.nextInt(5));
					this.setInventorySlotContents(randInt, stack);
			        this.ticksSinceDeleted = 0;
			        this.updateHopper();
				}
			}
        }
    }
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		this.fillWithLoot((EntityPlayer)null);
        this.getItems().set(index, stack);

        if (stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }
	}
	
	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.inventory;
	}
	
	@Override
    public void setTransferCooldown(int ticks)
    {
        this.transferCooldown = ticks;
    }
        
	private boolean isOnTransferCooldown()
    {
        return this.transferCooldown > 0;
    }
	
	@Override
    public boolean mayTransfer()
    {
        return this.transferCooldown > 8;
    }
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.inventory = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);

        if (!this.checkLootAndRead(compound))
        {
            ItemStackHelper.loadAllItems(compound, this.inventory);
        }

        if (compound.hasKey("CustomName", 8))
        {
            this.customName = compound.getString("CustomName");
        }

        this.transferCooldown = compound.getInteger("TransferCooldown");
    }
	
	@Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        if (!this.checkLootAndWrite(compound))
        {
            ItemStackHelper.saveAllItems(compound, this.inventory);
        }

        compound.setInteger("TransferCooldown", this.transferCooldown);

        if (this.hasCustomName())
        {
            compound.setString("CustomName", this.customName);
        }

        return compound;
    }
	
	private static ItemStack insertStack(IInventory source, IInventory destination, ItemStack stack, int index, EnumFacing direction)
    {
        ItemStack itemstack = destination.getStackInSlot(index);

        if (canInsertItemInSlot(destination, stack, index, direction))
        {
            boolean flag = false;
            boolean flag1 = destination.isEmpty();

            if (itemstack.isEmpty())
            {
                destination.setInventorySlotContents(index, stack);
                stack = ItemStack.EMPTY;
                flag = true;
            }
            else if (canCombine(itemstack, stack))
            {
                int i = stack.getMaxStackSize() - itemstack.getCount();
                int j = Math.min(stack.getCount(), i);
                stack.shrink(j);
                itemstack.grow(j);
                flag = j > 0;
            }

            if (flag)
            {
                if (flag1 && destination instanceof TileEntityCactusHopper)
                {
                	TileEntityCactusHopper tileCactusHopper1 = (TileEntityCactusHopper)destination;

                    if (!tileCactusHopper1.mayTransfer())
                    {
                        int k = 0;

                        if (source != null && source instanceof TileEntityCactusHopper)
                        {
                        	TileEntityCactusHopper tileCactusHopper = (TileEntityCactusHopper)source;

                            if (tileCactusHopper1.tickedGameTime >= tileCactusHopper.tickedGameTime)
                            {
                                k = 1;
                            }
                        }

                        tileCactusHopper1.setTransferCooldown(8 - k);
                    }
                }

                destination.markDirty();
            }
        }

        return stack;
    }
	
	private static boolean canInsertItemInSlot(IInventory inventoryIn, ItemStack stack, int index, EnumFacing side)
    {
        if (!inventoryIn.isItemValidForSlot(index, stack))
        {
            return false;
        }
        else
        {
            return !(inventoryIn instanceof ISidedInventory) || ((ISidedInventory)inventoryIn).canInsertItem(index, stack, side);
        }
    }
	
	private static boolean canCombine(ItemStack stack1, ItemStack stack2)
    {
        if (stack1.getItem() != stack2.getItem())
        {
            return false;
        }
        else if (stack1.getMetadata() != stack2.getMetadata())
        {
            return false;
        }
        else if (stack1.getCount() > stack1.getMaxStackSize())
        {
            return false;
        }
        else
        {
            return ItemStack.areItemStackTagsEqual(stack1, stack2);
        }
    }
	
	@Override
	protected boolean updateHopper()
    {
        if (this.world != null && !this.world.isRemote)
        {
            if (!this.isOnTransferCooldown() && BlockHopper.isEnabled(this.getBlockMetadata()))
            {
                boolean flag = false;

                if (!this.isInventoryEmpty())
                {
                    flag = this.transferItemsOut();
                }

                if (!this.isFull())
                {
                    flag = pullItems(this) || flag;
                }

                if (flag)
                {
                    this.setTransferCooldown(8);
                    this.markDirty();
                    return true;
                }
            }

            return false;
        }
        else
        {
            return false;
        }
    }
	
	private boolean transferItemsOut()
    {
        if (VanillaInventoryCodeHooks.insertHook(this)) { return true; }
        IInventory iinventory = this.getInventoryForHopperTransfer();

        if (iinventory == null)
        {
            return false;
        }
        else
        {
            EnumFacing enumfacing = BlockHopper.getFacing(this.getBlockMetadata()).getOpposite();

            if (this.isInventoryFull(iinventory, enumfacing))
            {
                return false;
            }
            else
            {
                for (int i = 0; i < this.getSizeInventory(); ++i)
                {
                    if (!this.getStackInSlot(i).isEmpty())
                    {
                        ItemStack itemstack = this.getStackInSlot(i).copy();
                        ItemStack itemstack1 = putStackInInventoryAllSlots(this, iinventory, this.decrStackSize(i, 1), enumfacing);

                        if (itemstack1.isEmpty())
                        {
                            iinventory.markDirty();
                            return true;
                        }

                        this.setInventorySlotContents(i, itemstack);
                    }
                }

                return false;
            }
        }
    }
	
	private boolean isInventoryFull(IInventory inventoryIn, EnumFacing side)
    {
        if (inventoryIn instanceof ISidedInventory)
        {
            ISidedInventory isidedinventory = (ISidedInventory)inventoryIn;
            int[] aint = isidedinventory.getSlotsForFace(side);

            for (int k : aint)
            {
                ItemStack itemstack1 = isidedinventory.getStackInSlot(k);

                if (itemstack1.isEmpty() || itemstack1.getCount() != itemstack1.getMaxStackSize())
                {
                    return false;
                }
            }
        }
        else
        {
            int i = inventoryIn.getSizeInventory();

            for (int j = 0; j < i; ++j)
            {
                ItemStack itemstack = inventoryIn.getStackInSlot(j);

                if (itemstack.isEmpty() || itemstack.getCount() != itemstack.getMaxStackSize())
                {
                    return false;
                }
            }
        }

        return true;
    }
	
	private IInventory getInventoryForHopperTransfer()
    {
        EnumFacing enumfacing = BlockHopper.getFacing(this.getBlockMetadata());
        return getInventoryAtPosition(this.getWorld(), this.getXPos() + (double)enumfacing.getFrontOffsetX(), this.getYPos() + (double)enumfacing.getFrontOffsetY(), this.getZPos() + (double)enumfacing.getFrontOffsetZ());
    }
	
    public long getLastUpdateTime() { return tickedGameTime; } // Forge
}
