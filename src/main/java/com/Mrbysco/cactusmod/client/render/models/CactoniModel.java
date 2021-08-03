package com.mrbysco.cactusmod.client.render.models;

import com.mrbysco.cactusmod.entities.CactoniEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class CactoniModel<T extends CactoniEntity> extends HierarchicalModel<T> {
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart torso;
    private final ModelPart left_arm;
    private final ModelPart right_arm;

    public CactoniModel(ModelPart part) {
        this.root = part;

        this.head = part.getChild("head");
        this.torso = part.getChild("torso");
        this.left_arm = part.getChild("left_arm");
        this.right_arm = part.getChild("right_arm");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("head", CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-5.0F, -9.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(-0.5F))
                        .texOffs(4, 88).addBox(5.0F, -10.0F, 6.0F, 1.0F, 1.0F, 1.0F)
                        .texOffs(0, 82).addBox(-5.0F, -10.0F, -8.0F, 10.0F, 1.0F, 1.0F)
                        .texOffs(0, 84).addBox(6.0F, -10.0F, -6.0F, 1.0F, 1.0F, 1.0F)
                        .texOffs(22, 69).addBox(-5.0F, -9.0F, -7.0F, 10.0F, 1.0F, 1.0F)
                        .texOffs(0, 71).addBox(-7.0F, -9.0F, -5.0F, 1.0F, 1.0F, 10.0F)
                        .texOffs(0, 107).addBox(-3.0F, -17.0F, -3.0F, 6.0F, 4.0F, 6.0F)
                        .texOffs(0, 84).addBox(-8.0F, -10.0F, -5.0F, 1.0F, 1.0F, 10.0F)
                        .texOffs(22, 71).addBox(6.0F, -9.0F, -5.0F, 1.0F, 1.0F, 10.0F)
                        .texOffs(4, 84).addBox(5.0F, -10.0F, -7.0F, 1.0F, 1.0F, 1.0F)
                        .texOffs(0, 69).addBox(-5.0F, -9.0F, 6.0F, 10.0F, 1.0F, 1.0F)
                        .texOffs(0, 95).addBox(-4.0F, -13.0F, -4.0F, 8.0F, 4.0F, 8.0F)
                        .texOffs(0, 88).addBox(6.0F, -10.0F, 5.0F, 1.0F, 1.0F, 1.0F)
                        .texOffs(4, 90).addBox(-7.0F, -10.0F, 5.0F, 1.0F, 1.0F, 1.0F)
                        .texOffs(22, 82).addBox(-5.0F, -10.0F, 7.0F, 10.0F, 1.0F, 1.0F)
                        .texOffs(4, 86).addBox(-7.0F, -10.0F, -6.0F, 1.0F, 1.0F, 1.0F)
                        .texOffs(22, 84).addBox(7.0F, -10.0F, -5.0F, 1.0F, 1.0F, 10.0F)
                        .texOffs(0, 90).addBox(-6.0F, -10.0F, 6.0F, 1.0F, 1.0F, 1.0F)
                        .texOffs(0, 86).addBox(-6.0F, -10.0F, -7.0F, 1.0F, 1.0F, 1.0F)
                        .texOffs(0, 56).addBox(-6.0F, -9.0F, -6.0F, 12.0F, 1.0F, 12.0F)
                        .texOffs(40, 0).addBox(-2.0F, -4.0F, -5.5F, 2.0F, 1.0F, 1.0F)
                        .texOffs(40, 0).addBox(-5.0F, -3.0F, -5.5F, 1.0F, 1.0F, 1.0F)
                        .texOffs(40, 0).addBox(2.0F, -2.0F, -5.5F, 4.0F, 1.0F, 1.0F)
                        .texOffs(40, 0).addBox(-5.0F, -2.0F, -5.5F, 4.0F, 1.0F, 1.0F)
                        .texOffs(40, 0).addBox(1.0F, -4.0F, -5.5F, 2.0F, 1.0F, 1.0F)
                        .texOffs(40, 0).addBox(5.0F, -3.0F, -5.5F, 1.0F, 1.0F, 1.0F)
                        .texOffs(40, 0).addBox(-3.0F, -3.0F, -5.5F, 7.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));

        partdefinition.addOrReplaceChild("torso", CubeListBuilder.create()
                        .texOffs(0, 22).addBox(-5.0F, -11.5F, -5.0F, 10.0F, 12.0F, 10.0F, new CubeDeformation(-0.5F))
                        .texOffs(40, 22).addBox(-5.0F, -0.5F, -5.0F, 10.0F, 12.0F, 10.0F, new CubeDeformation(-0.5F)),
                PartPose.offset(0.0F, 12.5F, 0.0F));

        partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create()
                        .texOffs(0, 44).addBox(-0.5F, -3.5F, -3.0F, 6.0F, 6.0F, 6.0F)
                        .texOffs(0, 44).addBox(5.5F, -3.5F, -3.0F, 6.0F, 6.0F, 6.0F)
                        .texOffs(0, 44).addBox(5.5F, -9.5F, -3.0F, 6.0F, 6.0F, 6.0F),
                PartPose.offset(5.0F, 6.5F, 0.0F));

        partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create()
                        .texOffs(0, 44).addBox(-6.5F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F)
                        .texOffs(0, 44).addBox(-12.5F, -9.0F, -3.0F, 6.0F, 6.0F, 6.0F)
                        .texOffs(0, 44).addBox(-12.5F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F),
                PartPose.offset(-4.0F, 10.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public ModelPart root() {
        return root;
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.yRot = netHeadYaw * 0.017453292F;
        this.head.xRot = headPitch * 0.017453292F;
        this.left_arm.xRot = Mth.cos(limbSwing * 0.6662F) * 0.8F * limbSwingAmount * 0.5F;
        this.right_arm.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount * 0.5F;
        this.left_arm.yRot = 0.0F;
        this.right_arm.yRot = 0.0F;
    }
}
