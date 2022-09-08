package us.blockgame.lib.command.impl;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import us.blockgame.lib.LibPlugin;
import us.blockgame.lib.command.CommandHandler;
import us.blockgame.lib.command.framework.Command;
import us.blockgame.lib.command.framework.CommandArgs;
import us.blockgame.lib.util.CC;
import us.blockgame.lib.util.StringUtil;

import java.util.List;

public class CommandInfoCommand {

    @Command(name = "commandinfo", aliases = {"cmdinfo"}, permission = "op")
    public void commandInfo(CommandArgs args) {
        if (args.length() < 1) {
            args.getSender().sendMessage(ChatColor.RED + "Usage: /" + args.getLabel() + " <command>");
            return;
        }
        CommandHandler commandHandler = LibPlugin.getInstance().getCommandHandler();
        String node = args.getArgs(0).toLowerCase();

        Object command = commandHandler.getCommand(node);

        //Check if command exists
        if (command == null) {
            args.getSender().sendMessage(ChatColor.RED + "Command not found.");
            return;
        }
        //Get permission of command
        String permission = commandHandler.getPermission(command, node);

        //Get all aliases of command
        List<String> aliases = commandHandler.getAliases(command, node);
        aliases.remove(node);

        //Return the name of the plugin that owns this command
        args.getSender().sendMessage(CC.PRIMARY + "Command " + CC.SECONDARY + node + CC.PRIMARY + " belongs to the plugin " + CC.SECONDARY + JavaPlugin.getProvidingPlugin(command.getClass()).getName() + CC.PRIMARY + ".");
        if (!permission.isEmpty()) {
            args.getSender().sendMessage(CC.PRIMARY + "Permission: " + CC.SECONDARY + permission);
        }
        if (aliases.size() > 0) {
            args.getSender().sendMessage(CC.PRIMARY + "Aliases: " + CC.RESET + StringUtil.join(aliases, ", "));
        }
        return;
    }
}
