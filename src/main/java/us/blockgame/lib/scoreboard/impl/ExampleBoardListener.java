package us.blockgame.lib.scoreboard.impl;

import com.google.common.collect.Maps;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import java.util.Map;
import java.util.UUID;

public class ExampleBoardListener implements Listener {

    public static Map<UUID, Long> enderpearlMap = Maps.newHashMap();

    @EventHandler
    public void onProjectile(ProjectileLaunchEvent event) {
        if (!(event.getEntity() instanceof EnderPearl) && !(event.getEntity().getShooter() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getEntity().getShooter();
        enderpearlMap.put(player.getUniqueId(), System.currentTimeMillis());
        return;
    }
}
