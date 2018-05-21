package com.Mrbysco.CactusMod.render.layers;

import com.Mrbysco.CactusMod.entities.EntityCactusSnowman;
import com.Mrbysco.CactusMod.init.CactusBlocks;
import com.Mrbysco.CactusMod.render.RenderCactusSnowman;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerCactusSnowmanHead implements LayerRenderer<EntityCactusSnowman>
{
    private final RenderCactusSnowman snowManRenderer;


    public LayerCactusSnowmanHead(RenderCactusSnowman snowManRenderer)
    {
        this.snowManRenderer = snowManRenderer;
    }

    public void doRenderLayer(EntityCactusSnowman entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
    	if (!entitylivingbaseIn.isInvisible() && entitylivingbaseIn.isCactusEquipped())
        {
            GlStateManager.pushMatrix();
            this.snowManRenderer.getMainModel().head.postRender(0.0625F);
            float f = 0.625F;
            GlStateManager.translate(0.0F, -0.34375F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.scale(0.625F, -0.625F, -0.625F);
            Minecraft.getMinecraft().getItemRenderer().renderItem(entitylivingbaseIn, new ItemStack(CactusBlocks.carved_cactus, 1), ItemCameraTransforms.TransformType.HEAD);
            GlStateManager.popMatrix();
        }
    }

    public boolean shouldCombineTextures()
    {
        return true;
    }
}