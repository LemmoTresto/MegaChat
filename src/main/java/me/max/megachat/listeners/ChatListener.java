package me.max.megachat.listeners;

import me.max.megachat.MegaChat;
import me.max.megachat.api.ApiManager;
import me.max.megachat.util.MetricsUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private MegaChat megaChat;
    private ApiManager apiManager;

    public ChatListener(MegaChat megaChat, ApiManager apiManager) {
        this.megaChat = megaChat;
        this.apiManager = apiManager;

        //register myself.
        this.megaChat.getServer().getPluginManager().registerEvents(this, megaChat);
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onChat(AsyncPlayerChatEvent event){
        //call pre process event
        //apiManager.callEvent(new PreProcessMessageEvent(event.getMessage(), event.getPlayer(), megaChat.getChannelManager().getChannelOfUser(event.getPlayer())));


        // increment value of messages processed in bstats metrics.
        MetricsUtil.incrementProcessedMessages(megaChat.getMetrics());

        //call post process event.
        //apiManager.callEvent(new PostProcessMessageEvent(event.isCancelled(), event.getMessage(), event.getPlayer(), megaChat.getChannelManager().getChannelOfUser(event.getPlayer())));
    }
}
