package com.mrbysco.cactusmod.client.render;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.client.ClientHandler;
import com.mrbysco.cactusmod.client.render.layers.LayerCactusSheep;
import com.mrbysco.cactusmod.client.render.models.CactusSheepModel;
import com.mrbysco.cactusmod.entities.CactusSheepEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CactusSheepRenderer extends MobRenderer<CactusSheepEntity, CactusSheepModel<CactusSheepEntity>> {
	private static final ResourceLocation texture = new ResourceLocation(Reference.MOD_ID,  "textures/entity/cactus_sheep.png");

	public CactusSheepRenderer(EntityRendererProvider.Context context) {
        super(context, new CactusSheepModel(context.bakeLayer(ClientHandler.CACTUS_SHEEP)), 0.7F);
        this.addLayer(new LayerCactusSheep(this, context.getModelSet()));
	}

	@Override
	public ResourceLocation getTextureLocation(CactusSheepEntity entity) {
		return texture;
	}
}