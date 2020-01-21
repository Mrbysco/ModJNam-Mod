package com.mrbysco.cactusmod.blocks;

import com.mrbysco.cactusmod.CactusMod;
import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.init.CactusGuiHandler;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;

import java.util.List;

public class BlockCactusWorkbench extends BlockWorkbench {
	public static final AxisAlignedBB FULL_AAB = new AxisAlignedBB(0.005D, 0.0D, 0.005D, 0.995D, 0.995D, 0.995D);

	public BlockCactusWorkbench(String registryName) {
		super();
		
		this.setHardness(1.5F);
		this.setCreativeTab(CactusMod.cactustab);
		this.setSoundType(SoundType.PLANT);
		
		this.setTranslationKey(Reference.PREFIX + registryName.replaceAll("_", ""));
		this.setRegistryName(registryName);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return FULL_AAB;
	}

	@Override
	public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		super.onEntityCollision(worldIn, pos, state, entityIn);
		entityIn.attackEntityFrom(DamageSource.CACTUS, 1.0F);
	}

	@Override
	public Material getMaterial(IBlockState state) {
		return Material.CACTUS;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
									EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) {
			return true;
		}
		else
		{
			int id = CactusGuiHandler.GUI_WORKBENCH;
			if(Loader.isModLoaded("fastbench"))
			{
				id = CactusGuiHandler.GUI_FAST_WORKBENCH;
			}
			playerIn.addStat(StatList.CRAFTING_TABLE_INTERACTION);
			playerIn.openGui(CactusMod.instance, id, worldIn, pos.getX(), pos.getY(), pos.getZ());
			return true;
		}
	}
    
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    	super.addInformation(stack, worldIn, tooltip, flagIn);
    	tooltip.add(TextFormatting.GREEN + I18n.format("cactus.workbench.info"));
    	tooltip.add(TextFormatting.GREEN + I18n.format("cactus.workbench.info2"));
    }
}
