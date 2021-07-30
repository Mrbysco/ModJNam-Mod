package com.mrbysco.cactusmod.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mrbysco.cactusmod.entities.CactusTNTEntity;
import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.TNTMinecartRenderer;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class CactusTNTRenderer extends EntityRenderer<CactusTNTEntity> {
    public CactusTNTRenderer(EntityRendererManager renderManager) {
		super(renderManager);
        this.shadowRadius = 0.5F;
	}

	@Override
	public ResourceLocation getTextureLocation(CactusTNTEntity entity)
    {
        return AtlasTexture.LOCATION_BLOCKS;
    }

    @Override
    public void render(CactusTNTEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        matrixStackIn.translate(0.0D, 0.5D, 0.0D);
        if ((float)entityIn.getFuse() - partialTicks + 1.0F < 10.0F) {
            float f = 1.0F - ((float)entityIn.getFuse() - partialTicks + 1.0F) / 10.0F;
            f = MathHelper.clamp(f, 0.0F, 1.0F);
            f = f * f;
            f = f * f;
            float f1 = 1.0F + f * 0.3F;
            matrixStackIn.scale(f1, f1, f1);
        }

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
        matrixStackIn.translate(-0.5D, -0.5D, 0.5D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90.0F));
        TNTMinecartRenderer.renderWhiteSolidBlock(CactusRegistry.CACTUS_TNT.get().defaultBlockState(), matrixStackIn, bufferIn, packedLightIn, entityIn.getFuse() / 5 % 2 == 0);
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }
}
