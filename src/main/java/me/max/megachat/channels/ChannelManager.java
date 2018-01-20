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

import me.max.megachat.MegaChat;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChannelManager {

    private List<Channel> channelList = new ArrayList<>();

    private MegaChat megaChat;

    public ChannelManager(MegaChat megaChat) {
        this.megaChat = megaChat;

        // make sure it is enabled.
        if (megaChat.getConfig().getBoolean("per-world-chat.enabled")) {
            List<World> worlds;
            // check if all worlds are per world chat.
            if (megaChat.getConfig().getBoolean("per-world-chat.all-worlds")) {
                worlds = Bukkit.getWorlds();
                for (String worldName : megaChat.getConfig().getStringList("per=world-chat.blacklisted-worlds")) {
                    worlds.remove(Bukkit.getWorld(worldName));
                }
            } else {
                worlds = new ArrayList<>();
                for (String worldName : megaChat.getConfig().getStringList("per=world-chat.whitelisted-worlds")) {
                    worlds.add(Bukkit.getWorld(worldName));
                }
            }
            for (World world : worlds) {
                List<Player> members = new ArrayList<>();
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.getWorld().equals(world)) {
                        members.add(p);
                    }
                }
                List<World> worldForChannel = new ArrayList<>();
                worldForChannel.add(world);
                addChannel(new Channel(world.getName(), members, getAutoJoinChannel().getFormats(), worldForChannel, getAutoJoinChannel().getChatRange(), getAutoJoinChannel().getMessageCost(), getAutoJoinChannel().getChatFilter(), false));
            }
        }

        ConfigurationSection channelsSection = megaChat.getConfig().getConfigurationSection("channels");

        for (String channelName : channelsSection.getKeys(false)) {
            List<Player> members = new ArrayList<>();
            Map<String, String> formats = new HashMap<>();
            Map<String, String> chatFilter = new HashMap<>();

            if (channelsSection.getBoolean(channelName + ".auto-join")) members.addAll(Bukkit.getOnlinePlayers());

            // remove players who already have a channel from per-world-chat.
            for (Player p : members) {
                if (getChannelByPlayer(p) != null) {
                    members.remove(p);
                }
            }

            ConfigurationSection formatsSection = channelsSection.getConfigurationSection(channelName + ".formats");
            for (String name : formatsSection.getKeys(false)) formats.put(name, formatsSection.getString(name));

            ConfigurationSection chatFilterSection = channelsSection.getConfigurationSection(channelName + ".chat-filter");
            for (String word : chatFilterSection.getKeys(false))
                chatFilter.put(word, chatFilterSection.getString(word));

            addChannel(new Channel(channelName, members, formats, Bukkit.getWorlds(), channelsSection.getInt(channelName + ".chat-range"), channelsSection.getDouble(channelName + ".message-cost"), chatFilter, channelsSection.getBoolean(channelName + ".auto-join")));
        }
    }

    public void addChannel(Channel channel) {
        if (!channelList.contains(channel) && channelList.stream().noneMatch(channel1 -> channel.getName().equalsIgnoreCase(channel1.getName())))
            channelList.add(channel);
    }

    public void removeChannel(Channel channel) {
        if (channelList.contains(channel)) channelList.remove(channel);
    }

    public Channel getAutoJoinChannel() {
        for (Channel channel : channelList) {
            if (channel.isAutoJoin()) {
                return channel;
            }
        }
        return null;
    }

    public void removeChannel(String name) {
        channelList.removeAll(channelList.stream().filter(channel -> channel.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList()));
    }

    public void removePlayerFromChannel(Player p) {
        getChannelByPlayer(p).removeMember(p);
    }

    public void addPlayerToAutoJoinChannel(Player p) {
        getAutoJoinChannel().addMember(p);
    }

    public List<Channel> getChannelList() {
        return channelList;
    }

    public Channel getChannelByName(String name) {
        for (Channel channel : channelList) if (channel.getName().equalsIgnoreCase(name)) return channel;
        return null;
    }

    public Channel getChannelByPlayer(Player player) {
        for (Channel channel : channelList) if (channel.getMembers().contains(player)) return channel;
        return null;
    }

    public List<Player> getPlayersInChatRange(Player sender, Channel channel) {
        List<Player> playersInRange = new ArrayList<>();
        for (Player p : channel.getMembers()) {
            if (p.getLocation().distance(sender.getLocation()) <= channel.getChatRange()) playersInRange.add(p);
        }
        return playersInRange;
    }
}
