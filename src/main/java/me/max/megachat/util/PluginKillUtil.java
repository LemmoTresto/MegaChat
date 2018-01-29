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

package me.max.megachat.util;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class PluginKillUtil {

    public static List<Plugin> findConflictingPlugins() {
        List<Plugin> conflictingPlugins = new ArrayList<>();

        //EssentialsChat
        Plugin ec = Bukkit.getPluginManager().getPlugin("EssentialsChat");
        if (ec != null && ec.isEnabled()) conflictingPlugins.add(ec);

        //VentureChat
        Plugin vc = Bukkit.getPluginManager().getPlugin("VentureChat");
        if (vc != null && vc.isEnabled()) conflictingPlugins.add(vc);

        return conflictingPlugins;
    }

    public static boolean killPlugins(List<Plugin> plugins) {
        boolean succeeded = true;
        for (Plugin pl : plugins) {
            try {
                Bukkit.getPluginManager().disablePlugin(pl);
            } catch (Exception e) {
                succeeded = false;
            }
        }
        return succeeded;
    }
}
