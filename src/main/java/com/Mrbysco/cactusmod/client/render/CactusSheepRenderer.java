package com.mrbysco.cactusmod.client.render;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.client.render.layers.LayerCactusSheep;
import com.mrbysco.cactusmod.client.render.models.CactusSheepModel;
import com.mrbysco.cactusmod.entities.CactusSheepEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class CactusSheepRenderer extends MobRenderer<CactusSheepEntity, CactusSheepModel<CactusSheepEntity>> {
	private static final ResourceLocation texture = new ResourceLocation(Reference.PREFIX + "textures/entity/cactus_sheep.png");

	public CactusSheepRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new CactusSheepModel(), 0.7F);
        this.addLayer(new LayerCactusSheep(this));
	}

	@Override
	public ResourceLocation getEntityTexture(CactusSheepEntity entity) {
		return texture;
	}
}