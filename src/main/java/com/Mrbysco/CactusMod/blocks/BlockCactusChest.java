package com.Mrbysco.CactusMod.blocks;

import javax.annotation.Nullable;

import com.Mrbysco.CactusMod.CactusMod;
import com.Mrbysco.CactusMod.Reference;
import com.Mrbysco.CactusMod.tileentities.TileEntityCactusChest;

import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCactusChest extends BlockChest{

    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    private static final AxisAlignedBB CACTUS_CHEST_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.875D, 0.9375D);

	public BlockCactusChest(String registryName) {
		super(Type.BASIC);
		
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        
		this.setCreativeTab(CactusMod.cactustab);
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
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return CACTUS_CHEST_AABB;
    }
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
	
	@Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasCustomBreakingProgress(IBlockState state)
    {
        return true;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote)
        {
            return true;
        }
        else
        {
            ILockableContainer ilockablecontainer = this.getLockableContainer(worldIn, pos);

            if (ilockablecontainer != null)
            {
                playerIn.displayGUIChest(ilockablecontainer);
                playerIn.addStat(StatList.CHEST_OPENED);
            }

            return true;
        }
	}
	
	@Nullable
    public ILockableContainer getLockableContainer(World worldIn, BlockPos pos)
    {
        return this.getContainer(worldIn, pos, false);
    }
	
    public ILockableContainer getContainer(World worldIn, BlockPos pos, boolean allowBlocking) {
    	TileEntity tileentity = worldIn.getTileEntity(pos);

        if (!(tileentity instanceof TileEntityCactusChest))
        {
            return null;
        }
        else
        {
            ILockableContainer ilockablecontainer = (TileEntityCactusChest)tileentity;
            if (worldIn.getBlockState(pos.up()).doesSideBlockChestOpening(worldIn, pos.up(), EnumFacing.DOWN))
            {
                return null;
            }
            else
            {
            	return ilockablecontainer;
            }
        }
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityCactusChest();
    }
    
    @Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
        entityIn.attackEntityFrom(DamageSource.CACTUS, 1.0F);
	}
    
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

        if (stack.hasDisplayName())
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityCactusChest)
            {
                ((TileEntityCactusChest)tileentity).setCustomName(stack.getDisplayName());
            }
        }
    }
    
    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
            enumfacing = EnumFacing.NORTH;

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }
    
    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }
    
    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }
    
    @Override
    protected BlockStateContainer createBlockState() {
    	return new BlockStateContainer(this, new IProperty[] {FACING});
    }
    
    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
    	return BlockFaceShape.UNDEFINED;
    }
}
