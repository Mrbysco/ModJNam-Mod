package com.mrbysco.cactusmod.render;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.entities.EntityCactusPig;
import com.mrbysco.cactusmod.render.layers.LayerCactusSaddle;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderCactusPig extends RenderLiving<EntityCactusPig> {
	public static final Factory FACTORY = new Factory();
	private static final ResourceLocation texture = new ResourceLocation(Reference.PREFIX + "textures/entity/cactus_pig.png");

	public RenderCactusPig(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelPig(), 0.7F);
		this.addLayer(new LayerCactusSaddle(this));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityCactusPig entity) {
		return texture;
	}
	  
	public static class Factory implements IRenderFactory<EntityCactusPig> {
		@Override
		public Render<? super EntityCactusPig> createRenderFor(RenderManager manager) {
			return new RenderCactusPig(manager);
		}
	}
}