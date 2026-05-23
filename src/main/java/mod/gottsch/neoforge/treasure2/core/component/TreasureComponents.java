package mod.gottsch.neoforge.treasure2.core.component;

import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.core.block.entity.GenerationContext;
import mod.gottsch.neoforge.treasure2.core.lock.LockState;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Unit;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * @author by Mark Gottschling on 10/8/2025
 */
public class TreasureComponents {

    public static final DeferredRegister.DataComponents REGISTRAR =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Treasure.MODID);

    // the component stores the actual list of ItemStacks (the inventory contents)
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemContainerContents>> ITEM_INVENTORY =
            REGISTRAR.registerComponentType(
                    "item_inventory",
                    builder -> builder
                            .persistent(ItemContainerContents.CODEC)
                            .networkSynchronized(ItemContainerContents.STREAM_CODEC)
            );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<LockStatesComponent>> LOCK_STATES = REGISTRAR.registerComponentType(
            "lock_states",
            builder -> builder
                    // the codec to read/write the data to disk
                    .persistent(LockStatesComponent.CODEC)
                    // the codec to read/write the data across the network
                    .networkSynchronized(LockStatesComponent.STREAM_CODEC)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SealedComponent>> SEALED = REGISTRAR.registerComponentType(
            "sealed",
            builder -> builder
                    // the codec to read/write the data to disk
                    .persistent(SealedComponent.CODEC)
                    // the codec to read/write the data across the network
                    .networkSynchronized(SealedComponent.STREAM_CODEC)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ResourceLocation>> LOOT_TABLE = REGISTRAR.registerComponentType(
            "loot_table",
            builder -> builder
                    // the codec to read/write the data to disk
                    .persistent(ResourceLocation.CODEC)
                    // the codec to read/write the data across the network
                    .networkSynchronized(ResourceLocation.STREAM_CODEC)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ResourceLocation>> MIMIC = REGISTRAR.registerComponentType(
            "mimic",
            builder -> builder
                    // the codec to read/write the data to disk
                    .persistent(ResourceLocation.CODEC)
                    // the codec to read/write the data across the network
                    .networkSynchronized(ResourceLocation.STREAM_CODEC)
    );

    // custom name
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<CustomNameComponent>> CUSTOM_NAME = REGISTRAR.registerComponentType(
            "custom_name",
            builder -> builder
                    // the codec to read/write the data to disk
                    .persistent(CustomNameComponent.CODEC)
                    // the codec to read/write the data across the network
                    .networkSynchronized(CustomNameComponent.STREAM_CODEC)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<GenerationContext>> GENERATION_CONTEXT = REGISTRAR.registerComponentType(
            "generation_context",
            builder -> builder
                    // the codec to read/write the data to disk
                    .persistent(GenerationContext.CODEC)
                    // the codec to read/write the data across the network
                    .networkSynchronized(GenerationContext.STREAM_CODEC)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemContainerContents>> KEY_RING_INVENTORY =
            REGISTRAR.registerComponentType(
                    "key_ring_inventory",
                    builder -> builder
                            .persistent(ItemContainerContents.CODEC)
                            .networkSynchronized(ItemContainerContents.STREAM_CODEC)
            );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemContainerContents>> POUCH_INVENTORY =
            REGISTRAR.registerComponentType(
                    "pouch_inventory",
                    builder -> builder
                            .persistent(ItemContainerContents.CODEC)
                            .networkSynchronized(ItemContainerContents.STREAM_CODEC)
            );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> KEY_RING_OPEN = REGISTRAR.registerComponentType(
            "key_ring_open",
            builder -> builder
                    // the codec to read/write the data to disk
                    .persistent(Unit.CODEC)
                    // the codec to read/write the data across the network
                    .networkSynchronized(StreamCodec.unit(Unit.INSTANCE))
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<DurabilityComponent>> DURABILITY = REGISTRAR.registerComponentType(
            "durability",
            builder -> builder
                    // the codec to read/write the data to disk
                    .persistent(DurabilityComponent.CODEC)
                    // the codec to read/write the data across the network
                    .networkSynchronized(DurabilityComponent.STREAM_CODEC)
    );

    public static void register(IEventBus modEventBus) {
        REGISTRAR.register(modEventBus);
    }
}
