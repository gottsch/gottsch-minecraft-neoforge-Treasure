package mod.gottsch.neoforge.treasure2.core.block.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mod.gottsch.neoforge.treasure2.core.rarity.IRarity;
import mod.gottsch.neoforge.treasure2.core.world.feature.IFeatureType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

/**
 * @author by Mark Gottschling on 11/30/2025
 */
/*
 * this is data that needs to be stored with chest entity, so when the chest is opened
 * for the first time and populated, feature and rarity is known to select the correct
 * loot tables.
 */
public class GenerationContext {
    public static final GenerationContext DEFAULT = new GenerationContext();

    public static final Codec<GenerationContext> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            IRarity.CODEC.fieldOf("lootRarity").forGetter(GenerationContext::getLootRarity),
            IFeatureType.CODEC.fieldOf("featureType").forGetter(GenerationContext::getFeatureType)
    ).apply(instance, GenerationContext::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, GenerationContext> STREAM_CODEC = StreamCodec.composite(
            IRarity.STREAM_CODEC,
            GenerationContext::getLootRarity,

            IFeatureType.STREAM_CODEC,
            GenerationContext::getFeatureType,

            GenerationContext::new
    );

    public GenerationContext() {

    }

    public GenerationContext(IRarity rarity, IFeatureType featureType) {
        this.lootRarity = rarity;
        this.featureType = featureType;
    }

    /*
     * The rarity level of the loot that the chest will contain
     */
    protected IRarity lootRarity;
    protected IFeatureType featureType;

    public IRarity getLootRarity() {
        return lootRarity;
    }

    public IFeatureType getFeatureType() {
        return featureType;
    }

    public void setFeatureType(IFeatureType type) {
        this.featureType = type;
    }
}
