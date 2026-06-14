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
import mod.gottsch.neoforge.treasure2.core.item.KeyItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;

/**
 * Combines two identical {@link KeyItem}s in an anvil by summing their remaining uses.
 *
 * <p>The Forge version used the durability capability; in 1.21 durability is a per-stack
 * vanilla data component, so the merged key boosts its per-stack {@code MAX_DAMAGE}
 * (capped at {@link #MAX_DURABILITY}) and resets {@code DAMAGE} accordingly. The per-key
 * {@link KeyItem#isDamageable(ItemStack)} check excludes infinite keys (e.g. the One Key).
 *
 * @author Mark Gottschling on Sep 6, 2020
 */
@EventBusSubscriber(modid = Treasure.MODID, bus = EventBusSubscriber.Bus.GAME)
public class AnvilEventHandler {

	private static final int MAX_DURABILITY = 100;

	@SubscribeEvent
	public static void onAnvilUpdate(AnvilUpdateEvent event) {
		ItemStack leftStack = event.getLeft();
		ItemStack rightStack = event.getRight();

		// both inputs must be the same KeyItem, damageable (excludes the infinite One Key)
		if (leftStack.getItem() instanceof KeyItem leftKey
				&& leftStack.getItem() == rightStack.getItem()
				&& rightStack.getItem() instanceof KeyItem rightKey
				&& leftKey.isDamageable(leftStack)
				&& rightKey.isDamageable(rightStack)
				&& leftStack.isDamageableItem()
				&& rightStack.isDamageableItem()) {

			event.setCost(1);

			int leftDurability = leftStack.getMaxDamage();
			int rightDurability = rightStack.getMaxDamage();
			int leftRemainingUses = leftDurability - leftStack.getDamageValue();
			int rightRemainingUses = rightDurability - rightStack.getDamageValue();
			int remainingUses = leftRemainingUses + rightRemainingUses;

			ItemStack outputStack = new ItemStack(leftStack.getItem());

			if (remainingUses > Math.max(leftDurability, rightDurability)) {
				// boost the combined key's max durability (capped) and fill it
				int newMax = Math.min(remainingUses, MAX_DURABILITY);
				outputStack.set(DataComponents.MAX_DAMAGE, newMax);
				outputStack.setDamageValue(0);
			} else {
				int newMax = (remainingUses < Math.min(leftDurability, rightDurability))
						? Math.min(leftDurability, rightDurability)
						: Math.max(leftDurability, rightDurability);
				outputStack.set(DataComponents.MAX_DAMAGE, newMax);
				outputStack.setDamageValue(newMax - remainingUses);
			}
			event.setOutput(outputStack);
		}
	}
}
