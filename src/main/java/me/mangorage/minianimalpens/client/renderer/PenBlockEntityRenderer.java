package me.mangorage.minianimalpens.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import me.mangorage.minianimalpens.common.blockentities.PenBlockEntity;
import me.mangorage.minianimalpens.common.blocks.PenBlock;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
public class PenBlockEntityRenderer implements BlockEntityRenderer<PenBlockEntity> {
    private final BlockRenderDispatcher BRD;
    private final EntityRenderDispatcher ERD;

    public PenBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.BRD = context.getBlockRenderDispatcher();
        this.ERD = context.getEntityRenderer();
    }


    @Override
    public void render(PenBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        Direction facing = pBlockEntity.getBlockState().getValue(PenBlock.PROP_FACING);

        renderEntity(pBlockEntity.getEntityA(), pBlockEntity.getEntityB(), facing, 0, pPartialTick, pPoseStack, pBufferSource, pPackedLight);
    }

    private <T extends Entity> void renderEntity(T entityA, T entityB, Direction facing, int EntityYaw, float partialTicks, PoseStack stack, MultiBufferSource source, int packed) {
        stack.pushPose();

        stack.rotateAround(
                facing.getRotation().rotateX((float)-java.lang.Math.PI/2f),
                0.5f,
                0.5f,
                0.5f
        );


        if (entityA != null && entityB == null) {
            stack.scale(0.3f, 0.3f, 0.3f);
            stack.translate(1.6, 0.0, 1.6);
            ERD.getRenderer(entityA).render(entityA, EntityYaw, partialTicks, stack, source, packed);

        } else if (entityA != null && entityB != null) {
            stack.scale(0.3f, 0.3f, 0.3f);
            stack.translate(1.2, 0.0, 1.6);
            ERD.getRenderer(entityA).render(entityA, EntityYaw, partialTicks, stack, source, packed);
            stack.translate(1.0, 0.0, 0);
            ERD.getRenderer(entityB).render(entityB, EntityYaw, partialTicks, stack, source, packed);
        }

        stack.popPose();
    }
}
