package com.mrbysco.cactusmod.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrbysco.cactusmod.blockentities.CactusChestBlockEntity;
import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class CactusChestItemRenderer extends BlockEntityWithoutLevelRenderer {
	private CactusChestBlockEntity cactusChest = null;
	private BlockEntityRenderDispatcher blockEntityRenderDispatcher = null;

	public CactusChestItemRenderer() {
		super(null, null);
	}

	@Override
	public void renderByItem(ItemStack itemStackIn, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLightIn, int combinedOverlayIn) {
		if(cactusChest == null) {
			this.cactusChest = new CactusChestBlockEntity(BlockPos.ZERO, CactusRegistry.CACTUS_CHEST.get().defaultBlockState());
		}
		if(blockEntityRenderDispatcher == null) {
			final Minecraft minecraft = Minecraft.getInstance();
			blockEntityRenderDispatcher = minecraft.getBlockEntityRenderDispatcher();
		}
		if(blockEntityRenderDispatcher != null) {
			blockEntityRenderDispatcher.renderItem((BlockEntity) cactusChest, poseStack, bufferSource, combinedLightIn, combinedOverlayIn);
		}
	}
}