import DiscordBotConstants.DiscordBotConstants;
import listner.ManagerCommand;
import listner.SendVoiceMessage;
import lombok.val;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class DiscordBotApplication {
    public static void main(String[] args) throws LoginException {
        val bot = JDABuilder.createDefault(System.getenv(DiscordBotConstants.ENV_BOT_TOKEN))
                .setActivity(Activity.watching("Minha vida (e n\u00E3o faz sentido)"))
                .build();

        bot.addEventListener(new ManagerCommand());
        bot.addEventListener(new SendVoiceMessage());

    }
}
