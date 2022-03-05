package com.mrbysco.cactusmod.client.render;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.client.render.layers.LayerCactusCowCactus;
import com.mrbysco.cactusmod.entities.CactusCowEntity;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CactusCowRenderer extends MobRenderer<CactusCowEntity, CowModel<CactusCowEntity>> {
	private static final ResourceLocation COW_TEXTURES = new ResourceLocation(Reference.MOD_ID, "textures/entity/cactus_cow.png");

	public CactusCowRenderer(EntityRendererProvider.Context context) {
		super(context, new CowModel<>(context.bakeLayer(ModelLayers.COW)), 0.7F);
		this.addLayer(new LayerCactusCowCactus(this));
	}

	public ResourceLocation getTextureLocation(CactusCowEntity entity) {
		return COW_TEXTURES;
	}
}