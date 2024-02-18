package com.mrbysco.cactusmod.entities;

import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class CactusBoatEntity extends Boat implements ICactusMob {
	private int timeInBoat;
	private int timeInBoat2;

	public CactusBoatEntity(EntityType<? extends Boat> type, Level level) {
		super(type, level);
	}

	public CactusBoatEntity(Level level, double x, double y, double z) {
		this(CactusRegistry.CACTUS_BOAT_ENTITY.get(), level);
		this.setPos(x, y, z);
		this.setDeltaMovement(Vec3.ZERO);
		this.xo = x;
		this.yo = y;
		this.zo = z;
	}

	@Override
	public Item getDropItem() {
		return CactusRegistry.CACTUS_BOAT.get();
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.level().isClientSide) {
			if (!this.getPassengers().isEmpty() && this.getPassengers().get(0) != null) {
				++this.timeInBoat;
			} else {
				if (this.timeInBoat > 0)
					this.timeInBoat = 0;
			}

			if (this.getPassengers().size() == 2 && this.getPassengers().get(1) != null) {
				++this.timeInBoat2;
			} else {
				if (this.timeInBoat2 > 0)
					this.timeInBoat2 = 0;
			}

			if (!this.getPassengers().isEmpty() && this.getPassengers().get(0) instanceof LivingEntity entity && this.timeInBoat >= 120) {
				if (!(entity instanceof ICactusMob))
					entity.hurt(damageSources().generic(), 1.0F);

				this.timeInBoat = 0;
			}


			if (this.getPassengers().size() == 2 && this.getPassengers().get(1) instanceof LivingEntity entity2 && this.timeInBoat2 >= 120) {
				if (!(entity2 instanceof ICactusMob))
					entity2.hurt(damageSources().cactus(), 1.0F);

				this.timeInBoat2 = 0;
			}
		}
	}

	@Override
	protected void positionRider(Entity passenger, MoveFunction moveFunction) {
		super.positionRider(passenger, moveFunction);
	}
}