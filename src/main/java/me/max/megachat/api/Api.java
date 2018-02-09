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

import me.max.megachat.api.exceptions.NoListenersFoundException;
import me.max.megachat.channels.ChannelManager;

import java.lang.reflect.Method;

public class Api {

    private ApiManager apiManager;
    private ChannelManager channelManager;

    public Api(ApiManager apiManager, ChannelManager channelManager) {
        this.apiManager = apiManager;
        this.channelManager = channelManager;
    }

    public void registerEvents(Object listener) {
        int eventListenerMethods = 0;
        for (Method method : listener.getClass().getMethods()) if (method.isAnnotationPresent(MegaChatEventHandler.class)) eventListenerMethods++;
        if (eventListenerMethods == 0) throw new NoListenersFoundException(listener);
        apiManager.addListener(listener);
    }

    public void unregisterEvents(Object listener) {
        apiManager.removeListener(listener);
    }

}
