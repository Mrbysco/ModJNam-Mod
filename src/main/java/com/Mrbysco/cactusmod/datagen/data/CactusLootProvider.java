package com.mrbysco.cactusmod.datagen.data;

import com.google.common.collect.ImmutableSet;
import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class CactusLootProvider extends LootTableProvider {

	public CactusLootProvider(PackOutput packOutput) {
		super(packOutput, Set.of(), List.of(
				new SubProviderEntry(CactusBlockLoot::new, LootContextParamSets.BLOCK),
				new SubProviderEntry(CactusEntityLoot::new, LootContextParamSets.ENTITY)
		));
	}

	public static class CactusEntityLoot extends EntityLootSubProvider {
		protected CactusEntityLoot() {
			super(FeatureFlags.REGISTRY.allFlags());
		}

		@Override
		public void generate() {
			this.add(CactusRegistry.CACTONI.get(), LootTable.lootTable()
					.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
							.add(LootItem.lootTableItem(Items.CACTUS).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 3.0F)))
									.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))).when(LootItemKilledByPlayerCondition.killedByPlayer())));

			this.add(CactusRegistry.CACTUS_COW.get(), LootTable.lootTable()
					.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
							.add(LootItem.lootTableItem(Items.LEATHER).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
									.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
					.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
							.add(LootItem.lootTableItem(Items.BEEF).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
									.apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
									.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));

			this.add(CactusRegistry.CACTUS_GOLEM.get(), LootTable.lootTable()
					.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
							.add(LootItem.lootTableItem(Blocks.CACTUS).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 5.0F)))))
					.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.IRON_NUGGET)
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 4.0F))))));

			this.add(CactusRegistry.CACTUS_PIG.get(), LootTable.lootTable()
					.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
							.add(LootItem.lootTableItem(Items.CACTUS).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
									.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
					.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
							.add(LootItem.lootTableItem(Items.PORKCHOP).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
									.apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
									.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));

			ResourceLocation sheepLocation = new ResourceLocation(Reference.MOD_ID, "entities/cactus_sheep");
			this.add(CactusRegistry.CACTUS_SHEEP.get(), sheepLocation, LootTable.lootTable()
					.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
							.add(LootItem.lootTableItem(Items.MUTTON).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
									.apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
									.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));

			ResourceLocation sheepLocation1 = new ResourceLocation(Reference.MOD_ID, "entities/cactus_sheep1");
			this.add(CactusRegistry.CACTUS_SHEEP.get(), sheepLocation1, LootTable.lootTable()
					.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
							.add(LootItem.lootTableItem(Items.CACTUS).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
									.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
					.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
							.add(LootTableReference.lootTableReference(sheepLocation))));

			this.add(CactusRegistry.CACTUS_SKELETON.get(), LootTable.lootTable()
					.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
							.add(LootItem.lootTableItem(Items.GREEN_DYE).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
									.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
					.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
							.add(LootItem.lootTableItem(CactusRegistry.CACTUS_BONE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
									.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));

			this.add(CactusRegistry.CACTUS_SLIME.get(), LootTable.lootTable()
					.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
							.add(LootItem.lootTableItem(Items.CACTUS).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
									.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));

			this.add(CactusRegistry.CACTUS_SNOW_GOLEM.get(), LootTable.lootTable()
					.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
							.add(LootItem.lootTableItem(Items.CACTUS).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
									.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));

			this.add(CactusRegistry.CACTUS_CREEPER.get(), LootTable.lootTable()
					.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
							.add(LootItem.lootTableItem(Items.CACTUS).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
									.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));

			this.add(CactusRegistry.CACTUS_SPIDER.get(), LootTable.lootTable()
					.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
							.add(LootItem.lootTableItem(Items.STRING).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
									.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
					.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
							.add(LootItem.lootTableItem(Items.GREEN_DYE).apply(SetItemCountFunction.setCount(UniformGenerator.between(-1.0F, 1.0F)))
									.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
							.when(LootItemKilledByPlayerCondition.killedByPlayer())));
		}

		private static final Set<EntityType<?>> SPECIAL_LOOT_TABLE_TYPES = ImmutableSet.of(
				CactusRegistry.CACTONI.get(), CactusRegistry.CACTUS_GOLEM.get(), CactusRegistry.CACTUS_SNOW_GOLEM.get()
		);

		@Override
		protected boolean canHaveLootTable(EntityType<?> entitytype) {
			return SPECIAL_LOOT_TABLE_TYPES.contains(entitytype) || entitytype.getCategory() != MobCategory.MISC;
		}

		@Override
		protected Stream<EntityType<?>> getKnownEntityTypes() {
			return CactusRegistry.ENTITIES.getEntries().stream().map(Supplier::get);
		}
	}

	public static class CactusBlockLoot extends BlockLootSubProvider {
		protected CactusBlockLoot() {
			super(Set.of(), FeatureFlags.REGISTRY.allFlags());
		}

		@Override
		public void generate() {
			this.dropSelf(CactusRegistry.CACTUS_BRICK_BLOCK.get());
			this.dropSelf(CactusRegistry.CACTUS_BRICK_STAIR.get());
			this.add(CactusRegistry.CACTUS_BRICK_SLAB.get(), this::createSlabItemTable);
			this.add(CactusRegistry.CACTUS_CAKE.get(), noDrop());
			this.dropSelf(CactusRegistry.CACTUS_CARPET.get());
			this.add(CactusRegistry.CACTUS_CHEST.get(), this::createNameableBlockEntityTable);
			this.dropSelf(CactusRegistry.CACTUS_CRAFTING_TABLE.get());
			this.add(CactusRegistry.CACTUS_DISPENSER.get(), this::createNameableBlockEntityTable);
			this.dropSelf(CactusRegistry.CACTUS_CRAFTING_TABLE.get());
			this.add(CactusRegistry.CACTUS_DOOR.get(), this::createDoorTable);
			this.dropSelf(CactusRegistry.CACTUS_FLOWER.get());
			this.add(CactusRegistry.CACTUS_HOPPER.get(), this::createNameableBlockEntityTable);
			this.dropSelf(CactusRegistry.CACTUS_PLANT.get());
			this.add(CactusRegistry.CACTUS_FLOWER.get(), (block) -> {
				return LootTable.lootTable().withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0F)).add(applyExplosionCondition(block, LootItem.lootTableItem(block))
								.when(LootItemEntityPropertyCondition.entityPresent(LootContext.EntityTarget.THIS))));
			});
			this.add(CactusRegistry.CACTUS_PLANT.get(), createSingleItemTable(CactusRegistry.CACTUS_FRUIT.get(), UniformGenerator.between(0.0F, 1.0F)));
			this.dropSelf(CactusRegistry.CACTUS_SLIME_BLOCK.get());
			this.dropSelf(CactusRegistry.CACTUS_TNT.get());
			this.add(CactusRegistry.CACTUS_TNT.get(), LootTable.lootTable().withPool(applyExplosionCondition(CactusRegistry.CACTUS_TNT.get(),
					LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
							.add(LootItem.lootTableItem(CactusRegistry.CACTUS_TNT.get())
									.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(CactusRegistry.CACTUS_TNT.get())
											.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TntBlock.UNSTABLE, false)))))));
			this.dropSelf(CactusRegistry.CARVED_CACTUS.get());
			this.dropSelf(CactusRegistry.JACKO_CACTUS.get());
			this.dropSelf(CactusRegistry.PRICKLY_IRON.get());
		}

		@Override
		protected Iterable<Block> getKnownBlocks() {
			return CactusRegistry.BLOCKS.getEntries().stream().map(holder -> (Block) holder.get())::iterator;
		}
	}

	@Override
	protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationContext) {
		map.forEach((name, table) -> table.validate(validationContext));
	}
}