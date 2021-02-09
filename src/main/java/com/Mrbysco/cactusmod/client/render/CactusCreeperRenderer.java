package com.mrbysco.cactusmod.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.client.render.layers.LayerCactusCreeperCharge;
import com.mrbysco.cactusmod.entities.hostile.CactusCreeperEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.CreeperModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class CactusCreeperRenderer extends MobRenderer<CactusCreeperEntity, CreeperModel<CactusCreeperEntity>> {
	private static final ResourceLocation texture = new ResourceLocation(Reference.PREFIX + "textures/entity/cactus_creeper.png");

	public CactusCreeperRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new CreeperModel(), 0.7F);
        this.addLayer(new LayerCactusCreeperCharge(this));
	}

    protected void preRenderCallback(CactusCreeperEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);
        float f1 = 1.0F + MathHelper.sin(f * 100.0F) * f * 0.01F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        f = f * f;
        f = f * f;
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.1F) / f1;
        matrixStackIn.scale(f2, f3, f2);
    }

    protected float getOverlayProgress(CactusCreeperEntity livingEntityIn, float partialTicks) {
        float f = livingEntityIn.getCreeperFlashIntensity(partialTicks);
        return (int)(f * 10.0F) % 2 == 0 ? 0.0F : MathHelper.clamp(f, 0.5F, 1.0F);
    }

	@Override
	public ResourceLocation getEntityTexture(CactusCreeperEntity entity) {
		return texture;
	}
}