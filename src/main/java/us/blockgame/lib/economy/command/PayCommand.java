package us.blockgame.lib.economy.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import us.blockgame.lib.LibPlugin;
import us.blockgame.lib.command.framework.Command;
import us.blockgame.lib.command.framework.CommandArgs;
import us.blockgame.lib.economy.EconomyHandler;
import us.blockgame.lib.util.CC;

public class PayCommand {

    @Command(name = "pay", inGameOnly = true)
    public void pay(CommandArgs args) {
        if (args.length() < 2) {
            args.getSender().sendMessage(ChatColor.RED + "Usage: /" + args.getLabel() + " <player> <amount>");
            return;
        }
        Player target = Bukkit.getPlayer(args.getArgs(0));

        //Make sure target is online
        if (target == null) {
            args.getSender().sendMessage(ChatColor.RED + "Could not find player.");
            return;
        }

        //Make sure player isn't sending money to themself
        if (args.getPlayer() == target) {
            args.getSender().sendMessage(ChatColor.RED + "You cannot send money to yourself!");
            return;
        }
        try {
            double amount = Double.parseDouble(args.getArgs(1));

            if (amount <= 0) {
                args.getSender().sendMessage(ChatColor.RED + "Please enter an amount greater than 0.");
                return;
            }

            EconomyHandler economyHandler = LibPlugin.getInstance().getEconomyHandler();

            Player player = args.getPlayer();
            if (economyHandler.getBalance(player) < amount) {
                args.getSender().sendMessage(ChatColor.RED + "Insufficient funds!");
                return;
            }

            //Set player's new balance
            economyHandler.setBalance(player, economyHandler.getBalance(player) - amount);
            args.getSender().sendMessage(CC.PRIMARY + "You have sent " + CC.SECONDARY + target.getName() + CC.PRIMARY + " a total of " + CC.SECONDARY + economyHandler.getEconomyType().getSymbol() + amount + CC.PRIMARY + ".");

            //Set target's new balance
            economyHandler.setBalance(target, economyHandler.getBalance(target) + amount);
            target.sendMessage(CC.SECONDARY + player.getName() + CC.PRIMARY + " sent you a total of " + CC.SECONDARY + economyHandler.getEconomyType().getSymbol() + amount + CC.PRIMARY + ".");

        } catch (Exception ex) {
            //Amount provided by the user cannot be converted to a double
            args.getSender().sendMessage(ChatColor.RED + "Please enter a valid amount.");
            return;
        }
        return;
    }
}
