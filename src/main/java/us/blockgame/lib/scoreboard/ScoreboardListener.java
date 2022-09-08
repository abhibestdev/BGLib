package us.blockgame.lib.scoreboard;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import us.blockgame.lib.LibPlugin;

public class ScoreboardListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        //Create scoreboard if enabled
        ScoreboardHandler scoreboardHandler = LibPlugin.getInstance().getScoreboardHandler();
        if (scoreboardHandler.isScoreboardEnabled()) {
            ScoreHelper.createScore(player);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        //Remove scoreboard for player
        if (ScoreHelper.hasScore(player)) {
            ScoreHelper.removeScore(player);
        }
    }
}
