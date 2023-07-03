package me.mangorage.minianimalpens.common.blockentities.penextensions;

import me.mangorage.minianimalpens.common.blockentities.PenBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SimulatedAnimals implements INBTSerializable<CompoundTag> {
    private List<SimulatedAnimal> ANIMALS = new ArrayList<>();

    public boolean breed(PenBlockEntity entity, ItemStack stack) {
        Stream<SimulatedAnimal> ANIMALS_STREAM = ANIMALS.stream().filter(e -> e.canBreed() && e.get().isFood(stack));
        List<SimulatedAnimal> ANIMALS_BREED = ANIMALS_STREAM.toList();

        if (ANIMALS_BREED.size() <= 1 || stack.getCount() <= 1) {
            return false;
        }

        SimulatedAnimal A = ANIMALS_BREED.get(0);
        SimulatedAnimal B = ANIMALS_BREED.get(1);
        SimulatedAnimal BABY = A.breed(B);
        if (BABY != null)
            ANIMALS.add(BABY);
        // Add particle
        // Play Sound
        entity.getLevel().playSound(
                null,
        entity.getBlockPos(),
                SoundEvents.COW_AMBIENT,
                SoundSource.NEUTRAL,
        1,
                1);





        return BABY == null ? false : true;
    }

    public void add(Animal animal) {
        ANIMALS.add(new SimulatedAnimal(animal));
    }

    public int getCount() {
        return ANIMALS.size();
    }


    @Override
    public CompoundTag serializeNBT() {
        return null;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

    }
}
