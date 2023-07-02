package me.mangorage.minianimalpens.common.blockentities;

import me.mangorage.minianimalpens.common.core.registry.MAPBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class PenBlockEntity extends BlockEntity {
    private ResourceLocation ENTITY_ID = null;
    private int entityCount = 0;

    @OnlyIn(Dist.CLIENT)
    private Entity entityA;

    @OnlyIn(Dist.CLIENT)
    private Entity entityB;


    public PenBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(MAPBlockEntities.PEN_BLOCK_ENTITY.get(), p_155229_, p_155230_);
    }

    public boolean addEntity(ResourceLocation ID, boolean simulate) {
        if (getLevel().isClientSide)
            return false;
        if (ENTITY_ID != null && ENTITY_ID != ID)
            return false;

        if (simulate)
            return true;

        if (ENTITY_ID == null)
            ENTITY_ID = ID;

        entityCount++;
        updateClient();

        return true;
    }

    public boolean breed(ItemStack stack) {
        return false;
    }

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
        if (ENTITY_ID != null) {
            tag.putString("EntityID", ENTITY_ID.toString());
            tag.putInt("EntityCount", entityCount);
        }
        return tag;
    }

    public void onDataLoad(CompoundTag tag) {
        if (tag != null && tag.contains("EntityID")) {
            ResourceLocation location = ResourceLocation.tryParse(tag.getString("EntityID"));
            EntityType entityType = ForgeRegistries.ENTITY_TYPES.getValue(location);
            if (entityType != null) {
                if (tag.contains("EntityCount"))
                    entityCount = tag.getInt("EntityCount");
                entityA = entityType.create(getLevel());

                if (entityCount > 1)
                    entityB = entityType.create(getLevel());
            }
        }
    }

    public void updateClient() {
        getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        updateClient();
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        onDataLoad(pkt.getTag());
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (ENTITY_ID != null) {
            tag.putString("EntityID", ENTITY_ID.toString());
            tag.putInt("EntityCount", entityCount);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag != null && tag.contains("EntityID")) {
            ENTITY_ID = ResourceLocation.tryParse(tag.getString("EntityID"));
            if (tag.contains("EntityCount"))
                entityCount = tag.getInt("EntityCount");
        }
    }
}
