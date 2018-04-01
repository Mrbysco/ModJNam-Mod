package com.Mrbysco.CactusMod.render;

import com.Mrbysco.CactusMod.Reference;
import com.Mrbysco.CactusMod.entities.EntityCactusCow;

import net.minecraft.client.model.ModelCow;
import net.minecraft.client.renderer.GlStateManager;
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
	
	protected void applyRotations(EntityCactusCow entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
    {
        super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);

        if ((double)entityLiving.limbSwingAmount >= 0.01D)
        {
            float f = 13.0F;
            float f1 = entityLiving.limbSwing - entityLiving.limbSwingAmount * (1.0F - partialTicks) + 6.0F;
            float f2 = (Math.abs(f1 % 13.0F - 6.5F) - 3.25F) / 3.25F;
            GlStateManager.rotate(6.5F * f2, 0.0F, 0.0F, 1.0F);
        }
    }
}