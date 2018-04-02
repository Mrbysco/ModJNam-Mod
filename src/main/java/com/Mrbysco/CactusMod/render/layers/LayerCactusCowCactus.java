package com.Mrbysco.CactusMod.render.layers;

import com.Mrbysco.CactusMod.entities.EntityCactusCow;
import com.Mrbysco.CactusMod.render.RenderCactusCow;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerCactusCowCactus implements LayerRenderer<EntityCactusCow>
{
    private final RenderCactusCow cactusCowRenderer;

    public LayerCactusCowCactus(RenderCactusCow cactusCowRendererIn)
    {
        this.cactusCowRenderer = cactusCowRendererIn;
    }

    public void doRenderLayer(EntityCactusCow entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (!entitylivingbaseIn.isChild() && !entitylivingbaseIn.isInvisible())
        {
            BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
            this.cactusCowRenderer.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            GlStateManager.enableCull();
            GlStateManager.cullFace(GlStateManager.CullFace.FRONT);
            GlStateManager.pushMatrix();
            GlStateManager.scale(1.0F, -1.0F, 1.0F);
            GlStateManager.translate(0.2F, 0.35F, 0.5F);
            GlStateManager.rotate(42.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.pushMatrix();
            GlStateManager.translate(-0.1F, -0.475F, 0.050F);
            GlStateManager.scale(0.30D, 0.30D, 0.30D);
            blockrendererdispatcher.renderBlockBrightness(Blocks.CACTUS.getDefaultState(), 1.0F);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.1F, 0.0F, -0.6F);
            GlStateManager.rotate(42.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(-0.2F, -0.475F, 0.150F);
            GlStateManager.scale(0.30D, 0.30D, 0.30D);
            blockrendererdispatcher.renderBlockBrightness(Blocks.CACTUS.getDefaultState(), 1.0F);
            GlStateManager.popMatrix();
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            this.cactusCowRenderer.getMainModel().head.postRender(0.0625F);
            GlStateManager.scale(1.0F, -1.0F, 1.0F);
            GlStateManager.translate(0.0F, 0.7F, -0.2F);
            GlStateManager.rotate(12.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(-0.075F, -0.575F, 0.575F);
            GlStateManager.scale(0.30D, 0.30D, 0.30D);
            blockrendererdispatcher.renderBlockBrightness(Blocks.CACTUS.getDefaultState(), 1.0F);
            GlStateManager.popMatrix();
            GlStateManager.cullFace(GlStateManager.CullFace.BACK);
            GlStateManager.disableCull();
        }
    }

    public boolean shouldCombineTextures()
    {
        return true;
    }
}