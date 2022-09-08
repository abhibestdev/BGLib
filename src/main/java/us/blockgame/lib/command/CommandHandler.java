package us.blockgame.lib.command;

import us.blockgame.lib.LibPlugin;
import us.blockgame.lib.command.framework.Command;
import us.blockgame.lib.command.framework.CommandFramework;
import us.blockgame.lib.command.impl.BuildCommand;
import us.blockgame.lib.command.impl.CommandInfoCommand;
import us.blockgame.lib.util.StringUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandHandler {

    private CommandFramework commandFramework;
    private List<Object> commands;

    public CommandHandler() {
        //Initialize command framework
        commandFramework = new CommandFramework(LibPlugin.getInstance());

        //Initialize list where commands will be stored
        commands = new ArrayList<>();

        //Register commands
        registerCommand(new CommandInfoCommand());
        registerCommand(new BuildCommand());
    }

    //Method to register a command
    public void registerCommand(Object o) {
        //Register commands
        commandFramework.registerCommands(o);

        //Add command instance to the list of commands
        commands.add(o);
    }

    //Get instance of command from just the name of the command
    public Object getCommand(String name) {
        for (Object o : commands) {
            for (Method method : o.getClass().getMethods()) {
                if (method.getAnnotation(Command.class) != null) {
                    Command command = method.getAnnotation(Command.class);
                    if (command.name().equalsIgnoreCase(name) || StringUtil.contains(command.aliases(), name)) {
                        return o;
                    }
                }
            }
        }
        return null;
    }

    //Return all aliases for a command
    public List<String> getAliases(Object o, String name) {
        List<String> aliases = new ArrayList<>();
        for (Method method : o.getClass().getMethods()) {
            if (method.getAnnotation(Command.class) != null) {
                Command command = method.getAnnotation(Command.class);

                if (command.name().equalsIgnoreCase(name) || StringUtil.contains(command.aliases(), name)) {

                    aliases.add(command.name().toLowerCase());
                    Arrays.stream(command.aliases()).forEach(a -> aliases.add(a.toLowerCase()));
                }
            }
        }
        return aliases;
    }

    //Return permission of a command
    public String getPermission(Object o, String name) {
        for (Method method : o.getClass().getMethods()) {
            if (method.getAnnotation(Command.class) != null) {
                Command command = method.getAnnotation(Command.class);

                if (command.name().equalsIgnoreCase(name) || StringUtil.contains(command.aliases(), name)) {
                    return command.permission();
                }
            }
        }
        return null;
    }
}
