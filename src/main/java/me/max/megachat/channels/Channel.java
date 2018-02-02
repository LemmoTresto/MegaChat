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

import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Channel {

    private String name;
    private List<Player> members;
    private ChannelType type;

    private List<Format> formats;

    private List<World> worlds;

    public List<Player> activeChatters;

    //zero or null means off.
    private int chatRange;
    private double messageCost;
    private int messageCooldown;
    private Map<String, String> chatFilter;

    private boolean autoJoin;


    public Channel(String name, ChannelType type, List<Player> members, List<Format> formats, List<World> worlds, int chatRange, double messageCost, int messageCooldown, Map<String, String> chatFilter, boolean autoJoin) {
        this.name = name;
        this.type = type;
        this.members = members;
        this.formats = formats;
        this.worlds = worlds;
        this.chatRange = chatRange;
        this.messageCooldown = messageCooldown;
        this.messageCost = messageCost;
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

    public void addFormat(Format format) {
        formats.add(format);
    }

    public void removeFormat(Format format) {
        formats.remove(format);
    }

    public void removeFormat(String name) {
        removeFormat(getFormat(name));
    }

    public void addWorld(World world) {
        worlds.add(world);
    }

    public void removeWorld(World world) {
        worlds.remove(world);
    }

    public void addChatFilter(String word, String replacement) {
        chatFilter.put(word, replacement);
    }

    public void removeChatFilter(String word) {
        chatFilter.remove(word);
    }

    public int getChatRange() {
        return chatRange;
    }

    public double getMessageCost() {
        return messageCost;
    }

    public List<Player> getMembers() {
        return members;
    }

    public List<World> getWorlds() {
        return worlds;
    }

    public List<Format> getFormats() {
        return formats;
    }

    public String getName() {
        return name;
    }

    public void setChatRange(int chatRange) {
        this.chatRange = chatRange;
    }

    public void setFormats(List<Format> formats) {
        this.formats = formats;
    }

    public void setMembers(List<Player> members) {
        this.members = members;
    }

    public void setMessageCost(double messageCost) {
        this.messageCost = messageCost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWorlds(List<World> worlds) {
        this.worlds = worlds;
    }

    public Map<String, String> getChatFilter() {
        return chatFilter;
    }

    public void setChatFilter(Map<String, String> chatFilter) {
        this.chatFilter = chatFilter;
    }

    public boolean isAutoJoin() {
        return autoJoin;
    }

    public void setAutoJoin(boolean autoJoin) {
        this.autoJoin = autoJoin;
    }

    public ChannelType getType() {
        return type;
    }

    public void setType(ChannelType type) {
        this.type = type;
    }

    public List<Player> getPlayersInChatRange(Player player) {
        List<Player> playersInRange = new ArrayList<>();
        for (Player p : getMembers()) {
            if (p.getLocation().distance(player.getLocation()) <= getChatRange()) playersInRange.add(p);
        }
        return playersInRange;
    }

    public Format getFormat(String name) {
        for (Format format : formats) {
            if (format.getGroupName().equals(name)) return format;
        }
        return null;
    }

    public int getMessageCooldown() {
        return messageCooldown;
    }

    public List<Player> getActiveChatters() {
        return activeChatters;
    }

    public void setActiveChatters(List<Player> activeChatters) {
        this.activeChatters = activeChatters;
    }

    public void setMessageCooldown(int messageCooldown) {
        this.messageCooldown = messageCooldown;
    }

    public void addActiveChatter(Player p) {
        if (!activeChatters.contains(p)) activeChatters.add(p);
    }

    public void removeActiveChatter(Player p) {
        if (activeChatters.contains(p)) activeChatters.remove(p);
    }
}
