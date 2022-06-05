package services;

import java.util.HashMap;

public class TTSMessageChanel {

    //TODO utilizar sqlite?
    private static final HashMap<String, String> TTSChanelId = new HashMap<>();

    public TTSMessageChanel() {}

    public static void setTTSChanel(String serverId, String chanelId) {
        TTSChanelId.put(serverId, chanelId);
    }

    public static String getTTSChanelId(String serverId) {
        synchronized (TTSMessageChanel.class) {
            return TTSChanelId.get(serverId);
        }
    }
}
