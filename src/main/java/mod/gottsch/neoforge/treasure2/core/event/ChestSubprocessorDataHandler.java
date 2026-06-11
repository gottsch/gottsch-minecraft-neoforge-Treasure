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
import mod.gottsch.neoforge.treasure2.core.rarity.IRarity;
import mod.gottsch.neoforge.treasure2.core.rarity.TreasureRarities;
import mod.gottsch.neoforge.treasure2.core.registry.ChestSubprocessorDataRegistry;
import mod.gottsch.neoforge.treasure2.core.structure.templatesystem.data.ChestSubprocessorData;
import mod.gottsch.neoforge.treasure2.core.util.ModUtil;
import mod.gottsch.neoforge.treasure2.core.world.feature.IFeatureType;
import mod.gottsch.neoforge.treasure2.core.world.feature.TreasureFeatureTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Handles loading custom chest subprocessor data from data packs.
 * @author by Mark Gottschling on 8/28/2025
 */
@EventBusSubscriber(modid = Treasure.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ChestSubprocessorDataHandler extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private static final String DATA_DIRECTORY = "subprocessors/chest";

    public ChestSubprocessorDataHandler() {
        super(GSON, DATA_DIRECTORY);
    }

    @SubscribeEvent
    public static void onAddReloadListener(AddReloadListenerEvent event) {
        event.addListener(new ChestSubprocessorDataHandler());
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsonElementMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        Treasure.LOGGER.info("loading chest subprocessors from data packs...");

        ChestSubprocessorDataRegistry.clear();

        jsonElementMap.forEach((location, jsonElement) ->
                ChestSubprocessorData.CODEC
                        .parse(JsonOps.INSTANCE, jsonElement)
                        .resultOrPartial(err -> Treasure.LOGGER.error("failed to parse chest subprocessor JSON for {}: {}", location, err))
                        .ifPresent(data -> {
                            // extract the top-level path key [aquatic|terranean] and map it to a feature type
                            Path path = Paths.get(location.getPath());
                            String parent = path.getName(0).toString().trim().toLowerCase();

                            IFeatureType type = TreasureFeatureTypes.getFeatureTypeByName(ModUtil.asLocation(parent))
                                    .orElseGet(() -> {
                                        Treasure.LOGGER.warn("unable to locate feature type {}, using unknown.", parent);
                                        return TreasureFeatureTypes.UNKNOWN.get();
                                    });

                            IRarity rarity = TreasureRarities.getRarityByName(data.getRarity()).orElseGet(() -> {
                                Treasure.LOGGER.warn("unable to locate rarity {}, using default common.", data.getRarity());
                                return TreasureRarities.COMMON.get();
                            });

                            ChestSubprocessorDataRegistry.register(type, rarity, data);
                        })
        );
        Treasure.LOGGER.info("loaded {} chest subprocessors.", jsonElementMap.size());
    }
}
