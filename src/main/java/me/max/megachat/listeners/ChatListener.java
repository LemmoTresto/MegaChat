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

        // increment value of messages processed in bstats metrics.
        MetricsUtil.incrementProcessedMessages(megaChat.getMetrics());

        //call post process event.
        megaChat.getApiManager().callEvent(new PostProcessMessageEvent(event.isCancelled(), event.getMessage(), event.getPlayer(), megaChat.getChannelManager().getChannelByPlayer(event.getPlayer())));
    }
}
