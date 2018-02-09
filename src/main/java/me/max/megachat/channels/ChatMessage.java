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

import org.bukkit.entity.Player;

import java.util.List;

public class ChatMessage {

    private String message;
    private String format;
    private Player sender;
    private List<Player> recipients;
    private ChatChannel chatChannel;

    public ChatMessage(String message, String format, Player sender, List<Player> recipients, ChatChannel chatChannel) {
        this.message = message;
        this.format = format;
        this.sender = sender;
        this.recipients = recipients;
        this.chatChannel = chatChannel;
    }

    public String getMessage() {
        return message;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public void setSender(Player sender) {
        this.sender = sender;
    }

    public Player getSender() {
        return sender;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ChatChannel getChatChannel() {
        return chatChannel;
    }

    public List<Player> getRecipients() {
        return recipients;
    }

    public void setChatChannel(ChatChannel chatChannel) {
        this.chatChannel = chatChannel;
    }

    public void setRecipients(List<Player> recipients) {
        this.recipients = recipients;
    }
}
