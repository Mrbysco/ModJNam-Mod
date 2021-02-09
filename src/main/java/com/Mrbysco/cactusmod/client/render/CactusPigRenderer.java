package com.mrbysco.cactusmod.client.render;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.entities.CactusPigEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.client.renderer.entity.model.PigModel;
import net.minecraft.util.ResourceLocation;

public class CactusPigRenderer extends MobRenderer<CactusPigEntity, PigModel<CactusPigEntity>> {
	private static final ResourceLocation texture = new ResourceLocation(Reference.PREFIX + "textures/entity/cactus_pig.png");

	public CactusPigRenderer(EntityRendererManager rendermanagerIn) {
		super(rendermanagerIn, new PigModel<>(), 0.7F);
		this.addLayer(new SaddleLayer<>(this, new PigModel<>(0.5F), new ResourceLocation("textures/entity/pig/pig_saddle.png")));
	}

	@Override
	public ResourceLocation getEntityTexture(CactusPigEntity entity) {
		return texture;
	}
}