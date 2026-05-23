package mod.gottsch.neoforge.treasure2.core.world.feature;

import mod.gottsch.neoforge.treasure2.Treasure;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;

/**
 * @author by Mark Gottschling on 11/29/2025
 */
public class TreasureFeatureTypes {
    // a unique identifier for your new registry.
    // this is what you will reference in data packs.
    public static final ResourceKey<Registry<IFeatureType>> FEATURE_TYPE_REGISTRY_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Treasure.MODID, "feature_types"));

    // this is the DeferredRegister instance for your custom registry.
    public static final DeferredRegister<IFeatureType> FEATURE_TYPE_REGISTRY =
            DeferredRegister.create(FEATURE_TYPE_REGISTRY_KEY, Treasure.MODID);

    /*
     * register all the feature types
     */
    // core feature types
    public static final DeferredHolder<IFeatureType, IFeatureType> UNKNOWN = FEATURE_TYPE_REGISTRY.register("unknown",
            () -> new FeatureType("unknown", -1));

    public static final DeferredHolder<IFeatureType, IFeatureType> AQUATIC = FEATURE_TYPE_REGISTRY.register("aquatic",
            () -> new FeatureType("aquatic", 0));

    public static final DeferredHolder<IFeatureType, IFeatureType> TERRANEAN = FEATURE_TYPE_REGISTRY.register("terranean",
            () -> new FeatureType("terranean", 1));

    /*
     *
     */
    public static Optional<IFeatureType> getFeatureTypeByName(ResourceLocation id) {
        return Optional.ofNullable(FEATURE_TYPE_REGISTRY.getRegistry().get().get(id));
    }

    public static void register(IEventBus modEventBus) {
        FEATURE_TYPE_REGISTRY.register(modEventBus);
    }
}
