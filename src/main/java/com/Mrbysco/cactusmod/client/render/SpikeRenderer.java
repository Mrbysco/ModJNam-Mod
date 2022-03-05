package com.mrbysco.cactusmod.client.render;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.entities.SpikeEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class SpikeRenderer extends AbstractSpikeRenderer<SpikeEntity> {
	public static final ResourceLocation SPIKE = new ResourceLocation(Reference.MOD_ID, "textures/entity/spike.png");

	public SpikeRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(SpikeEntity entity) {
		return SPIKE;
	}
}
