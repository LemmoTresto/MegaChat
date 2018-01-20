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
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class WorldChangeListener implements Listener {

    private MegaChat megaChat;

    public WorldChangeListener(MegaChat megaChat) {
        this.megaChat = megaChat;

        //register
        this.megaChat.getServer().getPluginManager().registerEvents(this, megaChat);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onWorldChange(PlayerChangedWorldEvent event) {
        // make sure it's enabled.
        if (megaChat.getConfig().getBoolean("per-world-chat.enabled")) {
            // check if per-world-chat channel exists.
            if (megaChat.getChannelManager().getChannelByName(event.getPlayer().getWorld().getName()) != null) {
                //remove and add player again to right channel.
                megaChat.getChannelManager().removePlayerFromChannel(event.getPlayer());
                megaChat.getChannelManager().getChannelByName(event.getPlayer().getWorld().getName()).addMember(event.getPlayer());
            }
        }
    }
}
