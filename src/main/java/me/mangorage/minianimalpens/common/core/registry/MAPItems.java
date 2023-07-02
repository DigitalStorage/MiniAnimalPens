package me.mangorage.minianimalpens.common.core.registry;

import me.mangorage.minianimalpens.common.items.CapsuleItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static me.mangorage.minianimalpens.common.core.Constants.MOD_ID;

public class MAPItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    public static final RegistryObject<CapsuleItem> CAPSULE_ITEM = ITEMS.register(
            "capsule",
            () -> new CapsuleItem(
                    new Item.Properties()
            )
    );

}
