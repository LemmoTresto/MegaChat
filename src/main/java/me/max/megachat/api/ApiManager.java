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

package me.max.megachat.api;

import me.max.megachat.api.events.MegaChatEvent;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ApiManager {

    public List<Object> listeners = new ArrayList<>();

    public MegaChatEvent callEvent(MegaChatEvent event) {
        for (MegaChatEventPriority priority : MegaChatEventPriority.values()) {
            for (Object listener : listeners) {
                for (Method method : listener.getClass().getMethods()) {
                    // checks
                    if (method.getParameters().length != 1) continue; // only 1 param
                    if (!method.getParameters()[0].getType().isAssignableFrom(event.getClass()))
                        continue; //does event want this?
                    if (!method.isAnnotationPresent(MegaChatEventHandler.class)) continue; //look for our annotation

                    //loop over all annotations.
                    for (Annotation annotation : method.getAnnotations()) {
                        if (!(annotation instanceof MegaChatEventHandler)) continue; // get our own annotation

                        MegaChatEventHandler eventHandlerAnnotation = (MegaChatEventHandler) annotation;
                        if (eventHandlerAnnotation.priority() != priority)
                            continue; // not the priority we are at right now in the for loop.

                        // method has to be accessible.
                        if (!method.isAccessible()) method.setAccessible(true);

                        try {
                            method.invoke(listener, event);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return event;
    }
}
