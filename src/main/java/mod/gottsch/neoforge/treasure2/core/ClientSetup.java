package mod.gottsch.neoforge.treasure2.core;

import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.client.model.blockentity.*;
import mod.gottsch.neoforge.treasure2.client.model.entity.*;
import mod.gottsch.neoforge.treasure2.client.renderer.blockentity.*;
import mod.gottsch.neoforge.treasure2.client.renderer.entity.*;
import mod.gottsch.neoforge.treasure2.client.screen.*;
import mod.gottsch.neoforge.treasure2.core.block.TreasureBlocks;
import mod.gottsch.neoforge.treasure2.core.block.entity.TreasureBlockEntities;
import mod.gottsch.neoforge.treasure2.core.block.state.properties.TreasureWoodTypes;
import mod.gottsch.neoforge.treasure2.core.entity.TreasureEntities;
import mod.gottsch.neoforge.treasure2.core.inventory.TreasureContainers;
import mod.gottsch.neoforge.treasure2.core.item.TreasureItems;
import net.minecraft.world.level.GrassColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

/**
 * @author by Mark Gottschling on 12/7/2025
 */
@EventBusSubscriber(modid = Treasure.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void onRegisterScreens(RegisterMenuScreensEvent event) {
        event.register(
                TreasureContainers.STANDARD_CHEST_CONTAINER.get(),
                StandardChestScreen::new
        );
        event.register(
                TreasureContainers.CELESTIAL_CHEST_CONTAINER.get(),
                CelestialChestScreen::new
        );
        event.register(
                TreasureContainers.SKULL_CHEST_CONTAINER.get(),
                SkullChestScreen::new
        );
        event.register(
                TreasureContainers.STRONGBOX_CONTAINER.get(),
                StrongboxScreen::new
        );
        event.register(
                TreasureContainers.COMPRESSOR_CHEST_CONTAINER.get(),
                CompressorChestScreen::new
        );
        event.register(
                TreasureContainers.VIKING_CHEST_CONTAINER.get(),
                VikingChestScreen::new
        );
        event.register(
                TreasureContainers.WITHER_CHEST_CONTAINER.get(),
                WitherChestScreen::new
        );
        event.register(
                TreasureContainers.POUCH_CONTAINER.get(),
                PouchScreen::new
        );
        event.register(
                TreasureContainers.KEY_RING_CONTAINER.get(),
                KeyRingScreen::new
        );
    }

    @SubscribeEvent
    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(TreasureBlockEntities.WOOD_CHEST_BLOCK_ENTITY_TYPE.get(), WoodChestRenderer::new);
        event.registerBlockEntityRenderer(TreasureBlockEntities.CRATE_CHEST_BLOCK_ENTITY_TYPE.get(), CrateChestRenderer::new);
        event.registerBlockEntityRenderer(TreasureBlockEntities.MOLDY_CRATE_CHEST_BLOCK_ENTITY_TYPE.get(), MoldyCrateChestRenderer::new);
        event.registerBlockEntityRenderer(TreasureBlockEntities.IRONBOUND_CHEST_BLOCK_ENTITY_TYPE.get(), IronboundChestRenderer::new);
        event.registerBlockEntityRenderer(TreasureBlockEntities.PIRATE_CHEST_BLOCK_ENTITY_TYPE.get(), PirateChestRenderer::new);
        event.registerBlockEntityRenderer(TreasureBlockEntities.SAFE_BLOCK_ENTITY_TYPE.get(), SafeRenderer::new);
        event.registerBlockEntityRenderer(TreasureBlockEntities.IRON_STRONGBOX_BLOCK_ENTITY_TYPE.get(), StrongboxRenderer::new);
        event.registerBlockEntityRenderer(TreasureBlockEntities.GOLD_STRONGBOX_BLOCK_ENTITY_TYPE.get(), StrongboxRenderer::createGold);
        event.registerBlockEntityRenderer(TreasureBlockEntities.DREAD_PIRATE_CHEST_BLOCK_ENTITY_TYPE.get(), DreadPirateChestRenderer::new);
        event.registerBlockEntityRenderer(TreasureBlockEntities.COMPRESSOR_CHEST_BLOCK_ENTITY_TYPE.get(), CompressorChestRenderer::new);
        event.registerBlockEntityRenderer(TreasureBlockEntities.SKULL_CHEST_BLOCK_ENTITY_TYPE.get(), SkullChestRenderer::new);
        event.registerBlockEntityRenderer(TreasureBlockEntities.GOLD_SKULL_CHEST_BLOCK_ENTITY_TYPE.get(), SkullChestRenderer::createGoldSkull);
        event.registerBlockEntityRenderer(TreasureBlockEntities.CRYSTAL_SKULL_CHEST_BLOCK_ENTITY_TYPE.get(), SkullChestRenderer::createCrystalSkull);
        event.registerBlockEntityRenderer(TreasureBlockEntities.CAULDRON_CHEST_BLOCK_ENTITY_TYPE.get(), CauldronChestRenderer::new);
        event.registerBlockEntityRenderer(TreasureBlockEntities.SPIDER_CHEST_BLOCK_ENTITY_TYPE.get(), SpiderChestRenderer::new);
        event.registerBlockEntityRenderer(TreasureBlockEntities.VIKING_CHEST_BLOCK_ENTITY_TYPE.get(), VikingChestRenderer::new);
        event.registerBlockEntityRenderer(TreasureBlockEntities.CARDBOARD_BOX_BLOCK_ENTITY_TYPE.get(), CardboardBoxRenderer::new);
        event.registerBlockEntityRenderer(TreasureBlockEntities.MILK_CRATE_BLOCK_ENTITY_TYPE.get(), MilkCrateRenderer::new);
        event.registerBlockEntityRenderer(TreasureBlockEntities.BARREL_CHEST_BLOCK_ENTITY_TYPE.get(), BarrelChestRenderer::new);
        event.registerBlockEntityRenderer(TreasureBlockEntities.VANILLA_CHEST_BLOCK_ENTITY_TYPE.get(), VanillaChestRenderer::new);
        event.registerBlockEntityRenderer(TreasureBlockEntities.WITHER_CHEST_BLOCK_ENTITY_TYPE.get(), WitherChestRenderer::new);
        event.registerBlockEntityRenderer(TreasureBlockEntities.BONE_CHEST.get(), BoneChestRenderer::new);
        event.registerBlockEntityRenderer(TreasureBlockEntities.CELESTIAL_CHEST.get(), CelestialChestRenderer::new);
        event.registerBlockEntityRenderer(TreasureBlockEntities.INFERNAL_CHEST.get(), InfernalChestRenderer::new);

        // entity renderers
        event.registerEntityRenderer(TreasureEntities.WOOD_CHEST_MIMIC_ENTITY_TYPE.get(), WoodChestMimicRenderer::new);
        event.registerEntityRenderer(TreasureEntities.PIRATE_CHEST_MIMIC_ENTITY_TYPE.get(), PirateChestMimicRenderer::new);
        event.registerEntityRenderer(TreasureEntities.VIKING_CHEST_MIMIC_ENTITY_TYPE.get(), VikingChestMimicRenderer::new);
        event.registerEntityRenderer(TreasureEntities.CAULDRON_CHEST_MIMIC_ENTITY_TYPE.get(), CauldronChestMimicRenderer::new);
        event.registerEntityRenderer(TreasureEntities.CRATE_CHEST_MIMIC_ENTITY_TYPE.get(), CrateChestMimicRenderer::new);
        event.registerEntityRenderer(TreasureEntities.MOLDY_CRATE_CHEST_MIMIC_ENTITY_TYPE.get(), MoldyCrateChestMimicRenderer::new);
        event.registerEntityRenderer(TreasureEntities.CARDBOARD_BOX_MIMIC_ENTITY_TYPE.get(), CardboardBoxMimicRenderer::new);
        event.registerEntityRenderer(TreasureEntities.MILK_CRATE_MIMIC_ENTITY_TYPE.get(), MilkCrateMimicRenderer::new);
        event.registerEntityRenderer(TreasureEntities.BARREL_MIMIC_ENTITY_TYPE.get(), BarrelMimicRenderer::new);
        event.registerEntityRenderer(TreasureEntities.VANILLA_CHEST_MIMIC_ENTITY_TYPE.get(), VanillaChestMimicRenderer::new);
        event.registerEntityRenderer(TreasureEntities.BOUND_SOUL_ENTITY_TYPE.get(), BoundSoulRenderer::new);
        event.registerEntityRenderer(TreasureEntities.WITHERWOOD_GOLEM_ENTITY_TYPE.get(), WitherwoodGolemRenderer::new);

        // sign block-entity renderers
        event.registerBlockEntityRenderer(TreasureBlockEntities.TREASURE_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(TreasureBlockEntities.TREASURE_HANGING_SIGN.get(), HangingSignRenderer::new);
    }

    /**
     * register model layer definitions
     */
    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(StandardChestModel.LAYER_LOCATION, StandardChestModel::createBodyLayer);
        event.registerLayerDefinition(CrateChestModel.LAYER_LOCATION, CrateChestModel::createBodyLayer);
        event.registerLayerDefinition(BandedChestModel.LAYER_LOCATION, BandedChestModel::createBodyLayer);
        event.registerLayerDefinition(SafeModel.LAYER_LOCATION, SafeModel::createBodyLayer);
        event.registerLayerDefinition(StrongboxModel.LAYER_LOCATION, StrongboxModel::createBodyLayer);
        event.registerLayerDefinition(DreadPirateChestModel.LAYER_LOCATION, DreadPirateChestModel::createBodyLayer);
        event.registerLayerDefinition(CompressorChestModel.LAYER_LOCATION, CompressorChestModel::createBodyLayer);
        event.registerLayerDefinition(SkullChestModel.LAYER_LOCATION, SkullChestModel::createBodyLayer);
        event.registerLayerDefinition(CauldronChestModel.LAYER_LOCATION, CauldronChestModel::createBodyLayer);
        event.registerLayerDefinition(SpiderChestModel.LAYER_LOCATION, SpiderChestModel::createBodyLayer);
        event.registerLayerDefinition(VikingChestModel.LAYER_LOCATION, VikingChestModel::createBodyLayer);
        event.registerLayerDefinition(CardboardBoxModel.LAYER_LOCATION, CardboardBoxModel::createBodyLayer);
        event.registerLayerDefinition(MilkCrateModel.LAYER_LOCATION, MilkCrateModel::createBodyLayer);
        event.registerLayerDefinition(WitherChestModel.LAYER_LOCATION, WitherChestModel::createBodyLayer);
        event.registerLayerDefinition(BarrelChestModel.LAYER_LOCATION, BarrelChestModel::createBodyLayer);
        event.registerLayerDefinition(VanillaChestModel.LAYER_LOCATION, VanillaChestModel::createBodyLayer);
        event.registerLayerDefinition(BoneChestModel.LAYER_LOCATION, BoneChestModel::createBodyLayer);
        event.registerLayerDefinition(CelestialChestModel.LAYER_LOCATION, CelestialChestModel::createBodyLayer);
        event.registerLayerDefinition(InfernalChestModel.LAYER_LOCATION, InfernalChestModel::createBodyLayer);

        event.registerLayerDefinition(WoodChestMimicModel.LAYER_LOCATION, WoodChestMimicModel::createBodyLayer);
        event.registerLayerDefinition(PirateChestMimicModel.LAYER_LOCATION, PirateChestMimicModel::createBodyLayer);
        event.registerLayerDefinition(VikingChestMimicModel.LAYER_LOCATION, VikingChestMimicModel::createBodyLayer);
        event.registerLayerDefinition(CauldronChestMimicModel.LAYER_LOCATION, CauldronChestMimicModel::createBodyLayer);
        event.registerLayerDefinition(CrateChestMimicModel.LAYER_LOCATION, CrateChestMimicModel::createBodyLayer);
        event.registerLayerDefinition(MoldyCrateChestMimicModel.LAYER_LOCATION, MoldyCrateChestMimicModel::createBodyLayer);
        event.registerLayerDefinition(CardboardBoxMimicModel.LAYER_LOCATION, CardboardBoxMimicModel::createBodyLayer);
        event.registerLayerDefinition(MilkCrateMimicModel.LAYER_LOCATION, MilkCrateMimicModel::createBodyLayer);
        event.registerLayerDefinition(BarrelMimicModel.LAYER_LOCATION, BarrelMimicModel::createBodyLayer);
        event.registerLayerDefinition(VanillaChestMimicModel.LAYER_LOCATION, VanillaChestMimicModel::createBodyLayer);
        event.registerLayerDefinition(BoundSoulModel.LAYER_LOCATION, BoundSoulModel::createBodyLayer);
        event.registerLayerDefinition(WitherwoodGolemModel.LAYER_LOCATION, WitherwoodGolemModel::createBodyLayer);
    }

    /**
     * Tint the FALLING_GRASS top face (grass_block_top is grayscale and needs biome tinting like vanilla grass).
     */
    @SubscribeEvent
    public static void onRegisterBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register(
                (state, level, pos, tintIndex) -> (level != null && pos != null)
                        ? BiomeColors.getAverageGrassColor(level, pos)
                        : GrassColor.get(0.5D, 1.0D),
                TreasureBlocks.FALLING_GRASS.get()
        );
    }

    @SubscribeEvent
    public static void onRegisterItemColors(RegisterColorHandlersEvent.Item event) {
        event.register(
                (stack, tintIndex) -> GrassColor.get(0.5D, 1.0D),
                TreasureItems.FALLING_GRASS.get()
        );
    }

    /**
     * Client setup: register the witherwood wood type's sign atlas materials and set cutout
     * render layers for blocks with transparent model parts (witherwood door / trapdoor).
     */
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            // sign atlas materials for standing/wall/hanging signs (textures: entity/signs/witherwood[/hanging])
            Sheets.addWoodType(TreasureWoodTypes.WITHERWOOD_TYPE);

            ItemBlockRenderTypes.setRenderLayer(TreasureBlocks.WITHERWOOD_DOOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(TreasureBlocks.WITHERWOOD_TRAPDOOR.get(), RenderType.cutout());

            // skeleton bone textures have transparent areas — cutout so they aren't rendered black/solid
            ItemBlockRenderTypes.setRenderLayer(TreasureBlocks.SKELETON.get(), RenderType.cutout());

            // spanish moss + structure_mob_set marker have transparent texture areas — cutout (per Forge list)
            ItemBlockRenderTypes.setRenderLayer(TreasureBlocks.SPANISH_MOSS.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(TreasureBlocks.STRUCTURE_MOB_SET.get(), RenderType.cutout());
        });
    }
}
