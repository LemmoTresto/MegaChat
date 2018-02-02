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

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import me.max.megachat.MegaChat;
import net.kyori.text.Component;
import net.kyori.text.serializer.ComponentSerializers;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PacketChatListener extends PacketAdapter {

    private MegaChat megaChat;

    public PacketChatListener(MegaChat megaChat) {
        super(megaChat, ListenerPriority.HIGHEST, PacketType.Play.Server.CHAT);

        this.megaChat = megaChat;
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        if (event.getPacketType() == PacketType.Play.Server.CHAT) {
            PacketContainer packet = event.getPacket();

            String raw = packet.getChatComponents().read(0).getJson();

            JSONObject json;
            try {
                json = (JSONObject) (new JSONParser()).parse(raw);
            } catch (ParseException e) {
                e.printStackTrace();
                event.setCancelled(true);
                return;
            }
            Component component = ComponentSerializers.JSON.deserialize(json.toString());

            // look what for chat it is.
            Object obj = json.get("translate");
            if (obj == null) {
                //it's normal chat (we process it)
                WrappedChatComponent chatComponent = packet.getChatComponents().read(0);
                //todo

            }
        }

    }
}
