package me.mangorage.minianimalpens.common.core.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static me.mangorage.minianimalpens.common.core.Constants.MOD_ID;

public class Registry {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final RegistryObject<CreativeModeTab> MAIN_TAB = TABS.register(
            "main",
            () -> CreativeModeTab.builder()
                    .title(Component.literal("Mini Animal Farms"))
                    .icon(() -> MAPBlocks.PEN_BLOCK_ITEM.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(MAPBlocks.PEN_BLOCK_ITEM.get());
                        output.accept(MAPItems.CAPSULE_ITEM.get());
                    })
                    .build()
    );

    public static void register() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        TABS.register(bus);
        MAPBlocks.BLOCKS.register(bus);
        MAPBlocks.ITEMS.register(bus);
        MAPBlockEntities.BLOCK_ENTITY_TYPES.register(bus);
    }
}
