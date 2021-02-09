package com.mrbysco.cactusmod.client.render;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.client.render.layers.LayerCactusSnowmanHead;
import com.mrbysco.cactusmod.entities.CactusSnowGolemEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.SnowManModel;
import net.minecraft.util.ResourceLocation;

public class CactusSnowmanRenderer extends MobRenderer<CactusSnowGolemEntity, SnowManModel<CactusSnowGolemEntity>> {
	private static final ResourceLocation texture = new ResourceLocation(Reference.PREFIX + "textures/entity/cactus_snowman.png");

	public CactusSnowmanRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new SnowManModel(), 0.5F);
        this.addLayer(new LayerCactusSnowmanHead(this));
	}

	@Override
	public ResourceLocation getEntityTexture(CactusSnowGolemEntity entity) {
		return texture;
	}
}