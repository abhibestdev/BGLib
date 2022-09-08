package us.blockgame.lib.tab.command;

import org.bukkit.plugin.java.JavaPlugin;
import us.blockgame.lib.LibPlugin;
import us.blockgame.lib.command.framework.Command;
import us.blockgame.lib.command.framework.CommandArgs;
import us.blockgame.lib.tab.BGTab;
import us.blockgame.lib.tab.TabHandler;
import us.blockgame.lib.util.CC;

public class TabInfoCommand {

    @Command(name = "tabinfo", permission = "op")
    public void tabInfo(CommandArgs args) {

        TabHandler tabHandler = LibPlugin.getInstance().getTabHandler();

        //Check if tab provider exists
        if (tabHandler.getBgTab() == null) {
            args.getSender().sendMessage(CC.RED + "No tab provider found.");
            return;
        }
        BGTab bgTab = tabHandler.getBgTab();
        args.getSender().sendMessage(CC.PRIMARY + "The tab is being provided by " + CC.SECONDARY + bgTab.getClass().getSimpleName() + CC.PRIMARY + " in the plugin " + CC.SECONDARY + JavaPlugin.getProvidingPlugin(bgTab.getClass()).getName() + CC.PRIMARY + ".");
        args.getSender().sendMessage(CC.PRIMARY + "The tab is " + (tabHandler.isInitialized() ? CC.GREEN + "currently" : CC.RED + "not currently") + CC.PRIMARY + " initialized.");
        return;
    }
}
