package me.max.megachat.util;

import me.max.megachat.MegaChat;

import java.io.*;

public class MessagesUtil {

    public static void saveDefaultMessages(MegaChat megaChat) {
        // yes I know this check is already done in saveResource method but I do this to be able to send messages to the console.
        if (!(new File(megaChat.getDataFolder(), "messages.yml").exists())) {
            megaChat.info("Messages file does not exist, creating it now..");
            saveResource(megaChat, "messages/en.yml", "messages.yml");
            megaChat.info("Written messages file successfully.");
        }
    }

    private static void saveResource(MegaChat megaChat, String resourcePath, String destination) {
        InputStream in = megaChat.getResource(resourcePath);

        File outFile = new File(megaChat.getDataFolder(), destination);

        try {
            OutputStream out = new FileOutputStream(outFile);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (IOException ex) {
            megaChat.error("Could not save " + outFile.getName() + " to " + outFile);
            ex.printStackTrace();
            megaChat.shutdown();
        }
    }
}
