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
import mod.gottsch.neo.gottschcore.random.RandomHelper;
import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.core.block.StandardChestBlock;
import mod.gottsch.neoforge.treasure2.core.block.TreasureBlocks;
import mod.gottsch.neoforge.treasure2.core.block.entity.AbstractTreasureChestBlockEntity;
import mod.gottsch.neoforge.treasure2.core.config.Config;
import mod.gottsch.neoforge.treasure2.core.registry.MimicRegistry;
import mod.gottsch.neoforge.treasure2.core.util.ModUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.component.SeededContainerLoot;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.Optional;

/**
 * Handles vanilla chests ({@link Blocks#CHEST}) embedded in Treasure structures: optionally converts
 * them to a Treasure mimic (vanilla chest skin), otherwise leaves the vanilla chest with a loot table.
 *
 * <p>NeoForge 1.21.1 port note: the vanilla container loot table is now the
 * {@link DataComponents#CONTAINER_LOOT} data component (the Forge {@code "LootTable"} NBT string is
 * gone), so it is written into the block info's {@code "components"} tag, which vanilla
 * {@code loadWithComponents} applies at placement.
 *
 * @author by Mark Gottschling on 8/27/2025
 */
public class VanillaChestProcessor extends ModProcessor {

    protected Optional<ResourceLocation> lootTable;

    public static final MapCodec<VanillaChestProcessor> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ResourceLocation.CODEC.optionalFieldOf("loot_table").forGetter(VanillaChestProcessor::getLootTable)
    ).apply(instance, VanillaChestProcessor::new));

    public VanillaChestProcessor(Optional<ResourceLocation> lootTable) {
        this.lootTable = lootTable;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModProcessors.VANILLA_CHEST_PROCESSOR.get();
    }

    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader, BlockPos pos, BlockPos relativePos, StructureTemplate.StructureBlockInfo original, StructureTemplate.StructureBlockInfo current, StructurePlaceSettings placementSettings) {

        // if the block is a vanilla chest, process it; otherwise pass through.
        if (current.state().is(Blocks.CHEST)) {
            ResourceLocation lootTable = getLootTable().orElse(BuiltInLootTables.SIMPLE_DUNGEON.location());
            return buildVanillaChest(levelReader.registryAccess(), placementSettings.getRandom(current.pos()), current.state(), current.pos(), lootTable);
        }

        return current;
    }

    public StructureTemplate.StructureBlockInfo buildVanillaChest(HolderLookup.Provider provider, RandomSource random, BlockState state, BlockPos pos, ResourceLocation lootTable) {

        BlockState newState;
        CompoundTag tag = new CompoundTag();

        // add mimic if any
        if (Config.SERVER.mobs.enableMimics.get() && RandomHelper.checkProbability(random, Config.SERVER.mobs.mimicProbability.get())) {
            Direction direction = state.getValue(ChestBlock.FACING).getOpposite();

            // switch to a Treasure2 Vanilla Chest (a Treasure chest block with a vanilla-chest skin)
            newState = TreasureBlocks.VANILLA_CHEST.get().defaultBlockState().setValue(StandardChestBlock.FACING, direction);

            // write the Treasure generation data (mimic + loot table) for the Treasure block entity to load
            CompoundTag genData = new CompoundTag();
            MimicRegistry.getMimic(ModUtil.getName(TreasureBlocks.VANILLA_CHEST.get()))
                    .ifPresent(mimicName -> genData.putString(AbstractTreasureChestBlockEntity.MIMIC_TAG, mimicName.toString()));
            genData.putString(AbstractTreasureChestBlockEntity.LOOT_TABLE_TAG, lootTable.toString());
            tag.put(AbstractTreasureChestBlockEntity.GENERATION_DATA_TAG, genData);

        } else {
            // place vanilla chest, setting its loot table via the vanilla container-loot data component
            newState = state;

            RegistryOps<Tag> ops = provider.createSerializationContext(NbtOps.INSTANCE);
            DataComponentMap components = DataComponentMap.builder()
                    .set(DataComponents.CONTAINER_LOOT, new SeededContainerLoot(ResourceKey.create(Registries.LOOT_TABLE, lootTable), random.nextLong()))
                    .build();
            DataComponentMap.CODEC.encodeStart(ops, components)
                    .resultOrPartial(err -> Treasure.LOGGER.warn("unable to encode vanilla chest loot component -> {}", err))
                    .ifPresent(encoded -> tag.put("components", encoded));
        }

        return new StructureTemplate.StructureBlockInfo(pos, newState, tag);
    }

    public Optional<ResourceLocation> getLootTable() {
        return lootTable;
    }
}
