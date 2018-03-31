package com.Mrbysco.CactusMod.tileentities;

import java.util.Random;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;

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
        return this.inventory.size();
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
            this.ticksSinceDeleted++;
            
            this.tickedGameTime = this.world.getTotalWorldTime();

            if (!this.isOnTransferCooldown())
            {
                this.setTransferCooldown(0);
                this.updateHopper();
            }
            if(this.ticksSinceDeleted <= 100)
            {
            	if(!this.isEmpty())
    			{
    				int randInt = rand.nextInt(this.getSizeInventory());
    				if(getItems().get(randInt) != ItemStack.EMPTY)
    				{
    					ItemStack stack = getItems().get(randInt);
    					int size = stack.getCount();
    					int minusSize = rand.nextInt(size+1);
    					
    					stack.setCount(size - minusSize);
    					this.setInventorySlotContents(randInt, stack);
    			        this.ticksSinceDeleted = 0;
    			        this.updateHopper();
    				}
    			}
            }
        }
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

                        if (source != null && source instanceof TileEntityHopper)
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
	
    public long getLastUpdateTime() { return tickedGameTime; } // Forge
}
