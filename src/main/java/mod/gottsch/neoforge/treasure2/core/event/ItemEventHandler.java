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
package mod.gottsch.neoforge.treasure2.core.event;

import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.core.entity.item.ExplosionProofItemEntity;
import mod.gottsch.neoforge.treasure2.core.item.TreasureChestBlockItem;
import net.minecraft.world.entity.item.ItemEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityStruckByLightningEvent;

/**
 * @author Mark Gottschling on Jun 5, 2023
 */
@EventBusSubscriber(modid = Treasure.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ItemEventHandler {

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide()
                && event.getEntity() instanceof ItemEntity itemEntity
                && !(event.getEntity() instanceof ExplosionProofItemEntity)) {
            if (itemEntity.getItem().getItem() instanceof TreasureChestBlockItem) {
                ExplosionProofItemEntity newItemEntity = new ExplosionProofItemEntity(
                        event.getLevel(), itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), itemEntity.getItem());
                event.getLevel().addFreshEntity(newItemEntity);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onLightningStrike(EntityStruckByLightningEvent event) {
        if (event.getEntity() instanceof ExplosionProofItemEntity) {
            event.setCanceled(true);
        }
    }
}
