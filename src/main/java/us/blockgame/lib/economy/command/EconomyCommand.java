package us.blockgame.lib.economy.command;

import org.bukkit.ChatColor;
import us.blockgame.lib.LibPlugin;
import us.blockgame.lib.cache.CacheHandler;
import us.blockgame.lib.command.framework.Command;
import us.blockgame.lib.command.framework.CommandArgs;
import us.blockgame.lib.economy.EconomyHandler;
import us.blockgame.lib.util.CC;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class EconomyCommand {

    @Command(name = "economy", aliases = {"eco"}, permission = "lib.command.economy")
    public void economy(CommandArgs args) {
        //Send economy help
        args.getSender().sendMessage(new String[]{
                ChatColor.RED + "Economy Help:",
                ChatColor.RED + " * /economy set <player> <amount>",
                ChatColor.RED + " * /economy give <player> <amount>",
                ChatColor.RED + " * /economy take <player> <amount>",
        });
    }

    @Command(name = "economy.set", aliases = {"eco.set"}, permission = "lib.command.economy")
    public void economySet(CommandArgs args) {
        if (args.length() < 2) {
            args.getSender().sendMessage(ChatColor.RED + "Usage: /" + args.getLabel() + " <player> <amount>");
            return;
        }
        CacheHandler cacheHandler = LibPlugin.getInstance().getCacheHandler();
        UUID uuid = cacheHandler.getOnlineOfflineUUID(args.getArgs(0));

        if (uuid == null) {
            args.getSender().sendMessage(ChatColor.RED + "That player has never logged in.");
            return;
        }

        try {
            double amount = Double.parseDouble(args.getArgs(1));

            if (amount < 0) {
                args.getSender().sendMessage(ChatColor.RED + "Please enter a positive amount.");
                return;
            }
            EconomyHandler economyHandler = LibPlugin.getInstance().getEconomyHandler();

            //Set player's new balance
            economyHandler.setOfflineBalance(uuid, amount);
            args.getSender().sendMessage(CC.PRIMARY + "You have set " + CC.SECONDARY + cacheHandler.getUsername(uuid) + "'s " + CC.PRIMARY + "balance to " + CC.SECONDARY + economyHandler.getEconomyType().getSymbol() + amount + CC.PRIMARY + ".");

        } catch (Exception ex) {
            //Amount provided by the user cannot be converted to a double
            args.getSender().sendMessage(ChatColor.RED + "Please enter a valid amount.");
            return;
        }
        return;
    }

    @Command(name = "economy.give", aliases = {"eco.give"}, permission = "lib.command.economy")
    public void economyGive(CommandArgs args) {
        if (args.length() < 2) {
            args.getSender().sendMessage(ChatColor.RED + "Usage: /" + args.getLabel() + " <player> <amount>");
            return;
        }
        CacheHandler cacheHandler = LibPlugin.getInstance().getCacheHandler();
        UUID uuid = cacheHandler.getOnlineOfflineUUID(args.getArgs(0));

        if (uuid == null) {
            args.getSender().sendMessage(ChatColor.RED + "That player has never logged in.");
            return;
        }

        try {
            double amount = Double.parseDouble(args.getArgs(1));

            if (amount <= 0) {
                args.getSender().sendMessage(ChatColor.RED + "Please enter an amount greater than 0.");
                return;
            }
            EconomyHandler economyHandler = LibPlugin.getInstance().getEconomyHandler();

            CompletableFuture.runAsync(() -> {
                double currentBalance = economyHandler.getOfflineBalance(uuid);

                //Set player's new balance
                economyHandler.setOfflineBalance(uuid, currentBalance + amount);
                args.getSender().sendMessage(CC.PRIMARY + "You have set " + CC.SECONDARY + cacheHandler.getUsername(uuid) + "'s " + CC.PRIMARY + "balance to " + CC.SECONDARY + economyHandler.getEconomyType().getSymbol() + (currentBalance + amount) + CC.PRIMARY + ".");
            });
        } catch (Exception ex) {
            //Amount provided by the user cannot be converted to a double
            args.getSender().sendMessage(ChatColor.RED + "Please enter a valid amount.");
            return;
        }
        return;
    }

    @Command(name = "economy.take", aliases = {"eco.take"}, permission = "lib.command.economy")
    public void economyTake(CommandArgs args) {
        if (args.length() < 2) {
            args.getSender().sendMessage(ChatColor.RED + "Usage: /" + args.getLabel() + " <player> <amount>");
            return;
        }
        CacheHandler cacheHandler = LibPlugin.getInstance().getCacheHandler();
        UUID uuid = cacheHandler.getOnlineOfflineUUID(args.getArgs(0));

        if (uuid == null) {
            args.getSender().sendMessage(ChatColor.RED + "That player has never logged in.");
            return;
        }

        try {
            double amount = Double.parseDouble(args.getArgs(1));

            if (amount <= 0) {
                args.getSender().sendMessage(ChatColor.RED + "Please enter an amount greater than 0.");
                return;
            }
            EconomyHandler economyHandler = LibPlugin.getInstance().getEconomyHandler();

            CompletableFuture.runAsync(() -> {
                double currentBalance = economyHandler.getOfflineBalance(uuid);

                if (amount > currentBalance) {
                    args.getSender().sendMessage(ChatColor.RED + "That player's balance is less than " + amount + ".");
                    return;
                }

                //Set player's new balance
                economyHandler.setOfflineBalance(uuid, currentBalance - amount);
                args.getSender().sendMessage(CC.PRIMARY + "You have set " + CC.SECONDARY + cacheHandler.getUsername(uuid) + "'s " + CC.PRIMARY + "balance to " + CC.SECONDARY + economyHandler.getEconomyType().getSymbol() + (currentBalance - amount) + CC.PRIMARY + ".");
            });
        } catch (Exception ex) {
            //Amount provided by the user cannot be converted to a double
            args.getSender().sendMessage(ChatColor.RED + "Please enter a valid amount.");
            return;
        }
        return;
    }
}
