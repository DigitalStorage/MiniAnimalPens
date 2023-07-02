package me.mangorage.minianimalpens.datagen;

import me.mangorage.minianimalpens.MiniAnimalPensCore;
import me.mangorage.minianimalpens.common.core.Constants;
import me.mangorage.minianimalpens.common.core.registry.MAPBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Objects;
import java.util.function.Function;

import static me.mangorage.minianimalpens.common.core.Constants.MOD_ID;

public class BlockStateGenerator extends BlockStateProvider {
    public BlockStateGenerator(PackOutput gen, ExistingFileHelper exFileHelper) {
        super(gen, MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModelFile controllerModel = models()
            .withExistingParent("block/animal_pen_block",
                MAPBlocks.PEN_BLOCK.getId().withPrefix("block/"))
            .renderType(new ResourceLocation("cutout"));

        horizontalBlock(MAPBlocks.PEN_BLOCK.get(), controllerModel);
        simpleBlockItem(MAPBlocks.PEN_BLOCK.get(), controllerModel);
    }
}
