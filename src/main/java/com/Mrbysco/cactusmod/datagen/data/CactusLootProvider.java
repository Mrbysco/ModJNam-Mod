package com.mrbysco.cactusmod.datagen.data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class CactusLootProvider extends LootTableProvider {
	public CactusLootProvider(DataGenerator gen) {
		super(gen);
	}

	@Override
	protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
		return ImmutableList.of(
				Pair.of(CactusEntityLoot::new, LootContextParamSets.ENTITY), Pair.of(CactusBlockLoot::new, LootContextParamSets.BLOCK)
		);
	}

	public static class CactusEntityLoot extends EntityLoot {
		private static final Set<EntityType<?>> SPECIAL_LOOT_TABLE_TYPES = ImmutableSet.of(CactusRegistry.CACTONI.get(), CactusRegistry.CACTUS_GOLEM.get(), CactusRegistry.CACTUS_SNOW_GOLEM.get());

		@Override
		protected void addTables() {
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
			this.add(sheepLocation, LootTable.lootTable()
					.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
							.add(LootItem.lootTableItem(Items.MUTTON).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
									.apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
									.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));

			ResourceLocation sheepLocation1 = new ResourceLocation(Reference.MOD_ID, "entities/cactus_sheep1");
			this.add(sheepLocation1, LootTable.lootTable()
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

		@Override
		protected boolean isNonLiving(EntityType<?> entitytype) {
			return !SPECIAL_LOOT_TABLE_TYPES.contains(entitytype) && entitytype.getCategory() == MobCategory.MISC;
		}

		@Override
		protected Iterable<EntityType<?>> getKnownEntities() {
			Stream<EntityType<?>> entityTypeStream = CactusRegistry.ENTITIES.getEntries().stream().map(RegistryObject::get);
			return entityTypeStream::iterator;
		}
	}

	public static class CactusBlockLoot extends BlockLoot {
		@Override
		protected void addTables() {
			this.dropSelf(CactusRegistry.CACTUS_BRICK_BLOCK.get());
			this.dropSelf(CactusRegistry.CACTUS_BRICK_STAIR.get());
			this.add(CactusRegistry.CACTUS_BRICK_SLAB.get(), BlockLoot::createSlabItemTable);
			this.add(CactusRegistry.CACTUS_CAKE.get(), noDrop());
			this.dropSelf(CactusRegistry.CACTUS_CARPET.get());
			this.add(CactusRegistry.CACTUS_CHEST.get(), BlockLoot::createNameableBlockEntityTable);
			this.dropSelf(CactusRegistry.CACTUS_CRAFTING_TABLE.get());
			this.add(CactusRegistry.CACTUS_DISPENSER.get(), BlockLoot::createNameableBlockEntityTable);
			this.dropSelf(CactusRegistry.CACTUS_CRAFTING_TABLE.get());
			this.add(CactusRegistry.CACTUS_DOOR.get(), BlockLoot::createDoorTable);
			this.dropSelf(CactusRegistry.CACTUS_FLOWER.get());
			this.add(CactusRegistry.CACTUS_HOPPER.get(), BlockLoot::createNameableBlockEntityTable);
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
			return CactusRegistry.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
		}
	}

	@Override
	protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationContext) {
		map.forEach((name, table) -> LootTables.validate(validationContext, name, table));
	}
}