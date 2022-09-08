package us.blockgame.lib.tab.impl;

import com.google.common.collect.Maps;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import us.blockgame.lib.tab.BGTab;

import java.util.Map;

public class ExampleTab implements BGTab {

    @Override
    public String getHeader() {
        return ChatColor.GOLD.toString() + ChatColor.BOLD + "BlockGame";
    }

    @Override
    public String getFooter() {
        return ChatColor.GOLD + "www.blockgame.us";
    }

    @Override
    public Map<Integer, String> getSlots(Player player) {
        Map<Integer, String> tabSlots = Maps.newHashMap();
        for (int i = 1; i <= 80; i++) {
            tabSlots.put(i, "Slot " + i);
        }
        return tabSlots;
    }
}
