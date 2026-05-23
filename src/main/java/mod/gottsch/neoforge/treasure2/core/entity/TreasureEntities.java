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
package mod.gottsch.neoforge.treasure2.core.entity;

import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.core.entity.monster.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * @author Mark Gottschling on Jul 22, 2021
 */
@EventBusSubscriber(modid = Treasure.MODID, bus = EventBusSubscriber.Bus.MOD)
public class TreasureEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, Treasure.MODID);

    public static final String BOUND_SOUL = "bound_soul";
    public static final String WOOD_CHEST_MIMIC = "wood_chest_mimic";
    public static final String PIRATE_CHEST_MIMIC = "pirate_chest_mimic";
    public static final String VIKING_CHEST_MIMIC = "viking_chest_mimic";
    public static final String CAULDRON_CHEST_MIMIC = "cauldron_chest_mimic";
    public static final String CRATE_CHEST_MIMIC = "crate_chest_mimic";
    public static final String MOLDY_CRATE_CHEST_MIMIC = "moldy_crate_chest_mimic";
    public static final String CARDBOARD_BOX_MIMIC = "cardboard_box_mimic";
    public static final String MILK_CRATE_MIMIC = "milk_crate_mimic";
    public static final String BARREL_MIMIC = "barrel_mimic";
    public static final String VANILLA_CHEST_MIMIC = "vanilla_chest_mimic";
    public static final String WITHERWOOD_GOLEM = "witherwood_golem";

    public static final DeferredHolder<EntityType<?>, EntityType<BoundSoul>> BOUND_SOUL_ENTITY_TYPE =
            ENTITIES.register(BOUND_SOUL, () -> EntityType.Builder.of(BoundSoul::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(12)
                    .build(BOUND_SOUL));

    public static final DeferredHolder<EntityType<?>, EntityType<WitherwoodGolem>> WITHERWOOD_GOLEM_ENTITY_TYPE =
            ENTITIES.register(WITHERWOOD_GOLEM, () -> EntityType.Builder.of(WitherwoodGolem::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(12)
                    .build(WITHERWOOD_GOLEM));

    public static final DeferredHolder<EntityType<?>, EntityType<WoodChestMimic>> WOOD_CHEST_MIMIC_ENTITY_TYPE =
            ENTITIES.register(WOOD_CHEST_MIMIC, () -> EntityType.Builder.of(WoodChestMimic::new, MobCategory.MONSTER)
                    .sized(0.875F, 0.875F)
                    .clientTrackingRange(12)
                    .build(WOOD_CHEST_MIMIC));

    public static final DeferredHolder<EntityType<?>, EntityType<PirateChestMimic>> PIRATE_CHEST_MIMIC_ENTITY_TYPE =
            ENTITIES.register(PIRATE_CHEST_MIMIC, () -> EntityType.Builder.of(PirateChestMimic::new, MobCategory.MONSTER)
                    .sized(0.875F, 0.875F)
                    .clientTrackingRange(12)
                    .build(PIRATE_CHEST_MIMIC));

    public static final DeferredHolder<EntityType<?>, EntityType<VikingChestMimic>> VIKING_CHEST_MIMIC_ENTITY_TYPE =
            ENTITIES.register(VIKING_CHEST_MIMIC, () -> EntityType.Builder.of(VikingChestMimic::new, MobCategory.MONSTER)
                    .sized(0.875F, 0.875F)
                    .clientTrackingRange(12)
                    .build(VIKING_CHEST_MIMIC));

    public static final DeferredHolder<EntityType<?>, EntityType<CauldronChestMimic>> CAULDRON_CHEST_MIMIC_ENTITY_TYPE =
            ENTITIES.register(CAULDRON_CHEST_MIMIC, () -> EntityType.Builder.of(CauldronChestMimic::new, MobCategory.MONSTER)
                    .sized(0.875F, 0.875F)
                    .clientTrackingRange(12)
                    .build(CAULDRON_CHEST_MIMIC));

    public static final DeferredHolder<EntityType<?>, EntityType<CrateChestMimic>> CRATE_CHEST_MIMIC_ENTITY_TYPE =
            ENTITIES.register(CRATE_CHEST_MIMIC, () -> EntityType.Builder.of(CrateChestMimic::new, MobCategory.MONSTER)
                    .sized(0.875F, 0.875F)
                    .clientTrackingRange(12)
                    .build(CRATE_CHEST_MIMIC));

    public static final DeferredHolder<EntityType<?>, EntityType<MoldyCrateChestMimic>> MOLDY_CRATE_CHEST_MIMIC_ENTITY_TYPE =
            ENTITIES.register(MOLDY_CRATE_CHEST_MIMIC, () -> EntityType.Builder.of(MoldyCrateChestMimic::new, MobCategory.MONSTER)
                    .sized(0.875F, 0.875F)
                    .clientTrackingRange(12)
                    .build(MOLDY_CRATE_CHEST_MIMIC));

    public static final DeferredHolder<EntityType<?>, EntityType<CardboardBoxMimic>> CARDBOARD_BOX_MIMIC_ENTITY_TYPE =
            ENTITIES.register(CARDBOARD_BOX_MIMIC, () -> EntityType.Builder.of(CardboardBoxMimic::new, MobCategory.MONSTER)
                    .sized(1F, 1.25F)
                    .clientTrackingRange(12)
                    .build(CARDBOARD_BOX_MIMIC));

    public static final DeferredHolder<EntityType<?>, EntityType<MilkCrateMimic>> MILK_CRATE_MIMIC_ENTITY_TYPE =
            ENTITIES.register(MILK_CRATE_MIMIC, () -> EntityType.Builder.of(MilkCrateMimic::new, MobCategory.MONSTER)
                    .sized(0.875F, 0.875F)
                    .clientTrackingRange(12)
                    .build(MILK_CRATE_MIMIC));

    public static final DeferredHolder<EntityType<?>, EntityType<BarrelMimic>> BARREL_MIMIC_ENTITY_TYPE =
            ENTITIES.register(BARREL_MIMIC, () -> EntityType.Builder.of(BarrelMimic::new, MobCategory.MONSTER)
                    .sized(1F, 1F)
                    .clientTrackingRange(12)
                    .build(BARREL_MIMIC));

    public static final DeferredHolder<EntityType<?>, EntityType<VanillaChestMimic>> VANILLA_CHEST_MIMIC_ENTITY_TYPE =
            ENTITIES.register(VANILLA_CHEST_MIMIC, () -> EntityType.Builder.of(VanillaChestMimic::new, MobCategory.MONSTER)
                    .sized(1F, 1F)
                    .clientTrackingRange(12)
                    .build(VANILLA_CHEST_MIMIC));

    public static void register(IEventBus modEventBus) {
        ENTITIES.register(modEventBus);
    }

    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(BOUND_SOUL_ENTITY_TYPE.get(), BoundSoul.createAttributes().build());
        event.put(WITHERWOOD_GOLEM_ENTITY_TYPE.get(), WitherwoodGolem.createAttributes().build());
        event.put(WOOD_CHEST_MIMIC_ENTITY_TYPE.get(), WoodChestMimic.createAttributes().build());
        event.put(PIRATE_CHEST_MIMIC_ENTITY_TYPE.get(), PirateChestMimic.createAttributes().build());
        event.put(VIKING_CHEST_MIMIC_ENTITY_TYPE.get(), VikingChestMimic.createAttributes().build());
        event.put(CAULDRON_CHEST_MIMIC_ENTITY_TYPE.get(), CauldronChestMimic.createAttributes().build());
        event.put(CRATE_CHEST_MIMIC_ENTITY_TYPE.get(), CrateChestMimic.createAttributes().build());
        event.put(MOLDY_CRATE_CHEST_MIMIC_ENTITY_TYPE.get(), MoldyCrateChestMimic.createAttributes().build());
        event.put(CARDBOARD_BOX_MIMIC_ENTITY_TYPE.get(), CardboardBoxMimic.createAttributes().build());
        event.put(MILK_CRATE_MIMIC_ENTITY_TYPE.get(), MilkCrateMimic.createAttributes().build());
        event.put(BARREL_MIMIC_ENTITY_TYPE.get(), BarrelMimic.createAttributes().build());
        event.put(VANILLA_CHEST_MIMIC_ENTITY_TYPE.get(), VanillaChestMimic.createAttributes().build());
    }
}
