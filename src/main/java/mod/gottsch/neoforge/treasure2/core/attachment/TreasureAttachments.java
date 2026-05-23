package mod.gottsch.neoforge.treasure2.core.attachment;

import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.core.block.entity.GenerationContext;
import mod.gottsch.neoforge.treasure2.core.component.CustomNameComponent;
import mod.gottsch.neoforge.treasure2.core.component.LockStatesComponent;
import mod.gottsch.neoforge.treasure2.core.component.SealedComponent;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by Mark Gottschling on 11/29/2025
 */
public class TreasureAttachments {

    // define the DeferredRegister specifically for AttachmentType
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Treasure.MODID);

    // inventory
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<ItemContainerContents>> INVENTORY =
            ATTACHMENT_TYPES.register("inventory", () ->
                    AttachmentType.builder(() -> ItemContainerContents.fromItems(List.of()))
                            .serialize(ItemContainerContents.CODEC)
                            .build()
            );

    // lock states
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<LockStatesComponent>> LOCK_STATES =
            ATTACHMENT_TYPES.register("lock_states", () ->
                    AttachmentType.builder(() -> new LockStatesComponent(new ArrayList<>()))
                            .serialize(LockStatesComponent.CODEC)
                            .build()
            );

    // facing
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Direction>> FACING =
            ATTACHMENT_TYPES.register("facing", () ->
                    AttachmentType.builder(() -> Direction.NORTH)
                            .serialize(Direction.CODEC)
                            .build()
            );

    // sealed
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<SealedComponent>> SEALED =
            ATTACHMENT_TYPES.register("sealed", () ->
                    AttachmentType.builder(() -> new SealedComponent(false))
                            .serialize(SealedComponent.CODEC)
                            .build()
            );

    // loot table
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<ResourceLocation>> LOOT_TABLE =
            ATTACHMENT_TYPES.register("loot_table", () ->
                    AttachmentType.builder(() -> ResourceLocation.parse(""))
                            .serialize(ResourceLocation.CODEC)
                            .build()
            );

    // mimic
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<ResourceLocation>> MIMIC =
            ATTACHMENT_TYPES.register("mimic", () ->
                    AttachmentType.builder(() -> ResourceLocation.parse(""))
                            .serialize(ResourceLocation.CODEC)
                            .build()
            );

    // component name
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<CustomNameComponent>> CUSTOM_NAME =
            ATTACHMENT_TYPES.register("custom_name", () ->
                    AttachmentType.builder(() -> new CustomNameComponent(""))
                            .serialize(CustomNameComponent.CODEC)
                            .build()
            );

    // generation context
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<GenerationContext>> GENERATION_CONTEXT =
            ATTACHMENT_TYPES.register("generation_context", () ->
                    AttachmentType.<GenerationContext>builder(() -> GenerationContext.DEFAULT) // Supplier for default value
                            .serialize(GenerationContext.CODEC) // Use the defined Codec for persistence
                            .build()
            );

    public static void register(IEventBus bus) {
        ATTACHMENT_TYPES.register(bus);
    }
}
