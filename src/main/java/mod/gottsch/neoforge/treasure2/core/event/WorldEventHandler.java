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

import mod.gottsch.neo.gottschcore.world.WorldInfo;
import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.core.persistence.TreasureSavedData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.LevelEvent;

/**
 * @author Mark Gottschling
 */
@EventBusSubscriber(modid = Treasure.MODID, bus = EventBusSubscriber.Bus.GAME)
public class WorldEventHandler {

    private static boolean isLoaded = false;
    private static boolean isClientLoaded = false;

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onWorldLoad(LevelEvent.Load event) {
        Treasure.LOGGER.info("In world load event");

        if (WorldInfo.isServerSide((Level) event.getLevel())) {
            ResourceLocation dimension = WorldInfo.getDimension((Level) event.getLevel());
            Treasure.LOGGER.info("In world load event for dimension {}", dimension.toString());

            if (!isLoaded) {
                Treasure.LOGGER.debug("reading in chests config...");
                // TODO: restore TreasureDataFixer.fix() when TreasureDataFixer is ported
                TreasureSavedData.get((ServerLevel) event.getLevel());
                isLoaded = true;
            }
        } else {
            if (!isClientLoaded) {
                // TODO: restore TreasureDataFixer.fix() when TreasureDataFixer is ported
                isClientLoaded = true;
            }
        }
    }

    public static boolean isServerLoaded() {
        return isLoaded;
    }

    public static boolean isClientLoaded() {
        return isClientLoaded;
    }
}
