package com.mrbysco.cactusmod.render;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.entities.EntityCactusSheep;
import com.mrbysco.cactusmod.render.layers.LayerCactusSheep;
import com.mrbysco.cactusmod.render.models.ModelCactusSheep2;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderCactusSheep extends RenderLiving<EntityCactusSheep> {
	public static final Factory FACTORY = new Factory();
	private static final ResourceLocation texture = new ResourceLocation(Reference.PREFIX + "textures/entity/cactus_sheep.png");

	public RenderCactusSheep(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelCactusSheep2(), 0.7F);
        this.addLayer(new LayerCactusSheep(this));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityCactusSheep entity) {
		return texture;
	}
	  
	public static class Factory implements IRenderFactory<EntityCactusSheep> {
		@Override
		public Render<? super EntityCactusSheep> createRenderFor(RenderManager manager) {
			return new RenderCactusSheep(manager);
		}
	}
}