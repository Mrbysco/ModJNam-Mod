package com.mrbysco.cactusmod.render;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.entities.EntityCactusSlime;
import com.mrbysco.cactusmod.render.layers.LayerCactusGel;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderCactusSlime extends RenderLiving<EntityCactusSlime> {
	public static final Factory FACTORY = new Factory();
	private static final ResourceLocation texture = new ResourceLocation(Reference.PREFIX + "textures/entity/cactus_slime.png");

	public RenderCactusSlime(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelSlime(16), 0.25F);
        this.addLayer(new LayerCactusGel(this));
	}
	
	@Override
	public void doRender(EntityCactusSlime entity, double x, double y, double z, float entityYaw, float partialTicks) {
		this.shadowSize = 0.25F * (float)entity.getSlimeSize();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
	
	@Override
	protected void preRenderCallback(EntityCactusSlime entitylivingbaseIn, float partialTickTime)
    {
        float f = 0.999F;
        GlStateManager.scale(0.999F, 0.999F, 0.999F);
        float f1 = (float)entitylivingbaseIn.getSlimeSize();
        float f2 = (entitylivingbaseIn.prevSquishFactor + (entitylivingbaseIn.squishFactor - entitylivingbaseIn.prevSquishFactor) * partialTickTime) / (f1 * 0.5F + 1.0F);
        float f3 = 1.0F / (f2 + 1.0F);
        GlStateManager.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
    }
	 
	@Override
	protected ResourceLocation getEntityTexture(EntityCactusSlime entity) {
		return texture;
	}
	  
	public static class Factory implements IRenderFactory<EntityCactusSlime> {
		@Override
		public Render<? super EntityCactusSlime> createRenderFor(RenderManager manager) {
			return new RenderCactusSlime(manager);
		}
	}
}