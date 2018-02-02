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

package me.max.megachat.channels;

import me.max.megachat.MegaChat;
import net.kyori.text.Component;
import net.kyori.text.TextComponent;
import net.kyori.text.event.ClickEvent;
import net.kyori.text.event.HoverEvent;
import net.kyori.text.serializer.ComponentSerializers;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Format {

    private MegaChat megaChat;

    private String groupName;
    private String format;

    private String channel;
    private String prefix;
    private String name;
    private String suffix;

    private String channel_tooltip;
    private String prefix_tooltip;
    private String name_tooltip;
    private String suffix_tooltip;

    private String channel_click_command;
    private String prefix_click_command;
    private String name_click_command;
    private String suffix_click_command;

    public Format(MegaChat megaChat, String groupName, ConfigurationSection formatSection) {
        this.megaChat = megaChat;

        this.groupName = groupName;
        this.format = formatSection.getString("format");

        this.channel = formatSection.getString("channel");
        this.prefix = formatSection.getString("prefix");
        this.name = formatSection.getString("name");
        this.suffix = formatSection.getString("suffix");

        this.channel_tooltip = formatSection.getString("tooltips.channel");
        this.prefix_tooltip = formatSection.getString("tooltips.prefix");
        this.name_tooltip = formatSection.getString("tooltips.name");
        this.suffix_tooltip = formatSection.getString("tooltips.suffix");

        this.channel_click_command = formatSection.getString("click_commands.channel");
        this.prefix_click_command = formatSection.getString("click_commands.prefix");
        this.name_click_command = formatSection.getString("click_commands.name");
        this.suffix_click_command = formatSection.getString("click_commands.suffix");
    }

    public String getFormattedMessage(Player sender) {
        String formattedFormat = format;
        //channel
        formattedFormat = formattedFormat.replace("%channel%", channel);
        //prefix
        formattedFormat = formattedFormat.replace("%prefix%", prefix);
        //name
        formattedFormat = formattedFormat.replace("%name%", name.replace("%username%", sender.getName()).replace("%displayname%", sender.getDisplayName()));
        //suffix
        formattedFormat = formattedFormat.replace("%suffix%", suffix);
        //message.
        formattedFormat = formattedFormat.replace("%message%", "%2$s");

        if (megaChat.getPlaceholderApiHook() == null) return formattedFormat;
        return megaChat.getPlaceholderApiHook().setPlaceholders(sender, formattedFormat);
    }

    public String getFormattedJsonMessage(Player sender) {
        String formattedFormat = ChatColor.translateAlternateColorCodes('&', format);

        //channel
        TextComponent.Builder channelFormat = TextComponent.builder(ChatColor.translateAlternateColorCodes('&', channel));
        if (!channel_tooltip.equalsIgnoreCase("")) {
            channelFormat.hoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.of(channel_tooltip)));
        }
        if (!channel_click_command.equalsIgnoreCase("")) {
            channelFormat.clickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, channel_click_command));
        }
        formattedFormat = formattedFormat.replace("%channel%", toJson(channelFormat.build()));

        //prefix
        TextComponent.Builder prefixFormat = TextComponent.builder(ChatColor.translateAlternateColorCodes('&', prefix));
        if (!prefix_tooltip.equalsIgnoreCase("")) {
            prefixFormat.hoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.of(prefix_tooltip)));
        }
        if (!prefix_click_command.equalsIgnoreCase("")) {
            prefixFormat.clickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, prefix_click_command));
        }
        formattedFormat = formattedFormat.replace("%prefix%", toJson(prefixFormat.build()));

        //name
        TextComponent.Builder nameFormat = TextComponent.builder(ChatColor.translateAlternateColorCodes('&', name.replace("%username%", sender.getName()).replace("%displayname%", sender.getDisplayName())));
        if (!name_tooltip.equalsIgnoreCase("")) {
            prefixFormat.hoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.of(name_tooltip)));
        }
        if (!name_click_command.equalsIgnoreCase("")) {
            prefixFormat.clickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, name_click_command));
        }
        formattedFormat = formattedFormat.replace("%name%", toJson(nameFormat.build()));

        //suffix
        TextComponent.Builder suffixFormat = TextComponent.builder(ChatColor.translateAlternateColorCodes('&', suffix));
        if (!suffix_tooltip.equalsIgnoreCase("")) {
            prefixFormat.hoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.of(suffix_tooltip)));
        }
        if (!suffix_click_command.equalsIgnoreCase("")) {
            prefixFormat.clickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, suffix_click_command));
        }
        formattedFormat = formattedFormat.replace("%suffix%", toJson(suffixFormat.build()));

        return formattedFormat.replace("%message%", "%2$s");
    }

    private String toJson(Component c) {
        return ComponentSerializers.JSON.serialize(c);
    }

    public String getGroupName() {
        return groupName;
    }
}
