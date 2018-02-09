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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class ChatChannel {

    private String name;
    private List<Player> members;
    private List<ChatFormat> chatFormats;
    private List<Player> activeChatters = new ArrayList<>();
    private int chatRange;
    private double messageCost;
    private int messageCooldown;
    private List<String> chatBlock;
    private Map<String, String> chatFilter;


    public ChatChannel(String name, List<Player> members, List<ChatFormat> chatFormats, int chatRange, double messageCost, int messageCooldown, List<String> chatBlock, Map<String, String> chatFilter) {
        this.name = name;
        this.members = members;
        this.chatFormats = chatFormats;
        this.chatRange = chatRange;
        this.messageCooldown = messageCooldown;
        this.messageCost = messageCost;
        this.chatBlock = chatBlock;
        this.chatFilter = chatFilter;
    }

    public void addMember(Player member) {
        if (members.contains(member)) return;
        members.add(member);
    }

    public void removeMember(Player member) {
        if (!members.contains(member)) return;
        members.remove(member);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessageCooldown(int messageCooldown) {
        this.messageCooldown = messageCooldown;
    }

    public void setActiveChatters(List<Player> activeChatters) {
        this.activeChatters = activeChatters;
    }

    public List<Player> getActiveChatters() {
        return activeChatters;
    }

    public int getMessageCooldown() {
        return messageCooldown;
    }

    public void setMessageCost(double messageCost) {
        this.messageCost = messageCost;
    }

    public void setMembers(List<Player> members) {
        this.members = members;
    }

    public void setChatFilter(Map<String, String> chatFilter) {
        this.chatFilter = chatFilter;
    }

    public void setChatRange(int chatRange) {
        this.chatRange = chatRange;
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getChatFilter() {
        return chatFilter;
    }

    public List<Player> getMembers() {
        return members;
    }

    public int getChatRange() {
        return chatRange;
    }

    public double getMessageCost() {
        return messageCost;
    }

    public List<ChatFormat> getChatFormats() {
        return chatFormats;
    }

    public void setChatFormats(List<ChatFormat> chatFormats) {
        this.chatFormats = chatFormats;
    }

    public List<String> getChatBlock() {
        return chatBlock;
    }

    public void setChatBlock(List<String> chatBlock) {
        this.chatBlock = chatBlock;
    }

    public ChatFormat getChatFormat(String groupname) {
        for (ChatFormat format : chatFormats) {
            if (format.getGroupName().equals(groupname)) return format;
        }
        return null;
    }
}
