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

public class VersionUtil {

    public static boolean supportsJson() {
        return isVersion("1.8") || isVersion("1.9") || isVersion("1.10") || isVersion("1.11") || isVersion("1.12");
    }

    public static boolean isVersion(String ver) {
        return Bukkit.getVersion().contains(ver) || Bukkit.getServer().getClass().getPackage().getName().contains(ver);
    }
}
