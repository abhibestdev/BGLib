package us.blockgame.lib.tab;

import org.bukkit.entity.Player;

import java.util.Map;

public interface BGTab {

    String getHeader();

    String getFooter();

    Map<Integer, String> getSlots(Player player);
}
