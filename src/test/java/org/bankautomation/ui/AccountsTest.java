package org.bankautomation.ui;

import org.bankautomation.pages.AccountsPage;
import org.bankautomation.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AccountsTest extends BaseTest {

    @Test(groups = {"ui", "smoke"})
    public void shouldDisplayTestUserAccounts() {

        LoginPage loginPage =
                new LoginPage(driver);

        loginPage.loginAsTestUser();

        AccountsPage accountsPage =
                new AccountsPage(driver);

        accountsPage.openAccountsPage();

        Assert.assertTrue(
                accountsPage.isAccountsPageDisplayed(),
                "Accounts page should be displayed"
        );

        Assert.assertTrue(
                accountsPage.isAccountNumberDisplayed(
                        "1000000001"
                ),
                "Test user's cheque account should be displayed"
        );

        Assert.assertTrue(
                accountsPage.isAccountNumberDisplayed(
                        "1000000002"
                ),
                "Test user's savings account should be displayed"
        );

        System.out.println(
                "Accounts page content:"
        );

        System.out.println(
                accountsPage.getPageText()
        );
    }

    @Test(groups = {"ui"})
    public void shouldDisplayAccountTypes() {

        LoginPage loginPage =
                new LoginPage(driver);

        loginPage.loginAsTestUser();

        AccountsPage accountsPage =
                new AccountsPage(driver);

        accountsPage.openAccountsPage();

        Assert.assertTrue(
                accountsPage.isAccountTypeDisplayed(
                        "CHEQUE"
                ),
                "CHEQUE account type should be displayed"
        );

        Assert.assertTrue(
                accountsPage.isAccountTypeDisplayed(
                        "SAVINGS"
                ),
                "SAVINGS account type should be displayed"
        );
    }

    @Test(groups = {"ui"})
    public void recipientShouldOnlySeeOwnAccount() {

        LoginPage loginPage =
                new LoginPage(driver);

        loginPage.loginAsRecipient();

        AccountsPage accountsPage =
                new AccountsPage(driver);

        accountsPage.openAccountsPage();

        Assert.assertTrue(
                accountsPage.isAccountNumberDisplayed(
                        "1000000003"
                ),
                "Recipient account should be displayed"
        );

        String pageText =
                accountsPage.getPageText();

        Assert.assertFalse(
                pageText.contains("1000000001"),
                "Recipient should not see test user's cheque account"
        );

        Assert.assertFalse(
                pageText.contains("1000000002"),
                "Recipient should not see test user's savings account"
        );
    }
}