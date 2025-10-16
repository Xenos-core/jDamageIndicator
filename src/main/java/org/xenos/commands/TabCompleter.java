package org.xenos.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

public class TabCompleter
implements org.bukkit.command.TabCompleter {
    private static final List<String> SUBCOMMANDS = Arrays.asList("reload", "info", "status", "help");

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> completions = new ArrayList<String>();
        if (!sender.hasPermission("jdi.admin")) {
            return completions;
        }
        if (args.length == 1) {
            StringUtil.copyPartialMatches((String)args[0], SUBCOMMANDS, completions);
        }
        return completions;
    }
}
