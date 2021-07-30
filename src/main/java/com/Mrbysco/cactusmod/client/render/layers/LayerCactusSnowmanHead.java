package com.mrbysco.cactusmod.client.render.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mrbysco.cactusmod.entities.CactusSnowGolemEntity;
import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.SnowManModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;

public class LayerCactusSnowmanHead extends LayerRenderer<CactusSnowGolemEntity, SnowManModel<CactusSnowGolemEntity>> {
	public LayerCactusSnowmanHead(IEntityRenderer<CactusSnowGolemEntity, SnowManModel<CactusSnowGolemEntity>> p_i50922_1_) {
		super(p_i50922_1_);
	}

	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, CactusSnowGolemEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!entitylivingbaseIn.isInvisible() && entitylivingbaseIn.isCactusEquipped()) {
			matrixStackIn.pushPose();
			this.getParentModel().getHead().translateAndRotate(matrixStackIn);
			float f = 0.625F;
			matrixStackIn.translate(0.0D, -0.34375D, 0.0D);
			matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F));
			matrixStackIn.scale(0.625F, -0.625F, -0.625F);
			ItemStack itemstack = new ItemStack(CactusRegistry.CARVED_CACTUS.get());
			Minecraft.getInstance().getItemRenderer().renderStatic(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.HEAD, false, matrixStackIn, bufferIn, entitylivingbaseIn.level, packedLightIn, LivingRenderer.getOverlayCoords(entitylivingbaseIn, 0.0F));
			matrixStackIn.popPose();
		}
	}
}