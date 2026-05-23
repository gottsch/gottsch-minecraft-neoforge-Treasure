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
import mod.gottsch.neoforge.treasure2.core.mobset.MobSetData;
import mod.gottsch.neoforge.treasure2.core.mobset.MobSetDataRegistry;
import mod.gottsch.neoforge.treasure2.core.mobset.WeightedMob;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author by Mark Gottschling on 9/18/2025
 */
@EventBusSubscriber(modid = Treasure.MODID, bus = EventBusSubscriber.Bus.GAME)
public class MobSetDataHandler extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private static final String DATA_DIRECTORY = "mobsets";

    public MobSetDataHandler() {
        super(GSON, DATA_DIRECTORY);
    }

    @SubscribeEvent
    public static void onAddReloadListener(AddReloadListenerEvent event) {
        event.addListener(new MobSetDataHandler());
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsonElementMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        Treasure.LOGGER.info("loading mobsets from data packs...");

        jsonElementMap.forEach((location, jsonElement) ->
                MobSetData.CODEC
                        .parse(JsonOps.INSTANCE, jsonElement)
                        .resultOrPartial(err -> Treasure.LOGGER.error("failed to parse mobset data JSON for {}: {}", location, err))
                        .ifPresent(newData -> {
                            MobSetDataRegistry.get(newData.getId())
                                    .ifPresentOrElse(
                                            existingData -> {
                                                MobSetData finalData = existingData.isReplace()
                                                        ? newData
                                                        : mergeMobSetData(existingData, newData);
                                                MobSetDataRegistry.register(finalData);
                                            },
                                            () -> MobSetDataRegistry.register(newData)
                                    );
                            Treasure.LOGGER.debug("registered mobset data -> {}", newData.getId());
                        })
        );
    }

    private MobSetData mergeMobSetData(MobSetData existingData, MobSetData newData) {
        MobSetData result = existingData.withCount(newData.getCount());

        Map<ResourceLocation, WeightedMob> mergedMobs = result.getMobs().stream()
                .collect(Collectors.toMap(
                        WeightedMob::id,
                        Function.identity(),
                        (oldMob, newMob) -> oldMob
                ));

        newData.getMobs().forEach(newMob ->
                mergedMobs.compute(newMob.getId(), (mobId, existingMob) ->
                        existingMob != null ? existingMob.withWeight(newMob.getWeight()) : newMob
                )
        );

        return result.withMobs(mergedMobs.values().stream().toList());
    }
}
