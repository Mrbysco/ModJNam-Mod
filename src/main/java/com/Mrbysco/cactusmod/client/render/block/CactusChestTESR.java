package com.mrbysco.cactusmod.client.render.block;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mrbysco.cactusmod.blocks.redstone.CactusChestBlock;
import com.mrbysco.cactusmod.client.ClientHandler;
import com.mrbysco.cactusmod.init.CactusRegistry;
import com.mrbysco.cactusmod.tileentities.CactusChestTile;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.tileentity.DualBrightnessCallback;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

public class CactusChestTESR <T extends TileEntity & IChestLid> extends TileEntityRenderer<T> {
	private final ModelRenderer singleLid;
	private final ModelRenderer singleBottom;
	private final ModelRenderer singleLatch;

	public CactusChestTESR(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);

		this.singleBottom = new ModelRenderer(64, 64, 0, 19);
		this.singleBottom.addBox(1.0F, 0.0F, 1.0F, 14.0F, 10.0F, 14.0F, 0.0F);
		this.singleLid = new ModelRenderer(64, 64, 0, 0);
		this.singleLid.addBox(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F, 0.0F);
		this.singleLid.y = 9.0F;
		this.singleLid.z = 1.0F;
		this.singleLatch = new ModelRenderer(64, 64, 0, 0);
		this.singleLatch.addBox(7.0F, -1.0F, 15.0F, 2.0F, 4.0F, 1.0F, 0.0F);
		this.singleLatch.y = 8.0F;
	}

	@Override
	public void render(T tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		World world = tileEntityIn.getLevel();
		boolean flag = world != null;

		BlockState blockstate = flag ? tileEntityIn.getBlockState() : CactusRegistry.CACTUS_CHEST.get().defaultBlockState().setValue(CactusChestBlock.FACING, Direction.SOUTH);
		Block block = blockstate.getBlock();

		if (block instanceof CactusChestBlock) {
			CactusChestBlock abstractchestblock = (CactusChestBlock)block;

			matrixStackIn.pushPose();
			float f = blockstate.getValue(ChestBlock.FACING).toYRot();
			matrixStackIn.translate(0.5D, 0.5D, 0.5D);
			matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-f));
			matrixStackIn.translate(-0.5D, -0.5D, -0.5D);

			TileEntityMerger.ICallbackWrapper<? extends CactusChestTile> icallbackwrapper;
			if (flag) {
				icallbackwrapper = abstractchestblock.getWrapper(blockstate, world, tileEntityIn.getBlockPos(), true);
			} else {
				icallbackwrapper = TileEntityMerger.ICallback::acceptNone;
			}

			float f1 = icallbackwrapper.<Float2FloatFunction>apply(CactusChestBlock.getLidRotationCallback(tileEntityIn)).get(partialTicks);
			f1 = 1.0F - f1;
			f1 = 1.0F - f1 * f1 * f1;
			int i = icallbackwrapper.<Int2IntFunction>apply(new DualBrightnessCallback<>()).applyAsInt(combinedLightIn);

			RenderMaterial rendermaterial = new RenderMaterial(Atlases.CHEST_SHEET, ClientHandler.CACTUS_CHEST_LOCATION);
			IVertexBuilder ivertexbuilder = rendermaterial.buffer(bufferIn, RenderType::entityCutout);

			this.renderModels(matrixStackIn, ivertexbuilder, this.singleLid, this.singleLatch, this.singleBottom, f1, i, combinedOverlayIn);

			matrixStackIn.popPose();
		}
	}

	private void renderModels(MatrixStack matrixStackIn, IVertexBuilder bufferIn, ModelRenderer chestLid, ModelRenderer chestLatch, ModelRenderer chestBottom, float lidAngle, int combinedLightIn, int combinedOverlayIn) {
		chestLid.xRot = -(lidAngle * ((float)Math.PI / 2F));
		chestLatch.xRot = chestLid.xRot;
		chestLid.render(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
		chestLatch.render(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
		chestBottom.render(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
	}
}
