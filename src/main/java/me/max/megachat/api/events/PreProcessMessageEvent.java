package me.max.megachat.api.events;

import me.max.megachat.channels.Channel;
import org.bukkit.entity.Player;

public class PreProcessMessageEvent extends MegaChatEvent {

    private boolean cancelled;
    private String message;
    private Player sender;
    private Channel channel;

    public PreProcessMessageEvent(String message, Player sender, Channel channel) {
        this.sender = sender;
        this.message = message;
        this.channel = channel;
        cancelled = false;
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

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}