package listner.commands.interfaces;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface ICommand {

    void execute(MessageReceivedEvent event);

    String command();

    String help();
}
