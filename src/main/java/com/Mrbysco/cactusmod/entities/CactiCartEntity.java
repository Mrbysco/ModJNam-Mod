package com.mrbysco.cactusmod.entities;

import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class CactiCartEntity extends AbstractMinecartEntity implements ICactusMob{
    private int timeInCart;

	public CactiCartEntity(EntityType<?> type, World world) {
        super(type, world);
	}
	
	public CactiCartEntity(World worldIn, double x, double y, double z) {
        super(CactusRegistry.CACTUS_CART_ENTITY.get(), worldIn, x, y, z);
    }

    public CactiCartEntity(FMLPlayMessages.SpawnEntity spawnEntity, World worldIn) {
        this(CactusRegistry.CACTUS_CART_ENTITY.get(), worldIn);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public static AttributeModifierMap.MutableAttribute func_234226_m_() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 4.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.2F);
    }

    @Override
    public ItemStack getCartItem() {
        return new ItemStack(CactusRegistry.CACTUS_CART.get());
    }

    @Override
	public boolean hasDisplayTile() {
		return false;
	}

	@Override
	public boolean canBeRidden() {
		return true;
	}
	
	@Override
	public boolean isPoweredCart() {
		return false;
	}

    @Override
    public Type getMinecartType() {
        return Type.RIDEABLE;
    }

    @Override
    public ActionResultType processInitialInteract(PlayerEntity player, Hand hand) {
        ActionResultType ret = super.processInitialInteract(player, hand);
        if (ret.isSuccessOrConsume()) return ret;
        if (player.isSecondaryUseActive()) {
            return ActionResultType.PASS;
        } else if (this.isBeingRidden()) {
            return ActionResultType.PASS;
        } else if (!this.world.isRemote) {
            return player.startRiding(this) ? ActionResultType.CONSUME : ActionResultType.PASS;
        } else {
            return ActionResultType.SUCCESS;
        }
    }

    @Override
    public void onActivatorRailPass(int x, int y, int z, boolean receivingPower) {
        if (receivingPower) {
            if (this.isBeingRidden()) {
                this.removePassengers();
            }

            if (this.getRollingAmplitude() == 0) {
                this.setRollingDirection(-this.getRollingDirection());
                this.setRollingAmplitude(10);
                this.setDamage(50.0F);
                this.markVelocityChanged();
            }
        }

    }

    @Override
    public void tick() {
        super.tick();
        if(!this.world.isRemote) {
            if(this.isBeingRidden()) {
                Entity entity = this.getPassengers().get(0);
                if(entity instanceof LivingEntity && !(entity instanceof ICactusMob)) {
                    ++this.timeInCart;
                    if(this.timeInCart >= 40) {
                        entity.attackEntityFrom(DamageSource.CACTUS, 1.0F);
                        this.timeInCart = 0;
                    }
                }
            } else {
                if(this.timeInCart > 0)
                    this.timeInCart = 0;
            }
        }
    }
}