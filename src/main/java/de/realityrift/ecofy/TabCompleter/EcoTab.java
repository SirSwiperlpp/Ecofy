package de.realityrift.ecofy.TabCompleter;

import de.realityrift.ecofy.Commands.EcoCMD;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EcoTab implements TabCompleter {

    private final EcoCMD ecoCMD;

    public EcoTab(EcoCMD ecoCMD)
    {
        this.ecoCMD = ecoCMD;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        List<String> completions = new ArrayList<>();

        if (sender.isOp()) {
            if (args.length == 1) {
                List<String> subCommands = Arrays.asList("pay", "money", "set", "reset", "summonbanker");
                StringUtil.copyPartialMatches(args[0], subCommands, completions);
            }

            if (args.length == 2 && args[0].equalsIgnoreCase("pay")) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!player.getName().equalsIgnoreCase(sender.getName())) {
                        completions.add(player.getName());
                    }
                }
            }

            if (args.length == 2 && args[0].equalsIgnoreCase("set")) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    completions.add(player.getName());
                }
            }

            if (args.length == 2 && args[0].equalsIgnoreCase("reset")) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    completions.add(player.getName());
                }
            }

            Collections.sort(completions);
            return completions;
        }

        if (args.length == 1) {
            List<String> subCommands = Arrays.asList("pay", "money");
            StringUtil.copyPartialMatches(args[0], subCommands, completions);
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("pay")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!player.getName().equalsIgnoreCase(sender.getName())) {
                    completions.add(player.getName());
                }
            }
        }

        if (sender.hasPermission("eco.money.other"))
        {
            if (args.length == 2 && args[0].equalsIgnoreCase("money")) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!player.getName().equalsIgnoreCase(sender.getName())) {
                        completions.add(player.getName());
                    }
                }
            }
        }

        Collections.sort(completions);
        return completions;
    }
}
