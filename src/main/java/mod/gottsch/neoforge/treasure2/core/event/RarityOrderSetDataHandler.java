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
import mod.gottsch.neoforge.treasure2.core.rarity.RarityOrderSet;
import mod.gottsch.neoforge.treasure2.core.registry.RarityOrderRegistry;
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
 * Handles loading custom rarity order set data from data packs.
 * @author by Mark Gottschling on 9/4/2025
 */
@EventBusSubscriber(modid = Treasure.MODID, bus = EventBusSubscriber.Bus.GAME)
public class RarityOrderSetDataHandler extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private static final String DATA_DIRECTORY = "rarity_order_sets";
    private static final String CORE_DIRECTORY = "core";

    public RarityOrderSetDataHandler() {
        super(GSON, DATA_DIRECTORY);
    }

    @SubscribeEvent
    public static void onAddReloadListener(AddReloadListenerEvent event) {
        event.addListener(new RarityOrderSetDataHandler());
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsonElementMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        Treasure.LOGGER.info("loading rarity order sets from data packs...");

        RarityOrderRegistry.clear();

        jsonElementMap.forEach((location, jsonElement) ->
                RarityOrderSet.CODEC
                        .parse(JsonOps.INSTANCE, jsonElement)
                        .resultOrPartial(err -> Treasure.LOGGER.error("failed to parse rarity order set JSON for {}: {}", location, err))
                        .ifPresent(rarityOrderSet -> {
                            Path path = Paths.get(location.getPath());
                            String parentKey = path.getName(0).toString().trim().toLowerCase();
                            if (CORE_DIRECTORY.equalsIgnoreCase(parentKey)) {
                                rarityOrderSet.rarityOrders().forEach(RarityOrderRegistry::registerCore);
                                RarityOrderRegistry.sortCore();
                            }
                            // TODO: specialty orders when implemented
                        })
        );
        Treasure.LOGGER.info("loaded {} rarity order sets.", jsonElementMap.size());
    }
}
