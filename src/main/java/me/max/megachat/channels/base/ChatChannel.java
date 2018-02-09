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

package me.max.megachat.channels.base;

import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class ChatChannel {

    private String name;
    private List<Player> members;
    private List<ChatFormat> chatFormats;
    private List<World> worlds;
    private List<String> regions;
    private List<Player> activeChatters = new ArrayList<>();
    private int chatRange;
    private double messageCost;
    private int messageCooldown;
    private List<String> chatBlock;
    private Map<String, String> chatFilter;
    private boolean autoJoin;


    public ChatChannel(String name, List<Player> members, List<ChatFormat> chatFormats, List<World> worlds, List<String> regions, int chatRange, double messageCost, int messageCooldown, List<String> chatBlock, Map<String, String> chatFilter, boolean autoJoin) {
        this.name = name;
        this.members = members;
        this.chatFormats = chatFormats;
        this.worlds = worlds;
        this.regions = regions;
        this.chatRange = chatRange;
        this.messageCooldown = messageCooldown;
        this.messageCost = messageCost;
        this.chatBlock = chatBlock;
        this.chatFilter = chatFilter;
        this.autoJoin = autoJoin;
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

    public void setRegions(List<String> regions) {
        this.regions = regions;
    }

    public List<String> getRegions() {
        return regions;
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

    public void setWorlds(List<World> worlds) {
        this.worlds = worlds;
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

    public List<World> getWorlds() {
        return worlds;
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

    public void setAutoJoin(boolean autoJoin) {
        this.autoJoin = autoJoin;
    }

    public boolean isAutoJoin() {
        return autoJoin;
    }

    public void setChatBlock(List<String> chatBlock) {
        this.chatBlock = chatBlock;
    }
}
