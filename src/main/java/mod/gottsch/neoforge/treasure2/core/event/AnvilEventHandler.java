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
import net.neoforged.fml.common.EventBusSubscriber;

/**
 * @author Mark Gottschling on Jun 5, 2023
 */
public class AnvilEventHandler {
    // TODO: restore onAnvilUpdate(AnvilUpdateEvent) when TreasureCapabilities.DURABILITY is ported (Session 5 dependency)
    // Logic: combines two identical KeyItems in the anvil by summing their remaining uses.
}
