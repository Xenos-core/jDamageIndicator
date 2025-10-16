package org.xenos.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.xenos.JDamageIndicator;

public class ConfigManager {
    private final JDamageIndicator plugin;
    private FileConfiguration config;

    public ConfigManager(JDamageIndicator plugin) {
        this.plugin = plugin;
    }

    public void loadConfig() {
        this.plugin.saveDefaultConfig();
        this.config = this.plugin.getConfig();
    }

    public void reloadConfig() {
        this.plugin.reloadConfig();
        this.config = this.plugin.getConfig();
    }

    public boolean isDamageIndicatorEnabled() {
        return this.config.getBoolean("damage-indicator.enabled", true);
    }

    public boolean showOnPlayers() {
        return this.config.getBoolean("damage-indicator.show-on-players", true);
    }

    public boolean showOnMobs() {
        return this.config.getBoolean("damage-indicator.show-on-mobs", true);
    }

    public String getDamageColor() {
        return this.config.getString("damage-indicator.color", "&c");
    }

    public double getCriticalThreshold() {
        return this.config.getDouble("damage-indicator.critical-threshold", 6.0);
    }

    public String getCriticalColor() {
        return this.config.getString("damage-indicator.critical-color", "&4");
    }

    public boolean showHealing() {
        return this.config.getBoolean("damage-indicator.show-healing", true);
    }

    public String getHealingColor() {
        return this.config.getString("damage-indicator.healing-color", "&a");
    }

    public int getDisplayDurationTicks() {
        return this.config.getInt("damage-indicator.display-duration-ticks", 30);
    }

    public double getYOffset() {
        return this.config.getDouble("damage-indicator.y-offset", 0.5);
    }

    public String getDamageFormat() {
        return this.config.getString("damage-indicator.format", "-{amount} \u2764");
    }

    public String getHealingFormat() {
        return this.config.getString("damage-indicator.healing-format", "+{amount} \u2764");
    }

    public int getDecimalPlaces() {
        return this.config.getInt("damage-indicator.decimal-places", 1);
    }

    public boolean isSoundEnabled() {
        return this.config.getBoolean("damage-indicator.sound-effects.enabled", true);
    }

    public String getDamageSound() {
        return this.config.getString("damage-indicator.sound-effects.damage-sound", "ENTITY_PLAYER_HURT");
    }

    public String getCriticalSound() {
        return this.config.getString("damage-indicator.sound-effects.critical-sound", "ENTITY_PLAYER_ATTACK_CRIT");
    }

    public String getHealingSound() {
        return this.config.getString("damage-indicator.sound-effects.healing-sound", "ENTITY_PLAYER_LEVELUP");
    }

    public float getSoundVolume() {
        return (float)this.config.getDouble("damage-indicator.sound-effects.volume", 0.5);
    }

    public float getSoundPitch() {
        return (float)this.config.getDouble("damage-indicator.sound-effects.pitch", 1.0);
    }

    public int getMaxIndicators() {
        return this.config.getInt("performance.max-indicators", 50);
    }

    public int getUpdateInterval() {
        return this.config.getInt("performance.update-interval", 2);
    }

    public boolean useInvisibleSpawnDelay() {
        return this.config.getBoolean("performance.invisible-spawn-delay", true);
    }

    public boolean isDebugEnabled() {
        return this.config.getBoolean("debug.enabled", false);
    }

    public boolean logDamageEvents() {
        return this.config.getBoolean("debug.log-damage-events", false);
    }

    public boolean logHealingEvents() {
        return this.config.getBoolean("debug.log-healing-events", false);
    }
}
