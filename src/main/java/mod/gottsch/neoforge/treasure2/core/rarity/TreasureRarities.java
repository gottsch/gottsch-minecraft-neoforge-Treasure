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

import mod.gottsch.neoforge.treasure2.Treasure;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author by Mark Gottschling on 8/25/2025
 */
public class TreasureRarities {
    // a unique identifier for your new registry.
    // this is what you will reference in data packs.
    public static final ResourceKey<Registry<IRarity>> RARITIES_REGISTRY_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Treasure.MODID, "rarities"));

    // this is the DeferredRegister instance for your custom registry.
    public static final DeferredRegister<IRarity> RARITY_REGISTRY =
            DeferredRegister.create(RARITIES_REGISTRY_KEY, Treasure.MODID);

    // this is the related Registry for the DeferredRegistry
//    public static final Supplier<Registry<IRarity>> RARITIES_REGISTRY_SUPPLIER = RARITY_REGISTRY.makeRegistry(
//            // must include hasTags() to enable tag support
//            () -> new RegistryBuilder<IRarity>(RARITIES_REGISTRY_KEY)
//    );


    /*
     * register all the rarities
     */
    // core rarities
    public static final DeferredHolder<IRarity, IRarity> UNKNOWN = RARITY_REGISTRY.register("unknown",
            () -> new Rarity("unknown", -1));
    public static final DeferredHolder<IRarity, IRarity> COMMON = RARITY_REGISTRY.register("common",
            () -> new Rarity("common", 0));
    public static final DeferredHolder<IRarity, IRarity> UNCOMMON = RARITY_REGISTRY.register("uncommon",
            () -> new Rarity("uncommon", 1));
    public static final DeferredHolder<IRarity, IRarity> SCARCE = RARITY_REGISTRY.register("scarce",
            () -> new Rarity("scarce", 2));
    public static final DeferredHolder<IRarity, IRarity> RARE = RARITY_REGISTRY.register("rare",
            () -> new Rarity("rare", 3));
    public static final DeferredHolder<IRarity, IRarity> EPIC = RARITY_REGISTRY.register("epic",
            () -> new Rarity("epic", 4));
    public static final DeferredHolder<IRarity, IRarity> LEGENDARY = RARITY_REGISTRY.register("legendary",
            () -> new Rarity("legendary", 5));
    public static final DeferredHolder<IRarity, IRarity> MYTHICAL = RARITY_REGISTRY.register("mythical",
            () -> new Rarity("mythical", 6));

    // special rarities
    public static DeferredHolder<IRarity, IRarity> SKULL = RARITY_REGISTRY.register("skull", () -> new Rarity("skull", 2) {
        @Override
        public String getDisplayName() {
            return SCARCE.get().getDisplayName();
        }
    });
    public static DeferredHolder<IRarity, IRarity> GOLD_SKULL = RARITY_REGISTRY.register("gold_skull", () -> new Rarity("gold_skull", 3) {
        @Override
        public String getDisplayName() {
            return RARE.get().getDisplayName();
        }
    });
    public static DeferredHolder<IRarity, IRarity> CRYSTAL_SKULL = RARITY_REGISTRY.register("crystal_skull", () -> new Rarity("crystal_skull", 4) {
        @Override
        public String getDisplayName() {
            return EPIC.get().getDisplayName();
        }
    });
    public static DeferredHolder<IRarity, IRarity> CAULDRON = RARITY_REGISTRY.register("cauldron", () -> new Rarity("cauldron", 4) {
        @Override
        public String getDisplayName() {
            return EPIC.get().getDisplayName();
        }
    });
    public static DeferredHolder<IRarity, IRarity> WITHER = RARITY_REGISTRY.register("wither", () -> new Rarity("wither", 2) {
        @Override
        public String getDisplayName() {
            return SCARCE.get().getDisplayName();
        }
    });
    public static DeferredHolder<IRarity, IRarity> BONE = RARITY_REGISTRY.register("bone", () -> new Rarity("bone", 2) {
        @Override
        public String getDisplayName() {
            return SCARCE.get().getDisplayName();
        }
    });

    static {
        // order rarities ???
    }

    public static void register(IEventBus eventBus) {
        RARITY_REGISTRY.register(eventBus);
    }

    public static List<IRarity> getRarities() {
        return TreasureRarities.RARITY_REGISTRY.getEntries().stream()
                .map(holder -> (IRarity)holder.get())
                .toList();
    }

    /**
     * retrieves a custom IRarityEntry from the registry using its ResourceLocation (name).
      *
     * @param name the ResourceLocation identifier of the rarity entry.
     * @param lookupProvider the HolderLookup.Provider, typically obtained from the world (level.registryAccess()) or Minecraft client.
     * @return an Optional containing the IRarityEntry if found, otherwise empty.
     */
    @Deprecated
    public static Optional<IRarity> getRarityByName(ResourceLocation name, HolderLookup.Provider lookupProvider) {
        // 1. get the Optional of the registry lookup object.
        Optional<HolderLookup.RegistryLookup<IRarity>> rarityLookup = lookupProvider.lookup(RARITIES_REGISTRY_KEY);

        if (rarityLookup.isPresent()) {
            // 2. create the ResourceKey for the specific entry using the registry key and the name.
            ResourceKey<IRarity> entryKey = ResourceKey.create(RARITIES_REGISTRY_KEY, name);

            // 3. use the get(ResourceKey) method, which returns the entry or null if not found.
            Optional<Holder.Reference<IRarity>> rarity = rarityLookup.get().get(entryKey);

            // 4. use map(Holder::value) to safely extract the raw IRarity object if the Holder is present.
            return rarity.map(Holder::value);
        }

        return Optional.empty();
    }

    public static Optional<IRarity> getRarityByName(ResourceLocation name) {
        Optional<IRarity> rarity = Optional.ofNullable(RARITY_REGISTRY.getRegistry().get().get(name));
        return rarity;
    }

    // server convenience method
    public static Optional<IRarity> getRarityByName(ResourceLocation name, ServerLevel level) {
        return getRarityByName(name, level.registryAccess());
    }

    /**
     * retrieves the ResourceLocation (ID) of a given IRarityEntry instance.
     * this requires a runtime context (HolderLookup.Provider).
     *
     * @param rarity the specific IRarityEntry instance to check.
     * @param lookupProvider the world/client registry access provider.
     * @return an Optional containing the ResourceLocation if found.
     */
    public static Optional<ResourceLocation> getKey(IRarity rarity, HolderLookup.Provider lookupProvider) {
        Optional<HolderLookup.RegistryLookup<IRarity>> rarityLookup = lookupProvider.lookup(RARITIES_REGISTRY_KEY);

        if (rarityLookup.isEmpty()) {
            return Optional.empty();
        }

        // 1. stream all registered elements (Holders).
        return rarityLookup.get().listElements()
                // 2. filter to find the Holder whose value matches the input rarity object instance.
                .filter(holder -> holder.value().equals(rarity))
                // 3. extract the ResourceKey from the matching Holder
                .map(holder -> ((Holder.Reference<IRarity>) holder).getKey())
                .filter(Objects::nonNull)
                // 4. convert the ResourceKey to the ResourceLocation.
                .map(ResourceKey::location)
                // 5. take the first (and only) result.
                .findFirst();
    }

//    /**
//     * a helper method to check if a Rarity object is in a given tag.
//     * this is similar to how you would check if an Item is in an ItemTag.
//     * @param object The Rarity RegistryObject to check.
//     * @param tagKey The TagKey to check against.
//     * @return True if the Rarity is in the tag, false otherwise.
//     */
//    public static boolean isInTag(RegistryObject<IRarity> object, TagKey<IRarity> tagKey) {
//        // we get the registry from the supplier, then check the object's Holder.
//        return RARITIES_REGISTRY_SUPPLIER.get().getHolder(object.getKey()).filter(holder -> holder.is(tagKey)).isPresent();
//    }

    /**
     * retrieves all TagKeys that the given IRarity instance belongs to.
     * this requires a runtime context (HolderLookup.Provider).
     * @param rarity the specific IRarity to check
     * @param lookupProvider the world/client registry access provider.
     * @return a List of TagKey<IRarity> that the entry is a member of.
     */
    public static List<TagKey<IRarity>> getTagsByRarity(IRarity rarity, HolderLookup.Provider lookupProvider) {
        Optional<ResourceLocation> key = getKey(rarity, lookupProvider);

        if (key.isEmpty()) {
            return List.of();
        }

        return getTagsByRarity(key.get(), lookupProvider);
    }

        /**
         * retrieves all TagKeys that the given IRarity ID belongs to.
         * this requires a runtime context (HolderLookup.Provider).
         *
         * @param rarityId the specific IRarity ID to check.
         * @param lookupProvider the world/client registry access provider.
         * @return a List of TagKey<IRarity> that the entry is a member of.
         */
    public static List<TagKey<IRarity>> getTagsByRarity(ResourceLocation rarityId, HolderLookup.Provider lookupProvider) {

        // 1. get the specific registry lookup from the provider.
        Optional<HolderLookup.RegistryLookup<IRarity>> rarityLookup = lookupProvider.lookup(RARITIES_REGISTRY_KEY);

        if (rarityLookup.isEmpty()) {
            return List.of();
        }

        // 2. create the ResourceKey using the registry key and the resource name / id.
        ResourceKey<IRarity> entryKey = ResourceKey.create(RARITIES_REGISTRY_KEY, rarityId);

        // 3. use the get(ResourceKey) method, which returns the entry or null if not found.
        Optional<Holder.Reference<IRarity>> rarityHolder = rarityLookup.get().get(entryKey);

        // 4. if found, stream the tags and collect them into a List.
        return rarityHolder
                .map(Holder::tags)
                .orElseGet(Stream::empty)
                .toList();
    }
}
