package com.mrbysco.cactusmod.client.render;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.entities.CactusPigEntity;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;

public class CactusPigRenderer extends MobRenderer<CactusPigEntity, PigModel<CactusPigEntity>> {
	private static final ResourceLocation texture = new ResourceLocation(Reference.MOD_ID,  "textures/entity/cactus_pig.png");

	public CactusPigRenderer(EntityRendererProvider.Context context) {
		super(context, new PigModel<>(context.bakeLayer(ModelLayers.PIG)), 0.7F);
		this.addLayer(new SaddleLayer<>(this, new PigModel<>(context.bakeLayer(ModelLayers.PIG_SADDLE)), new ResourceLocation("textures/entity/pig/pig_saddle.png")));
	}

	@Override
	public ResourceLocation getTextureLocation(CactusPigEntity entity) {
		return texture;
	}
}