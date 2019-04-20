package com.mrbysco.cactusmod.blocks.redstone;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.entities.EntityActualSpike;
import com.mrbysco.cactusmod.entities.EntitySpike;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockCactusDispenser extends Block{
    public static final PropertyDirection FACING = BlockDirectional.FACING;
    public static final PropertyBool TRIGGERED = PropertyBool.create("triggered");

	public BlockCactusDispenser(String registryName) {
		super(Material.CACTUS);

		this.setHardness(0.4F);
        this.setSoundType(SoundType.CLOTH);
		
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(TRIGGERED, Boolean.valueOf(false)));

		this.setUnlocalizedName(Reference.PREFIX + registryName.replaceAll("_", ""));
		this.setRegistryName(registryName);
	}

	@Override
	public int tickRate(World worldIn) {
		return 16;
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        super.onBlockAdded(worldIn, pos, state);
        this.setDefaultDirection(worldIn, pos, state);
    }

    private void setDefaultDirection(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote)
        {
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
            boolean flag = worldIn.getBlockState(pos.north()).isFullBlock();
            boolean flag1 = worldIn.getBlockState(pos.south()).isFullBlock();

            if (enumfacing == EnumFacing.NORTH && flag && !flag1)
            {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && flag1 && !flag)
            {
                enumfacing = EnumFacing.NORTH;
            }
            else
            {
                boolean flag2 = worldIn.getBlockState(pos.west()).isFullBlock();
                boolean flag3 = worldIn.getBlockState(pos.east()).isFullBlock();

                if (enumfacing == EnumFacing.WEST && flag2 && !flag3)
                {
                    enumfacing = EnumFacing.EAST;
                }
                else if (enumfacing == EnumFacing.EAST && flag3 && !flag2)
                {
                    enumfacing = EnumFacing.WEST;
                }
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing).withProperty(TRIGGERED, Boolean.valueOf(false)), 2);
        }
    }
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        worldIn.setBlockState(pos, state.withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer)), 2);
    }
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        boolean flag = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(pos.up());
        boolean flag1 = ((Boolean)state.getValue(TRIGGERED)).booleanValue();

        if (flag && !flag1)
        {
            worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
            worldIn.setBlockState(pos, state.withProperty(TRIGGERED, Boolean.valueOf(true)), 4);
        }
        else if (!flag && flag1)
        {
            worldIn.setBlockState(pos, state.withProperty(TRIGGERED, Boolean.valueOf(false)), 4);
        }
    }
		
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote)
        {
			EnumFacing facing = state.getValue(BlockCactusDispenser.FACING);
			
	        double d0 = pos.getX() + 0.7D * (double)facing.getFrontOffsetX();
	        double d1 = pos.getY() + 0.7D * (double)facing.getFrontOffsetY();
	        double d2 = pos.getZ() + 0.7D * (double)facing.getFrontOffsetZ();

			EntitySpike spike = new EntityActualSpike(worldIn, d0, d1 + 0.5, d2);
			spike.setDamage(0.5);
			
			switch(facing)
	        {
				default:
					spike.posX = d0 + 0.5;
					spike.posY = d1 + 0.5;
					spike.posZ = d2 + 0.5;
					break;
	        }
			
			spike.shoot((double)facing.getFrontOffsetX(), (double)((float)facing.getFrontOffsetY() + 0.1F), (double)facing.getFrontOffsetZ(), 1.1F, 6.0F);
			spike.setKnockbackStrength(1);
			
			worldIn.spawnEntity(spike);
        }
	}
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer)).withProperty(TRIGGERED, Boolean.valueOf(false));
    }
	
	@Override
	public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7)).withProperty(TRIGGERED, Boolean.valueOf((meta & 8) > 0));
    }

	@Override
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | ((EnumFacing)state.getValue(FACING)).getIndex();

        if (((Boolean)state.getValue(TRIGGERED)).booleanValue())
        {
            i |= 8;
        }

        return i;
    }
	
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }
	
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }
	
	@Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING, TRIGGERED});
    }
	
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    	super.addInformation(stack, worldIn, tooltip, flagIn);
    	tooltip.add(TextFormatting.GREEN + I18n.format("cactus.dispenser.info"));
    }
}
