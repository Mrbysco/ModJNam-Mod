package com.mrbysco.cactusmod.items;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class CustomSpawnEggItem extends Item {
	private static final Map<Supplier<EntityType<?>>, CustomSpawnEggItem> EGGS = Maps.newIdentityHashMap();
	private final Supplier<EntityType<?>> typeIn;
	private final int primaryColor;
	private final int secondaryColor;

	public CustomSpawnEggItem(Supplier<EntityType<?>> type, int primary, int secondary, Properties properties) {
		super(properties);
		this.typeIn = type;
		this.primaryColor = primary;
		this.secondaryColor = secondary;
		EGGS.put(type, this);
	}

	public InteractionResult useOn(UseOnContext context) {
		Level worldIn = context.getLevel();
		if (worldIn.isClientSide) {
			return InteractionResult.SUCCESS;
		} else {
			ItemStack stack = context.getItemInHand();
			BlockPos pos = context.getClickedPos();
			Direction dir = context.getClickedFace();
			BlockState state = worldIn.getBlockState(pos);
			Block block = state.getBlock();
			if (block == Blocks.SPAWNER) {
				BlockEntity tile = worldIn.getBlockEntity(pos);
				if (tile instanceof SpawnerBlockEntity) {
					BaseSpawner spawner = ((SpawnerBlockEntity)tile).getSpawner();
					EntityType<?> type = this.getType(stack.getTag());
					spawner.setEntityId(type);
					tile.setChanged();
					worldIn.sendBlockUpdated(pos, state, state, 3);
					stack.shrink(1);
					return InteractionResult.SUCCESS;
				}
			}

			BlockPos pos2;
			if (state.getBlockSupportShape(worldIn, pos).isEmpty()) {
				pos2 = pos;
			} else {
				pos2 = pos.relative(dir);
			}

			EntityType<?> type = this.getType(stack.getTag());
			if (!worldIn.isClientSide && type.spawn((ServerLevel)worldIn, stack, context.getPlayer(), pos2, MobSpawnType.SPAWN_EGG, true, !Objects.equals(pos, pos2) && dir == Direction.UP) != null) {
				stack.shrink(1);
			}

			return InteractionResult.SUCCESS;
		}
	}

	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		HitResult raytraceresult = getPlayerPOVHitResult(worldIn, playerIn, ClipContext.Fluid.SOURCE_ONLY);
		if (raytraceresult.getType() != HitResult.Type.BLOCK) {
			return InteractionResultHolder.pass(itemstack);
		} else if (worldIn.isClientSide) {
			return InteractionResultHolder.success(itemstack);
		} else {
			BlockHitResult blockraytraceresult = (BlockHitResult)raytraceresult;
			BlockPos blockpos = blockraytraceresult.getBlockPos();
			if (!(worldIn.getBlockState(blockpos).getBlock() instanceof LiquidBlock)) {
				return InteractionResultHolder.pass(itemstack);
			} else if (worldIn.mayInteract(playerIn, blockpos) && playerIn.mayUseItemAt(blockpos, blockraytraceresult.getDirection(), itemstack)) {
				EntityType<?> entitytype = this.getType(itemstack.getTag());
				if (!worldIn.isClientSide && entitytype.spawn((ServerLevel)worldIn, itemstack, playerIn, blockpos, MobSpawnType.SPAWN_EGG, false, false) == null) {
					return InteractionResultHolder.pass(itemstack);
				} else {
					if (!playerIn.getAbilities().instabuild) {
						itemstack.shrink(1);
					}

					playerIn.awardStat(Stats.ITEM_USED.get(this));
					return InteractionResultHolder.success(itemstack);
				}
			} else {
				return InteractionResultHolder.fail(itemstack);
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public int getColor(int p_195983_1_) {
		return p_195983_1_ == 0 ? this.primaryColor : this.secondaryColor;
	}

	public static Iterable<CustomSpawnEggItem> getEggs() {
		return Iterables.unmodifiableIterable(EGGS.values());
	}

	public EntityType<?> getType(@Nullable CompoundTag compound) {
		if (compound != null && compound.contains("EntityTag", 10)) {
			CompoundTag lvt_2_1_ = compound.getCompound("EntityTag");
			if (lvt_2_1_.contains("id", 8)) {
				return (EntityType)EntityType.byString(lvt_2_1_.getString("id")).orElse(this.typeIn.get());
			}
		}

		return this.typeIn.get();
	}
}