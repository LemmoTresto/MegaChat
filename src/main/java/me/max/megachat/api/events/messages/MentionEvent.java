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

package me.max.megachat.api.events.messages;

import me.max.megachat.api.events.MegaChatEvent;
import me.max.megachat.channels.ChatChannel;
import org.bukkit.entity.Player;

public class MentionEvent extends MegaChatEvent {

    private ChatChannel chatChannel;
    private String message;
    private Player mentioner;
    private Player mentionedPlayer;

    public MentionEvent(ChatChannel chatChannel, String message, Player mentioner, Player mentionedPlayer) {
        this.chatChannel = chatChannel;
        this.message = message;
        this.mentioner = mentioner;
        this.mentionedPlayer = mentionedPlayer;
    }

    public ChatChannel getChatChannel() {
        return chatChannel;
    }

    public Player getMentionedPlayer() {
        return mentionedPlayer;
    }

    public Player getMentioner() {
        return mentioner;
    }

    public String getMessage() {
        return message;
    }
}