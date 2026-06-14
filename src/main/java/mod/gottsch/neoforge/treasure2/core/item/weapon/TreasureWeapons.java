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

import java.util.function.Supplier;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

/**
 * Weapon tiers + the float-damage attribute helper.
 *
 * <p>The Forge {@code Sword} injected float attack damage by reflecting
 * {@code SwordItem.defaultModifiers}; in 1.21 attack modifiers are first-class
 * {@link ItemAttributeModifiers} set via {@code Item.Properties.attributes(...)}, so
 * {@link #createAttributes(Tier, float, float)} replaces the reflection entirely and
 * keeps true float damage (no int truncation). Custom {@code ForgeTier}s become the
 * {@link WeaponTier} record; the old harvest-level tags map to 1.21
 * {@code INCORRECT_FOR_*} tags.
 *
 * @author Mark Gottschling May 24, 2023
 */
public class TreasureWeapons {
	public static final float HAMMER_BASE_DAMAGE = 8.0f;
	public static final float HAMMER_BASE_SPEED = -3.6f;

	public static final float AXE_BASE_DAMAGE = 6.0f;
	public static final float AXE_BASE_SPEED = -3.1f;

	// tiers
	public static final Tier COPPER = new WeaponTier(BlockTags.INCORRECT_FOR_STONE_TOOL, 200, 5.0F, 1.0F, 10, () -> Ingredient.of(Items.COPPER_INGOT));
	public static final Tier STEEL = new WeaponTier(BlockTags.INCORRECT_FOR_IRON_TOOL, 600, 6.5F, 2.5F, 15, () -> Ingredient.of(Items.IRON_INGOT));
	public static final Tier BONE = new WeaponTier(BlockTags.INCORRECT_FOR_IRON_TOOL, 200, 6.25F, 2.0F, 16, () -> Ingredient.of(Items.BONE));
	public static final Tier SHADOW = new WeaponTier(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1600, 9.0F, 4.0F, 15, () -> Ingredient.of(Items.NETHERITE_INGOT));

	// special tiers
	public static final Tier SKULL = new WeaponTier(BlockTags.INCORRECT_FOR_IRON_TOOL, 1800, 9.0F, 4.0F, 15, () -> Ingredient.of(Items.SKELETON_SKULL));
	public static final Tier RARE = new WeaponTier(BlockTags.INCORRECT_FOR_IRON_TOOL, 1700, 9.5F, 3.0F, 18, () -> Ingredient.of(Items.DIAMOND));
	public static final Tier EPIC = new WeaponTier(BlockTags.INCORRECT_FOR_IRON_TOOL, 1800, 9.5F, 4.5F, 18, () -> Ingredient.of(Items.DIAMOND));
	public static final Tier LEGENDARY = new WeaponTier(BlockTags.INCORRECT_FOR_IRON_TOOL, 2200, 10.0F, 5.0F, 20, () -> Ingredient.of(Items.DIAMOND));
	public static final Tier MYTHICAL = new WeaponTier(BlockTags.INCORRECT_FOR_IRON_TOOL, 2400, 11.0F, 6.0F, 22, () -> Ingredient.of(Items.NETHERITE_INGOT));

	/**
	 * Builds the mainhand ATTACK_DAMAGE / ATTACK_SPEED modifiers with float damage,
	 * mirroring vanilla {@code SwordItem.createAttributes} (which adds the tier bonus)
	 * but without the int truncation.
	 */
	public static ItemAttributeModifiers createAttributes(Tier tier, float attackDamage, float attackSpeed) {
		return ItemAttributeModifiers.builder()
				.add(Attributes.ATTACK_DAMAGE,
						new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, attackDamage + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE),
						EquipmentSlotGroup.MAINHAND)
				.add(Attributes.ATTACK_SPEED,
						new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE),
						EquipmentSlotGroup.MAINHAND)
				.build();
	}

	/**
	 * 1.21 {@link Tier} implementation (replaces Forge's {@code ForgeTier}).
	 */
	public record WeaponTier(TagKey<Block> incorrectBlocksForDrops, int uses, float speed, float attackDamageBonus,
			int enchantmentValue, Supplier<Ingredient> repairIngredient) implements Tier {
		@Override
		public int getUses() {
			return uses;
		}

		@Override
		public float getSpeed() {
			return speed;
		}

		@Override
		public float getAttackDamageBonus() {
			return attackDamageBonus;
		}

		@Override
		public TagKey<Block> getIncorrectBlocksForDrops() {
			return incorrectBlocksForDrops;
		}

		@Override
		public int getEnchantmentValue() {
			return enchantmentValue;
		}

		@Override
		public Ingredient getRepairIngredient() {
			return repairIngredient.get();
		}
	}
}
