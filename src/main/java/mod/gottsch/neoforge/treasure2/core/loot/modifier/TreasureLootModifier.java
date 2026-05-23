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
package mod.gottsch.neoforge.treasure2.core.loot.modifier;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mod.gottsch.neo.gottschcore.spatial.Coords;
import mod.gottsch.neoforge.treasure2.core.config.Config;
import mod.gottsch.neoforge.treasure2.core.loot.ILootGenerator;
import mod.gottsch.neoforge.treasure2.core.loot.TreasureLootGenerators;
import mod.gottsch.neoforge.treasure2.core.loot.TreasureLootTableTypes;
import mod.gottsch.neoforge.treasure2.core.rarity.IRarity;
import mod.gottsch.neoforge.treasure2.core.rarity.TreasureRarities;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

/**
 *
 * @author Mark Gottschling Jun 12, 2023
 *
 */
public class TreasureLootModifier extends LootModifier {

    public static final Supplier<MapCodec<TreasureLootModifier>> CODEC = Suppliers.memoize(()
            -> RecordCodecBuilder.mapCodec(inst -> codecStart(inst)
            .and(Codec.INT.fieldOf("count").forGetter(m -> m.count))
            .and(ResourceLocation.CODEC.fieldOf("rarity").forGetter(m -> m.rarityName))
            .and(Codec.DOUBLE.fieldOf("chance").forGetter(m -> m.chance))
            .apply(inst, TreasureLootModifier::new)));

    private final int count;
    private final ResourceLocation rarityName;
    private final double chance;

    protected TreasureLootModifier(LootItemCondition[] conditionsIn, int count, ResourceLocation rarityName, double chance) {
        super(conditionsIn);
        this.count = count;
        this.rarityName = rarityName;
        this.chance = chance;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        IRarity rarity = TreasureRarities.getRarityByName(this.rarityName).orElseGet(() -> TreasureRarities.UNKNOWN.get());

        if (Config.SERVER.wealth.enableVanillaLootModifiers.get()
                && context.getRandom().nextDouble() < chance) {
            Vec3 vec3 = context.getParam(LootContextParams.ORIGIN);
            ILootGenerator lootGenerator = TreasureLootGenerators.GLOBAL_MODIFIER;
            Pair<List<ItemStack>, List<ItemStack>> lootStacks = lootGenerator.generateLoot(
                    context.getLevel(),
                    context.getLevel().getRandom(),
                    TreasureLootTableTypes.CHESTS.get(),
                    rarity,
                    null,
                    new Coords(vec3));

            if (lootStacks != null) {
                for (int index = 0; index < Math.min(count, lootStacks.getLeft().size()); index++) {
                    generatedLoot.add(lootStacks.getLeft().get(index));
                }
            }
        }
        return generatedLoot;
    }
}
