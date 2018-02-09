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

package me.max.megachat.channels;

import me.max.megachat.MegaChat;
import me.max.megachat.channels.base.ChatChannel;

import java.util.ArrayList;
import java.util.List;

public class ChannelManager {

    private List<ChatChannel> chatChannelList = new ArrayList<>();

    private MegaChat megaChat;

    public ChannelManager(MegaChat megaChat) {
        this.megaChat = megaChat;

        //todo
    }

    public void addChannel(ChatChannel chatChannel) {
        if (!chatChannelList.contains(chatChannel) && chatChannelList.stream().noneMatch(channel1 -> chatChannel.getName().equalsIgnoreCase(channel1.getName())))
            chatChannelList.add(chatChannel);
    }

    public void removeChannel(ChatChannel chatChannel) {
        if (chatChannelList.contains(chatChannel)) chatChannelList.remove(chatChannel);
    }

    public void removeChannelByName(String name) {
        for (ChatChannel chatChannel : chatChannelList) {
            if (chatChannel.getName().equals(name)) removeChannel(chatChannel);
        }
    }

    public ChatChannel getAutoJoinChannel() {
        for (ChatChannel chatChannel : chatChannelList) {
            if (chatChannel.isAutoJoin()) {
                return chatChannel;
            }
        }
        return null;
    }

    public ChatChannel getChannelByName(String name) {
        for (ChatChannel chatChannel : chatChannelList) {
            if (chatChannel.getName().equals(name)) return chatChannel;
        }
        return null;
    }
}
