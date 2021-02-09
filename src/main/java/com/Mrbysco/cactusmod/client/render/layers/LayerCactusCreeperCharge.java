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

	protected float func_225634_a_(float p_225634_1_) {
		return p_225634_1_ * 0.01F;
	}

	protected ResourceLocation func_225633_a_() {
		return LIGHTNING_TEXTURE;
	}

	protected EntityModel<CactusCreeperEntity> func_225635_b_() {
		return this.creeperModel;
	}
}