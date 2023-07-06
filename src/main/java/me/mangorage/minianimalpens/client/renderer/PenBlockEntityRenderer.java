package me.mangorage.minianimalpens.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import me.mangorage.minianimalpens.common.blockentities.PenBlockEntity;
import me.mangorage.minianimalpens.common.blocks.PenBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Sheep;
import org.joml.Matrix4f;

import java.awt.*;

public class PenBlockEntityRenderer implements BlockEntityRenderer<PenBlockEntity> {
    private final BlockRenderDispatcher BRD;
    private final EntityRenderDispatcher ERD;
    private final Font FONT;


    public PenBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.BRD = context.getBlockRenderDispatcher();
        this.ERD = context.getEntityRenderer();
        this.FONT = context.getFont();
    }


    @Override
    public void render(PenBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        Direction facing = pBlockEntity.getBlockState().getValue(PenBlock.PROP_FACING);

        renderText("" + pBlockEntity.getEntityCount(), facing, pPoseStack, pBufferSource, pPackedLight);
        renderEntity(pBlockEntity, facing, 0, pPartialTick, pPoseStack, pBufferSource, pPackedLight);
    }

    private void renderText(String string, Direction direction, PoseStack stack, MultiBufferSource source, int packedLight) {
        stack.pushPose();
        stack.translate(0.5F, 0.8F, 0.5F);
        stack.mulPose(ERD.cameraOrientation());
        stack.scale(-0.025F, -0.025F, 0.025F);
        float width = (float)(-FONT.width(string) / 2);

        FONT.drawInBatch(
                string,
                width,
                0f,
                Color.BLUE.getRGB(),
                false,
                stack.last().pose(),
                source,
                Font.DisplayMode.SEE_THROUGH,
                0,
                packedLight,
                false
        );

        stack.popPose();
    }

    private <T extends Entity> void renderEntity(PenBlockEntity BE, Direction facing, int EntityYaw, float partialTicks, PoseStack stack, MultiBufferSource source, int packed) {
        stack.pushPose();
        stack.rotateAround(
                facing.getRotation().rotateX((float)-java.lang.Math.PI/2f),
                0.5f,
                0.5f,
                0.5f
        );

        if (BE.getEntityCount() == 1) {
            Entity entityA = BE.getEntityA();
            if (entityA instanceof Sheep sheep)
                sheep.setSheared(true);
            stack.scale(0.3f, 0.3f, 0.3f);
            stack.translate(1.6, 0.0, 1.6);
            ERD.getRenderer(entityA).render(entityA, EntityYaw, partialTicks, stack, source, packed);

        } else if (BE.getEntityCount() > 1) {
            Entity entityA = BE.getEntityA();
            Entity entityB = BE.getEntityB();
            stack.scale(0.3f, 0.3f, 0.3f);
            stack.translate(1.2, 0.0, 1.6);
            ERD.getRenderer(entityA).render(entityA, EntityYaw, partialTicks, stack, source, packed);
            stack.translate(1.0, 0.0, 0);
            ERD.getRenderer(entityB).render(entityB, EntityYaw, partialTicks, stack, source, packed);
        }
        stack.popPose();
    }
}
