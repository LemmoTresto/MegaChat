package me.max.megachat;

import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public final class MegaChat extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        new Metrics(this);
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    public void info(String info){
        this.getLogger().info(info);
    }

    public void warning(String warning){
        this.getLogger().warning(warning);
    }

    public void error(String error){
        this.getLogger().severe(error);
    }
}
