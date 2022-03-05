package com.mrbysco.cactusmod.client.render.models;

import com.mrbysco.cactusmod.entities.hostile.CactusSpiderEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class CactusSpiderModel<T extends CactusSpiderEntity> extends HierarchicalModel<T> {
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart rightHindLeg;
	private final ModelPart leftHindLeg;
	private final ModelPart rightMiddleHindLeg;
	private final ModelPart leftMiddleHindLeg;
	private final ModelPart rightMiddleFrontLeg;
	private final ModelPart leftMiddleFrontLeg;
	private final ModelPart rightFrontLeg;
	private final ModelPart leftFrontLeg;

	public CactusSpiderModel(ModelPart part) {
		this.root = part;
		this.head = part.getChild("head");
		this.rightHindLeg = part.getChild("right_hind_leg");
		this.leftHindLeg = part.getChild("left_hind_leg");
		this.rightMiddleHindLeg = part.getChild("right_middle_hind_leg");
		this.leftMiddleHindLeg = part.getChild("left_middle_hind_leg");
		this.rightMiddleFrontLeg = part.getChild("right_middle_front_leg");
		this.leftMiddleFrontLeg = part.getChild("left_middle_front_leg");
		this.rightFrontLeg = part.getChild("right_front_leg");
		this.leftFrontLeg = part.getChild("left_front_leg");
	}

	public static LayerDefinition createSpiderBodyLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot();
		var1.addOrReplaceChild("head", CubeListBuilder.create().texOffs(32, 4)
						.addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F),
				PartPose.offset(0.0F, 15.0F, -4.0F));
		var1.addOrReplaceChild("cactus", CubeListBuilder.create().texOffs(0, 34)
						.addBox(0.0F, 0.0F, 0.0F, 14.0F, 16.0F, 14.0F),
				PartPose.offset(-7.0F, 1.5F, -6.0F));
		CubeListBuilder rightLeg = CubeListBuilder.create().texOffs(18, 0)
				.addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F);
		CubeListBuilder leftLeg = CubeListBuilder.create().texOffs(18, 0)
				.addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F);
		var1.addOrReplaceChild("right_hind_leg", rightLeg, PartPose.offset(-4.0F, 15.0F, 2.0F));
		var1.addOrReplaceChild("left_hind_leg", leftLeg, PartPose.offset(4.0F, 15.0F, 2.0F));
		var1.addOrReplaceChild("right_middle_hind_leg", rightLeg, PartPose.offset(-4.0F, 15.0F, 1.0F));
		var1.addOrReplaceChild("left_middle_hind_leg", leftLeg, PartPose.offset(4.0F, 15.0F, 1.0F));
		var1.addOrReplaceChild("right_middle_front_leg", rightLeg, PartPose.offset(-4.0F, 15.0F, 0.0F));
		var1.addOrReplaceChild("left_middle_front_leg", leftLeg, PartPose.offset(4.0F, 15.0F, 0.0F));
		var1.addOrReplaceChild("right_front_leg", rightLeg, PartPose.offset(-4.0F, 15.0F, -1.0F));
		var1.addOrReplaceChild("left_front_leg", leftLeg, PartPose.offset(4.0F, 15.0F, -1.0F));
		return LayerDefinition.create(var0, 64, 64);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.yRot = netHeadYaw * 0.017453292F;
		this.head.xRot = headPitch * 0.017453292F;
		float var7 = 0.7853982F;
		this.rightHindLeg.zRot = -0.7853982F;
		this.leftHindLeg.zRot = 0.7853982F;
		this.rightMiddleHindLeg.zRot = -0.58119464F;
		this.leftMiddleHindLeg.zRot = 0.58119464F;
		this.rightMiddleFrontLeg.zRot = -0.58119464F;
		this.leftMiddleFrontLeg.zRot = 0.58119464F;
		this.rightFrontLeg.zRot = -0.7853982F;
		this.leftFrontLeg.zRot = 0.7853982F;
		float var8 = -0.0F;
		float var9 = 0.3926991F;
		this.rightHindLeg.yRot = 0.7853982F;
		this.leftHindLeg.yRot = -0.7853982F;
		this.rightMiddleHindLeg.yRot = 0.3926991F;
		this.leftMiddleHindLeg.yRot = -0.3926991F;
		this.rightMiddleFrontLeg.yRot = -0.3926991F;
		this.leftMiddleFrontLeg.yRot = 0.3926991F;
		this.rightFrontLeg.yRot = -0.7853982F;
		this.leftFrontLeg.yRot = 0.7853982F;
		float var10 = -(Mth.cos(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbSwingAmount;
		float var11 = -(Mth.cos(limbSwing * 0.6662F * 2.0F + 3.1415927F) * 0.4F) * limbSwingAmount;
		float var12 = -(Mth.cos(limbSwing * 0.6662F * 2.0F + 1.5707964F) * 0.4F) * limbSwingAmount;
		float var13 = -(Mth.cos(limbSwing * 0.6662F * 2.0F + 4.712389F) * 0.4F) * limbSwingAmount;
		float var14 = Math.abs(Mth.sin(limbSwing * 0.6662F + 0.0F) * 0.4F) * limbSwingAmount;
		float var15 = Math.abs(Mth.sin(limbSwing * 0.6662F + 3.1415927F) * 0.4F) * limbSwingAmount;
		float var16 = Math.abs(Mth.sin(limbSwing * 0.6662F + 1.5707964F) * 0.4F) * limbSwingAmount;
		float var17 = Math.abs(Mth.sin(limbSwing * 0.6662F + 4.712389F) * 0.4F) * limbSwingAmount;
		ModelPart legVariable = this.rightHindLeg;
		legVariable.yRot += var10;
		legVariable = this.leftHindLeg;
		legVariable.yRot += -var10;
		legVariable = this.rightMiddleHindLeg;
		legVariable.yRot += var11;
		legVariable = this.leftMiddleHindLeg;
		legVariable.yRot += -var11;
		legVariable = this.rightMiddleFrontLeg;
		legVariable.yRot += var12;
		legVariable = this.leftMiddleFrontLeg;
		legVariable.yRot += -var12;
		legVariable = this.rightFrontLeg;
		legVariable.yRot += var13;
		legVariable = this.leftFrontLeg;
		legVariable.yRot += -var13;
		legVariable = this.rightHindLeg;
		legVariable.zRot += var14;
		legVariable = this.leftHindLeg;
		legVariable.zRot += -var14;
		legVariable = this.rightMiddleHindLeg;
		legVariable.zRot += var15;
		legVariable = this.leftMiddleHindLeg;
		legVariable.zRot += -var15;
		legVariable = this.rightMiddleFrontLeg;
		legVariable.zRot += var16;
		legVariable = this.leftMiddleFrontLeg;
		legVariable.zRot += -var16;
		legVariable = this.rightFrontLeg;
		legVariable.zRot += var17;
		legVariable = this.leftFrontLeg;
		legVariable.zRot += -var17;
	}
}