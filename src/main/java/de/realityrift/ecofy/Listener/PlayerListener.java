package de.realityrift.ecofy.Listener;

import de.realityrift.ecofy.Provider.EcoProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class PlayerListener implements Listener
{

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws SQLException {
        Player player = event.getPlayer();

        if (!EcoProvider.checkForPlayer(player).equals(player.getName()))
        {
            EcoProvider.insertPlayer(player);
            Bukkit.getLogger().info(player.getName() + " is now in your database");
            return;
        }
    }

}
