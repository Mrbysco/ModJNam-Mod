package com.mrbysco.cactusmod.entities.goals;

import com.mrbysco.cactusmod.entities.CactusSheepEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.EventHooks;

import java.util.EnumSet;

public class EatSandGoal extends Goal {
	/**
	 * The entity owner of this AITask
	 */
	private final CactusSheepEntity sandEaterEntity;
	/**
	 * The world the sand eater entity is eating from
	 */
	private final Level level;
	/**
	 * Number of ticks since the entity started to eat sand
	 */
	private int eatingSandTimer;

	public EatSandGoal(CactusSheepEntity sandEaterEntityIn) {
		this.sandEaterEntity = sandEaterEntityIn;
		this.level = sandEaterEntityIn.level();
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
	}

	public boolean canUse() {
		if (this.sandEaterEntity.getRandom().nextInt(this.sandEaterEntity.isBaby() ? 50 : 1000) != 0) {
			return false;
		} else {
			BlockPos blockpos = this.sandEaterEntity.blockPosition();
			return this.level.getBlockState(blockpos.below()).is(Tags.Blocks.SAND);
		}
	}

	public void start() {
		this.eatingSandTimer = 40;
		this.level.broadcastEntityEvent(this.sandEaterEntity, (byte) 10);
		this.sandEaterEntity.getNavigation().stop();
	}

	public void stop() {
		this.eatingSandTimer = 0;
	}

	public boolean canContinueToUse() {
		return this.eatingSandTimer > 0;
	}

	public int getEatingSandTimer() {
		return this.eatingSandTimer;
	}

	@Override
	public void tick() {
		this.eatingSandTimer = Math.max(0, this.eatingSandTimer - 1);
		if (this.eatingSandTimer == 4) {
			BlockPos blockpos = this.sandEaterEntity.blockPosition();
			if (this.level.getBlockState(blockpos).is(Tags.Blocks.SAND)) {
				if (EventHooks.getMobGriefingEvent(this.level, this.sandEaterEntity)) {
					this.level.destroyBlock(blockpos, false);
				}

				this.sandEaterEntity.eatSandBonus();
			} else {
				BlockPos blockpos1 = blockpos.below();
				BlockState state = this.level.getBlockState(blockpos1);
				if (state.is(Tags.Blocks.SAND)) {
					if (EventHooks.getMobGriefingEvent(this.level, this.sandEaterEntity)) {
						this.level.levelEvent(2001, blockpos1, Block.getId(state));
						this.level.setBlock(blockpos1, state, 2);
					}

					this.sandEaterEntity.eatSandBonus();
				}
			}
		}
	}
}