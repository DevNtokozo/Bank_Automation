package org.bankautomation.ui;

import org.bankautomation.pages.LoginPage;
import org.bankautomation.pages.TransactionPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TransactionTest extends BaseTest {

    @Test
    public void shouldDisplayTransactionsPage() {

        LoginPage loginPage =
                new LoginPage(driver);

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

        LoginPage loginPage =
                new LoginPage(driver);

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

        LoginPage loginPage =
                new LoginPage(driver);

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

        String expectedReference =
                "Test Transfer";

        int rowIndex =
                transactionPage.findTransactionByReference(
                        expectedReference
                );

        Assert.assertTrue(
                rowIndex >= 0,
                "Test Transfer transaction was not found"
        );

        String transactionId =
                transactionPage.getTransactionId(
                        rowIndex
                );

        String transactionType =
                transactionPage.getTransactionType(
                        rowIndex
                );

        String fromAccount =
                transactionPage.getFromAccount(
                        rowIndex
                );

        String toAccount =
                transactionPage.getToAccount(
                        rowIndex
                );

        String amount =
                transactionPage.getTransactionAmount(
                        rowIndex
                );

        String status =
                transactionPage.getTransactionStatus(
                        rowIndex
                );

        System.out.println(
                "Transaction ID: " +
                        transactionId
        );

        System.out.println(
                "Transaction Type: " +
                        transactionType
        );

        System.out.println(
                "From Account: " +
                        fromAccount
        );

        System.out.println(
                "To Account: " +
                        toAccount
        );

        System.out.println(
                "Amount: " +
                        amount
        );

        System.out.println(
                "Status: " +
                        status
        );

        Assert.assertEquals(
                transactionType,
                "TRANSFER",
                "Transaction type should be TRANSFER"
        );

        Assert.assertEquals(
                fromAccount,
                "1000000001",
                "Transfer should come from account 1000000001"
        );

        Assert.assertEquals(
                toAccount,
                "1000000003",
                "Transfer should go to account 1000000003"
        );

        Assert.assertEquals(
                status,
                "COMPLETED",
                "Transfer should have COMPLETED status"
        );

        Assert.assertEquals(
                transactionPage.getReference(rowIndex),
                expectedReference,
                "Transaction reference is incorrect"
        );
    }

    @Test
    public void shouldDisplayTransactionReference() {

        LoginPage loginPage =
                new LoginPage(driver);

        loginPage.loginAsTestUser();

        TransactionPage transactionPage =
                new TransactionPage(driver);

        transactionPage.openTransactionsPage();

        String expectedReference =
                "Test Transfer";

        int rowIndex =
                transactionPage.findTransactionByReference(
                        expectedReference
                );

        Assert.assertTrue(
                rowIndex >= 0,
                "Test Transfer transaction was not found"
        );

        String reference =
                transactionPage.getReference(
                        rowIndex
                );

        System.out.println(
                "Transaction reference: " +
                        reference
        );

        Assert.assertEquals(
                reference,
                expectedReference
        );
    }

    @Test
    public void shouldDisplayAccountBalance() {

        LoginPage loginPage =
                new LoginPage(driver);

        loginPage.loginAsTestUser();

        TransactionPage transactionPage =
                new TransactionPage(driver);

        transactionPage.openTransactionsPage();

        String balance =
                transactionPage.getSelectedAccountBalance();

        System.out.println(
                "Account balance: " +
                        balance
        );

        Assert.assertNotNull(
                balance,
                "Account balance was not displayed"
        );

        Assert.assertTrue(
                balance.contains("R"),
                "Account balance should contain R"
        );
    }
}