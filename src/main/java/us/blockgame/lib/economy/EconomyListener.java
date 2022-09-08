package us.blockgame.lib.economy;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import us.blockgame.lib.LibPlugin;

import java.util.concurrent.CompletableFuture;

public class EconomyListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        EconomyHandler economyHandler = LibPlugin.getInstance().getEconomyHandler();

        //Set player's balance
        CompletableFuture.runAsync(() -> {
            economyHandler.setBalance(player, economyHandler.getBalanceFromMongo(player.getUniqueId()));
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        EconomyHandler economyHandler = LibPlugin.getInstance().getEconomyHandler();

        //Save player's balance in mongo
        CompletableFuture.runAsync(() -> {
            economyHandler.setBalanceMongo(player.getUniqueId(), economyHandler.getBalance(player));
        });
    }
}
