package com.mrbysco.cactusmod.client.render.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.mrbysco.cactusmod.entities.CactusSnowGolemEntity;
import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SnowGolemModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.item.ItemStack;

public class LayerCactusSnowmanHead extends RenderLayer<CactusSnowGolemEntity, SnowGolemModel<CactusSnowGolemEntity>> {
	public LayerCactusSnowmanHead(RenderLayerParent<CactusSnowGolemEntity, SnowGolemModel<CactusSnowGolemEntity>> renderLayerParent) {
		super(renderLayerParent);
	}

	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLightIn, CactusSnowGolemEntity golemEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!golemEntity.isInvisible() && golemEntity.isCactusEquipped()) {
			poseStack.pushPose();
			this.getParentModel().getHead().translateAndRotate(poseStack);
			float f = 0.625F;
			poseStack.translate(0.0D, -0.34375D, 0.0D);
			poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
			poseStack.scale(0.625F, -0.625F, -0.625F);
			ItemStack itemstack = new ItemStack(CactusRegistry.CARVED_CACTUS.get());
			Minecraft.getInstance().getItemRenderer().renderStatic(golemEntity, itemstack, ItemTransforms.TransformType.HEAD, false, poseStack, bufferSource, golemEntity.level, packedLightIn, LivingEntityRenderer.getOverlayCoords(golemEntity, 0.0F), golemEntity.getId());
			poseStack.popPose();
		}
	}
}