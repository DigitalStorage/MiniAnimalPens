package me.mangorage.minianimalpens.common.core.penextensions;

import me.mangorage.minianimalpens.common.core.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.Supplier;

public class ExtensionManager {
    private static final HashMap<ResourceLocation, PenExtension> EXTENSIONS = new HashMap<>();

    static {
        EXTENSIONS.put(Util.getID(EntityType.SHEEP), new SheepPenExtension());
    }

    private static void registerExtension(ResourceLocation ID, PenExtension penExtension) {
        if (!EXTENSIONS.containsKey(ID))
            EXTENSIONS.put(ID, penExtension);
    }

    public static boolean hasExtension(ResourceLocation ID) {
        return EXTENSIONS.containsKey(ID);
    }

    public static Optional<PenExtension> getExtension(ResourceLocation ID) {
        return hasExtension(ID) ? Optional.of(EXTENSIONS.get(ID)) : Optional.empty();
    }
}
