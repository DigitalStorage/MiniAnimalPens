package me.mangorage.minianimalpens.common.core.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import java.util.function.Function;

public class BlockWithItem<B extends Block, I extends BlockItem> {
    public static <B extends Block, I extends BlockItem> BlockWithItem<B, I> create(RegistryObject<B> Block, Function<RegistryObject<B>, RegistryObject<I>> Item) {
        return new BlockWithItem<>(Block, Item);
    }

    public final RegistryObject<I> ITEM;
    public final RegistryObject<B> BLOCK;
    private BlockWithItem(RegistryObject<B> Block, Function<RegistryObject<B>, RegistryObject<I>> Item) {
        this.BLOCK = Block;
        this.ITEM = Item.apply(Block);
    }

    public RegistryObject<I> getItem() {
        return ITEM;
    }

    public RegistryObject<B> getBlock() {
        return BLOCK;
    }
}
