package com.mrbysco.cactusmod.entities.AI;

import com.mrbysco.cactusmod.items.CactusBowItem;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;

public class SpikeBowRangedAttackGoal<T extends Monster & RangedAttackMob> extends RangedBowAttackGoal {
	private final T entity;

	public SpikeBowRangedAttackGoal(T mob, double moveSpeedAmpIn, int attackCooldownIn, float maxAttackDistanceIn) {
		super(mob, moveSpeedAmpIn, attackCooldownIn, maxAttackDistanceIn);
		this.entity = mob;
	}

	@Override
	protected boolean isHoldingBow() {
		return this.entity.isHolding(stack -> stack.getItem() instanceof CactusBowItem);
	}
}