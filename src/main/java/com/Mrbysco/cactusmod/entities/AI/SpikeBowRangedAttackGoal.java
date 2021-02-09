package com.mrbysco.cactusmod.entities.AI;

import com.mrbysco.cactusmod.items.CactusBowItem;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.entity.monster.MonsterEntity;

public class SpikeBowRangedAttackGoal<T extends MonsterEntity & IRangedAttackMob> extends RangedBowAttackGoal {
    private final T entity;

    public SpikeBowRangedAttackGoal(T mob, double moveSpeedAmpIn, int attackCooldownIn, float maxAttackDistanceIn) {
        super(mob, moveSpeedAmpIn, attackCooldownIn, maxAttackDistanceIn);
        this.entity = mob;
    }

    @Override
    protected boolean isBowInMainhand() {
        return this.entity.func_233634_a_(item -> item instanceof CactusBowItem);
    }
}