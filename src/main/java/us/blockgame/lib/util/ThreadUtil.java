package us.blockgame.lib.util;

import org.bukkit.Bukkit;
import us.blockgame.lib.LibPlugin;

public class ThreadUtil {

    public static void runSync(Runnable runnable) {
        Bukkit.getScheduler().runTask(LibPlugin.getInstance(), runnable);
    }

    public static void runAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(LibPlugin.getInstance(), runnable);
    }
}
