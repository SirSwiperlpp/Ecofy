package de.realityrift.ecofy.Commands;

import de.realityrift.ecofy.API.EcoAPI;
import de.realityrift.ecofy.Language.Language;
import de.realityrift.ecofy.Main.Main;
import de.realityrift.ecofy.Provider.EcoProvider;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.SQLException;

public class EcoCMD implements CommandExecutor
{

    static Language language = new Language(new File(Main.getInstance().getDataFolder(), "lang.ini"));

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player))
            return true;

        Player player = (Player) sender;

        if (strings.length < 1)
        {
            String money = String.valueOf(EcoProvider.getPlayerMoney(String.valueOf(player.getUniqueId())));
            sender.sendMessage(language.get("prefix") + language.translateString("bank.money", money));
            return true;
        }

        switch (strings[0].toLowerCase())
        {
            case "pay":
                if (!Main.config.getBoolean("enableCommands.pay"))
                {
                    player.sendMessage(language.get("prefix") + language.get("command.disabled"));
                    return true;
                }

                if (strings.length < 3)
                {
                    player.sendMessage(language.get("prefix") + language.get("pay.usage"));
                    return true;
                }

                String filteredValue = strings[2].replaceAll("[-+]", "");

                if (!isNumber(filteredValue))
                {
                    player.sendMessage(language.get("prefix") + language.get("pay.needs.amount"));
                    return true;
                }

                Player reciever = Bukkit.getPlayer(strings[1]);
                if (reciever == null)
                {
                    player.sendMessage(language.get("prefix") + language.get("reciever.offline"));
                    return true;
                }

                int updatevalue = Integer.parseInt(filteredValue);
                EcoAPI.transferMoney(player, reciever, updatevalue);
                break;

            case "money":
                if (!Main.config.getBoolean("enableCommands.money"))
                {
                    player.sendMessage(language.get("prefix") + language.get("command.disabled"));
                    return true;
                }

                if (strings.length == 2)
                {
                    if (!player.hasPermission("eco.money.other"))
                    {
                        player.sendMessage(language.get("prefix") + language.get("no.permission"));
                        return true;
                    }

                    if (Bukkit.getPlayer(strings[1]) == null)
                    {
                        player.sendMessage(language.get("prefix") + language.get("reciever.offline"));
                        return true;
                    }
                    Player player2 = Bukkit.getPlayer(strings[1]);
                    int playermoney = EcoProvider.getPlayerMoney(String.valueOf(player2.getUniqueId()));
                    player.sendMessage(language.get("prefix") + language.translateString("bank.money.other", player2.getName(), String.valueOf(playermoney)));
                } else {
                    String money = String.valueOf(EcoProvider.getPlayerMoney(String.valueOf(player.getUniqueId())));
                    sender.sendMessage(language.get("prefix") + language.translateString("bank.money", money));
                    return true;
                }
                break;

            case "set":
                break;

            case "reset":
                break;

            case "summonbanker":
                player.sendMessage(language.get("prefix") + language.get("command.wip"));
                break;
        }

        return false;
    }

    public boolean isNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
