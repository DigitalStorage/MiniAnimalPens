package me.mangorage.minianimalpens.common.core.simulatedanimal;

import me.mangorage.minianimalpens.common.core.Util;
import me.mangorage.minianimalpens.common.core.penextensions.CooldownManager;
import me.mangorage.minianimalpens.common.core.penextensions.ExtensionManager;
import me.mangorage.minianimalpens.common.core.penextensions.PenExtension;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashMap;

public class SimulatedAnimal implements INBTSerializable<CompoundTag> {
    private static final String LOVE_NBT = "love";
    private static final String AGE_NBT = "age";
    private static final String MANAGER_NBT = "manager";

    private final Animal animal;
    private int loveCooldown = 0;
    private int age = 0; // negative is baby, positive can not breed, 0 can
    private CooldownManager MANAGER;


    public SimulatedAnimal(Animal animal) {
        this.animal = animal;
        ResourceLocation ID = Util.getID(animal.getType());
        if (ExtensionManager.hasExtension(ID)) {
            MANAGER = new CooldownManager();
            ExtensionManager.getExtension(ID).ifPresent(e -> e.registerCooldowns(MANAGER));
            MANAGER.lock();
        }
    }

    public SimulatedAnimal(Animal animal, int age) {
        this(animal);
        this.age = age;
    }

    public Animal get() {
        return animal;
    }

    public CooldownManager getManager() {
        return MANAGER;
    }

    public SimulatedAnimal breed(SimulatedAnimal B) {
        this.loveCooldown = 100;
        B.loveCooldown = 100;
        return new SimulatedAnimal(animal, -1000);
    }

    public void tick() {
        if (loveCooldown > 0)
            loveCooldown--;
        if (MANAGER != null)
            MANAGER.tick();
    }

    public boolean isBaby() {
        return age < 0;
    }

    public boolean canBreed() {
        return !isBaby() && loveCooldown == 0;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        if (age != 0)
            tag.putInt(AGE_NBT, age);
        if (loveCooldown != 0)
            tag.putInt(LOVE_NBT, loveCooldown);
        if (MANAGER != null)
            tag.put(MANAGER_NBT, MANAGER.serializeNBT());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains(LOVE_NBT))
            this.loveCooldown = tag.getInt(LOVE_NBT);
        if (tag.contains(AGE_NBT))
            this.age = tag.getInt(AGE_NBT);
        if (tag.contains(MANAGER_NBT))
            MANAGER.deserializeNBT(tag.getCompound(MANAGER_NBT));
    }
}
