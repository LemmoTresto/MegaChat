package me.max.megachat.util;

import me.max.megachat.MegaChat;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class ConfigUtil {

    public static void saveDefaultConfig(File configFile){
        if (!configFile.exists()){
            //get file from jar and save to data folder.
            saveResource("config/en.yml", configFile);
        }
    }

    private static void saveResource(String resource, File destination) {
        try {
            //get resource and save to destination.
            FileUtils.copyInputStreamToFile(MegaChat.class.getResourceAsStream(resource), destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
