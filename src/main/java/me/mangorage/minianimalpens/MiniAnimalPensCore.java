package me.mangorage.minianimalpens;

import me.mangorage.minianimalpens.common.core.registry.Registry;
import net.minecraftforge.fml.common.Mod;
import static me.mangorage.minianimalpens.common.core.Constants.MOD_ID;

@Mod(MOD_ID)
public class MiniAnimalPensCore {
    public MiniAnimalPensCore() {
        Registry.register();
    }
}
