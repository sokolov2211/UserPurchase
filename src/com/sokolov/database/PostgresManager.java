package com.sokolov.database;


import com.sokolov.AppConfigManager;
import com.sokolov.Main;
import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by admin on 30.09.2018.
 */
public class PostgresManager {
    private static PostgresManager postgresManagerInstance;
    private static Object mutex = new Object();

    private Connection postgresConnection;


    private PostgresManager() {
        this.connection();
    }

    private void connection() {
        AppConfigManager configManager = Main.configManager;
        try {
            Class.forName("org.postgresql.Driver");
            String connectionString = String.format("jdbc:postgresql://%s:%s/%s",
                    configManager.getDatabaseHost(), configManager.getDatabasePort(),
                    configManager.getDatabaseName());
            postgresConnection = DriverManager
                    .getConnection(connectionString,
                            configManager.getDatabaseUser(),
                            configManager.getDatabasePassword());
            System.out.println("Opened database successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public boolean execQuery(String sql)  {
        try {
            Statement stmt = postgresConnection.createStatement();
            boolean res = stmt.execute(sql);
            stmt.close();
            return res;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
            return false;
        }
    }

    public ResultSet execSelectQuery(String sql) {
        try {
            Statement stmt = postgresConnection.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            return  res;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
            return null;
        }

    }

    protected void finalize() {
        try {
            postgresConnection.close();
        } catch (SQLException e) {

        }
    }

    public static PostgresManager getInstance() {
        PostgresManager result = postgresManagerInstance;
        if (result == null) {
            synchronized (mutex) {
                result = postgresManagerInstance;
                if (result == null) {
                    postgresManagerInstance = result = new PostgresManager();
                }
            }
        }
        return result;
    }


}
