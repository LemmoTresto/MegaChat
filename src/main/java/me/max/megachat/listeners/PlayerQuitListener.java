package me.max.megachat.listeners;

import me.max.megachat.MegaChat;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private MegaChat megaChat;

    public PlayerQuitListener(MegaChat megaChat) {
        this.megaChat = megaChat;

        //register myself.
        this.megaChat.getServer().getPluginManager().registerEvents(this, megaChat);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        // remove player from its channels.
        megaChat.getChannelManager().removePlayerFromChannel(event.getPlayer());
    }
}
