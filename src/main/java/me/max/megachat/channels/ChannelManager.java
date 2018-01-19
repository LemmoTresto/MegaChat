package me.max.megachat.channels;

import me.max.megachat.MegaChat;
import org.bukkit.Bukkit;
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

        ConfigurationSection channelsSection = megaChat.getConfig().getConfigurationSection("channels");

        for (String channelName : channelsSection.getKeys(false)) {
            List<Player> members = new ArrayList<>();
            Map<String, String> formats = new HashMap<>();
            Map<String, String> chatFilter = new HashMap<>();

            if (channelsSection.getBoolean(channelName + ".auto-join")) members.addAll(Bukkit.getOnlinePlayers());

            ConfigurationSection formatsSection = channelsSection.getConfigurationSection(channelName + ".formats");
            for (String name : formatsSection.getKeys(false)) formats.put(name, formatsSection.getString(name));

            ConfigurationSection chatFilterSection = channelsSection.getConfigurationSection(channelName + ".chat-filter");
            for (String word : chatFilterSection.getKeys(false))
                chatFilter.put(word, chatFilterSection.getString(word));

            addChannel(new Channel(channelName, members, formats, new ArrayList<>(), channelsSection.getInt(channelName + ".chat-range"), channelsSection.getDouble(channelName + ".message-cost"), chatFilter, channelsSection.getBoolean(channelName + ".auto-join")));
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
