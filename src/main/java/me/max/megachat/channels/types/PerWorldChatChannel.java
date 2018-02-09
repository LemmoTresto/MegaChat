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

package me.max.megachat.channels.types;

import me.max.megachat.channels.ChatChannel;
import me.max.megachat.channels.ChatFormat;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class PerWorldChatChannel extends ChatChannel {

    private World world;

    public PerWorldChatChannel(String name, List<Player> members, List<ChatFormat> chatFormats, World world, int chatRange, double messageCost, int messageCooldown, List<String> chatBlock, Map<String, String> chatFilter) {
        super(name, members, chatFormats, chatRange, messageCost, messageCooldown, chatBlock, chatFilter);
        this.world = world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }
}
