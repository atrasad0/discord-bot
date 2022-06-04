package listner.commands;

import DiscordBotConstants.DiscordBotConstants;
import listner.ManagerCommand;
import listner.commands.interfaces.ICommand;
import lombok.val;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelpCommand implements ICommand {
    private static final Logger logger = LoggerFactory.getLogger(HelpCommand.class);

    @Override
    public String command() {
        return DiscordBotConstants.HELP;
    }

    @Override
    public String help() {
        return "Mostra todos os comandos deste bot";
    }

    @Override
    public void execute(MessageReceivedEvent event) {
        val help = new EmbedBuilder();
        val manager = ManagerCommand.getInstance();

        help.setTitle("Isso é o que sei fazer...");
        help.setDescription(String.join("", manager.allCommandsAndHelp()));
        help.setColor(DiscordBotConstants.DEFAULT_COLOR);

        event.getChannel().sendTyping().queue();
        event.getChannel().sendMessageEmbeds(help.build()).queue();
        help.clear();
    }
}
