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

public class BasicChatChannel extends ChatChannel {

    private List<World> worlds;
    private boolean autojoin;
    private List<String> regions;

    public BasicChatChannel(String name, List<Player> members, List<ChatFormat> chatFormats, List<World> worlds, List<String> regions, int chatRange, double messageCost, int messageCooldown, List<String> chatBlock, Map<String, String> chatFilter, boolean autoJoin) {
        super(name, members, chatFormats, chatRange, messageCost, messageCooldown, chatBlock, chatFilter);
        this.worlds = worlds;
        this.regions = regions;
        this.autojoin = autoJoin;
    }

    public List<World> getWorlds() {
        return worlds;
    }

    public void setWorlds(List<World> worlds) {
        this.worlds = worlds;
    }

    public List<String> getRegions() {
        return regions;
    }

    public void setRegions(List<String> regions) {
        this.regions = regions;
    }

    public boolean isAutojoin() {
        return autojoin;
    }

    public void setAutojoin(boolean autojoin) {
        this.autojoin = autojoin;
    }
}
