package me.mangorage.minianimalpens.common.core.registry;

import me.mangorage.minianimalpens.common.blockentities.PenBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static me.mangorage.minianimalpens.common.core.Constants.MOD_ID;

public class MAPBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MOD_ID);

    public static final RegistryObject<BlockEntityType<PenBlockEntity>> PEN_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "pen_block_entity",
            () -> BlockEntityType.Builder.of(
                    PenBlockEntity::new,
                    MAPBlocks.PEN_BLOCK.get()
            ).build(null)
    );
}
