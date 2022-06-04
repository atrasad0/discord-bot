package listner;

import DiscordBotConstants.DiscordBotConstants;
import lombok.val;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class SendVoiceMessage extends ListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(SendVoiceMessage.class);

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
        val lastJoiner = event.getMember();
        val chanelJoined = event.getChannelJoined().getName();

        val general = event.getGuild().getTextChannelById(DiscordBotConstants.GENERAL_CHANEL_ID);

        if (Objects.isNull(general)) {
            logger.error("Bot n\u00E3o conseguiu recuperar o canal General, id pesquisado: {}", DiscordBotConstants.GENERAL_CHANEL_ID);
            return;
        }

        val self = event.getGuild().getSelfMember();

        if (!Objects.requireNonNull(self.getVoiceState()).inAudioChannel()) {
            return;
        }

        val joinedChanel =  Objects.requireNonNull(lastJoiner.getVoiceState()).getChannel();

        if (!joinedChanel.getId().equals(self.getVoiceState().getChannel().getId())) {
            return;
        }

        if (lastJoiner.equals(self)) {
            return;
        }

        if (DiscordBotConstants.FOOLS_DISCRIMINATORS.contains(lastJoiner.getUser().getDiscriminator())) {
            general.sendMessage(String.format("Ninguem importante acabou de entrar no canal %s \uD83D\uDE12", chanelJoined)).tts(true).queue();
            return;
        }

        general.sendMessage(String.format("%s acabou de entrar no canal %s \uD83E\uDD42",
                lastJoiner.getEffectiveName(), chanelJoined)).tts(true).queue();

    }


}
