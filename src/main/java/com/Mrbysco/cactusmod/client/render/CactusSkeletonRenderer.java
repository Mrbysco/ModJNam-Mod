package com.mrbysco.cactusmod.client.render;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.entities.hostile.CactusSkeletonEntity;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.SkeletonModel;
import net.minecraft.util.ResourceLocation;

public class CactusSkeletonRenderer extends BipedRenderer<CactusSkeletonEntity, SkeletonModel<CactusSkeletonEntity>> {
	private static final ResourceLocation texture = new ResourceLocation(Reference.PREFIX + "textures/entity/cactus_skeleton.png");

	public CactusSkeletonRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new SkeletonModel(), 0.5F);
		this.addLayer(new BipedArmorLayer<>(this, new SkeletonModel(0.5F, true), new SkeletonModel(1.0F, true)));
	}
	
	@Override
	public ResourceLocation getEntityTexture(CactusSkeletonEntity entity) {
		return texture;
	}
}