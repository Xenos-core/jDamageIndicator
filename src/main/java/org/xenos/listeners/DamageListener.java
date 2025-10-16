package org.xenos.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.xenos.JDamageIndicator;
import org.xenos.config.ConfigManager;
import org.xenos.utils.DamageDisplayManager;

public class DamageListener
implements Listener {
    private final JDamageIndicator plugin;
    private final ConfigManager config;
    private final DamageDisplayManager displayManager;

    public DamageListener(JDamageIndicator plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfigManager();
        this.displayManager = plugin.getDisplayManager();
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onEntityDamage(EntityDamageEvent event) {
        if (!this.config.isDamageIndicatorEnabled()) {
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
        double damage = event.getFinalDamage();
        if (damage <= 0.0) {
            return;
        }
        if (this.config.isDebugEnabled() && this.config.logDamageEvents()) {
            this.plugin.getLogger().info("Damage event: " + String.valueOf(entity.getType()) + " took " + damage + " damage");
        }
        boolean isCritical = damage >= this.config.getCriticalThreshold();
        this.displayManager.showDamageIndicator(livingEntity, damage, isCritical, false);
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (this.config.isDebugEnabled() && this.config.logDamageEvents()) {
            Entity attacker = event.getDamager();
            Entity victim = event.getEntity();
            double damage = event.getFinalDamage();
            this.plugin.getLogger().info("Entity damage by entity: " + String.valueOf(attacker.getType()) + " damaged " + String.valueOf(victim.getType()) + " for " + damage);
        }
    }
}
