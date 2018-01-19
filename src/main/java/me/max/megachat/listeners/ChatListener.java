package me.max.megachat.listeners;

import me.max.megachat.MegaChat;
import me.max.megachat.api.events.PostProcessMessageEvent;
import me.max.megachat.api.events.PreProcessMessageEvent;
import me.max.megachat.channels.Channel;
import me.max.megachat.util.MetricsUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

public class ChatListener implements Listener {

    private MegaChat megaChat;

    public ChatListener(MegaChat megaChat) {
        this.megaChat = megaChat;

        //register myself.
        this.megaChat.getServer().getPluginManager().registerEvents(this, megaChat);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onChat(AsyncPlayerChatEvent event){
        //call pre process event
        megaChat.getApiManager().callEvent(new PreProcessMessageEvent(event.getMessage(), event.getPlayer(), megaChat.getChannelManager().getChannelByPlayer(event.getPlayer())));

        //get channel to forward this message to.
        Channel channel = megaChat.getChannelManager().getChannelByPlayer(event.getPlayer());

        if (!(channel.getChatRange() == 0)) {
            List<Player> playersInRange = megaChat.getChannelManager().getPlayersInChatRange(event.getPlayer(), channel);
        }

        // todo remake this but with channels. Will be done when channels are finished.
        //get blacklisted worlds
        //check if per world chat is enabled
        List<String> blacklistedWorlds = megaChat.getConfig().getStringList("per-world-chat.blacklisted-worlds");
        if (megaChat.getConfig().getBoolean("per-world-chat.enabled")) {
            //check if all worlds have a seperate chat and that player is not in a blacklisted world.
            if (megaChat.getConfig().getBoolean("per-world-chat.all-worlds") && !blacklistedWorlds.contains(event.getPlayer().getWorld().getName())) {
                // loop over all recipients and remove if not in the same world.
                for (Player p : event.getRecipients()) {
                    if (!p.getWorld().equals(event.getPlayer().getWorld())) {
                        event.getRecipients().remove(p);
                    }
                }
            }
            // check to make sure player is not in a blacklisted worlds.
            else if (!blacklistedWorlds.contains(event.getPlayer().getWorld().getName())) {
                // get all whitelisted worlds
                List<String> whitelistedWorlds = megaChat.getConfig().getStringList("per-world-chat.whitelisted-worlds");
                // make sure player is in a whitelisted world.
                // loop and remove people who are not in this world.
                if (whitelistedWorlds.contains(event.getPlayer().getWorld().getName())) {
                    for (Player p : event.getRecipients()) {
                        if (!p.getWorld().equals(event.getPlayer().getWorld())) {
                            event.getRecipients().remove(p);
                        }
                    }
                }
            }
        }

        // increment value of messages processed in bstats metrics.
        MetricsUtil.incrementProcessedMessages(megaChat.getMetrics());

        //call post process event.
        megaChat.getApiManager().callEvent(new PostProcessMessageEvent(event.isCancelled(), event.getMessage(), event.getPlayer(), megaChat.getChannelManager().getChannelByPlayer(event.getPlayer())));
    }
}
