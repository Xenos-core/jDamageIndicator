package org.xenos.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.xenos.JDamageIndicator;

public class JDICommand
implements CommandExecutor {
    private final JDamageIndicator plugin;

    public JDICommand(JDamageIndicator plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String subCommand;
        if (!sender.hasPermission("xdi.admin")) {
            sender.sendMessage(String.valueOf(ChatColor.RED) + "You don't have permission to use this command!");
            return true;
        }
        if (args.length == 0) {
            this.sendHelp(sender);
            return true;
        }
        switch (subCommand = args[0].toLowerCase()) {
            case "reload": {
                this.handleReload(sender);
                break;
            }
            case "info": {
                this.handleInfo(sender);
                break;
            }
            case "status": {
                this.handleStatus(sender);
                break;
            }
            default: {
                this.sendHelp(sender);
            }
        }
        return true;
    }

    private void handleReload(CommandSender sender) {
        try {
            this.plugin.reloadPlugin();
            send(sender, ChatColor.GREEN, "jDamageIndicator configuration reloaded successfully!");
        }
        catch (Exception e) {
            send(sender, ChatColor.RED, "Error reloading configuration: " + e.getMessage());
            this.plugin.getLogger().severe("Error reloading configuration: " + e.getMessage());
        }
    }

    private void handleInfo(CommandSender sender) {
        send(sender, ChatColor.GOLD, "========== jDamageIndicator Info ==========");
        sendKeyValue(sender, "Version", this.plugin.getDescription().getVersion());
        sendKeyValue(sender, "Author", "Macronis");
        sendKeyValue(sender, "Website", this.plugin.getDescription().getWebsite());
        sendKeyValue(sender, "Description", this.plugin.getDescription().getDescription());
        send(sender, ChatColor.GOLD, "=========================================");
    }

    private void handleStatus(CommandSender sender) {
        send(sender, ChatColor.GOLD, "========== jDamageIndicator Status ==========");
        boolean enabled = this.plugin.getConfigManager().isDamageIndicatorEnabled();
        send(sender, ChatColor.YELLOW, "Damage Indicators: " + (enabled ? ChatColor.GREEN + "ENABLED" : ChatColor.RED + "DISABLED"));
        if (enabled) {
            send(sender, ChatColor.YELLOW, "Show on Players: " + (this.plugin.getConfigManager().showOnPlayers() ? ChatColor.GREEN + "YES" : ChatColor.RED + "NO"));
            send(sender, ChatColor.YELLOW, "Show on Mobs: " + (this.plugin.getConfigManager().showOnMobs() ? ChatColor.GREEN + "YES" : ChatColor.RED + "NO"));
            send(sender, ChatColor.YELLOW, "Show Healing: " + (this.plugin.getConfigManager().showHealing() ? ChatColor.GREEN + "YES" : ChatColor.RED + "NO"));
            sendKeyValue(sender, "Critical Threshold", String.valueOf(this.plugin.getConfigManager().getCriticalThreshold()));
            sendKeyValue(sender, "Display Duration", this.plugin.getConfigManager().getDisplayDurationTicks() + " ticks");
        }
        send(sender, ChatColor.GOLD, "==========================================");
    }

    private void sendHelp(CommandSender sender) {
        send(sender, ChatColor.GOLD, "========== jDamageIndicator Commands ==========");
        sendCommand(sender, "/xdi help", "Show this help message");
        sendCommand(sender, "/xdi reload", "Reload plugin configuration");
        sendCommand(sender, "/xdi info", "Show plugin information");
        sendCommand(sender, "/xdi status", "Show plugin status");
        send(sender, ChatColor.GOLD, "============================================");
    }

    private void send(CommandSender sender, ChatColor color, String message) {
        sender.sendMessage(color + message);
    }

    private void sendKeyValue(CommandSender sender, String key, String value) {
        sender.sendMessage(ChatColor.YELLOW + key + ": " + ChatColor.WHITE + value);
    }

    private void sendCommand(CommandSender sender, String command, String description) {
        sender.sendMessage(ChatColor.YELLOW + command + ChatColor.WHITE + " - " + description);
    }
}
