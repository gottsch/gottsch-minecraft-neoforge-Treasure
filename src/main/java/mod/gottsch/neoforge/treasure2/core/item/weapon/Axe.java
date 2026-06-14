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
package mod.gottsch.neoforge.treasure2.core.item.weapon;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;

/**
 * Extends AxeItem so it behaves like a vanilla axe elsewhere (breaks shields, strips logs, etc.).
 * Float attack damage is applied via {@link TreasureWeapons#createAttributes(Tier, float, float)}.
 *
 * @author Mark Gottschling May 24, 2023
 */
public class Axe extends AxeItem implements IWeapon {
	private float criticalChance;
	private float criticalDamage;

	public Axe(Tier tier, float damageModifier, float speedModifier, Item.Properties properties) {
		this(tier, damageModifier, speedModifier, 0f, 0f, properties);
	}

	public Axe(Tier tier, float damageModifier, float speedModifier, float criticalChance, float criticalDamage, Item.Properties properties) {
		super(tier, properties.attributes(TreasureWeapons.createAttributes(tier, damageModifier, speedModifier)));
		this.criticalChance = criticalChance;
		this.criticalDamage = criticalDamage;
	}

	@Override
	public Component getName(ItemStack itemStack) {
		if (isUnique()) {
			return Component.translatable(this.getDescriptionId(itemStack)).withStyle(ChatFormatting.YELLOW);
		} else {
			return Component.translatable(this.getDescriptionId(itemStack));
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		appendStats(stack, context.level(), tooltip, flag);
		appendHoverExtras(stack, context.level(), tooltip, flag);
	}

	public float getCriticalChance() {
		return criticalChance;
	}

	public void setCriticalChance(float criticalChance) {
		this.criticalChance = criticalChance;
	}

	public float getCriticalDamage() {
		return criticalDamage;
	}

	public void setCriticalDamage(float criticalDamage) {
		this.criticalDamage = criticalDamage;
	}
}
