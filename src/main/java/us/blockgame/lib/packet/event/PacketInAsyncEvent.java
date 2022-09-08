package us.blockgame.lib.packet.event;

import lombok.Getter;
import net.minecraft.network.protocol.Packet;
import org.bukkit.entity.Player;
import us.blockgame.lib.event.PlayerEvent;

public class PacketInAsyncEvent extends PlayerEvent {

    @Getter private Packet packet;

    public PacketInAsyncEvent(Player player, Packet packet) {
        super(player);
        this.packet = packet;
    }
}
