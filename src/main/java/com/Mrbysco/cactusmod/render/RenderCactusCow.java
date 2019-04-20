package com.mrbysco.cactusmod.render;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.entities.EntityCactusCow;
import com.mrbysco.cactusmod.render.layers.LayerCactusCowCactus;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderCactusCow extends RenderLiving<EntityCactusCow> {
	public static final Factory FACTORY = new Factory();
	private static final ResourceLocation texture = new ResourceLocation(Reference.PREFIX + "textures/entity/cactus_cow.png");

	public RenderCactusCow(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelCow(), 0.7F);
        this.addLayer(new LayerCactusCowCactus(this));
	}
	
	public ModelCow getMainModel()
    {
        return (ModelCow)super.getMainModel();
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityCactusCow entity) {
		return texture;
	}
	  
	public static class Factory implements IRenderFactory<EntityCactusCow> {
		@Override
		public Render<? super EntityCactusCow> createRenderFor(RenderManager manager) {
			return new RenderCactusCow(manager);
		}
	}
}