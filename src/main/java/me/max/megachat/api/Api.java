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

package me.max.megachat.api;

import me.max.megachat.api.exceptions.NoListenersFoundException;
import me.max.megachat.channels.Channel;
import me.max.megachat.channels.ChannelManager;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.List;

public class Api {

    private ApiManager apiManager;
    private ChannelManager channelManager;

    public Api(ApiManager apiManager, ChannelManager channelManager) {
        this.apiManager = apiManager;
        this.channelManager = channelManager;
    }

    public void registerEvents(Object listener) {
        int eventListenerMethods = 0;
        for (Method method : listener.getClass().getMethods()) if (method.isAnnotationPresent(MegaChatEventHandler.class)) eventListenerMethods++;
        if (eventListenerMethods == 0) throw new NoListenersFoundException(listener);
        if (apiManager.listeners.contains(listener)) return;
        apiManager.listeners.add(listener);
    }

    public void unregisterEvents(Object listener) {
        if (!apiManager.listeners.contains(listener)) return;
        apiManager.listeners.remove(listener);
    }

    public Channel getChannelByName(String name) {
        return channelManager.getChannelByName(name);
    }

    public Channel getChannelByPlayer(Player player) {
        return channelManager.getChannelByPlayer(player);
    }

    public List<Channel> getChannelList() {
        return channelManager.getChannelList();
    }

    public void addChannel(Channel channel) {
        channelManager.addChannel(channel);
    }

    public void removeChannel(Channel channel) {
        channelManager.removeChannelByName(channel);
    }

    public void removeChannelByName(String name) {
        channelManager.removeChannelByName(name);
    }

    public Channel getAutoJoinChannel() {
        return channelManager.getAutoJoinChannel();
    }




}
