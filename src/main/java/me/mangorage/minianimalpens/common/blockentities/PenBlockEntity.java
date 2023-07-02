package me.mangorage.minianimalpens.common.blockentities;

import me.mangorage.minianimalpens.common.core.registry.MAPBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class PenBlockEntity extends BlockEntity {
    public PenBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(MAPBlockEntities.PEN_BLOCK_ENTITY.get(), p_155229_, p_155230_);
    }
}
