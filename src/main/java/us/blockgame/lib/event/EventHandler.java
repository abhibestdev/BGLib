package us.blockgame.lib.event;

import org.bukkit.Bukkit;
import us.blockgame.lib.LibPlugin;

public class EventHandler {

    public EventHandler() {
        //Register listeners
        Bukkit.getPluginManager().registerEvents(new EventListener(), LibPlugin.getInstance());
    }
}
