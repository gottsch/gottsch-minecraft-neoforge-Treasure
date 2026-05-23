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
 * @author Mark Gottschling May 25, 2023
 */
public class LivingEventHandler {
    // TODO: restore onLivingHurtEvent (LivingIncomingDamageEvent) when IWeapon is ported
    // Applies weapon critical-hit bonus damage when player holds an IWeapon item.
    // checkCharmsInteractionWithDamage (LivingDamageEvent) was empty in Forge — omitted.
}
