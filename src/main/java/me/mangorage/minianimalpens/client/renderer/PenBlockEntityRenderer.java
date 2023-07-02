package me.mangorage.minianimalpens.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Transformation;
import me.mangorage.minianimalpens.common.blockentities.PenBlockEntity;
import me.mangorage.minianimalpens.common.blocks.PenBlock;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.joml.Quaternionf;

public class PenBlockEntityRenderer implements BlockEntityRenderer<PenBlockEntity> {
    private enum Type {
        FIRST,
        SECOND,
        CENTER
    }
    private final BlockRenderDispatcher BRD;
    private final EntityRenderDispatcher ERD;
    private Cow COW;

    public PenBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.BRD = context.getBlockRenderDispatcher();
        this.ERD = context.getEntityRenderer();
    }


    @Override
    public void render(PenBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        if (COW == null)
            COW = EntityType.COW.create(pBlockEntity.getLevel());

        Direction facing = pBlockEntity.getBlockState().getValue(PenBlock.PROP_FACING);

        renderEntity(COW, facing, Type.SECOND, 0, pPartialTick, pPoseStack, pBufferSource, pPackedLight);
    }



    private <T extends Entity> void renderEntity(T entity, Direction facing, Type type, int EntityYaw, float partialTicks, PoseStack stack, MultiBufferSource source, int packed) {
        stack.pushPose();

        stack.translate(-0.5f, -0.5f, -0.5f);
        stack.rotateAround(
                facing.getRotation().rotateLocalZ(1.5f),
                1f,
                0,
                1f
        );
        stack.translate(0.5f,0.5f,0.5f);
        stack.pushPose();

        switch (type) {
            case FIRST -> {
                stack.scale(0.3f, 0.3f, 0.3f);

                stack.mulPose(facing.getRotation());
                stack.translate(1.6, 0.0, 1.6);
                ERD.getRenderer(entity).render(entity, EntityYaw, partialTicks, stack, source, packed);
            }
            case SECOND -> {
                stack.scale(0.3f, 0.3f, 0.3f);
                stack.translate(1.2, 0.0, 1.6);
                ERD.getRenderer(entity).render(entity, EntityYaw, partialTicks, stack, source, packed);
                stack.translate(1.0, 0.0, 0);
                ERD.getRenderer(entity).render(entity, EntityYaw, partialTicks, stack, source, packed);
            }
            case CENTER -> {
            }
        }
        stack.popPose();
        stack.popPose();
    }
}
