package org.bankautomation.ui;

import org.bankautomation.pages.LoginPage;
import org.bankautomation.pages.TransferPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TransferTest extends BaseTest {

    @Test(groups = {"ui", "smoke"})
    public void shouldCompleteTransferSuccessfully() {

        LoginPage loginPage =
                new LoginPage(driver);

        loginPage.loginAsTestUser();

        TransferPage transferPage =
                new TransferPage(driver);

        transferPage.openTransferPage();

        Assert.assertTrue(
                transferPage.isTransferPageDisplayed(),
                "Transfer page should be displayed"
        );

        transferPage.transfer(
                "2",
                "1000000003",
                "10.00",
                "Automation Test"
        );

        Assert.assertTrue(
                transferPage.isTransferSuccessful(),
                "Successful transfer message should be displayed"
        );

        String successMessage =
                transferPage.getSuccessMessage();

        System.out.println(
                "Transfer result: " + successMessage
        );

        Assert.assertTrue(
                successMessage.contains("Transfer successful"),
                "Transfer should be successful"
        );
    }

    @Test(groups = {"ui", "regression"})
    public void shouldRejectInsufficientFunds() {

        LoginPage loginPage =
                new LoginPage(driver);

        loginPage.loginAsTestUser();

        TransferPage transferPage =
                new TransferPage(driver);

        transferPage.openTransferPage();

        transferPage.transfer(
                "1",
                "1000000003",
                "999999999.00",
                "Insufficient Funds Test"
        );

        Assert.assertTrue(
                transferPage.isTransferErrorDisplayed(),
                "Transfer error should be displayed"
        );

        String errorMessage =
                transferPage.getErrorMessage();

        System.out.println(
                "Transfer error: " + errorMessage
        );

        Assert.assertEquals(
                errorMessage,
                "Insufficient funds",
                "Incorrect error message"
        );
    }

    @Test(groups = {"ui", "regression"})
    public void shouldRejectSameAccountTransfer() {

        LoginPage loginPage =
                new LoginPage(driver);

        loginPage.loginAsTestUser();

        TransferPage transferPage =
                new TransferPage(driver);

        transferPage.openTransferPage();

        transferPage.transfer(
                "1",
                "1000000001",
                "10.00",
                "Same account transfer"
        );

        Assert.assertTrue(
                transferPage.isTransferErrorDisplayed(),
                "Transfer error should be displayed"
        );

        String errorMessage =
                transferPage.getErrorMessage();

        System.out.println(
                "Same account transfer error: " + errorMessage
        );

        Assert.assertEquals(
                errorMessage,
                "Cannot transfer money to the same account",
                "Incorrect same-account transfer error message"
        );
    }

    @Test(groups = {"ui", "regression"})
    public void shouldRejectInvalidRecipientAccount() {

        LoginPage loginPage =
                new LoginPage(driver);

        loginPage.loginAsTestUser();

        TransferPage transferPage =
                new TransferPage(driver);

        transferPage.openTransferPage();

        transferPage.transfer(
                "1",
                "9999999999",
                "10.00",
                "Invalid recipient test"
        );

        Assert.assertTrue(
                transferPage.isTransferErrorDisplayed(),
                "Transfer error should be displayed"
        );

        String errorMessage =
                transferPage.getErrorMessage();

        System.out.println(
                "Invalid recipient error: " + errorMessage
        );

        Assert.assertEquals(
                errorMessage,
                "Destination account not found",
                "Incorrect invalid recipient error message"
        );
    }

}