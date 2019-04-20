package com.mrbysco.cactusmod.render;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.entities.hostile.EntityCactusSkelly;
import com.mrbysco.cactusmod.render.models.ModelCactiSkelly;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderCactusSkeleton extends RenderLiving<EntityCactusSkelly> {
	public static final Factory FACTORY = new Factory();
	private static final ResourceLocation texture = new ResourceLocation(Reference.PREFIX + "textures/entity/cactus_skeleton.png");

	public RenderCactusSkeleton(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelCactiSkelly(), 0.5F);
        this.addLayer(new LayerHeldItem(this));
        this.addLayer(new LayerBipedArmor(this)
        {
            protected void initArmor()
            {
                this.modelLeggings = new ModelCactiSkelly(0.5F, true);
                this.modelArmor = new ModelCactiSkelly(1.0F, true);
            }
        });
	}

	public void transformHeldFull3DItemLayer()
    {
        GlStateManager.translate(0.09375F, 0.1875F, 0.0F);
    }
	
	@Override
	protected ResourceLocation getEntityTexture(EntityCactusSkelly entity) {
		return texture;
	}
	  
	public static class Factory implements IRenderFactory<EntityCactusSkelly> {
		@Override
		public Render<? super EntityCactusSkelly> createRenderFor(RenderManager manager) {
			return new RenderCactusSkeleton(manager);
		}
	}
}