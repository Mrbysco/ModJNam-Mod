package com.mrbysco.cactusmod.blocks.redstone;

import com.mrbysco.cactusmod.tileentities.CactusHopperTile;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.HopperBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;
import java.util.List;

public class CactusHopperBlock extends HopperBlock {
	
	public CactusHopperBlock(AbstractBlock.Properties builder) {
        super(builder);
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		return new CactusHopperTile();
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(new TranslationTextComponent("cactus.hopper.info").mergeStyle(TextFormatting.GREEN));
	}
}
