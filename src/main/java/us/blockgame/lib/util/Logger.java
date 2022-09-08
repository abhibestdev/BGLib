package us.blockgame.lib.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Logger {

    public static void success(String message) {
        Bukkit.getConsoleSender().sendMessage(CC.GREEN + "[BGLib] " + message);
    }

    public static void info(String message) {
        Bukkit.getConsoleSender().sendMessage(CC.YELLOW + "[BGLib] " + message);
    }
    public static void error(String message) {
        Bukkit.getConsoleSender().sendMessage(CC.RED + "[BGLib] " + message);
    }
}
