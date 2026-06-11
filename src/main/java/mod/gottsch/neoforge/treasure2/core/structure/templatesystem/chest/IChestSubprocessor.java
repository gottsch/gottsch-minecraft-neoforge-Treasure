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
package mod.gottsch.neoforge.treasure2.core.structure.templatesystem.chest;

import mod.gottsch.neo.gottschcore.random.RandomHelper;
import mod.gottsch.neo.gottschcore.random.WeightedCollection;
import mod.gottsch.neo.gottschcore.spatial.Heading;
import mod.gottsch.neo.gottschcore.spatial.Rotate;
import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.core.block.AbstractTreasureChestBlock;
import mod.gottsch.neoforge.treasure2.core.block.ITreasureChestBlock;
import mod.gottsch.neoforge.treasure2.core.block.StandardChestBlock;
import mod.gottsch.neoforge.treasure2.core.block.TreasureBlocks;
import mod.gottsch.neoforge.treasure2.core.block.entity.AbstractTreasureChestBlockEntity;
import mod.gottsch.neoforge.treasure2.core.block.entity.GenerationContext;
import mod.gottsch.neoforge.treasure2.core.config.Config;
import mod.gottsch.neoforge.treasure2.core.item.LockItem;
import mod.gottsch.neoforge.treasure2.core.lock.LockLayout;
import mod.gottsch.neoforge.treasure2.core.lock.LockSlot;
import mod.gottsch.neoforge.treasure2.core.lock.LockState;
import mod.gottsch.neoforge.treasure2.core.loot.TreasureLootTableTypes;
import mod.gottsch.neoforge.treasure2.core.rarity.IRarity;
import mod.gottsch.neoforge.treasure2.core.rarity.RarityWeight;
import mod.gottsch.neoforge.treasure2.core.rarity.TreasureRarities;
import mod.gottsch.neoforge.treasure2.core.registry.LootTableRegistry;
import mod.gottsch.neoforge.treasure2.core.registry.MimicRegistry;
import mod.gottsch.neoforge.treasure2.core.registry.RarityTagAssociationRegistry;
import mod.gottsch.neoforge.treasure2.core.structure.templatesystem.data.ChestSubprocessorData;
import mod.gottsch.neoforge.treasure2.core.util.ModUtil;
import mod.gottsch.neoforge.treasure2.core.world.feature.IFeatureType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A chest subprocessor is similar to the legacy chest generator except that it deals with jigsaw
 * processing, i.e. blocks are not placed yet and block entities do not exist yet (their owning
 * blocks are not placed).
 *
 * <p>NeoForge 1.21.1 port note: the chest's loot/locks/sealed/mimic/generation-context data is no
 * longer written as flat NBT for the block entity to read on load via raw {@code CompoundTag}
 * fields. Instead it is encoded (via the relevant CODECs) into a single
 * {@link AbstractTreasureChestBlockEntity#GENERATION_DATA_TAG} compound on the returned
 * {@link StructureTemplate.StructureBlockInfo}. Vanilla {@code loadWithComponents(nbt)} feeds that
 * tag to the chest block entity at placement, where {@code loadAdditional} parses it once into the
 * persistent attachments. This is required because {@code finalizeProcessing} runs pre-placement
 * (no block entity exists there yet).
 *
 * @author by Mark Gottschling on 8/22/2025
 */
public interface IChestSubprocessor {
    // feature type is populated by the calling processor
    void setFeatureType(IFeatureType featureType);

    IFeatureType getFeatureType();

    void setData(ChestSubprocessorData data);

    ChestSubprocessorData getData();

    /*
     * fallback used when a subprocessor cannot generate a chest. Produces a sealed, common wood chest
     * with a vanilla simple-dungeon loot table.
     */
    default StructureTemplate.StructureBlockInfo defaultChest(LevelReader levelReader, BlockState state, BlockPos pos, StructurePlaceSettings placeSettings) {
        Treasure.LOGGER.debug("attempting to use default chest.");
        HolderLookup.Provider provider = levelReader.registryAccess();

        AbstractTreasureChestBlock chest = (AbstractTreasureChestBlock) TreasureBlocks.WOOD_CHEST.get();
        BlockState newState = addState(levelReader, state, pos, placeSettings, chest);

        IRarity rarity = TreasureRarities.COMMON.get();
        Direction direction = placeSettings.getRotation().rotate(state.getValue(StandardChestBlock.FACING));
        // get the current state's facing direction
        Direction facing = state.getValue(StandardChestBlock.FACING);
        Heading originalHeading = Heading.fromDirection(facing);
        // get the direction the block is facing currently
        Heading heading = Heading.fromDirection(direction);
        Rotate rotate = originalHeading.getRotation(heading);

        CompoundTag genData = new CompoundTag();
        RegistryOps<Tag> ops = provider.createSerializationContext(NbtOps.INSTANCE);
        genData.putString(AbstractTreasureChestBlockEntity.LOOT_TABLE_TAG, BuiltInLootTables.SIMPLE_DUNGEON.location().toString());
        genData.putBoolean(AbstractTreasureChestBlockEntity.SEALED_TAG, true);
        putLockStates(genData, ops, buildLocks(placeSettings.getRandom(pos), chest.getLockLayout(), rarity, rotate, provider));
        putGenerationContext(genData, ops, new GenerationContext(rarity, getFeatureType()));

        return new StructureTemplate.StructureBlockInfo(pos, newState, wrap(genData));
    }

    default Optional<StructureTemplate.StructureBlockInfo> process(LevelReader levelReader, RandomSource random, BlockState state, BlockPos pos, Rotation rotation, IRarity rarity, ChestSubprocessorData data) {
        HolderLookup.Provider provider = levelReader.registryAccess();

        // select a chest by the rarity
        AbstractTreasureChestBlock chest = selectChest(random, rarity, provider);
        Treasure.LOGGER.debug("using chest -> {}", ModUtil.getName(chest));

        // have to manually rotate chests as they do not extend vanilla chests and aren't recognized for rotation.
        FluidState fluidState = levelReader.getBlockState(pos).getFluidState();
        Direction newDirection = rotation.rotate(state.getValue(StandardChestBlock.FACING));
        BlockState newState = chest
                .defaultBlockState()
                .setValue(StandardChestBlock.FACING, newDirection)
                .setValue(ITreasureChestBlock.DISCOVERED, false)
                .setValue(AbstractTreasureChestBlock.WATERLOGGED, fluidState.getType() == Fluids.WATER);

        // rotate locks from NORTH to current facing
        Heading heading = Heading.fromDirection(newDirection);
        Rotate rotate = Heading.NORTH.getRotation(heading);

        CompoundTag genData = new CompoundTag();
        RegistryOps<Tag> ops = provider.createSerializationContext(NbtOps.INSTANCE);

        // the loot table (name only — loot rolls on first open)
        ResourceLocation lootTable = selectLootTable(random, rarity);
        if (lootTable != null) {
            genData.putString(AbstractTreasureChestBlockEntity.LOOT_TABLE_TAG, lootTable.toString());
        }
        // seal the chest
        genData.putBoolean(AbstractTreasureChestBlockEntity.SEALED_TAG, true);
        // add locks
        putLockStates(genData, ops, buildLocks(random, chest.getLockLayout(), rarity, rotate, provider));
        // add generation context (loot rarity + feature type)
        putGenerationContext(genData, ops, new GenerationContext(rarity, getFeatureType()));
        // add mimic, if any
        selectMimic(random, chest).ifPresent(mimic -> genData.putString(AbstractTreasureChestBlockEntity.MIMIC_TAG, mimic.toString()));

        return Optional.of(new StructureTemplate.StructureBlockInfo(pos, newState, wrap(genData)));
    }

    /*
     * wraps the generation data compound under the parent key recognised by the chest block entity.
     */
    default CompoundTag wrap(CompoundTag genData) {
        CompoundTag nbt = new CompoundTag();
        nbt.put(AbstractTreasureChestBlockEntity.GENERATION_DATA_TAG, genData);
        return nbt;
    }

    default void putLockStates(CompoundTag genData, RegistryOps<Tag> ops, List<LockState> locks) {
        if (locks != null && !locks.isEmpty()) {
            LockState.CODEC.listOf().encodeStart(ops, locks)
                    .resultOrPartial(err -> Treasure.LOGGER.warn("unable to encode lock states -> {}", err))
                    .ifPresent(tag -> genData.put(AbstractTreasureChestBlockEntity.LOCK_STATES_TAG, tag));
        }
    }

    default void putGenerationContext(CompoundTag genData, RegistryOps<Tag> ops, GenerationContext context) {
        GenerationContext.CODEC.encodeStart(ops, context)
                .resultOrPartial(err -> Treasure.LOGGER.warn("unable to encode generation context -> {}", err))
                .ifPresent(tag -> genData.put(AbstractTreasureChestBlockEntity.GENERATION_CONTEXT_TAG, tag));
    }

    default AbstractTreasureChestBlock selectChest(final RandomSource random, final IRarity rarity, final HolderLookup.Provider provider) {
        List<AbstractTreasureChestBlock> validChests = getValidChests(rarity, provider);

        if (validChests.isEmpty()) {
            Treasure.LOGGER.warn("unable to get treasure chest by rarity {}, no valid AbstractTreasureChestBlocks found", rarity);
            return (AbstractTreasureChestBlock) TreasureBlocks.WOOD_CHEST.get();
        }

        // select a random chest from the filtered list
        return validChests.get(random.nextInt(validChests.size()));
    }

    default List<AbstractTreasureChestBlock> getValidChests(final IRarity rarity, final HolderLookup.Provider provider) {
        List<Block> chestBlocks = RarityTagAssociationRegistry.getChestBlocks(rarity, provider);

        // filter and cast the list to ensure only valid chest blocks are considered
        return chestBlocks.stream()
                .filter(block -> block instanceof AbstractTreasureChestBlock)
                .map(block -> (AbstractTreasureChestBlock) block)
                .collect(Collectors.toList());
    }

    default BlockState addState(LevelReader level, BlockState state, BlockPos pos, StructurePlaceSettings placeSettings, AbstractTreasureChestBlock chest) {
        FluidState fluidState = level.getBlockState(pos).getFluidState();
        Direction direction = placeSettings.getRotation().rotate(state.getValue(StandardChestBlock.FACING));
        return chest
                .defaultBlockState()
                .setValue(StandardChestBlock.FACING, direction)
                .setValue(ITreasureChestBlock.DISCOVERED, false)
                .setValue(AbstractTreasureChestBlock.WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    /*
     * selects a loot table resource location for the chest. honours the subprocessor data's explicit
     * loot tables / loot-table-rarity weights, falling back to the rarity-based registry list and
     * finally to the vanilla simple-dungeon table.
     */
    default ResourceLocation selectLootTable(RandomSource random, IRarity defaultRarity) {
        IRarity rarity;

        // safely select a loot table rarity from the data association list, with a fallback
        List<RarityWeight> rarityWeights = getData().getLootTableRarities();
        if (!rarityWeights.isEmpty()) {
            WeightedCollection<Integer, ResourceLocation> weightedCollection = new WeightedCollection<>();
            rarityWeights.forEach(rarityWeight -> weightedCollection.add(rarityWeight.getWeight(), rarityWeight.getRarity()));
            rarity = TreasureRarities.getRarityByName(weightedCollection.next()).orElse(TreasureRarities.COMMON.get());
        } else {
            rarity = defaultRarity;
        }

        List<ResourceLocation> lootTables;
        if (!getData().getLootTables().isEmpty()) {
            lootTables = getData().getLootTables();
        } else {
            lootTables = LootTableRegistry.getLootTableIds(TreasureLootTableTypes.CHESTS.get(), rarity);
        }

        if (lootTables.isEmpty()) {
            Treasure.LOGGER.warn("unable to locate loot tables by rarity {}", rarity);
            return BuiltInLootTables.SIMPLE_DUNGEON.location();
        }

        ResourceLocation lootTable = lootTables.get(random.nextInt(lootTables.size()));
        Treasure.LOGGER.debug("using loot table -> {}", lootTable);
        return lootTable;
    }

    default Optional<ResourceLocation> selectMimic(RandomSource random, AbstractTreasureChestBlock chest) {
        // check against config if mimic should be used
        if (Config.SERVER.mobs.enableMimics.get() && random.nextDouble() < getData().getMimicProbability()) {
            return MimicRegistry.getMimic(ModUtil.getName(chest));
        }
        return Optional.empty();
    }

    /*
     * builds the list of lock states for the chest, selecting lock items by rarity (or the
     * subprocessor data's explicit lock rarities) and rotating each slot to match the chest facing.
     */
    default List<LockState> buildLocks(RandomSource random, LockLayout layout, IRarity defaultRarity, Rotate rotate, HolderLookup.Provider provider) {
        Treasure.LOGGER.debug("original lock layout -> {}", layout);
        List<LockItem> locks = new ArrayList<>();

        // create a list of locks to select from
        if (!getData().getLockRarities().isEmpty()) {
            List<IRarity> rarities = getData().getLockRarities().stream()
                    .map(TreasureRarities::getRarityByName)
                    .flatMap(Optional::stream)
                    .toList();

            rarities.forEach(rarity -> {
                List<LockItem> lockItems = RarityTagAssociationRegistry.getLockItems(rarity, provider).stream()
                        .filter(LockItem.class::isInstance)
                        .map(LockItem.class::cast)
                        .toList();
                locks.addAll(lockItems);
            });
        } else {
            locks.addAll(RarityTagAssociationRegistry.getLockItems(defaultRarity, provider).stream()
                    .filter(LockItem.class::isInstance)
                    .map(LockItem.class::cast)
                    .toList());
        }

        // determine the number of locks to use
        int numLocks = randomizedNumberOfLocks(random, layout);

        if (numLocks > 0 && !locks.isEmpty()) {
            return buildLocks(random, layout, locks, numLocks, rotate);
        }
        return new ArrayList<>();
    }

    default List<LockState> buildLocks(RandomSource random, LockLayout lockLayout, List<LockItem> locks, int numLocks, Rotate rotate) {
        List<LockState> lockStates = new ArrayList<>();
        if (locks == null || locks.isEmpty() || numLocks <= 0) {
            return lockStates; // no locks to add, so we can exit early.
        }

        for (int i = 0; i < numLocks; i++) {
            LockItem lock = locks.get(random.nextInt(locks.size()));

            LockState lockState = new LockState();
            LockSlot lockSlot = lockLayout.getSlots()[i];
            // skip rotate if NO_ROTATE as there is still processing within the rotate() method
            if (rotate != Rotate.NO_ROTATE) {
                lockSlot = lockSlot.rotate(rotate);
            }

            lockState.setSlot(lockSlot);
            lockState.setLock(lock);

            lockStates.add(lockState);
        }
        return lockStates;
    }

    default int randomizedNumberOfLocks(RandomSource random, LockLayout lockLayout) {
        // NOTE use RandomHelper instead of random.nextInt() since RandomHelper is inclusive and nextInt() is not.
        int numLocks = RandomHelper.randomInt(random, random.nextInt(100) < 20 ? 0 : 1, lockLayout.getMaxLocks());
        Treasure.LOGGER.debug("# of locks to use: {}", numLocks);
        return numLocks;
    }
}
