package com.Mrbysco.CactusMod.render;

import com.Mrbysco.CactusMod.Reference;
import com.Mrbysco.CactusMod.entities.hostile.EntityCactusCreeper;
import com.Mrbysco.CactusMod.render.layers.LayerCactusCreeperCharge;

import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderCactusCreeper extends RenderLiving<EntityCactusCreeper> {
	public static final Factory FACTORY = new Factory();
	private static final ResourceLocation texture = new ResourceLocation(Reference.PREFIX + "textures/entity/cactus_creeper.png");

	public RenderCactusCreeper(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelCreeper(), 0.7F);
        this.addLayer(new LayerCactusCreeperCharge(this));
	}
	
	protected void preRenderCallback(EntityCactusCreeper entitylivingbaseIn, float partialTickTime)
    {
        float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);
        float f1 = 1.0F + MathHelper.sin(f * 100.0F) * f * 0.01F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        f = f * f;
        f = f * f;
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.1F) / f1;
        GlStateManager.scale(f2, f3, f2);
    }
	
	protected int getColorMultiplier(EntityCactusCreeper entitylivingbaseIn, float lightBrightness, float partialTickTime)
    {
        float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);

        if ((int)(f * 10.0F) % 2 == 0)
        {
            return 0;
        }
        else
        {
            int i = (int)(f * 0.2F * 255.0F);
            i = MathHelper.clamp(i, 0, 255);
            return i << 24 | 822083583;
        }
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityCactusCreeper entity) {
		return texture;
	}
	  
	public static class Factory implements IRenderFactory<EntityCactusCreeper> {
		@Override
		public Render<? super EntityCactusCreeper> createRenderFor(RenderManager manager) {
			return new RenderCactusCreeper(manager);
		}
	}
}