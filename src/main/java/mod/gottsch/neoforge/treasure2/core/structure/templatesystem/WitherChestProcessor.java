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
import mod.gottsch.neoforge.treasure2.core.block.StandardChestBlock;
import mod.gottsch.neoforge.treasure2.core.cache.TreasureChestCache;
import mod.gottsch.neoforge.treasure2.core.cache.data.TreasureChestCacheData;
import mod.gottsch.neoforge.treasure2.core.rarity.IRarity;
import mod.gottsch.neoforge.treasure2.core.rarity.RarityWeightsManager;
import mod.gottsch.neoforge.treasure2.core.rarity.TreasureRarities;
import mod.gottsch.neoforge.treasure2.core.registry.ChestSubprocessorDataRegistry;
import mod.gottsch.neoforge.treasure2.core.structure.templatesystem.chest.IChestSubprocessor;
import mod.gottsch.neoforge.treasure2.core.structure.templatesystem.chest.TreasureChestSubprocessors;
import mod.gottsch.neoforge.treasure2.core.structure.templatesystem.data.ChestSubprocessorData;
import mod.gottsch.neoforge.treasure2.core.util.ModUtil;
import mod.gottsch.neoforge.treasure2.core.world.feature.IFeatureType;
import mod.gottsch.neoforge.treasure2.core.world.feature.TreasureFeatureTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.Optional;

/**
 *
 * @author by Mark Gottschling on 8/15/2025
 */
public class WitherChestProcessor extends TreasureChestProcessor {

    public static final MapCodec<WitherChestProcessor> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            IFeatureType.CODEC.fieldOf("feature_type").forGetter(TreasureChestProcessor::getFeatureType),
            ResourceLocation.CODEC.optionalFieldOf("dimension", ModUtil.asLocation("minecraft:overworld")).forGetter(TreasureChestProcessor::getDimension),
            ResourceLocation.CODEC.optionalFieldOf("loot_table").forGetter(VanillaChestProcessor::getLootTable)
    ).apply(instance, WitherChestProcessor::new));


    public WitherChestProcessor(IFeatureType featureType) {
        super(featureType);
    }

    public WitherChestProcessor(IFeatureType featureType, ResourceLocation dimension, Optional<ResourceLocation> lootTable) {
        super(featureType, dimension, lootTable);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModProcessors.WITHER_CHEST_PROCESSOR.get();
    }

    @Override
    public StructureTemplate.StructureBlockInfo buildTreasureChest(LevelReader levelReader, BlockState state, BlockPos pos, StructurePlaceSettings placeSettings) {

        // 1. wither chests are always terranean
        IFeatureType processedFeatureType = TreasureFeatureTypes.TERRANEAN.get();

        // 2. wither chests are always wither rarity
        IRarity rarityEntry = TreasureRarities.WITHER.get();
        Treasure.LOGGER.debug("rarity -> {}", rarityEntry);

        // 3. get chest subprocessor data.
        Optional<ChestSubprocessorData> dataOptional = ChestSubprocessorDataRegistry.getAssociation(processedFeatureType, rarityEntry);
        ChestSubprocessorData data = dataOptional.orElseGet(() -> {
            Treasure.LOGGER.warn("unable to locate chest subprocessor data for feature type -> {} and rarity -> {}, reverting to default subprocessor data.", processedFeatureType, rarityEntry.getName());
            // TODO this is going to be null; supply real default subprocessor data
            return TreasureChestSubprocessors.standard().getData();
        });

        // 4. wither chests always use the wither subprocessor.
        IChestSubprocessor subprocessor = TreasureChestSubprocessors.wither();

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
            chestSpawn.setFeatureType(getFeatureType());
            chestSpawn.setRarity(rarityEntry);
            chestSpawn.setDiscovered(false);
            TreasureChestCache.cache(chestSpawn);
            Treasure.LOGGER.debug("caching chest at pos -> {}", info.pos());

            // increment rarity weighted collection
            RarityWeightsManager.adjustAllWeightsExcept(getFeatureType(), 1, rarityEntry);

            return info;
        }).orElseGet(() -> {
            Treasure.LOGGER.warn("unable to generate StructureBlockInfo for processor type -> {}, reverting to default chest", data.getType());
            return subprocessor.defaultChest(levelReader, state, pos, placeSettings);
        });
    }
}
