/*
 * This file is part of Treasure2.
 * Copyright (c) 2026 Mark Gottschling (gottsch)
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
package mod.gottsch.neoforge.treasure2.core.potion;

import mod.gottsch.neoforge.treasure2.Treasure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Level III (amplifier 2) potions, only found as loot (ex. the epic potion pool).
 * Each uses the vanilla base name, like vanilla's strong_* potions, so it shows as
 * "Potion of Healing" etc. with the level in the tooltip and needs no lang entries.
 * Durations match vanilla's strong_* potions.
 *
 * @author Mark Gottschling on Oct 8, 2026
 */
public class TreasurePotions {
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(Registries.POTION, Treasure.MODID);

    public static final DeferredHolder<Potion, Potion> EXTRA_STRONG_HEALING = POTIONS.register("extra_strong_healing",
            () -> new Potion("healing", new MobEffectInstance(MobEffects.HEAL, 1, 2)));
    public static final DeferredHolder<Potion, Potion> EXTRA_STRONG_STRENGTH = POTIONS.register("extra_strong_strength",
            () -> new Potion("strength", new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1800, 2)));
    public static final DeferredHolder<Potion, Potion> EXTRA_STRONG_LEAPING = POTIONS.register("extra_strong_leaping",
            () -> new Potion("leaping", new MobEffectInstance(MobEffects.JUMP, 1800, 2)));
    public static final DeferredHolder<Potion, Potion> EXTRA_STRONG_SWIFTNESS = POTIONS.register("extra_strong_swiftness",
            () -> new Potion("swiftness", new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1800, 2)));
    public static final DeferredHolder<Potion, Potion> EXTRA_STRONG_REGENERATION = POTIONS.register("extra_strong_regeneration",
            () -> new Potion("regeneration", new MobEffectInstance(MobEffects.REGENERATION, 450, 2)));
    public static final DeferredHolder<Potion, Potion> EXTRA_STRONG_POISON = POTIONS.register("extra_strong_poison",
            () -> new Potion("poison", new MobEffectInstance(MobEffects.POISON, 432, 2)));

    public static void register(IEventBus modEventBus) {
        POTIONS.register(modEventBus);
    }
}
