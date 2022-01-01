package com.mrbysco.cactusmod.client.render.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.mrbysco.cactusmod.entities.CactusCowEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class LayerCactusCowCactus <T extends CactusCowEntity> extends RenderLayer<T, CowModel<T>> {
	public LayerCactusCowCactus(RenderLayerParent<T, CowModel<T>> rendererIn) {
		super(rendererIn);
	}

	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLightIn, T cactusCow, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!cactusCow.isBaby() && !cactusCow.isInvisible()) {
			BlockRenderDispatcher renderDispatcher = Minecraft.getInstance().getBlockRenderer();
			BlockState blockstate = Blocks.CACTUS.defaultBlockState();
			int i = LivingEntityRenderer.getOverlayCoords(cactusCow, 0.0F);
			poseStack.pushPose();
			poseStack.scale(-1.0F, -1.0F, 1.0F);
			poseStack.translate(0.2F, 0.35F, 0.5F);
			poseStack.mulPose(Vector3f.YP.rotationDegrees(42F));
			poseStack.translate(-0.125F, -0.475F, -0.25F);
			poseStack.scale(0.3F, 0.3F, 0.3F);
			renderDispatcher.renderSingleBlock(blockstate, poseStack, bufferSource, packedLightIn, i);
			poseStack.popPose();
			poseStack.pushPose();
			poseStack.scale(-1.0F, -1.0F, 1.0F);
			poseStack.translate(0.1F, 0.0F, -0.6F);
			poseStack.mulPose(Vector3f.YP.rotationDegrees(42.0F));
			poseStack.translate(-0.3125F, -2f/16f, 0.1F);
			poseStack.scale(0.3F, 0.3F, 0.3F);
			renderDispatcher.renderSingleBlock(blockstate, poseStack, bufferSource, packedLightIn, i);
			poseStack.popPose();
			poseStack.pushPose();
			poseStack.scale(-1.0F, -1.0F, 1.0F);
			poseStack.translate(0.0F, 0.7F, -0.2F);
			poseStack.mulPose(Vector3f.YP.rotationDegrees(-12F));
			poseStack.translate(-0.175F, -0.575F, 0.6125F);
			poseStack.scale(0.3F, 0.3F, 0.3F);
			renderDispatcher.renderSingleBlock(blockstate, poseStack, bufferSource, packedLightIn, i);
			poseStack.popPose();
		}
	}
}