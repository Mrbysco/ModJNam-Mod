package com.mrbysco.cactusmod.client.render.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mrbysco.cactusmod.entities.CactusCowEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.vector.Vector3f;

public class LayerCactusCowCactus <T extends CactusCowEntity> extends LayerRenderer<T, CowModel<T>> {
	public LayerCactusCowCactus(IEntityRenderer<T, CowModel<T>> rendererIn) {
		super(rendererIn);
	}

	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!entitylivingbaseIn.isBaby() && !entitylivingbaseIn.isInvisible()) {
			BlockRendererDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRenderer();
			BlockState blockstate = Blocks.CACTUS.defaultBlockState();
			int i = LivingRenderer.getOverlayCoords(entitylivingbaseIn, 0.0F);
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

			ModelRenderer head = this.getParentModel().getHead();
			matrixStackIn.translate((double)(head.x / 16.0F), (double)(head.y / 16.0F), (double)(head.z / 16.0F));

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