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

package me.max.megachat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CommandHandler {

    private List<Object> commandClasses = new ArrayList<>();

    public CommandHandler() {
    }

    public void registerCommand(Object obj) {
        for (Method method : obj.getClass().getMethods()) {
            if (method.isAnnotationPresent(MegaChatCommand.class)) {
                commandClasses.add(obj);
                return;
            }
        }
    }

    public boolean handle(CommandSender sender, Command command, String[] args) throws InvocationTargetException, IllegalAccessException {
        for (Object obj : commandClasses) {
            for (Method method : obj.getClass().getMethods()) {
                if (method.isAnnotationPresent(MegaChatCommand.class)) {
                    if (method.getAnnotation(MegaChatCommand.class).command().equalsIgnoreCase(command.getName())) {
                        return (boolean) method.invoke(sender, args);
                    }
                }
            }
        }
        return false;
    }
}
