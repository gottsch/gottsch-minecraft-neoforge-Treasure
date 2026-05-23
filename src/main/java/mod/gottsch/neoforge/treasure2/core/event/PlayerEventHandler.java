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
import mod.gottsch.neoforge.treasure2.core.config.Config;
import mod.gottsch.neoforge.treasure2.core.item.WealthItem;
import mod.gottsch.neoforge.treasure2.core.tag.TreasureTags;
import mod.gottsch.neoforge.treasure2.core.util.LangUtil;
import mod.gottsch.neoforge.treasure2.core.wishable.IWishableHandler;
import mod.gottsch.neoforge.treasure2.core.wishable.TreasureWishableHandlers;
import mod.gottsch.neoforge.treasure2.core.wishable.TreasureWishables;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Objects;

/**
 * @author Mark Gottschling May 26, 2023
 */
@EventBusSubscriber(modid = Treasure.MODID, bus = EventBusSubscriber.Bus.GAME)
public class PlayerEventHandler {

    @SubscribeEvent
    public static void playerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        if (!(player instanceof ServerPlayer)) {
            return;
        }

        if (player.tickCount % 5 == 0) {
            checkForWishables(player);
        }
    }

    private static void checkForWishables(Player player) {
        player.level().getEntitiesOfClass(ItemEntity.class, player.getBoundingBox().inflate(Config.SERVER.wells.scanForItemRadius.get()))
                .stream()
                .filter(item -> !(item.getItem().getItem() instanceof WealthItem) && item.getItem().is(TreasureTags.Items.WISHABLES))
                .filter(item -> item.getOwner() != null && Objects.equals(item.getOwner().getUUID(), player.getUUID()))
                .forEach(item -> {
                    IWishableHandler handler = TreasureWishables.getHandler(item.getItem().getItem())
                            .orElse(TreasureWishableHandlers.DEFAULT_WISHABLE_HANDLER);
                    if (handler.isValidLocation(item)) {
                        handler.doWishable(item);
                    }
                });
    }

    @SubscribeEvent
    public static void onItemInfo(ItemTooltipEvent event) {
        if (!(event.getItemStack().getItem() instanceof WealthItem) && event.getItemStack().is(TreasureTags.Items.WISHABLES)) {
            event.getToolTip().add(Component.translatable(LangUtil.tooltip("wishable")).withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
        }
    }
}
