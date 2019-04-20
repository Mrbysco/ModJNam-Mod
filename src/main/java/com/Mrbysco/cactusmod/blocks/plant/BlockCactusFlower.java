package com.mrbysco.cactusmod.blocks.plant;

import com.mrbysco.cactusmod.CactusMod;
import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.init.CactusBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockCactusFlower extends Block{
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 5);

	public BlockCactusFlower(String registryName) {
		super(Material.PLANTS);
		
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, Integer.valueOf(0)));
		
		setSoundType(SoundType.PLANT);
		setCreativeTab(CactusMod.cactustab);
		setHardness(0.4F);
		setSoundType(SoundType.WOOD);
		
		this.setUnlocalizedName(Reference.PREFIX + registryName.replaceAll("_", ""));
		this.setRegistryName(registryName);
		this.setTickRandomly(true);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Items.AIR;
    }
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!this.canSurviveAt(worldIn, pos))
        {
            worldIn.destroyBlock(pos, true);
        }
        else
        {
            BlockPos blockpos = pos.up();

            if (worldIn.isAirBlock(blockpos) && blockpos.getY() < 256)
            {
                int i = ((Integer)state.getValue(AGE)).intValue();

                if (i < 5 &&  net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, blockpos, state, rand.nextInt(1) == 0))
                {
                    boolean flag = false;
                    boolean flag1 = false;
                    IBlockState iblockstate = worldIn.getBlockState(pos.down());
                    Block block = iblockstate.getBlock();

                    if (block == Blocks.SAND)
                    {
                        flag = true;
                    }
                    else if (block == CactusBlocks.cactus_plant)
                    {
                        int j = 1;

                        for (int k = 0; k < 4; ++k)
                        {
                            Block block1 = worldIn.getBlockState(pos.down(j + 1)).getBlock();

                            if (block1 != CactusBlocks.cactus_plant)
                            {
                                if (block1 == Blocks.SAND)
                                {
                                    flag1 = true;
                                }

                                break;
                            }

                            ++j;
                        }

                        int i1 = 4;

                        if (flag1)
                        {
                            ++i1;
                        }

                        if (j < 2 || rand.nextInt(i1) >= j)
                        {
                            flag = true;
                        }
                    }
                    else if (iblockstate.getMaterial() == Material.AIR)
                    {
                        flag = true;
                    }

                    if (flag && areAllNeighborsEmpty(worldIn, blockpos, (EnumFacing)null) && worldIn.isAirBlock(pos.up(2)))
                    {
                        worldIn.setBlockState(pos, CactusBlocks.cactus_plant.getDefaultState(), 2);
                        this.placeGrownFlower(worldIn, blockpos, i);
                    }
                    else if (i < 4)
                    {
                        int l = rand.nextInt(4);
                        boolean flag2 = false;

                        if (flag1)
                        {
                            ++l;
                        }

                        for (int j1 = 0; j1 < l; ++j1)
                        {
                            EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.random(rand);
                            BlockPos blockpos1 = pos.offset(enumfacing);

                            if (worldIn.isAirBlock(blockpos1) && worldIn.isAirBlock(blockpos1.down()) && areAllNeighborsEmpty(worldIn, blockpos1, enumfacing.getOpposite()))
                            {
                                this.placeGrownFlower(worldIn, blockpos1, i + 1);
                                flag2 = true;
                            }
                        }

                        if (flag2)
                        {
                            worldIn.setBlockState(pos, CactusBlocks.cactus_plant.getDefaultState(), 2);
                        }
                        else
                        {
                            this.placeDeadFlower(worldIn, pos);
                        }
                    }
                    else if (i == 4)
                    {
                        this.placeDeadFlower(worldIn, pos);
                    }
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
                }
            }
        }
    }
	
	@Override
	public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction,
			IPlantable plantable) {
		if(direction == EnumFacing.UP)
		{
			EnumPlantType plant = plantable.getPlantType(world, pos.offset(direction));
			
			switch (plant) {
			case Desert:
				return true;
			default:
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
        entityIn.attackEntityFrom(DamageSource.CACTUS, 1.0F);
	}
	
	private void placeGrownFlower(World worldIn, BlockPos pos, int age)
    {
        worldIn.setBlockState(pos, this.getDefaultState().withProperty(AGE, Integer.valueOf(age)), 2);
        worldIn.playEvent(1033, pos, 0);
    }

    private void placeDeadFlower(World worldIn, BlockPos pos)
    {
        worldIn.setBlockState(pos, this.getDefaultState().withProperty(AGE, Integer.valueOf(5)), 2);
        worldIn.playEvent(1034, pos, 0);
    }

    private static boolean areAllNeighborsEmpty(World worldIn, BlockPos pos, EnumFacing excludingSide)
    {
        for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
        {
            if (enumfacing != excludingSide && !worldIn.isAirBlock(pos.offset(enumfacing)))
            {
                return false;
            }
        }

        return true;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    /**
     * Checks if this block can be placed exactly at the given position.
     */
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return super.canPlaceBlockAt(worldIn, pos) && this.canSurviveAt(worldIn, pos);
    }

    /**
     * Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor
     * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
     * block, etc.
     */
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if (!this.canSurviveAt(worldIn, pos))
        {
            worldIn.scheduleUpdate(pos, this, 1);
        }
    }

    public boolean canSurviveAt(World worldIn, BlockPos pos)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos.down());
        Block block = iblockstate.getBlock();

        if (block != CactusBlocks.cactus_plant && block != Blocks.SAND)
        {
            if (iblockstate.getMaterial() == Material.AIR)
            {
                int i = 0;

                for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
                {
                    IBlockState iblockstate1 = worldIn.getBlockState(pos.offset(enumfacing));
                    Block block1 = iblockstate1.getBlock();

                    if (block1 == CactusBlocks.cactus_plant)
                    {
                        ++i;
                    }
                    else if (iblockstate1.getMaterial() != Material.AIR)
                    {
                        return false;
                    }
                }

                return i == 1;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return true;
        }
    }

    /**
     * Spawns the block's drops in the world. By the time this is called the Block has possibly been set to air via
     * Block.removedByPlayer
     */
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
    {
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        spawnAsEntity(worldIn, pos, new ItemStack(Item.getItemFromBlock(this)));
    }

    protected ItemStack getSilkTouchDrop(IBlockState state)
    {
        return ItemStack.EMPTY;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(AGE, Integer.valueOf(meta));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return ((Integer)state.getValue(AGE)).intValue();
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {AGE});
    }

    public static void generatePlant(World worldIn, BlockPos pos, Random rand, int heigth)
    {
        worldIn.setBlockState(pos, CactusBlocks.cactus_plant.getDefaultState(), 2);
        growTreeRecursive(worldIn, pos, rand, pos, heigth, 0);
    }

    private static void growTreeRecursive(World worldIn, BlockPos pos1, Random rand, BlockPos pos2, int heigth, int p_185601_5_)
    {
        int i = rand.nextInt(3) + 1;

        if (p_185601_5_ == 0)
        {
            ++i;
        }

        for (int j = 0; j < i; ++j)
        {
            BlockPos blockpos = pos1.up(j + 1);

            if (!areAllNeighborsEmpty(worldIn, blockpos, (EnumFacing)null))
            {
                return;
            }

            worldIn.setBlockState(blockpos, CactusBlocks.cactus_plant.getDefaultState(), 2);
        }

        boolean flag = false;

        if (p_185601_5_ < 4)
        {
            int l = rand.nextInt(3);

            if (p_185601_5_ == 0)
            {
                ++l;
            }

            for (int k = 0; k < l; ++k)
            {
                EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.random(rand);
                BlockPos blockpos1 = pos1.up(i).offset(enumfacing);

                if (Math.abs(blockpos1.getX() - pos2.getX()) < heigth && Math.abs(blockpos1.getZ() - pos2.getZ()) < heigth && worldIn.isAirBlock(blockpos1) && worldIn.isAirBlock(blockpos1.down()) && areAllNeighborsEmpty(worldIn, blockpos1, enumfacing.getOpposite()))
                {
                    flag = true;
                    worldIn.setBlockState(blockpos1, CactusBlocks.cactus_plant.getDefaultState(), 2);
                    growTreeRecursive(worldIn, blockpos1, rand, pos2, heigth, p_185601_5_ + 1);
                }
            }
        }

        if (!flag)
        {
            worldIn.setBlockState(pos1.up(i), CactusBlocks.cactus_flower.getDefaultState().withProperty(AGE, Integer.valueOf(5)), 2);
        }
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
}
