package us.blockgame.lib.scoreboard.task;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import us.blockgame.lib.LibPlugin;
import us.blockgame.lib.scoreboard.BGBoard;
import us.blockgame.lib.scoreboard.ScoreHelper;
import us.blockgame.lib.scoreboard.ScoreboardHandler;

public class ScoreboardUpdateTask extends BukkitRunnable {

    public void run() {
        ScoreboardHandler scoreboardHandler = LibPlugin.getInstance().getScoreboardHandler();

        BGBoard bgBoard = scoreboardHandler.getBgBoard();

        Bukkit.getOnlinePlayers().stream().filter(ScoreHelper::hasScore).forEach(p -> {
            ScoreHelper scoreHelper = ScoreHelper.getByPlayer(p);

            try {
                //Update the scoreboard
                scoreHelper.setTitle(bgBoard.getTitle(p));
                scoreHelper.setSlotsFromList(bgBoard.getSlots(p));
            } catch (Exception ex) {
                ex.printStackTrace();
                //Ignore errors :3
            }
        });
    }
}
