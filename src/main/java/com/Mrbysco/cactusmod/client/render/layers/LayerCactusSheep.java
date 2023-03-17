package com.mrbysco.cactusmod.client.render.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.client.ClientHandler;
import com.mrbysco.cactusmod.client.render.models.CactusSheepModel;
import com.mrbysco.cactusmod.client.render.models.CactusWoolModel;
import com.mrbysco.cactusmod.entities.CactusSheepEntity;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class LayerCactusSheep extends RenderLayer<CactusSheepEntity, CactusSheepModel<CactusSheepEntity>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/entity/cactus_sheep_fur.png");
	private final CactusWoolModel<CactusSheepEntity> woolModel;

	public LayerCactusSheep(RenderLayerParent<CactusSheepEntity, CactusSheepModel<CactusSheepEntity>> rendererIn, EntityModelSet modelSet) {
		super(rendererIn);
		this.woolModel = new CactusWoolModel<>(modelSet.bakeLayer(ClientHandler.CACTUS_SHEEP_WOOL));
	}

	public void render(PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn, CactusSheepEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!entitylivingbaseIn.getSheared() && !entitylivingbaseIn.isInvisible()) {
			coloredCutoutModelCopyLayerRender(this.getParentModel(), this.woolModel, TEXTURE, poseStack, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, 1.0F, 1.0F, 1.0F);
		}
	}
}