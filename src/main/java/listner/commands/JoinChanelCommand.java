package listner.commands;

import DiscordBotConstants.DiscordBotConstants;
import listner.commands.interfaces.ICommand;
import lombok.val;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.TTSMessageChanel;

import java.util.Objects;

public class JoinChanelCommand implements ICommand {

    private static final Logger logger = LoggerFactory.getLogger(JoinChanelCommand.class);

    public void execute (MessageReceivedEvent event) {
        val self = event.getGuild().getSelfMember();
        TTSMessageChanel.setTTSChanel(event.getGuild().getId(), event.getChannel().getId());

        if (Objects.requireNonNull(self.getVoiceState()).inAudioChannel()) {
            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage("Ou... J\u00E1 to em um canal de voz \uD83D\uDD95").queue();
            return;
        }

        val member = event.getMember();

        if (Objects.isNull(member)) {
            logger.error("Não foi possivel capturar o membro que requisitou um --join command");
            return;
        }

        if (!Objects.requireNonNull(member.getVoiceState()).inAudioChannel()) {
            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage("Para user esse comando entre em um canal de voz \uD83D\uDE21").queue();
            return;
        }

        val audioManager = event.getGuild().getAudioManager();
        val voiceChanel = member.getVoiceState().getChannel();

        audioManager.openAudioConnection(voiceChanel);
    }

    @Override
    public String command() {
        return DiscordBotConstants.JOIN;
    }

    @Override
    public String help() {
        return "Bot entra em um canal de voz";
    }
}
