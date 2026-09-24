package org.bankautomation.database;

import org.bankautomation.config.ConfigReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private DatabaseConnection() {
    }

    public static Connection getConnection() {

        try {
            return DriverManager.getConnection(
                    ConfigReader.get("db.url"),
                    ConfigReader.get("db.username"),
                    ConfigReader.get("db.password")
            );

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Failed to connect to PostgreSQL database",
                    e
            );
        }
    }
}