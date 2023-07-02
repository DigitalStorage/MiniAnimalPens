package me.mangorage.minianimalpens;

import me.mangorage.minianimalpens.client.MiniAnimalPensClientCore;
import me.mangorage.minianimalpens.common.core.registry.Registry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;

import static me.mangorage.minianimalpens.common.core.Constants.MOD_ID;

@Mod(MOD_ID)
@Mod.EventBusSubscriber()
public class MiniAnimalPensCore {
    public MiniAnimalPensCore() {
        Registry.register();
        if (FMLEnvironment.dist.isClient())
            new MiniAnimalPensClientCore();
    }
}
