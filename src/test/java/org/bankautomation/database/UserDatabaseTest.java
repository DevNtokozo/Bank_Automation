package org.bankautomation.database;

import org.testng.Assert;
import org.testng.annotations.Test;

public class UserDatabaseTest {

    @Test(groups = {"database", "smoke"})
    public void shouldHaveUsersInDatabase() {

        int userCount = DatabaseUtils.getUserCount();

        System.out.println(
                "Users in database: " + userCount
        );

        Assert.assertTrue(
                userCount > 0,
                "Database should contain at least one user"
        );
    }
}