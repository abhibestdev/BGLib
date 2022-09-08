package us.blockgame.lib.util;

import org.bukkit.ChatColor;

public class CC {

    public static String PRIMARY = "" + ChatColor.YELLOW;
    public static String SECONDARY = "" + ChatColor.AQUA;
    public static final String RED = "" + ChatColor.RED;
    public static final String DARK_RED = "" + ChatColor.DARK_RED;
    public static final String AQUA = "" + ChatColor.AQUA;
    public static final String DARK_AQUA = "" + ChatColor.DARK_AQUA;
    public static final String BLUE = "" + ChatColor.BLUE;
    public static final String DARK_BLUE = "" + ChatColor.DARK_BLUE;
    public static final String GREEN = "" + ChatColor.GREEN;
    public static final String DARK_GREEN = "" + ChatColor.DARK_GREEN;
    public static final String PINK = "" + ChatColor.LIGHT_PURPLE;
    public static final String PURPLE = "" + ChatColor.DARK_PURPLE;
    public static final String YELLOW = "" + ChatColor.YELLOW;
    public static final String GOLD = "" + ChatColor.GOLD;
    public static final String GRAY = "" + ChatColor.GRAY;
    public static final String DARK_GRAY = "" + ChatColor.DARK_GRAY;
    public static final String WHITE = "" + ChatColor.WHITE;
    public static final String BLACK = "" + ChatColor.BLACK;
    public static final String ITALIC = "" + ChatColor.ITALIC;
    public static final String BOLD = "" + ChatColor.BOLD;
    public static final String STRIKE = "" + ChatColor.STRIKETHROUGH;
    public static final String UNDERLINE = "" + ChatColor.UNDERLINE;
    public static final String RANDOM = "" + ChatColor.MAGIC;
    public static final String RESET = "" + ChatColor.RESET;
    public String from(String string) {
        switch(string.toLowerCase()) {
            case "c":
                return RED;
            case "4":
                return DARK_RED;
            case "b":
                return AQUA;
            case "3":
                return DARK_AQUA;
            case "1":
                return DARK_BLUE;
            case "9":
                return BLUE;
            case "a":
                return GREEN;
            case "2":
                return DARK_GREEN;
            case "d":
                return PINK;
            case "5":
                return PURPLE;
            case "e":
                return YELLOW;
            case "6":
                return GOLD;
            case "7":
                return GRAY;
            case "8":
                return DARK_GRAY;
            case "f":
                return WHITE;
            case "0":
                return BLACK;
            case "o":
                return ITALIC;
            case "l":
                return BOLD;
            case "m":
                return STRIKE;
            case "n":
                return UNDERLINE;
            case "k":
                return RANDOM;
        }
        return WHITE;
    }

    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);

    }

    public static String strip(String message) {
        return ChatColor.stripColor(message);
    }

}
