package mod.gottsch.neoforge.treasure2.core.codec;

import com.mojang.serialization.Codec;
import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.core.rarity.IRarity;
import mod.gottsch.neoforge.treasure2.core.rarity.Rarity;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * @author by Mark Gottschling on 11/28/2025
 */
@Deprecated
public class TreasureCodecs {

    // this key defines the type of registry where the values are Codecs.
    public static final ResourceKey<Registry<Codec<? extends IRarity>>> RARITY_CODECS_REGISTRY_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Treasure.MODID, "rarity_codecs"));

    // the DeferredRegister holds the specific Codecs for your concrete classes.
    public static final DeferredRegister<Codec<? extends IRarity>> RARITY_CODECS =
            DeferredRegister.create(RARITY_CODECS_REGISTRY_KEY, Treasure.MODID);

    // register IRarity-based codecs
    public static final DeferredHolder<Codec<? extends IRarity>, Codec<IRarity>> RARITY_CODEC_HOLDER =
            RARITY_CODECS.register("rarity", () -> IRarity.CODEC);


}
