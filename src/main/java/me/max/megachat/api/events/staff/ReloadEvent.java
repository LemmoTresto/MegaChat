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

package me.max.megachat.api.events.staff;

import me.max.megachat.api.events.MegaChatEvent;
import org.bukkit.command.CommandSender;

public class ReloadEvent extends MegaChatEvent {

    final private boolean cancelled;
    final private boolean succeeded;
    final private CommandSender requester;

    public ReloadEvent(boolean cancelled, boolean succeeded, CommandSender requester) {
        this.cancelled = cancelled;
        this.succeeded = succeeded;
        this.requester = requester;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public CommandSender getRequester() {
        return requester;
    }
}
