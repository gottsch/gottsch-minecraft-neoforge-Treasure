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
package mod.gottsch.neoforge.treasure2.core.item;

import mod.gottsch.neo.gottschcore.world.WorldInfo;
import mod.gottsch.neoforge.treasure2.core.block.TreasureBlocks;
import mod.gottsch.neoforge.treasure2.core.particle.TreasureParticles;
import mod.gottsch.neoforge.treasure2.core.tag.TreasureTags;
import mod.gottsch.neoforge.treasure2.core.util.LangUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Mark Gottschling on Aug 19, 2024
 */
public class CloverItem extends Item {

    public CloverItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        Component lore = Component.translatable(LangUtil.tooltip("clover"));
        for (String s : lore.getString().split("~")) {
            tooltip.add(Component.translatable(s).withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (WorldInfo.isClientSide(context.getLevel())) {
            return InteractionResult.FAIL;
        }

        BlockPos pos = context.getClickedPos();
        BlockState state = context.getLevel().getBlockState(pos);

        if (state.is(TreasureTags.Blocks.WISHING_WELL_CANDIDATES)) {
            List<BlockPos> visited = new ArrayList<>();
            Queue<BlockPos> active = new LinkedList<>();
            active.add(pos);

            while (!active.isEmpty()) {
                BlockPos activePos = active.poll();
                Block activeBlock = context.getLevel().getBlockState(activePos).getBlock();
                if (activeBlock.equals(Blocks.MOSSY_COBBLESTONE)) {
                    context.getLevel().setBlockAndUpdate(activePos, TreasureBlocks.WISHING_WELL_MOSSY_COBBLESTONE.get().defaultBlockState());
                } else if (activeBlock.equals(Blocks.COBBLESTONE)) {
                    context.getLevel().setBlockAndUpdate(activePos, TreasureBlocks.WISHING_WELL_COBBLESTONE.get().defaultBlockState());
                } else if (activeBlock.equals(Blocks.MOSSY_STONE_BRICKS)) {
                    context.getLevel().setBlockAndUpdate(activePos, TreasureBlocks.WISHING_WELL_MOSSY_STONE_BRICKS.get().defaultBlockState());
                } else if (activeBlock.equals(Blocks.STONE_BRICKS)) {
                    context.getLevel().setBlockAndUpdate(activePos, TreasureBlocks.WISHING_WELL_STONE_BRICKS.get().defaultBlockState());
                } else {
                    context.getLevel().setBlockAndUpdate(activePos, TreasureBlocks.WISHING_WELL_MOSSY_COBBLESTONE.get().defaultBlockState());
                }

                RandomSource random = context.getLevel().getRandom();
                int coinIndex = random.nextInt(3);
                SimpleParticleType coinParticle = switch (coinIndex) {
                    case 0 -> TreasureParticles.COPPER_COIN_PARTICLE.get();
                    case 1 -> TreasureParticles.SILVER_COIN_PARTICLE.get();
                    default -> TreasureParticles.GOLD_COIN_PARTICLE.get();
                };
                ((ServerLevel) context.getLevel()).sendParticles(coinParticle,
                        (double) activePos.getX() + 0.5D + random.nextDouble() / 3.0D * (double) (random.nextBoolean() ? 1 : -1),
                        (double) activePos.getY() + random.nextDouble() + random.nextDouble(),
                        (double) activePos.getZ() + 0.5D + random.nextDouble() / 3.0D * (double) (random.nextBoolean() ? 1 : -1),
                        0,
                        0.0D, 0.07D, 0.0D, 0.3);

                checkAndAdd(context.getLevel(), pos, activePos.north(), active, visited);
                checkAndAdd(context.getLevel(), pos, activePos.south(), active, visited);
                checkAndAdd(context.getLevel(), pos, activePos.east(), active, visited);
                checkAndAdd(context.getLevel(), pos, activePos.west(), active, visited);
                checkAndAdd(context.getLevel(), pos, activePos.above(), active, visited);
                checkAndAdd(context.getLevel(), pos, activePos.below(), active, visited);

                visited.add(activePos);
            }

            context.getItemInHand().shrink(1);
        }
        return InteractionResult.SUCCESS;
    }

    private void checkAndAdd(Level level, BlockPos originPos, BlockPos targetPos, Queue<BlockPos> active, List<BlockPos> visited) {
        boolean isCandidate = level.getBlockState(targetPos).is(TreasureTags.Blocks.WISHING_WELL_CANDIDATES);
        double dist = originPos.distSqr(targetPos);
        boolean isVisited = visited.contains(targetPos);

        if (isCandidate && dist < 25 && !isVisited) {
            active.add(targetPos);
        }
    }
}
