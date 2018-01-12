package me.max.megachat;

import me.max.megachat.api.Api;
import me.max.megachat.listeners.ChatListener;
import me.max.megachat.util.ConfigUtil;
import me.max.megachat.util.MessagesUtil;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class MegaChat extends JavaPlugin {

    private Api api;
    private Metrics metrics;
    private File configFile = new File(getDataFolder() + "/config.yml");
    private File messagesFile = new File(getDataFolder() + "/messages.yml");

    @Override
    public void onEnable() {
        //if files not present then create them
        //message and config files.
        ConfigUtil.saveDefaultConfig(configFile);
        MessagesUtil.saveDefaultMessages(messagesFile);

        //setup bstats metrics
        metrics = new Metrics(this);

        //init listeners which register themselves.
        new ChatListener(this);

        //setup api.
        api = new Api();

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

    public Metrics getMetrics(){
        return metrics;
    }

    public Api getApi(){
        return api;
    }

}
