package syntal.testmod.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import syntal.testmod.TestMod;
import syntal.testmod.utils.IPowerContainer;

public class PacketSyncPower implements IMessage
{
    private int energy;

    @Override
    public void fromBytes(ByteBuf buf) {
        energy = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(energy);
    }

    // Important! Used by the network system internally!
    public PacketSyncPower() {
    }

    public PacketSyncPower(int energy) {
        this.energy = energy;
    }

    public static class Handler implements IMessageHandler<PacketSyncPower, IMessage>
    {
        @Override
        public IMessage onMessage(PacketSyncPower message, MessageContext ctx) {
            TestMod.proxy.addScheduledTaskClient(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketSyncPower message, MessageContext ctx) {
            EntityPlayer player = TestMod.proxy.getClientPlayer();
            if (player.openContainer instanceof IPowerContainer) {
                ((IPowerContainer) player.openContainer).syncPower(message.energy);
            }
        }
    }
}
