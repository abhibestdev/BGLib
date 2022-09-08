package us.blockgame.lib.scoreboard.impl;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import us.blockgame.lib.scoreboard.BGBoard;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExampleBoard implements BGBoard {

    @Override
    public String getTitle(Player player) {
        return ChatColor.GOLD.toString() + ChatColor.BOLD + "BlockGame " + ChatColor.GRAY + "%splitter% " + ChatColor.RESET + "Board";
    }

    @Override
    public List<String> getSlots(Player player) {
        List<String> toReturn = new ArrayList<>();

        long lastEnderpearl = ExampleBoardListener.enderpearlMap.getOrDefault(player.getUniqueId(), 0L);
        if (System.currentTimeMillis() - lastEnderpearl < 16000) {
            int difference = 16 - (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - lastEnderpearl);

            toReturn.add(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "--------------------");
            toReturn.add(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Enderpearl" + ChatColor.GRAY + ": " + ChatColor.RED + difference + "s");
            toReturn.add(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "--------------------");
        }
        return toReturn;
    }

    @Override
    public long getUpdateInterval() {
        return 0L;
    }
}
