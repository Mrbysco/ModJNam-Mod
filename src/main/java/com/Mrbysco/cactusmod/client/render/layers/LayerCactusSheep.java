package com.mrbysco.cactusmod.client.render.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.client.render.models.CactusSheepModel;
import com.mrbysco.cactusmod.client.render.models.CactusWoolModel;
import com.mrbysco.cactusmod.entities.CactusSheepEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class LayerCactusSheep extends LayerRenderer<CactusSheepEntity, CactusSheepModel<CactusSheepEntity>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/entity/cactus_sheep_fur.png");
	private final CactusWoolModel<CactusSheepEntity> sheepModel = new CactusWoolModel<>();

	public LayerCactusSheep(IEntityRenderer<CactusSheepEntity, CactusSheepModel<CactusSheepEntity>> rendererIn) {
		super(rendererIn);
	}

	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, CactusSheepEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!entitylivingbaseIn.getSheared() && !entitylivingbaseIn.isInvisible()) {
			renderCopyCutoutModel(this.getEntityModel(), this.sheepModel, TEXTURE, matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, 1.0F, 1.0F, 1.0F);
		}
	}
}