package me.mangorage.minianimalpens.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static me.mangorage.minianimalpens.common.core.Constants.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        final var generator = event.getGenerator();
        final PackOutput output = generator.getPackOutput();
        final var helper = event.getExistingFileHelper();

        //generator.addProvider(event.includeServer(), new RecipeGenerator(output));
        //generator.addProvider(event.includeServer(), new LootTableGenerator(output));
        //generator.addProvider(event.includeServer(), new BlockTagGenerator(output, event.getLookupProvider(), helper));

        generator.addProvider(event.includeClient(), new BlockStateGenerator(output, helper));
        //generator.addProvider(event.includeClient(), new ItemStateGenerator(output, helper));
        //generator.addProvider(event.includeClient(), new LangGenerator(output));
    }
}