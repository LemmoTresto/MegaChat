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
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatListener implements Listener {

    private MegaChat megaChat;
    private Map<Player, Integer> messageCooldowns = new HashMap<>();

    public ChatListener(MegaChat megaChat) {
        this.megaChat = megaChat;

        //register myself.
        this.megaChat.getServer().getPluginManager().registerEvents(this, megaChat);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onChat(AsyncPlayerChatEvent event){
        if (event.isCancelled() && !megaChat.getConfig().getBoolean("ignore-cancelled-chat-events")) return;
        event.setCancelled(false);

        //call pre process event for api
        megaChat.getApiManager().callEvent(new PreProcessMessageEvent(event.getMessage(), event.getPlayer(), megaChat.getChannelManager().getChannelByPlayer(event.getPlayer())));

        //get channel to forward this message to.
        Channel channel = megaChat.getChannelManager().getChannelByPlayer(event.getPlayer());

        //set correct format
        event.setFormat(getCorrectFormat(channel, event.getPlayer()));

        //todo (cooldown & more)

        //take message cost if present.
        int deductCost = deductCost(channel, event.getPlayer());
        if (deductCost == 0) {
            if (megaChat.getMessages().getBoolean("message-cost.enabled")) {
                if (megaChat.getMessages().getBoolean("message-cost.successful.enabled")) {
                    event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', megaChat.getMessages().getString("message-cost.successful.message")));
                }
            }
        }

        //remove all players & add ones in chatrange.
        if (channel.getChatRange() != 0) {
            event.getRecipients().removeAll(event.getRecipients());
            event.getRecipients().addAll(getPlayersInChatRange(channel, event.getPlayer()));
        }


        // increment value of messages processed in bstats metrics.
        MetricsUtil.incrementProcessedMessages(megaChat.getMetrics());

        //call post process event for api.
        megaChat.getApiManager().callEvent(new PostProcessMessageEvent(event.isCancelled(), event.getMessage(), event.getPlayer(), megaChat.getChannelManager().getChannelByPlayer(event.getPlayer()), event.getRecipients()));
    }

    private String getCorrectFormat(Channel channel, Player sender) {
        //check if protcollib is present
        //if so we use json messages.
        if (megaChat.getProtocolLibHook() == null) {
            //check if we can grab the group using vault
            if (megaChat.getVaultHook() == null || megaChat.getVaultHook().getChat().getPrimaryGroup(sender) == null || megaChat.getVaultHook().getChat().getPrimaryGroup(sender).equals("")) {
                //return default group.
                return channel.getFormat("default").getFormattedMessage(sender);
            }
            //vault is present get group and return format.
            return channel.getFormat(megaChat.getVaultHook().getChat().getPrimaryGroup(sender)).getFormattedMessage(sender);
        }

        //no protocollib so no json messages.
        if (megaChat.getVaultHook() == null || megaChat.getVaultHook().getChat().getPrimaryGroup(sender) == null || megaChat.getVaultHook().getChat().getPrimaryGroup(sender).equals("")) {
            //no vault present so return default group format.
            return channel.getFormat("default").getFormattedJsonMessage(sender);
        }
        //vault is present so return json message with correct format.
        return channel.getFormat(megaChat.getVaultHook().getChat().getPrimaryGroup(sender)).getFormattedJsonMessage(sender);
    }

    //returns 0, 1, 2 based on what happend.
    //0: successful.
    //1: failed
    //2: not enough money
    private int deductCost(Channel channel, Player sender) {
        //no vault so we fail.
        if (megaChat.getVaultHook() == null) return 1;
        //take cost
        EconomyResponse resp = megaChat.getVaultHook().takeMessageCost(sender, channel.getMessageCost());
        if (resp == null) {
            //the method returns null if there is not enough money,
            return 2;
        }
        //check response if it was successful.
        if (resp.transactionSuccess()) return 0;
        return 1;
    }

    private List<Player> getPlayersInChatRange(Channel channel, Player sender) {
        return channel.getPlayersInChatRange(sender);
    }
}
