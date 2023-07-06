package me.mangorage.minianimalpens.common.core.penextensions;

import me.mangorage.minianimalpens.common.core.simulatedanimal.SimulatedAnimal;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;

import java.util.List;

import static me.mangorage.minianimalpens.common.core.Constants.MOD_ID;

public class SheepPenExtension extends PenExtension {
    private final ResourceLocation SHEAR_COOLDOWN = new ResourceLocation(MOD_ID, "shear");

    @Override
    public void registerCooldowns(CooldownManager manager) {
        manager.register(SHEAR_COOLDOWN);
    }

    @Override
    public void interact(List<SimulatedAnimal> animals, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        animals.stream().filter(e -> e.getManager().get(SHEAR_COOLDOWN) == 0).findAny().ifPresent(e -> {
            if (stack.is(Tags.Items.SHEARS)) {
                player.level().playSound(
                        null,
                        player.getOnPos(),
                        SoundEvents.SHEEP_SHEAR,
                        SoundSource.NEUTRAL,
                        1,
                        1
                );

                e.getManager().set(SHEAR_COOLDOWN, 1000);
            }
        });
    }
}
