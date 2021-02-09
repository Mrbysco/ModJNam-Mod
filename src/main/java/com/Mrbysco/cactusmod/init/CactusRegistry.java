package com.mrbysco.cactusmod.init;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.blocks.BlockCactusWorkbench;
import com.mrbysco.cactusmod.blocks.CactusCakeBlock;
import com.mrbysco.cactusmod.blocks.CactusSlimeBlock;
import com.mrbysco.cactusmod.blocks.CarvedCactusBlock;
import com.mrbysco.cactusmod.blocks.PricklyIronBlock;
import com.mrbysco.cactusmod.blocks.PunjiCactusBlock;
import com.mrbysco.cactusmod.blocks.container.CactusWorkbenchContainer;
import com.mrbysco.cactusmod.blocks.plant.CactusFlowerBlock;
import com.mrbysco.cactusmod.blocks.plant.CactusPlantBlock;
import com.mrbysco.cactusmod.blocks.redstone.CactusChestBlock;
import com.mrbysco.cactusmod.blocks.redstone.CactusDoorBlock;
import com.mrbysco.cactusmod.blocks.redstone.CactusHopperBlock;
import com.mrbysco.cactusmod.blocks.redstone.CactusTNTBlock;
import com.mrbysco.cactusmod.blocks.redstone.DispenserCactusBlock;
import com.mrbysco.cactusmod.entities.CactiCartEntity;
import com.mrbysco.cactusmod.entities.CactoniEntity;
import com.mrbysco.cactusmod.entities.CactusBoatEntity;
import com.mrbysco.cactusmod.entities.CactusCowEntity;
import com.mrbysco.cactusmod.entities.CactusGolem;
import com.mrbysco.cactusmod.entities.CactusPigEntity;
import com.mrbysco.cactusmod.entities.CactusSheepEntity;
import com.mrbysco.cactusmod.entities.CactusSlimeEntity;
import com.mrbysco.cactusmod.entities.CactusSnowGolemEntity;
import com.mrbysco.cactusmod.entities.CactusTNTEntity;
import com.mrbysco.cactusmod.entities.SpikeEntity;
import com.mrbysco.cactusmod.entities.hostile.CactusCreeperEntity;
import com.mrbysco.cactusmod.entities.hostile.CactusSkeletonEntity;
import com.mrbysco.cactusmod.entities.hostile.CactusSpiderEntity;
import com.mrbysco.cactusmod.feature.CactusPlantFeature;
import com.mrbysco.cactusmod.items.CactusArmorItem;
import com.mrbysco.cactusmod.items.CactusBoatItem;
import com.mrbysco.cactusmod.items.CactusBonemealItem;
import com.mrbysco.cactusmod.items.CactusBowItem;
import com.mrbysco.cactusmod.items.CactusCartItem;
import com.mrbysco.cactusmod.items.CactusFruitItem;
import com.mrbysco.cactusmod.items.CactusJuiceItem;
import com.mrbysco.cactusmod.items.CustomSpawnEggItem;
import com.mrbysco.cactusmod.items.tools.CactusAxeItem;
import com.mrbysco.cactusmod.items.tools.CactusHoeItem;
import com.mrbysco.cactusmod.items.tools.CactusPickaxeItem;
import com.mrbysco.cactusmod.items.tools.CactusShieldItem;
import com.mrbysco.cactusmod.items.tools.CactusShovelItem;
import com.mrbysco.cactusmod.items.tools.CactusSwordItem;
import com.mrbysco.cactusmod.tileentities.CactusChestTile;
import com.mrbysco.cactusmod.tileentities.CactusHopperTile;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CactusRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Reference.MOD_ID);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Reference.MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Reference.MOD_ID);
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Reference.MOD_ID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Reference.MOD_ID);

    public static final RegistryObject<Feature<NoFeatureConfig>> CACTUS_PLANT_FEATURE = FEATURES.register("cactus_plant", () -> new CactusPlantFeature(NoFeatureConfig.field_236558_a_));

    public static final RegistryObject<ContainerType<WorkbenchContainer>> CACTUS_WORKBENCH_CONTAINER = CONTAINERS.register("cactus_workbench", () -> IForgeContainerType.create((windowId, inv, data) -> new CactusWorkbenchContainer(windowId, inv)));

    public static final RegistryObject<Block> PRICKLY_IRON = BLOCKS.register("prickly_iron", () -> new PricklyIronBlock(AbstractBlock.Properties.create(Material.IRON).hardnessAndResistance(2.0F, 7.0F).sound(SoundType.METAL)));
    public static final RegistryObject<Block> CACTUS_BRICK_BLOCK = BLOCKS.register("cactus_brick_block", () -> new Block(AbstractBlock.Properties.create(Material.CACTUS).hardnessAndResistance(0.4F).sound(SoundType.CLOTH)));
    public static final RegistryObject<Block> CACTUS_BRICK_STAIR = BLOCKS.register("cactus_brick_stair", () -> new StairsBlock(() -> CACTUS_BRICK_BLOCK.get().getDefaultState(), AbstractBlock.Properties.create(Material.CACTUS).hardnessAndResistance(0.4F).sound(SoundType.CLOTH)));
    public static final RegistryObject<Block> CACTUS_BRICK_SLAB = BLOCKS.register("cactus_brick_slab", () -> new SlabBlock(AbstractBlock.Properties.create(Material.CACTUS).hardnessAndResistance(0.4F).sound(SoundType.CLOTH)));
    public static final RegistryObject<Block> CARVED_CACTUS = BLOCKS.register("carved_cactus", () -> new CarvedCactusBlock(AbstractBlock.Properties.create(Material.CACTUS).hardnessAndResistance(0.4F).sound(SoundType.CLOTH)));
    public static final RegistryObject<Block> JACKO_CACTUS = BLOCKS.register("jacko_cactus", () -> new CarvedCactusBlock(AbstractBlock.Properties.create(Material.CACTUS).hardnessAndResistance(0.4F).sound(SoundType.CLOTH).setLightLevel((state) -> {
        return 15;
    })));
    public static final RegistryObject<Block> CACTUS_CARPET = BLOCKS.register("cactus_carpet", () -> new PunjiCactusBlock(AbstractBlock.Properties.create(Material.CACTUS).hardnessAndResistance(0.25F).sound(SoundType.CLOTH)));
    public static final RegistryObject<Block> CACTUS_CAKE = BLOCKS.register("cactus_cake", () -> new CactusCakeBlock(AbstractBlock.Properties.create(Material.CACTUS).hardnessAndResistance(0.5F).sound(SoundType.CLOTH)));
    public static final RegistryObject<Block> CACTUS_DISPENSER = BLOCKS.register("cactus_dispenser", () -> new DispenserCactusBlock(AbstractBlock.Properties.create(Material.CACTUS).hardnessAndResistance(0.4F).sound(SoundType.CLOTH)));
    public static final RegistryObject<Block> CACTUS_CRAFTING_TABLE = BLOCKS.register("cactus_crafting_table", () -> new BlockCactusWorkbench(AbstractBlock.Properties.create(Material.CACTUS).hardnessAndResistance(0.4F).sound(SoundType.CLOTH)));
    public static final RegistryObject<Block> CACTUS_CHEST = BLOCKS.register("cactus_chest", () -> new CactusChestBlock(AbstractBlock.Properties.create(Material.CACTUS).hardnessAndResistance(0.4F).sound(SoundType.CLOTH)));
    public static final RegistryObject<Block> CACTUS_HOPPER = BLOCKS.register("cactus_hopper", () -> new CactusHopperBlock(AbstractBlock.Properties.create(Material.CACTUS).hardnessAndResistance(1.0F, 0.4F).sound(SoundType.CLOTH)));
    public static final RegistryObject<Block> CACTUS_TNT = BLOCKS.register("cactus_tnt", () -> new CactusTNTBlock(AbstractBlock.Properties.create(Material.CACTUS).hardnessAndResistance(0.0F).sound(SoundType.CLOTH)));
    public static final RegistryObject<Block> CACTUS_DOOR = BLOCKS.register("cactus_door", () -> new CactusDoorBlock(AbstractBlock.Properties.create(Material.CACTUS).hardnessAndResistance(0.4F).sound(SoundType.CLOTH).notSolid()));
    public static final RegistryObject<Block> CACTUS_SLIME_BLOCK = BLOCKS.register("cactus_slime_block", () -> new CactusSlimeBlock(AbstractBlock.Properties.create(Material.CACTUS).hardnessAndResistance(0.25F).sound(SoundType.SLIME).notSolid()));
    public static final RegistryObject<Block> CACTUS_PLANT = BLOCKS.register("cactus_plant", () -> new CactusPlantBlock(AbstractBlock.Properties.create(Material.CACTUS).hardnessAndResistance(0.4F).sound(SoundType.CLOTH).notSolid()));
    public static final RegistryObject<Block> CACTUS_FLOWER = BLOCKS.register("cactus_flower", () -> new CactusFlowerBlock(() -> (CactusPlantBlock)CACTUS_PLANT.get(), AbstractBlock.Properties.create(Material.CACTUS).hardnessAndResistance(0.4F).sound(SoundType.WOOD).tickRandomly().notSolid()));

    public static final RegistryObject<TileEntityType<CactusChestTile>> CACTUS_CHEST_TILE = TILE_ENTITIES.register("cactus_chest", () -> TileEntityType.Builder.create(() -> new CactusChestTile(), CACTUS_CHEST.get()).build(null));
    public static final RegistryObject<TileEntityType<CactusHopperTile>> CACTUS_HOPPER_TILE = TILE_ENTITIES.register("cactus_hopper", () -> TileEntityType.Builder.create(() -> new CactusHopperTile(), CACTUS_HOPPER.get()).build(null));

    public static final RegistryObject<Item> PRICKLY_IRON_ITEM = ITEMS.register("prickly_iron", () -> new BlockItem(PRICKLY_IRON.get(), itemBuilder()));
    public static final RegistryObject<Item> CACTUS_BRICK_BLOCK_ITEM = ITEMS.register("cactus_brick_block", () -> new BlockItem(CACTUS_BRICK_BLOCK.get(), itemBuilder()));
    public static final RegistryObject<Item> CACTUS_BRICK_STAIR_ITEM = ITEMS.register("cactus_brick_stair", () -> new BlockItem(CACTUS_BRICK_STAIR.get(), itemBuilder()));
    public static final RegistryObject<Item> CACTUS_BRICK_SLAB_ITEM = ITEMS.register("cactus_brick_slab", () -> new BlockItem(CACTUS_BRICK_SLAB.get(), itemBuilder()));
    public static final RegistryObject<Item> CARVED_CACTUS_ITEM = ITEMS.register("carved_cactus", () -> new BlockItem(CARVED_CACTUS.get(), itemBuilder()));
    public static final RegistryObject<Item> JACKO_CACTUS_ITEM = ITEMS.register("jacko_cactus", () -> new BlockItem(JACKO_CACTUS.get(), itemBuilder()));
    public static final RegistryObject<Item> CACTUS_CARPET_ITEM = ITEMS.register("cactus_carpet", () -> new BlockItem(CACTUS_CARPET.get(), itemBuilder()));
    public static final RegistryObject<Item> CACTUS_CAKE_ITEM = ITEMS.register("cactus_cake", () -> new BlockItem(CACTUS_CAKE.get(), itemBuilder()));
    public static final RegistryObject<Item> CACTUS_DISPENSER_ITEM = ITEMS.register("cactus_dispenser", () -> new BlockItem(CACTUS_DISPENSER.get(), itemBuilder()));
    public static final RegistryObject<Item> CACTUS_CRAFTING_TABLE_ITEM = ITEMS.register("cactus_crafting_table", () -> new BlockItem(CACTUS_CRAFTING_TABLE.get(), itemBuilder()));
    public static final RegistryObject<Item> CACTUS_CHEST_ITEM = ITEMS.register("cactus_chest", () -> new BlockItem(CACTUS_CHEST.get(), itemBuilder().setISTER(() -> CactusISTERProvider.chest())));
    public static final RegistryObject<Item> CACTUS_HOPPER_ITEM = ITEMS.register("cactus_hopper", () -> new BlockItem(CACTUS_HOPPER.get(), itemBuilder()));
    public static final RegistryObject<Item> CACTUS_TNT_ITEM = ITEMS.register("cactus_tnt", () -> new BlockItem(CACTUS_TNT.get(), itemBuilder()));
    public static final RegistryObject<Item> CACTUS_DOOR_ITEM = ITEMS.register("cactus_door", () -> new BlockItem(CACTUS_DOOR.get(), itemBuilder()));
    public static final RegistryObject<Item> CACTUS_SLIME_BLOCK_ITEM = ITEMS.register("cactus_slime_block", () -> new BlockItem(CACTUS_SLIME_BLOCK.get(), itemBuilder()));
    public static final RegistryObject<Item> CACTUS_FLOWER_ITEM = ITEMS.register("cactus_flower", () -> new BlockItem(CACTUS_FLOWER.get(), itemBuilder()));
    public static final RegistryObject<Item> CACTUS_PLANT_ITEM = ITEMS.register("cactus_plant", () -> new BlockItem(CACTUS_PLANT.get(), itemBuilder()));


    public static final RegistryObject<Item> CACTUS_STICK = ITEMS.register("cactus_stick", () -> new Item(itemBuilder()));
    public static final RegistryObject<Item> CACTUS_BRICK_ITEM = ITEMS.register("cactus_brick", () -> new Item(itemBuilder()));
    public static final RegistryObject<Item> CACTUS_BONE = ITEMS.register("cactus_bone", () -> new Item(itemBuilder()));
    public static final RegistryObject<Item> CACTUS_BONEMEAL = ITEMS.register("cactus_bonemeal", () -> new CactusBonemealItem(itemBuilder()));
    public static final RegistryObject<Item> CACTUS_SLIMEBALL = ITEMS.register("cactus_slimeball", () -> new Item(itemBuilder()));
    public static final RegistryObject<Item> CACTUS_SWORD = ITEMS.register("cactus_sword", () -> new CactusSwordItem(CactusTool.CACTUS, 3, -2.4F, itemBuilder()));
    public static final RegistryObject<Item> CACTUS_SHOVEL = ITEMS.register("cactus_shovel", () -> new CactusShovelItem(CactusTool.CACTUS, 1.5F, -3.0F, itemBuilder()));
    public static final RegistryObject<Item> CACTUS_PICKAXE = ITEMS.register("cactus_pickaxe", () -> new CactusPickaxeItem(CactusTool.CACTUS, 1, -2.8F, itemBuilder()));
    public static final RegistryObject<Item> CACTUS_AXE = ITEMS.register("cactus_axe", () -> new CactusAxeItem(CactusTool.CACTUS, 6.0F, -3.2F, itemBuilder()));
    public static final RegistryObject<Item> CACTUS_HOE = ITEMS.register("cactus_hoe", () -> new CactusHoeItem(CactusTool.CACTUS, 0, -3.0F, itemBuilder()));
    public static final RegistryObject<Item> CACTUS_SHIELD = ITEMS.register("cactus_shield", () -> new CactusShieldItem(itemBuilder().maxDamage(97)));

    public static final RegistryObject<Item> CACTUS_HELMET = ITEMS.register("cactus_helmet", () -> new CactusArmorItem(CactusArmor.CACTUS, EquipmentSlotType.HEAD, itemBuilder()));
    public static final RegistryObject<Item> CACTUS_CHESTPLATE = ITEMS.register("cactus_chestplate", () -> new CactusArmorItem(CactusArmor.CACTUS, EquipmentSlotType.CHEST, itemBuilder()));
    public static final RegistryObject<Item> CACTUS_LEGGINGS = ITEMS.register("cactus_leggings", () -> new CactusArmorItem(CactusArmor.CACTUS, EquipmentSlotType.LEGS, itemBuilder()));
    public static final RegistryObject<Item> CACTUS_BOOTS = ITEMS.register("cactus_boots", () -> new CactusArmorItem(CactusArmor.CACTUS, EquipmentSlotType.FEET, itemBuilder()));

    public static final RegistryObject<Item> CACTUS_BOW = ITEMS.register("cactus_bow", () -> new CactusBowItem(itemBuilder().maxDamage(384)));
    public static final RegistryObject<Item> CACTUS_CART = ITEMS.register("cactus_cart", () -> new CactusCartItem(itemBuilder()));
    public static final RegistryObject<Item> CACTUS_BOAT = ITEMS.register("cactus_boat", () -> new CactusBoatItem(itemBuilder()));
    public static final RegistryObject<Item> CACTUS_JUICE = ITEMS.register("cactus_juice", () -> new CactusJuiceItem(itemBuilder().food(CactusFoods.JUICE)));
    public static final RegistryObject<Item> CACTUS_FRUIT = ITEMS.register("cactus_fruit", () -> new CactusFruitItem(itemBuilder().food(CactusFoods.FRUIT)));

    public static final RegistryObject<Item> CACTUS_GOLEM_SPAWN_EGG = ITEMS.register("cactus_golem_spawn_egg", () -> new CustomSpawnEggItem(() -> CactusRegistry.CACTUS_GOLEM.get(), 0xFF649832, 0xFF39581a, itemBuilder()));
    public static final RegistryObject<Item> CACTUS_COW_SPAWN_EGG = ITEMS.register("cactus_cow_spawn_egg", () -> new CustomSpawnEggItem(() -> CactusRegistry.CACTUS_COW.get(), 0xFF649832, 0xFF39581a, itemBuilder()));
    public static final RegistryObject<Item> CACTUS_CREEPER_SPAWN_EGG = ITEMS.register("cactus_creeper_spawn_egg", () -> new CustomSpawnEggItem(() -> CactusRegistry.CACTUS_CREEPER.get(), 0xFF649832, 0xFF39581a, itemBuilder()));
    public static final RegistryObject<Item> CACTUS_SNOW_GOLEM_SPAWN_EGG = ITEMS.register("cactus_snow_golem_spawn_egg", () -> new CustomSpawnEggItem(() -> CactusRegistry.CACTUS_SNOW_GOLEM.get(), 0xFF649832, 0xFF39581a, itemBuilder()));
    public static final RegistryObject<Item> CACTUS_SLIME_SPAWN_EGG = ITEMS.register("cactus_slime_spawn_egg", () -> new CustomSpawnEggItem(() -> CactusRegistry.CACTUS_SLIME.get(), 0xFF649832, 0xFF39581a, itemBuilder()));
    public static final RegistryObject<Item> CACTUS_SHEEP_SPAWN_EGG = ITEMS.register("cactus_sheep_spawn_egg", () -> new CustomSpawnEggItem(() -> CactusRegistry.CACTUS_SHEEP.get(), 0xFF649832, 0xFF39581a, itemBuilder()));
    public static final RegistryObject<Item> CACTUS_PIG_SPAWN_EGG = ITEMS.register("cactus_pig_spawn_egg", () -> new CustomSpawnEggItem(() -> CactusRegistry.CACTUS_PIG.get(), 0xFF649832, 0xFF39581a, itemBuilder()));
    public static final RegistryObject<Item> CACTUS_SPIDER_SPAWN_EGG = ITEMS.register("cactus_spider_spawn_egg", () -> new CustomSpawnEggItem(() -> CactusRegistry.CACTUS_SPIDER.get(), 0xFF649832, 0xFF39581a, itemBuilder()));
    public static final RegistryObject<Item> CACTUS_SKELETON_GOLEM_SPAWN_EGG = ITEMS.register("cactus_skeleton_spawn_egg", () -> new CustomSpawnEggItem(() -> CactusRegistry.CACTUS_SKELETON.get(), 0xFF649832, 0xFF39581a, itemBuilder()));

    private static Item.Properties itemBuilder() {
        return new Item.Properties().group(CactusTabs.CACTUS_TAB);
    }

    public static final RegistryObject<SoundEvent> HAT_MUSIC = SOUND_EVENTS.register("hat.music", () -> new SoundEvent(new ResourceLocation(Reference.MOD_ID, "hat.music")));

    public static final RegistryObject<EntityType<CactusGolem>> CACTUS_GOLEM = ENTITIES.register("cactus_golem", () -> register("cactus_golem", EntityType.Builder.<CactusGolem>create(CactusGolem::new, EntityClassification.MISC).size(1.4F, 2.7F).trackingRange(10)));
    public static final RegistryObject<EntityType<CactusCowEntity>> CACTUS_COW = ENTITIES.register("cactus_cow", () -> register("cactus_cow", EntityType.Builder.<CactusCowEntity>create(CactusCowEntity::new, EntityClassification.CREATURE).size(0.9F, 1.4F).trackingRange(10)));
    public static final RegistryObject<EntityType<CactiCartEntity>> CACTUS_CART_ENTITY = ENTITIES.register("cactus_cart", () -> register("cactus_cart", EntityType.Builder.<CactiCartEntity>create(CactiCartEntity::new, EntityClassification.MISC)
            .size(0.98F, 0.7F).trackingRange(8).setCustomClientFactory(CactiCartEntity::new)));
    public static final RegistryObject<EntityType<CactusTNTEntity>> CACTUS_TNT_ENTITY = ENTITIES.register("cactus_tnt", () -> register("cactus_tnt", EntityType.Builder.<CactusTNTEntity>create(CactusTNTEntity::new, EntityClassification.MISC)
            .size(0.98F, 0.7F).trackingRange(8).setCustomClientFactory(CactusTNTEntity::new)));
    public static final RegistryObject<EntityType<SpikeEntity>> CACTUS_SPIKE = ENTITIES.register("cactus_spike", () -> register("cactus_spike", EntityType.Builder.<SpikeEntity>create(SpikeEntity::new, EntityClassification.MISC)
            .size(0.5F, 0.5F).trackingRange(4).func_233608_b_(20).setCustomClientFactory(SpikeEntity::new)));
    public static final RegistryObject<EntityType<CactusCreeperEntity>> CACTUS_CREEPER = ENTITIES.register("cactus_creeper", () -> register("cactus_creeper", EntityType.Builder.<CactusCreeperEntity>create(CactusCreeperEntity::new, EntityClassification.MONSTER).size(0.6F, 1.7F).trackingRange(8)));
    public static final RegistryObject<EntityType<CactusSnowGolemEntity>> CACTUS_SNOW_GOLEM = ENTITIES.register("cactus_snow_golem", () -> register("cactus_snow_golem", EntityType.Builder.<CactusSnowGolemEntity>create(CactusSnowGolemEntity::new, EntityClassification.MISC).size(0.7F, 1.9F).trackingRange(8)));
    public static final RegistryObject<EntityType<CactusSlimeEntity>> CACTUS_SLIME = ENTITIES.register("cactus_slime", () -> register("cactus_slime", EntityType.Builder.<CactusSlimeEntity>create(CactusSlimeEntity::new, EntityClassification.MONSTER).size(2.04F, 2.04F).trackingRange(10)));
    public static final RegistryObject<EntityType<CactusSheepEntity>> CACTUS_SHEEP = ENTITIES.register("cactus_sheep", () -> register("cactus_sheep", EntityType.Builder.<CactusSheepEntity>create(CactusSheepEntity::new, EntityClassification.CREATURE).size(0.9F, 1.3F).trackingRange(10)));
    public static final RegistryObject<EntityType<CactusPigEntity>> CACTUS_PIG = ENTITIES.register("cactus_pig", () -> register("cactus_pig", EntityType.Builder.<CactusPigEntity>create(CactusPigEntity::new, EntityClassification.CREATURE).size(0.9F, 0.9F).trackingRange(10)));
    public static final RegistryObject<EntityType<CactusSpiderEntity>> CACTUS_SPIDER = ENTITIES.register("cactus_spider", () -> register("cactus_spider", EntityType.Builder.<CactusSpiderEntity>create(CactusSpiderEntity::new, EntityClassification.MONSTER).size(1.4F, 0.9F).trackingRange(8)));
    public static final RegistryObject<EntityType<CactusSkeletonEntity>> CACTUS_SKELETON = ENTITIES.register("cactus_skeleton", () -> register("cactus_skeleton", EntityType.Builder.<CactusSkeletonEntity>create(CactusSkeletonEntity::new, EntityClassification.MONSTER).size(0.6F, 1.99F).trackingRange(8)));
    public static final RegistryObject<EntityType<CactusBoatEntity>> CACTUS_BOAT_ENTITY = ENTITIES.register("cactus_boat", () -> register("cactus_boat", EntityType.Builder.<CactusBoatEntity>create(CactusBoatEntity::new, EntityClassification.MISC)
            .size(1.375F, 0.5625F).trackingRange(10).setCustomClientFactory(CactusBoatEntity::new)));
    public static final RegistryObject<EntityType<CactoniEntity>> CACTONI = ENTITIES.register("cactoni", () -> register("cactoni", EntityType.Builder.<CactoniEntity>create(CactoniEntity::new, EntityClassification.MISC).size(0.7F, 2.4F).trackingRange(8)));

    public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
        return builder.build(id);
    }
}
