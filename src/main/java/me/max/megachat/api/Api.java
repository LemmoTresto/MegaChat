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

}
