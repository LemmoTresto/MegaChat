/*
 *
 *  * MegaChat - An advanced but simple to use chat plugin.
 *  * Copyright (C) 2018 Max Berkelmans AKA LemmoTresto
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package me.max.megachat.util;

import me.max.megachat.MegaChat;

import java.io.*;

public class ConfigUtil {

    public static void saveDefaultConfig(MegaChat megaChat) {
        // yes I know this check is already done in saveResource method but I do this to be able to send messages to the console.
        if (!(new File(megaChat.getDataFolder(), "config.yml").exists())) {
            megaChat.info("Config file does not exist, creating it now..");
            saveResource(megaChat, "configs/en.yml", "config.yml");
            megaChat.info("Written config file successfully.");
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
