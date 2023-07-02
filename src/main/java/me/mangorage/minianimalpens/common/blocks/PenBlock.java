package me.mangorage.minianimalpens.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PenBlock extends Block {
    public static final DirectionProperty PROP_FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);

    public PenBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(PROP_FACING, Direction.NORTH));
    }

    public boolean skipRendering(@NotNull BlockState p_53972_, BlockState p_53973_, @NotNull Direction p_53974_) {
        return p_53973_.is(this) || super.skipRendering(p_53972_, p_53973_, p_53974_);
    }
    private static Direction getFacingFromEntity(BlockPos clickedBlock, LivingEntity entity) {
        return Direction.getNearest(
            (float) (entity.getX() - clickedBlock.getX()),
            0,
            (float) (entity.getZ() - clickedBlock.getZ()));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PROP_FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if (context.getPlayer() != null) {
            return this.defaultBlockState()
                .setValue(PROP_FACING, PenBlock.getFacingFromEntity(context.getClickedPos(), context.getPlayer()));
        }
        return super.getStateForPlacement(context);
    }

    @Nonnull
    public RenderShape getRenderShape(@Nonnull BlockState p_49232_) {
        return RenderShape.MODEL;
    }

}
