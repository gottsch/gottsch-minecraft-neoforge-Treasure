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
package mod.gottsch.neoforge.treasure2.core.particle;

import mod.gottsch.neoforge.treasure2.Treasure;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


/**
 * 
 * @author Mark Gottschling on Nov 26, 2022
 *
 */
@EventBusSubscriber(modid = Treasure.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class TreasureParticles {
	public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(Registries.PARTICLE_TYPE, Treasure.MODID);

	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SPANISH_MOSS_PARTICLE = PARTICLES.register("spanish_moss_particle", () -> new SimpleParticleType(true));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> MIST_PARTICLE = PARTICLES.register("mist_particle", () -> new SimpleParticleType(false));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BILLOWING_MIST_PARTICLE = PARTICLES.register("billowing_mist_particle", () -> new SimpleParticleType(false));
	public static final DeferredHolder<ParticleType<?>, CollidingParticleType> POISON_MIST_PARTICLE = PARTICLES.register("poison_mist_particle", CollidingParticleType::new);
	public static final DeferredHolder<ParticleType<?>, CollidingParticleType> WITHER_MIST_PARTICLE = PARTICLES.register("wither_mist_particle", CollidingParticleType::new);

	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> COPPER_COIN_PARTICLE = PARTICLES.register("copper_coin_particle", () -> new SimpleParticleType(true));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SILVER_COIN_PARTICLE = PARTICLES.register("silver_coin_particle", () -> new SimpleParticleType(true));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GOLD_COIN_PARTICLE = PARTICLES.register("gold_coin_particle", () -> new SimpleParticleType(true));

	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BLACK_SPORE_PARTICLE = PARTICLES.register("black_spore", () -> new SimpleParticleType(false));

	// DOESNT WORK?
	public static void register(IEventBus bus) {
		PARTICLES.register(bus);
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void registerFactories(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(SPANISH_MOSS_PARTICLE.get(), SpanishMossParticle.Provider::new);
		event.registerSpriteSet(MIST_PARTICLE.get(), MistParticle.Provider::new);
		event.registerSpriteSet(BILLOWING_MIST_PARTICLE.get(), BillowingMistParticle.Provider::new);
		event.registerSpriteSet(POISON_MIST_PARTICLE.get(), PoisonMistParticle.Provider::new);
		event.registerSpriteSet(WITHER_MIST_PARTICLE.get(), WitherMistParticle.Provider::new);

		event.registerSpriteSet(COPPER_COIN_PARTICLE.get(), CoinParticle.Provider::new);
		event.registerSpriteSet(SILVER_COIN_PARTICLE.get(), CoinParticle.Provider::new);
		event.registerSpriteSet(GOLD_COIN_PARTICLE.get(), CoinParticle.Provider::new);

		event.registerSpriteSet(BLACK_SPORE_PARTICLE.get(), BlackSporeParticle.Provider::new);
	}
}
