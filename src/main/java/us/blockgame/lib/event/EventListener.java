package us.blockgame.lib.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import us.blockgame.lib.event.impl.PlayerLeftClickEvent;
import us.blockgame.lib.event.impl.PlayerRightClickEvent;

public class EventListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        switch (event.getAction()) {
            case LEFT_CLICK_AIR:
            case LEFT_CLICK_BLOCK: {

                //Call Left click event
                PlayerLeftClickEvent playerLeftClickEvent = new PlayerLeftClickEvent(player, event.getAction() == Action.LEFT_CLICK_AIR, event.getClickedBlock());
                playerLeftClickEvent.call();
                event.setCancelled(playerLeftClickEvent.isCancelled());
                break;
            }
            case RIGHT_CLICK_AIR:
            case RIGHT_CLICK_BLOCK: {

                //Call right click event
                PlayerRightClickEvent playerRightClickEvent = new PlayerRightClickEvent(player, event.getAction() == Action.RIGHT_CLICK_AIR, event.getClickedBlock());
                playerRightClickEvent.call();
                event.setCancelled(playerRightClickEvent.isCancelled());
                break;
            }
        }
    }
}
