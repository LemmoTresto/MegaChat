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

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.max.megachat.MegaChat;
import me.max.megachat.api.events.messages.PostProcessMessageEvent;
import me.max.megachat.api.events.messages.PreProcessMessageEvent;
import me.max.megachat.channels.ChatChannel;
import me.max.megachat.util.MetricsUtil;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChatListener implements Listener {

    private MegaChat megaChat;
    private List<Player> playersOnCooldown = new ArrayList<>();

    public ChatListener(MegaChat megaChat) {
        this.megaChat = megaChat;
        playersOnCooldown.clear();

        //register myself.
        this.megaChat.getServer().getPluginManager().registerEvents(this, megaChat);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onChat(AsyncPlayerChatEvent event){
        if (event.isCancelled() && !megaChat.getConfig().getBoolean("ignore-cancelled-chat-events")) return;
        event.setCancelled(false);

        //call pre process event for api
        megaChat.getApiManager().callEvent(new PreProcessMessageEvent(event.getMessage(), event.getPlayer(), megaChat.getChannelManager().getChannelByPlayer(event.getPlayer())));

        //get chatChannel to forward this message to.
        ChatChannel chatChannel = megaChat.getChannelManager().getChannelByPlayer(event.getPlayer());

        if (!chatChannel.getRegions().isEmpty()) {
            if (megaChat.getWorldGuardHook() != null) {
                boolean isInRegion = false;
                for (ProtectedRegion region : megaChat.getWorldGuardHook().getRegionsOfPlayer(event.getPlayer()).getRegions()) {
                    if (chatChannel.getRegions().contains(region.getId())) {
                        isInRegion = true;
                        break;
                    }
                }
                if (!isInRegion) {
                    megaChat.getChannelManager().addPlayerToCorrectChannel(event.getPlayer());
                    chatChannel = megaChat.getChannelManager().getChannelByPlayer(event.getPlayer());
                }
            }
        }

        if (playersOnCooldown.contains(event.getPlayer())) {
            event.setCancelled(true);
        } else {
            playersOnCooldown.add(event.getPlayer());
            Bukkit.getScheduler().runTaskLaterAsynchronously(megaChat, () -> playersOnCooldown.remove(event.getPlayer()), chatChannel.getMessageCooldown() * 20L);
        }

        if (!chatChannel.getChatBlock().isEmpty()) {
            for (String word : chatChannel.getChatBlock()) {
                if (event.getMessage().contains(word)) {
                    event.setCancelled(true);
                    break;
                }
            }
        }

        //set the right recipients.
        event.getRecipients().clear();
        event.getRecipients().addAll(getCorrectRecipients(chatChannel, event.getPlayer()));

        //set correct format
        event.setFormat(getCorrectFormat(chatChannel, event.getPlayer()));

        //chat filter
        if (!chatChannel.getChatFilter().isEmpty()) {
            for (Map.Entry<String, String> filter : chatChannel.getChatFilter().entrySet()) {
                event.setMessage(event.getMessage().replace(filter.getKey(), filter.getValue()));
            }
        }

        //player has the permission for color in chat so translate the colors.
        if (event.getPlayer().hasPermission("megachat." + chatChannel.getName() + ".color"))
            event.setMessage(ChatColor.translateAlternateColorCodes('&', event.getMessage()));

        //take message cost if present.
        if (chatChannel.getMessageCost() != 0) {
            //call method which returns an integer depending on what happened.
            int deductCost = deductCost(chatChannel, event.getPlayer());
            // 0 means successful.
            if (deductCost == 0) {
                if (megaChat.getMessages().getBoolean("message-cost.enabled")) {
                    if (megaChat.getMessages().getBoolean("message-cost.successful.enabled")) {
                        event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', megaChat.getMessages().getString("message-cost.successful.message")));
                    }
                }
            }
        }

        // increment value of messages processed in bstats metrics.
        MetricsUtil.incrementProcessedMessages(megaChat.getMetrics());

        //call post process event for api.
        megaChat.getApiManager().callEvent(new PostProcessMessageEvent(event.isCancelled(), event.getMessage(), event.getPlayer(), megaChat.getChannelManager().getChannelByPlayer(event.getPlayer()), event.getRecipients()));
    }

    private String getCorrectFormat(ChatChannel chatChannel, Player sender) {
        //check if protcollib is present
        //if so we use json messages.
        if (megaChat.getProtocolLibHook() == null) {
            //check if we can grab the group using vault
            if (megaChat.getVaultHook() == null || megaChat.getVaultHook().getChat().getPrimaryGroup(sender) == null || megaChat.getVaultHook().getChat().getPrimaryGroup(sender).equals("")) {
                //return default group.
                return chatChannel.getFormat("default").getFormattedMessage(sender);
            }
            //check if there is a format for that group.
            if (chatChannel.getFormat(megaChat.getVaultHook().getChat().getPrimaryGroup(sender)) != null) {
                //vault is present get group and return format.
                return chatChannel.getFormat(megaChat.getVaultHook().getChat().getPrimaryGroup(sender)).getFormattedMessage(sender);
            }
            return chatChannel.getFormat("default").getFormattedMessage(sender);
        }

        //no protocollib so no json messages.
        if (megaChat.getVaultHook() == null || megaChat.getVaultHook().getChat().getPrimaryGroup(sender) == null || megaChat.getVaultHook().getChat().getPrimaryGroup(sender).equals("")) {
            //no vault present so return default group format.
            return chatChannel.getFormat("default").getFormattedJsonMessage(sender);
        }
        //check if the format exists.
        if (chatChannel.getFormat(megaChat.getVaultHook().getChat().getPrimaryGroup(sender)) != null) {
            //vault is present so return json message with correct format.
            return chatChannel.getFormat(megaChat.getVaultHook().getChat().getPrimaryGroup(sender)).getFormattedJsonMessage(sender);
        }
        return chatChannel.getFormat("default").getFormattedJsonMessage(sender);
    }

    //returns 0, 1, 2 based on what happend.
    //0: successful.
    //1: failed
    //2: not enough money
    private int deductCost(ChatChannel chatChannel, Player sender) {
        //no vault so we fail.
        if (megaChat.getVaultHook() == null) return 1;
        //take cost
        EconomyResponse resp = megaChat.getVaultHook().takeMessageCost(sender, chatChannel.getMessageCost());
        if (resp == null) {
            //the method returns null if there is not enough money,
            return 2;
        }
        //check response if it was successful.
        if (resp.transactionSuccess()) return 0;
        return 1;
    }

    private List<Player> getPlayersInChatRange(ChatChannel chatChannel, Player sender) {
        return chatChannel.getPlayersInChatRange(sender);
    }

    private List<Player> getCorrectRecipients(ChatChannel chatChannel, Player sender) {
        //check if we have to use regions.
        if (!chatChannel.getRegions().isEmpty()) {
            //make sure worldguard is there. if not we just fallback without regions.
            if (megaChat.getWorldGuardHook() != null) {
                return megaChat.getWorldGuardHook().getPlayersInRegionOfPlayer(sender);
            }
        }

        //if there is no chat range enabled then add all members of the chatChannel.
        if (chatChannel.getChatRange() == 0) {
            return chatChannel.getMembers();
        } else {
            // there is a chat range so return those players instead.
            return getPlayersInChatRange(chatChannel, sender);
        }
    }
}
