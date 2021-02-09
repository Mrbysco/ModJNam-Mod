package com.mrbysco.cactusmod.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.client.render.models.CactusSpiderModel;
import com.mrbysco.cactusmod.entities.hostile.CactusSpiderEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class CactusSpiderRenderer<T extends CactusSpiderEntity> extends MobRenderer<T, CactusSpiderModel<T>> {
	private static final ResourceLocation texture = new ResourceLocation(Reference.PREFIX + "textures/entity/cactus_spider.png");

	public CactusSpiderRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new CactusSpiderModel(), 0.25F);
//		this.addLayer(new SpiderEyesLayer<>(this));
	}

	@Override
	public ResourceLocation getEntityTexture(CactusSpiderEntity entity) {
		return texture;
	}

	protected float getDeathMaxRotation(T entityLivingBaseIn) {
		return 180.0F;
	}

	@Override
	public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		this.shadowSize = 0.25F * (float)entityIn.getSpiderSize();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	protected void preRenderCallback(T entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
		float f = 0.999F;
		matrixStackIn.scale(0.999F, 0.999F, 0.999F);
		float f1 = (float)entitylivingbaseIn.getSpiderSize();
		if(f1 == 1.0F)
			matrixStackIn.scale(0.5F, 0.5F, 0.5F);
	}
}