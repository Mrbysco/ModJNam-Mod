package com.mrbysco.cactusmod.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.entities.CactiCartEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.MinecartModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class CactusCartRenderer<T extends CactiCartEntity> extends EntityRenderer<T> {
	private static final ResourceLocation CACTICART = new ResourceLocation(Reference.MOD_ID, "textures/entity/cacticart.png");
	protected final EntityModel<T> modelMinecart;

	public CactusCartRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.modelMinecart = new MinecartModel<>(context.bakeLayer(ModelLayers.MINECART));
		this.shadowRadius = 0.7F;
	}

	@Override
	public ResourceLocation getTextureLocation(CactiCartEntity entity) {
		return CACTICART;
	}

	@Override
	public void render(T entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLightIn) {
		super.render(entityIn, entityYaw, partialTicks, poseStack, bufferSource, packedLightIn);
		poseStack.pushPose();
		long i = (long) entityIn.getId() * 493286711L;
		i = i * i * 4392167121L + i * 98761L;
		float f = (((float) (i >> 16 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
		float f1 = (((float) (i >> 20 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
		float f2 = (((float) (i >> 24 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
		poseStack.translate((double) f, (double) f1, (double) f2);
		double d0 = Mth.lerp((double) partialTicks, entityIn.xOld, entityIn.getX());
		double d1 = Mth.lerp((double) partialTicks, entityIn.yOld, entityIn.getY());
		double d2 = Mth.lerp((double) partialTicks, entityIn.zOld, entityIn.getZ());
		double d3 = (double) 0.3F;
		Vec3 vector3d = entityIn.getPos(d0, d1, d2);
		float f3 = Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot());
		if (vector3d != null) {
			Vec3 vector3d1 = entityIn.getPosOffs(d0, d1, d2, (double) 0.3F);
			Vec3 vector3d2 = entityIn.getPosOffs(d0, d1, d2, (double) -0.3F);
			if (vector3d1 == null) {
				vector3d1 = vector3d;
			}

			if (vector3d2 == null) {
				vector3d2 = vector3d;
			}

			poseStack.translate(vector3d.x - d0, (vector3d1.y + vector3d2.y) / 2.0D - d1, vector3d.z - d2);
			Vec3 vector3d3 = vector3d2.add(-vector3d1.x, -vector3d1.y, -vector3d1.z);
			if (vector3d3.length() != 0.0D) {
				vector3d3 = vector3d3.normalize();
				entityYaw = (float) (Math.atan2(vector3d3.z, vector3d3.x) * 180.0D / Math.PI);
				f3 = (float) (Math.atan(vector3d3.y) * 73.0D);
			}
		}

		poseStack.translate(0.0D, 0.375D, 0.0D);
		poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - entityYaw));
		poseStack.mulPose(Vector3f.ZP.rotationDegrees(-f3));
		float f5 = (float) entityIn.getHurtTime() - partialTicks;
		float f6 = entityIn.getDamage() - partialTicks;
		if (f6 < 0.0F) {
			f6 = 0.0F;
		}

		if (f5 > 0.0F) {
			poseStack.mulPose(Vector3f.XP.rotationDegrees(Mth.sin(f5) * f5 * f6 / 10.0F * (float) entityIn.getHurtDir()));
		}

		int j = entityIn.getDisplayOffset();
		BlockState blockstate = entityIn.getDisplayBlockState();
		if (blockstate.getRenderShape() != RenderShape.INVISIBLE) {
			poseStack.pushPose();
			float f4 = 0.75F;
			poseStack.scale(0.75F, 0.75F, 0.75F);
			poseStack.translate(-0.5D, (double) ((float) (j - 8) / 16.0F), 0.5D);
			poseStack.mulPose(Vector3f.YP.rotationDegrees(90.0F));
			this.renderBlockState(entityIn, partialTicks, blockstate, poseStack, bufferSource, packedLightIn);
			poseStack.popPose();
		}

		poseStack.scale(-1.0F, -1.0F, 1.0F);
		this.modelMinecart.setupAnim(entityIn, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F);
		VertexConsumer ivertexbuilder = bufferSource.getBuffer(this.modelMinecart.renderType(this.getTextureLocation(entityIn)));
		this.modelMinecart.renderToBuffer(poseStack, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		poseStack.popPose();
	}

	protected void renderBlockState(T entityIn, float partialTicks, BlockState stateIn, PoseStack poseStack, MultiBufferSource bufferSource, int packedLightIn) {
		Minecraft.getInstance().getBlockRenderer().renderSingleBlock(stateIn, poseStack, bufferSource, packedLightIn, OverlayTexture.NO_OVERLAY);
	}
}