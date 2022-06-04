package listner;

import DiscordBotConstants.DiscordBotConstants;
import listner.commands.DisconectChanelCommand;
import listner.commands.HelpCommand;
import listner.commands.JoinChanelCommand;
import listner.commands.SendInfoCommand;
import listner.commands.interfaces.ICommand;
import listner.commands.interfaces.ICommandManager;
import lombok.NonNull;
import lombok.val;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ManagerCommand extends ListenerAdapter implements ICommandManager {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        val args = event.getMessage().getContentRaw().split("\\s+");

        if (prefixCommand(DiscordBotConstants.INFO).equalsIgnoreCase(args[0])) {
            SendInfoCommand.send(event);
        }

        if (prefixCommand(DiscordBotConstants.JOIN).equalsIgnoreCase(args[0])) {
            JoinChanelCommand.join(event);
        }

        if (prefixCommand(DiscordBotConstants.OUT).equalsIgnoreCase(args[0])) {
            DisconectChanelCommand.disconect(event);
        }

        if (prefixCommand(DiscordBotConstants.HELP).equalsIgnoreCase(args[0])) {
            HelpCommand.help(event, commandsAndHelp());
        }
    }

    private String prefixCommand(@NonNull String command) {
        return DiscordBotConstants.BOT_PREFIX.concat(command);
    }

    @Override
    public Set<String> commands() {
        return this.allControllers().stream().map(ICommand::command)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> commandsAndHelp() {
        val list = new HashSet<String>();

         for (val controller : allControllers()) {
             list.add(String.format("%s: %s \n", this.prefixCommand(controller.command()), controller.help()));
         }
        return list;
    }

    @Override
    public Set<ICommand> allControllers() {
        val controllers = new HashSet<ICommand>();
        controllers.add(new HelpCommand());
        controllers.add(new SendInfoCommand());
        controllers.add(new JoinChanelCommand());
        controllers.add(new DisconectChanelCommand());

        return controllers;
    }
}

