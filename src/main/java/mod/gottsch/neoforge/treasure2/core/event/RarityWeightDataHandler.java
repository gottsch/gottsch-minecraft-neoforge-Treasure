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
package mod.gottsch.neoforge.treasure2.core.event;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.core.rarity.RarityWeightSet;
import mod.gottsch.neoforge.treasure2.core.rarity.RarityWeightsManager;
import mod.gottsch.neoforge.treasure2.core.rarity.TreasureRarities;
import mod.gottsch.neoforge.treasure2.core.world.feature.TreasureFeatureTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Handles loading custom rarity weight data from data packs.
 * @author by Mark Gottschling on 8/26/2025
 */
@EventBusSubscriber(modid = Treasure.MODID, bus = EventBusSubscriber.Bus.GAME)
public class RarityWeightDataHandler extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private static final String DATA_DIRECTORY = "rarity_weight_sets";

    public RarityWeightDataHandler() {
        super(GSON, DATA_DIRECTORY);
    }

    @SubscribeEvent
    public static void onAddReloadListener(AddReloadListenerEvent event) {
        event.addListener(new RarityWeightDataHandler());
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsonElementMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        Treasure.LOGGER.info("loading rarity weights from data packs...");

        RarityWeightsManager.clear();

        // obtain registry access from the running server for rarity lookups
        HolderLookup.Provider registries = ServerLifecycleHooks.getCurrentServer().registryAccess();

        jsonElementMap.forEach((location, jsonElement) ->
                RarityWeightSet.CODEC
                        .parse(JsonOps.INSTANCE, jsonElement)
                        .resultOrPartial(err -> Treasure.LOGGER.error("failed to parse rarity weight JSON for {}: {}", location, err))
                        .ifPresent(rarityWeightSet -> {
                            Path path = Paths.get(location.getPath());
                            String parentKey = path.getName(0).toString().trim().toLowerCase();
                            TreasureFeatureTypes.getFeatureTypeByName(
                                        ResourceLocation.fromNamespaceAndPath(Treasure.MODID, parentKey))
                                    .ifPresent(featureType -> rarityWeightSet.rarityWeights().stream()
                                            .filter(rw -> !rw.getRarity().equals(TreasureRarities.WITHER.getId()))
                                            .forEach(rw -> RarityWeightsManager.register(featureType, rw, registries)));
                        })
        );
        Treasure.LOGGER.info("loaded {} rarity weight sets.", jsonElementMap.size());
    }
}
