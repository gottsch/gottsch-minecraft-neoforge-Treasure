package mod.gottsch.neoforge.treasure2.core.capability;

import mod.gottsch.neoforge.treasure2.Treasure;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.items.IItemHandler;

/**
 * @author by Mark Gottschling on 12/1/2025
 */
public class TreasureCapabilities {

    // defines the Sided Block Capability for IItemHandler.
    // this allows hoppers and other blocks to interact with our inventory from different sides.
    public static final BlockCapability<IItemHandler, Direction> ITEM_HANDLER_BLOCK =
            BlockCapability.createSided(
                    ResourceLocation.fromNamespaceAndPath(Treasure.MODID, "inventory_handler"),
                    IItemHandler.class
            );

}
