package com.garam.pasterdelight.network.cookingpotpacket;

import com.garam.pasterdelight.world.block.pasterdelightcookingpot.PasterDelightCookingPotBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record SyncIsInputSlotLimitStackSizePacket(BlockPos blockPosition, boolean isLimited)
{
    public static void encode(SyncIsInputSlotLimitStackSizePacket packet, FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(packet.blockPosition);
        buffer.writeBoolean(packet.isLimited);
    }

    public static SyncIsInputSlotLimitStackSizePacket decode(FriendlyByteBuf buf)
    {
        return new SyncIsInputSlotLimitStackSizePacket(buf.readBlockPos(), buf.readBoolean());
    }

    public static void handle(SyncIsInputSlotLimitStackSizePacket packet, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
        {
            ServerPlayer player = context.get().getSender();
            if (player != null)
            {
                Level level = player.level();
                if (level.isLoaded(packet.blockPosition))
                {
                    BlockEntity blockEntity = level.getBlockEntity(packet.blockPosition);
                    if (blockEntity instanceof PasterDelightCookingPotBlockEntity pasterDelightCookingPotBlockEntity)
                    {
                        pasterDelightCookingPotBlockEntity.setIsInputSlotLimitStackSize(packet.isLimited);
                        pasterDelightCookingPotBlockEntity.setChanged();

                        BlockState blockState = level.getBlockState(packet.blockPosition);
                        level.sendBlockUpdated(packet.blockPosition, blockState, blockState, 2);
                    }
                }
            }
        });
        context.get().setPacketHandled(true);
    }
}
