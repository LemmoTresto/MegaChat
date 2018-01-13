package me.max.megachat.channels;

import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;

public class Channel {

    private String name;
    private List<Player> members;
    private String format;

    private List<World> worlds;

    //zero or null means off.
    private int chatRange;
    private double messageCost;


    public Channel(String name, List<Player> members, String format, List<World> worlds, int chatRange, double messageCost) {
        this.name = name;
        this.members = members;
        this.format = format;
        this.worlds = worlds;
        this.chatRange = chatRange;
        this.messageCost = messageCost;
    }

    public void addMember(Player member) {
        if (members.contains(member)) return;
        members.add(member);
    }

    public void removeMember(Player member) {
        if (!members.contains(member)) return;
        members.remove(member);
    }

    public int getChatRange() {
        return chatRange;
    }

    public double getMessageCost() {
        return messageCost;
    }

    public List<Player> getMembers() {
        return members;
    }

    public List<World> getWorlds() {
        return worlds;
    }

    public String getFormat() {
        return format;
    }

    public String getName() {
        return name;
    }

    public void setChatRange(int chatRange) {
        this.chatRange = chatRange;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setMembers(List<Player> members) {
        this.members = members;
    }

    public void setMessageCost(double messageCost) {
        this.messageCost = messageCost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWorlds(List<World> worlds) {
        this.worlds = worlds;
    }

    // I added this cause I'm new to builders and liked this.
    // Probably not useful.
    // Maybe for the api.
    public static class Builder {
        private String name;
        private List<Player> members;
        private String format;
        private List<World> worlds;
        private int chatRange;
        private double messageCost;

        public Channel build() {
            return new Channel(name, members, format, worlds, chatRange, messageCost);
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setMembers(List<Player> members) {
            this.members = members;
            return this;
        }

        public Builder setFormat(String format) {
            this.format = format;
            return this;
        }

        public Builder setWorlds(List<World> worlds) {
            this.worlds = worlds;
            return this;
        }

        public Builder setChatRange(int chatRange) {
            this.chatRange = chatRange;
            return this;
        }

        public Builder setMessageCost(double messageCost) {
            this.messageCost = messageCost;
            return this;
        }
    }
}
