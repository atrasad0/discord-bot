package listner.commands;

import DiscordBotConstants.DiscordBotConstants;
import listner.commands.interfaces.ICommand;
import lombok.val;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class DisconectChanelCommand implements ICommand {

    private static final Logger logger = LoggerFactory.getLogger(DisconectChanelCommand.class);

    public static void disconect (MessageReceivedEvent event) {
        val self = event.getGuild().getSelfMember();

        if (!Objects.requireNonNull(self.getVoiceState()).inAudioChannel()) {
            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage("Mas nem to, pqp em! \uD83E\uDD2C").queue();
            return;
        }

        val member = event.getMember();

        if (Objects.isNull(member)) {
            logger.error("Não foi possivel capturar o membro que requisitou um --out command");
            return;
        }

        if (!Objects.requireNonNull(member.getVoiceState()).inAudioChannel()) {
            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage("Para user esse comando entre no mesmo canal de voz que eu cara \uD83D\uDE2B").queue();
            return;
        }

        if (!Objects.requireNonNull(member.getVoiceState().getChannel()).getId()
                .equals(Objects.requireNonNull(self.getVoiceState().getChannel()).getId())) {
            event.getChannel().sendMessage("Para user esse comando entre no mesmo canal de voz que eu cara \uD83D\uDE08").queue();
            return;
        }

        val audioManager = event.getGuild().getAudioManager();
        audioManager.closeAudioConnection();
    }

    @Override
    public String command() {
        return DiscordBotConstants.OUT;
    }

    @Override
    public String help() {
        return "Bot se desconecta de um canal de voz.";
    }
}
