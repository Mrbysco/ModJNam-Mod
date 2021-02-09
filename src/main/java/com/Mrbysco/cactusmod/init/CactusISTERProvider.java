package com.mrbysco.cactusmod.init;

import com.mrbysco.cactusmod.client.render.block.CactusChestItemStackRenderer;
import com.mrbysco.cactusmod.tileentities.CactusChestTile;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;

import java.util.concurrent.Callable;

public class CactusISTERProvider {
	public static Callable<ItemStackTileEntityRenderer> chest() {
		return () -> new CactusChestItemStackRenderer(CactusChestTile::new);
	}
}
