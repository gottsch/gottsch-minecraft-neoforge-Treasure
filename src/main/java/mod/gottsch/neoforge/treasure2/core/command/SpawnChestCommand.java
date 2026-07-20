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
package mod.gottsch.neoforge.treasure2.core.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import mod.gottsch.neo.gottschcore.spatial.Heading;
import mod.gottsch.neo.gottschcore.spatial.Rotate;
import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.api.TreasureApi;
import mod.gottsch.neoforge.treasure2.core.block.AbstractTreasureChestBlock;
import mod.gottsch.neoforge.treasure2.core.block.ITreasureChestBlock;
import mod.gottsch.neoforge.treasure2.core.block.StandardChestBlock;
import mod.gottsch.neoforge.treasure2.core.block.TreasureBlocks;
import mod.gottsch.neoforge.treasure2.core.block.entity.AbstractTreasureChestBlockEntity;
import mod.gottsch.neoforge.treasure2.core.block.entity.GenerationContext;
import mod.gottsch.neoforge.treasure2.core.generator.chest.ChestGenerationHelper;
import mod.gottsch.neoforge.treasure2.core.rarity.IRarity;
import mod.gottsch.neoforge.treasure2.core.rarity.TreasureRarities;
import mod.gottsch.neoforge.treasure2.core.item.LockItem;
import mod.gottsch.neoforge.treasure2.core.lock.LockLayout;
import mod.gottsch.neoforge.treasure2.core.lock.LockState;
import mod.gottsch.neoforge.treasure2.core.registry.MimicRegistry;
import mod.gottsch.neoforge.treasure2.core.registry.ChestSubprocessorDataRegistry;
import mod.gottsch.neoforge.treasure2.core.registry.RarityTagAssociationRegistry;
import mod.gottsch.neoforge.treasure2.core.structure.templatesystem.chest.IChestSubprocessor;
import mod.gottsch.neoforge.treasure2.core.structure.templatesystem.chest.TreasureChestSubprocessors;
import mod.gottsch.neoforge.treasure2.core.structure.templatesystem.data.ChestSubprocessorData;
import mod.gottsch.neoforge.treasure2.core.world.feature.TreasureFeatureTypes;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Mark Gottschling on Aug 28, 2020
 *
 */
public class SpawnChestCommand {

    private static final String T2 = "t2";
    private static final String CHEST = "chest";
    private static final String NAME = "name";
    private static final String LOCKED = "locked";
    private static final String SEALED = "sealed";
    private static final String MIMIC = "mimic";
    private static final String POS = "pos";
    private static final String RARITY = "rarity";
    private static final String DIRECTION = "direction";

    private static final SuggestionProvider<CommandSourceStack> SUGGEST_RARITY = (source, builder) ->
            SharedSuggestionProvider.suggest(TreasureApi.getRarities()
                    .stream()
                    .map(r -> r.getName().toUpperCase()), builder);

    private static final SuggestionProvider<CommandSourceStack> SUGGEST_CHEST = (source, builder) -> {
        List<DeferredBlock<Block>> chests = List.of(
                TreasureBlocks.WOOD_CHEST,
                TreasureBlocks.CRATE_CHEST,
                TreasureBlocks.MOLDY_CRATE_CHEST,
                TreasureBlocks.IRONBOUND_CHEST,
                TreasureBlocks.PIRATE_CHEST,
                TreasureBlocks.SAFE,
                TreasureBlocks.IRON_STRONGBOX,
                TreasureBlocks.GOLD_STRONGBOX,
                TreasureBlocks.DREAD_PIRATE_CHEST,
                TreasureBlocks.COMPRESSOR_CHEST,
                TreasureBlocks.SKULL_CHEST,
                TreasureBlocks.GOLD_SKULL_CHEST,
                TreasureBlocks.CRYSTAL_SKULL_CHEST,
                TreasureBlocks.CAULDRON_CHEST,
                TreasureBlocks.SPIDER_CHEST,
                TreasureBlocks.VIKING_CHEST,
                TreasureBlocks.CARDBOARD_BOX,
                TreasureBlocks.MILK_CRATE,
                TreasureBlocks.BARREL_CHEST,
                TreasureBlocks.VANILLA_CHEST,
                TreasureBlocks.WITHER_CHEST,
                TreasureBlocks.BONE_CHEST
        );
        return SharedSuggestionProvider.suggest(chests.stream().map(c -> c.getId().toString()), builder);
    };

    private static final SuggestionProvider<CommandSourceStack> SUGGEST_DIRECTION = (source, builder) ->
            SharedSuggestionProvider.suggest(Heading.getNames().stream()
                    .filter(x -> !x.equalsIgnoreCase("UP") && !x.equalsIgnoreCase("DOWN")), builder);

    private static final SuggestionProvider<CommandSourceStack> SUGGEST_MIMIC = (source, builder) ->
            SharedSuggestionProvider.suggest(MimicRegistry.getMimics().stream().map(ResourceLocation::toString), builder);

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> baseCommand = Commands.literal(T2)
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal(CHEST)
                        .then(Commands.argument(POS, BlockPosArgument.blockPos())
                                .executes(context -> spawn(context, false, false, false))
                                .then(Commands.argument(NAME, ResourceLocationArgument.id())
                                        .suggests(SUGGEST_CHEST)
                                        .executes(context -> spawn(context, false, false, false))
                                        .then(Commands.argument(RARITY, StringArgumentType.string())
                                                .suggests(SUGGEST_RARITY)
                                                .executes(context -> spawn(context, false, false, false))
                                                .then(Commands.argument(DIRECTION, StringArgumentType.string())
                                                        .suggests(SUGGEST_DIRECTION)
                                                        .executes(context -> spawn(context, false, false, false))
                                                        .then(recursiveBooleanArgumentBuilder(
                                                                Commands.literal(LOCKED),
                                                                List.of(SEALED, MIMIC),
                                                                true, false, false))
                                                        .then(recursiveBooleanArgumentBuilder(
                                                                Commands.literal(SEALED),
                                                                List.of(LOCKED, MIMIC),
                                                                false, true, false))
                                                        .then(recursiveBooleanArgumentBuilder(
                                                                Commands.literal(MIMIC),
                                                                List.of(LOCKED, SEALED),
                                                                false, false, true))
                                                )
                                        )
                                )
                        )
                );

        dispatcher.register(baseCommand);
    }

    private static LiteralArgumentBuilder<CommandSourceStack> recursiveBooleanArgumentBuilder(
            LiteralArgumentBuilder<CommandSourceStack> literalBuilder,
            List<String> remainingOptions, boolean locked, boolean sealed, boolean mimic) {

        literalBuilder.executes(context -> spawn(context, locked, sealed, mimic));

        for (String option : remainingOptions) {
            LiteralArgumentBuilder<CommandSourceStack> newBranch = Commands.literal(option);
            newBranch.executes(context -> spawn(context,
                    LOCKED.equals(option) || locked,
                    SEALED.equals(option) || sealed,
                    MIMIC.equals(option) || mimic));

            List<String> nextOptions = new ArrayList<>(remainingOptions);
            nextOptions.remove(option);
            recursiveBooleanArgumentBuilder(newBranch, nextOptions,
                    LOCKED.equals(option) || locked,
                    SEALED.equals(option) || sealed,
                    MIMIC.equals(option) || mimic);

            literalBuilder.then(newBranch);
        }

        return literalBuilder;
    }

    private static int spawn(CommandContext<CommandSourceStack> context, boolean locked, boolean sealed, boolean mimic) {
        try {
            BlockPos pos = BlockPosArgument.getLoadedBlockPos(context, POS);
            String chestName = "";
            String rarityName = TreasureRarities.UNKNOWN.get().getName();
            String directionName = Heading.SOUTH.name();

            try { chestName = ResourceLocationArgument.getId(context, NAME).toString(); } catch (IllegalArgumentException ignored) {}
            try { rarityName = StringArgumentType.getString(context, RARITY); } catch (IllegalArgumentException ignored) {}
            try { directionName = StringArgumentType.getString(context, DIRECTION); } catch (IllegalArgumentException ignored) {}

            Treasure.LOGGER.debug("executing spawn chest, pos -> {}, name -> {}, rarity -> {}, locked -> {}, sealed -> {}, mimic -> {}",
                    pos, chestName, rarityName, locked, sealed, mimic);

            ServerLevel level = context.getSource().getLevel();
            RandomSource random = level.getRandom();

            Heading heading = Heading.valueOf(directionName.isEmpty() ? Heading.SOUTH.name() : directionName);
            Direction direction = heading.getDirection();
            IRarity rarity = TreasureRarities.getRarityByName(asLocation(rarityName.trim().toLowerCase()))
                    .orElseGet(TreasureRarities.COMMON::get);

            // resolve chest block — fall back to wood chest if name is missing or not an AbstractTreasureChestBlock
            final String resolvedChestName = chestName;
            AbstractTreasureChestBlock chest = (chestName.isEmpty()
                    ? java.util.Optional.<Block>empty()
                    : BuiltInRegistries.BLOCK.getOptional(asLocation(resolvedChestName)))
                    .filter(b -> b instanceof AbstractTreasureChestBlock)
                    .map(b -> (AbstractTreasureChestBlock) b)
                    .orElseGet(() -> {
                        if (!resolvedChestName.isEmpty()) {
                            Treasure.LOGGER.warn("unable to locate a treasure chest with name -> {}. Falling back to wood chest.", resolvedChestName);
                        }
                        return (AbstractTreasureChestBlock) TreasureBlocks.WOOD_CHEST.get();
                    });

            // place the block
            FluidState fluidState = level.getBlockState(pos).getFluidState();
            BlockState chestState = chest.defaultBlockState()
                    .setValue(StandardChestBlock.FACING, direction)
                    .setValue(ITreasureChestBlock.DISCOVERED, false)
                    .setValue(AbstractTreasureChestBlock.WATERLOGGED, fluidState.getType() == Fluids.WATER);
            level.setBlock(pos, chestState, 3);

            // initialize the block entity via direct setters
            if (level.getBlockEntity(pos) instanceof AbstractTreasureChestBlockEntity chestEntity) {
                chestEntity.setFacing(direction);
                // a locked chest must also be sealed, otherwise its loot never rolls (createMenu
                // only fills sealed chests on first open).
                boolean sealChest = sealed || locked;
                chestEntity.setSealed(sealChest);
                // sealed/locked chests need a loot table to roll on open; a mimic needs one so the
                // spawned mob drops the chest's contents when killed.
                if (sealChest || mimic) {
                    ChestGenerationHelper.randomLootTable(random, rarity)
                            .ifPresent(chestEntity::setLootTable);
                }
                // give sealed chests a generation context with a real rarity: createMenu reads
                // getGenerationContext().getLootRarity() and passes it to fillChest, which would NPE
                // on the default context's null rarity (e.g. addTreasureMap's rarity.getName()).
                if (sealChest) {
                    chestEntity.setGenerationContext(new GenerationContext(rarity, TreasureFeatureTypes.TERRANEAN.get()));
                }
                if (mimic) {
                    ResourceLocation chestKey = BuiltInRegistries.BLOCK.getKey(chest);
                    java.util.Optional<ResourceLocation> mimicName = MimicRegistry.getMimic(chestKey);
                    if (mimicName.isPresent()) {
                        chestEntity.setMimic(mimicName.get());
                        Treasure.LOGGER.debug("set mimic {} on chest {}", mimicName.get(), chestKey);
                    } else {
                        Treasure.LOGGER.warn("no mimic is registered for chest '{}' - it will open as a normal chest", chestKey);
                    }
                }
                // add locks if requested. Mirror worldgen lock-tier selection: a chest's subprocessor
                // data maps high/special tiers to lower lock-rarities via lock_rarities (e.g.
                // crystal_skull -> rare/epic), since those tiers have no lock items of their own.
                // Fall back to the chest's own rarity if there's no subprocessor data. Each slot is
                // rotated NORTH -> facing so the locks render on the correct face.
                if (locked) {
                    LockLayout layout = chest.getLockLayout();
                    Rotate rotate = Heading.NORTH.getRotation(heading);

                    List<ResourceLocation> lockRarityIds = ChestSubprocessorDataRegistry
                            .getAssociation(TreasureFeatureTypes.TERRANEAN.get(), rarity)
                            .map(ChestSubprocessorData::getLockRarities)
                            .filter(ids -> !ids.isEmpty())
                            .orElse(List.of());

                    List<IRarity> lockRarities = lockRarityIds.isEmpty()
                            ? List.of(rarity)
                            : lockRarityIds.stream()
                                    .map(id -> TreasureRarities.getRarityByName(id, level.registryAccess()))
                                    .flatMap(Optional::stream)
                                    .toList();

                    List<LockItem> lockItems = lockRarities.stream()
                            .flatMap(r -> RarityTagAssociationRegistry.getLockItems(r, level.registryAccess()).stream())
                            .filter(LockItem.class::isInstance)
                            .map(LockItem.class::cast)
                            .toList();

                    if (!lockItems.isEmpty()) {
                        IChestSubprocessor sub = TreasureChestSubprocessors.standard();
                        // an explicit "locked" request should always produce at least one lock
                        int numLocks = Math.max(1, sub.randomizedNumberOfLocks(random, layout));
                        chestEntity.setLockStates(sub.buildLocks(random, layout, lockItems, numLocks, rotate));
                    } else {
                        Treasure.LOGGER.warn("no lock items found for chest '{}' (rarity '{}') - chest will not be locked",
                                BuiltInRegistries.BLOCK.getKey(chest), rarity.getName());
                    }
                }
                // sync the block entity (facing/locks/etc.) to tracking clients so the locks render
                chestEntity.sendUpdates();
            }

            return 1;
        } catch (Exception e) {
            Treasure.LOGGER.error("An error occurred spawning chest: ", e);
            return 0;
        }
    }

    private static ResourceLocation asLocation(String name) {
        return name.contains(":") ? ResourceLocation.parse(name) : ResourceLocation.fromNamespaceAndPath(Treasure.MODID, name);
    }
}
