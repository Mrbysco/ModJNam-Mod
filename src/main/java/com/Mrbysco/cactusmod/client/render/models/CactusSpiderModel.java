package com.mrbysco.cactusmod.client.render.models;

import com.google.common.collect.ImmutableList;
import com.mrbysco.cactusmod.entities.hostile.CactusSpiderEntity;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class CactusSpiderModel<T extends CactusSpiderEntity> extends SegmentedModel<T> {
    private final ModelRenderer cactus;
    private final ModelRenderer spiderHead;
    private final ModelRenderer spiderLeg1;
    private final ModelRenderer spiderLeg2;
    private final ModelRenderer spiderLeg3;
    private final ModelRenderer spiderLeg4;
    private final ModelRenderer spiderLeg5;
    private final ModelRenderer spiderLeg6;
    private final ModelRenderer spiderLeg7;
    private final ModelRenderer spiderLeg8;

    public CactusSpiderModel() {
        this.texWidth = 64;
        this.texHeight = 64;
        this.spiderHead = new ModelRenderer(this, 32, 4);
        this.spiderHead.addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, 0.0F);
        this.spiderHead.setPos(0.0F, 15.0F, -4.0F);
        this.spiderLeg1 = new ModelRenderer(this, 18, 0);
        this.spiderLeg1.addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F);
        this.spiderLeg1.setPos(-4.0F, 15.0F, 2.0F);
        this.spiderLeg2 = new ModelRenderer(this, 18, 0);
        this.spiderLeg2.addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F);
        this.spiderLeg2.setPos(4.0F, 15.0F, 2.0F);
        this.spiderLeg3 = new ModelRenderer(this, 18, 0);
        this.spiderLeg3.addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F);
        this.spiderLeg3.setPos(-4.0F, 15.0F, 1.0F);
        this.spiderLeg4 = new ModelRenderer(this, 18, 0);
        this.spiderLeg4.addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F);
        this.spiderLeg4.setPos(4.0F, 15.0F, 1.0F);
        this.spiderLeg5 = new ModelRenderer(this, 18, 0);
        this.spiderLeg5.addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F);
        this.spiderLeg5.setPos(-4.0F, 15.0F, 0.0F);
        this.spiderLeg6 = new ModelRenderer(this, 18, 0);
        this.spiderLeg6.addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F);
        this.spiderLeg6.setPos(4.0F, 15.0F, 0.0F);
        this.spiderLeg7 = new ModelRenderer(this, 18, 0);
        this.spiderLeg7.addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F);
        this.spiderLeg7.setPos(-4.0F, 15.0F, -1.0F);
        this.spiderLeg8 = new ModelRenderer(this, 18, 0);
        this.spiderLeg8.addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F);
        this.spiderLeg8.setPos(4.0F, 15.0F, -1.0F);
        this.cactus = new ModelRenderer(this, 0, 34);
        this.cactus.setPos(-7.0F, 1.5F, -6.0F);
        this.cactus.addBox(0.0F, 0.0F, 0.0F, 14.0F, 16.0F, 14.0F, 0.0F, 0.0F, 0.0F);
    }

    public Iterable<ModelRenderer> parts() {
        return ImmutableList.of(this.spiderHead, this.spiderLeg1, this.spiderLeg2, this.spiderLeg3, this.spiderLeg4, this.spiderLeg5, this.spiderLeg6, this.spiderLeg7, this.spiderLeg8, this.cactus);
    }

    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.spiderHead.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.spiderHead.xRot = headPitch * ((float)Math.PI / 180F);
        float f = ((float)Math.PI / 4F);
        this.spiderLeg1.zRot = (-(float)Math.PI / 4F);
        this.spiderLeg2.zRot = ((float)Math.PI / 4F);
        this.spiderLeg3.zRot = -0.58119464F;
        this.spiderLeg4.zRot = 0.58119464F;
        this.spiderLeg5.zRot = -0.58119464F;
        this.spiderLeg6.zRot = 0.58119464F;
        this.spiderLeg7.zRot = (-(float)Math.PI / 4F);
        this.spiderLeg8.zRot = ((float)Math.PI / 4F);
        float f1 = -0.0F;
        float f2 = ((float)Math.PI / 8F);
        this.spiderLeg1.yRot = ((float)Math.PI / 4F);
        this.spiderLeg2.yRot = (-(float)Math.PI / 4F);
        this.spiderLeg3.yRot = ((float)Math.PI / 8F);
        this.spiderLeg4.yRot = (-(float)Math.PI / 8F);
        this.spiderLeg5.yRot = (-(float)Math.PI / 8F);
        this.spiderLeg6.yRot = ((float)Math.PI / 8F);
        this.spiderLeg7.yRot = (-(float)Math.PI / 4F);
        this.spiderLeg8.yRot = ((float)Math.PI / 4F);
        float f3 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbSwingAmount;
        float f4 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + (float)Math.PI) * 0.4F) * limbSwingAmount;
        float f5 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + ((float)Math.PI / 2F)) * 0.4F) * limbSwingAmount;
        float f6 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + ((float)Math.PI * 1.5F)) * 0.4F) * limbSwingAmount;
        float f7 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 0.0F) * 0.4F) * limbSwingAmount;
        float f8 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + (float)Math.PI) * 0.4F) * limbSwingAmount;
        float f9 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + ((float)Math.PI / 2F)) * 0.4F) * limbSwingAmount;
        float f10 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + ((float)Math.PI * 1.5F)) * 0.4F) * limbSwingAmount;
        this.spiderLeg1.yRot += f3;
        this.spiderLeg2.yRot += -f3;
        this.spiderLeg3.yRot += f4;
        this.spiderLeg4.yRot += -f4;
        this.spiderLeg5.yRot += f5;
        this.spiderLeg6.yRot += -f5;
        this.spiderLeg7.yRot += f6;
        this.spiderLeg8.yRot += -f6;
        this.spiderLeg1.zRot += f7;
        this.spiderLeg2.zRot += -f7;
        this.spiderLeg3.zRot += f8;
        this.spiderLeg4.zRot += -f8;
        this.spiderLeg5.zRot += f9;
        this.spiderLeg6.zRot += -f9;
        this.spiderLeg7.zRot += f10;
        this.spiderLeg8.zRot += -f10;
    }
}