package com.mrbysco.cactusmod.client.render;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.entities.SpikeEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class SpikeRenderer extends AbstractSpikeRenderer<SpikeEntity> {
    public static final ResourceLocation SPIKE = new ResourceLocation(Reference.PREFIX + "textures/entity/spike.png");

	public SpikeRenderer(EntityRendererManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public ResourceLocation getEntityTexture(SpikeEntity entity) {
		return SPIKE;
	}
}
