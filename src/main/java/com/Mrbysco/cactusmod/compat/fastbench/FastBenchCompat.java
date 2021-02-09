package com.mrbysco.cactusmod.compat.fastbench;

import com.mrbysco.cactusmod.Reference;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FastBenchCompat {
	/**
	 * Separate file to avoid classloading when it's not installed
	 */
	public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Reference.MOD_ID);

	public static final RegistryObject<ContainerType<WorkbenchContainer>> CACTUS_WORKBENCH_CONTAINER = CONTAINERS.register("cactus_workbench", () -> IForgeContainerType.create((windowId, inv, data) -> new com.mrbysco.cactusmod.blocks.container.CactusFastBenchContainer(windowId, inv)));
}
