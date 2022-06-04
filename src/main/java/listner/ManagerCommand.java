package listner;

import DiscordBotConstants.DiscordBotConstants;
import listner.commands.interfaces.ICommand;
import listner.commands.interfaces.ICommandManager;
import lombok.NonNull;
import lombok.val;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ManagerCommand extends ListenerAdapter implements ICommandManager {

    private static volatile ManagerCommand instance;

    private Set<ICommand> commandsClasses;

    private ManagerCommand(Set<ICommand> commandsClasses) {
        this.commandsClasses = commandsClasses;
    }

    private ManagerCommand() {}

    public static ManagerCommand getInstance() {
        if (instance == null ) {
            instance = new ManagerCommand(ICommandManager.instantiateAllCommandsClass());
        }
        return instance;
    }


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        val args = event.getMessage().getContentRaw().split("\\s+");

        allCommandsClassess().forEach(clazz -> {
            if (prefixCommand(clazz.command()).equalsIgnoreCase(args[0])) {
                clazz.execute(event);
            }
        });
    }

    private String prefixCommand(@NonNull String command) {
        return DiscordBotConstants.BOT_PREFIX.concat(command);
    }

    @Override
    public Set<String> allCommands() {
        return this.allCommandsClassess().stream().map(ICommand::command)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> allCommandsAndHelp() {
        val list = new HashSet<String>();

         for (val controller : allCommandsClassess()) {
             list.add(String.format("%s: %s \n", this.prefixCommand(controller.command()), controller.help()));
         }
        return list;
    }

    @Override
    public Set<ICommand> allCommandsClassess() {
        return Objects.requireNonNullElse(this.commandsClasses, new HashSet<>());
    }

}

