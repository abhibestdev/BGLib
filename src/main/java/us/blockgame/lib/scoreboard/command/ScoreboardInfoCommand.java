package us.blockgame.lib.scoreboard.command;

import org.bukkit.plugin.java.JavaPlugin;
import us.blockgame.lib.LibPlugin;
import us.blockgame.lib.command.framework.Command;
import us.blockgame.lib.command.framework.CommandArgs;
import us.blockgame.lib.scoreboard.BGBoard;
import us.blockgame.lib.scoreboard.ScoreboardHandler;
import us.blockgame.lib.util.CC;

public class ScoreboardInfoCommand {

    @Command(name = "scoreboardinfo", aliases = {"sbinfo"}, permission = "op")
    public void scoreboardInfo(CommandArgs args) {

        ScoreboardHandler scoreboardHandler = LibPlugin.getInstance().getScoreboardHandler();

        //Check if tab provider exists
        if (scoreboardHandler.getBgBoard() == null) {
            args.getSender().sendMessage(CC.RED + "No scoreboard provider found.");
            return;
        }
        BGBoard bgBoard = scoreboardHandler.getBgBoard();
        args.getSender().sendMessage(CC.PRIMARY + "The scoreboard is being provided by " + CC.SECONDARY + bgBoard.getClass().getSimpleName() + CC.PRIMARY + " in the plugin " + CC.SECONDARY + JavaPlugin.getProvidingPlugin(bgBoard.getClass()).getName() + CC.PRIMARY + ".");
        return;
    }
}
