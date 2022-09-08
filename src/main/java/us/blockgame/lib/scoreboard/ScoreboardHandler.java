package us.blockgame.lib.scoreboard;

import lombok.Getter;
import org.bukkit.Bukkit;
import us.blockgame.lib.LibPlugin;
import us.blockgame.lib.command.CommandHandler;
import us.blockgame.lib.scoreboard.command.ScoreboardInfoCommand;
import us.blockgame.lib.scoreboard.impl.ExampleBoard;
import us.blockgame.lib.scoreboard.impl.ExampleBoardListener;
import us.blockgame.lib.scoreboard.task.ScoreboardUpdateTask;
import us.blockgame.lib.tab.command.TabInfoCommand;

public class ScoreboardHandler {

    @Getter private BGBoard bgBoard;

    public ScoreboardHandler() {

        //Register example board
        if (LibPlugin.isExampleMode()) {
            setScoreboard(new ExampleBoard());
            Bukkit.getPluginManager().registerEvents(new ExampleBoardListener(), LibPlugin.getInstance());
        }

        //Register commands
        CommandHandler commandHandler = LibPlugin.getInstance().getCommandHandler();
        commandHandler.registerCommand(new ScoreboardInfoCommand());

        //Register Scoreboard Listener
        Bukkit.getPluginManager().registerEvents(new ScoreboardListener(), LibPlugin.getInstance());
    }

    public void setScoreboard(BGBoard bgBoard) {
        this.bgBoard = bgBoard;

        //Initialize scoreboard task
        new ScoreboardUpdateTask().runTaskTimerAsynchronously(LibPlugin.getInstance(), 0L, bgBoard.getUpdateInterval());
    }

    public boolean isScoreboardEnabled() {
        return bgBoard != null;
    }
}
