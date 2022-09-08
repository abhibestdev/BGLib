package us.blockgame.lib.scoreboard;

import org.bukkit.entity.Player;

import java.util.List;

public interface BGBoard {

    String getTitle(Player player);

    List<String> getSlots(Player player);

    long getUpdateInterval();
}
