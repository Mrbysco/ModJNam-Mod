package com.mrbysco.cactusmod.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.client.ClientHandler;
import com.mrbysco.cactusmod.client.render.models.CactusSpiderModel;
import com.mrbysco.cactusmod.entities.hostile.CactusSpiderEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CactusSpiderRenderer<T extends CactusSpiderEntity> extends MobRenderer<T, CactusSpiderModel<T>> {
	private static final ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, "textures/entity/cactus_spider.png");

	public CactusSpiderRenderer(EntityRendererProvider.Context context) {
		super(context, new CactusSpiderModel(context.bakeLayer(ClientHandler.CACTUS_SPIDER)), 0.25F);
	}

	@Override
	public ResourceLocation getTextureLocation(CactusSpiderEntity entity) {
		return texture;
	}

	protected float getFlipDegrees(T entityLivingBaseIn) {
		return 180.0F;
	}

	@Override
	public void render(T entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
		this.shadowRadius = 0.25F * (float) entityIn.getSpiderSize();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	protected void scale(T entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
		float f = 0.999F;
		matrixStackIn.scale(0.999F, 0.999F, 0.999F);
		float f1 = (float) entitylivingbaseIn.getSpiderSize();
		if (f1 == 1.0F)
			matrixStackIn.scale(0.5F, 0.5F, 0.5F);
	}
}