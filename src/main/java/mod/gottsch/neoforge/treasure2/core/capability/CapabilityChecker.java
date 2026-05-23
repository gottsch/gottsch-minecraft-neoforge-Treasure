package mod.gottsch.neoforge.treasure2.core.capability;


import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import java.util.Optional; // Standard Java Optional

/**
 * @author by Mark Gottschling on 11/26/2025
 */
@Deprecated
public class CapabilityChecker {

    /**
     * Gets the IItemHandler wrapped in a standard Java Optional.
     */
    public static Optional<IItemHandler> getOptionalItemHandler(ItemStack stack) {

        // 1. call the Neoforge API method, which returns @Nullable IItemHandler
        IItemHandler handler = stack.getCapability(
                Capabilities.ItemHandler.ITEM,
                null
        );

        // 2. wrap the result in a standard Java Optional.
        return Optional.ofNullable(handler);
    }
}