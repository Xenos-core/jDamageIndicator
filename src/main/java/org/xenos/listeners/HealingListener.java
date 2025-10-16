package org.xenos.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.xenos.JDamageIndicator;
import org.xenos.config.ConfigManager;
import org.xenos.utils.DamageDisplayManager;

public class HealingListener
implements Listener {
    private final JDamageIndicator plugin;
    private final ConfigManager config;
    private final DamageDisplayManager displayManager;

    public HealingListener(JDamageIndicator plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfigManager();
        this.displayManager = plugin.getDisplayManager();
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onEntityRegainHealth(EntityRegainHealthEvent event) {
        if (!this.config.isDamageIndicatorEnabled() || !this.config.showHealing()) {
            return;
        }
        Entity entity = event.getEntity();
        if (!(entity instanceof LivingEntity)) {
            return;
        }
        LivingEntity livingEntity = (LivingEntity)entity;
        if (entity instanceof Player && !this.config.showOnPlayers()) {
            return;
        }
        if (!(entity instanceof Player) && !this.config.showOnMobs()) {
            return;
        }
        double healing = event.getAmount();
        if (healing <= 0.0) {
            return;
        }
        if (this.config.isDebugEnabled() && this.config.logHealingEvents()) {
            this.plugin.getLogger().info("Healing event: " + String.valueOf(entity.getType()) + " healed " + healing + " health");
        }
        this.displayManager.showDamageIndicator(livingEntity, healing, false, true);
    }
}
