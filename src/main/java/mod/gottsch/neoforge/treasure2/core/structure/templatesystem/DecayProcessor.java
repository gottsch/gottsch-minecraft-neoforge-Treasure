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
import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.core.structure.BlockRotationUtil;
import mod.gottsch.neoforge.treasure2.core.structure.templatesystem.data.BlockDataCache;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Captures the original and processed block data of a structure, by individual piece, into
 * {@link BlockDataCache} for later use (e.g. RuinsStructure.afterPlace decay). Must be placed
 * at the end of the processor list in the JSON definition.
 *
 * @author by Mark Gottschling on 9/9/2025
 */
public class DecayProcessor extends ModProcessor {

    public static final MapCodec<DecayProcessor> CODEC = MapCodec.unit(DecayProcessor::new);

    // property to capture the size of the current piece
    private Vec3i size;

    @Override
    protected StructureProcessorType<?> getType() {
        return ModProcessors.DECAY_PROCESSOR.get();
    }

    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader, BlockPos pos, BlockPos relativePos, StructureTemplate.StructureBlockInfo original, StructureTemplate.StructureBlockInfo current, StructurePlaceSettings placementSettings) {
        // pass through; the capture happens in finalizeProcessing once the piece is fully processed.
        return current;
    }

    @Override
    public @Nullable StructureTemplate.StructureBlockInfo process(LevelReader levelReader, BlockPos piecePos, BlockPos relativePos, StructureTemplate.StructureBlockInfo original, StructureTemplate.StructureBlockInfo current, StructurePlaceSettings placeSettings, @Nullable StructureTemplate template) {
        this.size = template.getSize();
        return super.process(levelReader, piecePos, relativePos, original, current, placeSettings, template);
    }

    /*
     * Called after all other processors have finished for this specific piece.
     */
    @Override
    public List<StructureTemplate.StructureBlockInfo> finalizeProcessing(ServerLevelAccessor levelAccessor, BlockPos piecePos, BlockPos originalPos, List<StructureTemplate.StructureBlockInfo> blocks, List<StructureTemplate.StructureBlockInfo> processedBlocks, StructurePlaceSettings placeSettings) {

        // NOTE this class is PRE placement. The blocks appear rotated, but piecePos is the ORIGINAL UNROTATED pos,
        // so the piecePos is rotated (using the template size captured in process()) to compute the correct cache key.
        BlockPos newPiecePos = BlockRotationUtil.transformStartCoords(piecePos, this.size, placeSettings.getRotation());

        // allow to execute once per piece
        if (!hasFinalizeGuard(DECAY, newPiecePos)) {
            addFinalizeGuard(DECAY, newPiecePos);

            Treasure.LOGGER.debug("finalize processing called on piece pos -> {}", newPiecePos);

            // capture original blocks (rotated to absolute world positions)
            Set<StructureTemplate.StructureBlockInfo> currentOriginals = BlockDataCache.getOriginalBlocks(newPiecePos);
            currentOriginals.addAll(blocks.stream()
                    .map(block -> {
                        BlockPos absPos = block.pos().offset(piecePos);
                        BlockPos rotatedPos = BlockRotationUtil.rotateAroundPivot(absPos, piecePos, placeSettings.getRotation());
                        return new StructureTemplate.StructureBlockInfo(rotatedPos, block.state(), block.nbt());
                    })
                    .collect(Collectors.toSet()));
            BlockDataCache.putOriginalBlocks(newPiecePos, currentOriginals);

            // capture processed blocks
            Set<StructureTemplate.StructureBlockInfo> currentProcessed = BlockDataCache.getProcessedBlocks(newPiecePos);
            currentProcessed.addAll(processedBlocks.stream()
                    .map(processedBlock -> new StructureTemplate.StructureBlockInfo(processedBlock.pos(), processedBlock.state(), processedBlock.nbt()))
                    .collect(Collectors.toSet()));
            BlockDataCache.putProcessedBlocks(newPiecePos, currentProcessed);

            return processedBlocks;
        }
        return super.finalizeProcessing(levelAccessor, piecePos, originalPos, blocks, processedBlocks, placeSettings);
    }
}
