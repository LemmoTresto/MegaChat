package me.max.megachat.listeners;

import me.max.megachat.MegaChat;
import me.max.megachat.util.MetricsUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private MegaChat megaChat;

    public ChatListener(MegaChat megaChat){
        this.megaChat = megaChat;

        //register myself.
        this.megaChat.getServer().getPluginManager().registerEvents(this, megaChat);
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onChat(AsyncPlayerChatEvent event){

        // increment value of messages processed in bstats metrics.
        MetricsUtil.incrementProcessedMessages(megaChat.getMetrics());
    }
}
