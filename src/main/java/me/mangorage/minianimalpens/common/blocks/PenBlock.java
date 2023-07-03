package me.mangorage.minianimalpens.common.blocks;

import me.mangorage.minianimalpens.common.blockentities.PenBlockEntity;
import me.mangorage.minianimalpens.common.core.registry.MAPBlockEntities;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicBoolean;

public class PenBlock extends Block implements EntityBlock {
    public static final DirectionProperty PROP_FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);

    public PenBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(PROP_FACING, Direction.NORTH));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            AtomicBoolean suceed = new AtomicBoolean(false);
            pLevel.getBlockEntity(pPos, MAPBlockEntities.PEN_BLOCK_ENTITY.get()).ifPresent(e -> {
                suceed.set(e.breed(pPlayer.getItemInHand(pHand)));
            });
            if (!suceed.get()) {
                return InteractionResult.PASS;
            }
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
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

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PenBlockEntity(pos, state);
    }
}
