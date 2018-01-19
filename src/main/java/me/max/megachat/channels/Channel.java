package me.max.megachat.channels;

import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class Channel {

    private String name;
    private List<Player> members;
    private Map<String, String> formats;

    private List<World> worlds;

    //zero or null means off.
    private int chatRange;
    private double messageCost;
    private Map<String, String> chatFilter;

    private boolean autoJoin;


    public Channel(String name, List<Player> members, Map<String, String> formats, List<World> worlds, int chatRange, double messageCost, Map<String, String> chatFilter, boolean autoJoin) {
        this.name = name;
        this.members = members;
        this.formats = formats;
        this.worlds = worlds;
        this.chatRange = chatRange;
        this.messageCost = messageCost;
        this.chatFilter = chatFilter;
        this.autoJoin = autoJoin;
    }

    public void addMember(Player member) {
        if (members.contains(member)) return;
        members.add(member);
    }

    public void removeMember(Player member) {
        if (!members.contains(member)) return;
        members.remove(member);
    }

    public void addFormat(String name, String format) {
        formats.put(name, format);
    }

    public void removeFormat(String name) {
        formats.remove(name);
    }

    public String getFormat(String name) {
        return formats.get(name);
    }

    public void addWorld(World world) {
        worlds.add(world);
    }

    public void removeWorld(World world) {
        worlds.remove(world);
    }

    public void addChatFilter(String word, String replacement) {
        chatFilter.put(word, replacement);
    }

    public void removeChatFilter(String word) {
        chatFilter.remove(word);
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

    public Map<String, String> getFormats() {
        return formats;
    }

    public String getName() {
        return name;
    }

    public void setChatRange(int chatRange) {
        this.chatRange = chatRange;
    }

    public void setFormats(Map<String, String> formats) {
        this.formats = formats;
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

    public Map<String, String> getChatFilter() {
        return chatFilter;
    }

    public void setChatFilter(Map<String, String> chatFilter) {
        this.chatFilter = chatFilter;
    }

    public boolean isAutoJoin() {
        return autoJoin;
    }

    public void setAutoJoin(boolean autoJoin) {
        this.autoJoin = autoJoin;
    }
}
