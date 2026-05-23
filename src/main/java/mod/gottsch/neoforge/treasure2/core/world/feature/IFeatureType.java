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

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.core.rarity.IRarity;
import mod.gottsch.neoforge.treasure2.core.rarity.TreasureRarities;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import org.codehaus.plexus.util.StringUtils;

/**
 * 
 * @author Mark Gottschling May 12, 2023
 *
 */
public interface IFeatureType {

    Codec<IFeatureType> CODEC = ResourceLocation.CODEC.fieldOf("id")
            .flatXmap(
                    // Decoder: ResourceLocation -> DataResult<IRarity>
                    id ->
                            TreasureFeatureTypes.getFeatureTypeByName(id)
                                    .map(DataResult::success)
                                    .orElseGet(() -> {
                                        Treasure.LOGGER.warn("feature type not registered -> {}", id);
                                        // return as success() so that a rarity is always returned
                                        return DataResult.success(TreasureFeatureTypes.UNKNOWN.get());
                                    }),

                    // Encoder: IFeatureType -> DataResult<ResourceLocation> (Cannot fail)
                    rarity -> DataResult.success(rarity.getRegistryId())
            )
            .codec(); // convert the MapCodec (fieldOf) back to a regular Codec

    /**
     * A StreamCodec that converts the ResourceLocation of the IFeatureType
     * into the singleton instance and vice-versa.
     */
    public static final StreamCodec<FriendlyByteBuf, IFeatureType> STREAM_CODEC =
            StreamCodec.composite(
                    // 1. Writer: writes the ResourceLocation ID of the IRarity instance
                    ResourceLocation.STREAM_CODEC,
                    IFeatureType::getRegistryId,

                    // 2. Reader:reads the ResourceLocation and looks up the singleton instance
                    id -> TreasureFeatureTypes.getFeatureTypeByName(id)
                            .orElseThrow(() -> new IllegalStateException("feature type not registered for ID: " + id))
            );

    ResourceLocation getRegistryId();

    public String getName();

    default public String getDisplayName() {
        return StringUtils.capitaliseAllWords(getName().replace("_", ""));
    }

    int getCode();
}
