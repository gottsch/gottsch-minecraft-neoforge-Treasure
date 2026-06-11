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
package mod.gottsch.neoforge.treasure2.core.structure.templatesystem;

import com.google.common.collect.Lists;
import com.mojang.serialization.MapCodec;
import mod.gottsch.neo.gottschcore.size.DoubleRange;
import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.core.block.TreasureBlocks;
import mod.gottsch.neoforge.treasure2.core.block.entity.ProximityMobSetSpawnerBlockEntity;
import mod.gottsch.neoforge.treasure2.core.block.entity.StructureMobSetBlockEntity;
import mod.gottsch.neoforge.treasure2.core.config.Config;
import mod.gottsch.neoforge.treasure2.core.mobset.MobSetData;
import mod.gottsch.neoforge.treasure2.core.mobset.MobSetDataRegistry;
import mod.gottsch.neoforge.treasure2.core.util.ModUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagParser;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.neoforged.neoforge.common.MonsterRoomHooks;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Replaces structure-marker / vanilla spawner blocks with Treasure2 proximity spawners.
 *
 * @author by Mark Gottschling on 8/13/2025
 */
public class SpawnerProcessor extends StructureProcessor {

    public static final MapCodec<SpawnerProcessor> CODEC = MapCodec.unit(SpawnerProcessor::new);

    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader, BlockPos pos, BlockPos relativePos, StructureTemplate.StructureBlockInfo original, StructureTemplate.StructureBlockInfo current, StructurePlaceSettings placementSettings) {
        try {
            // if the block is not a structure mob set marker, process it normally.
            if (!current.state().is(TreasureBlocks.STRUCTURE_MOB_SET.get())) {

                // handle LEGACY iron block replacement
                if (current.state().is(Blocks.IRON_BLOCK)) {
                    return buildDefaultOneTimeSpawner(placementSettings.getRandom(current.pos()), current.pos(), new DoubleRange(1, 2), 10D);
                } else if (current.state().is(Blocks.SPAWNER)) {
                    Treasure.LOGGER.debug("current spawner nbt -> {}", current.nbt());
                    return buildVanillaSpawner(placementSettings.getRandom(current.pos()), current.pos());
                }
                // return the original block
                return current;
            }

            // process STRUCTURE_MOB_SET blocks.
            return Optional.ofNullable(current.nbt())
                    .map(tag -> tag.getString(StructureMobSetBlockEntity.MOBSET))
                    .filter(mobSet -> !mobSet.isEmpty())
                    .map(mobSet -> {
                        Treasure.LOGGER.info("found mobSet -> {}", mobSet);
                        return selectMobSet(new Random(), Lists.newArrayList(mobSet))
                                .map(selectedMobSet -> buildOneTimeMobSetSpawner(placementSettings.getRandom(current.pos()), current.pos(), selectedMobSet, current.nbt().getInt(StructureMobSetBlockEntity.PROXIMITY)))
                                .orElse(null); // return null if mob set isn't found
                    })
                    .orElseGet(() -> {
                        // fallback to mobSets list if the single mobSet value is empty
                        return Optional.ofNullable(current.nbt())
                                .map(tag -> tag.getList("mobSets", Tag.TAG_STRING))
                                .map(listTag -> {
                                    List<String> mobSets = listTag.stream().map(tag -> ((StringTag) tag).getAsString()).collect(Collectors.toList());
                                    return selectMobSet(new Random(), mobSets)
                                            .map(selectedMobSet -> buildOneTimeMobSetSpawner(placementSettings.getRandom(current.pos()), current.pos(), selectedMobSet, current.nbt().getInt(StructureMobSetBlockEntity.PROXIMITY)))
                                            .orElse(null); // return null if mob set isn't found
                                })
                                .orElseGet(() -> buildDefaultOneTimeSpawner(placementSettings.getRandom(current.pos()), current.pos(), new DoubleRange(1, 2), 10D));
                    });
        } catch (Exception e) {
            Treasure.LOGGER.error("error processing spawner block", e);
            throw e;
        }
    }

    private StructureTemplate.StructureBlockInfo buildVanillaSpawner(RandomSource random, BlockPos pos) {
        if (!Config.SERVER.markers.enableSpawner.get()) {
            Treasure.LOGGER.warn("unable to generate vanilla spawner block entity at -> {}", pos);
            return null;
        }

        BlockState spawnerState = Blocks.SPAWNER.defaultBlockState();
        EntityType<?> entityType = MonsterRoomHooks.getRandomMonsterRoomMob(random);
        String nbtValue = String.format(SPAWNER_NBT, EntityType.getKey(entityType).toString());

        return setSpawnerNbt(nbtValue)
                .map(tag -> new StructureTemplate.StructureBlockInfo(pos, spawnerState, tag))
                .orElse(null);
    }

    private static Optional<CompoundTag> setSpawnerNbt(String nbtString) {
        try {
            // parse the NBT string into a CompoundTag
            return Optional.ofNullable(TagParser.parseTag(nbtString));
        } catch (Exception e) {
            Treasure.LOGGER.warn("failed to parse NBT string for spawner: ", e);
        }
        return Optional.empty();
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModProcessors.SPAWNER_PROCESSOR.get();
    }

    private StructureTemplate.StructureBlockInfo buildOneTimeMobSetSpawner(RandomSource random, BlockPos pos, MobSetData mobSet, double proximity) {
        // create and populate nbt data of a ProximityMobSetSpawner
        CompoundTag tag = new CompoundTag();
        tag.putString(ProximityMobSetSpawnerBlockEntity.MOBSET_NAME, mobSet.getId().toString());
        tag.putInt(ProximityMobSetSpawnerBlockEntity.MIN_MOBS, mobSet.getCount().getMin());
        tag.putInt(ProximityMobSetSpawnerBlockEntity.MAX_MOBS, mobSet.getCount().getMax());
        tag.putDouble(ProximityMobSetSpawnerBlockEntity.PROXIMITY_TAG, proximity);
        tag.putBoolean(ProximityMobSetSpawnerBlockEntity.IS_DEAD, false);

        return new StructureTemplate.StructureBlockInfo(pos, TreasureBlocks.PROXIMITY_MOBSET_SPAWNER.get().defaultBlockState(), tag);
    }

    private StructureTemplate.StructureBlockInfo buildDefaultOneTimeSpawner(RandomSource random, BlockPos pos, DoubleRange range, double proximity) {
        // select a vanilla mob, with a 20% chance to be a VINDICATOR.
        EntityType<?> entityType = random.nextInt(100) < 20
                ? EntityType.VINDICATOR
                : MonsterRoomHooks.getRandomMonsterRoomMob(random);

        Treasure.LOGGER.debug("Using mob -> {} for proximity spawner.", EntityType.getKey(entityType));

        // create and populate nbt data of a ProximitySpawner
        CompoundTag tag = new CompoundTag();
        tag.putString("mobName", EntityType.getKey(entityType).toString());
        tag.putInt("mobNumMin", range.getMinInt());
        tag.putInt("mobNumMax", range.getMaxInt());
        tag.putDouble(ProximityMobSetSpawnerBlockEntity.PROXIMITY_TAG, proximity);
        tag.putBoolean(ProximityMobSetSpawnerBlockEntity.IS_DEAD, false);

        return new StructureTemplate.StructureBlockInfo(pos, TreasureBlocks.PROXIMITY_SPAWNER.get().defaultBlockState(), tag);
    }

    public Optional<MobSetData> selectMobSet(Random random, List<String> mobSets) {
        // check if the list is not null and not empty, then get a random element.
        return Optional.ofNullable(mobSets)
                .filter(mobList -> !mobList.isEmpty())
                .map(mobList -> ModUtil.asLocation(mobList.get(random.nextInt(mobList.size()))))
                .flatMap(mobSetName -> {
                    Treasure.LOGGER.debug("selected mob set -> {}", mobSetName);
                    return MobSetDataRegistry.get(mobSetName);
                });
    }

    private static final String SPAWNER_NBT = """
    {
        SpawnData: {
            entity: {
                id: "%s",
            }
        },
        SpawnCount: 2,
        SpawnRange: 4,
        Delay: 200,
        MinSpawnDelay: 100,
        MaxSpawnDelay: 300,
        RequiredPlayerRange: 16
    }
""";
}
