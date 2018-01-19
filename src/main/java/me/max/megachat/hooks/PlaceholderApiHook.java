package me.max.megachat.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class PlaceholderApiHook {

    public String setPlaceholders(Player p, String string) {
        return PlaceholderAPI.setPlaceholders(p, string);
    }
}
