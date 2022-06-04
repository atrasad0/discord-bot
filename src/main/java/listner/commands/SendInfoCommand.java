package listner.commands;

import DiscordBotConstants.DiscordBotConstants;
import listner.commands.interfaces.ICommand;
import lombok.val;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SendInfoCommand implements ICommand {

    @Override
    public void execute(MessageReceivedEvent event) {
        val author = event.getAuthor();
        val info = new EmbedBuilder();

        info.setTitle("Informa\u00E7\u00E3o");
        info.setDescription(String.format("Então %s, basicamente fui criado para falar o nome das pessoas que entrarem no servidor \uD83D\uDC7D", author.getName()));
        info.addField("Criador", "Atrasado", false);
        info.setColor(DiscordBotConstants.DEFAULT_COLOR);

        event.getChannel().sendTyping().queue();
        event.getChannel().sendMessageEmbeds(info.build()).queue();
        info.clear();
    }

    @Override
    public String command() {
        return DiscordBotConstants.INFO;
    }

    @Override
    public String help() {
        return "Breve descri\u00E7\u00E3o sobre o prop\u00F3sito do bot";
    }
}
