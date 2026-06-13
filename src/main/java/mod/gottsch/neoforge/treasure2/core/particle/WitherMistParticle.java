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

import mod.gottsch.neo.gottschcore.spatial.ICoords;
import mod.gottsch.neo.gottschcore.world.WorldInfo;
import mod.gottsch.neoforge.treasure2.core.network.WitherMistMessageToServer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 *
 * @author Mark Gottschling 2021
 *
 */
public class WitherMistParticle extends AbstractCollidingMistParticle {

	public WitherMistParticle(ClientLevel world, double x, double y, double z, ICoords coords) {
		super(world, x, y, z, coords);
		init();
	}

	@Override
	public float getMistAlpha() {
		return 0.4F;
	}

	/**
	 *
	 */
	@Override
	public void inflictEffectOnPlayer(Player player) {
		if (WorldInfo.isServerSide(player.level())) {
			return;
		}

		// if player does not have the wither effect, ask the server to add it
		if (!player.hasEffect(MobEffects.WITHER)) {
			PacketDistributor.sendToServer(new WitherMistMessageToServer(player.getStringUUID()));
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static class Provider implements ParticleProvider<CollidingParticleType> {
		private final SpriteSet spriteSet;

		public Provider(SpriteSet sprite) {
			this.spriteSet = sprite;
		}

		@Override
		public Particle createParticle(CollidingParticleType data, ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			WitherMistParticle particle = new WitherMistParticle(world, x, y, z, data.getSourceCoords());
			particle.pickSprite(spriteSet);
			return particle;
		}
	}
}
