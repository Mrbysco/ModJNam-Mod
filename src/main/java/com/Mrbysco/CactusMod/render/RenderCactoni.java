package com.Mrbysco.CactusMod.render;

import com.Mrbysco.CactusMod.Reference;
import com.Mrbysco.CactusMod.entities.EntityCactoni;
import com.Mrbysco.CactusMod.render.models.ModelCactoni;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderCactoni extends RenderLiving<EntityCactoni> {
	public static final Factory FACTORY = new Factory();
	private static final ResourceLocation texture = new ResourceLocation(Reference.PREFIX + "textures/entity/cactoni.png");

	public RenderCactoni(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelCactoni(), 0.5F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityCactoni entity) {
		return texture;
	}
	
	public ModelCactoni getMainModel()
    {
        return (ModelCactoni)super.getMainModel();
    }
	
	public static class Factory implements IRenderFactory<EntityCactoni> {
		@Override
		public Render<? super EntityCactoni> createRenderFor(RenderManager manager) {
			return new RenderCactoni(manager);
		}
	}
}