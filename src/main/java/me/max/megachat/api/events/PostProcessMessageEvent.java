package me.max.megachat.api.events;

import me.max.megachat.channels.Channel;
import org.bukkit.entity.Player;

public class PostProcessMessageEvent extends MegaChatEvent{

    private final boolean cancelled;
    private final String message;
    private final Player sender;
    private final Channel channel;

    public PostProcessMessageEvent(boolean cancelled, String message, Player sender, Channel channel) {
        this.cancelled = cancelled;
        this.message = message;
        this.sender = sender;
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    public Player getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public boolean isCancelled() {
        return cancelled;
    }
}
