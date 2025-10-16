package org.xenos;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.xenos.commands.JDICommand;
import org.xenos.commands.TabCompleter;
import org.xenos.config.ConfigManager;
import org.xenos.listeners.DamageListener;
import org.xenos.listeners.HealingListener;
import org.xenos.utils.DamageDisplayManager;

public class JDamageIndicator
extends JavaPlugin {
    private static JDamageIndicator instance;
    private ConfigManager configManager;
    private DamageDisplayManager displayManager;
    private DamageListener damageListener;
    private HealingListener healingListener;

    public void onEnable() {
        instance = this;
        this.configManager = new ConfigManager(this);
        this.configManager.loadConfig();
        this.displayManager = new DamageDisplayManager(this);
        this.damageListener = new DamageListener(this);
        this.healingListener = new HealingListener(this);
        this.getServer().getPluginManager().registerEvents((Listener)this.damageListener, (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)this.healingListener, (Plugin)this);
        JDICommand commandExecutor = new JDICommand(this);
        TabCompleter tabCompleter = new TabCompleter();
        this.getCommand("xdi").setExecutor((CommandExecutor)commandExecutor);
        this.getCommand("xdi").setTabCompleter((org.bukkit.command.TabCompleter)tabCompleter);
        this.getLogger().info("jDamageIndicator v" + this.getDescription().getVersion() + " has been enabled!");
        this.getLogger().info("Author: Macronis");
        this.getLogger().info("Website: " + this.getDescription().getWebsite());
        if (!this.configManager.isDamageIndicatorEnabled()) {
            this.getLogger().warning("Damage indicators are currently disabled in config.yml");
        }
    }

    public void onDisable() {
        if (this.displayManager != null) {
            this.displayManager.cleanup();
        }
        this.getLogger().info("jDamageIndicator has been disabled!");
        instance = null;
    }

    public static JDamageIndicator getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return this.configManager;
    }

    public DamageDisplayManager getDisplayManager() {
        return this.displayManager;
    }

    public void reloadPlugin() {
        this.configManager.reloadConfig();
        this.getLogger().info("Configuration reloaded successfully!");
    }
}
