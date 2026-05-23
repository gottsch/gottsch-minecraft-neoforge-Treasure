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
package mod.gottsch.neoforge.treasure2.core.rarity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mod.gottsch.neoforge.treasure2.Treasure;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

/**
 *
 * represents a single rarity entry as defined in a data pack.
 * @author by Mark Gottschling on 8/25/2025
 */
public class Rarity implements IRarity {


    // a Codec to parse this data from a JSON file.
//    public static final Codec<Rarity> CODEC = RecordCodecBuilder.create(instance -> instance.group(
//            Codec.STRING.fieldOf("name").forGetter(IRarity::getName),
//            Codec.INT.fieldOf("order").forGetter(IRarity::getOrder)
//    ).apply(instance, Rarity::new));
//
//    public static final StreamCodec<RegistryFriendlyByteBuf, Rarity> STREAM_CODEC = StreamCodec.composite(
//            ByteBufCodecs.STRING_UTF8, IRarity::getName,
//            ByteBufCodecs.INT, IRarity::getOrder,
//            Rarity::new
//    );

    public static final IRarity NONE = new Rarity("none", 0);

    // registry ID. all concrete implementation of IRarity must be registered to Treasure2 custom registry RARITY_REGISTRY,
    // so using the Treasure.MODID namespace for the ID ResourceLocation is safe.
    private final ResourceLocation ID;

    // the name of the rarity, used for display.
    private final String name;
    private final int order;

    /**
     *
     * @param name the name of the Rarity ex common
     */
    public Rarity(String name, int order) {
        this.name = name.trim().toLowerCase();
        this.order = order;
        ID = ResourceLocation.fromNamespaceAndPath(Treasure.MODID, name);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public ResourceLocation getRegistryId() {
        return ID;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Rarity that = (Rarity) o;
        return order == that.order && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, order);
    }

    @Override
    public String toString() {
        return "RarityEntry {" +
                "name='" + name + '\'' +
                ", order=" + order +
                '}';
    }
}
