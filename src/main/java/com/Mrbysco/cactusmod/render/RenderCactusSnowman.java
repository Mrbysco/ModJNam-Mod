package com.mrbysco.cactusmod.render;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.entities.EntityCactusSnowman;
import com.mrbysco.cactusmod.render.layers.LayerCactusSnowmanHead;
import net.minecraft.client.model.ModelSnowMan;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderCactusSnowman extends RenderLiving<EntityCactusSnowman> {
	public static final Factory FACTORY = new Factory();
	private static final ResourceLocation texture = new ResourceLocation(Reference.PREFIX + "textures/entity/cactus_snowman.png");

	public RenderCactusSnowman(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelSnowMan(), 0.5F);
        this.addLayer(new LayerCactusSnowmanHead(this));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityCactusSnowman entity) {
		return texture;
	}
	
	public ModelSnowMan getMainModel()
    {
        return (ModelSnowMan)super.getMainModel();
    }
	
	public static class Factory implements IRenderFactory<EntityCactusSnowman> {
		@Override
		public Render<? super EntityCactusSnowman> createRenderFor(RenderManager manager) {
			return new RenderCactusSnowman(manager);
		}
	}
}