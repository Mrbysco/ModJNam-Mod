package com.mrbysco.cactusmod.render;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.tileentities.TileEntityCactusChest;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class TileEntityCactusChestRenderer extends TileEntitySpecialRenderer<TileEntityCactusChest>{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.PREFIX + "textures/entity/cactus_chest.png");
    private final ModelChest singleChest = new ModelChest();

    @Override
    public void render(TileEntityCactusChest te, double x, double y, double z, float partialTicks, int destroyStage,
    		float alpha) {
    	int i = 0;
    	
    	if (te == null || te.isInvalid())
            return;
    	
        if (te != null && te.hasWorld())
            i = te.getBlockMetadata();

        if (destroyStage >= 0)
        {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 4.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        }
        else
        {
            this.bindTexture(TEXTURE);
        }

        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();
        GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
        GlStateManager.translate((float)x, (float)y + 1.0F, (float)z + 1.0F);
        GlStateManager.scale(1.0F, -1.0F, -1.0F);
        GlStateManager.translate(0.5F, 0.5F, 0.5F);
        int j = 0;

        if (i == 2)
            j = 180;

        if (i == 3)
            j = 0;

        if (i == 4)
            j = 90;

        if (i == 5)
            j = -90;

        GlStateManager.rotate((float)j, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(-0.5F, -0.5F, -0.5F);
        
        if(te != null)
        {
        	float f = te.prevLidAngle + (te.lidAngle - te.prevLidAngle) * partialTicks;
            f = 1.0F - f;
            f = 1.0F - f * f * f;
            this.singleChest.chestLid.rotateAngleX = -(f * ((float)Math.PI / 2F));
        }
        this.singleChest.renderAll();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        if (destroyStage >= 0)
        {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
    }

}
