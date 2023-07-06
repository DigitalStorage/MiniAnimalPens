package me.mangorage.minianimalpens.common.core.penextensions;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashMap;

public class CooldownManager implements INBTSerializable<CompoundTag> {
    private final HashMap<ResourceLocation, Integer> COOLDOWNS = new HashMap<>();
    private boolean registered = false;

    public void lock() {
        this.registered = true;
    }

    public void register(ResourceLocation ID) {
        if (registered)
            throw new IllegalStateException("Cooldown Manager has been locked!");
        if (COOLDOWNS.containsKey(ID))
            throw new IllegalStateException("Already registered Cooldown! " + ID.toString());

        COOLDOWNS.put(ID, 0);
    }

    public boolean isRegistered() {
        return registered;
    }

    public int get(ResourceLocation ID) {
        if (!registered)
            throw new IllegalStateException("Cannot get the cooldown as manager hasnt been locked yet!");
        return COOLDOWNS.getOrDefault(ID, -1);
    }

    public void set(ResourceLocation ID, int value) {
        if (!registered)
            throw new IllegalStateException("Cannot set the cooldown as manager hasnt been locked yet!");
        COOLDOWNS.replace(ID, value);
    }

    public boolean hasCooldown(ResourceLocation ID) {
        return COOLDOWNS.containsKey(ID);
    }

    public void tick() {
        COOLDOWNS.forEach((k, v) -> COOLDOWNS.replace(k, v--));
    }

    @Override
    public CompoundTag serializeNBT() {
        return new CompoundTag();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

    }
}
