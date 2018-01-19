package me.max.megachat.listeners;

import me.max.megachat.MegaChat;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private MegaChat megaChat;

    public PlayerJoinListener(MegaChat megaChat) {
        this.megaChat = megaChat;

        //register myself.
        this.megaChat.getServer().getPluginManager().registerEvents(this, megaChat);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        // add player to autojoin chat channel.
        megaChat.getChannelManager().addPlayerToAutoJoinChannel(event.getPlayer());
    }
}
