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

    // lock states — only serialize when list is non-empty
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<LockStatesComponent>> LOCK_STATES =
            ATTACHMENT_TYPES.register("lock_states", () ->
                    AttachmentType.<LockStatesComponent>builder(() -> new LockStatesComponent(new ArrayList<>()))
                            .serialize(LockStatesComponent.CODEC,
                                    ls -> ls != null && !ls.lockStates().isEmpty())
                            .build()
            );

    // facing
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Direction>> FACING =
            ATTACHMENT_TYPES.register("facing", () ->
                    AttachmentType.builder(() -> Direction.NORTH)
                            .serialize(Direction.CODEC)
                            .build()
            );

    // sealed — only serialize when true (default false)
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<SealedComponent>> SEALED =
            ATTACHMENT_TYPES.register("sealed", () ->
                    AttachmentType.<SealedComponent>builder(() -> new SealedComponent(false))
                            .serialize(SealedComponent.CODEC, s -> s != null && s.sealed())
                            .build()
            );

    // loot table — only serialize when path is non-empty
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<ResourceLocation>> LOOT_TABLE =
            ATTACHMENT_TYPES.register("loot_table", () ->
                    AttachmentType.<ResourceLocation>builder(() -> ResourceLocation.parse(""))
                            .serialize(ResourceLocation.CODEC, rl -> rl != null && !rl.getPath().isEmpty())
                            .build()
            );

    // mimic — only serialize when path is non-empty
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<ResourceLocation>> MIMIC =
            ATTACHMENT_TYPES.register("mimic", () ->
                    AttachmentType.<ResourceLocation>builder(() -> ResourceLocation.parse(""))
                            .serialize(ResourceLocation.CODEC, rl -> rl != null && !rl.getPath().isEmpty())
                            .build()
            );

    // component name — only serialize when name is non-blank
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<CustomNameComponent>> CUSTOM_NAME =
            ATTACHMENT_TYPES.register("custom_name", () ->
                    AttachmentType.<CustomNameComponent>builder(() -> new CustomNameComponent(""))
                            .serialize(CustomNameComponent.CODEC, c -> c != null && !c.name().isBlank())
                            .build()
            );

    // generation context — only serialize when both fields are non-null (default has null fields)
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<GenerationContext>> GENERATION_CONTEXT =
            ATTACHMENT_TYPES.register("generation_context", () ->
                    AttachmentType.<GenerationContext>builder(() -> GenerationContext.DEFAULT)
                            .serialize(GenerationContext.CODEC,
                                    ctx -> ctx.getLootRarity() != null && ctx.getFeatureType() != null)
                            .build()
            );

    public static void register(IEventBus bus) {
        ATTACHMENT_TYPES.register(bus);
    }
}
