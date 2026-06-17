package mod.gottsch.neoforge.treasure2;

import mod.gottsch.neoforge.treasure2.api.TreasureApi;
import mod.gottsch.neoforge.treasure2.core.attachment.TreasureAttachments;
import mod.gottsch.neoforge.treasure2.core.creativetab.TreasureCreativeModeTabs;
import mod.gottsch.neoforge.treasure2.core.block.TreasureBlocks;
import mod.gottsch.neoforge.treasure2.core.block.entity.AbstractTreasureChestBlockEntity;
import mod.gottsch.neoforge.treasure2.core.block.entity.TreasureBlockEntities;
import mod.gottsch.neoforge.treasure2.core.capability.TreasureCapabilities;
import mod.gottsch.neoforge.treasure2.core.component.TreasureComponents;
import mod.gottsch.neoforge.treasure2.core.config.Config;
import mod.gottsch.neoforge.treasure2.core.entity.TreasureEntities;
import mod.gottsch.neoforge.treasure2.core.inventory.TreasureContainers;
import mod.gottsch.neoforge.treasure2.core.loot.TreasureLootTableTypes;
import mod.gottsch.neoforge.treasure2.core.loot.modifier.TreasureLootModifiers;
import mod.gottsch.neoforge.treasure2.core.item.TreasureItems;
import mod.gottsch.neoforge.treasure2.core.material.TreasureArmorMaterials;
import mod.gottsch.neoforge.treasure2.core.network.TreasureNetworking;
import mod.gottsch.neoforge.treasure2.core.particle.TreasureParticles;
import mod.gottsch.neoforge.treasure2.core.rarity.TreasureRarities;
import mod.gottsch.neoforge.treasure2.core.sound.TreasureSounds;
import mod.gottsch.neoforge.treasure2.core.structure.TreasureStructures;
import mod.gottsch.neoforge.treasure2.core.structure.templatesystem.ModProcessors;
import mod.gottsch.neoforge.treasure2.core.world.feature.TreasureFeatureTypes;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.IItemHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Treasure.MODID)
public class Treasure {
    // logger
    public static final Logger LOGGER = LoggerFactory.getLogger(Treasure.MODID);

    public static final String MODID = "treasure2";

    public Treasure(IEventBus modEventBus, ModContainer modContainer) {
        Config.register(modContainer);

        // wire live config values into the public API so extension points (e.g.
        // third-party IWishableHandler implementations) can read them without
        // importing the loader-specific Config class.
        TreasureApi.bindConfig(
                () -> Config.SERVER.wells.scanForWellRadius.get(),
                () -> Config.SERVER.wells.scanMinBlockCount.get()
        );

        TreasureRarities.register(modEventBus);

        TreasureFeatureTypes.register(modEventBus);

        TreasureAttachments.register(modEventBus);
        TreasureComponents.register(modEventBus);
        TreasureBlocks.register(modEventBus);
        TreasureArmorMaterials.register(modEventBus);
        TreasureItems.register(modEventBus);
        TreasureBlockEntities.register(modEventBus);
        TreasureContainers.register(modEventBus);
        TreasureParticles.register(modEventBus);
        TreasureSounds.register(modEventBus);
        TreasureEntities.register(modEventBus);
        TreasureLootTableTypes.register(modEventBus);
        TreasureLootModifiers.register(modEventBus);
        TreasureCreativeModeTabs.register(modEventBus);
        ModProcessors.register(modEventBus);
        TreasureStructures.register(modEventBus);

        // register network payload handlers
        modEventBus.addListener(TreasureNetworking::register);

        // register mimic chest -> mimic-entity associations (after registries are populated)
        modEventBus.addListener(this::commonSetup);
    }

    /**
     * Registers the default chest -> mimic-entity associations once all registries are populated.
     * Without these, {@code MimicRegistry} is empty and chests can never become mimics (e.g. the
     * {@code /t2 chest ... mimic} command silently places an ordinary chest).
     */
    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            TreasureApi.registerMimic(TreasureBlocks.WOOD_CHEST.getId(), TreasureEntities.WOOD_CHEST_MIMIC_ENTITY_TYPE.getId());
            TreasureApi.registerMimic(TreasureBlocks.CRATE_CHEST.getId(), TreasureEntities.CRATE_CHEST_MIMIC_ENTITY_TYPE.getId());
            TreasureApi.registerMimic(TreasureBlocks.MOLDY_CRATE_CHEST.getId(), TreasureEntities.MOLDY_CRATE_CHEST_MIMIC_ENTITY_TYPE.getId());
            TreasureApi.registerMimic(TreasureBlocks.PIRATE_CHEST.getId(), TreasureEntities.PIRATE_CHEST_MIMIC_ENTITY_TYPE.getId());
            TreasureApi.registerMimic(TreasureBlocks.CAULDRON_CHEST.getId(), TreasureEntities.CAULDRON_CHEST_MIMIC_ENTITY_TYPE.getId());
            TreasureApi.registerMimic(TreasureBlocks.VIKING_CHEST.getId(), TreasureEntities.VIKING_CHEST_MIMIC_ENTITY_TYPE.getId());
            TreasureApi.registerMimic(TreasureBlocks.CARDBOARD_BOX.getId(), TreasureEntities.CARDBOARD_BOX_MIMIC_ENTITY_TYPE.getId());
            TreasureApi.registerMimic(TreasureBlocks.MILK_CRATE.getId(), TreasureEntities.MILK_CRATE_MIMIC_ENTITY_TYPE.getId());
            TreasureApi.registerMimic(TreasureBlocks.BARREL_CHEST.getId(), TreasureEntities.BARREL_MIMIC_ENTITY_TYPE.getId());
            TreasureApi.registerMimic(TreasureBlocks.VANILLA_CHEST.getId(), TreasureEntities.VANILLA_CHEST_MIMIC_ENTITY_TYPE.getId());
        });
    }

    /**
     * This method is called by NeoForge during mod loading to register all Block Capabilities.
     */
    @SubscribeEvent
    public static void registerBlockCapabilities(RegisterCapabilitiesEvent event) {

        // --- define the common accessor logic once ---
        // this lambda checks the type and calls the shared getInventoryHandler() method.
        final ICapabilityProvider<BlockEntity, Direction, IItemHandler> handlerAccessor = (BlockEntity blockEntity, Direction direction) -> {
            if (blockEntity instanceof AbstractTreasureChestBlockEntity chestBlockEntity) {
                return chestBlockEntity.getInventoryHandler();
            }
            return null;
        };

        // register custom capability for all chest block entity types
        event.registerBlockEntity(TreasureCapabilities.ITEM_HANDLER_BLOCK, TreasureBlockEntities.WOOD_CHEST_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(TreasureCapabilities.ITEM_HANDLER_BLOCK, TreasureBlockEntities.BARREL_CHEST_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(TreasureCapabilities.ITEM_HANDLER_BLOCK, TreasureBlockEntities.CRATE_CHEST_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(TreasureCapabilities.ITEM_HANDLER_BLOCK, TreasureBlockEntities.MOLDY_CRATE_CHEST_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(TreasureCapabilities.ITEM_HANDLER_BLOCK, TreasureBlockEntities.IRONBOUND_CHEST_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(TreasureCapabilities.ITEM_HANDLER_BLOCK, TreasureBlockEntities.PIRATE_CHEST_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(TreasureCapabilities.ITEM_HANDLER_BLOCK, TreasureBlockEntities.SAFE_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(TreasureCapabilities.ITEM_HANDLER_BLOCK, TreasureBlockEntities.IRON_STRONGBOX_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(TreasureCapabilities.ITEM_HANDLER_BLOCK, TreasureBlockEntities.GOLD_STRONGBOX_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(TreasureCapabilities.ITEM_HANDLER_BLOCK, TreasureBlockEntities.DREAD_PIRATE_CHEST_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(TreasureCapabilities.ITEM_HANDLER_BLOCK, TreasureBlockEntities.COMPRESSOR_CHEST_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(TreasureCapabilities.ITEM_HANDLER_BLOCK, TreasureBlockEntities.SKULL_CHEST_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(TreasureCapabilities.ITEM_HANDLER_BLOCK, TreasureBlockEntities.GOLD_SKULL_CHEST_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(TreasureCapabilities.ITEM_HANDLER_BLOCK, TreasureBlockEntities.CRYSTAL_SKULL_CHEST_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(TreasureCapabilities.ITEM_HANDLER_BLOCK, TreasureBlockEntities.CAULDRON_CHEST_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(TreasureCapabilities.ITEM_HANDLER_BLOCK, TreasureBlockEntities.SPIDER_CHEST_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(TreasureCapabilities.ITEM_HANDLER_BLOCK, TreasureBlockEntities.VIKING_CHEST_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(TreasureCapabilities.ITEM_HANDLER_BLOCK, TreasureBlockEntities.CARDBOARD_BOX_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(TreasureCapabilities.ITEM_HANDLER_BLOCK, TreasureBlockEntities.MILK_CRATE_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(TreasureCapabilities.ITEM_HANDLER_BLOCK, TreasureBlockEntities.VANILLA_CHEST_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(TreasureCapabilities.ITEM_HANDLER_BLOCK, TreasureBlockEntities.WITHER_CHEST_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(TreasureCapabilities.ITEM_HANDLER_BLOCK, TreasureBlockEntities.BONE_CHEST.get(), handlerAccessor);
        event.registerBlockEntity(TreasureCapabilities.ITEM_HANDLER_BLOCK, TreasureBlockEntities.CELESTIAL_CHEST.get(), handlerAccessor);
        event.registerBlockEntity(TreasureCapabilities.ITEM_HANDLER_BLOCK, TreasureBlockEntities.INFERNAL_CHEST.get(), handlerAccessor);

        // register standard NeoForge ItemHandler capability for hopper support
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TreasureBlockEntities.WOOD_CHEST_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TreasureBlockEntities.BARREL_CHEST_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TreasureBlockEntities.CRATE_CHEST_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TreasureBlockEntities.MOLDY_CRATE_CHEST_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TreasureBlockEntities.IRONBOUND_CHEST_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TreasureBlockEntities.PIRATE_CHEST_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TreasureBlockEntities.SAFE_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TreasureBlockEntities.IRON_STRONGBOX_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TreasureBlockEntities.GOLD_STRONGBOX_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TreasureBlockEntities.DREAD_PIRATE_CHEST_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TreasureBlockEntities.COMPRESSOR_CHEST_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TreasureBlockEntities.SKULL_CHEST_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TreasureBlockEntities.GOLD_SKULL_CHEST_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TreasureBlockEntities.CRYSTAL_SKULL_CHEST_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TreasureBlockEntities.CAULDRON_CHEST_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TreasureBlockEntities.SPIDER_CHEST_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TreasureBlockEntities.VIKING_CHEST_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TreasureBlockEntities.CARDBOARD_BOX_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TreasureBlockEntities.MILK_CRATE_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TreasureBlockEntities.VANILLA_CHEST_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TreasureBlockEntities.WITHER_CHEST_BLOCK_ENTITY_TYPE.get(), handlerAccessor);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TreasureBlockEntities.BONE_CHEST.get(), handlerAccessor);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TreasureBlockEntities.CELESTIAL_CHEST.get(), handlerAccessor);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TreasureBlockEntities.INFERNAL_CHEST.get(), handlerAccessor);
    }
}
