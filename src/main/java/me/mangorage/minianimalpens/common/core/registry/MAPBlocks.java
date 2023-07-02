package me.mangorage.minianimalpens.common.core.registry;

import me.mangorage.minianimalpens.common.items.CustomBlockItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static me.mangorage.minianimalpens.common.core.Constants.MOD_ID;

public class MAPBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<Item> ITEMS = MAPItems.ITEMS;

    public static final BlockWithItem<Block, CustomBlockItem> BWI_TEST = BlockWithItem.create(
            BLOCKS.register(
                    "test",
                    () -> new Block(
                            BlockBehaviour.Properties.of()
                    )
            ),
            (B) -> {
                return ITEMS.register(
                        "test",
                        () -> new CustomBlockItem(
                                B.get(),
                                new Item.Properties()
                        )
                );
            }
    );


}
