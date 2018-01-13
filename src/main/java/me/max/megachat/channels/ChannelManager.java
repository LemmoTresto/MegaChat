package me.max.megachat.channels;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChannelManager {

    private List<Channel> channelList = new ArrayList<>();

    public ChannelManager() {
        // todo add global channel.
    }

    public void addChannel(Channel channel) {
        if (!channelList.contains(channel) && channelList.stream().noneMatch(channel1 -> channel.getName().equalsIgnoreCase(channel1.getName())))
            channelList.add(channel);
    }

    public void removeChannel(Channel channel) {
        if (channelList.contains(channel)) channelList.remove(channel);
    }

    public void removeChannel(String name) {
        channelList.removeAll(channelList.stream().filter(channel -> channel.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList()));
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
}
