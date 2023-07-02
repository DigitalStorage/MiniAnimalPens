package me.mangorage.minianimalpens.client;

import me.mangorage.minianimalpens.client.renderer.PenBlockEntityRenderer;
import me.mangorage.minianimalpens.common.core.registry.MAPBlockEntities;
import me.mangorage.minianimalpens.common.core.registry.MAPBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.Bindings;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;
import org.jetbrains.annotations.Nullable;

public class MiniAnimalPensClientCore {

    public MiniAnimalPensClientCore() {
        IEventBus Forgebus = Bindings.getForgeBus().get();
        IEventBus Modbus = FMLJavaModLoadingContext.get().getModEventBus();
        Modbus.addListener(this::register);
        Modbus.addListener(this::registerRenderers);

    }

    public void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(
                MAPBlockEntities.PEN_BLOCK_ENTITY.get(),
                PenBlockEntityRenderer::new
        );
    }

    public void register(RegisterColorHandlersEvent.Block event) {
        BlockColor color = new BlockColor() {
            @Override
            public int getColor(BlockState p_92567_, @Nullable BlockAndTintGetter p_92568_, @Nullable BlockPos p_92569_, int p_92570_) {
                return event.getBlockColors().getColor(Blocks.GRASS_BLOCK.defaultBlockState(), Minecraft.getInstance().level, p_92569_);
            }
        };
        event.register(color, MAPBlocks.PEN_BLOCK.get());
    }
}
