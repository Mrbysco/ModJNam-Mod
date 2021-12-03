package com.mrbysco.cactusmod.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.mrbysco.cactusmod.blockentities.CactusChestBlockEntity;
import com.mrbysco.cactusmod.blocks.redstone.CactusChestBlock;
import com.mrbysco.cactusmod.client.ClientHandler;
import com.mrbysco.cactusmod.init.CactusRegistry;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CactusChestRenderer<T extends BlockEntity & LidBlockEntity> implements BlockEntityRenderer<T> {
	private final ModelPart lid;
	private final ModelPart bottom;
	private final ModelPart lock;

	public CactusChestRenderer(BlockEntityRendererProvider.Context context) {
		ModelPart modelpart = context.bakeLayer(ModelLayers.CHEST);
		this.bottom = modelpart.getChild("bottom");
		this.lid = modelpart.getChild("lid");
		this.lock = modelpart.getChild("lock");
	}

	@Override
	public void render(T blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLightIn, int combinedOverlayIn) {
		Level world = blockEntity.getLevel();
		boolean flag = world != null;

		BlockState blockstate = flag ? blockEntity.getBlockState() : CactusRegistry.CACTUS_CHEST.get().defaultBlockState().setValue(CactusChestBlock.FACING, Direction.SOUTH);
		Block block = blockstate.getBlock();

		if (block instanceof CactusChestBlock abstractchestblock) {

			poseStack.pushPose();
			float f = blockstate.getValue(CactusChestBlock.FACING).toYRot();
			poseStack.translate(0.5D, 0.5D, 0.5D);
			poseStack.mulPose(Vector3f.YP.rotationDegrees(-f));
			poseStack.translate(-0.5D, -0.5D, -0.5D);

			DoubleBlockCombiner.NeighborCombineResult<? extends CactusChestBlockEntity> icallbackwrapper;
			if (flag) {
				icallbackwrapper = abstractchestblock.getWrapper(blockstate, world, blockEntity.getBlockPos(), true);
			} else {
				icallbackwrapper = DoubleBlockCombiner.Combiner::acceptNone;
			}

			float f1 = icallbackwrapper.<Float2FloatFunction>apply(CactusChestBlock.opennessCombiner(blockEntity)).get(partialTicks);
			f1 = 1.0F - f1;
			f1 = 1.0F - f1 * f1 * f1;
			int i = icallbackwrapper.<Int2IntFunction>apply(new BrightnessCombiner<>()).applyAsInt(combinedLightIn);

			Material rendermaterial = new Material(Sheets.CHEST_SHEET, ClientHandler.CACTUS_CHEST_LOCATION);
			VertexConsumer ivertexbuilder = rendermaterial.buffer(bufferSource, RenderType::entityCutout);

			this.render(poseStack, ivertexbuilder, this.lid, this.lock, this.bottom, f1, i, combinedOverlayIn);

			poseStack.popPose();
		}
	}

	private void render(PoseStack poseStack, VertexConsumer consumer, ModelPart part, ModelPart part1, ModelPart part2, float p_112375_, int p_112376_, int combinedOverlayIn) {
		part.xRot = -(p_112375_ * ((float)Math.PI / 2F));
		part1.xRot = part.xRot;
		part.render(poseStack, consumer, p_112376_, combinedOverlayIn);
		part1.render(poseStack, consumer, p_112376_, combinedOverlayIn);
		part2.render(poseStack, consumer, p_112376_, combinedOverlayIn);
	}
}
