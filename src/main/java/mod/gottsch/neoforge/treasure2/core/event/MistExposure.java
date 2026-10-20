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
package mod.gottsch.neoforge.treasure2.core.event;

import mod.gottsch.neoforge.treasure2.core.block.SpanishMossBlock;
import mod.gottsch.neoforge.treasure2.core.block.WitherRootBlock;
import mod.gottsch.neoforge.treasure2.core.config.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Poison and wither mist, decided on the server.
 * <p>
 * The mist used to be detected by the particles on the client, which then asked the server for the effect. A modified
 * client could simply never ask. Now the server looks for the mist's sources round each player and applies the effect
 * itself, and the particles are only a picture of it.
 * <p>
 * The server can't see the particles, so it models where they go, from the particles' own numbers:
 * <ul>
 * <li>An activated Witherwood Root puts wither mist up to a block either side of it, just above its base. The mist
 * sinks to the ground and drifts at up to 0.009 a tick for 320 ticks, and touches a player within half a block.
 * So it reaches a player standing within about 2.5 blocks of the root, at the root's level. Torches thin it, as they
 * thin the particles ({@link mod.gottsch.neoforge.treasure2.core.block.IMistSupport#isMistAllowed}).</li>
 * <li>Activated Spanish Moss drops poison mist straight down. It lands on the first solid block below and drifts from
 * there, so it reaches a player standing on that ground within about 2 blocks of the moss.</li>
 * </ul>
 * Each check is a chance, not a certainty, so standing in the mist poisons in a second or two by a root, and in several
 * seconds under moss, which emits far less often. Both follow the server's {@code witherTrees} fog settings.
 *
 * @author Mark Gottschling on 10/20/2026
 */
public class MistExposure {
	/** how often each player is checked, in ticks */
	public static final int CHECK_INTERVAL = 10;
	/** the effect a dose of mist gives, as the old mist messages did */
	private static final int EFFECT_DURATION = 300;

	/** how far wither mist reaches from a root, centre to centre */
	private static final double ROOT_REACH = 2.5;
	/** the chance, per check, that a root's mist touches a player in reach */
	private static final float ROOT_CHANCE = 0.25F;

	/** how far poison mist spreads from where it lands below the moss */
	private static final double MOSS_REACH = 2.0;
	/** the furthest moss can be above the ground its mist lands on */
	private static final int MOSS_FALL = 16;
	/** the chance, per check, that one moss's mist touches a player in reach */
	private static final float MOSS_CHANCE = 0.05F;

	/** mist lies on the ground; it touches feet from 2 blocks below it to just above it */
	private static final double BELOW_MIST = 2.0;
	private static final double ABOVE_MIST = 0.6;

	private MistExposure() {}

	/**
	 * Gives the player wither or poison if they're standing in mist. Call it every {@link #CHECK_INTERVAL} ticks.
	 */
	public static void check(ServerPlayer player, RandomSource random) {
		if (player.isSpectator() || !player.isAlive()) {
			return;
		}
		Level level = player.level();
		boolean wither = Config.SERVER.witherTree.enableWitherFog.get() && !player.hasEffect(MobEffects.WITHER);
		boolean poison = Config.SERVER.witherTree.enablePoisonFog.get() && !player.hasEffect(MobEffects.POISON);
		if (!wither && !poison) {
			return;
		}
		BlockPos feet = player.blockPosition();
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

		if (wither) {
			int r = (int) Math.ceil(ROOT_REACH);
			search:
			for (int dx = -r; dx <= r; dx++) {
				for (int dz = -r; dz <= r; dz++) {
					// a root's mist touches feet from 2 below its base to just above it
					for (int dy = -1; dy <= 2; dy++) {
						pos.set(feet.getX() + dx, feet.getY() + dy, feet.getZ() + dz);
						BlockState state = level.getBlockState(pos);
						if (state.getBlock() instanceof WitherRootBlock root && state.getValue(WitherRootBlock.ACTIVATED)
								&& inReach(player, pos, pos.getY(), ROOT_REACH)
								&& random.nextFloat() < ROOT_CHANCE
								&& root.isMistAllowed(level, random, pos.getX(), pos.getY(), pos.getZ())) {
							give(player, MobEffects.WITHER);
							break search;
						}
					}
				}
			}
		}

		if (poison) {
			int r = (int) Math.ceil(MOSS_REACH);
			search:
			for (int dx = -r; dx <= r; dx++) {
				for (int dz = -r; dz <= r; dz++) {
					for (int dy = 0; dy <= MOSS_FALL; dy++) {
						pos.set(feet.getX() + dx, feet.getY() + dy, feet.getZ() + dz);
						BlockState state = level.getBlockState(pos);
						if (state.getBlock() instanceof SpanishMossBlock && state.getValue(SpanishMossBlock.ACTIVATED)) {
							int landing = landing(level, pos);
							if (landing != Integer.MIN_VALUE && inReach(player, pos, landing, MOSS_REACH)
									&& random.nextFloat() < MOSS_CHANCE) {
								give(player, MobEffects.POISON);
								break search;
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Whether a player is within mist lying at height {@code mistY} that has spread {@code reach} from the centre of
	 * {@code source}'s column.
	 */
	private static boolean inReach(ServerPlayer player, BlockPos source, double mistY, double reach) {
		double dx = player.getX() - (source.getX() + 0.5);
		double dz = player.getZ() - (source.getZ() + 0.5);
		double feet = player.getY();
		return dx * dx + dz * dz <= reach * reach && feet >= mistY - BELOW_MIST && feet <= mistY + ABOVE_MIST;
	}

	/**
	 * The height of the ground that mist dropped from this moss lands on: the top of the first block below it with
	 * anything to collide with. {@code Integer.MIN_VALUE} if it falls further than {@link #MOSS_FALL}.
	 */
	private static int landing(Level level, BlockPos moss) {
		BlockPos.MutableBlockPos pos = moss.mutable();
		for (int i = 1; i <= MOSS_FALL; i++) {
			pos.move(0, -1, 0);
			if (!level.getBlockState(pos).getCollisionShape(level, pos).isEmpty()) {
				return pos.getY() + 1;
			}
		}
		return Integer.MIN_VALUE;
	}

	private static void give(ServerPlayer player, Holder<MobEffect> effect) {
		player.addEffect(new MobEffectInstance(effect, EFFECT_DURATION, 0));
	}
}
