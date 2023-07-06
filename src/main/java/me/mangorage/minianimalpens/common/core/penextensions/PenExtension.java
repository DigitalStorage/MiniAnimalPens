package me.mangorage.minianimalpens.common.core.penextensions;

import me.mangorage.minianimalpens.common.core.simulatedanimal.SimulatedAnimal;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public abstract class PenExtension {
    abstract public void registerCooldowns(CooldownManager manager);
    abstract public void interact(List<SimulatedAnimal> animals, Player player, InteractionHand hand);

    public void handle(Entity entityA, Entity entityB) {

    }
}
