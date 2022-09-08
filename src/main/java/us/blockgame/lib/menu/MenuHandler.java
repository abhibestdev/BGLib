package us.blockgame.lib.menu;

import org.bukkit.Bukkit;
import us.blockgame.lib.LibPlugin;
import us.blockgame.lib.command.CommandHandler;
import us.blockgame.lib.menu.command.TestMenuCommand;

public class MenuHandler {

    public MenuHandler() {

        /**
         * Menu API from Zoot
         */

        //Register commands
        CommandHandler commandHandler = LibPlugin.getInstance().getCommandHandler();
        commandHandler.registerCommand(new TestMenuCommand());

        //Register listeners
        Bukkit.getPluginManager().registerEvents(new MenuListener(), LibPlugin.getInstance());
    }
}
