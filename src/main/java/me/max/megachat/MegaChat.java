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

package me.max.megachat;

import me.max.megachat.api.Api;
import me.max.megachat.api.ApiManager;
import me.max.megachat.channels.ChannelManager;
import me.max.megachat.hooks.PlaceholderApiHook;
import me.max.megachat.hooks.ProtocolLibHook;
import me.max.megachat.hooks.VaultHook;
import me.max.megachat.listeners.ChatListener;
import me.max.megachat.listeners.PlayerJoinListener;
import me.max.megachat.listeners.PlayerQuitListener;
import me.max.megachat.listeners.WorldChangeListener;
import me.max.megachat.util.ConfigUtil;
import me.max.megachat.util.LangUtil;
import me.max.megachat.util.MessagesUtil;
import me.max.megachat.util.PluginKillUtil;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class MegaChat extends JavaPlugin {

    private static Api api = null;
    private ApiManager apiManager;
    private ChannelManager channelManager;
    private Metrics metrics;
    private PlaceholderApiHook placeholderApiHook = null;
    private VaultHook vaultHook = null;
    private ProtocolLibHook protocolLibHook = null;
    private YamlConfiguration messagesFile = null;

    //todo add debug messages correctly.

    @Override
    public void onEnable() {
        //if files not present then create them
        ConfigUtil.saveDefaultConfig(this);
        MessagesUtil.saveDefaultMessages(this);
        reloadConfig();

        messagesFile = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "messages.yml"));

        if (getConfig().getBoolean("kill-conflicting-plugins")) {
            boolean succeeded = PluginKillUtil.killPlugins(PluginKillUtil.findConflictingPlugins());
            if (succeeded) debug("Killed conflicting plugins");
        }

        //todo add config language changing.
        //check if the desired language is used if not copy
        if (!getConfig().getString("current-language").equalsIgnoreCase(getConfig().getString("language"))) {
            LangUtil.updateFiles(this, getConfig().getString("language"));
        }
        //setup bstats metrics
        if (getConfig().getBoolean("metrics")) {
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
        } else {
            info("Not initialising Bstats metrics because this is disabled in the config.");
        }

        //init listeners which register themselves.
        info("Initialising listeners..");
        try {
            new ChatListener(this);
            new PlayerJoinListener(this);
            new PlayerQuitListener(this);
            new WorldChangeListener(this);
            info("Initialised listeners successfully.");
        } catch (Exception e) {
            error("Could not initialise listeners. Shutting down..");
            e.printStackTrace();
            shutdown();
        }

        //init channel manager
        info("Initialising channel manager..");
        try {
            channelManager = new ChannelManager(this);
        } catch (Exception e) {
            error("Could not initialise channel manager. Shutting down..");
            e.printStackTrace();
            shutdown();
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
            shutdown();
        }

        //hooks
        info("Initialising hooks..");
        try {
            if (Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")) {
                protocolLibHook = new ProtocolLibHook(this);
                info("Hooks - Successfully hooked into ProtocolLib.");
            }
            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                placeholderApiHook = new PlaceholderApiHook(this);
                info("Hooks - Successfully hooked into PlaceholderAPI.");
            }
            if (Bukkit.getPluginManager().isPluginEnabled("Vault")) {
                vaultHook = new VaultHook();
                info("Hooks - Successfully hooked into Vault.");
            }
        } catch (Exception e) {
            warning("Hooks - Something went wrong initialising.");
            if (getConfig().getInt("debug-mode") == 2) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDisable() {
    }

    public void debug(String message) {
        if (getConfig().getInt("debug-mode") > 0) {
            info("[DEBUG] " + message);
        }
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

    public void shutdown() {
        Bukkit.getServer().getPluginManager().disablePlugin(this);
    }

    public static Api getApi() {
        return api;
    }

    public Metrics getMetrics() {
        return metrics;
    }

    public ApiManager getApiManager() {
        return apiManager;
    }

    public ChannelManager getChannelManager() {
        return channelManager;
    }

    public YamlConfiguration getMessages() {
        return messagesFile;
    }

    public PlaceholderApiHook getPlaceholderApiHook() {
        return placeholderApiHook;
    }

    public VaultHook getVaultHook() {
        return vaultHook;
    }

    public ProtocolLibHook getProtocolLibHook() {
        return protocolLibHook;
    }
}
