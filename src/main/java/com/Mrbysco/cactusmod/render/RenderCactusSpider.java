package com.mrbysco.cactusmod.render;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.entities.hostile.EntityCactusSpider;
import com.mrbysco.cactusmod.render.models.ModelCactusSpider;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderCactusSpider extends RenderLiving<EntityCactusSpider> {
	public static final Factory FACTORY = new Factory();
	private static final ResourceLocation texture = new ResourceLocation(Reference.PREFIX + "textures/entity/cactus_spider.png");

	public RenderCactusSpider(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelCactusSpider(), 0.25F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityCactusSpider entity) {
		return texture;
	}
	
	protected float getDeathMaxRotation(EntityCactusSpider entityLivingBaseIn)
    {
        return 180.0F;
    }
	
	@Override
	public void doRender(EntityCactusSpider entity, double x, double y, double z, float entityYaw, float partialTicks) {
		this.shadowSize = 0.25F * (float)entity.getSpiderSize();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
	
	@Override
	protected void preRenderCallback(EntityCactusSpider entitylivingbaseIn, float partialTickTime)
    {
        float f = 0.999F;
        GlStateManager.scale(0.999F, 0.999F, 0.999F);
        float f1 = (float)entitylivingbaseIn.getSpiderSize();
        if(f1 == 1.0F)
        	GlStateManager.scale(0.5F, 0.5F, 0.5F);
    }
	
	public static class Factory implements IRenderFactory<EntityCactusSpider> {
		@Override
		public Render<? super EntityCactusSpider> createRenderFor(RenderManager manager) {
			return new RenderCactusSpider(manager);
		}
	}
}