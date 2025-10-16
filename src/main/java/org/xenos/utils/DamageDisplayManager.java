package org.xenos.utils;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.xenos.JDamageIndicator;
import org.xenos.config.ConfigManager;
import org.xenos.utils.ColorUtils;

public class DamageDisplayManager {
    private final JDamageIndicator plugin;
    private final ConfigManager config;
    private final Map<UUID, ArmorStand> activeIndicators;
    private final Map<UUID, BukkitTask> cleanupTasks;
    private BukkitTask updateTask;

    public DamageDisplayManager(JDamageIndicator plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfigManager();
        this.activeIndicators = new ConcurrentHashMap<UUID, ArmorStand>();
        this.cleanupTasks = new ConcurrentHashMap<UUID, BukkitTask>();
        this.startUpdateTask();
    }

    public void showDamageIndicator(LivingEntity entity, double amount, boolean isCritical, boolean isHealing) {
        String color;
        String format;
        if (this.activeIndicators.size() >= this.config.getMaxIndicators()) {
            this.removeOldestIndicator();
        }
        String formattedAmount = this.formatAmount(amount);
        if (isHealing) {
            format = this.config.getHealingFormat();
            color = this.config.getHealingColor();
        } else if (isCritical) {
            format = this.config.getDamageFormat();
            color = this.config.getCriticalColor();
        } else {
            format = this.config.getDamageFormat();
            color = this.config.getDamageColor();
        }
        String displayText = format.replace("{amount}", formattedAmount);
        displayText = ColorUtils.translateColors(color + displayText);
        final Location spawnLocation = entity.getLocation().add(0.0, entity.getHeight() + this.config.getYOffset(), 0.0);
        final UUID indicatorId = UUID.randomUUID();
        final String finalDisplayText = displayText;
        new BukkitRunnable(){

            public void run() {
                ArmorStand armorStand = (ArmorStand)spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.ARMOR_STAND);
                armorStand.setVisible(false);
                armorStand.setGravity(false);
                armorStand.setCanPickupItems(false);
                armorStand.setMarker(true);
                armorStand.setSmall(true);
                armorStand.setInvulnerable(true);
                armorStand.setSilent(true);
                armorStand.setCollidable(false);
                armorStand.setBasePlate(false);
                armorStand.setArms(false);
                armorStand.setCustomName(finalDisplayText);
                armorStand.setCustomNameVisible(true);
                DamageDisplayManager.this.activeIndicators.put(indicatorId, armorStand);
                BukkitTask cleanupTask = new BukkitRunnable(){

                    public void run() {
                        DamageDisplayManager.this.removeIndicator(indicatorId);
                    }
                }.runTaskLater((Plugin)DamageDisplayManager.this.plugin, (long)DamageDisplayManager.this.config.getDisplayDurationTicks());
                DamageDisplayManager.this.cleanupTasks.put(indicatorId, cleanupTask);
                if (DamageDisplayManager.this.config.isDebugEnabled()) {
                    DamageDisplayManager.this.plugin.getLogger().info("Created damage indicator: " + finalDisplayText + " at " + String.valueOf(spawnLocation));
                }
            }
        }.runTask((Plugin)this.plugin);
    }

    private String formatAmount(double amount) {
        int decimalPlaces = this.config.getDecimalPlaces();
        if (decimalPlaces <= 0) {
            return String.valueOf((int)Math.round(amount));
        }
        StringBuilder pattern = new StringBuilder("#.");
        for (int i = 0; i < decimalPlaces; ++i) {
            pattern.append("0");
        }
        DecimalFormat df = new DecimalFormat(pattern.toString());
        return df.format(amount);
    }

    private void removeIndicator(UUID indicatorId) {
        BukkitTask cleanupTask;
        ArmorStand armorStand = this.activeIndicators.remove(indicatorId);
        if (armorStand != null && !armorStand.isDead()) {
            armorStand.remove();
        }
        if ((cleanupTask = this.cleanupTasks.remove(indicatorId)) != null && !cleanupTask.isCancelled()) {
            cleanupTask.cancel();
        }
    }

    private void removeOldestIndicator() {
        if (!this.activeIndicators.isEmpty()) {
            UUID oldestId = this.activeIndicators.keySet().iterator().next();
            this.removeIndicator(oldestId);
        }
    }

    private void startUpdateTask() {
        this.updateTask = new BukkitRunnable(){

            public void run() {
                for (ArmorStand armorStand : DamageDisplayManager.this.activeIndicators.values()) {
                    if (armorStand.isDead()) continue;
                    Location current = armorStand.getLocation();
                    armorStand.teleport(current.add(0.0, 0.05, 0.0));
                }
            }
        }.runTaskTimer((Plugin)this.plugin, 0L, (long)this.config.getUpdateInterval());
    }

    public void cleanup() {
        if (this.updateTask != null && !this.updateTask.isCancelled()) {
            this.updateTask.cancel();
        }
        for (UUID indicatorId : this.activeIndicators.keySet()) {
            this.removeIndicator(indicatorId);
        }
        this.activeIndicators.clear();
        this.cleanupTasks.clear();
    }

    public int getActiveIndicatorCount() {
        return this.activeIndicators.size();
    }
}
