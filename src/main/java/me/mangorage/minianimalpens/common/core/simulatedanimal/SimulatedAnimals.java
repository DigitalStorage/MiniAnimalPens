package me.mangorage.minianimalpens.common.core.simulatedanimal;

import me.mangorage.minianimalpens.common.blockentities.PenBlockEntity;
import me.mangorage.minianimalpens.common.core.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SimulatedAnimals implements INBTSerializable<CompoundTag> {
    private static final String LIST_NBT = "simulatedlist";
    private static final String TYPE_NBT = "type";

    private final List<SimulatedAnimal> ANIMALS = new ArrayList<>();
    private EntityType<? extends Animal> ANIMAL_TYPE = null;
    private ListTag DATA_TO_LOAD = null;


    public List<SimulatedAnimal> getAnimals() {
        return ANIMALS;
    }


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
                1
        );

        return BABY != null;
    }

    public boolean canBreed() {
        return !ANIMALS.isEmpty() || ANIMALS.stream().filter(SimulatedAnimal::canBreed).toList().size() > 1;
    }

    public void add(Animal animal) {
        if (isValid(animal)) {
            if (getType() == null)
                this.ANIMAL_TYPE = (EntityType<? extends Animal>) animal.getType();
            ANIMALS.add(new SimulatedAnimal(animal));
        }
    }

    public void tick() {
        ANIMALS.forEach(SimulatedAnimal::tick);
    }

    public int getCount() {
        return ANIMALS.size();
    }

    public EntityType<? extends Animal> getType() {
        return ANIMAL_TYPE;
    }

    public ResourceLocation getID() {
        return getType() == null ? null : Util.getID(getType());
    }

    public boolean isValid(Animal animal) {
        return getType() == null || getType() == animal.getType();
    }

    public boolean isValid(ResourceLocation animal) {
        return getType() == null || getType() == ForgeRegistries.ENTITY_TYPES.getValue(animal);
    }

    public boolean isValid(EntityType<? extends Animal> animal) {
        return getType() == null || getType() == animal;
    }


    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        if (ANIMALS.size() > 0) {
            ListTag list = new ListTag();
            ANIMALS.forEach(simulatedAnimal -> {
                list.add(simulatedAnimal.serializeNBT());
            });
            tag.put(LIST_NBT, list);
        }
        if (getType() != null)
            tag.putString(TYPE_NBT, getID().toString());

        return tag;
    }

    public void onLoad(Level level) {
        if (level.isClientSide) {
            return;
        }
        if (DATA_TO_LOAD != null && getType() != null) {
            DATA_TO_LOAD.forEach(tag -> {
                SimulatedAnimal animal = new SimulatedAnimal(getType().create(level));
                animal.deserializeNBT((CompoundTag) tag);
                ANIMALS.add(animal);
            });
        }
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains(TYPE_NBT)) {
            ResourceLocation location = ResourceLocation.tryParse(tag.getString(TYPE_NBT));
            EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(location);
            try {
                EntityType<? extends Animal> animalType = (EntityType<? extends Animal>) entityType;
                this.ANIMAL_TYPE = animalType;
            } catch (ClassCastException e) {
                // Tried to but failed!
            }


            if (getType() != null && tag.contains(LIST_NBT))
                this.DATA_TO_LOAD = tag.getList(LIST_NBT, ListTag.TAG_COMPOUND);
        }
    }
}
