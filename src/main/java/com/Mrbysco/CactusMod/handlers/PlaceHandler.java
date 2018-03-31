package com.Mrbysco.CactusMod.handlers;

import com.Mrbysco.CactusMod.entities.EntityCactusGolem;
import com.Mrbysco.CactusMod.init.CactusBlocks;
import com.Mrbysco.CactusMod.init.CactusItems;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.pattern.BlockMaterialMatcher;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlaceHandler {
	
	@SubscribeEvent
	public void CactusPlacementCheck(PlayerInteractEvent.RightClickBlock event)
	{
		EntityPlayer player = event.getEntityPlayer();
		ItemStack stack = player.getHeldItemMainhand();
		BlockPos pos = event.getPos();
		World world = player.world;

		if(!world.isRemote)
		{
			if(stack.getItem() == CactusItems.cactus_stick)
			{
				System.out.println(world.getBlockState(pos).getBlock());
				if(world.getBlockState(pos).getBlock() instanceof BlockCactus)
					spawnGolem(world, pos);
			}
		}
	}
	
	public static void spawnGolem(World worldIn, BlockPos pos) {
		BlockPattern.PatternHelper cactusPatternHelper = getCactusGolemPattern().match(worldIn, pos);
		if(cactusPatternHelper != null)
		{
			for (int j = 0; j < getCactusGolemPattern().getPalmLength(); ++j)
            {
                for (int k = 0; k < getCactusGolemPattern().getThumbLength(); ++k)
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

            for (int k1 = 0; k1 < getCactusGolemPattern().getPalmLength(); ++k1)
            {
                for (int l1 = 0; l1 < getCactusGolemPattern().getThumbLength(); ++l1)
                {
                    BlockWorldState blockworldstate1 = cactusPatternHelper.translateOffset(k1, l1, 0);
                    worldIn.notifyNeighborsRespectDebug(blockworldstate1.getPos(), Blocks.AIR, false);
                }
            }
		}
	}
	
	private static BlockPattern cactusGolemPattern;
	
	public static BlockPattern getCactusGolemPattern()
    {
        if (cactusGolemPattern == null)
        {
        	cactusGolemPattern = FactoryBlockPattern.start().aisle("~^~", "###", "~#~").where('^', BlockWorldState.hasState(BlockStateMatcher.forBlock(Blocks.CACTUS))).where('#', BlockWorldState.hasState(BlockStateMatcher.forBlock(CactusBlocks.prickly_iron))).where('~', BlockWorldState.hasState(BlockMaterialMatcher.forMaterial(Material.AIR))).build();
        }

        return cactusGolemPattern;
    }
}
