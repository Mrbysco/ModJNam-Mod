package com.mrbysco.cactusmod.client.render;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.client.ClientHandler;
import com.mrbysco.cactusmod.client.render.models.CactoniModel;
import com.mrbysco.cactusmod.entities.CactoniEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CactoniRenderer extends MobRenderer<CactoniEntity, CactoniModel<CactoniEntity>> {
	private static final ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, "textures/entity/cactoni.png");

	public CactoniRenderer(EntityRendererProvider.Context context) {
        super(context, new CactoniModel(context.bakeLayer(ClientHandler.CACTONI)), 0.5F);
//		this.addLayer(new LayerCactusSnowmanHead(this));
	}

	@Override
	public ResourceLocation getTextureLocation(CactoniEntity entity) {
		return texture;
	}
}