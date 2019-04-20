package com.mrbysco.cactusmod.blocks.redstone;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.tileentities.TileEntityCactusHopper;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class BlockCactusHopper extends BlockHopper{
	
	public BlockCactusHopper(String registryName) {
	super();

	this.setSoundType(SoundType.CLOTH);
	this.setHardness(1.0F);
	
	this.setUnlocalizedName(Reference.PREFIX + registryName.replaceAll("_", ""));
	this.setRegistryName(registryName);
	}
	
	@Override
	public Material getMaterial(IBlockState state) {
		return Material.CACTUS;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCactusHopper();
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

        if (stack.hasDisplayName())
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityCactusHopper)
            {
                ((TileEntityCactusHopper)tileentity).setCustomName(stack.getDisplayName());
            }
        }
    }
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
        {
            return true;
        }
        else
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityCactusHopper)
            {
                playerIn.displayGUIChest((TileEntityCactusHopper)tileentity);
                playerIn.addStat(StatList.HOPPER_INSPECTED);
            }

            return true;
        }
    }
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof TileEntityCactusHopper)
        {
            InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityCactusHopper)tileentity);
            worldIn.updateComparatorOutputLevel(pos, this);
        }

        super.breakBlock(worldIn, pos, state);
    }
	
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    	super.addInformation(stack, worldIn, tooltip, flagIn);
    	tooltip.add(TextFormatting.GREEN + I18n.format("cactus.hopper.info"));
    }
}
