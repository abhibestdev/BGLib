package us.blockgame.lib.command.impl;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import us.blockgame.lib.LibPlugin;
import us.blockgame.lib.command.framework.Command;
import us.blockgame.lib.command.framework.CommandArgs;
import us.blockgame.lib.util.CC;

public class BuildCommand {

    @Command(name = "build", aliases = {"builder", "building"}, permission = "lib.command.build", inGameOnly = true)
    public void build(CommandArgs args) {
        Player player = args.getPlayer();

        //Add or remove build metadata depending on whether the user already possess it
        if (player.hasMetadata("build")) {
            player.removeMetadata("build", LibPlugin.getInstance());
        } else {
            player.setMetadata("build", new FixedMetadataValue(LibPlugin.getInstance(), true));
        }
        player.sendMessage(CC.PRIMARY + "You are " + (player.hasMetadata("build") ? CC.GREEN + "now" : CC.RED + "no longer") + CC.PRIMARY + " in build mode.");
        return;
    }
}
