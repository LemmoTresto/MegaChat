package me.max.megachat;

import me.max.megachat.api.Api;
import me.max.megachat.api.ApiManager;
import me.max.megachat.channels.ChannelManager;
import me.max.megachat.listeners.ChatListener;
import me.max.megachat.util.ConfigUtil;
import me.max.megachat.util.MessagesUtil;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public final class MegaChat extends JavaPlugin {

    private static Api api;
    private ApiManager apiManager;
    private ChannelManager channelManager;
    private Metrics metrics;

    @Override
    public void onEnable() {
        //if files not present then create them    //message and configs files.
        ConfigUtil.saveDefaultConfig(this);
        MessagesUtil.saveDefaultMessages(this);

        //setup bstats metrics
        info("Initialising Bstats metrics..");
        try {
            metrics = new Metrics(this);
            info("Initialised metrics successfully.");
        } catch (Exception e) {
            warning("Could not initialise Bstats metrics. Disabled metrics.");
            metrics = null;
            if (getConfig().getInt("debugMode") == 2) {
                e.printStackTrace();
            }
        }

        //init listeners which register themselves.
        info("Initialising listeners..");
        try {
            new ChatListener(this, apiManager);
            info("Initialised listeners successfully.");
        } catch (Exception e) {
            error("Could not initialise listeners. Shutting down..");
            e.printStackTrace();
            getPluginLoader().disablePlugin(this);
        }

        info("Initialising channel manager..");
        try {
            channelManager = new ChannelManager();
        } catch (Exception e) {
            error("Could not initialise channel manager. Shutting down..");
            e.printStackTrace();
            getPluginLoader().disablePlugin(this);
        }

        //setup api.
        info("Initialising API..");
        try {
            apiManager = new ApiManager();
            api = new Api(apiManager, channelManager);
            info("Initialised API successfully.");
        } catch (Exception e) {
            error("Could not initialise API. Shutting down..");
            e.printStackTrace();
            getPluginLoader().disablePlugin(this);
        }

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

    public static Api getApi() {
        return api;
    }

}
