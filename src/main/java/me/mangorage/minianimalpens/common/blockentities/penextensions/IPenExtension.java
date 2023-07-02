package me.mangorage.minianimalpens.common.blockentities.penextensions;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;

public interface IPenExtension {
    boolean interact(ItemStack stack);
    EntityType getEntityType();
}
