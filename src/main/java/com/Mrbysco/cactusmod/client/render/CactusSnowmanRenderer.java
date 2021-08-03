package com.mrbysco.cactusmod.client.render;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.client.render.layers.LayerCactusSnowmanHead;
import com.mrbysco.cactusmod.entities.CactusSnowGolemEntity;
import net.minecraft.client.model.SnowGolemModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CactusSnowmanRenderer extends MobRenderer<CactusSnowGolemEntity, SnowGolemModel<CactusSnowGolemEntity>> {
	private static final ResourceLocation texture = new ResourceLocation(Reference.MOD_ID,  "textures/entity/cactus_snowman.png");

	public CactusSnowmanRenderer(EntityRendererProvider.Context context) {
        super(context, new SnowGolemModel(context.bakeLayer(ModelLayers.SNOW_GOLEM)), 0.5F);
        this.addLayer(new LayerCactusSnowmanHead(this));
	}

	@Override
	public ResourceLocation getTextureLocation(CactusSnowGolemEntity entity) {
		return texture;
	}
}