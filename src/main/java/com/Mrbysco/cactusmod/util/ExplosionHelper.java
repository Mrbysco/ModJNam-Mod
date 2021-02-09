package com.mrbysco.cactusmod.util;

import com.mrbysco.cactusmod.entities.AbstractSpikeEntity;
import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;

import javax.annotation.Nullable;
import java.util.List;

public class ExplosionHelper {
    public static void arrowExplosion(@Nullable Entity entityIn, double x, double y, double z, float strength, boolean isSmoking) {
        if (entityIn.world.isRemote) {
            entityIn.world.playSound(x, y, z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (entityIn.world.rand.nextFloat() - entityIn.world.rand.nextFloat()) * 0.2F) * 0.7F, false);
        }
        entityIn.world.addParticle(ParticleTypes.EXPLOSION, x, y, z, 1.0D, 0.0D, 0.0D);

        for(int i = 0; i <= 20; i++) {
            AbstractSpikeEntity spike = CactusRegistry.CACTUS_SPIKE.get().create(entityIn.world);
            spike.setMotion((entityIn.world.rand.nextDouble() * 6D - 3D) * 0.3D, 0, (entityIn.world.rand.nextDouble() * 6D - 3D) * 0.3D);
        	spike.setPosition(x, y + 0.8, z);

            entityIn.world.addEntity(spike);
        }
        arrowExplosionB(entityIn, x, y, z, strength, isSmoking);
    }

    public static void arrowExplosionB(@Nullable Entity entityIn, double x, double y, double z, float strength, boolean isSmoking) {
        float f2 = strength * 2.0F;
        int k1 = MathHelper.floor(x - (double)f2 - 1.0D);
        int l1 = MathHelper.floor(x + (double)f2 + 1.0D);
        int i2 = MathHelper.floor(y - (double)f2 - 1.0D);
        int i1 = MathHelper.floor(y + (double)f2 + 1.0D);
        int j2 = MathHelper.floor(z - (double)f2 - 1.0D);
        int j1 = MathHelper.floor(z + (double)f2 + 1.0D);
        List<Entity> list = entityIn.world.getEntitiesWithinAABBExcludingEntity(entityIn, new AxisAlignedBB((double)k1, (double)i2, (double)j2, (double)l1, (double)i1, (double)j1));
        Vector3d vector3d = new Vector3d(x, y, z);

        for(int k2 = 0; k2 < list.size(); ++k2) {
            Entity entity = list.get(k2);
            if (!entity.isImmuneToExplosions()) {
                double d12 = (double)(MathHelper.sqrt(entity.getDistanceSq(vector3d)) / f2);
                if (d12 <= 1.0D) {
                    double d5 = entity.getPosX() - x;
                    double d7 = (entity instanceof TNTEntity ? entity.getPosY() : entity.getPosYEye()) - y;
                    double d9 = entity.getPosZ() - z;
                    double d13 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7 + d9 * d9);
                    if (d13 != 0.0D) {
                        d5 = d5 / d13;
                        d7 = d7 / d13;
                        d9 = d9 / d13;
                        double d14 = (double) Explosion.getBlockDensity(vector3d, entity);
                        double d10 = (1.0D - d12) * d14;
                        entity.attackEntityFrom(DamageSource.causeThornsDamage(entityIn), (float)((int)((d10 * d10 + d10) / 2.0D * 7.0D * (double)f2 + 1.0D)));
                        double d11 = d10;
                        if (entity instanceof LivingEntity) {
                            d11 = ProtectionEnchantment.getBlastDamageReduction((LivingEntity)entity, d10);
                        }

                        entity.setMotion(entity.getMotion().add(d5 * d11, d7 * d11, d9 * d11));
                    }
                }
            }
        }
    }
}
