package de.realityrift.ecofy.Main;

import de.realityrift.ecofy.Language.Language;
import de.realityrift.ecofy.SQL.EcoSQL;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {

    private static Main instance;
    public static FileConfiguration config;
    public static Language language;

    public void loadConfiguration() {
        File datafolder = this.getDataFolder();
        File configFile = new File(datafolder + File.separator + "config.yml");

        if (!configFile.exists()) {
            this.saveResource("config.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public Main() {
        instance = this;
        language = new Language(new File(getDataFolder(), "lang.ini"));
    }

    public static Main getInstance() {
        return instance;
    }

    private void checkAndCreateLanguageFile() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        File languageFile = new File(getDataFolder(), "lang.ini");
        if (!languageFile.exists()) {
            getLogger().info("language.ini not found. Creating...");

            saveResource("lang.ini", true);
        }
    }

    @Override
    public void onEnable() {
        loadConfiguration();
        checkAndCreateLanguageFile();
        EcoSQL.connect("ecofydb");
    }

    @Override
    public void onDisable() {
        EcoSQL.disconnect();
    }
}
