package com.mrbysco.cactusmod.render.layers;

import com.mrbysco.cactusmod.entities.EntityCactusPig;
import com.mrbysco.cactusmod.render.RenderCactusPig;
import com.mrbysco.cactusmod.render.RenderCactusSlime;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerCactusSaddle implements LayerRenderer<EntityCactusPig>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/pig/pig_saddle.png");
    private final RenderCactusPig pigRenderer;
    private final ModelPig pigModel = new ModelPig(0.5F);

    public LayerCactusSaddle(RenderCactusPig pigRenderer)
    {
        this.pigRenderer = pigRenderer;
    }

    public void doRenderLayer(EntityCactusPig entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (entitylivingbaseIn.getSaddled())
        {
            this.pigRenderer.bindTexture(TEXTURE);
            this.pigModel.setModelAttributes(this.pigRenderer.getMainModel());
            this.pigModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }
}