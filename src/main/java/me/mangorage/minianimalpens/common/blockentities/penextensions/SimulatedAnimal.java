package me.mangorage.minianimalpens.common.blockentities.penextensions;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.animal.Animal;
import net.minecraftforge.common.util.INBTSerializable;

public class SimulatedAnimal implements INBTSerializable<CompoundTag> {
    private final Animal animal;
    private int loveCooldown = 0;
    private int age = 0; // negative is baby, positive can not breed, 0 can

    public SimulatedAnimal(Animal animal) {
        this.animal = animal;
    }

    public SimulatedAnimal(Animal animal, int age) {
        this(animal);
        this.age = age;
    }

    public Animal get() {
        return animal;
    }

    public SimulatedAnimal breed(SimulatedAnimal B) {
        this.loveCooldown = 100;
        B.loveCooldown = 100;
        return new SimulatedAnimal(animal, -1000);
    }

    public void tick() {
        if (loveCooldown > 0)
            loveCooldown--;
    }

    public boolean isBaby() {
        return age < 0;
    }

    public boolean canBreed() {
        return !isBaby() && loveCooldown == 0;
    }

    @Override
    public CompoundTag serializeNBT() {
        return new CompoundTag();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

    }
}
