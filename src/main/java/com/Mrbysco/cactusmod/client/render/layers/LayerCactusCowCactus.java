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

	public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!entitylivingbaseIn.isBaby() && !entitylivingbaseIn.isInvisible()) {
			BlockRenderDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRenderer();
			BlockState blockstate = Blocks.CACTUS.defaultBlockState();
			int i = LivingEntityRenderer.getOverlayCoords(entitylivingbaseIn, 0.0F);
			matrixStackIn.pushPose();
			matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
			matrixStackIn.translate(0.2F, 0.35F, 0.5F);
			matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(42F));
			matrixStackIn.translate(-0.125F, -0.475F, -0.25F);
			matrixStackIn.scale(0.3F, 0.3F, 0.3F);
			blockrendererdispatcher.renderSingleBlock(blockstate, matrixStackIn, bufferIn, packedLightIn, i);
			matrixStackIn.popPose();
			matrixStackIn.pushPose();
			matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
			matrixStackIn.translate(0.1F, 0.0F, -0.6F);
			matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(42.0F));
			matrixStackIn.translate(-0.3125F, -2f/16f, 0.1F);
			matrixStackIn.scale(0.3F, 0.3F, 0.3F);
			blockrendererdispatcher.renderSingleBlock(blockstate, matrixStackIn, bufferIn, packedLightIn, i);
			matrixStackIn.popPose();
			matrixStackIn.pushPose();
			this.getParentModel().getHead().translateAndRotate(matrixStackIn);
			matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
			matrixStackIn.translate(0.0F, 0.7F, -0.2F);
			matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-12F));
			matrixStackIn.translate(-0.175F, -0.575F, 0.6125F);
			matrixStackIn.scale(0.3F, 0.3F, 0.3F);
			blockrendererdispatcher.renderSingleBlock(blockstate, matrixStackIn, bufferIn, packedLightIn, i);
			matrixStackIn.popPose();
		}
	}
}