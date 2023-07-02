package me.mangorage.minianimalpens.common.core.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class BlockWithItem<B extends Block, I extends BlockItem> {
    public final RegistryObject<I> ITEM;
    public final RegistryObject<B> BLOCK;
    public BlockWithItem(RegistryObject<B> Block, Function<RegistryObject<B>, RegistryObject<I>> Item) {
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
