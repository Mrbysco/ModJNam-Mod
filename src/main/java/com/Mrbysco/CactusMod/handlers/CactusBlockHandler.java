package com.Mrbysco.CactusMod.handlers;

import com.Mrbysco.CactusMod.blocks.BlockCarvedCactus;
import com.Mrbysco.CactusMod.entities.EntityCactusGolem;
import com.Mrbysco.CactusMod.entities.EntityCactusSnowman;
import com.Mrbysco.CactusMod.init.CactusBlocks;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.pattern.BlockMaterialMatcher;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class CactusBlockHandler {
	
	@SubscribeEvent
	public void CactusPlacementCheck(BlockEvent.PlaceEvent event)
	{
		World world = event.getWorld();
		BlockPos pos = event.getPos();
		
		if(!world.isRemote)
		{
			Block block = world.getBlockState(pos).getBlock();
			
			if(block instanceof BlockCarvedCactus)
			{
				spawnGolem(world, pos, block);
			}
		}
	}
	
	@SubscribeEvent
	public void CactusBenchCraft(PlayerEvent.ItemCraftedEvent event)
	{
		IInventory craftmatrix = event.craftMatrix;
		ITextComponent cactusText = new TextComponentTranslation("cactus.workbench.name");
		
		if(craftmatrix.getDisplayName() == cactusText)
		{
			EntityPlayer player = event.player;
			if(event.player.world.rand.nextInt(10) < 4 && player.world.isRemote)
			{
				player.attackEntityFrom(DamageSource.CACTUS, 1.0F);
			}
		}
	}
	
	public static void spawnGolem(World worldIn, BlockPos pos, Block block) {
		BlockPattern.PatternHelper cactusPatternHelper = getCactusGolemPattern(block).match(worldIn, pos);
		if(cactusPatternHelper != null)
		{
			for (int j = 0; j < getCactusGolemPattern(block).getPalmLength(); ++j)
            {
                for (int k = 0; k < getCactusGolemPattern(block).getThumbLength(); ++k)
                {
                    worldIn.setBlockState(cactusPatternHelper.translateOffset(j, k, 0).getPos(), Blocks.AIR.getDefaultState(), 2);
                }
            }

            BlockPos blockpos = cactusPatternHelper.translateOffset(1, 2, 0).getPos();
            EntityCactusGolem cactusGolem = new EntityCactusGolem(worldIn);
            cactusGolem.setPlayerCreated(true);
            cactusGolem.setLocationAndAngles((double)blockpos.getX() + 0.5D, (double)blockpos.getY() + 0.05D, (double)blockpos.getZ() + 0.5D, 0.0F, 0.0F);
            worldIn.spawnEntity(cactusGolem);

            for (EntityPlayerMP entityplayermp1 : worldIn.getEntitiesWithinAABB(EntityPlayerMP.class, cactusGolem.getEntityBoundingBox().grow(5.0D)))
            {
                CriteriaTriggers.SUMMONED_ENTITY.trigger(entityplayermp1, cactusGolem);
            }

            for (int j1 = 0; j1 < 120; ++j1)
            {
                worldIn.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, (double)blockpos.getX() + worldIn.rand.nextDouble(), (double)blockpos.getY() + worldIn.rand.nextDouble() * 3.9D, (double)blockpos.getZ() + worldIn.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
            }

            for (int k1 = 0; k1 < getCactusGolemPattern(block).getPalmLength(); ++k1)
            {
                for (int l1 = 0; l1 < getCactusGolemPattern(block).getThumbLength(); ++l1)
                {
                    BlockWorldState blockworldstate1 = cactusPatternHelper.translateOffset(k1, l1, 0);
                    worldIn.notifyNeighborsRespectDebug(blockworldstate1.getPos(), Blocks.AIR, false);
                }
            }
		}
		else
        {
			cactusPatternHelper = getCactusSnowmanPattern(block).match(worldIn, pos);

            if (cactusPatternHelper != null)
            {
            	for (int i = 0; i < getCactusSnowmanPattern(block).getThumbLength(); ++i)
                {
                    BlockWorldState blockworldstate = cactusPatternHelper.translateOffset(0, i, 0);
                    worldIn.setBlockState(blockworldstate.getPos(), Blocks.AIR.getDefaultState(), 2);
                }

                EntityCactusSnowman cactiMan = new EntityCactusSnowman(worldIn);
                BlockPos blockpos1 = cactusPatternHelper.translateOffset(0, 2, 0).getPos();
                cactiMan.setLocationAndAngles((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.05D, (double)blockpos1.getZ() + 0.5D, 0.0F, 0.0F);
                worldIn.spawnEntity(cactiMan);

                for (EntityPlayerMP entityplayermp : worldIn.getEntitiesWithinAABB(EntityPlayerMP.class, cactiMan.getEntityBoundingBox().grow(5.0D)))
                {
                    CriteriaTriggers.SUMMONED_ENTITY.trigger(entityplayermp, cactiMan);
                }

                for (int l = 0; l < 120; ++l)
                {
                    worldIn.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, (double)blockpos1.getX() + worldIn.rand.nextDouble(), (double)blockpos1.getY() + worldIn.rand.nextDouble() * 2.5D, (double)blockpos1.getZ() + worldIn.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
                }

                for (int i1 = 0; i1 < getCactusSnowmanPattern(block).getThumbLength(); ++i1)
                {
                    BlockWorldState blockworldstate2 = cactusPatternHelper.translateOffset(0, i1, 0);
                    worldIn.notifyNeighborsRespectDebug(blockworldstate2.getPos(), Blocks.AIR, false);
                }
            }
        }
	}
	
	private static BlockPattern cactusGolemPattern;
	
	public static BlockPattern getCactusGolemPattern(Block block)
    {
        if (cactusGolemPattern == null)
        {
        	if(block == CactusBlocks.carved_cactus)
        		cactusGolemPattern = FactoryBlockPattern.start().aisle("~^~", "###", "~#~").where('^', BlockWorldState.hasState(BlockStateMatcher.forBlock(CactusBlocks.carved_cactus))).where('#', BlockWorldState.hasState(BlockStateMatcher.forBlock(CactusBlocks.prickly_iron))).where('~', BlockWorldState.hasState(BlockMaterialMatcher.forMaterial(Material.AIR))).build();
        	else if(block == CactusBlocks.jacko_cactus)
        		cactusGolemPattern = FactoryBlockPattern.start().aisle("~^~", "###", "~#~").where('^', BlockWorldState.hasState(BlockStateMatcher.forBlock(CactusBlocks.jacko_cactus))).where('#', BlockWorldState.hasState(BlockStateMatcher.forBlock(CactusBlocks.prickly_iron))).where('~', BlockWorldState.hasState(BlockMaterialMatcher.forMaterial(Material.AIR))).build();
        }

        return cactusGolemPattern;
    }
	
    private static BlockPattern cactusSnowmanPattern;

	public static BlockPattern getCactusSnowmanPattern(Block block)
    {
        if (cactusSnowmanPattern == null)
        {
        	if(block == CactusBlocks.carved_cactus)
            	cactusSnowmanPattern = FactoryBlockPattern.start().aisle("^", "#", "#").where('^', BlockWorldState.hasState(BlockStateMatcher.forBlock(CactusBlocks.carved_cactus))).where('#', BlockWorldState.hasState(BlockStateMatcher.forBlock(Blocks.CACTUS))).build();
        	else if(block == CactusBlocks.jacko_cactus)
            	cactusSnowmanPattern = FactoryBlockPattern.start().aisle("^", "#", "#").where('^', BlockWorldState.hasState(BlockStateMatcher.forBlock(CactusBlocks.jacko_cactus))).where('#', BlockWorldState.hasState(BlockStateMatcher.forBlock(Blocks.CACTUS))).build();
        }

        return cactusSnowmanPattern;
    }
}
