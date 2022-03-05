package com.mrbysco.cactusmod.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.entities.CactusGolem;
import net.minecraft.client.model.IronGolemModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CactusGolemRenderer extends MobRenderer<CactusGolem, IronGolemModel<CactusGolem>> {
	private static final ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, "textures/entity/cactus_villager_golem.png");

	public CactusGolemRenderer(EntityRendererProvider.Context context) {
		super(context, new IronGolemModel(context.bakeLayer(ModelLayers.IRON_GOLEM)), 0.5F);
	}

	@Override
	public ResourceLocation getTextureLocation(CactusGolem entity) {
		return texture;
	}

	protected void setupRotations(CactusGolem entityLiving, PoseStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
		super.setupRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
		if (!((double) entityLiving.animationSpeed < 0.01D)) {
			float f = 13.0F;
			float f1 = entityLiving.animationPosition - entityLiving.animationSpeed * (1.0F - partialTicks) + 6.0F;
			float f2 = (Math.abs(f1 % 13.0F - 6.5F) - 3.25F) / 3.25F;
			matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(6.5F * f2));
		}
	}
}