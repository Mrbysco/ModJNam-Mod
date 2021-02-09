package com.mrbysco.cactusmod.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.entities.CactusGolem;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.IronGolemModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class CactusGolemRenderer extends MobRenderer<CactusGolem, IronGolemModel<CactusGolem>> {
	private static final ResourceLocation texture = new ResourceLocation(Reference.PREFIX + "textures/entity/cactus_villager_golem.png");

	public CactusGolemRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new IronGolemModel(), 0.5F);
	}

	@Override
	public ResourceLocation getEntityTexture(CactusGolem entity) {
		return texture;
	}

	protected void applyRotations(CactusGolem entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
		super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
		if (!((double)entityLiving.limbSwingAmount < 0.01D)) {
			float f = 13.0F;
			float f1 = entityLiving.limbSwing - entityLiving.limbSwingAmount * (1.0F - partialTicks) + 6.0F;
			float f2 = (Math.abs(f1 % 13.0F - 6.5F) - 3.25F) / 3.25F;
			matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(6.5F * f2));
		}
	}
}