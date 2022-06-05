package listner;

import DiscordBotConstants.DiscordBotConstants;
import lombok.val;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.TTSMessageChanel;

import java.util.Objects;

public class SendVoiceMessage extends ListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(SendVoiceMessage.class);

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
        val lastJoiner = event.getMember();
        val chanelJoined = event.getChannelJoined().getName();

        val TTSChanel = tryGetContextChanelOrDefault(event);

        if (Objects.isNull(TTSChanel)) {
            logger.error("Bot n\u00E3o conseguiu recuperar o canal para enviar mensagens TTS, id pesquisado");
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
            val message = String.format("Ninguem importante acabou de entrar no canal %s \uD83D\uDE12", chanelJoined);
            TTSChanel.sendMessage(message).tts(true).queue();
            return;
        }

        val message = String.format("%s acabou de entrar no canal %s \uD83E\uDD42",
                lastJoiner.getEffectiveName(), chanelJoined);

        TTSChanel.sendMessage(message).tts(true).queue();

    }

    private TextChannel tryGetContextChanelOrDefault(GuildVoiceJoinEvent event) {
        val chanelId = TTSMessageChanel.getTTSChanelId(event.getGuild().getId());

        if (Objects.isNull(chanelId)) {
            return getSomeChanel(event);
        }
       return event.getGuild().getTextChannelById(chanelId);
    }

    private TextChannel getSomeChanel(GuildVoiceJoinEvent event) {
        return event.getGuild().getTextChannels().get(0);
    }


}
