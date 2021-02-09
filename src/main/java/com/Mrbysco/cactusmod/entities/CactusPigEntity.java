package com.mrbysco.cactusmod.entities;

import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.Tags.Blocks;

import java.util.Random;

public class CactusPigEntity extends PigEntity implements ICactusMob {

	public CactusPigEntity(EntityType<? extends PigEntity> entityType, World worldIn) {
		super(entityType, worldIn);
	}

	public CactusPigEntity func_241840_a(ServerWorld worldIn, AgeableEntity p_241840_2_) {
		return CactusRegistry.CACTUS_PIG.get().create(worldIn);
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 10.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D);
	}

	public static boolean canAnimalSpawn(EntityType<? extends AnimalEntity> animal, IWorld worldIn, SpawnReason reason, BlockPos pos, Random random) {
		return worldIn.getBlockState(pos.down()).isIn(Blocks.SAND) && worldIn.getLightSubtracted(pos, 0) > 8;
	}
}
