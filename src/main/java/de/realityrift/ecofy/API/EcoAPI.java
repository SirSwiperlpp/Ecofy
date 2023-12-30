package de.realityrift.ecofy.API;

import de.realityrift.ecofy.Language.Language;
import de.realityrift.ecofy.Main.Main;
import de.realityrift.ecofy.Provider.EcoProvider;
import org.bukkit.entity.Player;

import java.io.File;

public class EcoAPI
{
    static Language language = new Language(new File(Main.getInstance().getDataFolder(), "lang.ini"));

    public static void transferMoney(Player sender, Player reciever, int updatevalue)
    {
        if (EcoProvider.getPlayerMoney(String.valueOf(sender.getUniqueId())) < updatevalue)
        {
            sender.sendMessage(language.get("prefix") + language.translateString("money.send.failed.not.enough"));
            return;
        }

        int senderbank = EcoProvider.getPlayerMoney(String.valueOf(sender.getUniqueId()));
        int recieverbank = EcoProvider.getPlayerMoney(String.valueOf(reciever.getUniqueId()));

        int moneyfromsender = senderbank - updatevalue;
        EcoProvider.updateMoney(String.valueOf(sender.getUniqueId()), moneyfromsender);
        sender.sendMessage(language.get("prefix") + language.translateString("money.send", String.valueOf(updatevalue), reciever.getName()));
        int moneytoreciever = recieverbank + updatevalue;
        EcoProvider.updateMoney(String.valueOf(reciever.getUniqueId()), moneytoreciever);
        reciever.sendMessage(language.get("prefix") + language.translateString("money.recieve", String.valueOf(updatevalue), sender.getName()));

        return;
    }

    public static void addMoney(String uuid, int updatevalue)
    {
        int playerbank = EcoProvider.getPlayerMoney(String.valueOf(uuid));
        int newvalue = playerbank + updatevalue;
        EcoProvider.updateMoney(String.valueOf(uuid), newvalue);
        return;
    }

    public static void removeMoney(String uuid, int updatevalue)
    {
        int playerbank = EcoProvider.getPlayerMoney(uuid);
        int newvalue = playerbank - updatevalue;
        EcoProvider.updateMoney(uuid, newvalue);
    }

    public static void setMoney(String uuid, int updatevalue)
    {
        EcoProvider.updateMoney(uuid, updatevalue);
    }

}
