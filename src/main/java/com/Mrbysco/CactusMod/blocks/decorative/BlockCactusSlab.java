package com.Mrbysco.CactusMod.blocks.decorative;

import java.util.Random;

import com.Mrbysco.CactusMod.Reference;
import com.Mrbysco.CactusMod.init.CactusBlocks;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockCactusSlab extends BlockSlab {
    public static final PropertyEnum<BlockCactusSlab.Variant> VARIANT = PropertyEnum.<BlockCactusSlab.Variant>create("variant", BlockCactusSlab.Variant.class);

	public BlockCactusSlab(String registryName) {
		super(Material.CACTUS);
		
        IBlockState iblockstate = this.blockState.getBaseState();
        if (!this.isDouble())
        {
            iblockstate = iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
        }
        this.setDefaultState(iblockstate.withProperty(VARIANT, BlockCactusSlab.Variant.DEFAULT));

		setHardness(0.4F);
		setSoundType(SoundType.CLOTH);
		
		this.setUnlocalizedName(Reference.PREFIX + registryName.replaceAll("_", ""));
		this.setRegistryName(registryName);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(CactusBlocks.cactus_brick_slab);
    }
	
	@Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(CactusBlocks.cactus_brick_slab);
    }
	
	@Override
	public IBlockState getStateFromMeta(int meta)
    {
        IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, BlockCactusSlab.Variant.DEFAULT);

        if (!this.isDouble())
        {
            iblockstate = iblockstate.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
        }

        return iblockstate;
    }

	@Override
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;

        if (!this.isDouble() && state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP)
        {
            i |= 8;
        }

        return i;
    }
	
	@Override
    protected BlockStateContainer createBlockState()
    {
        return this.isDouble() ? new BlockStateContainer(this, new IProperty[] {VARIANT}) : new BlockStateContainer(this, new IProperty[] {HALF, VARIANT});
    }
	
	@Override
	public String getUnlocalizedName(int meta) {
        return super.getUnlocalizedName();
	}

	@Override
	public IProperty<?> getVariantProperty()
    {
        return VARIANT;
    }

	@Override
	public Comparable<?> getTypeForItem(ItemStack stack)
    {
        return BlockCactusSlab.Variant.DEFAULT;
    }
	
	public static class Double extends BlockCactusSlab
    {
        public Double(String registryName) {
			super(registryName);
		}

		public boolean isDouble()
        {
            return true;
        }
    }

	public static class Half extends BlockCactusSlab
	    {
	        public Half(String registryName) {
	        	super(registryName);
	        }

			public boolean isDouble()
	        {
	            return false;
	        }
	    }
	
	public static enum Variant implements IStringSerializable
	{
	    DEFAULT;
	
	    public String getName()
	    {
	        return "default";
	    }
	}
}
