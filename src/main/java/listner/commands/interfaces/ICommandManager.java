package listner.commands.interfaces;

import listner.commands.DisconectChanelCommand;
import listner.commands.HelpCommand;
import listner.commands.JoinChanelCommand;
import listner.commands.SendInfoCommand;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public interface ICommandManager {

    Set<String> allCommands();

    Set<String> allCommandsAndHelp();

    Set<ICommand> allCommandsClassess();

    static Set<ICommand> instantiateAllCommandsClass() {
        return new HashSet<>(Arrays.asList(
            new HelpCommand(),
            new SendInfoCommand(),
            new JoinChanelCommand(),
            new DisconectChanelCommand()
        ));
    }
}
