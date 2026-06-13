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


import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mod.gottsch.neo.gottschcore.spatial.Coords;
import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.core.block.ITreasureChestBlock;
import mod.gottsch.neoforge.treasure2.core.block.StandardChestBlock;
import mod.gottsch.neoforge.treasure2.core.cache.TreasureChestCache;
import mod.gottsch.neoforge.treasure2.core.cache.data.TreasureChestCacheData;
import mod.gottsch.neoforge.treasure2.core.persistence.TreasureSavedData;
import mod.gottsch.neoforge.treasure2.core.rarity.IRarity;
import mod.gottsch.neoforge.treasure2.core.rarity.Rarity;
import mod.gottsch.neoforge.treasure2.core.rarity.RarityWeightsManager;
import mod.gottsch.neoforge.treasure2.core.rarity.TreasureRarities;
import mod.gottsch.neoforge.treasure2.core.registry.ChestSubprocessorDataRegistry;
import mod.gottsch.neoforge.treasure2.core.structure.BlockRotationUtil;
import mod.gottsch.neoforge.treasure2.core.structure.templatesystem.chest.IChestSubprocessor;
import mod.gottsch.neoforge.treasure2.core.structure.templatesystem.chest.TreasureChestSubprocessors;
import mod.gottsch.neoforge.treasure2.core.structure.templatesystem.data.ChestSubprocessorData;
import mod.gottsch.neoforge.treasure2.core.util.ModUtil;
import mod.gottsch.neoforge.treasure2.core.world.feature.IFeatureType;
import mod.gottsch.neoforge.treasure2.core.world.feature.TreasureFeatureTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author by Mark Gottschling on 8/15/2025
 */
public class TreasureChestProcessor extends VanillaChestProcessor {

    // NOTE feature type / dimension come from the JSON definition (cannot be passed via a custom PlaceSettings).
    protected IFeatureType featureType;
    @Deprecated
    protected ResourceLocation dimension;

    // property to capture the size of the current piece
    private Vec3i size;

    // cached info — used so that if multiple passes occur the cached info can be reused instead of
    // being replaced with the original/current info.
    private StructureTemplate.StructureBlockInfo cachedInfo;

    public static final MapCodec<TreasureChestProcessor> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            IFeatureType.BY_NAME_CODEC.fieldOf("feature_type").forGetter(TreasureChestProcessor::getFeatureType),
            ResourceLocation.CODEC.optionalFieldOf("dimension", ModUtil.asLocation("minecraft:overworld")).forGetter(TreasureChestProcessor::getDimension),
            ResourceLocation.CODEC.optionalFieldOf("loot_table").forGetter(VanillaChestProcessor::getLootTable)
    ).apply(instance, TreasureChestProcessor::new));


    public TreasureChestProcessor(IFeatureType featureType) {
        this(featureType, ModUtil.asLocation("minecraft:overworld"), Optional.empty());
    }

    public TreasureChestProcessor(IFeatureType featureType, ResourceLocation dimension, Optional<ResourceLocation> lootTable) {
        super(lootTable);
        this.featureType = featureType;
        this.dimension = dimension;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModProcessors.CHEST_PROCESSOR.get();
    }

    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader, BlockPos piecePos, BlockPos relativePos, StructureTemplate.StructureBlockInfo original, StructureTemplate.StructureBlockInfo current, StructurePlaceSettings placementSettings) {

        if (!(current.state().getBlock() instanceof ITreasureChestBlock)) {
            return current;
        }

        // get real world piece pos
        BlockPos newPiecePos = BlockRotationUtil.transformStartCoords(piecePos, this.size, placementSettings.getRotation());
        Treasure.LOGGER.debug("attempting process called on piece pos -> {}", newPiecePos);

        // NOTE this assumes that the structure has only 1 treasure chest present
        if (addProcessGuard(TREASURE_CHEST, newPiecePos)) {
            Treasure.LOGGER.debug("single pass processBlock called on pos -> {}", newPiecePos);
            StructureTemplate.StructureBlockInfo info = buildTreasureChest(levelReader, current.state(), current.pos(), placementSettings);
            cachedInfo = info;
            return info;
        }

        // return the cached info if it exists
        return cachedInfo != null ? cachedInfo : current;
    }

    @Override
    public @Nullable StructureTemplate.StructureBlockInfo process(LevelReader levelReader, BlockPos piecePos, BlockPos relativePos, StructureTemplate.StructureBlockInfo original, StructureTemplate.StructureBlockInfo current, StructurePlaceSettings placeSettings, @Nullable StructureTemplate template) {
        this.size = template.getSize();
        return super.process(levelReader, piecePos, relativePos, original, current, placeSettings, template);
    }

    @Override
    public List<StructureTemplate.StructureBlockInfo> finalizeProcessing(ServerLevelAccessor levelAccessor, BlockPos piecePos, BlockPos originalPos, List<StructureTemplate.StructureBlockInfo> blocks, List<StructureTemplate.StructureBlockInfo> processedBlocks, StructurePlaceSettings placeSettings) {
        // NOTE this is the piece, not the entire structure. This runs pre-placement (no block entity
        // exists yet) — it only stamps the in-memory chest cache with the dimension, it does not touch
        // block entities. The chest's loot/locks/etc. are applied at placement via the generation-data
        // NBT carried on the StructureBlockInfo (see IChestSubprocessor).
        BlockPos newPiecePos = BlockRotationUtil.transformStartCoords(piecePos, this.size, placeSettings.getRotation());

        if (hasProcessGuard(TREASURE_CHEST, newPiecePos)) {
            if (addFinalizeGuard(TREASURE_CHEST, newPiecePos)) {
                Treasure.LOGGER.debug("finalize processing called on pos -> {}", piecePos);
                processedBlocks.forEach(info -> {
                    if (info.state().getBlock() instanceof ITreasureChestBlock) {

                        // update the matching cached chest with the dimension value
                        TreasureChestCache.getCache().stream()
                                .filter(chest -> chest.getCoords().equals(Coords.of(info.pos())))
                                .filter(chest -> chest.getBiomeName() != null && chest.getBiomeName().equals(getBiomeName(levelAccessor, info.pos())))
                                .filter(chest -> chest.getDimensionName() == null)
                                .findFirst().ifPresent(chest -> {
                                    chest.setDimensionName(levelAccessor.dimensionType().effectsLocation());
                                    // mark the persistence data as dirty
                                    TreasureSavedData.get(levelAccessor.getLevel()).setDirty();
                                });
                    }
                });
            }
        }
        return super.finalizeProcessing(levelAccessor, piecePos, originalPos, blocks, processedBlocks, placeSettings);
    }


    public StructureTemplate.StructureBlockInfo buildTreasureChest(LevelReader levelReader, BlockState state, BlockPos pos, StructurePlaceSettings placeSettings) {

        // 1. map feature type to either AQUATIC or TERRANEAN.
        IFeatureType aquatic = TreasureFeatureTypes.AQUATIC.get();
        IFeatureType processedFeatureType = aquatic.equals(this.featureType) ? aquatic : TreasureFeatureTypes.TERRANEAN.get();

        // 2. get rarity and ensure it's a valid entry.
        Optional<Rarity> rarityOptional = Optional.ofNullable(RarityWeightsManager.getNextRarity(processedFeatureType))
                .filter(rarity -> rarity != Rarity.NONE)
                .filter(rarity -> rarity != TreasureRarities.UNKNOWN.get());

        IRarity rarityEntry = rarityOptional.orElseGet(() -> {
            Treasure.LOGGER.warn("unable to obtain the next rarity for generator -> {}, reverting to default rarity.", processedFeatureType);
            return (Rarity) TreasureRarities.COMMON.get();
        });
        Treasure.LOGGER.debug("rarity -> {}", rarityEntry);

        // 3. get chest subprocessor data.
        Optional<ChestSubprocessorData> dataOptional = ChestSubprocessorDataRegistry.getAssociation(processedFeatureType, rarityEntry);
        ChestSubprocessorData data = dataOptional.orElseGet(() -> {
            Treasure.LOGGER.warn("unable to locate chest subprocessor data for feature type -> {} and rarity -> {}, reverting to default subprocessor data.", processedFeatureType, rarityEntry.getName());
            // TODO this is going to be null; supply real default subprocessor data
            return TreasureChestSubprocessors.standard().getData();
        });

        // 4. get a chest subprocessor.
        IChestSubprocessor subprocessor = TreasureChestSubprocessors.getChestSubprocessor(data.getType()).orElseGet(() -> {
            Treasure.LOGGER.warn("unable to locate chest subprocessor for processor type -> {}, reverting to default subprocessor", data.getType());
            return TreasureChestSubprocessors.standard();
        });

        // 5. set properties and process the subprocessor to get the StructureBlockInfo.
        subprocessor.setFeatureType(processedFeatureType);
        subprocessor.setData(data);
        Treasure.LOGGER.debug("original chest is facing -> {}", state.getValue(StandardChestBlock.FACING));
        Optional<StructureTemplate.StructureBlockInfo> infoOptional = subprocessor
                .process(levelReader, placeSettings.getRandom(pos), state, pos, placeSettings.getRotation(), rarityEntry, data);

        // 6. if the block info is present, cache the data and return it. Otherwise, return the default chest.
        return infoOptional.map(info -> {
            Treasure.LOGGER.debug("info -> {}", info);
            TreasureChestCacheData chestSpawn = new TreasureChestCacheData();
            chestSpawn.setChestName(ModUtil.getName(info.state().getBlock()));
            chestSpawn.setCoords(Coords.of(info.pos()));
            chestSpawn.setBiomeName(getBiomeName(levelReader, info.pos()));
            chestSpawn.setFeatureType(getFeatureType()); // NOTE the original feature type to describe what feature spawned the chest
            chestSpawn.setRarity(rarityEntry);
            chestSpawn.setDiscovered(false);
            TreasureChestCache.cache(chestSpawn);
            Treasure.LOGGER.debug("caching chest at pos -> {}", info.pos());

            // increment rarity weighted collection
            RarityWeightsManager.adjustAllWeightsExcept(this.featureType, 1, rarityEntry);

            return info;
        }).orElseGet(() -> {
            Treasure.LOGGER.warn("unable to generate StructureBlockInfo for processor type -> {}, reverting to default chest", data.getType());
            return subprocessor.defaultChest(levelReader, state, pos, placeSettings);
        });
    }

    /**
     * @return the registry name of the biome at the given position, or null if it cannot be resolved.
     */
    protected static ResourceLocation getBiomeName(LevelReader level, BlockPos pos) {
        return level.getBiome(pos).unwrapKey().map(ResourceKey::location).orElse(null);
    }

    public IFeatureType getFeatureType() {
        return featureType;
    }

    public ResourceLocation getDimension() {
        return dimension;
    }
}
