package me.mangorage.minianimalpens.common.blockentities;

import me.mangorage.minianimalpens.common.core.simulatedanimal.SimulatedAnimals;
import me.mangorage.minianimalpens.common.core.registry.MAPBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class PenBlockEntity extends BlockEntity {
    private static final String SIMULATED_NBT = "simulated";
    private static final String ENTITY_ID_NBT = "entityid";
    private static final String ENTITY_COUNT_NBT = "count";

    private final SimulatedAnimals SIMULATED_ANIMALS = new SimulatedAnimals();


    @OnlyIn(Dist.CLIENT)
    private Entity entityA;

    @OnlyIn(Dist.CLIENT)
    private Entity entityB;

    @OnlyIn(Dist.CLIENT)
    private int entityCount = 0;


    public PenBlockEntity(BlockPos pos, BlockState state) {
        super(MAPBlockEntities.PEN_BLOCK_ENTITY.get(), pos, state);
    }

    public SimulatedAnimals getSimulatedAnimals() {
        return SIMULATED_ANIMALS;
    }

    public Animal createAnimal(ResourceLocation location) {
        if (ForgeRegistries.ENTITY_TYPES.getValue(location).create(getLevel()) instanceof Animal animal) {
            return animal;
        }
        return null;
    }

    public boolean canCreateAnimal(ResourceLocation location) {
        if (ForgeRegistries.ENTITY_TYPES.getValue(location) != null)
            return true;
        return false;
    }

    public boolean addEntity(ResourceLocation ID, boolean simulate) {
        if (getLevel().isClientSide)
            return false;

        if (!SIMULATED_ANIMALS.isValid(ID) || !canCreateAnimal(ID)) {
            return false;
        }

        if (!simulate) {
            SIMULATED_ANIMALS.add(createAnimal(ID));
            updateAll();
        }

        return true;
    }

    public boolean breed(ItemStack stack) {
        if (SIMULATED_ANIMALS.canBreed()) {
            boolean update = SIMULATED_ANIMALS.breed(this, stack);
            if (update)
                updateAll();
            return update;
        }
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public int getEntityCount() {
        return entityCount;
    }

    @OnlyIn(Dist.CLIENT)
    public Entity getEntityA() {
        return entityA;
    }

    @OnlyIn(Dist.CLIENT)
    public Entity getEntityB() {
        return entityB;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        if (SIMULATED_ANIMALS.getType() != null && SIMULATED_ANIMALS.getCount() > 0) {
            tag.putString(ENTITY_ID_NBT, SIMULATED_ANIMALS.getID().toString());
            tag.putInt(ENTITY_COUNT_NBT, SIMULATED_ANIMALS.getCount());
        }
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        if (tag.contains(ENTITY_ID_NBT)) {
            ResourceLocation ID = ResourceLocation.tryParse(tag.getString(ENTITY_ID_NBT));
            if (canCreateAnimal(ID)) {
                entityA = createAnimal(ID);
                entityB = createAnimal(ID);
                if (tag.contains(ENTITY_COUNT_NBT))
                    entityCount = tag.getInt(ENTITY_COUNT_NBT);
            }
        }
    }

    public void updateClient() {
        getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }

    public void updateAll() {
        setChanged();
        updateClient();
    }

    @Override
    public void onLoad() {
        super.onLoad();
        SIMULATED_ANIMALS.onLoad(getLevel());
        updateClient();
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        CompoundTag tag = pkt.getTag();
        if (tag != null)
            handleUpdateTag(tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (SIMULATED_ANIMALS.getCount() > 0)
            tag.put(SIMULATED_NBT, SIMULATED_ANIMALS.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains(SIMULATED_NBT))
            SIMULATED_ANIMALS.deserializeNBT(tag.getCompound(SIMULATED_NBT));
    }
}
