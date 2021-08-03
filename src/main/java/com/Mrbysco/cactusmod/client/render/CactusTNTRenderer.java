package com.mrbysco.cactusmod.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.mrbysco.cactusmod.entities.CactusTNTEntity;
import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class CactusTNTRenderer extends EntityRenderer<CactusTNTEntity> {
    public CactusTNTRenderer(EntityRendererProvider.Context context) {
		super(context);
        this.shadowRadius = 0.5F;
	}

	@Override
	public ResourceLocation getTextureLocation(CactusTNTEntity entity)
    {
        return TextureAtlas.LOCATION_BLOCKS;
    }

    @Override
    public void render(CactusTNTEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLightIn) {
        poseStack.pushPose();
        poseStack.translate(0.0D, 0.5D, 0.0D);
        if ((float)entityIn.getFuse() - partialTicks + 1.0F < 10.0F) {
            float f = 1.0F - ((float)entityIn.getFuse() - partialTicks + 1.0F) / 10.0F;
            f = Mth.clamp(f, 0.0F, 1.0F);
            f = f * f;
            f = f * f;
            float f1 = 1.0F + f * 0.3F;
            poseStack.scale(f1, f1, f1);
        }

        poseStack.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
        poseStack.translate(-0.5D, -0.5D, 0.5D);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(90.0F));
        TntMinecartRenderer.renderWhiteSolidBlock(CactusRegistry.CACTUS_TNT.get().defaultBlockState(), poseStack, bufferSource, packedLightIn, entityIn.getFuse() / 5 % 2 == 0);
        poseStack.popPose();
        super.render(entityIn, entityYaw, partialTicks, poseStack, bufferSource, packedLightIn);
    }
}
