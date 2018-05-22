package com.Mrbysco.CactusMod.blocks;

import javax.annotation.Nullable;

import com.Mrbysco.CactusMod.CactusMod;
import com.Mrbysco.CactusMod.Reference;
import com.Mrbysco.CactusMod.entities.EntityCactusGolem;
import com.Mrbysco.CactusMod.entities.EntityCactusSnowman;
import com.Mrbysco.CactusMod.init.CactusBlocks;
import com.google.common.base.Predicate;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMaterialMatcher;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCarvedCactus extends BlockHorizontal{
	protected static final AxisAlignedBB CACTUS_COLLISION_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.9375D, 0.9375D);
    protected static final AxisAlignedBB CACTUS_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 1.0D, 0.9375D);
    
    private static BlockPattern cactusGolemPattern;
    private static BlockPattern cactusSnowmanPattern;
    private static final Predicate<IBlockState> IS_CARVED_CACTUS = new Predicate<IBlockState>()
    {
        public boolean apply(@Nullable IBlockState p_apply_1_)
        {
            return p_apply_1_ != null && (p_apply_1_.getBlock() == CactusBlocks.carved_cactus || p_apply_1_.getBlock() == CactusBlocks.jacko_cactus);
        }
    };
    
    
	public BlockCarvedCactus(String registryName) {
		super(Material.CACTUS);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.setTickRandomly(true);

		setHardness(0.4F);
		setSoundType(SoundType.CLOTH);
		setCreativeTab(CactusMod.cactustab);
		
		this.setUnlocalizedName(Reference.PREFIX + registryName.replaceAll("_", ""));
		this.setRegistryName(registryName);
	}
	
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
        entityIn.attackEntityFrom(DamageSource.CACTUS, 1.0F);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return CACTUS_COLLISION_AABB;
    }

	@Override
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos)
    {
        return CACTUS_AABB.offset(pos);
    }
	
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
	}
	
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

	@Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

	@Override
    public int getMetaFromState(IBlockState state)
    {
        return ((EnumFacing)state.getValue(FACING)).getHorizontalIndex();
    }
	
	@Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }
	
	@Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
    
    @Override
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
    
    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
    	super.onBlockAdded(worldIn, pos, state);
        this.trySpawnGolem(worldIn, pos);
    }
    
    private void trySpawnGolem(World worldIn, BlockPos pos)
    {
        BlockPattern.PatternHelper blockpattern$patternhelper = this.getCactusSnowmanPattern().match(worldIn, pos);

        if (blockpattern$patternhelper != null)
        {
            for (int i = 0; i < this.getCactusSnowmanPattern().getThumbLength(); ++i)
            {
                BlockWorldState blockworldstate = blockpattern$patternhelper.translateOffset(0, i, 0);
                worldIn.setBlockState(blockworldstate.getPos(), Blocks.AIR.getDefaultState(), 2);
            }

            EntityCactusSnowman entitycactusman = new EntityCactusSnowman(worldIn);
            BlockPos blockpos1 = blockpattern$patternhelper.translateOffset(0, 2, 0).getPos();
            entitycactusman.setLocationAndAngles((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.05D, (double)blockpos1.getZ() + 0.5D, 0.0F, 0.0F);
            worldIn.spawnEntity(entitycactusman);

            for (EntityPlayerMP entityplayermp : worldIn.getEntitiesWithinAABB(EntityPlayerMP.class, entitycactusman.getEntityBoundingBox().grow(5.0D)))
            {
                CriteriaTriggers.SUMMONED_ENTITY.trigger(entityplayermp, entitycactusman);
            }

            for (int l = 0; l < 120; ++l)
            {
                worldIn.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, (double)blockpos1.getX() + worldIn.rand.nextDouble(), (double)blockpos1.getY() + worldIn.rand.nextDouble() * 2.5D, (double)blockpos1.getZ() + worldIn.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
            }

            for (int i1 = 0; i1 < this.getCactusSnowmanPattern().getThumbLength(); ++i1)
            {
                BlockWorldState blockworldstate2 = blockpattern$patternhelper.translateOffset(0, i1, 0);
                worldIn.notifyNeighborsRespectDebug(blockworldstate2.getPos(), Blocks.AIR, false);
            }
        }
        else
        {
            blockpattern$patternhelper = this.getCactusGolemPattern().match(worldIn, pos);

            if (blockpattern$patternhelper != null)
            {
                for (int j = 0; j < this.getCactusGolemPattern().getPalmLength(); ++j)
                {
                    for (int k = 0; k < this.getCactusGolemPattern().getThumbLength(); ++k)
                    {
                        worldIn.setBlockState(blockpattern$patternhelper.translateOffset(j, k, 0).getPos(), Blocks.AIR.getDefaultState(), 2);
                    }
                }

                BlockPos blockpos = blockpattern$patternhelper.translateOffset(1, 2, 0).getPos();
                EntityCactusGolem entitycactusgolem = new EntityCactusGolem(worldIn);
                entitycactusgolem.setPlayerCreated(true);
                entitycactusgolem.setLocationAndAngles((double)blockpos.getX() + 0.5D, (double)blockpos.getY() + 0.05D, (double)blockpos.getZ() + 0.5D, 0.0F, 0.0F);
                worldIn.spawnEntity(entitycactusgolem);

                for (EntityPlayerMP entityplayermp1 : worldIn.getEntitiesWithinAABB(EntityPlayerMP.class, entitycactusgolem.getEntityBoundingBox().grow(5.0D)))
                {
                    CriteriaTriggers.SUMMONED_ENTITY.trigger(entityplayermp1, entitycactusgolem);
                }

                for (int j1 = 0; j1 < 120; ++j1)
                {
                    worldIn.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, (double)blockpos.getX() + worldIn.rand.nextDouble(), (double)blockpos.getY() + worldIn.rand.nextDouble() * 3.9D, (double)blockpos.getZ() + worldIn.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
                }

                for (int k1 = 0; k1 < this.getCactusGolemPattern().getPalmLength(); ++k1)
                {
                    for (int l1 = 0; l1 < this.getCactusGolemPattern().getThumbLength(); ++l1)
                    {
                        BlockWorldState blockworldstate1 = blockpattern$patternhelper.translateOffset(k1, l1, 0);
                        worldIn.notifyNeighborsRespectDebug(blockworldstate1.getPos(), Blocks.AIR, false);
                    }
                }
            }
        }
    }
    
    protected BlockPattern getCactusSnowmanPattern()
    {
        if (this.cactusSnowmanPattern == null)
        {
        	cactusSnowmanPattern = FactoryBlockPattern.start().aisle("^", "#", "#").where('^', BlockWorldState.hasState(IS_CARVED_CACTUS)).where('#', BlockWorldState.hasState(BlockStateMatcher.forBlock(Blocks.CACTUS))).build();
        }

        return this.cactusSnowmanPattern;
    }
    
    protected BlockPattern getCactusGolemPattern()
    {
        if (this.cactusGolemPattern == null)
        {
    		cactusGolemPattern = FactoryBlockPattern.start().aisle("~^~", "###", "~#~").where('^', BlockWorldState.hasState(IS_CARVED_CACTUS)).where('#', BlockWorldState.hasState(BlockStateMatcher.forBlock(CactusBlocks.prickly_iron))).where('~', BlockWorldState.hasState(BlockMaterialMatcher.forMaterial(Material.AIR))).build();
        }

        return this.cactusGolemPattern;
    }
}
