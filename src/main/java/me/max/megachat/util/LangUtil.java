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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LangUtil {

    public enum SupportedLanguages {EN}

    public static List<String> supportedLanguages = new ArrayList<String>();


    static {
        for (SupportedLanguages supportedLanguage : SupportedLanguages.values()) {
            if (!supportedLanguages.contains(supportedLanguage.toString()))
                supportedLanguages.add(supportedLanguage.toString());
        }
    }

    private static void renameFiles(String path) {
        File configFile = new File(path, "config.yml");
        File messagesFile = new File(path, "messages.yml");

        configFile.renameTo(new File(path, "config-" + System.currentTimeMillis() + ".yml.old"));
        messagesFile.renameTo(new File(path, "messages-" + System.currentTimeMillis() + ".yml.old"));
    }

    private static void writeNewFiles(MegaChat megaChat, String lang) {
        ConfigUtil.saveNewConfig(megaChat, lang);
        MessagesUtil.saveNewConfig(megaChat, lang);
    }

    public static void updateFiles(MegaChat megaChat, String lang) {
        renameFiles(megaChat.getDataFolder().toString());
        writeNewFiles(megaChat, lang);
    }

}
