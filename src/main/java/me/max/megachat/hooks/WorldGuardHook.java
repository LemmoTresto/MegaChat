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

package me.max.megachat.hooks;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.max.megachat.MegaChat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WorldGuardHook {

    private MegaChat megaChat;
    private WorldGuardPlugin plugin;

    public WorldGuardHook(MegaChat megaChat) {
        this.megaChat = megaChat;

        plugin = WGBukkit.getPlugin();
    }

    public ApplicableRegionSet getRegionsOfPlayer(Player p) {
        return plugin.getRegionContainer().createQuery().getApplicableRegions(p.getLocation());
    }

    private List<Player> getPlayersInRegion(ProtectedRegion region) {
        List<Player> playersInRegion = new ArrayList<>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (getRegionsOfPlayer(p).getRegions().contains(region)) playersInRegion.add(p);
        }
        return playersInRegion;
    }

    public List<Player> getPlayersInRegionOfPlayer(Player p) {
        List<Player> playersInRegion = new ArrayList<>();
        for (ProtectedRegion region : getRegionsOfPlayer(p)) {
            //check if this region is in the channel.
            if (megaChat.getChannelManager().getChannelByPlayer(p).getRegions().contains(region.getId()))
                playersInRegion.addAll(getPlayersInRegion(region));
        }
        return playersInRegion;
    }
}
