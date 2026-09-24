package org.bankautomation.database;

import org.testng.Assert;
import org.testng.annotations.Test;

public class AccountDatabaseTest {

    @Test(groups = {"database", "smoke"})
    public void shouldHaveAccountsInDatabase() {

        int accountCount =
                DatabaseUtils.getAccountCount();

        System.out.println(
                "Accounts in database: " + accountCount
        );

        Assert.assertTrue(
                accountCount > 0,
                "Database should contain at least one account"
        );
    }

    @Test(groups = {"database"})
    public void shouldContainTestUserChequeAccount() {

        boolean exists =
                DatabaseUtils.accountExists(
                        "1000000001"
                );

        Assert.assertTrue(
                exists,
                "CHEQUE account 1000000001 should exist"
        );
    }

    @Test(groups = {"database"})
    public void shouldContainTestUserSavingsAccount() {

        boolean exists =
                DatabaseUtils.accountExists(
                        "1000000002"
                );

        Assert.assertTrue(
                exists,
                "SAVINGS account 1000000002 should exist"
        );
    }

    @Test(groups = {"database"})
    public void shouldHaveValidChequeAccountBalance() {

        java.math.BigDecimal balance =
                DatabaseUtils.getAccountBalance(
                        "1000000001"
                );

        System.out.println(
                "CHEQUE balance: R " + balance
        );

        Assert.assertNotNull(
                balance,
                "CHEQUE account balance should not be null"
        );

        Assert.assertTrue(
                balance.compareTo(
                        java.math.BigDecimal.ZERO
                ) >= 0,
                "CHEQUE account balance should not be negative"
        );
    }

    @Test(groups = {"database"})
    public void shouldHaveValidSavingsAccountBalance() {

        java.math.BigDecimal balance =
                DatabaseUtils.getAccountBalance(
                        "1000000002"
                );

        System.out.println(
                "SAVINGS balance: R " + balance
        );

        Assert.assertNotNull(
                balance,
                "SAVINGS account balance should not be null"
        );

        Assert.assertTrue(
                balance.compareTo(
                        java.math.BigDecimal.ZERO
                ) >= 0,
                "SAVINGS account balance should not be negative"
        );
    }
}