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

public class ChannelJoinEvent extends MegaChatEvent {

    private Channel channel;
    private Player player;

    public ChannelJoinEvent(Channel channel, Player player) {
        this.channel = channel;
        this.player = player;
    }

    public Channel getChannel() {
        return channel;
    }

    public Player getPlayer() {
        return player;
    }
}
