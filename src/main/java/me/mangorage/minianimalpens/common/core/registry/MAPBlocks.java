package me.mangorage.minianimalpens.common.core.registry;

import me.mangorage.minianimalpens.common.blocks.PenBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static me.mangorage.minianimalpens.common.core.Constants.MOD_ID;

public class MAPBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<Item> ITEMS = MAPItems.ITEMS;

    public static final RegistryObject<PenBlock> PEN_BLOCK = BLOCKS.register(
            "animal_pen",
            () -> new PenBlock(
                    BlockBehaviour.Properties.of()
            )
    );

    public static final RegistryObject<BlockItem> PEN_BLOCK_ITEM = ITEMS.register(
            "animal_pen",
            () -> new BlockItem(
                    PEN_BLOCK.get(),
                    new Item.Properties().stacksTo(65)
            )
    );
}
