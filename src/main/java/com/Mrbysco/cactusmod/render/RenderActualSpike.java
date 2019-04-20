package com.mrbysco.cactusmod.render;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.entities.EntityActualSpike;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderActualSpike extends RenderSpike<EntityActualSpike>{
	public static final Factory FACTORY = new Factory();
    public static final ResourceLocation SPIKE = new ResourceLocation(Reference.PREFIX + "textures/entity/spike.png");

	protected RenderActualSpike(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityActualSpike entity) {
		return SPIKE;
	}
	
	public static class Factory implements IRenderFactory<EntityActualSpike> {
		@Override
		public Render<? super EntityActualSpike> createRenderFor(RenderManager manager) {
			return new RenderActualSpike(manager);
		}
	}
}
