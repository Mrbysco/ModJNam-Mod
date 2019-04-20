package com.mrbysco.cactusmod.render.layers;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.entities.EntityCactusSheep;
import com.mrbysco.cactusmod.render.RenderCactusSheep;
import com.mrbysco.cactusmod.render.models.ModelCactusSheep;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerCactusSheep implements LayerRenderer<EntityCactusSheep>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.PREFIX + "textures/entity/cactus_sheep_fur.png");
    private final RenderCactusSheep sheepRenderer;
    private final ModelCactusSheep sheepModel = new ModelCactusSheep();

    public LayerCactusSheep(RenderCactusSheep sheepRendererIn)
    {
        this.sheepRenderer = sheepRendererIn;
    }

    public void doRenderLayer(EntityCactusSheep entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (!entitylivingbaseIn.getSheared() && !entitylivingbaseIn.isInvisible())
        {
            this.sheepRenderer.bindTexture(TEXTURE);

            this.sheepModel.setModelAttributes(this.sheepRenderer.getMainModel());
            this.sheepModel.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
            this.sheepModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    public boolean shouldCombineTextures()
    {
        return true;
    }
}