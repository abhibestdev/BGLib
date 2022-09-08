package us.blockgame.lib.economy.command;

import lombok.AllArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import us.blockgame.lib.LibPlugin;
import us.blockgame.lib.cache.CacheHandler;
import us.blockgame.lib.command.framework.Command;
import us.blockgame.lib.command.framework.CommandArgs;
import us.blockgame.lib.economy.EconomyHandler;
import us.blockgame.lib.economy.EconomyType;
import us.blockgame.lib.util.CC;

import java.util.UUID;

@AllArgsConstructor
public class BalanceCommand {

    @Command(name = "balance", aliases = {"money", "bal", "$", "points"}, inGameOnly = true)
    public void balance(CommandArgs args) {
        CacheHandler cacheHandler = LibPlugin.getInstance().getCacheHandler();

        Player player = args.getPlayer();

        UUID uuid = player.getUniqueId();
        if (args.length() > 0 && player.hasPermission("lib.admin")) {
            uuid = cacheHandler.getOnlineOfflineUUID(args.getArgs(0));

            if (uuid == null) {
                args.getSender().sendMessage(ChatColor.RED + "That player has never logged in.");
                return;
            }
        }
        EconomyHandler economyHandler = LibPlugin.getInstance().getEconomyHandler();

        //Send message with players balance
        args.getSender().sendMessage(
                (uuid.equals(player.getUniqueId()) ? CC.PRIMARY + "Your" : CC.SECONDARY + cacheHandler.getUsername(uuid) + "'s")
                        + CC.PRIMARY + (economyHandler.getEconomyType() == EconomyType.POINTS ? " points" : "") + " balance is: " + CC.SECONDARY + economyHandler.getEconomyType().getSymbol() +
                        economyHandler.getOfflineBalance(uuid));
        return;
    }
}
