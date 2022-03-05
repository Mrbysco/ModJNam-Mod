package com.mrbysco.cactusmod.util;

import com.mrbysco.cactusmod.entities.AbstractSpikeEntity;
import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class ExplosionHelper {
	public static void arrowExplosion(@Nullable Entity entityIn, double x, double y, double z, float strength, boolean isSmoking) {
		if (entityIn.level.isClientSide) {
			entityIn.level.playLocalSound(x, y, z, SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4.0F, (1.0F + (entityIn.level.random.nextFloat() - entityIn.level.random.nextFloat()) * 0.2F) * 0.7F, false);
		}
		entityIn.level.addParticle(ParticleTypes.EXPLOSION, x, y, z, 1.0D, 0.0D, 0.0D);

		for (int i = 0; i <= 20; i++) {
			AbstractSpikeEntity spike = CactusRegistry.CACTUS_SPIKE.get().create(entityIn.level);
			spike.setDeltaMovement((entityIn.level.random.nextDouble() * 6D - 3D) * 0.3D, 0, (entityIn.level.random.nextDouble() * 6D - 3D) * 0.3D);
			spike.setPos(x, y + 0.8, z);

			entityIn.level.addFreshEntity(spike);
		}
		arrowExplosionB(entityIn, x, y, z, strength, isSmoking);
	}

	public static void arrowExplosionB(@Nullable Entity entityIn, double x, double y, double z, float strength, boolean isSmoking) {
		float f2 = strength * 2.0F;
		int k1 = Mth.floor(x - (double) f2 - 1.0D);
		int l1 = Mth.floor(x + (double) f2 + 1.0D);
		int i2 = Mth.floor(y - (double) f2 - 1.0D);
		int i1 = Mth.floor(y + (double) f2 + 1.0D);
		int j2 = Mth.floor(z - (double) f2 - 1.0D);
		int j1 = Mth.floor(z + (double) f2 + 1.0D);
		List<Entity> list = entityIn.level.getEntities(entityIn, new AABB((double) k1, (double) i2, (double) j2, (double) l1, (double) i1, (double) j1));
		Vec3 vector3d = new Vec3(x, y, z);

		for (int k2 = 0; k2 < list.size(); ++k2) {
			Entity entity = list.get(k2);
			if (!entity.ignoreExplosion()) {
				double d12 = (double) (Mth.sqrt((float) entity.distanceToSqr(vector3d)) / f2);
				if (d12 <= 1.0D) {
					double d5 = entity.getX() - x;
					double d7 = (entity instanceof PrimedTnt ? entity.getY() : entity.getEyeY()) - y;
					double d9 = entity.getZ() - z;
					double d13 = (double) Mth.sqrt((float) (d5 * d5 + d7 * d7 + d9 * d9));
					if (d13 != 0.0D) {
						d5 = d5 / d13;
						d7 = d7 / d13;
						d9 = d9 / d13;
						double d14 = (double) Explosion.getSeenPercent(vector3d, entity);
						double d10 = (1.0D - d12) * d14;
						entity.hurt(DamageSource.thorns(entityIn), (float) ((int) ((d10 * d10 + d10) / 2.0D * 7.0D * (double) f2 + 1.0D)));
						double d11 = d10;
						if (entity instanceof LivingEntity) {
							d11 = ProtectionEnchantment.getExplosionKnockbackAfterDampener((LivingEntity) entity, d10);
						}

						entity.setDeltaMovement(entity.getDeltaMovement().add(d5 * d11, d7 * d11, d9 * d11));
					}
				}
			}
		}
	}
}
