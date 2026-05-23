package mod.gottsch.neoforge.treasure2.core;

import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.client.model.blockentity.StandardChestModel;
import mod.gottsch.neoforge.treasure2.client.model.entity.*;
import mod.gottsch.neoforge.treasure2.client.renderer.blockentity.WoodChestRenderer;
import mod.gottsch.neoforge.treasure2.client.renderer.entity.*;
import mod.gottsch.neoforge.treasure2.client.screen.StandardChestScreen;
import mod.gottsch.neoforge.treasure2.core.block.entity.TreasureBlockEntities;
import mod.gottsch.neoforge.treasure2.core.entity.TreasureEntities;
import mod.gottsch.neoforge.treasure2.core.inventory.TreasureContainers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
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
    }

    @SubscribeEvent
    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(TreasureBlockEntities.WOOD_CHEST_BLOCK_ENTITY_TYPE.get(), WoodChestRenderer::new);

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
    }

    /**
     * register model layer definitions
     */
    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(StandardChestModel.LAYER_LOCATION, StandardChestModel::createBodyLayer);

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
}
