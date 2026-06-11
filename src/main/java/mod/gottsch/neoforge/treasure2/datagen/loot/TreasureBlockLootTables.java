/*
 * This file is part of Treasure2.
 * Copyright (c) 2025 Mark Gottschling (gottsch)
 *
 * Treasure2 is free software: you can redistribute it and/or modify
 * it under the terms of the Open Software Licence 3.0.
 *
 * Treasure2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * Open Software Licence 3.0 for more details.
 *
 * You should have received a copy of the Open Software Licence
 * along with Treasure2. If not, see <https://www.tldrlegal.com/license/open-software-licence-3-0>.
 */
package mod.gottsch.neoforge.treasure2.datagen.loot;

import mod.gottsch.neoforge.treasure2.core.block.SkeletonBlock;
import mod.gottsch.neoforge.treasure2.core.block.TreasureBlocks;
import mod.gottsch.neoforge.treasure2.core.item.TreasureItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;

import java.util.List;
import java.util.Set;

/**
 * @author Mark Gottschling on Nov 6, 2022
 */
public class TreasureBlockLootTables extends BlockLootSubProvider {

    public TreasureBlockLootTables(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        // wishing wells drop their vanilla equivalent material
        this.dropOther(TreasureBlocks.WISHING_WELL.get(), Items.COBBLESTONE);
        this.dropOther(TreasureBlocks.WISHING_WELL_COBBLESTONE.get(), Items.COBBLESTONE);
        this.dropOther(TreasureBlocks.WISHING_WELL_MOSSY_COBBLESTONE.get(), Items.MOSSY_COBBLESTONE);
        this.dropOther(TreasureBlocks.WISHING_WELL_STONE_BRICKS.get(), Items.STONE_BRICKS);
        this.dropOther(TreasureBlocks.WISHING_WELL_MOSSY_STONE_BRICKS.get(), Items.MOSSY_STONE_BRICKS);
        this.dropOther(TreasureBlocks.DESERT_WISHING_WELL.get(), Items.SAND);

        // falling blocks drop themselves
        this.dropSelf(TreasureBlocks.FALLING_GRASS.get());
        this.dropSelf(TreasureBlocks.FALLING_SAND.get());
        this.dropSelf(TreasureBlocks.FALLING_RED_SAND.get());

        // spanish moss drops itself
        this.dropSelf(TreasureBlocks.SPANISH_MOSS.get());

        // gravestones — all 19 drop themselves
        this.dropSelf(TreasureBlocks.GRAVESTONE1_STONE.get());
        this.dropSelf(TreasureBlocks.GRAVESTONE1_COBBLESTONE.get());
        this.dropSelf(TreasureBlocks.GRAVESTONE1_MOSSY_COBBLESTONE.get());
        this.dropSelf(TreasureBlocks.GRAVESTONE1_POLISHED_GRANITE.get());
        this.dropSelf(TreasureBlocks.GRAVESTONE1_OBSIDIAN.get());
        this.dropSelf(TreasureBlocks.GRAVESTONE1_SMOOTH_QUARTZ.get());
        this.dropSelf(TreasureBlocks.GRAVESTONE2_STONE.get());
        this.dropSelf(TreasureBlocks.GRAVESTONE2_COBBLESTONE.get());
        this.dropSelf(TreasureBlocks.GRAVESTONE2_MOSSY_COBBLESTONE.get());
        this.dropSelf(TreasureBlocks.GRAVESTONE2_POLISHED_GRANITE.get());
        this.dropSelf(TreasureBlocks.GRAVESTONE2_OBSIDIAN.get());
        this.dropSelf(TreasureBlocks.GRAVESTONE2_SMOOTH_QUARTZ.get());
        this.dropSelf(TreasureBlocks.GRAVESTONE3_STONE.get());
        this.dropSelf(TreasureBlocks.GRAVESTONE3_COBBLESTONE.get());
        this.dropSelf(TreasureBlocks.GRAVESTONE3_MOSSY_COBBLESTONE.get());
        this.dropSelf(TreasureBlocks.GRAVESTONE3_POLISHED_GRANITE.get());
        this.dropSelf(TreasureBlocks.GRAVESTONE3_OBSIDIAN.get());
        this.dropSelf(TreasureBlocks.GRAVESTONE3_SMOOTH_QUARTZ.get());
        this.dropSelf(TreasureBlocks.SKULL_AND_CROSSBONES.get());

        // skeleton — drops the block item only when the top part breaks; otherwise bones/skull
        this.add(TreasureBlocks.SKELETON.get(),
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(TreasureItems.SKELETON_ITEM.get())
                                .setWeight(20)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(TreasureBlocks.SKELETON.get())
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(SkeletonBlock.PART, SkeletonBlock.EnumPartType.TOP))))
                        .add(LootItem.lootTableItem(Items.BONE).setWeight(75))
                        .add(LootItem.lootTableItem(Items.SKELETON_SKULL).setWeight(5))
                        .when(ExplosionCondition.survivesExplosion())));

        // gravestone spawners — drop themselves
        this.dropSelf(TreasureBlocks.GRAVESTONE1_SPAWNER_STONE.get());
        this.dropSelf(TreasureBlocks.GRAVESTONE2_SPAWNER_COBBLESTONE.get());
        this.dropSelf(TreasureBlocks.GRAVESTONE3_SPAWNER_OBSIDIAN.get());

        // witherwood structure blocks — drop themselves
        this.dropSelf(TreasureBlocks.WITHERWOOD_BROKEN_LOG.get());
        this.dropSelf(TreasureBlocks.WITHERWOOD_BRANCH.get());
        this.dropSelf(TreasureBlocks.WITHERWOOD_ROOT.get());
        this.dropSelf(TreasureBlocks.WITHERWOOD_TWIG.get());

        // witherwood woodset — drop self (door + slab need special tables)
        this.dropSelf(TreasureBlocks.WITHERWOOD_LOG.get());
        this.dropSelf(TreasureBlocks.WITHERWOOD_WOOD.get());
        this.dropSelf(TreasureBlocks.STRIPPED_WITHERWOOD_LOG.get());
        this.dropSelf(TreasureBlocks.STRIPPED_WITHERWOOD_WOOD.get());
        this.dropSelf(TreasureBlocks.WITHERWOOD_PLANKS.get());
        this.add(TreasureBlocks.WITHERWOOD_SLAB.get(), this.createSlabItemTable(TreasureBlocks.WITHERWOOD_SLAB.get()));
        this.dropSelf(TreasureBlocks.WITHERWOOD_STAIRS.get());
        this.dropSelf(TreasureBlocks.WITHERWOOD_FENCE.get());
        this.dropSelf(TreasureBlocks.WITHERWOOD_FENCE_GATE.get());
        this.dropSelf(TreasureBlocks.WITHERWOOD_BUTTON.get());
        this.dropSelf(TreasureBlocks.WITHERWOOD_PRESSURE_PLATE.get());
        this.add(TreasureBlocks.WITHERWOOD_DOOR.get(), this.createDoorTable(TreasureBlocks.WITHERWOOD_DOOR.get()));
        this.dropSelf(TreasureBlocks.WITHERWOOD_TRAPDOOR.get());

        // witherwood signs — wall variants drop the standing/ceiling sign item
        this.dropSelf(TreasureBlocks.WITHERWOOD_SIGN.get());
        this.dropOther(TreasureBlocks.WITHERWOOD_WALL_SIGN.get(), TreasureItems.WITHERWOOD_SIGN_ITEM.get());
        this.dropSelf(TreasureBlocks.WITHERWOOD_HANGING_SIGN.get());
        this.dropOther(TreasureBlocks.WITHERWOOD_WALL_HANGING_SIGN.get(), TreasureItems.WITHERWOOD_HANGING_SIGN_ITEM.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return List.of(
                TreasureBlocks.WISHING_WELL.get(),
                TreasureBlocks.WISHING_WELL_COBBLESTONE.get(),
                TreasureBlocks.WISHING_WELL_MOSSY_COBBLESTONE.get(),
                TreasureBlocks.WISHING_WELL_STONE_BRICKS.get(),
                TreasureBlocks.WISHING_WELL_MOSSY_STONE_BRICKS.get(),
                TreasureBlocks.DESERT_WISHING_WELL.get(),
                TreasureBlocks.FALLING_GRASS.get(),
                TreasureBlocks.FALLING_SAND.get(),
                TreasureBlocks.FALLING_RED_SAND.get(),
                TreasureBlocks.SPANISH_MOSS.get(),
                TreasureBlocks.GRAVESTONE1_STONE.get(),
                TreasureBlocks.GRAVESTONE1_COBBLESTONE.get(),
                TreasureBlocks.GRAVESTONE1_MOSSY_COBBLESTONE.get(),
                TreasureBlocks.GRAVESTONE1_POLISHED_GRANITE.get(),
                TreasureBlocks.GRAVESTONE1_OBSIDIAN.get(),
                TreasureBlocks.GRAVESTONE1_SMOOTH_QUARTZ.get(),
                TreasureBlocks.GRAVESTONE2_STONE.get(),
                TreasureBlocks.GRAVESTONE2_COBBLESTONE.get(),
                TreasureBlocks.GRAVESTONE2_MOSSY_COBBLESTONE.get(),
                TreasureBlocks.GRAVESTONE2_POLISHED_GRANITE.get(),
                TreasureBlocks.GRAVESTONE2_OBSIDIAN.get(),
                TreasureBlocks.GRAVESTONE2_SMOOTH_QUARTZ.get(),
                TreasureBlocks.GRAVESTONE3_STONE.get(),
                TreasureBlocks.GRAVESTONE3_COBBLESTONE.get(),
                TreasureBlocks.GRAVESTONE3_MOSSY_COBBLESTONE.get(),
                TreasureBlocks.GRAVESTONE3_POLISHED_GRANITE.get(),
                TreasureBlocks.GRAVESTONE3_OBSIDIAN.get(),
                TreasureBlocks.GRAVESTONE3_SMOOTH_QUARTZ.get(),
                TreasureBlocks.SKULL_AND_CROSSBONES.get(),
                TreasureBlocks.SKELETON.get(),
                TreasureBlocks.GRAVESTONE1_SPAWNER_STONE.get(),
                TreasureBlocks.GRAVESTONE2_SPAWNER_COBBLESTONE.get(),
                TreasureBlocks.GRAVESTONE3_SPAWNER_OBSIDIAN.get(),
                TreasureBlocks.WITHERWOOD_BROKEN_LOG.get(),
                TreasureBlocks.WITHERWOOD_BRANCH.get(),
                TreasureBlocks.WITHERWOOD_ROOT.get(),
                TreasureBlocks.WITHERWOOD_TWIG.get(),
                TreasureBlocks.WITHERWOOD_LOG.get(),
                TreasureBlocks.WITHERWOOD_WOOD.get(),
                TreasureBlocks.STRIPPED_WITHERWOOD_LOG.get(),
                TreasureBlocks.STRIPPED_WITHERWOOD_WOOD.get(),
                TreasureBlocks.WITHERWOOD_PLANKS.get(),
                TreasureBlocks.WITHERWOOD_SLAB.get(),
                TreasureBlocks.WITHERWOOD_STAIRS.get(),
                TreasureBlocks.WITHERWOOD_FENCE.get(),
                TreasureBlocks.WITHERWOOD_FENCE_GATE.get(),
                TreasureBlocks.WITHERWOOD_BUTTON.get(),
                TreasureBlocks.WITHERWOOD_PRESSURE_PLATE.get(),
                TreasureBlocks.WITHERWOOD_DOOR.get(),
                TreasureBlocks.WITHERWOOD_TRAPDOOR.get(),
                TreasureBlocks.WITHERWOOD_SIGN.get(),
                TreasureBlocks.WITHERWOOD_WALL_SIGN.get(),
                TreasureBlocks.WITHERWOOD_HANGING_SIGN.get(),
                TreasureBlocks.WITHERWOOD_WALL_HANGING_SIGN.get()
        );
    }
}
