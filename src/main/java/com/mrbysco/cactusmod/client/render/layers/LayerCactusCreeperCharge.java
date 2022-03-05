package com.mrbysco.cactusmod.client.render.layers;

import com.mrbysco.cactusmod.entities.hostile.CactusCreeperEntity;
import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.ResourceLocation;

public class LayerCactusCreeperCharge extends EnergySwirlLayer<CactusCreeperEntity, CreeperModel<CactusCreeperEntity>> {
	private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
	private final CreeperModel<CactusCreeperEntity> creeperModel;


	public LayerCactusCreeperCharge(RenderLayerParent<CactusCreeperEntity, CreeperModel<CactusCreeperEntity>> layerParent, EntityModelSet modelSet) {
		super(layerParent);
		this.creeperModel = new CreeperModel(modelSet.bakeLayer(ModelLayers.CREEPER_ARMOR));
	}

	protected float xOffset(float p_225634_1_) {
		return p_225634_1_ * 0.01F;
	}

	protected ResourceLocation getTextureLocation() {
		return LIGHTNING_TEXTURE;
	}

	protected EntityModel<CactusCreeperEntity> model() {
		return this.creeperModel;
	}
}