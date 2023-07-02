package me.mangorage.minianimalpens.common.items;

import me.mangorage.minianimalpens.common.core.registry.MAPBlockEntities;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraftforge.registries.ForgeRegistries;

public class CapsuleItem extends Item {
    public CapsuleItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (!pContext.getLevel().isClientSide) {
            pContext.getLevel().getBlockEntity(pContext.getClickedPos(), MAPBlockEntities.PEN_BLOCK_ENTITY.get()).ifPresent(
                    entity -> entity.addEntity(ForgeRegistries.ENTITY_TYPES.getKey(EntityType.PIG), false)
            );
        }

        return super.useOn(pContext);
    }
}
