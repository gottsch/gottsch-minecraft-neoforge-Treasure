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

import java.util.Random;

import mod.gottsch.neo.gottschcore.random.RandomHelper;
import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.core.item.weapon.IWeapon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

/**
 * Applies an {@link IWeapon}'s critical (power-attack) bonus damage when the attacker
 * is a player holding the weapon. Forge's {@code LivingHurtEvent} is NeoForge's
 * {@code LivingIncomingDamageEvent} in 1.21.
 *
 * @author Mark Gottschling May 25, 2023
 */
@EventBusSubscriber(modid = Treasure.MODID, bus = EventBusSubscriber.Bus.GAME)
public class LivingEventHandler {

	@SubscribeEvent
	public static void onLivingHurt(LivingIncomingDamageEvent event) {
		if (event.getEntity().level().isClientSide()) {
			return;
		}

		if (event.getSource().getDirectEntity() instanceof Player player) {
			ItemStack heldStack = player.getMainHandItem();
			if (heldStack.getItem() instanceof IWeapon weapon) {
				// criticalChance is already a 0-100 percent (and matches the tooltip), so it is
				// passed straight to checkProbability. NOTE: Forge multiplied by 100 here, which
				// always exceeded 100% and made named weapons crit on every hit — fixed.
				if (RandomHelper.checkProbability(new Random(), weapon.getCriticalChance())) {
					event.setAmount(event.getAmount() + weapon.getCriticalDamage());
				}
			}
		}
	}
}
