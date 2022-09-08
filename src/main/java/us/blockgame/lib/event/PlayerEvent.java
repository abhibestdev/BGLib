package us.blockgame.lib.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import us.blockgame.lib.event.BaseEvent;

@Getter
@AllArgsConstructor
public class PlayerEvent extends BaseEvent {

    private Player player;
}
