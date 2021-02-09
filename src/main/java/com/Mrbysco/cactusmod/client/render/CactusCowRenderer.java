package com.mrbysco.cactusmod.client.render;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.client.render.layers.LayerCactusCowCactus;
import com.mrbysco.cactusmod.entities.CactusCowEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.util.ResourceLocation;

public class CactusCowRenderer extends MobRenderer<CactusCowEntity, CowModel<CactusCowEntity>> {
	private static final ResourceLocation COW_TEXTURES = new ResourceLocation(Reference.PREFIX + "textures/entity/cactus_cow.png");

	public CactusCowRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new CowModel<>(), 0.7F);
		this.addLayer(new LayerCactusCowCactus(this));
	}

	public ResourceLocation getEntityTexture(CactusCowEntity entity) {
		return COW_TEXTURES;
	}
}