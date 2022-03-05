package com.mrbysco.cactusmod.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.entities.CactusSlimeEntity;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SlimeOuterLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class CactusSlimeRenderer extends MobRenderer<CactusSlimeEntity, SlimeModel<CactusSlimeEntity>> {
	private static final ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, "textures/entity/cactus_slime.png");

	public CactusSlimeRenderer(EntityRendererProvider.Context context) {
		super(context, new SlimeModel(context.bakeLayer(ModelLayers.SLIME)), 0.25F);
        this.addLayer(new SlimeOuterLayer(this, context.getModelSet()));
	}

	public void render(CactusSlimeEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLightIn) {
		this.shadowRadius = 0.25F * (float)entityIn.getSize();
		super.render(entityIn, entityYaw, partialTicks, poseStack, bufferSource, packedLightIn);
	}

	protected void scale(CactusSlimeEntity cactusSlimeEntity, PoseStack poseStack, float partialTickTime) {
		float f = 0.999F;
		poseStack.scale(0.999F, 0.999F, 0.999F);
		poseStack.translate(0.0D, (double)0.001F, 0.0D);
		float f1 = (float)cactusSlimeEntity.getSize();
		float f2 = Mth.lerp(partialTickTime, cactusSlimeEntity.oSquish, cactusSlimeEntity.squish) / (f1 * 0.5F + 1.0F);
		float f3 = 1.0F / (f2 + 1.0F);
		poseStack.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
	}
	 
	@Override
	public ResourceLocation getTextureLocation(CactusSlimeEntity entity) {
		return texture;
	}
}