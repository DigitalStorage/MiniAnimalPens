package me.mangorage.minianimalpens.common.core;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

public class Util {
    public static final ResourceLocation getID(final EntityType<?> type) {
        return ForgeRegistries.ENTITY_TYPES.getKey(type);
    }
}
