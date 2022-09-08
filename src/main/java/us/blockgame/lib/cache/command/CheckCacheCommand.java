package us.blockgame.lib.cache.command;

import us.blockgame.lib.LibPlugin;
import us.blockgame.lib.cache.CacheHandler;
import us.blockgame.lib.command.framework.Command;
import us.blockgame.lib.command.framework.CommandArgs;
import us.blockgame.lib.util.CC;

import java.util.UUID;

public class CheckCacheCommand {

    @Command(name = "checkcache", aliases = {"ccache"}, permission = "op")
    public void checkCache(CommandArgs args) {
        if (args.length() < 1) {
            args.getSender().sendMessage(CC.RED + "Usage: /" + args.getLabel() + " <uuid:name>");
            return;
        }
        CacheHandler cacheHandler = LibPlugin.getInstance().getCacheHandler();
        String argument = args.getArgs(0);

        //Decipher between uuid and player username
        if (argument.length() > 16) {
            try {
                UUID uuid = UUID.fromString(argument);

                if (cacheHandler.getUsername(uuid).equalsIgnoreCase("null")) {
                    args.getSender().sendMessage(CC.RED + "That UUID was not found in the cache.");
                    return;
                }
                args.getSender().sendMessage(CC.PRIMARY + "That UUID belongs to the player " + CC.SECONDARY + cacheHandler.getUsername(uuid) + CC.PRIMARY + ".");
            } catch (Exception ex) {
                args.getSender().sendMessage(CC.RED + "Error parsing UUID.");
            }
            return;
        }
        if (cacheHandler.getUUID(argument) == null) {
            args.getSender().sendMessage(CC.RED + "That username was not found in the cache.");
            return;
        }
        args.getSender().sendMessage(CC.PRIMARY + "That name belongs to the player with UUID " + CC.SECONDARY + cacheHandler.getUUID(argument).toString() + CC.PRIMARY + ".");
        return;
    }
}
