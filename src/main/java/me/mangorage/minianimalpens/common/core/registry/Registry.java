package me.mangorage.minianimalpens.common.core.registry;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class Registry {
    public static void register() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        MAPBlocks.BLOCKS.register(bus);
        MAPBlocks.ITEMS.register(bus);
        MAPBlockEntities.BLOCK_ENTITY_TYPES.register(bus);
    }
}
