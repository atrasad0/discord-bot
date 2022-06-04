package listner.commands.interfaces;

import java.util.Set;

public interface ICommandManager {

    Set<String> commands();

    Set<String> commandsAndHelp();

    Set<ICommand> allControllers();
}
