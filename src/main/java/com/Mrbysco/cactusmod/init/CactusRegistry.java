package com.mrbysco.cactusmod.init;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.blockentities.CactusChestBlockEntity;
import com.mrbysco.cactusmod.blockentities.CactusHopperBlockEntity;
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
import com.mrbysco.cactusmod.blocks.redstone.CactusDispenserBlock;
import com.mrbysco.cactusmod.blocks.redstone.CactusDoorBlock;
import com.mrbysco.cactusmod.blocks.redstone.CactusHopperBlock;
import com.mrbysco.cactusmod.blocks.redstone.CactusTNTBlock;
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
import com.mrbysco.cactusmod.items.block.CactusChestBlockItem;
import com.mrbysco.cactusmod.items.tools.CactusAxeItem;
import com.mrbysco.cactusmod.items.tools.CactusHoeItem;
import com.mrbysco.cactusmod.items.tools.CactusPickaxeItem;
import com.mrbysco.cactusmod.items.tools.CactusShieldItem;
import com.mrbysco.cactusmod.items.tools.CactusShovelItem;
import com.mrbysco.cactusmod.items.tools.CactusSwordItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.items.VanillaHopperItemHandler;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CactusRegistry {
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Reference.MOD_ID);
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Reference.MOD_ID);
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, Reference.MOD_ID);
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Reference.MOD_ID);
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, Reference.MOD_ID);
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, Reference.MOD_ID);
	public static final DeferredRegister<MenuType<?>> MENU = DeferredRegister.create(Registries.MENU, Reference.MOD_ID);

	public static final Supplier<Feature<NoneFeatureConfiguration>> CACTUS_PLANT_FEATURE = FEATURES.register("cactus_plant", () -> new CactusPlantFeature(NoneFeatureConfiguration.CODEC));

	public static final Supplier<MenuType<CraftingMenu>> CACTUS_WORKBENCH_CONTAINER = MENU.register("cactus_workbench", () -> IMenuTypeExtension.create((windowId, inv, data) -> new CactusWorkbenchContainer(windowId, inv)));

	public static final DeferredBlock<PricklyIronBlock> PRICKLY_IRON = BLOCKS.register("prickly_iron", () -> new PricklyIronBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(2.0F, 7.0F).sound(SoundType.METAL)));
	public static final DeferredBlock<Block> CACTUS_BRICK_BLOCK = BLOCKS.register("cactus_brick_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.4F).sound(SoundType.WOOL)));
	public static final DeferredBlock<StairBlock> CACTUS_BRICK_STAIR = BLOCKS.register("cactus_brick_stair", () -> new StairBlock(() -> CACTUS_BRICK_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.4F).sound(SoundType.WOOL)));
	public static final DeferredBlock<SlabBlock> CACTUS_BRICK_SLAB = BLOCKS.register("cactus_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.4F).sound(SoundType.WOOL)));
	public static final DeferredBlock<CarvedCactusBlock> CARVED_CACTUS = BLOCKS.register("carved_cactus", () -> new CarvedCactusBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.4F).sound(SoundType.WOOL)));
	public static final DeferredBlock<CarvedCactusBlock> JACKO_CACTUS = BLOCKS.register("jacko_cactus", () -> new CarvedCactusBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.4F).sound(SoundType.WOOL).lightLevel((state) -> {
		return 15;
	})));
	public static final DeferredBlock<PunjiCactusBlock> CACTUS_CARPET = BLOCKS.register("cactus_carpet", () -> new PunjiCactusBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.25F).sound(SoundType.WOOL)));
	public static final DeferredBlock<CactusCakeBlock> CACTUS_CAKE = BLOCKS.register("cactus_cake", () -> new CactusCakeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.5F).sound(SoundType.WOOL)));
	public static final DeferredBlock<CactusDispenserBlock> CACTUS_DISPENSER = BLOCKS.register("cactus_dispenser", () -> new CactusDispenserBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.4F).sound(SoundType.WOOL)));
	public static final DeferredBlock<BlockCactusWorkbench> CACTUS_CRAFTING_TABLE = BLOCKS.register("cactus_crafting_table", () -> new BlockCactusWorkbench(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.4F).sound(SoundType.WOOL)));
	public static final DeferredBlock<CactusChestBlock> CACTUS_CHEST = BLOCKS.register("cactus_chest", () -> new CactusChestBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.4F).sound(SoundType.WOOL)));
	public static final DeferredBlock<CactusHopperBlock> CACTUS_HOPPER = BLOCKS.register("cactus_hopper", () -> new CactusHopperBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(1.0F, 0.4F).sound(SoundType.WOOL)));
	public static final DeferredBlock<CactusTNTBlock> CACTUS_TNT = BLOCKS.register("cactus_tnt", () -> new CactusTNTBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).instabreak().sound(SoundType.WOOL)));
	public static final DeferredBlock<CactusDoorBlock> CACTUS_DOOR = BLOCKS.register("cactus_door", () -> new CactusDoorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.4F).sound(SoundType.WOOL).noOcclusion()));
	public static final DeferredBlock<CactusSlimeBlock> CACTUS_SLIME_BLOCK = BLOCKS.register("cactus_slime_block", () -> new CactusSlimeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.25F).sound(SoundType.SLIME_BLOCK).noOcclusion()));
	public static final DeferredBlock<CactusPlantBlock> CACTUS_PLANT = BLOCKS.register("cactus_plant", () -> new CactusPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.4F).sound(SoundType.WOOL).noOcclusion()));
	public static final DeferredBlock<CactusFlowerBlock> CACTUS_FLOWER = BLOCKS.register("cactus_flower", () -> new CactusFlowerBlock(() -> CACTUS_PLANT.get(), BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.4F).sound(SoundType.WOOD).randomTicks().noOcclusion()));

	public static final Supplier<BlockEntityType<CactusChestBlockEntity>> CACTUS_CHEST_BLOCK_ENTITY = BLOCK_ENTITIES.register("cactus_chest", () -> BlockEntityType.Builder.of(CactusChestBlockEntity::new, CACTUS_CHEST.get()).build(null));
	public static final Supplier<BlockEntityType<CactusHopperBlockEntity>> CACTUS_HOPPER_BLOCK_ENTITY = BLOCK_ENTITIES.register("cactus_hopper", () -> BlockEntityType.Builder.of(CactusHopperBlockEntity::new, CACTUS_HOPPER.get()).build(null));

	public static final DeferredItem<BlockItem> PRICKLY_IRON_ITEM = ITEMS.registerSimpleBlockItem(PRICKLY_IRON);
	public static final DeferredItem<BlockItem> CACTUS_BRICK_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(CACTUS_BRICK_BLOCK);
	public static final DeferredItem<BlockItem> CACTUS_BRICK_STAIR_ITEM = ITEMS.registerSimpleBlockItem(CACTUS_BRICK_STAIR);
	public static final DeferredItem<BlockItem> CACTUS_BRICK_SLAB_ITEM = ITEMS.registerSimpleBlockItem(CACTUS_BRICK_SLAB);
	public static final DeferredItem<BlockItem> CARVED_CACTUS_ITEM = ITEMS.registerSimpleBlockItem(CARVED_CACTUS);
	public static final DeferredItem<BlockItem> JACKO_CACTUS_ITEM = ITEMS.registerSimpleBlockItem(JACKO_CACTUS);
	public static final DeferredItem<BlockItem> CACTUS_CARPET_ITEM = ITEMS.registerSimpleBlockItem(CACTUS_CARPET);
	public static final DeferredItem<BlockItem> CACTUS_CAKE_ITEM = ITEMS.registerSimpleBlockItem(CACTUS_CAKE);
	public static final DeferredItem<BlockItem> CACTUS_DISPENSER_ITEM = ITEMS.registerSimpleBlockItem(CACTUS_DISPENSER);
	public static final DeferredItem<BlockItem> CACTUS_CRAFTING_TABLE_ITEM = ITEMS.registerSimpleBlockItem(CACTUS_CRAFTING_TABLE);
	public static final DeferredItem<CactusChestBlockItem> CACTUS_CHEST_ITEM = ITEMS.register("cactus_chest", () -> new CactusChestBlockItem(CACTUS_CHEST.get(), new Item.Properties()));
	public static final DeferredItem<BlockItem> CACTUS_HOPPER_ITEM = ITEMS.registerSimpleBlockItem(CACTUS_HOPPER);
	public static final DeferredItem<BlockItem> CACTUS_TNT_ITEM = ITEMS.registerSimpleBlockItem(CACTUS_TNT);
	public static final DeferredItem<BlockItem> CACTUS_DOOR_ITEM = ITEMS.registerSimpleBlockItem(CACTUS_DOOR);
	public static final DeferredItem<BlockItem> CACTUS_SLIME_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(CACTUS_SLIME_BLOCK);
	public static final DeferredItem<BlockItem> CACTUS_FLOWER_ITEM = ITEMS.registerSimpleBlockItem(CACTUS_FLOWER);
	public static final DeferredItem<BlockItem> CACTUS_PLANT_ITEM = ITEMS.registerSimpleBlockItem(CACTUS_PLANT);


	public static final DeferredItem<Item> CACTUS_STICK = ITEMS.register("cactus_stick", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> CACTUS_BRICK_ITEM = ITEMS.register("cactus_brick", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> CACTUS_BONE = ITEMS.register("cactus_bone", () -> new Item(new Item.Properties()));
	public static final DeferredItem<CactusBonemealItem> CACTUS_BONEMEAL = ITEMS.register("cactus_bonemeal", () -> new CactusBonemealItem(new Item.Properties()));
	public static final DeferredItem<Item> CACTUS_SLIMEBALL = ITEMS.register("cactus_slimeball", () -> new Item(new Item.Properties()));
	public static final DeferredItem<CactusSwordItem> CACTUS_SWORD = ITEMS.register("cactus_sword", () -> new CactusSwordItem(CactusTiers.CACTUS, 3, -2.4F, new Item.Properties()));
	public static final DeferredItem<CactusShovelItem> CACTUS_SHOVEL = ITEMS.register("cactus_shovel", () -> new CactusShovelItem(CactusTiers.CACTUS, 1.5F, -3.0F, new Item.Properties()));
	public static final DeferredItem<CactusPickaxeItem> CACTUS_PICKAXE = ITEMS.register("cactus_pickaxe", () -> new CactusPickaxeItem(CactusTiers.CACTUS, 1, -2.8F, new Item.Properties()));
	public static final DeferredItem<CactusAxeItem> CACTUS_AXE = ITEMS.register("cactus_axe", () -> new CactusAxeItem(CactusTiers.CACTUS, 6.0F, -3.2F, new Item.Properties()));
	public static final DeferredItem<CactusHoeItem> CACTUS_HOE = ITEMS.register("cactus_hoe", () -> new CactusHoeItem(CactusTiers.CACTUS, 0, -3.0F, new Item.Properties()));
	public static final DeferredItem<CactusShieldItem> CACTUS_SHIELD = ITEMS.register("cactus_shield", () -> new CactusShieldItem(new Item.Properties().durability(97)));

	public static final DeferredItem<CactusArmorItem> CACTUS_HELMET = ITEMS.register("cactus_helmet", () -> new CactusArmorItem(CactusArmor.CACTUS, ArmorItem.Type.HELMET, new Item.Properties()));
	public static final DeferredItem<CactusArmorItem> CACTUS_CHESTPLATE = ITEMS.register("cactus_chestplate", () -> new CactusArmorItem(CactusArmor.CACTUS, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	public static final DeferredItem<CactusArmorItem> CACTUS_LEGGINGS = ITEMS.register("cactus_leggings", () -> new CactusArmorItem(CactusArmor.CACTUS, ArmorItem.Type.LEGGINGS, new Item.Properties()));
	public static final DeferredItem<CactusArmorItem> CACTUS_BOOTS = ITEMS.register("cactus_boots", () -> new CactusArmorItem(CactusArmor.CACTUS, ArmorItem.Type.BOOTS, new Item.Properties()));

	public static final DeferredItem<CactusBowItem> CACTUS_BOW = ITEMS.register("cactus_bow", () -> new CactusBowItem(new Item.Properties().durability(384)));
	public static final DeferredItem<CactusCartItem> CACTUS_CART = ITEMS.register("cactus_cart", () -> new CactusCartItem(new Item.Properties()));
	public static final DeferredItem<CactusBoatItem> CACTUS_BOAT = ITEMS.register("cactus_boat", () -> new CactusBoatItem(new Item.Properties()));
	public static final DeferredItem<CactusJuiceItem> CACTUS_JUICE = ITEMS.register("cactus_juice", () -> new CactusJuiceItem(new Item.Properties().food(CactusFoods.JUICE)));
	public static final DeferredItem<CactusFruitItem> CACTUS_FRUIT = ITEMS.register("cactus_fruit", () -> new CactusFruitItem(new Item.Properties().food(CactusFoods.FRUIT)));

	public static final DeferredItem<DeferredSpawnEggItem> CACTUS_GOLEM_SPAWN_EGG = ITEMS.register("cactus_golem_spawn_egg", () -> new DeferredSpawnEggItem(CactusRegistry.CACTUS_GOLEM, 0xFF649832, 0xFF39581a, new Item.Properties()));
	public static final DeferredItem<DeferredSpawnEggItem> CACTUS_COW_SPAWN_EGG = ITEMS.register("cactus_cow_spawn_egg", () -> new DeferredSpawnEggItem(CactusRegistry.CACTUS_COW, 0xFF649832, 0xFF39581a, new Item.Properties()));
	public static final DeferredItem<DeferredSpawnEggItem> CACTUS_CREEPER_SPAWN_EGG = ITEMS.register("cactus_creeper_spawn_egg", () -> new DeferredSpawnEggItem(CactusRegistry.CACTUS_CREEPER, 0xFF649832, 0xFF39581a, new Item.Properties()));
	public static final DeferredItem<DeferredSpawnEggItem> CACTUS_SNOW_GOLEM_SPAWN_EGG = ITEMS.register("cactus_snow_golem_spawn_egg", () -> new DeferredSpawnEggItem(CactusRegistry.CACTUS_SNOW_GOLEM, 0xFF649832, 0xFF39581a, new Item.Properties()));
	public static final DeferredItem<DeferredSpawnEggItem> CACTUS_SLIME_SPAWN_EGG = ITEMS.register("cactus_slime_spawn_egg", () -> new DeferredSpawnEggItem(CactusRegistry.CACTUS_SLIME, 0xFF649832, 0xFF39581a, new Item.Properties()));
	public static final DeferredItem<DeferredSpawnEggItem> CACTUS_SHEEP_SPAWN_EGG = ITEMS.register("cactus_sheep_spawn_egg", () -> new DeferredSpawnEggItem(CactusRegistry.CACTUS_SHEEP, 0xFF649832, 0xFF39581a, new Item.Properties()));
	public static final DeferredItem<DeferredSpawnEggItem> CACTUS_PIG_SPAWN_EGG = ITEMS.register("cactus_pig_spawn_egg", () -> new DeferredSpawnEggItem(CactusRegistry.CACTUS_PIG, 0xFF649832, 0xFF39581a, new Item.Properties()));
	public static final DeferredItem<DeferredSpawnEggItem> CACTUS_SPIDER_SPAWN_EGG = ITEMS.register("cactus_spider_spawn_egg", () -> new DeferredSpawnEggItem(CactusRegistry.CACTUS_SPIDER, 0xFF649832, 0xFF39581a, new Item.Properties()));
	public static final DeferredItem<DeferredSpawnEggItem> CACTUS_SKELETON_GOLEM_SPAWN_EGG = ITEMS.register("cactus_skeleton_spawn_egg", () -> new DeferredSpawnEggItem(CactusRegistry.CACTUS_SKELETON, 0xFF649832, 0xFF39581a, new Item.Properties()));

	public static final DeferredHolder<SoundEvent, SoundEvent> HAT_MUSIC = SOUND_EVENTS.register("hat.music", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Reference.MOD_ID, "hat.music")));

	public static final Supplier<EntityType<CactusGolem>> CACTUS_GOLEM = ENTITIES.register("cactus_golem", () -> register("cactus_golem", EntityType.Builder.<CactusGolem>of(CactusGolem::new, MobCategory.MISC).sized(1.4F, 2.7F).clientTrackingRange(10)));
	public static final Supplier<EntityType<CactusCowEntity>> CACTUS_COW = ENTITIES.register("cactus_cow", () -> register("cactus_cow", EntityType.Builder.<CactusCowEntity>of(CactusCowEntity::new, MobCategory.CREATURE).sized(0.9F, 1.4F).clientTrackingRange(10)));
	public static final Supplier<EntityType<CactiCartEntity>> CACTUS_CART_ENTITY = ENTITIES.register("cactus_cart", () -> register("cactus_cart", EntityType.Builder.<CactiCartEntity>of(CactiCartEntity::new, MobCategory.MISC)
			.sized(0.98F, 0.7F).clientTrackingRange(8).setCustomClientFactory(CactiCartEntity::new)));
	public static final Supplier<EntityType<CactusTNTEntity>> CACTUS_TNT_ENTITY = ENTITIES.register("cactus_tnt", () -> register("cactus_tnt", EntityType.Builder.<CactusTNTEntity>of(CactusTNTEntity::new, MobCategory.MISC)
			.sized(0.98F, 0.7F).clientTrackingRange(8).setCustomClientFactory(CactusTNTEntity::new)));
	public static final Supplier<EntityType<SpikeEntity>> CACTUS_SPIKE = ENTITIES.register("cactus_spike", () -> register("cactus_spike", EntityType.Builder.<SpikeEntity>of(SpikeEntity::new, MobCategory.MISC)
			.sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).setCustomClientFactory(SpikeEntity::new)));
	public static final Supplier<EntityType<CactusCreeperEntity>> CACTUS_CREEPER = ENTITIES.register("cactus_creeper", () -> register("cactus_creeper", EntityType.Builder.<CactusCreeperEntity>of(CactusCreeperEntity::new, MobCategory.MONSTER).sized(0.6F, 1.7F).clientTrackingRange(8)));
	public static final Supplier<EntityType<CactusSnowGolemEntity>> CACTUS_SNOW_GOLEM = ENTITIES.register("cactus_snow_golem", () -> register("cactus_snow_golem", EntityType.Builder.<CactusSnowGolemEntity>of(CactusSnowGolemEntity::new, MobCategory.MISC).sized(0.7F, 1.9F).clientTrackingRange(8)));
	public static final Supplier<EntityType<CactusSlimeEntity>> CACTUS_SLIME = ENTITIES.register("cactus_slime", () -> register("cactus_slime", EntityType.Builder.<CactusSlimeEntity>of(CactusSlimeEntity::new, MobCategory.MONSTER).sized(2.04F, 2.04F).clientTrackingRange(10)));
	public static final Supplier<EntityType<CactusSheepEntity>> CACTUS_SHEEP = ENTITIES.register("cactus_sheep", () -> register("cactus_sheep", EntityType.Builder.<CactusSheepEntity>of(CactusSheepEntity::new, MobCategory.CREATURE).sized(0.9F, 1.3F).clientTrackingRange(10)));
	public static final Supplier<EntityType<CactusPigEntity>> CACTUS_PIG = ENTITIES.register("cactus_pig", () -> register("cactus_pig", EntityType.Builder.<CactusPigEntity>of(CactusPigEntity::new, MobCategory.CREATURE).sized(0.9F, 0.9F).clientTrackingRange(10)));
	public static final Supplier<EntityType<CactusSpiderEntity>> CACTUS_SPIDER = ENTITIES.register("cactus_spider", () -> register("cactus_spider", EntityType.Builder.<CactusSpiderEntity>of(CactusSpiderEntity::new, MobCategory.MONSTER).sized(1.4F, 0.9F).clientTrackingRange(8)));
	public static final Supplier<EntityType<CactusSkeletonEntity>> CACTUS_SKELETON = ENTITIES.register("cactus_skeleton", () -> register("cactus_skeleton", EntityType.Builder.<CactusSkeletonEntity>of(CactusSkeletonEntity::new, MobCategory.MONSTER).sized(0.6F, 1.99F).clientTrackingRange(8)));
	public static final Supplier<EntityType<CactusBoatEntity>> CACTUS_BOAT_ENTITY = ENTITIES.register("cactus_boat", () -> register("cactus_boat", EntityType.Builder.<CactusBoatEntity>of(CactusBoatEntity::new, MobCategory.MISC)
			.sized(1.375F, 0.5625F).clientTrackingRange(10).setCustomClientFactory(CactusBoatEntity::new)));
	public static final Supplier<EntityType<CactoniEntity>> CACTONI = ENTITIES.register("cactoni", () -> register("cactoni", EntityType.Builder.<CactoniEntity>of(CactoniEntity::new, MobCategory.MISC).sized(0.7F, 2.4F).clientTrackingRange(8)));

	public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
		return builder.build(id);
	}

	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, CACTUS_CHEST_BLOCK_ENTITY.get(), (cactusChestBlockEntity, side) ->
				new InvWrapper(cactusChestBlockEntity));

		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, CACTUS_HOPPER_BLOCK_ENTITY.get(), (hopperBlockEntity, side) ->
				new VanillaHopperItemHandler(hopperBlockEntity));
	}
}
