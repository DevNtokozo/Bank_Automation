package org.bankautomation.database;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.Connection;

public class DatabaseConnectionTest {

    @Test(groups = {"database", "smoke"})
    public void shouldConnectToDatabase() {

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            Assert.assertNotNull(
                    connection,
                    "Database connection should not be null"
            );

            Assert.assertFalse(
                    connection.isClosed(),
                    "Database connection should be open"
            );

            System.out.println(
                    "Database connection successful"
            );

        } catch (Exception e) {

            Assert.fail(
                    "Database connection failed: "
                            + e.getMessage()
            );
        }
    }
}