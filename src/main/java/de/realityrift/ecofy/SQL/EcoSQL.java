package de.realityrift.ecofy.SQL;

import de.realityrift.ecofy.Language.Language;
import de.realityrift.ecofy.Main.Main;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class EcoSQL
{


    static Language language = new Language(new File(Main.getInstance().getDataFolder(), "lang.ini"));

    public static String host = Main.config.getString("host");
    public static String port = Main.config.getString("port");
    public static String username = Main.config.getString("user");
    public static String password = Main.config.getString("pwd");
    public static Connection con;

    // connect
    public static void connect(String database) {
        if (!isConnected()) {
            try {
                String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?connectTimeout=0&socketTimeout=0&autoReconnect=true";

                con = DriverManager.getConnection(url, username, password);
                System.out.println(language.translateString("connection.established", database));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // disconnect
    public static void disconnect() {
        if (isConnected()) {
            try {
                con.close();
                System.out.println(language.get("connection.dc"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // isConnected
    public static boolean isConnected() {
        return (con != null);
    }

    // getConnection
    public static Connection getConnection() {
        return con;
    }


}

