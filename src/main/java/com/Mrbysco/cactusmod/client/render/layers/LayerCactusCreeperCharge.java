package com.mrbysco.cactusmod.client.render.layers;

import com.mrbysco.cactusmod.entities.hostile.CactusCreeperEntity;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.EnergyLayer;
import net.minecraft.client.renderer.entity.model.CreeperModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;

public class LayerCactusCreeperCharge extends EnergyLayer<CactusCreeperEntity, CreeperModel<CactusCreeperEntity>> {
	private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
	private final CreeperModel<CactusCreeperEntity> creeperModel = new CreeperModel<>(2.0F);

	public LayerCactusCreeperCharge(IEntityRenderer<CactusCreeperEntity, CreeperModel<CactusCreeperEntity>> p_i50947_1_) {
		super(p_i50947_1_);
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