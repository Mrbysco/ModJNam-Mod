package com.mrbysco.cactusmod.render;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.entities.EntityCactusGolem;
import net.minecraft.client.model.ModelIronGolem;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderCactusGolem extends RenderLiving<EntityCactusGolem> {
	public static final Factory FACTORY = new Factory();
	private static final ResourceLocation texture = new ResourceLocation(Reference.PREFIX + "textures/entity/cactus_villager_golem.png");

	public RenderCactusGolem(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelIronGolem(), 0.5F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityCactusGolem entity) {
		return texture;
	}
	  
	public static class Factory implements IRenderFactory<EntityCactusGolem> {
		@Override
		public Render<? super EntityCactusGolem> createRenderFor(RenderManager manager) {
			return new RenderCactusGolem(manager);
		}
	}
	
	protected void applyRotations(EntityCactusGolem entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
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