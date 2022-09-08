package us.blockgame.lib.menu.command;

import org.bukkit.entity.Player;
import us.blockgame.lib.command.framework.Command;
import us.blockgame.lib.command.framework.CommandArgs;
import us.blockgame.lib.menu.impl.ExampleMenu;

public class TestMenuCommand {

    @Command(name = "testmenu", permission = "op", inGameOnly = true)
    public void testMenu(CommandArgs args) {
        Player player = args.getPlayer();

        //Open example menu
        ExampleMenu exampleMenu = new ExampleMenu();
        exampleMenu.openMenu(player);
    }
}
