package us.blockgame.lib.tab;

import io.github.thatkawaiisam.ziggurat.Ziggurat;
import lombok.Getter;
import us.blockgame.lib.LibPlugin;
import us.blockgame.lib.command.CommandHandler;
import us.blockgame.lib.tab.command.TabInfoCommand;
import us.blockgame.lib.tab.impl.ExampleTab;

public class TabHandler {

    @Getter private BGTab bgTab;
    @Getter private boolean initialized;
    private Ziggurat ziggurat;

    public TabHandler() {
        //Register tab example
        if (LibPlugin.isExampleMode()) {
            setTab(new ExampleTab());
        }

        //Register commands
        CommandHandler commandHandler = LibPlugin.getInstance().getCommandHandler();
        commandHandler.registerCommand(new TabInfoCommand());
    }

    //Set the BGTab object
    public void setTab(BGTab bgTab) {
        this.bgTab = bgTab;
    }

    //Initialize the tab with ziggurat
    public void initialize() {
        ziggurat = new Ziggurat(LibPlugin.getInstance(), new TabAdapter(bgTab));
        ziggurat.setTicks(5L);
        ziggurat.setHook(true);

        //Tab is initialized
        this.initialized = true;
    }

    //Kill the tab
    public void kill() {
        ziggurat.disable();

        //Tab is no longer initialized
        this.initialized = false;
    }
}
