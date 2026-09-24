package org.bankautomation.ui;

import org.bankautomation.pages.LoginPage;
import org.bankautomation.pages.TransactionPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TransactionTest extends BaseTest {

    @Test
    public void shouldDisplayTransactionsPage() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.loginAsTestUser();

        TransactionPage transactionPage =
                new TransactionPage(driver);

        transactionPage.openTransactionsPage();

        Assert.assertTrue(
                transactionPage.isPageDisplayed(),
                "Transactions page was not displayed"
        );

        System.out.println(
                "Transactions page displayed successfully"
        );
    }

    @Test
    public void shouldDisplayTransactionHistory() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.loginAsTestUser();

        TransactionPage transactionPage =
                new TransactionPage(driver);

        transactionPage.openTransactionsPage();

        int transactionCount =
                transactionPage.getTransactionCount();

        System.out.println(
                "Transaction count: " + transactionCount
        );

        Assert.assertTrue(
                transactionCount > 0,
                "No transactions were displayed"
        );
    }

    @Test
    public void shouldDisplayCompletedTransfer() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.loginAsTestUser();

        TransactionPage transactionPage =
                new TransactionPage(driver);

        transactionPage.openTransactionsPage();

        int transactionCount =
                transactionPage.getTransactionCount();

        Assert.assertTrue(
                transactionCount > 0,
                "No transactions found"
        );

        String transactionId =
                transactionPage.getTransactionId(0);

        String transactionType =
                transactionPage.getTransactionType(0);

        String fromAccount =
                transactionPage.getFromAccount(0);

        String toAccount =
                transactionPage.getToAccount(0);

        String amount =
                transactionPage.getTransactionAmount(0);

        String status =
                transactionPage.getTransactionStatus(0);

        System.out.println(
                "Transaction ID: " + transactionId
        );

        System.out.println(
                "Transaction Type: " + transactionType
        );

        System.out.println(
                "From Account: " + fromAccount
        );

        System.out.println(
                "To Account: " + toAccount
        );

        System.out.println(
                "Amount: " + amount
        );

        System.out.println(
                "Status: " + status
        );

        Assert.assertEquals(
                transactionType,
                "TRANSFER"
        );

        Assert.assertEquals(
                fromAccount,
                "1000000001"
        );

        Assert.assertEquals(
                toAccount,
                "1000000003"
        );

        Assert.assertEquals(
                status,
                "COMPLETED"
        );
    }

    @Test
    public void shouldDisplayTransactionReference() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.loginAsTestUser();

        TransactionPage transactionPage =
                new TransactionPage(driver);

        transactionPage.openTransactionsPage();

        String reference =
                transactionPage.getReference(0);

        System.out.println(
                "Transaction reference: " + reference
        );

        Assert.assertEquals(
                reference,
                "Test Transfer"
        );
    }

    @Test
    public void shouldDisplayAccountBalance() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.loginAsTestUser();

        TransactionPage transactionPage =
                new TransactionPage(driver);

        transactionPage.openTransactionsPage();

        String balance =
                transactionPage.getSelectedAccountBalance();

        System.out.println(
                "Account balance: " + balance
        );

        Assert.assertTrue(
                balance.contains("R"),
                "Account balance was not displayed"
        );
    }
}