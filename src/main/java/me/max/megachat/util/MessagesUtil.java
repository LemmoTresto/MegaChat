package me.max.megachat.util;

import me.max.megachat.MegaChat;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class MessagesUtil {

    public static void saveDefaultMessages(File messagesFile){
        if (!messagesFile.exists()){
            saveResource("messages/en.yml", messagesFile);
        }
    }

    private static void saveResource(String resource, File destination) {
        try {
            FileUtils.copyInputStreamToFile(MegaChat.class.getResourceAsStream(resource), destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
