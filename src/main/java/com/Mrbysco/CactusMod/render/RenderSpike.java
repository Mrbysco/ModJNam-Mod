package com.Mrbysco.CactusMod.render;

import com.Mrbysco.CactusMod.Reference;
import com.Mrbysco.CactusMod.entities.EntitySpike;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderSpike extends RenderArrow<EntitySpike>{
	public static final Factory FACTORY = new Factory();
    public static final ResourceLocation SPIKE = new ResourceLocation(Reference.PREFIX + "textures/entity/spike.png");

	protected RenderSpike(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntitySpike entity) {
		return SPIKE;
	}
	
	public static class Factory implements IRenderFactory<EntitySpike> {
		@Override
		public Render<? super EntitySpike> createRenderFor(RenderManager manager) {
			return new RenderSpike(manager);
		}
	}
}
