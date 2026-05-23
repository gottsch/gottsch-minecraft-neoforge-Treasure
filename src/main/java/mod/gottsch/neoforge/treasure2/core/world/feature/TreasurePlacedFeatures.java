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
package mod.gottsch.neoforge.treasure2.core.world.feature;

import mod.gottsch.neoforge.treasure2.Treasure;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

/**
 * @author Mark Gottschling Jul 7, 2023
 */
public class TreasurePlacedFeatures {
    public static final ResourceKey<PlacedFeature> TOPAZ_PLACED_KEY = createKey("topaz_placed");
    public static final ResourceKey<PlacedFeature> ONYX_PLACED_KEY = createKey("onyx_placed");
    public static final ResourceKey<PlacedFeature> RUBY_PLACED_KEY = createKey("ruby_placed");
    public static final ResourceKey<PlacedFeature> SAPPHIRE_PLACED_KEY = createKey("sapphire_placed");

    /**
     * NOTE: aboveBottom values are offsets — ex -64 + 44 = -20, -64 + 84 = 20
     */
    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, TOPAZ_PLACED_KEY,
                configuredFeatures.getOrThrow(TreasureConfiguredFeatures.OVERWORLD_TOPAZ_ORE_KEY),
                TreasureOrePlacement.commonOrePlacement(1,
                        HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(44), VerticalAnchor.aboveBottom(84))));

        register(context, ONYX_PLACED_KEY,
                configuredFeatures.getOrThrow(TreasureConfiguredFeatures.OVERWORLD_ONYX_ORE_KEY),
                TreasureOrePlacement.commonOrePlacement(1,
                        HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(44), VerticalAnchor.aboveBottom(84))));

        register(context, RUBY_PLACED_KEY,
                configuredFeatures.getOrThrow(TreasureConfiguredFeatures.OVERWORLD_RUBY_ORE_KEY),
                TreasureOrePlacement.commonOrePlacement(1,
                        HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(14), VerticalAnchor.aboveBottom(70))));

        register(context, SAPPHIRE_PLACED_KEY,
                configuredFeatures.getOrThrow(TreasureConfiguredFeatures.OVERWORLD_SAPPHIRE_ORE_KEY),
                TreasureOrePlacement.commonOrePlacement(1,
                        HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(14), VerticalAnchor.aboveBottom(70))));
    }

    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(Treasure.MODID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key,
            Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key,
            Holder<ConfiguredFeature<?, ?>> configuration, PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }
}
