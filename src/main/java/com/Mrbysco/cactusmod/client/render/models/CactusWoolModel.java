package com.mrbysco.cactusmod.client.render.models;

import com.mrbysco.cactusmod.entities.CactusSheepEntity;
import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class CactusWoolModel<T extends CactusSheepEntity> extends QuadrupedModel<T> {
	private float headRotationAngleX;

	public CactusWoolModel() {
		super(12, 0.0F, false, 8.0F, 4.0F, 2.0F, 2.0F, 24);
		this.head = new ModelRenderer(this, 0, 0);
		this.head.addBox(-3.0F, -4.0F, -4.0F, 6.0F, 6.0F, 6.0F, 0.6F);
		this.head.setPos(0.0F, 6.0F, -8.0F);
		this.body = new ModelRenderer(this, 28, 8);
		this.body.addBox(-4.0F, -10.0F, -7.0F, 8.0F, 16.0F, 6.0F, 1.75F);
		this.body.setPos(0.0F, 5.0F, 2.0F);
		float f = 0.5F;
		this.leg0 = new ModelRenderer(this, 0, 16);
		this.leg0.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.5F);
		this.leg0.setPos(-3.0F, 12.0F, 7.0F);
		this.leg1 = new ModelRenderer(this, 0, 16);
		this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.5F);
		this.leg1.setPos(3.0F, 12.0F, 7.0F);
		this.leg2 = new ModelRenderer(this, 0, 16);
		this.leg2.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.5F);
		this.leg2.setPos(-3.0F, 12.0F, -5.0F);
		this.leg3 = new ModelRenderer(this, 0, 16);
		this.leg3.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.5F);
		this.leg3.setPos(3.0F, 12.0F, -5.0F);
	}

	public void prepareMobModel(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
		super.prepareMobModel(entityIn, limbSwing, limbSwingAmount, partialTick);
		this.head.y = 6.0F + entityIn.getHeadRotationPointY(partialTick) * 9.0F;
		this.headRotationAngleX = entityIn.getHeadRotationAngleX(partialTick);
	}

	/**
	 * Sets this entity's model rotation angles
	 */
	public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		this.head.xRot = this.headRotationAngleX;
	}
}
