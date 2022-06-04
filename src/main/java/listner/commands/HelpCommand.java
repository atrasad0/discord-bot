package listner.commands;

import DiscordBotConstants.DiscordBotConstants;
import listner.commands.interfaces.ICommand;
import lombok.val;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class HelpCommand implements ICommand {
    private static final Logger logger = LoggerFactory.getLogger(HelpCommand.class);

    public static void help (MessageReceivedEvent event, Set<String> commandsAndHelp)  {
        val help = new EmbedBuilder();
        help.clear();

        help.setTitle("Isso é o que sei fazer...");
        help.setDescription(String.join("", commandsAndHelp));
        help.setColor(DiscordBotConstants.DEFAULT_COLOR);

        event.getChannel().sendTyping().queue();
        event.getChannel().sendMessageEmbeds(help.build()).queue();
        help.clear();
    }

    @Override
    public String command() {
        return "help";
    }

    @Override
    public String help() {
        return "Mostra todos os comandos deste bot";
    }
}
