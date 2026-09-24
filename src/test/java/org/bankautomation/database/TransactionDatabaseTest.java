package org.bankautomation.database;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TransactionDatabaseTest {

    @Test(groups = {"database", "smoke"})
    public void shouldHaveTransactionsInDatabase() {

        int transactionCount =
                DatabaseUtils.getTransactionCount();

        System.out.println(
                "Transactions in database: "
                        + transactionCount
        );

        Assert.assertTrue(
                transactionCount > 0,
                "Database should contain at least one transaction"
        );
    }

    @Test(groups = {"database"})
    public void shouldContainKnownTransaction() {

        boolean exists =
                DatabaseUtils.transactionExists(
                        14L
                );

        Assert.assertTrue(
                exists,
                "Transaction ID 14 should exist"
        );
    }
}