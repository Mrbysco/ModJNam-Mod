package com.mrbysco.cactusmod.client.render;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.client.render.models.CactoniModel;
import com.mrbysco.cactusmod.entities.CactoniEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class CactoniRenderer extends MobRenderer<CactoniEntity, CactoniModel<CactoniEntity>> {
	private static final ResourceLocation texture = new ResourceLocation(Reference.PREFIX + "textures/entity/cactoni.png");

	public CactoniRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new CactoniModel(), 0.5F);
//		this.addLayer(new LayerCactusSnowmanHead(this));
	}

	@Override
	public ResourceLocation getEntityTexture(CactoniEntity entity) {
		return texture;
	}
}