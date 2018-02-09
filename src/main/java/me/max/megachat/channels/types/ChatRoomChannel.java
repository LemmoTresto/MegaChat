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
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class ChatRoomChannel extends ChatChannel {

    private List<OfflinePlayer> offlineMembers;

    public ChatRoomChannel(String name, List<Player> members, List<OfflinePlayer> offlineMembers, List<ChatFormat> chatFormats, int chatRange, double messageCost, int messageCooldown, List<String> chatBlock, Map<String, String> chatFilter) {
        super(name, members, chatFormats, chatRange, messageCost, messageCooldown, chatBlock, chatFilter);
        this.offlineMembers = offlineMembers;
    }

    public List<OfflinePlayer> getOfflineMembers() {
        return offlineMembers;
    }

    public void setOfflineMembers(List<OfflinePlayer> offlineMembers) {
        this.offlineMembers = offlineMembers;
    }

    public void addMember(Player player) {
        offlineMembers.remove(player);
        List<Player> members = getMembers();
        members.add(player);
        this.setMembers(members);
    }

    public void removeMember(Player p) {
        offlineMembers.add(p);
        List<Player> members = getMembers();
        members.remove(p);
        this.setMembers(members);
    }
}
