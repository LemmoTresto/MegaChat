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

package me.max.megachat.api.events;

import me.max.megachat.channels.Channel;
import org.bukkit.entity.Player;

public class PreProcessMessageEvent extends MegaChatEvent {

    private boolean cancelled;
    private String message;
    private Player sender;
    private Channel channel;

    public PreProcessMessageEvent(String message, Player sender, Channel channel) {
        this.sender = sender;
        this.message = message;
        this.channel = channel;
        cancelled = false;
    }

    public Channel getChannel() {
        return channel;
    }

    public Player getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}